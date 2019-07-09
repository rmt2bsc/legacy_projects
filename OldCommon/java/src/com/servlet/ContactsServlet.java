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
import com.constants.ContactsConst;

import com.action.ContactsAction;

import com.api.DataProviderConnectionApi;
import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;

import com.bean.db.DatabaseConnectionBean;

import com.servlet.AbstractServlet;

import com.util.ContactException;


public class ContactsServlet extends AbstractBaseServlet {

	private String path = "/forms/contact/";
  private final String START = "start";
  private final String ADD_PER = "add_per";
  private final String ADD_BUS = "add_bus";
  private final String DELETE_PER = "del_per";
  private final String DELETE_BUS = "del_bus";
  private final String SAVE_PER = "save_per";
  private final String SAVE_BUS = "save_bus";
  private final String BACK = "back";
  private final String BACK_BUS = "back_bus";
  private final String BACK_PER = "back_per";
  private final String SEARCH = "search";
  private final String RESET = "reset";

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
    this.clientAction = request.getParameter("clientAction");

    try {
			//  Start with new Combine Contact Criteria Bean on the session object
      if (this.clientAction.equalsIgnoreCase(this.START) || this.clientAction.equalsIgnoreCase(this.RESET)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.beginContactSession();
        this.redirect(path + "ContactSearchCriteria.jsp", request, response);
      }

      if (this.clientAction.equalsIgnoreCase(this.SEARCH)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.handleSearchAction();
        String contactType = request.getParameter("ContactType");
        if (contactType.equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
					this.redirect(path + "ContactBusinessFrameset.jsp", request, response);
				}
				if (contactType.equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
					this.redirect(path + "ContactPersonalFrameset.jsp", request, response);
				}
      }


      if (this.clientAction.equalsIgnoreCase(this.SAVE_BUS)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.handleSaveBusinessAction();
        String business_id = (String) request.getAttribute("business_id");
        String address_id = (String) request.getAttribute("address_id");
        String parms = "?business_id=" + business_id + "&address_id=" + address_id;
        this.redirect(path + "ContactBusinessMaint.jsp" + parms, request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.SAVE_PER)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.handleSavePersonAction();
        String person_id = (String) request.getAttribute("person_id");
        String address_id = (String) request.getAttribute("address_id");
        String parms = "?person_id=" + person_id + "&address_id=" + address_id;
        this.redirect(path + "ContactPersonMaint.jsp" + parms, request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.ADD_BUS)) {
        this.redirect(path + "ContactBusinessAdd.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.ADD_PER)) {
        this.redirect(path + "ContactPersonAdd.jsp", request, response);
      }

      if (this.clientAction.equalsIgnoreCase(this.DELETE_PER)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.handleDeleteContactAction("per");
        this.redirect(path + "ContactPersonalFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.DELETE_BUS)) {
        ContactsAction actionHandler = new ContactsAction(this.context, request);
        actionHandler.handleDeleteContactAction("bus");
        this.redirect(path + "ContactBusinessFrameset.jsp", request, response);
      }

      if (this.clientAction.equalsIgnoreCase(this.BACK)) {
        this.redirect(path + "ContactSearchCriteria.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.BACK_BUS)) {
        this.redirect(path + "ContactBusinessFrameset.jsp", request, response);
      }
      if (this.clientAction.equalsIgnoreCase(this.BACK_PER)) {
        this.redirect(path + "ContactPersonalFrameset.jsp", request, response);
      }
    }
    catch (SystemException e) {
      throw new ServletException(e.getMessage());
    }

    catch (DatabaseException e) {
      throw new ServletException(e.getMessage());
    }

    catch (ContactException e) {
      throw new ServletException(e.getMessage());
    }

  }

}  // End Class