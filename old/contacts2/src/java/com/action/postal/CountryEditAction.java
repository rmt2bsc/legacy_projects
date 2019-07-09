package com.action.postal;

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
import com.api.postal.CountryApi;
import com.api.postal.CountryException;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.Country;

import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.util.SystemException;

import com.xml.schema.misc.PayloadFactory;

/**
 * Action handler for managing request coming from the Country Edit page.  
 * The handler responds to save, delete, and back commands.
 * 
 * @author RTerrell
 *
 */
public class CountryEditAction extends AbstractActionHandler implements ICommand {
    /** Command name for saving changes to Country */
    protected static final String COMMAND_SAVE = "Country.Edit.save";

    /** Command name for deleting Country record */
    protected static final String COMMAND_DELETE = "Country.Edit.delete";

    /** Command name for navigating back to Country search page */
    protected static final String COMMAND_BACK = "Country.Edit.back";

    private CountryApi api;

    private Country country;
    
    private Logger logger;

    /**
     * Creates an instance of StateEditAction by initializing the logger.
     * 
     * @throws SystemException
     */
    public CountryEditAction() throws SystemException {
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
	logger.log(Level.INFO, "Initializing Country Edit Action handler");
//	this.api = AddressComponentsFactory.createCountryApi(this.dbConn);
    }

    /**
     * Driver for processing the client's Country Search page.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.StateEditAction.COMMAND_SAVE Save}, 
     * {@link com.action.contacts.StateEditAction.COMMAND_DELETE Delete}, and 
     * {@link com.action.contacts.StateEditAction.COMMAND_BACK Back}.
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
	if (command.equalsIgnoreCase(CountryEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(CountryEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(CountryEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Saves the modifications of a state/provinces record which are persisted to the database.  After 
     * successfully saving the data, the model state/provinces object, {@link com.bean.Country Country} 
     * is refreshed from the database so that it may be sent to the client for presentation. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createCountryApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.maintainCountry(this.country);
	    tx.commitUOW();
	    this.refreshState();
	    this.msg = "Country was saved successfully";
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Error: " + e.getMessage();
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
     * Get data for a single country instance.
     * 
     * @throws ActionHandlerException
     */
    private void refreshState() throws ActionHandlerException {
        try {
            this.country = (Country) this.api.findCountryById(this.country.getCountryId());
            if (this.country == null) {
                this.country = AddressComponentsFactory.createCountry();
            }
            return;
        }
        catch (CountryException e) {
            throw new ActionHandlerException(e);
        }
    }

    /**
     * Navigates the user back to the Country search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	try {
	    CountrySearchAction action = new CountrySearchAction();
	    action.processRequest(this.request, this.response, CountrySearchAction.COMMAND_LIST);
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
	this.country = AddressComponentsFactory.createCountry();
	try {
	    DataSourceAdapter.packageBean(this.request, this.country);
	    return;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }
    
    
    /**
     * Adds the results of single and list queries of state/provincess to the request
     * for the client to process. Single state/provinces query results are identified
     * on the request as
     * {@link com.constants.GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}.
     * Query results that are a list of state/provinces are identified on the request
     * as {@link com.constants.GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.country);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
        
        String xml = this.getXmlResults();
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
    }

    /**
     * Deletes a Country record from the database.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createCountryApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    int rows = this.api.deleteCountry(this.country);
	    this.msg = rows + " Country record(s) deleted successfully pertaining to state, " + this.country.getName();
	    tx.commitUOW();
	    return;
	}
	catch (ContactException e) {
	    this.msg = "Error: " + e.getMessage();
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
     * Sends a update confirmation to client as the XML message, RS_common_reply.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    protected String getXmlResults() throws ActionHandlerException {
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	return PayloadFactory.buildCommonPayload(this.country.getCountryId(), this.msg,  userSession.getLoginId());
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
