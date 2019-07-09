package com.api;

import java.util.List;

import com.bean.ProjEmployee;
import com.bean.VwEmployeeExt;

import com.util.EmployeeException;


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
    * @return {@link ProjEmployee}
    * @throws ProjectException
    */
   ProjEmployee findEmployee(int empId) throws EmployeeException;
   
   /**
    * Finds an extended employee data object using empId.
    * 
    * @param empId The employee id.
    * @return {@link VwEmployeeExt}
    * @throws ProjectException
    */
   VwEmployeeExt findEmployeeExt(int empId) throws EmployeeException;
   
   
   /**
    * Finds an extended employee data object using user id of the employee currently logged on to the system.
    * 
    * @return {@link VwEmployeeExt} when found.   Otherwise, null is returned.
    * @throws EmployeeException
    */
   VwEmployeeExt getLoggedInEmployee() throws EmployeeException;
   
   /**
    * Finds Employee by using personId.
    * 
    * @param personId  The person id of the employee profile..
    * @return {@link ProjEmployee}
    * @throws ProjectException
    */
   ProjEmployee findEmployeeByPersonId(int personId) throws EmployeeException;
   
   /**
    * Finds one or more employees by a title.
    * 
    * @param empTitleId  The employee's title id 
    * @return ArrayList of unknown.
    * @throws ProjectException
    */
   List findEmployeeByTitle(int empTitleId) throws EmployeeException;
   
   /**
    * Retrieves the master list of employee titles.
    * 
    * @return ArrayList of unknown.objects
    * @throws ProjectException
    */
   List findEmployeeTitles() throws EmployeeException;
   
   /**
    * Retrieves the master list of employee types.
    * @return ArrayList of unknown.objects
    * @throws ProjectException
    */
   List findEmployeeTypes() throws EmployeeException;

   /**
    * Creates a new or modifies an existing employee and persist the changes to the database.
    * 
    * @param employee The employee object that is to be applied to the database.
    * @return employee id.
    * @throws ProjectException
    */
   int maintainEmployee(ProjEmployee employee) throws EmployeeException;
}
