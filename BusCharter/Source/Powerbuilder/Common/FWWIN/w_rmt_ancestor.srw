$PBExportHeader$w_rmt_ancestor.srw
$PBExportComments$Ancestor of all window objects
forward
global type w_rmt_ancestor from window
end type
end forward

global type w_rmt_ancestor from window
integer x = 1056
integer y = 484
integer width = 2569
integer height = 1516
boolean titlebar = true
string title = "Untitled"
boolean controlmenu = true
boolean minbox = true
boolean maxbox = true
boolean resizable = true
long backcolor = 79741120
event type integer ue_save ( )
event type boolean ue_validate ( powerobject apo_pendingupdates[] )
event ue_preopen ( )
event ue_postopen ( )
event ue_close ( )
event type integer ue_messagerouter ( string as_message )
event ue_open ( )
event ue_setactivecontrol ( dragobject ado_control )
event ue_microhelp ( string as_microhelp )
event type integer ue_updatespending ( powerobject apo_control[] )
event ue_cancel ( )
event ue_preclose ( )
event type integer ue_update ( powerobject apo_control[] )
event type integer ue_add ( )
event type integer ue_delete ( integer ai_row )
event ue_search ( )
event ue_reset ( )
event type integer ue_predelete ( )
event type integer ue_postdelete ( )
event type integer ue_preupdate ( powerobject apo_control[] )
event type integer ue_postupdate ( powerobject apo_control[] )
event ue_enablecontrols ( )
event type boolean ue_print ( )
event type boolean ue_checksecurity ( powerobject apo_control[] )
end type
global w_rmt_ancestor w_rmt_ancestor

type variables
Public:
// - Common return value constants:
constant integer 		SUCCESS = 1
constant integer 		FAILURE = -1
constant integer 		NO_ACTION = 0
// - Continue/Prevent return value constants:
constant integer 		CONTINUE_ACTION = 1
constant integer 		PREVENT_ACTION = 0
//constant integer 		FAILURE = -1
n_rmt_winsrv       inv_base
n_rmt_winresize   inv_resize
n_rmt_windatabject  inv_data
str_dwargs     istr_args[]
str_winargs    istr_winargs
boolean         ib_bypass_dataobject = false

protected:
   boolean            ib_closestatus
   boolean            ib_disableclosequery
   boolean            ib_isupdateable = TRUE
   boolean            ib_notifyuser = FALSE
   u_rmt_dw         idw_active
   powerobject     ipo_updateobjects[]
   n_rmt_update   inv_update
   datawindow      idw_selectioncriteria
   string                 is_sql
   string                 is_oldsql

end variables

forward prototypes
public function integer of_getupdateobjects (ref powerobject apo_objects[])
public function integer of_setbase (boolean ab_switch)
public function integer of_setresize (boolean ab_switch)
public subroutine of_setactivecontrol (dragobject ado_control)
public function integer of_setupdate (boolean ab_switch)
public function integer of_setupdateable (boolean ab_switch)
public function boolean of_isupdateable ()
public function integer of_setupdateobjects (powerobject apo_objects[])
public function boolean of_validate ()
public function integer of_setcriteriadw (datawindow adw_criteria)
public function datawindow of_getcriteriadw ()
public function integer of_updatecheck ()
public function integer of_update ()
public function integer of_delete (integer ai_row)
public function integer of_closewindow ()
public function integer of_updatespending ()
public function integer of_add ()
public function boolean of_checksecurity ()
end prototypes

event ue_save;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_save
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	 0 = No pending changes found 
//	-1 = AcceptText error
//	-2 = UpdatesPending error was encountered
//	-3 = Validation error was encountered
// -9 = The ue_updateprep process failed
//	-4 = The ue_preupdate process failed
//	-5 = The ue_begintran process failed
//	-6 = The ue_update process failed
//	-7 = The ue_endtran process failed
//	-8 = The ue_postsave process failed
//
//	Description:
//	Performs a save operation on the window.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//////////////////////////////////////////////////////////////////////////////

integer		li_result
integer		li_save_rc
integer		li_endtran_rc
boolean 		lb_focusonerror
integer		li_pending_rc = 1
boolean		lb_validation_rc = true
powerobject lpo_control[]


