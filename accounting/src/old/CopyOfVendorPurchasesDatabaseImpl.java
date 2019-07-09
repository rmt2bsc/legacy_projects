package com.xact.purchases.vendor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.api.DaoApi;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceFactory;
import com.api.xml.XmlApiFactory;

import com.bean.OrmBean;
import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VendorItems;
import com.bean.VwVendorItemPurchaseOrderItem;
import com.bean.VwVendorItems;
import com.bean.Xact;
import com.bean.VwPurchaseOrderList;
import com.bean.CreditorType;
import com.bean.Creditor;
import com.bean.ItemMaster;
import com.bean.ItemMasterType;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.controller.Response;

import com.gl.AccountingConst;
import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorFactory;

import com.inventory.InventoryApi;
import com.inventory.InventoryFactory;
import com.inventory.ItemConst;
import com.inventory.ItemMasterException;

import com.remoteservices.http.HttpRemoteServicesConsumer;
import com.util.RMT2Date;
import com.util.SystemException;
import com.util.NotFoundException;

import com.xact.XactConst;
import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApiImpl;

/**
 * Api Implementation that manages inventory purchase transactions on account.  When a 
 * purchase order is submitted, the base transaction amount is posted to the xact table 
 * as a positive value, and the creditor activity amount is posted as positive value which 
 * increases the value of the creditor's account.   Conversely, when a purchase order is 
 * cancelled,  the base transaction amount is posted to the xact table as a negative value, 
 * and the  creditor activity amount is posted as negative value which decreases the value 
 * of the creditor's account. 
 * 
 * @author appdev
 *
 */
class CopyOfVendorPurchasesDatabaseImpl extends XactManagerApiImpl implements VendorPurchasesApi {
    private Logger logger;

    private Response response;

