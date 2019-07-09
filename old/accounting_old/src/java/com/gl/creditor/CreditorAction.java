package com.gl.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceFactory;

import com.bean.Creditor;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;

import com.gl.BasicGLApi;

import com.util.SystemException;

import com.xact.disbursements.DisbursementsConst;




/**
 * This abstarct class provides common functionality needed to serve various 
 * user interfaces pertaining to Creditor maintenance.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class CreditorAction extends AbstractActionHandler implements ICommand {
    private Logger logger;

    /** Creditor's balance */
    private Double balance;

    /** An ArrayList of Creditors */
    protected List creditors;

    /** Creditor */
    protected Object cred;

    /** Credotr Extension */
    protected Object credExt;

    /** Creditor's Business profile */
    private Object credDetail;

    private Object busTypes;

    private Object busServTypes;

//    protected BasicAccountingHelper acctHelper;

    /**
     * Default constructor
     * 
     */
    public CreditorAction() {
	super();
	logger = Logger.getLogger("CreditorAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param _context
     *            The servlet context to be associated with this action handler
     * @param _request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public CreditorAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
//	this.acctHelper = new BasicAccountingHelper();
    }

    /**
     * Processes the client's request using request, response, and command.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
    }

    /**
     * Obtains key creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
	try {
	    this.cred = CreditorFactory.createCreditor();
	    DataSourceFactory.packageBean(this.request, this.cred);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * <p>
     * Gathers data for the following objects and returns the data to the client
     * via the HttpServletRequest object to be handle at the discretion of the client:
     * <p>
     * <table width="100%" border="1" cellspacing="0" cellpadding="0">
     * <tr>
     * <td><strong>Attribute</strong></td>
     * <td><strong>Type</strong></td>
     * <td><strong>Description</strong></td>
     * <td><strong>Attribute Name</strong></td>
     * </tr>
     * <tr>
     * <td>CREDITOR</td>
     * <td>{@link Creditor}</td>
     * <td>Creditor data</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>CREDITOR LIST</td>
     * <td>List of {@link CreditorExt}</td>
     * <td>List of Creditors</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>BUSINESS CONTACT</td>
     * <td>XML from contact application</td>
     * <td>Business and Address data</td>
     * <td>business</td>
     * </tr>
     * <tr>
     * <td>BUSINESS TYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Type data</td>
     * <td>businesstypes</td>
     * </tr>
     * <tr>
     * <td>BUSINESS SERVTYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Service Type data</td>
     * <td>businessservicetypes</td>
     * </tr>
     * <tr>
     * <td>CREDITOR BALANCE</td>
     * <td>Double</td>
     * <td>Creditor's Balance</td>
     * <td>creditorbalance</td>
     * </tr>
     * <tr>
     * <td>MESSAGE</td>
     * <td>String</td>
     * <td>Server message</td>
     * <td>info</td>
     * </tr>
     * </table>
     * 
     * @throws ActionHandlerException
     */
    public void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(CreditorApi.CLIENT_DATA_CREDITOR, this.cred);
	this.request.setAttribute(DisbursementsConst.CLIENT_DATA_CREDEXTT, this.credExt);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.creditors);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, this.credDetail);
	this.request.setAttribute(BasicGLApi.CLIENT_BUSTYPES, this.busTypes);
	this.request.setAttribute(BasicGLApi.CLIENT_BUSSERVTYPES, this.busServTypes);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	this.request.setAttribute(BasicGLApi.CLIENT_CREDITORBAL, this.balance);
    }

    /**
     * Retrieves a single instance of creditor's detail data and its associated 
     * address from the database using the creditor's internal id. 
     * 
     * @param creditorId The creditor's internal id which is generally the primary key.
     * @throws ActionHandlerException
     */
    protected void fetchCreditor(int creditorId) throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorHelper helper = CreditorFactory.createCreditorHelper(this.request, this.response, (DatabaseConnectionBean) tx.getConnector());
	try {
	    if (helper == null) {
		return;
	    }
	    helper.fetchCreditor(creditorId);
	    this.cred = helper.getCred();
	    this.credDetail = helper.getCredDetail();
	    this.busTypes = helper.getBusTypes();
	    this.busServTypes = helper.getBusServTypes();
	    this.balance = helper.getBalance();
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    helper.close();
	    helper = null;
	}
    }

    /**
     * Obtains the creditor balance.
     * 
     * @param id
     *          The creditor Id
     * @param conBean
     *          The database connection
     * @return Double
     *          Creditor's balance
     */
    protected Double getBalance(int id, DatabaseConnectionBean conBean) {
	CreditorApi api = CreditorFactory.createApi(conBean, this.request);
	// Get Customer Balance
	Double balance = null;
	try {
	    balance = new Double(api.findCreditorBalance(id));
	}
	catch (CreditorException e) {
	    logger.log(Level.INFO, "Customer balance could not be obtained...defaulting to zero.");
	    balance = new Double(0);
	}
	finally {
	    api = null;
	}
	return balance;
    }

    /**
     * Create the data entities needed to represent a new creditor. 
     */
    public void add() throws ActionHandlerException {
	// Create query to where creditor is not found  
	int creditorId = -1;
	this.fetchCreditor(creditorId);
    }

    /**
     * Fetches data pertaining to the creditor and its contact profile.  The contact profile 
     * consists of business and address information.
     */
    public void edit() throws ActionHandlerException {
	// Create query to where creditor is not found in the event creditor object is null
	int creditorId = -1;
	if (this.cred != null) {
	    creditorId = ((Creditor) this.cred).getCreditorId();
	}
	this.fetchCreditor(creditorId);
	return;
    }

    /**
     * Applies creditor updates to the database.  
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi api = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	// Apply Creditor Updates to database
	try {
	    api.maintainCreditor((Creditor) this.cred, null);
	    tx.commitUOW();
	}
	catch (CreditorException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * No Action
     */
    public void delete() throws ActionHandlerException {

    }

    /**
     * @return the cred
     */
    public Object getCred() {
	return cred;
    }

}