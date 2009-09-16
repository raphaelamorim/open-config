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

    private List<Invocation> invocations = new LinkedList<Invocation>();

    private String configuratorName;

    public InvocationContext(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
        this.configuratorName =  interfaceClass.getSimpleName();
    }

    public InvocationContext(InvocationContext invocationContext) {
        this(invocationContext.getInterfaceClass());
        this.invocations.addAll(invocationContext.getInvocations());
    }

    public void addInvocation(Invocation invocation){
        invocations.add(invocation);
    }

    public Iterator<Invocation> getIterator(){
        return invocations.iterator();
    }

    public List<Invocation> getInvocations() {
        return Collections.unmodifiableList(invocations);
    }

    public String getConfiguratorName() {
        return configuratorName;
    }

    public Invocation getLastInvocation(){
        if(invocations.size() > 0){
            return invocations.get(invocations.size()-1);
        }else{
            throw new IllegalStateException("There are no invocations in this InvocationContext!");
        }
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }
    
}
