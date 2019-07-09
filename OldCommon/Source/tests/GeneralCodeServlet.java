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
import com.util.DatabaseException;
import com.util.GeneralCodeException;

import com.constants.RMT2ServletConst;
import com.constants.RMT2SqlConst;

import com.api.RMT2DBConnectionApi;

import com.bean.RMT2DBConnectionBean;

import com.action.GeneralCodeAction;



public class GeneralCodeServlet extends RMT2Servlet {

	private String path = "/forms/codes/";
  private final String ADD = "add";
  private final String DELETE = "delete";
  private final String SAVE = "save";
  private final String GROUP_DETAILS = "details";

  private final String ADD_CODE = "add_code";
	private final String DELETE_CODE = "delete_code";
	private final String SAVE_CODE = "save_code";


  private final String BACK = "back";
  private final String BACK_TO_PARENT = "back_parent";
  private final int GROUP_API = 1;
  private final int CODE_API = 2;


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
    String entity = null;
    int     apiType = 0;
    String group_id = null;

    super.processRequest(request, response);
    try {
			entity = request.getParameter("entity");
			if (entity.equals("group")) {
				apiType = this.GROUP_API;
			}
			if (entity.equals("code")) {
				apiType = this.CODE_API;
				group_id = request.getParameter("GroupId0");
				request.setAttribute("group_id", group_id);
			}

			//  Handle General Code Group Requests
      if (this.clientAction.equalsIgnoreCase(this.SAVE)) {
        GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.saveAction();
        this.redirect(path + "GeneralCodeGroupFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.ADD)) {
				GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.addAction();
        this.redirect(path + "GeneralCodesGroupAdd.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.DELETE)) {
        GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.deleteAction();
        this.redirect(path + "GeneralCodeGroupFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.GROUP_DETAILS)) {
        GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.groupDetailsAction();
        this.redirect(path + "GeneralCodeFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.BACK)) {
        this.redirect(path + "GeneralCodeGroupFrameset.jsp", request, response);
      }

			//  Handle General Code Requests
      if (this.clientAction.equalsIgnoreCase(this.SAVE_CODE)) {
        GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.saveAction();
        this.redirect(path + "GeneralCodeFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.ADD_CODE)) {
				GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.addAction();
        this.redirect(path + "GeneralCodesMaintAdd.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.DELETE_CODE)) {
        GeneralCodeAction actionHandler = new GeneralCodeAction(this.context, request, apiType);
        actionHandler.deleteAction();
        this.redirect(path + "GeneralCodeFrameset.jsp", request, response);
			}
      if (this.clientAction.equalsIgnoreCase(this.BACK_TO_PARENT)) {
        this.redirect(path + "GeneralCodeGroupFrameset.jsp", request, response);
      }
    }
    catch (DatabaseException e) {
      throw new ServletException(e.getMessage());
    }
    catch (GeneralCodeException e) {
      throw new ServletException(e.getMessage());
    }
    catch (SystemException e) {
      throw new ServletException(e.getMessage());
    }
  }
}  // End Class