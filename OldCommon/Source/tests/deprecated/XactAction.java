package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import java.sql.ResultSet;

import java.util.ArrayList;

import com.api.XactManagerApi;
import com.api.GLCustomerApi;

import com.bean.Customer;
import com.bean.VwXactList;
import com.bean.Xact;
import com.bean.XactQuery;
import com.bean.XactType;
import com.bean.XactCategory;
import com.bean.XactTypeItemActivity;
import com.bean.RMT2TagQueryBean;

import com.constants.XactConst;

import com.factory.XactFactory;
import com.factory.AcctManagerFactory;

import com.action.AbstractActionHandler;

import com.util.ActionHandlerException;
import com.util.GLAcctException;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.NotFoundException;
import com.util.XactException;

import com.constants.RMT2ServletConst;



/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 * transaction data.
 * 
 * @author Roy Terrell
 *
 */ 
public class XactAction extends AbstractActionHandler {
	  protected XactManagerApi api;
	  protected Xact xactBean;
	  protected XactType xactTypeBean;
      protected XactCategory xactCategory;
      protected ArrayList xactItems;
	  protected boolean isOkToCommit = false; // Used to control the commiting of transaction at the ancestor level
      protected int selecteRow;
      private boolean isNewXact = true;
	
	  /**
	   * Default constructor
	   *
	   */
	  public XactAction()  {
		  super(); 
	  }
	  
	  /**
		* Main contructor for this action handler.  
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	  public XactAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
	    super(_context, _request);
	    this.className = "XactAction";
	    api = XactFactory.create(this.dbConn, _request);
        
        //  Get selected row, if available.
        try {
            this.selecteRow = this.getSelectedRow("selCbx");    
        }
        catch (SystemException e) {
            this.selecteRow = -1;  // no row selected.
        }
	    this.xactBean = null;
	    this.xactTypeBean = null;
	  }
	  
	  /**
	   * Initializes this object using _conext and _request.  This is needed in the 
	   * event this object is inistantiated using the default constructor.
	   * 
	   * @throws SystemException
	   */
	  public void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
	      super.init(_context, _request);
	      double amount = 0;
	      int  xactTypeId = 0;
	      this.className = "XactAction";
          
          //  Get selected row, if available.
          try {
              this.selecteRow = this.getSelectedRow("selCbx");    
          }
          catch (SystemException e) {
              this.selecteRow = -1;  // no row selected.
          }
          
