package org.openconfig.core;

import java.lang.reflect.Method;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static java.util.regex.Pattern.compile;

import static org.openconfig.core.Accessor.*;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.core.bean.ProxyInvocationHandler;
import org.openconfig.event.EventPublisher;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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

    private static final char HIERARCHY_DELIMITOR = '.';

    private static final Pattern GET_PROPERTY_REGEX = compile("(get+)(.*)");

    private static final Pattern SET_PROPERTY_REGEX = compile("(set+)(.*)");

    private PropertyNormalizer propertyNormalizer;

    /**
     * A boolean to identify if the method hierarchy has been started.
     */
    private boolean initialized = false;

    /**
     * Preserves the method invocation in a string representation.
     */
    private StringBuilder hierarchy = new StringBuilder();

    /**
     * The class that handles the method invocations and delegates the work
     * off to the Configurator.
     */
    private ProxyInvocationHandler proxyInvocationHandler;

    private final EventPublisher eventPublisher;

    private String interfaceName;

    private final Class configuratorInterface;

    private final boolean alias;

    public ConfiguratorProxy(Class configuratorInterface, boolean alias, EventPublisher eventPublisher) {
        this.configuratorInterface = configuratorInterface;
        this.alias = alias;
        this.eventPublisher = eventPublisher;
        
        if (alias) {
            interfaceName = configuratorInterface.getSimpleName();
        }
    }

    public Object intercept(Object source, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
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
            handleHierarchy(property);
            LOGGER.debug("Method invocation hierarchy " + hierarchy);
            return proxyInvocationHandler.handle(method, property, arguments, accessor);
        }

        throw new MethodInvocationException(source, method);
    }

    protected void handleHierarchy(String property) {
        if (initialized) {
            hierarchy.append(HIERARCHY_DELIMITOR);
        } else {
            initialized = true;
            if (alias) {
                hierarchy.append(interfaceName).append(HIERARCHY_DELIMITOR);
            }
        }
        hierarchy.append(property);
    }

    /**
     * Returns a string representation of the method invocation hierarchy. An example of this
     * would person.getChild().getName() would return 'person.child.name'.
     *
     * @return String representation of the method invocation hierarchy.
     */
    public String toHierarchy() {
        return hierarchy.toString();
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

    public boolean isAliased() {
        return alias;
    }
}
