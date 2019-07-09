package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.api.DataProviderConnectionApi;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.JdbcDatabaseConnectionImpl;

import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2File;

/**
 * Factory used for creating {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} 
 * instances using a stand alone JDBC approach.
 * 
 * @author appdev
 *
 */
public class JdbcFactory {
    private static final Logger logger = Logger.getLogger(JdbcFactory.class);

    /**
     * Default constructor.
     */
    protected JdbcFactory() {
        return;
    }

    /**
     * Initializes the System properties for JDBC connections using the default SystemParms properties file.
     */
    public static void setupSystemProperties() {
        JdbcFactory.setupSystemProperties("SystemParms");
    }

    /**
     * Initializes the System properties for JDBC connections using a selected properties file containing 
     * unique configuration information.  Verify if we need to initialize system properties to locate XML 
     * datasource view documents for the ORM environemnt.  These properties include, <i>SAXDriver</i>, 
     * <i>webapps_drive</i>, <i>webapps_dir</i>, <i>app_dir</i>, and <i>datasource_dir</i>.  In most 
     * production scenarios, the initialization of these properties will be bypassed since they will have 
     * been already been set during web application startup.  Conversely, in the Test environment, the 
     * initialization logic will need to be executed.
     * 
     * @param configFile
     */
    public static void setupSystemProperties(String configFile) {
        // Test to see if ORM datasource configuration has been previously set.
        if (System.getProperty(HttpSystemPropertyConfig.PROPNAME_SAX_DRIVER) == null) {
            logger.log(Level.WARN, "ORM environment System properties were not loaded at server startup...will try to load from file configuration, " + configFile);
            try {
                System.setProperty(HttpSystemPropertyConfig.PROPNAME_SAX_DRIVER, RMT2File.getPropertyValue(configFile, HttpSystemPropertyConfig.PROPNAME_SAX_DRIVER));
                System.setProperty(HttpSystemPropertyConfig.PROPNAME_DATASOURCE_DIR, RMT2File.getPropertyValue(configFile, HttpSystemPropertyConfig.PROPNAME_DATASOURCE_DIR));
            }
            catch (Exception e) {
                // Do nothing...this is optional since establishing a database connection 
                // has nothing to do with any of the above properties.
            }
        }
    }

    /**
     * Obtains DatabaseConnectionBean instance which the database URL connection information is 
     * included in the SystemParms.properties file.
     * 
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} 
     */
    public static DatabaseConnectionBean getConnection() {
        DataProviderConnectionApi dbConApi = new JdbcDatabaseConnectionImpl();
        return getConnection(dbConApi);
    }

    /**
     * Obtains DatabaseConnectionBean instance by supplying the database URL.
     * 
     * @param url
     *          the database connection string.
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} 
     */
    public static DatabaseConnectionBean getConnection(String url) {
        DataProviderConnectionApi dbConApi = new JdbcDatabaseConnectionImpl(url);
        return getConnection(dbConApi);
    }

    /**
     * Obtains DatabaseConnectionBean instance by supplying the database URL and the 
     * name of properties file containing additional JDBC attributes needed to 
     * establish a database connection.
     * 
     * @param url
     *           The JDBC connection URL to override what is specified in <i>configFile</i>.
     *           This value can be null if there is no desire to override the configuration 
     *           DB URL. 
     * @param configFile
     *           The name of the properties file containing additional JDBC connection 
     *           attributes.
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     */
    public static DatabaseConnectionBean getConnection(String url, String configFile) {
        DataProviderConnectionApi dbConApi = new JdbcDatabaseConnectionImpl(url, configFile);
        return getConnection(dbConApi, configFile);
    }

    /**
     * Obtains DatabaseConnectionBean instance by supplying an instance of DataProviderConnectionApi.
     * 
     * @param dbConApi
     *          {@link com.api.DataProviderConnectionApi DataProviderConnectionApi}
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} 
     */
    private static DatabaseConnectionBean getConnection(DataProviderConnectionApi dbConApi) {
        JdbcFactory.setupSystemProperties();
        dbConApi.initConnections();
        DatabaseConnectionBean dbConn = (DatabaseConnectionBean) dbConApi.getDataProviderConnection();
        return dbConn;
    }

    /**
     * Obtains DatabaseConnectionBean instance by supplying an instance of DataProviderConnectionApi.
     * 
     * @param dbConApi
     *          {@link com.api.DataProviderConnectionApi DataProviderConnectionApi}
     * @param configFile
     *          The name of the properties file containing additional JDBC connection 
     *          attributes.        
     * @return {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} 
     */
    public static DatabaseConnectionBean getConnection(DataProviderConnectionApi dbConApi, String configFile) {
        JdbcFactory.setupSystemProperties(configFile);
        dbConApi.initConnections();
        DatabaseConnectionBean dbConn = (DatabaseConnectionBean) dbConApi.getDataProviderConnection();
        return dbConn;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        //	Connection con = test.getConnection();
        DatabaseConnectionBean conBean = JdbcFactory.getConnection("jdbc:sybase:Tds:rmtdaldev04:2638?ServiceName=framework");
        Connection con = conBean.getDbConn();
        try {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("select * from customers");
            if (rs != null) {
                while (rs.next()) {
                    String desc = rs.getString("surname");
                    System.out.println(desc);
                }
            }
            rs.close();
            stat.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
