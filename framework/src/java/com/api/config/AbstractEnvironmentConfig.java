package com.api.config;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bean.RMT2Base;

/**
 * This class provides base functionality to obtain environment related properties 
 * for a given application.  Currently, it manages data pertaining to application 
 * environment, configuration location, application name, and the server which the 
 * application is governed.  All information is accessed using JNDI and requires the 
 * user to setup the proper resources for successful operation.  
 * <p>
 * To properly identify the current environment in which the applicaltion is running,
 * the user must setup a resource identifying environment as "ENV".  Valid values for 
 * "ENV" are: 
 *    <ul>
 *       <li><b>DEV</b> = development</li>
 *       <li><b>TEST</b> = test</li>
 *       <li><b>STAGE</b> = staging</li>
 *       <li><b>PROD</b> = production</li>
 *    </ul> 
 * This property should exist in the web container's main web.xm configuration file.
 * <p>
 * The location of the application configuration data can be identified as 
 * "CONFIG_LOCATION" and its value must be a file name path in the format of format:  
 * &lt;drive letter&gt;:/&lt;sub dir&gt;/&lt;sub dir&gt;/...   This property should 
 * exist in the web container's main web.xml configuration file.
 * <p>
 * The application name is identified as "APP_NAME" and its value should equivalent 
 * to the subdirectory where the target application is stored.  This property should 
 * exist in the application's web.xml configuration file.  For example, if the app is 
 * located in c:\progam files\apache\webapps\music then the app name will be "music".
 * <p>
 * The server which the application exist is identified as "SERVER" and its value 
 * should be in the format of &lt;protocol&gt;://&lt;server name&gt;:&lt;port number&gt;.
 * This property should exist in the web container's main web.xm configuration file.
 * <p>
 * An example of adding environment variables to the web.xml file of the web container:
 * <blockquote><pre>
 * &lt;env-entry&gt; 	
 *    &lt;env-entry-name&gt;ENVIRONMENT&lt;/env-entry-name&gt;  	
 *    &lt;env-entry-value&gt;DEV&lt;/env-entry-value&gt;	
 *    &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;  
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;  	
 *    &lt;env-entry-name&gt;CONFIG_LOCATION&lt;/env-entry-name&gt;  	
 *    &lt;env-entry-value&gt;C:/Program Files/Apache Software Foundation/Tomcat 6.0/lib&lt;/env-entry-value&gt;  	
 *    &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;  
 * &lt;/env-entry&gt;  
 *  &lt;env-entry&gt;  	
 *    &lt;env-entry-name&gt;APP_CONFIG_LOCATION&lt;/env-entry-name&gt;  	
 *    &lt;env-entry-value&gt;WEB-INF/classes/config&lt;/env-entry-value&gt;  	
 *    &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;  
 * &lt;/env-entry&gt;  
 * &lt;env-entry&gt;  	
 *    &lt;env-entry-name&gt;SERVER&lt;/env-entry-name&gt;  	
 *    &lt;env-entry-value&gt;http://rmtdaldev03:8080&lt;/env-entry-value&gt;  	
 *    &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 * &lt;/env-entry&gt;</pre>      
 * </blockquote>
 * Similar entries can be made for the environment variables in the web application's web.xml configuration file.
 * <p>
 * <b><u>Note</u></b><br>When notating file paths for the value of an environment variable, it is best to use 
 * UNC file system naming conventions in order to be compatible to different types of OS's.
 * <p>
 * @author Roy Terrell
 *
 */
public abstract class AbstractEnvironmentConfig extends RMT2Base {
    private static String ENVTYPE_DEV = "DEV";

    private static String ENVTYPE_TEST = "TEST";

    private static String ENVTYPE_STAGE = "STAGE";

    private static String ENVTYPE_PROD = "PROD";

    protected static String CONFIG_ROOT = "config";

    protected static String CONFIG_APP_ROOT = "app";

