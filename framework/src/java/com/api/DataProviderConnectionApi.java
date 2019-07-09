package com.api;

import java.util.Vector;

import com.util.SystemException;

/**
 * An interface for managing connections pertaining to some external data provider.
 * 
 * @author appdev
 *
 */
public interface DataProviderConnectionApi {
    /**
     * Initialized connections.
     * 
     * @return Total number connections initialized.
     * @throws SystemException
     */
    int initConnections() throws SystemException;

    /**
     * Initialized connections using a boolean switch
     * 
     * @param _switch boolean value to be  used at the implementors discretion.
     * @return Total number connections initialized.
     * @throws SystemException
     */
    int initConnections(boolean _switch) throws SystemException;

    /**
     * Get the next available connection from the collecitve of connections.
     * 
     * @return The connection.
     * @throws SystemException
     */
    Object getDataProviderConnection() throws SystemException;

    /**
     * Obtain the connection collective.
     * @return Vector
     */
    Vector getConnectionCollective();

    /**
     * Obtain the total count of connections that are in use.
     * 
     * @return int
     */
    int getInUseCount();

    /**
     * Return the connection back to the collective an flag as "available".
     * 
     * @param obj the connection.
     * @return boolean value representing success or failure.
     */
    boolean releaseConnection(Object obj);

    /**
     * Probes the connection pool to identify and replace stale or closed connections 
     * with valid open connections.
     * 
     * @return The total number of connections recovered.
     * @throws SystemException
     */
    int recoverStaleConnections() throws SystemException;

    /**
     * Close the connection to release its resources.
     * 
     * @param obj The connection.
     * @return boolean value representing success or failure.
     * @throws SystemException
     */
    boolean closeConnection(Object obj) throws SystemException;

    /**
     * Get the data provider's driver.
     * @return Object
     */
    Object getDataProviderDriver();

    /**
     * Get the Applicatin name.
     * @return
     */
    String getAppName();

} // End of Class
