package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;

import com.action.accounting.creditor.CreditorAction;

import com.api.CashDisbursementsApi;
import com.api.db.DatabaseException;

import com.bean.Creditor;
import com.constants.GeneralConst;

import com.factory.CashDisburseFactory;
import com.factory.XactFactory;

import com.util.SystemException;

/**
 * This class provides common functionality needed to serve Creditor/Vendor cash 
 * disbursement request that involves combined transaction and creditor data from 
 * the user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorXactCommon extends AbstractXactAction {
	protected  static final String COMMAND_BACK = "XactDisburse.DisbursementCreditorXactView.back";
	private Logger logger;
	protected Creditor creditor;
	protected CreditorAction credAction;
	protected List trans;
	protected CashDisbursementsApi disbApi;

	/**
	 * Default constructor
	 * 
	 */
	public DisbursementsCreditorXactCommon() {
		super();
		logger = Logger.getLogger("DisbursementsCreditorXactCommon");
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
	public DisbursementsCreditorXactCommon(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		super(_context, _request);
		this.init(this.context, this.request);
	}

	/**
	 * Initializes this object using _conext and _request. This is needed in the
	 * event this object is inistantiated using the default constructor.
	 * 
	 * @throws SystemException
	 */
	protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
		super.init(_context, _request);
		try {
			this.credAction = new CreditorAction(_context, _request);
			this.baseApi = XactFactory.create(this.dbConn, _request);
            this.disbApi =  CashDisburseFactory.createApi(this.dbConn, _request);
		} 
		catch (Exception e) {
		    logger.log(Level.ERROR, e.getMessage());
			throw new SystemException(e.getMessage());
		}
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
	public void processRequest(HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
		super.processRequest(request, response, command);
	}


	/**
	 * Returns the user to the Cash Disbursement Transaction List page.
	 * 
	 * @throws ActionHandlerException
	 */
    protected void doBack() throws ActionHandlerException {
        this.receiveClientData();
        this.sendClientData();
    }
    
    /**
     * Obtains key transaction and creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
        super.receiveClientData();
        this.refreshXact();
        this.credAction.receiveClientData();
        this.credAction.fetchCreditor();
        this.creditor = this.credAction.getCreditor();
    }
    
    /**
     * Sends transaction data and creditor related data to the client.
     */
    public void sendClientData() throws ActionHandlerException {
        super.sendClientData();
        this.credAction.sendClientData();
        // Get Creditor transactions    
        this.creditor = this.credAction.getCreditor();
        this.trans = this.getCreditorTransactions(this.creditor.getId());
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.trans);
    }
    
    protected List getCreditorTransactions(int creditorId) throws ActionHandlerException {
        List list = new ArrayList();
        try {
            list = this.baseApi.findCreditorXactHist(creditorId);
            return list;
        }
        catch (Exception e) {
            throw new ActionHandlerException(e.getMessage());
        } 
    }
}