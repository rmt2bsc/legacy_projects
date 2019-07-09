package com.action.xact.purchases.vendor.inventory;

import javax.servlet.ServletContext;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.VendorItems;

import com.factory.InventoryFactory;

import com.util.SystemException;
import com.util.ItemMasterException;


/**
 * This class provides action handlers to manage vendor item edit requests.
 * 
 * @author Roy Terrell
 *
 */
public class VendorItemEditAction extends VendorItemAssocAction implements ICommand {
	private static final String COMMAND_BACK = "XactPurchase.VendorItemEdit.back";
	private static final String COMMAND_SAVE = "XactPurchase.VendorItemEdit.venditemsave";
	
	private VendorItems vi;
	
    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public VendorItemEditAction() throws SystemException {
        super();
        logger = Logger.getLogger("VendorItemEditAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public VendorItemEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
  }
  

  /**
   * Initializes this object using _conext and _request.  This is needed in the 
   * event this object is inistantiated using the default constructor.
   * 
   * @throws SystemException
   */
  protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
      super.init(_context, _request);
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
      super.processRequest(request, response, command);
      if (command.equalsIgnoreCase(VendorItemEditAction.COMMAND_SAVE)) {
          this.saveData();
      }
      if (command.equalsIgnoreCase(VendorItemEditAction.COMMAND_BACK)) {
          this.doBack();
      }
  }

  
  /**
   * Applies {@link VendorItems} changes to the database.   The changes are gathered 
   * from the client's request object and packaged into a VendorItems object 
   * which is passed to the InventoryApi to update the database.   
   *  
   * @throws ItemMasterException
   */
  public void save() throws ActionHandlerException {
	  super.save();
      try {
          this.api.maintainVendorItem(this.vi);
          this.transObj.commitUOW();
          this.setUpVendorItemEdit(vi.getVendorId(), vi.getItemMasterId());
          this.msg = "Vendor Item changes were saved successfully";
          return;
      }
      catch (ItemMasterException e) {
          this.msg = e.getMessage();
          logger.log(Level.ERROR, this.msg);
          this.transObj.rollbackUOW();    
          throw new ActionHandlerException(this.msg);
      }
  }
  

  protected void doBack() throws ActionHandlerException {
	  super.receiveClientData();
      this.sendClientData();
	  return;
  }
  
  
  protected void receiveClientData() throws ActionHandlerException {
	  super.receiveClientData();
	  
      try {
          this.vi = InventoryFactory.createVendorItem();
          DataSourceAdapter.packageBean(this.request, vi);    
      }
      catch (SystemException e) {
    	  this.msg = "Problem gathering Vendor Item request parameters.";
    	  logger.log(Level.ERROR, this.msg);
          throw new ActionHandlerException(this.msg);
      }
  }
  
  
}