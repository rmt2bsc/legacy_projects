package com.api.db;

import java.sql.Connection;

/**
 * Interface that containing methods that provide basic transactional 
 * functionality for an external data provider.
 * 
 * @author roy.terrell
 *
 */
public interface DatabaseTransApi {

    /**
     * Commits database transaction with exceptoin handling
     * 
     * @return 1 for success and -1 for failure
     */
    int commitUOW();

    /**
     * Rollback database transaction with exceptoin handling.
     * 
     * @return 1 for success and -1 for failure
     */
    int rollbackUOW();

    /**
     * Release database resources.
     */
    void close();

    /**
     * Gets the connection bean wrapper object associated with this api.
     * 
     * @return Object.
     */
    Object getConnector();

    /**
     * Sets the connectin bean wrapper object.
     * 
     * @param value
     *            Object
     */
    void setConnector(Object value);

    /**
     * Gets the JDBC Connection object associated with this api
     * 
     * @return Connection.
     */
    Connection getConnection();
} // End interface
