package com.xact.sales;

import java.util.List;

import com.api.db.DatabaseException;

import com.bean.SalesInvoice;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;
import com.bean.VwSalesOrderInvoice;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * This class implements the functionality required for querying sales order information in XML.
 *
 * @author RTerrell
 * @deprecated Will be removed in future versions.
 *
 */
public class SalesOrderManagerXmlImpl extends SalesOrderManagerApiImpl {

    /**
     * Default constructor
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public SalesOrderManagerXmlImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructs a sales order XML api with a specified database connection bean
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public SalesOrderManagerXmlImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
    }

    /**
     * Constructs a sales order XML api with a specified database connection bean and 
     * the user's request.
     *  
     * @param dbConn
     * @param request
     * @throws DatabaseException
     * @throws SystemException
     */
    public SalesOrderManagerXmlImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
    }

    /**
     * Retrieves sales order data using customer criteria.
     * 
     * @param criteria custom selection criteria.
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrder(String criteria) throws SalesOrderException {
	SalesOrder obj = SalesFactory.createXmlSalesOrder();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the items of a sales order using sales order id
     * 
     * @param _soId
     * @return XML document
     * @throws SalesOrderException
     * 
     */
    public String findSalesOrderItemsBySalesOrder(int soId) throws SalesOrderException {
	VwSalesorderItemsBySalesorder obj = SalesFactory.createVwSalesOrderItemsBySalesOrder();
	obj.addCriteria(VwSalesorderItemsBySalesorder.PROP_SOID, soId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves base sales order data from the database using sales order id
     * 
     * @param _soId Id of the sales order
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrderById(int soId) throws SalesOrderException {
	SalesOrder obj = SalesFactory.createSalesOrder();
	obj.addCriteria(SalesOrder.PROP_SOID, soId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves all sales order related to a given customer.
     * 
     * @param _custId  The id of the customer
     * @return XML document
     * @throws SalesOrderException
     */
    public List findSalesOrderByCustomer(int customerId) throws SalesOrderException {
	SalesOrder obj = SalesFactory.createSalesOrder();
	obj.addCriteria(SalesOrder.PROP_CUSTOMERID, customerId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves a list of extending sales orders related to a given customer.
     * 
     * @param customerId
     * @return XML document
     * @throws SalesOrderException
     */
    public String findExtendedSalesOrderByCustomer(int customerId) throws SalesOrderException {
	VwSalesOrderInvoice obj = SalesFactory.createVwSalesOrderInvoice();
	obj.addCriteria(VwSalesOrderInvoice.PROP_CUSTOMERID, customerId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves an list of invoiced sales orders based on customer id and invoiced flag.
     * 
     * @param customerId Id of the customer
     * @return XML document
     * @throws SalesOrderException
     */
    public String findInvoicedSalesOrderByCustomer(int customerId) throws SalesOrderException {
	String criteria = "customer_id = " + customerId + " and invoiced = 1";
	return this.findSalesOrder(criteria);
    }

    /**
     * Retrieves all items related to a sales order.
     * 
     * @param _soId The id of the sales order.
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrderItems(int soId) throws SalesOrderException {
	SalesOrderItems obj = SalesFactory.createSalesOrderItem();
	obj.addCriteria(SalesOrderItems.PROP_SOID, soId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves a single sales invoice related to a given sales order.
     * 
     * @param salesOrderId The id of the sales order to obtain the invoice.
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrderInvoice(int salesOrderId) throws SalesOrderException {
	SalesInvoice obj = SalesFactory.createSalesInvoice();
	obj.addCriteria(SalesInvoice.PROP_SOID, salesOrderId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the details of a sales order status by using its primary key identifier.
     * 
     * @param salesOrderStatusId The id of the sales order status.
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrderStatus(int salesOrderStatusId) throws SalesOrderException {
	SalesOrderStatus obj = SalesFactory.createSalesOrderStatus();
	obj.addCriteria(SalesOrderStatus.PROP_SOSTATUSID, salesOrderStatusId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the status history of sales order.
     *  
     * @param salesOrderId The id of the sales order
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrderStatusHistBySOId(int salesOrderId) throws SalesOrderException {
	SalesOrderStatusHist obj = SalesFactory.createSalesStatusHistory(salesOrderId);
	obj.addCriteria(SalesOrderStatusHist.PROP_SOID, salesOrderId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

//    /**
//     * Retrieves the current status of a sales order.
//     * 
//     * @param soId The id of the sales order to retreive status.
//     * @return XML document
//     * @throws SalesOrderException
//     */
//    public String findCurrentSalesOrderStatusHist(int soId) throws SalesOrderException {
//	String criteria = "sales_order_id = " + soId + " and end_date is null";
//	return this.findSalesOrderStatusHist(criteria);
//    }

    /**
     * Retrieves the sales order status history using custom criteria.
     * 
     * @param criteria The custom criteria
     * @return XML document
     * @throws SalesOrderException
     */
    public String findSalesOrderStatusHist(String criteria) throws SalesOrderException {
	SalesOrderStatusHist obj = SalesFactory.createXmlSalesStatusHistory(0);
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

}
