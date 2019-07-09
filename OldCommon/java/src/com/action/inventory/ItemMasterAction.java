package com.action.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.InventoryApi;
import com.api.db.DatabaseException;

import com.constants.ItemConst;
import com.constants.RMT2ServletConst;

import com.factory.InventoryFactory;

import com.util.SystemException;



/**
 * This class provides action handlers to serve common requests pertaining to 
 * the item master module. 
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterAction extends AbstractActionHandler implements ICommand {
    protected InventoryApi api;
    protected HttpItemMasterHelper itemHelper;
    private Logger logger;
    

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterAction() throws SystemException {
    	this.logger = Logger.getLogger("ItemMasterAction");
    }
    
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with 
	*   this action handler
	* @throws SystemException
	*/
  public ItemMasterAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
      try {
    	  this.api = InventoryFactory.createInventoryApi(this.dbConn, _request);
    	  this.itemHelper = new HttpItemMasterHelper(_context, _request);
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
   * @throws SystemException when an error needs to be reported.
   */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
      try {
          this.init(null, request);
          this.init();
      }
      catch (SystemException e) {
          throw new ActionHandlerException(e);
      }
  }

  
  /**
   * Preapres the client for adding an item to inventory by retrieving data 
   * from the client's request. 
   * 
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
      this.receiveClientData();
      return;
  }
  
  
  /**
   * Retrieves item master data from the client's request.  New Item Master View, and 
   * Item Master Status History objects are created and sent to the client.  The above 
   * objects are considered to be new when the property which represents the primary 
   * key is equal to zero.
   * <p>
   * The following objects are set on the request object identified as "item" and 
   * "itemhistory", respectively: {@link VwItemMaster} and {@link ItemMasterStatusHist}.  
   *
   * @throws ActionHandlerException
   */
  protected void receiveClientData() throws ActionHandlerException {
      try {
    	  this.itemHelper.getHttpCombined();    
      }
      catch (SystemException e) {
          this.msg = e.getMessage();
          logger.log(Level.ERROR, this.msg);
          throw new ActionHandlerException(this.msg);
      }
  }
  
  /**
   * Sends data to the client.
   * 
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
      // Set data on the request object to be used by client JSP.s
      this.request.setAttribute(ItemConst.CLIENT_DATA_ITEM, this.itemHelper.getItemExt());
      this.request.setAttribute(ItemConst.CLIENT_DATA_ITEMHIST, this.itemHelper.getImsh());
      this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
  }
  
  
  /**
   * No Action
   */
  public void edit() throws ActionHandlerException {
	  return;  
  }
  
  /**
   * No Action
   */
  public void save() throws ActionHandlerException {
	  return;  
  }
  /**
   * No Action
   */
  public void delete() throws ActionHandlerException {
	  return;  
  }
}