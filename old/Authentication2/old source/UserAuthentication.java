package com.api.security.user;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.api.security.UserAuthenticationException;
import com.api.security.authentication.AbstractAuthenticationImpl;

import com.bean.RMT2SessionBean;
import com.bean.UserLogin;
import com.bean.VwUserAppRoles;

import com.bean.db.DatabaseConnectionBean;

import com.api.security.pool.DatabaseConnectionPool;

import com.api.security.user.UserFactory;


import com.util.SystemException;

/**
 * Provides general user authentication and authorization implementation which 
 * extends the functionality of AbstractAuthenticationImpl.
 * 
 * @author roy.terrell
 * @deprecated remove from project
 * 
 */
public class UserAuthentication extends AbstractAuthenticationImpl {
    private Logger logger;

    /**
     * Default constructor
     */
    protected UserAuthentication() {
	super();
    this.logger = Logger.getLogger("UserAuthentication");
    }

    /**
     * 
     * @param request
     * @param response
     */
    protected UserAuthentication(HttpServletRequest request, HttpServletResponse response) {
	super(request, response);
    }

    /**
     * Creates a new instance of UserAuthentication.
     * 
     * @return UserAuthentication.
     */
    public static UserAuthentication getInstance() {
	UserAuthentication auth = new UserAuthentication();
	return auth;
    }

    /**
     * 
     * @param request
     * @param response
     * @return
     */
    public static UserAuthentication getInstance(HttpServletRequest request, HttpServletResponse response) {
	UserAuthentication auth = new UserAuthentication(request, response);
	return auth;
    }

    /**
     * Retreive current user from the database in order to populate the session
     * bean with user profile data.
     * 
     * @return A valid VwUser object or null if the user is invalid.
     * @see com.api.security.authentication.AbstractAuthenticationImpl#setUserProfile(com.bean.db.DatabaseConnectionBean,
     *      com.bean.RMT2SessionBean)
     */
    protected Object setUserProfile(DatabaseConnectionBean conBean,
            RMT2SessionBean sessionBean) {
        UserLogin user = UserFactory.createUserLogin();
        DaoApi dao = DataSourceFactory.createDao(conBean);
        user.addCriteria("LoginId", conBean.getLoginId());
        try {
            Object[] result = dao.retrieve(user);
            if (result != null && result.length > 0) {
                user = (UserLogin) result[0];
            }
            else {
                user = null;
            }
        }
        catch (Exception e) {
            return null;
        }
        if (user != null) {
            sessionBean.setFirstName(user.getFirstname());
            sessionBean.setLastName(user.getLastname());
            sessionBean.setAccessLevel(0);
            sessionBean.setGroupId(user.getGroupId());
        }
        return user;
    }

    
    /**
     * Retrieves a list of user roles from <view name>.
     *  
     * @param loginId The user's login id
     * @return A list of roles.
     * @throws SystemException database connection error or error retrieving user roles.
     */
    protected List getUserRoles(String loginId) throws SystemException {
        try {
            DatabaseConnectionBean conBean = DatabaseConnectionPool.getAvailConnectionBean(loginId);
            UserApi api = UserFactory.createApi(conBean);

            // Get application access information
            ArrayList roles = new ArrayList();
            List list = (List) api.getRoles(loginId);
            if (list == null) {
                return roles;
            }
            for (int ndx = 0; ndx < list.size(); ndx++) {
        	VwUserAppRoles vuar = (VwUserAppRoles) list.get(ndx);
        	roles.add(vuar.getAppRoleCode());
            }
            return roles;
        }
        catch (UserAuthenticationException e) {
            this.msg = "User Authentication Exception: "+ e.getMessage();
            this.logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }
    }
}
