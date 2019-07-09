package com.api;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.api.db.JdbcFactory;

import com.api.ip.IpAddressFactory;
import com.api.ip.IpApi;
import com.api.ip.IpException;

import com.bean.IpLocation;

import com.bean.db.DatabaseConnectionBean;


/**
 * @author rterrell
 *
 */
public class IpAddressTest {

    private DatabaseConnectionBean dbCon;


    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.dbCon = JdbcFactory.getConnection("jdbc:sybase:Tds:rmtdaldev04:2638?ServiceName=contacts");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.dbCon.close();
    }

    
    @Test
    public void testIpAddressFetch() {
        IpApi api = IpAddressFactory.createApi(dbCon);
        IpLocation loc = null;
        try {
            loc = api.getIpDetails("71.252.210.138");
            Assert.assertNotNull(loc.getCity());
            loc = api.getIpDetails("206.53.147.230");
            Assert.assertNotNull(loc.getCity());
            loc = api.getIpDetails("204.0.69.201");
            Assert.assertNotNull(loc.getCity());
        }
        catch (IpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}   
