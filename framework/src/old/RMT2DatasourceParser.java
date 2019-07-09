package com.util;

import java.util.Hashtable;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.util.SystemException;
import com.util.RMT2SaxDocBase;

import com.api.db.DbSqlConst;
import com.bean.RMT2SaxAttributesBean;
import com.bean.RMT2TagQueryBean;
import com.bean.db.DataSourceColumn;
import com.bean.db.ObjectMapperAttrib;
import com.bean.db.TableUsageBean;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

/**
 * This class constructs an {@link ObjectMapperAttrib} object from a DataSource 
 * view XML Document using SAX XML technologies.
 * 
 * @author roy.terrell
 *
 */
public class RMT2DatasourceParser extends RMT2SaxDocBase {
    private static Logger logger = Logger.getLogger(RMT2DatasourceParser.class);

    private ObjectMapperAttrib ds;

    private RMT2SaxAttributesBean attObj;

    private Hashtable selectStatement;

    private Hashtable colDefs;

    private Hashtable tables;

    private String dsName;

    private String orderByClause;

    private String whereClause;

    private int tableCount = 0;

    /**
     * Constructs a RMT2DatasourceParser object using a RMT2TagQueryBean, the 
     * document type, and the system ResourceBundle.
     * 
     * @param queryData Constains custom selection criteria and sequencing data to be applied to parsed DataSource view
     * @param docType The type of document to parse.
     * @param _sysData The system ResourceBundle.
     * @throws SystemException
     */
    public RMT2DatasourceParser(RMT2TagQueryBean queryData, int docType) throws SystemException {
        super(queryData.getQuerySource(), docType);
        logger.log(Level.DEBUG, "Creating DatasourceParser(RMT2TagQueryBean, int)");
	this.whereClause = queryData.getWhereClause();
	this.orderByClause = queryData.getOrderByClause();
	this.className = "RMT2DatasourceParser";
	ds = new ObjectMapperAttrib();
	ds.setWhereClause(this.whereClause);
	ds.setOrderByClause(this.orderByClause);

	// set Datasource's Document Type
	switch (docType) {
	case DbSqlConst.DOCTYPE_DATASOURCE:
	    ds.setType("xml");
	    ds.setName(queryData.getQuerySource());
	}
	this.attObj = new RMT2SaxAttributesBean();
	this.selectStatement = new Hashtable();
	this.colDefs = new Hashtable();
	this.tables = new Hashtable();
    }

    /**
     * Constructs a RMT2DatasourceParser using the name of the DataSource view and the System ResourceBundle.
     * 
     * @param dsName The name of the DataSource View.
     * @param _sysData ResourceBunlde of system properties.
     * @throws SystemException
     */
    public RMT2DatasourceParser(String dsName) throws SystemException {
        super(dsName, DbSqlConst.DOCTYPE_DATASOURCE);
        logger.log(Level.DEBUG, "Creating DatasourceParser(String");
	this.className = "RMT2DatasourceParser";
	ds = new ObjectMapperAttrib();

	// set Datasource's Document Type
	ds.setType("xml");
	ds.setName(dsName);
	this.attObj = new RMT2SaxAttributesBean();
	this.selectStatement = new Hashtable();
	this.colDefs = new Hashtable();
	this.tables = new Hashtable();
    }

    /**
     * Drives the process of parsing the DataSource view document.
     * 
     * @return ({@link ObjectMapperAttrib}
     */
    public Object parseDocument() throws SystemException {
	logger.log(Level.DEBUG, "Begin parsing XML document, " + this.docName);
	super.parseDocument();
	return ds;
    }

    /**
     * Once the end of the document is reach during parsing, the DataSource 
     * identity, SQL Select statement, column definitions and table names are set.
     */
    public void endDocument() throws SAXException {
	super.endDocument();
	ds.setName(dsName); // set Datasource's Identity (Name)
	ds.setSelectStatement(this.selectStatement); // set Datasource's selectStatement
	ds.setColumnDef(this.colDefs); // set Datasource's Column Definitions
	ds.setTables(this.tables); // set Datasource's Table List
	logger.log(Level.DEBUG, "endDocument at decendent is called:");
    }

