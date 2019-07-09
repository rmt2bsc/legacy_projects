package com.xact;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.bean.VwXactList;
import com.bean.Xact;
import com.bean.XactType;
import com.bean.XactCategory;
import com.bean.RMT2TagQueryBean;
import com.bean.XactTypeItemActivity;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.SystemException;

/**
 * This abastract class provides functionality for action handlers created to serve basic 
 * sales on account, cash receipts, cash disbursements, and purchases transaction requests.
 * 
 * @author Roy Terrell
 *
 */
public abstract class AbstractXactAction extends AbstractActionHandler implements ICommand {

    private boolean newXact = true;

    /** Used to control the commiting of transaction at the ancestor level */
    private boolean okToCommit = false;

    private Logger logger;

    protected Object genericXact;

    /** Transaction object varible */
    protected Xact xact;

    /** Transaction type object variable */
    protected XactType xactType;

    /** Transaction Category object variable */
    protected XactCategory xactCategory;

    /** Transaction items collection variable */
    protected List xactItems;

    /** Transaction item type collection variable */
    protected List xactItemTypes;

    /** Helper object used to obtain data from the client's request object */
    protected HttpXactHelper httpHelper;

    /**
     * Default constructor
     *
     */
    public AbstractXactAction() {
	super();
	logger = Logger.getLogger("AbstractXactAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public AbstractXactAction(Context _context, Request _request) throws SystemException, DatabaseException {
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

	// Create base transaction API and Http trnasacction helpter objects.
	try {
	    this.httpHelper = new HttpXactHelper(this.context, this.request);
	    this.httpHelper.setSelectedRow(this.selectedRow);
	}
	catch (DatabaseException e) {
	    throw new SystemException(e);
	}

	this.xact = null;
	this.xactType = null;
	this.xactCategory = null;
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
    }

    /**
     * Descendent should override for an add operation.
     */
    public void add() throws ActionHandlerException {
	return;
    }

    /**
     * Descendent should override for an edit operation.
     */
    public void edit() throws ActionHandlerException {
	return;
    }

    /**
     * Descendent should override for a save operation.
     */
    public void save() throws ActionHandlerException {
	return;
    }

    /**
     * Descendent should override for a delete operation.
     */
    public void delete() throws ActionHandlerException {
	return;
    }

    /**
     * Drives the process of obtaining base transaction data using basic data 
     * gathered from the client's request.  After invoking this method, use 
     * the results of this method call to initialize the transaction, transaction 
     * type, transction category, and transaction detail items objects with the 
     * results.
     * 
     * @return Transaction object.
     * @throws XactException
     */
    protected void initBaseXactData() {
	try {
	    this.httpHelper.getHttpCombined();
	    this.xact = this.httpHelper.getXact();
	    this.genericXact = this.xact;
	    this.xactType = this.httpHelper.getXactType();
	    this.xactCategory = this.httpHelper.getXactCategory();
	    this.xactItems = (ArrayList) this.httpHelper.getXactItems();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, " A general error occurred when obtaining transaction base data");
	}
    }

    /**
     * Gathers the base transaction data objects from the client's request.   <p>If transaction is required to be fetch from an 
     * external data source, be sure to set the internal transaction id via a call to setHttpXactId() method at the level of the 
     * decendent before calling this method.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	this.initBaseXactData();
    }

    /**
     * Sends base transaction data to the client via the HttpServletRequest object .   Base transacation data 
     * is considered to be {@link Xact}, {@link XactType}, {@link XactCategory}, and ArrayList of 
     * {@link XactTypeItemActivity} objects.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.genericXact);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTTYPE, this.xactType);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTCATG, this.xactCategory);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMS, this.xactItems);
    }

    /**
     * Creates a new query bean object and adds to the session object which indicates a new query session has begun.
     *
     */
    public void newXactSearch() {
	RMT2TagQueryBean query = RMT2TagQueryBean.getInstance();
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
    }

