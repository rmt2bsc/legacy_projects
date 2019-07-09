package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractInternalServerAction;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.UserAuthenticationException;

import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Service for retrieving one or more user profiles from the database 
 * as XML based on custom selection criteria input from the client.
 * Requires the client to send the following key/value pairs as input:
 * <ul>
 *   <li>crteria</li>:&nbsp;User's Profile selection criteria.
 * </ul>
 * <p>
 * @author RTerrell
 * @deprecated use {@link com.services.resources.UserProfileService UserProfileService}
 * 
 */
public class UsersProducer extends AbstractInternalServerAction {
	 
	private Logger logger;

    public UsersProducer() throws SystemException {
	this.logger = Logger.getLogger("UserProfileProducer");
    }

    /**
     * Retrieves the user's profile extension custom selection criteria.  The results of 
     * the query are stored in XML format.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector());
	String data = null;
	String criteria = (this.inData == null ? null : this.inData.toString());
	try {
	    data = (String) api.findUserExt(criteria);
	    this.outData = data;
	    logger.log(Level.INFO, data);
	}
	catch (UserAuthenticationException e) {
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * Accepts the selection criteria from the request which will be used 
     * to retrieve multiple user profiles.
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
    	super.receiveClientData();
	final String PARM_CRITERIA = "criteria";
	this.inData = this.request.getParameter(PARM_CRITERIA);
	
		// Check for stock criteria
	    String criteria = null;
		String value = this.request.getParameter("login_id");
		if (value != null) {
			criteria = " login_id = " + value;
			if (this.inData != null) {
				this.inData = this.inData.toString() + " and " + criteria; 
			}
			else {
				this.inData = criteria;
			}
		}
    }

}
