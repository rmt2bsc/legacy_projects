package com.action.userlogin;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.api.security.authentication.SessionBeanManager;
import com.api.security.pool.AppPropertyPool;
import com.bean.RMT2SessionBean;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.SystemException;

/**
 * This class serves as an action handler to authenticate a user's login
 * credentials for the local application. The requestor is required to send 
 * (tunnel) a Properties object via the request object containing the followng 
 * key elements: userid and password. Once the said user has been successfully 
 * authenticated and authorized, a RMT2SessionBean is returned (tunnel) to the 
 * requestor via the request object. Otherwise, nothing is sent, and the requestor 
 * should interpret the RMT2SessionBean as being null.
 * 
 * @author Roy Terrell
 * @deprecated Will be removed.  The class, com.api.security.DatabaseAuthenticationImpl, 
 *             will serve as the entry point for local authentication. 
 * 
 */
public class LocalLoginAction extends ExternalLoginAction {
    private Logger logger;

    /**
     * Default constructor which initializes the logger.
     * 
     */
    public LocalLoginAction() throws SystemException {
	super();
	logger = Logger.getLogger("LocalLoginAction");
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
    public LocalLoginAction(Request request, Response response) throws SystemException, DatabaseException {
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
	super.init(_context, _request);
    }

    /**
     * Processes the client's request to authenticate or log out a user in regards 
     * to the local application.
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
     * Assigns the session bean object to the user's web session for this application.
     * 
     * @throws AbstractActionHandler
     */
    public void sendClientData() throws ActionHandlerException {
	if (this.outData != null) {
	    if (this.outData instanceof RMT2SessionBean) {
		try {
		    RMT2SessionBean sessionBean = (RMT2SessionBean) this.outData;
		    String appId = AppPropertyPool.getProperty("appcode");
		    sessionBean.setOrigAppId(appId);
		    SessionBeanManager sbm = SessionBeanManager.getInstance(this.request);
		    sbm.addSessionBean(sessionBean);
		    return;
		}
		catch (Exception e) {
		    this.msg = e.getMessage();
		    this.logger.log(Level.ERROR, this.msg);
		    throw new ActionHandlerException(this.msg);
		}
	    }

	    // Handle exception
	    if (this.outData instanceof Exception) {
		Exception e = (Exception) this.outData;
		this.msg = e.getMessage();
		this.logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	}
	else {
	    this.msg = "A system error occurred: authentication return value must be a instance of RMT2SessionBean or and Exception";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

}