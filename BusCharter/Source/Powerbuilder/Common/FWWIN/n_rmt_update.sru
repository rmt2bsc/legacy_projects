$PBExportHeader$n_rmt_update.sru
$PBExportComments$Validation and Updating Object
forward
global type n_rmt_update from n_rmt_baseobject
end type
end forward

global type n_rmt_update from n_rmt_baseobject
end type
global n_rmt_update n_rmt_update

type variables
public:
   powerobject    ipo_requestor
   string               is_instancename   
end variables

forward prototypes
public function integer of_setrequestor (powerobject apo_requestor)
public function integer of_updatespending (powerobject apo_control[])
public function boolean of_checksecurity (powerobject apo_control[])
public function boolean of_validate (powerobject apo_control[])
public function integer of_update (powerobject apo_control[], boolean ab_accepttext, boolean ab_resetflag)
end prototypes

public function integer of_setrequestor (powerobject apo_requestor);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  
//	of_SetRequestor
//
//	Access:    Public
//
//	Arguments:
//   apo_requestor   The object requesting the service.
//
//	Returns:  Integer
//	1 if it succesful. 
// -1 in error.
//
//	Description:  
//	Associates a requestor powerobject control with the LUW service.
//
//	Note:
//	This reference is not required.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// Validate argument.
if IsNull(apo_requestor) or Not IsValid(apo_requestor) then 
	Return -1
end if

// Set the requestor
ipo_requestor = apo_requestor

// Give the object a better unique name.
is_instancename = ipo_requestor.ClassName()+'.'+this.ClassName()

Return 1
end function

