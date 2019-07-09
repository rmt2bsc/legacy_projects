package testcases.dao;


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import modules.model.PriceChange;
import modules.model.PriceChangeSkuItem;

import modules.pricechange.PriceChangeDao;
import modules.pricechange.PriceChangeFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.db.DatabaseException;

/**
 * @author rterrell
 *
 */
public class PriceChangeAdminDaoTest extends CommonDaoTest {

    private PriceChangeFactory f;
    
    private PriceChangeDao dao;
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new PriceChangeFactory();
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
    public void testFetchAllActivePriceChanges() {
	try {
	    Object list = this.dao.fetchAllActive();
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    
    @Test
    public void testFetchActivePriceChangesByShortSku() {
	try {
	    Object list = this.dao.fetchActiveBySku("27886566");
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    @Test
    public void testFetchActivePriceChangesByLongSku() {
	try {
	    Object list = this.dao.fetchActiveBySku("711658547600");
	    Assert.assertNotNull(list);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    
    @Test
    public void testFetchPriceChangeDetails() {
	try {
	    List<PriceChange> list1 = this.dao.fetchActiveBySku("711658547600");
	    Assert.assertNotNull(list1);
	    Assert.assertTrue(list1.size() > 0);
	    PriceChange item = list1.get(0);
	    
	    Object list2 = this.dao.fetchItemDetails(item);
	    Assert.assertNotNull(list2);
	    Assert.assertTrue(((List) list2).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    
    @Test
    public void testPriceChangeItemStatusUpdate() {
	List<PriceChangeSkuItem> items = new ArrayList<PriceChangeSkuItem>();
	PriceChangeSkuItem b1 = new PriceChangeSkuItem();
	b1.setSku(11111111L);
	b1.setOverFlowSku(1111L);
	items.add(b1);
	
	PriceChangeSkuItem b2 = new PriceChangeSkuItem();
	b2.setSku(22222222L);
	b2.setOverFlowSku(2222L);
	items.add(b2);
	
	PriceChangeSkuItem b3 = new PriceChangeSkuItem();
	b3.setSku(33333333L);
	b3.setOverFlowSku(2222L);
	items.add(b3);
	
	try {
	    this.dao.updateItemDetailsBySku(777777, "O", items);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }
}
