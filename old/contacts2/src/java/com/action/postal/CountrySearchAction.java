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
import com.api.postal.AddressComponentsFactory;
import com.api.postal.CountryApi;
import com.api.postal.CountryException;

import com.bean.Country;
import com.bean.RMT2TagQueryBean;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.services.handlers.PostalSearchHandler;
import com.util.SystemException;





/**
 * This abstract action handler provides common functionality to respond to the
 * requests originating from the Country search page. The following
 * commands are handled:
 * {@link com.action.postal.StateSearchAction#COMMAND_LIST List Results},
 * {@link com.action.postal.StateSearchAction#COMMAND_EDIT Edit country details}, and
 * {@link com.action.postal.StateSearchAction#COMMAND_BACK Navigate to Home page},
 * 
 * @author Roy Terrell
 * 
 */
public class CountrySearchAction extends AbstractActionHandler implements ICommand {
    /**
     * Command name for listing multiple country records based on
     * selection criteria
     */
    public static final String COMMAND_LIST = "Country.Search.list";

    /** Command name for adding a country record */
    protected static final String COMMAND_ADD = "Country.Search.add";
    
    /** Command name for view country record */
    protected static final String COMMAND_EDIT = "Country.Search.edit";

    /** Command name for navigating to previous page */
    protected static final String COMMAND_BACK = "Country.Search.back";

    private CountryApi api;

    private Object list;

    private Country country;

    private Logger logger;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public CountrySearchAction() throws SystemException {
        super();
        logger = Logger.getLogger("CountrySearchAction");
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
        if (command.equalsIgnoreCase(CountrySearchAction.COMMAND_LIST)) {
            this.doList();
        }
        if (command.equalsIgnoreCase(CountrySearchAction.COMMAND_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(CountrySearchAction.COMMAND_EDIT)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(CountrySearchAction.COMMAND_BACK)) {
            this.doBack();
        }
        return;
    }

    /**
     * Gathers country input data from the client's request.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	String temp = this.getInputValue("CountryId", null);
	int id;
	this.country = AddressComponentsFactory.createCountry();
	try {
	    id = Integer.parseInt(temp);
	    this.country.setCountryId(id);
        }
        catch (NumberFormatException e) {
            this.country = null;
        }
    }

    /**
     * Stores the results of queries that produces either a single country object 
     * or a list of country objects to the request for the client to process. 
     * Single country query results are identified on the request as
     * {@link com.constants.GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD}.
     * A resultset of a List of country are identified on the request
     * as {@link com.constants.GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.country);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.list);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
        
        String xml = this.getXmlResults();
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
    }
    
    /**
     * Sends a update confirmation to client as the XML message, RS_postal_search.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    protected String getXmlResults() throws ActionHandlerException {
	String responseId = "RS_postal_search";
	PostalSearchHandler srvc = new PostalSearchHandler(null, this.request);
	srvc.setResponseServiceId(responseId);
	return srvc.buildCountryRepsonsePayload((List <Country>) this.list, this.msg);
    }

    /**
     * Gathers the list of country records from the database based on the
     * user's selection criteria. After the data is fetched, the data is stored
     * onto the request in order to be sent to the client for processing.
     * 
     * @throws ActionHandlerException General database errors.
     */
    public void doList() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createCountryApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.list = this.api.findCountry(null);
            if (this.list == null) {
                this.list = new ArrayList<Country>();
            }
            this.msg = ((List <Country>) this.list).size() + " rows found";
            this.sendClientData();
        }
        catch (CountryException e) {
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
     * Creates a new {@link com.bean.Country Country} object that will be used for the 
     * "Add Country" client presentation.
     * 
     * @throws ActionHandlerException N/A
     */
    public void add() throws ActionHandlerException {
	this.country = AddressComponentsFactory.createCountry();
        return;
    }
    
    /**
     * Retrieves a single {@link com.bean.Country Country} instance from the
     * database using the country id obtain from
     * {@link com.action.postal.CountrySearchAction#receiveClientData() receiveClientData()}
     * method.
     * 
     * @throws ActionHandlerException
     *             When target zip code id is null or contains an invalid value.
     */
    public void edit() throws ActionHandlerException {
        if (this.country == null) {
            this.msg = "A country must be selected for the \"Edit\" command";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        // Get data for a single country instance.
        DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = AddressComponentsFactory.createCountryApi((DatabaseConnectionBean) tx.getConnector(), this.request);
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