package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractExternalServerAction;

import com.api.DaoApi;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.UserAuthenticationException;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.user.ApplicationApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;
import com.api.xml.XmlApiFactory;

import com.bean.AppRole;
import com.bean.UserLogin;
import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2Exception;
import com.util.SystemException;

/**
 * Service for retrieving application-role data from the database as XML.
 * Requires the client to send the following key/value pairs as input:
 * <ul>
 *     <li>An instance of {@link com.bean.Application Application} and/or {@link com.bean.Roles Roles}</li>
 * </ul>
 * <p>
 * The XML results yeilded from this service will be enclosed in the <i>data</i> 
 * tag element of the RSPayload feed, which is created from {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction}.   
 * The data format of this service's XML results can be described as follows:<br>
 * <pre>
 * &lt;UserRoles&gt;
 * &lt;UserLoginView&gt;
 *   &lt;user_login&gt;
 *     &lt;description/&gt;
 *     &lt;start_date&gt;some value&lt;/start_date&gt;
 *     &lt;ssn&gt;333-33-3333&lt;/ssn&gt;
 *     &lt;id&gt;1&lt;/id&gt;
 *     &lt;active&gt;0&lt;/active&gt;
 *     &lt;lastname&gt;terrell&lt;/lastname&gt;
 *     &lt;date_updated&gt;Sat Mar 01 01:02:25 CST 2008&lt;/date_updated&gt;
 *     &lt;email&gt;rmt2bsc2@verizon.net&lt;/email&gt;
 *     &lt;firstname&gt;roy&lt;/firstname&gt;
 *     &lt;password&gt;some value&lt;/password&gt;
 *     &lt;total_logons&gt;163&lt;/total_logons&gt;
 *     &lt;termination_date&gt;Thu Aug 30 00:00:00 CDT 2007&lt;/termination_date&gt;
 *     &lt;birth_date&gt;Tue May 17 00:00:00 CDT 1966&lt;/birth_date&gt;
 *     &lt;user_id&gt;admin&lt;/user_id&gt;
 *     &lt;group_id&gt;1&lt;/group_id&gt;
 *     &lt;date_created /&gt;
 *     &lt;login_id&gt;admin&lt;/login_id&gt;
 *   &lt;/user_login&gt;
 * &lt;/UserLoginView&gt;
 * 
 * &lt;ApplicationView&gt;
 *   &lt;application&gt;
 *     &lt;name&gt;common&lt;/name&gt;
 *     &lt;date_updated&gt;Thu Mar 06 22:44:14 CST 2008&lt;/date_updated&gt;
 *     &lt;id&gt;2&lt;/id&gt;
 *     &lt;user_id&gt;admin&lt;/user_id&gt;
 *     &lt;active&gt;1&lt;/active&gt;
 *     &lt;date_created&gt;Tue May 01 00:00:00 CDT 2007&lt;/date_created&gt;
 *     &lt;description&gt;External Test&lt;/description&gt;
 *   &lt;/application&gt;
 * &lt;/ApplicationView&gt;
 * 
 * &lt;VwUserAppRolesView&gt;
 *   &lt;vw_user_app_roles&gt;
 *     &lt;user_description /&gt;
 *     &lt;role_id&gt;1&lt;/role_id&gt;
 *     &lt;user_uid&gt;1&lt;/user_uid&gt;
 *     &lt;app_role_description&gt;Administrator of  Common Application.&lt;/app_role_description&gt;
 *     &lt;birth_date&gt;Tue May 17 00:00:00 CDT 1966&lt;/birth_date&gt;
 *     &lt;app_name&gt;common&lt;/app_name&gt;
 *     &lt;lastname&gt;terrell&lt;/lastname&gt;
 *     &lt;firstname&gt;roy&lt;/firstname&gt;
 *     &lt;group_id&gt;1&lt;/group_id&gt;
 *     &lt;app_role_id&gt;1&lt;/app_role_id&gt;
 *     &lt;role_name&gt;admin&lt;/role_name&gt;
 *     &lt;app_role_name&gt;Common Admin&lt;/app_role_name&gt;
 *     &lt;app_role_code&gt;comadmin&lt;/app_role_code&gt;
 *     &lt;termination_date&gt;Thu Aug 30 00:00:00 CDT 2007&lt;/termination_date&gt;
 *     &lt;ssn&gt;333-33-3333&lt;/ssn&gt;
 *     &lt;application_id&gt;2&lt;/application_id&gt;
 *     &lt;active&gt;0&lt;/active&gt;
 *     &lt;group_description&gt;Internal&lt;/group_description&gt;
 *     &lt;start_date&gt;Thu Aug 30 00:00:00 CDT 2007&lt;/start_date&gt;
 *     &lt;email&gt;rmt2bsc2@verizon.net&lt;/email&gt;
 *     &lt;login_id&gt;admin&lt;/login_id&gt;
 *   &lt;/vw_user_app_roles&gt;
 * &lt;/VwUserAppRolesView&gt;
 * 
 * &lt;VwAppRolesView&gt;
 *   &lt;vw_app_roles&gt;
 *     &lt;app_role_id&gt;6&lt;/app_role_id&gt;
 *     &lt;role_name&gt;security&lt;/role_name&gt;
 *     &lt;role_id&gt;2&lt;/role_id&gt;
 *     &lt;application_id&gt;2&lt;/application_id&gt;
 *     &lt;app_role_name&gt;Common Security&lt;/app_role_name&gt;
 *     &lt;app_role_code&gt;comsecure&lt;/app_role_code&gt;
 *     &lt;app_role_description&gt;Common Security Admnistatro&lt;/app_role_description&gt;
 *     &lt;app_name&gt;common&lt;/app_name&gt;
 *   &lt;/vw_app_roles&gt;
 * &lt;/VwAppRolesView&gt;
 * &lt;/UserRoles&gt;
 *</pre>
 *There can be one or more occurrences of <i>vw_user_app_roles</i> and <i>vw_app_roles elementes</i> 
 *depending on the number of roles found for each query scenario.
 *<p>
 * @author RTerrell
 * @deprecated No Longer used.  will be removed from future versions
 * 
 */
