package testcases.dao;



import java.util.List;

import junit.framework.Assert;

import modules.transfer.TransferDao;
import modules.transfer.bi.BITransferFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * @author rterrell
 *
 */
public class BITransferAdminDaoTest extends CommonDaoTest {

    private BITransferFactory f;
    
    private TransferDao dao;
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new BITransferFactory();
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
    public void testFetchAllHeaderTranfers() {
	try {
	    Object list = this.dao.fetchTransfers();
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    
    @Test
    public void testFetchDetailTransfers() {
	try {
	    Object list = this.dao.fetchTransferItems(12100037);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    

}
