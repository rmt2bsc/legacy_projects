package com.api.config;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;

import com.util.RMT2File;
import com.util.RMT2Utility;

/**
 * This class implements {@link com.api.config.ResourceConfigurator ResourceConfigurator} 
 * for the purpose of providing a centralized facility for a web application to identify 
 * properties.   Properties can be setup on a global level accessible by all applications 
 * within a single JVM or at the level of an individual application.   Global properties 
 * are stored and accessed via the system properties entity of the JVM.   On the other hand,
 * appliation properties are setup within each HttpSystemPropertyConfig instance of a single
 * application.   
 * <p>
 * The basic functionality of this class is to load various attributes from the web server's 
 * environment variables and local properties files into the current JVM's system properties 
 * and custom application properties.  Once the properties are properly loaded, they can be 
 * accessed using the {@link com.api.config.HttpSystemPropertyConfig.#getProperty(String) getProperty(String key)}
 * method.  For web applications, it is advisable to create an instance of HttpSystemPropertyConfig 
 * with an argument of type ServletConfig.
 * <p>
 * <b><u>How to use</u></b><br>
 * <pre>
 *  This code snippet is intended to be used inside the servlet method, <i>init(ServletConfig config)</i>.  
 *  Notice the ServletConfig parameter, <i>config</i>.
 *  
 *  import com.api.security.pool.AppPropertyPool;
 *      try {
 *         // Instantiate configurator.
 *         ResourceConfigurator cfg = new HttpSystemPropertyConfig(config);
 *         // Load the actual application properties  
 *         cfg.doSetup();  
 *         // Assign configurator to the application property pool as a ConfigAttribute type.
 *         ConfigAttributes appConfig = (ConfigAttributes) cfg;   
 *         AppPropertyPool pool = AppPropertyPool.getInstance();
 *         pool.addProperties(appConfig);
 *      }   
 *      catch (ConfigException e1) {       
 *         e1.printStackTrace();      
 *      }
 * </pre> 
 *   
 * @author RTerrell
 */
public class HttpSystemPropertyConfig extends AbstractEnvironmentConfig implements ResourceConfigurator, ConfigAttributes {
    /** Environment value for Development */
    public static String ENVTYPE_DEV = "DEV";

    /** Environment value for Test */
    public static String ENVTYPE_TEST = "TEST";

    /** Environment value for Staging */
    public static String ENVTYPE_STAGE = "STAGE";

    /** Environment value for Production */
    public static String ENVTYPE_PROD = "PROD";
    
    /** The name of the system ResourceBundle */
    public static final String CONFIG_SYSTEM = "SystemParms";

    /** Envirionment variable name */
    public static String PROPNAME_ENV = "env";

    /** Configuration location variable name */
    public static String PROPNAME_CFG_LOC = "config_location";
    
    /** Application configuration path location variable name */
    public static String PROPNAME_APP_CONFIG_PATH = "app_config_path";
    
    /** Application's AppParms.properties file location variable name */
    public static String PROPNAME_APPPARMS_LOCATION = "appParms_location";

    /** Application code name variable */
    public static String PROPNAME_APP_NAME = "app_name";

    /** Web Server variable name */
    public static String PROPNAME_SERVER = "server";

    /** DBMS Vendor code variable name */
    public static String PROPNAME_DBMS_VENDOR = "DBMSVendor";

    /** Services application variable name */
    public static String PROPNAME_SERVICE_APP = "services_app";

    /** Variable name used to identify the servlet designated for the services application. */
    public static String PROPNAME_SERVICE_SERVLET = "services_servlet";

    /** Variable name used to identify the host of the service application. */
    public static String PROPNAME_SERVICELOAD_HOST = "loadservices_host";

    /** Variable name used to identify the command to query all services. */
    public static String PROPNAME_SERVICELOAD_COMMAND = "loadservices_module";

    /** Variable name used to identify the service id to query all services. */
    public static String PROPNAME_SERVICELOAD_SERVICEID = "loadservices_id";

    /** Default database connections variable name */
    public static String PROPNAME_DEF_CONN = "defaultconnections";

    /** Maximum number of database connections variable name */
    public static String PROPNAME_MAX_CONN = "maxconnections";

    /** Session time out interval variable name */
    public static String PROPNAME_SESSION_TIMEOUT = "timeoutInterval";

    /** Serializatin drive letter variable name */
    public static String PROPNAME_SERIAL_DRIVE = "serial_drive";

    /** Serialization path variable name */
    public static String PROPNAME_SERIAL_PATH = "serial_path";

    /** Serialization file name extension variable name */
    public static String PROPNAME_SERIAL_EXT = "serial_ext";

