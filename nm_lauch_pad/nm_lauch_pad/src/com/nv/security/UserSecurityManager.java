package com.nv.security;

import java.util.Date;

import modules.model.Assoc;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nv.db.DatabaseException;
import com.nv.db.IbaistEnvConfig;

/**
 * Employes the Singleton design pattern to manage an instance of the
 * {@link SecurityToken}.
 * <p>
 * The security token represents an authenticated user and is used to gain
 * access to various resources pertaining to the Launch Pad application.
 * 
 * @author rterrell
 *
 */
public class UserSecurityManager {

    private static SecurityToken SECURITY_TOKEN;

    private static final Logger logger = Logger
            .getLogger(UserSecurityManager.class);

    /**
     * Create a SecurityUtil object containing an uninitialized user security
     * token.
     */
    public UserSecurityManager() {
        return;
    }

    /**
     * Return the user's security token.
     * 
     * @return an instance of iBatis' SqlMapClient. Null is returned if the
     *         iBatis environment has not bee intialized.
     */
    public static final SecurityToken getSecurityToken() {
        return UserSecurityManager.SECURITY_TOKEN;
    }

    /**
     * The purpose of this method is to setup the security token and to
     * initialize and assoicate a common database connection with the token.
     * <p>
     * The security token is not linked with a user at this point. Once the
     * token is successfully created, a connection to RF database is opend and
     * made available to the entire application via the security token.
     * 
     * @throws UserSecurityException
     *             database access error
     */
    public static final void initUserSecurity() throws UserSecurityException {
        try {
            // Get database connection
            SqlMapClient ibatisClient = IbaistEnvConfig.getSqlMap();
            SecurityToken token = new SecurityToken();
            // Add connection to security token
            token.setDbCon(ibatisClient);
            UserSecurityManager.SECURITY_TOKEN = token;
        } catch (DatabaseException e) {
            String msg = "Unable to establish a connection to the database";
            logger.error(msg);
            throw new UserSecurityException(msg, e);
        }
    }

    /**
     * Updates the security token with the valid user with administrative access
     * rights
     * 
     * @param user
     */
    public void updateSecurityToken(Assoc user) {
        if (SECURITY_TOKEN == null) {
            return;
        }
        SECURITY_TOKEN.setSecurityLevel(user.getSecurityLevel());
        SECURITY_TOKEN.setUserId(user.getId());
        Date dt = new Date();
        SECURITY_TOKEN.setSignOn(dt);
        SECURITY_TOKEN.setLastActivity(dt);
    }

    /**
     * Removes the user from the security token signaling that there is no one
     * logged into the system.
     */
    public void resetSecurityToken() {
        if (SECURITY_TOKEN == null) {
            return;
        }
        SECURITY_TOKEN.setSecurityLevel(0);
        SECURITY_TOKEN.setUserId(0);
        SECURITY_TOKEN.setSignOn(null);
        SECURITY_TOKEN.setLastActivity(null);
    }

    /**
     * Adds <i>server</i> to the security token.
     * 
     * @param server
     *            the server to set
     */
    public void setServer(String server) {
        if (SECURITY_TOKEN == null) {
            return;
        }
        SECURITY_TOKEN.setServer(server);
    }

}
