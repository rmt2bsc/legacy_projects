package com.api.security.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.config.HttpSystemPropertyConfig;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.RMT2SessionBean;

import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2Base;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Session;
import com.controller.stateless.scope.HttpVariableScopeFactory;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * An implementation of UserSecurity interface which provides functionality for a 
 * client to remotely log in - log out users and perform user authorization across 
 * application contexts.
 * 
 * @author RTerrell
 */
public abstract class AbstractClientAuthentication extends RMT2Base {

    protected Request request;

    protected Response response;

    protected Properties reqProp;

    protected Object sessionToken;

    private static Logger logger = Logger.getLogger(AbstractClientAuthentication.class);

    /**
     * Default constructor hat initializes the logger.
     *
     */
    public AbstractClientAuthentication() {
        super();
        logger.log(Level.INFO, "AbstractClientAuthentication initialized");
    }

    /**
     * Constructs a AbstractClientAuthentication that will possess an application context, 
     * the user's request object, and user's response object.
     * 
     * @param request The user's request.
     * @param response The user's response.
     */
    public void init(Request request, Response response) {
        this.request = request;
        this.response = response;
        this.reqProp = this.getRequestParms();
    }

    public void close() {
        return;
    }

    private Properties getRequestParms() {
        // As a reference to legacy logic, check out HttpRemoteServicesConsumer class

        Properties prop = new Properties();

        String loginId;
        String pw;
        String appName;
        RMT2SessionBean sessionBean = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        // Set the client action which actually is the service id.\
        String servId = this.request.getParameter("clientAction");
        String loginServId = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_LOGINSRC);

        // Nullify sessionBean when logging into an application
        if (servId != null && servId.equalsIgnoreCase(loginServId)) {
            sessionBean = null;
        }

