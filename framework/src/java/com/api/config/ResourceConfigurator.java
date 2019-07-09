package com.api.config;

import java.util.Properties;

/**
 * This interface declares the methods that must be implemented in order to provide a 
 * centralized facility for an application to identify and access properties.  When
 * implementing the init(Object) method, its generic argument can be used as an initialization
 * source for the process of instantiation.
 *   
 * @author RTerrell
 */
public interface ResourceConfigurator {
    /**
     * Commences the initialization of a ResourceConfigurator implementation.
     * 
     * @param initCtx An arbitrary object representing some form of data source.
     * @throws ConfigException
     */
    void init(Object initCtx) throws ConfigException;

    /**
     * Setup the properties to their respective entities.
     * 
     * @throws ConfigException
     */
    void doSetup() throws ConfigException;

    /**
     * Perform post setup logic.
     * 
     * @param ctx An arbitrary object representing some form of data source.
     * @throws ConfigException
     */
    void doPostSetup(Object ctx) throws ConfigException;

    /**
     * Get the value of the property named <i>key</i>.
     * 
     * @param key The property name.
     * @return String.
     */
    String getProperty(String key);

    /**
     * Get the environment indicator.
     * 
     * @return String.
     */
    String getEnv();

    /**
     * Gets the Application Properties object.
     * @return Properties.
     */
    Properties getAppProps();

    /**
     * Perform any clean up routines when deallocating an ResourceConfigurator 
     * implementation.
     * 
     * @throws ConfigException
     */
    void destroy() throws ConfigException;
}