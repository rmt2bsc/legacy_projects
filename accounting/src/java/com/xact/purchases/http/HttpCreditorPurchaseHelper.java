package com.xact.purchases.http;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.VwXactList;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorException;
import com.gl.creditor.CreditorExt;
import com.gl.creditor.CreditorFactory;

import com.util.NotFoundException;
import com.util.SystemException;

import com.xact.HttpXactHelper;
import com.xact.XactConst;
import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

/**
 * This abstract class provides functionality obtaining basic transacction related data 
 * from the client's request object
 * 
 * @author Roy Terrell
 *
 */
public class HttpCreditorPurchaseHelper extends HttpXactHelper {

    private VwXactList xactExt;

    private CreditorExt creditor;

    private Logger logger;

    /**
     * Default constructor
     *
     */
    public HttpCreditorPurchaseHelper() {
	super();
	logger = Logger.getLogger("HttpCreditorPurchaseHelper");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public HttpCreditorPurchaseHelper(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	logger = Logger.getLogger(HttpCreditorPurchaseHelper.class);
    }

    /**
     * Initializes this object using _conext and request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
    }

    /**
     * Combines the process of probing the client's HttpServletRequest object for basic transaction 
     * data (transaction base, transaction type, transaction category, and transaction detail items).    
     * After invoking this method, be sure to call the methods, getXact, getXactType, getXactCatg, 
     * and getXactItems to obtain the results.  
     * 
     * @return Transaction object.
     * @throws XactException
     */
    public void getHttpCombined() throws SystemException {
	super.getHttpCombined();
	try {
	    this.xactExt = this.getHttpXactExt();
	    this.creditor = this.getHttpCreditorBusiness();
	}
	catch (Exception e) {
	    throw new SystemException(e.getMessage());
	}
	return;
    }

    /**
     * Attempts to obtain extended transaction data from the client JSP into a transaction object.
     *  
     * @return {@link VwXactList}- Contains the transaction data retrieved from the client or a 
     *  newly initialized VwXactList object when transaction is not found.
     * @throws SalesOrderException
     */
    public VwXactList getHttpXactExt() throws XactException {
	VwXactList xact = XactFactory.createXactView();
	String temp = null;
	String subMsg = null;
	int xactId = 0;
	boolean listPage = true;

	// Determine if we are coming from a page that presents data as a list of orders or as single order.
	// Get selected row number from client page.  If this row number exist, then we 
	// are coming from page that contains a list of orders.
	subMsg = "Transaction number could not be obtained from list of Credit charge orders";
	temp = this.request.getParameter("XactId" + this.getSelectedRow());
	if (temp == null) {
	    listPage = false;
	}
	if (!listPage) {
	    // Get order id for single row
	    temp = this.request.getParameter("XactId");
	}
	try {
	    xactId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.INFO, subMsg);
	    this.xactExt = xact;
	    return xact;
	}

	// Get Transaction object
	try {
	    XactFactory.packageBean(this.request, xact);
	    xact.setId(xactId);
	    this.xactExt = xact;
	    return xact;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new XactException(e);
	}
    }