        // Look for the login id and application id from the request 
        // as either a parameter or an attribute in the stated order.
        if (sessionBean == null) {
            loginId = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
            if (loginId == null || loginId.equals("")) {
                loginId = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_USERID);
            }
            pw = this.request.getParameter(AuthenticationConst.AUTH_PROP_PASSWORD);
            if (pw == null || pw.equals("")) {
                pw = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_PASSWORD);
            }
            appName = this.request.getParameter(AuthenticationConst.AUTH_PROP_MAINAPP);
            if (appName == null || appName.equals("")) {
                appName = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_MAINAPP);
                if (appName == null) {
                    try {
                        appName = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
                    }
                    catch (Exception e) {
                        appName = null;
                    }
                }
            }
        }
        else {
            loginId = sessionBean.getLoginId();
            appName = sessionBean.getOrigAppId();
            pw = this.request.getParameter(AuthenticationConst.AUTH_PROP_PASSWORD);
            if (pw == null || pw.equals("")) {
                pw = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_PASSWORD);
            }
        }

        // Set user's login id      
        if (loginId != null) {
            prop.setProperty(AuthenticationConst.AUTH_PROP_USERID, loginId);
        }

        // Set user's password, if available        
        if (pw != null) {
            prop.setProperty(AuthenticationConst.AUTH_PROP_PASSWORD, pw);
        }

        // Get source application name
        if (appName != null) {
            prop.setProperty(AuthenticationConst.AUTH_PROP_MAINAPP, appName);
        }

        // Set session id
        String temp = this.request.getSession().getId();
        prop.put(AuthenticationConst.AUTH_PROP_SESSIONID, temp);

        return prop;
    }

    /**
     * @return the reqProp
     */
    public Properties getReqProp() {
        return reqProp;
    }

    /**
     * @return the sessionToken
     */
    public Object getSessionToken() {
        return sessionToken;
    }

    /**
     * Verifies if any of the required roles in <i>roles</i> match up with any of the roles 
     * setup in the user's session bean instance.
     * 
     * @param roles
     *          A String containing a list of required roles which each role is delimited by a comma.
     * @return
     *         true if one or more of the required roles matches the user's assigned roles.   
     *         Othewise, false is returned.
     * @throws AuthorizationException
     *           when <i>roles</i> is null.
     *           
     *         AuthenticationException
     *           <ul>
     *              <li>Invalid requrest instance is found</li>
     *              <li>Session instance is unobtainable</li>
     *              <li>The user's session bean token does not exist on the user's session</li>
     *           </ul>
     */
    public boolean isAuthorized(String roles) throws AuthorizationException, AuthenticationException {
        if (roles == null) {
            this.msg = "Unable to authorize user due the list of required roles are invalid";
            logger.error(this.msg);
            throw new AuthorizationException(this.msg);
        }
        if (this.request == null) {
            this.msg = "Unable to authorize user due the invalid request instance";
            logger.error(this.msg);
            throw new AuthenticationException(this.msg);
        }
        Session session = this.request.getSession(false);
        if (session == null) {
            this.msg = "Unable to authorize user due user's session is not established or unavailable";
            logger.error(this.msg);
            throw new AuthenticationException(this.msg);
        }

        RMT2SessionBean sessionBean = (RMT2SessionBean) session.getAttribute(RMT2ServletConst.SESSION_BEAN);
        if (sessionBean == null) {
            this.msg = "Unable to authorize user due the session bean token is not found on the user's session";
            logger.error(this.msg);
            throw new AuthenticationException(this.msg);
        }

        // If for some strange reason that the required roles does not exist at this point, 
        // flag the user as authorized.  May change this logic in the future.
        List<String> requiredRoles = RMT2String.getTokens(roles, ",");
        if (requiredRoles.size() <= 0) {
            return true;
        }
        // At this point we know we have at least one required role.  
        // Indicate user as unathorized if no roles exist on the user's session.
        if (sessionBean.getRoles() == null || sessionBean.getRoles().size() == 0) {
            return false;
        }
        // Match up the roles.
        List<String> matches = this.getMatchingRoles(sessionBean.getRoles(), requiredRoles);
        return (matches.size() > 0);
    }

    /**
     * Determines if the user possesses one or more required roles and builds a list 
     * every user role that matches with a required role.
     * 
     * @param userRoles 
     *         The roles assigned to the user.
     * @param requiredRoles 
     *         The roles the user must authorized for.
     * @return A List of roles in code name form that match one or more of the 
     *         required roles.
     */
    private List<String> getMatchingRoles(List<String> userRoles, List<String> requiredRoles) {
        List<String> matchedRoles = new ArrayList<String>();
        for (int ndx = 0; ndx < requiredRoles.size(); ndx++) {
            String reqRole = (String) requiredRoles.get(ndx);
            for (int ndx2 = 0; ndx2 < userRoles.size(); ndx2++) {
                String userRole = (String) userRoles.get(ndx2);
                if (userRole.trim().equalsIgnoreCase(reqRole.trim())) {
                    matchedRoles.add(reqRole.trim());
                }
            }
        }
        return matchedRoles;
    }

    /**
     * Creates a session bean for the user and stores it on the session context.
     * 
     * @return The new created {@link com.api.security.authentication.RMT2SessionBean RMT2SessionBean }
     * @throws SystemException
     */
    protected RMT2SessionBean createSessionBean() throws SystemException {
        String appId = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
        String loginId = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);

        RMT2SessionBean sessionBean;
        try {
            sessionBean = AuthenticationFactory.getSessionBeanInstance(loginId, appId);
            sessionBean.setOrigAppId(appId);
            sessionBean.setLoginId(loginId);
            SessionBeanManager sbm = SessionBeanManager.getInstance(this.request);
            sbm.addSessionBean(sessionBean);
            logger.info("User, " + sessionBean.getLoginId() + ", remotely signed on application, " + appId + ", successfully!");
            return sessionBean;
        }
        catch (AuthenticationException e) {
            throw new SystemException(e);
        }
    }
    
    /**
     * The new SOAP logout version.
     * 
     * @return
     * @throws LogoutException
     */
    protected RMT2SessionBean removeSession() throws LogoutException {
	RMT2SessionBean bean = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        try {
            this.request.getSession().removeAttribute(RMT2ServletConst.SESSION_BEAN);
            this.request.getSession().invalidate();
            return bean;
        }
        catch (Exception e) {
            throw new LogoutException(e);
        }
    }
}
