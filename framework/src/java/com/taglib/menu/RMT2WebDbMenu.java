package com.taglib.menu;

import java.util.ResourceBundle;
import java.util.ArrayList;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import com.ibm.xml.parsers.DOMParser;
//import com.ibm.xml.parser.TXDocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

import com.api.DataSourceApi;
import com.api.db.DatabaseException;

import com.util.SystemException;
import com.util.NotFoundException;
import com.util.RMT2Utility;

import com.taglib.menu.RMT2WebMenus;

/**
 * @deprecated Will be removed in future versions
 * @author roy.terrell
 *
 */
public class RMT2WebDbMenu extends RMT2WebMenus {

    protected DataSourceApi dso;

    protected Document doc;

    protected String sql;

    protected String dataColumn;

    protected String hyperlink;

    protected String reqParmClause;

    protected String className;

    protected String docClass;

    protected String filename;

    protected String parmNames;

    public RMT2WebDbMenu() {
        super();
        this.className = "RMT2WebDbMenu";
        this.reqParmClause = null;
        ResourceBundle prop = ResourceBundle.getBundle("SystemParms");
        this.docClass = prop.getString("docClass");
        //    this.filename = prop.getString("datasource_dir" + "\\test.xml");
        return;
    }

    public RMT2WebDbMenu(Object _dso, String _dataColumn, String _hyperlink, String _parmNames) {

        this();
        this.dso = (DataSourceApi) _dso;
        this.dataColumn = _dataColumn;
        this.hyperlink = _hyperlink;
        this.parmNames = _parmNames;
    }

    public String createMenu(String startMenu) throws SystemException {
        String value = null;

        if (this.dso == null) {
            throw new SystemException("Unable to construct dynamic task menu.  Datasource is null and invalid");
        }

        try {
            // Retrieve data
            this.dso.executeQuery();

            // Create Document Object
            doc = (Document) Class.forName(docClass).newInstance();

            // Create root element
            Element root = doc.createElement("root");

            // Get meun display items
            while (this.dso.nextRow()) {
                value = dso.getColumnValue(this.dataColumn);

                // Create Menu Item Element
                Element menuItem = doc.createElement("menuItem");

                // Create hyperlink sub menuitem
                Element hyperLinkItem = doc.createElement("hyperLink");
                this.reqParmClause = this.buildRequestParameters();
                String currentLink = this.hyperlink;
                if (this.reqParmClause != null && this.reqParmClause.length() > 0) {
                    currentLink += this.reqParmClause;
                }
                hyperLinkItem.appendChild(doc.createTextNode(currentLink));

                // Create name sub menuitem
                Element nameItem = doc.createElement("name");
                nameItem.appendChild(doc.createTextNode(RMT2Utility.wordCap(value)));

                // Arrange menu item and attach to root
                menuItem.appendChild(hyperLinkItem);
                menuItem.appendChild(nameItem);
                root.appendChild(menuItem);
            } // end while

            doc.appendChild(root);
            //      ((TXDocument) doc).setVersion("1.0");
            doc.setXmlVersion("1.0");

            //      FileWriter file = new FileWriter("c:\\testdocView.xml");
            //      ((TXDocument) doc).printWithFormat(new PrintWriter(file));

            // The XML4J way...should not need this code since we already have a valid refernece to "doc" from above.
            //      DOMParser parser = new DOMParser();
            //      parser.parse(this.getInputSource(doc));
            //      doc = parser.getDocument();

            return this.createMenuFromDoc(doc, startMenu);
        } // end try
        catch (DatabaseException e) {
            throw new SystemException("DatabaseException Error: " + e.getMessage());
        }
        catch (NotFoundException e) {
            throw new SystemException("NotFoundException Error: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            throw new SystemException("ClassNotFoundException Error: " + e.getMessage());
        }
        catch (InstantiationException e) {
            throw new SystemException("InstantiationException Error: " + e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new SystemException("IllegalAccessException Error: " + e.getMessage());
        }
        //    catch (SAXException e) {
        //      throw new SystemException("SAXException Error: " + e.getMessage());
        //    }
        //    catch (IOException e) {
        //      throw new SystemException("IOException Error: " + e.getMessage());
        //    }
    }

    protected String buildRequestParameters() throws DatabaseException, SystemException {

        String method = "buildRequestParameters";
        String col = null;
        String val = null;
        String temp = null;

        // exit if no parameter names were supplied.
        if (this.parmNames == null) {
            return "";
        }
        ArrayList parmArray = RMT2Utility.getTokens(this.parmNames, ",");
        if (parmArray == null) {
            return "";
        }

        try {
            for (int ndx = 0; ndx < parmArray.size(); ndx++) {
                col = (String) parmArray.get(ndx);
                val = this.dso.getColumnValue(col);
                if (val == null) {
                    continue;
                }
                if (temp == null) {
                    temp = "?";
                    temp += col + "=" + val;
                }
                else {
                    temp += "&" + col + "=" + val;
                }
            } // end for
            return temp;
        }
        catch (NotFoundException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

} // end class

