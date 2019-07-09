package com.xact.disbursements.general;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.VwXactList;
import com.bean.VwXactTypeItemActivity;
import com.bean.Xact;
import com.bean.XactTypeItemActivity;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

import com.xact.XactException;
import com.xact.XactConst;
import com.xact.AbstractXactAction;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.xact.disbursements.CashDisburseFactory;
import com.xact.disbursements.CashDisbursementsException;
import com.xact.disbursements.CashDisbursementsApi;

/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */
public class DisbursementsEditAction extends AbstractXactAction {
    private static final String COMMAND_ADD = "DisbursementsGeneral.Edit.add";

    private static final String COMMAND_SAVE = "DisbursementsGeneral.Edit.save";

    private static final String COMMAND_ADDITEM = "DisbursementsGeneral.Edit.additem";

    private static final String COMMAND_BACK = "DisbursementsGeneral.Edit.back";

    private Logger logger;

    private List tenderList;
    
    private double resultTotal;
    
    private int resultCount;
    
    
    
    /**
     * Default constructor
     *
     */
    public DisbursementsEditAction() {
	super();
	logger = Logger.getLogger("DisbursementsSearchAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public DisbursementsEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
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

	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);

	if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_ADD)) {
	    this.doNewXact();
	}
	if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_ADDITEM)) {
	    this.addItem();
	}
	if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(DisbursementsEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Instantiates the data objects needed to create a new cash 
     * disbursement transaction which the objects are sent across the wire 
     * to the client for presentation. 
     */
    public void doNewXact() throws ActionHandlerException {
	DisbursementsSearchAction action = new DisbursementsSearchAction();
	action.processRequest(this.request, this.response, DisbursementsSearchAction.COMMAND_ADD);
	Xact xact = null;
	try {
	    xact = new Xact();
	}
	catch (SystemException e) {
	    // Do NOthing 
	}
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, xact);
    }

    /**
     * Appends a new item object to the list of existing transaction item objects.
     * 
     * @throws ActionHandlerException
     */
    public void addItem() throws ActionHandlerException {
	this.receiveClientData();
	// Add new generic object to the transaction items array
	if (this.xactItems == null) {
	    this.xactItems = new ArrayList();
	}
	// Get tender codes by group id
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.tenderList = xactApi.findXactCodeByGroupId(2);
	}
	catch (XactException e) {
	    e.printStackTrace();
	    throw new ActionHandlerException(e);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
	Object obj = this.createNewXactItemObject();
	this.xactItems.add(obj);
	
	this.resultCount = this.xactItems.size();
	this.resultTotal = this.computeTotal(this.xactItems);
	
	// Send data to the client
	this.sendClientData();
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
     * Obtains key data peratining to the transaction fro the client and uses 
     * the key data to fetch related transaction data into member variables 
     * from the database.
     */
    public void edit() throws ActionHandlerException {
	super.edit();
	this.genericXact = this.fetchXactExt(this.xact.getXactId());
	this.xactItems = this.fetchXactItems(this.xact.getXactId());
	this.xactType = this.fetchXactType(this.httpHelper.getXactType().getXactTypeId());
    }

    /**
     * Applies changes to transaction data to the database.  An insert is performed 
     * new transactions (id <= 0) and a reversal is performed for existing transactions.
     * 
     * @throws ActionHandlerException
     * @see AbstractXactAction#save()
     */
    public void save() throws ActionHandlerException {
	super.save();
	int xactId = 0;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	CashDisbursementsApi disbApi = CashDisburseFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xactId = disbApi.maintainCashDisbursement(this.xact, this.xactItems);
	    tx.commitUOW();
	    this.msg = "Transaction Saved Successfully";
	}
	catch (CashDisbursementsException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException("General cash disbursement failed", e);
	}
	finally {
	    this.refreshXact(xactId);
	    disbApi.close();
	    tx.close();
	    disbApi = null;
	    tx = null;
	}
    }

    /**
     * Sends the user back to the cash disbursements page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	DisbursementsSearchAction action = new DisbursementsSearchAction();
	action.processRequest(this.request, this.response, DisbursementsSearchAction.COMMAND_LIST);
    }

    /**
     * Gathers cash disbursement data from the client and packages the data in relavent objects.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	// Be sure that the transactin type is properly initialized.
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xactType = xactApi.findXactTypeById(XactConst.XACT_TYPE_CASHDISBEXP);
	    this.httpHelper.getHttpXactType().setXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
	    this.httpHelper.getHttpXactType().setDescription(this.xactType.getDescription());
	    
		// Get all transaction type item entries by transaction type to be 
		// used generally as a UI Dropdown.
	    this.xactItemTypes = xactApi.findXactTypeItemsByXactTypeId(this.xactType.getXactTypeId());
	}
	catch (XactException e) {
		logger.log(Level.ERROR, e.getMessage());
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
     * Sends transaction data to the client as a response to the client's general 
     * cash disbursement request.
     * 
     * @throws ActionHandlerException
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.genericXact);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTTYPE, this.xactType);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMS, this.xactItems);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMTYPELIST, this.xactItemTypes);
	this.request.setAttribute(XactConst.CLIENT_DATA_TENDERLIST, this.tenderList);
	this.request.setAttribute(XactConst.CLIENT_DATA_TOTAL, this.resultTotal);
	this.request.setAttribute(XactConst.CLIENT_DATA_COUNT, this.resultCount);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

}