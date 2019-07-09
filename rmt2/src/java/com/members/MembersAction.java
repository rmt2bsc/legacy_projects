package com.members;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.quote.QuoteAction;
import com.util.SystemException;

/**
 * This is class is designed to handle requests pertaining to the member profile setup and maintenance pages.
 * Currently, the pages have not been implemented.
 * 
 * @author appdev
 *
 */
public class MembersAction extends AbstractActionHandler implements ICommand {
    
    private static Logger logger = Logger.getLogger(QuoteAction.class);
    
    /**
     * Constructs a Members action handler that is incapable of processing any requests.
     */
    public MembersAction() {
	super();
    }

    /**
     * Constructs a Members action handler that is capable of processing any requests and is 
     * knowlegable of the application context.
     * 
     * @param context
     *          the context of the application
     * @param request
     *          the user's request
     * @throws SystemException
     */
    public MembersAction(Context context, Request request) throws SystemException {
	super(context, request);
	this.init(context, request);
    }

    /**
     * Initializes the member action handler by invoking the direct ancestor method for assigning the 
     * application context and the user's request.
     * 
     * @param context 
     *          the application context
     * @param request 
     *          the user's request
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        super.init(context, request);
    }
    
    /**
     * Accepts, identifies, and processes the member's request.
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


    /* (non-Javadoc)
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
	// TODO Auto-generated method stub

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

}
