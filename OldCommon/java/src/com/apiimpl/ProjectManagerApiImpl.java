package com.apiimpl;

import java.sql.Types;

import java.util.List;
import java.util.ArrayList;

import com.api.ProjectManagerApi;

import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;

import com.api.DataSourceApi;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.RMT2Base;
import com.bean.ProjEmployee;
import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjTask;
import com.bean.ProjEvent;
import com.bean.db.DatabaseConnectionBean;

import com.util.ProjectException;
import com.util.SystemException;
import com.util.NotFoundException;


/**
 * Api Implementation that manages the employee projects.
 * 
 * @author RTerrell
 *
 */
public class ProjectManagerApiImpl extends RdbmsDataSourceImpl implements ProjectManagerApi  {

    protected String       criteria;
    protected ProjEvent  event;
    protected ArrayList   eventDetails;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: ProjectManagerApiImpl
//    Prototype: void
//      Returns: void
//       Throws: DatabaseException, SystemException
//  Description: Default Constructor.  Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjectManagerApiImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException  {
			 super(dbConn);
			 this.setBaseView("ProjEventView");
			 this.setBaseClass("com.bean.ProjEvent");
	 }

 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEmp
//    Prototype: int value
//      Returns: ProjEmployee
//       Throws: ProjectException
//  Description: Finds Employee by employee Id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjEmployee findEmp(int value) throws ProjectException  {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEmployeeView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEmployee");
			 this.criteria = "id  = " + value;
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
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEmp
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Finds one or more employee by using custom criteria (criteria) supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findEmp(String _criteria) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEmployeeView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEmployee");
			 this.criteria = _criteria;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEmpByPersonId
