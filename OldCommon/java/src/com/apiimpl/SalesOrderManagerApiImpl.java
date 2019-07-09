package com.apiimpl;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.ArrayList;

import com.api.SalesOrderApi;
import com.api.GLCustomerApi;
import com.api.InventoryApi;
import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.bean.SalesInvoice;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;
import com.bean.CombinedSalesOrder;
import com.bean.VwSalesOrderInvoice;
import com.bean.Xact;
import com.bean.Customer;
import com.bean.ItemMaster;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.constants.ItemConst;
import com.constants.SalesConst;
import com.constants.XactConst;

import com.factory.SalesFactory;
import com.factory.AcctManagerFactory;
import com.factory.InventoryFactory;

import com.util.ItemMasterException;
import com.util.SalesOrderException;
import com.util.SystemException;
import com.util.RMT2Utility;
import com.util.XactException;
import com.util.GLAcctException;

/**
 * This class implements the functionality required for creating, maintaining, cancelling, refunding, and trackings sales orders.
 *  <p>
 *  <p>
 * When a sales order is invoiced, the base transaction amount is posted to the xact table as a positive value, and the 
 * customer activity amount is posted as a positive value which increases the value of the company's revenue and the 
 * customer's account.   Conversely, when a sales order is cancelled or refunded,  the base transaction amount is posted 
 * to the xact table as a negative value, and the customer activity amount is posted as negative value which decreases the 
 * value of the company's revenue and the customer's account.
 * 
 * @author Roy Terrell
 *
 */
public class SalesOrderManagerApiImpl extends XactManagerApiImpl implements SalesOrderApi  {

   private String criteria;
   private Logger logger;


