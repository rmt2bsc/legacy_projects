package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractInternalServerAction;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.UserAuthenticationException;
import com.api.security.authentication.AuthenticationConst;

import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Service for retrieving a single user profile from the database as XML.
 * Requires the client to send the following key/value pairs as input:
 * <ul>
 *   <li>{@link AuthenticationConst.AUTH_PROP_USERID}</li>:&nbsp;User's Login Id.
 * </ul>
 * <p>
 * @author RTerrell
 * @deprecated Not Used at all.
 * 
 */
public class UserProfileProducer extends AbstractInternalServerAction {
    private Logger logger;

    public UserProfileProducer() throws SystemException {
	this.logger = Logger.getLogger("UserProfileProducer");
    }

    /**
     * Retrieves the user's profile using login id.  The results of 
     * the query are stored in XML format.
     * 
     * @throws ServiceHandlerException
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector());
	String data = null;
	try {
	    data = (String) api.findUserByUserName(this.inData.toString());
	    this.outData = data;
	    logger.log(Level.INFO, data);
	}
	catch (UserAuthenticationException e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Accepts the login id from the request which will be used to retrieve the
     * user's profile.
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
	this.inData = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
    }

}
