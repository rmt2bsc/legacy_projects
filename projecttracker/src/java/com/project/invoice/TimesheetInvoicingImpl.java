package com.project.invoice;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.soap.SOAPMessage;

import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.api.messaging.MessageException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.ServiceReturnCode;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;

import com.api.security.authentication.RMT2SessionBean;

import com.api.security.pool.AppPropertyPool;

import com.bean.ProjClient;
import com.bean.ProjTimesheet;
import com.bean.ProjTimesheetHist;
import com.bean.SalesOrderExt;
import com.bean.SalesOrderItems;
import com.bean.VwEmployeeExt;
import com.bean.VwTimesheetList;

import com.bean.bindings.JaxbProjectTrackerFactory;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;

import com.employee.EmployeeApi;
import com.employee.EmployeeFactory;

import com.project.ProjectConst;
import com.project.ProjectException;

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;

import com.project.timesheet.TimesheetApi;
import com.project.timesheet.TimesheetFactory;
import com.project.timesheet.TimesheetStatusApi;

import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAccountingInvoiceSalesOrder;
import com.xml.schema.bindings.RSAccountingInvoiceSalesOrder;
import com.xml.schema.bindings.SalesOrderType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Implementation of TimesheetApi that manages an employee's timesheet activities.
 * Each timesheet, regardless of the number of the number of project-task items,  
 * represents a single sales order item.
 * 
 * @author appdev
 *
 */
class TimesheetInvoicingImpl extends RdbmsDataSourceImpl implements InvoicingApi {

    private Logger logger;

    private TimesheetApi tsApi;

    private EmployeeApi empApi;

    private TimesheetStatusApi ptsApi;

    private SetupApi api;

    private VwEmployeeExt employee;

    private List timesheetId;

    private double invoiceAmt;

