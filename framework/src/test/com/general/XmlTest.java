package com.general;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;

import com.api.DaoApi;
import com.api.db.DatabaseException;
import com.api.xml.XmlApiFactory;
import com.api.xml.adapters.XmlAdapterFactory;
import com.api.xml.adapters.XmlBeanAdapter;

import com.bean.SalesOrderExtTest;

import com.util.RMT2XmlUtility;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * @author appdev
 *
 */
public class XmlTest {
    private String saleOrderWithItemsXml;

    private String saleOrderWithOutItemsXml;

    private String personXml;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.saleOrderWithItemsXml = "<SalesOrderExtTest><beanClassName>com.bean.SalesOrderExtTest</beanClassName><className /><customerId>1010</customerId><dateCreated>3-12-2008</dateCreated><dateUpdated>4-1-2008</dateUpdated><salesOrderId>555</salesOrderId><invoiced>0</invoiced><items><SalesOrderItems><beanClassName>com.bean.SalesOrderItems</beanClassName><backOrderQty>0.0</backOrderQty><className /><criteriaAvailable>false</criteriaAvailable><customCriteriaAvailable>false</customCriteriaAvailable><dataSourceClassName>com.bean.SalesOrderItems</dataSourceClassName><dataSourceName>SalesOrderItemsView</dataSourceName><dataSourceRoot>SalesOrderItems</dataSourceRoot><fileName /><id>0</id><inClauseAvailable>false</inClauseAvailable><initMarkup>1.0</initMarkup><initUnitCost>1080.0</initUnitCost><itemMasterId>157</itemMasterId><itemNameOverride>Terrell, Roy for 47.0 hours</itemNameOverride><jspOrigin /><methodName /><orderByAvailable>false</orderByAvailable><orderQty>1.0</orderQty><resultsetType>0</resultsetType><salesOrderId>555</salesOrderId><serialPath /><serializeXml>false</serializeXml></SalesOrderItems><SalesOrderItems><beanClassName>com.bean.SalesOrderItems</beanClassName><backOrderQty>0.0</backOrderQty><className /><criteriaAvailable>false</criteriaAvailable><customCriteriaAvailable>false</customCriteriaAvailable><dataSourceClassName>com.bean.SalesOrderItems</dataSourceClassName><dataSourceName>SalesOrderItemsView</dataSourceName><dataSourceRoot>SalesOrderItems</dataSourceRoot><fileName /><id>0</id><inClauseAvailable>false</inClauseAvailable><initMarkup>1.0</initMarkup><initUnitCost>1200.0</initUnitCost><itemMasterId>157</itemMasterId><itemNameOverride>Terrell, Roy for 80.0 hours</itemNameOverride><jspOrigin /><methodName /><orderByAvailable>false</orderByAvailable><orderQty>15.0</orderQty><resultsetType>0</resultsetType><salesOrderId>555</salesOrderId><serialPath /><serializeXml>false</serializeXml></SalesOrderItems></items><methodName /><orderTotal>4440.0</orderTotal><userId /></SalesOrderExtTest>";
	this.saleOrderWithOutItemsXml = "<SalesOrderExtTest><beanClassName>com.bean.SalesOrderExtTest</beanClassName><className /><customerId>1010</customerId><dateCreated>3-12-2008</dateCreated><dateUpdated>4-1-2008</dateUpdated><id>444</id><invoiced>0</invoiced><methodName /><orderTotal>3240.0</orderTotal><userId /></SalesOrderExtTest>";
	this.personXml = "<profiles><person><firstname>Roy</firstname><lastname>Terrell</lastname><data><partno>94329</partno><description>Intel Dual Core Extreme 9000</description><price>432.33</price></data></person><person><firstname>Billy</firstname><lastname>Cobham</lastname><data><partno>43223</partno><description>Best Fusion Drummer</description><price>32.34</price></data></person><person><firstname>Dennis</firstname><lastname>Chambers</lastname><data><partno>83838</partno><description>Most in-demand drummer</description><price>843.48</price></data></person></profiles>";
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.saleOrderWithItemsXml = null;
	this.saleOrderWithOutItemsXml = null;
    }

    @Test
    public void testXmlBeanAdapterWithItems() {
	XmlBeanAdapter adpt = XmlAdapterFactory.createNativeAdapter();
	Object bean = adpt.xmlToBean(this.saleOrderWithItemsXml);
	String xml2 = adpt.beanToXml(bean);
	System.out.println(xml2);
	System.out.println("String Length: " + xml2.length());
    }

    @Test
    public void testXmlBeanAdapterWithoutItems() {
	XmlBeanAdapter adpt = XmlAdapterFactory.createNativeAdapter();
	Object bean = adpt.xmlToBean(this.saleOrderWithOutItemsXml);
	String xml2 = adpt.beanToXml(bean);
	System.out.println(xml2);
	System.out.println("String Length: " + xml2.length());
    }

    @Test
    public void testDaoApiNavigation() {
	Object results[];
	String fn;
	String ln;
	String partNo;
	String descr;

	DaoApi api = XmlApiFactory.createXmlDao(this.personXml);
	try {
//	    results = api.retrieve("//profiles/person");
	    results = api.retrieve("person");
	    // Navigate forward
	    while (api.nextRow()) {
		fn = api.getColumnValue("firstname");
		ln = api.getColumnValue("lastname");
		partNo = api.getColumnValue("partno");
		descr = api.getColumnValue("description");
		System.out.println(fn + " - " + ln + " - " + partNo + " - " + descr);
	    }
	    // Navigate backwards
	    while (api.previousRow()) {
		fn = api.getColumnValue("firstname");
		ln = api.getColumnValue("lastname");
		partNo = api.getColumnValue("partno");
		descr = api.getColumnValue("description");
		System.out.println(fn + " - " + ln + " - " + partNo + " - " + descr);
	    }

	    // Navigate forward again
	    while (api.nextRow()) {
		fn = api.getColumnValue("firstname");
		ln = api.getColumnValue("lastname");
		partNo = api.getColumnValue("partno");
		descr = api.getColumnValue("description");
		System.out.println(fn + " - " + ln + " - " + partNo + " - " + descr);
	    }

	    if (api.firstRow()) {
		fn = api.getColumnValue("firstname");
		ln = api.getColumnValue("lastname");
		partNo = api.getColumnValue("partno");
		descr = api.getColumnValue("description");
		System.out.println(fn + " - " + ln + " - " + partNo + " - " + descr);
	    }

	    if (api.lastRow()) {
		fn = api.getColumnValue("firstname");
		ln = api.getColumnValue("lastname");
		partNo = api.getColumnValue("partno");
		descr = api.getColumnValue("description");
		System.out.println(fn + " - " + ln + " - " + partNo + " - " + descr);
	    }

	    api.close();
	    api = null;
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }

    @Test
    public void testSingleXmlElementAccess() {
	DaoApi api = XmlApiFactory.createXmlDao(this.personXml);
	Object results[];
	String fn;
	try {
	    results = api.retrieve("//profiles/person");
	    while (api.nextRow()) {
		fn = api.getColumnValue("firstname");
		System.out.println(fn);
	    }
	    api.close();
	    api = null;
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }

    @Test
    public void testSubDocumentXmlResult() {
	DaoApi api = XmlApiFactory.createXmlDao(this.personXml);
	Object results[];
	String subdoc;
	try {
	    results = api.retrieve("data");
	    while (api.nextRow()) {
		subdoc = api.getColumnValue("data");
		System.out.println(subdoc);
	    }
	    api.close();
	    api = null;
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
    
    @Test
    public void testXslTransformToMemory() {
	String xml = "<RSPayload><appRoot>/accounting</appRoot>	<pageTitle>Customer Payment Confirmation</pageTitle>	<CustomerExt>		<beanClassName>com.gl.customer.CustomerExt</beanClassName>		<accountNo>U1440-20090531</accountNo>		<active>0</active>		<balance>1970.0</balance>		<busType>26</busType>		<businessId>1440</businessId>		<className></className>		<contactEmail>rmt2bsc2@verizon.net</contactEmail>		<contactExt></contactExt>		<contactFirstname></contactFirstname>		<contactLastname></contactLastname>		<contactPhone></contactPhone>		<creditLimit>0.0</creditLimit>		<customerId>11</customerId>		<dateCreated>Thu Aug 27 16:43:51 CDT 2009</dateCreated>		<dateUpdated>Thu Aug 27 16:43:51 CDT 2009</dateUpdated>		<description></description>		<fileName></fileName>		<glAccountId>10</glAccountId>		<jspOrigin></jspOrigin>		<methodName></methodName>		<name>The Insource Group</name>		<serialPath></serialPath>		<servType>61</servType>		<shortname></shortname>		<taxId>85438484</taxId>		<userId>admin</userId>		<website>www.insourcegroup.com</website>	</CustomerExt>	<SalesOrder>		<beanClassName>com.bean.SalesOrder</beanClassName>		<className></className>		<criteriaAvailable>false</criteriaAvailable>		<customCriteriaAvailable>false</customCriteriaAvailable>		<customerId>0</customerId>		<dataSourceClassName>com.bean.SalesOrder</dataSourceClassName>		<dataSourceName>SalesOrderView</dataSourceName>		<dataSourceRoot>SalesOrder</dataSourceRoot>		<dateCreated></dateCreated>		<dateUpdated></dateUpdated>		<fileName></fileName>		<inClauseAvailable>false</inClauseAvailable>		<invoiced>0</invoiced>		<jspOrigin></jspOrigin>		<methodName></methodName>		<orderByAvailable>false</orderByAvailable>		<orderTotal>1970.0</orderTotal>		<resultsetType>0</resultsetType>		<serialPath></serialPath>		<serializeXml>false</serializeXml>		<soId>0</soId>		<userId></userId>	</SalesOrder>	<Xact>		<beanClassName>com.bean.Xact</beanClassName>		<bankTransInd></bankTransInd>		<className></className>		<confirmNo>1251409475197</confirmNo>		<criteriaAvailable>false</criteriaAvailable>		<customCriteriaAvailable>false</customCriteriaAvailable>		<dataSourceClassName>com.bean.Xact</dataSourceClassName>		<dataSourceName>XactView</dataSourceName>		<dataSourceRoot>Xact</dataSourceRoot>		<dateCreated>Thu Aug 27 16:44:35 CDT 2009</dateCreated>		<dateUpdated>Thu Aug 27 16:44:35 CDT 2009</dateUpdated>		<entityRefNo></entityRefNo>		<fileName></fileName>		<inClauseAvailable>false</inClauseAvailable>		<jspOrigin></jspOrigin>		<methodName></methodName>		<negInstrNo></negInstrNo>		<orderByAvailable>false</orderByAvailable>		<postedDate></postedDate>		<reason>Cash Receipt: test availibility of email</reason>		<resultsetType>0</resultsetType>		<serialPath></serialPath>		<serializeXml>false</serializeXml>		<tenderId>0</tenderId>		<userId>admin</userId>		<xactAmount>20.0</xactAmount>		<xactDate>Thu Aug 27 00:00:00 CDT 2009</xactDate>		<xactId>10672</xactId>		<xactSubtypeId>0</xactSubtypeId>		<xactTypeId>2</xactTypeId>	</Xact></RSPayload>	";
	String xslFile = "C:/tmp/CustomerPaymentConfirmation.xsl";
	RMT2XmlUtility xsl = RMT2XmlUtility.getInstance();
	ByteArrayOutputStream baos = null;
	try {
	    baos = new ByteArrayOutputStream();
	    xsl.transform(xslFile, xml, baos);
	    String results = baos.toString();
	    System.out.println(results);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	finally {
	    xsl = null;
	}
    }
    
    @Test
    public void testXmlToDom() {
	String xml = "<RSPayload><appRoot>/accounting</appRoot>	<pageTitle>Customer Payment Confirmation</pageTitle>	<CustomerExt>		<beanClassName>com.gl.customer.CustomerExt</beanClassName>		<accountNo>U1440-20090531</accountNo>		<active>0</active>		<balance>1970.0</balance>		<busType>26</busType>		<businessId>1440</businessId>		<className></className>		<contactEmail>rmt2bsc2@verizon.net</contactEmail>		<contactExt></contactExt>		<contactFirstname></contactFirstname>		<contactLastname></contactLastname>		<contactPhone></contactPhone>		<creditLimit>0.0</creditLimit>		<customerId>11</customerId>		<dateCreated>Thu Aug 27 16:43:51 CDT 2009</dateCreated>		<dateUpdated>Thu Aug 27 16:43:51 CDT 2009</dateUpdated>		<description></description>		<fileName></fileName>		<glAccountId>10</glAccountId>		<jspOrigin></jspOrigin>		<methodName></methodName>		<name>The Insource Group</name>		<serialPath></serialPath>		<servType>61</servType>		<shortname></shortname>		<taxId>85438484</taxId>		<userId>admin</userId>		<website>www.insourcegroup.com</website>	</CustomerExt>	<SalesOrder>		<beanClassName>com.bean.SalesOrder</beanClassName>		<className></className>		<criteriaAvailable>false</criteriaAvailable>		<customCriteriaAvailable>false</customCriteriaAvailable>		<customerId>0</customerId>		<dataSourceClassName>com.bean.SalesOrder</dataSourceClassName>		<dataSourceName>SalesOrderView</dataSourceName>		<dataSourceRoot>SalesOrder</dataSourceRoot>		<dateCreated></dateCreated>		<dateUpdated></dateUpdated>		<fileName></fileName>		<inClauseAvailable>false</inClauseAvailable>		<invoiced>0</invoiced>		<jspOrigin></jspOrigin>		<methodName></methodName>		<orderByAvailable>false</orderByAvailable>		<orderTotal>1970.0</orderTotal>		<resultsetType>0</resultsetType>		<serialPath></serialPath>		<serializeXml>false</serializeXml>		<soId>0</soId>		<userId></userId>	</SalesOrder>	<Xact>		<beanClassName>com.bean.Xact</beanClassName>		<bankTransInd></bankTransInd>		<className></className>		<confirmNo>1251409475197</confirmNo>		<criteriaAvailable>false</criteriaAvailable>		<customCriteriaAvailable>false</customCriteriaAvailable>		<dataSourceClassName>com.bean.Xact</dataSourceClassName>		<dataSourceName>XactView</dataSourceName>		<dataSourceRoot>Xact</dataSourceRoot>		<dateCreated>Thu Aug 27 16:44:35 CDT 2009</dateCreated>		<dateUpdated>Thu Aug 27 16:44:35 CDT 2009</dateUpdated>		<entityRefNo></entityRefNo>		<fileName></fileName>		<inClauseAvailable>false</inClauseAvailable>		<jspOrigin></jspOrigin>		<methodName></methodName>		<negInstrNo></negInstrNo>		<orderByAvailable>false</orderByAvailable>		<postedDate></postedDate>		<reason>Cash Receipt: test availibility of email</reason>		<resultsetType>0</resultsetType>		<serialPath></serialPath>		<serializeXml>false</serializeXml>		<tenderId>0</tenderId>		<userId>admin</userId>		<xactAmount>20.0</xactAmount>		<xactDate>Thu Aug 27 00:00:00 CDT 2009</xactDate>		<xactId>10672</xactId>		<xactSubtypeId>0</xactSubtypeId>		<xactTypeId>2</xactTypeId>	</Xact></RSPayload>	";
	Document doc = RMT2XmlUtility.stringToDocument(xml);
	NodeList obj = doc.getElementsByTagName("appRoot");
	return;
    }
}
