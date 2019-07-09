package com.api.security.authentication;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;

import com.api.security.UserSecurity;

import com.api.security.pool.AppPropertyPool;

import com.constants.RMT2ServletConst;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * This common action handler is called upon from a descendent of the AbstractCommandServlet and is designed to drive 
 * authentication requests from the client side.  It instantiates and uses the appropriate authenticator assigned to 
 * manage various security tasks such as user login, user logout, resource authorization, and etc.   This action handler 
 * possesses the capability to dynamically determine and invoke the {@link com.api.security.UserSecurity UserSecurity}
 * implementation based on the setting of the application property, <i>authenticator</i>. 
 * <p>
 * <b>Required Configuration</b><br>
 * A few configuration chores must be performed in order for this handler to invoke the appropriate authenticator 
 * implementation for the UserSecurity interface.
 * <blockquote>
 * <ol>
 *    <li>The property, <i>authenticator</i>, must be setup in the web.xml configuration file of the web application, and 
 *        its value should be the class name of the authenticator implementation for the respective application.  The 
 *        authenticator implementation is required to derived the class, 
 *        {@link com.api.security.authentication.AbstractClientAuthentication AbstractClientAuthentication}.
 *    </li>
 *    <li>This class, <i>AuthenticationAction</i>, is required to be assigned as the handler for the "login" and "logout" 
 *        navigation rules in the Security.properties file for each web applictaion.
 *    </li>
 * </ol>
 * </blockquote>
 * <br>
 * <b>Authenticator Discovery</b><br>
 *  The process of discovering the appropriate authenticator for a specifi application is centered around the factory 
 *  method, {@link com.api.security.authentication.AuthenticationFactory#getAuthenticator(String) AuthenticationFactory.getAuthenticator(String)}.
 *  In the init method of this class, the AuthenticationFactory.getAuthenticator method is invoked with the intent of 
 *  creating the authenticator based on the class name assigned to the <i>authenticator</i> property regarding the above 
 *  configuration details.  This is accomplished by passing null as an input parameter to the getAuthenticator method.   
 *  Alternatively, the authenticator can be established by direct means, hence, bypassing the authenticator configuration/discovery 
 *  process by passing a fully qualified class name of the authenticator to the getAuthenticator method.  Of course, you will 
 *  need to derived this class and override the init() method in order for this concept to work.
 * <p>
 * When handling the "login" request, each implementation is responsible for obtaining 
 * a session bean token for the user so that it may be placed on the application session.   
 * The association of the session bean with the application session will signify that the 
 * user has successfully logged into the system.
 *  
 * @author RTerrell
 *
 */
public class AuthenticationAction extends AbstractActionHandler implements ICommand {

    private static final String COMMAND_LOGIN = "Security.Authentication.login";

    private static final String COMMAND_LOGOUT = "Security.Authentication.logoff";

    private static final String COMMAND_AUTHORIZE = "Security.Authentication.authorize";
    
    private static final String COMMAND_VERIFYAUTH = "Security.Authentication.verifyauthentication";

    private static Logger logger = Logger.getLogger("AuthenticationAction");

    private UserSecurity api;

    private String userId;
    
    private String loginId;
    
    private String appName;
    
    private String srcSessionId;

    
    
    /**
     * Default constructor which sets up the logger.
     *
     */
    public AuthenticationAction() {
        super();
    }

    /**
     * Processes one of the the requested commands: user login, user logout or authorize user.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        super.processRequest(request, response, command);
        logger.log(Level.INFO, "Processing authentication command, " + command);
        if (this.api == null) {
            this.msg = "Unable to authenticate user...Authenticator is not initialized";
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        this.api.init(request, response);
        RMT2SessionBean sessionBean = null;
        try {
            if (command.equalsIgnoreCase(AuthenticationAction.COMMAND_LOGIN)) {
                sessionBean = (RMT2SessionBean) this.api.doLogin();
                this.assignSessionBean(sessionBean);
            }
            else if (command.equalsIgnoreCase(AuthenticationAction.COMMAND_LOGOUT)) {
                this.api.doLogout();
            }
            else if (command.equalsIgnoreCase(AuthenticationAction.COMMAND_AUTHORIZE)) {
                return;
            }
            else if (command.equalsIgnoreCase(AuthenticationAction.COMMAND_VERIFYAUTH)) {
                sessionBean = this.api.getAuthentication();
                if (sessionBean != null) {
                    this.assignSessionBean(sessionBean);    
                }
            }
        }
        catch (Exception e) {
            this.msg = "Service command failed: " + command;
            logger.error(this.msg);
            throw new ActionHandlerException(this.msg, e);
        }
        finally {
            // Send results as XML to non-java based clients regardless of success or failure
            this.sendClientData();
            this.api.close();
            this.api = null;
            this.request.setAttribute(AuthenticationConst.AUTH_PROP_USERID, this.userId);

        }
    }

    /**
     * Creates a user security api instance based on the locale type of authentication.  
     * The locale type can be found in the application, AppParms.properties, file within 
     * the configuration area.
     * 
     * @param context the servet context
     * @param request the http servlet request
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        this.userId = request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
        try {
            this.api = AuthenticationFactory.getAuthenticator(null);
        }
        catch (LoginException e) {
            this.msg = "Initialization of common Authentication action hanlder failed";
            logger.error(this.msg);
            throw new SystemException(e);
        }
    }

    /**
     * Assins a RMT2SessionBean instance to the user's application session.  If the session 
     * bean is found to be invalid then an exception is produced.
     * 
     * @param sessionBean A valid {@link RMT2SessionBean} instance
     * @throws SystemException If <i>sessionBean</i> is invalid.
     */
    private void assignSessionBean(RMT2SessionBean sessionBean) throws SystemException {
        String appId = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
        sessionBean.setOrigAppId(appId);
        SessionBeanManager sbm = SessionBeanManager.getInstance(this.request);
        sbm.addSessionBean(sessionBean);
        logger.log(Level.INFO, "Session ID from Target App, " + appId + ": " + sessionBean.getSessionId());
        return;
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#receiveClientData()
     */
    @Override
    protected void receiveClientData() throws ActionHandlerException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#sendClientData()
     */
    @Override
    protected void sendClientData() throws ActionHandlerException {
        Object token = this.api.getSessionToken();
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, token);
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#save()
     */
    public void save() throws ActionHandlerException, DatabaseException {
        return;
    }

}
