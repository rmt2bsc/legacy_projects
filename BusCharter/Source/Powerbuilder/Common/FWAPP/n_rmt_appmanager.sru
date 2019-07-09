$PBExportHeader$n_rmt_appmanager.sru
$PBExportComments$Application Manager
forward
global type n_rmt_appmanager from n_rmt_baseobject
end type
end forward

global type n_rmt_appmanager from n_rmt_baseobject
event ue_open ( string as_commandline )
event ue_close ( )
event ue_systemerror ( )
event ue_idle ( )
event ue_preabout ( ref n_rmt_aboutattrib anv_aboutattrib )
event type connectprivilege ue_connectionbegin ( string as_userid,  string as_password,  string as_connectstring )
event ue_connectionend ( )
event ue_presplash ( ref n_rmt_splashattrib anv_splashattrib )
event ue_exit ( )
event ue_prelogon ( ref n_rmt_logonattrib anv_logonattrib )
event type integer ue_logon ( ref n_rmt_logonattrib an_logonattrib )
event ue_connect ( )
end type
global n_rmt_appmanager n_rmt_appmanager

type variables
Public:
   application	      iapp_object
   environment	      ienv_object
   n_rmt_messaging        inv_msg
   n_rmt_datacache        inv_cache
   n_rmt_olecontroller      inv_client
   //n_cst_security	inv_security

  // Contants
   int   ii_ADMIN  = 218
	int   ii_POWER  = 217    
	int   ii_NORMAL = 216

Protected:
boolean	ib_microhelp = true
string	is_appinifile
string	is_userinifile
string	is_appkey
string	is_userkey
string	is_helpfile
string	is_logo
string	is_userid
int            ii_access
string	is_copyright
string        is_sqldir
string	is_version
string        is_build
string        is_devdate
string        is_devtime
string        is_developer
string        is_dev_company
string        is_dev_email
string        is_dev_company_website
string        is_licensed_to
string        is_dbPath
string        is_dbName
string        is_backupPath
long          il_nextbackupcount

w_rmt_frame	iw_frame


end variables
forward prototypes
public function string of_getuserid ()
public function boolean of_isregistryavailable ()
public function string of_getcopyright ()
public function string of_getversion ()
public function string of_getappinifile ()
public function string of_gethelpfile ()
public function string of_getuserinifile ()
public function string of_getlogo ()
public function integer of_setmicrohelp (boolean ab_microhelp)
public function boolean of_getmicrohelp ()
public function integer of_about ()
public function integer of_splash (integer ai_secondsvisible)
public function integer of_setcopyright (string as_copyright)
public function integer of_setappinifile (string as_appinifile)
public function integer of_setversion (string as_version)
public function integer of_setuserinifile (string as_userinifile)
public function integer of_setuserid (string as_userid)
public function integer of_setlogo (string as_logo)
public function integer of_sethelpfile (string as_helpfile)
public function integer of_setappkey (string as_appkey)
public function integer of_setuserkey (string as_userkey)
public function string of_getappkey ()
public function string of_getuserkey ()
public function integer of_setsecurity (boolean ab_switch)
public function integer of_setapppreference (boolean ab_switch)
public function integer of_setmessaging (boolean ab_switch)
public function integer of_setdatacache (boolean ab_switch)
public function integer of_setframe (w_rmt_frame aw_frame)
public function w_rmt_frame of_getframe ()
public function integer of_setolecontroller (boolean ab_switch)
public function string of_getsqldir ()
public subroutine of_setsqldir (string as_sqldir)
public function integer of_logondlg ()
public function integer of_getaccess ()
public subroutine of_setaccess (integer ai_access)
public subroutine of_setbuild (string as_build)
public subroutine of_setdevdate (string as_devdate)
public subroutine of_setdevtime (string as_devtime)
public subroutine of_setdeveloper (string as_name)
public subroutine of_setdevcompany (string as_devcompany)
public subroutine of_setdevemail (string as_dev_email)
public subroutine of_setdevwebsite (string as_website)
public function string of_getbuild ()
public function string of_getdevdate ()
public function string of_getdevtime ()
public function string of_getdeveloper ()
public function string of_getdevcompany ()
public function string of_getdevemail ()
public function string of_getcompanywebsite ()
public subroutine of_setlicensedto (string as_company)
public function string of_getlicensedto ()
public function string of_getdbname ()
public function string of_getdbpath ()
public function String of_getbackuppath ()
public subroutine of_setdbpath (string as_value)
public subroutine of_setbackuppath (string as_value)
public subroutine of_setnextbackupcount (long al_value)
public function long of_getnextbackupcount ()
public subroutine of_setdbname (string as_value)
public subroutine of_dbbackup ()
public subroutine of_dbrecover ()
end prototypes

