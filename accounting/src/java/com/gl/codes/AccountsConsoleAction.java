package com.gl.codes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;
import com.bean.GlAccounts;
import com.bean.VwAccount;
import com.bean.VwCategory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.gl.BasicGLApi;
import com.gl.GLException;
import com.gl.GeneralLedgerFactory;

import com.util.RMT2Money;
import com.util.RMT2Utility;
import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the account console page.  The following request types are serviced: 
 * refresh, category list, account list, account edit, account add, account 
 * delete, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class AccountsConsoleAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ACCT_REFRESH = "AccountsConsole.Accounts.refresh";

    private static final String COMMAND_ACCT_CATGLIST = "AccountsConsole.Accounts.catglist";

    private static final String COMMAND_ACCT_LIST = "AccountsConsole.Accounts.list";

    private static final String COMMAND_ACCT_EDIT = "AccountsConsole.Accounts.edit";

    private static final String COMMAND_ACCT_ADD = "AccountsConsole.Accounts.add";

    private static final String COMMAND_DELETE = "App.Edit.delete";

    private static final String COMMAND_BACK = "App.Edit.back";

    private Logger logger;

    private Object acct;

    private GlAccountTypes acctType;

    private GlAccountCategory catg;

    private List catgList;

    private List acctList;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AccountsConsoleAction() throws SystemException {
	super();
	logger = Logger.getLogger("AccountsConsoleAction");
    }

    /**
     * Performs post initialization and sets up an BasicGLApi reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's requests to manage the General Ledger Account Console: 
     * refresh, list categories, list accounts by category, edit account, add account 
     * delete and return back to the main menu.
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
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_REFRESH)) {
	    this.refreshConsole();
	}
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_CATGLIST)) {
	    this.listCategories();
	}
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_LIST)) {
	    this.listAccounts();
	}
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Gathers data from the user's request and packages the data into the appropriate data 
     * object (account type, account category, or account).
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	String rowStr = this.request.getParameter("selCbx");
	int rowNdx = 0;

	//  Client must select a row to edit.
	if (rowStr == null) {
	    this.msg = "Client must select a row to edit";
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	try {
	    //  Get index of the row that is to be processed from the HttpServeltRequest object
	    rowNdx = RMT2Money.stringToNumber(rowStr).intValue();

	    // Retrieve selected account type
	    if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_CATGLIST)) {
		this.acctType = GeneralLedgerFactory.createAcctType();
		GeneralLedgerFactory.packageBean(this.request, this.acctType, rowNdx);
	    }

	    //  Retrieve Selected Catgeory
	    if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_LIST)) {
		this.catg = GeneralLedgerFactory.createCatg();
		GeneralLedgerFactory.packageBean(this.request, this.catg, rowNdx);
	    }

	    // Retrieve account record for edit.
	    if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_EDIT)) {
		this.acct = GeneralLedgerFactory.createGlAccount();
		GeneralLedgerFactory.packageBean(this.request, this.acct, rowNdx);
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Sends GL Account data from the GL Account console page in various forms to the client 
     * as data objects and any server messages via the request object.  A single account type 
     * record, a single account category record, a single account record, a list of accounts 
     * by category, a list of account categories by account type, and any server messages as  
     * <i>acctType</i>, <i>acctCatg</i>, {@link GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}, 
     * {@link GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}, <i>catgList</i>, and 
     * {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO}, respectively.  
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute("acctType", this.acctType);
	this.request.setAttribute("acctCatg", this.catg);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.acct);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctList);
	this.request.setAttribute("catgList", this.catgList);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Prepares the page for first time presentation by creating a new 
     * {@link com.bean.GlAccountCategory GlAccountCategory}.
     * 
     * @throws ActionHandlerException
     */
    private void refreshConsole() throws ActionHandlerException {
	this.catg = GeneralLedgerFactory.createCatg();
	this.sendClientData();
    }

    /**
     * Uses account type id from the user's request to obtain a list of GL Account 
     * Categories that will be* displayed on the account edit page when an account
     * type is selected. 
     *
     * @throws ActionHandlerException
     */
    private void listCategories() throws ActionHandlerException {
	this.receiveClientData();
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);

	// Using account type id, get account type object and a list of account 
	// type categories and send results to the client via the request object
	try {
	    // Get all GL Account Categories belonging to account type id.
	    this.catgList = api.findAcctCatgByAcctType(this.acctType.getAcctTypeId());
	    if (this.catgList == null) {
		this.catgList = new ArrayList();
	    }
	}
	catch (GLException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}

	// Send data to client
	this.sendClientData();
    }

    /**
     * Uses data from the client's request to retrieve a list of accounts by category id.
     * This data is used to display the list of accounts when a category is selected.
     * 
     * @throws ActionHandlerException
     */
    private void listAccounts() throws ActionHandlerException {
	this.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);

	// Using category id, get list of accounts and send results 
	// to the client via the request object
	try {
	    // Get all GL Accounts belonging to a given category.
	    this.acctList = api.findByAcctCatgExt(this.catg.getAcctCatgId());
	    if (this.acctList == null) {
		this.acctList = new ArrayList();
	    }
	}
	catch (GLException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}

	// Send data to client
	this.sendClientData();
    }

    /**
     * Prepares for a add operation by creating a new {@link com.bean.VwAccount VwAccount} 
     * and populating it with {@link com.bean.VwCategory VwCategory} data obtained from the 
     * database using category id.  This object will be displayed on GL Account Category 
     * Maintenance page for editing.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int catgId = Integer.parseInt(this.request.getParameter("masterAcctCatgId"));
	try {
	    VwCategory catg = (VwCategory) api.findAcctCatgByIdExt(catgId);
	    VwAccount vwAcct = GeneralLedgerFactory.createVwAccount();
	    vwAcct.setAcctTypeId(catg.getAccttypeid());
	    vwAcct.setAcctCatId(catg.getAcctcatid());
	    vwAcct.setAccttypedescr(catg.getAccttypedescr());
	    vwAcct.setAcctcatgdescr(catg.getAcctcatgdescr());
	    this.acct = vwAcct;
	    return;
	}
	catch (GLException e) {
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
     * Obtains a single GL Account record to be displayed on the GL Account 
     * Maintenance page for editing.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get account view object.
	    int id = ((GlAccounts) this.acct).getAcctId();
	    this.acct = api.findByIdExt(id);
	}
	catch (GLException e) {
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
     * Navigates the user to the General Ledger main menu.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	try {
	    // Retrieve all application records from the database using unique id.
	    //this.data = this.api.getAll();
	    this.sendClientData();
	    this.request.removeAttribute(RMT2ServletConst.REQUEST_MSG_INFO);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * No Action
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	return;
    }

    /**
     * No Action
     * 
     * @throws ApplicationException
     */
    public void delete() throws ActionHandlerException {
	return;
    }

}