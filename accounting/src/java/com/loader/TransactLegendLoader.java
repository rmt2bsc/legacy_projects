package com.loader;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;

import com.loader.transactions.LoaderTransType;

import com.util.SystemException;
import com.util.RMT2SaxDocBase;

/**
 * This class builds a Map of {@link com.loader.LoaderTransType LoaderTransType} objects, 
 * which is keyed by LoaderTransType.name, by way of parsing an XML document using SAX 
 * technologies.
 * 
 * @author roy.terrell
 *
 */
public class TransactLegendLoader extends RMT2SaxDocBase {
    private Logger logger;

    private Map legend;

    private String code;

    private String name;

    private int colCount;

    /**
     * Creates a TransactLegendLoader object with the XML document containing 
     * a master list of transaction types.
     * 
     * @param doc 
     *          The path and file name of the XML document to parse.
     * @throws SystemException 
     *          Parsing errors.
     */
    public TransactLegendLoader(String doc) throws SystemException {
	super(doc);
	this.legend = new HashMap();
	this.logger = Logger.getLogger("TransactLegendLoader");
	this.name = null;
	this.code = null;
	this.text = null;
	this.colCount = 0;
    }

    /**
     * Obtains the SAX driver needed to parse document
     * 
     * @throws SystemException
     */
    protected void getDocResources() throws SystemException {
	//        this.saxDriver = AppPropertyPool.getProperty("SAXDriver");

	// TODO:  Comment when executed in suitable environment
	this.saxDriver = "com.ibm.xml.parsers.SAXParser";
	return;
    }

    /**
     * Renders the total number of legend items loaded..
     * 
     * @throws SAXException
     */
    public void endDocument() throws SAXException {
	super.endDocument();
	this.logger.log(Level.INFO, "Total number of legend items loaded: " + this.legend.size());
    }

    /**
     * Relies on the ancestor logic.
     * 
     * @param elementName the element to process
     * @param amap the attribute list data values for the elementName
     * @throws SAXException
     */
    public void startElement(String elementName, AttributeList amap) throws SAXException {
	super.startElement(elementName, amap);
    }

    /**
     * Creates a LoaderTransType instance from the element values <i>name</i> and 
     * <i>code</i> member variables and adds the instance to a Map collection key 
     * by <i>name</i>.  
     * 
     * @param elementName 
     *          the element to process which includes only <i>name</i> and <i>code</i>.
     * @throws SAXException
     */
    public void endElement(String elementName) throws SAXException {
	super.endElement(elementName);
	if (elementName.equalsIgnoreCase("name")) {
	    this.name = this.text;
	    this.colCount++;
	}

	if (elementName.equalsIgnoreCase("code")) {
	    this.code = this.text;
	    this.colCount++;
	}

	if (this.colCount == 2) {
	    LoaderTransType transType = new LoaderTransType();
	    transType.setName(this.name);
	    transType.setCode(this.code);
	    this.legend.put(this.name, transType);

	    this.msg = "Element loaded..." + this.name;
	    System.out.println(this.msg);
	    this.logger.log(Level.INFO, this.msg);

	    this.name = null;
	    this.code = null;
	    this.colCount = 0;
	}
    }

    /**
     * @return the legend
     */
    public Map getLegend() {
	return legend;
    }

}
