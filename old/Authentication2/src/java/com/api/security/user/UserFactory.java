package com.api.security.user;

import com.controller.Request;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.Application;
import com.bean.AppRole;
import com.bean.ApplicationAccess;
import com.bean.OrmBean;
import com.bean.Roles;
import com.bean.User;
import com.bean.UserAppRole;
import com.bean.UserLogin;
import com.bean.UserGroup;
import com.bean.VwUser;
import com.bean.VwUserGroup;
import com.bean.VwAppRoles;
import com.bean.VwUserAppRoles;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * A factory that creates new instances of UserApi and UserLogin.
 * 
 * @author roy.terrell
 * 
 */
public class UserFactory extends DataSourceAdapter {

    /**
     * Create a new instance of UserApi using a DatabaseConnectionBean.
     * 
     * @param _dbo
     *            {@link DatabaseConnectionBean}
     * @return {@link UserApi}
     */
    public static UserApi createApi(DatabaseConnectionBean _dbo) {
	UserApi api = null;
	try {
	    api = new UserBeanImpl(_dbo);
	    api.setBaseView("UserLoginView");
	    api.setBaseClass("com.bean.UserLogin");
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
     * Create an instance of UserApi interface using the implementation of UserXmlImpl 
     * class with a database connection and a Http Servlet Request.
     * 
     * @param dbo The database connection
     * @param request The user's request.
     * @return {@link UserApi}
     */
    public static UserApi createXmlApi(DatabaseConnectionBean dbo) {
	UserApi api = null;
	try {
	    api = new UserXmlImpl(dbo);
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
     * Create an instance of UserApi interface using the implementation of UserBeanImpl 
     * class with a database connection and a Http Servlet Request.
     * 
     * @param dbo The database connection
     * @param request The user's request.
     * @return {@link UserApi}
     */
    public static UserApi createApi(DatabaseConnectionBean dbo, Request request) {
	UserApi api = null;
	try {
	    api = new UserBeanImpl(dbo, request);
	    api.setBaseView("UserLoginView");
	    api.setBaseClass("com.bean.UserLogin");
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
     * Create an instance of ApplicationApi interface using the implementation 
     * of ApplicationBeanImpl class with a database connection.
     * 
     * @param dbo The database connection
     * @return {@link ApplicationApi}
     */
    public static ApplicationApi createAppApi(DatabaseConnectionBean dbo) {
	ApplicationApi api = null;
	try {
	    api = new ApplicationBeanImpl(dbo);
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
     * Create an instance of ApplicationApi interface using the implementation 
     * of ApplicationBeanImpl class with a database connection and a Http Servlet 
     * Request.
     * 
     * @param dbo The database connection
     * @param request The user's request.
     * @return {@link ApplicationApi}
     */
    public static ApplicationApi createAppApi(DatabaseConnectionBean dbo, Request request) {
	ApplicationApi api = null;
	try {
	    api = new ApplicationBeanImpl(dbo, request);
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
     * Create an instance of ApplicationApi interface using the implementation of 
     * ApplicationXmlImpl class with a database connection and a Http Servlet Request.
     * 
     * @param dbo The database connection
     * @param request The user's request.
     * @return {@link ApplicationApi}
     */
    public static ApplicationApi createAppXmlApi(DatabaseConnectionBean dbo) {
	ApplicationApi api = null;
	try {
	    api = new ApplicationXmlImpl(dbo);
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
     * Create a new instance of a UserLogin class.
     * 
     * @return {@link UserLogin}
     */
    public static UserLogin createUserLogin() {
	try {
	    return new UserLogin();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new UserLogin object which its data is expected to 
     * be handled as XML.   By default, the UserLogin object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link UserLogin}
     */
    public static UserLogin createXmlUserLogin() {
	try {
	    UserLogin obj = createUserLogin();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    
    /**
     * Create a new instance of a VwUser class.
     * 
     * @return {@link VwUser}
     */
    public static VwUser createUserExt() {
	try {
	    return new VwUser();
	}
	catch (Exception e) {
	    return null;
	}
    }

    
    /**
     * Create a new XML instance of a VwUser class
     * @return {@link VwUser}
     */
    public static VwUser createXmlUserExt() {
    	try {
    		VwUser obj = createUserExt();
    	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
    	    obj.setSerializeXml(false);
    	    return obj;
    	}
    	catch (Exception e) {
    	    return null;
    	}
        }
    

    /**
     * Create a new instance of a UserGroup class.
     * 
     * @return {@link UserLogin}
     */
    public static UserGroup createUserGroup() {
	try {
	    return new UserGroup();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new UserGroup object which its data is expected to 
     * be handled as XML.   By default, the UserGroup object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link UserLogin}
     */
    public static UserGroup createXmlUserGroup() {
	try {
	    UserGroup obj = createUserGroup();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    
    /**
     * Create a new instance of a Role class.
     * 
     * @return {@link Roles}
     */
    public static Roles createRole() {
    try {
        return new Roles();
    }
    catch (Exception e) {
        return null;
    }
    }

    /**
     * Creates a new Roles object which its data is expected to 
     * be handled as XML.   By default, the Roles object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link UserLogin}
     */
    public static Roles createXmlRole() {
    try {
        Roles obj = createRole();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    catch (Exception e) {
        return null;
    }
    }

    
    /**
     * Creates a new instance of Application.
     * @return {@link User}
     */
    public static Application createApplication() {
	try {
	    return new Application();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new Application object which its data is expected to 
     * be handled as XML.   By default, the Application object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link VwUserGroup}
     */
    public static Application createXmlApplication() {
	Application obj = createApplication();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creates an instance of {@link com.bean.AppRole AppRole}.
     * 
     * @return AppRole
     */
    public static AppRole createAppRole() {
	try {
	    return new AppRole();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new AppRole object which its data is expected to 
     * be handled as XML.   By default, the AppRole object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link VwUserGroup}
     */
    public static AppRole createXmlAppRole() {
	AppRole obj = createAppRole();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creates a new instance of ApplicationAccess.
     * @return {@link User}
     */
    public static ApplicationAccess createApplicationAccess() {
	try {
	    return new ApplicationAccess();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new ApplicationAccess object which its data is expected to 
     * be handled as XML.   By default, the ApplicationAccess object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link ApplicationAccess}
     */
    public static ApplicationAccess createXmlApplicationAccess() {
	ApplicationAccess obj = createApplicationAccess();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creates a new instance of User.
     * @return {@link User}
     */
    public static User createUser() {
	try {
	    return new User();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create a new instance of a VwUserGroup class.
     * 
     * @return {@link VwUserGroup}
     */
    public static VwUserGroup createVwUserGroup() {
	try {
	    return new VwUserGroup();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new VwUserGroup object which its data is expected to 
     * be handled as XML.   By default, the VwUserGroup object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link VwUserGroup}
     */
    public static VwUserGroup createXmlVwUserGroup() {
	try {
	    VwUserGroup obj = createVwUserGroup();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create a new instance of a VwUserAppRoles class.
     * 
     * @return {@link VwUserAppRoles}
     */
    public static VwUserAppRoles createVwUserAppRoles() {
	try {
	    return new VwUserAppRoles();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new VwUserAppRoles object which its data is expected to 
     * be handled as XML.   By default, the VwUserAppRoles object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link VwUserAppRoles}
     */
    public static VwUserAppRoles createXmlVwUserAppRoles() {
	try {
	    VwUserAppRoles obj = createVwUserAppRoles();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    
    /**
     * Create a new instance of a VwAppRoles class.
     * 
     * @return {@link VwAppRoles}
     */
    public static VwAppRoles createVwAppRoles() {
	try {
	    return new VwAppRoles();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new VwAppRoles object which its data is expected to 
     * be handled as XML.   By default, the VwAppRoles object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link VwAppRoles}
     */
    public static VwAppRoles createXmlVwAppRoles() {
	try {
	    VwAppRoles obj = createVwAppRoles();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * Create a new instance of a UserAppRole class.
     * 
     * @return {@link com.bean.UserAppRole UserAppRole}
     */
    public static UserAppRole createUserAppRole() {
        try {
            return new UserAppRole();
        }
        catch (Exception e) {
            return null;
        }   
    }
    
    /**
     * Creates a new UserAppRole object which its data is expected to 
     * be handled as XML.   By default, the UserAppRole object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return @return {@link com.bean.UserAppRole UserAppRole}
     */
    public static UserAppRole createXmlUserAppRole() {
        try {
            UserAppRole obj = createUserAppRole();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
        
}
