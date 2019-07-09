package com.bean.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.DataSourceApi;

import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceFactory;

import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2BaseBean;
import com.bean.RMT2TagQueryBean;

import com.util.NotFoundException;
import com.util.SystemException;

/**
 * Class that serves as a wrapper for a JDBC Database Connection object.  Also responsible for retrieving SQL 
 * Datasource (sql and xml).
 * 
 * @author appdev
 *
 */
public class DatabaseConnectionBean extends RMT2BaseBean implements Serializable {
    private static final long serialVersionUID = -2122518924309758033L;

    private Connection dbConn;

    private String dbDriverClass;

    private boolean inUse;

    private String name;

    private String dbUserId;

    private String dbPassword;

    private String dbms;

    private String dbmsVersion;

    private String dbURL;

    private String loginId;

    private String driverName;

    private int driverMinorVerNo;

    private int driverMajorVerNo;

    private Logger logger;

    ////////////////////////////////////
    //          Constructor
    ////////////////////////////////////
    public DatabaseConnectionBean() throws SystemException {
        super();
        logger = Logger.getLogger("DatabaseConnectionBean");
    }

    /**
     * Creates a sq or xml ObjectMapperAttrib.  The variable,  querySource, can be the filename of an XML Datasource or 
     * it can be a SQL select Statement.  The value of queryType must be either "sql"  or "xml".   The where clause and/or  order by 
     * clause will be appended to the existing where and order by clauses at the time of  object instantiation.  
     * The arguments: querySource, queryType, whereClause, and orderByClause must be packaged as a RMT2TagQueryBean 
     * object and the RMT2TagQueryBean object passed as an input parameter to RMT2DataSourceApiImpl constructor.
     * 
     * @param querySource
     * @param queryType
     * @param whereClause
     * @param orderByClause
     * @return
     * @throws NotFoundException
     * @throws DatabaseException
     * @throws SystemException
     * @deprecated Use {@link DataSourceFactory#create(DatabaseConnectionBean, RMT2TagQueryBean)}
     */
    public DataSourceApi getDataObject(String querySource, String queryType, String whereClause, String orderByClause) throws NotFoundException, DatabaseException, SystemException {
        RMT2TagQueryBean queryData = new RMT2TagQueryBean(querySource, queryType, whereClause, orderByClause);
        DataSourceApi api = DataSourceFactory.create(this, queryData);
        return api;
    }

    /**
     * Creates a XML ObjectMapperAttrib object.  The variable, dataSourceName, must be the 
     * filename of an XML Datasource which will serve as the only input argument for constructing 
     * a RMT2DataSourceApiImpl class. A RMT2TagQueryBean object must be created using querySource 
     * and the literals "xml", null, and null in order to create a datasource.object derived from a 
     * XML datasource  document.  The RMT2TagQueryBean object is passed as an input parameter  to 
     * RMT2DataSourceApiImpl constructor.
     * 
     * @param dataSourceName
     * @return
     * @throws NotFoundException
     * @throws DatabaseException
     * @throws SystemException
     * @deprecated Use {@link DataSourceFactory#create(DatabaseConnectionBean, String)}
     */
    public DataSourceApi getDataObject(String dataSourceName) throws NotFoundException, DatabaseException, SystemException {
        DataSourceApi api = DataSourceFactory.create(this, dataSourceName);
        return api;
    }

    /**
     * Ensures that the internal connection is marked "In Use".
     *
     */
    public void open() {
        String appName = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
        this.setInUse(true);
        String msg = "+++++++ A database connection was obtained for application, " + appName + ".  Database Connection hash code: " + this.dbConn.hashCode();
        logger.log(Level.INFO, msg);
        return;
    }

    /**
     * Ensures that the database connection is closed.  When managing connections via the straight JDBC 
     * approach, the connection is physically closed.  When a database connection pool is employed, the 
     * connection is returned to the pool for reuse 
     *
     */
    public void close() {
        String appName = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
        //  Make this DBConnectionBean available for use
        String msg = null;
        this.inUse = false;
        this.loginId = null;
        try {
            if (!this.dbConn.isClosed()) {
                msg = "------- Returning DB connection to connection pool for application, " + appName + ".  Database Connection has been closed.  Connection hash code: "
                        + this.dbConn.hashCode();
                logger.log(Level.INFO, msg);
                this.dbConn.close();
            }
        }
        catch (SQLException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msgArgs);
            e.printStackTrace();
            throw new SystemException(msg);
        }
    }

    /**
     * Gets the JDBC Connection object.
     * @return Connection
     */
    public Connection getDbConn() {
        return this.dbConn;
    }

    /**
     * Sets the JDBC Connection object.
     * @param value Connection
     */
    public void setDbConn(Connection value) {
        this.dbConn = value;
    }

    public String getDbDriverClass() {
        return this.dbDriverClass;
    }

    public void setDbDriverClass(String value) {
        this.dbDriverClass = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDbms() {
        return this.dbms;
    }

    public void setDbms(String value) {
        this.dbms = value;
    }

    public String getDbmsVersion() {
        return this.dbmsVersion;
    }

    public void setDbmsVersion(String value) {
        this.dbmsVersion = value;
    }

    public String getDbUrl() {
        String val = null;
        if (this.dbURL == null) {
            if (this.dbConn != null) {
                try {
                    val = this.dbConn.getMetaData().getURL();
                }
                catch (SQLException e) {
                    val = "DB URL could not be obtained from meta data";
                }
            }
            else {
                val = "DB URL information is N/A";
            }
        }
        else {
            val = this.dbURL;
        }
        return val;
    }

    public void setDbUrl(String value) {
        this.dbURL = value;
    }

    public String getDbUserId() {
        return this.dbUserId;
    }

    public void setDbUserId(String value) {
        this.dbUserId = value;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String value) {
        this.loginId = value;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public void setInUse(boolean value) {
        this.inUse = value;
    }

    public boolean getInUse() {
        return this.inUse;
    }

    public int getDriverMajorVerNo() {
        return driverMajorVerNo;
    }

    public void setDriverMajorVerNo(int driverMajorVerNo) {
        this.driverMajorVerNo = driverMajorVerNo;
    }

    public int getDriverMinorVerNo() {
        return driverMinorVerNo;
    }

    public void setDriverMinorVerNo(int driverMinorVerNo) {
        this.driverMinorVerNo = driverMinorVerNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void initBean() throws SystemException {

        try {
            this.inUse = false;
            this.name = "DBConn";
            return;
        }
        catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    /**
     * Returns information about the connection as String delimited by line breaks
     * 
     * @return String
     */
    public String getInfo() {
        StringBuffer details = new StringBuffer();
        details.append("Connection name=");
        details.append(this.name);
        details.append("\n");
        details.append("Connected User Id=");
        details.append(this.dbUserId);
        details.append("\n");
        return details.toString();
    }
} // End of Class