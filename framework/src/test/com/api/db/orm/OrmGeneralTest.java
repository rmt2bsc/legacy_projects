package com.api.db.orm;

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


import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;

import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.pagination.PageCalculator;
import com.api.db.pagination.PaginationQueryResults;

import com.bean.Customers;
import com.bean.Products;

import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2Base64Decoder;
import com.util.RMT2Base64Encoder;
import com.util.RMT2File;

/**
 * @author rterrell
 *
 */
public class OrmGeneralTest {

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
	//	this.dbCon = JdbcFactory.getConnection("jdbc:sybase:Tds:DEN-LW117409:2638?ServiceName=framework");
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
    public void testImagePersistence() {
	Connection con = this.dbCon.getDbConn();
	try {
	    Statement stmt = con.createStatement();    
	    ResultSet rs = stmt.executeQuery("select * from products where id = 700");
	    if (rs.next()) {
		String name = rs.getString("name");
		String descr = rs.getString("description");
		String size = rs.getString("size");
		String color = rs.getString("color");
		int qty = rs.getInt("Quantity");
		double price = rs.getDouble("unitprice");
		
		// Try to persist photo String data as .jpg to disk.
		byte photo[] = rs.getBytes("photo");  
		System.out.println("Length of bytes Before Encoding: " + photo.length);
		
		String encoded = RMT2Base64Encoder.encode(photo);
		System.out.println("Encoded value: " + encoded);
		System.out.println("Encoded Length: " + encoded.length());
		
		byte photo2[] = RMT2Base64Decoder.decodeToBytes(encoded);
		System.out.println("Length of bytes After Decoding: " + photo2.length);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(photo2);
		RMT2File.outputFile(bais, "c:/tmp/test_db_jpg2.jpg");
		
		// Build XML
		StringBuffer xml = new StringBuffer();
		xml.append("<Product>");
		xml.append("<Name>");
		xml.append(name);
		xml.append("</Name>");
		xml.append("<Description>");
		xml.append(descr);
		xml.append("</Description>");
		xml.append("<Size>");
		xml.append(size);
		xml.append("</Size>");
		xml.append("<Color>");
		xml.append(color);
		xml.append("</Color>");
		xml.append("<Qty>");
		xml.append(qty);
		xml.append("</Qty>");
		xml.append("<Price>");
		xml.append(price);
		xml.append("</Price>");
		xml.append("<Photo>");
		xml.append(encoded);
		xml.append("</Photo>");
		xml.append("</Product>");
		
		String xmlStr = xml.toString();
		System.out.println("Size of xml: " + xmlStr.length());
		System.out.println(xmlStr);
		RMT2File.outputFile(xmlStr, "c:/tmp/xml_photo.xml");
	    }
	    rs.close();
	    stmt.close();
	    rs = null;
	    stmt = null;
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}
    }
    
    @Test
    public void testFetch() {
	Customers obj = new Customers();
	obj.addCriteria(Customers.PROP_STATE, "Tx");
	List results = null;
	try {
	    results = this.daoHelper.retrieveList(obj);
	    Assert.assertNotNull(results);
	    Assert.assertTrue(results.size() > 0);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void testLongBinaryFetch() {
	Products obj = new Products();
	obj.addCriteria(Products.PROP_ID, 300);
	Products results = null;
	try {
	    results = (Products) this.daoHelper.retrieveObject(obj);
	    InputStream is = results.getPhoto();
	    byte buffer[] = RMT2File.getStreamByteData(is);
	    File file = new File("C:\\tmp\\test.jpg");
	    try {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(buffer);    
		fos.close();
	    }
	    catch (FileNotFoundException e) {
		return;
	    }
	    catch (IOException e) {
		e.printStackTrace();
		return;
	    }
	    Assert.assertNotNull(results);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }

}