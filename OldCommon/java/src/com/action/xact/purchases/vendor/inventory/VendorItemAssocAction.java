package com.action.xact.purchases.vendor.inventory;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.InventoryApi;
import com.api.GLCreditorApi;
import com.api.db.DatabaseException;

import com.bean.Creditor;
import com.bean.VendorItems;
import com.bean.VwCreditorBusiness;
import com.bean.ItemMaster;
import com.constants.RMT2ServletConst;

import com.factory.InventoryFactory;
import com.factory.AcctManagerFactory;

import com.util.GLAcctException;
import com.util.SystemException;
import com.util.ItemMasterException;


/**
 * This class provides action handlers to respond to an associated 
 * controller for searching, adding, deleting, and eidting Vendor/Item 
 * associations.
 * 
 * @author Roy Terrell
 *
 */
public class VendorItemAssocAction extends AbstractActionHandler implements ICommand {
	private static final String COMMAND_START = "XactPurchase.VendorSearch.vendoritemview";
	private static final String COMMAND_ASSIGN = "XactPurchase.VendorItemAssoc.vendoritemassign";
	private static final String COMMAND_REMOVE = "XactPurchase.VendorItemAssoc.vendoritemunassign";
	private static final String COMMAND_EDIT = "XactPurchase.VendorItemAssoc.venditemedit";
	private static final String COMMAND_OVERIDE_ADD = "XactPurchase.VendorItemAssoc.venditemoverride";
	private static final String COMMAND_OVERIDE_REMOVE = "XactPurchase.VendorItemAssoc.removevenditemoverride";
	private static final String COMMAND_SEARCH = "XactPurchase.VendorItemAssoc.search";
	
	protected InventoryApi api;
	protected Logger logger;
	protected Creditor creditor; 
	protected List availList;
	protected List asgnList;
    protected VwCreditorBusiness vendor;
    protected ItemMaster im;
    protected VendorItems vi;
	protected HttpVendorItemHelper httpHelper;
    protected String command;
    

    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public VendorItemAssocAction() throws SystemException {
        super();
        logger = Logger.getLogger("VendorItemAssocAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public VendorItemAssocAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
    this.init(_context, _request);
  }
  

  /**
   * Initializes this object using _conext and _request.  This is needed in the 
   * event this object is inistantiated using the default constructor.
   * 
   * @throws SystemException
   */
  protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
      super.init(_context, _request);
      try {
    	  this.api = InventoryFactory.createInventoryApi(this.dbConn, _request);
          httpHelper = new HttpVendorItemHelper(_context, _request);
      }
      catch (Exception e) {
    	  logger.log(Level.ERROR, e.getMessage());
    	  throw new SystemException(e.getMessage());
      }
  }
  
