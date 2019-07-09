package com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;
import com.bean.VwCodes;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.CodeDetailType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.LookupCodeType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQCodeDetailSearch;
import com.xml.schema.bindings.RSCodeDetailSearch;
import com.xml.schema.bindings.RSCodeLookup;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching a
 * one or more records from the general_codes table using various forms of 
 * selection criteria.  The incoming and outgoing data is expected to be in 
 * the form of SOAP XML. 
 * 
 * @author Roy Terrell
 * @deprecated Use CodeDetailsFetchHandler
 *
 */
public class CodeDetailsFetchService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger(CodeDetailsFetchService.class);

    private RQCodeDetailSearch reqMessage;

    /**
     * Default constructor
     *
     */
    protected CodeDetailsFetchService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public CodeDetailsFetchService(DatabaseConnectionBean con, Request request) {
	super(con, request);
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
	this.reqMessage = (RQCodeDetailSearch) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
	xml = this.doFetchCodes(this.reqMessage.getGroupId().intValue());
	return xml;
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
    protected String doFetchCodes(int groupId) throws SoapProcessorException {
	CodesApi api = CodesFactory.createCodesApi(this.con, this.request);

	//Send content to Browser
	try {
	    List<GeneralCodes> codes = (List<GeneralCodes>) api.findCodeByGroup(groupId);
	    if (codes == null) {
		return null;
	    }
	    return this.buildCodeDetailsResponsePayload(codes);
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

    public String buildCodeGroupResponsePayload(GeneralCodesGroup grp, String message) throws ActionHandlerException {
	ObjectFactory f = new ObjectFactory();
	RSCodeLookup ws = f.createRSCodeLookup();

	HeaderType header = PayloadFactory.createHeader("RS_code_lookup", "SYNC", "RESPONSE", this.currentUser);
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, 1);
	ws.setReplyStatus(rst);

	LookupCodeType lct = JaxbContactsFactory.getLookupCodeTypeInstance(grp, null);
	ws.setItem(lct);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    /**
     * Builds the payload for the  RS_code_detail_search response message.
     *  
     * @param results
     * @return
     */
    public String buildCodeDetailsResponsePayload(List<GeneralCodes> results) {
	this.responseMsgId = "RS_code_detail_search";
	ObjectFactory f = new ObjectFactory();
	RSCodeDetailSearch ws = f.createRSCodeDetailSearch();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE",this.currentUser);
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", null, (results == null ? 0 : results.size()));
	ws.setReplyStatus(rst);

	JaxbContactsFactory jaxbUtil = new JaxbContactsFactory(this.con);

	if (results != null) {
	    for (GeneralCodes dbCode : results) {
		CodeDetailType cdt = jaxbUtil.createCodeDetailTypeInstance(dbCode);
		ws.getGeneralCodes().add(cdt);
	    }
	}
	else {
	    CodeDetailType cdt = f.createCodeDetailType();
	    ws.getGeneralCodes().add(cdt);
	}

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    /**
     * 
     * @param code
     * @param message
     * @return
     */
    public String buildCodeDetailsResponsePayload(GeneralCodes code, String message) {
	ObjectFactory f = new ObjectFactory();
	RSCodeLookup ws = f.createRSCodeLookup();

	HeaderType header = PayloadFactory.createHeader("RS_code_lookup", "SYNC", "RESPONSE", this.currentUser);
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, 1);
	ws.setReplyStatus(rst);

	List<GeneralCodes> codes = new ArrayList<GeneralCodes>();
	codes.add(code);
	GeneralCodesGroup gcg = CodesFactory.createGeneralGroup();
	gcg.setCodeGrpId(code.getCodeGrpId());
	LookupCodeType lct = JaxbContactsFactory.getLookupCodeTypeInstance(gcg, codes);
	ws.setItem(lct);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    /**
     * 
     * @param codes
     * @return
     */
    public String buildCodeLookupResponsePayload(List<VwCodes> codes) {
	if (codes == null) {
	    return null;
	}

	ObjectFactory f = new ObjectFactory();
	RSCodeLookup ws = f.createRSCodeLookup();

	List<LookupCodeType> jaxbLst = JaxbContactsFactory.getLookupCodeTypeInstance(codes);
	// Append list of lookup codes to XML
	if (jaxbLst != null) {
	    ws.getGroup().addAll(jaxbLst);
	}

	HeaderType header = PayloadFactory.createHeader("RS_code_lookup", "SYNC", "RESPONSE", this.currentUser);
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", null, (jaxbLst == null ? 0 : jaxbLst.size()));
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}