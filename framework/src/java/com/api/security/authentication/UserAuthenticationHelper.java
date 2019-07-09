package com.api.security.authentication;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.messaging.MessageException;
import com.api.messaging.ResourceFactory;
import com.api.messaging.webservice.soap.client.SoapClientWrapper;
import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2Base;
import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import com.remoteservices.ServiceConsumer;
import com.remoteservices.http.HttpRemoteServicesConsumer;
import com.util.SystemException;

/**
 * This implemntation provides a common basic user authentication services that can be
 * shared by the entire library.
 * 
 * @author Roy Terrell
 * @deprecated Should use a descendent of {@link com.api.security.authentication.AbstractClientAuthentication AbstractClientAuthentication}
 * 
 */
public abstract class UserAuthenticationHelper extends RMT2Base {

    private static Logger logger = Logger.getLogger("UserAuthenticationHelper");

    /**
     * Default constructor which initializes the logger.
     * 
     */
    private UserAuthenticationHelper() {
        super();
    }

    /**
     * Authenticates a remote user who is attempting to gain access to a
     * web application or resources of a web application from another context. 
     * Authenticating a remote user involves verifying that the user exists in 
     * the system and ensuring that the user is signed onto the system. A session 
     * bean object is identified to indicate that the user is signed on the system.
     * It is required that the login id of the user is passed to this method call
     * via the request object as either a request parameter or an attribute.
     * 
     * @param request
     *            The HttpServletRequest object.
     * @param response
     *            The HttpServletResponse object.
     * @return The user's session bean as an arbitrary object.
     * @throws SystemException 
     *            Login id was not included in the request object, error in remote 
     *            service call, or user is not logged into one or more applications.
     * @deprecated  Will be removed in future releases.            
     */
    public static Object authenticateRemoteUser(Request request, Response response) throws SystemException {

        String appName = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
        String msg = "Session Id for application, " + appName + ": " + request.getSession().getId();
        logger.log(Level.INFO, msg);
        HttpRemoteServicesConsumer service = new HttpRemoteServicesConsumer();

        //  Check for login id in the sessionBean, request parameter, and request attribute scopes.
        RMT2SessionBean sessionBean = (RMT2SessionBean) request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        String loginId = null;
        if (sessionBean != null) {
            loginId = sessionBean.getLoginId();
        }
        if (loginId == null) {
            loginId = request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
        }
        if (loginId == null) {
            loginId = (String) request.getAttribute(AuthenticationConst.AUTH_PROP_USERID);
        }
        if (loginId == null) {
            msg = "Remote User Authentication failed:  Login id must be supplied within the user\'s request object.";
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        try {
            // Authenticate remote user
            service.processRequest(request, response, ServiceConsumer.REMOTE_AUTH_SERVICE);
        }
        catch (ActionHandlerException e) {
            throw new SystemException(e.getMessage());
        }
        // User is logged into the system if bean is not null.
        Object bean = request.getAttribute(ServiceConsumer.SERVICE_RESULTS);
        if (bean == null) {
            throw new SystemException("Remote Application access denied...User is not logged into the system: " + loginId);
        }
        return bean;
    }

    /**
     * Verifies whether the user is logged on to a particlar application using the 
     * service identified as, {@link com.remoteservices.ServiceConsumer.CHECK_APP_LOGIN_SERVICE CHECK_APP_LOGIN_SERVICE}
     * 
     * @param request The request object
     * @param response The response object
     * @param appName The target application. 
     * @return true when user is logged on to appName.  Otherwise, false is returned when 
     *         the user is not logged on to the application or if the return value is not 
     *         of Boolean.
     * @throws SystemException
     */
    public static boolean isUserLoggedOn(Request request, Response response, String appName) throws SystemException {

        HttpRemoteServicesConsumer service = new HttpRemoteServicesConsumer();
        String loginId = request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
        request.setAttribute(AuthenticationConst.AUTH_PROP_MAINAPP, appName);
        try {
            // Authenticate remote user
            service.processRequest(request, response, ServiceConsumer.CHECK_APP_LOGIN_SERVICE);
        }
        catch (ActionHandlerException e) {
            throw new SystemException(e.getMessage());
        }
        // User is logged into the system if bean is not null.
        Object bean = request.getAttribute(ServiceConsumer.SERVICE_RESULTS);
        if (bean == null) {
            throw new SystemException("Remote Application access denied...User is not logged into the system: " + loginId);
        }
        if (bean instanceof RMT2SessionBean) {
            return true;
        }
        if (bean instanceof Boolean) {
            return ((Boolean) bean).booleanValue();
        }
        return false;
    }
}