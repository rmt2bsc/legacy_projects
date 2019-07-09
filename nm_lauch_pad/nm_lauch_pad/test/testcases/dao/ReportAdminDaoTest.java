package testcases.dao;

import java.util.List;

import junit.framework.Assert;

import modules.report.ReportDao;
import modules.report.ReportFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author rterrell
 *
 */
public class ReportAdminDaoTest extends CommonDaoTest {

    private ReportFactory f;

    private ReportDao dao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new ReportFactory();
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
    public void getAllHeader() {
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
    public void getReportDetails() {
	try {
	    Object list = this.dao.fetchDetails("idt");
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void getReportParameters() {
	try {
	    Object list = this.dao.fetchParams("idt-extract");
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
}
