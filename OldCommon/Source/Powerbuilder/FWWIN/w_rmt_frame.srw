$PBExportHeader$w_rmt_frame.srw
$PBExportComments$MDI Frame Window
forward
global type w_rmt_frame from w_rmt_ancestor
end type
type mdi_1 from mdiclient within w_rmt_frame
end type
end forward

global type w_rmt_frame from w_rmt_ancestor
string title = "User Maintenance"
string menuname = "m_rmt_ancestor"
windowtype windowtype = mdihelp!
long backcolor = 80269524
event type integer ue_minimizeall ( )
event type integer ue_layer ( )
event type integer ue_cascade ( )
event type integer ue_tilehorizontal ( )
event type integer ue_tile ( )
event type integer ue_undoarrange ( )
event ue_codetables ( )
event ue_states ( )
event ue_city ( )
event type integer ue_iconize ( )
event ue_user_maint ( )
event ue_general_groups ( )
event ue_closeactivesheet ( )
event type integer ue_login ( )
event ue_logout ( )
event ue_about ( )
event ue_printsetup ( )
event ue_dbbackup ( )
event ue_dbrecover ( )
event ue_changepassword ( )
mdi_1 mdi_1
end type
global w_rmt_frame w_rmt_frame

type variables
Public:
n_rmt_winsrv_sheetmanager	inv_sheetmanager
end variables

forward prototypes
public function integer of_setsheetmanager (boolean ab_switch)
public function integer of_setstatusbar (boolean ab_switch)
end prototypes

event ue_minimizeall;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_minimizeall
//
//	Arguments:  none
//
//	Returns:  integer
//	 number of sheets minimized
//	-1 = error
//
//	Description:
//	Minimizes all open sheets
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_minimizeall()
end if

return li_result
end event

event ue_layer;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_layer
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:	Layers windows
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_layer()
else
	li_result = this.ArrangeSheets (layer!)
end if

return li_result
end event

event ue_cascade;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_cascade
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:	Cascades windows
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_cascade()
else
	li_result = this.ArrangeSheets (cascade!)
end if

return li_result
end event

event ue_tilehorizontal;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_tilehorizontal
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:	Tiles windows horizontally
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_tilehorizontal()
else
	li_result = this.ArrangeSheets (tilehorizontal!)
end if

return li_result
end event

event ue_tile;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_tilevertical
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:	Tiles windows vertically
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_tilevertical()
else
	li_result = this.ArrangeSheets (tile!)
end if

return li_result
end event

event ue_undoarrange;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_undoarrange
//
//	Arguments:  none
//
//	Returns:  integer
//	number of sheets affected by undo
//	-1 = error
//
//	Description:	Undo last window arrange
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_undoarrange()
end if

return li_result
end event

event ue_codetables;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_codetables
//
//	Description: Invoke the code table maintenance window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////



//opensheet(w_general_codes, gnv_app.of_getframe(), 1, layered!)

open(w_general_codes)
return
end event

event ue_states;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_states
//
//	Description: Invoke the states code table maintenance window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////



//opensheet(w_states, gnv_app.of_getframe(), 1, layered!)

open(w_states)
return
end event

event ue_city;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_city
//
//	Description: Invoke the city code table maintenance window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////



//opensheet(w_city, gnv_app.of_getframe(), 1, layered!)


open(w_city)
return
end event

event ue_iconize;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_iconize
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:	Iconize windows
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result

if IsValid (inv_sheetmanager) then
	li_result = this.inv_sheetmanager.event ue_cascade()
else
	li_result = this.ArrangeSheets (icons!)
end if

return li_result
end event

event ue_user_maint;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_user_maint
//
//	Description: opens the user maintenance window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////

str_winargs  lstr  

lstr.dataclassname = "n_rmt_windatabject"
openwithparm(w_user_maint, lstr)

end event

event ue_general_groups;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_general_Groups
//
//	Description: Invoke the general groups maintenance window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////



//opensheet(w_general_codes, gnv_app.of_getframe(), 1, layered!)

open(w_general_groups)
return
end event

event ue_closeactivesheet;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_closeActiveSheet
//
//	Description:	Closes the active sheet
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////

window   lw_window

if not isValid(inv_sheetmanager) or isNull(inv_sheetmanager) then
	lw_window = this.getActiveSheet()
	if isValid(lw_window) then
		close(lw_window)
	end if
	
	return
end if

this.inv_sheetmanager.of_closeActiveSheet()

return
end event

event ue_login;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_login
//
//	Description:	Invokes the login screen.  If login is not successfull, then
//                shutdown the application.  Otherwise, check and enable the
//                controls as necessary at the descendent level.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if gnv_app.of_logondlg() <> 1 then
	 halt
end if
this.event ue_enablecontrols()

return 1
end event

event ue_logout();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_login
//
//	Description:	Logs out the current user and enable/disable the
//                controls as necessary at the descendent level.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
int  li_result
string  ls_user

ls_user = gnv_app.of_getUserId()
li_result = gnv_app.inv_msg.of_message("Loggin out user, " + ls_user + ".  ~r~nAre you sure you want to proceed? ", question!, YesNo!)
if li_result = 1 then
	gnv_app.of_setUserID("")
	gnv_app.of_setAccess(0)   // SCR7 RMT040202
   this.event ue_enablecontrols()
	gnv_app.inv_msg.of_message("User, " + ls_user + ", successfully logged out of systems!")
