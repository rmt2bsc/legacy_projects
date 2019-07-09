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

import com.bean.GlAccounts;
import com.bean.VwAccount;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.gl.BasicGLApi;
import com.gl.GLException;
import com.gl.GeneralLedgerFactory;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the General Ledger Account Maintenance page.  The following request types are 
 * serviced: save , delete an application, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class AccountEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "Account.Edit.save";

    private static final String COMMAND_DELETE = "Account.Edit.delete";

    private static final String COMMAND_BACK = "Account.Edit.back";

    private static Logger logger = Logger.getLogger("AccountEditAction");

//    private BasicGLApi api;

    private Object acct;

    private Object catg;

    private List acctList;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AccountEditAction() throws SystemException {
	super();
    }

    /**
     * Performs post initialization and sets up a BasciGLApi reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's requests to save changes made to a GL Account, 
     * delete a GL Account, and to navigate back to the GL Account List page.
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
	if (command.equalsIgnoreCase(AccountEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(AccountEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(AccountEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Gathers data from the user's request and packages the GL Account data into a
     * data object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	this.acct = GeneralLedgerFactory.createGlAccount();
	try {
	    GeneralLedgerFactory.packageBean(this.request, this.acct);
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Sends GL Account data in various forms to the client as data objects and any 
     * server messages via the request object.  A single account record, a single 
     * account category record, a list of accounts, and any server messages are placed 
     * onto the request as {@link GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}, 
     * <i>acctCatg</i>, {@link GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}, and 
     * {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO}, respectively.  
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.acct);
	this.request.setAttribute("acctCatg", this.catg);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctList);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Creates a new or modifies an exising GlAccount record by persiting changes to the 
     * database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainAccount((GlAccounts) this.acct);
	    tx.commitUOW();
	    this.refreshPage((DatabaseConnectionBean) tx.getConnector());
	    this.msg = "GL account saved successfully";
	    return;
	}
	catch (GLException e) {
	    tx.rollbackUOW();
	    this.msg = "Error occurred saving GL Account data";
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Delete an general ledger account from the database using the account id.
     * 
     * @param appId  The id of the account
     * @return Total number of rows deleted.
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.refreshPage((DatabaseConnectionBean) tx.getConnector());
	try {
	    api.deleteAccount(((VwAccount) this.acct).getId());
	    tx.commitUOW();
	    this.msg = "GL Account was deleted successfully";
	    return;
	}
	catch (GLException e) {
	    tx.rollbackUOW();
	    this.msg = "Error occurred deleting  GL Accoung";
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Navigates the user to the general ledger account list page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	this.receiveClientData();
	this.getAccounts();
	this.sendClientData();
	this.msg = null;
    }

    /**
     * Retrieves the current account record in extended form after an insert or an 
     * update operation.
     * 
     * @param conBean
     *          The database connection
     * @throws ActionHandlerException
     */
    private void refreshPage(DatabaseConnectionBean conBean) throws ActionHandlerException {
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi(conBean, this.request);
	int id = 0;
	try {
	    // Get account view object.
	    id = ((GlAccounts) this.acct).getAcctId();
	    this.acct = api.findByIdExt(id);
	}
	catch (GLException e) {
	    this.msg = "Error occurred retrieving data to refresh GL account, " + id;
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    // Do not close api since its connection object is shared with the caller
	    api = null;
	}
    }

    /**
     * Obtains a list of {@link com.bean.VwAccount VwAccount} objects by category id 
     * which will be presented on the Account list page when the <i>Back</i> button 
     * is clicked.
     * 
     * @throws ActionHandlerException
     */
    private void getAccounts() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int catgId = ((GlAccounts) this.acct).getAcctCatgId();
	try {
	    this.catg = api.findAcctCatgById(catgId);
	    // Get all GL Accounts belonging to a given category.
	    this.acctList = api.findByAcctCatgExt(catgId);
	    if (this.acctList == null) {
		this.acctList = new ArrayList();
	    }
	}
	catch (GLException e) {
	    this.msg = "Error occurred retrieving a list of GL Accounts";
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * No Action
     */
    public void edit() throws ActionHandlerException {
	return;
    }

    /**
     * No Action
     */
    public void add() throws ActionHandlerException {
	return;
    }

}