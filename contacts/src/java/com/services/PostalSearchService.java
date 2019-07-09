package com.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.pagination.PageCalculator;
import com.api.db.pagination.PaginationQueryResults;
import com.api.ip.IpAddressFactory;
import com.api.ip.IpApi;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.Country;
import com.bean.IpLocation;
import com.bean.TimeZone;
import com.bean.VwStateCountry;
import com.bean.VwZipcode;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;

import com.xml.schema.bindings.CountryType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.IpCriteriaType;
import com.xml.schema.bindings.IpDetails;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.PaginationType;
import com.xml.schema.bindings.RQPostalSearch;
import com.xml.schema.bindings.RSPostalSearch;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.StateType;
import com.xml.schema.bindings.TimezoneType;
import com.xml.schema.bindings.ZipcodeFullType;
import com.xml.schema.bindings.ZipcodeType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that queries the database for postal realted data using 
 * various forms of selection criteria.  Postal related data includes Countries, Provinces, Zip 
 * codes, and IP Addresses.   The incoming and outgoing data is expected to be in the form of 
 * SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class PostalSearchService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger(PostalSearchService.class);

    private RQPostalSearch reqMessage;

    /**
     * Default constructor
     *
     */
    protected PostalSearchService() {
	super();
	this.responseMsgId = "RS_postal_search";
    }

    /**
     * 
     * @param con
     * @param request
     */
    public PostalSearchService(DatabaseConnectionBean con, Request request) {
	super(con, request);
	this.responseMsgId = "RS_postal_search";
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
	String xml = null;
	this.reqMessage = (RQPostalSearch) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);

	// Validate Search criteria
	try {
	    this.validateCriteria(this.reqMessage.getPostalCriteria());
	}
	catch (PostalSearchCriteriaException e) {
	    throw new SoapProcessorException("Postal search criteria validation error", e);
	}

	// Determine the type of postal search to perform.
	if (this.reqMessage.getPostalCriteria().getIpAddr() != null) {
	    xml = this.doFetchIpAddressDetails(this.reqMessage.getPostalCriteria().getIpAddr());
	}
	//	if (this.reqMessage.getIpCriteria() != null) {
	//	    xml = this.doFetchIpDetails(this.reqMessage.getIpCriteria());
	//	}

	return xml;
    }

    /**
     * Verifies that one and only one criteria type exist out of the collection of postal 
     * criteria realted to RQ_postal_search request.
     * 
     * @param criteria
     */
    private void validateCriteria(RQPostalSearch.PostalCriteria criteria) throws PostalSearchCriteriaException {
	if (criteria == null) {
	    this.msg = "Postal search criteria instance is invalid or null";
	    logger.error(this.msg);
	    throw new PostalSearchCriteriaException(this.msg);
	}

	// criteria collection should contain one and only one criteria instance	
	List<Object> criteriaObj = new ArrayList<Object>();
	criteriaObj.add(criteria.getCountry());
	criteriaObj.add(criteria.getIpAddr());
	criteriaObj.add(criteria.getProvince());
	criteriaObj.add(criteria.getZipcode());

	int validCount = this.getNonNullCriteriaCount(criteriaObj);
	switch (validCount) {
	case 0:
	    this.msg = "Postal search criteria collection does not contain any non-null criteria instances";
	    logger.error(this.msg);
	    throw new PostalSearchCriteriaException(this.msg);

	case 1:
	    break;

	default:
	    this.msg = "Postal search criteria must be mututal exclusive.  Too many criteria instances provided.";
	    logger.error(this.msg);
	    throw new PostalSearchCriteriaException(this.msg);
	}

    }

    /**
     * 
     * @param list
     * @return
     */
    private int getNonNullCriteriaCount(List<Object> list) {
	int validCount = 0;
	for (Object item : list) {
	    if (item != null) {
		validCount++;
	    }
	}
	return validCount;
    }

    /**
     * Fetch one or more records from the general_codes table by group id.
     * 
     * @param contentId
     *          the primary key
     * @return String
     *           the raw XML data representing the single content record.
     * @throws SoapProcessorException
     *           when the content record is not found.
     */
    protected String doFetchIpAddressDetails(IpCriteriaType criteria) throws SoapProcessorException {
	IpApi api = IpAddressFactory.createApi(this.con);
	try {
	    IpLocation loc = api.getIpDetails(criteria.getIpStandard());
	    return this.buildIpAddressResponsePayload(loc, null);
	}
	catch (Exception e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SoapProcessorException(e);
	}
	finally {
	    api = null;
	}
    }

   
    
    /**
     * 
     * @param countries
     * @param message
     * @return
     */
    public String buildCountryRepsonsePayload(List<Country> countries, String message) {
	ObjectFactory f = new ObjectFactory();
	RSPostalSearch ws = f.createRSPostalSearch();
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	List<CountryType> codes = JaxbContactsFactory.getCountryTypeInstance(countries);
	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, codes == null ? 0 : codes.size());
	ws.setReplyStatus(rst);
	ws.getCountry().addAll(codes);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    /**
     * 
     * @param data
     * @param command
     * @return
     */
    public String buildProvinceResponsePayload(List<VwStateCountry> results, String message) {
	ObjectFactory f = new ObjectFactory();
	RSPostalSearch ws = f.createRSPostalSearch();
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, results == null ? 0 : results.size());
	ws.setReplyStatus(rst);

	JaxbContactsFactory cf = new JaxbContactsFactory();
	if (results != null) {
	    List<StateType> stList = cf.getStateType(results);
	    ws.getState().addAll(stList);
	}
	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    /**
     * Builds the payload for the  RS_code_detail_search response message.
     *  
     * @param results
     * @return
     */
    public String buildIpAddressResponsePayload(IpLocation loc, String message) {
	ObjectFactory f = new ObjectFactory();
	RSPostalSearch ws = f.createRSPostalSearch();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, (loc == null ? 0 : 1));
	ws.setReplyStatus(rst);

	IpDetails ip = JaxbContactsFactory.getIpDetailsInstance(loc);
	ws.setIpData(ip);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    
    public String buildZipShortResponsePayload( List <VwZipcode> results, String message) {
        ObjectFactory f = new ObjectFactory();
        RSPostalSearch ws = f.createRSPostalSearch();
        RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

        List <VwZipcode> list = null;
        if (results == null) {
            list = new ArrayList<VwZipcode>();
        }
        else {
            list = (List <VwZipcode>) results;
        }
        List<ZipcodeType> jaxbResults = JaxbContactsFactory.getZipShortInstance(list);
        ws.getZipShort().addAll(jaxbResults);
        int count = list.size();
        
        HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
        ws.setHeader(header);
        message += " (Results are in short format)";
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, count);
        ws.setReplyStatus(rst);
        
        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
    
    /**
     * 
     * @param results
     * @param message
     * @return 
     */
    public String buildZipShortResponsePayload(PaginationQueryResults results, String message) {
	ObjectFactory f = new ObjectFactory();
        RSPostalSearch ws = f.createRSPostalSearch();
        RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

        List <VwZipcode> list = null;
        if (results == null) {
            list = new ArrayList<VwZipcode>();
        }
        else {
            list = (List <VwZipcode>) results.getResults();
        }
        List<ZipcodeType> jaxbResults = JaxbContactsFactory.getZipShortInstance(list);
        ws.getZipShort().addAll(jaxbResults);
        int count = list.size();
        
        HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
        ws.setHeader(header);
        message += " (Results are in short format)";
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, count);
        ws.setReplyStatus(rst);
        
        PaginationType pt = f.createPaginationType();
        pt.setPageCount(results.getTotalPageCount());
        pt.setPageNo(BigInteger.valueOf(results.getPageNo()));
        pt.setPageRowCount(BigInteger.valueOf(results.getPageRowCount()));
        pt.setQueryRowCount(results.getTotalRowCount());
        
        // Obtain maximum page links value from configuration
        pt.setPageMaxLinks(BigInteger.valueOf(PageCalculator.getMaxPageLinkSetSize()));
        
        ws.setPaginationInfo(pt);
        
        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
    
    /**
     * 
     * @param results
     * @param message
     * @return
     */
    public String buildZipFullResponsePayload(List <VwZipcode> results, String message) {
	ObjectFactory f = new ObjectFactory();
        RSPostalSearch ws = f.createRSPostalSearch();
        RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

        if (results == null) {
            results = new ArrayList<VwZipcode>();
        }
        List<ZipcodeFullType> jaxbResults = JaxbContactsFactory.getZipFullTypeInstance(results);
        ws.getZipFull().addAll(jaxbResults);
        int count = results.size();
        
        HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
        ws.setHeader(header);
        message += " (Results are in full format)";
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, count);
        ws.setReplyStatus(rst);
        
        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
    
    /**
     * 
     * @param results
     * @param message
     * @return
     */
    public String buildTimezoneResponsePayload(List <TimeZone> results, String message) {
	ObjectFactory f = new ObjectFactory();
        RSPostalSearch ws = f.createRSPostalSearch();
        RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

        if (results == null) {
            results = new ArrayList<TimeZone>();
        }
        List<TimezoneType> jaxbResults = JaxbContactsFactory.getTimezoneInstance(results);
        ws.getTimezoneData().addAll(jaxbResults);
        int count = results.size();
        
        HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
        ws.setHeader(header);
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, count);
        ws.setReplyStatus(rst);
        
        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
}