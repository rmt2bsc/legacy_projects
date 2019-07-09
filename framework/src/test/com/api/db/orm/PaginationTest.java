/**
 * 
 */
package com.api.db.orm;


import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;
import com.api.db.pagination.PageCalculator;
import com.api.db.pagination.PaginationApi;
import com.api.db.pagination.PaginationFactory;
import com.api.db.pagination.PaginationQueryResults;
import com.bean.Customers;
import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2File;

/**
 * @author appdev
 *
 */
public class PaginationTest {
    
    private DatabaseConnectionBean dbCon;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        RMT2File.loadSystemProperties("TestSystemParms");
        String configLoc = System.getProperty(HttpSystemPropertyConfig.PROPNAME_APPPARMS_LOCATION);
        this.dbCon = JdbcFactory.getConnection("jdbc:sybase:Tds:rmtdaldev04:2638?ServiceName=framework", configLoc);
        //      this.dbCon = JdbcFactory.getConnection("jdbc:sybase:Tds:DEN-LW117409:2638?ServiceName=framework");
        this.daoHelper = new RdbmsDaoQueryHelper(this.dbCon);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        this.dbCon.close();
        this.dbCon = null;
    }

    
    @Test
    public void testPaginationFetchApiEntryPoint() {
        Customers obj = new Customers();
        obj.addOrderBy(Customers.PROP_SURNAME, Customers.ORDERBY_ASCENDING);
        obj.addOrderBy(Customers.PROP_GIVENNAME, Customers.ORDERBY_ASCENDING);
        PaginationQueryResults results = null;
        
        PaginationApi pageApi = PaginationFactory.createDao(this.dbCon);
        try {
            results = (PaginationQueryResults) pageApi.retrieveList(obj, 2);
            Assert.assertNotNull(results);
            Assert.assertTrue(results.getPageNo() == 2);
            Assert.assertNotNull(results.getResults());
            Assert.assertTrue(((List) results.getResults()).size() > 0);
            Assert.assertTrue(results.getTotalRowCount() > 0);
            results.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testPaginationFetchDaoHelperEntryPoint() {
        Customers obj = new Customers();
        obj.addOrderBy(Customers.PROP_SURNAME, Customers.ORDERBY_ASCENDING);
        obj.addOrderBy(Customers.PROP_GIVENNAME, Customers.ORDERBY_ASCENDING);
        PaginationQueryResults results = null;
        try {
            results = (PaginationQueryResults) this.daoHelper.retrieveList(obj, 2);
            Assert.assertNotNull(results);
            Assert.assertTrue(results.getPageNo() == 2);
            Assert.assertNotNull(results.getResults());
            Assert.assertTrue(((List) results.getResults()).size() > 0);
            Assert.assertTrue(results.getTotalRowCount() > 0);
            results.toString();
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPageCalculator() {
        double result = 0;
        boolean boolResult = false;
        PageCalculator calc = new PageCalculator(100, 10);

        result = calc.calcTotalPages();
        Assert.assertEquals(10, result);

        boolResult = calc.isFirstPage(1);
        Assert.assertTrue(boolResult);
        boolResult = calc.isFirstPage(5);
        Assert.assertFalse(boolResult);

        boolResult = calc.isLastPage(4);
        Assert.assertFalse(boolResult);
        boolResult = calc.isLastPage(10);
        Assert.assertTrue(boolResult);

        result = calc.getNextPage(10);
        Assert.assertEquals(PageCalculator.EOF, result);
        result = calc.getNextPage(5);
        Assert.assertEquals(6, result);
        result = calc.getNextPage(9);
        Assert.assertEquals(10, result);

        result = calc.getPrevPage(1);
        Assert.assertEquals(PageCalculator.BOF, result);
        result = calc.getPrevPage(10);
        Assert.assertEquals(9, result);
        result = calc.getPrevPage(4);
        Assert.assertEquals(3, result);

        // Try page size that is not evenly divisible
        calc = new PageCalculator(100, 14);
        result = calc.calcTotalPages();
        Assert.assertEquals(8, result);

        calc = new PageCalculator(100, 13);
        result = calc.calcTotalPages();
        Assert.assertEquals(8, result);

        boolResult = calc.isFirstPage(1);
        Assert.assertTrue(boolResult);
        boolResult = calc.isFirstPage(5);
        Assert.assertFalse(boolResult);

        boolResult = calc.isLastPage(4);
        Assert.assertFalse(boolResult);
        boolResult = calc.isLastPage(8);
        Assert.assertTrue(boolResult);
        boolResult = calc.isLastPage(18);
        Assert.assertFalse(boolResult);

        result = calc.getNextPage(8);
        Assert.assertEquals(PageCalculator.EOF, result);
        result = calc.getNextPage(9);
        Assert.assertEquals(PageCalculator.EOF, result);
        result = calc.getNextPage(5);
        Assert.assertEquals(6, result);
        result = calc.getNextPage(7);
        Assert.assertEquals(8, result);

        result = calc.getPrevPage(1);
        Assert.assertEquals(PageCalculator.BOF, result);
        result = calc.getPrevPage(8);
        Assert.assertEquals(7, result);
        result = calc.getPrevPage(13);
        Assert.assertEquals(12, result);
    }
}