event ue_open;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_open
//
//	Arguments:
//	as_commandline:  command line parameter to the application
//
//	Returns:  None
//
//	Description:  Perform open processing
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//	6.0   Add Application Preference and Most Recently Used Service hooks
//////////////////////////////////////////////////////////////////////////////


integer		li_rc

iapp_object.dwMessageTitle = iapp_object.DisplayName
this.of_setMessaging(true)
open(w_frame)



//  OPen login.  If successfull login, then open frame
end event

event ue_close;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_close
//
//	Arguments:  None
//
//	Returns:  None
//
//	Description:  Perform close processing
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//	6.0   Add Application Preference and MRU Service hooks
//
//////////////////////////////////////////////////////////////////////////////

end event

event ue_systemerror;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_systemerror
//
//	Arguments:	None
//
//	Returns:  None
//
//
//	Description:  Triggered whenever the a system level error occurs.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

string 	ls_message
string	ls_msgparm[1]

ls_message = 'Error Number '+string(error.number) & 
	+'.~r~nError text = '+Error.text &
	+'.~r~nWindow/Menu/Object = '+error.windowmenu &
	+'.~r~nError Object/Control = '+Error.object &
	+'.~r~nScript = '+Error.objectevent &
	+'.~r~nLine in Script = '+string(Error.line) + '.'

if isvalid(inv_msg) then
	ls_msgparm[1] = ls_message
	inv_msg.of_message(inv_msg.SYSTEMERROR, ls_msgparm)
else
	Messagebox(this.iapp_object.displayname + ' - System Error',ls_message, StopSign!, Ok!)
end if

this.event ue_exit()



end event

event ue_preabout;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preabout
//
//	Arguments:		
//	anv_aboutattrib:  About object by reference
//
//	Returns:  None
//
//	Description:	Populate about object before the about window opens.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


anv_aboutattrib.is_application = iapp_object.displayname
anv_aboutattrib.is_logo = is_logo
anv_aboutattrib.is_version = is_version
anv_aboutattrib.is_copyright = is_copyright
end event

event ue_presplash;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_presplash
//
//	Arguments:		
//	anv_splashattrib  splash object by reference
//
//	Returns:  None
//
//	Description:  Populate splash object before the splash window opens.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


anv_splashattrib.is_application = iapp_object.displayname
anv_splashattrib.is_logo = gnv_app.of_getLogo()  //is_logo
anv_splashattrib.is_version = gnv_app.of_getVersion()   //is_version
anv_splashattrib.is_copyright = is_copyright

end event

event ue_exit;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_exit
//
//	Arguments:  None
//
//	Returns:  None
//
//	Description:
//	Exit from the application.
//	If the application contains a Frame, an explicit close will be issued to
//	the active MDI frame window.
//
//	NOTE:  This event is a stubb event and may need to be overriden.
// Examples of why to override:
//	1) The application is SDI and you want to issue an explicit close to the main window
//	2) The application contains multiple frames and the desired behavior is to
// 	close all of them.
// 3) ...
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//////////////////////////////////////////////////////////////////////////////


w_rmt_frame	lw_activeframe

lw_activeframe = this.of_GetFrame()
if IsValid (lw_activeframe) then
	close (lw_activeframe)
