package com.xact.purchases.creditor;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Xact;
import com.bean.XactTypeItemActivity;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;
import com.gl.creditor.CreditorExt;

import com.util.SystemException;
import com.xact.XactConst;
import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

/**
 * This class provides action handlers to respond to an associated controller for searching, 
 * adding, deleting, and validating Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesEditAction extends CreditorPurchasesAction {
    private static final String COMMAND_ADD = "PurchasesCreditor.Edit.add";

    private static final String COMMAND_SEARCH = "PurchasesCreditor.Edit.search";

    private static final String COMMAND_ADDITEM = "PurchasesCreditor.Edit.additem";

    private static final String COMMAND_SAVE = "PurchasesCreditor.Edit.save";

    private static Logger logger = Logger.getLogger("CreditorPurchasesEditAction");

    private ArrayList xactList;

    private CreditorPurchasesApi api;
    

    /**
     * Default constructor
     *
     */
    public CreditorPurchasesEditAction() throws SystemException {
	super();
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CreditorPurchasesEditAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);

    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_ADDITEM)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_ADD)) {
	    this.doNewTransaction();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_SEARCH)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
    }

    /**
     * Preapres the client for adding an item to creditor purchase order
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.receiveClientData();
	// Add new generic object to the transaction items array
	Object obj = this.createNewXactItemObject();
	this.httpXactHelper.getXactItems().add(obj);

	// Get all transaction type item entries by transaction type to be 
	// used generally as a UI Dropdown.
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xactItemTypes = xactApi.findXactTypeItemsByXactTypeId(this.httpXactHelper.getXactType().getXactTypeId());
	    this.resultCount = this.httpXactHelper.getXactItems().size();
	    this.resultTotal = this.computeTotal(this.httpXactHelper.getXactItems());
	    this.getCreditorList();    
	    return;
	}
	catch (XactException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
    }
    
    /**
     * Calculates the total dollar amount from a list of a cash disbursement items.  The cash disbursements 
     * contained in the list must be of type XactTypeItemActivity.
     *  
     * @param list
     *          A List of {@link com.bean.XactTypeItemActivity XactTypeItemActivity} 
     * @return double
     *          The summed dollar amount 
     */
    private double computeTotal(List <XactTypeItemActivity> list) {
	if (list == null) {
	    return 0;
	}
	double total = 0;
	for (XactTypeItemActivity item : list) {
	    total += item.getAmount();
	}
	return total;
    }


    /**
     * Saves the changes of an expense credit purchase transaction to the database.
     */
    public void save() throws ActionHandlerException {
	int xactId = 0;
	Xact xact = this.httpXactHelper.getXact();
	List items = (List) this.httpXactHelper.getXactItems();
	CreditorExt cred = this.httpXactHelper.getCreditor();
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = CreditorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xactId = this.api.maintainCreditCharge(xact, items, cred.getCreditorId());
	    tx.commitUOW();
	    this.httpXactHelper.retrieveCreditOrder(xactId, cred.getCreditorId());
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Creditor Purchase Transaction failed";
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg, e); 
	}
	finally {
		this.api.close();
		tx.close();
		this.api = null;
		tx = null;
	}

    }

    /** 
     * This operation is the first step in preparing the user to return 
     * to the Creditor Purchases Console which performs a search based on the previous 
     * criteria stored in the session.
     * 
     * @throws ActionHandlerException
     */
    public void doBack() throws ActionHandlerException {
        super.doBack();
    }

    /**
     * Setup for new Creditor Purcases Transaction
     * 
     * @throws ActionHandlerException
     */
    public void doNewTransaction() throws ActionHandlerException {
	CreditorPurchasesSearchAction action = new CreditorPurchasesSearchAction();
	action.processRequest(request, response, CreditorPurchasesSearchAction.COMMAND_ADD);
	action = null;
    }

    protected void sendClientData() throws ActionHandlerException {
	super.sendClientData();

	// Set data on request object
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTLIST, this.xactList);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMTYPELIST, this.xactItemTypes);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

}