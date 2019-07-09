package com.api.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.DataProviderConnectionApi;

import com.api.security.pool.AppPropertyPool;
import com.api.security.pool.DatabaseConnectionPool;

import com.util.RMT2BeanUtility;
import com.util.SystemException;

/**
 * Configures one or more JDBC resources for a given application.   Each application must implement the 
 * {@link com.api.DataProviderConnectionApi DataProviderConnectionApi} api in order for this process to 
 * recoginze and setup database connections.
 *    
 * @author RTerrell
 *
 */
public class JdbcConfig extends AbstractEnvironmentConfig implements ResourceConfigurator {
    private static Logger logger = Logger.getLogger("JdbcConfig");

    private String appCode;

    private DataProviderConnectionApi dbConApi;

    /**
     * Creates a JdbcConfig object.
     */
    public JdbcConfig() throws ConfigException {
        super();
        if (!AppPropertyPool.isAppConfigured()) {
            this.msg = "Failed to initialize " + this.getClass().getName() + ": application properties are not loaded";
            logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }
        String appCode = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
        if (appCode == null) {
            this.msg = "Failed to initialize " + this.getClass().getName() + ": application property, \"" + HttpSystemPropertyConfig.PROPNAME_APP_NAME + "\", was not found";
            logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }
        this.init(appCode);
    }

    /**
     * Creates an instance of {@link com.api.DataProviderConnectionApi DataProviderConnectionApi}.  This 
     * is where you would plug in a specific implementation of the DataProviderConnectionApi for the 
     * purpose of configuring the application JDBC connections.
     *   
     * @param initCtx 
     *           String value representing the application code
     * @throws ConfigException 
     *           When the application code cannot be identified or is invalid, general database 
     *           errros or system errors.
     */
    public void init(Object initCtx) throws ConfigException {
        this.appCode = (String) initCtx;
        if (this.appCode == null) {
            this.msg = "Failed to initialize " + this.getClass().getName() + ": init method parameter is invalid";
            logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }

        // Use JNDI to obtain class name for database connection factory from web.xml
        String dbConnectionFactory = null;
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            // Be sure this is setup in the web.xml of the application.
            dbConnectionFactory = (String) env.lookup("DB_CONNECTION_FACTORY");
        }
        catch (NamingException e) {
            throw new ConfigException(e);
        }

        // Validate database connection factory
        if (dbConnectionFactory == null) {
            this.msg = "Factory for database connection is not defined in configuration";
            logger.error(this.msg);
            throw new ConfigException(this.msg);
        }

        // This is where you plug in the specific implementation of the database connection api. 
        // The c onfiguration should exist in the application's web.xml file.
        RMT2BeanUtility beanUtil = new RMT2BeanUtility();
        try {
            this.dbConApi = (DataProviderConnectionApi) beanUtil.createBean(dbConnectionFactory);    
        }
        catch (Throwable e) {
            throw new SystemException(e);
        }
        
    }

    /**
     * Create database connections pertaining to the application's data source.  
     *  
     * @throws ConfigException
     */
    public void doSetup() throws ConfigException {
        try {
            logger.log(Level.INFO, "API Created");
            logger.log(Level.INFO, "Begin creating connections");
            this.dbConApi.initConnections();
            logger.log(Level.INFO, "Connections created");
            // Assign database connection api to the connection pool manager
            DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
            pool.addConnectionApi(this.dbConApi);
            logger.log(Level.INFO, "Database Connection Pool created!");
        }
        catch (SystemException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ConfigException(e.getMessage());
        }
        catch (Exception e) {
            this.msg = "Jdbc Configurator General Error: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }
        finally {
            logger.log(Level.INFO, "Database configuration for application, " + AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME) + ", is completed");
        }
    }

    /* (non-Javadoc)
     * @see com.api.config.ResourceConfigurator#postSetup()
     */
    public void doPostSetup(Object ctx) throws ConfigException {
        return;
    }

    /* (non-Javadoc)
     * @see com.api.config.ResourceConfigurator#getAppProperty(java.lang.String)
     */
    public String getProperty(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.config.ResourceConfigurator#getEnv()
     */
    public String getEnv() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.config.ResourceConfigurator#destroy()
     */
    public void destroy() throws ConfigException {
        // TODO Auto-generated method stub

    }

    /**
     * Always returns null.
     * 
     * @return null.
     */
    public Properties getAppProps() {
        return null;
    }

}
