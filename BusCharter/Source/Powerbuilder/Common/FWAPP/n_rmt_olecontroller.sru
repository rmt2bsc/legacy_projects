$PBExportHeader$n_rmt_olecontroller.sru
$PBExportComments$Base OLE Automation Controller (Client)
forward
global type n_rmt_olecontroller from oleobject
end type
end forward

global type n_rmt_olecontroller from oleobject
event type long ue_getdata ( str_retrieveparms astr_parms )
event ue_senddata ( )
event type long ue_connectserver ( )
event ue_disconnect ( )
event type long ue_connectdb ( )
end type
global n_rmt_olecontroller n_rmt_olecontroller

type variables
protected:
   constant long    il_DWCHANDLE = 9999999
   string     is_oleclass
   string     is_oletype
   string     is_dbname
   string     is_servername
   string     is_userid
   string     is_password
   boolean    ib_serverconnected
   boolean    ib_dbconnected
   string       is_sqldir
end variables

forward prototypes
public subroutine of_setoleclass (string as_class)
public subroutine of_setoletype (string as_type)
public function string of_getoleclass ()
public function string of_getoletype ()
public function string of_getserverappname ()
public subroutine of_setdbname (string as_dbname)
public subroutine of_setservername (string as_servername)
public subroutine of_setuserid (string as_userid)
public subroutine of_setpassword (string as_password)
public function string of_getdbname ()
public function string of_getservername ()
public function string of_getuserid ()
protected function string of_getpassword ()
public function long of_connect ()
public function long of_getdata (str_retrieveparms astr_parms)
end prototypes

event ue_getdata;//////////////////////////////////////////////////////////////////////////////
//
//	Event:   		ue_GetData
//
//	Access:  		public
//
//	Arguments:     str_retreiveparms
//
//	Returns:  		Long
//						
//	Description:  	Makes a request to retrieve data from the application server
//                for astr_parms.object, which is a datawindow, datastore, or
//                datawindowchild.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

long              ll_handle
long              ll_error
long              ll_rows
int               li_file
int               li_ndx
string            ls_data
string            ls_sql
u_rmt_dw          ldw
n_rmt_ds          lds
datawindowchild   ldwc


// validate the data object
if not isvalid(astr_parms.object) then
	return -1
end if

// Determine if astr_parms.object is a datawindow, datastore, or datawindowchild
choose case astr_parms.object.typeof()
	case Datawindow!
		ldw = astr_parms.object
		ll_handle = Handle(ldw)
	case Datastore!
		lds = astr_parms.object		
		// Try to generate a handle for the datastore
		randomize(0)
		ll_handle  = rand(999) + 9000
		lds.il_handle = ll_handle
	case Datawindowchild!
		ldwc = astr_parms.object	
		ll_handle = 999                  // static handle for dddw's
		astr_parms.updateable = 0        // insure that datawindowchild is not updateable!
	case else
		return -2
end choose

ll_error = this.GetData(ll_handle, ref ls_data, astr_parms.sql, astr_parms.criteria, astr_parms.updateable)

if ll_error < 0 then
	return ll_error  // return code of the database error.
end if
if isnull(ls_data) or len(ls_data) <= 0 then
	return -4
end if


// Import data into the datawindow, datastore, or datawindowchild.
// Reset the update flags for the datasource since importstring() assigns a 
// status of NewModified! to each row.
choose case astr_parms.object.typeof()
   case Datawindow!
	   ll_rows = ldw.importstring(ls_data)
		ldw.resetupdate()

   case Datastore!
	   ll_rows = lds.importstring(ls_data)
		lds.resetupdate()
		
   case Datawindowchild!
	   ll_rows = ldwc.importstring(ls_data)
		ldwc.resetupdate()
end choose

return ll_rows
end event

event ue_connectserver;//////////////////////////////////////////////////////////////////////////////
//
//	Event:   		ue_ConnectServer
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		Integer
//						0  success
//                -1  Invalid Call: the argument is the Object property of a control
//                -2  Class name not found
//                -3  Object could not be created
//                -4  Could not connect to object
//                -5  Invalid OLE Automation Server Name  (user-defined)
//                -9  Other error
//						
//	Description:  	Establishes connection to Application Server.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
string    ls_server
long      ll_result


ib_serverconnected = false

