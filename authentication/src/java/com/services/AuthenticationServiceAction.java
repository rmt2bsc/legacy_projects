package com.services;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;

import com.bean.db.DatabaseConnectionBean;

import com.services.login.UserAppLoginVerificationService;
import com.services.login.UserLoginService;
import com.services.login.UserLogoutService;

import com.services.resources.UserProfileService;
import com.services.resources.UserResourceService;
import com.services.resources.UserResourceSubTypeService;



/**
 * A web service receiver designed to dispatch business contact related requests to the 
 * appropriate business contact service implementation.
 * 
 * @author Roy Terrell
 *
 */
public class AuthenticationServiceAction extends AbstractSoapServiceHandler {
    private static final String COMMAND_LOGIN = "Services.RQ_authentication_login";
    
    private static final String COMMAND_LOGOUT = "Services.RQ_authentication_logout";
    
    private static final String COMMAND_APPCHECK = "Services.RQ_authentication_user_appcheck";
    
    private static final String COMMAND_RSRC_SUBTYPE_FETCH = "Services.RQ_authentication_resource_subtype";
    
    private static final String COMMAND_RSRC_USER_ACCESS = "Services.RQ_authentication_user_resource_access";
    
    private static final String COMMAND_USER_PROFILE = "Services.RQ_authentication_user_profile";
    
    

    private static Logger logger = Logger.getLogger(AuthenticationServiceAction.class);

    /**
     * Default constructor
     *
     */
    public AuthenticationServiceAction() {
	super();
    }

    /**
     * Invokes the process designed to serve various web service requests peratining to RQ_authentication_*.
     *  
     *  @param inMsg
     *          the request message.
     * @return String
     *          the web service's response.
     * @throws ActionHandlerException
     */
    @Override
    protected SOAPMessage doService(SOAPMessage inMsg) throws ActionHandlerException {
	SOAPMessage resp = null;
	if (AuthenticationServiceAction.COMMAND_LOGIN.equalsIgnoreCase(this.command)) {
	    resp = this.loginUser(inMsg);
	}
	if (AuthenticationServiceAction.COMMAND_LOGOUT.equalsIgnoreCase(this.command)) {
	    resp = this.logoutUser(inMsg);
	}
	if (AuthenticationServiceAction.COMMAND_APPCHECK.equalsIgnoreCase(this.command)) {
	    resp = this.verifyUserLoggedIn(inMsg);
	}
	if (AuthenticationServiceAction.COMMAND_RSRC_SUBTYPE_FETCH.equalsIgnoreCase(this.command)) {
	    resp = this.fetchResourceSubTypes(inMsg);
	}
	if (AuthenticationServiceAction.COMMAND_RSRC_USER_ACCESS.equalsIgnoreCase(this.command)) {
	    resp = this.fetchUserResourceProfile(inMsg);
	}
	if (AuthenticationServiceAction.COMMAND_USER_PROFILE.equalsIgnoreCase(this.command)) {
            resp = this.fetchUserProfile(inMsg);
        }
	return resp;
    }

    private SOAPMessage loginUser(SOAPMessage inMsg) throws ActionHandlerException {
	AuthenticationServiceAction.logger.info("Prepare to Login User");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserLoginService srvc = new UserLoginService((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
            this.msg = "Web Service Action handler for user login service failed";
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);	    
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }


    private SOAPMessage logoutUser(SOAPMessage inMsg) throws ActionHandlerException {
	AuthenticationServiceAction.logger.info("Prepare to Logout User");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserLogoutService srvc = new UserLogoutService((DatabaseConnectionBean) tx.getConnector(), this.request); 
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Web Service Action handler for user logout service failed";
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }
    
    
    private SOAPMessage verifyUserLoggedIn(SOAPMessage inMsg) throws ActionHandlerException {
	AuthenticationServiceAction.logger.info("Prepare to verify applications user maybe logged in");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserAppLoginVerificationService srvc = new UserAppLoginVerificationService((DatabaseConnectionBean) tx.getConnector(), this.request); 
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Web Service Action handler thata verifies whether or not a user is logged in failed";
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }
    
    
    private SOAPMessage fetchResourceSubTypes(SOAPMessage inMsg) throws ActionHandlerException {
	AuthenticationServiceAction.logger.info("Prepare to fetch User Resource Sub Type records");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserResourceSubTypeService srvc = new UserResourceSubTypeService((DatabaseConnectionBean) tx.getConnector(), this.request); 
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Web Service Action handler for fetching resource sub types failed";
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }
    
    
    private SOAPMessage fetchUserResourceProfile(SOAPMessage inMsg) throws ActionHandlerException {
	AuthenticationServiceAction.logger.info("Prepare to fetch User granted and revokded resource profiles");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserResourceService srvc = new UserResourceService((DatabaseConnectionBean) tx.getConnector(), this.request); 
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Web Service Action handler for fetching user related resource profiles failed";
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }
    
    
    private SOAPMessage fetchUserProfile(SOAPMessage inMsg) throws ActionHandlerException {
        AuthenticationServiceAction.logger.info("Prepare to fetch one or more user profiles");
        DatabaseTransApi tx = DatabaseTransFactory.create();
        UserProfileService srvc = new UserProfileService((DatabaseConnectionBean) tx.getConnector(), this.request); 
        try {
            SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
            tx.commitUOW();
            return outSoapMsg;
        }
        catch (Exception e) {
            tx.rollbackUOW();
            this.msg = "Web Service Action handler for fetching user profiles failed";
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);
        }
        finally {
            srvc.close();
            tx.close();
            tx = null;
        }
    }
    
}