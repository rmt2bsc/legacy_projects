/**
 * 
 */
package com.xml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.security.pool.AppPropertyPool;
import com.api.xml.parsers.datasource.RMT2OrmDatasourceParser;
import com.api.xml.parsers.datasource.RMT2OrmDatasourceParserFactory;

/**
 * @author WinXP User
 *
 */
public class SAXParserTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	AppPropertyPool pool = AppPropertyPool.getInstance();
	System.setProperty("SAXDriver", "org.apache.xerces.parsers.SAXParser");
	System.setProperty("webapps_drive", "c:");
	System.setProperty("webapps_dir", "\\projects\\framework\\Data\\test");
//	System.setProperty("app_dir", "\\framework");
//	System.setProperty("datasource_dir", "\\Data\\test");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSax2Implementation() {
//	String xmlDoc = "C:\\projects\\framework\\src\\test\\data\\CreditorView.xml";
	String xmlDoc = "CreditorView.rmt2";

	RMT2OrmDatasourceParserFactory f = RMT2OrmDatasourceParserFactory.getNewInstance();
	RMT2OrmDatasourceParser parser = f.getSax2OrmDatasourceParser(xmlDoc);
	parser.parseDocument();

    }
}
