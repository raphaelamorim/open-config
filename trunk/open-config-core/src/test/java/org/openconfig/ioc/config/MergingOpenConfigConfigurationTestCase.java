package org.openconfig.ioc.config;

import static org.easymock.EasyMock.*;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Dushyanth (Dee) Inguva
 */
public class MergingOpenConfigConfigurationTestCase {

	private OpenConfigConfiguration mock1 = createMock(OpenConfigConfiguration.class);
	private OpenConfigConfiguration mock2 = createMock(OpenConfigConfiguration.class);
    private MergingOpenConfigConfiguration mergingOpenConfigConfiguration = new MergingOpenConfigConfiguration(mock1, mock2);
	String alias = "PropertyNormalizer";
	@SuppressWarnings("unchecked")
	Class fakeClass = String.class;

    @Test
    public void testPresentInFirst() throws IOException {
    	expect(mock1.hasClass(alias)).andReturn(true);
    	expect(mock1.getClass(alias)).andReturn(fakeClass);
    	replay(mock1, mock2);
    	
    	assertTrue(mergingOpenConfigConfiguration.hasClass(alias));
    	assertEquals(mergingOpenConfigConfiguration.getClass(alias), fakeClass);
    	verify(mock1, mock2);
    }

    @Test
    public void testPresentInSecond() throws IOException {
    	expect(mock1.hasClass(alias)).andReturn(false);
    	expect(mock2.hasClass(alias)).andReturn(true);
    	expect(mock1.getClass(alias)).andReturn(null);
    	expect(mock2.getClass(alias)).andReturn(fakeClass);
    	replay(mock1, mock2);
    	
    	assertTrue(mergingOpenConfigConfiguration.hasClass(alias));
    	assertEquals(mergingOpenConfigConfiguration.getClass(alias), fakeClass);
    	verify(mock1, mock2);
    }

    @Test
    public void testAbsentFromBoth() throws IOException {
    	expect(mock1.getClass(alias)).andReturn(null);
    	expect(mock2.getClass(alias)).andReturn(null);
    	
    	replay(mock1, mock2);
    	assertNull(mergingOpenConfigConfiguration.getClass(alias));
    	verify(mock1, mock2);
    }
    
    @Test(expected=UnsupportedOperationException.class) 
    public void testProcess() throws Exception {
    	mergingOpenConfigConfiguration.process("");
    }
}
