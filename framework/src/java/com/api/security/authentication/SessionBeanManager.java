package com.api.security.authentication;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.config.HttpSystemPropertyConfig;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2Base;
import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Session;

import com.util.SystemException;

/**
 * Configures an existing user's session bean object with data specific 
 * to the local of the application context.   
 *   
 * @author RTerrell
 * 
 */
public class SessionBeanManager extends RMT2Base {
    private Request request;

    private SessionBeanManager(Request request) {
        this.request = request;
    }

    /**
     * Creates an instance of SessionBeanManager using a HttpServletRequest.
     * 
     * @param request THe user's request.
     * @return SessionBeanManager.
     */
    public static SessionBeanManager getInstance(Request request) {
        SessionBeanManager manager = new SessionBeanManager(request);
        return manager;
    }

    /**
     * Populates the usre's Session Bean object with information that is domicile 
     * to the user's application context and adds the session bean to the user's 
     * web session.  A HttpSession object is obtained and associated with the 
     * session bean.  It is required that the session bean is valid and contains 
     * a valid user's login id.  A temporary work area is created in the file system 
     * for the user to store and manage state data pertaining to the application.
     * 
     * @return RMT2SessionBean A valid user sessio bean.
     * @throws SystemException 
     *           When the session bean is invalid or does not contain a valid login 
     *           id and source application identifier.
     */
    public void addSessionBean(RMT2SessionBean bean) throws SystemException {
        // At this point a session should already be assigned. If not, throw an
        // exception.
        Session session = request.getSession(false);
        String msg;
        if (session == null) {
            msg = "SessionBean setup failed:  The user's session is invalid.";
            Logger.getLogger("SessionBeanManager").log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        this.validateSessionBean(bean);
        bean.setSessionId(session.getId());

        // Initialize User's Session Bean
        bean.initSessionBean(request);

        // Obtain the timeout interval
        this.configureSessionTimeOut(bean);

        // Create user's work area in the file system where the web server
        // resides
        bean.createUserWorkArea();

        // Begin to configure HttpSession object with data from the user's session bean.
        session.setMaxInactiveInterval(bean.getSessionMaxInactSecs());
        // Store the Session Bean on user's session
        session.setAttribute(RMT2ServletConst.SESSION_BEAN, bean);
        // Store a RMT2TagQueryBean on user's session
        session.setAttribute(RMT2ServletConst.QUERY_BEAN, new RMT2TagQueryBean());

        HttpServletRequest nativeReq = null;
        if (request.getNativeInstance() != null) {
            nativeReq = (HttpServletRequest) request.getNativeInstance();
            bean.setLocale(nativeReq.getLocale().getDisplayName());
        }
        bean.setRemoteAddress(request.getRemoteAddr());
        bean.setRemoteHost(request.getRemoteHost());
        bean.setRemotePort(request.getRemotePort());
        bean.setScheme(request.getScheme());
        bean.setServerName(request.getServerName());
        bean.setServerPort(request.getServerPort());
        bean.setServerProtocol(request.getProtocol());
        bean.setServletContext(request.getContextPath());
        return;
    }

    /**
     * validates the user's session bean.
     * 
     * @param bean The session bean to be validated.
     * @throws SystemException
     *           When the session bean is invalid or does not contain a valid login 
     *           id and source application identifier.
     */
    protected void validateSessionBean(RMT2SessionBean bean) throws SystemException {
        if (bean == null) {
            msg = "The user session bean object is invalid";
            Logger.getLogger("SessionBeanManager").log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        if (bean.getLoginId() == null) {
            msg = "The user session object must have login id";
            Logger.getLogger("SessionBeanManager").log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        if (bean.getOrigAppId() == null) {
            msg = "The user session object must have the name of the source application";
            Logger.getLogger("SessionBeanManager").log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Initializes the session bean with default session timeout values obtained 
     * from the application's configuration properties file.
     *   
     * @param bean The session bean to be initialized.
     * @throws SystemException If the timeout value is not obtainable.
     */
    protected void configureSessionTimeOut(RMT2SessionBean bean) throws SystemException {
        int timeoutInterval = 0;
        String temp = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_SESSION_TIMEOUT);
        try {
            timeoutInterval = new Integer(temp).intValue();
            if (timeoutInterval < AuthenticationConst.MIN_TIMEOUT) {
                timeoutInterval = AuthenticationConst.MIN_TIMEOUT;
            }
        }
        catch (NumberFormatException e) {
            // If interval does not exist or is invalid, default to 1 hour
            timeoutInterval = AuthenticationConst.DEFAULT_TIMEOUT;
        }
        finally {
            bean.setSessionMaxInactSecs(timeoutInterval);
        }
    }
}
