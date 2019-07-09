package com.util;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.jdom.Document;
import org.jdom.JDOMException;

import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;

import com.api.security.pool.AppPropertyPool;
import com.api.xml.NamespacePrefixResolver;
import com.api.xml.XmlApiFactory;
import com.api.xml.XmlDao;

import com.controller.Request;
import com.controller.Response;

import javax.servlet.ServletOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.util.SystemException;

/**
 * A utility for performing commonly used XML operations.  Conatins functionality 
 * regarding XSLT transformation and converting java objects to XML.
 * <p>
 * The XSLT functionality offering contains XSLT technology to transform XML data 
 * into meaningful output.   The output can be in the form of XML, HTML, 
 * FO-XSLT, and PDF.   The Apache FO Processor is used to convert XSLT-FO 
 * documents to PDF's which can be directed to disk or the browser.
 * <p>
 * Also contains functionality to convert javabeans to XML documents, which the the 
 * XML documents created are String based.  The following javabeans are supported: 
 * <ul>
 *   <li>primitive data type</li>
 *   <li>wrapper objects</li>
 *   <li>non-native complex javabeans</li>
 *   <li>List</li>
 *   <li>Map</li>
 *   <li>Set</li>
 * </ul>
 * The following java native classes are ignored when encountered as a top-level class 
 * or nested:  
 * <ul>
 *   <li>Class</li>
 *   <li>ObjectInput</li>
 *   <li>FileOutputStream</li>
 *   <li>FileInputStream</li>
 *   <li>ObjectOutput</li>
 * </ul>
 * 
 * @author appdev
 *
 */
public class RMT2XmlUtility {
    /** The xml tag that is to be used as the root of a xml document derived from a SQL query */
    public static final String XML_TAG_START = "<dataitem>";

    /** The xml tag that is to be used as the end of a xml document derived from a SQL query */
    public static final String XML_TAG_END = "</dataitem>";

    public static Logger logger = Logger.getLogger("RMT2XmlUtility");

    private String msg;

    /**
     * Privately constructs a RMT2XmlUtility object.
     *
     */
    private RMT2XmlUtility() {
        return;
    }

    /**
     * Produces an instance of RMT2XmlUtility.
     * 
     * @return {@link RMT2XmlUtility}
     */
    public static RMT2XmlUtility getInstance() {
        return new RMT2XmlUtility();
    }

    /**
     * Checks a XML document for well-formness or validity.
     * 
     * @param doc 
     *          A String which is the actual contents of the XML document to check.
     * @param checkValidity 
     *          true instructs to perform a validity check.   Otherwise, well-formness is checked.
     * @throws SystemException
     *          When <i>doc</i> is invalid or not well-formed or an I/O error occurred.
     */
    public void checkDocument(String doc, boolean checkValidity) throws SystemException {
        InputSource src = RMT2XmlUtility.createXmlDocument(doc);

        // Determine if validation should be enabled
        SAXBuilder builder = new SAXBuilder(checkValidity);

        // Use InputSource to validate XML
        try {
            // If there are no well-formedness or validity errors, then no exception is thrown.
            builder.build(src);
        }
        // Indicates a well-formedness or validity error
        catch (JDOMException e) {
            logger.log(Level.ERROR, "XML document was not valid or well-formed.");
            throw new SystemException(e);
        }
        catch (IOException e) {
            logger.log(Level.ERROR, "I/O error occurred trying to validate XML document");
            throw new SystemException(e);
        }
    }

    /**
     * Performs the transformation of a XSLT document to a file 
     * using the path names of the input and output documents.   
     * <p>This method implies that the source and target documents 
     * are retrieved from and stored to disk.
     *  
     * @param xsltFileName File name of the XSLT input document.
     * @param xmlFileName File name of the input XML document.
     * @param outFileName File name that will store the results of the transformation
     * @throws SystemException
     */
    public void transformXslt(String xsltFileName, String xmlFileName, String outFileName) throws SystemException {
        File xsltFile = new File(xsltFileName);
        File xmlFile = new File(xmlFileName);
        File outFile = new File(outFileName);
        this.transformXslt(xsltFile, xmlFile, outFile);
    }

