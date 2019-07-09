package com.api.security;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;


import com.api.security.authentication.InvalidUserCredentialsException;
import com.api.security.authentication.LoginException;

import com.controller.Request;

/**
 * This implementation provides functionality for authenticating users local to 
 * the Authentication application.
 * 
 * @author appdev
 *
 */
public class LocalAuthenticator extends DatabaseAuthenticator {
    private static Logger logger = Logger.getLogger(LocalAuthenticator.class);
    
    /**
     * Default constructor
     */
    public LocalAuthenticator() {
	super();
    }

    /**
     * Initializes instance as a servicer of local requests and obtains the id of the 
     * local session
     * 
     * @param request  
     *           The user's request object.
     * @param credentials 
     *           This is of no use regarding local authentication. 
     * @throws LoginException 
     *           Invalid data contained in <i>credentials</i> or if the data source provider 
     *           is unobtainable.
     */
    public void initInstance(Request request, Object credentials) throws LoginException, InvalidUserCredentialsException {
	try {
	    super.initInstance(request, credentials);
	}
	catch (InvalidUserCredentialsException e) {
	    // Ignore invalid credentials
	}
	this.setLoginOrigin(UserSecurity.LOGIN_ORIGIN_LOCAL);
	if (request != null) {
	    String sessionId = request.getSession().getId();
	    this.setSessionId(sessionId);
	    logger.log(Level.INFO, "Processing local authentication request for session " + sessionId);
	}
    }
}
