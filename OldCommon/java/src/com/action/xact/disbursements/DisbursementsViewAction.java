package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.xact.AbstractXactAction;
import com.action.ActionHandlerException;

import com.api.CashDisbursementsApi;

import com.api.db.DatabaseException;

import com.factory.CashDisburseFactory;

import com.util.CashDisbursementsException;
import com.util.SystemException;

import com.util.XactException;


/**
 * This class provides functionality needed View Cash Disbursements transactions in read-only mode
 * 
 * @author Roy Terrell
 *
 */ 
public class DisbursementsViewAction extends AbstractXactAction {
    private static final String COMMAND_ADD = "XactDisburse.DisbursementView.add";
    private static final String COMMAND_REVERSE = "XactDisburse.DisbursementView.reverse";
    private static final String COMMAND_BACK = "XactDisburse.DisbursementView.back";
    private CashDisbursementsApi disbApi;
	private Logger logger;
	
      /**
       * Default constructor
       *
       */
      public DisbursementsViewAction()  {
          super(); 
          logger = Logger.getLogger("DisbursementsViewAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public DisbursementsViewAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          if (command.equalsIgnoreCase(DisbursementsViewAction.COMMAND_ADD)) {
        	  this.doNewXact();
          }
          if (command.equalsIgnoreCase(DisbursementsViewAction.COMMAND_REVERSE)) {
              this.doReverse();
          }
          if (command.equalsIgnoreCase(DisbursementsViewAction.COMMAND_BACK)) {
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
      }

      /**
       * Reverses a cash disbursement transaction which the transaction data that 
       * is to be reversed is obtained from the client.
       * 
       * @throws ActionHandlerException
       */
      public void doReverse() throws ActionHandlerException {
          this.receiveClientData();
          this.save();
          this.msg = "Transaction Reversed Successfully";
          this.sendClientData();
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
              this.xact = this.baseApi.findXactById(this.xact.getId());
              xactId = this.disbApi.maintainCashDisbursement(this.xact, this.xactItems);
              this.transObj.commitUOW();
              this.msg = "Transaction Saved Successfully";
          }
          catch (XactException e){
              logger.log(Level.ERROR, e.getMessage());
              this.msg = "Error: " + e.getMessage();
              this.transObj.rollbackUOW();
              throw new ActionHandlerException(e);
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
      

}