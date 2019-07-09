package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.constants.ItemConst;

import com.action.ItemMasterAction;
import com.action.VendorItemAction;
import com.action.GlCreditorAction;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.GLAcctException;
import com.util.ActionHandlerException;


public class ItemMasterServlet extends RMT2Servlet {
    private static final long serialVersionUID = 6186776800531561356L;

/**
 * Initialization method for servlet.
 */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.path = "/forms/accounting/inventory/";
  }

  public void initServlet() throws SystemException {
    return;
  }

  /**
   * Process GET/POST Re-directed request
   */
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    super.processRequest(request, response);
    this.clientAction = request.getParameter("clientAction");

    try {
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_NEWSEARCH)) {
            ItemMasterAction actionHandler = new ItemMasterAction(this.context, request);
            actionHandler.startSearchConsole();
            this.redirectPage = this.path + "ItemMasterSearch.jsp";
            this.redirect(this.redirectPage, request, response);
          }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_SEARCH)) {
            ItemMasterAction actionHandler = new ItemMasterAction(this.context, request);
            actionHandler.buildSearchCriteria();
            this.redirectPage = this.path + "ItemMasterSearch.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_OLDSEARCH)) {
            this.redirectPage = this.path + "ItemMasterSearch.jsp";
            this.redirect(this.redirectPage, request, response);
        }        
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_EDIT)) {
            ItemMasterAction actionHandler = new ItemMasterAction(this.context, request);
            actionHandler.edit();
            this.redirectPage = this.path + "ItemMasterEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_SAVE)) {
            ItemMasterAction actionHandler = new ItemMasterAction(this.context, request);
            actionHandler.save();
            this.redirectPage = this.path + "ItemMasterEdit.jsp";
            this.redirect(this.redirectPage, request, response);  
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_ADD)) {
            ItemMasterAction actionHandler = new ItemMasterAction(this.context, request);
            actionHandler.add();
            this.redirectPage = this.path + "ItemMasterEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_DELETE)) {
            ItemMasterAction actionHandler = new ItemMasterAction(this.context, request);
            actionHandler.delete();
            this.redirectPage = this.path + "ItemMasterEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_VIEW)) {
            VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.startSearchConsole();
            this.redirectPage = this.path + "VendorItemAssoc.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_ASSIGN)) {
            VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.add();
            this.redirectPage = this.path + "VendorItemAssoc.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_UNASSIGN)) {
            VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.delete();
            this.redirectPage = this.path + "VendorItemAssoc.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_EDIT)) {
            VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.edit();
            this.redirectPage = this.path + "VendorItemEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_SAVE)) {
            VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.save();
            this.redirectPage = this.path + "VendorItemEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_BACK)) {
        	GlCreditorAction actionHandler = new GlCreditorAction(this.context, request);
            actionHandler.editAction();
            this.redirectPage = "/forms/accounting/" + "GlCreditorMaintEdit.jsp?requestType=xact";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_OVERRIDE)) {
        	VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.activateItemOverride();
            this.redirectPage = this.path + "VendorItemAssoc.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(ItemConst.REQ_VEND_ITEM_OVERRIDEREMOVE)) {
        	VendorItemAction actionHandler = new VendorItemAction(this.context, request);
            actionHandler.deActivateItemOverride();
            this.redirectPage = this.path + "VendorItemAssoc.jsp";
            this.redirect(this.redirectPage, request, response);
        }
    }
    catch (ActionHandlerException e) {
        throw new ServletException(e.getMessage());
    }
    catch (GLAcctException e) {
    	throw new ServletException(e.getMessage());
    }
    catch (SystemException e) {
      throw new ServletException(e.getMessage());
    }
    catch (DatabaseException e) {
      throw new ServletException(e.getMessage());
    }


  }


}  // End Class