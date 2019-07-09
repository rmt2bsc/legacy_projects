package com.api.security;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Properties;

import com.action.ActionHandlerException;


import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.SessionBeanManager;
import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2SessionBean;

import com.constants.GeneralConst;

import com.remoteservices.ServiceConsumer;
import com.remoteservices.ServiceHandlerException;

import com.remoteservices.http.AbstractHttpClientAction;

import com.util.SystemException;


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
public class UserLoginServiceConsumer extends AbstractHttpClientAction implements ServiceConsumer {
    protected static final String SERVICE_LOGIN = "login";
    private Logger logger;
    private Properties prop;

    /**
     * Gathers the authentication service's URL parameter list using the service id, user id
     * and password.  that will be used to retrieve the user's
     * profile. It is required that the parameter list include the client
     * action, the user's login id and the name of the orginating application.
     * The service id, login id and password is required to invoke remote services.  If the
     * service id is not provided by the client, the default service  id, "login", will be used.
     *
     * @throws ActionHandlerException
     *             When either the service id, login Id or password are not provided.
     */
    public void receiveClientData() throws ActionHandlerException {
        this.prop = this.receiveBasicClientData();
        if (prop.getProperty(GeneralConst.REQ_SERVICEID) == null) {
            prop.setProperty(GeneralConst.REQ_SERVICEID, UserLoginServiceConsumer.SERVICE_LOGIN);
        }
        // Verify that login id exists as an input parameter of the URL.
        if (this.prop.getProperty(AuthenticationConst.AUTH_PROP_USERID) == null) {
            this.msg = "User Authentication failed: Login Id is required when logging into system";
            throw new ActionHandlerException(this.msg);
        }
        // Verify that user's password has been provided
        if (this.prop.getProperty(AuthenticationConst.AUTH_PROP_PASSWORD) == null) {
            this.msg = "User Authentication failed: Password is required when logging into system";
            throw new ActionHandlerException(this.msg);
        }
        this.setServiceData(this.prop);
    }

    /**
     * Obtains the user's session bean created by the authentication service and adds
     * the sessin bean to the user's web session.
     *
     * @param results InputStream
     * @throws ServiceHandlerException
     */
    public void processResults(Object results) throws ServiceHandlerException {
	super.processResults(results);
	logger = Logger.getLogger("UserLoginServiceConsumer");
	try {
	    Object obj = this.getServiceResults();
	    if (obj instanceof Exception) {
		throw new SystemException(((Exception) obj).getMessage());
	    }
	    RMT2SessionBean sessionBean = (RMT2SessionBean) obj;
	    this.logger.log(Level.INFO, "Session ID from Authentication App: " + sessionBean.getSessionId());
            String appId = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
            sessionBean.setOrigAppId(appId);
            SessionBeanManager sbm = SessionBeanManager.getInstance(this.request);
            sbm.addSessionBean(sessionBean);
            this.logger.log(Level.INFO, "Session ID from Target App: " + sessionBean.getSessionId());
	}
	catch (SystemException e) {
	    e.printStackTrace();
	    throw new ServiceHandlerException(e.getMessage());
	}
    }

}
