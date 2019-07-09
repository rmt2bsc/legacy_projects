package com.util;

import java.io.InputStream;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.util.SystemException;

import com.api.security.pool.AppPropertyPool;
import com.bean.RMT2SaxAttributesBean;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.Parser;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * Java XML class used to parse XML documents using the SAX API
 * 
 * @author roy.terrell
 * @deprecated User {@link com.api.xml.parsers.datasource.RMT2Sax2OrmThridPartyDriverImpl RMT2Sax2OrmThridPartyDriverImpl} or
 *                  {@link com.api.xml.parsers.datasource.RMT2Sax1OrmDatasourceParserImpl RMT2Sax1OrmDatasourceParserImpl} 
 *
 */
public class RMT2SaxDocBase extends HandlerBase {
    private static Logger logger = Logger.getLogger(RMT2SaxDocBase.class);

    private int docType;

    protected String saxDriver;

    private Hashtable elementAtts;

    protected Parser parser;

    protected Object obj;

    protected String msg;

    protected String className;

    protected String text;

    protected String docPath;

    protected String docName;

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    private RMT2SaxDocBase() throws SystemException {
        logger.log(Level.DEBUG, "Creating RMT2SaxDocBase()");
        this.msg = null;
    }

    /**
     * Creates a RMT2SaxDocBase object with the XML document.
     * 
     * @param doc 
     *          The XML Document
     * @throws SystemException 
     *           Illegal access or resources, problem instatitating document, 
     *           SAX driver cannot be found or general errors.
     */
    public RMT2SaxDocBase(String doc) throws SystemException {
        this();
        logger.log(Level.DEBUG, "Creating RMT2SaxDocBase(String");
        try {
            this.docName = doc;
            this.getDocResources();
            // get class object for SAX Driver
            Class c = Class.forName(this.saxDriver);
            // create instance of the class
            parser = (Parser) c.newInstance();
            // register document handler
            parser.setDocumentHandler(this);
        }
        catch (IllegalAccessException e) {
            msg = e.getMessage() + " - Illegal Access Exception";
            throw new SystemException(msg);
        }
        catch (InstantiationException e) {
            msg = e.getMessage() + " - Intantiation Exception";
            throw new SystemException(msg);
        }
        catch (ClassNotFoundException e) {
            msg = e.getMessage() + " - Class Not Found Exception";
            throw new SystemException(msg);
        }
        catch (NotFoundException e) {
            throw new SystemException(e.getMessage());
        }
        catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    /**
     * Constructs a RMT2SaxDocBase object using the document name, document type 
     * and system ResourcBundle.
     * 
     * @param doc The name of the document
     * @param docType The document type
     * @throws SystemException
     */
    public RMT2SaxDocBase(String doc, int docType) throws SystemException {
        this(doc);
        logger.log(Level.DEBUG, "Creating RMT2SaxDocBase(String, int");
        this.docType = docType;
    }

    /**
     * Parses the XML document
     * 
     * @return null
     * @throws SystemException
     */
    public Object parseDocument() throws SystemException {
        // parse document
        try {
            parser.parse(this.docName);
            return null;
        }
        catch (Exception e) {
            String targetDoc = this.docName;
            String msg = "Problem parsing doc: " + targetDoc;
            throw new SystemException(msg + ": " + e.getMessage());
        }
    }

    /**
     * Obtains system properties from the system ResourceBunled needed for 
     * parsing XML documents.
     * 
     * @throws SystemException
     */
    protected void getDocResources() throws SystemException {
        StringBuffer temp = new StringBuffer(50);
        try {
            // Since we are no longer using XML4J, SAXDriver will contain a value of "default"
            this.saxDriver = AppPropertyPool.getProperty("SAXDriver");
            // Test scenarios will obtain property values from SystemParms.properties file.
            if (this.saxDriver == null) {
                this.saxDriver = System.getProperty("SAXDriver");
                temp.append(System.getProperty("webapps_drive"));
                temp.append(System.getProperty("webapps_dir"));
                temp.append(System.getProperty("app_dir"));
                temp.append(System.getProperty("datasource_dir"));
            }
            else {
                // Non-Test scenario
                temp.append(System.getProperty("webapps_drive"));
                temp.append(System.getProperty("webapps_dir"));
                temp.append(AppPropertyPool.getProperty("app_dir"));
                temp.append(AppPropertyPool.getProperty("datasource_dir"));
            }
            if (this.saxDriver == null) {
                this.msg = "XML document could not be parsed due to SAX driver is invalid, null, or could not be found";
                logger.log(Level.ERROR, this.msg);
                throw new NotFoundException(this.msg);
            }
            temp.append("\\");
            this.docPath = temp.toString();
            this.docName = this.docPath + this.docName + ".xml";
        }
        catch (NullPointerException e) {
            String msg = e.getMessage() + " - One of the properties in SystemParms.properties are null";
            throw new SystemException(msg);
        }
    }

    /**
     * Logs the beginning of this method invocation
     */
    public void startDocument() throws SAXException {
        logger.log(Level.DEBUG, "startDocument is called:");
    }

    /**
     * Logs the beginning of this method invocation.
     */
    public void endDocument() throws SAXException {
        logger.log(Level.DEBUG, "endDocument is called:");
    }

    /**
     * Creates a RMT2SaxAttributesBean object populated with the data values 
     * contained in amap for element identified as, "name".
     */
    public void startElement(String name, AttributeList amap) throws SAXException {
        logger.log(Level.DEBUG, "startElement is called: element name=" + name);

        this.text = null;
        try {
            this.elementAtts = new Hashtable();
            for (int i = 0; i < amap.getLength(); i++) {
                RMT2SaxAttributesBean att = new RMT2SaxAttributesBean();
                String elementName = amap.getName(i);
                String type = amap.getType(i);
                String value = amap.getValue(i);
                if (value == null) {
                    value = "";
                }

                att.setAttName(elementName);
                att.setAttType(type);
                att.setAttValue(value);
                this.elementAtts.put(elementName, att);
                logger.log(Level.DEBUG, "  attribute name=" + elementName + " type=" + type + " value=" + value);
            }
        }
        catch (SystemException e) {
            throw new SAXException(e.getMessage());
        }
    }

    /**
     * Sets the element attribute varialbe to null.
     */
    public void endElement(String name) throws SAXException {
        logger.log(Level.DEBUG, "endElement is called: " + name);
        this.elementAtts = null;
    }

    /**
     * Get the contents of the element.
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        int end = start + length;
        for (int ndx = start; ndx < end; ndx++) {
            if (this.text == null) {
                this.text = "";
            }
            this.text += ch[ndx];
        }
        logger.log(Level.DEBUG, "characters is called: " + new String(ch, start, length));
    }

    /**
     * Get Document Path.
     * 
     * @return Document path
     */
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * Get the name of document.
     *  
     * @return Document's name
     */
    public String getDocName() {
        return this.docName;
    }

    /**
     * Get the Document type.
     * 
     * @return document type.
     */
    public int getDocType() {
        return this.docType;
    }

    /**
     * Get SAX Driver.
     * 
     * @return Driver name.
     */
    public String getSaxDriver() {
        return this.saxDriver;
    }

    /**
     * Get Element attributes
     * 
     * @return Hashtable of attribute name and values.
     */
    public Hashtable getElementAtts() {
        return this.elementAtts;
    }

}
