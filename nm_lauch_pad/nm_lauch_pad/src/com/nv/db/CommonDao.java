package com.nv.db;

import java.sql.Connection;

/**
 * a common DAO Interface for the Launch Pad application.
 * 
 * @author rterrell
 *
 */
public interface CommonDao {

    /**
     * Implement to reelease any allocated resources.
     */
    void close();

    /**
     * Returns the SQL connection object that is internal to the DAO.
     * 
     * @return {@link Connection} or null if connection is unobtainable
     */
    Connection getInternalConnection();

}