    /** The name of the authentication source for logging users in/out of the system. */
    public static String PROPNAME_AUTHENTICATOR = "authenticator";

    private static Properties props;

    private String env;

    private String configLoc;

    private String appConfigLoc;

    private String appName;

    private String appRootDirName;

    private String server;

    private String dbmsVendor;

    private String webapps_drive;

    private String webapps_dir;

    private String authentication;

    private Hashtable envTypes;

    /**
     * Default constructor which functions to initialize application 
     * properties hash and creates a master map of environment types.
     */
    public AbstractEnvironmentConfig() {
        super();
        AbstractEnvironmentConfig.props = new Properties();
        this.envTypes = new Hashtable();

        // Set master environment type hash.
        this.envTypes.put(AbstractEnvironmentConfig.ENVTYPE_DEV, 1);
        this.envTypes.put(AbstractEnvironmentConfig.ENVTYPE_TEST, 2);
        this.envTypes.put(AbstractEnvironmentConfig.ENVTYPE_STAGE, 3);
        this.envTypes.put(AbstractEnvironmentConfig.ENVTYPE_PROD, 4);
    }

    /**
     * Gathers the user-defined environment variables set in the web container.   Determines the environment 
     * the application is running, the configuration root location, Application name, server and database vendor 
     * code.  Environment types are: "DEV", TEST", "STAGE", or "PROD".  Also, the master list of environment 
     * types are setup as a Hashtable as well as log4j logger.
     * <p>
     * The path that is to represent the location of the application's configuration should be entered into the 
     * data source using the following format:  &lt;drive letter&gt;:/&lt;sub dir&gt;/&lt;sub dir&gt;/...&nbsp;&nbsp;&nbsp;
     * Despite the fact that some resources may be expected to be included in the classpath, <b>absolute paths</b> should be 
     * used in order to locate/load the resources from the file system.   Depending on when the descendent instance of 
     * this class is loaded into the JVM, the target resource may not be available to the classpath in the event the descendent 
     * is instantiated during the time of server start up.  
     * <p>
     * Valid DBMSVendor code goes as follows:
     *   <ul>
     *      <li>1 = Sybase Sql Anywhere, Adaptive Server Anywhere</li>
     *      <li>2 = Sybase Adaptive Server Enterprise</li>
     *      <li>3 = Oracle</li>
     *      <li>4 = MS SQL Server</li>
     *      <li>5 = DB2</li>
     *   </ul>
     *   
     * @param initCtx 
     *                arbitrary object representing the application's configuration.
     * @throws ConfigException 
     *           If environment, configuration root path, server, and/or application name 
     *           are not defined, or the environment variable defined with an invalid value.
     */
    public void init(Object initCtx) throws ConfigException {
        Context ctx;
        String defaultRoot = null;
        try {
            ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            this.env = (String) env.lookup("ENVIRONMENT");
            this.webapps_drive = (String) env.lookup("webapps_drive");
            this.webapps_dir = (String) env.lookup("webapps_dir");
            this.configLoc = (String) env.lookup("CONFIG_LOCATION");
            this.appName = (String) env.lookup("APP_NAME");
            this.appRootDirName = (String) env.lookup("APP_ROOT_DIR_NAME");
            defaultRoot = (String) env.lookup("DEFAULT_APP_ROOT_DIR_NAME");
            if (this.appRootDirName.equalsIgnoreCase("default")) {
                this.appRootDirName = defaultRoot;
            }
            this.server = (String) env.lookup("SERVER");
            this.dbmsVendor = (String) env.lookup("DBMSVendor");
            try {
                this.authentication = (String) env.lookup(PROPNAME_AUTHENTICATOR);
            }
            catch (NamingException e) {
                System.out.println(this.appName + "does not require authentication");
            }
            this.appConfigLoc = (String) env.lookup("APP_CONFIG_LOCATION");
            // Calculate the absolute file path pointing to where the target application's configuration is deployed.
            this.appConfigLoc = this.webapps_drive + this.webapps_dir + "/" + this.appRootDirName + "/" + this.appConfigLoc + "/";
        }
        catch (NamingException e) {
            throw new ConfigException(e);
        }

        // Validate environment value.
        if (this.env == null) {
            throw new ConfigException("Web Server environment variable is not defined in configuration");
        }
        if (!this.envTypes.containsKey(this.env)) {
            throw new ConfigException("Web Server environment value is invalid: " + this.env);
        }
        // Validate web server configuration location.
        if (this.configLoc == null) {
            throw new ConfigException("Web server configuration path variable is not defined in configuration");
        }
        // Validate application configuration location.
        if (this.appConfigLoc == null) {
            throw new ConfigException("Application configuration path variable is not defined in configuration");
        }
        // Validate application name.
        if (this.appName == null) {
            throw new ConfigException("Application Name variable is not defined in configuration");
        }
        // Validate server name.
        if (this.server == null) {
            throw new ConfigException("Server Name variable is not defined in configuration");
        }
        // Validate web directory
        if (this.webapps_dir == null) {
            throw new ConfigException("Web container directory variable is not defined in configuration");
        }
        // Validate web container drive letter.
        if (this.webapps_drive == null) {
            throw new ConfigException("Web container driver letter variable is not defined in configuration");
        }

        // Initialize logging.  Each application should have its own logging configuration
        String logPath = this.appConfigLoc + "log4j.properties";
        PropertyConfigurator.configure(logPath);
        Logger logger = Logger.getLogger("AbstractEnvironmentConfig");
        logger.log(Level.INFO, "Logger initialized successfully for application, " + this.appName);
    }