end if
	
end event
event ue_about;
//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_about
//
//	Description: 	Open the about window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////

open(w_about)
end event

event ue_printsetup;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_printSetup
//
//	Description:	Calls the Printer Setup dialog box provided by the 
//                system printer driver and lets the user specify settings 
//                for the printer.
//
// Returns:  boolean (true=print, false=do not print)
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

printSetup()
end event

event ue_dbbackup;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_DBBackup
//
//	Description:	Stub event for backing up Database.  Logic should coded at
//                the descendent level
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return
end event

event ue_dbrecover;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_DBRecover
//
//	Description:	Stub event for recovering Database.  Logic should coded at
//                the descendent level
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return
end event

event ue_changepassword;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_changepassword
//
//	Description:	Event for changeing the password of the currently logged on
//                user.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////


open(w_password_change)
end event

public function integer of_setsheetmanager (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetSheetManager
//
//	Access:  public
//
//	Arguments:		
//	ab_switch   create/destroy the service
//
//	Returns:  integer
//	 1 = Successful operation.
//	 0 = No action taken.
//	-1 = An error was encountered.
//
//	Description:  Starts or stops the Sheet manager service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


integer	li_rc

// Check arguments.
if IsNull (ab_switch) then return -1

if ab_switch then
	if IsNull(inv_sheetmanager) Or not IsValid (inv_sheetmanager) then
		inv_sheetmanager = create n_rmt_winsrv_sheetmanager
		inv_sheetmanager.of_SetRequestor (this)
		li_rc = 1
	end if
else
	if IsValid (inv_sheetmanager) then
		destroy inv_sheetmanager
		li_rc = 1
	end if
end if

return li_rc
end function

public function integer of_setstatusbar (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetStatusBar
//
//	Access:  		public
//
//	Arguments:		
//	ab_switch		True - start (create) the service,
//						False - stop (destroy) the service
//
//	Returns:  		Integer
//	 1 - Successful operation.
//	 0 - No action taken.
//	-1 - An error was encountered.
//
//	Description:
//	Starts or stops the Statusbar service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


environment	lenv_obj

// Check arguments
If IsNull(ab_switch) Then 
	Return -1
End If

If this.windowtype <> mdihelp! Then
	Return -1
End If 

// Check current platform to determine if statusbar service is supported
GetEnvironment (lenv_obj)
if IsValid (lenv_obj) then
	if lenv_obj.ostype <> windows! and lenv_obj.ostype <> windowsnt! then
		return -1
	end if
end if
//
//if ab_switch then
//	if IsNull(inv_statusbar) Or not IsValid (inv_statusbar) then
//		inv_statusbar = create n_rmt_winsrv_statusbar
//		inv_statusbar.of_SetRequestor (this)
//		Return 1
//	end if
//else
//	if IsValid (inv_statusbar) then
//		destroy inv_statusbar
//		Return 1
//	end if
//end if

Return 0
end function

on w_rmt_frame.create
int iCurrent
call super::create
if this.MenuName = "m_rmt_ancestor" then this.MenuID = create m_rmt_ancestor
this.mdi_1=create mdi_1
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.mdi_1
end on

on w_rmt_frame.destroy
call super::destroy
if IsValid(MenuID) then destroy(MenuID)
destroy(this.mdi_1)
end on

event close;call super::close;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  close
//
//	Description:
//	Destroy any instantiated services
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.02   Initial version
//
//////////////////////////////////////////////////////////////////////////////

of_SetSheetManager (false)
of_SetStatusbar (false)
end event

event ue_postopen;call super::ue_postopen;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postopen
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  Post Open functionality.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


gnv_app.of_splash(5)

//// Logon to the database
//sqlca.dbms = profilestring(gnv_app.of_getappinifile(), "Database", "DBMS", "");
//sqlca.dbparm = profilestring(gnv_app.of_getappinifile(), "Database", "DBParm", "");
//sqlca.userid = profilestring(gnv_app.of_getappinifile(), "Database", "UserID", "");
//sqlca.dbpass = profilestring(gnv_app.of_getappinifile(), "Database", "Password", "");
//connect using sqlca;
//
//if sqlca.sqlcode <> 0 then
//	messagebox(gnv_app.iapp_object.DisplayName, "Problem logging on to the database.~r~nSQL Error: " + sqlca.sqlerrtext)
//	halt
//end if

gnv_app.event ue_connect()

this.of_setSheetManager(true)

this.event ue_enablecontrols()


end event

event resize;call super::resize;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  resize
//
//	Description:  Notify related objects that window has resized.  Place code
//               in the decendent.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return


end event

event open;call super::open;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  Activate
//
//	Description:
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////



// Set window state to Maximized
this.windowstate = maximized!


end event

event ue_microhelp;call super::ue_microhelp;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_microhelp
//
//	Arguments:		
//	as_microhelp   string to be displayed as microhelp
//
//	Returns:  none
//
//	Description:  Displays specified microhelp
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if gnv_app.of_GetMicrohelp() then
	this.SetMicroHelp(as_microhelp)
end if
end event

event ue_preopen;call super::ue_preopen;ib_bypass_dataobject = true

// Set the active frame of the application manager object.
gnv_app.of_SetFrame (this)
end event

type mdi_1 from mdiclient within w_rmt_frame
long BackColor=276856960
end type

