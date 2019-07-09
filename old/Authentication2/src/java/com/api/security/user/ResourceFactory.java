package com.api.security.user;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;
import com.bean.UserResource;
import com.bean.UserResourceAccess;
import com.bean.UserResourceType;
import com.bean.UserResourceSubtype;
import com.bean.VwResource;
import com.bean.VwUserResourceAccess;
import com.bean.VwResourceType;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * A factory that creates new instances pertaining to the Resource API
 * 
 * @author roy.terrell
 * 
 */
public class ResourceFactory extends DataSourceAdapter {

    /**
     * Create a new instance of ResourceApi using a DatabaseConnectionBean.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @return {@link com.api.security.user.ResourceApi ResourceApi}
     */
    public static ResourceApi createApi(DatabaseConnectionBean dbo) {
        ResourceApi api = null;
	try {
	    api = new UserResourceBeanImpl(dbo);
	    return api;
	}
	catch (SystemException e) {
	    return null;
	}
	catch (DatabaseException e) {
	    return null;
	}
    }
    
    /**
     * Create a new instance of ResourceApi using a DatabaseConnectionBean and 
     * the user's request.
     * 
     * @param dbo The database connection bean
     * @param request The use's request object.
     * @return RespouceApi
     */
    public static ResourceApi createApi(DatabaseConnectionBean dbo, Request request) {
        ResourceApi api = null;
	try {
	    api = new UserResourceBeanImpl(dbo, request);
	    return api;
	}
	catch (SystemException e) {
	    return null;
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    /**
     * Create an instance of ResourceApi interface using the implementation of UserResourceXmlImpl 
     * class with a database connection.
     * 
     * @param dbo The database connection
     * @param request The user's request.
     * @return {@link com.api.security.user.ResourceApi ResourceApi}
     */
    public static ResourceApi createXmlApi(DatabaseConnectionBean dbo) {
        ResourceApi api = null;
	try {
	    api = new UserResourceXmlImpl(dbo);
	    return api;
	}
	catch (SystemException e) {
	    return null;
	}
	catch (DatabaseException e) {
	    return null;
	}
    }
    
    /**
     * Create a new instance of a UserResource class.
     * 
     * @return {@link com.bean.UserResource UserResource}
     */
    public static UserResource createUserResource() {
    try {
        return new UserResource();
    }
    catch (Exception e) {
        return null;
    }
    }
    
    /**
     * Create a new instance of a UserResource class.
     * 
     * @return {@link com.bean.UserResource UserResource}
     */
    public static UserResource createXmlUserResource() {
        UserResource obj = createUserResource();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    
    
    /**
     * Create a new instance of a VwUserResourceAccess class.
     * 
     * @return {@link com.bean.VwUserResourceAccess VwUserResourceAccess}
     */
    public static VwUserResourceAccess createUserAccess() {
    try {
        return new VwUserResourceAccess();
    }
    catch (Exception e) {
        return null;
    }
    }
    
    /**
     * Create a new instance of a VwUserResourceAccess class.
     * 
     * @return {@link com.bean.VwUserResourceAccess VwUserResourceAccess}
     */
    public static VwUserResourceAccess createXmlUserAccess() {
        VwUserResourceAccess obj = createUserAccess();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    
    
    /**
     * Create a new instance of a UserResourceAccess class.
     * 
     * @return {@link com.bean.UserResourceAccess UserResourceAccess}
     */
    public static UserResourceAccess createUserResourceAccess() {
        try {
            return new UserResourceAccess();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Create a new instance of a UserResourceAccess class.
     * 
     * @return {@link com.bean.UserResourceAccess UserResourceAccess}
     */
    public static UserResourceAccess createXmlUserResourceAccess() {
        UserResourceAccess obj = createUserResourceAccess();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    
    
    
    /**
     * Create a new instance of a UserResourceType class.
     * 
     * @return {@link com.bean.UserResourceType UserResourceType}
     */
    public static UserResourceType createUserResourceType() {
    try {
        return new UserResourceType();
    }
    catch (Exception e) {
        return null;
    }
    }
    
    /**
     * Create a new instance of a UserResourceType class.
     * 
     * @return {@link com.bean.UserResourceType UserResourceType}
     */
    public static UserResourceType createXmlUserResourceType() {
        UserResourceType obj = createUserResourceType();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    
    
    /**
     * Create a new instance of a UserResourceSubtype class.
     * 
     * @return {@link com.bean.UserResourceSubtype UserResourceSubtype}
     */
    public static UserResourceSubtype createUserResourceSubtype() {
    try {
        return new UserResourceSubtype();
    }
    catch (Exception e) {
        return null;
    }
    }
    
    /**
     * Create a new instance of a UserResourceSubtype class.
     * 
     * @return {@link com.bean.UserResourceSubtype UserResourceSubtype}
     */
    public static UserResourceSubtype createXmlUserResourceSubtype() {
        UserResourceSubtype obj = createUserResourceSubtype();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    
    
    /**
     * Create a new instance of a VwResourceType class.
     * 
     * @return {@link com.bean.VwResourceType VwResourceType}
     */
    public static VwResourceType createVwResourceType() {
    try {
        return new VwResourceType();
    }
    catch (Exception e) {
        return null;
    }
    }
    
    /**
     * Create a new instance of a VwResourceType class.
     * 
     * @return {@link com.bean.VwResourceType VwResourceType}
     */
    public static VwResourceType createXmlVwResourceType() {
	VwResourceType obj = createVwResourceType();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    
    /**
     * Create a new instance of a VwResource class.
     * 
     * @return {@link com.bean.VwResource VwResource}
     */
    public static VwResource createVwResource() {
    try {
        return new VwResource();
    }
    catch (Exception e) {
        return null;
    }
    }
    
    /**
     * Create a new instance of a VwResource class.
     * 
     * @return {@link com.bean.VwResource VwResource}
     */
    public static VwResource createXmlVwResource() {
	VwResource obj = createVwResource();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
}
