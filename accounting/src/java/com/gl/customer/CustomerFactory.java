package com.gl.customer;

import com.bean.Customer;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.api.db.orm.DataSourceAdapter;

import com.util.SystemException;

import com.controller.Request;
import com.controller.Response;

/**
 * Factory for creating General Ledger related resources.
 * 
 * @author RTerrell
 *
 */
public class CustomerFactory extends DataSourceAdapter {

    /**
     * Factory method for creating an instance of CustomerApi using a 
     * DatabaseConnectionBean object.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @return {@link com.gl.customer.CustomerApi CustomerApi} or null if instance 
     *         cannot be created.
     * @throws SystemException
     * @throws DatabaseException
     */
    public static CustomerApi createApi(DatabaseConnectionBean dbo) {
	try {
	    CustomerApi api = new CustomerImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Factory method for creating an instance of CustomerApi using a DatabaseConnectionBean 
     * object and a Request.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req HttpServletRequest object.
     * @return {@link com.gl.customer.CustomerApi CustomerApi} or null if instance 
     *         cannot be created.
     */
    public static CustomerApi createApi(DatabaseConnectionBean dbo, Request req) {
	try {
	    CustomerApi api = new CustomerImpl(dbo, req);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Factory method for creating an instance of CustomerApi using a DatabaseConnectionBean 
     * object, a Request and a Response object.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req Request object.
     * @param resp Respones object.
     * @return {@link com.gl.customer.CustomerApi CustomerApi} or null if instance 
     *         cannot be created.
     */
    public static CustomerApi createApi(DatabaseConnectionBean dbo, Request req, Response resp) {
	try {
	    CustomerApi api = new CustomerImpl(dbo, req, resp);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Factory method for creating a XML instance of CustomerApi using a 
     * DatabaseConnectionBean object.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @return {@link com.gl.customer.CustomerApi CustomerApi} or null if instance 
     *         cannot be created.
     * @throws SystemException
     * @throws DatabaseException
     * @deprecated Will be removed in future versions
     * 
     */
    public static CustomerApi createXmlApi(DatabaseConnectionBean dbo) {
	try {
	    CustomerApi api = new CustomerXmlImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Factory method for creating a XML instance of CustomerApi using a DatabaseConnectionBean 
     * object and a Request.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req HttpServletRequest object.
     * @return {@link com.gl.customer.CustomerApi CustomerApi} or null if instance 
     *         cannot be created.
     * @deprecated Will be removed in future versions        
     */
    public static CustomerApi createXmlApi(DatabaseConnectionBean dbo, Request req) {
	try {
	    CustomerApi api = new CustomerXmlImpl(dbo, req);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Factory method for creating a XML instance of CustomerApi using a DatabaseConnectionBean 
     * object, a Request and a Response object.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req Request object.
     * @param resp Respones object.
     * @return {@link com.gl.customer.CustomerApi CustomerApi} or null if instance 
     *         cannot be created.
     * @deprecated Will be removed in future versions
     */
    public static CustomerApi createXmlApi(DatabaseConnectionBean dbo, Request req, Response resp) {
	try {
	    CustomerApi api = new CustomerXmlImpl(dbo, req, resp);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of CustomerHelper which is initialized with a DatabaseConnectionBean 
     * object.
     * 
     * @param dbCon  The databse connection
     * @return {@link CustomerHelper}
     */
    public static CustomerHelper createCustomerHelper(DatabaseConnectionBean dbCon) {
	try {
	    CustomerHelper obj = new CustomerHelper(dbCon);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates an instance of CustomerHelper which is initialized with a Request, Response,
     *  and DatabaseConnectionBean objects.
     * 
     * @param request The user'srequest
     * @param response The user's response
     * @param dbCon The database connection
     * @return {@link CustomerHelper}
     */
    public static CustomerHelper createCustomerHelper(Request request, Response response, DatabaseConnectionBean dbCon) {
	try {
	    CustomerHelper obj = new CustomerHelper(request, response, dbCon);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Factory method for creating a Customer object.
     * 
     * @return {@link com.gl.customer.Customer Customer} or null if instance 
     *         cannot be created.
     */
    public static Customer createCustomer() {
	try {
	    Customer obj = new Customer();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Factory method for creating a Customer object that is able to manage XML data.
     * 
     * @return {@link com.gl.customer.Customer Customer} or null if instance 
     *         cannot be created.
     */
    public static Customer createXmlCustomer() {
	Customer obj = CustomerFactory.createCustomer();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

}
