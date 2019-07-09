package com.api.security.pool;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.config.ConfigAttributes;
import com.api.config.ResourceConfigurator;

import com.bean.RMT2Base;

/**
 * Manages the application's properties.
 * 
 * @author Roy Terrell
 *
 */
public class AppPropertyPool extends RMT2Base {
    private static Logger logger = Logger.getLogger("AppPropertyPool");

    private static ConfigAttributes props;

    /**
     * Default constructor which initializes AppPropertyPool
     *
     */
    private AppPropertyPool() {
        super();
    }

    /**
     * Creates an instance of AppPropertyPool class.
     * 
     * @return DatabaseConnectionPool.
     */
    public static AppPropertyPool getInstance() {
        try {
            AppPropertyPool pool = new AppPropertyPool();
            return pool;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            return null;
        }

    }

    /**
     * Initializes the application properties collection from an outside source.
     * 
     * @param props ({@link com.api.config.ConfigAttributes ConfigAttributes}
     */
    public final void addProperties(ConfigAttributes props) {
        AppPropertyPool.props = props;
    }

    /**
     * Returns the value of the property named <i>key</i>.
     * 
     * @param key The name of the property to access.
     * @return The property value or null if property does not exist 
     *         or if the properties collection is invalid.
     */
    public static final String getProperty(String key) {
        if (AppPropertyPool.props == null) {
            return null;
        }
        return AppPropertyPool.props.getProperty(key);
    }

    /**
     * Verifies whether or not an AppPropertyPool instance's configuration property pool 
     * has been setup.
     * 
     * @return true when initialized and false for not initiailized.
     */
    public static final boolean isAppConfigured() {
        return (props != null);
    }

    public static final String getEnvirionment() {
        if (props == null) {
            return null;
        }
        if (props instanceof ResourceConfigurator) {
            return ((ResourceConfigurator) props).getEnv();
        }
        return null;

    }

}