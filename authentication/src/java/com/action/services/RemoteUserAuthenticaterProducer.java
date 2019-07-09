package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractInternalServerAction;

import com.api.security.authentication.AuthenticationConst;

import com.api.security.UserSecurity;
import com.api.security.SecurityFactory;

import com.util.SystemException;

/**
 * Services the client's request to authenticate a remote user.  Requires the 
 * client to send the following key/value pairs as input:
 * <ul>
 *   <li>{@link AuthenticationConst.AUTH_PROP_USERID}</li>:&nbsp;User's Login Id.
 *   <li>{@link AuthenticationConst.AUTH_PROP_MAINAPP}</li>:&nbsp;User's Source Applicatin Id.
 * </ul>
 * <p>
 * 
 * @author RTerrell
 * @deprecated  User services implemented in com.services.* package
 * 
 */
public class RemoteUserAuthenticaterProducer extends AbstractInternalServerAction {
    private static final String SERVICE_CHECKAPP = "Services.checkapp";
    private static Logger logger;

    private UserSecurity authApi;

    public RemoteUserAuthenticaterProducer() throws SystemException {
	logger = Logger.getLogger(RemoteUserAuthenticaterProducer.class);
    }

    /**
     * Authenticates a remote user and forces the sendClientData method to send 
     * a RMT2SessionBean instance to the caller.
     * 
     * @throws ServiceHandlerException
     */
    public void processData() throws ActionHandlerException {
	Properties prop = (Properties) this.inData;
	if (this.authApi == null) {
	    this.authApi = SecurityFactory.getDatabaseAuthenticationApi(this.request, prop);
	}
	try {
	    if (RemoteUserAuthenticaterProducer.SERVICE_CHECKAPP.equalsIgnoreCase(this.command)) {
		String loginId = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
		String appName = this.request.getParameter(AuthenticationConst.AUTH_PROP_MAINAPP);
		String srcSessionId = this.request.getParameter(AuthenticationConst.AUTH_PROP_SESSIONID);
		this.outData = this.authApi.getAuthentication(loginId, appName, srcSessionId);
	    }
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    this.outData = e;
	}
    }

    /**
     * Accepts required data, which is the login id and the source application 
     * name, needed to login a remote user.
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
	Properties args = new Properties();
	String temp;
	temp = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
	if (temp != null) {
	    args.put(AuthenticationConst.AUTH_PROP_USERID, temp);
	}
	temp = this.request.getParameter(AuthenticationConst.AUTH_PROP_MAINAPP);
	if (temp != null) {
	    args.put(AuthenticationConst.AUTH_PROP_MAINAPP, temp);
	}
	this.inData = args;
    }

}
