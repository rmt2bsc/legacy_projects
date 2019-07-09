package com.apiimpl;

import java.sql.Types;

import java.util.List;

import com.api.GeneralCodeGroupManagerApi;
import com.api.GeneralCodeManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.GeneralCodesGroup;
import com.bean.GeneralCodes;
import com.bean.db.DatabaseConnectionBean;

import com.util.GeneralCodeException;
import com.util.SystemException;


/**
 * Api Implementation that manages general code groups and general codes.
 * 
 * @author RTerrell
 *
 */
public class GeneralCodeManagerApiImpl extends RdbmsDataSourceImpl implements GeneralCodeGroupManagerApi, GeneralCodeManagerApi  {

    protected String       criteria;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: GeneralCodeManagerApiImpl
//    Prototype: void
//      Returns: void
//       Throws: DatabaseException, SystemException
//  Description: Default Constructor.  Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GeneralCodeManagerApiImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException  {
			 super(dbConn);
			 this.setBaseView("GeneralCodesGroupView");
			 this.setBaseClass("com.bean.GeneralCodesGroup");
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGroupById
//    Prototype: int value
//      Returns: GeneralCodesGroup
//       Throws: GeneralCodeException
//  Description: Finds Genreal Code Group using primary key
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GeneralCodesGroup findGroupById(int value) throws GeneralCodeException  {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesGroupView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodesGroup");
			 this.criteria = "id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (GeneralCodesGroup) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGroupByDescription
//    Prototype: String _value
//      Returns: ArrayList
//       Throws: GeneralCodeException
//  Description: Finds General Code Group by Description
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findGroupByDescription(String _value) throws GeneralCodeException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesGroupView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodesGroup");
			 this.criteria = "description like '" + _value + "'%'";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGroup
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: GeneralCodeException
//  Description: Finds one or more general code groups by using custom criteria (criteria) supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findGroup(String _criteria) throws GeneralCodeException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesGroupView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodesGroup");
			 this.criteria = _criteria;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCodeByGroup
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: GeneralCodeException
//  Description: Finds all general codes by using general code group id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findCodeByGroup(int value) throws GeneralCodeException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodes");
			 this.criteria = "group_id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCodeById
//    Prototype: String _criteria
//      Returns: GeneralCodes
//       Throws: GeneralCodeException
//  Description: Finds general code by using primary key
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GeneralCodes findCodeById(int value) throws GeneralCodeException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodes");
			 this.criteria = "id  = " + value;
			 try {
				 list = this.find(this.criteria);
				 if (list == null || list.size() <= 0) {
						 return null;
				 }
				 return (GeneralCodes) list.get(0);
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCodeByDescription
//    Prototype: String _value
//      Returns: ArrayList
//       Throws: GeneralCodeException
//  Description: Finds General Code by Description
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findCodeByDescription(String _value) throws GeneralCodeException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodes");
			 this.criteria = "description like '" + _value + "'%'";
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCode
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: GeneralCodeException
//  Description: Finds one or more general codes  by using custom criteria (criteria) supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findCode(String _criteria) throws GeneralCodeException {
			 String prevBaseClass;
			 String prevBaseView;
			 List list;

			 prevBaseView = this.setBaseView("GeneralCodesView");
			 prevBaseClass = this.setBaseClass("com.bean.GeneralCodes");
			 this.criteria = _criteria;
			 try {
				 list = this.find(this.criteria);
				 return list;
			 }
			 catch (SystemException e) {
			 	 this.msgArgs.clear();
				 this.msgArgs.add(e.getMessage());
				 throw new GeneralCodeException(this.connector, 1000, this.msgArgs);
			 }
			 finally {
				 this.setBaseView(prevBaseView);
				 this.setBaseClass(prevBaseClass);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainGroup
//    Prototype: GeneralCodesGroup _obj
//      Returns: int  - Code Group Id
//       Throws: GeneralCodeException
//  Description: Creates or modifies Genreal Code Group profile
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainGroup(GeneralCodesGroup _obj) throws GeneralCodeException {
			 int      key;

			 if (_obj == null) {
					 throw new GeneralCodeException(this.connector, 1017, null);
			 }
			 if (_obj.getDescription() == null || _obj.getDescription().length() <= 0 ) {
					 throw new GeneralCodeException(this.connector, 1018, null);
			 }

			 if (_obj.getId() == 0 ) {
					 key = this.createCodeGroup(_obj);
			 }
			 else {
					 key = this.updateCodeGroup(_obj);
			 }

			 return key;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createCodeGroup
//    Prototype: GeneralCodesGroup
//      Returns: int  - Genreal Code Group Primary Key
//       Throws: GeneralCodeException
//  Description: Creates an General Code Group
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int createCodeGroup(GeneralCodesGroup _obj) throws GeneralCodeException {
			String method = "createCodeGroup";
			Object   newObj;
			Integer  intObj;

			 try {
					 //  Add genreal code group
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _obj.getId(), true);
					 this.dynaApi.addParm("description", Types.VARCHAR,  _obj.getDescription(), false);

					 // Call stored procedure to add General Code Group
					 this.dynaApi.execute("exec usp_add_gen_code_grp ??");

								 //  Get new general code group key
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _obj.setId(intObj.intValue());
					 }
					 return _obj.getId();
			 }
			 catch (DatabaseException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateCodeGroup
//    Prototype: SecurityBase _usr
//      Returns: int  - User Id
//       Throws: GeneralCodeException
//  Description: Updates general code group
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateCodeGroup(GeneralCodesGroup _obj) throws GeneralCodeException {
			String method = "updateCodeGroup";

			 try {
							 //  Modify general code group
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _obj.getId(), false);
					 this.dynaApi.addParm("description", Types.VARCHAR,  _obj.getDescription(), false);

								 // Call stored procedure to modify Genreal Code Group
					 this.dynaApi.execute("exec usp_upd_gen_code_grp ??");
					 return _obj.getId();
			 }
			 catch (DatabaseException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: deleteGroup
//    Prototype: GeneralCodesGroup _obj
//      Returns: void
//       Throws: GeneralCodeException
//  Description: Deletes a General Code Group
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public void deleteGroup(GeneralCodesGroup _obj) throws GeneralCodeException {
			String method = "deleteGroup";

			 try {
							 //  Delete general code group
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _obj.getId(), false);

								 // Call stored procedure to delete Genreal Code Group
					 this.dynaApi.execute("exec usp_del_gen_code_grp ?");
					 return;
			 }
			 catch (DatabaseException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainCode
//    Prototype: GeneralCodes _obj
//      Returns: int  - Code Id
//       Throws: GeneralCodeException
//  Description: Creates or modifies Genreal Code profile
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainCode(GeneralCodes _obj) throws GeneralCodeException {
			 int      key;

			 if (_obj == null) {
					 throw new GeneralCodeException(this.connector, 1019, null);
			 }
			 if (_obj.getGroupId() <= 0 ) {
					 throw new GeneralCodeException(this.connector, 1020, null);
			 }
			 if ((_obj.getShortdesc() == null || _obj.getShortdesc().length() <=0) &&
			     (_obj.getLongdesc() == null || _obj.getLongdesc().length() <= 0) ) {
					 throw new GeneralCodeException(this.connector, 1021, null);
			 }

			 if (_obj.getId() == 0 ) {
					 key = this.createCode(_obj);
			 }
			 else {
					 key = this.updateCode(_obj);
			 }

			 return key;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createCode
//    Prototype: GeneralCodes
//      Returns: int  - Genreal Code Primary Key
//       Throws: GeneralCodeException
//  Description: Creates an General Code
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int createCode(GeneralCodes _obj) throws GeneralCodeException {
			String method = "createCode";
			Object   newObj;
			Integer  intObj;

			 try {
					 //  Add genreal code group
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _obj.getId(), true);
					 this.dynaApi.addParm("group_id", Types.INTEGER,  _obj.getGroupId(), false);
					 this.dynaApi.addParm("shortdesc", Types.VARCHAR,  _obj.getShortdesc(), false);
					 this.dynaApi.addParm("longdesc", Types.VARCHAR,  _obj.getLongdesc(), false);
					 this.dynaApi.addParm("gen_ind_value", Types.VARCHAR,  _obj.getGenIndValue(), false);

					 // Call stored procedure to add General Code Group
					 this.dynaApi.execute("exec usp_add_gen_code ?????");

								 //  Get new general code group key
					 newObj = this.dynaApi.getOutParm("id");
					 if (newObj instanceof Integer) {
							 intObj = (Integer) newObj;
							 _obj.setId(intObj.intValue());
					 }
					 return _obj.getId();
			 }
			 catch (DatabaseException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateCode
//    Prototype: GeneralCodes _obj
//      Returns: int  - Genreal Code Id
//       Throws: GeneralCodeException
//  Description: Updates general code
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateCode(GeneralCodes _obj) throws GeneralCodeException {
			String method = "updateCode";

			 try {
							 //  Modify general code
					 this.dynaApi.addParm("id", Types.INTEGER,  _obj.getId(), false);
					 this.dynaApi.addParm("group_id", Types.INTEGER,  _obj.getGroupId(), false);
					 this.dynaApi.addParm("shortdesc", Types.VARCHAR,  _obj.getShortdesc(), false);
					 this.dynaApi.addParm("longdesc", Types.VARCHAR,  _obj.getLongdesc(), false);
					 this.dynaApi.addParm("gen_ind_value", Types.VARCHAR,  _obj.getGenIndValue(), false);

					 // Call stored procedure to add General Code Group
					 this.dynaApi.execute("exec usp_upd_gen_code ?????");
					 return _obj.getId();
			 }
			 catch (DatabaseException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: deleteCode
//    Prototype: GeneralCodes _obj
//      Returns: void
//       Throws: GeneralCodeException
//  Description: Deletes a General Code
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public void deleteCode(GeneralCodes _obj) throws GeneralCodeException {
			String method = "deleteCode";

			 try {
							 //  Delete general code group
					 this.dynaApi.clearParms();
					 this.dynaApi.addParm("id", Types.INTEGER,  _obj.getId(), false);

								 // Call stored procedure to delete Genreal Code Group
					 this.dynaApi.execute("exec usp_del_gen_code ?");
					 return;
			 }
			 catch (DatabaseException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new GeneralCodeException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }



}