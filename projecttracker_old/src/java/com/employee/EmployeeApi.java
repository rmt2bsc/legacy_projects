package com.employee;


import com.api.BaseDataSource;

import com.bean.ProjEmployee;
import com.bean.ProjEmployeeProject;

/**
 * Declares the methods that are accessible for managing employee data.
 * 
 * @author appdev
 *
 */
public interface EmployeeApi extends BaseDataSource {

    /**
     * Finds Employee by employee Id.
     * 
     * @param empId The employee id.
     * @return an arbitrary object representing the employee when found.   
     *         Otherwise, null is returned.
     * @throws EmployeeException
     */
    Object findEmployee(int empId) throws EmployeeException;

    /**
     * Finds an extended employee data object using empId.
     * 
     * @param empId The employee id.
     * @return an arbitrary object representing the employee when found.   
     *         Otherwise, null is returned.
     * @throws ProjectException
     */
    Object findEmployeeExt(int empId) throws EmployeeException;
    
    /**
     * Finds an extended employee data object using custom selection criteria.
     * 
     * @param criteria The selection criteria
     * @return an arbitrary object representing the employee when found.   
     *         Otherwise, null is returned.
     * @throws ProjectException
     */
    Object findEmployeeExt(String criteria) throws EmployeeException;
    

    /**
     * Finds an extended employee data object using user id of the employee 
     * currently logged on to the system.
     * 
     * @return an arbitrary object representing the employee when found.   
     *         Otherwise, null is returned.
     * @throws EmployeeException
     */
    Object getLoggedInEmployee() throws EmployeeException;

   
    /**
     * Finds one or more employees by a title.
     * 
     * @param empTitleId  The employee's title id 
     * @return the employee
     * @throws ProjectException
     */
    Object findEmployeeByTitle(int empTitleId) throws EmployeeException;

    /**
     * Retrieves the master list of employee titles.
     * 
     * @return an arbitrary object of employee titles
     * @throws ProjectException
     */
    Object findEmployeeTitles() throws EmployeeException;

    /**
     * Retrieves the master list of employee types.
     * @return ArrayList of unknown.objects
     * @throws ProjectException
     */
    Object findEmployeeTypes() throws EmployeeException;
    
    /**
     * Retrieves all managers
     * 
     * @return an arbitrary object of one or more managers
     * @throws EmployeeException
     */
    Object findManagers() throws EmployeeException;
    
    /**
     * Retrieves a single project related to a given employee
     * 
     * @param empId
     *           The employee id
     * @param projId
     *           The project id
     * @return an arbitrary object representing the project when found.   
     *         Otherwise, null is returned.
     * @throws EmployeeException
     */
    Object findProject(int empId, int projId)  throws EmployeeException;
    
    /**
     * Retrieves all projects relative to a given employee
     * 
     * @param empId
     *           The employee id
     * @return an arbitrary object representing all projects belonging to <i>empId</i>.   
     *         Otherwise, null is returned.
     * @throws EmployeeException
     */
    Object findProject(int empId) throws EmployeeException;

    /**
     * Retrieves a single employee project profile.
     * @param empProjId
     *          The employee project id
     * @return an arbitrary object representing the employee project.   
     *         Otherwise, null is returned.
     * @throws EmployeeException
     */
    Object findEmployeeProject(int empProjId)  throws EmployeeException;
    
    /**
     * Retrieves a list of all clients that have projects assigned to a particular employee.
     * 
     * @param empId
     * @return an arbitrary object representing one or more clients.
     * @throws EmployeeException
     */
    Object findClients(int empId) throws EmployeeException;
    
    /**
     * Creates a new or modifies an existing employee and persist the changes to some datasource.
     * 
     * @param employee The employee object that is to be applied to the database.
     * @return int
     * @throws ProjectException
     */
    int maintainEmployee(ProjEmployee employee) throws EmployeeException;
    
    /**
     * Creates a new or modifies an existing employee project profile and persist the changes 
     * to some datasource.
     * 
     * @param empProj
     * @return int
     * @throws EmployeeException
     */
    int maintainEmployeeProject(ProjEmployeeProject empProj) throws EmployeeException;
}
