package com.action.contacts;


import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.ContactException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.RMT2TagQueryBean;

import com.bean.bindings.JaxbContactsFactory;

import com.bean.criteria.ContactCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.services.handlers.CommonContactFetchHandler;

import com.util.SystemException;

import com.xml.schema.bindings.CommonContactCriteria;




/**
 * This action handler provides functionality to service the searching needs of  common contacts.
 * 
 * @author Roy Terrell
 * 
 */
public class CommonContactSearchAction extends AbstractContactSearchAction implements ICommand {
    /** Command name for new contact search */
    protected static final String COMMAND_NEWSEARCH = "CommonContact.Search.search";

    /** Command name for contact search */
    protected static final String COMMAND_SEARCH = "CommonContact.Search.doSearch";

    private Logger logger = Logger.getLogger("CommonContactSearchAction");

    private String returnXml;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public CommonContactSearchAction() throws SystemException {
        super();
    }

    /**
     * Initializes the common contact api implementation needed by this action handler.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
        logger.log(Level.INFO, "Initializing Common Contact Api's");
    }

    /**
     * Driver for processing the client's Common Contact requests.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.BusinessContactSearchAction.COMMAND_NEWSEARCH New Search}, 
     * {@link com.action.contacts.BusinessContactSearchAction.COMMAND_SEARCH Search}, 
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
        if (command.equalsIgnoreCase(CommonContactSearchAction.COMMAND_NEWSEARCH)) {
            this.doNewSearch();
        }
        if (command.equalsIgnoreCase(CommonContactSearchAction.COMMAND_SEARCH)) {
            this.doSearch();
        }
    }

    /**
     * Intializes the base view to <i>VwCommonContactView</i> for a common contact query 
     * and invokes ancestor functionality to establish a ContactCriteria instance from from 
     * the user's request. 
     * 
     * @return {@link ContactCriteria}
     * @throws ActionHandlerException problem occurs creating the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
        Object criteriaObj = super.doCustomInitialization();

        // Set data source so that the build-criteria process can discover field names.
        this.baseView = "VwCommonContactView";
        this.logger.log(Level.INFO, "Common Contact DataSource view was initialized to " + this.baseView);
        return criteriaObj;
    }

    /**
     * Force the initial results set to gather all contacts where contact_name begins with 
     * the letter 'A'.
     * 
     * @param queryBean The Query Object.
     * @return The selection criteria.as a String
     * @throws ActionHandlerException
     */
    protected String doInitialCriteria(RMT2TagQueryBean queryBean) throws ActionHandlerException {
        String criteria = null;
        super.doInitialCriteria(queryBean);
        criteria = "contact_name like 'A%i";
        return criteria;
    }

    /**
     * Perfomrs the actual search of contacts based on the selection criteria  supplied by the client.  To date, selection 
     * criteria can be constructed on contact id and contact name
     * 
     * @throws ActionHandlerException 
     *           Invalid parameters exits or general database access errors.
     *           
     * @see com.action.contacts.AbstractContactSearchAction#doSearch()           
     */
    @Override
    public void doSearch() throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        DatabaseConnectionBean con = (DatabaseConnectionBean) tx.getConnector();

        // Get parmaters via ancestor
        super.doSearch();

        String responseMsgId = "RS_common_contact_search";
        ContactCriteria clientCriteria = (ContactCriteria) this.query.getCustomObj();
        CommonContactCriteria criteria = JaxbContactsFactory.createCommonSelectionCriteria(clientCriteria);
        CommonContactFetchHandler srvc = new CommonContactFetchHandler(con, this.request);
        srvc.setResponseServiceId(responseMsgId);
        try {
            this.returnXml = srvc.doFetch(criteria);
            this.sendClientData();
        }
        catch (ContactException e) {
            this.msg = "Unable to fetch common contact data due to a Contact API error";
            logger.error(this.msg, e);
            throw new ActionHandlerException(this.msg, e);
        }
    }
    
    
    /**
     * Prepares to send the results of the common contact query to the clinet.  Initially 
     * the results exists as a List of java objects which are subsequently converted to XML.  
     * The sequence of events of this method is: obtains the list of contacts, creates the a 
     * XML message, converts the java results to XML and appends to the message, and stores the 
     * message onto the request object as an attribute named, {@link com.constants.RMT2ServletConst#RESPONSE_NONJSP_DATA RESPONSE_NONJSP_DATA}. 
     * 
     * @throws ActionHandlerException
     */
    @Override
    protected void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, this.returnXml);
    }
    

}