    /**
     * Searches for a property based on the specified key.
     * 
     * @param key the property key.
     * @return The value of the specified key.
     */
    public String getAppProperty(String key) {
        if (AbstractEnvironmentConfig.props == null) {
            return null;
        }
        return AbstractEnvironmentConfig.props.getProperty(key);
    }

    /**
     * Get authentication class name.
     * 
     * @return String.
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     * Return the environment indicator.
     * 
     * @return String as "DEV", TEST", "STAGE", or "PROD";
     */
    public String getEnv() {
        return this.env;
    }

    /**
     * Obtains a reference to the environment types master hash.
     * 
     * @return Hashtable.
     */
    protected Hashtable getEnvTypes() {
        return this.envTypes;
    }

    /**
     * Returns the path of the system configuration root.
     *  
     * @return String
     */
    protected String getConfigLoc() {
        return configLoc;
    }

    /**
     * Returns the name of the application.
     * 
     * @return String
     */
    protected String getAppName() {
        return appName;
    }

    /**
     * Sets the application name.
     * 
     * @param appName String
     */
    protected void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Return the server name.
     * 
     * @return String
     */
    protected String getServer() {
        return server;
    }

    /**
     * Return the DBMS Vendor code.
     * @return the dbmsVendor
     */
    protected String getDbmsVendor() {
        return dbmsVendor;
    }

    /**
     * @return the webapps_dir
     */
    protected String getWebapps_dir() {
        return webapps_dir;
    }

    /**
     * @return the webapps_drive
     */
    protected String getWebapps_drive() {
        return webapps_drive;
    }

    /**
     * @return the appConfigLoc
     */
    public String getAppConfigLoc() {
        return appConfigLoc;
    }

    /**
     * @param appConfigLoc the appConfigLoc to set
     */
    public void setAppConfigLoc(String appConfigLoc) {
        this.appConfigLoc = appConfigLoc;
    }

    /**
     * @return the appRootDirName
     */
    public String getAppRootDirName() {
        return appRootDirName;
    }

    /**
     * @param appRootDirName the appRootDirName to set
     */
    public void setAppRootDirName(String appRootDirName) {
        this.appRootDirName = appRootDirName;
    }

}
