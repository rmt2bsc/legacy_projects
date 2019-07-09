package com.codes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;
import com.api.codes.GeneralCodeException;
import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;

import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.pagination.PageCalculator;
import com.api.db.pagination.PaginationQueryResults;
import com.api.messaging.ResourceFactory;

import com.bean.Customers;
import com.bean.Products;
import com.bean.VwCodes;
import com.bean.VwCommonContact;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2Base64Decoder;
import com.util.RMT2Base64Encoder;
import com.util.RMT2File;
import com.xml.schema.bindings.CommonContactType;
import com.xml.schema.bindings.LookupCodeType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RSCodeLookup;
import com.xml.schema.bindings.RSCommonContactSearch;

/**
 * @author rterrell
 *
 */
public class CodeFetchTest {

    private DatabaseConnectionBean dbCon;

    private RdbmsDaoQueryHelper daoHelper;
    
    private ObjectFactory f;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.dbCon = JdbcFactory.getConnection("jdbc:sybase:Tds:rmtdaldev04:2638?ServiceName=contacts");
	this.daoHelper = new RdbmsDaoQueryHelper(this.dbCon);
	this.f = new ObjectFactory();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.dbCon.close();
	this.f = null;
    }

    
    @Test
    public void testMultiLookupCodes() {
        CodesApi api = CodesFactory.createCodesApi(this.dbCon);
        int grp[] = {7, 5, 26};
        try {
            List <VwCodes> oLst = api.findLookupData(grp);
            List <LookupCodeType> nLst = JaxbContactsFactory.getLookupCodeTypeInstance(oLst); 
            RSCodeLookup ws = f.createRSCodeLookup();
            ws.getGroup().addAll(nLst);
            String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
            System.out.println(msg);
        }
        catch (GeneralCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}   
