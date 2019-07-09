package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.util.SystemException;
import com.util.DatabaseException;

import com.action.GlCreditorAction;
import com.action.GlCustomerAction;

import com.bean.RMT2DBConnectionBean;

import com.servlet.RMT2Servlet;

import com.util.GLAcctException;
import com.util.CreditorException;


public class GlCreditorServlet extends RMT2Servlet {

	private String path = "/forms/accounting/";
  private static final String ADD = "add";
  private static final String DELETE = "delete";
  private static final String EDIT = "edit";
  private static final String SAVE = "save";
  private static final String BACK = "back";
  private static final String SEARCH = "search";
  private static final String NEWSEARCH = "newsearch";
  private static final String RESET = "reset";
  private String redirectPage = null;

  /////////////////////////////////////
  //     Constructor
  /////////////////////////////////////
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  public void initServlet() throws SystemException {
    return;
  }

  ////////////////////////////////////////////
  // Process GET/POST Re-directed request
  ////////////////////////////////////////////
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    RMT2DBConnectionBean dbBean = null;
    Object d;

    super.processRequest(request, response);
    this.clientAction = request.getParameter("clientAction");
    
    // Did this request originate from the Accounting module or the Transaction module?
    String requestType = request.getParameter("requestType");
    this.parms = requestType.equals("") ? "" : "? requestType=" + requestType;
    try {
        if (this.clientAction.equalsIgnoreCase(GlCreditorServlet.NEWSEARCH)) {
            GlCreditorAction actionHandler = new GlCreditorAction(this.context, request);
         	this.redirectPage = path + "GlCreditorSearchFrameset.jsp" + this.parms;
         	actionHandler.displayCreditorSearchConsole();
         	this.redirect(this.redirectPage, request, response);
         }
	      if (this.clientAction.equalsIgnoreCase(GlCreditorServlet.SEARCH)) {
	      	GlCreditorAction actionHandler = new GlCreditorAction(this.context, request);
	      	this.redirectPage = path + "GlCreditorMaintList.jsp" + this.parms;
	      	actionHandler.getCreditorsAction();
	      	this.redirect(this.redirectPage, request, response);
	      }
	      if (this.clientAction.equalsIgnoreCase(GlCreditorServlet.EDIT)) {
	      	GlCreditorAction actionHandler = new GlCreditorAction(this.context, request);
	      	this.redirectPage = path + "GlCreditorMaintEdit.jsp" + this.parms;
	        actionHandler.editAction();
	        this.redirect(this.redirectPage, request, response);
	      }
	      if (this.clientAction.equalsIgnoreCase(GlCreditorServlet.SAVE)) {
	        GlCreditorAction actionHandler = new GlCreditorAction(this.context, request);
	        this.redirectPage = path + "GlCreditorMaintEdit.jsp" + this.parms;
	        actionHandler.saveAction();
	        this.redirect(this.redirectPage, request, response);
	      }
	      if (this.clientAction.equalsIgnoreCase(GlCreditorServlet.ADD)) {
	        GlCreditorAction actionHandler = new GlCreditorAction(this.context, request);
	        this.redirectPage = path + "GlCreditorMaintEdit.jsp" + this.parms;
	        actionHandler.addAction();
	        this.redirect(this.redirectPage, request, response);
	      }
	      if (this.clientAction.equalsIgnoreCase(GlCreditorServlet.BACK)) {
					this.redirectPage = path + "GlCreditorSearchFrameset.jsp" + this.parms;
	        this.redirect(this.redirectPage, request, response);
	      }

/*
      if (this.action.equalsIgnoreCase(this.DELETE)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.handleDeleteContactAction("per");
        this.redirect("/ContactPersonalFrameset.jsp", request, response);
      }
*/
    }
    catch (SystemException e) {
      throw new ServletException(e.getMessage());
    }
    catch (DatabaseException e) {
      throw new ServletException(e.getMessage());
    }
    catch (GLAcctException e) {
        throw new ServletException(e.getMessage());
//      this.redirect(this.redirectPage, request, response);
    }
    catch (CreditorException e) {
        throw new ServletException(e.getMessage());
      //this.redirect(this.redirectPage, request, response);
    }    
  }

}  // End Class