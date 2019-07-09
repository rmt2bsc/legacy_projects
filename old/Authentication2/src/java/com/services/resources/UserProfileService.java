package com.services.resources;

import java.util.List;
import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.security.UserAuthenticationException;

import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.VwUser;
import com.bean.bindings.JaxbAuthenticationFactory;
import com.bean.criteria.UserCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.RMT2Date;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationUserProfile;
import com.xml.schema.bindings.RSAuthenticationUserProfile;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.UserType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching one or more user 
 * profile records as related to specific selection criteria.  The incoming and outgoing data 
 * is expected to be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class UserProfileService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("UserProfileService");

    private static String LOGIN_ID = "loginId";
    
    private static String USER_NAME = "userName";
    
    private static String FNAME = "firstName";
    
    private static String LNAME = "lastName";
    
    private static String EMAIL = "email";
    
    private static String LOGGEDIN = "loggedIn";
    
    private static String DOB = "dob";
    
    private static String SSN = "ssn";
    
    private static String STARTDATE = "startDate";
    
    private static String ENDDATE = "endDate";
    
    private static String ACTIVE = "active";
    
    private RQAuthenticationUserProfile reqMessage;

     

    /**
     * Default constructor
     *
     */
    protected UserProfileService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public UserProfileService(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    /**
     * Get login information and add to Properties collection.
     * 
     * @param soap
     * @return
     * @throws SoapProcessorException
     */
    protected Properties doGetRequestData(SOAPMessage soap) throws SoapProcessorException {
	Properties props = super.doGetRequestData(soap);
	this.reqMessage = (RQAuthenticationUserProfile) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	if (this.reqMessage.getLoginId() != null) {
	    props.setProperty(UserProfileService.LOGIN_ID, this.reqMessage.getLoginId().toString());
	}
	if (this.reqMessage.getUserName() != null) {
	    props.setProperty(UserProfileService.USER_NAME, this.reqMessage.getUserName());
	}
	if (this.reqMessage.getFirstName() != null) {
	    props.setProperty(UserProfileService.FNAME, this.reqMessage.getFirstName());
	}
	if (this.reqMessage.getLastName() != null) {
            props.setProperty(UserProfileService.LNAME, this.reqMessage.getLastName());
        }
	if (this.reqMessage.getEmail() != null) {
            props.setProperty(UserProfileService.EMAIL, this.reqMessage.getEmail());
        }
	if (this.reqMessage.isLoggedIn() != null) {
            props.setProperty(UserProfileService.LOGGEDIN, this.reqMessage.isLoggedIn().toString());
        }
	if (this.reqMessage.getDob() != null) {
            props.setProperty(UserProfileService.DOB, this.reqMessage.getDob().toString());
        }
	if (this.reqMessage.getSsn() != null) {
            props.setProperty(UserProfileService.SSN, this.reqMessage.getSsn());
        }
	if (this.reqMessage.getStartDate() != null) {
            props.setProperty(UserProfileService.STARTDATE, this.reqMessage.getStartDate().toString());
        }
	if (this.reqMessage.getTermDate() != null) {
            props.setProperty(UserProfileService.ENDDATE, this.reqMessage.getTermDate().toString());
        }
	if (this.reqMessage.isActive() != null) {
            props.setProperty(UserProfileService.ACTIVE, this.reqMessage.isActive().toString());
        }
	return props;
    }

    /**
     * Fetches the general code records based on selection criteria contained in <i>reqParms</i>.  
     * 
     * @param reqParms
     * @return String
     *          XML Payload for the response message.
     * @throws SoapProcessorException
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
        UserCriteria criteria = UserCriteria.getInstance(); 
        
        criteria.setQry_Id(reqParms.getProperty(UserProfileService.LOGIN_ID));
        criteria.setQry_Username(reqParms.getProperty(UserProfileService.USER_NAME));
        criteria.setQry_Firstname(reqParms.getProperty(UserProfileService.FNAME));
        criteria.setQry_Lastname(reqParms.getProperty(UserProfileService.LNAME));
        criteria.setQry_Email(reqParms.getProperty(UserProfileService.EMAIL));
        criteria.setQry_Ssn(reqParms.getProperty(UserProfileService.SSN));
        criteria.setQry_LoggedIn(reqParms.getProperty(UserProfileService.LOGGEDIN));
        criteria.setQry_Active(reqParms.getProperty(UserProfileService.ACTIVE));
        criteria.setQry_BirthDate(RMT2Date.xmlDateStrToDateStr(reqParms.getProperty(UserProfileService.DOB)));
        criteria.setQry_StartDate(RMT2Date.xmlDateStrToDateStr(reqParms.getProperty(UserProfileService.STARTDATE)));
        criteria.setQry_TerminationDate(RMT2Date.xmlDateStrToDateStr(reqParms.getProperty(UserProfileService.ENDDATE)));
       
        String xml = null;
        try {
            List <VwUser> data = api.findUserProfile(criteria);
            xml = this.buildResponsePayload(data);
            logger.log(Level.INFO, xml);
            return xml;
        }
	catch (UserAuthenticationException e) {
	    throw new SoapProcessorException(e);
	}
	catch (DatabaseException e) {
	    throw new SoapProcessorException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

  

    /**
     * Builds the payload for the  RS_authentication_user_resource_access response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(List <VwUser> data) {
	this.responseMsgId = "RS_authentication_user_profile";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationUserProfile ws = f.createRSAuthenticationUserProfile();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, null, null, data.size());
	ws.setReplyStatus(rst);

	// Adapt List of VwUser instances to List of UserType instances
	for (VwUser user : data) {
	    UserType ut = JaxbAuthenticationFactory.getUserTypeInstance(user);
	    ws.getUserProfile().add(ut);
	}
	// Marshall XML message isntance.
        String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;

    }

}