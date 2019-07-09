package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractExternalServerAction;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.UserAuthenticationException;

import com.api.security.user.ApplicationApi;
import com.api.security.user.UserFactory;

import com.bean.Application;
import com.bean.Roles;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Service for retrieving application-role data from the database as XML.
 * Requires the client to send the following key/value pairs as input:
 * <ul>
 *     <li>An instance of {@link com.bean.Application Application} and/or {@link com.bean.Roles Roles}</li>
 * </ul>
 * <p>
 * 
 * @author RTerrell
 * @deprecated Use {@link com.services.resources.HttpUserAppRolesService HttpUserAppRolesService} class
 * 
 */
public class AppRoleProducer extends AbstractExternalServerAction {
    private Logger logger;
    private int appId;
    private int roleId;

    public AppRoleProducer() throws SystemException {
        this.logger = Logger.getLogger("AppRoleProducer");
    }

    /**
     * Retrieves {@link com.bean.VwAppRoles VwAppRoles} in XML format based on 
     * application and roles selection criteria.
     *  
     * @throws ServiceHandlerException
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
        ApplicationApi api = UserFactory.createAppXmlApi((DatabaseConnectionBean) tx.getConnector());
        Application app = UserFactory.createXmlApplication();
        Roles role = UserFactory.createXmlRole();
        
        app.setAppId(this.appId);
        role.setRoleId(this.roleId);
        
        String data = null;
        try {
            data = (String) api.getAppRole(app, role);
            this.outData = data;
            logger.log(Level.INFO, data);
        }
        catch (UserAuthenticationException e) {
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Accepts the application id and/or role id from the request which will 
     * be used to retrieve the Application-Role data.
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
        try {
            this.appId = Integer.parseInt(this.request.getParameter("APP_ID"));    
        }
        catch (NumberFormatException e) {
           this.appId = 0; 
        }
        
        try {
            this.roleId = Integer.parseInt(this.request.getParameter("ROLE_ID"));    
        }
        catch (NumberFormatException e) {
           this.roleId = 0; 
        }
    }
}