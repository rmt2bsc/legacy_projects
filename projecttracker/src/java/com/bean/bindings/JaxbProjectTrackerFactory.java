package com.bean.bindings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPMessage;

import com.api.messaging.MessageException;
import com.api.messaging.MessagingException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.RMT2Base;
import com.bean.SalesOrderExt;
import com.bean.SalesOrderItems;
import com.controller.Request;

import com.project.setup.SetupFactory;
import com.util.RMT2Date;
import com.util.SystemException;
import com.xml.schema.bindings.AppRoleType;
import com.xml.schema.bindings.ClientProjectType;
import com.xml.schema.bindings.CustomerCriteriaType;
import com.xml.schema.bindings.CustomerType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQCustomerSearch;
import com.xml.schema.bindings.RSCustomerSearch;
import com.xml.schema.bindings.SalesOrderItemType;
import com.xml.schema.bindings.SalesOrderType;
import com.xml.schema.bindings.UserSessionType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Serves as a helper for persisting JAXB changes to the database and a factory that uses 
 * various JAXB type objects to create Project Tracker bean instances.
 *  
 * @author Roy Terrell
 *
 */
public class JaxbProjectTrackerFactory extends RMT2Base {

    /**
     * Default Constructor
     */
    public JaxbProjectTrackerFactory() {
	super();
    }

    public List<CustomerType> getClients(CustomerCriteriaType criteria, String userLogin) throws MessagingException {
	SoapClientWrapper client = new SoapClientWrapper();
	RSCustomerSearch soapResp = null;
	ObjectFactory f = new ObjectFactory();
	try {
	    RQCustomerSearch ws = f.createRQCustomerSearch();
	    HeaderType header = PayloadFactory.createHeader("RQ_customer_search", "SYNC", "REQUEST", userLogin);
	    ws.setHeader(header);

	    // Setup customer criteria without setting any properties.
	    if (criteria == null) {
		criteria = f.createCustomerCriteriaType();
	    }
	    ws.setCriteriaData(criteria);
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new MessagingException(errMsg);
	    }
	    soapResp = (RSCustomerSearch) client.getSoapResponsePayload();
	}
	catch (MessageException e) {
	    e.printStackTrace();
	}

