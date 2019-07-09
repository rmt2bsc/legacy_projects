package com.api.security.user;

import com.api.BaseDataSource;

import com.api.security.UserAuthenticationException;

import com.bean.AppRole;
import com.bean.Application;
import com.bean.ApplicationAccess;
import com.bean.Roles;
import com.bean.UserAppRole;
import com.bean.UserLogin;

public interface ApplicationApi extends BaseDataSource {

    /**
     * Find application using primary key id.
     * 
     * @param uid A unique id identifying an application.
     * @return An arbitrary object representing an application.
     * @throws UserAuthenticationException
     */
    Object findApp(int uid) throws UserAuthenticationException;

    /**
     * Find application using application name.
     * 
     * @param appName
     * @return An arbitrary object representing one or more applications.
     * @throws UserAuthenticationException
     */
    Object findApp(String appName) throws UserAuthenticationException;
    
    /**
     * Fetches all applications in the system.
     * 
     * @return An arbitrary object containing results.
     * @throws UserAuthenticationException
     */
    Object getAllApps() throws UserAuthenticationException;

    /**
     * Fetches all Application entries.
     * 
     * @return An arbitrary object contain one or more applications.
     * @throws UserAuthenticationException
     */
    Object getAll() throws UserAuthenticationException;

    /**
     * Find a user's Application Access using primary key.
     * 
     * @param uid A unique id identifying a user's application access.
     * @return An arbitrary object representing a user's application access.
     * @throws UserAuthenticationException
     */
    Object findAppAccess(int uid) throws UserAuthenticationException;

    /**
     * Find a user's Application access using the user login id key value.
     * 
     * @param userLoginId THe primary key of the user.
     * @return One or more arbitrary objects as Application access objects.
     * @throws UserAuthenticationException
     */
    Object findAppAccessByUserLoginId(int userLoginId) throws UserAuthenticationException;

    /**
     * Find a user's Application access using an applicaltion id.
     * 
     * @param appId The application id.
     * @return One or more arbitrary objects as Application access objects. 
     * @throws UserAuthenticationException
     */
    Object findAppAccessByAppId(int appId) throws UserAuthenticationException;

    /**
     * Find a user's application access using the application id and the user's 
     * login uid.
     * 
     * @param appId  The application id.
     * @param userLoginId The user's login id key.
     * @return An arbitrary object representing a user's application access.
     * @throws UserAuthenticationException
     */
    Object findUserAppAccess(int appId, int userLoginId) throws UserAuthenticationException;

    /**
     * Obtains a list of applications which the user can access.
     * 
     * @param userLoginId User's login id.
     * @return a list of generic objects representing applications.
     * @throws UserAuthenticationException
     */
    Object findUserApps(String userLoginId) throws UserAuthenticationException;

    /**
     * Retrieve one or more application access profiles indicating that a user 
     * is logged into the system.
     *  
     * @param userLoginId The unique id of the user.
     * @return An arbitrary object representing one or more application access profiles. 
     * @throws UserAuthenticationException
     */
    Object getLoggedInAppAccessProfiles(int userLoginId) throws UserAuthenticationException;

    /**
     * Get all roles belonging to an applicaltion.
     * 
     * @param appName The name of the application.
     * @return An arbitrary object containing the application's roles.
     * @throws UserAuthenticationException
     */
    Object getRoles(String appName) throws UserAuthenticationException;
    
    /**
     * Fetches all roles in the system.
     * 
     * @return An arbitrary object containing results.
     * @throws UserAuthenticationException
     */
    Object getAllRoles() throws UserAuthenticationException;

    /**
     * Adds an application access profile entry for the user which indicates that 
     * the user is logged into the system.
     * 
     * @param appAccess Application access object.
     * @return int
     * @throws UserAuthenticationException
     */
    int addUserApplicationAccess(ApplicationAccess appAccess) throws UserAuthenticationException;

    /**
     * Removes one or more user applicatin access profiles from the system.
     * 
     * @param loginId The user's unique identifier.
     * @param sessionId The user's unique session identifier.
     * @return int The number of profiles removed from the system.
     * @throws UserAuthenticationException
     */
    int removeUserApplicationAccess(int loginId, String sessionId) throws UserAuthenticationException;

    /**
     * 
     * @param loginId
     * @param appId
     * @return
     * @throws UserAuthenticationException
     */
    int removeUserApplicationAccess(int loginId, int appId) throws UserAuthenticationException;
    