   /**
    * Default Constructor.
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
   protected SalesOrderManagerApiImpl() throws DatabaseException, SystemException   {
	   super();  
   }


   /**
    * Constructs a sales order api with a specified database connection bean
    * 
    * @param dbConn
    * @throws DatabaseException
    * @throws SystemException
    */
    public SalesOrderManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
    	super(dbConn);
		this.setBaseView("SalesOrderItemsBySalesOrderView");
		this.setBaseClass("com.bean.SalesOrderItemsBySalesOrder");
		this.logger = Logger.getLogger("SalesOrderManagerApiImpl");
    }

    /**
     * Constructs a sales order api with the specified database connection bean and servlet request object
     * 
     * @param dbConn {@link DatabaseConnectionBean} object
     * @param _request Servlet request object
     * @throws DatabaseException
     * @throws SystemException
     */
    public SalesOrderManagerApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
        super(dbConn);
        this.setRequest(_request);
        this.logger = Logger.getLogger("SalesOrderManagerApiImpl");

    }

    /**
     * @deprecated
     */
	  public List findSalesOrderItemsBySalesOrder(int _soId) throws SalesOrderException {
		  this.setBaseView("SalesOrderItemsView");
		  this.setBaseClass("com.bean.SalesOrderItems");
		  this.criteria = "sales_order_items.sales_order_id  = " + _soId;
		  try {
		      List list = this.find(this.criteria);
			  return list;
		  }
		  catch (SystemException e) {
			  this.msgArgs.clear();
			  this.msgArgs.add(e.getMessage());
			  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
		  }
	  }


	  public SalesOrder findSalesOrderById(int _soId) throws SalesOrderException {
	      List list = null;
          this.setBaseView("SalesOrderView");
          this.setBaseClass("com.bean.SalesOrder");
          this.criteria = "id  = " + _soId;
          try {
        	  list = this.find(this.criteria);
        	  if (list == null || list.size() <= 0) {
        		  return null;
        	  }
        	  return (SalesOrder) list.get(0);
          }
          catch (SystemException e) {
        	  this.msgArgs.clear();
			  this.msgArgs.add(e.getMessage());
			  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
          }
	  }

	  public List  findSalesOrderByCustomer(int _customerId) throws SalesOrderException {
		  this.setBaseView("SalesOrderView");
          this.setBaseClass("com.bean.SalesOrder");
		  this.criteria = "sales_order.customer_id  = " + _customerId;
		  try {
		      List list = this.find(this.criteria);
			  return list;
		  }
		  catch (SystemException e) {
			  this.msgArgs.clear();
			  this.msgArgs.add(e.getMessage());
			  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
		  }
	  }

	  /**
	   * Retrieves a list of extending sales orders related to a given customer.
	   * 
	   * @param _customerId
	   * @return ArrayList of {@link CombinedSalesOrder} objects
	   * @throws SalesOrderException
	   */
	  public List findExtendedSalesOrderByCustomer(int _customerId) throws SalesOrderException {
	      List results = new ArrayList();
		  VwSalesOrderInvoice vsoi = null;
		  String oldView = this.setBaseView("VwSalesOrderInvoiceView");
		  String oldClass = this.setBaseClass("com.bean.VwSalesOrderInvoice");

		  this.criteria = "customer_id  = " + _customerId;
		  try {
			  // Get sales order data
		      List list = this.find(this.criteria);
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
			  this.msgArgs.clear();
			  this.msgArgs.add(e.getMessage());
			  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
		  }
		  finally {
			  this.setBaseView(oldView);
			  this.setBaseClass(oldClass);
		  }
	  }
	  

	  public List  findInvoicedSalesOrderByCustomer(int _customerId) throws SalesOrderException {
		  this.criteria = "customer_id = " + _customerId + " and invoiced = 1";
		  try {
		      List list = this.find(this.criteria);
			  return list;
		  }
		  catch (SystemException e) {
			  this.msgArgs.clear();
			  this.msgArgs.add(e.getMessage());
			  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
		  }
	  }


	  public List findSalesOrderItems(int _soId) throws SalesOrderException {
		  this.setBaseView("SalesOrderItemsView");
		  this.setBaseClass("com.bean.SalesOrderItems");
		  this.criteria = "sales_order_id  = " + _soId;
		  try {
		      List list = this.find(this.criteria);
			  return list;
		  }
		  catch (SystemException e) {
			  this.msgArgs.clear();
			  this.msgArgs.add(e.getMessage());
			  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
		  }
	  }



	  public SalesInvoice findSalesOrderInvoice(int _salesOrderId) throws SalesOrderException {
	      String oldView = this.setBaseView("SalesInvoiceView");
	      String oldClass = this.setBaseClass("com.bean.SalesInvoice");
	      this.criteria = "sales_order_id  = " + _salesOrderId;
	      try {
	          List list = this.find(this.criteria);
	    	  if (list == null || list.size() == 0) {
	    		  return null;
	    	  }
	    	  return (SalesInvoice) list.get(0);
	      }
	      catch (SystemException e) {
	    	  this.msgArgs.clear();
	    	  this.msgArgs.add(e.getMessage());
	    	  throw new SalesOrderException(this.connector, 1000, this.msgArgs);
	      }
	      finally {
	    	  this.setBaseView(oldView);
	    	  this.setBaseClass(oldClass);
	      }
	  }
	  
	  
	  public SalesOrderStatus findSalesOrderStatus(int _salesOrderStatusId) throws SalesOrderException {
         String oldView = this.setBaseView("SalesOrderStatusView");
			String oldClass = this.setBaseClass("com.bean.SalesOrderStatus");
			this.criteria = "id  = " + _salesOrderStatusId;
			try {
			    List list = this.find(this.criteria);
				if (list == null || list.size() == 0) {
				    return null;
				}
				return (SalesOrderStatus) list.get(0);
			}
			catch (SystemException e) {
			    this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new SalesOrderException(this.connector, 1000, this.msgArgs);
			}
			finally {
				  this.setBaseView(oldView);
				  this.setBaseClass(oldClass);
			}
	  }
	  
	  

   public List findSalesOrderStatusHistBySOId(int _salesOrderId) throws SalesOrderException {
	   this.setBaseView("SalesOrderStatusHistView");
	   this.setBaseClass("com.bean.SalesOrderStatusHist");
	   this.criteria = "sales_order_id = " + _salesOrderId;
	   	try {
	   	 List list = this.find(this.criteria);
			return list;
		}
		catch (SystemException e) {
			this.msgArgs.clear();
			this.msgArgs.add(e.getMessage());
			throw new SalesOrderException(this.connector, 1000, this.msgArgs);
		}
   }

   public SalesOrderStatusHist findCurrentSalesOrderStatusHist(int _soId) throws SalesOrderException {
       this.setBaseView("SalesOrderStatusHistView");
       this.setBaseClass("com.bean.SalesOrderStatusHist");
       this.criteria = "sales_order_id = " + _soId + " and end_date is null";
       try {
           List list = this.find(this.criteria);
    	   if (list.size() == 0 || list.size() == 0) {
    		   return null;
    	   }
    	   return (SalesOrderStatusHist) list.get(0);
       }
       catch (IndexOutOfBoundsException e) {
    	   this.msgArgs.clear();
    	   this.msgArgs.add(String.valueOf(_soId));
    	   throw new SalesOrderException(this.connector, 704, this.msgArgs);
       }
       catch (SystemException e) {
    	   this.msgArgs.clear();
    	   this.msgArgs.add(e.getMessage());
    	   throw new SalesOrderException(this.connector, 1000, this.msgArgs);
       }
   }

   /**
    * Drives the process of creating a new or modifying an existing Sales Order.     The Customer Id must contain a 
    * value > zero, and there must be at least on item to process if we are creating a Sales Order.  _items is an 
    * ArrayList of SalesOrder items that must created prior to invoking this method.
    * 
    * @param _so
    * @param _cust
    * @param _items
    * @return Sales order id.
    * @throws SalesOrderException
    */
   public int maintainSalesOrder(SalesOrder _so, Customer _cust, List _items)  throws SalesOrderException {
			 int      soId;

			 if (_so == null) {
				 //TODO: Create message in database
				 throw new SalesOrderException("Sales order cannot be null");
				 //throw new SalesOrderException(this.dbo, 702, null);
			 }
			 //  Customer Id must be a value greater than zero.
			 if (_cust != null && _cust.getId() <= 0) {
			     throw new SalesOrderException(this.connector, 701, null);
			}

    	   // Verify that new sales order will have items.
			if (_items != null && _items.size() > 0) {
			    //  Items are available to be processed.
			}
			else {
			    throw new SalesOrderException(this.connector, 702, null);
			}

         // Determine if we are creating or modifying a Sales Order.
			if (_so.getId() <= 0) {
			    soId = this.createSalesOrder(_so, _cust, _items);
			}
			else {
			    soId = this.updateSalesOrder(_so, _cust, _items);
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
  private int createSalesOrder(SalesOrder _so, Customer _cust) throws SalesOrderException {
      int soId = 0;
      Object temp = null;
	  GLCustomerApi  custApi = null;
      DaoApi dao = DataSourceFactory.createDao(this.connector);
      UserTimestamp ut = null;

	  try {
		  custApi = AcctManagerFactory.createCustomer(this.connector);
		  temp = custApi.findCustomerById(_cust.getId());
		  if (temp == null) {
			  throw new SalesOrderException("Sales Order cannot be add, because customer is invalid");
		  }
          ut = RMT2Utility.getUserTimeStamp(this.request);
		  _so.setCustomerId(_cust.getId());
          _so.setDateCreated(ut.getDateCreated());
          _so.setDateUpdated(ut.getDateCreated());
          _so.setUserId(ut.getLoginId());
		  soId = dao.insertRow(_so, true);
		  return soId;
	  }
	  catch (GLAcctException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (DatabaseException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (SystemException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
  }
  
  
  private double createSalesOrderItems(int _soId, List _items) throws SalesOrderException {
	  SalesOrderItems soi = null;
	  int itemCount = _items.size();
      double soAmount = 0;

	  this.deleteSalesOrderItems( _soId);
	  for (int ndx = 0; ndx < itemCount; ndx++) {
		  soi = (SalesOrderItems) _items.get(ndx);
          soi.setSalesOrderId(_soId);
          soi.setId(ndx + 1);
		  this.createSalesOrderItem(soi);
          soAmount += soi.getOrderQty() * (soi.getInitUnitCost() * soi.getInitMarkup());
	  }
	  return soAmount;
  }
  
  private int createSalesOrderItem(SalesOrderItems _soi) throws SalesOrderException {
	  double backOrderQty = 0;
	  int rc = 0;
	  ItemMaster im = null;
	  InventoryApi itemApi = null;
      DaoApi dao = DataSourceFactory.createDao(this.connector);
	  
	  try {
		  itemApi = InventoryFactory.createInventoryApi(this.connector);
		  im = itemApi.findItemById(_soi.getItemMasterId());
		  
		  // Compare order quantity of item to actual inventory quantity on hand to determine if back order is required.
		  if (_soi.getOrderQty() > im.getQtyOnHand()) {
			  backOrderQty = _soi.getOrderQty() - im.getQtyOnHand();
			  _soi.setBackOrderQty(backOrderQty);
			  //_soi.setOrderQty(im.getQtyOnHand());
		  }
		  // Apply to database
          if (_soi.getInitMarkup() <= 0) {
              _soi.setInitMarkup(im.getMarkup());    
          }
          if (_soi.getInitUnitCost() <= 0) {
              _soi.setInitUnitCost(im.getUnitCost());    
          }
		  rc = dao.insertRow(_soi, false);
		  return rc;
	  }
	  catch (ItemMasterException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (DatabaseException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (SystemException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
  }

  
  
  protected int updateSalesOrder(SalesOrder _so, Customer _cust, List _items) throws SalesOrderException {
       double orderAmount = 0;
	   
       orderAmount = this.createSalesOrderItems(_so.getId(), _items);
       _so.setOrderTotal(orderAmount);
       this.updateSalesOrder(_so, _cust);

	   // No need to update status at this point.
	   return _so.getId();
  }


  private int updateSalesOrder(SalesOrder _so, Customer _cust) throws SalesOrderException {
      int rc = 0;
      Object temp = null;
	  GLCustomerApi  custApi = null;
      DaoApi dao = DataSourceFactory.createDao(this.connector);
      UserTimestamp ut = null;

	  try {
		  custApi = AcctManagerFactory.createCustomer(this.connector);
		  temp = custApi.findCustomerById(_cust.getId());
		  if (temp == null) {
			  throw new SalesOrderException("Sales Order cannot be update, because customer is invalid");
		  }
          ut = RMT2Utility.getUserTimeStamp(this.request);		  
		  _so.setCustomerId(_cust.getId());
          _so.setDateUpdated(ut.getDateCreated());
          _so.setUserId(ut.getLoginId());
          _so.addCriteria("Id", _so.getId());
		  rc = dao.updateRow(_so);
		  return rc;
	  }
	  catch (GLAcctException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (DatabaseException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (SystemException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
  }

  
  /**
   * Deletes a sales order including its items.  <p>The sales order must be currently in quote status in order to be deleted.
   * 
   * @param _salesOrder  Sales Order object to delete.
   * @throws SalesOrderException When Sales Order status is something other than Quote status or for general database access error.
   */
  public void deleteSalesOrder(SalesOrder _salesOrder) throws SalesOrderException {
	  if (_salesOrder ==  null) {
		  throw new SalesOrderException("Delte Faile:  Sales order object is invalid");
	  }

	  //  Sales order must be in quote or new status to be deleted.
	  SalesOrderStatusHist sosh = this.findCurrentSalesOrderStatusHist(_salesOrder.getId());
	  SalesOrderStatus sos = this.findSalesOrderStatus(sosh.getSalesOrderStatusId());
	  switch (sos.getId()) {
	  case SalesConst.STATUS_CODE_QUOTE:
	  case SalesConst.STATUS_CODE_NEW:
		  break;
	  default:
		  throw new SalesOrderException("Sales ordermust be in quote status in order to be deleted.  Sales Order Id:  " + _salesOrder.getId());
	  }
	  
	  int rc = 0;
	  this.deleteSalesOrderStatuses(_salesOrder.getId());
	  this.deleteSalesOrderItems(_salesOrder.getId());
      DaoApi dao = DataSourceFactory.createDao(this.connector);
	  try {
	      _salesOrder.addCriteria("Id", _salesOrder.getId());
		  rc = dao.deleteRow(_salesOrder);
		  logger.log(Level.DEBUG, "Total number of sales orders deleted: " + rc);
	  }
	  catch (DatabaseException e) {
		  logger.log(Level.DEBUG, e.getMessage());
		  throw new SalesOrderException(e.getMessage());
	  }
  }
  
  /**
   * Deletes one or more items from a sales order.
   * 
   * @param _soId The id of the sales order
   * @return A count of all items deleted.
   * @throws SalesOrderException
   */
  private int deleteSalesOrderItems(int _soId) throws SalesOrderException {
	  SalesOrderItems soi = null;
	  int rc = 0;
      DaoApi dao = DataSourceFactory.createDao(this.connector);

	  try {
          soi = SalesFactory.createSalesOrderItem();
          soi.resetCriteria();
          soi.addCriteria("SalesOrderId", _soId);
          rc = dao.deleteRow(soi);
          logger.log(Level.DEBUG, "Total number of sales order items deleted: " + rc);
          return rc;              
	  }
	  catch (Exception e) {
		  logger.log(Level.DEBUG, e.getMessage());
		  throw new SalesOrderException(e.getMessage());
	  }
  }
  

  /**
   * Deletes all statuses assoicated with a sales order.
   * 
   * @param _soId The id of the sales order.
   * @return The count of all statuses removed from the sales order. 
   * @throws SalesOrderException General database access error.
   */
  private int deleteSalesOrderStatuses(int _soId) throws SalesOrderException {
	  int rc = 0;
      DaoApi dao = DataSourceFactory.createDao(this.connector);
	  try {
		  SalesOrderStatusHist sosh = SalesFactory.createSalesStatusHistory(_soId);
		  sosh.resetCriteria();
		  sosh.addCriteria("SalesOrderId", sosh.getSalesOrderId());
          rc = dao.deleteRow(sosh);
          logger.log(Level.DEBUG, "Total number of sales order statuses deleted: " + rc);
          return rc;              
	  }
	  catch (Exception e) {
		  logger.log(Level.DEBUG, e.getMessage());
		  throw new SalesOrderException(e.getMessage());
	  }
  }
 
  
  
  /**
   * Changes the status of a sales order to _newStatusId
   * 
   * @param _soId  The id of sales order
   * @param _newStatusId The id of the status to apply to the sales order
   * @return {@link SalesOrderStatusHist} object of the status before change
   * @throws SalesOrderException When _newStatusId is considered out of sequence, a database error 
   * occurrence, or a System failure.
   */
  private SalesOrderStatusHist changeSalesOrderStatus(int _soId, int _newStatusId) throws SalesOrderException {
	  SalesOrderStatusHist sosh = null;
	  SalesOrderStatus sos = null;
      DaoApi dao = DataSourceFactory.createDao(this.connector);
	  UserTimestamp ut = null;

	  //Validate new status id value
	  sos = this.findSalesOrderStatus(_newStatusId);
	  if (sos == null) {
		  throw new SalesOrderException("Problem changing sales order status...new status id (" + _newStatusId + ") is invalid");
	  }
      
      this.verifyStatusChange(_soId, _newStatusId);
	  
	  try {
		  ut = RMT2Utility.getUserTimeStamp(this.request);
		  
		  // End current status, if available
		  sosh = this.findCurrentSalesOrderStatusHist(_soId);
		  if (sosh != null) {
			  sosh.setEndDate(ut.getDateCreated());
			  sosh.setUserId(ut.getLoginId());
			  sosh.addCriteria("Id", sosh.getId());
			  dao.updateRow(sosh);
		  }
		  
		  // Begin a new status.
		  sosh = SalesFactory.createSalesStatusHistory(_soId);
		  sosh.setSalesOrderId(_soId);
		  sosh.setSalesOrderStatusId(_newStatusId);
		  sosh.setEffectiveDate(ut.getDateCreated());
		  sosh.setNull("endDate");
		  sosh.setDateCreated(ut.getDateCreated());
		  sosh.setUserId(ut.getLoginId());
		  dao.insertRow(sosh, true);
		  return sosh;
	  }
	  catch (DatabaseException e) {
		  throw new SalesOrderException(e.getMessage());
	  }
	  catch (SystemException e) {
		  throw new SalesOrderException(e.getMessage());
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
  
  public int cancelSalesOrder(int _salesOrderId) throws SalesOrderException {
	  int rc = this.cancelSalesOrder(_salesOrderId, XactConst.XACT_TYPE_CANCEL);
      
	  // Change sales order status to cancelled
      this.changeSalesOrderStatus(_salesOrderId, SalesConst.STATUS_CODE_CANCELLED);
      return rc;
  }
  
  public int refundSalesOrder(int _salesOrderId) throws SalesOrderException {
	  int rc = this.cancelSalesOrder(_salesOrderId, XactConst.XACT_TYPE_SALESRETURNS);
      
	  // Change sales order status to refunded
      this.changeSalesOrderStatus(_salesOrderId, SalesConst.STATUS_CODE_REFUNDED);
      return rc;
  }

  /**
   * Basically resets the invoice flag of a sales order, pushes the sales order items back 
   * to inventory, reverses the associated transaction, and reverses customer activity
   * . 
   * @param _salesOrderId The id of the sales order to reverse.
   * @return The id of the newly reversed transaction. 
   * @throws SalesOrderException
   */
  protected int cancelSalesOrder(int _salesOrderId, int _xactTypeId) throws SalesOrderException {
      DaoApi dao = DataSourceFactory.createDao(this.connector);
	  SalesInvoice si = null;
	  SalesOrder so = null;
	  Xact xact = null;
	  int newXactId = 0;
     
	  // Verify that sales order exist and is invoiced.
	  so = this.findSalesOrderById(_salesOrderId);
	  if (so == null) {
		  throw new SalesOrderException("Problem cancelling sales order.  Sales order is invalid");
	  }
	  if (so.getInvoiced() != SalesConst.INVOICED_FLAG_YES) {
		  throw new SalesOrderException("Problem cancelling sales order.  Sales order must be in Invoiced status");
	  }
	  System.out.println("Sales order was found to be invoiced");
	  
	  // Switch the invoice flag Change Sales Order base.
	  try {
		  so.setInvoiced(SalesConst.INVOICED_FLAG_NO);
		  so.addCriteria("Id", so.getId());
  	      dao.updateRow(so);
	  }
	  catch (DatabaseException e) {
		  throw new SalesOrderException("Problem cancelling sales order.  Sales order base could not be updated");
	  }
	  System.out.println("Sales order invoice flag was turned off");
	  
	  // Put back inventory for each sales order item
	  this.updateInventory(so.getId(), SalesConst.UPDATE_INV_DEPLETE);
	  System.out.println("Sales order items were pushed back to inventory");
	  
      // Get Original Transaction and reverse it.
      si = this.findSalesOrderInvoice(so.getId());
      if (si == null) {
    	  throw new SalesOrderException("Problem cancelling sales order.  Sales order must be in Invoiced status");
      }
      try {
    	 xact = this.findXactById(si.getXactId());
    	  // Cannot modify or adjust a transaction that has been finalized.
         if (!this.isXactModifiable(xact)) {
       	  msg = "Sales Order cannot be cancelled or refunded, because its associated transaction has been finalized";
		      System.out.println("[SalesOrderManagerApiImpl.cancelOrder] " + msg);
		      throw new SalesOrderException(msg);
         }  
    	 
    	 this.finalizeXact(xact);
         xact.setXactAmount(xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER);
         xact.setXactSubtypeId(_xactTypeId);
         switch (_xactTypeId) {
             case XactConst.XACT_TYPE_CANCEL:
                 xact.setReason("Cancelled Sales Order " + so.getId());
                 break;
             case XactConst.XACT_TYPE_SALESRETURNS:
                 xact.setReason("Refunded Sales Order " + so.getId());
                 break;
         }
         
         // Create transaction
    	 newXactId = this.maintainXact(xact, null);
    	 System.out.println("Sales order transaction was cancelled.  New transaction id is: " + newXactId);
    	
    	 //Reverse customer activity
    	 this.createCustomerActivity(so.getCustomerId(), newXactId, xact.getXactAmount());
    	 System.out.println("Sales order customer activity was reversed");
      }
      catch (XactException e) {
    	  throw new SalesOrderException(e);
      }
      return newXactId;
 }

  	 

  	/**
  	 * Generates an invoice number in theformat of <sales order id>-<yyyymmdd> 
  	 * 
  	 * @param _so sales order object
  	 * @return Invoice number
  	 * @throws SalesOrderException Problem creating the date part
  	 */
  	private String createInvoiceNumber(SalesOrder _so) throws SalesOrderException {
  		String datePart = null;
  		String invoiceNumber = null;
  		java.util.Date today = new java.util.Date();
  		datePart = RMT2Utility.combineDateParts(today);
  		if (datePart == null) {
  			throw new SalesOrderException("Problem assigning an invoice number");
  		}
  		invoiceNumber = _so.getId() + "-" + datePart;
  		return invoiceNumber;
  	}
  	
  	
  	
	  public int invoiceSalesOrder(SalesOrder _so, Xact _xact) throws SalesOrderException {
	     int rc = 0;
	     int xactId = 0;
         double revXactAmount = 0;
	     Xact xact = null;
         DaoApi dao = DataSourceFactory.createDao(this.connector);
	     
	     try {
             // Ensure that xact type and amount are set
             _xact.setXactAmount(_so.getOrderTotal());
             _xact.setXactTypeId(XactConst.XACT_TYPE_SALESONACCTOUNT);
             // Create Transaction
	    	 xactId = this.maintainXact(_xact, null);
	    	 xact = this.findXactById(xactId);
	    	 if (xact == null) {
	    		 throw new SalesOrderException("Problem invoicing sales order because transaction could not be verified");
	    	 }
             // Revise transaction amount in the event other charges were included.
             revXactAmount = this.getSalesOrderTotal(_so.getId());
             xact.setXactAmount(revXactAmount);
             xact.setNull("tenderId");
             xact.setNull("xactSubtypeId");
             xact.addCriteria("Id", xact.getId());
             dao.updateRow(xact);
             
             // Create customer activity regarding sale order transaction.
             this.createCustomerActivity(_so.getCustomerId(), xactId, xact.getXactAmount());
	     }
	     catch (XactException e) {
	    	 throw new SalesOrderException(e.getMessage());
	     }
         catch (DatabaseException e) {
             throw new SalesOrderException(e.getMessage());
         }

	     // Setup invoice
	     try {
            
	    	 String invoiceNumber = this.createInvoiceNumber(_so);
		     SalesInvoice si = SalesFactory.createSalesInvoice();
		     si.setInvoiceNo(invoiceNumber);
		     si.setSalesOrderId(_so.getId());
		     si.setXactId(xact.getId());
		     si.setDateCreated(xact.getDateCreated());
		     si.setDateUpdated(xact.getDateCreated());
		     si.setUserId(xact.getUserId());
		     rc = dao.insertRow(si, true);
		     
		     //Flag Sales order base as invoiced and update sales order total with transaction amount
             _so.setOrderTotal(xact.getXactAmount());
		     _so.setInvoiced(SalesConst.INVOICED_FLAG_YES);
		     _so.setDateUpdated(xact.getDateCreated());
		     _so.setUserId(xact.getUserId());
		     dao.updateRow(_so);
		     
		     // Updaet Sales Order status to invoiced
		     this.changeSalesOrderStatus(_so.getId(), SalesConst.STATUS_CODE_INVOICED);
		     
		     // Update Inventory
		     this.updateInventory(_so.getId(), SalesConst.UPDATE_INV_ADD);
             return rc;
	     }
	     catch (DatabaseException e) {
	    	 throw new SalesOrderException(e.getMessage());
	     }
	     catch (SystemException e) {
	    	 throw new SalesOrderException(e.getMessage());
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
			  InventoryApi itemApi = InventoryFactory.createInventoryApi(this.connector, this.request);
			  List items = this.findSalesOrderItems(_soId);
			  for (int ndx = 0; ndx < items.size(); ndx++) {
				  soi = (SalesOrderItems) items.get(ndx);
				  im = itemApi.findItemById(soi.getItemMasterId());
				  if (im.getItemTypeId() == ItemConst.ITEM_TYPE_MERCH) {
					  int ordQty = new Double(soi.getOrderQty()).intValue();
					  switch (_action) {
					  		case SalesConst.UPDATE_INV_ADD:
					  			itemApi.pullInventory(im.getId(), ordQty);
					  			break;
					  		case SalesConst.UPDATE_INV_DEPLETE:
					  			itemApi.pushInventory(im.getId(), ordQty);
					  			break;
					  } // end switch
					  merchCount++;
				  }
			  }
			  return merchCount;
		  }
		  catch (ItemMasterException e) {
			  throw new SalesOrderException(e.getMessage());
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

          items = this.findSalesOrderItems(_orderId);
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
       * @param _soId The id of the sales order
       * @param _newStatusId The id of the new sales order status
       * @return The id of the current sales order status before changing to the new status.
       * @throws SalesOrderException When the the new status is not in sequence to the current status regarding 
       * changing the status of the slaes order.   The exception should give a detail explanation as to the reason why the 
       * status cannot be changed. 
       */
      protected int verifyStatusChange(int _soId, int _newStatusId)  throws SalesOrderException {
        SalesOrderStatusHist sosh = null;
        int currentStatusId = 0;
        
        sosh = this.findCurrentSalesOrderStatusHist(_soId);
        currentStatusId = (sosh == null ? SalesConst.STATUS_CODE_NEW : sosh.getSalesOrderStatusId());
        switch (_newStatusId) {
            case SalesConst.STATUS_CODE_QUOTE:
                if (currentStatusId != SalesConst.STATUS_CODE_NEW) {
                    throw new SalesOrderException("Quote status can only be assigned when the sales order is new");
                    //TODO: obtain message from the database.
                    //throw new PurchaseOrderException(this.dbo, 434, null);
                }
                break;
                
            case SalesConst.STATUS_CODE_INVOICED:
                if (currentStatusId != SalesConst.STATUS_CODE_QUOTE) {
                    throw new SalesOrderException("Sales order must be in Quote status before changing to Invoiced");
                    //TODO: obtain message from the database.
                    //throw new PurchaseOrderException(this.dbo, 434, null);
                }
                break;
                
            case SalesConst.STATUS_CODE_CLOSED:
                if (currentStatusId != SalesConst.STATUS_CODE_INVOICED) {
                    throw new SalesOrderException("Sales order must be in Invoiced status before changing to Closed");
                    //TODO: obtain message from the database.
                    //throw new PurchaseOrderException(this.dbo, 434, null);
                }
                break;
                
            case SalesConst.STATUS_CODE_CANCELLED:
                if (currentStatusId != SalesConst.STATUS_CODE_INVOICED) {
                    throw new SalesOrderException("Sales order must be in Invoiced status before changing to Cancelled");
                    //TODO: obtain message from the database.
                    //throw new PurchaseOrderException(this.dbo, 434, null);
                }
                break;
                
            case SalesConst.STATUS_CODE_REFUNDED:
                switch (currentStatusId) {
                    case SalesConst.STATUS_CODE_INVOICED:
                    case SalesConst.STATUS_CODE_CLOSED:
                        break;
                    
                    default:
                        throw new SalesOrderException("Sales order must be in Invoiced or Closed statuses before changing to Refunded");
                        //TODO: obtain message from the database.
                        //throw new PurchaseOrderException(this.dbo, 434, null);
                } // end inner switch
                break;
                
        } // end outer switch
        
        return currentStatusId;
      }

		
}
