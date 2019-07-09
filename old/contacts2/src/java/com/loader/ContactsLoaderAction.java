package com.loader;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * Action handler to serve the user's request to migrate historical expense 
 * and revenue transactions to the accounting system. 
 * 
 * @author Roy Terrell
 *
 */
public class ContactsLoaderAction extends AbstractActionHandler implements ICommand {
    private Logger logger;

    /**
     * Default constructor
     * 
     */
    public ContactsLoaderAction() {
	super();
	logger = Logger.getLogger("ContactsLoaderAction");
    }

    /**
     * Initializes this object using context and request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
    }

    /**
     * Processes the client's request to load transactions using request, response, 
     * and command.
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
	this.addData();
    }

    /**
     * Initalizes the transaction loader an drives the process of migrating the historical 
     * transactions to the accounting system.
     *  
     * @throws SystemException
     *           general errors.
     */
    public void add() throws ActionHandlerException {
    	String begDate = RMT2Date.formatDate(new Date(), "yyyy-MMM-dd HH:mm:ss");
    	String endDate = null;
    	
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	try {
    		ContactsLoader loader = new ContactsLoader((DatabaseConnectionBean) tx.getConnector(), this.request, null);
    		loader.parseDocument();
    	}
    	catch (Exception e) {
    		this.logger.log(Level.ERROR, e.getMessage());
    		throw new ActionHandlerException(e);
    	}
    	finally {
    		endDate = RMT2Date.formatDate(new Date(), "yyyy-MMM-dd HH:mm:ss");
    		this.logger.log(Level.INFO, "Transaction loader start time: " + begDate);
    		this.logger.log(Level.INFO, "Transaction loader end time: " + endDate);
    	}
		


    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#save()
     */
    public void save() throws ActionHandlerException, DatabaseException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#receiveClientData()
     */
    @Override
    protected void receiveClientData() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#sendClientData()
     */
    @Override
    protected void sendClientData() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

}