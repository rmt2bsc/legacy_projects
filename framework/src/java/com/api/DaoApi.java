package com.api;

import java.io.InputStream;

import com.util.NotFoundException;
import com.util.SystemException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseException;

import com.bean.db.ObjectMapperAttrib;

/**
 * This interface provides the methods responsible for the storage of data to, and the 
 * retrieval of data from an external data source.
 * 
 * @author roy.terrell
 *
 */
public interface DaoApi extends DatabaseTransApi {
    /** Code for tabular result set type */
    static final int RESULTSET_TYPE_TABULAR = 0;

    /** Code for xml result set type */
    static final int RESULTSET_TYPE_XML = 20;

    /** RDBMS null literal */
    static final String DB_NULL = "null";

    /** The location where the output of serialized objects are stored */
    static final String XMLOUT_LOCATION = "c:/tmp/";

    /** Start of file status */
    static final int BOF = -100;

    /** End of file status */
    static final int EOF = -999;

    /**
     * Retrieves pertaining to the datasource that ormBean is associated with.   User is 
     * expected to setup the appropriate selection and ordering criteria from within ormBean.
     * 
     * @param ormBean The ORM bean that is used to obtain data from the database.
     * @return One or more ormBean objects.
     * @throws DatabaseException A database access error occurs.
     */
    Object[] findData(Object ormBean) throws DatabaseException;

    /**
     * Executes SLQ Query i which the reuslts will be in the format of XML.
     * 
     * @param sql
     *            The query to exectute
     * @return XML as String
     * @throws DatabaseException
     * @throws SystemException
     */
    String executeXmlQuery(String sql) throws DatabaseException, SystemException;

    /**
     * Retrieves data from the database using _obj as the source.  
     * <p>
     * <p>
     * Example Usage:
     * <p>
     * To retreive all rows lreated to the ORM bean
     * <blockquote>
     * UserSecurity ul = UserFactory.createUserLogin();
     * <p>
     * Object list [] = dso.retrieve(ul);
     * </blockquote>
     * <p>
     * To retrieve a filtered data set
     * <blockquote>
     * ul.addCriteria("Login", "admin");
     * <p>
     * RMT2DataSourceApi dso = DataSourceFactory.create(this.dbConn);
     * <p>
     * Object list [] = dso.retrieve(ul);
     * </blockquote>
     * 
     * @param _obj The object that is used to identify the source of the data to retrieve. 
     * @return Object[]
     * @throws DatabaseException
     */
    Object[] retrieve(Object _obj) throws DatabaseException;

    /**
     * Adds data to the database using _obj as the source.  Primary keys can be automatically generated depending on the value of _autoKey.
     * Currently, this method only supports those situations where there is a one to one mapping of a POJO and database table.
     *  
     * @param _obj A POJO, which maps to some table in the database, and is required to have an associated DataSource View document.
     * @param _autoKey A flag indicating whether or not primary keys are generated and made available for retrieval.
     * @return ResultSet containing the generated keys when _autoKey is equal true.   Otherwise, null is returned.
     * @throws DatabaseException
     */
    int insertRow(Object _obj, boolean _autoKey) throws DatabaseException;

    /**
     * Modifies one record contained in a table residing in the database based on the values contained in _obj.
     * Currently, this method only supports those situations where there is a one to one mapping of a POJO and database table.
     * 
     * @param _obj A POJO, which maps to some table in the database, and is required to have an associated DataSource View document.
     * @return A count of the number of rows effected by this operation when _autoKey equals false.  Otherwise, returns the auto-generated id.
     * @throws DatabaseException
     */
    int updateRow(Object _obj) throws DatabaseException;

    /**
     * Deleted one record contained in a table residing in the database based on the values contained in _obj.
     * Currently, this method only supports those situations where there is a one to one mapping of a POJO and database table.
     * 
     * @param _obj A POJO, which maps to some table in the database, and is required to have an associated DataSource View document.
     * @return A count of the number of rows effected by this operation.
     * @throws DatabaseException
     */
    int deleteRow(Object _obj) throws DatabaseException;

    /**
     * Sets the data connector object which serves as a conduit between the application 
     * and an external data source provider.
     * 
     * @param value The conncector.
     */
    void setConnector(Object value);

    /**
     * Gets the data connector object
     * 
     * @return {@link DatabaseConnectionBean}
     */
    Object getConnector();

    /**
     * Sets the data source name for this DAO. 
     *    
     * @param dsn The name of the data source
     */
    void setDataSourceName(String dsn);

    /**
     * Gets the identity of this DAO by is data source name.
     * 
     * @return Data source name.
     */
    String getDataSourceName();

    /**
     * Sets the base view name of this datasource.
     * 
     * @param value String
     * @return The previous name.
     */
    String setBaseView(String value);

    /**
     * Gets the base view name of this datasource.
     * 
     * @return The previous name.
     */
    String getBaseView();

    /**
     * Sets the base class name for this datasource.
     * 
     * @param value String
     * @return The previous name
     */
    String setBaseClass(String value);

    /**
     * Gets the base class name for this datasource.
     * 
     * @return String
     */
    String getBaseClass();

    /**
     * Gets the data source attribute object
     * 
     * @return {@link ObjectMapperAttrib}
     */
    ObjectMapperAttrib getDataSourceAttib();

    /**
     * Sets the result set type.
     * 
     * @param value
     */
    void setResultType(int value);

    /**
     * Gets the result set type.
     * 
     * @return int
     */
    int getResultType();

    /**
     * Positions internal dataset to the first row.
     * 
     * @return true if the row is valid and false if there are no rows in the dataset 
     * @throws DatabaseException
     * @throws SystemException
     */
    boolean firstRow() throws DatabaseException, SystemException;

    /**
     * Positions internal dataset to the last row.
     * 
     * @return true if the row is valid and false if there are no rows in the dataset.
     * @throws DatabaseException
     * @throws SystemException
     */
    boolean lastRow() throws DatabaseException, SystemException;

    /**
     * Positions internal dataset to the next row.
     * 
     * @return true if the row is valid and false if there are no more rows.
     * @throws DatabaseException
     * @throws SystemException
     */
    boolean nextRow() throws DatabaseException, SystemException;

    /**
     * Positions internal dataset to the next row.
     * 
     * @return true if the row is valid and false if there are no more rows.
     * @throws DatabaseException
     * @throws SystemException
     */
    boolean previousRow() throws DatabaseException, SystemException;

    /**
     * Sets the value of the datasource's target column, _property.
     * 
     * @param _property The property name.
     * @param value The valus to set for property.
     * @throws SystemException
     * @throws NotFoundException
     * @throws DatabaseException
     */
    void setColumnValue(String _property, Object value) throws SystemException, NotFoundException, DatabaseException;

    /**
     * Retreives the column value from the datasource based on the input 
     * property, _property.  The value is returned to the caller as type String.
     * 
     * @param _property The property name.
     * @return The value of property
     * @throws DatabaseException
     * @throws NotFoundException
     * @throws SystemException
     */
    String getColumnValue(String _property) throws DatabaseException, NotFoundException, SystemException;

    /**
     * Retrieves value as an InputStream from the data source based on the property name.  
     * 
     * @param property
     *         The name of the property to retrieve
     * @return
     *         InputStream
     * @throws DatabaseException
     * @throws NotFoundException
     * @throws SystemException
     */
    Object getColumnBinaryValue(String property) throws DatabaseException, NotFoundException, SystemException;

} // End interface
