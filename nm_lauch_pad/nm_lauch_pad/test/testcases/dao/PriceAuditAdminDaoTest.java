package testcases.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import modules.model.PriceAuditSkuItem;
import modules.priceaudit.PriceAuditDao;
import modules.priceaudit.PriceAuditFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.db.DatabaseException;


/**
 * @author rterrell
 *
 */
public class PriceAuditAdminDaoTest extends CommonDaoTest {

    private PriceAuditFactory f;
    
    private PriceAuditDao dao;
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new PriceAuditFactory();
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
    public void testFetchAllActivePriceAudits() {
	try {
	    Object list = this.dao.fetchAllActive();
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    
    private int createDeleteData() {
	Connection con = this.dao.getInternalConnection();
	Statement stmt = null;
	try {
	    stmt = con.createStatement();
	    int rows = 0;
	    rows = stmt.executeUpdate("insert into item_audit " +
	    		"(dept, " +
	    		"class, " +
	    		"vendor, " +
	    		"style, " +
	    		"color, " +
	    		"size, " +
	    		"old_rtl, " +
	    		"new_rtl, " +
	    		"mrkd_qty, " +
	    		"mark_date, " +
	    		"uid, " +
	    		"reason_cd, " +
	    		"status, " +
	    		"status_date, " +
	    		"sku_overflow, " +
	    		"sku) " +
	    	 "values " +
	    	 "(20, " +
	    	 "8, " +
	    	 "3333, " +
	    	 "4444, " +
	    	 "50, " +
	    	 "120, " +
	    	 "123.99, " +
	    	 "300.99, " +
	    	 "12, " +
	    	 "'2012-04-12', " +
	    	 "111111, " +
	    	 "12, " +
	    	 "'A', " +
	    	 "'2012-04-12', " +
	    	 "0, " +
	    	 "77777)");
	    con.commit();
	    return rows;
	}
	catch (SQLException e) {
	    try {
		con.rollback();
		return -1;
	    }
	    catch (SQLException e1) {
		throw new RuntimeException(e1);
	    }
	}
	finally {
	    try {
		if (stmt != null) {
		    stmt.close();
		}
		if (con != null) {
		    con.close();
		}
		stmt = null;
		con = null;
	    }
	    catch (SQLException e) {
		throw new RuntimeException(e);
	    }
	}

    }
    
    
    @Test
    public void testDeleteItem() {
	this.createDeleteData();
	PriceAuditSkuItem item = new PriceAuditSkuItem();
	item.setOverFlowSku(0L);
	item.setSku(77777L);
	item.setDept(20);
	item.setClazz(8);
	item.setVendor(3333);
	item.setStyle(4444);
	item.setColor(50);
	item.setSize(120);
	item.setOldRetail(123.99);
	item.setNewRetail(300.99);
	try {
	    int rc = this.dao.deleteItem(item);
	    Assert.assertEquals(1, rc);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }
}
