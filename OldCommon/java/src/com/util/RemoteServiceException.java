package com.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//           Class: RMT2Exception
//
//    Constructors: public RMT2Exception()
//                       public RMT2Exception(String msg)
//                       public RMT2Exception(int code)
//                       public RMT2Exception(String _msg, int _code)
//                       public RMT2Exception(String _msg, int _code, String _objName, String _methodName)
//                       public RMT2Exception(Exception e)
//
//             Lineage: Exception
//       Description: Class designed to house and handle exception data.
//
//  SCR          Date                          Developer                   Description
// =======   ==============  ===============   =====================================
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

import com.bean.RMT2ExceptionBean;

import com.bean.db.DatabaseConnectionBean;

import com.api.db.DbSqlConst;
import com.api.DataSourceApi;
import com.api.db.orm.DataSourceFactory;





public class RemoteServiceException extends Exception {

  protected   final int  FAILURE = -1;
  protected   final int  SUCCESS = 1;
  protected   final String DELIM = "%s";

  protected   String  objName;
  protected   String  methodName;
  protected   int        errorCode;
  protected   String  errorMsg;
  protected   String  className;
  protected   ArrayList msgArgs;

  private       DatabaseConnectionBean con;


/////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: void
//              Returns: void
//               Throws: none
//        Description: Default constructor.
/////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException() {
			this.className = this.getClass().getName();
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: String msg
//              Returns: void
//               Throws: none
//        Description: Constructor which initializes the error message
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException(String msg) {
			this();
      this.errorMsg = msg;
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: String msg
//              Returns: void
//               Throws: none
//        Description: Constructor which initializes the error code
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException(int code) {
    this();
    this.errorCode = code;
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: Object _con, int _code, ArrayList _args
//              Returns: void
//               Throws: none
//        Description: Constructor which obtains the error message from the Database using the error code.
//                          _con is expected to be of type DatabaseConnectionBean.   _args contains an array of String argument values
//                          that will replace "%s" placeholders in the message text retrieved from the database using _code.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException(Object _con, int _code, ArrayList _args) {
      this(_code);
 			String msg;

      this.msgArgs = _args;
			msg = this.getDBMessage(_con,  _code);
			if (msg != null) {
					this.setErrorMessage(msg);
			}
			else {
					this.setErrorMessage("An invalid RMT2DBConnection object was passed to RemoteServiceException(Object _con, int _code, String _objName, String _methodName)");
			}

      if (_args != null && _args.size() > 0) {
					this.fillMsgArgs();
			}
      this.setErrorCode(_code);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: String _msg, int _code
//              Returns: void
//               Throws: none
//        Description: Constructor which initializes the error message and error code
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException(String _msg, int _code) {
     this();
     this.setErrorMessage(_msg);
     this.setErrorCode(_code);
  }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: String _msg, int _code, String _objName, String _methodName
//              Returns: void
//               Throws: none
//        Description: Constructor which initializes the error message and error code
//                                object name and method name
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException(String _msg, int _code, String _objName, String _methodName) {
     this(_msg, _code);
     this.objName = (_objName == null ? "Unknown" : _objName);
     this.methodName = (_methodName == null ? "Unknown" : _methodName);
     this.setErrorMessage(_msg);
     this.setErrorCode(_code);
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: RemoteServiceException
//            Prototype: Exception e
//              Returns: void
//               Throws: none
//        Description: Constructor which initializes the error message with the input of
//                                the message value from the argument of type Exception.  If the
//                                value of the message is null set the error message to be class
//                               name of the Exception "e".
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RemoteServiceException(Exception e) {
      this.errorMsg = e.getMessage();
      if (this.errorMsg == null) {
		this.errorMsg = e.getClass().getName();
	  }
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: getDBMessage
//            Prototype: Object _con,
//                            int _code
//              Returns: String  (Error Message)
//               Throws: none
//        Description: Retrieves the error message associated with the error code, _code, from the database and returns the
//                          message to the caller.     If the message is not found, then "Unknown Error" is returned to the caller.   If an
//                          an exception occurs notify caller that message is not obtainable due to system exception.   _con must be a
//                          valid DatabaseConnectionBean.  Otherwise, return null to the caller signaling that the connectgion object is
//                          invalid.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private String getDBMessage(Object _con, int _code) {

       String msg = null;

           //  Validate _con
        if (_con instanceof DatabaseConnectionBean) {
  				this.con = (DatabaseConnectionBean) _con;
  		 }
  		 else {
  				return null;
  		 }

       try {
					 DataSourceApi api = DataSourceFactory.create(this.con, "SystemMessageDetailView");
					 api.setDatasourceSql(DbSqlConst.WHERE_KEY, null);
					 api.setDatasourceSql(DbSqlConst.WHERE_KEY, "id = " + _code);
					 api.executeQuery();

					 while(api.nextRow()) {
							 msg = api.getColumnValue("Description");
					 }

					 return (msg == null ? "Unknown Error" : msg);
			 }
			 catch (RMT2Exception e) {
					 return e.getMessage();
			 }
	 }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: getFormatedMsg
//            Prototype: void
//              Returns: void
//               Throws: none
//        Description: Constructs the error message by concatenating the member variables: className, objName, methodName, and
//                         errorCode provided errorCode is less than zero.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public String getFormatedMsg() {
        StringBuffer msg = new StringBuffer(500);
        msg.append("Exception Type: ");
        msg.append(this.getClassName());
        msg.append("  Class: ");
        msg.append(this.objName);
        msg.append("  Method: ");
        msg.append(this.methodName);

        if (this.errorCode < 0) {
            msg.append("  Error Code: ");
            msg.append(this.errorCode);
        }

        msg.append("  Message: ");
        msg.append(this.errorMsg);

    return msg.toString();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getHtmlMessage
//            Prototype: none
//              Returns: String
//               Throws: none
//        Description: Formats the error message as HTML.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public String getHtmlMessage() {

    StringBuffer msg = new StringBuffer(500);

    if (this.objName == null && this.methodName == null && this.errorCode == 0) {
      return this.errorMsg;
    }

    if (this.objName != null && this.methodName != null && this.errorCode != 0) {
      msg.append("RMT2 Error<br><br>");
      msg.append("Exception Type: ");
      msg.append(this.className);
      msg.append("<br>");
      msg.append("Object Name: ");
      msg.append(this.objName);
      msg.append("<br>");
      msg.append("Method Name: ");
      msg.append(this.methodName);
      msg.append("<br>");
      msg.append("Error Message: ");
      msg.append(this.errorMsg);
      msg.append("<br>");
      msg.append("Error Code: ");
      msg.append(this.errorCode);
      return msg.toString();
    }

    return this.errorMsg;
  }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//               Method: getExceptionBean
//            Prototype: void
//              Returns: RMT2ExceptionBean.  If error occurs returns null
//               Throws: none
//        Description: Packages the RMT2ExceptionBean with error message, error code and class Name.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RMT2ExceptionBean getExceptionBean()  {

     String  exName = this.getClassName();
     try {
	   RMT2ExceptionBean e = new RMT2ExceptionBean();
	   e.setErrorMsg(this.errorMsg);
	   e.setErrorCode(this.errorCode);
	   e.setClassName(exName);
	   if (exName.equalsIgnoreCase("SystemException")) {
		 e.setResolution( "Consult System Administrator or Developer");
	   }
	   if (exName.equalsIgnoreCase("DatabaseException")) {
		 e.setResolution( "Go back and adjust your actions based on the error details shown in the \"What Happened\" section.   If error persist consult System Administrator or Developer");
	   }
	   if (exName.equalsIgnoreCase("NotFoundException")) {
		 e.setResolution( "Go back and refine search criteria");
	   }
	   if (exName.equalsIgnoreCase("BusinessException")) {
		 e.setResolution("Go back and correct the error.");
	   }
	   if (exName.equalsIgnoreCase("LoginException")) {
		 e.setResolution("Go back and enter the correct user id and/or password.");
	   }
	   return e;
	 }
	 catch (SystemException e ) {
	   return null;
	 }
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: getExceptionBean
//            Prototype: int _errorId
//              Returns: RMT2ExceptionBean.  If error occurs returns null
//               Throws: none
//        Description: Obtains the exception data from the database based on "_errorId.  Packages the RMT2ExceptionBean with
//                           exception data.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RMT2ExceptionBean getExceptionBean(int _errorId)  {

      try {
				// TODO:  Create RMT2ExceptionBeanView.xml document.
				//               Retrieve exception data from the database via xml document
				//               Populate exception bean with data retrieved from the database
				//               Return exception bean.
		RMT2ExceptionBean e = new RMT2ExceptionBean();
		return e;
	  }
	  catch (SystemException e) {
		return null;
	  }

  }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: getExceptionBean
//            Prototype: void
//              Returns: RMT2ExceptionBean.  If error occurs returns null
//               Throws: none
//        Description: Packages the RMT2ExceptionBean with error message, error code
//                               and class Name.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public RMT2ExceptionBean getExceptionBean(String _className )  {
	   this.className = _className;
	   return this.getExceptionBean();
  }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: getClassName
//            Prototype: void
//              Returns: String
//               Throws: none
//        Description: Extracts the actual class name from the fully qualified class name
//                               value, this.className.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public String getClassName() {
	int       ndx = 0;
	String value;