else
	halt close
end if
end event

event ue_prelogon;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_prelogondlg
//
//	Arguments:		
//	anv_logonattrib:  Logon object by reference
//
//	Returns:  None
//
//	Description:	Populate logon object before the logon window opens.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


//string	ls_userid
//n_cst_platform	lnv_platform
//
////////////////////////////////////////////////////////////////////////////////
//// Determine default User ID
////////////////////////////////////////////////////////////////////////////////
//if of_IsRegistryAvailable() then
//	if Len (is_userkey) > 0 then
//		RegistryGet (is_userkey + "\logon", "userid", ls_userid)		
//	end if
//else
//	if Len (is_userinifile) > 0 then
//		ls_userid = ProfileString (is_userinifile, "logon", "userid", "")
//	end if
//end if
//if Len (ls_userid) = 0 then
//	f_setplatform (lnv_platform, true)
//	ls_userid = lnv_platform.of_GetUserID()
//	f_setplatform (lnv_platform, false)
//end if
//anv_logonattrib.is_userid = ls_userid
//
////////////////////////////////////////////////////////////////////////////////
//// This object will by default be used to perform logon
////////////////////////////////////////////////////////////////////////////////
anv_logonattrib.ipo_source = this
//
////////////////////////////////////////////////////////////////////////////////
//// Application name and logo
////////////////////////////////////////////////////////////////////////////////
anv_logonattrib.is_appname = iapp_object.DisplayName
anv_logonattrib.is_logo = is_logo
end event

event ue_logon;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_logon
//
//	Arguments:
//	as_userid   User ID attempting to logon
//	as_password   Password of user attempting to logon
//
//	Returns:  integer
//	 1 = successful logon
//  0 = user not found
//  >1 = more than one user have like identities
//	-1 = failure
//
//	Description:  Specific logon functionality for the application.
//	Perform logon processing based on User ID and password given.
//
//	Note:  this event will be responsible for displaying any error messages
//	if the logon fails for any reason.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

datastore  ds
int        li_result
long       ll_dbhandle

ds = create datastore
ds.dataobject = "d_valid_user"

  // if cal to settransobject fails try to resotre the database connection...if disconnected
ll_dbhandle = sqlca.DBHandle()
if ll_dbhandle <= 0 or isNull(ll_dbhandle) then
	this.event ue_connect()
end if

ds.settransobject(sqlca)
li_result = ds.retrieve(an_logonattrib.is_userid, an_logonattrib.is_password)
if li_result = 1 then
	an_logonattrib.ii_access = ds.getitemnumber(1, "access_level")
end if

return li_result
end event

event ue_connect;
return


end event

public function string of_getuserid ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetUserid
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string   the current user's id.
//
//	Description:  Returns the user's id.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_userid

end function

public function boolean of_isregistryavailable ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_IsRegistryAvailable
//
//	Access:  	public
//
//	Arguments:	 none
//
//	Returns:  boolean
//
//	Description:  
//	Returns a boolean stating whether the registry is available.
//
//	Note: 
// This is a stub function.  Overload to get desired functionality.
// For example, if you only want INI functionality simply overload this
// function to always return False.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
// 5.0.04 Corrected so that (OSType=Windows! and OSMajorRevision=3 and
//			OSMinorRevision >= 95) does not return TRUE. A 16bit application 
//			cannot access the registry
//
//////////////////////////////////////////////////////////////////////////////


If ienv_object.Win16 Then Return False

If (ienv_object.OSType=Windows! and ienv_object.OSMajorRevision >= 4) or &
	ienv_object.OSType=WindowsNT! Then
	Return True
End If

Return False

end function

public function string of_getcopyright ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetCopyright
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string   the copyright statement.
//
//	Description:  Returns the application copyright statement.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_copyright

end function

public function string of_getversion ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetVersion
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  String   the current application version.
//
//	Description:  Returns the application version.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_version

end function

