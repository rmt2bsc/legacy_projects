package com.api.db;


import org.apache.log4j.Logger;

import com.api.db.orm.DataSourceAdapter;

import com.api.security.pool.DatabaseConnectionPool;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2SystemExceptionConst;

import com.util.SystemException;


/**
 * Factory that is used to create a {@link DatabaseTransApi} objects.
 * 
 * @author roy.terrell
 *
 */
public class DatabaseTransFactory extends DataSourceAdapter {

    /**
     * Creates a DatabaseTransApi instance using the database pooling api.
     * 
     * @return  DatabaseTransApi
     */
    public static DatabaseTransApi create() {
        Logger logger = Logger.getLogger(DatabaseTransFactory.class);
        try {
            DatabaseConnectionBean dbConn = DatabaseConnectionPool.getAvailConnectionBean();
            if (dbConn == null) {
                logger.error(RMT2SystemExceptionConst.MSG_CONNECTION_INVALID);
                throw new SystemException(RMT2SystemExceptionConst.MSG_CONNECTION_INVALID);
            }
            DatabaseTransApi api = new DatabaseTransImpl(dbConn);
            return api;
        }
        catch (SystemException e) {
            throw e;
        }
        catch (DatabaseException e) {
            throw e;
        }
    }

    
    /**
     * Creates a DatabaseTransApi instance using a .properties file containing JDBC connection information.
     * <p>
     * The properties file is required to provide values for the following keys before a database connection 
     * is created:<br>
     * <ol>
     *   <li>dbdriver - class path of the JDBC driver</li>
     *   <li>dburl - The URL used to locate the DB server</li>
     *   <li>userId - The User id database credential</li>
     *   <li>password - The password to the database.</li>
     * </ol>
     * <br>
     * If the connection is going to be used with teh ORM api, the following properties must also be provided:
     * <ol>
     *   <li>SAXDriver - class path to the SAX parser</li>
     *   <li>datasource_dir - The directory where the ORM bean configuration is located.</li>
     * </ol>
     * 
     * @param propFile
     *          A .properties file residing in either the file system or the classpath containing the 
     *          information needed to establish a connection via native JDBC means.
     * @return An instance of DatabaseTransApi.
     */
    public static DatabaseTransApi create(String propFile) {
        Logger logger = Logger.getLogger(DatabaseTransFactory.class);
        try {
            JdbcFactory.setupSystemProperties(propFile);
    	    String dbUrl = null;
    	    DatabaseConnectionBean dbConn = JdbcFactory.getConnection(dbUrl, propFile);
            if (dbConn == null) {
                logger.error(RMT2SystemExceptionConst.MSG_CONNECTION_INVALID);
                throw new SystemException(RMT2SystemExceptionConst.MSG_CONNECTION_INVALID);
            }
            DatabaseTransApi api = new DatabaseTransImpl(dbConn);
            return api;
        }
        catch (SystemException e) {
            throw e;
        }
        catch (DatabaseException e) {
            throw e;
        }
    }


}