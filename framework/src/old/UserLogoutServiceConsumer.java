package com.api.security;

import java.util.Properties;

import com.action.ActionHandlerException;
import com.api.security.authentication.AuthenticationConst;


import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.remoteservices.ServiceConsumer;
import com.remoteservices.ServiceHandlerException;

import com.remoteservices.http.AbstractHttpClientAction;


/**
 * Invokes the user authentication service for the purpose of authenticating the user's credentials.
 * The sequence of communication goes as follows when connecting to a remote service: 1) the client
 * connects to the service dispatcher, 2) the service dispatcher connects to the target service,
 * 3) the service processes the request and sends results back to the dispatcher, and 4) the
 * dispatcher sends the results bact to the requester.  It usual for this client to contact the
 * service dispatcher over a unsecurred channel.
 * <p>
 * The following data values are required to be included in the service call as URL
 * parameters:
 * <ul>
 * <li>The service id.</li>
 * <li>The user's login id.</li>
 * <li>The user's password.</li>
 * </ul>
 *
 * @author RTerrell
 *
 */
public class UserLogoutServiceConsumer extends AbstractHttpClientAction implements ServiceConsumer {
    protected static final String SERVICE_LOGOUT = "logoff";

    private Properties prop;

    /**
     * Gathers the authentication service's URL parameter list using the service id, user id
     * and password.  that will be used to retrieve the user's
     * profile. It is required that the parameter list include the client
     * action, the user's login id and the name of the orginating application.
     * The service id, login id and password is required to invoke remote services.  If the
     * service id is not provided by the client, the default serve, "login", will be used.
     *
     * @throws ActionHandlerException
     *             When either the service id, login Id or password are not provided.
     */
    public void receiveClientData() throws ActionHandlerException {
        this.prop = this.receiveBasicClientData();
        if (prop.getProperty(GeneralConst.REQ_SERVICEID) == null) {
            prop.setProperty(GeneralConst.REQ_SERVICEID, UserLogoutServiceConsumer.SERVICE_LOGOUT);
        }
        // Verify that login id exists as an input parameter of the URL.
        if (this.prop.getProperty(AuthenticationConst.AUTH_PROP_USERID) == null) {
            this.msg = "User Authentication failed: Login Id is required when logging into system";
            throw new ActionHandlerException(this.msg);
        }
        this.setServiceData(this.prop);
    }

    /**
     * Basically, invalidates the user's session, hence, logging the user out of the system.
     *
     * @param results InputStream
     * @throws ServiceHandlerException
     */
    public void processResults(Object results) throws ServiceHandlerException {
	super.processResults(results);
	Object obj = this.getServiceResults();
        if (obj instanceof Exception) {
            throw new ServiceHandlerException(((Exception) obj).getMessage());
        }
        this.request.getSession().removeAttribute(RMT2ServletConst.SESSION_BEAN);
	this.request.getSession().invalidate();
    }
}
