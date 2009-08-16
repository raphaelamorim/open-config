package org.openconfig.providers.ast.transformers;

import org.openconfig.providers.ast.transformers.Transformer;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.SimpleNode;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.IntrospectionException;
import static java.beans.Introspector.getBeanInfo;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
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
            Set<Node> attributes = new HashSet<Node>();
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

    public void toNode(Object bean, Set<Node> attributes) {
        try {
            BeanInfo info = getBeanInfo(bean.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (!EXCLUDE_NAMES.contains(pd.getName())) {
                    if (pd.getPropertyType().isPrimitive() || pd.getPropertyType().equals(String.class)) {
                        Node attribute = new SimpleNode(pd.getName(), readProperty(pd, bean));
                        attributes.add(attribute);
                    } else {
                        Node child = transform(readProperty(pd, bean), pd.getName());
                        if (child != null) {
                            attributes.add(child);
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
