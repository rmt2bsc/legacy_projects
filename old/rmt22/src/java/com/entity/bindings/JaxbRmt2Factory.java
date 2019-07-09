package com.entity.bindings;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.RMT2SessionBean;
import com.bean.RMT2Base;
import com.controller.Request;
import com.util.SystemException;
import com.xml.schema.bindings.AppRoleType;
import com.xml.schema.bindings.UserSessionType;





/**
 * Serves as a helper for persisting JAXB changes to the database and a factory that uses 
 * various JAXB type objects to create RMT2 bean instances.
 *  
 * @author Roy Terrell
 *
 */
public class JaxbRmt2Factory extends RMT2Base {

    private static Logger logger = Logger.getLogger("JaxbRmt2Factory");
    
    /**
     * Default Constructor
     */
    public JaxbRmt2Factory() {
	super();
    }

    public static RMT2SessionBean  getUserSession(UserSessionType obj, Request request) {
        RMT2SessionBean session;
        try {
            session = AuthenticationFactory.getSessionBeanInstance(obj.getLoginId(), obj.getOrigAppId());
        }
        catch (AuthenticationException e) {
            throw new SystemException(e);
        }
        session.setLoginId(obj.getLoginId());
        
        session.setAuthSessionId(obj.getAuthSessionId());
        session.setFirstName(obj.getFname());
        session.setLastName(obj.getLname());
        session.setAccessLevel(obj.getAccessLevel().intValue());
        session.setGatewayInterface(obj.getGatewayInterface());
        session.setRemoteAppName(obj.getRemoteAppName());
        session.setSessionCreateTime(obj.getSessionCreate());
        session.setSessionLastAccessedTime(obj.getSessionLastAccessed());
        session.setSessionMaxInactSecs(obj.getSessionMax().intValue());
        session.setGroupId(obj.getGroupId() == null ? 0 : Integer.parseInt(obj.getGroupId()));
    
        if (request != null) {
            session.setRemoteHost(request.getRemoteHost());
            session.setRemoteAddress(request.getRemoteAddr());
            session.setServerName(request.getServerName());
            session.setServerPort(request.getServerPort());
            session.setServletContext(request.getContextPath());
            session.setScheme(request.getScheme());
            session.setSessionId(request.getSession().getId());
            
            // TODO:  change interface to recognize these attributes.
            session.setServerProtocol(obj.getServerProtocol());
            session.setServerInfo(obj.getServerInfo());
            session.setServerSoftware(obj.getServerSoftware());
            session.setUserAgent(obj.getUserAgent());
            session.setLocale(obj.getLocal());
            session.setAccept(obj.getAccept());
            session.setAcceptLanguage(obj.getAcceptLang());
            session.setAcceptEncoding(obj.getAcceptEncoding());
        }
        
        List<String> roles = new ArrayList<String>();
        for (AppRoleType role : obj.getRoles()) {
            roles.add(role.getAppRoleCode());
        }
        session.setRoles(roles);
        return session;
    }

}
