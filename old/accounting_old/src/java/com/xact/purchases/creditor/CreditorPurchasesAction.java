package com.xact.purchases.creditor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.bean.bindings.JaxbAccountingFactory;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorApi;

import com.util.SystemException;

import com.xact.AbstractXactAction;
import com.xact.XactConst;
import com.xact.disbursements.CashDisbursementsApi;
import com.xact.purchases.http.HttpCreditorPurchaseHelper;

/**
 * Class that manages transactions and their related General Ledger accounts
 *
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesAction extends AbstractXactAction {
    private Logger logger;

    protected HttpCreditorPurchaseHelper httpXactHelper;

    protected CreditorApi credApi;

    protected CashDisbursementsApi ccApi;

    protected Hashtable errors = new Hashtable();
    
    protected static Object CREDITOR_LIST;
    
    protected double resultTotal;
    
    protected int resultCount;

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    public CreditorPurchasesAction() throws SystemException {
	logger = Logger.getLogger("CreditorPurchasesAction");
    }

    /**
     * Main contructor for this action handler.
     *
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CreditorPurchasesAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
	try {
	    this.httpXactHelper = new HttpCreditorPurchaseHelper(context, request);
	    this.httpXactHelper.setSelectedRow(this.selectedRow);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e.getMessage());
	}
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
     * Retrieves creditor/vendor purchase order data from the client's request.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
        // Get creditor list from the session
        CreditorPurchasesAction.CREDITOR_LIST = this.request.getSession().getAttribute(CreditorPurchasesConst.CLIENT_DATA_CREDIOTRLIST);
	try {
	    this.httpXactHelper.getHttpCombined();
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    /**
     * Set all pertinent purchase order data onto the request object which 
     * is transmitted to the client.
     * 
     * <p>
     * <p>
     * The following list dipicts the java objects and their names that are 
     * stored in the HttpServletRequest object on the client:
     * <table border="1">
     *   <tr>
     *     <th align="left"><strong>Java Data Object</strong></th>
     *     <th><strong>Id on client</strong></th>
     *   </tr>
     *   <tr>
     *     <td>{@link PurchaseOrder}</td>
     *     <td>po</td>
     *   </tr>
     *   <tr>
     *     <td>ArrayList of {@link PurchaseOrderItems}</td>
     *     <td>poItems</td>
     *   </tr>
     *   <tr>
     *     <td>{@link PurchaseOrderStatus}</td>
     *     <td>pos</td>
     *   </tr>
     *   <tr>
     *     <td>{@link PurchaseOrderStatusHist}</td>
     *     <td>posh</td>
     *   </tr>
     *   <tr>
     *     <td>{@link CreditorExt}</td>
     *     <td>vendor</td>
     *   </tr>
     * </table>
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(CreditorApi.CLIENT_DATA_CREDITOR, this.httpXactHelper.getCreditor());
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.httpXactHelper.getXact());
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTEXT, this.httpXactHelper.getXactExt());
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTTYPE, this.httpXactHelper.getXactType());
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTCATG, this.httpXactHelper.getXactCategory());
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMS, this.httpXactHelper.getXactItems());
	
	// Put the creditor list on the session for the purpose of improving performance for decendent classes. 
	this.request.getSession().setAttribute(CreditorPurchasesConst.CLIENT_DATA_CREDIOTRLIST, CreditorPurchasesAction.CREDITOR_LIST);
	this.request.setAttribute(XactConst.CLIENT_DATA_TOTAL, this.resultTotal);
	this.request.setAttribute(XactConst.CLIENT_DATA_COUNT, this.resultCount);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }
    
    /**
     * Retrieves a complete list of vendors as an XML document where creditor id equals 2.
     * Since the list of creditor are obtained via a very expensive web service call , a 
     * singleton approach is used to fetch the list of creditors for the sake of performance.
     * 
     * @return String
     *           XML document of one or more creditor records
     * @throws ActionHandlerException
     */
    protected void getCreditorList() throws ActionHandlerException {
        // fetch using a singleton approach.
        if (CreditorPurchasesAction.CREDITOR_LIST != null) {
            return;    
        }
	try {
	    JaxbAccountingFactory f = new JaxbAccountingFactory();
	    CreditorPurchasesAction.CREDITOR_LIST = f.getCreditorList(this.request);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

}