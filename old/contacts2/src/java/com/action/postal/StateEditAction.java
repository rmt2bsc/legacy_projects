package com.action.postal;

import java.util.ArrayList;
import java.util.List;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.ContactException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.api.postal.AddressComponentsFactory;
import com.api.postal.StatesApi;
import com.api.postal.StatesException;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.State;
import com.bean.VwStateCountry;

import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.util.SystemException;

import com.xml.schema.misc.PayloadFactory;

/**
 * Action handler for managing request coming from the State/Province Edit page.  
 * The handler responds to save, delete, and back commands.
 * 
 * @author RTerrell
 *
 */
public class StateEditAction extends AbstractActionHandler implements ICommand {
    /** Command name for saving changes to State/Province */
    protected static final String COMMAND_SAVE = "State.Edit.save";

    /** Command name for deleting State/Province record */
    protected static final String COMMAND_DELETE = "State.Edit.delete";

    /** Command name for navigating back to State/Province search page */
    protected static final String COMMAND_BACK = "State.Edit.back";

    private StatesApi api;

    private State state;

    private Logger logger;

    /**
     * Creates an instance of StateEditAction by initializing the logger.
     * 
     * @throws SystemException
     */
    public StateEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("PersonContactEditAction");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger.log(Level.INFO, "Initializing State/Province Edit Action handler");
    }

    /**
     * Driver for processing the client's State/Province Search page.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.StateEditAction.COMMAND_SAVE Save}, 
     * {@link com.action.contacts.StateEditAction.COMMAND_DELETE Delete}, and 
     * {@link com.action.contacts.StateEditAction.COMMAND_BACK Back} 
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
	if (command.equalsIgnoreCase(StateEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(StateEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(StateEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Saves the modifications of a state/province record which are persisted to the database.  After 
     * successfully saving the data, the model state/province object, {@link com.bean.State State} 
     * is refreshed from the database so that it may be sent to the client for presentation. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createStatesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.maintainState(this.state);
	    tx.commitUOW();
	    this.refreshState();
	    this.msg = "State was saved successfully";
	    this.setError(false);
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.setError(true);
	    this.msg = "Unable to save State/Province record [State Id = " + this.state.getStateId() + "].  " + e.getMessage();
	    logger.error(this.msg);
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

    /**
     * Get data for a single state/province instance.
     * 
     * @throws ActionHandlerException
     */
    private void refreshState() throws ActionHandlerException {
	try {
	    this.state = (State) this.api.findStateById(this.state.getStateId());
	    if (this.state == null) {
		this.state = AddressComponentsFactory.createState();
	    }
	    return;
	}
	catch (StatesException e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Navigates the user back to the State/Province search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	try {
	    StateSearchAction action = new StateSearchAction();
	    action.processRequest(this.request, this.response, StateSearchAction.COMMAND_LIST);
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}
	return;
    }

    /**
     * Gathers data from the request to populate a {@link com.bean.State State} object. 
     * 
     * @throws ActionHandlerException Error obtaining client data.
     */
    protected void receiveClientData() throws ActionHandlerException {
	this.state = AddressComponentsFactory.createState();
	try {
	    DataSourceAdapter.packageBean(this.request, this.state);
	    return;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Adds the results of single and list queries of state/provinces to the request
     * for the client to process. Single state/province query results are identified
     * on the request as
     * {@link com.constants.GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}.
     * Query results that are a list of state/province are identified on the request
     * as {@link com.constants.GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.state);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);

	String xml = this.getXmlResults();
	this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
    }

    @Override
    protected String getXmlResults() throws ActionHandlerException {
	List<VwStateCountry> results = null;
	if (this.state != null && this.state instanceof State) {
	    VwStateCountry item = new VwStateCountry();
	    results = new ArrayList<VwStateCountry>();
	    item.setStateCode(this.state.getAbbrCode());
	    item.setStateId(this.state.getStateId());
	    item.setCountryId(this.state.getCountryId());
	    item.setStateName(this.state.getStateName());
	    results.add(item);
	}

	String xml;
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	if (this.isError()) {
	    xml = PayloadFactory.buildCommonErrorPayload(-1, this.msg,  userSession.getLoginId());
	}
	else {
	    xml =  PayloadFactory.buildCommonPayload(this.state.getStateId(), this.msg,  userSession.getLoginId());
	}
	return xml;
    }

    /**
     * Deletes a State/Province record from the database.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createStatesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    int rows = this.api.deleteState(this.state);
	    this.msg = rows + " State records deleted successfully pertaining to state, " + this.state.getStateName();
	    this.setError(false);
	    tx.commitUOW();
	    return;
	}
	catch (ContactException e) {
	    this.msg = "Unable to delete State/Province record [State Id = " + this.state.getStateId() + "].  " + e.getMessage();
	    logger.error(this.msg);
	    this.setError(true);
	    tx.rollbackUOW();
	}
	finally {
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

    /**
     * N/A
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	return;
    }

    /**
     * N/A
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	return;
    }

}