    /**
     * Performs the transformation of a XSLT document to a file 
     * using the path names of the input sources and an output 
     * object that is a descendent of either an OutputStream, File, 
     * or Writer object.   
     * <p>This method implies that the source documents are retrieved 
     * from and stored to disk and "out" has been set up as an appropriate 
     * output object.
     *  
     * @param xsltFileName File name of the XSLT input document.
     * @param xmlFileName File name of the input XML document.
     * @param out OutputStream, File, or Writer object which output will be channeled.
     * @throws SystemException
     */
    public void transformXslt(String xsltFileName, String xmlFileName, Object out) throws SystemException {
        File xsltFile = new File(xsltFileName);
        File xmlFile = new File(xmlFileName);
        this.transformXslt(xsltFile, xmlFile, out);
    }

    /**
     * Performs the transformation of a XSLT document to a file.  <p>The 
     * input and output documents are in the form of File objects.   
     * This method implies that the source and target documents 
     * are retrieved from and stored to disk.
     * 
     * @param xsltFile A non-null File reference pointing to the XSLT file.
     * @param xmlFile A non-null File reference pointing to the XML file.
     * @param outFile A non-null File reference pointing to a valid output file.
     * @throws SystemException
     */
    public void transformXslt(File xsltFile, File xmlFile, Object out) throws SystemException {
        String fileName = null;

        // All file references cannot be null
        if (xsltFile != null && xmlFile != null && out != null) {
            // THe references are valid
        }
        else {
            this.msg = "The XSLT and/or XML document File references must be valid";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }

        // Input files must exist and cannot be direcotires.
        fileName = xsltFile.getAbsolutePath();
        if (!xsltFile.exists()) {
            if (fileName == null || fileName.length() <= 0) {
                fileName = "<File Name Unknown";
            }
            this.msg = "XSLT document named, " + fileName + " does not exist in file system";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        if (xsltFile.isDirectory()) {
            this.msg = "XSLT document named, " + fileName + " cannot be a directory";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        fileName = xmlFile.getAbsolutePath();
        if (!xmlFile.exists()) {
            if (fileName == null || fileName.length() <= 0) {
                fileName = "<File Name Unknown";
            }
            this.msg = "XML document named, " + fileName + " does not exist in file system";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        if (xmlFile.isDirectory()) {
            this.msg = "XML document named, " + fileName + " cannot be a directory";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }

        // Setup Streams
        InputStream xsltStream = null;
        InputStream xmlStream = null;
        try {
            xsltStream = new FileInputStream(xsltFile);
            xmlStream = new FileInputStream(xmlFile);
            this.transformXslt(xsltStream, xsltFile.getAbsolutePath(), xmlStream, out);
        }
        catch (FileNotFoundException e) {
            this.msg = "Output file is a directory rather than a regular file, does not exist, cannot be created, or cannot be opened for some other reason";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        catch (NullPointerException e) {
            this.msg = "One or more of the File arguments are invalid";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        finally {
            try {
                xsltStream.close();
                xmlStream.close();
            }
            catch (IOException e) {
                this.msg = "A problem occurred closing either the XSLT, XML, or output files";
                logger.log(Level.ERROR, this.msg);
                throw new SystemException(this.msg);
            }
        }
    }

    /**
     * Performs the transformation of a XSLT document to its expected output 
     * format.  <p>The input and output documents are in the form of InputStream 
     * and OutputStream objects, respectively. The input streams are converted 
     * to StreamSource objects and the ouput stream is converted to a StreamResult 
     * object.
     *    
     * @param xsltStream A non-null InputStream object representing the XSLT document.
     * @param xsltPath The path of the XSLT document in the event the stream is derived from a file.  Otherwise, it is legal to be null.
     * @param xmlStream A non-null InputStream object representing the XML document.
     * @param outStream A non-null InputStream object representing the results of the transformation.
     * @throws SystemException
     */
    public void transformXslt(InputStream xsltStream, String xsltPath, InputStream xmlStream, Object out) throws SystemException {
        StreamSource xsltSrc = null;
        StreamSource xmlSrc = null;
        StreamResult outSrc = null;

        xsltSrc = new StreamSource(xsltStream, xsltPath);
        xmlSrc = new StreamSource(xmlStream);
        outSrc = this.initResultStream(out);

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsltSrc);
            transformer.transform(xmlSrc, outSrc);
            String filename = outSrc.getSystemId();
            logger.log(Level.DEBUG, "Output source created: " + filename);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "XSLT Transformation error: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        finally {

        }
    }

    /**
     * Performs an in-memory transformation of a XSLT document to its expected output 
     * format.  The XSLT document is in the form of an InputStream, the XML data input 
     * is in the form of a String, and output is in the form of OutputStream object. 
     * The input streams are converted to StreamSource objects and the ouput stream is 
     * converted to a StreamResult object.
     *    
     * @param xslFileName 
     *          The filename of the XSL reosurce.  The file name can either be absoulte 
     *          or relative. 
     * @param xml 
     *          A String representing the XML document that is to be transformed.
     * @param outStream 
     *          A generic object which its runtime type is evaluated as either File, 
     *          OutputStream, or Writer instance.  This will represent the results of 
     *          the transformation.
     * @throws SystemException
     *          For general transformation errors
     */
    public void transform(String xslFileName, String xml, Object out) throws SystemException {
        InputStream in = RMT2File.getFileInputStream(xslFileName);
        if (in == null) {
            this.msg = "An input stream could not be created for file, " + xslFileName;
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        StreamSource xsltSrc = new StreamSource(in, null);
        StreamSource xmlSrc = new StreamSource(bais);
        StreamResult outSrc = this.initResultStream(out);

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsltSrc);
            transformer.transform(xmlSrc, outSrc);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "XSLT Transformation error: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        finally {
            xsltSrc = null;
            xmlSrc = null;
        }
    }

    /**
     * Transform a XML input source instance to a String value.
     * 
     * @param source
     * @return String
     *          XML document
     * @throws SystemException
     */
    public String transform(Source source) throws SystemException {
        String msg = null;
        String xml = null;
        try {
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, result);
            xml = stringWriter.getBuffer().toString();
            return xml;
        }
        catch (TransformerConfigurationException e) {
            msg = "TransformerConfigurationException while parsing soap body to string: " + e.getMessage();
            logger.error(msg);
            logger.debug("Stack Trace: ", e);
            throw new SystemException(e);
        }
        catch (TransformerException e) {
            msg = "TransformerException while parsing soap body to string: " + e.getMessage();
            logger.error(msg);
            logger.debug("Stack Trace: ", e);
            throw new SystemException(e);
        }
    }

    /**
     * Creates a StreamResult object out of outSrc.
     * 
     * @param outSrc An object of type OutputStream, Writer, or File.
     * @return StreamResult.
     * @throws SystemException When ourSrc is of an invalid type or is null.
     */
    private StreamResult initResultStream(Object outSrc) throws SystemException {
        StreamResult out = null;
        if (outSrc == null) {
            this.msg = "Output source cannot be null";
            throw new SystemException(this.msg);
        }
        if (outSrc instanceof File) {
            out = new StreamResult((File) outSrc);
        }
        else if (outSrc instanceof OutputStream) {
            out = new StreamResult((OutputStream) outSrc);
        }
        else if (outSrc instanceof Writer) {
            out = new StreamResult((Writer) outSrc);
        }
        else {
            this.msg = "Output source is of a data type that is not applicable for converting to a StreamResult.  Must be of type OutputStream, Writer, or File";
            throw new SystemException(this.msg);
        }
        return out;
    }

    /**
     * Renders a XSLT Formatted Object document to a file in PDF format.
     * 
     * @param srcFileName The XSLT-FO document to be rendered.
     * @param destFileName The path where the PDF results will be stored on the file system.
     * @throws SystemException
     */
    public void renderPdf(String srcFileName, String destFileName) throws SystemException {
	File outFile = new File(destFileName);
        OutputStream outFileStream = null;
        try {
            outFileStream = new BufferedOutputStream(new FileOutputStream(outFile));
            this.generateXsltPdf(srcFileName, outFileStream);
            outFileStream.flush();
            outFileStream.close();
        }
        catch (FileNotFoundException e) {
            this.msg = "Output file is a directory rather than a regular file, does not exist, cannot be created, or cannot be opened for some other reason";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        catch (IOException e) {
            this.msg = "IOException:  A problem occurred closing either the XSLT, XML, or output files";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
    }

    
    /**
     * Renders a XSLT Formatted Object document to the client's browser in PDF format.
     * 
     * @param srcFileName The XSLT-FO document to be rendered.
     * @param request The HTTP request object that may contain data needed to process PDF document.
     * @param response The HTTP response object used to send the PDF result to the browser.
     * @throws SystemException
     */
    public void renderPdf(String srcFileName, Request request, Response response) throws SystemException {
        ByteArrayOutputStream stream = this.generateXsltPdf(srcFileName);
        try {
            //Prepare response
            response.setContentType("application/pdf");
            response.setContentLength(stream.size());

            //Send content to Browser
            ((ServletOutputStream) response.getOutputStream()).write(stream.toByteArray());
            ((ServletOutputStream) response.getOutputStream()).flush();
        }
        catch (IOException e) {
            this.msg = "IOException:  A problem occurred redering PDF output to browser";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
    }
    
    
      
    /**
     * Generates a PDF document from a XSLT Formatted Object document, <i>srcFileName</i>,  using Apache 
     * Formatted Object Processeor.
     * 
     * @param srcFileName The XSLT-FO document to be rendered.
     * @return ByteArrayOutputStream representing the PDF results.
     * @throws SystemException
     */
    private ByteArrayOutputStream generateXsltPdf(String srcFileName) throws SystemException {
        // Setup a buffer to obtain the content length
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        this.generateXsltPdf(srcFileName, pdfStream);
        return pdfStream;
    }
    
    
    /**
     * Generates a PDF document from a XSLT Formatted Object document using Apache Formatted Object 
     * Processeor to a generic output stream
     * 
     * @param srcFileName
     *                	the filename of the XSL-FO document acting as the source of the generated PDF.
     * @param pdfStream
     * 			a generic output stream for direciting the generated results.
     * @throws SystemException
     */
    private void generateXsltPdf(String srcFileName, OutputStream outStream) throws SystemException {

        try {
            // Set up input and output streams.
            // Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
            File inFile = new File(srcFileName);

            // Setup input and output for XSLT transformation 
            // Setup input stream
            Source source = new StreamSource(inFile);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            // Construct fop with desired output format
            // (reuse if you plan to render multiple documents!)
            FopFactory fopFactory = FopFactory.newInstance();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outStream);
            Result result = new SAXResult(fop.getDefaultHandler());

            // Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            // Start XSLT transformation and FOP processing
            transformer.transform(source, result);
            return;
        }
        catch (TransformerConfigurationException e) {
            this.msg = "TransformerConfigurationException: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        catch (TransformerException e) {
            this.msg = "TransformerException: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        catch (FOPException e) {
            this.msg = "FOPException: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        finally {
            try {
                outStream.close();
            }
            catch (IOException e) {
                this.msg = "IOException: " + e.getMessage();
                logger.log(Level.ERROR, this.msg);
                throw new SystemException(this.msg);
            }
        }
    }

    public void serialize(Object obj, String filePath) throws SystemException {
        XMLEncoder out = null;
        try {
            out = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)));
            out.writeObject(obj);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new SystemException(e);
        }
        finally {
            out.close();
        }

    }

