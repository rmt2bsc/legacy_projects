//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//           Class: RMT2WebMenus
//
//    Constructors: public RMT2WebMenus()
//
//         Lineage: none
//
//      Implements: none
//
//     Description: Action class that actually constructs and jsp menus from an
//                        XML datasource.
//
//  SCR            Date                        Developer                   Description
// =======   ==============  ===============   =====================================
//                     2/9/2003                   RMT                             Add changes to distinquish between  menus that are
//                                                                                              to be created at the top of a jsp page or on the side
//                                                                                              of a jsp page as well as hiding and displaying the
//                                                                                              menu by default, respectively.
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.taglib.menu;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.StringReader;

import java.util.ResourceBundle;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//Import XML Classes
//import com.ibm.xml.parsers.DOMParser;
//import com.ibm.xml.parser.TXDocument;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.constants.RMT2SystemExceptionConst;
import com.constants.RMT2ServletConst; // RMT 02092003

import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * @deprecated Will be removed in future versions
 * @author roy.terrell
 *
 */
public class RMT2WebMenus {

    /////////////////////////////////
    //     Member declarations
    /////////////////////////////////
    protected ArrayList _arrayHolderArray = new ArrayList();

    protected ArrayList _arrayNamesArray = new ArrayList();

    //Default Image
    protected String imageHTML = null;

    protected String _strCurrentMenu = "";

    protected String _strSaveToFile = null;

    protected int menuType = 0; // RMT 02092003

    protected int _intLevel = 0;

    protected String className;

    /////////////////////////////////
    //       Implementation
    /////////////////////////////////
    public RMT2WebMenus() {
        this.className = "RMT2WebMenus";
        init();
    }

    public void setSubMenuImage(String _subMenuImage) {

        if (_subMenuImage != null && !_subMenuImage.equals("")) {
            imageHTML = "<img align=\"right\" vspace=\"2\" height=\"10\" width=\"10\" border=\"0\" src=\"" + _subMenuImage + "\">";
        }
        else {
            imageHTML = "<img align=\"right\" vspace=\"2\" height=\"10\" width=\"10\" border=\"0\" src=\"images/rt_arrow.gif\">";
        }

    }

    public void setOutputFile(String sFileToSaveTo) {
        _strSaveToFile = sFileToSaveTo;
    }

    public void setMenuType(int value) {
        this.menuType = value;
    }

    private void init() {
        _strCurrentMenu = "";
        _intLevel = 1;
    }

    //  public String createMenu(String startMenu, String file) throws SystemException {
    //
    //    String strOutput = "";
    //    Document document;
    //
    //    try {
    //	// The XML4J way of parsing
    //	DOMParser parser = new DOMParser();
    //	parser.parse(file);
    //	document = parser.getDocument();
    //	
    //	
    //	return this.createMenuFromDoc(document, startMenu);
    //    }
    //    catch (SAXParseException spe) {
    //      strOutput += "Parse Error.  Line: " + spe.getLineNumber();
    //      strOutput += ", URI: " + spe.getSystemId();
    //      strOutput += ", Message: " + spe.getMessage();
    //      throw new SystemException(strOutput);
    //    }
    //    catch (SAXException se) {
    //      strOutput += "SAXException: " + se.getMessage();
    //      throw new SystemException(strOutput);
    //    }
    //    catch (IOException ioe) {
    //      strOutput += "IO Error: " + ioe.getMessage();
    //      throw new SystemException(strOutput);
    //    }
    //  }

    public String createMenu(String startMenu, String file) throws SystemException {
        Document document;
        // The JAXP way of parsing
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();
            document = parser.parse(file);
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
        return this.createMenuFromDoc(document, startMenu);
    }