    /**
     * Creates or modifies an application object.
     * 
     * @param app The application object to perform maintenance for.
     * @return The application id.
     * @throws ApplicationException
     */
    int maintainApp(Application app) throws ApplicationException;

    /**
     * Delete an application from the system using the id of the application.
     * 
     * @param appId  The id of the application
     * @return Total number of targets deleted.
     * @throws ApplicationException
     */
    int deleteApp(int appId) throws ApplicationException;
    
    /**
     * Get Application Role data based on the unique identifier of the record.
     * 
     * @param uid The unique identifier of the application role.
     * @return An arbitrary object representing the application role.
     * @throws ApplicationException
     */
    Object getAppRole(int uid)  throws UserAuthenticationException;
    
    /**
     * Get Application Role data based on information contained in an 
     * {@link com.bean.Application Application} instance.
     * 
     * @param criteria An instance of the Application object containing values 
     *            needed to build selection criteria.
     * @return An arbitrary object representing the application role.
     * @throws ApplicationException
     */
    Object getAppRole(Application criteria)  throws UserAuthenticationException;
    
    /**
     * Get Application Role data based on information contained in an 
     * {@link com.bean.Roles Roles} instance.
     * 
     * @param criteria An instance of the Roles object containing values 
     *            needed to build selection criteria.
     * @return An arbitrary object representing the application role.
     * @throws ApplicationException
     */
    Object getAppRole(Roles criteria)  throws UserAuthenticationException;
    
    /**
     * Get Application Role data based on information contained in 
     * {@link com.bean.Application Application} and {@link com.bean.Roles Roles} 
     * instances.
     * 
     * @param appCriteria An instance of the Application object containing values 
     *            needed to build selection criteria.
     * @param roleCriteria An instance of the Roles object containing values 
     *            needed to build selection criteria.
     * @return An arbitrary object representing the application role.
     * @throws ApplicationException
     */
    Object getAppRole(Application appCriteria, Roles roleCriteria)  throws UserAuthenticationException;
    
    /**
     * Get Application Role data based on it's code name.
     * 
     * @param code The code name of the AppRole record.
     * @return An arbitrary object representing the application role.
     * @throws ApplicationException
     */
    Object getAppRole(String code) throws UserAuthenticationException;
   
    /**
     * Get all application roles assigned to a user based on the selection criteria set as a 
     * UserLogin object and a AppRole object.
     * 
     * @param userCriteria 
     *           User login criteria set as a {@link com.bean.UserLogin UserLogin} object.
     * @param appRoleCriteria 
     *           Application role criteria set as a {@link com.bean.AppRole AppRole} object.
     * @return An arbitrary object containing the results.
     * @throws UserAuthenticationException
     */
    Object getAppRoleAssigned(UserLogin userCriteria, AppRole appRoleCriteria) throws UserAuthenticationException;
    
    /**
     * Get all application roles are not assigned to a user based on the selection criteria 
     * set as a UserLogin object and a AppRole object.
     * 
     * @param userCriteria
     *           User login criteria set as a {@link com.bean.UserLogin UserLogin} object.
     * @param appRoleCriteria
     *           Application role criteria set as a {@link com.bean.AppRole AppRole} object.
     * @return An arbitrary object containing the results.
     * @throws UserAuthenticationException
     */
    Object getAppRoleRevoked(UserLogin userCriteria, AppRole appRoleCriteria) throws UserAuthenticationException;
    
    /**
     * Creates a new or modifies an existing application role entity.
     * 
     * @param obj The application role object
     * @return The unique identifier of the application role.
     * @throws ApplicationException
     */
    int maintainAppRole(AppRole obj) throws ApplicationException;
    
    /**
     * Deletes an application role from the system.
     * 
     * @param uid The uniqe identifier of the application role.
     * @return The number of targets effected by the transaction.
     * @throws ApplicationException
     */
    int deleteAppRole(int uid) throws ApplicationException;
    
    /**
     * Creates new or modifies existing assigned and revoked user applicatin-roles.
     * 
     * @param loginId The login id belonging to the user.
     * @param appId The id of the application.
     * @param assignedRoles A String array of assigned application-role id's.
     * @param revokedRoles A String array of revoked application-role id's.
     * @return Total number of rows effected.
     * @throws ApplicationException
     */
    int maintainUserAppRole(String loginId, int appId, String assignedRoles[], String revokedRoles[]) throws ApplicationException;
}