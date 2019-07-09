package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.AbstractApiImpl;

import com.api.db.DatabaseException;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.constants.RMT2SystemExceptionConst;
import com.controller.Request;

import com.util.SystemException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This implementation of DatabaseTransApi provides basic database transactional 
 * functionality.
 * 
 * @author roy.terrell
 * 
 */
public class DatabaseTransImpl extends AbstractApiImpl implements DatabaseTransApi {
    private static Logger logger = Logger.getLogger("DatabaseTransImpl");

    protected Connection con = null;

    /** The JDBC Statement object */
    protected Statement stmt = null;

    /** The JDBC ResultSet object */
    protected ResultSet rs = null;

    /** Accumulates the total number rows effected by a SQL transaction */
    protected int dbChangeCount;

    /** The Database Connectin Bean wrapper */
    protected DatabaseConnectionBean connector;

    /**  User's login id  */
    protected String loginId;

    /**
     * Default constructor
     *
     */
    public DatabaseTransImpl() {
        super();
        logger.log(Level.DEBUG, "Starting constructor, DatabaseTransImpl()");
    }

    /**
     * Initializes DatabaseTransImpl object using {@link com.bean.db.DatabaseConnectionBean}
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public DatabaseTransImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
        super();
        logger.log(Level.DEBUG, "Database Transaction initialized with a ConnectionBean only");
        if (dbConn == null) {
            throw new SystemException("Api Could not be created due to invalid database object");
        }
        connector = dbConn;
        this.con = dbConn.getDbConn();
        return;
    }

    /**
     * Initializes DatabaseTransImpl object using a Connection object and the loginId.
     * 
     * @param con
     * @param loginId
     * @throws DatabaseException
     * @throws SystemException
     */
    public DatabaseTransImpl(Connection con, String loginId) throws DatabaseException, SystemException {
        this();
        logger.log(Level.DEBUG, "Database Transaction initialized with a JDBC Connection and User's ogin id");
        this.con = con;
        this.loginId = loginId;
        this.verifyInstance();
    }

    /**
     * Initializes DatabaseTransImpl object using a Connection object and 
     * the Request object.
     * 
     * @param dbConn
     * @param req
     * @throws DatabaseException
     * @throws SystemException
     */
    public DatabaseTransImpl(DatabaseConnectionBean dbConn, Request req) throws DatabaseException, SystemException {
        super(req);
        logger.log(Level.DEBUG, "Database Transaction initialized with a ConnectionBean and User's Request");
        this.connector = dbConn;
        this.con = dbConn.getDbConn();
        this.verifyInstance();
        return;
    }

    /**
     * Initializes the connection object and the statement object that is to 
     * be used with this class.
     */
    protected void initApi() throws DatabaseException, SystemException {
        logger.log(Level.DEBUG, "Starting initApi()");
        this.stmt = null;
        this.rs = null;
        this.connector = null;
        this.dbChangeCount = 0;
    } // End init

    /**
     * Verifies that the connection object has been properly initialized.
     * 
     * @throws DatabaseException  General database errors
     * @throws DatabaseConnectionClosedException Connection is closed
     * @throws SystemException If the connection object is null or invalid.
     */
    private void verifyInstance() throws DatabaseException, SystemException {
        logger.debug("Verifying ConnectionBean Instance");
        try {
            if (this.con == null) {
                this.msg = RMT2SystemExceptionConst.MSG_CONNECTION_INVALID + " for user: " + this.loginId;
                logger.log(Level.ERROR, msg);
                throw new SystemException(this.msg);
            }

            logger.log(Level.DEBUG, "Verify if database connection is open or closed");
            if (this.con.isClosed()) {
                this.msg = "Database connection is closed.  \n" + this.connector.getInfo();
                logger.log(Level.ERROR, this.msg);
                throw new DatabaseConnectionClosedException(this.msg);
            }
            logger.log(Level.DEBUG, "Before setting auto commit flag for connection");
            this.con.setAutoCommit(false);
            logger.log(Level.DEBUG, "After setting auto commit flag for connection");
            this.dbChangeCount = 0;
            logger.debug("ConnectionBean instance was verified successfully");
        }
        catch (SQLException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, msg);
            throw new DatabaseException(this.msg);
        }
    }

    /**
     * Rollback database transaction and resets the change count to zero.
     * 
     * @return 1 for success and -1 for failure
     */
    public int rollbackUOW() {
        try {
            if (this.con != null && !this.con.isClosed()) {
                this.con.rollback();
                this.dbChangeCount = 0;
            }
            return 1;
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            return -1;
        }
    }

    /**
     * Commits database transaction and resets the change count to zero.
     * 
     * @return 1 for success and -1 for failure
     */
    public int commitUOW() {
        try {
            if (this.con != null && !this.con.isClosed()) {
                this.con.commit();
                this.dbChangeCount = 0;
            }
            return 1;
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            return -1;
        }
    }

    /**
     * Release typical JDBC database resources such as the Statement and the 
     * ResultSet objects. 
     * 
     * @throws DatabaseException
     */
    public void close() {
        logger.info("Releasing database transaction object");
        try {
            if (rs != null) {
                this.rs.close();
            }
            if (this.stmt != null) {
                this.stmt.close();
            }
            if (this.connector != null) {
                this.connector.close();
                this.connector = null;
            }
            else {
                logger.info("Database connection for this transaction object has already been released by the client");
            }
            this.release();
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DatabaseException("JDBC SQL Exception occurred", e);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DatabaseException("A General error occurred while trying to release transaction object", e);
        }

    } // End close

    /**
     * Returns the the connection bean to the connection pool by disassociating
     * the connection from the user's session which in turn will trigger the
     * valueUnbound method of DatabaseConnectionBean.
     * 
     */
    private void release() {
        if (this.request == null) {
            return;
        }
        this.request.getSession().removeAttribute(RMT2ServletConst.DB_CONNECTION_BEAN);
        //	connector = null;
    }

    /**
     * {@inheritDoc}
     */
    public final Object getConnector() {
        return connector;
    }

    /**
     * Sets the connectin bean wrapper object.  The connection is set to null if 
     * value is of a type other than DatabaseConnectionBean.
     * 
     * @param value {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     */
    public void setConnector(Object value) {
        if (value instanceof DatabaseConnectionBean) {
            this.connector = (DatabaseConnectionBean) value;
        }
        else {
            this.connector = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Connection getConnection() {
        Connection goodCon = null;
        try {
            if (this.con.isClosed()) {
                if (this.connector.getDbConn().isClosed()) {
                    this.msg = "Connection is closed";
                    logger.log(Level.ERROR, this.msg);
                    throw new SystemException(this.msg);
                }
                goodCon = this.connector.getDbConn();
            }
            else {
                goodCon = this.con;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return goodCon;
    }

} // end class

