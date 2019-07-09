package com.api.db.orm;

import java.sql.Connection;

import com.api.db.DatabaseTransImpl;
import com.api.db.DatabaseException;
import com.bean.db.DatabaseConnectionBean;
import com.controller.Request;

import com.util.SystemException;

/**
 * @author RTerrell
 *
 */
public class BaseDataSourceImpl extends DatabaseTransImpl {

    /** The name of the DataSource view */
    protected String baseView;

    /** The name of the DataSource class */
    protected String baseBeanClass;

    /**
     * Initializes DataSourceBaseImpl object using a Connection object and the loginId.
     * 
     * @param _con
     * @param _loginId
     * @throws DatabaseException
     * @throws SystemException
     */
    public BaseDataSourceImpl() throws SystemException {
        super();
    }

    /**
     * Initializes DataSourceBaseImpl object using a Connection object and the loginId.
     * 
     * @param _con
     * @param _loginId
     * @throws DatabaseException
     * @throws SystemException
     */
    public BaseDataSourceImpl(Connection _con, String _loginId) throws DatabaseException, SystemException {
        super(_con, _loginId);
    }

    /**
     * Initializes a RdbmsDataSourceImpl object using a
     * {@link DatabaseConnectionBean} object.
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public BaseDataSourceImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
        super(dbConn);
        return;
    }

    /**
     * Initializes a RdbmsDataSourceImpl object using a
     * DatabaseConnectionBean object and a HttpServletRequest object.
     * 
     * @param dbConn
     * @param req
     * @throws DatabaseException
     * @throws SystemException
     */
    public BaseDataSourceImpl(DatabaseConnectionBean dbConn, Request req) throws DatabaseException, SystemException {
        super(dbConn, req);
        return;
    }

    /**
     * 
     * @see com.api.AbstractApiImpl#initApi()
     */
    @Override
    protected void initApi() throws DatabaseException, SystemException {
        return;
    }

    /**
     * Set the base DataSource view name.
     * 
     * @param viewName
     *            Name of Datasource view.
     * @return The previous datasource view name.
     */
    public String setBaseView(String viewName) {
        String prevBaseView = this.baseView;
        this.baseView = viewName;
        return prevBaseView;
    }

    /**
     * Set the base DataSource class name.
     * 
     * @param className
     *            Name of Datasource class.
     * @return The previous datasource class name.
     */
    public String setBaseClass(String className) {
        String prevBaseClass = this.baseBeanClass;
        this.baseBeanClass = className;
        return prevBaseClass;
    }

    /**
     * Get current DataSource base view name.
     * 
     * @return String
     */
    public String getBaseView() {
        return this.baseView;
    }

    /**
     * Get current DataSource base class name.
     * 
     * @return String
     */
    public String getBaseClass() {
        return this.baseBeanClass;
    }

}