    /**
     * Process either the datasource, sql, TableUsage, or colattr tags of 
     * the DataSource view.
     * 
     * @param elementName the element to process
     * @param amap the attribute list data values for the elementName
     */
    public void startElement(String elementName, AttributeList amap) throws SAXException {
	super.startElement(elementName, amap);
	Hashtable atts = this.getElementAtts();
	int count = 0;

	// Obtain Datasource information
	if (elementName.equalsIgnoreCase("datasource")) {
	    attObj = (RMT2SaxAttributesBean) atts.get(amap.getName(0));
	    this.dsName = attObj.getAttValue();
	}

	// Obtain SQL Statement information
	if (elementName.equalsIgnoreCase("sql")) {
	    count = atts.size();
	    logger.log(Level.DEBUG, "startElement @ decendent - Atribute Count: " + count);
	    Enumeration keys = atts.keys();
	    String keyName = null;

	    while (keys.hasMoreElements()) {
		keyName = (String) keys.nextElement();
		int keyValue = DbSqlConst.getSelectObjectKey(keyName); // get numeric key value
		attObj = (RMT2SaxAttributesBean) atts.get(keyName); // get key's sql clause
		logger.log(Level.DEBUG, "startElement @ decendent - Atribute name: " + attObj.getAttName() + "  Value: " + attObj.getAttValue());
		if (attObj.getAttValue() != null) {
		    selectStatement.put(new Integer(keyValue), this.getModifiedValue(attObj));
		}
		else {
		    selectStatement.put(new Integer(keyValue), "");
		}
	    }
	}

	// Obtain all Table Entities
	if (elementName.equalsIgnoreCase("TableUsage")) {
	    count = atts.size();
	    Enumeration keys = atts.keys();
	    String keyName = null;
	    TableUsageBean dsTable = null;

	    try {
		dsTable = new TableUsageBean();
	    }
	    catch (SystemException e) {
		throw new SAXException(e.getMessage());
	    }
	    tableCount++;
	    while (keys.hasMoreElements()) {
		keyName = (String) keys.nextElement();
		attObj = (RMT2SaxAttributesBean) atts.get(keyName); // get key's Table Attribute

		if (keyName.equalsIgnoreCase("name"))
		    dsTable.setName(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("dbName"))
		    dsTable.setDbName(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("Updateable"))
		    dsTable.setUpdateable((attObj.getAttValue().equalsIgnoreCase("true") ? true : false));
	    }
	    // Key is stored in numerical order: the order which
	    // the tables appear in the XML Document.
	    tableCount = tables.size() + 1;
	    tables.put(new Integer(tableCount), dsTable);
	}

	// Obtain SQL Column Attributes
	if (elementName.equalsIgnoreCase("colattr")) {
	    count = atts.size();
	    Enumeration keys = atts.keys();
	    String keyName = null;
	    DataSourceColumn dsc = null;

	    try {
		dsc = new DataSourceColumn();
	    }
	    catch (SystemException e) {
		throw new SAXException(e.getMessage());
	    }
	    while (keys.hasMoreElements()) {
		keyName = (String) keys.nextElement();
		attObj = (RMT2SaxAttributesBean) atts.get(keyName); // get key's sql clause

		if (keyName.equalsIgnoreCase("name"))
		    dsc.setName(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("dbName"))
		    dsc.setDbName(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("SqlType"))
		    dsc.setSqlType(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("IsNull"))
		    dsc.setNullable((attObj.getAttValue().equalsIgnoreCase("true") ? true : false));
		else if (keyName.equalsIgnoreCase("JavaType"))
		    dsc.setJavaType(RMT2Utility.getJavaType(attObj.getAttValue()));
		else if (keyName.equalsIgnoreCase("PrimaryKey"))
		    dsc.setPrimaryKey((attObj.getAttValue().equalsIgnoreCase("true") ? true : false));
		else if (keyName.equalsIgnoreCase("TableName"))
		    dsc.setTableName(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("Updateable"))
		    dsc.setUpdateable((attObj.getAttValue().equalsIgnoreCase("true") ? true : false));
		else if (keyName.equalsIgnoreCase("DisplayHeader"))
		    dsc.setDisplayValue(attObj.getAttValue());
		else if (keyName.equalsIgnoreCase("Computed"))
		    dsc.setComputed((attObj.getAttValue().equalsIgnoreCase("true") ? true : false));
	    }
	    colDefs.put(dsc.getName(), dsc);
	    logger.log(Level.DEBUG, "startElement @ decendent: Column Def for " + dsc.getName() + ": " + dsc);
	}
	logger.log(Level.DEBUG, "startElement at decendent is called:");
    }

    /**
     * Relies on the ancestor to handle the end of element trigger.
     */
    public void endElement(String elementName) throws SAXException {
	super.endElement(elementName);
	logger.log(Level.DEBUG, "endElement at decendent is called:");
    }

    /**
     * Modifies the where clause or order by clause based on values contained in att.
     * If the stock clause exist then the value of att is appended to that clause.  Otherwise,
     * the clause is equal to the appropriate value of att.
     * 
     * @param att contains the name and value of the where or order by clause.
     * @return Modified where clause or order by clause.
     */
    private String getModifiedValue(RMT2SaxAttributesBean att) {

	String attName = att.getAttName();
	String attValue = att.getAttValue();

	if (!attName.equalsIgnoreCase("Where") && !attName.equalsIgnoreCase("OrderBy")) {
	    return attValue;
	}

	if (attName.equalsIgnoreCase("Where")) {
	    if (this.whereClause != null && this.whereClause.length() > 0) {
		if (attValue == null || attValue.equals("")) {
		    attValue = this.whereClause;
		}
		else {
		    attValue += " and " + this.whereClause;
		}

		return attValue;
	    }
	}

	if (attName.equalsIgnoreCase("OrderBy")) {
	    if (this.orderByClause != null && this.orderByClause.length() > 0) {
		if (attValue == null || attValue.equals("")) {
		    attValue = this.orderByClause;
		}
		else {
		    attValue += ", " + this.orderByClause;
		}

		return attValue;
	    }
	}
	return attValue;
    }

}
