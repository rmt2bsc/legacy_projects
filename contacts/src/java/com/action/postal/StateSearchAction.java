package com.action.postal;

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

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.api.postal.AddressComponentsFactory;
import com.api.postal.StatesApi;
import com.api.postal.StatesException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.RMT2TagQueryBean;
import com.bean.State;
import com.bean.VwStateCountry;

import com.bean.criteria.StateCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.services.handlers.PostalSearchHandler;

import com.util.SystemException;
import com.xml.schema.misc.PayloadFactory;




/**
 * This abstract action handler provides common functionality to respond to the
 * requests originating from the State/Province search page. The following
 * commands are handled:
 * {@link com.action.postal.StateSearchAction#COMMAND_NEWSEARCH New Search},
 * {@link com.action.postal.StateSearchAction#COMMAND_SEARCH Search},
 * {@link com.action.postal.StateSearchAction#COMMAND_LIST List Results},
 * {@link com.action.postal.StateSearchAction#COMMAND_EDIT Edit state/province details},
 * {@link com.action.postal.StateSearchAction#COMMAND_BACK Navigate to Home page},
 * and {@link com.action.postal.StateSearchAction#COMMAND_RESET Page reset}.
 * 
 * @author Roy Terrell
 * 
 */
public class StateSearchAction extends AbstractActionHandler implements ICommand {

    /** Command name for new state/province search */
    protected static final String COMMAND_NEWSEARCH = "State.Search.newsearch";

    /** Command name for state/province search */
    protected static final String COMMAND_SEARCH = "State.Search.search";

    /**
     * Command name for listing multiple state/province records based on
     * selection criteria
     */
    public static final String COMMAND_LIST = "State.Search.list";

    /** Command name for adding a state/province record */
    protected static final String COMMAND_ADD = "State.Search.add";

    /** Command name for view state/province record */
    protected static final String COMMAND_EDIT = "State.Search.edit";

    /** Command name for navigating to previous page */
    protected static final String COMMAND_BACK = "State.Search.back";

    /** Command name for resetting the state/province search page */
    protected static final String COMMAND_RESET = "State.Search.reset";

    private StatesApi api;

    private Object list;

    private State state;

    private String stateIdStr;

