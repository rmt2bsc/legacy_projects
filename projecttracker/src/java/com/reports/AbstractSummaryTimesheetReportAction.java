package com.reports;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.controller.Request;
import com.controller.Context;

import com.action.ActionHandlerException;
import com.action.AbstractReportAction;

import com.api.DaoApi;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.RMT2TagQueryBean;
import com.bean.ProjClient;
import com.bean.VwTimesheetSummary;
import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.project.setup.SetupFactory;

import com.util.HostCompanyManager;
import com.util.SystemException;

/**
 * Abstract class providing base functionality for producing a timesheet summary report.     The descendent is required 
 * to implement the sendClientData which will generally address the routing of the results.
 *
 * @author rterrell
 *
 */
public abstract class AbstractSummaryTimesheetReportAction extends AbstractReportAction {
    /** The name of the field that represents timesheet id input control on the client */
    protected static final String ATTRIB_TIMESHEETID = "TimesheetId";

    /** The name of the field that represents client id input control on the client */
    protected static final String ATTRIB_CLIENTID = "ClientId";

    /** The name of the field that represents employee id input control on the client */
    protected static final String ATTRIB_EMPLOYEEID = "EmployeeId";

    private Logger logger;

//    private RdbmsDaoQueryHelper daoHelper;

    protected int timesheetId;

    protected int clientId;
    
    protected int empId;

    /**
     * Default constructor.
     */
    public AbstractSummaryTimesheetReportAction() {
	super();
    }

    /**
     * Constructor responsible for initializing this object's servlet context and Http Request objects
     *
     * @param context The servlet context passed by the servlet
     * @param request The Http Servle Request object passed by the servlet
     * @throws SystemException
     */
    public AbstractSummaryTimesheetReportAction(Context context, Request request) throws SystemException {
	this();
	this.init(context, request);
    }

    /**
     * Initializes this object using _conext and _request.  Initializes this object to use 
     * the datasource, VwTimesheetSummaryView. 
     *
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
//	this.daoHelper = new RdbmsDaoQueryHelper(this.dbConn);
	logger = Logger.getLogger("SummaryTimesheetReportAction");
    }

    /**
     * Obtains the timesheet id, employee id, and client id from the request 
     * and sets the values on the session object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	String temp;
	query.clearAllKeyValues();
	try {
	    temp = this.request.getParameter("TimesheetId");
	    query.addKeyValues(AbstractSummaryTimesheetReportAction.ATTRIB_TIMESHEETID, temp);
	    temp = this.request.getParameter("ClientId");
	    query.addKeyValues(AbstractSummaryTimesheetReportAction.ATTRIB_CLIENTID, temp);
	    temp = this.request.getParameter("EmpId");
	    query.addKeyValues(AbstractSummaryTimesheetReportAction.ATTRIB_EMPLOYEEID, temp);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	return;
    }

    /**
     * Prepare the environment for running the Employee Timesheet report.  The 
     * procees includes creating and/or identifying the user's profile work area, 
     * identifying the input and expected output data files, and gathering the report 
     * data needed to build the report xml data file. This method is call prior to the
     * actual generation of the report.
     *  
     * @return Timesheet Data as XML
     * @throws ActionHandlerException
     */
    protected String initializeReport() throws ActionHandlerException {
	String temp = null;

	// Copy report input layout file to user's profile work area
	try {
	    this.setupReportLayout();
	}
	catch (SystemException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	//  Recover data fro mthe session
	RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.REPORT_BEAN);
	temp = (String) query.getKeyValues(AbstractSummaryTimesheetReportAction.ATTRIB_TIMESHEETID);
	this.timesheetId = Integer.parseInt(temp);
	temp = (String) query.getKeyValues(AbstractSummaryTimesheetReportAction.ATTRIB_CLIENTID);
	this.clientId = Integer.parseInt(temp);
	temp = (String) query.getKeyValues(AbstractSummaryTimesheetReportAction.ATTRIB_EMPLOYEEID);
	this.empId = Integer.parseInt(temp);

	// Build company data
	String xml = this.fetchHostCompany(HostCompanyManager.getInstance());

	DatabaseTransApi tx = DatabaseTransFactory.create();
	DaoApi dao = DataSourceFactory.createDao((DatabaseConnectionBean) tx.getConnector());
	RdbmsDaoQueryHelper daoHelper = new RdbmsDaoQueryHelper((DatabaseConnectionBean) tx.getConnector());
	try {
		// Get Client data
		try {
		    ProjClient client = SetupFactory.createXmlClient();
		    client.addCriteria(ProjClient.PROP_CLIENTID, clientId);
		    xml += daoHelper.retrieveXml(client);
		}
		catch (Exception e) {
		    this.msg = "Error building XML data from the VwClientExt data source";
		    logger.log(Level.ERROR, this.msg);
		    throw new ActionHandlerException(this.msg);
		}

		// Get Timesheet data
		try {
		    VwTimesheetSummary emp = new VwTimesheetSummary();
		    emp.addCriteria("TimesheetId", timesheetId);
		    emp.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
		    Object results[] = dao.retrieve(emp);
		    xml += (String) results[0];
		    return xml;
		}
		catch (DatabaseException e) {
		    this.msg = "Error building XML data from the VwTimesheetSummary data source";
		    logger.log(Level.ERROR, this.msg);
		    throw new ActionHandlerException(this.msg);
		}
	}
	catch (ActionHandlerException e) {
		throw e;
	}
	finally {
		daoHelper.close();
		daoHelper = null;
		dao.close();
		dao = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Generates the Summary Timesheet Report.  
     * 
     * @throws ActionHandlerException
     */
    protected abstract void sendClientData() throws ActionHandlerException;
}