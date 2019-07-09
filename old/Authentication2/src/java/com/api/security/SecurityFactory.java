package com.api.security;

import com.controller.Request;

import com.api.security.UserSecurity;

import com.api.db.orm.DataSourceAdapter;


/**
 * A factory that creates various new instances of UserSecurity api.
 * 
 * @author roy.terrell
 * 
 */
public class SecurityFactory extends DataSourceAdapter {

    /**
     * Create a new instance of UserSecurity using a Database implementation 
     * of the abstract authentication/authorization security interface.
     * 
     * @param request A HttpServletRequest object that houses the user's request data.
     * @param credentials A Hashtable of login credentials.
     * @return {@link UserSecurity}
     */
    public static UserSecurity getDatabaseAuthenticationApi(Request request, Object credentials) {
        try {
            UserSecurity api = new DatabaseAuthenticator(request, credentials);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
}