//    Prototype: String _criteria
//      Returns: ProjEmployee
//       Throws: ProjectException
//  Description: Finds employee by using person Id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjEmployee findEmpByPersonId(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEmployeeView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEmployee");
			 this.criteria = "person_id  = " + value;
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
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEmpByTitle
//    Prototype: int value
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Find one or more employees by Title.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findEmpByTitle(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEmployeeView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEmployee");
			 this.criteria = "title_id  = " + value;;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getEmpTitles
//    Prototype: none
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Retrieves all of the employee titles
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List getEmpTitles() throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEmployeeTitleView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEmployeeTitle");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getEmpTypes
//    Prototype: none
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Retrieves all Employee Types
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List getEmpTypes() throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEmployeeTypeView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEmployeeType");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getEventStatus
//    Prototype: none
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Returns an ArrayList of all the event status
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List getEventStatus() throws com.util.ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEventStatusView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEventStatus");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findClient
//    Prototype: none
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Find Client by using Client Id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjClient findClient(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjClientView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjClient");
			 this.criteria = "id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (ProjClient) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findClientByBusinessId
//    Prototype: int value
//      Returns: ProjClient
//       Throws: ProjectException
//  Description: Find Client by Business Id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjClient findClientByBusinessId(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjClientView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjClient");
			 this.criteria = "business_id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (ProjClient) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findProject
//    Prototype: int value
//      Returns: ProjProject
//       Throws: ProjectException
//  Description:Find Project by Id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjProject findProject(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjProjectView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjProject");
			 this.criteria = "id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (ProjProject) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findProject
//    Prototype: String _criteria
//      Returns: ProjProject
//       Throws: ProjectException
//  Description: Finds one or more Projects by using custom criteria provided by the client
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findProject(String _criteria) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjProjectView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjProject");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findProjectByClientId
//    Prototype: int value
//      Returns: ProjProject
//       Throws: ProjectException
//  Description: Finds one or more projects using client id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findProjectByClientId(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjProjectView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjProject");
			 this.criteria = "proj_client_id = " + value;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findRole
//    Prototype: int value
//      Returns: ProjProject
//       Throws: ProjectException
//  Description: Find Project Role by Role Id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjTask findRole(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjRoleView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjRole");
			 this.criteria = "id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (ProjTask) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findRole
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Find one or more Project Roles by using custom criteria supplied b the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findRole(String _criteria) throws com.util.ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjRoleView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjRole");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findRoleByBillable
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Find one or more Project Roles based on billable indicator.   Return all roles
//                    that are billable when "flag" = 1.   Return all roles that are non-billable when "flag" = 0.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findRoleByBillable(int flag) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjRoleView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjRole");
			 this.criteria = "billable = " + flag;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEvent
//    Prototype: int value
//      Returns: ProjEvent
//       Throws: ProjectException
//  Description: Find Project Event using project Event Id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ProjEvent findEvent(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEventView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEvent");
			 this.criteria = "id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (ProjEvent) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEvent
//    Prototype: String _criteria
//      Returns: ProjEvent
//       Throws: ProjectException
//  Description: Find one or more Project Events using custom criteria supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findEvent(String _criteria) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEventView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEvent");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEventDetailsByEventId
//    Prototype: int value
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Retrieves one or more Project Event Detail items based on the Project Event Id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findEventDetailsByEventId(int value) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEventDetailsView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEventDetails");
			 this.criteria = "proj_event_id = " + value;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findEventDetails
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Retrieves one or more Project Event Detail Items using custom
//                    selection criteria supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findEventDetails(String _criteria) throws ProjectException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("ProjEventDetailsView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjEventDetails");
			 this.criteria = "";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getBillRate
//    Prototype: int _empId - employee Id
//                    int _clientId - client id
//                    int _projId - Project Id
//      Returns: ArrayList
//       Throws: ProjectException
//  Description: Retrieves the current bill rate for the  employee of a particular project.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List getBillRate(int _empId, int _clientId, int _projId) throws ProjectException {
 			 String prevBaseClass;
			 String prevBaseView;
			 List  list = new ArrayList();

			 prevBaseView = this.setBaseView("ProjClientBillHistView");
			 prevBaseClass = this.setBaseClass("com.bean.ProjClientBillHist");

			 if (_empId == 0 && _clientId == 0) {
					 this.criteria = " end_date is null";
			 }
			 if (_empId != 0 && _clientId == 0) {
					 this.criteria = " proj_employee_id = " + _empId + " and end_date is null";
			 }
			 if (_empId == 0 && _clientId != 0) {
					 this.criteria = " proj_client_id = " + _clientId + " and end_date is null";
			 }
			 if (_empId != 0 && _clientId != 0) {
					 this.criteria = "proj_employee_id = " + _empId + " and proj_client_id = " + _clientId + " and end_date is null";
			 }

			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new ProjectException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainEmployee
//    Prototype: ProjEmployee _empId - employee Object
//                    Person _person - Person Object
//      Returns: int  - Employee Id
//       Throws: ProjectException
//  Description: Creates or modifies Employee data.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainEmployee(ProjEmployee _emp) throws ProjectException {
			 int      empId;
			 if (_emp == null) {
					 throw new ProjectException(this.connector, 802, null);
			 }
			 if (_emp.getId() < 0 ) {
					 throw new ProjectException(this.connector, 803, null);
			 }
			 if (_emp.getPersonId() <= 0 ) {
					 throw new ProjectException(this.connector, 549, null);
			 }
			 if (_emp.getId() == 0 ) {
					 empId = this.createEmployee(_emp);
			 }
			 else {
					 empId = this.updateEmployee(_emp);
			 }

			 return empId;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createEmployee
//    Prototype: ProjEmployee _empId - employee Object
//      Returns: int  - Employee Id
//       Throws: ProjectException
//  Description: Creates an Employee.   _person is expected to have  been processed before _emp is processed.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int createEmployee(ProjEmployee _emp) throws ProjectException {
			String method = "createEmployee";
			Object   newObj;
			Integer  intObj;

			 try {
							 //  Add employee.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _emp.getId(), true);
					 this.dynaApi.addParm("person_id", Types.INTEGER,  _emp.getPersonId(), false);
					 this.dynaApi.addParm("title_id", Types.INTEGER,  _emp.getTitleId(), false);
					 this.dynaApi.addParm("type_id", Types.INTEGER,  _emp.getTypeId(), false);
					 this.dynaApi.addParm("start_date", Types.DATE,  _emp.getStartDate(), false);
					 this.dynaApi.addParm("temination_date", Types.DATE,  _emp.getTerminationDate(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_add_proj_employee ? ? ? ? ? ?");

								 //  Get new Employee Id
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _emp.setId(intObj.intValue());
					 }
					 return _emp.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateEmployee
//    Prototype: ProjEmployee _empId - employee Object
//                    Person _person - Person Object
//      Returns: int  - Employee Id
//       Throws: ProjectException
//  Description: Updates and Employee.   _person is expected to have  been processed before _emp is processed.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateEmployee(ProjEmployee _emp) throws ProjectException {
			String method = "updateEmployee";

			 try {
							 //  Modify employee.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _emp.getId(), false);
					 this.dynaApi.addParm("person_id", Types.INTEGER,  _emp.getPersonId(), false);
					 this.dynaApi.addParm("title_id", Types.INTEGER,  _emp.getTitleId(), false);
					 this.dynaApi.addParm("type_id", Types.INTEGER,  _emp.getTypeId(), false);
					 this.dynaApi.addParm("start_date", Types.DATE,  _emp.getStartDate(), false);
					 this.dynaApi.addParm("temination_date", Types.DATE,  _emp.getTerminationDate(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_upd_proj_employee ? ? ? ? ? ?");
					 return _emp.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainClient
//    Prototype: ProjClient _client - Client Object
//                    Business _business - Business Object
//      Returns: int  - Client Id
//       Throws: ProjectException
//  Description: Creates or Modifies a Client.   _business is expected to have  been processed before _client is processed.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainClient(ProjClient _client) throws ProjectException {
			 int      clientId;
			 if (_client == null) {
					 throw new ProjectException(this.connector, 804, null);
			 }
			 if (_client.getId() < 0 ) {
					 throw new ProjectException(this.connector, 805, null);
			 }
			 if (_client.getId() == 0 ) {
					 clientId = this.createClient(_client);
			 }
			 else {
					 clientId = this.updateClient(_client);
			 }

			 return clientId;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createClient
//    Prototype: ProjClient _client - client Object
//                    Business _business - Business Object
//      Returns: int  - Client Id
//       Throws: ProjectException
//  Description: Creates an Client.   _business is expected to have  been processed before _client is processed.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int createClient(ProjClient _client) throws ProjectException {
			String method = "createClient";
			Object   newObj;
			Integer  intObj;

			 try {
							 //  Add client.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _client.getId(), true);
					 //this.dynaApi.addParm("business_id", Types.INTEGER,  _client.getBusinessId(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_add_proj_client ?");

								 //  Get new Employee Id
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _client.setId(intObj.intValue());
					 }
					 return _client.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateClient
//    Prototype: ProjClient _client - client Object
//                    Business _business - Business Object
//      Returns: int  - Client Id
//       Throws: ProjectException
//  Description: Updates a Client.   _business is expected to have  been processed before _client is processed.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateClient(ProjClient _client) throws ProjectException {
			String method = "updateClient";

			 try {
							 //  Update client.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _client.getId(), false);
					 //this.dynaApi.addParm("business_id", Types.INTEGER,  _client.getBusinessId(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_upd_proj_client ? ?");
					 return _client.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainProject
//    Prototype: ProjProject _proj - Project Object
//      Returns: int  - Project Id
//       Throws: ProjectException
//  Description: Creates or Updates a Project.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainProject(ProjProject _proj) throws ProjectException {
			 int      projId;

			 if (_proj == null) {
					 throw new ProjectException(this.connector, 806, null);
			 }
			 if (_proj.getId() < 0 ) {
					 throw new ProjectException(this.connector, 807, null);
			 }

			 if (_proj.getId() == 0 ) {
					 projId = this.createProject(_proj);
			 }
			 else {
					 projId = this.updateProject(_proj);
			 }
			 return projId;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createProject
//    Prototype: ProjProject _proj - Project Object
//      Returns: int  - Project Id
//       Throws: ProjectException
//  Description: Creates a Project.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int createProject(ProjProject _proj) throws ProjectException {
			String method = "createProject";
			Object   newObj;
			Integer  intObj;

			 try {
							 //  Add Project.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _proj.getId(), true);
					 this.dynaApi.addParm("projClientId", Types.INTEGER,  _proj.getProjClientId(), false);
					 this.dynaApi.addParm("description", Types.VARCHAR,  _proj.getDescription(), false);
					 this.dynaApi.addParm("effectiveDate", Types.DATE,  _proj.getEffectiveDate(), false);
					 this.dynaApi.addParm("endDate", Types.DATE,  _proj.getEndDate(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_add_proj_project ? ? ? ? ?");

								 //  Get new Employee Id
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _proj.setId(intObj.intValue());
					 }

					 return _proj.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateProject
//    Prototype: ProjProject _proj - Project Object
//      Returns: int  - Project Id
//       Throws: ProjectException
//  Description: Updates a Project.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateProject(ProjProject _proj) throws ProjectException {
			String method = "updateProject";

			 try {
							 //  Add Project.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _proj.getId(), false);
					 this.dynaApi.addParm("projClientId", Types.INTEGER,  _proj.getProjClientId(), false);
					 this.dynaApi.addParm("description", Types.VARCHAR,  _proj.getDescription(), false);
					 this.dynaApi.addParm("effectiveDate", Types.DATE,  _proj.getEffectiveDate(), false);
					 this.dynaApi.addParm("endDate", Types.DATE,  _proj.getEndDate(), false);
								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_upd_proj_project ? ? ? ? ?");
					 return _proj.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainEvent
//    Prototype: ProjEvent _event - Event Object
//      Returns: int  - Event Id
//       Throws: ProjectException
//  Description: Creates or Updates a Project Event
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainEvent(ProjEvent _event) throws ProjectException {
			 int      eventId;

			 if (_event == null) {
					 throw new ProjectException(this.connector, 808, null);
			 }
			 if (_event.getId() < 0 ) {
					 throw new ProjectException(this.connector, 809, null);
			 }

			 if (_event.getId() == 0 ) {
					 eventId = this.createEvent(_event);
			 }
			 else {
					 eventId = this.updateEvent(_event);
			 }
			 return eventId;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createEvent
//    Prototype: ProjEvent _event - Event Object
//      Returns: int  - Event Id
//       Throws: ProjectException
//  Description: Creates a Project Event
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int createEvent(ProjEvent _event) throws ProjectException {
			String method = "createEvent";
			Object   newObj;
			Integer  intObj;

			 try {
							 //  Add Event.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _event.getId(), true);
//					 this.dynaApi.addParm("effectiveDate", Types.DATE,  _event.getEffectiveDate(), false);
//					 this.dynaApi.addParm("endDate", Types.DATE,  _event.getEndDate(), false);
//					 this.dynaApi.addParm("projEmployeeId", Types.INTEGER,  _event.getProjEmployeeId(), false);
//					 this.dynaApi.addParm("totalHours", Types.NUMERIC,  _event.getTotalHours(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_add_proj_event ? ? ? ? ?");

								 //  Get new Event Id
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _event.setId(intObj.intValue());
					 }
					 return _event.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateEvent
//    Prototype: ProjEvent _event - Event Object
//      Returns: int  - Event Id
//       Throws: ProjectException
//  Description: Updates a Project Event
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateEvent(ProjEvent _event) throws ProjectException {
			String method = "updateEvent";

			 try {
							 //  Update Event.
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _event.getId(), false);
//					 this.dynaApi.addParm("effectiveDate", Types.DATE,  _event.getEffectiveDate(), false);
//					 this.dynaApi.addParm("endDate", Types.DATE,  _event.getEndDate(), false);
//					 this.dynaApi.addParm("projEmployeeId", Types.INTEGER,  _event.getProjEmployeeId(), false);
//					 this.dynaApi.addParm("totalHours", Types.NUMERIC,  _event.getTotalHours(), false);

								 // Call stored procedure to add Transaction
					 this.dynaApi.execute("exec usp_upd_proj_event ? ? ? ? ?");
					 return _event.getId();
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }





/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: calcHours
//    Prototype: int _eventId - event Id
//      Returns: double - total hours calculated
//       Throws: ProjectException
//  Description: Computes time for a specific project event.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public double calcHours(int _eventId) throws ProjectException {

			String method = "calcHours(int)";
      Object newObj;
      Double  decObj;

			      // Event Id must be a value greater than zero.
			 if (_eventId <= 0) {
					 throw new ProjectException(this.connector, 800, null);
			}

			 try {
					      //  Begin to invoice Sales Order
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("event_id", Types.INTEGER,  _eventId, false);
					 this.dynaApi.addParm("hours", Types.DOUBLE,  0, true);

										 // Call stored procedure to invoice sales order
					 this.dynaApi.execute("exec usp_calc_proj_hours ? ?");

                    //  Get new invoice id.
	         newObj = this.dynaApi.getOutParm("hours");
	         if (newObj instanceof Double) {
							 decObj = (Double) newObj;
							 return decObj.doubleValue();
					 }

					    // Something must have gone wrong.
           return RMT2Base.FAILURE;
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: calcHours
//    Prototype: int _empId - Employee Id
//                    int  _year - Year to be calculated
//                    int  _month - Month of the year to be calculated.
//       Returns: double - total hours caleculated.
//       Throws: ProjectException
//  Description: Computes time for an employee of a specified period.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public double calcHours(int _empId, int _year, int _month) throws ProjectException {
			 int      eventId;
			 String  eventIdStr;

        try {
							// Get Event Id using Employee, Year, and Month
						DataSourceApi dso = this.getDataSource("ProjEventView");
						dso.setDatasourceSql(DbSqlConst.WHERE_KEY, "proj_employee_id = " + _empId);
						dso.setDatasourceSql(DbSqlConst.WHERE_KEY, "year = " + _year);
						dso.setDatasourceSql(DbSqlConst.WHERE_KEY, "month = " + _month);
						dso.executeQuery();

						if (dso.nextRow()) {
								eventIdStr = dso.getColumnValue("Id");
								eventId = new Integer(eventIdStr).intValue();
						}
						else {
								this.msgArgs.clear();
								this.msgArgs.add(String.valueOf(_empId));
								this.msgArgs.add(String.valueOf(_year));
								this.msgArgs.add(String.valueOf(_month));
								throw new ProjectException(this.connector, 124, this.msgArgs);
						}
						dso.close();
						return this.calcHours(eventId);
				}
				catch (NumberFormatException e) {
						throw new ProjectException(this.connector, 124, null);
				}
				catch (DatabaseException e) {
						throw new ProjectException(e);
				}
				catch (NotFoundException e) {
						throw new ProjectException(e);
				}
				catch (SystemException e) {
						throw new ProjectException(e);
				}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: submit
//    Prototype: int _eventId - Event Id
//       Returns: int id - The Id of the status created
//       Throws: ProjectException
//  Description: Submits time for approval
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int submit(int _eventId) throws ProjectException {
			 String method = "submit";
  		 Object   newObj;
			 Integer  intObj;
			 int         id;

			 try {
							 //  Change current status to "Submittted"
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _eventId, false);

								 // Call stored procedure to submit project time
					 this.dynaApi.execute("exec usp_submit_project ?");

            //  Get new status  id.
           id = 0;
	         newObj = this.dynaApi.getOutParm("stat_id");
	         if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 id = intObj.intValue();
					 }
					 return id;
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }

	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: approve
//    Prototype: int _eventId - Event Id
//       Returns: int id - The Id of the status created
//       Throws: ProjectException
//  Description: Attempts to approve time for a project's current period.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int approve(int _eventId) throws ProjectException {
			 String method = "approve";
  		 Object   newObj;
			 Integer  intObj;
			 int         id;

			 try {
							 //  Change current status to "Approved"
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _eventId, false);

								 // Call stored procedure to Approve project Time
					 this.dynaApi.execute("exec usp_approve_project ?");

            //  Get new status  id.
           id = 0;
	         newObj = this.dynaApi.getOutParm("stat_id");
	         if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 id = intObj.intValue();
					 }
					 return id;
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: reject
//    Prototype: int _eventId - Event Id
//       Returns: int id - The Id of the status created
//       Throws: ProjectException
//  Description: Rejects project timesheet
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int reject(int _eventId) throws ProjectException {
			 String method = "reject";
  		 Object   newObj;
			 Integer  intObj;
			 int         id;

			 try {
							 //  Change current status to "Approved"
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _eventId, false);
					 this.dynaApi.addParm("stat_id", Types.INTEGER,  0, true);

								 // Call stored procedure to reject project Time
					 this.dynaApi.execute("exec usp_reject_project ?");

            //  Get new status  id.
           id = 0;
	         newObj = this.dynaApi.getOutParm("stat_id");
	         if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 id = intObj.intValue();
					 }
					 return id;
			 }
			 catch (DatabaseException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ProjectException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

}