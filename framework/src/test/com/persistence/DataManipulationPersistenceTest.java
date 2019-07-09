package com.persistence;


import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.persistence.CannotCreateManagerException;
import com.api.persistence.CannotPersistException;
import com.api.persistence.CannotRemoveException;
import com.api.persistence.CannotRetrieveException;
import com.api.persistence.PersistenceFactory;
import com.api.persistence.PersistenceManager;

/**
 * @author Roy Terrell
 *
 */
public class DataManipulationPersistenceTest {
    

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
    public void setupUsingDefaultConfig() {
	PersistenceFactory f = PersistenceFactory.getInstance();
	PersistenceManager api = null;
	try {
	    api = f.createManager();
	    api.clear();
	    api.save("ProductId", "prod560042");
	    api.save("DisplayName", "OLV ML CT");
	    api.save("SourceCategory", "catEbayDelisted");
	    api.save("DestinationCategory", "catEbayDelistQ");
	    api.save("CatalogCode", "FDF11");
	    api.save("ItemCode", "N161K");
	    
	    Map initState = api.retrieveAll();
	    int initCount = initState.size();
	    
	    api.remove("DisplayName");
	    Map postDeleteState = api.retrieveAll();
	    int postCount = postDeleteState.size();
	    
	    Assert.assertEquals(6, initCount);
	    Assert.assertEquals(5, postCount);
	}
	catch (CannotCreateManagerException e) {
	    e.printStackTrace();
	}
	catch (CannotPersistException e) {
	    e.printStackTrace();
	}
	catch (CannotRemoveException e) {
	    e.printStackTrace();
	}
	catch (CannotRetrieveException e) {
	    e.printStackTrace();
	}
	finally {
	    api.shutDown();
	}
    }
    
    
   
}
