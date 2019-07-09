package com.employee;

import com.controller.Request;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjEmployee;
import com.bean.ProjEmployeeProject;
import com.bean.ProjEmployeeTitle;
import com.bean.ProjEmployeeType;
import com.bean.VwEmployeeExt;
import com.bean.VwEmployeeProjects;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.util.SystemException;

/**
 * Manages the creation, modifification, and searching of employee data.
 * 
 * @author appdev
 *
 */
class EmployeeXmlImpl extends RdbmsDaoImpl implements EmployeeApi {

    private static Logger logger = Logger.getLogger(EmployeeXmlImpl.class);

    private RdbmsDaoQueryHelper daoHelper;

    protected String criteria;

    /**
     * Default Constructor.  Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn The database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public EmployeeXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
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
    public EmployeeXmlImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
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
	logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Finds an extended employee data object using custom selection criteria.
     * Data is ordered by last name and first name in ascending order.
     * 
     * @param criteria The selection criteria
     * @return XML document in the representing the format of a list of {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @throws ProjectException
     */
    public String findEmployeeExt(String criteria) throws EmployeeException {
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(VwEmployeeExt.PROP_LASTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	obj.addOrderBy(VwEmployeeExt.PROP_FIRSTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
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
     * @param empProjId
     *           The employee's project id
     * @return XML document in the representing the format of {@link com.bean.VwEmployeeProjects VwEmployeeProjects}
     * @throws EmployeeException
     *           general database access errors
     */
    public String findProject(int empId, int empProjId) throws EmployeeException {
	VwEmployeeProjects obj = EmployeeFactory.createVwEmployeeProjects();
	obj.addCriteria(ProjEmployeeProject.PROP_EMPID, empId);
	obj.addCriteria(ProjEmployeeProject.PROP_EMPPROJID, empProjId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    /**
     * Retrieves all projects relative to a given employee.  Orders the results by client name, 
     * project name, and project employee effective date in ascending, ascending, and descending 
     * order, respectively.
     * 
     * @param empId
     *           The employee id
     * @return XML document in the representing the format of a list of {@link com.bean.ProjEmployeeProject ProjEmployeeProject}
     * @throws EmployeeException
     *           general database access errors
     */
    public String findProject(int empId) throws EmployeeException {
	VwEmployeeProjects obj = EmployeeFactory.createXmlVwEmployeeProjects();
	obj.addCriteria(ProjEmployeeProject.PROP_EMPID, empId);
	obj.addOrderBy(VwEmployeeProjects.PROP_CLIENTNAME, VwEmployeeProjects.ORDERBY_ASCENDING);
	obj.addOrderBy(VwEmployeeProjects.PROP_PROJECTNAME, VwEmployeeProjects.ORDERBY_ASCENDING);
	obj.addOrderBy(VwEmployeeProjects.PROP_PROJEMPEFFECTIVEDATE, VwEmployeeProjects.ORDERBY_DESCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public String findEmployee(int empId) throws EmployeeException {
	ProjEmployee obj = EmployeeFactory.createEmployee();
	obj.addCriteria(ProjEmployee.PROP_EMPID, empId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public String findEmployeeExt(int empId) throws EmployeeException {
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCriteria(VwEmployeeExt.PROP_EMPLOYEEID, empId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }
    
    
   
    public String findEmployeeProject(int empProjId) throws EmployeeException {
	VwEmployeeProjects obj = EmployeeFactory.createVwEmployeeProjects();
	obj.addCriteria(ProjEmployeeProject.PROP_EMPPROJID, empProjId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }   

    public String getLoggedInEmployee() throws EmployeeException {
	RMT2SessionBean sessionBean = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCriteria(VwEmployeeExt.PROP_LOGINNAME, sessionBean.getLoginId());
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new EmployeeException(e);
	}
    }

    public String findEmployeeByTitle(int empTitleId) throws EmployeeException {
	ProjEmployee obj = EmployeeFactory.createEmployee();
	obj.addCriteria(ProjEmployee.PROP_EMPTITLEID, empTitleId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public String findEmployeeTitles() throws EmployeeException {
	ProjEmployeeTitle obj = new ProjEmployeeTitle();
	obj.addOrderBy(ProjEmployeeTitle.PROP_DESCRIPTION, ProjEmployeeTitle.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    public String findEmployeeTypes() throws EmployeeException {
	ProjEmployeeType obj = new ProjEmployeeType();
	obj.addOrderBy(ProjEmployeeType.PROP_DESCRIPTION, ProjEmployeeTitle.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeApi#findManagers()
     */
    public Object findManagers() throws EmployeeException {
	VwEmployeeExt obj = EmployeeFactory.createEmployeeExt();
	obj.addCriteria(VwEmployeeExt.PROP_ISMANAGER, 1);
	obj.addOrderBy(VwEmployeeExt.PROP_LASTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	obj.addOrderBy(VwEmployeeExt.PROP_FIRSTNAME, VwEmployeeExt.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    return null;
	}
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeApi#maintainEmployee(com.bean.ProjEmployee)
     */
    public int maintainEmployee(ProjEmployee employee) throws EmployeeException {
	EmployeeApi nativeApi = EmployeeFactory.createApi(this.connector, this.request);
	return nativeApi.maintainEmployee(employee);
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeApi#maintainEmployeeProject(com.bean.ProjEmployeeProject)
     */
    public int maintainEmployeeProject(ProjEmployeeProject empProj) throws EmployeeException {
	EmployeeApi nativeApi = EmployeeFactory.createApi(this.connector, this.request);
	return nativeApi.maintainEmployeeProject(empProj);
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeApi#findClients(int)
     */
    public Object findClients(int empId) throws EmployeeException {
        // TODO Auto-generated method stub
        return null;
    }

}