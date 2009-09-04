package org.openconfig.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Dushyanth (Dee) Inguva
 */
public class InvocationContext {

    private Class interfaceClass;

    private List<Method> methods = new LinkedList<Method>();

    public InvocationContext(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void addMethod(Method method){
        methods.add(method);
    }

    public Iterator<Method> getIterator(){
        return methods.iterator();
    }

    public List<Method> getMethods() {
        return Collections.unmodifiableList(methods);
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

}
