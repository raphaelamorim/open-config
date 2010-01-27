package org.openconfig.core.bean;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openconfig.core.Accessor;
import org.openconfig.core.ConfiguratorProxy;
import org.openconfig.core.InvocationContext;
import org.openconfig.providers.DataProvider;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConfiguratorProxyInvocationHandlerTest {

    private ProxyInvocationHandler handler;

    @Mock
    private DataProvider dataProvider;

    private InvocationContext invocationContext;

    @Test
    public void verifyGetStringProperty() throws NoSuchMethodException {
        Accessor accessor = Accessor.getAccessor("getName");
        Object[] arguments = {};
        Method method = MyConfigurator.class.getDeclaredMethod("getName");
        when(dataProvider.getValue((InvocationContext)anyObject())).thenReturn("Richard");
        Object name = handler.handle(invocationContext, method, arguments, accessor);
        assertEquals("Richard", name);
    }

    @Test
    public void verifyFailWhenGetterAcceptsParameter() throws NoSuchMethodException {
        Accessor accessor = Accessor.getAccessor("getString");
        Object[] arguments = {"name"};
        Method method = MyConfigurator.class.getDeclaredMethod("getString", String.class);

        try{
            handler.handle(invocationContext, method, arguments, accessor);
            fail("Expected IllegalArgumentException since the getter method accepts a parameter.");
        }catch(IllegalArgumentException iae){
        }
    }

    @Test
    public void verifyFailWithReturningOfArray() throws NoSuchMethodException {
        Accessor accessor = Accessor.getAccessor("getStrings");
        Object[] arguments = {};
        Method method = MyConfigurator.class.getDeclaredMethod("getStrings");

        try{
            handler.handle(invocationContext, method, arguments, accessor);
            fail("Expected UnsupportedOperationException since arrays are not supported.");
        }catch(UnsupportedOperationException iae){
        }
    }

    @Test
    public void verifyFailWithReturningOfAnnotation() throws NoSuchMethodException {
        Accessor accessor = Accessor.getAccessor("getAnnotation");
        Object[] arguments = {};
        Method method = MyConfigurator.class.getDeclaredMethod("getAnnotation");
        try{
            handler.handle(invocationContext, method, arguments, accessor);
            fail("Expected UnsupportedOperationException since annotation are not supported.");
        }catch(UnsupportedOperationException iae){
        }
    }

    @Test
    public void verifyFailForSetter() throws NoSuchMethodException {
        Accessor accessor = Accessor.getAccessor("setName");
        Object[] arguments = {"Richard"};
        Method method = MyConfigurator.class.getDeclaredMethod("setName", String.class);
        InvocationContext invocationContext = new InvocationContext(MyConfigurator.class);

        try{
            handler.handle(invocationContext, method, arguments, accessor);
            fail("Expected UnsupportedOperationException since setters are not supported.");
        }catch(UnsupportedOperationException iae){
        }
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        invocationContext = new InvocationContext(MyConfigurator.class);
        ConfiguratorProxy proxy = new ConfiguratorProxy(MyConfigurator.class, false);
        proxy.setDataProvider(dataProvider);
        handler = new ConfiguratorProxyInvocationHandler(proxy);
    }

    interface MyConfigurator {
        String getName();
        void setName(String name);
        MyAnnotation getAnnotation();
        String getString(String name);
        String[] getStrings();
    }

    @interface MyAnnotation{

    }
}
