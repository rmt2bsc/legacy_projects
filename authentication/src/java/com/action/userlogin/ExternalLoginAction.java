package com.action.userlogin;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.remoteservices.http.AbstractInternalServerAction;

import com.api.db.DatabaseException;

import com.api.security.authentication.LoginException;
import com.api.security.authentication.LogoutException;
import com.api.security.SecurityFactory;
import com.api.security.UserSecurity;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.RMT2Exception;
import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * This class serves as an action handler to authenticate a user's login
 * credentials. The requestor is required to send (tunnel) a Properties object
 * via the request object containing the followng key elements: userid and
 * password. Once the said user has been successfully authenticated and
 * authorized, a RMT2SessionBean is returned (tunnel) to the requestor via the
 * request object. Otherwise, nothing is sent, and the requestor should
 * interpret the RMT2SessionBean as being null.
 * 
 * @author Roy Terrell
 * @deprecated USe AuthenticationServiceAction.java
 * 
 */

public class ExternalLoginAction extends AbstractInternalServerAction {
    private static final String COMMAND_LOGIN = "login";

    private static final String COMMAND_LOGOFF = "logoff";

    private UserSecurity authApi;

    private Logger logger;

    /**
     * Default constructor which initializes the logger.
     * 
     */
    public ExternalLoginAction() throws SystemException {
	super();
	logger = Logger.getLogger("ExternalLoginAction");
    }

    /**
     * Constructor which ensures that a valid login datasource is created, and
     * the login id and password is not null.
     * 
     * @param _dataSource
     *            A string that represents the login datasource name
     * @param _conPool
     *            The database connection api ({@link DataProviderConnectionApi})
     *            which contains the connection pool.
     * @param _conBean
     *            The user's connection object {@link DatabaseConnectionBean}
     * @param parms
     *            The user credentials such as user Id and password. This data
     *            is contained as a Hashtable.
     * @throws LoginException
     */
    public ExternalLoginAction(Request request, Response response) throws SystemException, DatabaseException {
	this();
	this.init(null, request);
	logger = Logger.getLogger("ExternalLoginAction");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor which
     * the invocation of this method should be from within the descendent.
     * 
     * @param _context
     *            the servet context
     * @param _request
     *            the http servlet request
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(null, _request);
	// Get servlet context in the event it is not initizialized.
	if (this.context == null) {
	    this.context = this.request.getSession().getContext();
	}
	logger = Logger.getLogger("ExternalLoginAction");
    }

    /**
     * Processes the client's request to authenticate or log out a user.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @throws SystemException
     *             when a general error occurs processing the request.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        super.processRequest(request, response, command);
    }

    
    /**
     * Adapts the input data that exist in the request object as a Properties collection.
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
        super.receiveClientData();
        if (this.inData == null) {
            this.inData = RMT2Utility.getRequestData(this.request);    
        }
    }
    
    /**
     * Based on the user's request, this method either challenges the user's
     * credentials during system login or logs the user out of the system.
     * 
     * @param inObj
     *            Contains the user login credentials.
     * @return The user's session bean disguised as an Object.
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        this.authApi = SecurityFactory.getDatabaseAuthenticationApi(this.request, this.inData);
        try {
            if (this.command.equalsIgnoreCase(ExternalLoginAction.COMMAND_LOGIN)) {
        	this.doLogin();
            }
            if (this.command.equalsIgnoreCase(ExternalLoginAction.COMMAND_LOGOFF)) {
        	this.doLogout();
            }
            return;
        }
        catch (RMT2Exception e) {
            throw new ActionHandlerException(e.getMessage(), e.getErrorCode());
        }
        finally {
            this.authApi.close();
            this.authApi = null;
        }
    }


    
    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#doLogin()
     */
    public Object doLogin() throws LoginException {
	if (this.authApi == null) {
	    this.msg = "Unable to login user due to invalid authentication api instance";
	    logger.log(Level.FATAL, this.msg);
	    throw new LoginException(this.msg);
	}
	this.outData = this.authApi.doLogin();
	return this.outData;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#doLogout()
     */
    public int doLogout() throws LogoutException {
	if (this.authApi == null) {
	    this.msg = "Unable to logout user due to invalid authentication api instance";
	    logger.log(Level.FATAL, this.msg);
	    throw new LogoutException(this.msg);
	}
	return this.authApi.doLogout();
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#isUserAuthenticated(java.lang.String, java.lang.String)
     */
    public boolean isUserAuthenticated(String loginId, String appName) throws LoginException {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#isUserAuthenticated(java.lang.String)
     */
    public boolean isUserAuthenticated(String loginId) throws LoginException {
	// TODO Auto-generated method stub
	return false;
    }

    /**
     * Attempts to log the user out of the system.
     * 
     * @throws ActionHandlerException
     *             When the system fails to log out the user
     */
    public void delete() throws ActionHandlerException {
	super.delete();
	// See if we have a valid login credential object.
		if (this.authApi == null) {
            this.authApi = SecurityFactory.getDatabaseAuthenticationApi(this.request, null);
	}
	try {
	    this.authApi.doLogout();
	}
	catch (LogoutException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
            this.authApi.close();
            this.authApi = null;
        }
    }

}