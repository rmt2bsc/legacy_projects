package com.loader;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;


import com.util.SystemException;
import com.util.RMT2SaxDocBase;

/**
 * This class builds a Map of {@link com.loader.LoaderTransType ContactCategory} objects, 
 * which is keyed by ContactCategory.name, by way of parsing an XML document using SAX 
 * technologies.
 * 
 * @author roy.terrell
 *
 */
public class CategoryLoader extends RMT2SaxDocBase {
    private static Logger logger = Logger.getLogger(CategoryLoader.class);

    private Map hash;

    private int count;

    /**
     * Creates a CategoryLoader object with the XML document containing 
     * a master list of transaction types.
     * 
     * @param doc 
     *          The path and file name of the XML document to parse.
     * @throws SystemException 
     *          Parsing errors.
     */
    public CategoryLoader(String doc) throws SystemException {
	super(doc);
	this.hash = new HashMap();
	logger = Logger.getLogger("CategoryLoader");
	this.text = null;
	this.count = 0;
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
     * Renders the total number of hash items loaded..
     * 
     * @throws SAXException
     */
    public void endDocument() throws SAXException {
	super.endDocument();
	logger.log(Level.INFO, "Total number of contact category items loaded: " + this.count);
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
     * Creates a ContactCategory instance from the element values <i>name</i> and 
     * <i>code</i> member variables and adds the instance to a Map collection key 
     * by <i>name</i>.  
     * 
     * @param elementName 
     *          the element to process which includes only <i>name</i> and <i>code</i>.
     * @throws SAXException
     */
    public void endElement(String elementName) throws SAXException {
	super.endElement(elementName);
	if (elementName.equalsIgnoreCase("item")) {
	    this.hash.put(this.text, this.text);
	    this.count++;
	}
    }

    /**
     * @return the hash
     */
    public Map getCategories() {
	return hash;
    }

}
