package com.action.postal;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.util.SystemException;

/**
 * This abstract action handler provides common functionality to respond 
 * to the requests originating from the zip code view/edit page.  The only 
 * command that is handled is: {@link com.action.postal.ZipCodeViewAction#COMMAND_BACK Navigate back to search page}, and
 * 
 * @author Roy Terrell
 * 
 */
public class ZipCodeViewAction extends AbstractActionHandler implements ICommand {

    /** Command name for navigating to previous page */
    protected static final String COMMAND_BACK = "Zipcode.View.back";

    private Logger logger;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public ZipCodeViewAction() throws SystemException {
        super();
        logger = Logger.getLogger("ZipCodeViewAction");
        this.logger.log(Level.DEBUG, "Logger initialized");
    }

    /**
     * Performs the initialization needed to properly utilize this handler.   
     * Initializes this object using <i>context</i> and <i>request</i>. 
     * 
     * @param context the servet context.
     * @param request the http servlet request.
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        super.init(context, request);
    }

    /**
     * Driver for processing the client's zip code view page.
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
    	try {
    	    this.init(null, request);
    	    this.init();
    	    this.command = command;
    	}
    	catch (SystemException e) {
    	    throw new ActionHandlerException(e);
    	}
    	if (command.equalsIgnoreCase(ZipCodeViewAction.COMMAND_BACK)) {
    	    this.doBack();
    	}
    	return;
    }

    /**
     * N/A 
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
        return;
    }

    /**
     * N/A 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        return;
    }
    /**
     * N/A
     * 
     * @throws ActionHandlerException When target zip code id is null or contains an invalid value.
     */
    public void edit() throws ActionHandlerException {
        return;
    }

    /**
     * Navigates the user back to the home page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	try {
	    ZipCodeSearchAction search = new ZipCodeSearchAction();
	    search.processRequest(this.request, this.response, ZipCodeSearchAction.COMMAND_LIST);
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}
        return;
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