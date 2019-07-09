package com.apiimpl;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.api.ProjectTimesheetStatusApi;

import com.api.db.DatabaseException;

import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.ProjTimesheetHist;
import com.bean.ProjTimesheetStatus;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.factory.ProjectManagerFactory;

import com.util.ProjectException;
import com.util.SystemException;
import com.util.RMT2Utility;

/**
 * Implementation of ProjectTimesheetStatusApi that manages a timesheet activities.
 * 
 * @author appdev
 *
 */
public class ProjectTimesheetStatusImpl extends RdbmsDataSourceImpl implements ProjectTimesheetStatusApi  {
	private String criteria;
	private Logger logger;

   /**
    * Default Constructor
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
   protected ProjectTimesheetStatusImpl() throws DatabaseException, SystemException   {
	   super();   
	   logger = Logger.getLogger("ProjectTimesheetImpl");
   }

   /**
    * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
    * 
    * @param dbConn
    * @throws DatabaseException
    * @throws SystemException
    */
    public ProjectTimesheetStatusImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
    	super(dbConn);
        logger = Logger.getLogger("ProjectTimesheetImpl");
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws DatabaseException
     * @throws SystemException
     */
    public ProjectTimesheetStatusImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
        super(dbConn);
        this.setRequest(_request);
        logger = Logger.getLogger("ProjectTimesheetImpl");
    }
    

    
    public ProjTimesheetStatus findTimesheetStatus(int _statusId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjTimesheetStatus");
        this.setBaseView("ProjTimesheetStatusView");
        this.criteria = "id  = " + _statusId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (ProjTimesheetStatus) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    public List findTimesheetStatus() throws ProjectException {
    	this.setBaseClass("com.bean.ProjTimesheetStatus");
        this.setBaseView("ProjTimesheetStatusView");
        this.criteria = null;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    public ProjTimesheetHist findTimesheetCurrentStatus(int _timesheetId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjTimesheetHist");
        this.setBaseView("ProjTimesheetHistView");
        this.criteria = "proj_timesheet_id  = " + _timesheetId + " and  end_date is null ";
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (ProjTimesheetHist) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
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
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
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
        		ut = RMT2Utility.getUserTimeStamp(this.request);	
        		pth.setEndDate(ut.getDateCreated());
        		pth.setUserId(ut.getLoginId());
        		pth.addCriteria("Id", pth.getId());
        		dao.updateRow(pth);
        	}
        	
        	// Add new status
        	pth = ProjectManagerFactory.createTimesheetHist();
        	ut = RMT2Utility.getUserTimeStamp(this.request);
            pth.setProjTimesheetId(_timesheetId);
            pth.setProjTimesheetStatusId(_newStatusId);
        	pth.setEffectiveDate(ut.getDateCreated());
        	pth.setNull("endDate");
        	pth.setUserId(ut.getLoginId());
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
    protected int verifyStatusChange(int _timesheetId, int _newStatusId)  throws ProjectException {
    	ProjTimesheetHist pth = null;
    	int currentStatusId = 0;
    	
    	pth = this.findTimesheetCurrentStatus(_timesheetId);
    	currentStatusId = (pth == null ? ProjectTimesheetStatusApi.STATUS_NEW : pth.getProjTimesheetStatusId());
    	switch (_newStatusId) {
    		case ProjectTimesheetStatusApi.STATUS_NOTSUBMITTED:
    			if (currentStatusId != ProjectTimesheetStatusApi.STATUS_NEW) {
    				this.msg = "Timesheet status can only change to Not Submitted when the current status is New";
    				logger.log(Level.ERROR, this.msg);
    				throw new ProjectException(this.msg);
    			}
    			break;
    			
    		case ProjectTimesheetStatusApi.STATUS_SUBMITTED:
    			if (currentStatusId != ProjectTimesheetStatusApi.STATUS_NOTSUBMITTED) {
    				this.msg = "Timesheet status can only change to Submitted when the current status is Not Submitted";
    				logger.log(Level.ERROR, this.msg);
    				throw new ProjectException(this.msg);
    			}
    			break;
    			
    		case ProjectTimesheetStatusApi.STATUS_APPROVED:
    		case ProjectTimesheetStatusApi.STATUS_DECLINED:
    			if (currentStatusId != ProjectTimesheetStatusApi.STATUS_SUBMITTED) {
    				this.msg = "Timesheet status can only change to Approved or Declined when the current status is Submitted";
    				logger.log(Level.ERROR, this.msg);
    				throw new ProjectException(this.msg);
    			}
    			break;
    	} // end outer switch
    	
    	return currentStatusId;
    }
    
    public int deleteTimesheetStatus(int _timesheetId) throws ProjectException {
        int rc = 0;
        ProjTimesheetHist pth = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        pth = ProjectManagerFactory.createTimesheetHist();
        pth.addCriteria("ProjTimesheetId", _timesheetId);
        try {
            rc = dao.deleteRow(pth);
            return rc;    
        }
        catch (DatabaseException e) {
            throw new ProjectException(e.getMessage());
        }
    }
}