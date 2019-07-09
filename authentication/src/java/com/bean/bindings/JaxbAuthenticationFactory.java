package com.bean.bindings;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.api.security.authentication.RMT2SessionBean;
import com.bean.Application;
import com.bean.RMT2Base;
import com.bean.UserLogin;
import com.bean.UserResource;
import com.bean.UserResourceSubtype;
import com.bean.VwAppRoles;
import com.bean.VwUser;
import com.bean.VwUserAppRoles;
import com.bean.VwUserResourceAccess;
import com.util.InvalidDataException;
import com.util.RMT2Date;
import com.xml.schema.bindings.AppRoleType;
import com.xml.schema.bindings.ApplicationType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.ResourceAccessType;
import com.xml.schema.bindings.ResourceSubtypeType;
import com.xml.schema.bindings.ResourceType;
import com.xml.schema.bindings.RoleType;
import com.xml.schema.bindings.UserAppRolesType;
import com.xml.schema.bindings.UserSessionType;
import com.xml.schema.bindings.UserType;



/**
 * Serves as a helper for persisting JAXB changes to the database and a factory that uses 
 * various JAXB type objects to create Accounting bean instances.
 *  
 * @author Roy Terrell
 *
 */
public class JaxbAuthenticationFactory extends RMT2Base {

    /**
     * Default Constructor
     */
    public JaxbAuthenticationFactory() {
	super();
    }

    /**
     * 
     * @param session
     * @return
     */
    public static UserSessionType getUserSessionType(RMT2SessionBean session) {
        ObjectFactory f = new ObjectFactory();
        UserSessionType ust = f.createUserSessionType();
        ust.setLoginId(session.getLoginId());
        ust.setWsToken(null);
        ust.setAuthSessionId(session.getAuthSessionId());
        ust.setFname(session.getFirstName());
        ust.setLname(session.getLastName());
        ust.setAccessLevel(BigInteger.valueOf(session.getAccessLevel()));
        ust.setGatewayInterface(session.getGatewayInterface());
        ust.setRemoteHost(session.getRemoteHost());
        ust.setRemoteAddr(session.getRemoteAddress());
        ust.setRemoteAppName(session.getRemoteAppName());
        ust.setServerProtocol(session.getServerProtocol());
        ust.setServerSoftware(session.getServerSoftware());
        ust.setServerName(session.getServerName());
        ust.setServerPort(BigInteger.valueOf(session.getServerPort()));
        ust.setUserAgent(session.getUserAgent());
        ust.setLocal(session.getLocale());
        ust.setAccept(session.getAccept());
        ust.setAcceptLang(session.getAcceptLanguage());
        ust.setAcceptEncoding(session.getAcceptEncoding());
        ust.setScheme(session.getScheme());
        ust.setServerInfo(session.getServerInfo());
        ust.setServletContext(session.getServletContext());
        ust.setSessionCreate(session.getSessionCreateTime());
        ust.setSessionLastAccessed(session.getSessionLastAccessedTime());
        ust.setSessionMax(BigInteger.valueOf(session.getSessionMaxInactSecs()));
        ust.setOrigAppId(session.getOrigAppId());
        ust.setGroupId(String.valueOf(session.getGroupId()));
    
        for (Object roleName : session.getRoles()) {
            AppRoleType role = f.createAppRoleType();
            role.setAppRoleCode(roleName.toString());
            ust.getRoles().add(role);
        }
        return ust;
    }

    /**
     * 
     * @param subTypes
     * @return
     */
    public static List<ResourceSubtypeType> getUserResourceSubTypeInstance(List<UserResourceSubtype> subTypes) {
        List<ResourceSubtypeType> jaxbSubTypes = new ArrayList<ResourceSubtypeType>();
        if (subTypes != null) {
            ObjectFactory f = new ObjectFactory();
            for (UserResourceSubtype item : subTypes) {
        	ResourceSubtypeType jaxbItem = f.createResourceSubtypeType();
        	jaxbItem.setRsrcSubtypeId(BigInteger.valueOf(item.getRsrcSubtypeId()));
        	jaxbItem.setRsrcTypeId(BigInteger.valueOf(item.getRsrcTypeId()));
        	jaxbItem.setRsrcSubtypeName(item.getName());
        	jaxbItem.setRsrcSubtypeDescr(item.getDescription());
        	jaxbSubTypes.add(jaxbItem);
            }
        }
        return jaxbSubTypes;
    }

