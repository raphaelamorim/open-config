package org.openconfig.transformers;

import org.openconfig.transformers.Transformer;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.SimpleNode;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.IntrospectionException;
import static java.beans.Introspector.getBeanInfo;
import java.util.*;
import static java.util.Arrays.asList;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Richard L. Burton III
 */
public class BeanToNodeTransformer implements Transformer<Object, ComplexNode> {

    private static final Set<String> EXCLUDE_NAMES = new HashSet<String>(asList("class"));

    public ComplexNode transform(Object bean) {
        if (bean != null) {
            return transform(bean, bean.getClass().getSimpleName());
        } else {
            return null;
        }

    }

    protected ComplexNode transform(Object bean, String name) {
        ComplexNode node = null;

        if (bean != null) {
            Map<String, AbstractNode> attributes = new HashMap<String, AbstractNode>();
            node = new ComplexNode(name, attributes);

            if (Collection.class.isAssignableFrom(bean.getClass())) {
                Collection collection = (Collection) bean;
                for (Object object : collection) {
                    toNode(object, attributes);
                }
            } else if (bean.getClass().isArray()) {
                for (Object object : (Object[]) bean) {
                    toNode(object, attributes);
                }
            } else {
                toNode(bean, attributes);
            }
        }
        return node;
    }

    public void toNode(Object bean, Map<String, AbstractNode> attributes) {
        try {
            BeanInfo info = getBeanInfo(bean.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (!EXCLUDE_NAMES.contains(pd.getName())) {
                    if (pd.getPropertyType().isPrimitive() || pd.getPropertyType().equals(String.class)) {
                        AbstractNode attribute = new SimpleNode(pd.getName(), readProperty(pd, bean));
                        attributes.put(pd.getName(), attribute);
                    } else {
                        AbstractNode child = transform(readProperty(pd, bean), pd.getName());
                        if (child != null) {
                            attributes.put(pd.getName(), child);
                        }
                    }
                }
            }
        } catch (IntrospectionException ie) {
            throw new RuntimeException(ie);
        } catch (InvocationTargetException ite) {
            throw new RuntimeException(ite);
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(iae);
        }
    }

    protected Object readProperty(PropertyDescriptor pd, Object instance) throws IllegalAccessException, InvocationTargetException {
        Method readMethod = pd.getReadMethod();
        return readMethod.invoke(instance, null);
    }
}