public function string of_getappinifile ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetAppIniFile
//
//	Access:  public
//
//	Arguments:	 none
//
//	Returns:  string   the name of the application INI file.
//
//	Description:  Returns the name of the application INI file.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_appinifile
end function

public function string of_gethelpfile ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetHelpFile
//
//	Access:  public
//
//	Arguments:  None
//
//	Returns:  String   the name of the application help file
//
//	Description:  Returns the name of the application's help file.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_helpfile

end function

public function string of_getuserinifile ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetUserIniFile
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string   the name of the user INI file.
//
//	Description:  Returns the name of the user-specific INI file.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_userinifile

end function

public function string of_getlogo ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetLogo
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string   the bitmap filename of the logo.
//
//	Description:  returns the bitmap file name of the application logo
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_logo

end function

public function integer of_setmicrohelp (boolean ab_microhelp);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetMicrohelp
//
//	Access:  public
//
//	Arguments:		
//	ab_microhelp   enable/disable microhelp
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Enables/disables microhelp for the application
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// Check arguments
if IsNull (ab_microhelp) then
	return -1
end if

ib_microhelp = ab_microhelp
return 1
end function

public function boolean of_getmicrohelp ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetMicrohelp
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  Boolean
//	True - Application has microhelp capabilities
//	False - Application does not use microhelp
//
//	Description:  Returns the current application's microhelp behavior.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return ib_microhelp
end function

public function integer of_about ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_About
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:  Display the about window.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

n_rmt_aboutattrib lnv_aboutattrib

// Populate information passed to the About window.
this.Event ue_preabout (lnv_aboutattrib)
Return 1 // OpenWithParm (w_about, lnv_aboutattrib)

end function

public function integer of_splash (integer ai_secondsvisible);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_Splash
//
//	Access:  public
//
//	Arguments:		
//	ai_secondsvisible   the length of time to display the splash window.
//
//	Returns:integer
//	 1 = success
//	-1 = error
//
//	Description:  Display the splash window for a specified length of time.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

n_rmt_splashattrib lnv_splashattrib

// Check arguments
If IsNull(ai_secondsvisible) Then
	ai_secondsvisible = 0
End If

// Number of seconds for splash window to be visible
lnv_splashattrib.ii_secondsvisible = ai_secondsvisible

// Populate information passed to the Splash window.
//this.Event ue_presplash (lnv_splashattrib)
lnv_splashattrib.is_application = iapp_object.displayname
lnv_splashattrib.is_logo = gnv_app.of_getLogo()  //is_logo
lnv_splashattrib.is_version = gnv_app.of_getVersion()   //is_version
//lnv_splashattrib.is_copyright = is_copyright


Return OpenWithParm (w_splash, lnv_splashattrib, iw_frame)

end function

public function integer of_setcopyright (string as_copyright);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetCopyright
//
//	Access:  public
//
//	Arguments:  as_copyright   the copyright statement.
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:  Sets the application copyright message
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


If IsNull(as_copyright) Then
	Return -1
End If

is_copyright = as_copyright
Return 1
end function

public function integer of_setappinifile (string as_appinifile);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetAppIniFile
//
//	Access:  public
//
//	Arguments:
//	as_appinifile:  the full pathname of the application INI file
//
//	Returns:  Integer
//	 1 = success
//	-1 = error
//
//	Description:  Sets the filename of the application's INI file
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//////////////////////////////////////////////////////////////////////////////
string  ls_msg

//Check arguments
if IsNull(as_appinifile) then
	ls_msg = "Reference to Application's initialization file is nil!~r~nContact technical support"
   if isValid(this.inv_msg) then
		this.inv_msg.of_message(ls_msg, stopsign!)
   else		
      messagebox("Application Error", ls_msg, stopsign!)
	end if	
	return -1
end if

