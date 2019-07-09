package com.action.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.InventoryApi;
import com.api.db.DatabaseException;

import com.bean.VwItemMaster;
import com.bean.ItemMaster;
import com.bean.ItemMasterStatusHist;

import com.factory.InventoryFactory;

import com.util.SystemException;
import com.util.ItemMasterException;


/**
 * This class provides action handlers to serve Item Master Search page requests. 
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterEditAction extends ItemMasterAction {
    private static final String COMMAND_SAVE = "ItemMaster.Edit.save";
    private static final String COMMAND_ADD = "ItemMaster.Edit.add";
    private static final String COMMAND_DELETE = "ItemMaster.Edit.delete";
    private static final String COMMAND_BACK = "ItemMaster.Edit.back";
    private Logger logger;
    

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterEditAction() throws SystemException {
    	this.logger = Logger.getLogger("ItemMasterEditAction");
    }
    
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with 
	*   this action handler
	* @throws SystemException
	*/
  public ItemMasterEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
	  if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_SAVE)) {
          this.saveData();
      }
      if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_ADD)) {
          this.addData();
      }
      if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_DELETE)) {
          this.deleteData();
      }
      if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_BACK)) {
          this.doBack();
      }
  }

  /**
   * Prepares the user to navigate back to the search page.
   * 
   * @throws ActionHandlerException
   */
  protected void doBack() throws ActionHandlerException {
	  return;
  }
  
  
  /**
   * Persist the changes made to an item master to the database.
   * 
   * @throws ActionHandlerException
   */
  public void save() throws ActionHandlerException {
      try {
          this.api.maintainItemMaster(this.itemHelper.getItem(), null);
          this.transObj.commitUOW();
          this.itemHelper.refreshData();
          this.msg = "Item was saved successfully";
          return;
      }
      catch (ItemMasterException e) {
          this.msg = e.getMessage();
          logger.log(Level.ERROR,this.msg);
          this.transObj.rollbackUOW();   
          throw new ActionHandlerException(this.msg);
      }
  }
  
  /**
   * Deletes an item master from the system.   If the item targeted for deletion is 
   * linked to one or more dependent entities, then the item is marked inactive.
   * 
   * @throws ActionHandlerException general database errors
   */
  public void delete() throws ActionHandlerException {
      int itemId = 0;
      ItemMaster im = null;
      boolean deleteFailed = false;
      
      this.itemHelper.refreshData();
      itemId = this.itemHelper.getItem().getId();
      im = this.itemHelper.getItem();
      
      try {
          this.api.deleteItemMaster(itemId);
          this.transObj.commitUOW();
          this.msg = "Item was deleted from the database successfully";
      }
      catch (ItemMasterException e) {
          this.transObj.rollbackUOW();
          deleteFailed = true;
      }
      
      if (deleteFailed) {
          try {
              im.setActive(0);
              this.api.maintainItemMaster(im, null);
              this.transObj.commitUOW();
              this.msg = "Item was not deleted from the database.   Instead marked inactive since item is linked to one or more orders.";
          }
          catch (ItemMasterException e) {
              this.msg = e.getMessage();
              logger.log(Level.ERROR, this.msg);
              this.transObj.rollbackUOW();
          }
      }
      return;
  }
}