    /**
     * Default Constructor
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected CopyOfVendorPurchasesDatabaseImpl() throws DatabaseException, SystemException {
	super();

    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the 
     * acestor level.
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public CopyOfVendorPurchasesDatabaseImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
    }

    /**
     * Constructor that uses dbConn and request.
     * 
     * @param dbConn The database connection bean
     * @param request The user's request
     * @throws DatabaseException
     * @throws SystemException
     */
    public CopyOfVendorPurchasesDatabaseImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	this(dbConn);
	this.setRequest(request);
    }

    /**
     * Constructor that uses dbConn, request, and a response.
     * 
     * @param dbConn The database connection bean
     * @param request The user's request
     * @param response The user's response
     * @throws DatabaseException
     * @throws SystemException
     */
    public CopyOfVendorPurchasesDatabaseImpl(DatabaseConnectionBean dbConn, Request request, Response response) throws DatabaseException, SystemException {
	this(dbConn, request);
	this.response = response;
    }

    /**
     * Initializes the logger.
     */
    public void init() {
	super.init();
	this.logger = Logger.getLogger("VendorPurchasesDatabaseImpl");
    }

    public void close() {
	this.daoHelper = null;
	super.close();
    }

    /**
     * Finds a purchase order that is associated with value.
     * 
     * @param poId The Id of the purchase order.
     * @return An instance {@link com.bean.PurchaseOrder PurchaseOrder}
     * @throws VendorPurchasesException
     */
    public Object findPurchaseOrder(int poId) throws VendorPurchasesException {
	PurchaseOrder obj = VendorPurchasesFactory.createPurchaseOrder();
	obj.addCriteria(PurchaseOrder.PROP_POID, poId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Find all purchase order items pertaining to a given vendor.
     * 
     * @param vendorId The vendor id
     * @param poId The purchase order id
     * @return A List of {@link com.bean.VwVendorItemPurchaseOrderItem VwVendorItemPurchaseOrderItem}
     * @throws VendorPurchasesException
     */
    public List findVendorItemPurchaseOrderItems(int vendorId, int poId) throws VendorPurchasesException {
	VwVendorItemPurchaseOrderItem obj = VendorPurchasesFactory.createVwVendorItemPurchaseOrderItem();
	obj.addCriteria(VwVendorItemPurchaseOrderItem.PROP_VENDORID, vendorId);
	obj.addCriteria(VwVendorItemPurchaseOrderItem.PROP_POID, poId);
	obj.addOrderBy(VwVendorItemPurchaseOrderItem.PROP_DESCRIPTION, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Retrieves one vendor item object using vendorId and itemId
     * 
     * @param vendorId The id of the vendor
     * @param itemId The id of the inventory item
     * @return An instance of {@link com.bean.VendorItems VendorItems}
     * @throws ItemMasterException
     */
    public Object findVendorItem(int vendorId, int itemId) throws VendorPurchasesException {
	VendorItems obj = VendorPurchasesFactory.createVendorItems();
	obj.addCriteria(VendorItems.PROP_CREDITORID, vendorId);
	obj.addCriteria(VendorItems.PROP_ITEMID, itemId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Finds a single purchase order item using _poItemId.
     * 
     * @param poId  The Id of the purchase order.
     * @param poItemId  The Id of the purchase order item.
     * @return An instance of {@link com.bean.PurchaseOrderItems PurchaseOrderItems}
     * @throws VendorPurchasesException
     */
    public Object findPurchaseOrderItem(int poId, int poItemId) throws VendorPurchasesException {
	PurchaseOrderItems obj = VendorPurchasesFactory.createPurchaseOrderItem();
	obj.addCriteria(PurchaseOrderItems.PROP_POID, poId);
	obj.addCriteria(PurchaseOrderItems.PROP_ITEMID, poItemId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Finds all items belonging to a purchase order identified as, value
     * 
     * @param poId The purchase order id
     * @return A List of {@link com.bean.PurchaseOrderItems PurchaseOrderItems}
     * @throws VendorPurchasesException
     */
    public List findPurchaseOrderItems(int poId) throws VendorPurchasesException {
	PurchaseOrderItems obj = VendorPurchasesFactory.createPurchaseOrderItem();
	obj.addCriteria(PurchaseOrderItems.PROP_POID, poId);
	obj.addOrderBy(PurchaseOrderItems.PROP_POID, OrmBean.ORDERBY_DESCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Finds a purchase order that is associated with value.
     * 
     * @param poId The Id of the purchase order.
     * @return An instance of {@link com.bean.VwPurchaseOrderList VwPurchaseOrderList}
     * @throws VendorPurchasesException
     */
    public Object findCurrentPurchaseOrder(int poId) throws VendorPurchasesException {
	VwPurchaseOrderList po = VendorPurchasesFactory.createVwPurchaseOrderList();
	try {
	    po.addCriteria(VwPurchaseOrderList.PROP_ID, poId);
	    po = (VwPurchaseOrderList) this.daoHelper.retrieveObject(po);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}

	//  Build list business id's to submit to remote service
	StringBuffer busId = new StringBuffer();
	busId.append(po.getBusinessId());

	// Call remote service to fetch business contact based on list of business id's
	String busContactXml;
	try {
	    this.request.setAttribute("Arg_BusinessId", busId.toString());
	    HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer(this.request, this.response, "getBusAddrById");
	    srvc.processRequest();
	    Object results = srvc.getServiceResults();
	    if (results instanceof Exception) {
		this.msg = ((Exception) results).getMessage();
		throw new VendorPurchasesException(this.msg);
	    }
	    busContactXml = results.toString();
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}

	PurchaseOrderExt poExt = this.populatePurchaseOrderExt(po, busContactXml);

	return poExt;
    }

    /**
     * Fetches purchase orders based on using custom selection criteria.
     * 
     * @param criteria A String representing custom criteria.
     * @return A List of {@link com.xact.purchases.vendor.PurchaseOrderExt PurchaseOrderExt}
     * @throws VendorPurchasesException
     */
    public Object findPurchaseOrder(String criteria) throws VendorPurchasesException {
	List<VwPurchaseOrderList> poList = null;
	try {
	    VwPurchaseOrderList obj = VendorPurchasesFactory.createVwPurchaseOrderList();
	    obj.addCustomCriteria(criteria);
	    poList = this.daoHelper.retrieveList(obj);
	    if (poList == null) {
		poList = new ArrayList();
	    }
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}

	//  Build list business id's to submit to remote service
	StringBuffer busId = new StringBuffer();
	for (int ndx = 0; ndx < poList.size(); ndx++) {
	    VwPurchaseOrderList obj = poList.get(ndx);
	    if (busId.length() > 0) {
		busId.append(",");
	    }
	    busId.append(obj.getBusinessId());
	}

	// Call remote service to fetch business contact based on list of business id's
	String busContactXml;
	try {
	    this.request.setAttribute("Arg_BusinessId", busId.toString());
	    HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer(this.request, this.response, "getBusAddrById");
	    srvc.processRequest();
	    Object results = srvc.getServiceResults();
	    if (results instanceof Exception) {
		this.msg = ((Exception) results).getMessage();
		throw new VendorPurchasesException(this.msg);
	    }
	    busContactXml = results.toString();
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}

	List<PurchaseOrderExt> poExtList = new ArrayList<PurchaseOrderExt>();
	for (VwPurchaseOrderList po : poList) {
	    PurchaseOrderExt poExt = this.populatePurchaseOrderExt(po, busContactXml);
	    if (poExt != null) {
		poExtList.add(poExt);
	    }
	}

	return poExtList;

    }

    /**
     * Creates an instance of PurchaseOrderExt from <i>po</i> and <i>xml</i>.  
     * Essentially, data is transferred from <i>po</i> and one record from <i>xml</i>
     * where the business id of <i>po</i> matches the business id from one of the 
     * vw_business_address elements of <i>xml</i>.
     *  
     * @param po 
     *          {@link com.bean.VwPurchaseOrderList VwPurchaseOrderList} object.
     * @param xml 
     *          A XML document representing data in the format of one or more 
     *          vw_business_address
     * @return {@link com.xact.purchases.vendor.PurchaseOrderExt PurchaseOrderExt}
     * @throws VendorPurchasesException
     */
    private PurchaseOrderExt populatePurchaseOrderExt(VwPurchaseOrderList po, String xml) throws VendorPurchasesException {
	int intTmp;
	String value;
	PurchaseOrderExt poExt = null;

	// Get business contact data.
	DaoApi dao = XmlApiFactory.createXmlDao(xml);
	try {
	    poExt = new PurchaseOrderExt();
	    dao.retrieve("//VwBusinessAddressView/vw_business_address");
	    while (dao.nextRow()) {
		try {
		    intTmp = Integer.parseInt(dao.getColumnValue("business_id"));
		    if (intTmp != po.getBusinessId()) {
			continue;
		    }
		    poExt.setBusinessId(intTmp);
		}
		catch (NumberFormatException e) {
		    poExt.setBusType(0);
		}
		try {
		    intTmp = Integer.parseInt(dao.getColumnValue("bus_entity_type_id"));
		    poExt.setBusType(intTmp);
		}
		catch (NumberFormatException e) {
		    poExt.setBusType(0);
		}
		try {
		    intTmp = Integer.parseInt(dao.getColumnValue("bus_serv_type_id"));
		    poExt.setServType(intTmp);
		}
		catch (NumberFormatException e) {
		    poExt.setServType(0);
		}

		value = dao.getColumnValue("bus_longname");
		poExt.setName(value);
		value = dao.getColumnValue("bus_shortname");
		poExt.setShortname(value);
		value = dao.getColumnValue("bus_contact_firstname");
		poExt.setContactFirstname(value);
		value = dao.getColumnValue("bus_contact_lastname");
		poExt.setContactLastname(value);
		value = dao.getColumnValue("bus_contact_phone");
		poExt.setContactPhone(value);
		value = dao.getColumnValue("bus_contact_ext");
		poExt.setContactExt(value);
		value = dao.getColumnValue("bus_tax_id");
		poExt.setTaxId(value);
		value = dao.getColumnValue("bus_website");
		poExt.setWebsite(value);
		break;
	    } // end while
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}

	// Get Purchase Order data
	poExt.setId(po.getId());
	poExt.setVendorId(po.getVendorId());
	poExt.setStatusId(po.getStatusId());
	poExt.setStatusDescription(po.getStatusDescription());
	poExt.setRefNo(po.getRefNo());
	poExt.setTotal(po.getTotal());
	poExt.setStatusHistId(po.getStatusHistId());
	poExt.setEffectiveDate(po.getEffectiveDate());
	poExt.setEndDate(po.getEndDate());
	poExt.setCreditorTypeId(po.getCreditorTypeDescr());
	poExt.setCreditTypeId(po.getCreditTypeId());
	poExt.setAccountNumber(po.getAccountNumber());
	poExt.setCreditLimit(po.getCreditLimit());
	return poExt;
    }

    /**
     * Retreives either the item_master version or the vendor_item version of an 
     * inventory item using vendor id and item id.
     * 
     * @param vendorId  The vendor id
     * @param itemId The id of the item to retrieve
     * @return An instance of {@link com.bean.VwVendorItems VwVendorItems}
     * @throws VendorPurchasesException
     */
    public Object findCurrentItemByVendor(int vendorId, int itemId) throws VendorPurchasesException {
	VwVendorItems obj = VendorPurchasesFactory.createVwVendorItems();
	obj.addCriteria(VwVendorItems.PROP_CREDITORID, vendorId);
	obj.addCriteria(VwVendorItems.PROP_ITEMID, itemId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Obtains the purchase order status based on the value of _poStatusId.
     * 
     * @param poStatusId The id of the purchase order status to retrieve.
     * @return An instance of {@link com.bean.PurchaseOrderStatus PurchaseOrderStatus}
     * @throws VendorPurchasesException
     */
    public Object findPurchaseOrderStatus(int poStatusId) throws VendorPurchasesException {
	PurchaseOrderStatus obj = VendorPurchasesFactory.createPurchaseOrderStatus();
	obj.addCriteria(PurchaseOrderStatus.PROP_POSTATUSID, poStatusId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Obtains the current status of a purchase order.
     * 
     * @param poId Is the id of the purchase order
     * @return An instance of {@link com.bean.PurchaseOrderStatusHist PurchaseOrderStatusHist}
     * @throws VendorPurchasesException
     */
    public Object findCurrentPurchaseOrderHistory(int poId) throws VendorPurchasesException {
	PurchaseOrderStatusHist obj = VendorPurchasesFactory.createPurchaseOrderStatusHist();
	obj.addCriteria(PurchaseOrderStatusHist.PROP_POID, poId);
	obj.addCustomCriteria("end_date is null");
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Gathers the status history of a purchase order.
     * 
     * @param poId The purchase order id
     * @return A List of {@link com.bean.PurchaseOrderStatusHist PurchaseOrderStatusHist}
     * @throws VendorPurchasesException
     */
    public List findPurchaseOrderHistory(int poId) throws VendorPurchasesException {
	PurchaseOrderStatusHist obj = VendorPurchasesFactory.createPurchaseOrderStatusHist();
	obj.addCriteria(PurchaseOrderStatusHist.PROP_POID, poId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Retrieves one or more inventory items for vendor, <i>vendorId</i>, from the 
     * database which have not been assoicated with a purchase order, <i>poId</i>.
     * 
     * @param vendorId The id of the vendor.
     * @param poId The id of the purchase order.
     * @return A List of {@link com.bean.VwVendorItems VwVendorItems}
     * @throws VendorPurchasesException
     */
    public Object getPurchaseOrderAvailItems(int vendorId, int poId) throws VendorPurchasesException {
	VwVendorItems obj = VendorPurchasesFactory.createVwVendorItems();
	obj.addCriteria(VwVendorItems.PROP_CREDITORID, vendorId);

	// Setup special criteria to filter those items that are not assoicated with the PO.
	StringBuffer criteria = new StringBuffer(100);
	criteria.append("item_id not in ( select item_id from purchase_order_items where po_id = ");
	criteria.append(poId);
	criteria.append(")");
	obj.addCustomCriteria(criteria.toString());

	obj.addOrderBy(VwVendorItems.PROP_DESCRIPTION, VwVendorItems.ORDERBY_ASCENDING);

	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Stub method
     * 
     * @param purchaseOrderId The id of a valid purchase order.
     * @throws VendorPurchasesException
     */
    public void doVendorPurchaseReturnAllow(int purchaseOrderId) throws VendorPurchasesException {
	return;
    }

    /**
     * Creates a new or modifies an existing purchase order.  When the purchase order id 
     * equals zero, a new purchase order is created.  When the purchase order id greater 
     * than zero, an existing purchase order is modified.
     *     
     * @param po  Purchase Order object.  Must be valid.
     * @param items list of purchase order items.  Must be valid.
     * @return Id of a new purchase order when _po's id property equals zero or the total 
     *         number of rows updated for existing purchase orders where po's id property 
     *         is greater thatn zero.
     * @throws VendorPurchasesException
     */
    public int maintainPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException {
	int rc = 0;
	if (po == null) {
	    this.msg = "Base purchase order object is invalid";
	    this.logger.log(Level.FATAL, this.msg);
	    throw new VendorPurchasesException(this.msg, 480);
	}
	if (items == null) {
	    this.msg = "Purchase order items object is invalid";
	    this.logger.log(Level.FATAL, this.msg);
	    throw new VendorPurchasesException(this.msg, 481);
	}

	if (po.getPoId() == 0) {
	    // Returns new Purchase order id
	    rc = this.insertPurchaseOrder(po, items);
	}
	else {
	    // Returns total number of rows updated.
	    rc = this.updatePurchaseOrder(po, items, true);
	}
	return rc;
    }

    /**
     * Adds items to the existing list of purchase order items without performing 
     * a purcase order item refresh.
     * 
     * @param po Purchase Order object.  Must be valid.
     * @param items 
     *          list of new purchase order items that will be appended to the 
     *          existing list.  Must be valid.
     * @return Id of a new purchase order when _po's id property equals zero or 
     *         the total number of rows updated for existing purchase orders 
     *         where <i>po's</i> id property is greater thatn zero.
     * @throws VendorPurchasesException
     */
    public int addItemsToPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException {
	int rc = 0;
	if (po == null) {
	    this.msg = "Item could not be added to purchase order.  Base purchase order object is invalid.";
	    this.logger.log(Level.FATAL, this.msg);
	    throw new VendorPurchasesException(this.msg, 482);
	}
	if (items == null) {
	    this.msg = "Item could not be added to purchase order.  Purchase order item collection is invalid.";
	    this.logger.log(Level.FATAL, this.msg);
	    throw new VendorPurchasesException(this.msg, 483);
	}

	if (po.getPoId() == 0) {
	    // Returns new Purchase order id
	    rc = this.insertPurchaseOrder(po, items);
	    po.setPoId(rc);
	}
	else {
	    // Returns total number of rows updated.
	    rc = this.updatePurchaseOrder(po, items, false);
	}
	return po.getPoId();
    }

    /**
     * Adds a purchase order and its items to the databse.  A status of  "Quote" will 
     * be assigned to the new purchase order.
     * 
     * @param po Base purchase order data.
     * @param items All related purchase order items
     * @return The new purchase order id
     * @throws VendorPurchasesException
     */
    protected int insertPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException {
	int poId = 0;

	// We must be working with a valid po at this point, so add to the database
	poId = this.insertPurchaseOrderBase(po);
	this.insertPurchaseOrderItems(poId, items);

	// Always assign a status of "Quote" for a new purchase order.
	this.setPurchaseOrderStatus(poId, VendorPurchasesConst.PURCH_STATUS_QUOTE);
	return poId;
    }

    /**
     * Adds base purchase order data to the database.
     * 
     * @param po Base purchase order data.
     * @return The new purchase order id
     * @throws VendorPurchasesException
     */
    private int insertPurchaseOrderBase(PurchaseOrder po) throws VendorPurchasesException {
	int poId = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	// We must be working with a valid po at this point, so add to the database
	try {
	    // Setup DataSource object to apply database updates.
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);

	    // Apply base purchase order
	    this.validatePurchaseOrder(po);
	    po.setNull("status");
	    po.setNull("xactId");
	    po.setDateCreated(ut.getDateCreated());
	    po.setDateUpdated(ut.getDateCreated());
	    po.setUserId(ut.getLoginId());
	    poId = dao.insertRow(po, true);
	    return poId;
	}
	catch (Exception e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Adds a purchase order's items to the databse 
     * 
     * @param poId Id of the base purchase order.
     * @param items All related purchase order items
     * @return The total number of rows effect by transaction.
     * @throws VendorPurchasesException
     */
    protected int insertPurchaseOrderItems(int poId, List items) throws VendorPurchasesException {
	int rc = 0;
	//int existItemTotal = 0;
	//List existingItems = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderItems poi = null;

	try {
	    // existingItems = this.findPurchaseOrderItems(poId);
	    // existItemTotal = existingItems == null ? 0 : existingItems.size();

	    // Setup DataSource object to apply database updates.
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);

	    // Apply all items belonging to the base purchase order.
	    for (int ndx = 0; ndx < items.size(); ndx++) {
		poi = (PurchaseOrderItems) items.get(ndx);
		poi.setPoId(poId);
		this.validatePurchaseOrderItem(poi);
		//poi.setId(ndx + 1 + existItemTotal);
		poi.setDateCreated(ut.getDateCreated());
		poi.setDateUpdated(ut.getDateCreated());
		poi.setUserId(ut.getLoginId());
		rc += dao.insertRow(poi, true);
	    }
	    return rc;
	}
	catch (Exception e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Modifies an exisitng purchase order and its items.   If a purchase order has 
     * been received,  change its status to "Received" and update the purchase order 
     * with external reference number, if available. 
     * <p>
     * <b><u>NOTE</u></b>
     * <p>
     * A purchase order can only be updated when its status is either "Quote" or "Submitted".
     * 
     * @param po 
     *          Modified base purchase order data.
     * @param items 
     *          Modified Purchase order items.
     * @param refresh 
     *          boolean which determines whether existing purchase order items should be 
     *          deleted prior to adding revisions.
     * @return -1 when purchase order is not in Quote or Submitted statuses.  1 is returned 
     *         which indicatesthe successful update of a Quote purchase order.   0 indicating 
     *         the purchase order has been completely fulfilled and is in "Received" status.
     * @throws VendorPurchasesException
     */
    protected int updatePurchaseOrder(PurchaseOrder po, List items, boolean refresh) throws VendorPurchasesException {
	int rc = 0;
	int poStatusId = 0;
	PurchaseOrderStatusHist posh = null;

	// Purchase order can only be updated when status is either Quote or Submitted.
	posh = (PurchaseOrderStatusHist) this.findCurrentPurchaseOrderHistory(po.getPoId());
	poStatusId = posh.getPoStatusId();
	if (poStatusId != VendorPurchasesConst.PURCH_STATUS_QUOTE && poStatusId != VendorPurchasesConst.PURCH_STATUS_FINALIZE) {
	    this.logger.log(Level.DEBUG, " Purchase order #" + po.getPoId() + " was denied updates.   Must be in Quote or Submitted status.");
	    return -1;
	}

	// We must be working with a valid po at this point, so add to the database
	this.updatePurchaseOrderBase(po);
	if (refresh) {
	    rc = this.updatePurchaseOrderItems(po.getPoId(), items);
	}
	else {
	    rc = this.insertPurchaseOrderItems(po.getPoId(), items);
	}

	// Determine if purchase order is ready to be placed in "Received" status.  The purchase order quantity of all items 
	// must have been received and the current purchase order status must be "Submitted".
	if (rc == VendorPurchasesConst.PO_UPDATE_RECEIVED && poStatusId == VendorPurchasesConst.PURCH_STATUS_FINALIZE) {
	    this.setPurchaseOrderStatus(po.getPoId(), VendorPurchasesConst.PURCH_STATUS_RECEIVED);
	}
	return rc;
    }

    /**
     * Modifies an exisitng base purchase order
     * 
     * @param po Modified base purchase order data.
     * @return total number of rows updated.  Returns a -1 if purchase order is not 
     *         in Quote or Submitted statuses.
     * @throws VendorPurchasesException
     */
    private void updatePurchaseOrderBase(PurchaseOrder po) throws VendorPurchasesException {
	String method = "[" + this.className + ".updatePurchaseOrder] ";
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrder oldPo = null;
	UserTimestamp ut = null;

	// We must be working with a valid po at this point, so add to the database
	try {
	    // Setup DataSource object to apply database updates.
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // Get old version of data and apply to new object.  This will ensure that unedited 
	    // data persists after applying database updates
	    oldPo = (PurchaseOrder) this.findPurchaseOrder(po.getPoId());
	    po.setNull("status");

	    // Transaction Id should be zero if purchase order is not being finalized.
	    if (po.getXactId() == 0) {
		po.setNull("xactId");
		po.setNull("total");
	    }
	    else {
		po.removeNull("xactId");
		po.removeNull("total");
	    }
	    if (po.getRefNo() != null) {
		po.setRefNo(oldPo.getRefNo());
	    }
	    po.setDateCreated(oldPo.getDateCreated());
	    this.validatePurchaseOrder(po);
	    po.setDateUpdated(ut.getDateCreated());
	    po.setUserId(ut.getLoginId());
	    po.addCriteria(PurchaseOrder.PROP_POID, po.getPoId());
	    dao.updateRow(po);
	    return;
	}
	catch (DatabaseException e) {
	    this.logger.log(Level.DEBUG, method + " DatabaseException - " + e.getMessage());
	    throw new VendorPurchasesException(e);
	}
	catch (SystemException e) {
	    this.logger.log(Level.DEBUG, method + " SystemException - " + e.getMessage());
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Modifies exisitng purchase order items and applys the changes to the database.   
     * All existing items for a purchase order are deleted and replaced by _items when 
     * the purchase order's current status is "Quote".   When the current status is 
     * "Submitted", all exisiting items can no longer be deleted and are sequentially 
     * retrieved from and updated to the database instead.  
     * 
     * @param poId Id of the base purchase order
     * @param items Modified Purchase order items.
     * @return  1 indicating the successful update of a "Quote" purchase order or 
     *          "Submitted" purchase order that has not received its total order 
     *          quantity.   0 indicating the "Submitted" purchase order has been 
     *          completely received. 
     * @throws VendorPurchasesException
     */
    private int updatePurchaseOrderItems(int poId, List items) throws VendorPurchasesException {
	int rc = 0;
	int poStatusId = 0;
	int poUncollectCnt = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderItems deltaPoi = null;
	PurchaseOrderStatusHist posh = null;
	UserTimestamp ut = null;

	// Purchase order can only be updated when status is either Quote or Submitted.
	posh = (PurchaseOrderStatusHist) this.findCurrentPurchaseOrderHistory(poId);
	poStatusId = posh.getPoStatusId();

	// We must be working with a valid po at this point, so add to the database
	try {
	    // Setup DataSource object to apply database updates.
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // Use the current purchase order status to determine if items are to be totally refreshed or 
	    // if each item should be retrieved, reconciled, and updated accordingly.
	    switch (poStatusId) {
	    case VendorPurchasesConst.PURCH_STATUS_QUOTE:
		// Perform a complete refresh of all purchase order items.
		this.deleteAllItems(poId);
		for (int ndx = 0; ndx < items.size(); ndx++) {
		    deltaPoi = (PurchaseOrderItems) items.get(ndx);
		    //deltaPoi.setId(ndx + 1);
		    this.validatePurchaseOrderItem(deltaPoi);
		    deltaPoi.setDateCreated(ut.getDateCreated());
		    deltaPoi.setDateUpdated(ut.getDateCreated());
		    deltaPoi.setUserId(ut.getLoginId());
		    rc = dao.insertRow(deltaPoi, true);
		}
		rc = VendorPurchasesConst.PO_UPDATE_SUCCESSFUL;
		break;

	    case VendorPurchasesConst.PURCH_STATUS_FINALIZE:
		for (int ndx = 0; ndx < items.size(); ndx++) {
		    try {
			deltaPoi = (PurchaseOrderItems) items.get(ndx);
			rc = this.updatePurchaseOrderItem(deltaPoi);
			// Add item's uncollected order quantity 
			poUncollectCnt += (deltaPoi.getQty() - deltaPoi.getQtyRcvd());
		    }
		    catch (NotFoundException e) {
			continue;
		    }
		}
		if (poUncollectCnt == 0) {
		    rc = VendorPurchasesConst.PO_UPDATE_RECEIVED;
		}
		if (poUncollectCnt >= 0) {
		    rc = VendorPurchasesConst.PO_UPDATE_SUCCESSFUL;
		}
		break;
	    } // end switch

	    return rc;
	}
	catch (Exception e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Applies the modifications of an exisitng purchase order item to the database and 
     * updates inventory with the quantity received for _deltaItem.   This method is 
     * generally used during the time of updating a "Submitted" purchase order for the 
     * purpose of reconciling the quantity ordered and quantity received totals for each 
     * item.
     * <p>
     * Basic processing scenario:
     * <p>
     * <ol>
     *    <li>Retrieve the orginal version of the purchase order</li>
     *    <li>Update item's received quantity with input</li>
     *    <li>Calculate difference in quantity ordered and quantity received</li>
     *    <li>Apply item update to the database</li>
     *    <li>Add the difference to uncollected order quantity count for the purchase order</li>
     *  </ol>
     *   
     * @param deltaItem The purchase order item data.
     * @return 0 indicating the total order quantity for this item has beed received.   
     *         > 0 indicating the item's uncollected order quantity.  
     * @throws VendorPurchasesException
     * @throws NotFoundException a problem accessing item data from the database.
     */
    private int updatePurchaseOrderItem(PurchaseOrderItems deltaItem) throws NotFoundException, VendorPurchasesException {
	int rc = 0;
	int adjQtyOnHand = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderItems oldPoi = null;
	UserTimestamp ut = null;

	// We must be working with a valid po item at this point, so apply updates to the database
	try {
	    // Setup DataSource object to apply database updates.
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // Use the current purchase order status to determine if items are to be totally refreshed or 
	    // if each item should be retrieved and updated accordingly.
	    oldPoi = (PurchaseOrderItems) this.findPurchaseOrderItem(deltaItem.getPoId(), deltaItem.getItemId());
	    if (oldPoi == null) {
		this.msg = "Purchase order item, " + deltaItem.getItemId() + ", was not found for PO #" + deltaItem.getPoId();
		this.logger.log(Level.ERROR, this.msg);
		throw new NotFoundException(this.msg);
	    }

	    // Apply Quantity Received and user update timestamp
	    oldPoi.setQtyRcvd(deltaItem.getQtyRcvd() + oldPoi.getQtyRcvd());
	    oldPoi.setDateUpdated(ut.getDateCreated());
	    oldPoi.setUserId(ut.getLoginId());
	    this.validatePurchaseOrderItem(oldPoi);
	    oldPoi.addCriteria(PurchaseOrderItems.PROP_POID, oldPoi.getPoId());
	    oldPoi.addCriteria(PurchaseOrderItems.PROP_ITEMID, oldPoi.getItemId());
	    rc = dao.updateRow(oldPoi);

	    // Update inventory by adding quantity received to Quantity on hand for target item.
	    try {
		InventoryApi invApi = InventoryFactory.createApi(this.connector, this.request);
		ItemMaster im = (ItemMaster) invApi.findItemById(oldPoi.getItemId());
		adjQtyOnHand = im.getQtyOnHand() + this.calculateItemNetOrderQty(oldPoi);
		im.setQtyOnHand(adjQtyOnHand);
		invApi.maintainItemMaster(im, null);
	    }
	    catch (ItemMasterException e) {
		this.msg = "Problemu updating inventory for Purchase order item, " + deltaItem.getItemId() + ", of PO #" + deltaItem.getPoId() + ":  " + e.getMessage();
		throw new VendorPurchasesException(e);
	    }

	    if (rc >= 1) {
		// Set return code to equal the uncollected order quantity for this item.
		rc = oldPoi.getQty() - oldPoi.getQtyRcvd();
	    }
	    return rc;
	}
	catch (Exception e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Deletes a purchase order and all of its items from the database.  Purchase 
     * order can only be deleted from the system when in Quote status
     * 
     * @param poId The purchase order id
     * @return Total number of rows effected by this transaction
     * @throws VendorPurchasesException 
     *           If purchase order status is something other than "Quote".
     */
    public int deletePurchaseOrder(int poId) throws VendorPurchasesException {
	String method = "[" + this.className + ".deletePurchaseOrder] ";
	int rc = 0;
	int poStatusId = 0;
	PurchaseOrder po = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderStatusHist posh = null;

	// Purchase order can only be deleted from the system when in Quote status.
	posh = (PurchaseOrderStatusHist) this.findCurrentPurchaseOrderHistory(poId);
	poStatusId = posh.getPoStatusId();
	if (poStatusId != VendorPurchasesConst.PURCH_STATUS_QUOTE) {
	    this.msg = method + " Purchase order #" + poId + " cannot be deleted.   Must be in Quote status to perform deletes.";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new VendorPurchasesException(this.msg, -1);
	}

	// Begin to delete data.
	po = (PurchaseOrder) this.findPurchaseOrder(poId);
	try {
	    po.addCriteria(PurchaseOrder.PROP_POID, po.getPoId());
	    rc += dao.deleteRow(po);
	    this.logger.log(Level.DEBUG, "Purchase Order #" + po.getPoId() + " was successfully deleted");
	    rc += this.deleteAllItems(poId);
	    return rc;
	}
	catch (DatabaseException e) {
	    this.logger.log(Level.DEBUG, method + " DatabaseException - " + e.getMessage());
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Deletes one purchase order item from the database.
     * 
     * @param poId Id of the purchase order which all items will be removed.
     * @param poItemId Id of the purchase order item
     * @return The number of rows effected
     * @throws VendorPurchasesException
     */
    public int deleteItem(int poId, int poItemId) throws VendorPurchasesException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderItems poi = null;

	poi = (PurchaseOrderItems) this.findPurchaseOrderItem(poId, poItemId);
	try {
	    poi.addCriteria(PurchaseOrderItems.PROP_POID, poId);
	    poi.addCriteria(PurchaseOrderItems.PROP_ITEMID, poItemId);
	    rc = dao.deleteRow(poi);
	    this.logger.log(Level.DEBUG, "PO Item #" + poItemId + " was successfully deleted from PO #" + poi.getPoId());
	}
	catch (DatabaseException e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new VendorPurchasesException(e);
	}
	return rc;
    }

    /**
     * Deletes all items belonging to a purchase order.
     * 
     * @param poId Id of the purchase order which all items will be removed.
     * @return The total number of rows effected.
     * @throws VendorPurchasesException
     */
    public int deleteAllItems(int poId) throws VendorPurchasesException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderItems poi = null;
	List list = null;

	list = this.findPurchaseOrderItems(poId);
	if (list == null) {
	    return 0;
	}

	try {
	    // Cycle through all items of the purchase order.
	    for (int ndx = 0; ndx < list.size(); ndx++) {
		poi = (PurchaseOrderItems) list.get(ndx);
		poi.addCriteria(PurchaseOrderItems.PROP_POID, poId);
		rc += dao.deleteRow(poi);
		this.logger.log(Level.DEBUG, "PO Item #" + poId + " was successfully deleted from PO #" + poi.getPoId());
	    }
	}
	catch (DatabaseException e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new VendorPurchasesException(e);
	}
	return rc;
    }

    /**
     * Validates base purchase order data.
     * <p>
     * <p>
     * The following validations must be met:
     * <ul>
     *    <li>A purchase order's creditor id must be greater than zero</li>
     *    <li>Creditor must be a vendor type</li>
     * </ul> 
     * 
     * @param po Purchase order object containing the base data.
     * @throws VendorPurchasesException if the creditor value is less than or equal to zero, or creditor does not exist, or creditor is not a vendor type.   
     */
    protected void validatePurchaseOrder(PurchaseOrder po) throws VendorPurchasesException {
	CreditorType ctVend = null;
	CreditorApi credApi = null;
	Creditor cred = null;

	// Get creditor data to used as validating metric
	try {
	    credApi = CreditorFactory.createApi(this.connector, this.request);
	    ctVend = (CreditorType) credApi.findCreditorTypeById(AccountingConst.CREDITOR_TYPE_VENDOR);

	    // Creditor id must be greater than zero.
	    if (po.getCreditorId() <= 0) {
		this.msg = "Creditor id must be greater than zero";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 432);
	    }

	    // Creditor must exist in the database.
	    cred = (Creditor) credApi.findById(po.getCreditorId());
	    if (cred == null) {
		this.msg = "Creditor must exist in the database";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 433);
	    }

	    // Creditor must be of type vendor.
	    if (cred.getCreditorTypeId() != ctVend.getCreditorTypeId()) {
		this.msg = "Creditor must be of type vendor";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 434);
	    }
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Validates an item belonging to a purchase order.  
     * <p>
     * <p>
     * The following validations must be met:
     * <ul>
     *    <li>The item must contain a valid purchase order id which must be greater than zero.  This check is only performed for existing purchase order items</li>
     *    <li>Item Master Id must be a valid and is greater than zero</li>
     *    <li>The selected item master id must be a tangible and resalable inventory item</li>
     *    <li>The value of quantity orderd must be greater than or equal to zero</li>
     *    <li>The value of quantity received must be less than or equal to quantity ordered</li>
     * </ul>
     * 
     * @param poi The purchase order item being evaluated.
     * @throws VendorPurchasesException
     */
    protected void validatePurchaseOrderItem(PurchaseOrderItems poi) throws VendorPurchasesException {
	PurchaseOrder po = null;
	InventoryApi invApi = null;
	ItemMaster im = null;
	ItemMasterType imt = null;

	try {

	    if (poi == null) {
		this.msg = "Purchase order object is invalid";
		this.logger.log(Level.FATAL, this.msg);
		throw new VendorPurchasesException(this.msg, 454);

	    }
	    // Purchase order id must be greater than zero for existing PO items
	    if (poi.getPoId() < 0) {
		this.msg = "Purchase order id must be greater than zero for existing PO items";
		this.logger.log(Level.FATAL, this.msg);
		throw new VendorPurchasesException(this.msg, 435);
	    }
	    // Purchase order id must be exist in the system
	    po = (PurchaseOrder) this.findPurchaseOrder(poi.getPoId());
	    if (po == null) {
		this.msg = "Purchase order must be exist in the system";
		this.logger.log(Level.FATAL, this.msg);
		throw new VendorPurchasesException(this.msg, 436);
	    }

	    // Item master id must be valid and greater than zero.
	    if (poi.getItemId() <= 0) {
		this.msg = "Item master id for purchase order must be valid and greater than zero";
		this.logger.log(Level.FATAL, this.msg);
		throw new VendorPurchasesException(this.msg, 437);
	    }
	    // Get Item Master item.
	    invApi = InventoryFactory.createApi(this.connector, this.request);
	    im = (ItemMaster) invApi.findItemById(poi.getItemId());
	    if (im == null) {
		this.msg = "Item master record for item, " + poi.getItemId();
		this.logger.log(Level.FATAL, this.msg);
		throw new VendorPurchasesException(this.msg, 438);
	    }

	    // Item must be a tangible resalable inventory item.
	    imt = (ItemMasterType) invApi.findItemTypeById(ItemConst.ITEM_TYPE_MERCH);
	    if (im.getItemTypeId() != imt.getItemTypeId()) {
		this.msg = "Item must be a tangible resalable inventory item";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 439);
	    }

	    // Qty ordered must be >= to zero.
	    if (poi.getQty() < 0) {
		this.msg = "Qty ordered must be greater than or equal to zero";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 440);
	    }

	    // Qty received must be <= Qty ordered
	    if (poi.getQtyRcvd() > poi.getQty()) {
		this.msg = "Qty ordered must be less than or equal to qty ordered";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 441);
	    }
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Assigns a new status to a purchase order and applies the changes to purchase order 
     * status history table in the database.  Before the new status assignment, the current 
     * status is terminated by assigning an end date of the current day.   Since there is 
     * no logic implemented in this method to govern the movement of purchase order statuses, 
     * invoke method, verifyStatusChange(int, int), prior to this method in order to verify 
     * that moving to the new status is in alignment with the business rules of changing 
     * purchase order statuses. 
     * 
     * @param poId The id of the target purchase order
     * @param newStatusId The id of status that is to be assigned to the purchase order.
     * @throws VendorPurchasesException
     */
    public void setPurchaseOrderStatus(int poId, int newStatusId) throws VendorPurchasesException {
	UserTimestamp ut = null;
	PurchaseOrderStatusHist posh = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	posh = (PurchaseOrderStatusHist) this.findCurrentPurchaseOrderHistory(poId);
	try {
	    if (posh != null) {
		ut = RMT2Date.getUserTimeStamp(this.request);
		posh.setEndDate(ut.getDateCreated());
		posh.setUserId(ut.getLoginId());
		posh.addCriteria(PurchaseOrderStatusHist.PROP_POSTATUSHISTID, posh.getPoStatusHistId());
		dao.updateRow(posh);
	    }

	    // Add new status
	    posh = VendorPurchasesFactory.createPurchaseOrderStatusHist(poId, newStatusId);
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    posh.setEffectiveDate(ut.getDateCreated());
	    posh.setNull("endDate");
	    posh.setUserId(ut.getLoginId());
	    dao.insertRow(posh, true);
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Verifies that changing the status of the purchase order identified as _poId to the new status represented as _newStatusId is legal.
     * The change is considered legal only if an exception is not thrown.
     * <p>
     * The following sequence must be followed when changing the status of a purchase order:
     * <p>
     * <ul>
     * <li>The purchase order must be new in order to change the status to "Quote"</li>
     * <li>The purchase order must be in "Quote" status before changing to "Submitted".</li>
     * <li>The purchase order must be in "Submitted" status before changing to "Received".</li>
     * <li>The purchase order must be in "Quote" or "Submitted" status before changing to "Cancelled".</li>
     * </ul>
     * 
     * @param poId Target purchase order id
     * @param newStatusId The id of the status that is to be assigned to the purchase order.
     * @return The id of the old status.
     * @throws VendorPurchasesException When the prospective new status is not in sequence to the current status regarding 
     * changing the status of the purchase order.   The exception should give a detail explanation as to the reason why the 
     * status cannot be changed. 
     */
    protected int verifyStatusChange(int poId, int newStatusId) throws VendorPurchasesException {
	PurchaseOrderStatusHist posh = null;
	int currentStatusId = 0;

	posh = (PurchaseOrderStatusHist) this.findCurrentPurchaseOrderHistory(poId);
	currentStatusId = (posh == null ? VendorPurchasesConst.NEW_PO_STATUS : posh.getPoStatusId());
	switch (newStatusId) {
	case VendorPurchasesConst.PURCH_STATUS_QUOTE:
	    if (currentStatusId != VendorPurchasesConst.NEW_PO_STATUS) {
		this.msg = "Quote status can only be assigned when the purchase order is new";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 455);
	    }
	    break;

	case VendorPurchasesConst.PURCH_STATUS_FINALIZE:
	    if (currentStatusId != VendorPurchasesConst.PURCH_STATUS_QUOTE) {
		this.msg = "Purchase order must be in Quote status before changing to Submitted";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 453);
	    }
	    break;

	case VendorPurchasesConst.PURCH_STATUS_RECEIVED:
	    if (currentStatusId != VendorPurchasesConst.PURCH_STATUS_FINALIZE) {
		this.msg = "Purchase order must be in Submitted status before changing to Received";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 452);
	    }
	    break;

	case VendorPurchasesConst.PURCH_STATUS_CANCEL:
	    switch (currentStatusId) {
	    case VendorPurchasesConst.PURCH_STATUS_QUOTE:
	    case VendorPurchasesConst.PURCH_STATUS_FINALIZE:
		break;

	    default:
		this.msg = "Purchase order must be in Quote or Submitted status before changing to Cancelled";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg, 451);

	    } // end inner switch
	    break;

	case VendorPurchasesConst.PURCH_STATUS_RETURN:
	    switch (currentStatusId) {
	    case VendorPurchasesConst.PURCH_STATUS_RECEIVED:
	    case VendorPurchasesConst.PURCH_STATUS_PARTRET:
		break;

	    default:
		this.msg = "Purchase order must be in Received status before changing to Returned";
		this.logger.log(Level.ERROR, this.msg);
		throw new VendorPurchasesException(this.msg);
	    } // end inner switch
	    break;
	} // end outer switch

	return currentStatusId;
    }

    /**
     * Obtains the total amount of a purchase order which may include 1) purchase order 
     * item total,  2) taxes, and 3) other fees.
     * 
     * @param poId The Id of the target purchase order 
     * @return Total amount
     * @throws VendorPurchasesException
     */
    public double calcPurchaseOrderTotal(int poId) throws VendorPurchasesException {
	double total = 0;

	total += this.calculateItemTotal(poId);
	total += this.calculateTaxes(poId);
	total += this.calculateOtherFees(poId);
	return total;
    }

    /**
     * Calculates the purchase order item total which is Qty * Unit Cost.
     * 
     * @param poId The id of the target purchase order
     * @return Item Total
     * @throws VendorPurchasesException
     */
    private double calculateItemTotal(int poId) throws VendorPurchasesException {
	List items = null;
	PurchaseOrderItems poi = null;
	double total = 0;

	items = this.findPurchaseOrderItems(poId);
	if (items == null) {
	    return 0;
	}
	// Cycle through all items summing each as (qty * unit_price)
	for (int ndx = 0; ndx < items.size(); ndx++) {
	    poi = (PurchaseOrderItems) items.get(ndx);
	    total += poi.getUnitCost() * poi.getQty();
	}
	return total;
    }

    /**
     * Calculates the purchase order taxes.
     * 
     * @param poId The id of the target purchase order
     * @return Purchase order tax amount.
     * @throws VendorPurchasesException
     */
    private double calculateTaxes(int poId) throws VendorPurchasesException {
	return 0;
    }

    /**
     * Calculate other fees that may be applicable to the purchase order.
     * 
     * @param poId The id of the target purchase order
     * @return Other fee total.
     * @throws VendorPurchasesException
     */
    private double calculateOtherFees(int poId) throws VendorPurchasesException {
	return 0;
    }

    /**
     * This method begins the process of realizing the purchase order as a transaction.   
     * Firstly, a purchase order transaction is created, secondly, the creditor is realizes 
     * the transaction, thirdly, the pusrchase order itself is associated with the 
     * transaction, and lastly, the status is changed to "Submitted".  The completion of 
     * the previous tasks are pending on the fact that the status change is truly legal 
     * base on current business rules.  Creates a purchase order transaction.
     * 
     * @param po The purchase order
     * @param items Purchase order items
     * @return Transaction Id.
     * @throws VendorPurchasesException
     */
    public int submitPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException {
	double poTotal = 0;
	int xactId = 0;
	Xact xact = null;
	UserTimestamp ut = null;

	this.verifyStatusChange(po.getPoId(), VendorPurchasesConst.PURCH_STATUS_FINALIZE);
	this.maintainPurchaseOrder(po, items);
	poTotal = this.calcPurchaseOrderTotal(po.getPoId());
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // Setup Transaction
	    xact = XactFactory.createXact();
	    xact.setXactAmount(poTotal);
	    xact.setXactDate(ut.getDateCreated());
	    xact.setXactTypeId(XactConst.XACT_TYPE_INVPURCHASES);
	    xact.setReason("Submitted Inventory Purchase Order " + po.getPoId());
	    xact.setNull("tenderId");
	    xactId = this.maintainXact(xact, null);

	    // Associate transaction with creditor
	    this.createCreditorActivity(po.getCreditorId(), xactId, poTotal);

	    // Associate transaction with PO
	    po.setXactId(xactId);
	    po.setTotal(poTotal);
	    this.updatePurchaseOrderBase(po);

	    // Change purchase order status to finalized
	    this.setPurchaseOrderStatus(po.getPoId(), VendorPurchasesConst.PURCH_STATUS_FINALIZE);

	    return xactId;
	}
	catch (Exception e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * This will cancel purchase orders.   The purchase order is required to be in "Quote" 
     * or "Submitted" status prior to cancellation, and cannot undergo a partial cancellation.   
     * Inventory will not be pulled as a result of this transaction.
     *    
     * @param poId The id of target purchase order
     * @throws VendorPurchasesException If current status cannot be cancelled
     */
    public void cancelPurchaseOrder(int poId) throws VendorPurchasesException {
	PurchaseOrder po = (PurchaseOrder) this.findPurchaseOrder(poId);
	Xact xact = null;
	int currentStatus = 0;

	// Check to see if it is okay to cancel this PO.  An exception will be thrown if chech fails!
	currentStatus = this.verifyStatusChange(poId, VendorPurchasesConst.PURCH_STATUS_CANCEL);

	// Reverse transaction if purchase order has been submitted and a transaction has been assigned.
	if (currentStatus == VendorPurchasesConst.PURCH_STATUS_FINALIZE && po.getXactId() > 0) {
	    try {
		xact = this.findXactById(po.getXactId());
		this.reverseXact(xact, null);
		this.createCreditorActivity(po.getCreditorId(), xact.getXactId(), xact.getXactAmount());
	    }
	    catch (XactException e) {
		throw new VendorPurchasesException(e);
	    }
	}

	// Cancel Purchase Order by changing the status.
	this.setPurchaseOrderStatus(poId, VendorPurchasesConst.PURCH_STATUS_CANCEL);
    }

    /**
     * 
     */
    protected void preReverseXact(Xact xact, List xactItems) {
	xact.setNull("tenderId");
	return;
    }

    /**
     * This method executes a purchases, returns, and allowances transaction which will 
     * allow one or more items of the purchase order to be returned.   It is required 
     * for the purchase order to be in "Received" or "Partially Received" status before 
     * a return is performed.   Inventory will be pulled for each item involved in this 
     * transaction.  The purchase order will be flagged with a status of "Returned" when 
     * the summation of quantity ordered minus quantity returned for each  item of the 
     * purchase order equals zero.   Otherwise, the purchase order will be placed in 
     * Partially Returned status. 
     * 
     * @param poId The id of target purchase order
     * @param items The items that are to be returned.
     * @throws VendorPurchasesException 
     *           When the return quantity exceeds the quantity available for an item, 
     *           database error occurs, or a system error occurs. 
     */
    public void returnPurchaseOrder(int poId, List items) throws VendorPurchasesException {
	PurchaseOrder po = (PurchaseOrder) this.findPurchaseOrder(poId);
	Xact xact = null;
	int rc = 0;

	// Check to see if it is okay to cancel this PO.  An exception will be thrown if chech fails!
	this.verifyStatusChange(poId, VendorPurchasesConst.PURCH_STATUS_RETURN);

	// At this point it is evident that we have a transaction to reverse since this.verifyStatusChange did not bomb!
	try {
	    xact = this.findXactById(po.getXactId());
	    this.reverseXact(xact, null);
	    // Associate transaction with creditor activity
	    this.createCreditorActivity(po.getCreditorId(), xact.getXactId(), xact.getXactAmount());
	}
	catch (XactException e) {
	    throw new VendorPurchasesException(e);
	}

	// Pull applicable items from inventory
	rc = this.pullPurchaseOrderInventory(items);

	// Flagg Purchase Order as either Returned or Partially Returned
	if (rc >= 1) {
	    this.setPurchaseOrderStatus(poId, VendorPurchasesConst.PURCH_STATUS_PARTRET);
	}
	else {
	    this.setPurchaseOrderStatus(poId, VendorPurchasesConst.PURCH_STATUS_RETURN);
	}
    }

    /**
     * Pulls inventory for each purchase order item based on the requested item quantity 
     * return amount.
     * 
     * @param items 
     *         List of purchase order items accompanied with a quantity return value.
     * @return 0 when all items of the purchase order have been returned.  >= 1 when an 
     *         order quantity exists for one or more items after the returns are applied.
     * @throws VendorPurchasesException 
     *            When return quantity exceeds the order quantity, an item master error 
     *            occurs, a database error occurs, or a system error occurrs.
     */
    private int pullPurchaseOrderInventory(List items) throws VendorPurchasesException {
	int availQty = 0;
	int qtyRtn = 0;
	int totalOrderQty = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	PurchaseOrderItems deltaPoi = null;
	PurchaseOrderItems oldPoi = null;

	try {
	    // Setup DataSource object to apply database updates.
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);

	    // Apply all items belonging to the base purchase order.
	    for (int ndx = 0; ndx < items.size(); ndx++) {
		deltaPoi = (PurchaseOrderItems) items.get(ndx);
		oldPoi = (PurchaseOrderItems) this.findPurchaseOrderItem(deltaPoi.getPoId(), deltaPoi.getItemId());
		availQty = this.calculateItemNetOrderQty(oldPoi);
		qtyRtn = deltaPoi.getQtyRtn();
		if (availQty < qtyRtn) {
		    this.msg = "Return Quantity (" + qtyRtn + ") cannot exceed Available Quantity(" + availQty + ") for purchase order";
		    this.logger.log(Level.ERROR, this.msg);
		    throw new VendorPurchasesException(this.msg, 450);
		}

		oldPoi.setQtyRtn(oldPoi.getQtyRtn() + qtyRtn);
		oldPoi.setDateUpdated(ut.getDateCreated());
		oldPoi.setUserId(ut.getLoginId());
		oldPoi.addCriteria(PurchaseOrderItems.PROP_POID, deltaPoi.getPoId());
		oldPoi.addCriteria(PurchaseOrderItems.PROP_ITEMID, deltaPoi.getItemId());
		dao.updateRow(oldPoi);

		// Keep track of the order quantity for each item after thr return quantity is applied. 
		totalOrderQty += this.calculateItemNetOrderQty(oldPoi);

		// Pull item from inventory based on the amount request (qtyRtn).
		try {
		    InventoryApi invApi = InventoryFactory.createApi(this.connector);
		    invApi.pullInventory(oldPoi.getItemId(), qtyRtn);
		}
		catch (ItemMasterException e) {
		    throw new VendorPurchasesException(e);
		}
	    } // end for
	    return totalOrderQty;
	}
	catch (DatabaseException e) {
	    throw new VendorPurchasesException(e);
	}
	catch (SystemException e) {
	    throw new VendorPurchasesException(e);
	}
    }

    /**
     * Calculates the net order quantity of a purchase order item.   The net order quantity is basically the order quantity of a 
     * purchase order item available to be applied to inventory based on the current state of a purchase order item's 
     * order quantity, quantity received, and quantity returned.  The formula for this calculation is:
     * <p>
     * <p>
     *  Beginning order quantity - (beginning order quantity - quantity received) - quantity returned.
     *   
     * @param poItem
     * @return The net order quantity
     */
    private int calculateItemNetOrderQty(PurchaseOrderItems poItem) {
	int begOrdQty = poItem.getQty();
	int returnQty = poItem.getQtyRtn();
	int recvQty = poItem.getQtyRcvd();
	int remainOrdQty = 0;
	int adjOrdQty = 0;
	int netOrdQty = 0;

	remainOrdQty = begOrdQty - recvQty;
	adjOrdQty = begOrdQty - remainOrdQty;
	netOrdQty = adjOrdQty - returnQty;

	return netOrdQty;
    }
}
