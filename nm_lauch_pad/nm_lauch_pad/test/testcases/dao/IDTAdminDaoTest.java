package testcases.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import modules.idt.IDTFactory;
import modules.idt.IdtDao;
import modules.model.SkuItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.util.GeneralUtil;

/**
 * @author rterrell
 *
 */
public class IDTAdminDaoTest extends CommonDaoTest {

    private IDTFactory f;

    private IdtDao dao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new IDTFactory();
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
    public void testFetchAllReticketingITems() {
	try {
	    SkuItem item = new SkuItem();
	    Object list = this.dao.fetchReticketHeader(item);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void testFetchSingleSku() {
	try {
	    SkuItem item = this.dao.fetchSku(14877);
	    Assert.assertNotNull(item);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void testFetchReticketingITemsBySku() {
	try {
	    SkuItem item = this.dao.fetchSku(14877);
	    Assert.assertNotNull(item);
	    Object list = this.dao.fetchReticketHeader(item);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void testFetchFilteredReticketingIiems() {
	try {
	    SkuItem item = new SkuItem();
	    item.setStyle(1960);
	    item.setColor(10);
	    item.setSize(260);
	    Object list = this.dao.fetchReticketHeader(item);
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    @Test
    public void testUpdateQuantity() {
	try {
	    SkuItem item = new SkuItem();
	    item.setDept(10);
	    item.setClazz(7);
	    item.setVendor(2521);
	    item.setStyle(1960);
	    item.setColor(10);
	    item.setSize(260);
	    Date dt = GeneralUtil.stringToDate("2012-03-23");
	    item.setStatusDate(dt);
	    int rows = this.dao.updateSkuQty(item, 100);
	    Assert.assertEquals(1, rows);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    @Test
    public void testDeleteSku() {
	try {
	    this.createDeleteData();
	    
	    SkuItem item = new SkuItem();
	    item.setDept(20);
	    item.setClazz(8);
	    item.setVendor(3333);
	    item.setStyle(4444);
	    item.setColor(50);
	    item.setSize(120);	    
	    Date dt = GeneralUtil.stringToDate("2012-04-12");
	    item.setStatusDate(dt);
	    int rows = this.dao.deleteSku(item);
	    Assert.assertEquals(3, rows);
	    
	    item = new SkuItem();
	    item.setDept(20);
	    item.setClazz(8);
	    item.setVendor(4344);
	    item.setStyle(5555);
	    item.setColor(51);
	    item.setSize(122);
	    dt = GeneralUtil.stringToDate("2012-04-13");
	    item.setStatusDate(dt);
	    rows = this.dao.deleteSku(item);
	    Assert.assertEquals(4, rows);
	    
	    
	}
	catch (Exception e) {
	    this.removeDeleteData();
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }


    private int createDeleteData() {
	Connection con = this.dao.getInternalConnection();
	Statement stmt = null;
	try {
	    stmt = con.createStatement();
	    int rows = 0;
	    rows = stmt.executeUpdate("insert into idt_retick (dept, class, vendor, style, color, size, new_dept, new_class, price, qty, status, status_date) values (20, 8, 3333, 4444, 50, 120, 731, 22, 123.99, 30, 'A', '2012-04-12')");
	    rows += stmt.executeUpdate("insert into informix.idt_retick (dept, class, vendor, style, color, size, new_dept, new_class, price, qty, status, status_date) values (20, 8, 4344, 5555, 51, 122, 731, 22, 44.99, 67, 'A', '2012-04-13')");
	    rows += stmt.executeUpdate("insert into informix.idt_sku (sku, dept, class, vendor, style, color, size, status, status_date) values (15000, 20, 8, 3333, 4444, 50, 120, 'A', '2012-04-12')");
	    rows += stmt.executeUpdate("insert into informix.idt_sku (sku, dept, class, vendor, style, color, size, status, status_date) values (15001, 20, 8, 3333, 4444, 50, 120, 'A', '2012-04-12')");
	    rows += stmt.executeUpdate("insert into informix.idt_sku (sku, dept, class, vendor, style, color, size, status, status_date) values (15002, 20, 8, 4344, 5555, 51, 122, 'A', '2012-04-13')");
	    rows += stmt.executeUpdate("insert into informix.idt_sku (sku, dept, class, vendor, style, color, size, status, status_date) values (15003, 20, 8, 4344, 5555, 51, 122, 'A', '2012-04-13')");
	    rows += stmt.executeUpdate("insert into informix.idt_sku (sku, dept, class, vendor, style, color, size, status, status_date) values (15004, 20, 8, 4344, 5555, 51, 122, 'A', '2012-04-13')");
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
    
    
    private int removeDeleteData() {
	Connection con = this.dao.getInternalConnection();
	Statement stmt = null;
	try {
	    stmt = con.createStatement();
	    int rows = 0;
	    rows = stmt.executeUpdate("delete from idt_retick where dept = 20");
	    rows += stmt.executeUpdate("delete from idt_sku where dept = 20");
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
}
