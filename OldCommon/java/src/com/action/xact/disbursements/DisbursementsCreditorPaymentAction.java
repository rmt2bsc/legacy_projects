package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;

import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.bean.Business;
import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;

import com.factory.CashDisburseFactory;
import com.factory.ContactsFactory;

import com.util.SystemException;


/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */ 
public class DisbursementsCreditorPaymentAction extends DisbursementsCreditorXactCommon {
    private static final String COMMAND_SAVE = "XactDisburse.DisbursementCreditorPayment.save";
    private static final String COMMAND_BACK = "XactDisburse.DisbursementCreditorPayment.back";
	private Logger logger;
    private Business bus;
	
	
      /**
       * Default constructor
       *
       */
      public DisbursementsCreditorPaymentAction()  {
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
      public DisbursementsCreditorPaymentAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          
          if (command.equalsIgnoreCase(DisbursementsCreditorPaymentAction.COMMAND_SAVE)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(DisbursementsCreditorPaymentAction.COMMAND_BACK)) {
              this.doBack();
          }
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
               // Set Confirmation number.   Confirmation number will be a value from an external source such 
               // as the creditor/vendor's payment website or it will be null if direct  cash payment.
               String confirmNo =  this.request.getParameter("ConfirmNo");
               this.xact.setConfirmNo(confirmNo);
               
               this.xactItems = this.baseApi.findXactTypeItemsActivityByXactId(this.xact.getId());
               xactId = this.disbApi.maintainCashDisbursement(this.xact, this.xactItems, creditor.getId());
               this.xact = this.baseApi.findXactById(xactId);
               
               // Get Business object      
               Object results[];
               DaoApi dao = DataSourceFactory.createDao(this.dbConn);
               this.bus = ContactsFactory.createBusiness();
               this.bus.addCriteria("Id", this.creditor.getBusinessId());
               results = dao.retrieve(this.bus);
               if (results.length > 0) {
                  this.bus = (Business) results[0];
               }
               this.transObj.commitUOW();
           }
           catch (Exception e) {
              msg = e.getMessage();
              logger.log(Level.ERROR, msg);
              this.transObj.rollbackUOW();  
              throw new ActionHandlerException(msg);  
          }
      }
      
        
      
      /**
       * Sends the user back to the cash disbursements console page.
       * 
       * @throws ActionHandlerException
       */
      protected void doBack() throws ActionHandlerException {
          this.receiveClientData();
          this.sendClientData();
      }
      
      /**
       * Obtains key creditor data entered by the user from the request object
       */
      public void receiveClientData() throws ActionHandlerException {
          super.receiveClientData();
      }

      /**
       * Sends key transaction and creditor data to the client.
       */
      public void sendClientData() throws ActionHandlerException {
          super.sendClientData();
      }
}