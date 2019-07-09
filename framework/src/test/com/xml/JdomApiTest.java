package com.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.w3c.dom.Document;

import com.util.RMT2XmlUtility;



/**
 * @author WinXP User
 *
 */
public class JdomApiTest {

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
    public void parseAndPrintPrettyDocument() {
	String strOutput = "";
	Document document;
	String fileName = "C:\\projects\\framework\\src\\test\\data\\sales_order.xml";
	try {
	    // The JAXP way of parsing
	    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	    DocumentBuilder parser = f.newDocumentBuilder();
	    document = parser.parse(fileName);

	    strOutput = RMT2XmlUtility.printDocumentWithJdom(document, true, false);
	    System.out.println(strOutput);
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
    
    
    @Test
    public void parseAndPrintCompactDocument() {
	String strOutput = "";
	Document document;
	String fileName = "C:\\projects\\framework\\src\\test\\data\\sales_order.xml";
	try {
	    // The JAXP way of parsing
	    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	    DocumentBuilder parser = f.newDocumentBuilder();
	    document = parser.parse(fileName);

	    strOutput = RMT2XmlUtility.printDocumentWithJdom(document, false, true);
	    System.out.println(strOutput);
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
