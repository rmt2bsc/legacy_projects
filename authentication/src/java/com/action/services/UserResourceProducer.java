package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractInternalServerAction;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.user.ResourceFactory;
import com.api.security.user.ResourceApi;

import com.bean.UserResource;
import com.bean.VwUserResourceAccess;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Service for retrieving one or more user resources from the database as XML
 * based on custom selection criteria input from the client. Optionally, the
 * client to send the following input key/value pairs to build selection
 * criteria:
 * <ul>
 * <li>Name</li>:&nbsp;The name of the resource to obtain.
 * <li>ResourceTypeId</li>:&nbsp;The resource type id to obtain.
 * <li>ResourceSubtypeId</li>:&nbsp;The resource sub type id to obtain.
 * </ul>
 * <p>
 * 
 * @author RTerrell
 * @deprecated Will be removed from future versions.   Use {@link com.services.resources.ResourceService ResourceService}
 * 
 */
public class UserResourceProducer extends AbstractInternalServerAction {
//    private static final String SERVICE_GETSERVICES = "Services.Admin.getservices";
    private static final String SERVICE_GETSERVICES = "Services.RQ_authentication_resource";
    private static final String SERVICE_RESOURCE_ACCESS = "Services.getuserresource";
    private Logger logger;

    /**
     * Inner class tha manages user resource and resource accessibility data.
     * 
     * @author appdev
     *
     */
    protected class UserProfileCombo {
	private UserResource res;
	private VwUserResourceAccess access;
	
	private UserProfileCombo(UserResource resource, VwUserResourceAccess accessibility) {
	    res = resource;
	    access = accessibility;
	}
	
	protected UserResource getResource() {
	    return res;
	}
	protected VwUserResourceAccess getAccess() {
	    return access;
	}
    }
    

    /**
     * Default constructor which instantiates the logger.
     * 
     * @throws SystemException
     */
    public UserResourceProducer() throws SystemException {
        this.logger = Logger.getLogger("UserResourceProducer");
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
        UserResource resource = ResourceFactory.createXmlUserResource();
        VwUserResourceAccess access = ResourceFactory.createXmlUserAccess();
        try {
            ResourceFactory.packageBean(this.request, resource);
            ResourceFactory.packageBean(this.request, access);
            UserProfileCombo resCombo = new UserProfileCombo(resource, access);
            this.inData = resCombo;            
        }
        catch (SystemException e) {
            e.printStackTrace();
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
	ResourceApi api = ResourceFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector());
	UserProfileCombo combo = (UserProfileCombo) this.inData;
	
	if (combo == null) {
	    this.msg = "UserResourceProducer service, " + this.command + ", failed.  Input data was not supplied";
	    logger.log(Level.ERROR, this.msg);
	    this.outData = new ActionHandlerException(this.msg);
	    return;
	}
	
	try {
            if (UserResourceProducer.SERVICE_GETSERVICES.equalsIgnoreCase(this.command)) {
                this.outData = api.get(combo.getResource());
            }
            if (UserResourceProducer.SERVICE_RESOURCE_ACCESS.equalsIgnoreCase(this.command)) {
                this.outData = api.getAccessibility(combo.getAccess());
            }	    
	}
	catch (Exception e) {
	    this.outData = e;
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
	return;
    }
    
}
