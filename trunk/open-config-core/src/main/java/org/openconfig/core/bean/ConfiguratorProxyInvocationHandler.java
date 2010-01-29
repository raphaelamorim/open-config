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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import static org.openconfig.util.Assert.*;

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
            isTrue(arguments.length == 0, "The getter method should not access an argument. Method " + method.toString());
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

    /**
     * TODO: We don't support Iterator, etc..
     * @param clazz
     * @return
     */
    protected boolean isSupported(Class clazz) {
        if (clazz.isArray()) {
            throw new UnsupportedOperationException("Returning an Array isn't unsupported. The class is " + clazz.getName());
        } else if (clazz.isAnnotation()) {
            throw new UnsupportedOperationException("Returning an Annotation isn't unsupported. The class is " + clazz.getName());
        } else if (clazz.isAssignableFrom(Collection.class)){
            throw new UnsupportedOperationException("Returning an instance of Collection isn't unsupported. The class is " + clazz.getName());
        } else if (clazz.isAssignableFrom(List.class)){
            throw new UnsupportedOperationException("Returning an instance of List isn't unsupported. The class is " + clazz.getName());
        } else if (clazz.isAssignableFrom(Set.class)){
            throw new UnsupportedOperationException("Returning an instance of Set isn't unsupported. The class is " + clazz.getName());
        } else if (clazz.isAssignableFrom(Map.class)){
            throw new UnsupportedOperationException("Returning an Map isn't unsupported. The class is " + clazz.getName());
        } else if (Modifier.isFinal(clazz.getModifiers()) && isNotAWrapper(clazz)){
            throw new UnsupportedOperationException("Returning a class that's final isn't unsupported. The class is " + clazz.getName());
        }
        return true;
    }

    protected boolean isNotAWrapper(Class clazz){
        return !(clazz == String.class  ||
               clazz == Boolean.class   ||
               clazz == Integer.class   ||
               clazz == Character.class ||
               clazz == Short.class     ||
               clazz == Byte.class      ||
               clazz == Long.class      ||
               clazz == Float.class     ||
               clazz == Double.class    ||
               clazz == boolean.class   ||
               clazz == int.class       ||
               clazz == char.class      ||
               clazz == short.class     ||
               clazz == byte.class      ||
               clazz == long.class      ||
               clazz == float.class     ||
               clazz == double.class);
    }
}