	return soapResp.getCustomerList();
    }

    /**
     * Converts a List of <i>CustomerType</i> instances to a List of <i>ProjClient</i> instances.
     * 
     * @param customers
     * @return
     */
    public List<ProjClient> createProjectClients(List<CustomerType> customers) {
	List<ProjClient> list = new ArrayList<ProjClient>();
	for (CustomerType ct : customers) {
	    ProjClient projClient = this.createProjectClient(ct);
	    list.add(projClient);
	}
	return list;
    }

    /**
     * 
     * @param ct
     * @return
     */
    public ProjClient createProjectClient(CustomerType ct) {
	ProjClient projClient = SetupFactory.createClient();
	if (ct.getCustomerId() != null) {
	    projClient.setClientId(ct.getCustomerId().intValue());
	}
	if (ct.getAccountNo() != null) {
	    projClient.setAccountNo(ct.getAccountNo());
	}
	if (ct.getContactDetails() != null) {
	    if (ct.getContactDetails().getBusinessId() != null) {
		projClient.setBusinessId(ct.getContactDetails().getBusinessId().intValue());
	    }
	    if (ct.getContactDetails().getLongName() != null) {
		projClient.setName(ct.getContactDetails().getLongName());
	    }
	    if (ct.getContactDetails().getContactFirstname() != null) {
		projClient.setContactFirstname(ct.getContactDetails().getContactFirstname());
	    }
	    if (ct.getContactDetails().getContactLastname() != null) {
		projClient.setContactLastname(ct.getContactDetails().getContactLastname());
	    }
	    if (ct.getContactDetails().getContactPhone() != null) {
		projClient.setContactPhone(ct.getContactDetails().getContactPhone());
	    }
	    if (ct.getContactDetails().getContactExt() != null) {
		projClient.setContactExt(ct.getContactDetails().getContactExt());
	    }
	    if (ct.getContactDetails().getContactEmail() != null) {
		projClient.setContactEmail(ct.getContactDetails().getContactEmail());
	    }
	}
	return projClient;
    }

    /**
    * Converts a List of <i>CustomerType</i> instances to a List of <i>ProjClient</i> instances.
    * 
    * @param customers
    * @return
    */
    public String createProjectClientsAsXml(List<CustomerType> customers) {
	StringBuffer xml = new StringBuffer();
	xml.append("<ProjClients>");
	for (CustomerType ct : customers) {
	    ProjClient projClient = SetupFactory.createClient();
	    if (ct.getCustomerId() != null) {
		projClient.setClientId(ct.getCustomerId().intValue());
	    }
	    if (ct.getContactDetails() != null) {
		if (ct.getContactDetails().getBusinessId() != null) {
		    projClient.setBusinessId(ct.getContactDetails().getBusinessId().intValue());
		}
		if (ct.getContactDetails().getLongName() != null) {
		    projClient.setName(ct.getContactDetails().getLongName());
		}
	    }
	    xml.append(projClient.toXml());
	}
	xml.append("</ProjClients>");
	return xml.toString();
    }

    /**
     * Adapts a list of ProjProject instances to a list of ClientProjectType instances.
     * 
     * @param items
     *         a List <{@link ProjProject}>
     * @return a List of {@link ProjProject} instances.  If <i>items</i> is null, then an 
     *         empty list returned.
     */
    public static List<ClientProjectType> getClientProjectTypeListInstance(List<ProjProject> items) {
	List<ClientProjectType> newList = new ArrayList<ClientProjectType>();
	if (items == null) {
	    return newList;
	}
	for (ProjProject proj : items) {
	    ClientProjectType cpt = getClientProjectTypeInstance(proj);
	    newList.add(cpt);
	}
	return newList;
    }

    /**
     * 
     * @param project
     * @return
     */
    public static ClientProjectType getClientProjectTypeInstance(ProjProject project) {
	if (project == null) {
	    return null;
	}
	ObjectFactory f = new ObjectFactory();
	ClientProjectType cpt = f.createClientProjectType();
	cpt.setClientId(BigInteger.valueOf(project.getClientId()));
	cpt.setProjId(BigInteger.valueOf(project.getProjId()));
	cpt.setDescription(project.getDescription());
	cpt.setEffectiveDate(RMT2Date.toXmlDate(project.getEffectiveDate()));
	cpt.setEndDate(RMT2Date.toXmlDate(project.getEndDate()));
	cpt.setDateCreated(RMT2Date.toXmlDate(project.getDateCreated()));
	cpt.setDateUpdated(RMT2Date.toXmlDate(project.getDateUpdated()));
	cpt.setUpdateUser(project.getUserId());
	return cpt;
    }

    /**
     * 
     * @param soit
     * @return
     */
    public static SalesOrderItemType getXmlSalesOrderItemInstance(SalesOrderItems soi) {
	ObjectFactory f = new ObjectFactory();
	SalesOrderItemType soit = f.createSalesOrderItemType();
	soit.setItemId(BigInteger.valueOf(soi.getItemId()));
	soit.setSalesOrderItemId(BigInteger.valueOf(soi.getSoItemId()));
	soit.setSalesOrderId(BigInteger.valueOf(soi.getSoId()));
	soit.setItemName(soi.getItemNameOverride());
	soit.setOrderQty(BigInteger.valueOf(Double.valueOf(soi.getOrderQty()).longValue()));
	soit.setBackOrderQty(BigDecimal.valueOf(soi.getBackOrderQty()));
	soit.setUnitCost(BigDecimal.valueOf(soi.getInitUnitCost()));
	soit.setMarkup(BigDecimal.valueOf(soi.getInitMarkup()));
	return soit;
    }

    /**
     * 
     * @param sot
     * @return
     */
    public static SalesOrderType getXmlSalesOrderInstance(SalesOrderExt soExt) {
	ObjectFactory f = new ObjectFactory();
	SalesOrderType sot = f.createSalesOrderType();
	sot.setAccountNo(soExt.getAccountNo());
	sot.setSalesOrderId(BigInteger.valueOf(soExt.getSalesOrderId()));
	sot.setCustomerId(BigInteger.valueOf(soExt.getCustomerId()));
	sot.setInvoiced(soExt.getInvoiced() == 1);
	sot.setOrderTotal(BigDecimal.valueOf(soExt.getOrderTotal()));
	sot.setSalesOrderReason(soExt.getReason());
	if (soExt.getItems() != null) {
	    for (SalesOrderItems item : soExt.getItems()) {
		SalesOrderItemType soit = JaxbProjectTrackerFactory.getXmlSalesOrderItemInstance(item);
		sot.getItems().add(soit);
	    }
	}
	return sot;
    }

    public static RMT2SessionBean getUserSession(UserSessionType obj, Request request) {
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
