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
import com.bean.VwCategory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.gl.BasicGLApi;
import com.gl.GLException;
import com.gl.GeneralLedgerFactory;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the GL Account Category edit page.  The following request types are 
 * serviced: save, delete, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class CatgEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "AcctCategory.Edit.save";

    private static final String COMMAND_DELETE = "AcctCategory.Edit.delete";

    private static final String COMMAND_BACK = "AcctCategory.Edit.back";

    private Logger logger;

    private Object catg;

    private Object acctType;

    private List acctCatgList;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public CatgEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("CatgEditAction");
    }

    /**
     * Performs post initialization and sets up an BasciGLApi reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's requests to save changes made to a GL Account Category, 
     * delete a GL Account Category, and to navigate back to the GL Account Category 
     * List page.
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
	if (command.equalsIgnoreCase(CatgEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(CatgEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(CatgEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Gathers data from the user's request and packages the data into a single
     * GL Account Category object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	this.catg = GeneralLedgerFactory.createCatg();
	try {
	    GeneralLedgerFactory.packageBean(this.request, this.catg);
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Sends GL Account Category data in various forms to the client as data objects and any 
     * server messages via the request object.  A single account category record, a list of 
     * account category object, and any server messages are placed onto the request as 
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
     * Creates a new or modifies an exising GL Account Category record by persiting 
     * changes to the database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainAcctCatg((GlAccountCategory) this.catg);
	    tx.commitUOW();
	    this.refreshPage((DatabaseConnectionBean) tx.getConnector());
	    this.msg = "GL account category saved successfully";
	    return;
	}
	catch (GLException e) {
	    tx.rollbackUOW();
	    this.msg = "Error: " + e.getMessage();
	    this.logger.log(Level.ERROR, this.msg);
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
     * Delete a GL Account Category record from the database using the id of the category.
     * 
     * @param appId  The id of the application
     * @return Total number of rows deleted.
     * @throws ApplicationException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.refreshPage((DatabaseConnectionBean) tx.getConnector());
	try {
	    api.deleteAcctCatg(((VwCategory) this.catg).getAcctcatid());
	    tx.commitUOW();
	    this.msg = "Account category was deleted successfully";
	    return;
	}
	catch (GLException e) {
	    tx.rollbackUOW();
	    this.msg = "Error: " + e.getMessage();
	    this.logger.log(Level.ERROR, this.msg);
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
     * Navigates the user to the Account Category List page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	this.receiveClientData();
	this.getCategories();
	this.sendClientData();
	this.msg = null;
    }

    /**
     * Retrieves the current account category record in extended form after an insert or an 
     * update operation.
     * 
     * @throws ActionHandlerException
     */
    private void refreshPage(DatabaseConnectionBean conBean) throws ActionHandlerException {
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi(conBean, this.request);
	try {
	    // Get Recently updated Account Category record.
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
	    // Do not close api since its connection object is shared with the caller.
	    api = null;
	}
    }

    /**
     * Obtains a list of {@link com.bean.GlAccountCategory GlAccountCategory} objects 
     * by account type id which will be presented on the Account Category list page 
     * when the <i>Back</i> button is clicked.
     * 
     * @throws ActionHandlerException
     */
    private void getCategories() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BasicGLApi api = GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get Acount TYpe object
	    int acctTypeId = ((GlAccountCategory) this.catg).getAcctTypeId();
	    this.acctType = api.findAcctTypeById(acctTypeId);

	    // Get all GL Account Categories belonging to account type id.
	    this.acctCatgList = api.findAcctCatgByAcctType(acctTypeId);
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