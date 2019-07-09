package com.entity.bindings;

import java.math.BigInteger;


import com.bean.RMT2Base;
import com.controller.Request;


import com.xml.schema.bindings.UserSessionType;




/**
 * Serves to provide JAXB/DTO adaptor functionality for RMT2 entities.
 *  
 * @author Roy Terrell
 *
 */
public class JaxbRmt2Adapter extends RMT2Base {

    /**
     * Default Constructor
     */
    public JaxbRmt2Adapter() {
	super();
    }

    /**
     * 
     * @param obj
     * @param request
     */
    public static void updateUserSessionType(UserSessionType obj, Request request) {
        if (request != null) {
            obj.setRemoteHost(request.getRemoteHost());
            obj.setServerName(request.getServerName());
            obj.setServerPort(BigInteger.valueOf(request.getServerPort()));
            obj.setServletContext(request.getContextPath());
            obj.setScheme(request.getScheme());
            obj.setSessionId(request.getSession().getId());
        }
        return;
    }
     
}
