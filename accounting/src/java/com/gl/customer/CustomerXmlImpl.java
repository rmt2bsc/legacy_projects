package com.gl.customer;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;

import com.api.db.DatabaseException;

import com.util.SystemException;

import com.bean.Customer;

import com.bean.criteria.CustomerCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.xml.adapters.XmlAdapterFactory;
import com.api.xml.adapters.XmlBeanAdapter;

/**
 * An XML implementation of {@link com.gl.customer.CustomerApi CustomerApi}.  It provides 
 * functionality that creates, updates, deletes, and queries customer account entities.
 * 
 * @author RTerrell
 * @deprecated Will be removed in future versions
 *
 */
class CustomerXmlImpl extends RdbmsDaoImpl implements CustomerApi {

    private RdbmsDaoQueryHelper daoHelper;

    private Logger logger;

    private Response response;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public CustomerXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CustomerXmlImpl");
	this.logger.log(Level.DEBUG, "Logger created for CustomerXmlImpl");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and the 
     * HttpServletRequest object at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @param req The request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public CustomerXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CustomerXmlImpl");
    }

    /**
     * Construct a CustomerImpl object that contains an initialized database connection, 
     * request, and response.
     * 
     * @param dbConn The database connection
     * @param req The user's request
     * @param resp The user's response
     * @throws SystemException General system failures
     * @throws DatabaseException General database errors.
     */
    public CustomerXmlImpl(DatabaseConnectionBean dbConn, Request req, Response resp) throws SystemException, DatabaseException {
	this(dbConn, req);
	this.response = resp;
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CustomerImpl");
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findByBusinessId(int)
     */
    public String findByBusinessId(int businessId) throws CustomerException {
	Customer obj = CustomerFactory.createXmlCustomer();
	obj.addCriteria(Customer.PROP_BUSINESSID, businessId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findById(int)
     */
    public String findById(int id) throws CustomerException {
	Customer obj = CustomerFactory.createXmlCustomer();
	obj.addCriteria(Customer.PROP_CUSTOMERID, id);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findCustomer(java.lang.String)
     */
    public String findCustomer(String criteria) throws CustomerException {
	Customer obj = CustomerFactory.createXmlCustomer();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findCustomerBalance(int)
     */
    public double findCustomerBalance(int id) throws CustomerException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findCustomerBusiness(int)
     */
    public String findCustomerBusiness(int customerId) throws CustomerException {
	return null;
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findCustomerBusiness(java.lang.String)
     */
    public String findCustomerBusiness(String criteria) throws CustomerException {
	CustomerApi custImpl = CustomerFactory.createApi(this.connector, this.request);
	List list = (List) custImpl.findCustomerBusiness(criteria);
	String xml = null;
	try {
	    XmlBeanAdapter adpt = XmlAdapterFactory.createNativeAdapter();
	    xml = adpt.beanToXml(list);
	    return xml;
	}
	catch (SystemException e) {
	    throw new CustomerException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#deleteCustomer(int)
     */
    public int deleteCustomer(int custId) throws CustomerException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#maintainCustomer(com.bean.Customer, java.lang.Object)
     */
    public int maintainCustomer(Customer custBase, Object custExt) throws CustomerException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#validateCustomer(com.bean.Customer)
     */
    public void validateCustomer(Customer cust) throws CustomerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#validateCustomerExt(java.lang.Object)
     */
    public void validateCustomerExt(Object custExt) throws CustomerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findCustomerBusiness(com.bean.criteria.CustomerCriteria)
     */
    public Object findCustomerBusiness(CustomerCriteria criteria) throws CustomerException {
	// TODO Auto-generated method stub
	return null;
    }
}