    /**
     * This method is responsible for gathering creditor data from the client's request 
     * object.
     * <p>
     * In order to use this method, the client is required to provide the creditor id 
     * identified as "Id" or "CreditorId".   Otherwise, an SalesOrderException is thrown. 
     * The processed data will be returned to the client via the HttpServletRequest object 
     * and can be identified as follows:
     * <p>
     * <blockquote>
     * "customer" - {@link CreditorExt}
     * "balance" - Double object representing the customer's current balance.
     * </blockquote>
     * 
     * @return {@link CreditorExt}
     * @throws XactException
     */
    public CreditorExt getHttpCreditorBusiness() throws XactException {
	String credIdStr = null;
	String msg = null;
	int credId = 0;
	boolean listData = true;
	CreditorExt cb = null;

	try {
	    // Obtain Creditor data from the request object.
	    try {
		// Get selected row number from client page.  If this row number exist, then we 
		// are coming from page that contains a list of orders.
		credIdStr = this.request.getParameter("CreditorId" + this.getSelectedRow());
		if (credIdStr == null) {
		    listData = false;
		}
		else {
		    credId = Integer.parseInt(credIdStr);
		}
	    }
	    catch (NumberFormatException e) {
		msg = "Creditor was not selected";
		logger.log(Level.ERROR, msg);
		throw new NotFoundException(msg);
	    }

	    try {
		if (!listData) {
		    credIdStr = this.request.getParameter("Id");
		    if (credIdStr == null) {
			credIdStr = this.request.getParameter("CreditorId");
		    }
		    credId = Integer.parseInt(credIdStr);
		}
	    }
	    catch (NumberFormatException e) {
		msg = "Creditor was not selected";
		logger.log(Level.ERROR, msg);
		throw new NotFoundException(msg);
	    }

	    DatabaseTransApi tx = DatabaseTransFactory.create();
	    CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	    try {
	    	cb = (CreditorExt) credApi.findCreditorBusiness(credId);	
	    }
	    catch (Exception e) {
	    	logger.log(Level.ERROR, e.getMessage());	
	    }
	    finally {
	    	credApi = null;
	    	tx.close();
	    	tx = null;
	    }
	    return cb;
	} // end try
	catch (Exception e) {
	    logger.log(Level.ERROR, "GLAcctException occurred. " + e.getMessage());
	    throw new XactException(e);
	}
    }

    /**
     * This method retrieves credit purchase order data from an external datasource.  It is 
     * assumed that the transaction object and the creditor object are valid and accounted 
     * for.  Otherwise, this operation bypasses the retrieval.
     * 
     * @throws ActionHandlerException General database error.
     */
    public void retrieveCreditOrder() throws ActionHandlerException {
	if (this.xactExt != null && this.creditor != null) {
	    this.retrieveCreditOrder(this.xactExt.getId(), this.creditor.getCreditorId());
	}
	return;
    }

    /**
     * Uses xactId and creditorId to retrieve credit purchase order data from the 
     * database.   After the data is retrieved, this method packages the data into 
     * various objects which are sent to the client via HttpServletRequest object for 
     * presentation.
     * 
     * @param xactId The id of the target transactin of credit order.
     * @param creditorId The id of the target creditor
     * @throws ActionHandlerException
     */
    public void retrieveCreditOrder(int xactId, int creditorId) throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	
	try {
	    // Get vendor data.  Expect to receive a vendor id at all times since purchase orders created per vendor.
	    this.creditor = (CreditorExt) credApi.findCreditorBusiness(creditorId);
	}
	catch (CreditorException e) {
	    this.creditor = new CreditorExt();
	}
	
	try {
	    // Refresh existing transaction objects from the database only.
	    if (this.xact != null && this.xact.getXactId() > 0) {
		this.xact = xactApi.findXactById(xactId);
		this.xactExt = xactApi.findXactListViewByXactId(xactId);
		// Get Transaction Items
		this.xactItems = xactApi.findVwXactTypeItemActivityByXactId(xactId);
	    }
	    if (this.xact == null) {
		// This might be a new credit purchase order request, so ensure that all objects 
		// are created and initialized to prevent null exceptions on the client.
		this.createNewCreditPurchase();
		return;
	    }
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    xactApi.close();
	    credApi.close();
	    xactApi = null;
	    credApi = null;
	}
	return;
    }

    /**
     * 
     * @throws ActionHandlerException
     */
    private void createNewCreditPurchase() throws ActionHandlerException {
	this.xact = XactFactory.createXact();
	this.xactExt = XactFactory.createXactView();
	this.xactItems = new ArrayList();
	this.xactType = XactFactory.createXactType();
	this.xactType.setXactTypeId(XactConst.XACT_TYPE_CREDITCHARGE);
	this.xactCategory = XactFactory.createXactCategory();
	return;
    }

    /**
     * Gets transaction object.
     * 
     * @return
     */
    public VwXactList getXactExt() {
	return this.xactExt;
    }

    /**
     * @return the creditor
     */
    public CreditorExt getCreditor() {
	return creditor;
    }

}