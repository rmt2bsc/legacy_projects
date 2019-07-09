package com.api.security.user;

import com.api.BaseDataSource;

import com.bean.UserResource;
import com.bean.UserResourceType;
import com.bean.UserResourceSubtype;
import com.bean.VwResource;
import com.bean.VwResourceType;
import com.bean.VwUserResourceAccess;

import com.api.db.DatabaseException;

/**
 * An api that queries a data source for user resource data.   
 * 
 * @author appdev
 *
 */
public interface ResourceApi extends BaseDataSource {

    /**
     * Get a user resource object by its unique identifier.
     * 
     * @param uid The unique idenifier of the resource.
     * @return An arbitrary object representing the resource.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object get(int uid) throws DatabaseException;

    /**
     * Get a user resource object by its name.   
     * 
     * @param name THe name of the resource
     * @return An arbitrary object containing the results.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object get(String name) throws DatabaseException;

    /**
     * Get a user resource using a {@link com.bean.UserResource UserResource} object 
     * as the source of selection criteria.
     * 
     * @param criteria 
     * 	         An instance of UserResource which contains the property value 
     *           assignments neede to build the selection criteria 
     *           
     * @return An arbitrary object containig the results.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object get(UserResource criteria) throws DatabaseException;
    
    /**
     * Get extended resource data.
     * 
     * @param criteria {@link com.bean.VwResource VwResource}
     * @return A list arbitrary objects representing extended resource data. 
     * @throws DatabaseException
     */
    Object getExt(VwResource criteria) throws DatabaseException;
   

    /**
     * Get one or more user resources by a specific resource type.
     * 
     * @param typeId The id of the resource resource type
     * @return An arbitrary object containing the results.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getByType(int typeId) throws DatabaseException;

    /**
     * Get all user resource items that belong to a specific resource sub type.
     * 
     * @param subTypeId  The id of the sub type.
     * @return One or more arbitrary objects as the results.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getBySubType(int subTypeId) throws DatabaseException;

    /**
     * Retrieve all secured user resources which requires user authentication/authorization 
     * in order to access.
     * 
     * @return One or more arbitrary objecs as the results.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getSecured() throws DatabaseException;

    /**
     * Retreive all unsecured user resources which do not require user authentication/authorization
     * in order to access.
     * 
     * @return One or more arbitrary objects as the results.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getUnsecured() throws DatabaseException;

    /**
     * Obtains the accessibility of one or more user resources using specific criteria based 
     * on VwUserResourceAccess.
     *  
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess}   
     * @return An arbitrary object contining the results
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getAccessibility(VwUserResourceAccess criteria) throws DatabaseException;
    
    /**
     * Obtain a list of secured resources that are assigned to the user.
     * 
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess}  
     * @return An arbitrary object contining the results
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getUserResourceAssigned(VwUserResourceAccess criteria) throws DatabaseException;
    
    /**
     * Obtain a list of secured resources that are revoked from the user.
     *  
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess}  
     * @return An arbitrary object contining the results
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getUserResourceRevoked(VwUserResourceAccess criteria) throws DatabaseException;

    /**
     * Creates or modifies a resource object.
     * 
     * @param res {@link com.bean.UserResource UserResource} containing the 
     * 		   data to be updated.
     * @return The unique identifier of the resource.
     * @throws ResourceException
     */
    int maintainResource(UserResource res) throws ResourceException;
    
    /**
     * Deletes a resource.
     * 
     * @param uid The unique id of the resource sub-type.
     * @return Total number of resource targets affected.
     * @throws DatabaseException
     */
    int deleteResource(int uid) throws ResourceException;
    
    /**
     * Get a user resource type using UserResourceType object as the source of selection 
     * criteria.
     * 
     * @param criteria 
     *           {@link com.bean.UserResourceType UserResourceType} containing the data values to 
     *           use as selection criteria.
     * @return An arbitrary object contining the results
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getType(UserResourceType criteria) throws DatabaseException;
    
    /**
     * Get type and sub-type data for one or more resources.
     * 
     * @param criteria {@link com.bean.VwResourceType VwResourceType}
     * @return A list arbitrary objects representing resource type data. 
     * @throws DatabaseException
     */
    Object getTypeSubtype(VwResourceType criteria) throws DatabaseException;
    
 
    /**
     * Creates or modifies a resource type object.
     * 
     * @param type {@link com.bean.UserResourceType UserResourceType} containing the 
     * 		   data to be updated.
     * @return The unique identifier of the resource type.
     * @throws ResourceException
     */
    int maintainType(UserResourceType type) throws ResourceException;
    
    /**
     * Deletes a resource type.
     * 
     * @param typeId The unique id of the resource type.
     * @return Total number of resource type targets affected.
     * @throws DatabaseException
     */
    int deleteType(int typeId) throws ResourceException;

    /**
     * Get a user resource type using UserResourceType and UserResourceSubtype objects as the source 
     * of selection criteria.
     * 
     * @param criteria 
     *           {@link com.bean.UserResourceSubtype UserResourceSubtype} containing the data values to 
     *           use as selection criteria pertaining the the user resource sub type.
     * @return An arbitrary object contining the reslts
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    Object getSubType(UserResourceSubtype criteria) throws DatabaseException;

    
    /**
     * Creates or modifies a resource sub-type object.
     * 
     * @param subType {@link com.bean.UserResourceSubtype UserResourceSubtype} containing the 
     * 		   data to be updated.
     * @return The unique identifier of the resource sub-type.
     * @throws ResourceException
     */
    int maintainSubtype(UserResourceSubtype subType) throws ResourceException;
    
    /**
     * Deletes a resource sub-type.
     * 
     * @param subTypeId The unique id of the resource sub-type.
     * @return Total number of resource type targets affected.
     * @throws DatabaseException
     */
    int deleteSubtype(int subTypeId) throws ResourceException;
    
    /**
     * Updates the user profile in terms of resources assigned and revoked.
     * 
     * @param loginId The login id of the user.
     * @param assignedRsrc A String array of resource id's to assign
     * @param revokedRsrc A String array of resource id's to revoke.
     * @return int
     * @throws ResourceException
     */
    int maintainUserResourceAccess(String loginId, String[] assignedRsrc, String[] revokedRsrc) throws ResourceException;
}