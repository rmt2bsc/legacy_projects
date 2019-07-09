package com.api;

import java.sql.Connection;

import com.api.db.DatabaseException;

import com.bean.RMT2Base;

import com.bean.db.ObjectMapperAttrib;
import com.util.SystemException;

/**
 * This class provides numerous stub methods which statisfies the minimum implemention requirements 
 * of the DaoApi interface.
 * 
 * @author roy.terrell
 *
 */
public class DaoApiStub extends RMT2Base {

    /* (non-Javadoc)
     * @see com.api.db.DatabaseTransApi#commitUOW()
     */
    public int commitUOW() {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.api.db.DatabaseTransApi#rollbackUOW()
     */
    public int rollbackUOW() {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.api.db.DatabaseTransApi#close()
     */
    public void close() throws DatabaseException {
        return;
    }

    /* (non-Javadoc)
     * @see com.api.db.DatabaseTransApi#close(boolean)
     */
    public void close(boolean replenishPool) throws DatabaseException {
        return;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#getBaseClass()
     */
    public String getBaseClass() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#getBaseView()
     */
    public String getBaseView() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#getDataSourceAttib()
     */
    public ObjectMapperAttrib getDataSourceAttib() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#getDataSourceName()
     */
    public String getDataSourceName() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#getResultType()
     */
    public int getResultType() {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#setBaseClass(java.lang.String)
     */
    public String setBaseClass(String value) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#setBaseView(java.lang.String)
     */
    public String setBaseView(String value) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#setDataSourceName(java.lang.String)
     */
    public void setDataSourceName(String dsn) {
        return;
    }

    /* (non-Javadoc)
     * @see com.api.DaoApi#setResultType(int)
     */
    public void setResultType(int value) {
        return;
    }

    /* (non-Javadoc)
     * @see com.api.db.DatabaseTransApi#getConnection()
     */
    public Connection getConnection() {
        return null;
    }

    /**
     * Stub implementation
     * 
     * @param query
     * @return null
     * @throws DatabaseException
     * @throws SystemException
     */
    public String executeXmlQuery(String query) throws DatabaseException, SystemException {
        return null;
    }
} // end class

