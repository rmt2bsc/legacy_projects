/**
 * 
 */
package com.io.file;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.util.RMT2File;

/**
 * @author WinXP User
 *
 */
public class TestOpenResourceBundle {
    
    private String resource;
    
    private String resourcePath;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.resource = "RMT2NamespaceContext.properties";
	this.resourcePath = "com.api.xml.RMT2NamespaceContext";
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindResourceUnqualified() {
	Object rc1, rc2;
	rc1 = RMT2File.getFileInputStream(this.resource);    
	
	// This one works with framework/webapp/resources on the classpath
	rc2 = RMT2File.getFileInputStream("/email/" + this.resource);
	
	rc1 = RMT2File.getFileInputStream(this.resourcePath);    
	rc2 = RMT2File.getFileInputStream("/" + this.resourcePath);
	return;
    }
}
