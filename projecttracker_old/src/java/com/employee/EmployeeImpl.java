package com.employee;

import com.controller.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjClient;
import com.bean.ProjEmployee;
import com.bean.ProjEmployeeProject;
import com.bean.VwEmployeeExt;
import com.bean.VwEmployeeProjects;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.project.ProjectException;
import com.project.setup.SetupFactory;
import com.util.RMT2Date;
import com.util.SystemException;

/**
 * Manages the creation, modifification, and searching of employee data.
 * 
 * @author appdev
 *
 */
class EmployeeImpl extends RdbmsDaoImpl implements EmployeeApi {

    private static Logger logger = Logger.getLogger(EmployeeImpl.class);

    private RdbmsDaoQueryHelper daoHelper;

    protected String criteria;
    
    

    /**
     * Default Constructor.  Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn The database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public EmployeeImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws SystemException
     * @throws DatabaseException
     */
    public EmployeeImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
	this.setRequest(request);
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2Base#init()
     */
    @Override
    public void init() {
	super.init();
	EmployeeImpl.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Finds an extended employee data object using custom selection criteria.
     * Data is ordered by last name and first name in ascending order.
     * 
     * @param criteria The selection criteria
     * @return {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @throws ProjectException
     */
    public List findEmployeeExt(String criteria) throws EmployeeException {
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(VwEmployeeExt.PROP_LASTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	obj.addOrderBy(VwEmployeeExt.PROP_FIRSTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    /**
     * Retrieves a single project related to a given employee
     * 
     * @param empId
     *           The employee id
     * @param projId
     *           The project id
     * @return {@link com.bean.ProjEmployeeProject ProjEmployeeProject}
     * @throws EmployeeException
     *           general database access errors
     */
    public ProjEmployeeProject findProject(int empId, int projId) throws EmployeeException {
	ProjEmployeeProject obj = EmployeeFactory.createEmployeeProject();
	obj.addCriteria(ProjEmployeeProject.PROP_EMPID, empId);
	obj.addCriteria(ProjEmployeeProject.PROP_PROJID, projId);
	try {
	    return (ProjEmployeeProject) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }
    
    
    /**
     * Retrieves a single employee project profile.
     * 
     * @param empProjId
     *          The employee project id
     * @return {@link com.bean.ProjEmployeeProject ProjEmployeeProject} 
     *         Otherwise, null is returned.
     * @throws EmployeeException
     */
    public Object findEmployeeProject(int empProjId) throws EmployeeException {
	ProjEmployeeProject obj = EmployeeFactory.createEmployeeProject();
	obj.addCriteria(ProjEmployeeProject.PROP_EMPPROJID, empProjId);
	try {
	    return (ProjEmployeeProject) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }   
    

    /**
     * Retrieves all projects relative to a given employee
     * 
     * @param empId
     *           The employee id
     * @return List of {@link com.bean.ProjEmployeeProject ProjEmployeeProject}
     * @throws EmployeeException
     *           general database access errors
     */
    public List findProject(int empId) throws EmployeeException {
	ProjEmployeeProject obj = EmployeeFactory.createEmployeeProject();
	obj.addCriteria(ProjEmployeeProject.PROP_EMPID, empId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public ProjEmployee findEmployee(int empId) throws EmployeeException {
	ProjEmployee obj = EmployeeFactory.createEmployee();
	obj.addCriteria(ProjEmployee.PROP_EMPID, empId);
	try {
	    return (ProjEmployee) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public VwEmployeeExt findEmployeeExt(int empId) throws EmployeeException {
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCriteria(VwEmployeeExt.PROP_EMPLOYEEID, empId);
	try {
	    return (VwEmployeeExt) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new EmployeeException(e);
	}
    }

    public VwEmployeeExt getLoggedInEmployee() throws EmployeeException {
	RMT2SessionBean sessionBean = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCriteria(VwEmployeeExt.PROP_LOGINNAME, sessionBean.getLoginId());
	try {
	    return (VwEmployeeExt) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new EmployeeException(e);
	}
    }

    public List findEmployeeByTitle(int empTitleId) throws EmployeeException {
	List list;

	this.setBaseView("ProjEmployeeView");
	this.setBaseClass("com.bean.ProjEmployee");
	this.criteria = "title_id  = " + empTitleId;
	try {
	    list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new EmployeeException(e);
	}
    }

    public List findEmployeeTitles() throws EmployeeException {
	List list;

	this.setBaseView("ProjEmployeeTitleView");
	this.setBaseClass("com.bean.ProjEmployeeTitle");
	this.criteria = "";
	try {
	    list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new EmployeeException(e);
	}
    }

    public List findEmployeeTypes() throws EmployeeException {
	List list;

	this.setBaseView("ProjEmployeeTypeView");
	this.setBaseClass("com.bean.ProjEmployeeType");
	this.criteria = "";
	try {
	    list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new EmployeeException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeApi#findManagers()
     */
    public List findManagers() throws EmployeeException {
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCriteria(VwEmployeeExt.PROP_ISMANAGER, 1);
	obj.addOrderBy(VwEmployeeExt.PROP_LASTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	obj.addOrderBy(VwEmployeeExt.PROP_FIRSTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public int maintainEmployee(ProjEmployee emp) throws EmployeeException {
	int empId;

	if (emp == null) {
	    this.msg = "Employee object cannot be null";
	    throw new EmployeeException(this.msg, 802);
	}
	if (emp.getEmpId() == 0) {
	    empId = this.createEmployee(emp);
	}
	else {
	    empId = this.updateEmployee(emp);
	}
	return empId;
    }

    /**
     * Creates an Employee and persist the changes in the database.   a personal profile is expected to have been 
     * created prior to processing  _emp.
     * 
     * @param emp The employee data object.
     * @return The new employee id
     * @throws EmployeeException
     */
    protected int createEmployee(ProjEmployee emp) throws EmployeeException {
	this.validateEmployee(emp);
	try {
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    emp.setDateCreated(ut.getDateCreated());
	    emp.setDateUpdated(ut.getDateCreated());
	    emp.setUserId(ut.getLoginId());
	    if (emp.getManagerId() == 0) {
		emp.setNull(ProjEmployee.PROP_MANAGERID);
	    }
	    int rc = this.insertRow(emp, true);
	    emp.setEmpId(rc);
	    return rc;
	}
	catch (Exception e) {
	    throw new EmployeeException(e);
	}
    }

    /**
     * Updates an existing Employee by applying any changes made to <i>emp</i> to the database.
     * 
     * @param emp 
     *          The employee data object.
     * @return int 
     *          The employee's id
     * @throws EmployeeException
     *          If employee profile does not contain an employee type or an employee title, or if a general 
     *          database error occurs.
     */
    protected int updateEmployee(ProjEmployee emp) throws EmployeeException {
	this.validateEmployee(emp);
	try {
	    // Get original employee from database and set the manager id, employee type id, and the employee title id
	    ProjEmployee deltaEmp = this.findEmployee(emp.getEmpId());
	    deltaEmp.setManagerId(emp.getManagerId());
	    deltaEmp.setEmpTitleId(emp.getEmpTitleId());
	    deltaEmp.setEmpTypeId(emp.getEmpTypeId());
	    if (deltaEmp.getManagerId() == 0) {
		deltaEmp.setNull(ProjEmployee.PROP_MANAGERID);
	    }

	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    deltaEmp.setDateUpdated(ut.getDateCreated());
	    deltaEmp.setUserId(ut.getLoginId());
	    deltaEmp.addCriteria(ProjEmployee.PROP_EMPID, deltaEmp.getEmpId());
	    this.updateRow(deltaEmp);
	    return deltaEmp.getEmpId();
	}
	catch (Exception e) {
	    throw new EmployeeException(e);
	}
    }

    /**
     * Verifies that the employee contains a title and a type.
     * 
     * @param emp
     * @throws EmployeeException
     *          If <i>emp</i> does not contain an employee type or an employee title.
     */
    protected void validateEmployee(ProjEmployee emp) throws EmployeeException {
	if (emp.getEmpTitleId() == 0) {
	    this.msg = "Employee profile must contain an employee title";
	    logger.log(Level.ERROR, this.msg);
	    throw new EmployeeException(this.msg, 804);
	}
	if (emp.getEmpTypeId() == 0) {
	    this.msg = "Employee profile must contain an employee type";
	    logger.log(Level.ERROR, this.msg);
	    throw new EmployeeException(this.msg, 805);
	}
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeApi#maintainEmployeeProject(com.bean.ProjEmployeeProject)
     */
    public int maintainEmployeeProject(ProjEmployeeProject empProj) throws EmployeeException {
	int empProjId;

	if (empProj == null) {
	    this.msg = "Employee Project object cannot be null";
	    throw new EmployeeException(this.msg, 820);
	}
	if (empProj.getEmpProjId() == 0) {
	    empProjId = this.createEmployeeProject(empProj);
	}
	else {
	    empProjId = this.updateEmployeeProject(empProj);
	}
	return empProjId;
    }
    
    
    /**
     * Creates an Employee Project and persist the changes in the database.
     * 
     * @param emp The employee project data object.
     * @return int
     *           The new employee project id
     * @throws EmployeeException
     *           If employee project profile does not contain a project, or if a general 
     *           database error occurs.
     */
    protected int createEmployeeProject(ProjEmployeeProject empProj) throws EmployeeException {
	this.validateEmployeeProject(empProj);
	try {
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    empProj.setDateCreated(ut.getDateCreated());
	    empProj.setDateUpdated(ut.getDateCreated());
	    empProj.setUserId(ut.getLoginId());
	    empProj.setIpCreated(ut.getIpAddr());
	    empProj.setIpUpdated(ut.getIpAddr());
	    int rc = this.insertRow(empProj, true);
	    empProj.setEmpProjId(rc);
	    return rc;
	}
	catch (Exception e) {
	    throw new EmployeeException(e);
	}
    }

    /**
     * Updates an existing Employee Project by applying any changes made to <i>empProj</i> to the database.
     * 
     * @param emp 
     *          The employee project data object.
     * @return int 
     *          The employee project id
     * @throws EmployeeException
     *          If employee project profile does not contain a project, or if a general 
     *          database error occurs.
     */
    protected int updateEmployeeProject(ProjEmployeeProject empProj) throws EmployeeException {
	this.validateEmployeeProject(empProj);
	try {
	    // Get original employee project from database and set the end date, pay rate, overtime pay rate, and flat rate.
	    ProjEmployeeProject delta = (ProjEmployeeProject) this.findEmployeeProject(empProj.getEmpProjId());
	    delta.setEndDate(empProj.getEndDate());
	    delta.setHourlyRate(empProj.getHourlyRate());
	    delta.setHourlyOverRate(empProj.getHourlyOverRate());
	    delta.setFlatRate(empProj.getFlatRate());

	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    delta.setDateUpdated(ut.getDateCreated());
	    delta.setUserId(ut.getLoginId());
	    delta.setIpUpdated(ut.getIpAddr());
	    delta.addCriteria(ProjEmployeeProject.PROP_EMPID, delta.getEmpId());
	    delta.addCriteria(ProjEmployeeProject.PROP_EMPPROJID, delta.getEmpProjId());
	    this.updateRow(delta);
	    return delta.getEmpId();
	}
	catch (Exception e) {
	    throw new EmployeeException(e);
	}
    }
   
    /**
     * Verifies that the employee has never been assinged to the specified project.
     * 
     * @param empProj
     * @throws EmployeeException
     *          If the employee id - project id combination exists in the proj_employee_project table.
     */
    protected void validateEmployeeProject(ProjEmployeeProject empProj) throws EmployeeException {
        Object result = this.findProject(empProj.getEmpId(), empProj.getProjId());
        if (result != null) {
            this.msg = "The employee is already assigned to the specified project.  Employee Id [" + empProj.getEmpId() + "] and Project Id [" + empProj.getProjId() + "]";
            logger.error(this.msg);
            throw new EmployeeException(this.msg);
        }
	return;
    }

    /**
     * Retrieves a list of all clients that have projects assigned to a particular employee.
     * 
     * @param empId
     * @return List {@link com.project.setup.ProjClient ProjClient}
     * @throws EmployeeException
     */
    public List <ProjClient> findClients(int empId) throws EmployeeException {
        // Get ProjClient objects
        
        List <VwEmployeeProjects> projList;
        try {
            VwEmployeeProjects obj = EmployeeFactory.createVwEmployeeProjects();
            obj.addOrderBy(VwEmployeeProjects.PROP_CLIENTNAME, ProjClient.ORDERBY_ASCENDING);
            projList = this.daoHelper.retrieveList(obj);
            if (projList ==null) {
                return null;
            }
        }
        catch (Exception e) {
            throw new EmployeeException(e);
        }
        
        Map <String, String> map = new HashMap<String, String>();
        List <ProjClient> clientList = new ArrayList <ProjClient>();
        for (VwEmployeeProjects proj : projList) {
            try {
                // Use map to eliminate duplicate entries when building client list.
                String clientId = String.valueOf(proj.getClientId());
                Object result = map.put(clientId, proj.getClientName());
                // A non-null value means a duplicate was found and replaced.  No need to add client to List.
                if (result == null) {
                    ProjClient client = new ProjClient();
                    client.setClientId(proj.getClientId());
                    client.setName(proj.getClientName());
                    clientList.add(client);    
                }
            }
            catch (Exception e) {
                throw new EmployeeException("Problem building list of employee clients", e);
            }
        }
        return clientList;
    }

}