    private Logger logger;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public StateSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("StateSearchAction");
    }

    /**
     * Performs the initialization needed to properly utilize this handler.
     * Initializes this object using <i>context</i> and <i>request</i>.
     * 
     * @param context
     *            the servet context.
     * @param request
     *            the http servlet request.
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
    }

    /**
     * Driver for processing the client's zip code Search page.
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
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_LIST)) {
	    this.doList();
	}
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_RESET)) {
	    this.doReset();
	}
	return;
    }

    /**
     * Gathers state/province input data from the client's request.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	this.stateIdStr = this.getInputValue("StateId", null);
    }

    /**
     * Stores the results of queries that produces either a single state/province object 
     * or a list of state/province objects to the request for the client to process. 
     * Single state/province query results are identified on the request as
     * {@link com.constants.GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}.
     * A resultset of a List of state/provinces are identified on the request
     * as {@link com.constants.GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.state);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.list);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);

	String xml = this.getXmlResults();
	this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
    }

    @Override
    protected String getXmlResults() throws ActionHandlerException {
        if (this.isError()) {
            RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
            return PayloadFactory.buildCommonErrorPayload(-1, this.msg,  userSession.getLoginId());
        }
        
	List <VwStateCountry> results = null;
	if (command.equalsIgnoreCase(StateSearchAction.COMMAND_LIST) || command.equalsIgnoreCase(StateSearchAction.COMMAND_SEARCH)) {
	    if (this.list != null && this.list instanceof List) {
		results = (List <VwStateCountry>) this.list;
	    }
	}
	else if (command.equalsIgnoreCase(StateSearchAction.COMMAND_ADD)) {
	    VwStateCountry item = new VwStateCountry();
	    results = new ArrayList<VwStateCountry>();
	    results.add(item);
	}
	else if (command.equalsIgnoreCase(StateSearchAction.COMMAND_EDIT)) {
	    if (this.state != null && this.state instanceof State) {
		VwStateCountry item = new VwStateCountry();
		results = new ArrayList<VwStateCountry>();
		item.setStateCode(this.state.getAbbrCode());
		item.setStateId(this.state.getStateId());
		item.setCountryId(this.state.getCountryId());
		item.setStateName(this.state.getStateName());
		results.add(item);
	    }
	}
	
	String responseId = "RS_postal_search";
	PostalSearchHandler srvc = new PostalSearchHandler(null, this.request);	
	srvc.setResponseServiceId(responseId);
	return srvc.buildProvinceResponsePayload(results, this.msg);
    }

    /**
     * Creates an instance of StateCriteria and attempts to obtain its data
     * from the user's request based on the DataSource view, <i>VwStateCountryView</i>.
     * 
     * @return {@link com.bean.criteria.StateCriteria StateCriteria}
     * @throws ActionHandlerException problem occurs creating the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	StateCriteria criteriaObj = StateCriteria.getInstance();
	this.baseView = "VwStateCountryView";
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		throw new ActionHandlerException(e);
	    }
	}
	return criteriaObj;
    }

    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "state_id = -1";
    }

    /**
     * Refreshes the selection criteria and list sections of the state/province search
     * page.
     * 
     * @throws ActionHandlerException
     */
    protected void doNewSearch() throws ActionHandlerException {
	this.setFirstTime(true);
	this.receiveClientData();
	this.startSearchConsole();
	this.list = new ArrayList<VwStateCountry>();
	this.sendClientData();
    }

    /**
     * Verifies that the state/province search criteria exists and is found on the
     * user's session. This logic is used to respond to the "Search" command.
     * 
     * @throws ActionHandlerException
     *             When the selection criteria object is null or invalid.
     */
    protected void doSearch() throws ActionHandlerException {
	this.buildSearchCriteria();
	this.doList();
	return;
    }

    /**
     * Gathers the list of state/province records from the database based on the
     * user's selection criteria. After the data is fetched, the data is stored
     * onto the request in order to be sent to the client for processing.
     * 
     * @throws ActionHandlerException
     *             General database errors.
     */
    public void doList() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createStatesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    String criteria = this.query.getWhereClause();
	    this.list = this.api.findState(criteria);
	    if (this.list == null) {
		this.list = new ArrayList<VwStateCountry>();
	    }
	    this.msg = ((List<VwStateCountry>) this.list).size() + " rows found";
	    this.setError(false);
	    this.sendClientData();
	}
	catch (StatesException e) {
	    this.setError(true);
	    this.msg = e.getMessage();
	    this.sendClientData();
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
     * Creates a new {@link com.bean.State State} object that will be used for the 
     * "Add State" client presentation.
     * 
     * @throws ActionHandlerException N/A
     */
    public void add() throws ActionHandlerException {
	this.state = AddressComponentsFactory.createState();
	this.msg = "A new State instance was created for an Add operation";
	return;
    }

    /**
     * Retrieves a single {@link com.bean.VwStateCountry VwStateCountry} instance from the
     * database using the state/province id obtain from
     * {@link com.action.postal.StateSearchAction#receiveClientData() receiveClientData()}
     * method.
     * 
     * @throws ActionHandlerException
     *             When target zip code id is null or contains an invalid value.
     */
    public void edit() throws ActionHandlerException {
	// Convert value to number
	int stateId;
	try {
	    if (this.stateIdStr == null) {
		this.msg = "A state/province ide must selected for the \"Edit\" command";
		this.logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	    stateId = Integer.parseInt(this.stateIdStr);
	}
	catch (NumberFormatException e) {
	    this.msg = this.stateIdStr + " is invalid state identifier";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	// Get data for a single state/province instance.
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createStatesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.state = (State) this.api.findStateById(stateId);
	    if (this.state == null) {
		this.state = AddressComponentsFactory.createState();
	    }
	    this.msg = "A State instance was obtained for an Edit operation";
	    return;
	}
	catch (StatesException e) {
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
     * Navigates the user back to the Contacts home page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	return;
    }

    /**
     * Resets the selection criteria and list sections of the state/province search
     * page.
     * 
     * @throws ActionHandlerException
     */
    protected void doReset() throws ActionHandlerException {
	this.doNewSearch();
	return;
    }

    /**
     * N/A
     */
    public void save() throws ActionHandlerException {
	return;
    }

    /**
     * N/A
     */
    public void delete() throws ActionHandlerException {
	return;
    }

}