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
import com.bean.VwCategory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.gl.BasicGLApi;
import com.gl.GLException;
import com.gl.GeneralLedgerFactory;

import com.util.RMT2Utility;
import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the account category console page.  The following request types are 
 * serviced: refresh, list, edit, add, delete, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class CatgConsoleAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ACCTTYPECATG_REFRESH = "AcctCatgConsole.AcctTypeCatg.refresh";

    private static final String COMMAND_ACCTTYPECATG_LIST = "AcctCatgConsole.AcctTypeCatg.list";

    private static final String COMMAND_ACCTTYPECATG_EDIT = "AcctCatgConsole.AcctTypeCatg.edit";

    private static final String COMMAND_ACCTTYPECATG_ADD = "AcctCatgConsole.AcctTypeCatg.add";

    private static final String COMMAND_DELETE = "App.Edit.delete";

    private static final String COMMAND_BACK = "App.Edit.back";

    private Logger logger;

    private GlAccountTypes acctType;

    private Object catg;

    private List acctCatgList;

    private int acctTypeId;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public CatgConsoleAction() throws SystemException {
	super();
	logger = Logger.getLogger("CatgConsoleAction");
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
     * Processes the client's requests to manage the GL Account Category console page.
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
	if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_REFRESH)) {
	    this.refreshConsole();
	}
	if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_LIST)) {
	    this.listCategories();
	}
	if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Gathers data from the user's request and packages the data into the appropriate data 
     * object (account type or account category).
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	String rowStr = this.request.getParameter("selCbx");
	int rowNdx = 0;

	//  Client must select a row to edit.
	if (rowStr == null) {
	    throw new ActionHandlerException("Client must select a row to edit");
	}

	try {
	    //  Get index of the row that is to be processed from the HttpServeltRequest object
	    rowNdx = RMT2Utility.stringToNumber(rowStr).intValue();

	    //  Retrieve values from the request object into the User object.
	    if (this.command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_LIST)) {
		this.acctType = GeneralLedgerFactory.createAcctType();
		GeneralLedgerFactory.packageBean(this.request, this.acctType, rowNdx);
	    }

	    // Retrieve account category record for edit.
	    if (this.command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_EDIT)) {
		this.catg = GeneralLedgerFactory.createCatg();
		GeneralLedgerFactory.packageBean(this.request, this.catg, rowNdx);
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Sends GL Account Category data from the GL Account Category console page in 
     * various forms to the client as data objects and any server messages via the 
     * request object.  A single account type object, a single account category 
     * object, a list of account category objects, and any server messages as  
     * <i>acctType</i>, {@link GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}, 
     * {@link GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}, and 
     * {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO}, respectively.  
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute("acctType", this.acctType);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.catg);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctCatgList);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Prepares the page for first time presentation by creating a new 
     * {@link com.bean.GlAccountType GlAccountType} and an empty List of 
     * representing Account Categories.
     * 
     * @throws ActionHandlerException
     */
    private void refreshConsole() throws ActionHandlerException {
	this.acctType = GeneralLedgerFactory.createAcctType();
	this.acctCatgList = new ArrayList();
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
	    // Get account type record.
	    this.acctType = api.findAcctTypeById(this.acctType.getAcctTypeId());

	    // Get all GL Account Categories belonging to account type id.
	    this.acctCatgList = api.findAcctCatgByAcctType(this.acctType.getAcctTypeId());
	    if (this.acctCatgList == null) {
		this.acctCatgList = new ArrayList();
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
     * Prepares for a add operation by creating a new {@link com.bean.VwCategory VwCategory} 
     * and populating it with {@link com.bean.GlAccountTypes GlAccountTypes} data obtained 
     * from the database using account type id.  This object will be displayed on GL Account 
     * Category Maintenance page for editing.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	String strAcctTypId = this.request.getParameter("masterAcctTypeId");
	try {
	    this.acctTypeId = Integer.valueOf(strAcctTypId).intValue();
	}
	catch (NumberFormatException e) {
	    this.msg = "Problem identifying selected item from the GL account type list";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.catg = GeneralLedgerFactory.createVwCategory();
	try {
	    this.acctType = api.findAcctTypeById(this.acctTypeId);
	    ((VwCategory) this.catg).setAccttypeid(this.acctType.getAcctTypeId());
	    ((VwCategory) this.catg).setAccttypedescr(this.acctType.getDescription());
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
     * Obtains a single GL Account Category object fro the database using the category id 
     * from the client.  This object will be used to display an account category record on 
     * the GL Account Category Maintenance page for editing.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get all GL Account Categories belonging to account type id.
	    int acctCatgId = ((GlAccountCategory) this.catg).getAcctCatgId();
	    this.catg = api.findAcctCatgByIdExt(acctCatgId);
	    if (this.catg == null) {
		this.catg = GeneralLedgerFactory.createCatg();
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
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	return;
    }

    /**
     * No Action
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	return;
    }

}