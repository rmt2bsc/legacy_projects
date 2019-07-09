package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.SQLException;

import com.api.AbstractApiImpl;
import com.api.DataProviderConnectionApi;

import com.api.config.HttpSystemPropertyConfig;

import com.api.security.pool.AppPropertyPool;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2SystemExceptionConst;

import com.util.RMT2File;
import com.util.SystemException;

/**
 * Custom JDBC implementation of the api, {@link DataProviderConnectionApi}, which uses a 
 * non-JNDI approach to manage database connections.   This api provides its own connection 
 * pooling functionality.   This was the original verison of the database connection manager.
 * 
 * @author appdev
 *
 */
public class JdbcDatabaseConnectionImpl extends AbstractApiImpl implements DataProviderConnectionApi {

    private Logger logger;

    private boolean debugMode;

    private Vector<DatabaseConnectionBean> dbConnections;

    private String dbDriverClass;

    private String dbURL;

    private String dbUserID;

    private String dbPassword;

    private int maxDbCon; // Maximum number of DB Connections that can be open

    private int estDbCon; // Expected number of connections to be assigned to

    private int minDbCon; // Minimum number of connections that are assigned to

    private Properties systemData;

    private Properties dbParms;

    private Driver dbDriver;

    private String database;

    private String dbPropName;

    private String appName;

    private String urlOverride;

    /**
     * Default constructor 
     */
    public JdbcDatabaseConnectionImpl() throws DatabaseException, SystemException {
        super();
        this.start();
    }

    /**
     * Create a JdbcDatabaseConnectionImpl that is capable of establishing a database connection by URL
     * 
     * @param dbURL
     * @throws DatabaseException
     * @throws SystemException
     */
    public JdbcDatabaseConnectionImpl(String dbURL) throws DatabaseException, SystemException {
        this.initApi();
        this.urlOverride = dbURL;
        this.start();
    }

    /**
     * Create a JdbcDatabaseConnectionImpl that is capable of establishing a database 
     * connection by URL and a specified configuration file.
     * 
     * @param dbURL
     * @param config
     * @throws DatabaseException
     * @throws SystemException
     */
    public JdbcDatabaseConnectionImpl(String dbURL, String config) throws DatabaseException, SystemException {
        this.initApi();
        this.urlOverride = dbURL;
        this.start(config);
    }

    /**
     * Initializes the JDBC SQL driver using database realted property 
     * values stored in SystemParms.properties.
     * 
     * @throws DatabaseException 
     *            When the SystemParms.properties file is not found or 
     *            cannont be instantiated into a ResourceBundle.
     * @throws SystemException 
     *            When the following properties are not found or do 
     *            not have values in the SystemParms.properties:
     *                         <blockquote>
     *                         <uo>
     *                           <li>dbdriver - the JDBC Driver name</li>
     *                           <li>dburl - The URL of the database to establish a connection</li>
     *                           <li>userid - the database user id</li>
     *                           <li>password - the databae password</li>
     *                           <li>defaultconnections - the total number of default connections</li>
     *                        </uo>.
     *                        </blockquote>
     *                        Also, an exception is thrown when the total number of default connections is not a 
     *                        valid number.
     */
    public void initApi() throws DatabaseException, SystemException {
        this.dbDriverClass = null;
        this.dbURL = null;
        this.dbUserID = null;
        this.dbPassword = null;
        this.maxDbCon = 0;
        this.minDbCon = 0;
        this.estDbCon = 0;
        this.debugMode = false;
        this.urlOverride = null;

    }

