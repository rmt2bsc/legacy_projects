package com.project.timesheet;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;

import java.util.List;

import com.api.db.DatabaseException;

import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.ProjTimesheetHist;
import com.bean.ProjTimesheetStatus;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.project.ProjectException;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * Implementation of TimesheetStatusApi that manages a timesheet activities.
 * 
 * @author appdev
 *
 */
public class TimesheetStatusImpl extends RdbmsDataSourceImpl implements TimesheetStatusApi {
    private String criteria;

    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Default Constructor
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected TimesheetStatusImpl() throws DatabaseException, SystemException {
	super();
	logger = Logger.getLogger("TimesheetImpl");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public TimesheetStatusImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws DatabaseException
     * @throws SystemException
     */
    public TimesheetStatusImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn);
	this.setRequest(request);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2Base#init()
     */
    @Override
    public void init() {
	super.init();
	this.logger = Logger.getLogger("TimesheetImpl");
    }

    public ProjTimesheetStatus findTimesheetStatus(int statusId) throws ProjectException {
	ProjTimesheetStatus obj = TimesheetFactory.createTimesheetStatus();
	obj.addCriteria(ProjTimesheetStatus.PROP_TIMESHEETSTATUSID, statusId);
	try {
	    obj = (ProjTimesheetStatus) this.daoHelper.retrieveObject(obj);
	    return obj;
	}
	catch (DatabaseException e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves the entire master list of timesheet statuses which the data set 
     * is orderd by timesheet status id in ascending order.
     * 
     * @return An ArrayList of {@link ProjTimesheetStatus}
     * @throws ProjectException
     */
    public List findTimesheetStatus() throws ProjectException {
	ProjTimesheetStatus obj = TimesheetFactory.createTimesheetStatus();
	obj.addOrderBy(ProjTimesheetStatus.PROP_TIMESHEETSTATUSID, ProjTimesheetStatus.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public ProjTimesheetHist findTimesheetCurrentStatus(int timesheetId) throws ProjectException {
	ProjTimesheetHist obj = TimesheetFactory.createTimesheetHist();
	obj.addCustomCriteria("timesheet_id = " +  timesheetId + " and end_date is null ");
	try {
	    obj = (ProjTimesheetHist) this.daoHelper.retrieveObject(obj);
	    return obj;
	}
	catch (DatabaseException e) {
	    throw new ProjectException(e);
	}
    }

    public List findTimesheetStatusHist(int _timesheetId) throws ProjectException {
	this.setBaseClass("com.bean.ProjTimesheetHist");
	this.setBaseView("ProjTimesheetHistView");
	this.criteria = "proj_timesheet_id  = " + _timesheetId;
	this.criteria = null;
	try {
	    List list = this.find(this.criteria);
	    if (list == null || list.size() <= 0) {
		return null;
	    }
	    return list;
	}
	catch (SystemException e) {
	    this.msg = "Unable to locate status history for timesheet, " + _timesheetId;
	    logger.error(this.msg);
	    throw new ProjectException(this.msg, e);
	}
    }

    public void setTimesheetStatus(int _timesheetId, int _newStatusId) throws ProjectException {
	UserTimestamp ut = null;
	ProjTimesheetHist pth = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	this.verifyStatusChange(_timesheetId, _newStatusId);
	pth = this.findTimesheetCurrentStatus(_timesheetId);
	try {
	    if (pth != null) {
		ut = RMT2Date.getUserTimeStamp(this.request);
		pth.setEndDate(ut.getDateCreated());
		pth.setUserId(ut.getLoginId());
		pth.setIpUpdated(ut.getIpAddr());
		pth.addCriteria(ProjTimesheetHist.PROP_TIMESHEETHISTID, pth.getTimesheetHistId());
		dao.updateRow(pth);
	    }

	    // Add new status
	    pth = TimesheetFactory.createTimesheetHist();
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    pth.setTimesheetId(_timesheetId);
	    pth.setTimesheetStatusId(_newStatusId);
	    pth.setEffectiveDate(ut.getDateCreated());
	    pth.setNull("endDate");
	    pth.setUserId(ut.getLoginId());
	    pth.setIpCreated(ut.getIpAddr());
	    pth.setIpUpdated(ut.getIpAddr());
	    dao.insertRow(pth, true);
	}
	catch (DatabaseException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
	catch (SystemException e) {
	    throw new ProjectException(this.msg);
	}
    }

    /**
     * Verifies that changing the status of the timesheetr identified as _timesheetId to the new status represented as _newStatusId is legal.
     * The change is considered legal only if an exception is not thrown.
     * <p>
     * The following sequence must be followed when changing the status of a purchase order:
     * <p>
     * <ul>
     * <li>The timesheet must be new in order to change the status to "Not Submitted"</li>
     * <li>The timesheet must be in "Not Submitted" status before changing to "Submitted".</li>
     * <li>The timesheet must be in "Submitted" status before changing to "Received".</li>
     * <li>The timesheet must be in "Received" status before changing to "Approved" or "Declined".</li>
     * </ul>
     * 
     * @param _timesheetId Target timesheet id
     * @param _newStatusId The id of the status that is to be assigned to the timesheet
     * @return The id of the old status.
     * @throws ProjectException When the prospective new status is not in sequence to the current status regarding 
     * changing the status of the timesheet.   The exception should give a detail explanation as to the reason why the 
     * status cannot be changed. 
     */
    protected int verifyStatusChange(int _timesheetId, int _newStatusId) throws ProjectException {
	ProjTimesheetHist pth = null;
	int currentStatusId = 0;

	pth = this.findTimesheetCurrentStatus(_timesheetId);
	currentStatusId = (pth == null ? TimesheetStatusApi.STATUS_NEW : pth.getTimesheetStatusId());
	switch (_newStatusId) {
	case TimesheetStatusApi.STATUS_NOTSUBMITTED:
	    if (currentStatusId != TimesheetStatusApi.STATUS_NEW) {
		this.msg = "Timesheet status can only change to Not Submitted when the current status is New";
		logger.log(Level.ERROR, this.msg);
		throw new ProjectException(this.msg);
	    }
	    break;

	case TimesheetStatusApi.STATUS_SUBMITTED:
	    if (currentStatusId != TimesheetStatusApi.STATUS_NOTSUBMITTED) {
		this.msg = "Timesheet status can only change to Submitted when the current status is Not Submitted";
		logger.log(Level.ERROR, this.msg);
		throw new ProjectException(this.msg);
	    }
	    break;

	case TimesheetStatusApi.STATUS_APPROVED:
	case TimesheetStatusApi.STATUS_DECLINED:
	    if (currentStatusId != TimesheetStatusApi.STATUS_SUBMITTED) {
		this.msg = "Timesheet status can only change to Approved or Declined when the current status is Submitted";
		logger.log(Level.ERROR, this.msg);
		throw new ProjectException(this.msg);
	    }
	    break;
	} // end outer switch

	return currentStatusId;
    }

    public int deleteTimesheetStatus(int timesheetId) throws ProjectException {
	int rc = 0;
	ProjTimesheetHist pth = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	pth = TimesheetFactory.createTimesheetHist();
	pth.addCriteria(ProjTimesheetHist.PROP_TIMESHEETID, timesheetId);
	try {
	    rc = dao.deleteRow(pth);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new ProjectException(e.getMessage());
	}
    }
}