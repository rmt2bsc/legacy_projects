package com.api.db;

import com.api.db.orm.DataSourceAdapter;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Factory for creating DynamicSqlApi objects.
 * 
 * @author roy.terrell
 * 
 */
public class DynamicSqlFactory extends DataSourceAdapter {

    /**
     * Creates a new instance of DynamicSqlApi using a
     * {@link DatabaseConnectionBean}.
     * 
     * @param _dbo
     *            The connection bean
     * @return {@link DynamicSqlApi}
     */
    public static DynamicSqlApi create(DatabaseConnectionBean _dbo) {
        try {
            DynamicSqlApi api = new DynamicSqlImpl(_dbo.getDbConn(), _dbo.getDbUserId());
            api.setConnector(_dbo);
            return api;
        }
        catch (SystemException e) {
            return null;
        }
        catch (DatabaseException d) {
            return null;
        }

    }

}
