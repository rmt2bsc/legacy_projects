package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.servlet.AbstractServlet;

import com.util.SystemException;
import com.util.NotFoundException;
import com.util.BusinessException;
import com.util.GLAcctException;

import com.bean.db.DatabaseConnectionBean;

import com.action.GlAccountAction;
import com.api.db.DatabaseException;



public class GlAccountServlet extends AbstractBaseServlet {

	private String path = "/forms/accounting/";
  private final String START = "start";
  private final String GETCATG = "getAcctCatg";
  private final String GETACCT = "getAcct";
  private final String BACK = "back";
  private final String EDIT = "edit";
  private final String ADD = "add";
  private final String DELETE = "del";
  private final String SAVE = "save";
  


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

    DatabaseConnectionBean dbBean = null;

    super.processRequest(request, response);

    // TODO: Test action and respond appropriately
    try {
      if (this.clientAction.equalsIgnoreCase(this.GETCATG)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.getAccountCategoryAction();
        this.redirect(path + "GlAcctMaintCatgList.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.GETACCT)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.getAccountsAction();
        this.redirect(path + "GlAcctMaintList.jsp", request, response);
      }      
      if (this.clientAction.equalsIgnoreCase(this.SAVE)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.saveAction();
        this.redirect(path + "GlAcctMaintEdit.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.EDIT)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.editAction();
        this.redirect(path + "GlAcctMaintEdit.jsp", request, response);
      }      
      if (this.clientAction.equalsIgnoreCase(this.ADD)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.addAction();
        this.redirect(path + "GlAcctMaintEdit.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.BACK)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.backAction();
        this.redirect(path + "GlAcctMaintList.jsp", request, response);
      }      
      if (this.clientAction.equalsIgnoreCase(this.DELETE)) {
      	GlAccountAction actionHandler = new GlAccountAction(this.context, request);
        actionHandler.deleteAction("GlAccountsView", "selCbx");
        this.redirect(path + "GlAcctMaintList.jsp", request, response);
      }
    }
    catch (SystemException e) {
      throw new ServletException(e.getMessage());
    }
    catch (DatabaseException e) {
      throw new ServletException(e.getMessage());
    }
    catch (NotFoundException e) {
      throw new ServletException(e.getMessage());
    }
    catch (GLAcctException e) {
      throw new ServletException(e.getMessage());
    }


  }


}  // End Class