    private int invoiceMasterItemId;

//    private Response response;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Default Constructor
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected TimesheetInvoicingImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param request
     * @throws DatabaseException
     * @throws SystemException
     */
    public TimesheetInvoicingImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn);
	this.setRequest(request);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.api = SetupFactory.createApi(this.connector, this.request);
	this.tsApi = TimesheetFactory.createApi(this.connector, this.request);
	this.empApi = EmployeeFactory.createApi(this.connector, this.request);
	this.ptsApi = TimesheetFactory.createStatusApi(this.connector, this.request);
	// Get the item master id for all timesheet invoices created
	String temp = AppPropertyPool.getProperty("invoice_item_id");
	this.invoiceMasterItemId = Integer.parseInt(temp);
	this.timesheetId = new ArrayList();
    }

    /**
     * Create a TimesheetInvoicingImpl object which will contain a database connection, the 
     * user's request and the user's response.
     * 
     * @param dbConn The database connection bean.
     * @param request The user's request object
     * @param response The user's response object.
     * @throws DatabaseException
     * @throws SystemException
     */
    public TimesheetInvoicingImpl(DatabaseConnectionBean dbConn, Request request, Response response) throws DatabaseException, SystemException {
	this(dbConn, request);
//	this.response = response;
    }

    /**
     * Initializes the necessary api's needed make a TimesheetInvoicingImpl functional.  The 
     * following api's created are TimesheetApi, TimesheetStatusApi, SetupApi, and the EmployeeApi.  
     * The the item master id that assigned to all timesheet invoices created is obtained 
     * from the Properties file, ProjectParms.properties. 
     * 
     */
    public void init() {
	super.init();
	logger = Logger.getLogger("TimesheetInvoicingImpl");
	this.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Invoices a single timesheet.    
     * 
     * @param timesheetId 
     *           The id of the timesheet to invoice.
     * @return int
     *           invoice id.
     * @throws InvoicingException
     */
    public int invoiceSingle(int timesheetId) throws InvoicingException {
	ProjTimesheet ts = null;
	ProjClient client = null;
	try {
	    // Verify timesheet
	    ts = (ProjTimesheet) this.tsApi.findTimesheet(timesheetId);
	    if (ts == null) {
		this.msg = "Timesheet, " + timesheetId + ", does not exist";
		this.logger.log(Level.ERROR, this.msg);
		throw new InvoicingException(this.msg);
	    }

	    // Verify client
	    client = (ProjClient) this.api.findClient(ts.getClientId());
	    if (client == null) {
		this.msg = "Client must exist when invoicing a timesheet";
		this.logger.log(Level.ERROR, this.msg);
		throw new InvoicingException(this.msg);
	    }
	}
	catch (ProjectException e) {
	    throw new InvoicingException(e);
	}

	// Invoice the timesheet
	ProjTimesheet timesheets[] = new ProjTimesheet[1];
	timesheets[0] = ts;
	this.timesheetId.add(ts.getTimesheetId());
	int xactId = this.createInvoices(client, timesheets);
	return xactId;
    }

    /**
     * 
     * @param client
     * @param timesheets
     * @return
     * @throws InvoicingException
     */
    private SalesOrderExt buildSalesOrder(ProjClient client, ProjTimesheet timesheets[]) throws InvoicingException {
	// Setup sales order object
	SalesOrderExt so = new SalesOrderExt();
	so.setCustomerId(client.getClientId());

	// Process each timesheet as a single sales order item
	List<SalesOrderItems> items = new ArrayList <SalesOrderItems> ();
	StringBuffer tsDisplayIds = new StringBuffer();
	for (int ndx = 0; ndx < timesheets.length; ndx++) {
	    SalesOrderItems soi = this.buildSalesOrderItem(timesheets[ndx]);
	    if (soi == null) {
		continue;
	    }
	    this.invoiceAmt += soi.getInitUnitCost() * soi.getOrderQty();
	    items.add(soi);

	    if (tsDisplayIds.length() > 0) {
		tsDisplayIds.append(", ");
	    }
	    tsDisplayIds.append(timesheets[ndx].getDisplayValue());	
	}

	String xactReason = null;
	if (tsDisplayIds.length() > 0) {
	    xactReason = "Invoiced Time Sheet(s): " + tsDisplayIds.toString();
	}
	so.setItems(items);
	so.setOrderTotal(this.invoiceAmt);
	so.setReason(xactReason);
	so.setDateCreated(new Date());

	return so;
    }

    /**
     * Creates a sales order item for a given timesheet.    The itme master description will be 
     * overriden by the description of the sales order item and will be formatted as such:
     * <blockquote>
     * <b> [Employee Lastname], [Employee Firstname] for ## hours</b>
     * </blockquote>
     * 
     * @param _ts The id of the timesheet.
     * @return {@link SalesOrderItems}
     * @throws InvoicingException
     */
    private SalesOrderItems buildSalesOrderItem(ProjTimesheet ts) throws InvoicingException {
	try {
	    // Get employee
	    this.employee = (VwEmployeeExt) this.empApi.findEmployeeExt(ts.getEmpId());
	    
	    // Calculate timesheet invoice amount
	    double timesheetAmt = this.tsApi.calculateInvoice(ts.getTimesheetId());
	    double hours = this.tsApi.calculateTimehseetBillableHours(ts.getTimesheetId());

	    ProjTimesheetHist status = this.ptsApi.findTimesheetCurrentStatus(ts.getTimesheetId());
	    if (status == null) {
		return null;
	    }
	    if (status.getTimesheetStatusId() != ProjectConst.TIMESHEET_STATUS_APPROVED) {
		return null;
	    }

	    // Create Sales Order Item
	    SalesOrderItems soi = new SalesOrderItems();
	    soi.setItemId(this.invoiceMasterItemId); 
	    
	    // Build complex description line item.
	    String descr = this.employee.getShortname() + " for " + hours + " hours";
	    if (ts.getExtRef() != null && !ts.getExtRef().equals("")) {
	    	descr += ", " + ts.getExtRef();
	    }
	    soi.setItemNameOverride(descr);
	    soi.setInitUnitCost(timesheetAmt);
	    soi.setInitMarkup(1);
	    soi.setOrderQty(1);
	    return soi;
	}
	catch (Exception e) {
	    throw new InvoicingException(e);
	}
    }

    /**
     * Updates the timesheet's status and reference number after the timesheet 
     * has been invoiced.
     * 
     * @param timesheets 
     *          An array of {@link ProjTimesheet} objects
     * @param invoiceId 
     *          The invoice id that was created for the sales order.
     * @throws InvoicingException
     */
    private void postInvoice(ProjTimesheet timesheets[], int invoiceId) throws InvoicingException {
	try {
	    for (int ndx = 0; ndx < timesheets.length; ndx++) {
		// Change the status of timesheet to invoice.
		this.ptsApi.setTimesheetStatus(timesheets[ndx].getTimesheetId(), ProjectConst.TIMESHEET_STATUS_INVOICED);

		// Setup reference number.
		timesheets[ndx].setInvoiceRefNo(String.valueOf(invoiceId));
		this.tsApi.maintainTimesheet(timesheets[ndx]);
	    }
	}
	catch (ProjectException e) {
	    throw new InvoicingException(e);
	}
    }

    /**
     * Invoices one or more timesheets as a single transaction.
     * 
     * @param itemId The unique identifiers of the business items to invoice.
     * @return int
     * @throws InvoicingException
     */
    public int invoiceMultiple(int clientId[]) throws InvoicingException {
	final int STATE_NO_DATA = 0;
	int count = 0;

	if (clientId == null) {
	    return STATE_NO_DATA;
	}
	for (int ndx = 0; ndx < clientId.length; ndx++) {
	    try {
		this.invoiceMultiple(clientId[ndx]);
		count++;
	    }
	    catch (Exception e) {
		throw new InvoicingException(e);
	    }
	}

	return count;
    }

    /**
     * Invoices all timesheets pertaining to a single client identified by <i>clientId</i> 
     * when engaged from the bulk invoice feature.  The invoices are sent to the sales order 
     * subsystems for processing.  This request is submitted by an employee of manager status.
     * 
     * @param clientId The id of the client.
     * @return int as the new sales order id.
     * @throws InvoicingException 
     *           client validation errors, problem gathering timesheets, or sales order 
     *           processing errors or database access errors.
     */
    private int invoiceMultiple(int clientId) throws InvoicingException {
	ProjClient client = SetupFactory.createClient();
	try {
	    // Get client object
	    client.addCriteria(ProjClient.PROP_CLIENTID, clientId);
	    client = (ProjClient) this.daoHelper.retrieveObject(client);

	    // Get client approved timesheets
	    VwTimesheetList obj = new VwTimesheetList();
	    obj.addCriteria(VwTimesheetList.PROP_CLIENTID, clientId);
	    obj.addCriteria(VwTimesheetList.PROP_TIMESHEETSTATUSID, ProjectConst.TIMESHEET_STATUS_APPROVED);

	    List<VwTimesheetList> ts = this.daoHelper.retrieveList(obj);
	    if (ts == null) {
		return 0;
	    }
	    // Convert timesheet views to base timesheet objects.
	    int totTimesheets = ts.size();
	    ProjTimesheet timesheets[] = new ProjTimesheet[totTimesheets];
	    for (int ndx = 0; ndx < totTimesheets; ndx++) {
		ProjTimesheet baseTs = TimesheetFactory.createTimesheet();
		VwTimesheetList tsView = (VwTimesheetList) ts.get(ndx);
		baseTs.setTimesheetId(tsView.getTimesheetId());
		baseTs.setDisplayValue(tsView.getDisplayValue());
		baseTs.setClientId(tsView.getClientId());
		baseTs.setEmpId(tsView.getEmpId());
		baseTs.setBeginPeriod(tsView.getBeginPeriod());
		baseTs.setEndPeriod(tsView.getEndPeriod());
		baseTs.setProjId(tsView.getProjId());
		baseTs.setComments(tsView.getComments());
		baseTs.setExtRef(tsView.getExtRef());
		baseTs.setDocumentId(tsView.getDocumentId());
		timesheets[ndx] = baseTs;

		// Collect timesheet id's for confirmation list
		this.timesheetId.add(String.valueOf(tsView.getTimesheetId()));
	    }
	    // Begin to create sales order and invoice.
	    int xactId = this.createInvoices(client, timesheets);
	    return xactId;
	}
	catch (Exception e) {
	    throw new InvoicingException(e);
	}
    }

    /**
     * 
     * @param client
     * @param timesheets
     * @return
     * @throws InvoicingException
     */
    private int createInvoices(ProjClient client, ProjTimesheet timesheets[]) throws InvoicingException {
	int invoiceId;
	try {
	    SalesOrderExt so = this.buildSalesOrder(client, timesheets);
	    ServiceReturnCode rc = this.invoiceSalesOrder(so);
	    if (rc.getCode() <= 0) {
		this.logger.log(Level.ERROR, rc.getMessage());
		throw new InvoicingException(rc.getMessage());
	    }
	    invoiceId = rc.getCode();
	    this.postInvoice(timesheets, invoiceId);
	    return invoiceId;
	}
	catch (Exception e) {
	    throw new InvoicingException(e);
	}
    }

    /**
     * Creates a sales order for one or more timesheets pertaining to the client's project(s) 
     * and invoices each sales order.
     * 
     * @param clientId The id of the client that is to receive the invoice.
     * @param items One or more sales order items derived from the timesheets of the employees assigned to the client's project(s).
     * @return XML document representing the resulting transaction data
     * @throws InvoicingException errors pertaining to Sales Order processing, Timesheet project processing, or general Systems errors.
     */
    private ServiceReturnCode invoiceSalesOrder(SalesOrderExt salesOrder) throws DatabaseException, InvoicingException {
        RMT2SessionBean userSession  = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        SoapClientWrapper client = new SoapClientWrapper();
        RSAccountingInvoiceSalesOrder soapResp = null;
        ObjectFactory f = new ObjectFactory();
        String serviceId = "RQ_accounting_invoice_sales_order";
        try {
            RQAccountingInvoiceSalesOrder ws = f.createRQAccountingInvoiceSalesOrder();
            HeaderType header = PayloadFactory.createHeader(serviceId, "SYNC", "REQUEST", userSession.getLoginId());
            ws.setHeader(header);

            // Setup customer's sales order that will be invoiced
            SalesOrderType sot = JaxbProjectTrackerFactory.getXmlSalesOrderInstance(salesOrder);
            ws.setSalesOrder(sot);
            String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
            SOAPMessage resp = client.callSoap(msg);
            if (client.isSoapError(resp)) {
                String errMsg = client.getSoapErrorMessage(resp);
                throw new InvoicingException(errMsg);
            }
            soapResp = (RSAccountingInvoiceSalesOrder) client.getSoapResponsePayload();
            ServiceReturnCode rc = new ServiceReturnCode();
            rc.setCode(soapResp.getInvoiceResult().getReturnCode().intValue());
            rc.setMessage(soapResp.getInvoiceResult().getReturnMessage());
            return rc;
        }
        catch (MessageException e) {
            throw new InvoicingException(e);
        }
    }

    /**
     * Return all timesheet id's that were invoiced.
     * 
     * @return List
     *           A List of Integer objects which each Integer object is a timesheet id.
     */
    public Object getProcessedData() {
	return this.timesheetId;
    }

}