// Check if the CloseQuery process is in progress
if not ib_closestatus then
   // Determine if any changes are pending.
   li_pending_rc = this.of_updatespending()
   if li_pending_rc < 0 then
	   return -2
   end if

   // Check for Errors on controls.
   lb_validation_rc = this.of_validate()
   if not lb_validation_rc then 
	   return -3
   end if	

   if li_pending_rc = 0 then 
	   // No changes to update were found.
   	return 0
   end if
end if


// Prevent datawindow dberror messages from appearing while ue_Save 
// updates are in progress. 
//ib_savestatus = True

// Update the changed objects.
li_save_rc = this.of_update() 

if li_save_rc = 1 then
	commit;
else
	rollback;
end if

// ue_Save Updates are no longer in progress.
//ib_savestatus = False


Return li_save_rc
end event

event ue_validate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  
//	pfc_validate
//
//	Arguments:		
//	apo_pendingupdates   Array of controls that need validation.
//
//	Returns:  integer
//	 true = success
//	 false = validation failed
//
//	Description:
//	Perform validation for specified powerobject array.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

boolean   lb_result

// Let Update Service perform the functionality (create if necessary).
if IsNull(inv_update) Or Not IsValid (inv_update) then 
	of_SetUpdate(true)
end if
if IsValid(inv_update) then
	lb_result = inv_update.of_validate(apo_pendingupdates)
	Return lb_result
End If

Return false
end event

event ue_preopen;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preopen
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description: Performs a preopen logic by capturing the message object passed
//              by the caller and instantiating the window's data object. 
//              Add custom logic at the descendent level.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//////////////////////////////////////////////////////////////////////////////


//  if window is the logon screen or decendent of a frame
//  do not try to capture the message.powerobjectparm
if ib_bypass_dataobject then
	setnull(inv_data)
	return
end if

if isvalid(message.powerobjectparm) and not isnull(message.powerobjectparm) then
	istr_winargs = message.powerobjectparm
	if not isnull(istr_winargs.DataClassName) and len(istr_winargs.DataClassName) > 0 then
   	inv_data = create using istr_winargs.DataClassName
	else
		inv_data = create n_rmt_windatabject
	end if
	inv_data.il_id = handle(this)
	inv_data.is_name = this.ClassName()
else
	inv_data = create n_rmt_windatabject
//	messagebox(gnv_app.iapp_object.displayname, "An RMT data object was not associated with the current window object.~r~nMake note of this error and report to the technical department.");
end if
istr_winargs.parentwin = this    // Assign window reference
inv_data.iw_parent = this
istr_winargs.busobj = inv_data   // Hold business object so that descendent can retrieve and properly use it.

return
end event

event ue_postopen();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postopen
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:	Assigns a reference to this object for all visual controls contained within
//
// Note:        Any additional postopen activity should be coded at the descendent level
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR    Date        Developer    Description
// =====  =========== =========  =============================================
//    7   04/02/2002   RMT        Make Call to of_checksecurity().
//////////////////////////////////////////////////////////////////////////////

if isValid(inv_data) and not isNull(inv_data) then
	inv_data.of_setParent(this, this.control)
end if

this.of_CheckSecurity()  /* SCR7 RMT040202 */
return














//  if window is the logon screen or decendent of a frame
//  do not try to capture the message.powerobjectparm
//if ib_bypass_dataobject then
//	setnull(inv_data)
//	return
//end if
//
//if isvalid(message.powerobjectparm) and not isnull(message.powerobjectparm) then
//	istr_winargs = message.powerobjectparm
//	if not isnull(istr_winargs.DataClassName)  then
//   	inv_data = create using istr_winargs.DataClassName
//	else
//		inv_data = create n_rmt_windatabject
//	end if
//	inv_data.il_id = handle(this)
//	inv_data.is_name = this.ClassName()
//else
//	inv_data = create n_rmt_windatabject
////	messagebox(gnv_app.iapp_object.displayname, "An RMT data object was not associated with the current window object.~r~nMake note of this error and report to the technical department.");
//end if
end event
event ue_close;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_close
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:
//	Close the window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return


//Close (this)
end event

event ue_messagerouter;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_messagerouter
//
//	Arguments:
//	as_message   message (event notification) to send
//
//	Returns:  integer
//	 1 = message successfully sent
//	 0 = no receivers recognized the message
//	-1 = error
//
//	Description:	 
//	This event routes a message (event notification) 
//	to the appropriate object.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

graphicobject lgo_focus

// Check argument
if IsNull (as_message) or Len (Trim (as_message)) = 0 then
	return -1
end if