	      try {
	          api = XactFactory.create(this.dbConn, xactTypeId, amount); 
	      }
	      catch (DatabaseException e) {
	          throw new SystemException(e);
	      }
	  }
	
	  /**
	   * Creates a new query bean object and adds to the session object which indicates a new query session has begun.
	   *
	   */
	  public void newXactSearch() {
		  RMT2TagQueryBean query = RMT2TagQueryBean.getInstance();
		  this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
	  }
	  
	  /**
	   * Transalates the Transaction categroy code sent by the client to it's primary key value.   The results are passed back to the client via the session object.
	   * 
	   * @throws XactException
	   */
	  public void changeCashFlowCriteriaAction() throws XactException {
	
	    String method = "changeCashFlowCriteriaAction()";
	    HttpServletRequest req = this.getRequest();
	    XactQuery query;
	    String xactCatg = req.getParameter("XactCatgId");
	    int xactCatgId = 0;
	    String temp;
	    
	    if (xactCatg != null) {
	    	//  We've got a transaction category code to obtain related transaction category id.   Otherwise, transaction category id will default to zero.
		    try {
		    	// Now let's get related transaction category id from the database.
		    	this.dso = this.dbConn.getDataObject("XactCategoryView", "xml", "code = \'" + xactCatg + "\'", null);
			    ResultSet rs = this.dso.executeQuery();
			    if (this.dso.nextRow()) {
			    	temp = this.dso.getColumnValue("Id");
			    	xactCatgId = new Integer(temp).intValue();
			    }
			  }
		    catch (SystemException e) {
		    	throw new XactException(e);
		    }
		    catch (DatabaseException e) {
		    	throw new XactException(e);
		    }
		    catch (NotFoundException e) {
		    	throw new XactException(e);
		    }
		    catch (NumberFormatException e) {
		    	this.msg = "Transaction Category key could not be converted to integer for the selected Transaction Categroy code [" + xactCatg + "]";
		    	throw new XactException(this.msg, -1);
		    }
	    }
	    
	    // Prepare to send results to the client via session object.
	    try {
	    	query = new XactQuery();
	      query.setXactCatgId(xactCatgId);
	      RMT2TagQueryBean sessionQuery = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	      sessionQuery.setCustomObj(query);
	      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, sessionQuery);
	      return;
	    }
	    catch (SystemException e) {
	    	throw new XactException(e);
	    }
	  }  
	
	  
	  /**
	   * Gathers all data needed to view general transaction details.
	   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
	   * at the discretion of the client: 
		 * <p>
		 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		 * <tr>
		 *   <td><strong>Attribute</strong></td>
		 *   <td><strong>Type</strong></td>
		 *   <td><strong>Description</strong></td>
		 *  </tr>
		 * <tr>
		 *   <td>xact</td>
		 *   <td>{@link Xact}</td>
		 *   <td>Transaction object</td>
		 * </tr> 
		 * <tr>
		 *   <td>xactType</td>
		 *   <td>{@link XactType}</td>
		 *   <td>Transaction Type object</td>
		 * </tr>  
		 *</table> 
		 * 
	   * @throws XactException
	   */
	  public void viewXact() throws XactException {
	  	 String method = "viewXact";
         VwXactList xact = null;
         ArrayList list = null;
     
         // Get base transaction data from the JSP Client
         this.getHttpXactBase();
         
         // Get base transaction item data from JSP Client
         this.xactItems = this.api.findXactTypeItemsActivityByXactId(this.xactBean.getId());
         
         if (this.xactBean == null) {
             throw new XactException("Base Transaction object is invalid...viewXact failed.", -1, this.className, method);
         }
            
         try {
             // Create transaction API in the event it is not initialized
             if (this.api == null) {
                 this.api = XactFactory.create(this.dbConn);        
             }
           
             // Get combined transaction data object
             xact = this.getCombinedXact(this.xactBean.getId());
             // Get combined transaction items
             list = this.api.findVwXactTypeItemActivityByXactId(this.xactBean.getId());
             
             this.msg = "";
             return;
         }
         catch (DatabaseException e) {
              System.out.println("DatabaseException: " + e.getMessage());
              this.msg = e.getMessage();
              throw new XactException(e);
         }
         catch (SystemException e) {
              System.out.println("SystemException: " + e.getMessage());
              this.msg = e.getMessage();
              throw new XactException(e);
         }
         catch (XactException e) {
            this.msg = e.getMessage();
            throw e;
         }
         finally {
              // Send request results to the client.
             this.request.setAttribute(XactConst.PARM_XACT, xact);
             this.request.setAttribute(XactConst.PARM_XACTCATG, this.xactCategory);
             this.request.setAttribute(XactConst.PARM_XACTITEMS, list);
             this.request.setAttribute(AbstractActionHandler.SINGLE_MSG, this.msg);
        }
	  }
	
		/**
		 * Creates a new transaction consisting of the following objects which are used for client presentation: xact, xactType, xactCategory, XactTypeItem, and xactTypeItemActivity.
	     * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
	     * at the discretion of the client: 
		 * <p>
		 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		 * <tr>
		 *   <td><strong>Attribute</strong></td>
		 *   <td><strong>Type</strong></td>
		 *   <td><strong>Description</strong></td>
		 *  </tr>
		 * <tr>
		 *   <td>xact</td>
		 *   <td>{@link Xact}</td>
		 *   <td>Transaction object</td>
		 * </tr> 
		 * <tr>
		 *   <td>xactType</td>
		 *   <td>{@link XactType}</td>
		 *   <td>Transaction Type object</td>
		 * </tr>
         * <tr>
         *   <td>xactCategory</td>
         *   <td>{@link XactCategory}</td>
         *   <td>Transaction Category object</td>
         * </tr>  
         * <tr>
         *   <td>xactTypeItemActivity</td>
         *   <td>{@link XactTypeItemActivity}</td>
         *   <td>One or more Transaction Type Item Activity objects package into an ArrayList</td>
         * </tr>      
         * <tr>
         *   <td>XactTypeItem</td>
         *   <td>{@link XactTypeItem}</td>
         *   <td>All Transaction Type Item objects packaged into an ArrayList based on Transact Category Id.</td>
         * </tr>  
		 *</table> 
		 * 
		 * @throws XactException
		 */
	  public void addXact() throws XactException {
		  String method = "addXact";
	
          this.isNewXact = true;
		  // Get new base transaction object 
	  	  this.xactBean = XactFactory.createXact();
              
          // Initialize transaction type item array list
          this.xactItems = new ArrayList();
		  	  
          // Get additional transaction details
          this.getXactDetails();
	  	  
		 // Send request results to the client.
		 this.request.setAttribute(XactConst.PARM_XACT, this.xactBean);
		 this.request.setAttribute(XactConst.PARM_XACTTYPE, this.xactTypeBean);
         this.request.setAttribute(XactConst.PARM_XACTCATG, this.xactCategory);
         this.request.setAttribute(XactConst.PARM_XACTITEMS, this.xactItems);
	
	     return;
	  }
	
        /**
         * Adds one transaction type item to a transaction that is work in progress.
         * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
         * at the discretion of the client: 
         * <p>
         * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         * <tr>
         *   <td><strong>Attribute</strong></td>
         *   <td><strong>Type</strong></td>
         *   <td><strong>Description</strong></td>
         *  </tr>
         * <tr>
         *   <td>xact</td>
         *   <td>{@link Xact}</td>
         *   <td>Transaction object</td>
         * </tr> 
         * <tr>
         *   <td>xactType</td>
         *   <td>{@link XactType}</td>
         *   <td>Transaction Type object</td>
         * </tr>
         * <tr>
         *   <td>xactCategory</td>
         *   <td>{@link XactCategory}</td>
         *   <td>Transaction Category object</td>
         * </tr>  
         * <tr>
         *   <td>xactTypeItemActivity</td>
         *   <td>{@link XactTypeItemActivity}</td>
         *   <td>One or more Transaction Type Item Activity objects package into an ArrayList</td>
         * </tr>      
         * <tr>
         *   <td>XactTypeItem</td>
         *   <td>{@link XactTypeItem}</td>
         *   <td>All Transaction Type Item objects packaged into an ArrayList based on Transact Category Id.</td>
         * </tr>  
         *</table> 
         *
         * @throws XactException
         */
      public void addXactItem() throws XactException {
          Xact xact = null;
          this.isNewXact = false;
          
          //  Get base transaction data from the JSP Client
          xact = this.getHttpXactBase();
          
          // Get additional transaction details
          this.getXactDetails();
          
          // Send request results to the client.
          this.request.setAttribute(XactConst.PARM_XACT, xact);
          this.request.setAttribute(XactConst.PARM_XACTTYPE, this.xactTypeBean);
          this.request.setAttribute(XactConst.PARM_XACTCATG, this.xactCategory);
          this.request.setAttribute(XactConst.PARM_XACTITEMS, this.xactItems);
      }
	
      /**
       * Uses data from the client to build the Xactype and XactCaegory objects, and creates one or 
       * more XactTypeItemActivity objects which are packaged into an ArrayList for transport.
       * <p>  
       * To extend the functionality of this method to map transaction items to datasources other 
       * than XactTypeItemActivity , read documentation pertaining to the methods getHttpXactItems
       * and createNewXactItemObject.
       * 
       * 
       * @throws XactException
       */
      protected void getXactDetails() throws XactException {
          int xactTypeId = 0;
          
          String temp = null;
          ArrayList xtiList = null;
          Object obj = null;
          
          // Use Selected Transaction Type Id from JSP client to obtain transaction type object provided that it is not already initialized.
          if (this.xactTypeBean == null) {
              temp = this.request.getParameter("xactTypeId") == null ? this.request.getParameter("XactTypeId") : this.request.getParameter("xactTypeId");
              if (temp == null) {
                  temp = "0";
              }
              xactTypeId = Integer.parseInt(temp);
              this.xactTypeBean = api.findXactTypeById(xactTypeId);    
          }
          
          // Use transaction type object to obtain transaction category provided that it is not already initialized.
          if (this.xactCategory == null) {
              this.xactCategory = api.findXactCatgById(this.xactTypeBean.getXactCategoryId());    
          }
          
          // Get base transaction item data from JSP Client
          if (!this.isNewXact) {
              this.getHttpXactItems();    
          }
          
          // Add new generic object to the transaction items array
          obj = this.createNewXactItemObject();
          this.xactItems.add(obj);          
          
          // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.
          xtiList = this.api.findXactTypeItemsByXactTypeId(this.xactTypeBean.getId());
          this.request.setAttribute(XactConst.PARM_XACTITEMSLIST, xtiList);
      }
      
      

      /**
       * This method is intended to be overriden in order to obtain a custom transaction api designed to 
       * handle specific business needs of the user.    
       * 
       * @return A descendent of {@link XactManagerApi} if a custom api is found to be implemented.  Otherwise, null is returned. 
       */
      protected XactManagerApi getCustomXactApi() {
    	  return null;
      }
      
	  /**
	   * Saves the base transaction.
	   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
	   * at the discretion of the client: 
		 * <p>
		 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		 * <tr>
		 *   <td><strong>Attribute</strong></td>
		 *   <td><strong>Type</strong></td>
		 *   <td><strong>Description</strong></td>
		 *  </tr>
		 * <tr>
		 *   <td>xact</td>
		 *   <td>{@link Xact}</td>
		 *   <td>Transaction object</td>
		 * </tr> 
		 * <tr>
		 *   <td>xactType</td>
		 *   <td>{@link XactType}</td>
		 *   <td>Transaction Type object</td>
		 * </tr>
		 * <tr>
		 *   <td>message</td>
		 *   <td>String</td>
		 *   <td>Message denoting the result of the operation.</td>
		 * </tr>     
		 *</table>
		 *  
	   * @return int - transaction id.
	   * @throws XactException
	   * @throws DatabaseException
	   * 
	   */

      public int saveXact() throws XactException, DatabaseException {
    	  String method = "saveXact";
    	  int xactId = 0;
    	  ArrayList httpItems = null;
    	  Xact httpXact = null;
    	  VwXactList xactView = null;
    	  ArrayList xactItemActvViewList = null;
	 
    	  // Get base transaction data from the JSP Client
    	  httpXact = this.getHttpXactBase();
        
    	  if (httpXact == null) {
    		  throw new XactException("Base Transaction object is invalid...Save failed.", -1, this.className, method);
    	  }
        
    	  // Get base transaction item data from JSP Client
    	  httpItems = this.getHttpXactItems();
		  	
    	  try {
    		  // Attempt to obtain custom transaction API
    		  this.api = this.getCustomXactApi();
    		  
    		  // Obtain default api in the event a custom api is unavailable.
    		  if (this.api == null) {
    			  this.api = XactFactory.create(this.dbConn);        
    		  }

    		  // Create transaction
    		  xactId = this.api.maintainXact(httpXact, httpItems);
          
            // Get combined transaction data object
    		  xactView = this.getCombinedXact(xactId);
            
            // Get combined transaction items
    		  xactItemActvViewList = this.api.findVwXactTypeItemActivityByXactId(xactId);
	          
	         if (isOkToCommit) {
	             this.transObj.commitTrans();
	         }
	         return xactId;
	  	 }
	  	 catch (DatabaseException e) {
	  	     if (isOkToCommit) {
	  	         this.transObj.rollbackTrans();
	  	     }
			  System.out.println("DatabaseException: " + e.getMessage());
             this.msg = e.getMessage();
			  throw new XactException(e);
	  	 }
	  	 catch (SystemException e) {
	  	     if (isOkToCommit) {
	  	         this.transObj.rollbackTrans();
	  	     }
			  System.out.println("SystemException: " + e.getMessage());
             this.msg = e.getMessage();
	  		  throw new XactException(e);
	  	 }
	  	 catch (XactException e) {
	  	     if (isOkToCommit) {
	  	         this.transObj.rollbackTrans();
	  	     }
           this.msg = e.getMessage();
           throw e;
	  	 }
		 finally {
			 // Send request results to the client.
			 this.request.setAttribute(XactConst.PARM_XACT, xactView);
//			 this.request.setAttribute(XactConst.PARM_XACTTYPE, this.xactTypeBean);
//			 this.request.setAttribute(XactConst.PARM_XACTCATG, this.xactCategory);
			 this.request.setAttribute(XactConst.PARM_XACTITEMS, xactItemActvViewList);
			 this.request.setAttribute(AbstractActionHandler.SINGLE_MSG, this.msg);
		}
	}

  	/**
  	 * Reverses the base transaction amount and post the reversed amount to the General Ledger.
  	 * The reversal process simply multiplies a -1 to the base amount which basically will zero out the 
  	 * transaction.
  	 * <p>
  	   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
  	   * at the discretion of the client: 
  		 * <p>
  		 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		 * <tr>
  		 *   <td><strong>Attribute</strong></td>
  		 *   <td><strong>Type</strong></td>
  		 *   <td><strong>Description</strong></td>
  		 *  </tr>
  		 * <tr>
  		 *   <td>xact</td>
  		 *   <td>{@link Xact}</td>
  		 *   <td>Transaction object</td>
  		 * </tr> 
  		 * <tr>
  		 *   <td>xactType</td>
  		 *   <td>{@link XactType}</td>
  		 *   <td>Transaction Type object</td>
  		 * </tr>
  		 * <tr>
  		 *   <td>message</td>
  		 *   <td>String</td>
  		 *   <td>Message denoting the result of the operation.</td>
  		 * </tr>     
  		 *</table>
  		 * 
  	 * @return int - transaction id.
  	 * @throws XactException
  	 * @throws DatabaseException
  	 * 
  	 */
	public int reverseXact() throws XactException, DatabaseException {
	    String method = "reverseXact";
	    int  xactId = 0;
        VwXactList xact = null;
        ArrayList httpItems = null;
  	    Xact httpXact = null;
		ArrayList list = null;
	
	    // Get base transaction data from the JSP Client
	    httpXact = this.getHttpXactBase();
        
		 if (httpXact == null) {
		     throw new XactException("Base Transaction object is invalid...Save failed.", -1, this.className, method);
		 }
		 if (httpXact.getId() <= 0) {
		     throw new XactException("Cannot reverse a new Transaction.", -1, this.className, method);
		 }
         
		 try {
	   		  // Attempt to obtain custom transaction API
	   		  this.api = this.getCustomXactApi();
	   		  
	   		  // Obtain default api in the event a custom api is unavailable.
	   		  if (this.api == null) {
	   			  this.api = XactFactory.create(this.dbConn);        
	   		  }
             
	   		  // Get base transaction item data from JSP Client
	   		  httpItems = this.api.findXactTypeItemsActivityByXactId(httpXact.getId());
		     
	   		  // Reverse the transaction and apply to the database
	   		  xactId = this.api.reverseXact(httpXact, httpItems);   
            
	   		  // Get combined transaction data object
	   		  xact = this.getCombinedXact(xactId);
           
	   		  // Get combined transaction items
	   		  list = this.api.findVwXactTypeItemActivityByXactId(xactId);
	   		  this.msg = "Transaction was reversed successfully!";
           
	   		  if (isOkToCommit) {
	   			  this.transObj.commitTrans();
	   		  }
	   		  return xactId;
		 }
		 catch (DatabaseException e) {
		     if (isOkToCommit) {
		         this.transObj.rollbackTrans();
		     }
			  System.out.println("DatabaseException: " + e.getMessage());
			  throw new XactException(e);
		 }
		 catch (SystemException e) {
		     if (isOkToCommit) {
		         this.transObj.rollbackTrans();
		     }
			  System.out.println("SystemException: " + e.getMessage());
			  throw new XactException(e);
		 }
		 catch (XactException e) {
		     if (isOkToCommit) {
		         this.transObj.rollbackTrans();
		     }
		     throw e;
		 }
		 finally {
			  // Send request results to the client.
			 this.request.setAttribute(XactConst.PARM_XACT, xact);
//			 this.request.setAttribute(XactConst.PARM_XACTTYPE, this.xactTypeBean);
//             this.request.setAttribute(XactConst.PARM_XACTCATG, this.xactCategory);
             this.request.setAttribute(XactConst.PARM_XACTITEMS, list);
			 this.request.setAttribute(AbstractActionHandler.SINGLE_MSG, this.msg);
		}
	}

	
	  
    
	/**
	 * Stub method to be overridden at descendent.    Intended use is performing any data manipulation of the 
	 * transaction object before applying changes to the database.  
	 *
	 *@deprecated Logic should be implemented in custom api
	 */
	protected void preSaveXact() {
		return;
	}
	
	/**
	 * Stub method to be overridden at descendent.    Intended use is performing any data manipulation of the 
	 * transaction object before reversing transaction and applying changes to the database.
	 *
	 *@deprecated Logic should be implemented in custom api
	 */
	protected void preReverseXact() {
	    return;
	}
	
    
       /**
        * Reverses the base transaction amount.   As a result sof this operation, the internal transaction amount is permanently changed. 
        * The reversal process simply multiplies a -1 to the base transaction amount which basically will zero out the transaction.
        * 
        * @return {@link Xact} - The reversed base transaction object.
        * @deprecated
        */
    protected Xact reverseBaseXact() {
        String method = "reverseBaseXact";
        double amount = this.xactBean.getXactAmount() * XactConst.REVERSE_MULTIPLIER;
        this.xactBean.setXactAmount(amount);
        this.xactBean.setReason("Reversed Transaction:  " + this.xactBean.getId() + ": " + this.xactBean.getReason());
        return this.xactBean;
   }
       

   /**
    * Reverses all items of an existing transaction and associates each item with the id of the reversed base transaction as _xactId.   By default each item is expected
    * to be of type {@link XactTypeItemActivity}.   This method can be overridden to handle the reversal of transaction item types other than <b>XactTypeItemActivity</b>.
    * See documentation on method, Object reverseXactItems(Object, int), about overriding to utilize transaction items of a different data type.
    * 
    * @return ArrayList of {@link XactTypeItemActivity} which contains a list of all reversed transaction item objects.. 
    * @param _xactId is the id of the reversed base transaction.
    * @return ArrayList of reversed transaction items.   By default, each item is expected to be of type {@link XactTypeItemActivity}.
    * @deprecated
    */
   protected ArrayList reverseXactItems(int _xactId) {
       ArrayList newList = new ArrayList();
       Object item = null;
       int total = this.xactItems.size();
       
       for (int ndx = 0; ndx < total; ndx++) {
           item = this.reverseXactItems(this.xactItems.get(ndx), _xactId);
           newList.add(item);
       }
       return newList;
   }
   

   /**
    * Reverses transaction item, _obj, and returns the results to the caller.   As a result of this operation, the internal item's transaction amount is permanently changed. 
    * The reversal process simply multiplies a -1 to the item's transaction amount which basically will zero out the item.  By default, the runtime type of each item is 
    * expected to be {@link XactTypeItemActivity}.  Override this method to handle an object type other than {@link XactTypeItemActivity}.
    *   
    * @param _obj is the transaction item that is to be reversed.
    * @param _xactId is the new transaction id that is to be associated with _obj which is to be reversed.
    * @return _obj in its reverse state.   By default, _obj is of type {@link XactTypeItemActivity}.
    * @deprecated
    */
   protected Object reverseXactItems(Object _obj, int _xactId) {
       XactTypeItemActivity newItem = (XactTypeItemActivity) _obj;
       
       double amount = newItem.getAmount() * XactConst.REVERSE_MULTIPLIER;
       newItem.setAmount(amount);
       newItem.setXactId(_xactId);
       
       // Reset id to make it appear that this is a new entry.
       newItem.setId(0);
       return newItem;
   }
	
	/**
	 * Locates the transaction Id from the client's JSP.   The client must conform to naminig the JSP transaction  
	 * input fields to its respective datasource view properties.   The transaction id is required to be named either <b>Id</b> or <b>XactId</b>.  
	 * It also can be uniquely named as if it exists in a list transaction id's on the client's JSP such as <b>Id1</b> or <b>XactId10</b>.
	 *       
	 * @return int - Transaction Id
	 * @throws XactException
	 */
	protected int getXactIdFromClient() throws XactException {
		String temp = null;
		String idName = "Id";
        String xactIdName = "XactId";
		boolean isFound = false;
		int xactId = 0;
		
		// Attempt to locate transaction id from a list of transaction id's.
   	    // Try to get uniquely named transaction id prefixed with "Id". 
		temp = this.request.getParameter(idName + this.selecteRow);
		// Final test to check if transaction id was found.
		isFound = (temp != null);
	  	
        // Try to get uniquely named transaction id prefixed with "XactId".		
        if (!isFound) {
            temp = this.request.getParameter(xactIdName + this.selecteRow);
            // Final test to check if transaction id was found.
            isFound = (temp != null);
        }
        
	  	// If not successful, attempt to locate transaction id using various forms of property names.
	  	if (!isFound) {
	  	    temp = this.request.getParameter(xactIdName);
		    isFound = (temp != null);
			 // Transaction must be new if attribute is not found after series of test.
			 if (!isFound) {
			     return 0;
			 }
	  	}
		 
	    //  We found the attribute, now determine if transaction is new or existing.
	    this.msg = "[XactAction.getXactIdFromClient] Transaction Id was found within the client's request: " + temp;
	    System.out.println(this.msg);
	  	 try {
	  	 	 xactId = Integer.parseInt(temp);
	  		 return xactId;
	  	 }
  	    catch (NumberFormatException e ) {
  	        this.msg =  "The JSP transaction Id data value could not be converted to an number: " + temp;
  		     System.out.println("[XactAction.getXactIdFromClient] " + this.msg);
  		     throw new XactException(this.msg);
  	    }
	}
	
	
	
	  /**
	   * Attempts to obtain transaction data from the client JSP into a transaction object.
	   *  
	   * @return {@link Xact}- Contains the transaction data retrieved from the client.
	   * @throws SalesOrderException
	   */
	  protected Xact getXactObjectFromPage() throws XactException {
	      Xact xact = null;
		   String temp = null;
		   String subMsg = null;
		   int xactId = 0;
		   int row = 0;
		   boolean isListPage = true;
	      
	      
	      // Determine if we are coming from a page that presents data as a list of orders or as single order.
		   try {
		       // Get selected row number from client page.  If this row number exist, then we 
		       // are coming from page that contains a list of orders.
			    row = this.getSelectedRow("selCbx");
			    temp = this.request.getParameter("XactId" + row);
			    subMsg = "Transaction number could not be obtained from list of orders";
			    xactId = Integer.parseInt(temp);
		   }
		   catch (NumberFormatException e) {
		       System.out.println("[XactAction.getXactObjectFromPage] " + subMsg + ".");
		       throw new XactException(subMsg);
		   }
		   catch (SystemException e) {
		       System.out.println("[XactAction.getXactObjectFromPage] Originated from single record form.");
		       isListPage = false;
		   }
	
		   if (!isListPage) {
		       try {
		    	   // Get order id for single row
		           temp = this.request.getParameter("XactId");
		           subMsg = "Order number could not be obtained from single record form.  Must be creating an order.";
		           xactId = Integer.parseInt(temp);    
		       }
		 	   catch (NumberFormatException e) {
			       System.out.println("[XactAction.getXactObjectFromPage] " + subMsg + ".");
		           return XactFactory.createXact();    
			   }  
		   }
		   
		   // Get Transaction object
		   try {
			   XactManagerApi xactApi = XactFactory.create(this.dbConn,this.request);
		       xact = xactApi.findXactById(xactId);
		       if (xact == null) {
		           xact = XactFactory.createXact();    
		       }
		   }
		   catch (DatabaseException e) {
			   System.out.println("[XactAction.getXactObjectFromPage] " + e.getMessage());
	           throw new XactException(e);
		 	}
		   catch (SystemException e) {
			   System.out.println("[XactAction.getXactObjectFromPage] " + e.getMessage());
		       throw new XactException(e);
		   }
		   return xact;
	  }

	  
	  /**
	   * Locates customer data by querying the clinet's request object that contains the JSP page that was subimtted.   
	   * The data can be chosen from a list of customers or from a single customer record.   If customer cannot be 
	   * created using request data, then a new Customer object is created.
	   * 
	   * @return {@link Customer}
	   * @throws GLAcctException
	   */
	  protected Customer getCustomerObjectFromPage() throws GLAcctException {
	       Customer cust = null;
		   String temp = null;
		   String subMsg = null;
		   int custId = 0;
		   int row = 0;
		   boolean isOrderListPage = true;
		   GLCustomerApi custApi  = null;
	      
	      
	      // Determine if we are coming from a page that presents data as a list of orders or as single order.
		   try {
		       // Get selected row number from client page.  If this row number exist, then we 
		       // are coming from page that contains a list of orders.
			    row = this.getSelectedRow("selCbx");
			    temp = this.request.getParameter("CustomerId" + row);
			    custId = Integer.parseInt(temp);
		   }
		   catch (NumberFormatException e) {
		       subMsg = "Customer number could not be obtained from list of orders";
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		   }
		   catch (SystemException e) {
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] Originated from single record form.");
		       isOrderListPage = false;
		   }
	
		   if (!isOrderListPage) {
		       try {
		           temp = this.request.getParameter("CustomerId");
		 		     custId = Integer.parseInt(temp);    
		       }
		 	    catch (NumberFormatException e) {
		 	        subMsg = "Customer number could not be obtained from single record form.";
			        System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
			        throw new GLAcctException(subMsg);
			    }  
		   }
		   
		   // Get Customer object
		   try {
			   custApi = AcctManagerFactory.createCustomer(this.dbConn);
		       cust = custApi.findCustomerById(custId);
		       if (cust == null) {
		           cust = custApi.createCustomer();
		       }
		   }
		   catch (GLAcctException e) {
		       subMsg = "Problem retrieving Customer information from the database using Customer Id: " + custId;
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		 	}
		   catch (DatabaseException e) {
		       subMsg = "Problem creating new Customer object due to database error ";
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		   }
		   catch (SystemException e) {
		       subMsg = "Problem creating new Customer object due to system error";
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		   }
		   return cust;
	  }
	
	  
	
		/**
		 * Gathers base transaction data from the client to create transactions.  If the transaction is new then data is
		 *  retrieved from the request object.   Otherwise, a copy of the transaction is obtained from the database using 
		 *  transaction id gathered from the client.  Once completed, the properties xactBean and xactTypeBean will be 
		 * based on the values retrieved from the client's JSP page.   A transaction is considered new if the transaction if 
		 * equals zero.   The transaction id of an existing transaction will have a value equal to 1 or greater.  This method also
		 * expects the transaction type id to be supplied which is required to be named, "xactTypeId".
		 * 
		 * @return Transaction object.
		 * @throws XactException
		 */
	  protected Xact getHttpXactBase() throws XactException {
		  int xactTypeId =  0;
		  int xactId = 0;
		  Xact xact = null;;
		  XactType xactType = null;
          XactCategory xactCatg = null;
         
		 //  Get transaction id from the request object so that we can further process the transaction.
		 xactId = this.getXactIdFromClient();
		 try {
             if (xactId > 0) {
            	 // This is an existing Transaction, get copy of transaction from the database.
            	 System.out.println("Working with an exisiting Transaction.");
            	 xact = this.api.findXactById(xactId) ;
             }
             else {
            	 // Transaction is new, get remaining data from the request object.
            	 System.out.println("Working with a new Transaction.");
            	 xact = XactFactory.createXact();	 
            	 XactFactory.packageBean(this.request, xact);
            	 // Ensure that transaction id is equal to zero.
            	 xact.setId(0);
             }
             
             // Transaction type id input is required  
             try {
                 // At this point we know that we've got a valid transaction object, and we have one of two ways of obtaining the transaction id:
                 //  1.  from an existing transaction object.
                 //  2.  from the client's request parameter.
                 if (xact.getXactTypeId() > 0) {
                     xactTypeId = xact.getXactTypeId();
                     // Get transaction type object based on transaction type id passed via request object
                     xactType = api.findXactTypeById(xactTypeId);                     
                 }
                 else {
                     xactType = this.getHttpXactType();
                 }
             }
             catch (NumberFormatException e) {
                 this.msg = "Transaction Type could not be converted to a number from JSP page: " + xactType;
                 System.out.println("[XactAction.getHttpXactBase] " + this.msg);
                 throw new XactException(this.msg);
             }

             //  Use transaction type object to obtain transaction category provided that it is not already initialized.
             if (xactType != null) {
                 xactCatg = api.findXactCatgById(xactType.getXactCategoryId());    
             }
             else {
                 xactCatg = XactFactory.createXactCategory();
             }
             
		     System.out.println("[XactAction.getHttpXactBase] Transaction was successfully gathered from the client");
		     System.out.println("[XactAction.getHttpXactBase] Transaction Id: " + xact.getId());
		     System.out.println("[XactAction.getHttpXactBase] Transaction Amount: " + xact.getXactAmount());
		 }
		 catch (SystemException e) {
			 this.msg = "SystemException - " + e.getMessage();
			 System.out.println("[XactAction.getHttpXactBase]  " + this.msg);
			 throw new XactException(this.msg);
		 }
		 
		 
		 this.xactBean = xact;
		 this.xactTypeBean = xactType;
         this.xactCategory = xactCatg;
         
		 this.request.setAttribute(XactConst.PARM_XACTTYPE, xactType);
		 this.request.setAttribute(XactConst.PARM_XACTCATG, xactCatg);
		 return xact;
	  }
	

      /**
       * Searches for a transaction type id from the client's HTTP request object retrieves the associated reference data from the database.
       *  
       * @return  {@link XactType object}.
       * @throws XactException
       */
      protected XactType getHttpXactType()  throws XactException {
          String temp = null;
          int xactTypeId = 0;
          XactType xactType = null;
          
          temp = this.request.getParameter("xactTypeId") == null ? this.request.getParameter("XactTypeId") : this.request.getParameter("xactTypeId");
          if (temp == null) {
              // Try to obtain from a list of transactions
              temp = this.request.getParameter("xactTypeId" + this.selecteRow);
              if (temp ==  null) {
                  temp = "0";    
              }
          }
          xactTypeId = Integer.parseInt(temp);       
   
          // Get transaction type object based on transaction type id passed via request object
          xactType = api.findXactTypeById(xactTypeId);
          return xactType;
      }
      
     /**
      * Gathers and packages data from the client's request object into separate generic objects of type Object, which 
      * generically represents the detail items of the base transaction.  Subsequently, the transaction item buffer is refreshed with 
       * the ArrayList of generic objects that is intended to be associated with the base transaction.
       * 
      * @return 
      * @throws XactException
      */
	  protected ArrayList getHttpXactItems() throws XactException {
          ArrayList items = new ArrayList();
          Object obj = null;
          String rowIndexes[] = null;
          String property = null;
          
          System.out.println("Retrieving Base Transaction items from client UI");
          property = XactConst.ITEM_COUNT_IND;
          rowIndexes = this.request.getParameterValues(property);

          //  Return to caller if there are no items to process.
          if (rowIndexes == null) {
              return null;
          }
          
          // Process each transaction object coming from the clients http request object
          for (int ndx = 0; ndx < rowIndexes.length; ndx++) {
              obj = this.getHttpXactItems(ndx, rowIndexes);
              if (obj != null) {
                  items.add(obj);    
              }
          } // end for
          
          this.xactItems = items;
          return  items;
      }

      /**
       * Default functionality for evaluating and capturing individual pieces of data pertaining to the client's transaction item data.   
       * The data is mapped by default to {@link XactTypeItemAcivity}.  This method can be overriden to target another object other 
       * than <b>XactTypeItemAcivity</b> to map the transaction item data by also overriding the method, <b>createNewXactItemObject()</b>.
       * 
       * @param _ndx represents the target position in _rowIndexes that is to be processed.
       * @param _rowIndexes An String array of row numbers which corresponds to each transaction item derived from the client.
       * @return Generic object that will represent the mapped transaction item.  The object type returned by default is {@link XactTypeItemAcivity}.
       * @throws XactException
       */
      protected Object getHttpXactItems(int _ndx, String _rowIndexes[]) throws XactException {
          XactTypeItemActivity xtia = null;
          String value = null;
          String property = null;
          int tempInt = 0;
          int row = 0;
          double tempDouble = 0;
          
          try {
              // Get new transaction item object
              xtia = (XactTypeItemActivity) createNewXactItemObject();
              
              // Get row number
              this.msg = "[XactAction.getHttpXactItems] Problem converting row value to integer.";
              row = Integer.parseInt(_rowIndexes[_ndx]);
              
              // Was item selected by the user?
              property = XactConst.ITEM_SELECTOR  + row;
              value = this.request.getParameter(property);
              if (value != null) {
                  // Do not include this item if it is selected to be removed from the order
                  property = "Id" + row;
                  value = this.request.getParameter(property);
                  System.out.println("The following item was removed from transaction details: " + value);
                  return null;
              }
              
              // Get transaction type item activity id
              this.msg = "[XactAction.getHttpXactItems] Problem converting transaction type item activity id value to integer.";
              property = "Id" + row;
              value = this.request.getParameter(property);
              tempInt = Integer.parseInt(value);
              xtia.setId(tempInt);
              
              // Get transaction id
              this.msg = "[XactAction.getHttpXactItems] Problem converting xact id value to integer.";
              property = "XactId" + row;
              value = this.request.getParameter(property);
              tempInt = Integer.parseInt(value);
              xtia.setXactId(tempInt);
              
              // Get Transaction Type Item Id
              this.msg = "[XactAction.getHttpXactItems] Problem converting Transaction Type Item Id value to integer.";
              property = "XactTypeItemId" + row;
              value = this.request.getParameter(property);
              value = (value ==  null || value.equals("") ? "0" : value);
              tempInt = new Integer(value).intValue();
              xtia.setXactTypeItemId(tempInt);

              // Get Transaction Type Item amount
              this.msg = "[XactAction.getHttpXactItems] Problem converting Transaction Type Item amount value to integer.";
              property = "Amount" + row;
              value = this.request.getParameter(property);
              tempDouble = new Double(value).doubleValue();
              xtia.setAmount(tempDouble);
              
              // Get Transaction Type Item Description
              this.msg = "[XactAction.getHttpXactItems] Problem converting Transaction Type Item description value to integer.";
              property = "Description" + row;
              value = this.request.getParameter(property);
              xtia.setDescription(value);
              
              // Return generic object to caller
              return xtia;
          }
          catch (NumberFormatException e) {
              System.out.println(this.msg);
              return null;
          }
      }
      
      /**
       * Default functionality for creating the datasource object, {@link XactTypeItemAcivity}.  This method is generally invoked 
       * by the method, <b>getXactDetails()</b>, to store data mapped to the client's transaction items.  This method can be 
       * overriden to create an object other than <b>XactTypeItemAcivity</b> for mapping transaction item data.
       * 
       * @return By default, returns {@link XactTypeItemAcivity} cast as type Object.
       */
      protected Object createNewXactItemObject() {
          XactTypeItemActivity xtia = null;
          xtia = XactFactory.createXactTypeItemActivity();
          return xtia;
      }      

      /**
       * Retrieves transaction data from the xact, xact_type, and xact_category tables and packages the combined 
       * data in {@link VwXactList}.  Only one row at a time is obtained using _xactId.
       * 
       * @param _xactId
       * @return {@link VwXactList}
       * @throws XactException
       */
      public VwXactList getCombinedXact(int _xactId)  throws XactException {
          ArrayList list = null;
          VwXactList xact = null;
          
          this.api.setBaseClass("com.bean.VwXactList");
          this.api.setBaseView("VwXactListView");
          list = this.api.findXact("id = " + _xactId, null);
          if (list.size() > 0) {
              xact = (VwXactList) list.get(0);
          }
          return xact;
      }
      
      
	  /**
	   * Returns the total number of messages in the error queue.
	   * 
	   * @return
	   */
	  public int getErrorCount() {
	      return this.messages.size();
	  }
	  
	  
      protected void receiveClientData() throws ActionHandlerException{}
      protected void sendClientData() throws ActionHandlerException{}
      public void add() throws ActionHandlerException{}
      public void edit() throws ActionHandlerException{}
      public void save() throws ActionHandlerException{}
      public void delete() throws ActionHandlerException{}

}