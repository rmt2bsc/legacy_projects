/**
 * 
 */
package testcases.util;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.util.GeneralUtil;

/**
 * @author rterrell
 *
 */
public class TestPropertiesFileIO {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void readFromFile() {
	Properties prop = GeneralUtil.loadProperties("/application.properties");
	String val = prop.getProperty("jdbc.oracle.driverClassName");
	Assert.assertNotNull(val);
    }
    
    @Test
    public void saveToFile() {
	Properties prop = GeneralUtil.loadProperties("/application.properties");
	prop.setProperty("serverName", "Sever Name Changed");
	
	// Try to determine the current directory
	File file = new File(".");
	String path = null;
	try {
	    path = file.getCanonicalPath();
	}
	catch (IOException e) {
	    e.printStackTrace();
	    return;
	}
	path += "\\new.properties";
	GeneralUtil.persistProperties(prop, path);
	
	
	Properties newProp = GeneralUtil.loadProperties(path);
	String val = prop.getProperty("serverName");
	Assert.assertNotNull(val);
    }
    
}
