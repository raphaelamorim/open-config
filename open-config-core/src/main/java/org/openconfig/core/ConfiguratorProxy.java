package org.openconfig.core;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import static org.openconfig.core.Accessor.getAccessor;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.core.bean.ProxyInvocationHandler;
import org.openconfig.providers.DataProvider;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;

/**
 * The ConfiguratorProxy class handles the interception of method invocations on the
 * custom interface provided by the client and delegates the invocation to a
 * <tt>proxyInvocationHandler</tt>. An instance of this class is aware of the following items:
 * <p/>
 * 1. The custom interface of the Configuration class.
 * 2. How to parse the property/method being executed.
 * 3. The ProxyInvocationHandler that will fullfill the request.
 *
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class ConfiguratorProxy implements PropertyNormalizerable, MethodInterceptor {

    private static final Logger LOGGER = getLogger(ConfiguratorProxy.class);

    private static final int PROPERTY_NAME_INDEX = 2;

    private static final Pattern GET_PROPERTY_REGEX = compile("(get+)(.*)");

    private static final Pattern SET_PROPERTY_REGEX = compile("(set+)(.*)");

    private PropertyNormalizer propertyNormalizer;

    /**
     * The class that handles the method invocations and delegates the work
     * off to the Configurator.
     */
    private ProxyInvocationHandler proxyInvocationHandler;

    private final Class configuratorInterface;

    private DataProvider dataProvider;

    /**
     *
     * @param configuratorInterface
     */
    public ConfiguratorProxy(Class configuratorInterface) {
        this.configuratorInterface = configuratorInterface;
    }

    // TODO: hashCode() and equals(..)
    public Object intercept(Object source, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        InvocationContext invocationContext = new InvocationContext(configuratorInterface);
        String name = method.getName();
        String property;
        Matcher matcher;
        Accessor accessor = getAccessor(name);

        if(LOGGER.isDebugEnabled())
            LOGGER.debug("Extracting the property name from method " + name + " with accessor of " + accessor);

        switch (accessor) { // TODO: Escape logic should be in place for toString(...) and hashCode(...) that take parameters.
            case GETTER:
                matcher = GET_PROPERTY_REGEX.matcher(name);
                break;
            case SETTER:
                matcher = SET_PROPERTY_REGEX.matcher(name);
                break;
            case TOSTRING:
                return dataProvider.toString();
            case HASHCODE:
                return dataProvider.hashCode();
            default:
                throw new MethodInvocationException(source, method);
        }

        if (matcher.find()) {
            property = matcher.group(PROPERTY_NAME_INDEX);
            property = propertyNormalizer.normalize(property);
            invocationContext.addInvocation(new Invocation(method, property));
            return proxyInvocationHandler.handle(invocationContext, method, arguments, accessor);
        }

        throw new MethodInvocationException(source, method);
    }

    public void setPropertyNormalizer(PropertyNormalizer propertyNormalizer) {
        this.propertyNormalizer = propertyNormalizer;
    }

    public void setProxyInvocationHandler(ProxyInvocationHandler proxyInvocationHandler) {
        this.proxyInvocationHandler = proxyInvocationHandler;
    }

    public Class getConfiguratorInterface() {
        return configuratorInterface;
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public PropertyNormalizer getPropertyNormalizer() {
        return propertyNormalizer;
    }
}
