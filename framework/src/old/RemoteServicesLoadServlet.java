package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;
import java.util.ResourceBundle;

import com.api.security.pool.RemoteServiesPool;

import com.util.RMT2File;
import com.util.SystemException;

import com.constants.RMT2ServletConst;
import com.constants.RMT2SystemExceptionConst;

import com.servlet.AbstractServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * Loads all remote service URL's into a property file, which the key/value pairs 
 * map to service name and service URL, repsectively.  Also, loads the service types 
 * into a Properties file, which the key/value pairs are mapped to service name and 
 * service type id, respectively.
 * 
 * @author roy.terrell
 * 
 */
public class RemoteServicesLoadServlet extends AbstractServlet {
    private static final long serialVersionUID = 4728432690522744363L;

    private ResourceBundle systemData;

    private Properties dbParms;

    private Driver dbDriver;

    private String dbDriverClass;

    private String dbURL;

    private String dbUserID;

    private String dbPassword;
    
    private String database;
    
    private String dbPropName;

    /**
     * Initialize instance and context variables. Create datasource connections
     * and place them on the context of the web application.
     */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	String msg;
	Properties services = new Properties();
	Properties serviceTypes = new Properties();
	Properties serviceSubTypes = new Properties();
	Logger logger = Logger.getLogger("RemoteServicesLoadServlet");

	logger.log(Level.INFO, "Loading Remote Service context...");
	try {
	    this.systemData = RMT2File.loadAppConfigProperties("SystemParms");
	    if (this.systemData == null) {
		msg = "System Configuration file could not be loaded";
		logger.log(Level.ERROR, msg);
		throw new ServletException(msg);
	    }
	}
	catch (SystemException e) {
	    msg = e.getMessage();
	    logger.log(Level.ERROR, msg);
	    throw new ServletException(msg);
	}

	try {
	    this.dbDriverClass = this.systemData.getString("dbdriver");
	    this.dbURL = this.systemData.getString("dburl");
	    this.dbUserID = this.systemData.getString("userid");
	    this.dbPassword = this.systemData.getString("password");
	    this.database = this.systemData.getString("database");
	    this.dbPropName = this.systemData.getString("dbPropertyName");

	    // Setup Database Driver Object and its parameters.
	    this.dbDriver = (Driver) Class.forName(this.dbDriverClass).newInstance();
	    this.dbParms = new Properties();
	    this.dbParms.setProperty("user", this.dbUserID);
	    this.dbParms.setProperty("password", this.dbPassword);
	    this.dbParms.setProperty(dbPropName, database);
	}
	catch (Exception e) {
	    msg = "Problem accessing database connection property from system configuration file";
	    logger.log(Level.ERROR, msg);
	    throw new ServletException(msg);
	}

	try {
	    Connection con = this.createConnection();
	    Statement stmt = con.createStatement();
	    
	    // TODO: Change sql statement to grab other service subtypes as needed.
	    String sql = "select * from user_resource where resource_type_id = 3";
	    ResultSet rs = stmt.executeQuery(sql);
	    while (rs.next()) {
		String srvName = rs.getString("name");
		String srvUrl = rs.getString("url");
		int srvType = rs.getInt("resource_type_id");
		int srvSubType = rs.getInt("resource_subtype_id");
		services.put(srvName, srvUrl);
		serviceTypes.put(srvName, String.valueOf(srvType));
		serviceSubTypes.put(srvName, String.valueOf(srvSubType));
	    }
	    rs.close();
	    stmt.close();
	    con.close();
	    rs = null;
	    stmt = null;
	    con = null;

	    RemoteServiesPool pool = RemoteServiesPool.getInstance();
	    pool.setServices(services);
	    pool.setServiceTypes(serviceTypes);
	    pool.setServiceSubTypes(serviceSubTypes);
	    logger.log(Level.INFO, "Remote Service URL load complete.");
	    logger.log(Level.INFO, "Total number of Remote Service URL\'s loaded: " + services.size());

	}
	catch (SQLException e) {
	    msg = "SQL Exception: " + e.getMessage();
	    logger.log(Level.ERROR, msg);
	    throw new ServletException(msg);
	}
	catch (SystemException e) {
	    msg = "DatabaseConnectionServlet General Error: " + e.getMessage();
	    logger.log(Level.ERROR, msg);
	    throw new ServletException(msg);
	}
    }

    /**
     * Clean up instance and context variables. Close down all data source
     * provider connections.
     */
    public void destroy() {
	this.dbDriverClass = null;
	this.dbURL = null;
	this.dbUserID = null;
	this.dbPassword = null;
	this.systemData = null;
    }

    /**
     * Create a Connection and return to caller.
     * 
     * @return Connection
     * @throws SystemException Error establishing the database connection.
     */
    private Connection createConnection() throws SystemException {
	Connection con = null;
	try {
	    con = this.dbDriver.connect(this.dbURL, this.dbParms);
	    if (con == null) {
		con = DriverManager.getConnection(this.dbURL);
	    }
	    return con;
	}
	catch (SQLException e) {
	    throw new SystemException(RMT2SystemExceptionConst.MSG_DB_DOWN + " - " + e.getMessage());
	}
    }

    /**
     * Process Post/Get request
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	super.processRequest(request, response);
    }

    /**
     * Process the HTTP Get request.
     */
    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
	    this.processRequest(request, response);
	}
	catch (ServletException e) {
	    throw new ServletException(e.getMessage() + " - Could not process GET request");
	}
    }

    /**
     * Process the HTTP Post request.
     */
    public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
	    this.processRequest(request, response);
	}
	catch (ServletException e) {
	    throw new ServletException(e.getMessage() + " - Could not process Post request");
	}
    }
}