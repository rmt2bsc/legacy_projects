package com.api.security.user;

import java.util.List;

import com.api.BaseDataSource;
import com.api.security.UserAuthenticationException;

import com.bean.Roles;
import com.bean.UserLogin;
import com.bean.UserGroup;
import com.bean.VwUser;
import com.bean.criteria.UserCriteria;

public interface UserApi extends BaseDataSource {

    /**
     * Find user using primary key id.
     * 
     * @param uid A unique id identifying the user.
     * @return An arbitrary object representing a user.
     * @throws UserAuthenticationException
     */
    Object findUser(int uid) throws UserAuthenticationException;

    /**
     * Find one or more users using custom selection criteria.
     * 
     * @param criteria String representing the SQL where clause.
     * @return One or more objects represinting user(s).
     * @throws UserAuthenticationException
     */
    Object findUser(String criteria) throws UserAuthenticationException;
    
    /**
     * Find one or more users extension profiles using custom selection criteria.
     * @param criteria
     *         String representing the SQL where clause.
     * @return Object
     *         an arbitrary object representing the resultset of the query.
     * @throws UserAuthenticationException
     */
    Object findUserExt(String criteria) throws UserAuthenticationException;

    /**
     * Find user using user name.
     * 
     * @param userName The user's user name.
     * @return An arbitrary object representing the user.
     * @throws UserAuthenticationException
     */
    Object findUserByUserName(String userName) throws UserAuthenticationException;

    /**
     * Create or update a UserLogin object. Changes should persist to a specific
     * external data source.
     * 
     * @param _user
     *            UserLogin
     * @return int
     * @throws UserAuthenticationException
     */
    int maintainUser(UserLogin _user) throws UserAuthenticationException;

    /**
     * Delete a user.
     * 
     * @param _user
     *            UserLogin
     * @throws UserAuthenticationException
     */
    void deleteUser(UserLogin _user) throws UserAuthenticationException;

    /**
     * Set the activate flag of a user to true. Results should be persisted via an
     * external data source.
     * 
     * @param _user
     *            UserLogin
     * @throws UserAuthenticationException
     */
    void activateUser(UserLogin _user) throws UserAuthenticationException;

    /**
     * Set the activate flag of a user to false. Results should be persisted via an
     * external data source.
     * 
     * @param _user
     *            UserLogin
     * @throws UserAuthenticationException
     */
    void inActivateUser(UserLogin _user) throws UserAuthenticationException;

    /**
     * Updates user's login profile
     * 
     * @param _usr The user changes to persist
     * @return The id of the user.
     * @throws UserAuthenticationException
     */
    int updateUserLogin(UserLogin _usr) throws UserAuthenticationException;

    /**
     * Increment user's login count.
     * 
     * @param _usr
     *            UserLogin
     * @throws UserAuthenticationException
     */
    void updateUserLoginCount(UserLogin _usr) throws UserAuthenticationException;

    /**
     * Verify a user based the values of _userId and _password.
     * 
     * @param loginId The login id of the user.
     * @param password The user's passowrd.
     * @return A valid UserLogin object or null if login credentials failed.
     * @throws UserAuthenticationException
     */
    Object verifyLogin(String loginId, String password) throws UserAuthenticationException;

    /**
     * Obtains information pertaining to the user's group.
     * 
     * @param loginId the user's login id.
     * @return An arbitrary object that represents the user's group..
     * @throws UserAuthenticationException
     */
    Object getUserGroup(String loginId) throws UserAuthenticationException;

    /**
     * Obtains all user groups.
     * 
     * @return An arbitrary object containing list of groups.
     * @throws UserAuthenticationException
     */
    Object getAllGroups() throws UserAuthenticationException;

    /**
     * Obtains a user group by its unique id.
     * 
     * @param grpId The group id.
     * @return An arbitrary object
     * @throws UserAuthenticationException
     */
    Object getGroup(int grpId) throws UserAuthenticationException;

    /**
     * Obtains one or more roles in which a user is assigned.
     * 
     * @param loginId The user's login id.
     * @return An arbitrary object that represents the user's roles.
     * @throws UserAuthenticationException
     */
    Object getRoles(String loginId) throws UserAuthenticationException;

    /**
     * Obtains one or more application roles assigned to a user for a specific application.
     * 
     * @param loginId The user's login id.
     * @param appName The application to target for user roles.
     * @return An arbitrary object that represents the user's roles belonging 
     *         to a particular application.
     * @throws UserAuthenticationException
     */
    Object getRoles(String loginId, String appName) throws UserAuthenticationException;
    
    /**
     * Obtains a list of active sessions by session id.
     * 
     * @param sessionId
     *          the id of the session
     * @return
     *         one or more session records
     * @throws UserAuthenticationException
     */
    Object getActiveSessions(String sessionId) throws UserAuthenticationException;

    /**
     * Creates or modifies a user group record.
     * 
     * @param grp The Group that is to be updated.
     * @return The id of the group.
     * @throws UserAuthenticationException
     */
    int maintainGroup(UserGroup grp) throws UserAuthenticationException;

    /**
     * Deletes a user group from the system.
     * 
     * @param grpId The id of the group that is to be delete.
     * @return The total number of targets affected.
     * @throws UserAuthenticationException
     */
    int deleteGroup(int grpId) throws UserAuthenticationException;

    /**
     * Retrieves a role based on its unique key value.
     * 
     * @param uid The id of the role to retrieve.
     * @return An arbitrary object representing the role.
     * @throws UserAuthenticationException
     */
    Object getRole(int uid) throws UserAuthenticationException;

    /**
     * Obtains a complete list of all roles in the system.
     * 
     * @return An arbitrary object that represents the all roles.
     * @throws UserAuthenticationException
     */
    Object getAllRoles() throws UserAuthenticationException;

    /**
     * Creates or modifies a security role.
     * 
     * @param role The {@link com.bean.Roles Role} that is to be updated.
     * @return The id of the role.
     * @throws UserAuthenticationException
     */
    int maintainRole(Roles role) throws UserAuthenticationException;

    /**
     * Deletes a security roles from the system.
     * 
     * @param roleId The id of the role that is to be delete.
     * @return The total number of targets affected.
     * @throws UserAuthenticationException
     */
    int deleteRole(int roleId) throws UserAuthenticationException;
    
    /**
     * 
     * @param criteria
     * @return
     * @throws UserAuthenticationException
     */
    List <VwUser> findUserProfile(UserCriteria criteria) throws UserAuthenticationException;
}