public function integer of_updatespending (powerobject apo_control[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  
//	of_UpdatesPending
//
//	Access:  protected
//
//	Arguments:
//	apo_control array of controls to check for any updates pending
// apo_pendingupdates - array of controls that have pending updates as a result of this
//                      this method
//
//	Returns:  integer
//	 # of objects with updates pending
//	-1 = error
//
//	Description:
//	Check in each object for updatespending for the specified array and store
//	references in ipo_pendingupdates
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
//////////////////////////////////////////////////////////////////////////////

integer		    li_new
integer         li_totalcontrols
integer         li_cntr_ndx
integer         li_pendingtotal
boolean		    lb_updatespending
powerobject	    lpo_tocheck
u_rmt_base	    luo_control
u_rmt_tab		 ltab_control
w_rmt_ancestor	 lw_control
u_rmt_dw 	    ldw_control
n_rmt_ds 	    lds_control
datawindow 	    ldw_nonrmt
datastore 	    lds_nonrmt




li_totalcontrols = upperbound (apo_control)
li_pendingtotal = 0

// Loop thru all the objects
for li_cntr_ndx = 1 to li_totalcontrols
	lpo_tocheck = apo_control[li_cntr_ndx]
	if IsNull(lpo_tocheck) or not IsValid(lpo_tocheck) then 
		Continue
	end if
	
	// Initialize
	lb_updatespending = false
 
	choose case typeof(lpo_tocheck)
		case window!
			lw_control = lpo_tocheck
			li_pendingtotal += lw_control.of_updatespending() 
			if li_pendingtotal < 0 then 
				return -1
			end if
			continue
			
		case tab!
			ltab_control = lpo_tocheck
			li_pendingtotal += ltab_control.of_updatespending() 
			if li_pendingtotal < 0 then 
				return li_pendingtotal
			end if
			continue

		case userObject!
			luo_control = lpo_tocheck
			li_pendingtotal += luo_control.of_updatespending() 
			if li_pendingtotal < 0 then 
				return li_pendingtotal
			end if
			continue
			
		case datawindow!
			ldw_control = lpo_tocheck
			ldw_control.accepttext()
			lb_updatespending = (ldw_control.ModifiedCount() + ldw_control.DeletedCount() >= 1) and ldw_control.of_isupdateable()
			
		case datastore!
			lds_control = lpo_tocheck
			ldw_control.accepttext()
		   lb_updatespending = (lds_control.ModifiedCount() + lds_control.DeletedCount() >= 1) and ldw_control.of_isupdateable()
	end choose 
			

	// If Updates are Pending, add the object to the list.
	if lb_updatespending then
		li_pendingtotal++            // accumulate total number of pending updates for parent object
		// Get the new upperbound for the pending changes.
		//li_new = upperbound(apo_pendingupdates) + 1
		// Store the control with updates pending.
		//apo_pendingupdates[li_new] = lpo_tocheck  // *Add Entry* to instance.
	end if
next

return li_pendingtotal         //upperbound(apo_pendingupdates)
end function

public function boolean of_checksecurity (powerobject apo_control[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  
//	of_CheckSecurity
//
//	Access:  public
//
//	Arguments:
//	apo_control array of controls to check for security
//
//	Returns:  boolean
//	         true - success
//	         false - failure
//
//	Description:  Check security for all datawindows for a window or userobject
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


integer		   li_new
integer        li_totalcontrols
integer        li_cntr_ndx
boolean		   lb_valid
powerobject	   lpo_tocheck
u_rmt_base  	luo_control
u_rmt_tab		ltab_control
w_rmt_ancestor	lw_control
u_rmt_dw 	   ldw_control
n_rmt_ds 	   lds_control
datawindow 	   ldw_nonrmt
datastore 	   lds_nonrmt
scriptdefinition	lscrd_object
classdefinition   lcd
string            ls_args[]



// Initialize
lb_valid = false

// Loop thru all the objects
li_totalcontrols = upperbound (apo_control)
For li_cntr_ndx = 1 to li_totalcontrols
	lpo_tocheck = apo_control[li_cntr_ndx]
	if IsNull(lpo_tocheck) or not IsValid(lpo_tocheck) then 
		Continue
	end if
	
	choose case typeof(lpo_tocheck)
		case window!
			lw_control = lpo_tocheck
			lb_valid = lw_control.of_checksecurity() 
			continue
			
		case tab!
			ltab_control = lpo_tocheck
			lb_valid = ltab_control.of_checksecurity() 
			if not lb_valid then
				return false
			end if
			continue

		case userObject!
			luo_control = lpo_tocheck
			lb_valid = luo_control.of_checksecurity() 
			if not lb_valid then
				return false
			end if
			continue
			
		case datawindow!
			ldw_control = lpo_tocheck
      	lb_valid = ldw_control.event dynamic ue_checksecurity()
			
		case datastore!
			lds_control = lpo_tocheck
	end choose 
next

return lb_valid 
end function
public function boolean of_validate (powerobject apo_control[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  
//	of_Validate
//
//	Access:  public
//
//	Arguments:
//	apo_control array of controls to check for any updates pending
//
//	Returns:  boolean
//	         true - data passed validation
//	         false - data validation failed
//
//	Description:
//	Check in each object for valid data.
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
//////////////////////////////////////////////////////////////////////////////


integer		   li_new
integer        li_totalcontrols
integer        li_cntr_ndx
boolean		   lb_valid
powerobject	   lpo_tocheck
u_rmt_base  	luo_control
u_rmt_tab		ltab_control
w_rmt_ancestor	lw_control
u_rmt_dw 	   ldw_control
n_rmt_ds 	   lds_control
datawindow 	   ldw_nonrmt
datastore 	   lds_nonrmt
scriptdefinition	lscrd_object
classdefinition   lcd
string            ls_args[]



// Initialize
lb_valid = false

// Loop thru all the objects
li_totalcontrols = upperbound (apo_control)
For li_cntr_ndx = 1 to li_totalcontrols
	lpo_tocheck = apo_control[li_cntr_ndx]
	if IsNull(lpo_tocheck) or not IsValid(lpo_tocheck) then 
		Continue
	end if
	
	choose case typeof(lpo_tocheck)
		case window!
			lw_control = lpo_tocheck
			lb_valid = lw_control.of_validate() 
			continue
			
		case tab!
			ltab_control = lpo_tocheck
			lb_valid = ltab_control.of_validate()  
			if not lb_valid then
				return false
			end if
			continue

		case userObject!
			luo_control = lpo_tocheck
			lb_valid = luo_control.of_validate() 
			if not lb_valid then
				return false
			end if
			continue
			
		case datawindow!
			ldw_control = lpo_tocheck
//			if not ldw_control.of_isupdateable() then
//				continue
//			end if
			
         // Determine if lpo_check belongs to the RMT Framework
			lcd = ldw_control.classdefinition
         lscrd_object = lcd.findMatchingFunction("of_Validate", ls_args)
         if isValid (lscrd_object) then
         	lb_valid = ldw_control.event dynamic ue_validate()
				if not lb_valid and ldw_control.of_isupdateable() then
					return false
				end if
         end if
			
		case datastore!
			lds_control = lpo_tocheck
//			if not lds_control.of_isupdateable() then
//				continue
//			end if
         // Determine if lpo_check belongs to the RMT Framework
			lcd = lds_control.classdefinition
         lscrd_object = lcd.findMatchingFunction("of_Validate", ls_args)
         if isValid (lscrd_object) then			
   			lb_valid = lds_control.event dynamic ue_validate()
				if not lb_valid and lds_control.of_isupdateable() then
					return false
				end if
			end if
	end choose 
next

return lb_valid 
end function
public function integer of_update (powerobject apo_control[], boolean ab_accepttext, boolean ab_resetflag);////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_Update
//
//	Access:  public
//
//	Arguments:
//	apo_control[]   Array of controls that need to be updated
//	ab_accepttext	When applicable, specifying whether control should perform an
//						AcceptText prior to performing the update:
//	ab_resetflag	Value specifying whether object should automatically 
//						reset its update flags.
//
//	Returns:   integer
//	 1 = all updates successful
//	-1 = At least one update failed
//
//	Description:
//	Updates the specified array of controls.
//
//	Note:
//	This function will update objects in the order in which they are found in
//	the array.  The linked datawindows are updated according to linkage service
// information.
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
n_rmt_ds 	lds_obj
DataWindow	ldw_nonrmt
DataStore	lds_nonrmt


if UpperBound(apo_control) = 0 then 
	return NO_ACTION
end if


// Determine the object to pass in as the requestor.
if IsValid(ipo_requestor) then
	lpo_updaterequestor = ipo_requestor
else
	lpo_updaterequestor = this
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
			li_rc = this.of_Update(lw_control.control, ab_accepttext, ab_resetflag ) 
			if li_rc < 0 then 
				return -1
			end if
			continue
	
		case Tab!
			// Test for Tab Controls
			ltab_control = lpo_tocheck
			li_rc = this.of_Update(ltab_control.control, ab_accepttext, ab_resetflag) 
			if li_rc < 0 then 
				return -1
			end if
			continue


		case UserObject!
			// Test for UserObjects
			luo_control = lpo_tocheck
			li_rc = this.of_Update(luo_control.control, ab_accepttext, ab_resetflag)
			if li_rc < 0 then 
				return -1
			end if
			continue
	end choose
			
		
	// Handle DataWindows/DataStores.
	if typeOf(lpo_tocheck) = DataWindow! then
		ldw_obj = lpo_tocheck
		if not ldw_obj.of_isupdateable() then
			continue
		end if
//		if ldw_obj.Update(ab_accepttext, ab_resetflag) < 0 Then
         if ldw_obj.event ue_update() < 0 then
			// Visual notification is not displayed by framework.  Left up to the descendent
			// object to process.
			return -1
		end if
	elseif TypeOf (lpo_tocheck) = DataStore! then
		lds_obj = lpo_tocheck
		if not lds_obj.of_isupdateable() then
			continue
		end if
		if lds_obj.Update (ab_accepttext, ab_resetflag) < 0 then
			// Visual notification is not displayed by framework.  Left up to the descendent
			// object to process.
			return -1
		end if
	end if	
next

// All updates were successful.
return 1

end function
on n_rmt_update.create
call super::create
end on

on n_rmt_update.destroy
call super::destroy
end on

