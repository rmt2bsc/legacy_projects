package com.quote;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.constants.RMT2ServletConst;

import com.entity.Quote;

import com.util.SystemException;

/**
 * Action hander for responding to and managing requests pertaining to the Quote module.
 * 
 * @author appdev
 *
 */
public class QuoteAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_NEW = "Contact.Quote.new";
    private static final String COMMAND_SUBMIT = "Contact.Quote.submit";
    private static Logger logger = Logger.getLogger(QuoteAction.class);
    private Quote quote;
    
    /**
     * Constructs a quote action handler which is unaware of its environment.
     */
    public QuoteAction() {
	super();
    }

    /**
     * Constructs a quote action handler initialized with an application context 
     * and user's request.
     * 
     * @param context
     *         {@link com.controller.Context Context} 
     * @param request
     *         {@link com.controller.Request Request}
     * @throws SystemException
     */
    public QuoteAction(Context context, Request request) throws SystemException {
	super(context, request);
	this.init(context, request);
    }

    
    /**
     * Processes requests to display a new quote and persisting the quote to the database
     *  
     * @param request 
     *          The user's reqest
     * @param response 
     *          The user's response
     * @param command 
     *          The user's command
     * @throws ActionHandlerException
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);

	// Handle command.
	if (command.equalsIgnoreCase(QuoteAction.COMMAND_NEW)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(QuoteAction.COMMAND_SUBMIT)) {
	    this.saveData();
	    logger.log(Level.INFO, "Quote save successfully, " + this.quote.getQuoteId());
	}
    }
    
    
    /**
     * Creates a quote instance from the user's request.
     * 
     */
    @Override
    protected void receiveClientData() throws ActionHandlerException {
	this.quote = QuoteFactory.createQuote(this.request);

    }

    /**
     * Adds the in-memeory quote and server message to the usr's request in order to 
     * be transported to the client
     */
    @Override
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(QuoteConst.DATA_QUOTE, this.quote);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Saves a quote to the database.
     * 
     * @throws ActionHandlerException 
     *           validation errors or general data access errors.
     * @throws DatabaseException 
     *           when a database error.
     */
    public void save() throws ActionHandlerException, DatabaseException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	QuoteApi api = QuoteFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainQuote(this.quote);
	    tx.commitUOW();
	    this.msg = "Your quote was submitted, and you will be contacted by one of our representatives";
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "A problem occurred during the submission of your quote.  Please contact RMT2 Business Systems Corp to discuss your objective";
            throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api = null;
	    tx.close();
	    tx = null;
	}
    }
    

    /**
     * Creates a new quote instance defaults its contact preferences flag to 
     * "Contact by Phone".
     */
    public void add() throws ActionHandlerException {
	this.quote = QuoteFactory.createQuote();
	// Set contact preference to "Email Me"
	this.quote.setContactPref(2);
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
	return;
    }

    
    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	// TODO Auto-generated method stub

    }

}
