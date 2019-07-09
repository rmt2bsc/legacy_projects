package com.inventory;

import com.bean.ItemMaster;
import com.bean.ItemMasterStatus;
import com.bean.ItemMasterType;
import com.bean.OrmBean;
import com.bean.VwItemMaster;
import com.bean.VendorItems;
import com.bean.ItemMasterStatusHist;
import com.bean.VwVendorItems;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.util.SystemException;

/**
 * Factory for creating instances pertaining to inventory entities and api's.
 * 
 * @author appdev
 *
 */
public class InventoryFactory extends DataSourceAdapter {

    /**
     * Create an instance of InventoryApi using a DatabaseConnectionBean object.
     * 
     * @param _dbo
     * @return
     * @throws SystemException
     * @throws DatabaseException
     */
    public static InventoryApi createApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
	InventoryApi api = new InventoryApiImpl(_dbo);
	return api;
    }

    /**
     * Create an instance of InventoryApi using a DatabaseConnectionBean object and a Request.
     * 
     * @param _dbo
     * @param _req
     * @return
     */
    public static InventoryApi createApi(DatabaseConnectionBean _dbo, Request _req) {
	try {
	    InventoryApi api = new InventoryApiImpl(_dbo, _req);
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
     * Create a XML instance of InventoryApi using a DatabaseConnectionBean object and a Request.
     * 
     * @param _dbo
     * @param _req
     * @return
     */
    public static InventoryApi createXmlApi(DatabaseConnectionBean _dbo, Request _req) {
	try {
	    InventoryApi api = new InventoryApiImpl(_dbo, _req);
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
     * Create an instance of ItemMaster.
     * 
     * @return
     * @throws SystemException
     */
    public static ItemMaster createItemMaster() {
	ItemMaster item;
	try {
	    item = new ItemMaster();
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of ItemMaster.
     * 
     * @return
     * @throws SystemException
     */
    public static ItemMaster createXmlItemMaster() {
	ItemMaster item;
	try {
	    item = new ItemMaster();
	    item.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    item.setSerializeXml(false);
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of VwItemMaster.
     * 
     * @return
     */
    public static VwItemMaster createVwItemMaster() {
	VwItemMaster item;
	try {
	    item = new VwItemMaster();
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of VwItemMaster.
     * 
     * @return
     */
    public static VwItemMaster createXmlVwItemMaster() {
	VwItemMaster item;
	try {
	    item = new VwItemMaster();
	    item.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    item.setSerializeXml(false);
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of VendorItems.
     * 
     * @return
     */
    public static VendorItems createVendorItem() {
	VendorItems obj = null;
	try {
	    obj = new VendorItems();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of VendorItems.
     * 
     * @return
     */
    public static VendorItems createXmlVendorItem() {
	VendorItems obj = null;
	try {
	    obj = new VendorItems();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of VendorItems which the vendor, item, item serial number, 
     * vendor item number, and item unit cost are known.
     * 
     * @param vendorId
     * @param itemId
     * @param serialNo
     * @param vendorItemNo
     * @param unitCost
     * @return
     */
    public static VendorItems createVendorItem(int vendorId, int itemId, String serialNo, String vendorItemNo, double unitCost) {
	VendorItems obj = null;
	obj = InventoryFactory.createVendorItem();
	obj.setCreditorId(vendorId);
	obj.setItemId(itemId);
	obj.setItemSerialNo(serialNo);
	obj.setVendorItemNo(vendorItemNo);
	obj.setUnitCost(unitCost);
	return obj;
    }

    /**
     * Create a XML instance of VendorItems which the vendor, item, item serial number, 
     * vendor item number, and item unit cost are known.
     * 
     * @param vendorId
     * @param itemId
     * @param serialNo
     * @param vendorItemNo
     * @param unitCost
     * @return
     */
    public static VendorItems createXmlVendorItem(int vendorId, int itemId, String serialNo, String vendorItemNo, double unitCost) {
	VendorItems obj = null;
	obj = InventoryFactory.createVendorItem();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	obj.setCreditorId(vendorId);
	obj.setItemId(itemId);
	obj.setItemSerialNo(serialNo);
	obj.setVendorItemNo(vendorItemNo);
	obj.setUnitCost(unitCost);
	return obj;
    }

    /**
     * Create an instance of VwItemMaster.
     * 
     * @return
     */
    public static VwItemMaster createItemMasterView() {
	try {
	    VwItemMaster item = new VwItemMaster();
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of VwItemMaster.
     * 
     * @return
     */
    public static VwItemMaster createXmlItemMasterView() {
	try {
	    VwItemMaster item = new VwItemMaster();
	    item.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    item.setSerializeXml(false);
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of ItemMasterStatusHist.
     * 
     * @return
     */
    public static ItemMasterStatusHist createItemMasterStatusHist() {
	try {
	    ItemMasterStatusHist item = new ItemMasterStatusHist();
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of ItemMasterStatusHist.
     * 
     * @return
     */
    public static ItemMasterStatusHist createXmlItemMasterStatusHist() {
	try {
	    ItemMasterStatusHist item = new ItemMasterStatusHist();
	    item.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    item.setSerializeXml(false);
	    return item;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of ItemMasterType.
     * 
     * @return
     */
    public static ItemMasterType createItemMasterType() {
	try {
	    ItemMasterType obj = new ItemMasterType();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of ItemMasterType.
     * 
     * @return
     */
    public static ItemMasterType createXmlItemMasterType() {
	try {
	    ItemMasterType obj = new ItemMasterType();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of ItemMasterStatus.
     * @return
     */
    public static ItemMasterStatus createItemMasterStatus() {
	try {
	    ItemMasterStatus obj = new ItemMasterStatus();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of ItemMasterStatus.
     * 
     * @return
     */
    public static ItemMasterStatus createXmlItemMasterStatus() {
	try {
	    ItemMasterStatus obj = new ItemMasterStatus();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create an instance of VwVendorItems.
     * @return
     */
    public static VwVendorItems createVwVendorItems() {
	try {
	    VwVendorItems obj = new VwVendorItems();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a XML instance of VwVendorItems.
     * @return
     */
    public static VwVendorItems createXmlVwVendorItems() {
	try {
	    VwVendorItems obj = new VwVendorItems();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

}
