package com.inventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.bindings.JaxbAccountingFactory;
import com.bean.criteria.CreditorCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;
import com.gl.BasicAccountingHelper;
import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorConst;
import com.gl.creditor.CreditorExt;
import com.gl.creditor.CreditorFactory;

import com.util.SystemException;

/**
 * This class provides action handlers to serve common requests pertaining to 
 * the item master module. 
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterAction extends AbstractActionHandler implements ICommand {
    protected HttpItemMasterHelper itemHelper;

//    protected BasicAccountingHelper acctHelper;
    
//    protected List vendorList;
    
    protected Object vendorList;

    private Logger logger;

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterAction() {
	this.logger = Logger.getLogger("ItemMasterAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with 
     *   this action handler
     * @throws SystemException
     */
    public ItemMasterAction(Context context, Request request) throws SystemException, DatabaseException {
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
	    this.itemHelper = new HttpItemMasterHelper(context, request);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e.getMessage());
	}
//	this.acctHelper = new BasicAccountingHelper();
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	try {
	    this.init(null, request);
	    this.init();
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Preapres the client for adding an item to inventory by retrieving data 
     * from the client's request. 
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.receiveClientData();
	return;
    }

    /**
     * Retrieves item master data from the client's request.  New Item Master View, and 
     * Item Master Status History objects are created and sent to the client.  The above 
     * objects are considered to be new when the property which represents the primary 
     * key is equal to zero.
     * <p>
     * The following objects are set on the request object identified as "item" and 
     * "itemhistory", respectively: {@link VwItemMaster} and {@link ItemMasterStatusHist}.  
     *
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    this.itemHelper.getHttpCombined();
	}
	catch (SystemException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    /**
     * Sends data to the client.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	// Set data on the request object to be used by client JSP.s
	this.request.setAttribute(ItemConst.CLIENT_DATA_ITEM, this.itemHelper.getItemExt());
	this.request.setAttribute(ItemConst.CLIENT_DATA_ITEMHIST, this.itemHelper.getImshList());
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
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
    public void save() throws ActionHandlerException {
	return;
    }

    /**
     * No Action
     */
    public void delete() throws ActionHandlerException {
	return;
    }

    /**
     * Retrieves a complete list of creditors as an XML document where creditor id equals 1.
     * 
     * @return String
     *          XML document of one or more vendor records
     * @throws ActionHandlerException
     */
    protected String getVendorList() throws ActionHandlerException {
	try {
	    JaxbAccountingFactory f = new JaxbAccountingFactory();
	    return f.getVendorList(this.request);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }
}