    /**
     * 
     * @param user
     * @param grantedItems
     * @param revokedItems
     * @return
     * @throws InvalidDataException
     */
    public static ResourceAccessType getUserResourceAccessTypeInstance(UserLogin user, List<VwUserResourceAccess> grantedItems, List<UserResource> revokedItems)
            throws InvalidDataException {
        String msg = null;
        if (user == null) {
            msg = "Failed to adapt granted and revoked user resource data due to invalid input UserLogin object";
            JaxbAuthenticationAdapter.logger.error(msg);
            throw new InvalidDataException(msg);
        }
        ObjectFactory f = new ObjectFactory();
        ResourceAccessType rat = f.createResourceAccessType();
    
        // Setup user information
        rat.setLoginId(user.getUsername());
        rat.setUserFirstname(user.getFirstname());
        rat.setUserLastname(user.getLastname());
        rat.setUserUid(BigInteger.valueOf(user.getLoginId()));
    
        // Setup Granted resources
        if (grantedItems != null) {
            for (VwUserResourceAccess item : grantedItems) {
        	ResourceType rt = f.createResourceType();
        	rt.setRsrcId(BigInteger.valueOf(item.getRsrcId()));
        	rt.setRsrcName(item.getResrcName());
        	rt.setUrl(item.getResrcUrl());
        	rt.setHost(item.getHost());
        	rt.setDescription(item.getResrcDesc());
        	rt.setSecured(item.getResrcSecured() == 1);
        	rt.setRsrcTypeId(BigInteger.valueOf(item.getRsrcTypeId()));
        	rt.setRsrcTypeName(item.getResrcTypeName());
        	rt.setRsrcSubtypeId(BigInteger.valueOf(item.getRsrcSubtypeId()));
        	rt.setRsrcSubtypeName(item.getResrcSubtypeName());
        	rat.getGrantedResources().add(rt);
            }
        }
    
        // Setup Revoked resources
        if (revokedItems != null) {
            for (UserResource item : revokedItems) {
        	ResourceType rt = f.createResourceType();
        	rt.setRsrcId(BigInteger.valueOf(item.getRsrcId()));
        	rt.setRsrcName(item.getName());
        	rt.setUrl(item.getUrl());
        	rt.setHost(item.getHost());
        	rt.setDescription(item.getDescription());
        	rt.setSecured(item.getSecured() == 1);
        	rt.setRsrcTypeId(BigInteger.valueOf(item.getRsrcTypeId()));
        	rt.setRsrcTypeName(null);
        	rt.setRsrcSubtypeId(BigInteger.valueOf(item.getRsrcSubtypeId()));
        	rt.setRsrcSubtypeName(null);
        	rat.getRevokedResources().add(rt);
            }
        }
        return rat;
    }

    /**
     * 
     * @param daoItems
     * @return
     */
    public static List <ResourceType> getResourceTypesInstance(List <UserResource> daoItems) {
        List<ResourceType> items = new ArrayList<ResourceType>();
        ObjectFactory f = new ObjectFactory();
        for (UserResource item : daoItems) {
            ResourceType rt = f.createResourceType();
            rt.setRsrcId(BigInteger.valueOf(item.getRsrcId()));
            rt.setRsrcName(item.getName());
            rt.setUrl(item.getUrl());
            rt.setHost(item.getHost());
            rt.setDescription(item.getDescription());
            rt.setSecured(item.getSecured() == 1);
            rt.setRsrcTypeId(BigInteger.valueOf(item.getRsrcTypeId()));
            rt.setRsrcTypeName(null);
            rt.setRsrcSubtypeId(BigInteger.valueOf(item.getRsrcSubtypeId()));
            rt.setRsrcSubtypeName(null);
            items.add(rt);
        }
        return items;
    }

    /**
     * Get the profile of a user where the data source, <i>user</i>, is an instance of UserLogin}
     *  
     * @param user
     *         an instance of {@ com.bean.UserLogin UserLogin}
     * @return
     */
    public static UserType getUserTypeInstance(UserLogin user) {
        ObjectFactory f = new ObjectFactory();
        UserType ut = null;
        if (user != null) {
            ut = f.createUserType();
            ut.setLoginId(BigInteger.valueOf(user.getLoginId()));
            ut.setUserName(user.getUsername());
            ut.setFirstName(user.getFirstname());
            ut.setLastName(user.getLastname());
            ut.setShortName(user.getLastname() + ", " + user.getFirstname());
            ut.setEmail(user.getEmail());
            ut.setLoggedIn(user.getLoginId() == 1);
            ut.setTotalLogons(BigInteger.valueOf(user.getLoggedIn()));
            ut.setSsn(user.getSsn());
            ut.setDescription(user.getDescription());
            
            // Set Birth Date as XMLGregorianCalendar type
            ut.setDob(RMT2Date.toXmlDate(user.getBirthDate()));
            
            // Set Start date as XMLGregorianCalendar type
            ut.setStartDate(RMT2Date.toXmlDate(user.getStartDate())); 
            
            // Set Termination date as XMLGregorianCalendar type
            ut.setTermDate(RMT2Date.toXmlDate(user.getTerminationDate()));
        }
        return ut;
    }

