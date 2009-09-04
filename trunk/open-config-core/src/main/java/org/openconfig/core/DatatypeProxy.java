package org.openconfig.core;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;

import org.openconfig.core.bean.ProxyInvocationHandler;
import org.openconfig.core.bean.PropertyNormalizer;
import static org.openconfig.core.Accessor.getAccessor;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;

/**
 * @author Dushyanth (Dee) Inguva
 */
   public class DatatypeProxy implements MethodInterceptor {

    private InvocationContext invocationContext;

    private final ProxyInvocationHandler proxyInvocationHandler;

    private static final Logger LOGGER = getLogger(ConfiguratorProxy.class);

    private static final int PROPERTY_NAME_INDEX = 2;

    private static final char HIERARCHY_DELIMITOR = '.';

    private static final Pattern GET_PROPERTY_REGEX = compile("(get+)(.*)");

    private static final Pattern SET_PROPERTY_REGEX = compile("(set+)(.*)");

    private final PropertyNormalizer propertyNormalizer;

    private String parentContext;

    public DatatypeProxy(ProxyInvocationHandler proxyInvocationHandler, Class proxiedClass, PropertyNormalizer propertyNormalizer, String parentContext) {
        invocationContext = new InvocationContext(proxiedClass);
        this.proxyInvocationHandler = proxyInvocationHandler;
        this.propertyNormalizer = propertyNormalizer;
        this.parentContext = parentContext;
    } 


    /**
     *
     *
     * @param source
     * @param method
     * @param arguments
     * @param proxy
     * @return
     * @throws Throwable
     */
    public Object intercept(Object source, Method method, Object[] arguments, MethodProxy proxy) throws Throwable {
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
            default:
                throw new MethodInvocationException(source, method);
        }

        if (matcher.find()) {
            property = matcher.group(PROPERTY_NAME_INDEX);
            property = propertyNormalizer.normalize(property);
            invocationContext.addMethod(method);
            return proxyInvocationHandler.handle(invocationContext,method, parentContext + "." + property, arguments, accessor);
        }

        throw new MethodInvocationException(source, method);    }
}