  /**
   * Processes the client's request using request, response, and command.
   *
   * @param request   The HttpRequest object
   * @param response  The HttpResponse object
   * @param command  Comand issued by the client.
   * @Throws SystemException when an error needs to be reported.
   */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
      try {
          this.init(null, request);
          this.init();
          this.command = command;
      }
      catch (SystemException e) {
          throw new ActionHandlerException(e);
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_START)) {
          this.startConsole();
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_ASSIGN)) {
          this.addData();
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_REMOVE)) {
          this.unassignItems();
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_EDIT)) {
          this.editData();
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_OVERIDE_ADD)) {
          this.activateItemOverride();
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_OVERIDE_REMOVE)) {
          this.deActivateItemOverride();
      }
      if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
  }

  
  /**
   * Displays the Vendor-Item association maintenance page which retreives all items 
   * currently associated with the vendor and all items from inventory that are not 
   * assigned to the vendor.
   *
   *@throws ActionHandlerException  When a general database error occurs.
   */
  protected void startConsole() throws ActionHandlerException {
	  this.receiveClientData();
	  this.sendClientData();
  }
  
  

  /**
   * Retreives the items that are available to the vendor and those items that assigned 
   * to the vendor which the data is passed to the clients HttpServletRequest object for 
   * presentation.
   * 
   * @throws ActionHandlerException
   */
  private void refreshData() throws ActionHandlerException {
	  try {
		  this.creditor = this.httpHelper.getHttpVendor(); 
		  // Get items assigned to Vendor
		  this.asgnList = this.api.findVendorAssignItems(this.creditor.getId());
          // Get items item not assigned to Vendor
		  this.availList = this.api.findVendorUnassignItems(this.creditor.getId());
		  // Getvendor details
		  this.vendor = this.getVendorDetails(this.creditor.getId());
	  }
	  catch (Exception e) {
		  this.msg = e.getMessage();
		  logger.log(Level.ERROR, this.msg);
		  throw new ActionHandlerException(this.msg);
	  }
  }

  /**
   * Begins the process of assigning one or more inventory items to a vendor.   This 
   * method is expecting the following client variables to be accessible from the 
   * HttpServletRequest object for processing:  "VendorId" and "AvailItems".
   * 
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
	  this.receiveClientData();
	  this.creditor = this.httpHelper.getCreditor();
	  int items[] = this.httpHelper.getUnassignItems();

      try {
          this.api.assignVendorItems(this.creditor.getId(), items);
          this.transObj.commitUOW();
          return;
      }
      catch (ItemMasterException e) {
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e);
      }
  }
  
  /**
   * Begins the process of unassigning one or more inventory items from a vendor.   
   * This method is expecting the following client variables to be accessible from 
   * the HttpServletRequest object for processing:  "VendorId" and "AssignItems".
   * 
   * @return
   * @throws ActionHandlerException
   */
  protected void unassignItems() throws ActionHandlerException {
	  this.receiveClientData();
	  this.creditor = this.httpHelper.getCreditor();
	  int items[] = this.httpHelper.getAssignItems();

      try {
          this.api.removeVendorItems(this.creditor.getId(), items);
          this.transObj.commitUOW();
          return;
      }
      catch (ItemMasterException e) {
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e);
      }
      finally {
    	  this.sendClientData();
      }
  }
  
  
  /**
   * Drives the process of editing an assigned vendor inventory item.      Only one item can be edited at a time. 
   * 
   * @throws ItemMasterException If more than one item is found for processing.
   */
  public void edit() throws ActionHandlerException {
      int items[];
      int vendorId = this.httpHelper.getCreditor().getId();
      items = this.httpHelper.getAssignItems();
      if (items.length > 1) {
    	  throw new ActionHandlerException("Only one assigned vendor item can be edited at a time");
      }
      this.setUpVendorItemEdit(vendorId, items[0]);
      return;
  }
  
  
  
  /**
   * Uses _vendorId and _itemId to retrieve the vendor, item master, and vendor item 
   * data objects to be used by the client. 
   * <p>
   * The following objects are set on the request object identified as "vendor", 
   * "itemmaster", and "vendoritem", respectively: 
   * {@link VwCreditorBusiness}, {@link ItemMaster} and {@link VendorItems}. 
   * 
   * @param _vendorId The vendor id
   * @param _itemId The item master id
   * @throws ActionHandlerException
   */
  protected void setUpVendorItemEdit(int _vendorId, int _itemId) throws ActionHandlerException {
      try {
    	  this.vendor = this.getVendorDetails(_vendorId);
    	  this.vi = this.api.findVendorItem(_vendorId, _itemId);
          this.im = this.api.findItemById(_itemId);  
      }
      catch(ItemMasterException e) {
    	  throw new ActionHandlerException(e);
      }
  }
  
  
  /**
   * Obtains the vendor detail data derived from the creditor and business tables.
   * 
   * @param _vendorId The id of the vendor
   * @return {@link VwCreditorBusiness} or null if an error occurs.
   */
  private VwCreditorBusiness getVendorDetails(int _vendorId) {
	  GLCreditorApi credApi = null;
      VwCreditorBusiness cred = null;
      List credList = null;
      
      try {
    	  credApi = AcctManagerFactory.createCreditor(this.dbConn);
    	  credList = credApi.findCreditorBusiness("creditor_id = " + _vendorId);
    	  cred = (VwCreditorBusiness) credList.get(0);
    	  return cred;
      }
      catch (GLAcctException e) {
    	  	return null;
      }
      catch (DatabaseException e) {
   			return null;
      }
      catch (SystemException e) {
   	   		return null;
      }
  }
  
  protected void activateItemOverride() throws ActionHandlerException {
	  this.receiveClientData(); 
	  int items[] = this.httpHelper.getAssignItems();

      try {
          this.api.addInventoryOverride(this.httpHelper.getCreditor().getId(), items);  
          this.transObj.commitUOW();
          return;
      }
      catch (ItemMasterException e) {
          System.out.println(e.getMessage());
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e);
      }          
      finally {
    	  this.sendClientData();
      }
  }
  
  
  protected void deActivateItemOverride() throws ActionHandlerException {
	  this.receiveClientData(); 
	  int items[] = this.httpHelper.getAssignItems();

      try {
          this.api.removeInventoryOverride(this.httpHelper.getCreditor().getId(), items);  
          this.transObj.commitUOW();
          return;
      }
      catch (ItemMasterException e) {
          System.out.println(e.getMessage());
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e);
      }          
      finally {
    	  this.sendClientData();
      }
  }
  
  /**
   * Stub method
   * 
   * @throws ActionHandlerException
   */
  protected void doSearch() throws ActionHandlerException {
	  return;
  }
  
  /**
   * Stub method.   
   *  
   * @throws ItemMasterException
   */
  public void delete() throws ActionHandlerException {
	  return;
  }

  /**
   * Stub method.   
   *  
   * @throws ItemMasterException
   */
  public void save() throws ActionHandlerException {
	  return;
  }

  protected void receiveClientData() throws ActionHandlerException {
	  try {
		  this.httpHelper.getHttpCombined();  
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e);
	  }
  }
  
  
  protected void sendClientData() throws ActionHandlerException {
	  this.refreshData();
	  this.request.setAttribute("vendor", this.vendor);
	  this.request.setAttribute("availList", this.availList);
      this.request.setAttribute("assignList", this.asgnList);
      this.request.setAttribute("itemmaster", this.im);
      this.request.setAttribute("vendoritem", this.vi);
      this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
  }  
  
}