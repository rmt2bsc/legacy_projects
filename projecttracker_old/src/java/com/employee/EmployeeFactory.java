package com.employee;

import com.controller.Request;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.bean.ProjEmployee;
import com.bean.ProjEmployeeProject;
import com.bean.VwEmployeeExt;
import com.bean.VwEmployeeProjects;
import com.bean.VwTimesheetList;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * 
 * @author RTerrell
 *
 */
public class EmployeeFactory extends DataSourceAdapter {

    /**
     * Instantiates an employee Api initialized with a DatabaseConnectionBean instance.
     *  
     * @param dbo Database connection bean
     * @return {@link EmployeeApi}
     */
    public static EmployeeApi createApi(DatabaseConnectionBean dbo) {
	try {
	    EmployeeApi api = new EmployeeImpl(dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Instantiates an employee Api initialized with a DatabaseConnectionBean instance and the user's request.
     * 
     * @param dbo Database connection bean
     * @param request Servlet Http Request object.
     * @return
     */
    public static EmployeeApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    EmployeeApi api = new EmployeeImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    
    /**
     * Instantiates an employee XML Api initialized with a DatabaseConnectionBean instance.
     *  
     * @param dbo Database connection bean
     * @return {@link EmployeeApi}
     */
    public static EmployeeApi createXmlApi(DatabaseConnectionBean dbo) {
	try {
	    EmployeeApi api = new EmployeeXmlImpl(dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Instantiates an employee XML Api initialized with a DatabaseConnectionBean instance and the user's request.
     * 
     * @param dbo Database connection bean
     * @param request Servlet Http Request object.
     * @return
     */
    public static EmployeeApi createXmlApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    EmployeeApi api = new EmployeeXmlImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Cerates an employee object.
     * 
     * @return {@link ProjEmployee}
     * @throws SystemException
     * 
     */
    public static ProjEmployee createEmployee() throws SystemException {
	try {
	    return new ProjEmployee();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a VwEmployeeExt object.
     * 
     * @return VwEmployeeExt
     */
    public static VwEmployeeExt createEmployeeExt() {
	try {
	    return new VwEmployeeExt();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a ProjEmployeeProject object.
     * 
     * @return {@link com.bean.ProjEmployeeProject ProjEmployeeProject}
     */
    public static ProjEmployeeProject createEmployeeProject() {
	try {
	    return new ProjEmployeeProject();
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    /**
     * Creates a VwEmployeeProjects object.
     * @return {@link com.bean.VwEmployeeProjects VwEmployeeProjects}
     */
    public static VwEmployeeProjects createVwEmployeeProjects() {
	try {
	    return new VwEmployeeProjects();
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    /**
     * Creates a XML VwEmployeeProjects object.
     * @return {@link com.bean.VwEmployeeProjects VwEmployeeProjects}
     */
    public static VwEmployeeProjects createXmlVwEmployeeProjects() {
	VwEmployeeProjects obj = EmployeeFactory.createVwEmployeeProjects();
	obj.setResultsetType(VwTimesheetList.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }
}