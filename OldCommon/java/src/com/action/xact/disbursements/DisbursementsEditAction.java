package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;

import com.api.CashDisbursementsApi;
import com.api.db.DatabaseException;

import com.bean.RMT2TagQueryBean;
import com.bean.Xact;

import com.constants.RMT2ServletConst;
import com.constants.XactConst;

import com.factory.CashDisburseFactory;

import com.util.SystemException;
import com.util.XactException;
import com.util.CashDisbursementsException;


/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */ 
public class DisbursementsEditAction extends AbstractXactAction {
    private static final String COMMAND_ADD = "XactDisburse.DisbursementEdit.add";
    private static final String COMMAND_SAVE = "XactDisburse.DisbursementEdit.save";
    private static final String COMMAND_ADDITEM = "XactDisburse.DisbursementEdit.additem";
    private static final String COMMAND_BACK = "XactDisburse.DisbursementEdit.back";
	private Logger logger;
	private CashDisbursementsApi disbApi;
		
	
	
      /**
       * Default constructor
       *
       */
      public DisbursementsEditAction()  {
          super(); 
          logger = Logger.getLogger("DisbursementsSearchAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public DisbursementsEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          this.init(this.context, this.request);
      }
      
      
      
      /**
       * Initializes this object using _conext and _request.  This is needed in the 
       * event this object is inistantiated using the default constructor.
       * 
       * @throws SystemException
       */
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
          this.disbApi = CashDisburseFactory.createApi(this.dbConn, this.request);
          this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
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
          
          this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
          
          if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_ADD)) {
        	  this.doNewXact();
          }
          if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_ADDITEM)) {
        	  this.addItem();
          }
          if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_SAVE)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_BACK)) {
              this.doBack();
          }
      }
      
   
      
      /**
       * Instantiates the data objects needed to create a new cash 
       * disbursement transaction which the objects are sent across the wire 
       * to the client for presentation. 
       */
      public void doNewXact() throws ActionHandlerException {
    	  DisbursementsSearchAction action = new DisbursementsSearchAction();
    	  action.processRequest(this.request, this.response, DisbursementsSearchAction.COMMAND_ADD);
          Xact xact = null;
          try {
           xact = new Xact();   
          }
          catch (SystemException e){
              // Do NOthing 
          }
          this.request.setAttribute(XactConst.CLIENT_DATA_XACT, xact);
      }

      /**
       * Appends a new item object to the list of existing transaction item objects.
       * 
       * @throws ActionHandlerException
       */
      public void addItem() throws ActionHandlerException {
    	  this.receiveClientData();
    	  // Add new generic object to the transaction items array
          if (this.xactItems == null) {
              this.xactItems = new ArrayList();
          }
          Object obj = this.createNewXactItemObject();
          this.xactItems.add(obj); 
          // Send data to the client
          this.sendClientData();
      }
      
      /**
       * Obtains key data peratining to the transaction fro the client and uses 
       * the key data to fetch related transaction data into member variables 
       * from the database.
       */
      public void edit() throws ActionHandlerException {
    	  super.edit();
    	  this.genericXact = this.fetchXactExt(this.xact.getId());
    	  this.xactItems = this.fetchXactItems(this.xact.getId());
    	  this.xactType = this.fetchXactType(this.httpHelper.getXactType().getId());
      }
      
      /**
       * Applies changes to transaction data to the database.  An insert is performed 
       * new transactions (id <= 0) and a reversal is performed for existing transactions.
       * 
       * @throws ActionHandlerException
       * @see AbstractXactAction#save()
       */
      public void save() throws ActionHandlerException {
    	  super.save();
    	  int xactId = 0;
    	  
    	  try {
    		  xactId = this.disbApi.maintainCashDisbursement(this.xact, this.xactItems);
    		  this.transObj.commitUOW();
    		  this.msg = "Transaction Saved Successfully";
    	  }
    	  catch (CashDisbursementsException e){
    		  logger.log(Level.ERROR, e.getMessage());
    		  this.msg = "Error: " + e.getMessage();
    		  this.transObj.rollbackUOW();
    		  throw new ActionHandlerException(e);
    	  }
    	  finally {
    		  this.refreshXact(xactId);  
    	  }
      }
      
        
      
      /**
       * Sends the user back to the cash disbursements page.
       * 
       * @throws ActionHandlerException
       */
      protected void doBack() throws ActionHandlerException {
    	  DisbursementsSearchAction action = new DisbursementsSearchAction();
    	  action.processRequest(this.request, this.response, DisbursementsSearchAction.COMMAND_LIST);
      }
      
      /**
       * Gathers cash disbursement data from the client and packages the data in relavent objects.
       * 
       * @throws ActionHandlerException
       */
      protected void receiveClientData() throws ActionHandlerException {
          super.receiveClientData();
          // Be sure that the transactin type is properly initialized.
          try {
              this.xactType = this.baseApi.findXactTypeById(XactConst.XACT_TYPE_CASHDISBEXP);
              this.httpHelper.getHttpXactType().setId(XactConst.XACT_TYPE_CASHDISBEXP);
              this.httpHelper.getHttpXactType().setDescription(this.xactType.getDescription());
          }
          catch (XactException e) {
              this.msg = "XactException: " + e.getMessage();
              throw new ActionHandlerException(this.msg);
          }
          // Get all transaction type item entries by transaction type to be 
          // used generally as a UI Dropdown.
          try {
        	  this.xactItemTypes = this.baseApi.findXactTypeItemsByXactTypeId(this.xactType.getId());  
          }
          catch (XactException e) {
        	  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e);
          }
      }
      
      
      /**
       * Sends transaction data to the client as a response to the client's general 
       * cash disbursement request.
       * 
       * @throws ActionHandlerException
       */
      public void sendClientData() throws ActionHandlerException {
    	  super.sendClientData();
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.genericXact);
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACTTYPE, this.xactType);
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMS, this.xactItems);
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMTYPELIST, this.xactItemTypes);
    	  this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
      }
      

}