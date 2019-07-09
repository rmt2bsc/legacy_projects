package com.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.SalesInvoice;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;
import com.bean.VwSalesOrderInvoice;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.Xact;
import com.bean.Customer;
import com.bean.ItemMaster;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.inventory.InventoryApi;
import com.inventory.ItemConst;
import com.inventory.InventoryFactory;
import com.inventory.ItemMasterException;

import com.xact.XactConst;
import com.xact.XactException;

import com.controller.Request;

import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerFactory;

import com.util.RMT2Date;
import com.util.RMT2String;
import com.util.SystemException;

import com.xact.XactManagerApiImpl;
import com.xact.receipts.CashReceiptsApi;
import com.xact.receipts.CashReceiptsException;
import com.xact.receipts.ReceiptsFactory;

/**
 * This class implements the functionality required for creating, maintaining, cancelling, 
 * refunding, and trackings sales orders.
 *  <p>
 * When a sales order is invoiced, the base transaction amount is posted to the xact table 
 * as a positive value, and the customer activity amount is posted as a positive value which 
 * increases the value of the company's revenue and the customer's account.   Conversely, when 
 * a sales order is cancelled or refunded,  the base transaction amount is posted to the xact 
 * table as a negative value, and the customer activity amount is posted as negative value 
 * which decreases the value of the company's revenue and the customer's account.
 * 
 * @author Roy Terrell
 *
 */
public class SalesOrderManagerApiImpl extends XactManagerApiImpl implements SalesOrderApi {