    /** SAX driver variable name */
    public static String PROPNAME_SAX_DRIVER = "SAXDriver";

    /** XML Document class variable name */
    public static String PROPNAME_DOC_CLASS = "docClass";

    /** JSP Polling page variable name */
    public static String PROPNAME_POLL_PAGE = "polling_page";

    /** Web application mapping configuration variable name */
    public static String PROPNAME_APP_MAPPING = "web_app_mapping";

    /** Web application mapping configuration path variable name */
    public static String PROPNAME_APP_MAPPING_PATH = "web_app_mapping_path";

    /** Report XSLT output path variable name */
    public static String PROPNAME_XSLT_PATH = "rpt_xslt_path";

    /** Report output file extension variable name */
    public static String PROPNAME_RPT_FILE_EXT = "rpt_file_ext";

    /** Images directory variable name */
    public static String PROPNAME_IMG_DIR = "image_dir";

    /** Encryption cycle count variable name */
    public static String PROPNAME_ENCRYPT_CYCLE = "ENCRYPT_CYCLE";

    /** SMTP mail authentication variable name */
    public static String PROPNAME_MAIL_AUTH = "mail.authentication";

    /** SMTP password variable name */
    public static String PROPNAME_MAIL_PW = "mail.password";

    /** SMTP user id variable name */
    public static String PROPNAME_MAIL_UID = "mail.userId";

    /** POP server variable name */
    public static String PROPNAME_POP_SERVER = "mail.host.pop3";

    /** SMTP Server variable name */
    public static String PROPNAME_SMTP_SERVER = "mail.host.smtp";

    /** INternal SMTP Domain variable name */
    public static String PROPNAME_INTERNAL_SMTP_DOMAIN = "mail.internal_smtp_domain";
    
    /** The name that points to the datasource directory */ 
    public static String PROPNAME_DATASOURCE_DIR = "datasource_dir";

    /** Web drive */
    public static String PROPNAME_WEB_DRIVE = "webapps_drive";

    /** web directory */
    public static String PROPNAME_WEB_DIR = "webapps_dir";

    /** The name of the attribute that indicates whether user atuhentication is performed locally or remotely. */
    public static String PROPNAME_AUTHLOCALE = "user_auth_locale";

    /** The name of the attribute that indicates the SOAP Host. */
    public static String SOAP_HOST = "soaphost";
    
    /** The name of the attribute that indicates whether or not the SOAP parser is namespace aware. */
    public static String SOAP_NAMESPACE_AWARE = "soapNameSpaceAware";

    /** 
     * The name of the authentication source for logging users in to the system. 
     * 
     */
    public static String PROPNAME_LOGINSRC = "user_login_source";

    /** 
     * The name of the authentication source for logging users out of the system. 
     * 
     */
    public static String PROPNAME_LOGOUTSRC = "user_logout_source";
    
    /**  The name of the ORM pagination page size indicator */
    public static String PROPNAME_ORM_PAGE_SIZE = "pagination_page_size";
    
    /**  The name of the ORM maximum pagination links total indicator */
    public static String PROPNAME_ORM_PAGE_LINK_TOTAL = "page_link_max";
    

    /** Company text description */
    public static String OWNER_TEXT = "owner.companyTxt";

    public static String OWNER_NAME = "owner.Name";

    public static String OWNER_CONTACT = "owner.Contact";

    public static String OWNER_ADDR1 = "owner.Address1";

    public static String OWNER_ADDR2 = "owner.Address2";

    public static String OWNER_ADDR3 = "owner.Address3";

    public static String OWNER_ADDR4 = "owner.Address4";

    public static String OWNER_CITY = "owner.City";

    public static String OWNER_STATE = "owner.State";

    public static String OWNER_ZIP = "owner.Zip";

    public static String OWNER_PHONE = "owner.Phone";

    public static String OWNER_FAX = "owner.Fax";

    public static String OWNER_EMAIL = "owner.Email";

    public static String OWNER_WEBSITE = "owner.Website";

    public static String DBMSTYPE_ASA = "ASA";

    public static String DBMSTYPE_ASE = "ASE";

    public static String DBMSTYPE_ORACLE = "ORACLE";

    public static String DBMSTYPE_SQLSERVER = "SQLSRVR";

    public static String DBMSTYPE_DB2 = "DB2";

    private static Map<String, String> dbmsTypes;

    /** Application properties */
    private Properties appProps;

    /** Application display name */
    private String appDisplayName;

    private Logger logger;

