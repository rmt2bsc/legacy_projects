package testcases.dao;



import java.util.List;

import junit.framework.Assert;

import modules.rtv.RtvDao;
import modules.rtv.si.SIRTVFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author rterrell
 *
 */
public class SIRTVAdminDaoTest extends CommonDaoTest {

    private SIRTVFactory f;
    
    private RtvDao dao;
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new SIRTVFactory();
	this.dao = f.getDaoInstance(this.token);
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
    public void testFetchAllHeader() {
	try {
	    Object list = this.dao.fetchHeader();  
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    
    @Test
    public void testFetchDetail() {
	try {
	    Object list = this.dao.fetchItems(884263);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    

}