//  Validate Automation Server name
ls_server = this.of_getserverappname()
if isnull(ls_server) then
	return -5
end if

//  Connect to Automation Server
ll_result = this.ConnectToNewObject(ls_server)
if ll_result < 0 then
	return ll_result
end if

//  indicate that logon was successful!
ib_serverconnected = true

return 0


end event

event ue_connectdb;//////////////////////////////////////////////////////////////////////////////
//
//	Event:   		ue_Connectdb
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		Integer
//						0  success
//                < 0 DB error
//						
//	Description:  	Connects to Database.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
long      ll_result


ib_dbconnected = false

// Connect to database
ll_result = this.Connect(is_dbname, is_servername, is_userid, is_password)
if ll_result >= 0 then
	ib_dbconnected = true
end if

return ll_result

end event

public subroutine of_setoleclass (string as_class);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetOLEClass
//
//	Access:  		public
//
//	Arguments:     String
//
//	Returns:  		String
//						
//						
//	Description:  Sets the value of is_oleclasee
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

is_oleclass = as_class
end subroutine

public subroutine of_setoletype (string as_type);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetOLEType
//
//	Access:  		public
//
//	Arguments:     String
//
//	Returns:  		String
//						
//						
//	Description:  Sets the value of is_oletype
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


is_oletype = as_type
end subroutine

public function string of_getoleclass ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_OLEClassName
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  	Returns the Class part of the OLE Application Server name
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
return is_oleclass
end function

public function string of_getoletype ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetOLEType
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  Returns the Type part of the OLE Application Server name
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_oletype
end function

public function string of_getserverappname ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetServerAppName
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  	Returns the fully qualified application server name.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_oleclass + "." + is_oletype
end function

public subroutine of_setdbname (string as_dbname);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetDBName
//
//	Access:  		public
//
//	Arguments:     String
//
//	Returns:  		String
//						
//						
//	Description:  Set the value of the is_dbname
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

is_dbname = as_dbname
end subroutine

public subroutine of_setservername (string as_servername);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetServername
//
//	Access:  		public
//
//	Arguments:     String
//
//	Returns:  		String
//						
//						
//	Description:  Sets the value of the is_servername
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

is_servername = as_servername
end subroutine

public subroutine of_setuserid (string as_userid);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetUserid
//
//	Access:  		public
//
//	Arguments:     String
//
//	Returns:  		String
//						
//						
//	Description:  Set sthe value of the is_userid
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
is_userid = as_userid
end subroutine

public subroutine of_setpassword (string as_password);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetPassword
//
//	Access:  		public
//
//	Arguments:     String
//
//	Returns:  		String
//						
//						
//	Description:  Sets the value of the is_password
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

is_password = as_password
end subroutine

public function string of_getdbname ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetdbName
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  	Returns the database name
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_dbname
end function

public function string of_getservername ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetServerName
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  	Returns database server name.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
return  is_servername
end function

public function string of_getuserid ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetUserid
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  Returns the user id 
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_userid
end function

protected function string of_getpassword ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_Getpassword
//
//	Access:  		protected
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  	Returns the Database Password
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
return is_password
end function

public function long of_connect ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_Connect
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		Integer
//						0  success
//                < 0 - Application Server and/or Database error
//						
//	Description:  	Establishes connection to Application Server and Database.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

long    ll_result

ll_result = this.event ue_connectserver()
ll_result = this.event ue_connectdb()


return ll_result
end function

public function long of_getdata (str_retrieveparms astr_parms);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetData
//
//	Access:  		public
//
//	Arguments:     astr_parms (str_dwargs)
//
//	Returns:  		String
//						
//						
//	Description:  	Triggers the uw_getdata event.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return this.event ue_getdata(astr_parms)
end function

on n_rmt_olecontroller.create
call oleobject::create
TriggerEvent( this, "constructor" )
end on

on n_rmt_olecontroller.destroy
call oleobject::destroy
TriggerEvent( this, "destructor" )
end on

event error;string msg

msg = "An error has occurred.~r~n   The error number: " + String(errornumber) + "~r~n" + &
      "The error object: " + errorobject + "~r~n" + &
		"The Description is: " + errortext


messagebox('App Client User OLE Error MEssage', msg)

action = ExceptionIgnore! 
end event

