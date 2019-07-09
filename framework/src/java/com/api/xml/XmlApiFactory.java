package com.api.xml;

import org.w3c.dom.Node;

import java.util.ResourceBundle;

import com.bean.RMT2Base;

import com.controller.Request;

import com.util.RMT2File;
import com.util.SystemException;

/**
 * Factory designed to create new instances of classes related to the XML api.
 * 
 * @author roy.terrell
 *
 */
public class XmlApiFactory extends RMT2Base {

    private static ResourceBundle NAMESPACES;

    public static String NAMESPACES_RESOURCES = "com.api.xml.RMT2NamespaceContext";

    /**
     * Creates an instance of XML XmlDao a XML document in String format.
     *  
     * @param xmlDoc String representation of the XML document to manipulate
     * @return XmlDao
     */
    public static XmlDao createXmlDao(String xmlDoc) {
        try {
            return new XmlDaoImpl(xmlDoc);
        }
        catch (SystemException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates an instance of XML XmlDao using a org.w3c.dom.Node.
     *  
     * @param docNode 
     *          org.w3c.dom.Node
     * @return XmlDao
     */
    public static XmlDao createXmlDao(Node docNode) {
        try {
            return new XmlDaoImpl(docNode);
        }
        catch (SystemException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates an instance of NodeDataSource without initializing 
     * any data members.
     * 
     * @return NodeDataSource
     */
    public static NodeDataSource createNodeDataSource() {
        NodeDataSource xmlObj = new NodeDataSource();
        return xmlObj;
    }

    /**
     * Creates an instance of NodeDataSource by initializing it with an 
     * XML data source.  The data source is require to be of type NodeList.
     * 
     * @param source
     *            The XML data source.
     * @return NodeDataSource
     */
    public static NodeDataSource createNodeDataSource(Object source) {
        NodeDataSource xmlObj = new NodeDataSource();
        xmlObj.setXmlSrc(source);
        return xmlObj;
    }

    /**
     * Creates an instance of NodeDataSource with the XML data source and
     * with the user's request object. The data source is required to be of type
     * String or NodeList.
     * 
     * @param source
     *            The XML data source.
     * @param request
     *            HttpServletRequest
     * @return NodeDataSource
     */
    public static NodeDataSource createNodeDataSource(Object source, Request request) {
        NodeDataSource xmlObj = new NodeDataSource(request);
        xmlObj.setXmlSrc(source);
        return xmlObj;
    }

    /**
     * Creates an instance of DocumentDataSource with the XML data source. The
     * data source is require to be of type String or NodeList.
     * 
     * @param source
     *            The XML data source.
     * @return DocumentDataSource
     */
    public static DocumentDataSource createDocumentDataSource(Object source) {
        DocumentDataSource xmlObj = new DocumentDataSource();
        xmlObj.setXmlSrc(source);
        return xmlObj;
    }

    /**
     * Creates an instance of DocumentDataSource with the XML data source and
     * with the user's request object. The data source is required to be of type
     * String or NodeList.
     * 
     * @param source
     *            The XML data source.
     * @param request
     *            HttpServletRequest
     * @return DocumentDataSource
     */
    public static DocumentDataSource createDocumentDataSource(Object source, Request request) {
        DocumentDataSource xmlObj = new DocumentDataSource(request);
        xmlObj.setXmlSrc(source);
        return xmlObj;
    }

    /**
     * Fetches the all of the XML namespace configurations into a ResourceBundle instance.
     * 
     * @return ResourceBundle
     *          a hash collection containing namespace URI's keyed by the namespace URI prefix. 
     * @throws SystemException
     *          a problem occurred accessing or loading the ResourceBundle
     */
    public static ResourceBundle getXmlNamespaces() throws SystemException {
        if (XmlApiFactory.NAMESPACES == null) {
            XmlApiFactory.NAMESPACES = RMT2File.loadAppConfigProperties(XmlApiFactory.NAMESPACES_RESOURCES);
        }
        return XmlApiFactory.NAMESPACES;
    }

}