    protected String createMenuFromDoc(Document document, String startMenu) {

        ArrayList startArray = new ArrayList();
        String strVariable = "";
        String strTemp = "";
        String hyperLink = "";
        String strOutput = "";

        //document.getDocumentElement().normalize();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            String name = currentNode.getNodeName();

            //Only do further processing on Element Nodes
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                _strCurrentMenu = startMenu + "_" + (i + 1);
                String thisMenu = startMenu;

                if (currentNode.getChildNodes().getLength() > 5) {
                    strVariable = "<span id=\"" + thisMenu + "_span" + (i + 1) + "\" class='cellOff' onMouseOver=\"stateChange('" + _strCurrentMenu + "',this," + _intLevel
                            + ")\" onMouseOut=\"stateChange('',this,'')\">" + imageHTML + getNodeValue(currentNode.getChildNodes().item(3)) + "</span><br>\n";
                    startArray.add(strVariable);
                    try {
                        createSubMenus(currentNode);
                    }
                    catch (Exception e) {
                        return strOutput += "Error: " + e.getMessage();
                    }
                }
                else {
                    //          String str1 = getNodeValue(currentNode.getChildNodes().item(1));
                    //          String str2 = getNodeValue(currentNode.getChildNodes().item(2));
                    //          String str3 = getNodeValue(currentNode.getChildNodes().item(3));
                    strVariable = "<span id=\"" + thisMenu + "_span" + (i + 1) + "\" class='cellOff' onMouseOver=\"stateChange('',this,'');hideDiv(" + _intLevel
                            + ")\" onMouseOut=\"stateChange('',this,'')\" onClick=\"location.href='" + getNodeValue(currentNode.getChildNodes().item(1)) + "'\">"
                            + getNodeValue(currentNode.getChildNodes().item(3)) + "</span><br>\n";
                    startArray.add(strVariable);
                } // end if
            } // end if
        } // end for

        startArray.trimToSize();
        _arrayNamesArray.add(startMenu);

        for (int i = 0; i < startArray.size(); i++) {
            strTemp += startArray.get(i).toString();
        }
        _arrayHolderArray.add(strTemp);

        return generateHTML();

    } //  createMenu

    private String generateHTML() {

        StringBuffer strOutput = new StringBuffer();
        //Reverse Array order so we don't have to worry about the ZIndex of each div layer
        //Loop through arrays and write out divs and their individual span content items

        for (int i = _arrayNamesArray.size() - 1; i > -1; i--) {
            if (this.menuType == RMT2ServletConst.MENUTYPE_SIDE) { // RMT 02092003
                strOutput.append("<div id=\"" + _arrayNamesArray.get(i).toString() + "\" class=\"taskMenuItems\">"); // RMT 02092003
                //        strOutput.append("<div id='" + _arrayNamesArray.get(i).toString() + "' style='position:absolute; left:1px; top:1px; width:162px; height:538px; z-index:1; background-color: #eeeee6; layer-background-color: #eeeee6; border: 1px none #000000'>");       // RMT 02092003
            }
            else {
                strOutput.append("<div id='" + _arrayNamesArray.get(i).toString() + "' class='clsMenu'>"); // RMT 02092003
            }

            strOutput.append(_arrayHolderArray.get(i).toString());
            strOutput.append("</div>\n");
        }
        _arrayHolderArray.clear();
        _arrayNamesArray.clear();

        if (_strSaveToFile != null) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(_strSaveToFile);
                writer.write(strOutput.toString());
                writer.flush();
                if (writer != null)
                    writer.close();
            }
            catch (IOException io) {
                strOutput.append("File Error: " + io.getMessage());
                return strOutput.toString();
            }
        }
        init(); // Reset everything to accomodate multiple m
        return strOutput.toString();

    } //generateHTML

    private void createSubMenus(Node node) {
        _intLevel += 1;
        String strVariable = "";
        String strTemp = "";
        ArrayList tempArray = new ArrayList();
        for (int j = 5; j < node.getChildNodes().getLength(); j++) {
            Node newNode = node.getChildNodes().item(j);
            if (newNode.getNodeType() == Node.ELEMENT_NODE) {
                if (newNode.getChildNodes().getLength() > 5) { // Each node should have a 1=hyperlink and 2=text node so don't call the function again if there are just these children
                    _strCurrentMenu += "_" + (j - 4);
                    String thisMenu = _strCurrentMenu.substring(0, _strCurrentMenu.length() - 2);
                    strVariable = "<span id=\"" + thisMenu + "_span" + (j - 4) + "\" class='cellOff' onMouseOver=\"stateChange('" + _strCurrentMenu + "',this," + _intLevel
                            + ")\" onMouseOut=\"stateChange('',this,'')\">" + imageHTML + getNodeValue(newNode.getChildNodes().item(3)) + "</span><br>\n";
                    tempArray.add(strVariable);
                    createSubMenus(newNode);
                }
                else {
                    strVariable = "<span id=\"" + _strCurrentMenu + "_span" + (j - 4) + "\" class='cellOff' onMouseOver=\"stateChange('',this,'');hideDiv(" + _intLevel
                            + ")\" onMouseOut=\"stateChange('',this,'')\" onClick=\"location.href='" + getNodeValue(newNode.getChildNodes().item(1)) + "'\">"
                            + getNodeValue(newNode.getChildNodes().item(3)) + "</span><br>\n";
                    tempArray.add(strVariable);
                }
            }
        }

        tempArray.trimToSize();
        _arrayNamesArray.add(_strCurrentMenu);
        for (int i = 0; i < tempArray.size(); i++) {
            strTemp += tempArray.get(i).toString();
        }
        _arrayHolderArray.add(strTemp);
        _strCurrentMenu = _strCurrentMenu.substring(0, _strCurrentMenu.length() - 2); //Exiting function so go back to previous menu version
        _intLevel -= 1;
        tempArray.clear();

    } // createSubMenus

    private String getNodeValue(Node node) {
        if (node.hasChildNodes()) {
            return node.getFirstChild().getNodeValue();
        }
        else {
            if (node.getNodeName().toUpperCase().equals("HYPERLINK")) {
                return "JavaScript: function() {return false;}";
            }
            else {
                return "";
            }
        }
    }

    protected InputSource getInputSource(Document doc) throws SystemException {
        //    try {
        // THE XML4J way to obtain XML string from Document instance...
        //      StringWriter buf = new StringWriter();
        //      ((TXDocument) doc).printWithFormat(new PrintWriter(buf));
        //      String xml = buf.toString();
        //      StringReader is = new StringReader(xml);
        //      InputSource insrc = new InputSource(is);
        //      return insrc;
        //	
        //	
        //    }
        //    catch (IOException e) {
        //      throw new SystemException("IOException Error: " + e.getMessage());
        //    }

        // The JDOM way to obtain the SAX InputSource Instance.
        return RMT2XmlUtility.getSaxInputSource(doc);
    }

} // RMT2WebMenus
