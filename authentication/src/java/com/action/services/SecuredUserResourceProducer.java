package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractExternalServerAction;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.UserAuthenticationException;
import com.api.security.user.ResourceFactory;
import com.api.security.user.ResourceApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.UserLogin;
import com.bean.VwUserResourceAccess;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Service for managing secured assigned and/or revoked resources pertaining to a given user. 
 * The data is retrieved from the database as XML based on custom selection criteria input 
 * from the client.
 * <p>  
 * The format of the XML value returned can be optionally based on RSPayload feed described
 * in {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction}.
 * It is required of the client to send the following input key/value pairs to 
 * build selection criteria:
 * <ul>
 * <li>Login Id:&nbsp;The user's login id within the realm of the secured user resource page.</li>
 * <li>ResourceTypeId:&nbsp;The resource type id to obtain.</li>
 * <li>ResourceSubtypeId:&nbsp;The resource sub type id to obtain.</li>
 * </ul>
 * <p>
 * This service is only concerned with those resources that configured to be 
 * secured, meaning these services can only be accessed based on certain 
 * security measures being met.
 * 
 * @author RTerrell
 * @deprecated Will be removed in future versions.   Use {@link com.services.resources.UserResourceService UserResourceService}
 * 
 */
public class SecuredUserResourceProducer extends AbstractExternalServerAction {
    private Logger logger;
    private int typeId;
    private int subTypeId;
    private String userRsrcLoginId;
    private UserLogin user;

    /**
     * Default constructor which instantiates the logger.
     * 
     * @throws SystemException
     */
    public SecuredUserResourceProducer() throws SystemException {
        this.logger = Logger.getLogger("SecuredUserResourceProducer");
    }

    /**
     * Accepts the selection criteria from the request which will be used to
     * retrieve assigned and revoked resources that pertains the a given user. 
     * The user's request object is examined for the following data items which 
     * will serve as parameters for the service call:
     * 
     * <ul>
     * <li>Login Id:&nbsp;The user's login id.</li>
     * <li>ResourceTypeId:&nbsp;The resource type id to obtain.</li>
     * <li>ResourceSubtypeId:&nbsp;The resource sub type id to obtain.</li>
     * </ul>
     * 
     * @throws AbstractActionHandler 
     *           When the login id, resource type id, or the resource sub type 
     *           id are not supplied by the client, or when either the resource 
     *           type or the resource sub type are invalid values (not numeric), 
     *           or when the user's profile is unobtainable using the login id. 
     */
    public void receiveClientData() throws ActionHandlerException {
        this.userRsrcLoginId = this.request.getParameter("UserRsrcLoginId");
        if (this.userRsrcLoginId == null) {
            this.msg = "The User Resource Login id must be supplied";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        // Get resource type id from client
        try {
            String temp = this.request.getParameter("ResourceTypeId");
            if (temp == null) {
                this.msg = "Resource Type Id must be supplied";
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionHandlerException(this.msg);
            }
            this.typeId = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Resource Type Id is invalid";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        // Get resource sub type id from client.
        try {
            String temp = this.request.getParameter("ResourceSubtypeId");
            if (temp == null) {
                this.msg = "Resource Sub Type Id must be supplied";
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionHandlerException(this.msg);
            }
            this.subTypeId = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Resource Sub Type Id is invalid";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        // Obtain user login object from the database using user's login id.
        DatabaseTransApi tx = DatabaseTransFactory.create();
        UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
        try {
            this.user = (UserLogin) userApi.findUserByUserName(this.userRsrcLoginId);
        }
        catch (UserAuthenticationException e) {
            throw new ActionHandlerException(e);
        }
        finally {
            userApi.close();
	    tx.close();
	    userApi = null;
	    tx = null;
	}
    }

    /**
     * Retrieves the user's assigned and revoked resources. The results of the query 
     * are stored internally as XML.
     * 
     * @throws ActionHandlerException if a problem occurs during service execution.
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
        ResourceApi api = ResourceFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector());
        VwUserResourceAccess criteria = ResourceFactory.createXmlUserAccess();
        Object assignedData = null;
        Object revokedData = null;
        StringBuffer xml = new StringBuffer(100);

        // Setup serach criteria
        criteria.setUserUid(this.user.getLoginId());
        criteria.setResrcSecured(1);
        criteria.setRsrcTypeId(this.typeId);
        criteria.setRsrcSubtypeId(this.subTypeId);
        
        try {
            assignedData = api.getUserResourceAssigned(criteria);
        }
        catch (Exception e) {
            assignedData = null;
        }
        try {
            revokedData = api.getUserResourceRevoked(criteria);
        }
        catch (Exception e) {
            revokedData = null;
        }
        if (assignedData != null) {
            xml.append(assignedData.toString());    
        }
        if (revokedData != null) {
            xml.append(revokedData.toString());    
        }
        this.outData = "<UserResources>" + xml.toString() + "</UserResources>";
        
        api.close();
	tx.close();
	api = null;
	tx = null;
	
        return;
    }

}
