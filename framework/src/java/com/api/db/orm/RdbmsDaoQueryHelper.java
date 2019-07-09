package com.api.db.orm;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.DaoApi;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceFactory;
import com.api.db.pagination.PaginationApi;
import com.api.db.pagination.PaginationFactory;
import com.api.db.pagination.PaginationQueryResults;

import com.bean.OrmBean;
import com.bean.RMT2Base;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Provides generic database query access methods that interact with the DaoApi
 * interface. The return values rendered by this class can be a single object, a
 * list of objects, or a String in the form of a XML document.
 * 
 * @author RTerrell
 * 
 */
public class RdbmsDaoQueryHelper extends RMT2Base {
    private Logger logger;

    private DaoApi dao;

    private PaginationApi pageApi;

    /**
     * Default constructor that creates the logger.
     */
    public RdbmsDaoQueryHelper() {
        this.logger = Logger.getLogger("RdbmsDaoQueryHelper");
    }

    /**
     * Creates a RdbmsDaoQueryHelper object using an arbitrary connection object.
     * 
     * @param connection
     *            A generic object that represents a database connection.
     * @throws {@link com.util.SystemException SystemException}
     *             when connection is null or the connection instance is not a
     *             descendent of DatabaseConnectionBean.
     */
    public RdbmsDaoQueryHelper(Object connection) throws SystemException {
        this();
        if (connection == null) {
            this.msg = "RdbmsDaoQueryHelper class could not be instantiated due to invalid connection object";
            this.logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        if (!(connection instanceof DatabaseConnectionBean)) {
            this.msg = "RdbmsDaoQueryHelper class could not be instantiated due to invalid connection object";
            this.logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
        DatabaseConnectionBean con = (DatabaseConnectionBean) connection;
        this.dao = DataSourceFactory.createDao(con);
        this.pageApi = PaginationFactory.createDao(con);
    }

    public void close() {
        this.logger = null;
        this.dao.close();
        this.dao = null;
    }

    /**
     * Retrieves data from a RDBMS in the form of a single object.
     * 
     * @param obj
     *            The target ORM object used for data retrieval.
     * @return An arbitrary object or null if object is not found.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object retrieveObject(OrmBean obj) throws DatabaseException {
        if (obj == null) {
            return null;
        }
        try {
            Object data[] = this.dao.retrieve(obj);
            if (data != null && data.length > 0) {
                return data[0];
            }
            return null;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Problem retrieving single ORMBean instance data", e);
        }
    }

    /**
     * Retrieves data from a RDBMS in the form of a List.
     * 
     * @param obj
     *            The ORM object to retrieve data.
     * @return A List of arbitrary data objects
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public List retrieveList(OrmBean obj) throws DatabaseException {
        if (obj == null) {
            return null;
        }
        try {
            Object data[] = this.dao.retrieve(obj);
            if (data != null && data.length > 0) {
                return java.util.Arrays.asList(data);
            }
            return null;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Problem retrieving ORMBean List data", e);
        }
    }

    /**
     * Retrieves data from a RDBMS in the form of XML.
     * 
     * @param obj
     *            A generic instance of {@link com.bean.OrmBean OrmBean} that is
     *            used to retrieve data.
     * @return Results of the query as an XML document String.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public String retrieveXml(OrmBean obj) throws DatabaseException {
        if (obj == null) {
            return null;
        }
        // Force the ORM Api to return the results of processing "obj" as XML.
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        try {
            Object data[] = this.dao.retrieve(obj);
            if (data != null && data.length > 0) {
                return (String) data[0];
            }
            return null;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Problem retrieving ORMBean XML data", e);
        }
    }

    public long retrieveCount(OrmBean obj) throws DatabaseException {
        if (obj == null) {
            return -2;
        }
        try {
            // Get row count
            this.pageApi.setReturnRowCount(true);
            this.pageApi.setPageNo(0);
            Object data[] = this.pageApi.retrieve(obj);
            if (data != null && data.length > 0) {
                Long count = (Long) data[0];
                return count;
            }
            return -1;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Problem retrieving database count of ORMBean criteria", e);
        }
    }

    /**
     * 
     * @param obj
     * @param pageNo
     * @return
     * @throws DatabaseException
     * @deprecated 
     */
    public Object retrieveList(OrmBean obj, int pageNo) throws DatabaseException {
        if (obj == null) {
            return null;
        }

        this.pageApi.setPageNo(pageNo);
        // Do a non-pagination query when page number is less than or equal to zero
        if (pageNo <= 0) {
            return this.retrieveList(obj);
        }

        PaginationQueryResults pageObj = new PaginationQueryResults();
        pageObj.setPageNo(pageNo);
        try {
            // Get complete dataset
            this.pageApi.setReturnRowCount(false);
            Object data[] = this.pageApi.retrieve(obj);
            if (data != null && data.length > 0) {
                pageObj.setResults(java.util.Arrays.asList(data));
            }
            else {
                return null;
            }
            // Get row count
            Long count = this.retrieveCount(obj);
            pageObj.setTotalRowCount(count);
            return pageObj;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Problem retrieving ORMBean List data for page #" + pageNo, e);
        }
    }

    /**
     * 
     * @param obj
     * @param pageNo
     * @return
     * @throws DatabaseException
     * @deprecated 
     */
    public Object retrieveXml(OrmBean obj, int pageNo) throws DatabaseException {
        if (obj == null) {
            return null;
        }
        // Force the ORM Api to return the results of processing "obj" as XML.
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        try {
            Object data[] = this.pageApi.retrieve(obj);
            if (data != null && data.length > 0) {
                return (String) data[0];
            }
            return null;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Problem retrieving ORMBean XML data for page #" + pageNo, e);
        }
    }

}
