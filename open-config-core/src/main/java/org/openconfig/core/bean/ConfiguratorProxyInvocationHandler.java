package org.openconfig.core.bean;

import org.apache.log4j.Logger;
import org.openconfig.core.ConfiguratorProxy;
import org.openconfig.core.Accessor;
import org.openconfig.core.InvocationContext;
import org.openconfig.core.DatatypeProxy;
import static org.openconfig.core.Accessor.GETTER;
import org.openconfig.transformers.Transformer;
import org.openconfig.transformers.EnumValue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;

/**
 * This class is the invocation handler for method calls. Depending on the return type,
 * this handler may return the value of the request configuration parameter (if the return
 * type is a primitive data type) or a proxy if the return type is a complex type.
 *
 * @author Richard L. Burton III
 */
public class ConfiguratorProxyInvocationHandler implements ProxyInvocationHandler {

    private Logger LOGGER = Logger.getLogger(ConfiguratorProxyInvocationHandler.class);

    private ConfiguratorProxy proxy;

    // todo: Inject this later and define an interface.
    private DataTypeManager dataTypeManager = new DataTypeManager();

    public ConfiguratorProxyInvocationHandler(ConfiguratorProxy proxy) {
        this.proxy = proxy;
    }

    public boolean shouldProxy(Method method) {
        Class clazz = method.getReturnType();
        return isSupported(clazz) && !clazz.isPrimitive() && isNotFinal(clazz) && !clazz.isEnum();
    }

    private boolean isNotFinal(Class clazz) {
        return !Modifier.isFinal(clazz.getModifiers());
    }

    public Object handle(InvocationContext invocationContext, Method method, Object[] arguments, Accessor accessor) {
        invocationContext = new InvocationContext(invocationContext);
        Class clazz = method.getReturnType();
        if (accessor == GETTER) {
            if (shouldProxy(method)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Enhancing complex type of " + clazz.toString());
                }
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(clazz);
                DatatypeProxy datatypeProxy = new DatatypeProxy(this, proxy.getPropertyNormalizer(), invocationContext);
                enhancer.setCallback(datatypeProxy);
                return enhancer.create();
            } else {
                // In the case of Enums, a wrapper EnumValue is created to
                // encapsulate all the required data for the transformer.
                Object returnvalue = proxy.getDataProvider().getValue(invocationContext);
                if (clazz.isEnum()) {
                    returnvalue = new EnumValue(clazz, returnvalue);
                    clazz = EnumValue.class;
                }

                Transformer transformer = dataTypeManager.findTransformer(clazz);
                return transformer.transform(returnvalue);
            }
        } else {
            //proxy.getDataProvider().setValue(property, arguments);
            throw new UnsupportedOperationException("This logic hasn't been implemented yet.");
        }
    }

    protected boolean isSupported(Class clazz) {
        if (clazz.isArray()) {
            throw new UnsupportedOperationException("Returning an Array isn't unsupported.");
        } else if (clazz.isAnnotation()) {
            throw new UnsupportedOperationException("Returning an Annotation isn't unsupported.");
        }
        return true;
    }

}