// Try sending the message to this window, if successful exit event.
if this.triggerevent(as_message) = 1 then 
	return 1
end if

// Try sending the message to the current control, if successful exit event.
lgo_focus = GetFocus()
if IsValid (lgo_focus) then
	if lgo_focus.TriggerEvent(as_message) = 1 then 
		return 1
	end if
end if

// Try sending the message to the last active datawindow, if successful exit event.
if IsValid (idw_active) then
	if idw_active.TriggerEvent(as_message) = 1 then 
		return 1
	end if
end if

// No objects recognized the message
return 0
end event

event ue_setactivecontrol;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_setactivecontrol
//
//	Arguments:
//	adrg_control   Control which just got focus
//
//	Returns:  none
//
//	Description:
//	Keeps track of last active DataWindow
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

If ado_control.TypeOf() = DataWindow! Then
	idw_active = ado_control
End If

end event

event ue_microhelp;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  pfc_microhelp
//
//	Arguments:
//	as_microhelp   microhelp which should be displayed
//
//	Returns:  none
//
//	Description:
//	Display specified microhelp
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

end event

event ue_updatespending;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_UpdatesPending
//
//	Arguments:	
//	apo_control[]	The controls on which to perform functionality.
//
//	Returns:  integer
//	 1 = updates are pending (no errors found)
//	 0 = No updates pending (no errors found)
//	-1 = error
//
//	Description:
//	Request the Logical Unit of Work service to determine which objects have
//	UpdatesPending. 
//
// Note:
//	Simulated Event overloading.
//	This event does NOT store the objects with updates pending in inv_pendingupdates.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_pendingtotal


// Let Update Service perform the functionality (create if necessary).
if IsNull(inv_update) Or Not IsValid (inv_update) then 
	this.of_SetUpdate(true)
end if

if IsValid(inv_update) then
	li_pendingtotal = inv_update.of_updatespending(apo_control)   
	return li_pendingtotal
End If

Return -1
end event

event ue_cancel;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_cancel
//
//	Description:
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

ib_disableclosequery = false
end event

event ue_preclose;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preclose
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description: Insert logic to occur before window closes
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return
end event

event ue_update;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_Update
//
//	Arguments:	None
//
//	Returns:  integer
//	 1 = updates are successful
//	 0 = No updates occurred
//	-1 = error
//
//	Description:
//	Request the Logical Unit of Work (n_rmt_update) service to determine which 
//	objects have updates pending. 
//
// Note:
//	Simulated Event overloading.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int  li_rc

// Make sure there is something to take action on.
if UpperBound(apo_control) = 0 then 
	return NO_ACTION
end if
if not this.of_IsUpdateable() then 
	return NO_ACTION
end if

// Let Logical Unit of Work Service to perform the functionality (create if necessary).
if isNull(inv_update) or not IsValid (inv_update) then 
	this.of_setUPdate(True)
end if
if isValid(inv_update) then
	li_rc = inv_update.of_update(apo_control, true, true)
	return li_rc
end if

return -1


end event

event ue_add;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_add
//
//	Description: adds a row to the datawindow
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return 0
end event

event ue_delete;return 1
end event

event ue_reset;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_reset
//
//	Description:	Code reset or clear functionality at the descendent level
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

event ue_predelete;

return 1
end event

event ue_postdelete;

return 1
end event

event ue_preupdate;

return 1
end event

event ue_postupdate;

return 1
end event

event ue_enablecontrols;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_enablecontrols
//
//	Description:  Add code here at the descendent level to enable/disable
//               graphic controls and ect.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return
end event

event ue_print;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_print
//
//	Description:	Calls the Printer dialog box provided by the 
//                system printer driver and lets the user specify settings 
//                for the printer.  Add logic at the descendent to perform
//                the actual printing, and query AncestorReturnValue to 
//                determine if printing should occur.
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

n_rmt_utility   lnv_util


return lnv_util.of_printDialog(this)
end event

event type boolean ue_checksecurity(powerobject apo_control[]);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_CheckSecurity
//
//	Access:  public
//
//	Arguments:	apo_control array of controls to check for security
//
//	Returns:  boolean
//	         true - success
//	         false - failure
//
//	Description:  Check security for all datawindows contained if any
//
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR    Date        Developer    Description
// =====  =========== =========  =============================================
//    7   04/02/2002   RMT        Added.
//////////////////////////////////////////////////////////////////////////////