    /**
     * Updates a transaction with data obtained from the database pertaining to the same transaction id.
     * The Reason property is the only property that is ignored in this ooperation.
     * 
     * @param xact The transaction object to be synchronized.
     * @throws ActionHandlerException
     * @throws DatabaseException
     */
    protected void syncXact(Xact xact) throws ActionHandlerException, DatabaseException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi api = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	Xact temp = null;
	try {
	    temp = api.findXactById(xact.getXactId());
	    this.xact.setEntityRefNo(temp.getEntityRefNo());
	    this.xact.setXactAmount(temp.getXactAmount());
	    this.xact.setXactDate(temp.getXactDate());
	    this.xact.setXactSubtypeId(temp.getXactSubtypeId());
	    this.xact.setXactTypeId(temp.getXactTypeId());
	    this.xact.setTenderId(temp.getTenderId());
	}
	catch (XactException e) {
	    this.msg = "SystemException - " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Default functionality for creating the datasource object, {@link XactTypeItemAcivity}.  This method is generally invoked 
     * by the method, <b>getXactDetails()</b>, to store data mapped to the client's transaction items.  This method can be 
     * overriden to create an object other than <b>XactTypeItemAcivity</b> for mapping transaction item data.
     * 
     * @return By default, returns {@link XactTypeItemAcivity} cast as type Object.
     */
    protected Object createNewXactItemObject() {
	XactTypeItemActivity xtia = null;
	xtia = XactFactory.createXactTypeItemActivity();
	return xtia;
    }

    /**
     * Gets the indicator that determines whether or not it is okay to commit a transaction.
     * 
     * @return true when it is okay to commit.   Otherwise, false is returned.
     */
    public boolean isOkToCommit() {
	return this.okToCommit;
    }

    /**
     * Gets the indicator that determines whether or not this transaction is new. 
     * @return
     */
    public boolean isNewXact() {
	return this.newXact;
    }

    /**
     * Retrieves transaction data from the xact, xact_type, and xact_category tables and packages the combined 
     * data in {@link VwXactList}.  Only one row at a time is obtained using _xactId.
     * 
     * @param xactId
     * @return {@link VwXactList}
     */
    protected VwXactList fetchXactExt(int xactId) {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	VwXactList xactVw = null;
	Object results[];
	DaoApi dao = DataSourceFactory.createDao((DatabaseConnectionBean) tx.getConnector());

	try {
	    xactVw = new VwXactList();
	    xactVw.addCriteria("Id", xactId);
	    results = dao.retrieve(xactVw);
	    if (results.length > 0) {
		xactVw = (VwXactList) results[0];
	    }
	    return xactVw;
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    return xactVw;
	}
	finally {
	    dao.close();
	    tx.close();
	    dao = null;
	    tx = null;
	}
    }

    /**
     * Fetches the transaction activity items related to xactId.
     * 
     * @param xactId Transaction id.
     * @return ArrayList of one or more {@linkVwXactTypeItemActivity} obejcts
     * @throws ActionHandlerException
     */
    protected List fetchXactItems(int xactId) {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi api = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	List list = new ArrayList();
	try {
	    list = api.findVwXactTypeItemActivityByXactId(xactId);
	    return list;
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    return list;
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Fetches the transaction type object that is related to xactId.
     * 
     * @param xactTypeId Transaction type id
     * @return {@link XactType}
     */
    protected XactType fetchXactType(int xactTypeId) {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactType xactType = XactFactory.createXactType();
	Object results[];
	DaoApi dao = DataSourceFactory.createDao((DatabaseConnectionBean) tx.getConnector());

	try {
	    xactType.addCriteria("Id", xactTypeId);
	    results = dao.retrieve(xactType);
	    if (results.length > 0) {
		xactType = (XactType) results[0];
	    }
	    return xactType;
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    return xactType;
	}
	finally {
	    dao.close();
	    tx.close();
	    dao = null;
	    tx = null;
	}
    }

    /**
     * Obtains transaction data from the database using xactId as the key.  Member 
     * variables of the following types are populated with transaction data: 
     * {@link Xact}, {@link XactType}, ArrayList of {@link XactTypeItemActivity}
     * 
     * @param xactId
     */
    protected void refreshXact(int xactId) {
	this.genericXact = this.fetchXactExt(xactId);
	this.xactItems = this.fetchXactItems(xactId);
	this.xactType = this.fetchXactType(this.httpHelper.getXactType().getXactTypeId());
    }

    /**
     * Updates the current transaction object with data from the database.
     * 
     */
    protected void refreshXact() throws ActionHandlerException {
	if (this.xact == null || this.xact.getXactId() <= 0) {
	    return;
	}
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi api = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xact = api.findXactById(this.xact.getXactId());
	    this.genericXact = this.xact;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Gets the current transaction type object.
     * 
     */
    protected void refreshXactType() throws ActionHandlerException {
	if (this.xactType == null || this.xactType.getXactTypeId() <= 0) {
	    return;
	}
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi api = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xactType = api.findXactTypeById(this.xactType.getXactTypeId());
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Gets the current transactin category object.
     * 
     */
    protected void getXactCatgory() throws ActionHandlerException {
	if (this.xactCategory == null || this.xactCategory.getXactCatgId() <= 0) {
	    return;
	}
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi api = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xactCategory = api.findXactCatgById(this.xactCategory.getXactCatgId());
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Gets the List of transaction detail items.
     * 
     * @return List
     */
    public List getXactItems() {
	return this.xactItems;
    }

    /**
     * Gets the List of Transaction item types.
     * 
     * @return List
     */
    public List getXactItemTypes() {
	return this.xactItemTypes;
    }

}