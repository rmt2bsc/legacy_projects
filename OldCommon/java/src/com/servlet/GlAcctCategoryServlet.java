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

import com.action.GlAcctCategoryAction;
import com.api.db.DatabaseException;



public class GlAcctCategoryServlet extends AbstractBaseServlet {

	private String path = "/forms/accounting/";
  private final String START = "start";
  private final String ADD = "add";
  private final String EDIT = "edit";
  private final String CANCEL = "cancel";
  private final String DELETE = "delete";
  private final String SAVE = "save";
  private final String GETCATG = "getAcctCatg";


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
    System.out.println("Client Action: " + this.clientAction);

    // TODO: Test action and respond appropriately
    try {
      if (this.clientAction.equalsIgnoreCase(this.START)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.startAction();
        this.redirect(path + "GlAcctCatgMaintFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.GETCATG)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.getAccountCategoryAction();
        this.redirect(path + "GlAcctCatgMaintList.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.SAVE)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.saveAction();
        this.redirect(path + "GlAcctCatgMaintList.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.EDIT)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.editAction();
        this.redirect(path + "GlAcctCatgMaintEdit.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.ADD)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.addAction();
        this.redirect(path + "GlAcctCatgMaintEdit.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.CANCEL)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.cancelAction();
        this.redirect(path + "GlAcctCatgMaintList.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.DELETE)) {
        GlAcctCategoryAction actionHandler = new GlAcctCategoryAction(this.context, request);
        actionHandler.deleteAction("GlAccountCategoryView", "selCbx");
        this.redirect(path + "GlAcctCatgMaintList.jsp", request, response);
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