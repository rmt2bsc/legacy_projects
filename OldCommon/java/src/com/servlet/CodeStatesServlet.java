package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.Hashtable;

import com.util.SystemException;
import com.util.NotFoundException;
import com.util.BusinessException;

import com.constants.RMT2ServletConst;

import com.api.DataProviderConnectionApi;
import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;

import com.bean.db.DatabaseConnectionBean;

import com.action.CodeStatesAction;

import com.servlet.AbstractServlet;


public class CodeStatesServlet extends AbstractBaseServlet {

  private final String EDIT = "Edit";
  private final String ADD = "Add";
  private final String DELETE = "Delete";
  private final String SAVE = "Save";


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
      if (this.clientAction.equalsIgnoreCase(this.EDIT)) {
        CodeStatesAction actionHandler = new CodeStatesAction(this.context, request);
        actionHandler.editAction();
        this.redirect("/fh_codeStatesMaint.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.SAVE)) {
        CodeStatesAction actionHandler = new CodeStatesAction(this.context, request);
        actionHandler.saveAction();
        this.redirect("/fh_codeStatesList.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.ADD)) {
        CodeStatesAction actionHandler = new CodeStatesAction(this.context, request);
        actionHandler.addAction();
        this.redirect("/fh_codeStatesMaint.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.DELETE)) {
        CodeStatesAction actionHandler = new CodeStatesAction(this.context, request);
        actionHandler.deleteAction("StatesCodeView", "selCbx");
        this.redirect("/fh_codeStatesList.jsp", request, response);
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
    catch (BusinessException e) {
      throw new ServletException(e.getMessage());
    }

  }


}  // End Class