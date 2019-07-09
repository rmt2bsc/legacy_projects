package com.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.http.service.AbstractHttpUrlWebServiceImpl;
import com.api.postal.AddressComponentsFactory;
import com.api.postal.StatesApi;

import com.bean.VwStateCountry;
import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;
import com.constants.GeneralConst;

import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RSUsStatesSearch;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.StateType;

import com.xml.schema.misc.PayloadFactory;

/**
 * HTTP URL Web Service for retrieving one or more U.S. State records from the database 
 * based on custom selection criteria input from the client. 
 * <p>
 * The U.S. State records returned from the query will be in XML format.  Optionally, the 
 * client can send the following input key/value pairs to build selection criteria:
 * <ul>
 * <li><b>clientAction</b></li>:&nbsp;The name of the web service.
 * <li><b>StateId</b></li>:&nbsp;The unique if of a state record.  Must be a number greater than zero..
 * <li><b>CountryId</b></li>:&nbsp;The unique if of a country.  Must be a number greater than zero.
 * <li><b>StateName</b></li>:&nbsp;The long name of the state.
 * <li><b>AbbrvState</b></li>:&nbsp;The abbreviated code of the targeted state.
 * </ul>
 * <p>
 * 
 * @author RTerrell
 * 
 */
public class HttpUsStatesSearchService extends AbstractHttpUrlWebServiceImpl {
    
    private static Logger logger = Logger.getLogger("HttpUsStatesSearchService");

    /** The id of the web service to invoke */
    private String serviceId;
    
    /** The state id parameter */
    private String stateId;

    /** The country id parameter */
    private String countryId;

    /** The state name parameter */
    private String stateName;
    
    /** The abbreviated state code */
    private String abbrvState;
    
    
    

    /**
     * Default constructor which instantiates the logger.
     * 
     * @throws SystemException
     */
    public HttpUsStatesSearchService() throws SystemException {
        super();
        logger.info("Logger for HttpUsStatesSearchService is initialized");
    }

  
    /**
     * Retrieves request data, service id, state id, country id, state name, and abbreviated state 
     * code, by their property names, clientAction, StateId, CountryId, StateName, and 
     * AbbrvState, respectively.  
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
       super.receiveClientData();
       this.serviceId = this.inProp.getProperty(GeneralConst.REQ_CLIENTACTION);
       this.stateId = this.inProp.getProperty("StateId");
       this.countryId = this.inProp.getProperty("CountryId");
       this.stateName = this.inProp.getProperty("StateName");
       this.abbrvState = this.inProp.getProperty("AbbrvState");
       return;
    }

    
    /**
     * Retrieves a list of U.S. state records based on the requested selection criteria. The
     * results of the query are stored internally as XML.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        logger.info("Processing request, " + this.serviceId);
        DatabaseTransApi tx = null;
        StatesApi api = null;
        List <VwStateCountry> results = null;
        String criteria = this.getCriteria();
       
        try {
            tx = DatabaseTransFactory.create();
            api = AddressComponentsFactory.createStatesApi((DatabaseConnectionBean) tx.getConnector(), this.request);
            results = (List<VwStateCountry>) api.findState(criteria);
            this.outData = this.buildResponsePayload(results, criteria);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
            tx.close();
            api.close();
            tx = null;
            api = null;
        }
    }
    
    private String getCriteria() {
        StringBuffer c = new StringBuffer();
        if (this.abbrvState != null) {
            c.append(" abbr_code like \'");
            c.append(this.abbrvState.toUpperCase());
            c.append("%\'");
        }
        
        if (this.countryId != null) {
            if (c.length() > 0) {
                c.append(" and ");
            }
            c.append(" country_id = ");
            c.append(this.countryId);
       }
        
       if (this.stateId != null) {
            if (c.length() > 0) {
                c.append(" and ");
            }
            c.append(" state_id = ");
            c.append(this.stateId);
       }
       
       if (stateName != null) {
           if (this.stateName != null) {
               c.append(" state_name like \'");
               c.append(this.stateName);
               c.append("%\'");
           }
       }
       
       if (c.length() > 0) {
           return c.toString();
       }
       return null;
    }
    
    /**
     * Builds the payload for the  RS_us_states_search response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(List <VwStateCountry> items, String criteria) {
        String responseMsgId = "RS_us_states_search";
        ObjectFactory f = new ObjectFactory();
        RSUsStatesSearch ws = f.createRSUsStatesSearch();

        HeaderType header = PayloadFactory.createHeader(responseMsgId, "http", "RESPONSE", "");
        ws.setHeader(header);
        
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "SUCCESS", "Selection Criteria used: " + criteria, items.size());
        ws.setReplyStatus(rst);

        // Adapt list of DAO states to JAXB states instances
        List<StateType> list = JaxbContactsFactory.getStateTypeInstance(items);
        ws.getStateInfo().addAll(list);

        // Marshall XML message instance.
        String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
    
    
}