    /**
     * Get the profile of a user where the data source, <i>user</i>, is an instance of VwUser}
     *  
     * @param user
     *         an instance of {@ com.bean.VwUser VwUser}
     * @return
     */
    public static UserType getUserTypeInstance(VwUser user) {
        ObjectFactory f = new ObjectFactory();
        UserType ut = null;
        if (user != null) {
            ut = f.createUserType();
            ut.setLoginId(BigInteger.valueOf(user.getLoginId()));
            ut.setUserName(user.getUsername());
            ut.setFirstName(user.getFirstname());
            ut.setLastName(user.getLastname());
            ut.setShortName(user.getLastname() + ", " + user.getFirstname());
            ut.setEmail(user.getEmail());
            ut.setLoggedIn(user.getLoginId() == 1);
            ut.setTotalLogons(BigInteger.valueOf(user.getLoggedIn()));
            ut.setSsn(user.getSsn());
            ut.setDescription(user.getDescription());
            
            // Set Birth Date as XMLGregorianCalendar type
            ut.setDob(RMT2Date.toXmlDate(user.getBirthDate()));
            
            // Set Start date as XMLGregorianCalendar type
            ut.setStartDate(RMT2Date.toXmlDate(user.getStartDate())); 
            
            // Set Termination date as XMLGregorianCalendar type
            ut.setTermDate(RMT2Date.toXmlDate(user.getTerminationDate()));
        }
        return ut;
    }

    /**
     * 
     * @param user
     * @param app
     * @param assigned
     * @param revoked
     * @return
     */
    public static UserAppRolesType getUserAppRolesTypeInstance(UserLogin user, Application app, List <VwUserAppRoles> assigned, List <VwAppRoles> revoked) {
        ObjectFactory f = new ObjectFactory();
        ApplicationType at = f.createApplicationType();
        UserAppRolesType uart = f.createUserAppRolesType();
        
        // Get user information
        UserType ut = getUserTypeInstance(user);
        if (ut != null) {
            uart.setUserInfo(ut);    
        }
    
        // get Application information
        at.setAppId(BigInteger.valueOf(app.getAppId()));
        at.setAppCode(app.getName());
        at.setDescription(app.getDescription());
        at.setActive(app.getActive() == 1);
        uart.setAppInfo(at);
        
        // get assigned roles
        for (VwUserAppRoles role : assigned) {
            AppRoleType art = f.createAppRoleType();
            art.setAppRoleId(BigInteger.valueOf(role.getAppRoleId()));
            art.setAppRoleCode(role.getAppRoleCode());
            art.setAppRoleName(role.getAppRoleName());
            art.setAppRoleDesc(role.getAppRoleDescription());
            
            RoleType rt = f.createRoleType();
            rt.setRoleId(BigInteger.valueOf(role.getRoleId()));
            rt.setRoleName(role.getRoleName());
            art.setBaseRole(rt);
            
            art.setAppId(BigInteger.valueOf(role.getApplicationId()));
            uart.getAssignedRoles().add(art);
        }
        
        // get revoked roles
        for (VwAppRoles role : revoked) {
            AppRoleType art = f.createAppRoleType();
            art.setAppRoleId(BigInteger.valueOf(role.getAppRoleId()));
            art.setAppRoleCode(role.getAppRoleCode());
            art.setAppRoleName(role.getAppRoleName());
            art.setAppRoleDesc(role.getAppRoleDescription());
            
            RoleType rt = f.createRoleType();
            rt.setRoleId(BigInteger.valueOf(role.getRoleId()));
            rt.setRoleName(role.getRoleName());
            art.setBaseRole(rt);
            
            art.setAppId(BigInteger.valueOf(role.getApplicationId()));
            uart.getRevokedRoles().add(art);
        }
        
        return uart;
    }

   
  
    
}
