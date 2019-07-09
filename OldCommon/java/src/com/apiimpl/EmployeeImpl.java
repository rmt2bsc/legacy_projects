package com.apiimpl;

import javax.servlet.http.HttpServletRequest;

import java.sql.Types;

import java.util.List;

import com.api.EmployeeApi;
import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.ProjEmployee;
import com.bean.RMT2SessionBean;
import com.bean.VwEmployeeExt;
import com.bean.db.DatabaseConnectionBean;
import com.constants.RMT2ServletConst;

import com.util.EmployeeException;
import com.util.SystemException;


/**
 * Manages the creation, modifification, and searching of employee data.
 * 
 * @author appdev
 *
 */
public class EmployeeImpl extends RdbmsDataSourceImpl implements EmployeeApi  {

    protected String       criteria;

    /**
     * Default Constructor.  Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn The database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
   public EmployeeImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException  {
       super(dbConn);
       this.setBaseView("ProjEventView");
       this.setBaseClass("com.bean.ProjEvent");
   }
   
   /**
    * Constructor that uses dbConn and _request.
    * 
    * @param dbConn
    * @param _request
    * @throws SystemException
    * @throws DatabaseException
    */
   public EmployeeImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws SystemException, DatabaseException  {
       super(dbConn);
       this.setRequest(_request);
   }

 
   public ProjEmployee findEmployee(int empId) throws EmployeeException  {
       List list;

       this.setBaseView("ProjEmployeeView");
       this.setBaseClass("com.bean.ProjEmployee");
       this.criteria = "id  = " + empId;
       try {
           list = this.find(this.criteria);
           if (list == null || list.size() <= 0) {
               return null;
           }
           return (ProjEmployee) list.get(0);
       }
       catch (SystemException e) {
           this.msgArgs.clear();
           this.msgArgs.add(e.getMessage());
           throw new EmployeeException(this.connector, 1000, this.msgArgs);
       }
   }

   
   public VwEmployeeExt findEmployeeExt(int empId) throws EmployeeException {
       List list = null;

         this.setBaseView("VwEmployeeExtView");
         this.setBaseClass("com.bean.VwEmployeeExt");
         this.criteria = "employee_id  = " + empId;
         try {
             list = this.find(this.criteria);
             if (list == null || list.size() <= 0) {
                 return null;
             }
             return (VwEmployeeExt) list.get(0);
         }
         catch (SystemException e) {
             this.msgArgs.clear();
             this.msgArgs.add(e.getMessage());
             throw new EmployeeException(this.connector, 1000, this.msgArgs);
         }
   }
   

   public VwEmployeeExt getLoggedInEmployee() throws EmployeeException {
       List list = null;
		  
		  RMT2SessionBean sessionBean = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
		  
		  try {
			  this.setBaseClass("com.bean.VwEmployeeExt");
		      this.setBaseView("VwEmployeeExtView");
			  list = this.findData(" login_id =  \'" + sessionBean.getLoginId() + "\'", null);  
			  if (list.size() > 0) {
				  return (VwEmployeeExt) list.get(0);
			  }
			  else {
				  return null;
			  }
		  }
		  catch (SystemException e) {
			  throw new EmployeeException(e.getMessage());
		  }
		  
	  }

   
   public ProjEmployee findEmployeeByPersonId(int personId) throws EmployeeException {
       List list;
    
       this.setBaseView("ProjEmployeeView");
       this.setBaseClass("com.bean.ProjEmployee");
       this.criteria = "person_id  = " + personId;
       try {
           list = this.find(this.criteria);
           if (list == null || list.size() <= 0) {
               return null;
           }
           return (ProjEmployee) list.get(0);
       }
       catch (SystemException e) {
           this.msgArgs.clear();
           this.msgArgs.add(e.getMessage());
           throw new EmployeeException(this.connector, 1000, this.msgArgs);
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
         	 this.msgArgs.clear();
        	 this.msgArgs.add(e.getMessage());
        	 throw new EmployeeException(this.connector, 1000, this.msgArgs);
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
         	 this.msgArgs.clear();
        	 this.msgArgs.add(e.getMessage());
        	 throw new EmployeeException(this.connector, 1000, this.msgArgs);
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
         	 this.msgArgs.clear();
        	 this.msgArgs.add(e.getMessage());
        	 throw new EmployeeException(this.connector, 1000, this.msgArgs);
         }
	 }

