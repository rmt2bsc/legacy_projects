package testcases.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import modules.counts.CountsDao;
import modules.counts.CountsFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.util.GeneralUtil;

/**
 * @author rterrell
 *
 */
public class CountsAdminDaoTest extends CommonDaoTest {

    private CountsFactory f;

    private CountsDao dao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new CountsFactory();
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
    public void fetchAllHeader() {
	try {
	    Object list = this.dao.fetchAllHeaders();
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void fetchDetail() {
	try {
	    Object list = this.dao.fetchHeaderDetails(568);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void fetchTransmittedHeaders() {
	try {
	    Object list = this.dao.fetchTransmittedHeaders(20);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void updateCount() {
	Integer hdrId = null;
	try {
//	    this.dao.updateCount(578, 66, new Date(), 777777, 111111);
	    Date dt = GeneralUtil.stringToDate("2000-3-17");    
	    hdrId = this.dao.update(578, 66, dt, 777777, 111111);
	    Assert.assertNotNull(hdrId);
	    Assert.assertTrue(hdrId > 0);
	    System.out.println("Header Id update: " + hdrId);
	}
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void deleteCount() {
	Integer hdrId = null;
	try {
	    Date dt = GeneralUtil.stringToDate("2000-5-17");    
	    hdrId = this.dao.update(578, 66, dt, 777777, 111111);
	    int rows = this.dao.delete(hdrId, 777777);
	    Assert.assertEquals(1, rows);
	}
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