    public Object deserialize(String filePath) throws SystemException {
        XMLDecoder in = null;
        try {
            in = new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
            Object obj = in.readObject();
            return obj;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new SystemException(e);
        }
        finally {
            in.close();
        }
    }

    /**
     * Creates a XML instance in the form of an InputSource from a XML document of type String.
     * 
     * @param xmlData 
     *          XML document in String format.
     * @return InputSOurce representation of <i>xmlData</i>
     * @throws SystemException
     */
    public static InputSource createXmlDocument(String xmlData) throws SystemException {
        if (xmlData.length() <= 0) {
            throw new SystemException("The XML input source does not contain any content");
        }

        // Create an InputStream
        ByteArrayInputStream bais = new ByteArrayInputStream(xmlData.getBytes());
        InputSource inputSource = new InputSource(bais);
        return inputSource;
    }

    
    /**
     * Creates a XML instance in the form of a org.w3c.dom.Document from a XML document of type 
     * String.
     * 
     * @param xmlSource
     * @return
     * @throws SystemException
     */
    public static org.w3c.dom.Document stringToDocument(String xmlSource)   throws SystemException {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true);
	DocumentBuilder builder;
	String errMsg = null;
	try {
	    builder = factory.newDocumentBuilder();
	    return builder.parse(new InputSource(new StringReader(xmlSource)));
	}
	catch (ParserConfigurationException e) {
	    errMsg = "Unable to convert XML String to Document instance due to a parser configuration problem for the XML DocumentBuilder interface : " + e.getMessage();
	    logger.error(errMsg);
	    throw new SystemException(errMsg);
	}
	catch (SAXException e) {
	    errMsg = "Unable to convert XML String to Document instance due to the existence of an error related to the actual parsing of the XML document:  " + e.getMessage();
	    logger.error(errMsg);
	    throw new SystemException(errMsg);
	}
	catch (IOException e) {
	    errMsg = "Unable to convert XML String to Document instance due to general IO error(s):  " + e.getMessage();
	    logger.error(errMsg);
	    throw new SystemException(errMsg);
	}
    }
    
    /**
     * Converts a Document instance to a XML String.
     * 
     * @param node
     * @return
     */
    public static String documentToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } 
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } 
        catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * Obtains the name of the root element of the XML document.  This will work for qualified and 
     * unqualified elements.
     * 
     * @param xml
     *                XML document in String form.
     * @return
     *                the name of the element which could contain an assoicated namespace prefix.
     * @throws SystemException
     *                document parsing errors.
     */
    public static String getDocumentName(String xml) throws SystemException {
	org.w3c.dom.Document doc = RMT2XmlUtility.stringToDocument(xml);
	Element e = doc.getDocumentElement();  
	String docName = e.getTagName();
	return docName;
    }

    /**
     * Applies the XPath expression from the query property to the 
     * InputSource object that represents the XML data.  The return 
     * type is of type XPathConstants.NODESET which, by default, is 
     * a NodeList.
     * 
     * @param xpathExp The XPath query
     * @return NodeList The results of the XPath expression as one or more nodes.
     * @throws SystemException 
     *           If xpathExp is null or invalid or if the expression could 
     *           not be applied.
     */
    public static NodeList executeXpathQuery(String xpathExp, InputSource doc) throws SystemException {
        return (NodeList) RMT2XmlUtility.executeXpathQuery(xpathExp, doc, XPathConstants.NODESET);
    }

    /**
     * Applies the XPath expression from the query property to the 
     * InputSource object that represents the XML data.
     * 
     * @param xpathExp The XPath query
     * @param doc XML document as an InputSource object.
     * @param qName  
     * @return Object 
     *           The results of the XPath expression which could be 
     *           either a String or an Object. 
     * @throws SystemException 
     *           If xpathExp is null or invalid or if the expression could 
     *           not be applied.
     */
    public static Object executeXpathQuery(String xpathExp, InputSource doc, QName qName) throws SystemException {
        if (xpathExp == null) {
            throw new SystemException("The XPath Expression is null or invalid");
        }
        Object result = null;
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new NamespacePrefixResolver());

        try {
            result = xpath.evaluate(xpathExp, doc, qName);
            return result;
        }
        catch (XPathExpressionException e) {
            throw new SystemException("XPathExpressionException: " + e.getMessage());
        }
    }

    /**
     * Do the conversion from a DOM Node to a XML String
     * 
     * @param node The node to convert
     * @return The String representing the Node
     */
    public static String transformNodeToString(Node node) {
        StringBuffer nodeString = new StringBuffer();

        if (node.getNodeName() != null) {
            if (!node.getNodeName().equals("#text")) {
                nodeString.append("<" + node.getNodeName() + ">");
            }
            // get your node value
            if (node.getNodeValue() != null) {
                nodeString.append(node.getTextContent());
            }

            // do i have a child node to handle?
            if (node.hasChildNodes()) {
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    nodeString.append(RMT2XmlUtility.transformNodeToString(node.getChildNodes().item(i)));
                }
            }
            if (!node.getNodeName().equals("#text")) {
                nodeString.append("</" + node.getNodeName() + ">");
            }
        }
        return nodeString.toString();
    }

    /**
     * Retrieves the XML name space URI that is mapped to a specified name space prefix.
     * 
     * @param uriPrefix
     *           the URI namespace prefix to search.
     * @return String
     *           the matching URI of <i>uriPrefix</i>
     * @throws NotFoundException
     *           the URI prefix was not found in designated XML namespace 
     *           configuration file set as {@link com.api.xml.XmlApiFactory.NAMESPACES_RESOURCES NAMESPACES_RESOURCES} 
     * @throws SystemException
     *           when <i>uriPrefix</i> is null or class cast error occurs due to the value retrieved from 
     *           ResourceBundle that is mapped to <i>uriPrefix</i>.
     * @throws NotFoundException
     *           when <i>uriPrefix</i> is not included in the namespace resource bunlde or it is included 
     *           but not mapped to any value.           
     */
    public static String getXmlNamespaceUri(String uriPrefix) throws NotFoundException, SystemException {
	String errMsg = null;
        ResourceBundle rb = XmlApiFactory.getXmlNamespaces();
        try {
            String uri = rb.getString(uriPrefix);   
            if (uri == null) {
                throw new NotFoundException("XML namespace prefix, " + uriPrefix + ", was not found or not configured in the XML namespace configuration file, "
                        + XmlApiFactory.NAMESPACES_RESOURCES);
            }
            return uri;    
        }
        catch (NullPointerException e) {
            errMsg = "Unable to determine XML element's namespace URI due to the input URI prefix is null";
            logger.error(errMsg);
            throw new SystemException(errMsg);
        }
        catch (MissingResourceException e) {
            errMsg = "Unable to determine XML element's namespace URI due to input URI prefix key [" + uriPrefix + "] is not included in resource bundle, " + XmlApiFactory.NAMESPACES_RESOURCES + ".properties";
            logger.error(errMsg);
            throw new NotFoundException(errMsg);
        }
        catch (ClassCastException e) {
            errMsg = "Unable to determine XML element's namespace URI due to class cast exception:  " + e.getMessage();
            logger.error(errMsg);
            throw new SystemException(errMsg);
        }
    }

    /**
     * 
     * @param source
     * @return
     * @throws SystemException
     */
    public static final Node getRootNode(Source source) throws SystemException {
        try {
            Node root = null;
            if (source instanceof DOMSource) {
                root = ((DOMSource) source).getNode();
            }
            else if (source instanceof StreamSource) {
                InputStream is = ((StreamSource) source).getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                DocumentBuilder db = null;
                db = dbf.newDocumentBuilder();
                org.w3c.dom.Document doc = db.parse(is);
                root = (Node) doc.getDocumentElement();
            }
            else if (source instanceof SAXSource) {
                InputSource inSource = ((SAXSource) source).getInputSource();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                DocumentBuilder db = null;
                db = dbf.newDocumentBuilder();
                org.w3c.dom.Document doc = db.parse(inSource);
                root = (Node) doc.getDocumentElement();
            }
            return root;
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
    }

    /**
     * Obtain an element from an XML document on the fly by supplying the name of the 
     * element whose value we want to retrieve, the XPath query, and the XML document 
     * as a String.   This method is not the most efficient means of performing XML 
     * queries, and it is not recommended to invoke this method iteratively.    It is 
     * best suited for one time type queries.
     * 
     * @param elementName
     *         the name of the element to retreive value.
     * @param xmlQuery
     *         XPath expression
     * @param xml
     *         XML String
     * @return
     *         the value of <i>elementName</i>
     * @throws DatabaseException
     * @throws SystemException
     */
    public static String getElementValue(String elementName, String xmlQuery, String xml) throws DatabaseException, SystemException {
        XmlDao dao = XmlApiFactory.createXmlDao(xml);
        String value = null;
        dao.retrieve(xmlQuery);
        if (dao.nextRow()) {
            value = dao.getColumnValue(elementName);
        }
        return value;
    }

    /**
     * Prints a W3C XML document instance using JDOM api.
     * 
     * @param document
     * @return
     * @throws TransformerException
     */
    public static String printDocumentWithJdom(org.w3c.dom.Document document, boolean pretty, boolean omitDeclaration) throws SystemException {
        DOMBuilder builder = new DOMBuilder();
        Document jdomDoc = builder.build(document);

        // Output document
        //	Document doc = new Document(root);
        Format format = null;
        if (pretty) {
            format = Format.getPrettyFormat();
            format.setIndent("   ");
        }
        else {
            format = Format.getCompactFormat();
        }
        format.setOmitDeclaration(omitDeclaration);
        XMLOutputter outputter = new XMLOutputter(format);
        String xml = outputter.outputString(jdomDoc);
        return xml;
    }

    /**
     * Creates a SAX InputSource instance using an instance of  org.w3c.dom.Document.
     * 
     * @param doc
     * @return
     * @throws SystemException
     */
    public static InputSource getSaxInputSource(org.w3c.dom.Document doc) throws SystemException {
        // THE JDOM way to obtain XML string from Document instance...
        String xml = RMT2XmlUtility.printDocumentWithJdom(doc, true, true);
        StringReader is = new StringReader(xml);
        InputSource insrc = new InputSource(is);
        return insrc;
    }

    /**
     * Will try to obtain SAX driver class name from a preconfigured source where the SAX driver property is named, "SAXDriver".    
     * First, an attempt is made to obtain the driver name from the AppPropertyPool manager.   If null, a second attempt is made to 
     * obtain the driver name from the System class.
     * 
     * @return  The name of the driver or null if the driver is not configured in the environment.
     * @throws SystemException
     */
    public static String getSaxDriver() throws SystemException {
        String driver = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_SAX_DRIVER);
        if (driver == null) {
            driver = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SAX_DRIVER);
        }
        return driver;
    }

} // end class

