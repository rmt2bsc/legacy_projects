package com.project.invoice;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.VwTimesheetList;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.controller.Response;

import com.constants.RMT2ServletConst;


import com.project.ProjectConst;

import com.project.timesheet.TimesheetFactory;
import com.project.timesheet.TimesheetSearchAction;

import com.util.SystemException;



/**
 * This class provides action handlers to respond to the client's commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages. 
 * Handles requests pertaining to adding, deleting, saving, submitting (finalize), approving, declining and invoicing timehseets.
 * 
 * @author Roy Terrell
 *
 */
public class InvoiceTimesheetAction extends AbstractActionHandler implements ICommand {

    private static final String COMMAND_BACK = "TimesheetInvoice.Bulk.back";

    private static final String COMMAND_BULKINVOICE = "TimesheetInvoice.Bulk.bulkinvoicesubmit";

    private static final String COMMAND_BULKINVOICE2 = "TimesheetInvoice.Bulk.bulkinvoicesubmit2";

    private static final String BULK_DATA = "BULKDATA";

    private Logger logger;

    private List selectedItems;

    private List timesheets;

    /**
     * Default constructor.
     *
     */
    public InvoiceTimesheetAction() {
	this.logger = Logger.getLogger("TimesheetEditAction");
    }

    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public InvoiceTimesheetAction(Request request, Response response, String command) throws ActionHandlerException {
	this();
	try {
	    this.init(null, request);
	    this.init();
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
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
	try {
	    if (command.equalsIgnoreCase(InvoiceTimesheetAction.COMMAND_BULKINVOICE)) {
		this.receiveClientData();
	    }
	    if (command.equalsIgnoreCase(InvoiceTimesheetAction.COMMAND_BULKINVOICE2)) {
		this.doBulkTimesheetInvoice();
	    }
	    if (command.equalsIgnoreCase(InvoiceTimesheetAction.COMMAND_BACK)) {
		this.doBack();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    // Ensure that any updates made to the the query object is set on the session. 
	    if (this.query != null) {
		this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	    }
	}
    }

    /**
     * Initializes the following API's: <code>SetupApi</code>, <code>TimesheetApi</code>, 
     * <code>EmployeeApi</code>, and <code>TimesheetStatusApi</code>.
     * 
     * @see com.bean.RMT2Base#init()
     */
    public void init() {
	super.init();
	return;
    }

    /**
     * Receives the Timesheet data that was input by the client, and attempts to package the data into a useable format.
     * 
     * @throws ActionHandlerException if a validation error occurs.
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    // Get selected items
	    this.selectedItems = this.getSelectedClients("selClients");
	    if (this.selectedItems == null && this.command.equalsIgnoreCase(InvoiceTimesheetAction.COMMAND_BULKINVOICE)) {
		this.msg = "One or more clients must be selected for bulk invoicing of timesheets";
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	    this.query.addKeyValues(InvoiceTimesheetAction.BULK_DATA, this.selectedItems);
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * Builds an integer array of client id's from a list of client id's existing 
     * on the UI.  The UI component that houses the client id's is identified as 
     * <i>selClients</i>.
     * 
     * @param uiCompName
     *           the name of the UI component containg the list of client id's.
     * @return String array
     *           list of client id's
     */
    private List getSelectedClients(String uiCompName) {
	String selValues[] = this.request.getParameterValues(uiCompName);
	if (selValues == null) {
	    return null;
	}
	List list = new ArrayList();
	for (int ndx = 0; ndx < selValues.length; ndx++) {
	    list.add(selValues[ndx]);
	}
	return list;
    }

    /**
     * Migrates a list of String values, which are client id's, to an array of Strings.
     * 
     * @param clientIdStr The client id's to move
     * @return int[]
     */
    private int[] getSelectedClients(List clientIdStr) {
	int tot = clientIdStr.size();
	int clients[] = new int[tot];
	int clientId = 0;
	for (int ndx = 0; ndx < tot; ndx++) {
	    clientId = Integer.parseInt((String) clientIdStr.get(ndx));
	    clients[ndx] = clientId;
	}
	return clients;
    }


    /**
     * Retrieves project details and packages the data into the request to be sent to the client.    If the project 
     * exist then the data is obtained from the database.    Otherwise, new data objects are instaintiated and sent to the clinet.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(ProjectConst.CLIENT_DATA_TIMESHEETS, this.timesheets);
	return;
    }


    /**
     * Invoices the approved timesheets of all clients in the system using the client data from the HTTP Request.
     * 
     * @return The number of timesheets sucessfully processed.
     * @throws ActionHandlerException
     */
    protected int doBulkTimesheetInvoice() throws ActionHandlerException {
	List clients;
	int clientId[];
	int count = 0;

	// Poll Bulk Timesheet Invoicing uisng "Wait Pleases..." JSP if this is the first time that the Project search screen is displayed.
	clients = (List) this.query.getKeyValues(InvoiceTimesheetAction.BULK_DATA);
	if (clients == null) {
	    return 0;
	}

	clientId = this.getSelectedClients(clients);
	DatabaseTransApi tx = DatabaseTransFactory.create();
	InvoicingApi invoiceApi = InvoicingFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Perform bulk invoicing.
	    count = invoiceApi.invoiceMultiple(clientId);
	    tx.commitUOW();
	    this.timesheets = (List) this.getProcessedTimesheets((List) invoiceApi.getProcessedData());
	    this.query.removeKeyValues(InvoiceTimesheetAction.BULK_DATA);
	    this.sendClientData();
	    tx.commitUOW();
	    return count;
	}
	catch (InvoicingException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
		invoiceApi = null;
		tx.close();
		tx = null;
	}

    }

    /**
     * Use all timesheet id's stored in this.timehessts List collection to retireve a 
     * List of VwTimesheetList objects.
     * 
     * @param timesheets List of int representing timesheet id's.
     * @return A List of {@link com.bean.VwTimesheetList VwTimesheetList}
     * @throws InvoicingException General data access errors.
     */
    private List getProcessedTimesheets(List<String> timesheets) throws InvoicingException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
	RdbmsDaoQueryHelper helper = new RdbmsDaoQueryHelper((DatabaseConnectionBean) tx.getConnector());
	try {
	    String tsId[] = new String[timesheets.size()];
	    tsId = timesheets.toArray(tsId);
	    VwTimesheetList ts = TimesheetFactory.createVwTimesheet();
	    ts.addInClause(VwTimesheetList.PROP_TIMESHEETID, tsId);
	    ts.addOrderBy(VwTimesheetList.PROP_CLIENTNAME, VwTimesheetList.ORDERBY_ASCENDING);
	    ts.addOrderBy(VwTimesheetList.PROP_LASTNAME, VwTimesheetList.ORDERBY_ASCENDING);
	    ts.addOrderBy(VwTimesheetList.PROP_FIRSTNAME, VwTimesheetList.ORDERBY_ASCENDING);
	    ts.addOrderBy(VwTimesheetList.PROP_TIMESHEETID, VwTimesheetList.ORDERBY_ASCENDING);
	    List list = helper.retrieveList(ts);
	    return list;
	}
	catch (Exception e) {
	    throw new InvoicingException(e);
	}
	finally {
		tx.close();
		tx = null;
	}
    }

    /**
     * Action handler for returning the user back to the time sheet search page.
     *  
     * @throws ActionHandlerException.
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	TimesheetSearchAction action = new TimesheetSearchAction();
	action.processRequest(this.request, this.response, TimesheetSearchAction.COMMAND_LIST);
	return;
    }
    
    public void edit() throws ActionHandlerException {
	return;
    }

    public void add() throws ActionHandlerException {
	return;
    }

    public void save() throws ActionHandlerException, DatabaseException {
	return;
    }

    public void delete() throws ActionHandlerException {
	return;
    }
}