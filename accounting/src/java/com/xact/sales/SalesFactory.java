package com.xact.sales;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.SalesInvoice;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;
import com.bean.VwSalesOrderInvoice;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xact.XactManagerApi;

import com.util.SystemException;

/**
 * Factory class that contain methods responsible for the creation of  various objects pertaining to the sales order module.
 * 
 * @author Roy Terrell.
 *
 */
public class SalesFactory extends DataSourceAdapter {

    /**
     * Creates an {@link XactManagerApi} api implemented as {@link SalesOrderManagerApiImpl} with a specifiic 
     * database connection bean and servlet request object.
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @param _request HttpServletRequest
     * @return {@link XactManagerApi}
     */
    public static XactManagerApi createBaseXactApi(DatabaseConnectionBean _dbo, Request _request) {
	try {
	    XactManagerApi api = new SalesOrderManagerApiImpl(_dbo, _request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates an sales order api implemented with a specific database connection bean
     * 
     * @param _dbo {@link DatabaseConnectionBean}
     * @return Sales Order api
     * @throws SystemException
     * @throws DatabaseException
     */
    public static SalesOrderApi createApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
	SalesOrderApi api = new SalesOrderManagerApiImpl(_dbo);
	api.setBaseView("SalesOrderView");
	api.setBaseClass("com.bean.SalesOrder");
	return api;
    }

    /**
     * Creates an sales order api implemented with a specific database connection bean and 
     * servlet request object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return Sales Order api
     * @throws SystemException
     * @throws DatabaseException
     */
    public static SalesOrderApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    SalesOrderApi api = new SalesOrderManagerApiImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a XML sales order api implemented with a specific database connection bean 
     * and servlet request object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return Sales Order api
     * @throws SystemException
     * @throws DatabaseException
     */
    public static SalesOrderApi createXmlApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    SalesOrderApi api = new SalesOrderManagerXmlImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new sales order object
     * 
     * @return {@link SalesOrder}
     */
    public static SalesOrder createSalesOrder() {
	try {
	    SalesOrder obj = new SalesOrder();
	    obj.setInvoiced(0);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new XML sales order object
     * 
     * @return {@link SalesOrder}
     */
    public static SalesOrder createXmlSalesOrder() {
	try {
	    SalesOrder obj = new SalesOrder();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    obj.setInvoiced(0);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a sales order object which initializing its customer id property to _cusId
     *  
     * @param _custId The id of a particular customer.
     * @return {@link SalesOrder}
     * @throws SystemException
     */
    public static SalesOrder createSalesOrder(int _custId) throws SystemException {
	SalesOrder obj = SalesFactory.createSalesOrder();
	obj.setCustomerId(_custId);
	return obj;
    }

    /**
     * Creates a single sales order item object
     * 
     * @return {@link SalesOrderItems}
     */
    public static SalesOrderItems createSalesOrderItem() {
	try {
	    SalesOrderItems obj = new SalesOrderItems();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a single XML sales order item object
     * 
     * @return {@link SalesOrderItems}
     */
    public static SalesOrderItems createXmlSalesOrderItem() {
	try {
	    SalesOrderItems obj = new SalesOrderItems();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a single sales order item object using sales order id, item id, and item quantity
     * 
     * @param _salesOrderId The id of a sales order
     * @param _itemId The id of an inventory item
     * @param _qty The quantity to associate with _itemId
     * @return {@link SalesOrderItems}
     * @throws SystemException
     */
    public static SalesOrderItems createSalesOrderItem(int salesOrderId, int itemId, double qty) throws SystemException {
	SalesOrderItems obj = SalesFactory.createSalesOrderItem();
	obj.setSoId(salesOrderId);
	obj.setItemId(itemId);
	obj.setOrderQty(qty);
	return obj;
    }

    /**
     * Creates a sales invoice object
     * 
     * @return {@link SalesInvoice}
     * @throws SystemException
     */
    public static SalesInvoice createSalesInvoice() {
	try {
	    SalesInvoice obj = new SalesInvoice();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a XML sales invoice object
     * 
     * @return {@link SalesInvoice}
     * @throws SystemException
     */
    public static SalesInvoice createXmlSalesInvoice() {
	try {
	    SalesInvoice obj = new SalesInvoice();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a sales invoice object using sales order id and transaction id
     * 
     * @param _salesOrderId The id of a sales order
     * @param _xactId The id of the transaction that is assoicated with _salesOrderId
     * @return {@link SalesInvoice}
     * @throws SystemException
     */
    public static SalesInvoice createSalesInvoice(int salesOrderId, int xactId) throws SystemException {
	SalesInvoice obj = SalesFactory.createSalesInvoice();
	obj.setSoId(salesOrderId);
	obj.setXactId(xactId);
	return obj;
    }

    /**
     * Creates a sales order status history object using sales order id
     * 
     * @param _salesOrderId The id of a particular sales order
     * @return {@link SalesOrderStatusHist}
     */
    public static SalesOrderStatusHist createSalesStatusHistory(int _salesOrderId) {
	SalesOrderStatusHist obj = null;
	try {
	    obj = new SalesOrderStatusHist();
	    obj.setSoId(_salesOrderId);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a XML sales order status history object using sales order id.
     * 
     * @param salesOrderId The id of a particular sales order
     * @return {@link SalesOrderStatusHist}
     */
    public static SalesOrderStatusHist createXmlSalesStatusHistory(int salesOrderId) {
	SalesOrderStatusHist obj = null;
	try {
	    obj = new SalesOrderStatusHist();
	    obj.setSoId(salesOrderId);
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a VwSalesorderItemsBySalesorder object.
     * 
     * @return {@link VwSalesorderItemsBySalesorder}
     */
    public static VwSalesorderItemsBySalesorder createVwSalesOrderItemsBySalesOrder() {
	VwSalesorderItemsBySalesorder obj;
	try {
	    obj = new VwSalesorderItemsBySalesorder();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a XML VwSalesorderItemsBySalesorder object.
     * 
     * @return {@link VwSalesorderItemsBySalesorder}
     */
    public static VwSalesorderItemsBySalesorder createXmlVwSalesOrderItemsBySalesOrder() {
	VwSalesorderItemsBySalesorder obj;
	try {
	    obj = new VwSalesorderItemsBySalesorder();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a VwSalesOrderInvoice object.
     * 
     * @return {@link VwSalesOrderInvoice}
     */
    public static VwSalesOrderInvoice createVwSalesOrderInvoice() {
	VwSalesOrderInvoice obj;
	try {
	    obj = new VwSalesOrderInvoice();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a XML VwSalesOrderInvoice object.
     * 
     * @return {@link VwSalesOrderInvoice}
     */
    public static VwSalesOrderInvoice createXmlVwSalesOrderInvoice() {
	VwSalesOrderInvoice obj;
	try {
	    obj = new VwSalesOrderInvoice();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a SalesOrderStatus object.
     * 
     * @return {@link SalesOrderStatus}
     */
    public static SalesOrderStatus createSalesOrderStatus() {
	SalesOrderStatus obj;
	try {
	    obj = new SalesOrderStatus();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /** 
     * Creates a XML SalesOrderStatus object.
     * 
     * @return {@link SalesOrderStatus}
     */
    public static SalesOrderStatus createXmlSalesOrderStatus() {
	SalesOrderStatus obj;
	try {
	    obj = new SalesOrderStatus();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }
}