    private Logger logger;

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected SalesOrderManagerApiImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructs a sales order api with a specified database connection bean
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public SalesOrderManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.setBaseView("SalesOrderItemsBySalesOrderView");
	this.setBaseClass("com.bean.SalesOrderItemsBySalesOrder");
	this.logger = Logger.getLogger(SalesOrderManagerApiImpl.class);
    }

    /**
     * Constructs a sales order api with the specified database connection bean and servlet request object
     * 
     * @param dbConn {@link DatabaseConnectionBean} object
     * @param _request Servlet request object
     * @throws DatabaseException
     * @throws SystemException
     */
    public SalesOrderManagerApiImpl(DatabaseConnectionBean dbConn, Request _request) throws DatabaseException, SystemException {
	this(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.setRequest(_request);
    }

    /**
     * 
     */
    public void close() {
	super.close();
    }

    /**
     * Retrieves sales order data using customer criteria.
     * 
     * @param criteria custom selection criteria.
     * @return List of SalesOrder objects.
     * @throws SalesOrderException
     */
    public Object findSalesOrder(String criteria) throws SalesOrderException {
	SalesOrder obj = SalesFactory.createSalesOrder();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the items of a sales order using sales order id
     * 
     * @param _soId
     * @return List of {@link VwSalesorderItemsBySalesorder} objects
     * @throws SalesOrderException
     * 
     */
    public Object findSalesOrderItemsBySalesOrder(int soId) throws SalesOrderException {
	VwSalesorderItemsBySalesorder obj = SalesFactory.createVwSalesOrderItemsBySalesOrder();
	obj.addCriteria(VwSalesorderItemsBySalesorder.PROP_SOID, soId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves base sales order data from the database using sales order id
     * 
     * @param _soId Id of the sales order
     * @return {@link SalesOrder} object
     * @throws SalesOrderException
     */
    public Object findSalesOrderById(int soId) throws SalesOrderException {
	SalesOrder obj = SalesFactory.createSalesOrder();
	obj.addCriteria(SalesOrder.PROP_SOID, soId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves all sales order related to a given customer.
     * 
     * @param _custId  The id of the customer
     * @return List of {@link SalesOrder} objects
     * @throws SalesOrderException
     */
    public Object findSalesOrderByCustomer(int customerId) throws SalesOrderException {
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
     * @return ArrayList of {@link CombinedSalesOrder} objects
     * @throws SalesOrderException
     */
    public Object findExtendedSalesOrderByCustomer(int customerId) throws SalesOrderException {
	try {
	    // Get sales order data
	    List list = null;
	    VwSalesOrderInvoice obj = SalesFactory.createVwSalesOrderInvoice();
	    obj.addCriteria(VwSalesOrderInvoice.PROP_CUSTOMERID, customerId);
	    obj.addOrderBy(VwSalesOrderInvoice.PROP_SALESORDERDATE, VwSalesOrderInvoice.ORDERBY_DESCENDING);
	    obj.addOrderBy(VwSalesOrderInvoice.PROP_SALESORDERID, VwSalesOrderInvoice.ORDERBY_DESCENDING);
	    obj.addOrderBy(VwSalesOrderInvoice.PROP_XACTID, VwSalesOrderInvoice.ORDERBY_DESCENDING);
	    try {
		list = this.daoHelper.retrieveList(obj);
		if (list == null) {
		    list = new ArrayList();
		}
	    }
	    catch (DatabaseException e) {
		throw new SalesOrderException(e);
	    }

	    List results = new ArrayList();
	    VwSalesOrderInvoice vsoi = null;
	    for (int ndx = 0; ndx < list.size(); ndx++) {
		vsoi = (VwSalesOrderInvoice) list.get(ndx);
		CombinedSalesOrder cso = new CombinedSalesOrder();
		cso.setOrderId(vsoi.getSalesOrderId());
		cso.setCustomerId(vsoi.getCustomerId());
		cso.setOrderDate(vsoi.getSalesOrderDate());
		cso.setIsInvoiced(vsoi.getInvoiced());
		cso.setXactId(vsoi.getXactId());
		cso.setInvoiceNo(vsoi.getInvoiceNo());
		cso.setInvoiceDate(vsoi.getInvoiceDate());
		cso.setStatusId(vsoi.getOrderStatusId());
		cso.setStatusDescription(vsoi.getOrderStatusDescr());
		cso.setXactAmount(vsoi.getOrderTotal());
		cso.setInvoiceId(vsoi.getInvoiceId());
		results.add(cso);
	    }
	    return results;
	}
	catch (SystemException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves a list of extending sales orders related to a given customer.
     * 
     * @param customerId
     * @return ArrayList of {@link CombinedSalesOrder} objects
     * @throws SalesOrderException
     */
    public Object findExtendedInvoicedSalesOrderByCustomer(int customerId) throws SalesOrderException {
	try {
	    // Get sales order data
	    List list = null;
	    VwSalesOrderInvoice obj = SalesFactory.createVwSalesOrderInvoice();
	    obj.addCriteria(VwSalesOrderInvoice.PROP_CUSTOMERID, customerId);
	    obj.addCriteria(VwSalesOrderInvoice.PROP_ORDERSTATUSID, SalesConst.STATUS_CODE_INVOICED);
	    obj.addOrderBy(VwSalesOrderInvoice.PROP_SALESORDERDATE, VwSalesOrderInvoice.ORDERBY_DESCENDING);
	    obj.addOrderBy(VwSalesOrderInvoice.PROP_SALESORDERID, VwSalesOrderInvoice.ORDERBY_DESCENDING);
	    obj.addOrderBy(VwSalesOrderInvoice.PROP_XACTID, VwSalesOrderInvoice.ORDERBY_DESCENDING);

	    try {
		list = this.daoHelper.retrieveList(obj);
		if (list == null) {
		    list = new ArrayList();
		}
	    }
	    catch (DatabaseException e) {
		throw new SalesOrderException(e);
	    }

	    List results = new ArrayList();
	    VwSalesOrderInvoice vsoi = null;
	    for (int ndx = 0; ndx < list.size(); ndx++) {
		vsoi = (VwSalesOrderInvoice) list.get(ndx);
		CombinedSalesOrder cso = new CombinedSalesOrder();
		cso.setOrderId(vsoi.getSalesOrderId());
		cso.setCustomerId(vsoi.getCustomerId());
		cso.setOrderDate(vsoi.getSalesOrderDate());
		cso.setIsInvoiced(vsoi.getInvoiced());
		cso.setXactId(vsoi.getXactId());
		cso.setInvoiceNo(vsoi.getInvoiceNo());
		cso.setInvoiceDate(vsoi.getInvoiceDate());
		cso.setStatusId(vsoi.getOrderStatusId());
		cso.setStatusDescription(vsoi.getOrderStatusDescr());
		cso.setXactAmount(vsoi.getOrderTotal());
		results.add(cso);
	    }
	    return results;
	}
	catch (SystemException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves an list of invoiced sales orders based on customer id and invoiced flag.
     * 
     * @param customerId Id of the customer
     * @return List of {@link VwSalesOrderInvoice} objects
     * @throws SalesOrderException
     */
    public Object findInvoicedSalesOrderByCustomer(int customerId) throws SalesOrderException {
	String criteria = "customer_id = " + customerId + " and invoiced = 1";
	List list = (List) this.findSalesOrder(criteria);
	return list;
    }

    /**
     * Retrieves all items related to a sales order.
     * 
     * @param _soId The id of the sales order.
     * @return  List of {@link SalesOrderItems} objects
     * @throws SalesOrderException
     */
    public Object findSalesOrderItems(int soId) throws SalesOrderException {
	SalesOrderItems obj = SalesFactory.createSalesOrderItem();
	obj.addCriteria(SalesOrderItems.PROP_SOID, soId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves a single sales invoice related to a given sales order.
     * 
     * @param salesOrderId The id of the sales order to obtain the invoice.
     * @return {@link SalesInvoice} object.
     * @throws SalesOrderException
     */
    public Object findSalesOrderInvoice(int salesOrderId) throws SalesOrderException {
	SalesInvoice obj = SalesFactory.createSalesInvoice();
	obj.addCriteria(SalesInvoice.PROP_SOID, salesOrderId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the details of a sales order status by using its primary key identifier.
     * 
     * @param salesOrderStatusId The id of the sales order status.
     * @return {@link SalesOrderStatus} object.
     * @throws SalesOrderException
     */
    public Object findSalesOrderStatus(int salesOrderStatusId) throws SalesOrderException {
	SalesOrderStatus obj = SalesFactory.createSalesOrderStatus();
	obj.addCriteria(SalesOrderStatus.PROP_SOSTATUSID, salesOrderStatusId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the status history of sales order.
     *  
     * @param salesOrderId The id of the sales order
     * @return List of {@link SalesOrderStatusHist} objects
     * @throws SalesOrderException
     */
    public Object findSalesOrderStatusHistBySOId(int salesOrderId) throws SalesOrderException {
	SalesOrderStatusHist obj = SalesFactory.createSalesStatusHistory(salesOrderId);
	obj.addCriteria(SalesOrderStatusHist.PROP_SOSTATUSHISTID, salesOrderId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the current status of a sales order.
     * 
     * @param soId The id of the sales order to retreive status.
     * @return {@link SalesOrderStatusHist} or null if empty result set.
     * @throws SalesOrderException
     */
    public SalesOrderStatusHist findCurrentSalesOrderStatusHist(int soId) throws SalesOrderException {
        SalesOrderStatusHist obj = SalesFactory.createSalesStatusHistory(soId);
        String criteria = "so_id = " + soId + " and end_date is null";
        obj.addCriteria(SalesOrderStatusHist.PROP_SOID, soId);
        obj.addCustomCriteria(criteria);
        try {
            return (SalesOrderStatusHist) this.daoHelper.retrieveObject(obj);
        }
        catch (DatabaseException e) {
            throw new SalesOrderException(e);
        }
    }

    /**
     * Retrieves the sales order status history using custom criteria.
     * 
     * @param criteria The custom criteria
     * @return A List of {@link SalesOrderStatusHist} objects.
     * @throws SalesOrderException
     */
    public Object findSalesOrderStatusHist(String criteria) throws SalesOrderException {
	SalesOrderStatusHist obj = SalesFactory.createSalesStatusHistory(0);
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Drives the process of creating a new or modifying an existing Sales Order.     The Customer Id must contain a 
     * value > zero, and there must be at least on item to process if we are creating a Sales Order.  _items is an 
     * ArrayList of SalesOrder items that must created prior to invoking this method.
     * 
     * @param so
     * @param cust
     * @param items
     * @return Sales order id.
     * @throws SalesOrderException
     */
    public int maintainSalesOrder(SalesOrder so, Customer cust, List items) throws SalesOrderException {
	int soId;

	if (so == null) {
	    throw new SalesOrderException("Sales order cannot be null");
	}
	//  Customer Id must be a value greater than zero.
	if (cust != null && cust.getCustomerId() <= 0) {
	    throw new SalesOrderException(this.connector, 701, null);
	}

	// Verify that new sales order will have items.
	if (items != null && items.size() > 0) {
	    //  Items are available to be processed.
	}
	else {
	    this.msg = "There are not items to process for this order: " + so.getSoId();
	    throw new SalesOrderException(this.msg, 702);
	}

	// Determine if we are creating or modifying a Sales Order.
	if (so.getSoId() <= 0) {
	    soId = this.createSalesOrder(so, cust, items);
	}
	else {
	    soId = this.updateSalesOrder(so, cust, items);
	}

	return soId;
    }

    /**
     * Creates a sales order using a sales order object, customer object, and a list of sales order item objects.
     * 
     * @param _so Sales order object
     * @param _cust Customer object
     * @param _items List of sales order item objects
     * @return The sales order id.
     * @throws SalesOrderException
     */
    protected int createSalesOrder(SalesOrder _so, Customer _cust, List _items) throws SalesOrderException {
	int soId = 0;

	soId = this.createSalesOrder(_so, _cust);
	this.createSalesOrderItems(soId, _items);
	this.changeSalesOrderStatus(soId, SalesConst.STATUS_CODE_QUOTE);
	return soId;
    }

    /**
     * Creates a sales order.
     * 
     * @param _so Sales order object
     * @param _cust Customer object
     * @return Sales order id
     * @throws SalesOrderException
     */
    private int createSalesOrder(SalesOrder so, Customer cust) throws SalesOrderException {
	int soId = 0;
	Object temp = null;
	CustomerApi custApi = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	try {
	    custApi = CustomerFactory.createApi(this.connector);
	    temp = custApi.findById(cust.getCustomerId());
	    if (temp == null) {
		this.msg = "Sales Order cannot be add, because customer is invalid";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    so.setCustomerId(cust.getCustomerId());
	    // This condition is for preserving the create date of those 
	    // transaction originating from the Transaction Loader.
	    if (so.getDateCreated() == null) {
		so.setDateCreated(ut.getDateCreated());
	    }

	    so.setDateUpdated(ut.getDateCreated());
	    so.setUserId(ut.getLoginId());
	    so.setIpCreated(ut.getIpAddr());
	    so.setIpUpdated(ut.getIpAddr());
	    soId = dao.insertRow(so, true);
	    return soId;
	}
	catch (Exception e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Assoicates one or more items to a sales order.
     * 
     * @param soId
     * @param items
     * @return
     * @throws SalesOrderException
     */
    private double createSalesOrderItems(int soId, List items) throws SalesOrderException {
	SalesOrderItems soi = null;
	int itemCount = items.size();
	double soAmount = 0;

	this.deleteSalesOrderItems(soId);
	for (int ndx = 0; ndx < itemCount; ndx++) {
	    soi = (SalesOrderItems) items.get(ndx);
	    soi.setSoId(soId);
	    this.createSalesOrderItem(soi);
	    soAmount += soi.getOrderQty() * (soi.getInitUnitCost() * soi.getInitMarkup());
	}
	return soAmount;
    }

    /**
     * Creates a sales order item.
     * 
     * @param _soi
     * @return
     * @throws SalesOrderException
     */
    private int createSalesOrderItem(SalesOrderItems soi) throws SalesOrderException {
	double backOrderQty = 0;
	int rc = 0;
	ItemMaster im = null;
	InventoryApi itemApi = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	try {
	    itemApi = InventoryFactory.createApi(this.connector);
	    im = (ItemMaster) itemApi.findItemById(soi.getItemId());

	    // Compare order quantity of item to actual inventory quantity on hand to determine if back order is required.
	    if (soi.getOrderQty() > im.getQtyOnHand()) {
		backOrderQty = soi.getOrderQty() - im.getQtyOnHand();
		soi.setBackOrderQty(backOrderQty);
		//_soi.setOrderQty(im.getQtyOnHand());
	    }
	    // Apply to database
	    if (soi.getInitMarkup() <= 0) {
		soi.setInitMarkup(im.getMarkup());
	    }
	    if (soi.getInitUnitCost() <= 0) {
		soi.setInitUnitCost(im.getUnitCost());
	    }
	    rc = dao.insertRow(soi, true);
	    return rc;
	}
	catch (Exception e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Updates a sales order based on the customer and sales order items.
     * 
     * @param _so
     * @param _cust
     * @param _items
     * @return
     * @throws SalesOrderException
     */
    protected int updateSalesOrder(SalesOrder so, Customer cust, List items) throws SalesOrderException {
	double orderAmount = 0;

	orderAmount = this.createSalesOrderItems(so.getSoId(), items);
	so.setOrderTotal(orderAmount);
	this.updateSalesOrder(so, cust);

	// No need to update status at this point.
	return so.getSoId();
    }

    /**
     * Updates a sales order based on customer.
     * 
     * @param _so The sales order object
     * @param _cust The customer object
     * @return The total number of rows effected by the transaction.
     * @throws SalesOrderException
     */
    private int updateSalesOrder(SalesOrder so, Customer cust) throws SalesOrderException {
	int rc = 0;
	Object temp = null;
	CustomerApi custApi = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	try {
	    custApi = CustomerFactory.createApi(this.connector);
	    temp = custApi.findById(cust.getCustomerId());
	    if (temp == null) {
		this.msg = "Sales Order cannot be update, because customer is invalid";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    so.setCustomerId(cust.getCustomerId());
	    so.setDateUpdated(ut.getDateCreated());
	    so.setUserId(ut.getLoginId());
	    so.setIpUpdated(ut.getIpAddr());
	    so.addCriteria(SalesOrder.PROP_SOID, so.getSoId());
	    rc = dao.updateRow(so);
	    return rc;
	}
	catch (Exception e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Deletes a sales order including its items.  <p>The sales order must be currently 
     * in quote status in order to be deleted.
     * 
     * @param _salesOrder  Sales Order object to delete.
     * @throws SalesOrderException 
     *           When Sales Order status is something other than Quote status or for 
     *           general database access error.
     */
    public void deleteSalesOrder(SalesOrder salesOrder) throws SalesOrderException {
	if (salesOrder == null) {
	    this.msg = "Delete Failed:  Sales order object is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SalesOrderException(this.msg);
	}

	//  Sales order must be in quote or new status to be deleted.
	SalesOrderStatusHist sosh = (SalesOrderStatusHist) this.findCurrentSalesOrderStatusHist(salesOrder.getSoId());
	SalesOrderStatus sos = (SalesOrderStatus) this.findSalesOrderStatus(sosh.getSoStatusId());
	switch (sos.getSoStatusId()) {
	case SalesConst.STATUS_CODE_QUOTE:
	case SalesConst.STATUS_CODE_NEW:
	    break;
	default:
	    this.msg = "Sales order must be in quote status in order to be deleted.  Sales Order Id:  " + salesOrder.getSoId();
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SalesOrderException(this.msg);
	}

	int rc = 0;
	this.deleteSalesOrderStatuses(salesOrder.getSoId());
	this.deleteSalesOrderItems(salesOrder.getSoId());
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	try {
	    salesOrder.addCriteria(SalesOrder.PROP_SOID, salesOrder.getSoId());
	    rc = dao.deleteRow(salesOrder);
	    logger.log(Level.DEBUG, "Total number of sales orders deleted: " + rc);
	}
	catch (DatabaseException e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Deletes one or more items from a sales order.
     * 
     * @param _soId The id of the sales order
     * @return A count of all items deleted.
     * @throws SalesOrderException
     */
    private int deleteSalesOrderItems(int soId) throws SalesOrderException {
	SalesOrderItems soi = null;
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	try {
	    soi = SalesFactory.createSalesOrderItem();
	    soi.resetCriteria();
	    soi.addCriteria(SalesOrderItems.PROP_SOID, soId);
	    rc = dao.deleteRow(soi);
	    logger.log(Level.DEBUG, "Total number of sales order items deleted: " + rc);
	    return rc;
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Deletes all statuses assoicated with a sales order.
     * 
     * @param _soId The id of the sales order.
     * @return The count of all statuses removed from the sales order. 
     * @throws SalesOrderException General database access error.
     */
    private int deleteSalesOrderStatuses(int soId) throws SalesOrderException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	try {
	    SalesOrderStatusHist sosh = SalesFactory.createSalesStatusHistory(soId);
	    sosh.resetCriteria();
	    sosh.addCriteria(SalesOrderStatusHist.PROP_SOID, sosh.getSoId());
	    rc = dao.deleteRow(sosh);
	    logger.log(Level.DEBUG, "Total number of sales order statuses deleted: " + rc);
	    return rc;
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Changes the status of a sales order to the value of <i>newStatusId</i>
     * 
     * @param soId  
     *          The id of sales order
     * @param newStatusId 
     *          The id of the status to apply to the sales order
     * @return {@link SalesOrderStatusHist} object of the status before change
     * @throws SalesOrderException 
     *          When newStatusId is considered out of sequence, a database error 
     * 			occurrence, or a System failure.
     */
    public SalesOrderStatusHist changeSalesOrderStatus(int soId, int newStatusId) throws SalesOrderException {
	SalesOrderStatusHist sosh = null;
	SalesOrderStatus sos = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	//Validate new status id value
	sos = (SalesOrderStatus) this.findSalesOrderStatus(newStatusId);
	if (sos == null) {
	    this.msg = "Problem changing sales order status...new status id (" + newStatusId + ") is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SalesOrderException(this.msg);
	}

	this.verifyStatusChange(soId, newStatusId);

	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // End current status, if available
	    sosh = (SalesOrderStatusHist) this.findCurrentSalesOrderStatusHist(soId);
	    if (sosh != null) {
		sosh.setEndDate(ut.getDateCreated());
		sosh.setUserId(ut.getLoginId());
		sosh.addCriteria(SalesOrderStatusHist.PROP_SOSTATUSHISTID, sosh.getSoStatusHistId());
		sosh.setIpUpdated(ut.getIpAddr());
		dao.updateRow(sosh);
	    }

	    // Begin a new status.
	    sosh = SalesFactory.createSalesStatusHistory(soId);
	    sosh.setSoId(soId);
	    sosh.setSoStatusId(newStatusId);
	    sosh.setEffectiveDate(ut.getDateCreated());
	    sosh.setNull("endDate");
	    sosh.setDateCreated(ut.getDateCreated());
	    sosh.setUserId(ut.getLoginId());
	    sosh.setIpCreated(ut.getIpAddr());
	    sosh.setIpUpdated(ut.getIpAddr());
	    dao.insertRow(sosh, true);
	    return sosh;
	}
	catch (Exception e) {
	    throw new SalesOrderException(e);
	}
    }

    protected void preCreateXact(Xact _xact) {
	super.preCreateXact(_xact);
	if (_xact.getTenderId() == 0) {
	    _xact.setNull("tenderId");
	}

	// At this point, cash payments received should be a negative amount.   Every other 
	// sales order transaction should of handled xact amount before reaching this method.
	if (_xact.getXactTypeId() == XactConst.XACT_TYPE_CASHPAY && _xact.getXactSubtypeId() == 0) {
	    _xact.setXactAmount(_xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER);
	}
	return;
    }

    public int cancelSalesOrder(int soId) throws SalesOrderException {
        SalesOrderStatusHist hist =  this.findCurrentSalesOrderStatusHist(soId);
        if (hist == null || hist.getSoStatusId() != SalesConst.STATUS_CODE_INVOICED) {
            this.msg = "Sales order cannot be cancelled due to current sales order is not in \"Invoiced\" status";
            logger.error(this.msg);
            throw new SalesOrderException(this.msg);
        }
	Xact rc = this.cancelSalesOrder(soId, XactConst.XACT_TYPE_CANCEL);

	// Change sales order status to cancelled
	this.changeSalesOrderStatus(soId, SalesConst.STATUS_CODE_CANCELLED);
	return rc.getXactId();
    }

    public int refundSalesOrder(int soId) throws SalesOrderException {
        SalesOrderStatusHist hist =  this.findCurrentSalesOrderStatusHist(soId);
        if (hist == null || hist.getSoStatusId() != SalesConst.STATUS_CODE_CLOSED) {
            this.msg = "Sales order cannot be refunded due to current sales order is not in \"Closed\" status";
            logger.error(this.msg);
            throw new SalesOrderException(this.msg);
        }
        Xact soXact = this.cancelSalesOrder(soId, XactConst.XACT_TYPE_SALESRETURNS);
        int soRc = soXact.getXactId();
	
        // Create cash receipt transaction of the entire amount of sales order being reversed.   
        // This will offset the original cash receipt transaction that was created along with 
        // sales order that is currently being reversed. 
	CashReceiptsApi crApi = ReceiptsFactory.createCashReceiptsApi(this.connector, this.request);
	// The amount should not require any changes since it is expected to have a negative value.
	soXact.setXactId(0);
	soXact.setXactSubtypeId(XactConst.XACT_TYPE_SALESRETURNS);
	soXact.setReason("Reversed trans amount related to the refunding of sales order " + soId);
	SalesOrder so = (SalesOrder) this.findSalesOrderById(soId);
	try {
            crApi.maintainCustomerPayment(soXact, so.getCustomerId());
        }
        catch (CashReceiptsException e) {
            this.msg = "Unable to apply cash receipt reversal for the refunding of sales order, " + soId;
            logger.error(this.msg);
            throw new SalesOrderException(this.msg, e);
        }
        finally {
            crApi = null;
        }

	// Change sales order status to refunded
	this.changeSalesOrderStatus(soId, SalesConst.STATUS_CODE_REFUNDED);
	return soRc;
    }

    /**
     * Reverses an invoiced sales order for the appropriate transaction types: cancellations and refunds.
     * The functionality basically resets the invoice flag of a sales order, pushes the sales order 
     * items back to inventory, applies the entire sales order amount as a reversal transaction 
     * including customer activity, and finalizes the transaction.   An exception is thrown if the
     * the most recent transaction of the sales order is found to be finalized (sales order can not be 
     * futher modified).
     * . 
     * @param soId 
     *         the unique identifier of the sales order to reverse.
     * @param xactTypeId
     *         the type of transaction that the reversal is targeting
     * @return the newly reversed transaction instance. 
     * @throws SalesOrderException
     *           <ul>
     *             <li>sales order does not exist</li>
     *             <li>sales order is not in invoiced status or sales order invoice record is unobtainable</li>
     *             <li>the most recent transaction is found to be finalized</li>
     *             <li>general database errors</li>
     *             </li>general transaction errors</li>
     *           </ul>    
     */
    protected Xact cancelSalesOrder(int soId, int xactTypeId) throws SalesOrderException {
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	SalesInvoice si = null;
	SalesOrder so = null;
	Xact xact = null;
	int newXactId = 0;
	
	// Verify that sales order exist and is invoiced.
	so = (SalesOrder) this.findSalesOrderById(soId);
	if (so == null) {
	    this.msg = "Problem cancelling sales order.  Sales order is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SalesOrderException(this.msg);
	}
	if (so.getInvoiced() != SalesConst.INVOICED_FLAG_YES) {
	    throw new SalesOrderException("Problem cancelling sales order.  Sales order must be in Invoiced status");
	}
	logger.log(Level.DEBUG, "Sales order was found to be invoiced");

	// Switch the invoice flag Change Sales Order base.
	try {
	    so.setInvoiced(SalesConst.INVOICED_FLAG_NO);
	    so.addCriteria(SalesOrder.PROP_SOID, so.getSoId());
	    dao.updateRow(so);
	}
	catch (DatabaseException e) {
	    throw new SalesOrderException("Problem cancelling sales order.  Sales order base could not be updated.  Technical error: " + e.getMessage());
	}
	logger.log(Level.DEBUG, "Sales order invoice flag was turned off");

	// Put back inventory for each sales order item
	this.updateInventory(so.getSoId(), SalesConst.UPDATE_INV_DEPLETE);
	logger.log(Level.DEBUG, "Sales order items were pushed back to inventory");

	// Get Original Transaction and reverse it.
	si = (SalesInvoice) this.findSalesOrderInvoice(so.getSoId());
	if (si == null) {
	    this.msg = "Problem cancelling sales order.  Unable to fetch sales order invoice record";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SalesOrderException(this.msg);
	}
	try {
	    xact = this.findXactById(si.getXactId());
	    // Cannot modify or adjust a transaction that has been finalized.
	    if (!this.isXactModifiable(xact)) {
		msg = "Sales Order cannot be cancelled, because its associated transaction has been finalized";
		logger.log(Level.ERROR, msg);
		throw new SalesOrderException(msg);
	    }
	    
	    // Flag this transaction to be final. 
	    this.finalizeXact(xact);
	    // Apply the revesed transaction amount
	    xact.setXactAmount(xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER);
	    xact.setXactSubtypeId(xactTypeId);
	    switch (xactTypeId) {
	    case XactConst.XACT_TYPE_CANCEL:
		xact.setReason("Cancelled Sales Order " + so.getSoId());
		break;
	    case XactConst.XACT_TYPE_SALESRETURNS:
		xact.setReason("Refunded Sales Order " + so.getSoId());
		break;
	    }

	    // Create transaction
	    newXactId = this.maintainXact(xact, null);
	    logger.log(Level.DEBUG, "Sales order transaction was cancelled.  New transaction id is: " + newXactId);

	    //Reverse customer activity
	    this.createCustomerActivity(so.getCustomerId(), newXactId, xact.getXactAmount());
	    logger.log(Level.DEBUG, "Sales order customer activity was reversed");
	}
	catch (XactException e) {
	    throw new SalesOrderException(e);
	}
	xact.setXactId(newXactId);
	return xact;
    }

    /**
     * Generates an invoice number in theformat of <sales order id>-<yyyymmdd> 
     * 
     * @param _so sales order object
     * @return Invoice number
     * @throws SalesOrderException Problem creating the date part
     */
    private String createInvoiceNumber(SalesOrder so) throws SalesOrderException {
	String datePart = null;
	String invoiceNumber = null;
	java.util.Date today = new java.util.Date();
	datePart = RMT2Date.combineDateParts(today);
	if (datePart == null) {
	    this.msg = "Problem assigning an invoice number";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SalesOrderException(this.msg);
	}
	invoiceNumber = so.getSoId() + "-" + datePart;
	return invoiceNumber;
    }

    /**
     * Creates a sales invoice using _so and _xactId.   When invoicing a sales order, a 
     * transaction will be created which includes a positive transaction amount being 
     * posted to the transaction table and the customer activity table.
     * 
     * @param _so Sales Order object
     * @param _xact New transaction object
     * @return The invoice id.
     * @throws SalesOrderException
     */
    public int invoiceSalesOrder(SalesOrder so, Xact xact) throws SalesOrderException {
	int rc = 0;
	int xactId = 0;
	double revXactAmount = 0;
	Xact x = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	try {
	    // Ensure that xact type and amount are set
	    xact.setXactAmount(so.getOrderTotal());
	    xact.setXactTypeId(XactConst.XACT_TYPE_SALESONACCTOUNT);

	    // Create Transaction
	    xactId = this.maintainXact(xact, null);
	    x = this.findXactById(xactId);
	    if (x == null) {
		this.msg = "Problem invoicing sales order because transaction could not be verified";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }

	    // Take care of any single quote literals.
	    String reason = RMT2String.replaceAll(x.getReason(), "''", "'");
	    x.setReason(reason);

	    // Revise transaction amount in the event other charges were included.
	    revXactAmount = this.getSalesOrderTotal(so.getSoId());
	    x.setXactAmount(revXactAmount);
	    x.setNull("tenderId");
	    x.setNull("xactSubtypeId");
	    x.addCriteria(Xact.PROP_XACTID, x.getXactId());
	    dao.updateRow(x);

	    // Create customer activity regarding sale order transaction.
	    this.createCustomerActivity(so.getCustomerId(), xactId, x.getXactAmount());

	    // Update xact paramter's xactAmount
	    xact.setXactAmount(revXactAmount);
	}
	catch (Exception e) {
	    throw new SalesOrderException("Invoicing of sales order failed", e);
	}

	// Setup invoice
	try {

	    String invoiceNumber = this.createInvoiceNumber(so);
	    SalesInvoice si = SalesFactory.createSalesInvoice();
	    si.setInvoiceNo(invoiceNumber);
	    si.setSoId(so.getSoId());
	    si.setXactId(x.getXactId());
	    si.setDateCreated(x.getXactDate());
	    si.setDateUpdated(x.getDateCreated());
	    si.setUserId(x.getUserId());
	    si.setIpCreated(request.getRemoteAddr());
	    si.setIpUpdated(request.getRemoteAddr());
	    rc = dao.insertRow(si, true);

	    //Flag Sales order base as invoiced and update sales order total with transaction amount
	    so.setOrderTotal(x.getXactAmount());
	    so.setInvoiced(SalesConst.INVOICED_FLAG_YES);
	    so.setDateUpdated(x.getDateCreated());
	    so.setUserId(x.getUserId());

	    so.addCriteria(SalesOrder.PROP_SOID, so.getSoId());
	    dao.updateRow(so);

	    // Updaet Sales Order status to invoiced
	    this.changeSalesOrderStatus(so.getSoId(), SalesConst.STATUS_CODE_INVOICED);

	    // Update Inventory
	    this.updateInventory(so.getSoId(), SalesConst.UPDATE_INV_ADD);
	    return rc;
	}
	catch (Exception e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Decreases inventory by the sales order item order quantity.  Applies to only those sales 
     * order items that are of type "Merchandise".
     * 
     * @param _soId
     * @return  Total count of merchandise items processed.
     * @throws SalesOrderException
     */
    private int updateInventory(int _soId, int _action) throws SalesOrderException {
	SalesOrderItems soi = null;
	ItemMaster im = null;
	int merchCount = 0;

	try {
	    InventoryApi itemApi = InventoryFactory.createApi(this.connector, this.request);
	    List items = (List) this.findSalesOrderItems(_soId);
	    for (int ndx = 0; ndx < items.size(); ndx++) {
		soi = (SalesOrderItems) items.get(ndx);
		im = (ItemMaster) itemApi.findItemById(soi.getItemId());
		if (im.getItemTypeId() == ItemConst.ITEM_TYPE_MERCH) {
		    int ordQty = new Double(soi.getOrderQty()).intValue();
		    switch (_action) {
		    case SalesConst.UPDATE_INV_ADD:
			itemApi.pullInventory(im.getItemId(), ordQty);
			break;
		    case SalesConst.UPDATE_INV_DEPLETE:
			itemApi.pushInventory(im.getItemId(), ordQty);
			break;
		    } // end switch
		    merchCount++;
		}
	    }
	    return merchCount;
	}
	catch (ItemMasterException e) {
	    throw new SalesOrderException(e);
	}
    }

    /**
     * Retrieves the sales order total at retail.   The sales order total encompasses item total, sales order fees, sales order taxes, and other charges.
     * 
     * @throws SalesOrderException
     */
    public double getSalesOrderTotal(int _orderId) throws SalesOrderException {
	double soTotal = 0;

	soTotal += this.calcSalesOrderItemTotal(_orderId);
	soTotal += this.calcSalesOrderFees(_orderId);
	soTotal += this.calcSalesOrderTaxes(_orderId);
	soTotal += this.calcSalesOrderOtherCharges(_orderId);
	return soTotal;
    }

    /**
     * Calculates sales order item total.  Override the method to implement customizations for item total
     * 
     * @param _orderId The sales order id of of items to calculate
     * @return Total retail dollar amount of all items of a sales order 
     * @throws SalesOrderException
     */
    protected double calcSalesOrderItemTotal(int _orderId) throws SalesOrderException {
	List items = null;
	SalesOrderItems soi = null;
	double amt = 0;

	items = (List) this.findSalesOrderItems(_orderId);
	if (items == null) {
	    return 0;
	}
	for (int ndx = 0; ndx < items.size(); ndx++) {
	    soi = (SalesOrderItems) items.get(ndx);
	    amt += soi.getOrderQty() * (soi.getInitUnitCost() * soi.getInitMarkup());
	}
	return amt;
    }

    /**
     * Calculates sales order fees.  Override the method to implement customizations for fee calculations
     * 
     * @param _orderId The sales order id used to calculate fees
     * @return Fee amount
     * @throws SalesOrderException
     */
    protected double calcSalesOrderFees(int _orderId) throws SalesOrderException {
	return 0;
    }

    /**
     * Calculates sales order taxes.  Override the method to implement customizations for sales order tax calculations
     * 
     * @param _orderId The sales order id used to calculate taxes
     * @return Sales Order tax amount
     * @throws SalesOrderException
     */
    protected double calcSalesOrderTaxes(int _orderId) throws SalesOrderException {
	return 0;
    }

    /**
     * Calculate other charges pertaining to the sales order
     * 
     * @param _orderId The id of the sales order to calculate other charges
     * @return The amount of other charges
     * @throws SalesOrderException
     */
    protected double calcSalesOrderOtherCharges(int _orderId) throws SalesOrderException {
	return 0;
    }

    /**
     * Verifies that changing the status of the sales order identified as _soId to the new status represented as _newStatusId is legal.
     * The change is considered legal only if an exception is not thrown.
     * <p>
     * The following sequence must be followed when changing the status of a sales order:
     * <p>
     * <ul>
     * <li>The purchase order must be new in order to change the status to "Quote"</li>
     * <li>The purchase order must be in "Quote" status before changing to "Invoiced".</li>
     * <li>The purchase order must be in "Invoiced" status before changing to "Closed".</li>
     * <li>The purchase order must be in "Invoiced" status before changing to "Cancelled".</li>
     * <li>The purchase order must be in "Invoiced" or "Closed" statuses before changing to "Refunded".</li>
     * </ul>
     * 
     * @param soId 
     *          The id of the sales order
     * @param newStatusId 
     *          The id of the new sales order status
     * @return int 
     *          The id of the current sales order status before changing to the new status.
     * @throws SalesOrderException 
     *          When the the new status is not in sequence to the current status regarding 
     *          changing the status of the slaes order.  The exception should give a detail 
     *          explanation as to the reason why the status cannot be changed. 
     */
    protected int verifyStatusChange(int soId, int newStatusId) throws SalesOrderException {
	SalesOrderStatusHist sosh = null;
	int currentStatusId = 0;

	sosh = (SalesOrderStatusHist) this.findCurrentSalesOrderStatusHist(soId);
	currentStatusId = (sosh == null ? SalesConst.STATUS_CODE_NEW : sosh.getSoStatusId());
	switch (newStatusId) {
	case SalesConst.STATUS_CODE_QUOTE:
	    if (currentStatusId != SalesConst.STATUS_CODE_NEW) {
		this.msg = "Quote status can only be assigned when the sales order is new";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }
	    break;

	case SalesConst.STATUS_CODE_INVOICED:
	    if (currentStatusId != SalesConst.STATUS_CODE_QUOTE) {
		this.msg = "Sales order must be in Quote status before changing to Invoiced";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }
	    break;

	case SalesConst.STATUS_CODE_CLOSED:
	    if (currentStatusId != SalesConst.STATUS_CODE_INVOICED) {
		this.msg = "Sales order must be in Invoiced status before changing to Closed";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }
	    break;

	case SalesConst.STATUS_CODE_CANCELLED:
	    if (currentStatusId != SalesConst.STATUS_CODE_INVOICED) {
		this.msg = "Sales order must be in Invoiced status before changing to Cancelled";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    }
	    break;

	case SalesConst.STATUS_CODE_REFUNDED:
	    switch (currentStatusId) {
	    case SalesConst.STATUS_CODE_INVOICED:
	    case SalesConst.STATUS_CODE_CLOSED:
		break;

	    default:
		this.msg = "Sales order must be in Invoiced or Closed statuses before changing to Refunded";
		this.logger.log(Level.ERROR, this.msg);
		throw new SalesOrderException(this.msg);
	    } // end inner switch
	    break;

	} // end outer switch

	return currentStatusId;
    }

}
