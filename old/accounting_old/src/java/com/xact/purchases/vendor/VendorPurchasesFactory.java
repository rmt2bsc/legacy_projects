package com.xact.purchases.vendor;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;
import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VendorItems;
import com.bean.VwPurchaseOrderList;
import com.bean.VwVendorItemPurchaseOrderItem;
import com.bean.VwVendorItems;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.controller.Response;

import com.xact.XactManagerApi;

/**
 * A factory class for creating instances pertaining to the purchase order module.
 * 
 * @author RTerrell
 *
 */
public class VendorPurchasesFactory extends DataSourceAdapter {

    /**
     * Creates a common XactManagerApi object using the VendorPurchasesDatabaseImpl 
     * implementation.
     * 
     * @param dbo The database connection bean.
     * @return {@link com.xact.XactManagerApi XactManagerApi}
     */
    public static XactManagerApi createBaseXactApi(DatabaseConnectionBean dbo) {
	try {
	    XactManagerApi api = new VendorPurchasesDatabaseImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a purchase order api  based on the VendorPurchasesDatabaseImpl implemtation 
     * using a database connection bean.
     * 
     * @param dbo The database connection bean.
     * @return {@link VendorPurchasesApi}
     */
    public static VendorPurchasesApi createApi(DatabaseConnectionBean dbo) {
	try {
	    VendorPurchasesApi api = new VendorPurchasesDatabaseImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a purchase order api based on the VendorPurchasesDatabaseImpl implemtation 
     * using a database connection bean and a user request object.
     * 
     * @param dbo The database connection bean.
     * @param request The request
     * @return {@link VendorPurchasesApi}
     */
    public static VendorPurchasesApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    VendorPurchasesApi api = new VendorPurchasesDatabaseImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a purchase order api based on the VendorPurchasesDatabaseImpl 
     * implemtation using a database connection bean, a user request object, 
     * and a the response object.
     * 
     * @param dbo The database connection bean.
     * @param request The request
     * @param request The response
     * @return {@link VendorPurchasesApi}
     */
    public static VendorPurchasesApi createApi(DatabaseConnectionBean dbo, Request request, Response response) {
	try {
	    VendorPurchasesApi api = new VendorPurchasesDatabaseImpl(dbo, request, response);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a purchase order bean.
     * 
     * @return {@link com.bean.PurchaseOrder PurchaseOrder}
     */
    public static PurchaseOrder createPurchaseOrder() {
	try {
	    PurchaseOrder obj = new PurchaseOrder();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML purchase order bean.
     * 
     * @return {@link com.bean.PurchaseOrder PurchaseOrder}
     */
    public static PurchaseOrder createXmlPurchaseOrder() {
	PurchaseOrder obj = VendorPurchasesFactory.createPurchaseOrder();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a view vendor item purchase order item bean.
     * 
     * @return {@link com.bean.VwVendorItemPurchaseOrderItem VwVendorItemPurchaseOrderItem}
     */
    public static VwVendorItemPurchaseOrderItem createVwVendorItemPurchaseOrderItem() {
	try {
	    VwVendorItemPurchaseOrderItem obj = new VwVendorItemPurchaseOrderItem();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML view vendor item purchase order item bean.
     * 
     * @return {@link com.bean.VwVendorItemPurchaseOrderItem VwVendorItemPurchaseOrderItem}
     */
    public static VwVendorItemPurchaseOrderItem createXmlVwVendorItemPurchaseOrderItem() {
	VwVendorItemPurchaseOrderItem obj = VendorPurchasesFactory.createVwVendorItemPurchaseOrderItem();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a Vendor Items bean.
     * 
     * @return {@link com.bean.VendorItems VendorItems}
     */
    public static VendorItems createVendorItems() {
	try {
	    VendorItems obj = new VendorItems();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML Vendor Items bean.
     * 
     * @return {@link com.bean.VendorItems VendorItems}
     */
    public static VendorItems createXmlVendorItems() {
	VendorItems obj = VendorPurchasesFactory.createVendorItems();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a View Vendor Items bean.
     * 
     * @return {@link com.bean.VendorItems VendorItems}
     */
    public static VwVendorItems createVwVendorItems() {
	try {
	    VwVendorItems obj = new VwVendorItems();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML View Vendor Items bean.
     * 
     * @return {@link com.bean.VendorItems VendorItems}
     */
    public static VwVendorItems createXmlVwVendorItems() {
	VwVendorItems obj = VendorPurchasesFactory.createVwVendorItems();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a purchase order items bean.
     * 
     * @return {@link com.bean.PurchaseOrderItems PurchaseOrderItems}
     */
    public static PurchaseOrderItems createPurchaseOrderItem() {
	try {
	    PurchaseOrderItems obj = new PurchaseOrderItems();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a purchase order items bean.
     * 
     * @return {@link com.bean.PurchaseOrderItems PurchaseOrderItems}
     */
    public static PurchaseOrderItems createPurchaseOrderItem(int poId) {
	PurchaseOrderItems obj = VendorPurchasesFactory.createPurchaseOrderItem();
	obj.setPoId(poId);
	return obj;
    }

    /**
     * Create a XML purchase order items bean.
     * 
     * @return {@link com.bean.PurchaseOrderItems PurchaseOrderItems}
     */
    public static PurchaseOrderItems createXmlPurchaseOrderItem() {
	PurchaseOrderItems obj = VendorPurchasesFactory.createPurchaseOrderItem();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a view purchase order list bean.
     * 
     * @return {@link com.bean.VwPurchaseOrderList VwPurchaseOrderList}
     */
    public static VwPurchaseOrderList createVwPurchaseOrderList() {
	try {
	    VwPurchaseOrderList obj = new VwPurchaseOrderList();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML view purchase order list bean.
     * 
     * @return {@link com.bean.VwPurchaseOrderList VwPurchaseOrderList}
     */
    public static VwPurchaseOrderList createXmlVwPurchaseOrderList() {
	VwPurchaseOrderList obj = VendorPurchasesFactory.createVwPurchaseOrderList();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a purchase order status bean.
     * 
     * @return {@link com.bean.PurchaseOrderStatus PurchaseOrderStatus}
     */
    public static PurchaseOrderStatus createPurchaseOrderStatus() {
	try {
	    PurchaseOrderStatus obj = new PurchaseOrderStatus();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML purchase order status bean.
     * 
     * @return {@link com.bean.PurchaseOrderStatus PurchaseOrderStatus}
     */
    public static PurchaseOrderStatus createXmlPurchaseOrderStatus() {
	PurchaseOrderStatus obj = VendorPurchasesFactory.createPurchaseOrderStatus();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a purchase order status history bean.
     * 
     * @return {@link com.bean.PurchaseOrderStatusHist PurchaseOrderStatusHist}
     */
    public static PurchaseOrderStatusHist createPurchaseOrderStatusHist() {
	try {
	    PurchaseOrderStatusHist obj = new PurchaseOrderStatusHist();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a XML purchase order status history bean.
     * 
     * @return {@link com.bean.PurchaseOrderStatusHist PurchaseOrderStatusHist}
     */
    public static PurchaseOrderStatusHist createXmlPurchaseOrderStatusHist() {
	PurchaseOrderStatusHist obj = VendorPurchasesFactory.createPurchaseOrderStatusHist();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Create a purchase order status history bean with purchase order id and status id.
     * 
     * @param poId The purchase order id
     * @param statusId The purchase order status id
     * @return {@link com.bean.PurchaseOrderStatusHist PurchaseOrderStatusHist}
     */
    public static PurchaseOrderStatusHist createPurchaseOrderStatusHist(int poId, int statusId) {
	PurchaseOrderStatusHist obj = VendorPurchasesFactory.createPurchaseOrderStatusHist();
	obj.setPoId(poId);
	obj.setPoStatusId(statusId);
	return obj;
    }

}
