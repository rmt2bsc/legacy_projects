package com.action.userlogin;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;

import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.IOException;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.SessionBeanManager;

import com.bean.RMT2SessionBean;

import com.constants.GeneralConst;

import com.util.SystemException;

import com.remoteservices.http.HttpClient;
import com.remoteservices.http.HttpException;

import com.util.RMT2File;
import com.util.RMT2Utility;

/**
 * This class provides action handlers to authenticate a user's login
 * credentials.
 * 
 * @author Roy Terrell
 * 
 */
public class UserLoginAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LOGIN = "Security.Authentication.login";
    private static final String COMMAND_LOGOFF = "Security.Authentication.logoff";
    
    private Logger logger;
    


    /**
     * Default constructor which initializes className.
     * 
     */
    public UserLoginAction() {
        super();
        logger = Logger.getLogger("UserLoginAction");
    }

    /**
     * Constructor which ensures that a valid login datasource is created, and
     * the login id and password is not null.
     * 
     * @param _dataSource
     *            A string that represents the login datasource name
     * @param _conPool
     *            The database connection api ({@link DataProviderConnectionApi})
     *            which contains the connection pool.
     * @param _conBean
     *            The user's connection object {@link DatabaseConnectionBean}
     * @param parms
     *            The user credentials such as user Id and password. This data
     *            is contained as a Hashtable.
     * @throws LoginException
     */
    public UserLoginAction(HttpServletRequest request,
            HttpServletResponse response, String command)
            throws ActionHandlerException {
        this();
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor which
     * the invocation of this method should be from within the descendent.
     * 
     * @param _context
     *            the servet context
     * @param _request
     *            the http servlet request
     * @throws SystemException
     */
    protected void init(ServletContext _context, HttpServletRequest _request)
            throws SystemException {
        this.context = _context;
        this.request = _request;
        logger = Logger.getLogger("UserLoginAction");
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response, String command)
            throws ActionHandlerException {
        try {
            this.init(null, request);
        }
        catch (SystemException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionHandlerException(e.getMessage());
        }

        if (command.equalsIgnoreCase(UserLoginAction.COMMAND_LOGIN)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(UserLoginAction.COMMAND_LOGOFF)) {
            this.deleteData();
        }
    }

    public void add() throws ActionHandlerException {
        String url = "http://rmtdaldev03:8080/authentication/loginProcessor";
        Properties loginData = new Properties();

        // Set client action
        loginData.put(GeneralConst.REQ_CLIENTACTION, "login");
        // Set user id
        String temp = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
        loginData.put(AuthenticationConst.AUTH_PROP_USERID, temp);
        // Set password
        temp = this.request.getParameter(AuthenticationConst.AUTH_PROP_PASSWORD);
        loginData.put(AuthenticationConst.AUTH_PROP_PASSWORD, temp);

        // Set session id
        temp = this.request.getSession().getId();
        loginData.put(AuthenticationConst.AUTH_PROP_SESSIONID, temp);

        // Set application id
        try {
            temp = RMT2File.getPropertyValue(RMT2Utility.CONFIG_SYSTEM, AuthenticationConst.AUTH_PROP_MAINAPP);
            loginData.put(AuthenticationConst.AUTH_PROP_MAINAPP, temp);
        }
        catch (SystemException e) {
            throw new ActionHandlerException(e.getMessage());
        }

        try {
            HttpClient http = new HttpClient(url);
            InputStream in = (InputStream) http.sendPostMessage(loginData);
            ObjectInputStream ois = new ObjectInputStream(in);
            if (ois != null) {
                RMT2SessionBean sessionBean = (RMT2SessionBean) ois.readObject();
                String appId = RMT2File.getPropertyValue(RMT2Utility.CONFIG_SYSTEM, "appcode");
                sessionBean.setOrigAppId(appId);
                SessionBeanManager sbm = SessionBeanManager.getInstance(this.request);
                sbm.addSessionBean(sessionBean);
                logger.log(Level.INFO, "Session ID from Authentication App: " + sessionBean.getSessionId());
            }
        }
        catch (HttpException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        catch (SystemException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        catch (IOException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        catch (ClassNotFoundException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

    }

    public void delete() throws ActionHandlerException {
        String url = "http://rmtdaldev03:8080/authentication/loginProcessor";
        Properties loginData = new Properties();

        // Set client action
        loginData.put(GeneralConst.REQ_CLIENTACTION, "logoff");
        // Get user id
        String temp = this.request
                .getParameter(AuthenticationConst.AUTH_PROP_USERID);
        loginData.put(AuthenticationConst.AUTH_PROP_USERID, temp);

        try {
            HttpClient http = new HttpClient(url);
            InputStream in = (InputStream) http.sendPostMessage(loginData);
            ObjectInputStream ois = new ObjectInputStream(in);
            this.request.getSession().invalidate();
        }
        catch (HttpException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        catch (IOException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
    }

    public void edit() throws ActionHandlerException {

    }

    public void save() throws ActionHandlerException {

    }

    public void receiveClientData() throws ActionHandlerException {
        return;
    }

    public void sendClientData() throws ActionHandlerException {
        return;
    }

}