package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.bean.PurchaseOrderStatus;

import com.constants.PurchasesConst;

import com.action.PurchaseOrderAction;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.ActionHandlerException;


public class PurchaseOrderServlet extends RMT2Servlet {
    private static final long serialVersionUID = 6186776800531561356L;

/**
 * Initialization method for servlet.
 */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.path = "/forms/purchases/";
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
    PurchaseOrderStatus pos = null;

    try {
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_NEWSEARCH)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.startSearchConsole();
            this.redirectPage = this.path + "PurchaseOrderSearch.jsp";
            this.redirect(this.redirectPage, request, response);
          }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_SEARCH)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.buildSearchCriteria();
            this.redirectPage = this.path + "PurchaseOrderSearch.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_OLDSEARCH)) {
            this.redirectPage = this.path + "PurchaseOrderSearch.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_ADD)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.add();
            this.redirectPage = this.path + "PurchaseOrderItemSelect.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_ADDITEM)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.addItem();
            this.redirectPage = this.path + "PurchaseOrderItemSelect.jsp";
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_EDIT)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.edit();
            pos = (PurchaseOrderStatus) request.getAttribute("pos");
            if (pos.getId() > 1) {
                this.redirectPage = this.path + "PurchaseOrderView.jsp";
            }
            else {
                this.redirectPage = this.path + "PurchaseOrderEdit.jsp";    
            }
            
            this.redirect(this.redirectPage, request, response);
        }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_SAVENEWITEM)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.saveNewItems();
            this.redirectPage = this.path + "PurchaseOrderEdit.jsp";
            this.redirect(this.redirectPage, request, response);  
        }
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_SAVE)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.save();
            this.redirectPage = this.path + "PurchaseOrderEdit.jsp";
            this.redirect(this.redirectPage, request, response);  
        }

        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_FINALIZE)) {
        	PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.submit();
            this.redirectPage = this.path + "PurchaseOrderConfirmation.jsp";
            this.redirect(this.redirectPage, request, response);  
        }

        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_CANCEL)) {
            PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.cancel();
            this.redirectPage = this.path + "PurchaseOrderConfirmation.jsp";
            this.redirect(this.redirectPage, request, response);  
        }
        
        if (this.clientAction.equalsIgnoreCase(PurchasesConst.REQ_DELETE)) {
            PurchaseOrderAction actionHandler = new PurchaseOrderAction(this.context, request);
            actionHandler.delete();
            this.redirectPage = this.path + "ItemMasterEdit.jsp";
            this.redirect(this.redirectPage, request, response);
        }
    }
    catch (ActionHandlerException e) {
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