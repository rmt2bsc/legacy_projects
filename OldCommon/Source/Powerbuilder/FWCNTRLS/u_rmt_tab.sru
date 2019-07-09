$PBExportHeader$u_rmt_tab.sru
$PBExportComments$Tab Control
forward
global type u_rmt_tab from tab
end type
end forward

global type u_rmt_tab from tab
integer width = 1723
integer height = 948
integer taborder = 10
integer textsize = -10
integer weight = 400
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = swiss!
string facename = "Arial"
long backcolor = 79741120
boolean raggedright = true
integer selectedtab = 1
event type integer ue_updatespending ( powerobject apo_control[] )
event type boolean ue_validate ( powerobject apo_control[] )
event resize pbm_size
event type integer ue_update ( powerobject apo_control[] )
event ue_init ( )
event type boolean ue_checksecurity ( powerobject apo_control[] )
end type
global u_rmt_tab u_rmt_tab

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
n_rmt_winresize	inv_resize
n_rmt_utility              inv_utility
w_rmt_ancestor       iw_parent

Protected:
// Logical Unit of Work -  SelfUpdatingObject - Save Process - (Attributes).
boolean		ib_isupdateable = True
powerobject	ipo_updateobjects[]
n_rmt_update	inv_update
graphicobject          parentobj
u_rmt_tabpage        i_currentpage
end variables

forward prototypes
public function integer of_setresize (boolean ab_switch)
public function integer of_setupdate (boolean ab_switch)
public function integer of_updatespending ()
public function integer of_setupdateable (boolean ab_switch)
public function boolean of_isupdateable ()
public function integer of_setupdateobjects (powerobject apo_objects[])
public function integer of_getupdateobjects (ref powerobject apo_objects[])
public function boolean of_validate ()
public subroutine of_setparentwindow (w_rmt_ancestor aw_parent)
public function boolean of_checksecurity ()
end prototypes

event ue_updatespending;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  pfc_UpdatesPending
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
//	UpdatesPending.  Store references in pending array.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


constant integer NO_UPDATESPENDING = 0
integer	li_pendingtotal
powerobject lpo_updatearray[]


// Make sure there is something to take action on.
if UpperBound(apo_control) = 0 then 
	return NO_UPDATESPENDING
end if
if not of_IsUpdateable() then 
	return NO_UPDATESPENDING
end if

// Let Logical Unit of Work Service perform the functionality (create if necessary).
if IsNull(inv_update) or not IsValid (inv_update) then 
	of_SetUpdate(true)
end if
if IsValid(inv_update) then
	li_pendingtotal = inv_update.of_UpdatesPending(apo_control)
	return li_pendingtotal
end if

return -1
end event

event ue_validate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  
//	ue_validate
//
//	Arguments:	
//	apo_control[]	The controls on which to perform functionality.
//
//	Returns:  boolean
//	 true = success
//  false = error
//
//	Description:
//	Request the Logical Unit of Work service to perform Validation functionality
//	on the array.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

boolean  lb_result


// Make sure there is something to take action on.
if UpperBound(apo_control) = 0 then 
	return true
end if
if not of_IsUpdateable() then 
	return true
end if

// Let Logical Unit of Work Service perform the functionality (create if necessary).
If IsNull(inv_update) Or Not IsValid (inv_update) Then 
	this.of_SetUpdate(True)
end if
If IsValid(inv_update) Then
	lb_result = inv_update.of_Validate(apo_control)
	Return lb_result
End If

Return false
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
	inv_resize.Event ue_Resize (sizetype, This.Width, This.Height)
End If
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
	return inv_update.of_update(apo_control, true, false)
end if

return -1


end event

event ue_init;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  ue_init
//
//	Description:  Perform any initializations
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// Get parent window of this listview object
iw_parent = inv_utility.of_getParentWindow(this)

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
		inv_resize.of_SetOrigSize (this.Width, this.Height)
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

public function integer of_updatespending ();//////////////////////////////////////////////////////////////////////////////
//
//	Funciton:
//	of_UpdatesPending
//
//	Access:  		public
//
//	Arguments:  none
//
//	Returns:  integer
//	1 = Updates are pending.
//	0 = No updates are pending
// -1 = Failure
//
//	Description:
//	Determine if any updates are pending on this object.  
//	Set the ipo_pendingupdates array.
//
// Note:
//	Specific UpdatesPending logic should be coded in descendant pfc_UpdatesPending event.
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


constant integer NO_UPDATESPENDING =0
integer	li_pendingtotal 
powerobject lpo_updatearray[]

// Make sure there is something to take action on.
if Not of_IsUpdateable() then 
	Return NO_UPDATESPENDING
end if

// Determine the appropriate array.
If UpperBound(ipo_updateobjects) > 0 Then
	lpo_updatearray = ipo_updateobjects
Else
	lpo_updatearray = This.Control		
End If
li_pendingtotal = this.Event ue_UpdatesPending(lpo_updatearray)

return li_pendingtotal
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


if IsNull (ab_switch) then return -1
ib_isupdateable =  ab_switch
return 1
end function

public function boolean of_isupdateable ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetUpdateable
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

public function integer of_getupdateobjects (ref powerobject apo_objects[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetUpdateObjects
//
//	Access:  protected
//
//	Arguments:		
//	apo_objects[] (by reference) An array of objects on which the update process 
//				will take effect.
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Gets the current default array holding objects for which an updates will be
//	attempted.
//
//	Note:
// If ipo_updateobjects has not yet been set, the function returns the object's
//	control array.
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
	Return 1
End If

apo_objects = this.Control
Return 1
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
powerobject lpo_updatearray[]

// Make sure there is something to take action on.
if Not of_IsUpdateable() then 
	Return true
end if

// Determine the appropriate array.
If UpperBound(ipo_updateobjects) > 0 Then
	lpo_updatearray = ipo_updateobjects
Else
	lpo_updatearray = This.Control		
End If


lb_valid = this.Event ue_Validate(lpo_updatearray)

return lb_valid
end function

public subroutine of_setparentwindow (w_rmt_ancestor aw_parent);
iw_parent = aw_parent


return
end subroutine

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
event destructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  destructor
//
//	Description:
//	Perform cleanup.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0.04   Initial version
// 6.0		Enhanced to cleanup new services.
//
//////////////////////////////////////////////////////////////////////////////


// Destroy instantiated services
of_SetUpdate(False)
of_SetResize(False)
end event

event constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  constructor
//
//	Description:
//	Perform any initializations
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

this.event ue_init()


end event

event selectionchanged;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  selectionchanged
//
//	Description:	Keeps track of the tab page currently selected using 
//                the instance variable, which is of type u_rmt_tabpage,
//                i_currentpage.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if upperbound(this.control) > 0 then
	i_currentpage = this.control[newindex]
end if

return 1
end event