    /**
     * Initializes the DatabaseConnectionPoolImpl instance using the default configuration 
     * .properties file, <i>SystemParms</i>.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    private void start() throws DatabaseException, SystemException {
	String appParmsPath = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APPPARMS_LOCATION);
        this.start(appParmsPath);
    }

    /**
     * Initializes the DatabaseConnectionPoolImpl instance using a specified configuration 
     * .properties file, <i>configFile</i>.
     * 
     * Initializes the DatabaseConnectionPoolImpl instance by gathering all of the data needed to 
     * establish connections, monitor the min and max number of required connections, and 
     * initilizing the database connection pool collective.
     * 
     * @param configFile
     * 		.properties file containing the JDBC configuration data needed to establish a DB 
     *          connection.  This resource can be loaded from either the file systems or the classpath.
     *          First, an attempt is made to load the properties file from the files system.   Upon 
     *          failure, the next attempt is to load the properties from the classpath.  
     * @throws DatabaseException
     * @throws SystemException
     *           When the <i>configFile</i> cannot be loaded
     */
    private void start(String configFile) throws DatabaseException, SystemException {
        Integer maxConnections = null;
        Integer minConnections = null;
        this.logger = Logger.getLogger("JdbcDatabaseConnectionImpl");

        try {
            // Get properties for SystemParm.properties.
            if (this.systemData == null) {
        	try {
        	    // Try to create Properties instance from a .properties file residing somewhere in the file system.
        	    this.systemData = RMT2File.loadPropertiesObject(configFile);    
        	}
        	catch (Exception e) {
        	    // try to create Properties instance from .properties file residing somewhere in the java classpath.
        	    try {
        		ResourceBundle rb = RMT2File.loadAppConfigProperties(configFile);
            	    	this.systemData = RMT2File.convertResourceBundleToProperties(rb);
        	    }
        	    catch (Exception ee) {
        		this.msg = "JDBC Connection configuration could not be loaded.  Proerties file, " + configFile + ", could not be found in file system or classpath";
        		this.logger.error(this.msg);
        		throw new SystemException(this.msg);
        	    }
        	}
                
                this.dbDriverClass = this.systemData.getProperty("dbdriver");
                this.dbURL = this.systemData.getProperty("dburl");
                if (this.urlOverride != null) {
                    this.dbURL = this.urlOverride;
                }
                this.dbUserID = this.systemData.getProperty("userid");
                this.dbPassword = this.systemData.getProperty("password");
                this.database = this.systemData.getProperty("database");
                this.dbPropName = this.systemData.getProperty("dbPropertyName");
                String num = this.systemData.getProperty("defaultconnections");
                if (num != null) {
                    num = num.trim();
                    maxConnections = new Integer(num);
                }
                num = this.systemData.getProperty("minconnections");
                if (num != null) {
                    num = num.trim();
                    minConnections = new Integer(num);
                }
                if (minConnections == 0) {
                    this.minDbCon = 0;
                }
                else {
                    this.minDbCon = minConnections;
                }
                this.appName = this.systemData.getProperty("appcode");

                // Validate essential database variables
                if (this.dbDriverClass == null) {
                    this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_NULL_DB_DRIVER);
                    throw new SystemException(RMT2SystemExceptionConst.MSG_NULL_DB_DRIVER);
                }
                if (this.dbURL == null) {
                    this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_NULL_DB_URL);
                    throw new SystemException(RMT2SystemExceptionConst.MSG_NULL_DB_URL);
                }
                if (this.dbUserID == null) {
                    this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_NULL_DB_USERID);
                    throw new SystemException(RMT2SystemExceptionConst.MSG_NULL_DB_USERID);
                }
                if (this.dbPassword == null) {
                    this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_NULL_DB_PASSWORD);
                    throw new SystemException(RMT2SystemExceptionConst.MSG_NULL_DB_PASSWORD);
                }
                if (maxConnections == null || maxConnections.intValue() == 0) {
                    this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_NULL_CONNECTION_COUNT);
                    throw new SystemException(RMT2SystemExceptionConst.MSG_NULL_CONNECTION_COUNT);
                }
                this.maxDbCon = maxConnections.intValue();
                // Setup Database Driver Object and its parameters.
                this.dbDriver = (Driver) Class.forName(this.dbDriverClass).newInstance();
                this.dbParms = new Properties();
                this.dbParms.setProperty("user", this.dbUserID);
                this.dbParms.setProperty("password", this.dbPassword);
                if (database != null) {
                    this.dbParms.setProperty(dbPropName, database);
                }
            }

            // Setup the connections for each dbConn
            if (this.debugMode) {
                this.estDbCon = 1;
            }
            else {
                this.estDbCon = this.minDbCon;
            }

            // Create database pool collective
            this.dbConnections = new Vector<DatabaseConnectionBean>();
        } // end try
        catch (NumberFormatException e) {
            throw new SystemException(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            throw new DatabaseException(e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
        catch (InstantiationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Initialize the database Connection Collective which debug mode is disabled by default.
     * 
     * @return Total number of connections created.
     * @throws SystemException General SQL errors
     */
    public synchronized int initConnections() throws SystemException {
        DatabaseConnectionBean conBean;
        // Create connections
        for (int ndx = 0; ndx < this.estDbCon; ndx++) {
            conBean = this.createPoolElementInstance();
            this.addConnectionObj(conBean);
        }
        return this.dbConnections.size();
    }

    /**
     * Initializes database connections using _switch to enable/disable debug mode.
     * 
     * @param _switch tru enables debug mode and false disables debug mode.
     * @return Total number of connections created.
     * @throws SystemException General SQL errors
     */
    public synchronized int initConnections(boolean _switch) throws SystemException {
        this.debugMode = _switch;
        return this.initConnections();
    }

    /**
     * Create a Connection and return to caller.
     * 
     * @return Connection
     * @throws SystemException Error establishing the database connection.
     */
    protected synchronized Connection createConnection() throws SystemException {
        Connection con = null;

        // Check if you are trying to exceed that maximum
        // allowable DB Connections to be assigned to a session.
        if (this.getInUseCount() == this.maxDbCon) {
            this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_MAX_DBASSIGN_EXCEEDED);
            throw new SystemException(RMT2SystemExceptionConst.MSG_MAX_DBASSIGN_EXCEEDED);
        }
        try {
            con = this.dbDriver.connect(this.dbURL, this.dbParms);
            if (con == null) {
                con = DriverManager.getConnection(this.dbURL);
            }
            return con;
        }
        catch (SQLException e) {
            this.msg = RMT2SystemExceptionConst.MSG_DB_DOWN + " - " + e.getMessage();
            this.logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
    }

    /**
     * Adds a {@link DatabaseConnectionBean} to the database collective.
     * 
     * @param conBean DatabaseConnectionBean
     */
    protected void addConnectionObj(DatabaseConnectionBean conBean) {
        // if the connection vector has not been initalize
        // then initialize with "InitalCount"
        if (this.dbConnections == null) {
            this.dbConnections = new Vector<DatabaseConnectionBean>();
        }
        // Add the element to the connection collective
        this.dbConnections.addElement(conBean);
    }

    /**
     * Get connection collective object.
     * 
     * @return Vector
     */
    public Vector<DatabaseConnectionBean> getConnectionCollective() {
        return this.dbConnections;
    }

    /**
     * Count the total number of database connections that are in use
     * 
     * @return The number of in-use connections.
     */
    public int getInUseCount() {
        DatabaseConnectionBean dbConn;
        int inUseCount = 0;

        // If this is the first connection to be added to the DB connection
        // pool, then "dbConnections" may be null, hence return 0.
        if (this.dbConnections == null) {
            return 0;
        }

        // Tally all connections that are currently in use
        for (int ndx = 0; ndx < dbConnections.size(); ndx++) {
            dbConn = (DatabaseConnectionBean) dbConnections.elementAt(ndx);
            if (dbConn.getInUse()) {
                inUseCount++;
            }
        }
        return inUseCount;
    }

    /**
     * Creates a DatabaseConnectionBean instance an initializes it with the data 
     * it needs to exist as an element in the connection pool.
     * 
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @throws SystemException 
     *            A DatabaseConnectionBean instance failed to be created or the 
     *            connection pool collective is null or invalid.
     */
    private DatabaseConnectionBean createPoolElementInstance() throws SystemException {
        DatabaseConnectionBean dbConn;
        try {
            dbConn = new DatabaseConnectionBean();
        }
        catch (Exception e) {
            throw new SystemException("Problem discovered during the process of creating and initializing database connection bean");
        }
        int ndx = 0;
        if (dbConnections == null) {
            throw new SystemException("Connection pool collective is invalid");
        }

        // Initialize to properties of dbConn and add to the DBConnection Collective
        ndx = dbConnections.size();
        dbConn.setDbConn(this.createConnection());
        String name = (this.appName == null ? dbConn.getName() : dbConn.getName());
        dbConn.setName(name + new Integer(ndx).toString());
        return dbConn;
    }

    /**
     * Probes the connection pool to identify and replace stale or closed connections 
     * with valid open connections.  Both in-use and out-of-use stale connections are 
     * corrected.  The need for this implementation was first discovered when using 
     * Sybase Adaptive Server Anywhere RDBMS which seems to timeout connections after
     * a certain period of inactivity.
     * 
     * @return The total number of connections recovered.
     * @throws SystemException 
     *            A problem occurred checking the status of the connection or an attempt 
     *            to obtain a connection outside of the range of the pool.
     */
    public int recoverStaleConnections() throws SystemException {
        int recoveryCount = 0;
        DatabaseConnectionBean dbConn;

        try {
            if (this.dbConnections != null) {
                for (int ndx = 0; ndx < this.dbConnections.size(); ndx++) {
                    dbConn = (DatabaseConnectionBean) this.dbConnections.elementAt(ndx);
                    // If Connection object is stale or closed then remove from pool 
                    // and establish a new one.
                    try {
                        if (dbConn.getDbConn().isClosed()) {
                            dbConn = this.createPoolElementInstance();
                            this.dbConnections.setElementAt(dbConn, ndx);
                            recoveryCount++;
                        }
                    }
                    catch (SQLException e) {
                        this.msg = "Could check the status of the Connection before retrieving from the Pool:  " + e.getMessage();
                        this.logger.log(Level.ERROR, this.msg);
                        throw new SystemException(this.msg);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        this.msg = "Unable to recover db connection fetch at position, " + ndx + " due to ArrayIndexOutOfBoundsException";
                        this.logger.log(Level.ERROR, this.msg);
                        throw new SystemException(this.msg);
                    }
                } // end for
            } // end if

            return recoveryCount;
        } // end try
        catch (SystemException e) {
            throw e;
        }
        finally {
            // Display revocery message regardless of the outcome of this method.
            this.msg = "Total number of stale connections found and recovered in Connection Pool: " + recoveryCount;
            this.logger.log(Level.INFO, this.msg);
        }
    }

    /**
     * Gets the next available database connection bean object. If no object is found with a 
     * "not in use" status, then create one and add to the collective.
     * 
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} disguised as an Object.
     * @throws SystemException Problem obtaining database connection bean.
     */
    public synchronized Object getDataProviderConnection() throws SystemException {
        DatabaseConnectionBean dbConn;
        String appName = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);

        // Find an available connection that is not in use
        if (this.dbConnections != null) {
            for (int ndx = 0; ndx < this.dbConnections.size(); ndx++) {
                dbConn = (DatabaseConnectionBean) this.dbConnections.elementAt(ndx);
                if (dbConn.getInUse() == false) {
                    logger.log(Level.DEBUG, "Borrowing database connection #" + ndx + " for current transaction in application, " + appName);
                    return dbConn;
                }
            }
        }

        // At this point, not connections are available.  Create one.
        dbConn = this.createPoolElementInstance();
        dbConn.setInUse(true);
        this.addConnectionObj(dbConn);
        logger.log(Level.DEBUG, "Created a new database connection for this transaction and added to the pool in application, " + appName);
        return dbConn;
    }

    /**
     * Releases the database connection object and makes it available for reuse.
     * 
     * @param obj The database connection bean to release.
     * @return true for success and false if release failed. 
     */
    public synchronized boolean releaseConnection(Object obj) {
        DatabaseConnectionBean dbConn = (DatabaseConnectionBean) obj;

        // Set the InUse property of the current DBConnection object to
        // false which makes it available for reuse.
        try {
            dbConn.setInUse(false);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Closes a database connection bean object so that all of its allocated resources will be released.
     * 
     * @param obj The database connection bean to close
     * @return true for success and false if connection could not be closed failed.
     * @throws SystemException
     */
    public synchronized boolean closeConnection(Object obj) throws SystemException {
        DatabaseConnectionBean dbConn = (DatabaseConnectionBean) obj;
        try {
            dbConn.getDbConn().close();
        }
        catch (Exception e) {
            throw new SystemException(e.getMessage());
        }

        return true;
    }

    /**
     * Gets the SQL driver
     */
    public Object getDataProviderDriver() {
        return this.dbDriver;
    }

    /**
     * Gets the application name.
     */
    public String getAppName() {
        return this.appName;
    }
} // End of Class