   public int maintainEmployee(ProjEmployee _emp) throws EmployeeException {
       int      empId;

       if (_emp == null) {
           throw new EmployeeException(this.connector, 802, null);
       }
       if (_emp.getId() < 0 ) {
           throw new EmployeeException(this.connector, 803, null);
       }
       if (_emp.getPersonId() <= 0 ) {
           throw new EmployeeException(this.connector, 549, null);
       }
       if (_emp.getId() == 0 ) {
           empId = this.createEmployee(_emp);
       }
       else {
           empId = this.updateEmployee(_emp);
       }
       return empId;
   }

   /**
    * Creates an Employee and persist the changes in the database.   a personal profile is expected to have been created prior to processing  _emp.
    * 
    * @param _emp The employee data object.
    * @return The new employee id
    * @throws EmployeeException
    */
   protected int createEmployee(ProjEmployee _emp) throws EmployeeException {
			String method = "createEmployee";
			Object   newObj;
			Integer  intObj;

			 try {
							 //  Add employee.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _emp.getId(), true);
					 this.dynaApi.addParm("person_id", Types.INTEGER,  _emp.getPersonId(), false);
                     this.dynaApi.addParm("manager_id", Types.INTEGER,  _emp.getManagerId(), false);
					 this.dynaApi.addParm("title_id", Types.INTEGER,  _emp.getTitleId(), false);
					 this.dynaApi.addParm("type_id", Types.INTEGER,  _emp.getTypeId(), false);
                     this.dynaApi.addParm("bill_rate", Types.NUMERIC,  _emp.getBillRate(), false);
                     this.dynaApi.addParm("ot_bill_date", Types.NUMERIC,  _emp.getOtBillRate(), false);
					 this.dynaApi.addParm("start_date", Types.DATE,  _emp.getStartDate(), false);
					 this.dynaApi.addParm("temination_date", Types.DATE,  _emp.getTerminationDate(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_add_proj_employee ? ? ? ? ? ? ? ? ?");

								 //  Get new Employee Id
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _emp.setId(intObj.intValue());
					 }
					 return _emp.getId();
			 }
			 catch (DatabaseException e) {
					 throw new EmployeeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new EmployeeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


   /**
    * Updates an existing Employee by applying any changes made to _emp to the database.
    * 
    * @param _emp The employee data object.
    * @return The employee's id
    * @throws EmployeeException
    */
   protected int updateEmployee(ProjEmployee _emp) throws EmployeeException {
			String method = "updateEmployee";

			 try {
							 //  Modify employee.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _emp.getId(), false);
					 this.dynaApi.addParm("person_id", Types.INTEGER,  _emp.getPersonId(), false);
                     this.dynaApi.addParm("manager_id", Types.INTEGER,  _emp.getManagerId(), false);
					 this.dynaApi.addParm("title_id", Types.INTEGER,  _emp.getTitleId(), false);
					 this.dynaApi.addParm("type_id", Types.INTEGER,  _emp.getTypeId(), false);
                     this.dynaApi.addParm("bill_rate", Types.NUMERIC,  _emp.getBillRate(), false);
                     this.dynaApi.addParm("ot_bill_date", Types.NUMERIC,  _emp.getOtBillRate(), false);
					 this.dynaApi.addParm("start_date", Types.DATE,  _emp.getStartDate(), false);
					 this.dynaApi.addParm("temination_date", Types.DATE,  _emp.getTerminationDate(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_upd_proj_employee ? ? ? ? ? ? ? ? ?");
					 return _emp.getId();
			 }
			 catch (DatabaseException e) {
					 throw new EmployeeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new EmployeeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


}