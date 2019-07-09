$PBExportHeader$n_rmt_windatabject.sru
$PBExportComments$Object used to convey data to and from window objects
forward
global type n_rmt_windatabject from n_rmt_baseobject
end type
end forward

global type n_rmt_windatabject from n_rmt_baseobject
end type
global n_rmt_windatabject n_rmt_windatabject

type variables
public:
  long il_id
  string is_name
  datawindow idw_data
end variables

forward prototypes
public function datawindow of_setdataobject (u_rmt_dw adw_data)
public function integer of_enablecontrols (powerobject apo_control[])
public function integer of_setparent (window aw_parent, powerobject apo_control[])
end prototypes

public function datawindow of_setdataobject (u_rmt_dw adw_data);//////////////////////////////////////////////////////////////////////////////
//
//	Method:  of_setDataObject
//
//	Arguments:  u_rmt_dw
//
//	Returns:  none
//
//	Description: copies the data from the target datawindow (adw_data) to this object's
//              datasotre (this.ids_data).
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_result

idw_data = adw_data
//
//ids_data.dataobject = adw_data.dataobject
//ids_data.settransobject(sqlca)
//
//adw_data.rowscopy(1, adw_data.rowcount(), primary!, this.ids_data, 1, primary!)
//
return this.idw_data

end function

public function integer of_enablecontrols (powerobject apo_control[]);////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_enableControls
//
//	Access:  public
//
//	Arguments:
//	apo_control[]   Array of controls that need to be updated
//
//	Returns:   integer
//	 1 = all updates successful
//	-1 = At least one update failed
//
//	Description:	Enable/Disable controls of datawindow objects as specified 
//                in the array of controls.
//
//	Note:
//	This function will enable/disable objects in the order in which they are found in
//	the array.
//
//	Note:	 
//	This function is called recursively to handle tab controls and user objects.
////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
////////////////////////////////////////////////////////////////////////////

Integer		li_max
Integer		li_i
Integer		li_rc
PowerObject lpo_tocheck
PowerObject	lpo_updaterequestor
UserObject	luo_control
tab			ltab_control
window		lw_control
u_rmt_dw 	ldw_obj
//n_rmt_ds 	lds_obj
//DataWindow	ldw_control



if UpperBound(apo_control) = 0 then 
	return NO_ACTION
end if


// Loop thru all the objects
li_max = UpperBound (apo_control)
For li_i = 1 to li_max
	lpo_tocheck = apo_control[li_i]
	if isNull(lpo_tocheck) or not isValid(lpo_tocheck) then 
		continue
	end if

   // Windows!, Tabs!, and UserObjects! can either be SelfUpdatingObjects (SUO) or
	// they can be controls which may be holding SelfUpdatingObjects.
	// If they are found to be SUO then they will treated as such, if not their
	// respective control array will be traversed in search of other SUOs.
	choose case typeOf(lpo_tocheck)
		case Window!
			// Test for Window Controls
			lw_control = lpo_tocheck
			li_rc = this.of_enableControls(lw_control.control) 
			if li_rc < 0 then 
				return -1
			end if
			continue
	
		case Tab!
			// Test for Tab Controls
			ltab_control = lpo_tocheck
			li_rc = this.of_enableControls(ltab_control.control) 
			if li_rc < 0 then 
				return -1
			end if
			continue


		case UserObject!
			// Test for UserObjects
			luo_control = lpo_tocheck
			li_rc = this.of_enableControls(luo_control.control)
			if li_rc < 0 then 
				return -1
			end if
			continue
	end choose
			
		
	// Handle DataWindows/DataStores.
	if typeOf(lpo_tocheck) = DataWindow! then
		ldw_obj = lpo_tocheck
		ldw_obj.event dynamic ue_enablecontrols()
	end if	
next

// All updates were successful.
return 1

end function

public function integer of_setparent (window aw_parent, powerobject apo_control[]);////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_setParent
//
//	Access:  public
//
//	Arguments:
// aw_parent       Reference to parent window
//	apo_control[]   Array of controls that need to be updated
//
//	Returns:   integer
//	 1 = all updates successful
//	-1 = At least one update failed
//
//	Description:	Sets the iw_parent property for all RMT2 visual objects to the parent window
//                to which they are contained.
//
//	Note:	 
//	This function is called recursively to handle tab controls and user objects.
////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
////////////////////////////////////////////////////////////////////////////

Integer		li_max
Integer		li_i
Integer		li_rc
PowerObject lpo_tocheck
u_rmt_base	luo_control
u_rmt_tab	ltab_control
w_rmt_ancestor	lw_control
u_rmt_dw 	ldw_obj


if UpperBound(apo_control) = 0 then 
	return NO_ACTION
end if


// Loop thru all the objects
li_max = UpperBound (apo_control)
For li_i = 1 to li_max
	lpo_tocheck = apo_control[li_i]
	if isNull(lpo_tocheck) or not isValid(lpo_tocheck) then 
		continue
	end if

   // Windows!, Tabs!, and UserObjects! can either be SelfUpdatingObjects (SUO) or
	// they can be controls which may be holding SelfUpdatingObjects.
	// If they are found to be SUO then they will treated as such, if not their
	// respective control array will be traversed in search of other SUOs.
	choose case typeOf(lpo_tocheck)
		case Window!
			// Test for Window Controls
			lw_control = lpo_tocheck
			li_rc = this.of_setParent(aw_parent, lw_control.control) 
			if li_rc < 0 then 
				return -1
			end if
			continue
	
		case Tab!
			// Test for Tab Controls
			ltab_control = lpo_tocheck
			ltab_control.iw_parent = aw_parent
			li_rc = this.of_setParent(aw_parent, ltab_control.control) 
			if li_rc < 0 then 
				return -1
			end if
			continue


		case UserObject!
			// Test for UserObjects
			luo_control = lpo_tocheck
			luo_control.iw_parent = aw_parent
			li_rc = this.of_setParent(aw_parent, luo_control.control)
			if li_rc < 0 then 
				return -1
			end if
			continue
	end choose
			
		
	// Handle DataWindows/DataStores.
	if typeOf(lpo_tocheck) = DataWindow! then
		ldw_obj = lpo_tocheck
		ldw_obj.iw_parent = aw_parent
	end if	
next

// All updates were successful.
return 1

end function

on n_rmt_windatabject.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_windatabject.destroy
TriggerEvent( this, "destructor" )
end on

event constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  constructor
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description: Initializes protperties that will be returned to the calling 
//              script via a call to CloseWithReturn.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

idw_data = create datawindow
setnull(il_id)
setnull(is_name)
end event

