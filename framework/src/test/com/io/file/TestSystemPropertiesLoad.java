package com.io.file;

import junit.framework.Assert;

import org.junit.Test;

import com.util.RMT2File;


/**
 * @author appdev
 *
 */
public class TestSystemPropertiesLoad {

    @Test
    public void testLoadUsingClasspath() {
        RMT2File.loadSystemProperties("TestSystemParms");
        String parm1 = System.getProperty("SAXDriver");
        String parm2 = System.getProperty("apptitle");
        Assert.assertNotNull(parm1);
        Assert.assertNotNull(parm2);
    }
    
    @Test
    public void testLoadUsingFileSystem() {
        RMT2File.loadSystemProperties("C:/projects/framework/src/test/TestSystemParms.properties");
        String parm1 = System.getProperty("dbdriver");
        String parm2 = System.getProperty("dburl");
        Assert.assertNotNull(parm1);
        Assert.assertNotNull(parm2);
    }
    
    @Test
    public void testLoadUsingClasspathError() {
        try {
            RMT2File.loadSystemProperties("/TestSystemParms");    
            Assert.fail("Test failed for testLoadUsingClasspathError...an exception should have been thrown!");
        }
        catch (Exception e) {
            // ..properties file should not have been found.
        }
    }
}
