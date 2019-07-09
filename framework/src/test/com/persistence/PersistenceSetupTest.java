package com.persistence;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.persistence.CannotCreateManagerException;
import com.api.persistence.PersistenceFactory;
import com.api.persistence.PersistenceManager;

/**
 * @author Roy Terrell
 *
 */
public class PersistenceSetupTest {
    
    private String configName;
    
    private String classpathConfig;
    
    private String filesystemConfig;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.configName = "persist";  // Do not include file extension
	this.classpathConfig = "com.api.persistence." + this.configName;
	this.filesystemConfig = "C:\\projects\\framework\\src\\test\\data\\" + this.configName + ".properties";
	
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    
    @Test
    public void setupUsingDefaultConfig() {
	PersistenceFactory f = PersistenceFactory.getInstance();
	PersistenceManager api = null;
	try {
	    api = f.createManager();
	}
	catch (CannotCreateManagerException e) {
	    e.printStackTrace();
	}
	finally {
	    api.shutDown();
	}
    }
    
    
    @Test
    public void setupUsingFileSysConfig() {
	PersistenceFactory f = PersistenceFactory.getInstance();
	PersistenceManager api = null;
	try {
	    api = f.createManager(this.filesystemConfig);
	}
	catch (CannotCreateManagerException e) {
	    e.printStackTrace();
	}
	finally {
	    api.shutDown();
	}
    }
    
    @Test
    public void setupUsingClasspathConfig() {
	PersistenceFactory f = PersistenceFactory.getInstance();
	PersistenceManager api = null;
	try {
	    api = f.createManager(this.classpathConfig);
	}
	catch (CannotCreateManagerException e) {
	    e.printStackTrace();
	}
	finally {
	    api.shutDown();
	}
    }
}
