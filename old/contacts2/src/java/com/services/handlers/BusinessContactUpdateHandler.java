package com.services.handlers;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.ContactException;
import com.api.address.AddressApi;
import com.api.address.AddressFactory;
import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;

import com.api.messaging.CommonMessageHandlerImpl;
import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.bean.Address;
import com.bean.Business;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.AddressType;
import com.xml.schema.bindings.BusinessType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQBusinessContactUpdate;
import com.xml.schema.bindings.RSBusinessContactUpdate;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.ZipcodeType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of updating a
 * single record located in the business table.  The incoming and outgoing 
 * data is expected to be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class BusinessContactUpdateHandler extends CommonMessageHandlerImpl {

    private static Logger logger = Logger.getLogger(BusinessContactUpdateHandler.class);

    private RQBusinessContactUpdate reqMessage;

    /**
     * Default constructor
     *
     */
    protected BusinessContactUpdateHandler() {
	super();
	return;
    }

    public BusinessContactUpdateHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
	return;
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    public String execute(Properties parms) throws MessagingHandlerException {
	super.execute(parms);
	String xml = null;
	this.reqMessage = (RQBusinessContactUpdate) parms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doUpdate(this.reqMessage.getBusinessContact());
	    return xml;
	}
	catch (ContactException e) {
	    throw new MessagingHandlerException(e);
	}
    }

    /**
     * Fetch a single record from the content table using the primary key (content id).
     * 
     * @param contentId
     *          the primary key
     * @return String
     *           the raw XML data representing the single content record.
     * @throws ContactException
     *           when the content record is not found.
     */
    protected String doUpdate(BusinessType bt) throws ContactException {
	BusinessApi busApi = BusinessFactory.createBusinessApi(this.con, this.request);
	AddressApi addrApi = AddressFactory.createAddressApi(this.con, request);

	Business bus = null;
	Address addr = null;
	int rc = 0;
	try {
	    bus = this.buildBusinessOrmBean(bt);
	    addr = this.buildAddressOrmBean(bt);
	    rc = busApi.maintainContact(bus);
	    logger.info("Return code of business contact update: " + rc);
	    addr.setBusinessId(bus.getBusinessId());
	    rc = addrApi.maintainContact(addr);
	    logger.info("Return code of address contact update: " + rc);
	    return this.buildResponsePayload(bus.getBusinessId());
	}
	catch (Exception e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    this.msg = "The busines contact update service failed";
	    logger.error(this.msg);
	    throw new ContactException(this.msg, e);
	}
	finally {
	    busApi = null;
	    addrApi = null;
	}
    }

    private Business buildBusinessOrmBean(BusinessType bt) {
	if (bt == null) {
	    return null;
	}
	Business bus = BusinessFactory.createBusiness();
	bus.setBusinessId(bt.getBusinessId() == null ? 0 : bt.getBusinessId().intValue());
	bus.setLongname(bt.getLongName());
	bus.setContactFirstname(bt.getContactFirstname() == null ? null : bt.getContactFirstname());
	bus.setContactLastname(bt.getContactLastname() == null ? null : bt.getContactLastname());
	bus.setContactPhone(bt.getContactPhone() == null ? null : bt.getContactPhone());
	bus.setContactExt(bt.getContactExt() == null ? null : bt.getContactExt());
	bus.setContactEmail(bt.getContactEmail() == null ? null : bt.getContactEmail());
	bus.setTaxId(bt.getTaxId() == null ? null : bt.getTaxId());
	bus.setWebsite(bt.getWebsite() == null ? null : bt.getWebsite());
	bus.setShortname(bt.getShortName());
	if (bt.getEntityType() != null) {
	    bus.setEntityTypeId(bt.getEntityType().getCodeId() == null ? 0 : bt.getEntityType().getCodeId().intValue());
	}
	if (bt.getServiceType() != null) {
	    bus.setServTypeId(bt.getServiceType().getCodeId() == null ? 0 : bt.getServiceType().getCodeId().intValue());
	}
	return bus;
    }

    private Address buildAddressOrmBean(BusinessType bt) {
	if (bt == null || bt.getAddress() == null) {
	    return null;
	}
	Address addr = AddressFactory.createAddress();
	AddressType at = bt.getAddress();
	ZipcodeType zt = at.getZip();
	addr.setAddrId(at.getAddrId() == null ? 0 : at.getAddrId().intValue());
	addr.setAddr1(at.getAddr1());
	addr.setAddr2(at.getAddr2());
	addr.setAddr3(at.getAddr3());
	addr.setAddr4(at.getAddr4());
	addr.setZip(zt.getZipcode() == null ? 0 : zt.getZipcode().intValue());
	addr.setZipext(at.getZipExt() == null || at.getZipExt() == null ? 0 : at.getZipExt().intValue());
	addr.setPhoneCell(at.getPhoneCell() == null ? null : at.getPhoneCell());
	addr.setPhoneExt(at.getPhoneWorkExt() == null ? null : at.getPhoneWorkExt());
	addr.setPhoneFax(at.getPhoneFax() == null ? null : at.getPhoneFax());
	addr.setPhoneHome(at.getPhoneHome() == null ? null : at.getPhoneHome());
	addr.setPhoneMain(at.getPhoneMain() == null ? null : at.getPhoneMain());
	addr.setPhonePager(at.getPhonePager() == null ? null : at.getPhonePager());
	addr.setPhoneWork(at.getPhoneWork() == null ? null : at.getPhoneWork());
	return addr;
    }

    public String buildResponsePayload(int businessId) {
	ObjectFactory f = new ObjectFactory();
	RSBusinessContactUpdate ws = f.createRSBusinessContactUpdate();

	HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Business Contact update was successful", null, businessId);
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}