is_appinifile = as_appinifile
if not fileExists(as_appinifile) then
	ls_msg = "Application's initialization file is locked by another application or the file is not found!~r~n"
	ls_msg += "Ensure that the file, " + as_appinifile + ", is located in the applications's directory."
   if isValid(this.inv_msg) then
		this.inv_msg.of_message(ls_msg, stopsign!)
   else		
      messagebox("Application Error", ls_msg, stopsign!)
	end if	
   return -2
end if

return 1
end function

public function integer of_setversion (string as_version);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetVersion
//
//	Access:  public
//
//	Arguments:		
//	as_version   Application version.
//
//	Returns:  Integer
//	 1 = succeess
//	-1 = error
//
//	Description:  Sets the version of this application
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


If IsNull(as_version) Then
	Return -1
End If

is_version = as_version
Return 1
end function

public function integer of_setuserinifile (string as_userinifile);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUserIniFile
//
//	Access:  public
//
//	Arguments:		
//	as_userinifile   Full pathname of the user's INI file.
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:  Sets the filename for the user INI file
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


If IsNull(as_userinifile) Then
	Return -1
End If

is_userinifile = as_userinifile
Return 1
end function

public function integer of_setuserid (string as_userid);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUserid
//
//	Access:  public
//
//	Arguments:  as_userid   the current user's id
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:  Sets the current user's id
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


If IsNull(as_userid) Then
	Return -1
End If

is_userid = as_userid
Return 1

end function

public function integer of_setlogo (string as_logo);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetLogo
//
//	Access:  		public
//
//	Arguments:		
//	as_logo			Name of the logo.
//
//	Returns:  		Integer
//						1 if it succeeds and -1 if an error occurs. 
//
//	Description:  	Sets the value for the Logo attribute.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


If IsNull(as_logo) Then	
	Return -1
End If

is_logo = as_logo
Return 1
end function

public function integer of_sethelpfile (string as_helpfile);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetHelpfile
//
//	Access:  public
//
//	Arguments:
//	as_helpfile:  Full pathname of the application's online help file.
//
//	Returns:  Integer
//	 1 = success
//	-1 = error
//
//	Description:  Sets the value of the online help file of the application
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

If IsNull(as_helpfile) Then
	Return -1
End If

is_helpfile = as_helpfile
Return 1
end function

public function integer of_setappkey (string as_appkey);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetAppKey
//
//	Access:  public
//
//	Arguments:		
//	as_appkey   Full key value for the application.
//
//	Returns:  Integer
//	 1 = success 
//	-1 = error 
//
//	Description:  Sets the value for the application key on the registry.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

If IsNull(as_appkey) Then
	Return -1
End If

is_appkey = as_appkey
Return 1
end function

public function integer of_setuserkey (string as_userkey);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUserKey
//
//	Access:  public
//
//	Arguments:		
//	as_userkey:  full registry key for the user.
//
//	Returns:  Integer
//	 1 = success
//	-1 = error 
//
//	Description:  Sets the value of the registy key for the user
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

If IsNull(as_userkey) Then
	Return -1
End If

is_userkey = as_userkey
Return 1
end function

public function string of_getappkey ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetAppKey
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string   the name of the application key.
//
//	Description:  Returns the name of the application's key in the registry.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_appkey
end function

public function string of_getuserkey ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetUserKey
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string
//
//	Description:  Returns the name of the user's key in the registry
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return is_userkey
end function

