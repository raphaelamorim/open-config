package org.openconfig.core;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import static org.openconfig.core.Accessor.getAccessor;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.core.bean.ProxyInvocationHandler;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;

/**
 * TODO: Should this class and ConfiguratorProxy extend a common base class? There's a lot of duplication of code here..
 *
 * @author Dushyanth (Dee) Inguva
 */
public class DatatypeProxy implements MethodInterceptor {

    private static final Logger LOGGER = getLogger(ConfiguratorProxy.class);

    private final InvocationContext invocationContext;

    private final ProxyInvocationHandler proxyInvocationHandler;

    private static final int PROPERTY_NAME_INDEX = 2;

    private static final Pattern GET_PROPERTY_REGEX = compile("(get+)(.*)");

    private static final Pattern SET_PROPERTY_REGEX = compile("(set+)(.*)");

    private final PropertyNormalizer propertyNormalizer;

    public DatatypeProxy(ProxyInvocationHandler proxyInvocationHandler, PropertyNormalizer propertyNormalizer, InvocationContext invocationContext) {
        this.proxyInvocationHandler = proxyInvocationHandler;
        this.propertyNormalizer = propertyNormalizer;
        this.invocationContext = new InvocationContext(invocationContext);
    }

    public Object intercept(Object source, Method method, Object[] arguments, MethodProxy proxy) throws Throwable {
        InvocationContext newContext = new InvocationContext(invocationContext);
        String name = method.getName();
        String property;
        Matcher matcher;
        Accessor accessor = getAccessor(name);

        switch (accessor) {
            case GETTER:
                matcher = GET_PROPERTY_REGEX.matcher(name);
                break;
            case SETTER:
                matcher = SET_PROPERTY_REGEX.matcher(name);
                break;
            case TOSTRING:
            case HASHCODE:
                return proxy.invokeSuper(source, arguments);
            default:
                throw new MethodInvocationException(source, method);
        }

        if (matcher.find()) {
            property = matcher.group(PROPERTY_NAME_INDEX);
            property = propertyNormalizer.normalize(property);
            newContext.addInvocation(new Invocation(method, property));
            return proxyInvocationHandler.handle(newContext, method, arguments, accessor);
        }

        throw new MethodInvocationException(source, method);
    }

}
