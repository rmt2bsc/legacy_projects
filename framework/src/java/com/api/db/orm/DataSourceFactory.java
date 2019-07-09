package com.api.db.orm;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.DaoApi;
import com.api.DataSourceApi;
import com.bean.RMT2TagQueryBean;
import com.bean.db.DatabaseConnectionBean;

/**
 * Factory designed to create new instances of DataSourceApi and DaoApi objects.
 * 
 * @author roy.terrell
 *
 */
public class DataSourceFactory extends DataSourceAdapter {
    private static Logger logger = Logger.getLogger(DataSourceFactory.class);

    /**
     * Creates a XML datasource api from NulltableView view.
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @return {@link RMT2DataSourceApi}
     */
    public static DataSourceApi create(DatabaseConnectionBean _dbo) {
        return DataSourceFactory.create(_dbo, "NulltableView");
    }

    /**
     * Creates a XML RMT2DataSourceApi object using _dbo and the _dataSourceName
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @param _dataSourceName The name of the datasource
     * @return {@link RMT2DataSourceApi}
     */
    public static DataSourceApi create(DatabaseConnectionBean _dbo, String _dataSourceName) {
        RMT2TagQueryBean queryData = null;
        try {
            queryData = new RMT2TagQueryBean(_dataSourceName, "xml", null, null);
        }
        catch (Exception e) {
            // Do Nothing
        }
        return DataSourceFactory.create(_dbo, queryData);
    }

    /**
     * Creates a SQL or XML ObjectMapperAttrib using a DatabaseConnectionBean and a RMT2TagQueryBean
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @param _queryData Object containing the details about the type of datasource to create.
     * @return {@link RMT2DataSourceApi}
     */
    public static DataSourceApi create(DatabaseConnectionBean _dbo, RMT2TagQueryBean _queryData) {
        try {
            DataSourceApi api = new RdbmsDataSourceImpl(_dbo.getDbConn(), null, _queryData);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a DaoApi object using a DatabaseConnectionBean.
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @return {@link DaoApi}
     */
    public static DaoApi createDao(DatabaseConnectionBean _dbo) {
        try {
            DaoApi api = new RdbmsDaoImpl(_dbo);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a DaoApi object using a DatabaseConnectionBean and the data source name.
     * 
     * @param _dbo A valid Database connection bean and cannot be null.
     * @param dsn The name of the data source.
     * @return {@link DaoApi}
     */
    public static DaoApi createDao(Object _dbo, String dsn) {
        DatabaseConnectionBean dbBean = null;
        if (_dbo != null && _dbo instanceof DatabaseConnectionBean) {
            dbBean = (DatabaseConnectionBean) _dbo;
        }
        try {
            DaoApi api = new RdbmsDaoImpl(dbBean, dsn);
            api.setConnector(dbBean);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a SQL or XML ObjectMapperAttrib using a DatabaseConnectionBean and a RMT2TagQueryBean
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @param _queryData Object containing the details about the type of datasource to create.
     * @return {@link RMT2DataSourceApi}
     */
    public static DaoApi createDao(DatabaseConnectionBean _dbo, RMT2TagQueryBean _queryData) {
        try {
            logger.log(Level.DEBUG, "Begin to Create DAO object");
            DaoApi api = new RdbmsDaoImpl(_dbo.getDbConn(), null, _queryData);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
}
