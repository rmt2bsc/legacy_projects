package testcases.dao;


import junit.framework.Assert;
import modules.GeneralDao;
import modules.GeneralDaoImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test General DAO functionality.
 * 
 * @author rterrell
 *
 */
public class GeneralDaoTest extends CommonDaoTest {

    private static final String INVALID_PARM = "Invalid_Display_IDT";
    
    private GeneralDao dao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.dao = new GeneralDaoImpl(this.token);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.dao.close();
	this.dao = null;
	super.tearDown();
    }

    @Test
    public void testFetchIdtFlagExists() {
	try {
	    boolean rc = this.dao.hasIdtAdminAccess(GeneralDao.IDT_ADMIN_FLAG_VALUE);
	    Assert.assertTrue(rc);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    @Test
    public void testFetchIdtFlagNotExists() {
	try {
	    boolean rc = this.dao.hasIdtAdminAccess(GeneralDaoTest.INVALID_PARM);
	    Assert.assertFalse(rc);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
}
