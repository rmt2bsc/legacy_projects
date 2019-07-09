package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.util.RMT2Utility;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.ActionHandlerException;

import com.action.CreditChargesAction;

import com.constants.XactConst;
import com.constants.RMT2ServletConst;

import com.servlet.RMT2Servlet;


public class CreditChargesServlet extends RMT2Servlet {

    private static final long serialVersionUID = 7854991683405713469L;

  /**
   * Method that initializes servlet.
   */
  public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.path = "/forms/creditcharges/";
  }
  public void initServlet() throws SystemException {
      return;
  }


 


  /**
   * Process GET/POST Re-directed request.
   */
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	 int rc = 0;

    super.processRequest(request, response);
    this.clientAction = request.getParameter("clientAction");

    try {
        if (this.clientAction.equalsIgnoreCase(XactConst.NEWSEARCH)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            actionHandler.newXactSearch();
            this.redirectPage = path + "CreditChargeSearch.jsp";   
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.SEARCH)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            rc = actionHandler.search(RMT2ServletConst.SEARCH_MODE_NEW);
            if (rc == 0) {
                String pollService = "creditchargeservlet";
                this.redirectPage = "/polling_wait_page.jsp?clientAction=" + XactConst.SEARCH + "&pollService=" + pollService;
            }
            else {
                this.redirectPage = path + "CreditChargeSearch.jsp";
            }
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.OLDSEARCH)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            rc = actionHandler.search(RMT2ServletConst.SEARCH_MODE_OLD);
            if (rc == 0) {
//                String pollService = RMT2Utility.getWebAppContext(request) + "/creditchargeservlet";
                String pollService = "/creditchargeservlet";
                this.redirectPage = "/polling_wait_page.jsp?clientAction=" + XactConst.OLDSEARCH + "&pollService=" + pollService;
            }
            else {
                this.redirectPage = path + "CreditChargeSearch.jsp";
            }
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.ADD)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            actionHandler.add();
            this.redirectPage = path + "CreditChargeEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.ADDITEM)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            actionHandler.addItem();
            this.redirectPage = path + "CreditChargeEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.EDIT)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            actionHandler.edit();
            this.redirectPage = path + "CreditChargeConfirmation.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.SAVE)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            actionHandler.save();
            this.redirectPage = path + "CreditChargeConfirmation.jsp";    
            this.redirect(this.redirectPage, request, response);
        }
        
        if (this.clientAction.equalsIgnoreCase(XactConst.REVERSE)) {
            CreditChargesAction actionHandler = new CreditChargesAction(this.context, request);
            actionHandler.reverse();
            this.redirectPage = path + "CreditChargeConfirmation.jsp";    
            this.redirect(this.redirectPage, request, response);
        }
 
    }
    catch (SystemException e) {
        throw new ServletException(e.getMessage());
    }
    catch (DatabaseException e) {
        throw new ServletException(e.getMessage());
    }
    catch (ActionHandlerException e) {
        throw new ServletException(e.getMessage());
    }
  }

}  // End Class