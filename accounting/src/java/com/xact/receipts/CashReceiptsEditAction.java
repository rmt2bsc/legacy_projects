package com.xact.receipts;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.messaging.MessageException;
import com.api.messaging.MessageManager;
import com.api.messaging.email.EmailMessageBean;
import com.api.messaging.email.smtp.SmtpApi;
import com.api.messaging.email.smtp.SmtpFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;
import com.gl.customer.CustomerExt;

import com.util.RMT2Exception;
import com.util.RMT2Utility;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

import com.xact.XactConst;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.xact.receipts.CashReceiptsApi;

import com.xact.sales.SalesConst;
import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderApi;
import com.xact.sales.SalesOrderException;
import com.xact.sales.SalesOrderMaintAction;

/**
 * This class provides functionality to serve the requests of the Customer New 
 * Sales Order Item Selection user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CashReceiptsEditAction extends SalesOrderMaintAction {

    private static final String COMMAND_ACCEPTPAYMENT = "CashReceipts.Edit.acceptpayment";

    private static final String COMMAND_EMAIL = "CashReceipts.Edit.email";

    private static final String COMMAND_BACK = "CashReceipts.Edit.back";

    private static Logger logger;

    private double balance;

    protected CashReceiptsApi crApi;

    private String appRoot;

    private String appFilePath;

    private List selectedOrders;

    private double selectedOrderTotal;

    /**
     * Default constructor
     *
     */
    public CashReceiptsEditAction() {
	super();
	CashReceiptsEditAction.logger = Logger.getLogger("CashReceiptsEditAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CashReceiptsEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
    }

    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	this.appRoot = RMT2Utility.getWebAppContext(request);
	this.appFilePath = "/email/";
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);

	if (command.equalsIgnoreCase(CashReceiptsEditAction.COMMAND_ACCEPTPAYMENT)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(CashReceiptsEditAction.COMMAND_EMAIL)) {
	    this.emailPaymentConfirmation();
	}
	if (command.equalsIgnoreCase(CashReceiptsEditAction.COMMAND_BACK)) {
	    this.prepareCustomerSalesConsole();
	}
    }

    /**
     * Retreives basic customer, sales order, and transactin  data from the client's 
     * request.  Also retrieves any selected invoices which payment is to be applied 
     * and the total dollar amount of invoices selected.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();

	// Get selected row id's and their corresponding sales order id's
	String rowIds[] = this.request.getParameterValues(GeneralConst.CLIENTROW_PROPERTY);
	if (rowIds != null) {
	    int total = rowIds.length;
	    this.selectedOrders = new ArrayList();
	    for (int ndx = 0; ndx < total; ndx++) {
		int soId;
		try {
		    String temp = this.request.getParameter("SoId" + rowIds[ndx]);
		    soId = Integer.parseInt(temp);
		    this.selectedOrders.add(soId);
		}
		catch (NumberFormatException e) {
		    continue;
		}
	    }
	}

	String temp = this.request.getParameter("selInvoiceTot");
	try {
	    this.selectedOrderTotal = Double.parseDouble(temp);
	}
	catch (Exception e) {
	    this.selectedOrderTotal = 0;
	}
    }

    /**
     * Packages detail Sales Order data into the HttpServlerRequest object which is 
     * required to serve as the response to the client. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.sendClientData();
	    // Replace the value of sales order total with the account balance. 
	    this.salesOrder.setOrderTotal(this.balance);
	    this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	    tx.commitUOW();
	}
	catch (ActionHandlerException e) {
	    tx.rollbackUOW();
	}
	finally {
	    this.salesApi.close();
	    this.salesApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Applies customer payment to which will reduce the customer's balance by the payment. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	super.save();
	this.xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.crApi = ReceiptsFactory.createCashReceiptsApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    int xactId = this.crApi.maintainCustomerPayment(this.xact, this.customer.getCustomerId());
	    CashReceiptsEditAction.logger.log(Level.DEBUG, "New Transaction id for cash payment: " + xactId);

	    // Update selected sales order statuses to "CLOSED"
	    this.updateSalesOrderPaymentStatus(tx);
	    // Commit transaction
	    tx.commitUOW();
	    this.salesOrder = this.calcCustomerBalance(this.customer.getCustomerId());
	    this.balance = this.salesOrder.getOrderTotal();
	    this.xact = xactApi.findXactById(xactId);
	    this.msg = "Customer payment was applied successfully";
	}
	catch (RMT2Exception e) {
	    this.msg = "Cash Receipt transaction failed.  ";
	    logger.log(Level.ERROR, this.msg + e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    this.crApi.close();
	    this.crApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Updates the status of one or more invoiced sales orders to "Closed" when 
     * a payment is received.  The total amount of selected invoices must not 
     * exceed the amount of payment received for the account.
     * 
     * @param tx
     *          The transaction object used by the caller of this method.
     * @return int
     *          The total number of sales orders successfully processed.
     * @throws SalesOrderException
     *          When total dollar amount of selected invoices exceeds the 
     *          payment received for the account.
     */
    private int updateSalesOrderPaymentStatus(DatabaseTransApi tx) throws SalesOrderException {
	if (this.selectedOrders == null || this.selectedOrders.size() <= 0) {
	    return 0;
	}

	if (this.selectedOrderTotal > this.xact.getXactAmount()) {
	    this.msg = "The total dollar amount of selected invoices must be less than or equal to the payment amount received";
	    throw new SalesOrderException(this.msg);
	}
	int processCount = 0;
	int total = this.selectedOrders.size();
	SalesOrderApi soApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);

	for (int ndx = 0; ndx < total; ndx++) {
	    Integer soId = (Integer) this.selectedOrders.get(ndx);
	    soApi.changeSalesOrderStatus(soId, SalesConst.STATUS_CODE_CLOSED);
	    processCount++;
	}
	return processCount;
    }

    /**
     * Emails the payment confirmation to the customer's email address.
     * 
     * @throws ActionHandlerException
     */
    private void emailPaymentConfirmation() throws ActionHandlerException {
	String emailSubject = "RMT2 Business Systems Corp Account Payment Confirmation";
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.receiveClientData();
	try {
	    this.salesOrder = this.calcCustomerBalance(this.customer.getCustomerId());
	    this.balance = this.salesOrder.getOrderTotal();
	    this.xact = xactApi.findXactById(this.xact.getXactId());
	    tx.commitUOW();
	    this.msg = "Customer payment confirmation was emailed successfully";
	}
	catch (RMT2Exception e) {
	    this.msg = "Cash Receipt transaction failed to send email.  " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    xactApi.close();
	    xactApi = null;
	    tx.close();
	    tx = null;
	}

	// Build XML data file
	StringBuffer xmlBuf = new StringBuffer();
	xmlBuf.append(MessageManager.MSG_OPEN_TAG);
	xmlBuf.append(MessageManager.MSG_OPEN_APPROOT_TAG);
	xmlBuf.append(this.appRoot);
	xmlBuf.append(MessageManager.MSG_CLOSE_APPROOT_TAG);
	xmlBuf.append("<pageTitle>");
	xmlBuf.append("Customer Payment Confirmation");
	xmlBuf.append("</pageTitle>");
	xmlBuf.append(((CustomerExt) this.customerExt).toXml());
	xmlBuf.append(this.salesOrder.toXml());
	xmlBuf.append(this.xact.toXml());
	xmlBuf.append(MessageManager.MSG_CLOSE_TAG);
	String xml = xmlBuf.toString();

	// Transform XML to HTML document
	RMT2XmlUtility xsl = RMT2XmlUtility.getInstance();
	String xslFile = this.appFilePath + "CustomerPaymentConfirmation.xsl";
	ByteArrayOutputStream baos = null;
	try {
	    baos = new ByteArrayOutputStream();
	    xsl.transform(xslFile, xml.toString(), baos);
	}
	catch (SystemException e) {
	    this.msg = "XSL Customer Payment Email transformation failed for resource, " + xslFile + " due to a System error.  " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ActionHandlerException(e);
	}
	finally {
	    xsl = null;
	}

	// Get results of transformation
	String html = baos.toString();

	// Build email message
	EmailMessageBean msg = new EmailMessageBean();
	msg.setFromAddress(System.getProperty(HttpSystemPropertyConfig.OWNER_EMAIL));
	msg.setToAddress(((CustomerExt) this.customerExt).getContactEmail());
	msg.setSubject(emailSubject);
	msg.setBody(html, EmailMessageBean.HTML_CONTENT);

	// Send Email message to intended recipient
	SmtpApi api = SmtpFactory.getSmtpInstance();
	if (api == null) {
	    return;
	}
	try {
	    api.sendMessage(msg);
	    api.close();
	    this.msg = "Customer payment confirmation was sent via email successfully";
	}
	catch (MessageException e) {
	    e.printStackTrace();
	    this.msg = "Customer payment confirmation errored.  " + e.getMessage();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.sendClientData();
	}
    }
}