public function integer of_setsecurity (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetSecurity
//
//	Access:  		public
//
//	Arguments:		
//	ab_Switch		True - start (create) the service,
//						False - stop (destroy) the service
//
//Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  	Starts or stops the Security service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//Check arguments
If IsNull(ab_switch) Then
	Return -1
End if

IF ab_Switch THEN
//	IF IsNull(inv_security) Or Not IsValid (inv_security) THEN
//		inv_security = CREATE n_cst_security
//		Return 1
//	END IF
ELSE
//	IF IsValid (inv_security) THEN
//		DESTROY inv_security
//		Return 1
//	END IF	
END IF

Return 0

end function

public function integer of_setapppreference (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetAppPreference
//
//	Access:  		public
//
//	Arguments:
//	ab_switch		True - start (create) the service,
//						False - stop (destroy) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//						
//	Description:  	Starts or stops the Application Preference Service.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
//If IsNull(ab_switch) Then
//	Return -1
//End If
//
//IF ab_Switch THEN
//	IF IsNull(inv_apppref) Or Not IsValid (inv_apppref) THEN
//		inv_apppref = CREATE n_cst_apppreference
//		inv_apppref.of_SetRequestor ( this )
//
//		// set up some defaults
//		inv_apppref.of_setappkey( this.of_getappkey() )
//		inv_apppref.of_setappinifile( this.of_getappinifile() )
//		inv_apppref.of_setuserkey( this.of_getuserkey() )
//		inv_apppref.of_setuserinifile( this.of_getuserinifile() )
//
//		Return 1
//	END IF
//ELSE
//	IF IsValid (inv_apppref) THEN
//		DESTROY inv_apppref
//		Return 1
//	END IF	
//END IF

Return 0
end function

public function integer of_setmessaging (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetMessaging
//
//	Access:  		public
//
//	Arguments:
//	ab_switch		True - start (create) the service,
//						False - stop (destroy) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//						
//	Description:  	Starts or stops the Error Handling Service.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
if IsNull(ab_switch) then
	return -1
end if

if ab_Switch THEN
	if IsNull(inv_msg) or not IsValid (inv_msg) then
		inv_msg = create n_rmt_messaging
		return 1
	end if
else
	if IsValid (inv_msg) then
		destroy inv_msg
		return 1
	end if
end if

Return 0
end function

public function integer of_setdatacache (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetDataCache
//
//	Access:  		public
//
//	Arguments:
//	ab_switch		True - start (create) the service,
//						False - stop (destroy) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//						
//	Description:  	Starts or stops the Error Handling Service.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0	Enhanced to default User name.
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
If IsNull(ab_switch) Then
	Return -1
End If

IF ab_Switch THEN
	IF IsNull(inv_cache) Or Not IsValid (inv_cache) THEN
		inv_cache = CREATE n_rmt_datacache
//		inv_cache.of_SetUser( this.of_GetUserID() )
		Return 1
	END IF
ELSE
	IF IsValid (inv_cache) THEN
		DESTROY inv_cache
		Return 1
	END IF	
END IF

Return 0
end function

public function integer of_setframe (w_rmt_frame aw_frame);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetFrame
//
//	Access:  public
//
//	Arguments:  aw_frame   the MDI Frame Window
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						-1 - An error was encountered.
//
//	Description:  Sets the currently active MDI frame.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


/* No Check for valid arguments is performed. */

// This variable might not contain a valid reference.
iw_frame = aw_frame
Return 1
end function

public function w_rmt_frame of_getframe ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetFrame
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  w_frame
//
//	Description:  Returns the currently active frame window
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return iw_frame

end function

public function integer of_setolecontroller (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetOleController
//
//	Access:  		public
//
//	Arguments:
//	ab_switch		True - start (create) the service,
//						False - stop (destroy) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//						
//	Description:  	Starts or stops the OLE Automation Client Service.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0	Enhanced to default User name.
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
If IsNull(ab_switch) Then
	Return -1
End If

IF ab_Switch THEN
	IF IsNull(inv_client) Or Not IsValid (inv_client) THEN
		inv_client = CREATE n_rmt_olecontroller
		Return 1
	END IF
ELSE
	IF IsValid (inv_client) THEN
		DESTROY inv_client
		Return 1
	END IF	
END IF

Return 0
end function

public function string of_getsqldir ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetSQLDir
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  string   the sql director path.
//
//	Description:  Returns the application sql directory path.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_sqldir
end function

public subroutine of_setsqldir (string as_sqldir);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetSQLDir
//
//	Access:  public
//
//	Arguments:  string   the sql director path.
//
//	Returns:  none
//
//	Description:  Sets the application sql directory path.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


is_sqldir = as_sqldir
end subroutine

public function integer of_logondlg ();////////////////////////////////////////////////////////////////////////////////
////
////	Function:  of_LogonDlg
////
////	Access:  public
////
////	Arguments:  none
////
////	Returns:  integer
////	 1 = successful logon
////	 0 = User cancelled from the logon dialog
////	-1 = an error occurred opening the logon window
////
////	Description:  Obtain a User ID and password from the user
////
////////////////////////////////////////////////////////////////////////////////
////
////	Revision History
////
////	Version
////	5.0   Initial version
////
////////////////////////////////////////////////////////////////////////////////
//
n_rmt_logonattrib	lnv_logonattrib
//
////////////////////////////////////////////////////////////////////////////////
//// Load logon object values
////////////////////////////////////////////////////////////////////////////////
this.event ue_prelogon(lnv_logonattrib)

////////////////////////////////////////////////////////////////////////////////
//// Open logon window
////////////////////////////////////////////////////////////////////////////////
if OpenWithParm (w_logon, lnv_logonattrib) < 0 then
	return -1
end if

////////////////////////////////////////////////////////////////////////////////
//// Get return logon object
////////////////////////////////////////////////////////////////////////////////
lnv_logonattrib = message.powerobjectparm

////////////////////////////////////////////////////////////////////////////////
//// Store user id
////////////////////////////////////////////////////////////////////////////////
//if Len (lnv_logonattrib.is_userid) > 0 then
//	if of_IsRegistryAvailable() then
//		RegistrySet (is_userkey + "\logon", "userid", lnv_logonattrib.is_userid)
//	else
//		SetProfileString (is_userinifile, "logon", "userid", lnv_logonattrib.is_userid)
//	end if
//
of_SetUserID(lnv_logonattrib.is_userid)
of_SetAccess(lnv_logonattrib.ii_access)
//end if

return lnv_logonattrib.ii_rc

//
end function

public function integer of_getaccess ();return this.ii_access
end function

public subroutine of_setaccess (integer ai_access);

this.ii_access = ai_access
end subroutine

public subroutine of_setbuild (string as_build);

this.is_build = as_build
end subroutine

public subroutine of_setdevdate (string as_devdate);this.is_devdate = as_devdate
end subroutine

public subroutine of_setdevtime (string as_devtime);this.is_devtime = as_devtime
end subroutine

public subroutine of_setdeveloper (string as_name);this.is_developer = as_name
end subroutine

public subroutine of_setdevcompany (string as_devcompany);this.is_dev_company = as_devcompany
end subroutine

public subroutine of_setdevemail (string as_dev_email);this.is_dev_email = as_dev_email
end subroutine

public subroutine of_setdevwebsite (string as_website);this.is_dev_company_website = as_website
end subroutine

public function string of_getbuild ();return this.is_build
end function

public function string of_getdevdate ();return this.is_devdate
end function

public function string of_getdevtime ();return this.is_devtime
end function

public function string of_getdeveloper ();return this.is_developer
end function

public function string of_getdevcompany ();return this.is_dev_company
end function

public function string of_getdevemail ();return this.is_dev_email
end function

public function string of_getcompanywebsite ();return this.is_dev_company_website
end function

public subroutine of_setlicensedto (string as_company);this.is_licensed_to = as_company
end subroutine

public function string of_getlicensedto ();return this.is_licensed_to
end function

public function string of_getdbname ();return this.is_DBName
end function

public function string of_getdbpath ();return this.is_DBPath
end function

public function String of_getbackuppath ();return this.is_BackupPath
end function

public subroutine of_setdbpath (string as_value);this.is_DBPath = as_value
end subroutine

public subroutine of_setbackuppath (string as_value);this.is_BackupPath = as_value
end subroutine

public subroutine of_setnextbackupcount (long al_value);this.il_NextBackupCount = al_value

setprofilestring(this.of_getAppIniFile(), "Backup", "NextBackupCount", string(al_value))
end subroutine

public function long of_getnextbackupcount ();return this.il_NextBackupCount
end function

public subroutine of_setdbname (string as_value);this.is_DBName = as_value
end subroutine

public subroutine of_dbbackup ();String  ls_command
String  ls_BKFileName
int     li_result


disconnect;
ls_bkfilename = "bk" + string(this.il_nextbackupcount)
this.of_setNextBackupCount(this.il_nextbackupcount + 1)
ls_command = "db_backup.bat" + space(1)
ls_command += this.is_DBPath + space(1) 
ls_command += ls_bkfilename + space(1) 
ls_command += this.is_DBPath + space(1) 
ls_command += this.is_BackupPath

//messagebox("", ls_command)
li_result = run(ls_command)
li_result = li_result


return 
end subroutine

public subroutine of_dbrecover ();String  ls_command
String  ls_BKFileName
string  ls_path
int     li_result
string  ls_title
string  ls_filter

ls_title = "Select Archive to Recover Database"
ls_filter = "Database Archives (*.arj), *.arj"

if getFileOpenName(ls_title, ls_path, ls_bkfilename, "ARJ", ls_filter) <> 1 then
   return
end if

disconnect;
ls_command = "db_restore_backup.bat" + space(1)
ls_command += ls_path + space(1) 
ls_command += this.is_DBPath

li_result = run(ls_command)

return
end subroutine

event destructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  destructor
//
//	Description:	Destroy any instantiated application service objects 
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//	5.0.02   Destroy security service
// 6.0	Destroy app preference and MRU services
//
//////////////////////////////////////////////////////////////////////////////


of_Setdatacache(false)
of_SetSecurity (false)
of_SetAppPreference (false)

end event

event constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  	ue_n_cst_appmanager
//
//	Description:  	PFC Application Manager class
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////
// Get a handle to the application object
//////////////////////////////////////////////////////////////////////////////
iapp_object = GetApplication()

//////////////////////////////////////////////////////////////////////////////
// Populate the environment object
//////////////////////////////////////////////////////////////////////////////
GetEnvironment (ienv_object)



//////////////////////////////////////////////////////////////////////////////
// The following code can be implemented in descendants
//////////////////////////////////////////////////////////////////////////////
/// The file name of the application INI file
//of_SetAppIniFile ("greens")

//// Name of the application
//iapp_object.DisplayName = profilestring(this.of_getAppIniFile(), "Proprietary", "displayname", "")
//
//// Microhelp functionality
//of_SetMicroHelp (true)

//// The application version
//of_SetVersion (profilestring(this.of_getAppIniFile(), "Proprietary", "version", ""))

//// The application build
//of_Setbuild (profilestring(this.of_getAppIniFile(), "Proprietary", "build", ""))

//// The application logo (bitmap file name)
//of_SetLogo (profilestring(this.of_getAppIniFile(), "Proprietary", "logo", ""))
//
//// Application copyright message
//of_SetCopyright (profilestring(this.of_getAppIniFile(), "Proprietary", "copyrightdate", "1999"))

//// Application developed date
//of_SetDevDate (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_date", ""))

//// Application copyright message
//of_SetDevTime (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_time", ""))

//// Application Developer name
//of_SetDeveloper (profilestring(this.of_getAppIniFile(), "Proprietary", "developer", ""))

//// Application Developer Company
//of_SetDevCompany (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_company", ""))

//// Developer E-Mail
//of_SetDevEMail (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_email", ""))

//// Developer Web Site
//of_SetDevWebSite (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_company_website", ""))

//// Licensed Company
//of_SetLicensedTo (profilestring(this.of_getAppIniFile(), "Proprietary", "licensed_to", ""))




//// The file name of the user INI file
//of_SetUserIniFile ("")
//
//// Application registry key
//of_SetAppKey ("")
//
//// User registry key
//of_SetUserKey ("")
//
//// The file name of the application's online help file
//of_SetHelpFile ("")
//
end event

on n_rmt_appmanager.create
call super::create
end on

on n_rmt_appmanager.destroy
call super::destroy
end on