    /**
     * Default constructor.
     */
    public HttpSystemPropertyConfig() {
        super();
        HttpSystemPropertyConfig.dbmsTypes = new HashMap<String, String>();
        HttpSystemPropertyConfig.dbmsTypes.put("1", HttpSystemPropertyConfig.DBMSTYPE_ASA);
        HttpSystemPropertyConfig.dbmsTypes.put("2", HttpSystemPropertyConfig.DBMSTYPE_ASE);
        HttpSystemPropertyConfig.dbmsTypes.put("3", HttpSystemPropertyConfig.DBMSTYPE_ORACLE);
        HttpSystemPropertyConfig.dbmsTypes.put("4", HttpSystemPropertyConfig.DBMSTYPE_SQLSERVER);
        HttpSystemPropertyConfig.dbmsTypes.put("5", HttpSystemPropertyConfig.DBMSTYPE_DB2);
    }

    /**
     * Creates an instance of HttpSystemPropertyConfig using an Object 
     * that serves as an initializer.
     *  
     * @param initCtx This should a valid instance of ServletConfig.
     * @throws ConfigException
     */
    public HttpSystemPropertyConfig(Object initCtx) throws ConfigException {
        this();
        this.init(initCtx);
    }

    /**
     * Determines the name of the web application being initilized, outputs
     * application information to the console, and initializes the logger.
     * 
     * @param initCtx
     *            Required to be a valid instance of ServletConfig.
     * @throws ConfigException
     */
    public void init(Object initCtx) throws ConfigException {
        super.init(initCtx);
        String initMsg = "Starting Application: ";
        if (initCtx != null && initCtx instanceof ServletConfig) {
            ServletContext ctx = ((ServletConfig) initCtx).getServletContext();
            this.appDisplayName = ctx.getServletContextName();
            if (this.appDisplayName == null) {
                this.appDisplayName = ctx.getRealPath("");
            }
            initMsg += this.appDisplayName;
        }
        else {
            initMsg += "[Application is unknown]";
        }
        // Initialize logger
        this.logger = Logger.getLogger("HttpSystemPropertyConfig");
        this.logger.log(Level.INFO, initMsg);
    }

    /**
     * Assigns properties from SystemParms.properties and the target application's AppParms.properties to JVM 
     * System instance and AppPropertyPool collection, respectively. 
     *  
     * @throws ConfigException
     */
    public void doSetup() throws ConfigException {
	this.logger.log(Level.INFO, "Configuring application, " + this.getAppName() + "...");
        // Environment variable should be valid
        this.addSysProperty(HttpSystemPropertyConfig.PROPNAME_ENV, this.getEnv());
        // Configuration path should be valid
        this.addSysProperty(HttpSystemPropertyConfig.PROPNAME_CFG_LOC, this.getConfigLoc());
        // Server should be valid
        this.addSysProperty(HttpSystemPropertyConfig.PROPNAME_SERVER, this.getServer());
        
        // Web container drive letter
        this.addSysProperty(HttpSystemPropertyConfig.PROPNAME_WEB_DRIVE, this.getWebapps_drive());
        // Web container directory root
        this.addSysProperty(HttpSystemPropertyConfig.PROPNAME_WEB_DIR, this.getWebapps_dir());

        // Use absolte file path  to locate SystemParms.properties
        String systemParmsPath = this.getConfigLoc() + "/" + HttpSystemPropertyConfig.CONFIG_SYSTEM + ".properties";
        // Add name/value pairs from SystemParms.properties to the JVM System properties .
        this.addLocalSystemProperties(systemParmsPath);

        // Setup local application properties
        String appParmsPath = this.getAppConfigLoc() + RMT2Utility.CONFIG_APP + ".properties";
        try {
            // Add name/value pairs from the target application's AppParms.properties to the AppPropertyPool collection .
            this.appProps = RMT2File.loadPropertiesObject(appParmsPath);
        }
        catch (Exception e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }
        // Add application name to AppPropertyPool properties
        this.appProps.put(HttpSystemPropertyConfig.PROPNAME_APP_NAME, this.getAppName());
        // Add Authenticator class name to application properties
        if (this.getAuthentication() != null) {
            this.appProps.put(AbstractEnvironmentConfig.PROPNAME_AUTHENTICATOR, this.getAuthentication());
        }
        // Add APplication resource configuration path location to AppPropertyPool properties
        if (this.getAppConfigLoc() != null) {
            this.appProps.put(HttpSystemPropertyConfig.PROPNAME_APP_CONFIG_PATH, this.getAppConfigLoc());
        }
        // Add AppParms.properties location to AppPropertyPool properties
        if (this.getAppConfigLoc() != null) {
            this.appProps.put(HttpSystemPropertyConfig.PROPNAME_APPPARMS_LOCATION, appParmsPath);
        }
        
    }

