package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import com.api.AbstractApiImpl;

import com.api.DataProviderConnectionApi;

import com.api.config.HttpSystemPropertyConfig;

import com.api.security.pool.AppPropertyPool;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2SystemExceptionConst;

import com.util.RMT2File;
import com.util.SystemException;

/**
 * A JNDI implementation of the api, {@link DataProviderConnectionApi}, for managing database connections.  
 * This api expects that the connections are container managed and uses the connection pooling that is 
 * provided by the container.
 * 
 * @author appdev
 *
 */
public class JndiDatabaseConnectionImpl extends AbstractApiImpl implements DataProviderConnectionApi {

    private Logger logger;

    private int maxDbCon; // Maximum number of DB Connections that can be open

    private int minDbCon; // Minimum number of connections that are assigned to

    private String appName;

    private String ctxName;

    /**
     * Default constructor 
     */
    public JndiDatabaseConnectionImpl() throws DatabaseException, SystemException {
        super();
        this.start();
    }

    public JndiDatabaseConnectionImpl(String ds) throws DatabaseException, SystemException {
        super();
        this.ctxName = ds;
        this.start();
    }

    /**
     * Performs additional initialization for this instance.
     * 
     * @throws DatabaseException
     * @throws SystemException 
     */
    public void initApi() throws DatabaseException, SystemException {
        this.maxDbCon = 0;
        this.minDbCon = 0;
    }

    /**
     * Initializes the DatabaseConnectionPoolImpl instance by gathering all of the data needed to 
     * establish default number of connections, monitor the min and max number of required connections, 
     * and initilizing the database connection pool collective.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected void start() throws DatabaseException, SystemException {
        this.logger = Logger.getLogger("JndiDatabaseConnectionImpl");

        // Get properties from the system configurator.
        String temp = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_DEF_CONN);
        try {
            this.minDbCon = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.minDbCon = 1;
        }

        temp = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_MAX_CONN);
        try {
            this.maxDbCon = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.maxDbCon = 2;
        }

        // Get datasource context name from AppParms.properties
        String appParmsPath = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APPPARMS_LOCATION);
        Properties props = RMT2File.loadPropertiesObject(appParmsPath);
        this.ctxName = props.getProperty("dbDataSource");
    }

    /**
     * Creates the default number of database connections.
     * 
     * @return int 
     *          Total number of connections created.
     * @throws SystemException 
     *          General SQL errors
     */
    public synchronized int initConnections() throws SystemException {
        // Create connections
        List<Connection> conList = new ArrayList<Connection>();
        try {
            if (this.minDbCon > 0) {
                Context ctx = new InitialContext();
                Context env = (Context) ctx.lookup("java:comp/env");
                for (int ndx = 0; ndx < this.minDbCon; ndx++) {
                    DataSource ds = (DataSource) env.lookup(this.ctxName);
                    Connection con = ds.getConnection();
                    conList.add(con);
                }
                for (int ndx = 0; ndx < conList.size(); ndx++) {
                    Connection con = conList.get(ndx);
                    con.close();
                }
            }
            return conList.size();
        }
        catch (NamingException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        catch (SQLException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(e);
        }
    }

    /**
     * Initializes database connections using _switch to enable/disable debug mode.
     * 
     * @param flag 
     *          boolean which true enables debug mode and false disables debug mode.
     * @return int
     *          Total number of connections created.
     * @throws SystemException 
     *          General SQL errors
     */
    public synchronized int initConnections(boolean flag) throws SystemException {
        return this.initConnections();
    }

    /**
     * Create a Connection and return to caller.
     * 
     * @return Connection
     * @throws SystemException Error establishing the database connection.
     */
    protected synchronized Connection createConnection() throws SystemException {
        // Check if you are trying to exceed that maximum allowable DB Connections to be assigned to a session.
        // This should always return false since in use count will always equal zero.
        if (this.getInUseCount() == this.maxDbCon) {
            this.logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_MAX_DBASSIGN_EXCEEDED);
            throw new SystemException(RMT2SystemExceptionConst.MSG_MAX_DBASSIGN_EXCEEDED);
        }

        Connection con = null;
        try {
            // Obtain JDBC Connection via DataSource
            Context ctx;
            ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            DataSource jdbcDs = (DataSource) env.lookup(this.ctxName);
            jdbcDs = (DataSource) env.lookup(this.ctxName);
            if (jdbcDs == null) {
                this.msg = "JNDI lookup return invalid JDBC DataSource reference";
                this.logger.log(Level.ERROR, this.msg);
                throw new SystemException(this.msg);
            }
            con = jdbcDs.getConnection();
            if (con == null) {
                this.msg = "Unable to obtain JDBC connection via DataSource JNDI configuration";
                this.logger.log(Level.ERROR, this.msg);
                throw new SystemException(this.msg);
            }
            return con;
        }
        catch (SQLException e) {
            this.msg = RMT2SystemExceptionConst.MSG_DB_DOWN + " - " + e.getMessage();
            this.logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        catch (NamingException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
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
        DatabaseConnectionBean dbConn = null;
        try {
            dbConn = new DatabaseConnectionBean();
        }
        catch (Exception e) {
            throw new SystemException("Problem discovered during the process of creating and initializing database connection bean");
        }

        // Initialize to properties of dbConn and add to the DBConnection Collective
        dbConn.setDbConn(this.createConnection());
        // Initialize name property of dbConn.  Format will be <app name> + <date long value> or <connection name> + <date long value>.
        String seq = String.valueOf(new Date().getTime());
        String name = (this.appName == null ? dbConn.getName() + seq : this.appName + seq);
        dbConn.setName(name);
        logger.log(Level.DEBUG, "Created a new database connection for this transaction and added to the pool in application, " + appName);
        return dbConn;
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
     * Gets the application name.
     */
    public String getAppName() {
        return this.appName;
    }

    /**
     * Stubbed.
     * 
     * @return int
     *           Always returns zero.
     * @throws SystemException 
     */
    public int recoverStaleConnections() throws SystemException {
        return 0;
    }

    /**
     * Stubbed
     * 
     * @return Always returns null;
     */
    public Vector<Object> getConnectionCollective() {
        return null;
    }

    /**
     * Stubbed.
     * 
     * @return int
     *          Always returns 0
     */
    public int getInUseCount() {
        return 0;
    }

    /**
     * Stubbed.
     * 
     * @param obj 
     *           The database connection bean to release.
     * @return boolean
     *           Always returns true. 
     */
    public synchronized boolean releaseConnection(Object obj) {
        return true;
    }

    /**
     * Stubbed.
     * 
     * @return Always returns null since driver is container managed. 
     */
    public Object getDataProviderDriver() {
        return null;
    }
} // End of Class