public class UserAppRoleProducer extends AbstractExternalServerAction {
    private Logger logger;

    private int appId;

    private String userAppLoginId;

    public UserAppRoleProducer() throws SystemException {
	this.logger = Logger.getLogger("AppRoleProducer");
    }

    /**
     * Retrieves the user's granted and revoked roles for a particular application 
     * based on certain selection criteria.  The results returned to the client are 
     * in the format of XML.
     *  
     * @throws ServiceHandlerException
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi appApi = UserFactory.createAppXmlApi((DatabaseConnectionBean) tx.getConnector());
	UserApi userApi = UserFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector());
	Object userXml;
	Object appXml;
	Object assignedRoleXml = null;
	Object revokedRoleXml = null;
	String xml;

	try {
	    // Get user xml data
	    userXml = userApi.findUserByUserName(this.userAppLoginId);
	    DaoApi xmlApi = XmlApiFactory.createXmlDao(userXml.toString());
	    xmlApi.retrieve("//UserLoginView/user_login");
	    int userId = 0;
	    String temp = null;
	    if (xmlApi.nextRow()) {
		temp = xmlApi.getColumnValue("login_id");
		try {
		    userId = Integer.parseInt(temp);
		}
		catch (NumberFormatException e) {
		    userId = 0;
		}
	    }
	    
	    // Get application xml data
	    appXml = appApi.findApp(this.appId);

	    // setup user criteria
	    UserLogin userCriteria = UserFactory.createUserLogin();
	    userCriteria.setLoginId(userId);

	    // Setup application-role criteria
	    AppRole appRoleCriteria = UserFactory.createAppRole();
	    appRoleCriteria.setAppId(this.appId);

	    // Get assigned roles as xml
	    try {
		assignedRoleXml = appApi.getAppRoleAssigned(userCriteria, appRoleCriteria);
	    }
	    catch (Exception e) {
		assignedRoleXml = null;
	    }
	    
	    // Get revoked roles as xml.
	    try {
		revokedRoleXml = appApi.getAppRoleRevoked(userCriteria, appRoleCriteria);		
	    }
	    catch (Exception e) {
		revokedRoleXml = null;
	    }

	    // Arrange the payload.
	    xml = userXml.toString() + appXml.toString();
	    if (assignedRoleXml != null) {
		xml += assignedRoleXml.toString();
	    }
	    if (revokedRoleXml != null) {
		xml += revokedRoleXml.toString();
	    }
	    this.outData = "<UserRoles>" + xml + "</UserRoles>";
	}
	catch (RMT2Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    appApi.close();
	    userApi.close();
	    tx.close();
	    appApi = null;
	    userApi = null;
	    tx = null;
	}
	return;
    }

    /**
     * Accepts the application id and login id from the client which are 
     * required to execute this service.
     * 
     * @throws AbstractActionHandler 
     *             When the login id and/or application id are not submitted 
     *             or when the application id is not a valid number. 
     */
    public void receiveClientData() throws ActionHandlerException {
	this.userAppLoginId = this.request.getParameter("UserAppLoginId");
	if (this.userAppLoginId == null) {
	    this.msg = "Login id for User-Application roles must be supplied";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	try {
	    String temp = this.request.getParameter("ApplicationId");
	    if (temp == null) {
		this.msg = "Application Id must be supplied";
		this.logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	    this.appId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    this.msg = "Application Id is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }
}