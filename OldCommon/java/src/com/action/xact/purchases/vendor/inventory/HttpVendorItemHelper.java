package com.action.xact.purchases.vendor.inventory;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.List;

import com.action.ActionHandlerException;
import com.action.HttpBasicHelper;

import com.api.InventoryApi;
import com.api.GLCreditorApi;
import com.api.db.DatabaseException;

import com.bean.Creditor;
import com.bean.VwCreditorBusiness;

import com.factory.InventoryFactory;
import com.factory.AcctManagerFactory;

import com.util.GLAcctException;
import com.util.SystemException;


/**
 * This class provides action handlers to respond to an associated 
 * controller for searching, adding, deleting, and eidting 
 * Vendor/Item associations.
 * 
 * @author Roy Terrell
 *
 */
public class HttpVendorItemHelper extends HttpBasicHelper {
	private Logger logger;
    private InventoryApi api;
    private Creditor creditor;
    private int assignItems[];
    private int unassignItems[];
    private VwCreditorBusiness creditorExt;
    

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    public HttpVendorItemHelper() throws SystemException {
    	super();
    	this.logger = Logger.getLogger("HttpVendorItemHelper");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
    public HttpVendorItemHelper(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    	super(_context, _request);
    	this.className = "VendorItemAction";
    	this.api = InventoryFactory.createInventoryApi(this.dbConn, _request);
    }
  
	/**
	 * Initializes this object using _conext and _request. This is needed in the
	 * event this object is inistantiated using the default constructor.
	 * 
	 * @throws SystemException
	 */
	protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
		super.init(_context, _request);
		this.api = InventoryFactory.createInventoryApi(this.dbConn, _request);
	}
  
  
  
	public void getHttpCombined() throws SystemException {
		super.getHttpCombined();
		this.creditor = this.getHttpVendor();
		this.unassignItems = this.getHttpSelectedItems("UnAssignedtems");
		this.assignItems = this.getHttpSelectedItems("AssignedItems");
		
	}
	
	
	/**
	 * Attempts to obtain basic data pertaining to a vendor from the
	 * client JSP. This method requires that the client identifies the creditor
	 * as either qry_VendorId, qry_VendorIdXXX, VendorId, or VendorIdXXX.
	 * 
	 * @return {@link Creditor} containing the data retrieved from the client or
	 *         a newly initialized Creditor object when the creditor id is not
	 *         found.
	 * @throws SalesOrderException
	 */
	public Creditor getHttpVendor() throws SystemException {
		Creditor obj = AcctManagerFactory.createCreditor();
		String temp = null;
		String subMsg = null;
		boolean listPage = true;
		int creditorId = 0;

		// Determine if we are coming from a page that presents data as a list
		// or as a single record.
		// Get selected row number from client page. If this row number exist,
		// then we are coming from page that contains lists of records.
		subMsg = "Vendor id could not be identified from client";
		temp = this.request.getParameter("qry_VendorId" + this.selectedRow);
		if (temp == null) {
			temp = this.request.getParameter("VendorId" + this.selectedRow);
		}
		if (temp == null) {
			listPage = false;
		}
		if (!listPage) {
			// Get order id for single row
			temp = this.request.getParameter("qry_VendorId");
			if (temp == null) {
				temp = this.request.getParameter("VendorId");
			}
		}

		// Validate value
		try {
			creditorId = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			logger.log(Level.INFO, subMsg);
			this.creditor = obj;
			return obj;
		}

		// Get Creditor/Vendor object
		try {
			AcctManagerFactory.packageBean(this.request, obj);
			this.creditor = obj;
			this.creditor.setId(creditorId);
			return obj;
		} 
		catch (SystemException e) {
			logger.log(Level.ERROR, e.getMessage());
			throw e;
		}
	}
  
  /**
   * Obtains the item master id from the http client.
   * 
   * @return The id of the inventory item
   * @throws ActionHandlerException
   */
  protected int getHttpItemMasterId() throws ActionHandlerException {
	  String strItemId = this.request.getParameter("ItemMasterId");
	  int itemId = 0;
	  if (strItemId == null) {
		  throw new ActionHandlerException("Vendor Item Id is required");
	  }
	  
	  try {
		  itemId = Integer.parseInt(strItemId);
		  return itemId;
	  }
	  catch (NumberFormatException e) {
		  throw new ActionHandlerException("An invalid Item Master number has been supplied");
	  }
  }
  
  /**
   * Obtains one or more Inventory item id's from the client's HttpServletRequest object.
   * 
   * @param _property The name of the property containing the inventory item id's
   * @return An integer array of selected item id's
   * @throws SystemException If the client did not select one or more items, or one or more item id's are invalid.
   */
  public int [] getHttpSelectedItems(String _property) throws SystemException {
	  String values[] = this.request.getParameterValues(_property);
	  
	  if (values == null) {
          return null;
		  //throw new SystemException("One or more items must be selected");
	  }
	  
	  int items[] = new int[values.length];
	  for (int ndx = 0; ndx < values.length; ndx++) {
		  try {
			  items[ndx] = Integer.parseInt(values[ndx]);
		  }
		  catch (NumberFormatException e) {
			  throw new SystemException("One or more of the selected items are invalid");
		  }
	  }
	  return items;
  }
  
  
  /**
   * @return the creditor
   */
  public Creditor getCreditor() {
  	return creditor;
  }

  /**
   * @return the creditorExt
   */
  public VwCreditorBusiness getCreditorExt() {
  	return creditorExt;
  }

  /**
   * @return the selectedItems
   */
  public int[] getAssignItems() {
  	return assignItems;
  }  
  
  /**
   * @return the removeItems
   */
  public int[] getUnassignItems() {
  	return unassignItems;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  


/**
   * Obtains the vendor detail data derived from the creditor and business tables.
   * 
   * @param _vendorId The id of the vendor
   * @return {@link VwCreditorBusiness} or null if an error occurs.
   */
  protected VwCreditorBusiness getVendorDetails(int _vendorId) {
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


  

}