    /**
     * Output message to console signaling that the application has been initialized 
     * successfully.
     * 
     * @param ctx An arbitrary object that is ignored.
     * @throws ConfigException
     */
    public void doPostSetup(Object ctx) throws ConfigException {
        this.logger.log(Level.INFO, "Application configuration completed.");
    }

    /**
     * Gets the property value of <i>key</i> from either system properties or 
     * application properties.  If <i>key</i> is not found in application 
     * properties then an attempt will be made to locate <i>key</i> in system 
     * properties.
     * 
     * @param key The property name.
     * @return The property value or null if not found.
     */
    public String getProperty(String key) {
        if (this.appProps == null) {
            logger.log(Level.WARN, "Application property could not be found due to properties are not loaded");
            return null;
        }
        String result = this.appProps.getProperty(key);
        if (result == null) {
            result = System.getProperty(key);
        }
        return result;
    }

    /**
     * Releases any resources that may have been consumed by this instance.
     * 
     * @throws ConfigException
     */
    public void destroy() throws ConfigException {
        this.appProps.clear();
        this.appProps = null;
        this.logger = null;
    }

    /**
     * Loads the name/value pairs from SystemParms.properties housed in the shared lib directory of the 
     * servlet container and assigns each name/value pair to the System properties.  
     * <p>
     * As a safety measure it is best that this method rely on the file system to locate the resource, hence 
     * employing the FileInputStream class , instead of depending on the resource to be found in the classpath.   
     * There is a distinct possiblity that the resource has yet to be loaded into the JVM by the time this 
     * method is invoked.  
     * 
     * @param fileName
     *               The absoulte path and file name of the SystemParms.properties file.
     * @throws ConfigException 
     *             For general I/O failures or when a key/value pair is determined to 
     *             be incorrect.  
     */
    private void addLocalSystemProperties(String fileName) throws ConfigException {
        // Locate and load global properties to System properties collection
        FileInputStream fis;
        try {
            fis = new FileInputStream(fileName);
        }
        catch (FileNotFoundException e) {
            this.msg = e.getMessage();
            this.logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }
        Properties globalProps = new Properties();
        try {
            globalProps.load(fis);
        }
        catch (IOException e) {
            this.msg = "An I/O Exception occurred when attempting to load system properties file, " + fileName + ".  Detail Message: "
                    + (e.getMessage() == null ? "N/A" : e.getMessage());
            this.logger.log(Level.ERROR, this.msg);
            throw new ConfigException(this.msg);
        }

        // Add local properties to the System property collection.
        for (Object key : globalProps.keySet()) {
            String propName = (String) key;
            this.addSysProperty(propName, globalProps.getProperty(propName));
            this.logger.log(Level.DEBUG, "key/Value pair added as System Properties: " + propName + "=" + globalProps.getProperty(propName));
        }
    }

    /**
     * Adds a key/value pair to the System properties collection.  A check is performed 
     * to ensure that a system property is not duplicated from another application context.  
     * As a requirement, the user must have adequate security to access/assign the system 
     * prooperty, key and value must be a non-null value, and the key cannot be an empty 
     * String.
     * 
     * @param key 
     *          The name of the system property.  This key is added provided that it 
     *          does not already exist.
     * @param value 
     *          The value of the system proeprty.
     * @throws ConfigException 
     *            If a security restricts the access/assignment of the system property, or 
     *            when the key and/or the value is null, or when the key is an empty String.
     */
    private void addSysProperty(String key, String value) throws ConfigException {
        // Validate key and add only if it is not already included
        // in the list of System properties.
        try {
            boolean keyExist = (System.getProperty(key) != null ? true : false);
            if (keyExist) {
                return;
            }
        }
        catch (SecurityException e) {
            throw new ConfigException("Security problem exist in attempting to access system property variable, " + key);
        }
        catch (NullPointerException e) {
            throw new ConfigException("System property key argument is null");
        }
        catch (IllegalArgumentException e) {
            throw new ConfigException("System property key argument is an empty String");
        }

        // Let's attempt to add key/value pair to System properties.
        try {
            System.setProperty(key, value);
        }
        catch (SecurityException e) {
            throw new ConfigException("Security problem exist in attempting to assign system property: Key=" + key + "  value=" + value);
        }
        catch (NullPointerException e) {
            throw new ConfigException("System property value argument is null");
        }
        return;
    }

    /**
     * Get all properties.
     * 
     * @return the appProps
     */
    public Properties getAppProps() {
        return appProps;
    }

    /**
     * Retrieves the DBMS Vendor type description based on its key.
     * 
     * @param key The key of the DBMS system.
     * @return The value assoicated with <i>key</i> or null if the value is not mapped.
     */
    public static final String getDbmsType(String key) {
        return HttpSystemPropertyConfig.dbmsTypes.get(key);
    }

}
