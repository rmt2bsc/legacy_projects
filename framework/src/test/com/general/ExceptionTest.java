/**
 * 
 */
package com.general;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.util.NotFoundException;
import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * @author appdev
 *
 */
public class ExceptionTest {

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
    public void testUncheckedExceptions() throws Exception {
	this.throwUncheckedException();
    }

    @Test
    public void testExceptions() {
	try {
	    this.throwException();
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}
    }

    private void throwException() throws Exception {

	try {
	    throw new NotFoundException("Not Found Exception thrown");
	}
	catch (NotFoundException e) {
	    System.out.println(e.getMessage());
	    throw new SystemException("Now throwing SystemException derived from NotFoundException from within catch block", e);
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	    throw new Exception("Rethrowing general Exception from within catch block", e);
	}
    }

    private void throwUncheckedException() throws Exception {

	try {
	    throw new NullPointerException("Null pointer Exception thrown");
	}
	catch (NotFoundException e) {
	    System.out.println(e.getMessage());
	    throw new SystemException("Now throwing SystemException derived from NotFoundException from within catch block", e);
	}
    }

}
