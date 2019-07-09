package com.general;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.util.RMT2HashUtility;


public class StringTest {

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
    public void testStringToMD5() {
	String str = "This is a String to MD5 test";
	String results = RMT2HashUtility.md5(str);
	System.out.println("MD5: " + results);
    }
    
    
    @Test
    public void testStringToSHA() {
	String str = "This is a String to SHA test";
	String results = RMT2HashUtility.sha(str);
	System.out.println("SHA: " + results);
    }
}