// Let Update Service perform the functionality (create if necessary).
if IsNull(inv_update) Or Not IsValid (inv_update) then 
	this.of_SetUpdate(true)
end if

if IsValid(inv_update) then
	return inv_update.of_CheckSecurity(apo_control)   
end if

end event

public function integer of_getupdateobjects (ref powerobject apo_objects[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetUpdateObjects
//
//	Access:  protected
//
//	Arguments:		
//	apo_objects[]   by Reference.  The current array of objects on which the update 
//						process may take effect.
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Get the array on which updates would be attempted.
//
// Note:
// If the ipo_updateobjects array has not been set, the PFC Save functionality, 
//	uses the window control array (this.Control[]).  
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


If UpperBound(ipo_updateobjects) > 0 Then
	apo_objects = ipo_updateobjects
Else
	// If the ipo_updateobjects array has not been set, the ue_save functionality, 
	//	uses the window control array (this.Control[]).  	
	apo_objects = this.Control
End If

Return 1
end function

public function integer of_setbase (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetBase
//
//	Access:  public
//
//	Arguments:
//	ab_switch   enable/disable the base window service
//
//	Returns:  integer
//	 1 = success
//	 0 = no action necessary
//	-1 = error
//
//	Description:
//	Instantiates or destroys the base window service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_rc

// Check arguments
if IsNull (ab_switch) then
	return -1
end if

if ab_Switch then
	if IsNull(inv_base) Or not IsValid(inv_base) then
		inv_base = create n_rmt_winsrv
		inv_base.of_SetRequestor(this)
		li_rc = 1
	end if
else
	if IsValid (inv_base) then
		destroy inv_base
		li_rc = 1
	end if	
end if

return li_rc


end function

public function integer of_setresize (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetResize
//
//	Access:  public
//
//	Arguments:		
//	ab_switch   starts/stops the window resize service
//
//	Returns:  integer
//	 1 = success
//	 0 = no action necessary
//	-1 = error
//
//	Description:
//	Starts or stops the window resize service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


integer	li_result

// Check arguments
if IsNull (ab_switch) then
	return -1
end if

if ab_Switch then
	if IsNull(inv_resize) Or not IsValid (inv_resize) then
		inv_resize = create n_rmt_winresize
		inv_resize.of_SetOrigSize (this.WorkSpaceWidth(), this.WorkSpaceHeight())
		li_result = 1
	end if
else
	if IsValid (inv_resize) then
		destroy inv_resize
		li_result = 1
	end if
end If

return li_result

end function

public subroutine of_setactivecontrol (dragobject ado_control);
end subroutine

public function integer of_setupdate (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUpdate
//
//	Access:  public
//
//	Arguments:
//	ab_switch   enable/disable the base window service
//
//	Returns:  integer
//	 1 = success
//	 0 = no action necessary
//	-1 = error
//
//	Description:
//	Instantiates or destroys the base window service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result

// Check arguments
if IsNull (ab_switch) then
	return -1
end if

if ab_Switch then
	if IsNull(inv_update) Or not IsValid(inv_update) then
		inv_update = create n_rmt_update
		inv_update.of_SetRequestor(this)
		li_result = 1
	end if
else
	if IsValid (inv_update) then
		destroy inv_update
		li_result = 1
	end if	
end if

return li_result


end function

public function integer of_setupdateable (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUpdateable
//
//	Access:  Protected
//
//	Arguments:
//	ab_switch   Indicates whether the Object is updateable
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Sets whether the Object is updateable
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


if IsNull (ab_switch) then 
	return -1
end if
ib_isupdateable =  ab_switch
return 1
end function

public function boolean of_isupdateable ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_isUpdateable
//
//	Access:  Protected
//
//	Arguments: none
//	
//
// Returns:  boolean
//	TRUE   The object is marked as updateable
//	FALSE   The object is not marked as updateable
//
//	Description:
//	Gets the value of the updateable property.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return ib_isupdateable

end function

public function integer of_setupdateobjects (powerobject apo_objects[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUpdateObjects
//
//	Access:  protected
//
//	Arguments:		
//	apo_objects[]   An array of objects on which the update process will take 
//						effect.
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Sets a new default array holding objects for which an updates will be attempted.
// If this function is not called, the PFC default is the object control 
//	array (this.Control[]).
//
// Note:
//	To reset the default back to the window control array, call this function 
//	with an empty powerobject array.
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if IsNull(apo_objects) then 
	return -1
end if
ipo_updateobjects = apo_objects

return 1
end function

public function boolean of_validate ();//////////////////////////////////////////////////////////////////////////////
//
//	Funciton:
//	of_Validate
//
//	Access:  		public
//
//	Arguments:  apo_control[]
//
//	Returns:  boolean
//	true = Passed validations
//	false = Failed validations
//
//	Description:
//	Validate all updateable objects
//
// Note:
//	Specific Validation logic should be coded in descendant ue_Validate event.
//	Part of the Self Updating Object (SUO) API.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

boolean	lb_valid
powerobject lpo_validateobjects[]



// Determine the objects for which an update will be attempted.
// For the pfc_save, the order sequence is as follows: 
//		1) Specified permananent sequence (thru of_SetUpdateObjects(...)).
//		2) None was specified, so use default window control array.
if upperbound(ipo_updateobjects) > 0 then
   lpo_validateobjects = ipo_updateobjects
else
 	lpo_validateobjects = this.Control		
end if

// Make sure there is something to take action on.
if Not of_IsUpdateable() then 
	Return true
end if

lb_valid = this.Event ue_Validate(lpo_validateobjects)

return lb_valid
end function

public function integer of_setcriteriadw (datawindow adw_criteria);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetCriteriaDW
//
//	Access:  public
//
//	Arguments:		
//	adw_criteria   (Datawindow)
//
//	Returns:  integer
//	 1 = success
//	 0 = no action necessary
//	-1 = error
//
//	Description:	Sets the datawindow that contains the selection criteria to be used by
//                this window object's datawindows and datastores.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result

// Check arguments
if not isvalid(adw_criteria) then
	return -1
end if

idw_selectioncriteria = adw_criteria

return 1

end function

public function datawindow of_getcriteriadw ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetCriteriaDW
//
//	Access:  public
//
//	Arguments:		
//	adw_criteria   (datawinow)
//
//	Returns:  datawinodw
//
//	Description:   Returns the datawindow that contains the selection criteria 
//                to be used by this window object's datawindows and datastores.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return idw_selectioncriteria
end function

public function integer of_updatecheck ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_UpdateCheck
//
//	Access:  protected
//
//	Arguments:  
//	  apo_control[]  The array of object for which the checks need to be 
//							performed.
//
//	Returns:  integer
//	 1 = updates were found
//	 0 = No changes to update were found
//	-1 = AcceptText error
//	-2 = UpdatesPending error was encountered
//	-3 = Validation error was encountered
//
//	Description:	Perform accepttext, updatestpending and validation on 
//			the objects.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
boolean 		lb_focusonerror
integer		li_pending_rc = 1
boolean		lb_validation_rc = true
powerobject lpo_control[]


// Check if the CloseQuery process is in progress
if not ib_closestatus then
   // Determine if any changes are pending.
   li_pending_rc = this.of_updatespending()
   if li_pending_rc < 0 then
	   return -2
   end if
end if

// Check for Errors on controls.
lb_validation_rc = this.of_validate()
if not lb_validation_rc then 
	return -3
end if	

if li_pending_rc = 0 then 
	// No changes to update were found.
	return 0
end if

// There are updates pending and no Errors were found.
return 1
end function

public function integer of_update ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:	of_Update
//
//	Access:  	public
//
//	Arguments:  None
//
//	Returns: Integer
//	 1 = The update was successful
//  0 = No action 
//	-1 = The update failed
//
//	Description:  	
//	Execute the specific Update logic.  
//
// Note:
//	Specific Update logic should be coded in descendant ue_Update event.
//	No Action will be executed, If the object is not Updateable.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

powerobject lpo_updatearray[]
int         li_result

// Make sure there is something to take action on.
if not of_IsUpdateable() then 
	return NO_ACTION
end if

if UpperBound(ipo_updateobjects) > 0 then
	lpo_updatearray = ipo_updateobjects
else
	lpo_updatearray = This.Control		
end if

if this.event ue_preupdate(lpo_updatearray) < 1 then
	return -1
end if
if this.event ue_update(lpo_updatearray) < 1 then
	return -2
end if
if this.event ue_postupdate(lpo_updatearray) < 1 then
	return -3
end if

return 1
end function

public function integer of_delete (integer ai_row);
if this.event ue_predelete() < 1 then
	return -1
end if

if this.event ue_delete(ai_row) < 1 then
	return -2
end if

if this.event ue_postdelete() < 1 then
	return -3
end if

return 1
end function

public function integer of_closewindow ();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_closewindow
//
//	Description:  Performs any clean up services before closing the window.
//               Code the events, ue_preclose and ue_close at the descendent
//               level.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


this.event ue_preclose()
this.event ue_close()

return 1
end function

public function integer of_updatespending ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_UpdatesPending
//
//	Access:  protected
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = updates were found
//	 0 = No changes to update were found
//	-1 = AcceptText error
//	-2 = UpdatesPending error was encountered
//	-3 = Validation error was encountered
//
//	Description:	Perform accepttext, updatespending and validation 
//		functionality on the objects which can be found on the control array.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0	Added function overloading to support updatechecks for specific objects.
//
//////////////////////////////////////////////////////////////////////////////

integer		li_pendingtotal
powerobject  lpo_updatespending[]


// Determine the objects for which an update will be attempted.
// For the pfc_save, the order sequence is as follows: 
//		1) Specified permananent sequence (thru of_SetUpdateObjects(...)).
//		2) None was specified, so use default window control array.
if upperbound(ipo_updateobjects) > 0 then
   lpo_updatespending = ipo_updateobjects
else
 	lpo_updatespending = this.Control		
end if

// Perform the Update Checks to determine if there are any updates pending
li_pendingtotal = this.Event ue_UpdatesPending(lpo_updatespending)
if li_pendingtotal <= 0 then 
	return li_pendingtotal
end if

return 1  
end function

public function integer of_add ();
this.event ue_add()

return 1
end function

public function boolean of_checksecurity ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_CheckSecurity
//
//	Access:  public
//
//	Arguments:	apo_control array of controls to check for security
//
//	Returns:  boolean
//	         true - success
//	         false - failure
//
//	Description:  Check security for all datawindows contained if any
//
//	Note:
//	This function is called recursively to handle tab controls and UserObjects
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR    Date        Developer    Description
// =====  =========== =========  =============================================
//    7   04/02/2002   RMT        Added.
//////////////////////////////////////////////////////////////////////////////


powerobject  lpo_controls[]

lpo_controls = this.Control		

// Perform Security check for all contained objects
return this.Event ue_CheckSecurity(lpo_controls)
end function

on w_rmt_ancestor.create
end on

on w_rmt_ancestor.destroy
end on

event close;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  close
//
//	Description:
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// Perform preclose service
this.of_closeWindow()

// Destroy instantiated services
this.of_SetBase(false)








//Integer li_rc
//window	lw_obj
//
//// store preference service information
//If IsValid(inv_preference) Then
//	If gnv_app.of_IsRegistryAvailable() Then
//		If Len(gnv_app.of_GetUserKey())> 0 Then 
//			li_rc = inv_preference.of_Save( &
//				gnv_app.of_GetUserKey()+'\'+this.ClassName()+'\Preferences')
//		ElseIf IsValid(gnv_app.inv_debug) Then				
//			of_MessageBox ("pfc_master_close_preferenceregistrydebug", &
//				"PowerBuilder Class Library", "The PFC Window Preferences service" +&
//				" has been requested but The Required registry attribute has not" +&
//				" been Set.  Use of_SetRegistryUserKey on The Application Manager" +&
//				" to Set The attribute.", &
//				Exclamation!, OK!, 1)				
//		End If
//	Else
//		If Len(gnv_app.of_GetUserIniFile()) > 0 Then
//			li_rc = inv_preference.of_Save( &
//				gnv_app.of_GetUserIniFile(), This.ClassName()+' Preferences')
//		ElseIf IsValid(gnv_app.inv_debug) Then		
//			of_MessageBox ("pfc_master_close_preferenceinidebug", &
//				"PowerBuilder Class Library", "The PFC Window Preferences service" +&
//				" has been requested but The Required inifile attribute has not" +&
//				" been Set.  Use of_SetUserIniFile on The Application Manager" +&
//				" to Set The attribute.", &
//				Exclamation!, OK!, 1)
//		End If
//	End If
//End If
//
//// Update MRU information on the parent window
//If IsValid(gnv_app.inv_mru) Then
//	lw_obj = this.parentwindow()
//	If IsValid(lw_obj) Then
//		lw_obj.post dynamic event pfc_mrurestore()
//	End If
//End If

//of_SetResize(false)
//of_SetPreference(false)

end event

event closequery;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  closequery
//
//	Description:
//	Search for unsaved datawindows prompting the user if any
//	pending updates are found.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


Constant Integer	ALLOW_CLOSE = 0
Constant Integer	PREVENT_CLOSE = 1
powerobject       lpo_updatearray[], lpo_pendingupdates[]
int               li_result



// Check if the CloseQuery process has been disabled
if ib_disableclosequery then
	return ALLOW_CLOSE
end if


// Prevent validation error messages from appearing while the window is closing
// and allow others to check if the  CloseQuery process is in progress
//ib_closestatus = True
ib_closestatus = false

// Check for any pending updates
li_result = of_UpdatesPending()
if li_result = 0 then
	// Updates are NOT pending, allow the window to be closed.
	ib_closestatus = false
	return ALLOW_CLOSE
else
	 // Changes are pending, prompt the user to determine if they should be saved
	 if li_result > 0 then
   	 if IsValid(gnv_app.inv_msg) then
	   	 li_result = gnv_app.inv_msg.of_Message("Do you want to save changes?", exclamation!, YesNoCancel!)		
	    else
		    li_result = MessageBox(gnv_app.iapp_object.DisplayName, "Do you want to save changes?", exclamation!, YesNoCancel!, 1)
	    end if
   	 choose case li_result
		    case 1
			    // YES - Update
			    // If the update fails, prevent the window from closing
			    if this.event ue_save() >= 1 then
				   // Successful update, allow the window to be closed
				   ib_closestatus = false
				   return ALLOW_CLOSE
			    end if
		   case 2
			   // NO - Allow the window to be closed without saving changes
			   ib_closestatus = false
			   return ALLOW_CLOSE
		   case 3
			   // CANCEL -  Prevent the window from closing
	   end choose
	end if
end if

// Prevent the window from closing
ib_closestatus = False
Return PREVENT_CLOSE
end event

event open;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  open
//
//	Description:
//	opens the window
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

Integer li_result

// Allow for pre and post open events to occur
this.event ue_preopen()
this.post event ue_postopen()

// Default window title is application title
if len (this.title) = 0 then
	if IsValid (gnv_app.iapp_object) Then
		this.title = gnv_app.iapp_object.DisplayName
	end if
else
	this.title = gnv_app.iapp_object.DisplayName + " - " + this.title
End If

// Allow preference service to restore settings if necessary
//If IsValid(inv_preference) Then
//	If gnv_app.of_IsRegistryAvailable() Then
//		If Len(gnv_app.of_GetUserKey())> 0 Then 
//			li_result = inv_preference.of_Restore( &
//				gnv_app.of_GetUserKey()+'\'+this.ClassName()+'\Preferences')
//		ElseIf IsValid(gnv_app.inv_debug) Then				
//			of_MessageBox ("pfc_master_open_preferenceregistrydebug", &
//				"PowerBuilder Foundation Class Library", "The PFC User Preferences service" +&
//				" has been requested but The UserRegistrykey property has not" +&
//				" been Set on The application manager Object.~r~n~r~n" + &
//  				"Call of_SetRegistryUserKey on The Application Manager" +&
//				" to Set The property.", &
//				Exclamation!, OK!, 1)
//		End If
//	Else
//		If Len(gnv_app.of_GetUserIniFile()) > 0 Then
//			li_result = inv_preference.of_Restore (gnv_app.of_GetUserIniFile(), This.ClassName()+' Preferences')
//		ElseIf IsValid(gnv_app.inv_debug) Then		
//			of_MessageBox ("pfc_master_open_preferenceinidebug", &
//				"PowerBuilder Class Library", "The PFC User Preferences service" +&
//				" has been requested but The UserINIFile property has not" +&
//				" been Set on The application manager Object.~r~n~r~n" + &
//  				"Call of_SetUserIniFile on The Application Manager" +&
//				" to Set The property.", &
//				Exclamation!, OK!, 1)		
//		End If
//	End If
//End If


end event

event resize;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  resize
//
//	Description:
//	Send resize notification to services
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// Notify the resize service that the window size has changed.
If IsValid (inv_resize) Then
	inv_resize.Event ue_Resize (sizetype, This.WorkSpaceWidth(), This.WorkSpaceHeight())
End If

// Store the position and size on the preference service.
// With this information the service knows the normal size of the 
// window even when the window is closed as maximized/minimized.	
//If IsValid (inv_preference) And This.windowstate = normal! Then
//	inv_preference.Post of_SetPosSize()
//End If
end event

