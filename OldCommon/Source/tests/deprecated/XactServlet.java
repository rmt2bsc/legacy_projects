package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.bean.Xact;
import com.bean.VwXactList;

import com.util.RMT2Utility;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;
import com.util.SalesOrderException;
import com.util.ActionHandlerException;

import com.action.XactAction;
import com.action.XactCashDisburseAction;
import com.action.XactJournalEntryAction;
import com.action.XactSalesOnAccountAction;
import com.action.XactCashReceiptsAction;
import com.action.XactCashDisburseCreditorAction;

import com.constants.XactConst;

import com.servlet.RMT2Servlet;


public class XactServlet extends RMT2Servlet {

    private static final long serialVersionUID = 7854991683405713469L;
    
    /**  Base transaction action handler */
    private XactJournalEntryAction baseHandler;
    
    protected static final boolean XACT_GL_SUPPORT = true;
    

  /**
   * Method that initializes servlet.
   */
  public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.path = "/forms/xact/";
  }
  public void initServlet() throws SystemException {
      return;
  }


  /**
   * Sets the base action handler
   * 
   * @param value
   */
  public void setBaseHandler(XactJournalEntryAction value) {
      this.baseHandler = value;
  }
  
  /**
   * Gets the base action Handler.
   * 
   * @return
   */
  public XactJournalEntryAction getBaseHandler() {
      return this.baseHandler;
  }


  /**
   * Process GET/POST Re-directed request.
   */
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	 int rc = 0;

    super.processRequest(request, response);
    this.clientAction = request.getParameter("clientAction");

    try {
        // General transaction JSP actions
    	if (this.clientAction.equalsIgnoreCase(XactConst.REFRESH_CF)) {
    		XactAction actionHandler = new XactAction(this.context, request);
    		this.redirectPage = path + "XactSearch.jsp";
    		actionHandler.changeCashFlowCriteriaAction();
    		this.redirect(this.redirectPage, request, response);
    	}
        
    	// General Cash Disbursement actions
        if (this.clientAction.equalsIgnoreCase(XactConst.SAVE)) {
            XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
            actionHandler.save();
            Object obj = request.getAttribute(XactConst.PARM_XACT);
            if (obj instanceof VwXactList) {
            	rc = ((VwXactList) obj).getId();
            }
            if (obj instanceof Xact) {
            	rc = ((Xact) obj).getId();
            }
            
            if (rc > 0) {
                this.redirectPage = path + "XactGLBaseView.jsp";    
            }
            else {
                this.redirectPage = path + "XactGLBaseEdit.jsp" + this.parms;
            }
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.NEWSEARCH)) {
            XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
            actionHandler.newXactSearch();
            this.redirectPage = path + "XactSearchFrameset.jsp" + this.parms;   
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.REVERSE)) {
            XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
            actionHandler.reverseXact();
            this.parms = "?jspOrigin=" + this.jspOrigin;    
            this.redirectPage = path + "XactGeneralConfirmation.jsp" + this.parms;
            this.redirect(this.redirectPage, request, response);
        }

        if (this.clientAction.equalsIgnoreCase(XactConst.SEARCH)) {
        	XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
            rc = actionHandler.search();
            if (rc == 0) {
                String pollService = "xactservlet";
            	this.redirectPage = "/polling_wait_page.jsp?clientAction=" + XactConst.SEARCH + "&pollService=" + pollService;
            }
            else {
            	this.redirectPage = path + "XactList.jsp";
            }
      	    this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.VIEW)) {
            XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
      	    actionHandler.viewXact();
            this.parms = "?jspOrigin=" + this.jspOrigin;
            this.redirectPage = path + "XactGLBaseView.jsp" + this.parms;
            this.redirect(this.redirectPage, request, response);
        }
      
        if (this.clientAction.equalsIgnoreCase(XactConst.ADD)) {
            XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
            actionHandler.addXact();
            this.parms = "?jspOrigin=" + this.jspOrigin;
            this.redirectPage = path + "XactGLBaseEdit.jsp" + this.parms;
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.ADDITEM)) {
            XactCashDisburseAction actionHandler = new XactCashDisburseAction(this.context, request);
       	    this.redirectPage = path + "XactGLBaseEdit.jsp";
            actionHandler.addXactItem();
            this.redirect(this.redirectPage, request, response);
         }
 
        // Creditor Cash Disbursement actions
        if (this.clientAction.equalsIgnoreCase(XactConst.CD_CREDITORPAYMENT) || this.clientAction.equalsIgnoreCase(XactConst.CD_ADDREVERSE)) {
            XactCashDisburseCreditorAction actionHandler = new XactCashDisburseCreditorAction(this.context, request);
            actionHandler.addXact();
            this.redirectPage = "/forms/sales/" + "CreditorPaymentEdit.jsp";  
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(XactConst.CD_SAVECREDITORPAYMENT)) {
            XactCashDisburseCreditorAction actionHandler = new XactCashDisburseCreditorAction(this.context, request);
            actionHandler.saveXact();
            this.redirectPage = "/forms/sales/" + "CreditorPaymentConfirmation.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.CD_REVERSE)) {
            XactCashDisburseCreditorAction actionHandler = new XactCashDisburseCreditorAction(this.context, request);
            actionHandler.reverseXact();
            this.redirectPage = "/forms/sales/" + "CreditorPaymentConfirmation.jsp";
            this.redirect(this.redirectPage, request, response);
        }
     
        if (this.clientAction.equalsIgnoreCase(XactConst.CD_CREDITORXACT)) {
            XactCashDisburseCreditorAction actionHandler = new XactCashDisburseCreditorAction(this.context, request);
            actionHandler.getJournalEntryList();
            this.redirectPage = "/forms/sales/" + "CreditorXactList.jsp";
            this.redirect(this.redirectPage, request, response);
        }
      
        /*
         //  Sales Order actions
         if (this.clientAction.equalsIgnoreCase(XactConst.SO_SELECTITEMS)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
  	         actionHandler.getAvailJournalEntryItems();
   	         this.redirectPage = "/forms/sales/" + "SalesOrderItemSelection.jsp";
   	         this.redirect(this.redirectPage, request, response);
         } 
      
         if (this.clientAction.equalsIgnoreCase(XactConst.SO_ADDITEM)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
  	         actionHandler.addSalesOrderItem();
   	         this.redirectPage = "/forms/sales/" + "SalesOrderItemSelection.jsp";
   	         this.redirect(this.redirectPage, request, response);
         }
      
         if (this.clientAction.equalsIgnoreCase(XactConst.SO_UPDATEORDER)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
  	         rc = actionHandler.updateSalesOrder();
  	         switch (rc) {
             case 1:
                 this.redirectPage = "/forms/sales/" + "SalesOnAccountEdit.jsp";
	  	         break;
	  	     case 2:
	  	         this.redirectPage = "/forms/sales/" + "SalesOnAccountConfirmation.jsp";
	  	         break;    
  	          }
   	          this.redirect(this.redirectPage, request, response);
         }
      
         if (this.clientAction.equalsIgnoreCase(XactConst.SO_CANCELORDER)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
  	         actionHandler.cancelOrder();
  	         this.redirectPage = "/forms/sales/" + "SalesOnAccountView.jsp?result=cancel";
   	         this.redirect(this.redirectPage, request, response);
         }
         
         if (this.clientAction.equalsIgnoreCase(XactConst.SO_SALESRETURN)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
             actionHandler.refundOrder();
             this.redirectPage = "/forms/sales/" + "SalesOnAccountView.jsp?result=refund";
             this.redirect(this.redirectPage, request, response);
         }
      
         // Common General Journal actions
         if (this.clientAction.equalsIgnoreCase(XactConst.VIEWJOURNALLIST)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
  	         actionHandler.getJournalEntryList();
   	         this.redirectPage = "/forms/sales/" + "SalesOnAccountList.jsp";
   	         this.redirect(this.redirectPage, request, response);
         }

         if (this.clientAction.equalsIgnoreCase(XactConst.JOURNALDETAILS)) {
             XactSalesOnAccountAction actionHandler = new XactSalesOnAccountAction(this.context, request);
  	         rc = actionHandler.getJournalEntryDetails();
  	         switch (rc) {
  	         case 1:
  	             this.redirectPage = "/forms/sales/" + "SalesOnAccountEdit.jsp";
  	             break;
  	         case 2:
  	             this.redirectPage = "/forms/sales/" + "SalesOnAccountView.jsp?result=normal";
  	             break;    
	         }
   	         this.redirect(this.redirectPage, request, response);
         }

         //  Cash Receipts actions
         if (this.clientAction.equalsIgnoreCase(XactConst.CR_CUSTOMERPAYMENT) || this.clientAction.equalsIgnoreCase(XactConst.CR_ADDREVERSE)) {
                 XactCashReceiptsAction actionHandler = new XactCashReceiptsAction(this.context, request);
    	  	     actionHandler.addXact();
    	  	     this.redirectPage = "/forms/sales/" + "CustomerPaymentEdit.jsp";  
    	   	     this.redirect(this.redirectPage, request, response);
         }
         
         if (this.clientAction.equalsIgnoreCase(XactConst.CR_REVERSE)) {
             XactCashReceiptsAction actionHandler = new XactCashReceiptsAction(this.context, request);
             actionHandler.reverseXact();
             this.redirectPage = "/forms/sales/" + "CustomerPaymentConfirmation.jsp";  
             this.redirect(this.redirectPage, request, response);
         }
      
         if (this.clientAction.equalsIgnoreCase(XactConst.CR_SAVECUSTOMERPAYMENT)) {
             XactCashReceiptsAction actionHandler = new XactCashReceiptsAction(this.context, request);
  	         actionHandler.saveXact();
  	         this.redirectPage = "/forms/sales/" + "CustomerPaymentConfirmation.jsp";
   	         this.redirect(this.redirectPage, request, response);
         }
      
         if (this.clientAction.equalsIgnoreCase(XactConst.CR_CUSTOMERXACT)) {
             XactCashReceiptsAction actionHandler = new XactCashReceiptsAction(this.context, request);
             actionHandler.getJournalEntryList();
             this.redirectPage = "/forms/sales/" + "CustomerSalesXactList.jsp";
             this.redirect(this.redirectPage, request, response);
         }
*/      
         
      
  
    }
    catch (SystemException e) {
        throw new ServletException(e.getMessage());
    }
    catch (DatabaseException e) {
        throw new ServletException(e.getMessage());
    }
    catch (XactException e) {
        throw new ServletException(e.getMessage());
    }
    catch (ActionHandlerException e) {
        throw new ServletException(e.getMessage());
    }
  }

}  // End Class