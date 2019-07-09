package com.controller.stateless;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.api.config.ConfigAttributes;
import com.api.config.ConfigException;
import com.api.config.JdbcConfig;
import com.api.config.HttpSystemPropertyConfig;
import com.api.config.ResourceConfigurator;

import com.api.security.pool.AppPropertyPool;
import com.util.SystemException;

/**
 * Initializes the application upon startup.  User-defined JVM and application properties 
 * are loaded into memory from localized sources and it creates the application's database 
 * connection pool.   Configure this servlet to load automatically when the web container 
 * or server is started. 
 * 
 * @author roy.terrell
 * 
 */
public class SystemConfigurator extends AbstractServlet {
    private static final long serialVersionUID = 7358434218143689146L;

    /**
     * Initialize instance and context variables. Create datasource connections
     * and place them on the context of the web application.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String msg = null;

        // Load properties for each web app being initialized.
        try {
            if (!AppPropertyPool.isAppConfigured()) {
                ResourceConfigurator cfg = new HttpSystemPropertyConfig(config);
                cfg.doSetup();
                ConfigAttributes appConfig = (ConfigAttributes) cfg;
                AppPropertyPool pool = AppPropertyPool.getInstance();
                pool.addProperties(appConfig);
                cfg.doPostSetup(config);
            }
        }
        catch (ConfigException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

        // Load Database connections, if available.
        try {
            // Setup database pool.
            ResourceConfigurator dbCfg = new JdbcConfig();
            dbCfg.doSetup();
        }
        catch (ConfigException e) {
            msg = AppPropertyPool.getProperty("apptitle") + " does not have JDBC Datasoure configuration.  Other message: " + e.getMessage();
            Logger.getLogger("SystemConfigurator").log(Level.WARN, msg);
        }
        catch (SystemException e) {
            msg  =  AppPropertyPool.getProperty("apptitle")  + "Unable to create DataProviderConnectionApi instance: " + e.getMessage();
            Logger.getLogger("SystemConfigurator").log(Level.ERROR, msg);
        }
        msg = "Initialization of application, " + AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME) + ", in the " + AppPropertyPool.getEnvirionment()
                + " completed.";
        Logger.getLogger("SystemConfigurator").log(Level.INFO, msg);
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

    /**
     * No action
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.processRequest(request, response);
    }
}