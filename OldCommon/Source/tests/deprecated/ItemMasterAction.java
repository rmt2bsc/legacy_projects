package com.action;

import javax.servlet.ServletContext;


import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import com.api.InventoryApi;

import com.bean.RMT2TagQueryBean;
import com.bean.VwItemMaster;
import com.bean.ItemMasterQuery;
import com.bean.ItemMaster;
import com.bean.ItemMasterStatusHist;

import com.constants.ItemConst;
import com.constants.RMT2ServletConst;

import com.factory.InventoryFactory;
import com.factory.DataSourceAdapter;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.ItemMasterException;
import com.util.ActionHandlerException;
import com.util.RMT2Utility;



/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 * Customer information.
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterAction extends AbstractActionHandler implements IRMT2ServletActionHandler {
    private InventoryApi api;
    

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public ItemMasterAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
    this.className = "ItemMasterAction";
    this.api = InventoryFactory.createInventoryApi(this.dbConn, _request);
  }
  
  /**
   * Displays the Item Master Search Console for the first time rendering the search criteria section with blank values and the 
   * Search Result Set section with an empty result set.
   *
   */
  /*
  public void startSearchConsole() {
      
      ItemMasterQuery imq = ItemMasterQuery.getInstance();
      RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
      query.setWhereClause(null);
       
      // Force an empty result set by purposely constructing erroneous  selection criteria.
      query.setWhereClause("id = -1");
      query.setCustomObj(imq);
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
      return;
  }
*/

  /**
   * This method is responsible for gathering the user's input of selection criteria data from the request object.
   * 
   * @return Object which represents the custom object that is a member of {@link RMT2TagQueryBean}. 
   * @throws ActionHandlerException
   */
  protected Object doCustomInitialization() throws ActionHandlerException {
      String method = "[" + this.className + ".getCriteriaObject] ";
      this.setBaseView("VwItemMasterView");
      ItemMasterQuery imq = ItemMasterQuery.getInstance();
      if (!this.isFirstTime()) {
          try {
              DataSourceAdapter.packageBean(this.request, imq);    
          }
          catch (SystemException e) {
              System.out.println(method + "Problem gathering Item Master request parameters.");
              System.out.println(method + e.getMessage());
              throw new ActionHandlerException(e);
          }    
      }
      return imq;
  }

  
  /**
   *  This method signals to the caller to ignore the selection criteria that was setup for _filedName in the ancestor script.   Typically, this istrue when _fieldName is pointing to the Active or Inactive fields,
   *  
   *  @return String - Return "" which means to ignore the criteria of _fieldName which was setup in the ancestor script.   Returns null to indicate that there is no custom criteria to be applied and to apply that which was built in the ancestor. 
   */
  protected String buildCustomClientCriteria(String _fieldName) {
      if (_fieldName.equalsIgnoreCase("qry_Active") || _fieldName.equalsIgnoreCase("qry_Inactive")) {
          return "";
      }
      return null;
  }

  
  protected String postBuildCustomClientCriteria() {
      StringBuffer criteria = new StringBuffer(100);
      String temp = null;
      String temp2 = null;
      ArrayList activeInactive = new ArrayList(); 
      
      temp = this.request.getParameter("qry_Active");
      temp2 = this.request.getParameter("qry_Inactive");
      if (temp != null && !temp.equals("")) {
          activeInactive.add(temp);
      }
      if (temp2 != null && !temp2.equals("")) {
          activeInactive.add(temp2);
      }
      if (activeInactive.size() > 0) {
          if (criteria.length() > 0) {
              criteria.append(" and ");
          }
          criteria.append(" active in(");
          for (int ndx = 0; ndx < activeInactive.size(); ndx++) {
              if (ndx > 0) {
                  criteria.append(", ");
              }
              temp = (String) activeInactive.get(ndx);
              criteria.append(temp);
          }
          criteria.append(") ");    
      }
      if (criteria.length() > 0) {
          return criteria.toString();
      }
      return null;
  }


  /**
   * Uses data from the client's request object to retrieve target VwItemMaster and 
   * ItemMasterStatusHist objects from the databse for a single Item Master record edit session.
   * <p>
   * The following objects are set on the request object identified as "item" and "itemhistory", respectively: 
   * {@link VwItemMaster} and {@link ItemMasterStatusHist}. 
   * 
   * @throws ItemMasterException
   */
  public void edit() throws ActionHandlerException {
      String method = "[" + this.className + ".edit] ";
      int row = 0;
      int itemId = 0;
      String property = "Id";
      String temp = null;
      VwItemMaster item = null;
      ItemMasterStatusHist imsh = null;
      
      // Determine the row number selected.
      try {
          row = this.getSelectedRow("selCbx");    
      }
      catch (SystemException e) {
          this.msg = method + "A row must be selected in order to perform Edit operation";
          System.out.println(msg);
          throw new ActionHandlerException(this.msg, -1);
      }
      
      // Get item master id.
      temp = this.request.getParameter(property + row);
      if (temp == null) {
          this.msg = method + "Problem locating item Master selection for row number " + row;
          System.out.println(msg);
          throw new ActionHandlerException(this.msg, -1);
      }
      itemId = Integer.parseInt(temp);
      
      try {
           // Get item master record.
          item = this.api.findItemViewById(itemId);
          
          // Get item master most recent status history.
          imsh = this.api.findCurrentItemStatusHist(itemId);    
      }
      catch (ItemMasterException e) {
          throw new ActionHandlerException(e);
      }
      

      // Set data on the request object to be used by client JSP.s
      this.request.setAttribute("item", item);
      this.request.setAttribute("itemhistory", imsh);
      return;
  }
  
  /**
   * Preapres the client for adding an item to inventory.   A new Item Master View object and a new Item Master Status History object
   * is created and sent to the client for data entry.   The above objects are considered to be new when the property which represents 
   * the primary key is equal to zero.
   * <p>
   * The following objects are set on the request object identified as "item" and "itemhistory", respectively: 
   * {@link VwItemMaster} and {@link ItemMasterStatusHist}.  
   *
   */
  public void add() {
      VwItemMaster item = null;
      ItemMasterStatusHist imsh = null;
      
      // Get item master View record.
      item = InventoryFactory.createItemMasterView();
      
      // Get item master most recent status history.
      imsh = InventoryFactory.createItemMasterStatusHist();

      // Set data on the request object to be used by client JSP.s
      this.request.setAttribute("item", item);
      this.request.setAttribute("itemhistory", imsh);
      return;
  }
  
  /**
   * 
   * @throws ItemMasterException
   */
  public void save() throws ActionHandlerException, DatabaseException {
      String method = "[" + this.className + ".save] ";
      ItemMaster item = null;
      VwItemMaster itemView = null;
      ItemMasterStatusHist imsh = null;
      boolean error = false;
      
      
      try {
          item = InventoryFactory.createItemMaster();
          DataSourceAdapter.packageBean(this.request, item);    
      }
      catch (SystemException e) {
          System.out.println(method + "Problem gathering Item Master request parameters.");
          System.out.println(method + e.getMessage());
          throw new ActionHandlerException(e);
      }
      
      try {
          this.api.maintainItemMaster(item, null);
          this.transObj.commitTrans();
          this.msg = "Item was saved successfully";
          return;
      }
      catch (ItemMasterException e) {
          this.msg = e.getMessage();
          itemView = this.copyToItemViewObject(item);
          error = true;
          this.transObj.rollbackTrans();    
      }
      finally {
          // confirm change
          if (!error && item.getId() > 0) {
              try {
                  // Get item master View record.
                  itemView = this.api.findItemViewById(item.getId());
                  // Get current item status history
                  imsh = this.api.findCurrentItemStatusHist(item.getId());                  
              }
              catch (ItemMasterException e) {
                  System.out.println(e.getMessage());
                  throw new ActionHandlerException(e);
              }
          }
          
          // Set data on the request object to be used by client JSP.s
          this.request.setAttribute("item", itemView);
          this.request.setAttribute("itemhistory", imsh);              
          this.request.setAttribute("msg", this.msg);
      }
  }
  
  public void delete() throws ActionHandlerException, DatabaseException {
      String method = "[" + this.className + ".delete] ";
      int row = 0;
      int itemId = 0;
      String property = "Id";
      String temp = null;
      VwItemMaster item = null;
      ItemMaster im = null;
      ItemMasterStatusHist imsh = null;
      boolean deleteFailed = false;
      
      // Determine the row number selected.
      try {
          row = this.getSelectedRow("selCbx");
          // Get item master id.
          temp = this.request.getParameter(property + row);
      }
      catch (SystemException e) {
          temp = this.request.getParameter("Id");
          if (temp == null) {
              this.msg = method + "An item must be selected in order to perform Delete operation";
              System.out.println(msg);
              throw new ActionHandlerException(this.msg, -1);              
          }
      }
      
      itemId = Integer.parseInt(temp);
      
      try {
          // Get item master record.
          im = this.api.findItemById(itemId);
          item = this.copyToItemViewObject(im);
          
          // Get item master most recent status history.
          imsh = this.api.findCurrentItemStatusHist(itemId);
      }
      catch (ItemMasterException e) {
          System.out.println(e.getMessage());
          throw new ActionHandlerException(e);              
      }
      
      try {
          this.api.deleteItemMaster(itemId);
          this.transObj.commitTrans();
          this.msg = "Item was deleted from the database successfully";
      }
      catch (ItemMasterException e) {
          this.transObj.rollbackTrans();
          deleteFailed = true;
      }
      
      if (deleteFailed) {
          try {
              im.setActive(0);
              this.api.maintainItemMaster(im, null);
              this.transObj.commitTrans();
              this.msg = "Item was not deleted from the database.   Instead marked inactive since item is linked to one or more orders.";
          }
          catch (ItemMasterException e) {
              this.msg = e.getMessage();
              this.transObj.rollbackTrans();
          }
      }

      // Set data on the request object to be used by client JSP.s
      this.request.setAttribute("item", item);
      this.request.setAttribute("itemhistory", imsh);
      this.request.setAttribute("msg", this.msg);
      return;
  }
  
  
  
  /**
   * Creates an object of type, VwItemMaster, and copies the contents of _item.
   * 
   * @param _item
   * @return VwItemMaster
   */
  private VwItemMaster copyToItemViewObject(ItemMaster _item) {
      VwItemMaster itemView = InventoryFactory.createItemMasterView();
      itemView.setId(_item.getId());
      itemView.setVendorId(_item.getVendorId());
      itemView.setVendorItemNo(_item.getVendorItemNo());
      itemView.setDescription(_item.getDescription());
      itemView.setItemTypeId(_item.getItemTypeId());
      itemView.setItemSerialNo(_item.getItemSerialNo());
      itemView.setQtyOnHand(_item.getQtyOnHand());
      itemView.setUnitCost(_item.getUnitCost());
      itemView.setRetailPrice(_item.getRetailPrice());
      itemView.setOverrideRetail(_item.getOverrideRetail());
      itemView.setMarkup(_item.getMarkup());
      itemView.setActive(_item.getActive());
      return itemView;
  }
  
  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}
  

}