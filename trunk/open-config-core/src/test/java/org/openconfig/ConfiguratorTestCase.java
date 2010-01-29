package org.openconfig;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.NoOpConfiguratorLocator;
import org.openconfig.junit.LocalTestCase;

/**
 * @author Richard L. Burton III
 */
public class ConfiguratorTestCase extends LocalTestCase {

    private ConfiguratorFactory factory;

    private MyConfigurator configurator;
    private UnSupportedConfigurator unSupportedConfigurator;

    @Before
    public void setUp() {
        ConfigurationFactoryBuilder configurationFactoryBuilder = new ConfigurationFactoryBuilder();
        configurationFactoryBuilder.setConfigurationLocator(new NoOpConfiguratorLocator());
        factory = configurationFactoryBuilder.build();
        unSupportedConfigurator = factory.newInstance(UnSupportedConfigurator.class);
        configurator = factory.newInstance(MyConfigurator.class);
    
    }

    @Test
    public void testNamingConvention() throws Exception {
        assertEquals("Bond", configurator.getName());
        assertEquals("Richard", configurator.getAbstractObject().getName());
        assertEquals(45, configurator.getAge());
        assertEquals(46, configurator.getAge2());
        assertEquals("Austin Powers", configurator.getPerson().getChild().getName());
        assertEquals("James Bond", configurator.getPerson().getName());
    }

    @Test
    public void verifyInvalidAnnotationType() throws Exception {
        try {
            unSupportedConfigurator.getUnsupportedAnnotation();
            fail("Expected UnsupportedOperationException for Annotations!");
        }catch(UnsupportedOperationException uoe){
        }
    }

    @Test
    public void verifyInvalidArrayType() throws Exception {
        try {
            unSupportedConfigurator.getUnsupportedArray();
            fail("Expected UnsupportedOperationException for Array!");
        }catch(UnsupportedOperationException uoe){
        }
    }

    @Test
    public void verifyInvalidIntArray() throws Exception {
        try {
            unSupportedConfigurator.getUnsupportedIntArray();
            fail("Expected UnsupportedOperationException for Int Array!");
        }catch(UnsupportedOperationException uoe){
        }
    }

    @Test
    public void verifyInvalidList() throws Exception {
        try {
            unSupportedConfigurator.getUnsupportedList();
            fail("Expected UnsupportedOperationException for List!");
        }catch(UnsupportedOperationException uoe){
        }
    }

    @Test
    public void verifyInvalidMap() throws Exception {
        try {
            unSupportedConfigurator.getUnsupportedMap();
            fail("Expected UnsupportedOperationException for Map!");
        }catch(UnsupportedOperationException uoe){
        }
    }

    @Test
    public void verifyInvalidSet() throws Exception {
        try {
            System.out.println(unSupportedConfigurator.getUnsupportedSet());
            fail("Expected UnsupportedOperationException for Set!");
        }catch(UnsupportedOperationException uoe){
        }
    }

    @Test
    public void verifyInvalidFinal() throws Exception {
        try {
            unSupportedConfigurator.getUnsupportedFinalObject();
            fail("Expected UnsupportedOperationException for a final class!");
        }catch(UnsupportedOperationException uoe){
        }
    }

}
