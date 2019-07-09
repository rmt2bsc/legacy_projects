package com.services.resources;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.http.service.AbstractHttpUrlWebServiceImpl;

import com.api.security.user.ApplicationApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.AppRole;
import com.bean.Application;
import com.bean.UserLogin;
import com.bean.VwAppRoles;
import com.bean.VwUserAppRoles;

import com.bean.bindings.JaxbAuthenticationFactory;

import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2Exception;
import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RSAuthenticationUserAppRoles;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.UserAppRolesType;

import com.xml.schema.misc.PayloadFactory;

/**
 * HTTP URL Web Service for retrieving one or more user application roles from the database as XML
 * based on custom selection criteria input from the client. Optionally, the client to
 * send the following input key/value pairs to build selection criteria:
 * <ul>
 * <li>UserName</li>:&nbsp;The user's login.
 * <li>ApplicationId</li>:&nbsp;The id of the application to retrieve user's roles.
 * </ul>
 * <p>
 * 
 * @author RTerrell
 * 
 */
public class HttpUserAppRolesService extends AbstractHttpUrlWebServiceImpl {
    
    public static final String SERVICE_GETSERVICES = "Services.RQ_authentication_user_app_roles";

    private static Logger logger = Logger.getLogger("HttpUserAppRolesService");

    private int appId;

    private String userAppLoginId;
    

    /**
     * Default constructor which instantiates the logger.
     * 
     * @throws SystemException
     */
    public HttpUserAppRolesService() throws SystemException {
        logger.info("Logger for HttpUserAppRolesService is initialized");
    }

    /**
     * Accepts the selection criteria from the request which will be used to
     * retrieve one or more user resources.  Examine the user's request object
     * for the following parameters to obtain selection criteria data that 
     * can be applied to the query:
     * 
     * <ul>
     *    <li>Name</li>:&nbsp;The name of the resource to obtain.
     *    <li>ResourceTypeId</li>:&nbsp;The resource type id to obtain.
     *    <li>ResourceSubtypeId</li>:&nbsp;The resource sub type id to obtain.
     * </ul>     
     * 
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
        this.userAppLoginId = this.request.getParameter("UserAppLoginId");
        if (this.userAppLoginId == null) {
            this.msg = "Login id for User-Application roles must be supplied";
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        try {
            String temp = this.request.getParameter("ApplicationId");
            if (temp == null) {
                this.msg = "Application Id must be supplied";
                logger.log(Level.ERROR, this.msg);
                throw new ActionHandlerException(this.msg);
            }
            this.appId = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Application Id is invalid";
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
    }

    /**
     * Retrieves user resource data based on the user's input criteria. The
     * results of the query are stored internally as XML.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ApplicationApi appApi = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector());
        UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
        UserLogin user;
        Application app;
        List <VwUserAppRoles> assignedRoles = null;
        List <VwAppRoles> revokedRoles = null;

        try {
            // Get user xml data
            user = (UserLogin) userApi.findUserByUserName(this.userAppLoginId);
            
            // Get application xml data
            app = (Application) appApi.findApp(this.appId);

            // setup user criteria
            UserLogin userCriteria = UserFactory.createUserLogin();
            userCriteria.setLoginId(user.getLoginId());

            // Setup application-role criteria
            AppRole appRoleCriteria = UserFactory.createAppRole();
            appRoleCriteria.setAppId(this.appId);

            // Get assigned roles as xml
            try {
                assignedRoles = (List <VwUserAppRoles>) appApi.getAppRoleAssigned(userCriteria, appRoleCriteria);
            }
            catch (Exception e) {
                assignedRoles = null;
            }
            
            // Get revoked roles as xml.
            try {
                revokedRoles = (List <VwAppRoles>) appApi.getAppRoleRevoked(userCriteria, appRoleCriteria);               
            }
            catch (Exception e) {
                revokedRoles = null;
            }

            // Arrange the payload.
            this.outData = this.buildResponsePayload(user, app, assignedRoles, revokedRoles);
        }
        catch (RMT2Exception e) {
            throw new ActionHandlerException(e);
        }
        finally {
            appApi.close();
            userApi.close();
            tx.close();
            appApi = null;
            userApi = null;
            tx = null;
        }
        return;
    }
    
    
    /**
     * Builds the payload for the  RS_authentication_user_resource_access response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(UserLogin user, Application app, List <VwUserAppRoles> assigned, List <VwAppRoles> revoked) {
	String responseMsgId = "RS_authentication_user_app_roles";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationUserAppRoles ws = f.createRSAuthenticationUserAppRoles();

	// Setup header
	HeaderType header = PayloadFactory.createHeader(responseMsgId, "http", "RESPONSE", "");
	ws.setHeader(header);
	
	this.msg = "";
	if (user == null) {
	    this.msg = "user inforomation was not provided for response; ";
	}
	if (app == null) {
            this.msg = "application inforomation was not provided for response; ";
        }
	if (!this.msg.equals("")) {
	    ReplyStatusType rst = PayloadFactory.createReplyStatus(true, null, this.msg, 1);
	    ws.setReplyStatus(rst);
	}

	// Adapt user instance, list of granted roles, and list of revoked roles to JAXB instance
	UserAppRolesType uart = JaxbAuthenticationFactory.getUserAppRolesTypeInstance(user, app, assigned, revoked);
	ws.setRoleData(uart);

	// Marshall XML message instance.
	String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }
}