	try {
	  ndx = this.className.lastIndexOf(".");
	  if ( ndx == this.FAILURE) {
			if (this.className.length() > 0) {
				return this.className;
			}
			return "Class Unknown";
	  }
	  value = this.className.substring(++ndx);
	  return value;
	}
	catch (NullPointerException e) {
	  return "Class is Null or Invalid";
	}
	catch (IndexOutOfBoundsException e) {
	  return "Class Not Parsed";
	}
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Constructor: fillMsgArgs
//            Prototype: void
//              Returns: void
//               Throws: none
//        Description: Fills in the place holders in this.errorMsg with values contained in the Message Argument ArrayList variable, msgArgs.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  protected void fillMsgArgs() {
			String  arg = null;
			String  temp;
			int  count = this.msgArgs.size();

			try {
					temp = this.errorMsg;
					for (int ndx = 0; ndx < count; ndx++) {
							arg = (String) this.msgArgs.get(ndx);
			        temp = RMT2Utility.replace(temp, arg, this.DELIM);
					}
					this.errorMsg = temp;
					this.msgArgs.clear();
			}
			catch (IndexOutOfBoundsException e) {
					this.errorMsg = "Failure reading Message Argument structure - exceeded array bounds";
			}
	}


  public String getMessage() {
        return this.errorMsg;
  }
  public int getErrorCode() {
			return this.errorCode;
  }
  public void setErrorMessage(String value) {
     this.errorMsg = value;
  }
  public void setErrorCode(int value) {
    this.errorCode = value;
  }
  public String getFullClassName() {
    return this.className;
  }
  public String setObjName() {
			return this.objName;
	}
  public String setMethodName() {
			return this.methodName;
	}
	public void setMsgArgs(ArrayList value) {
			this.msgArgs = value;
	}
}

