$PBExportHeader$n_rmt_ds.sru
forward
global type n_rmt_ds from datastore
end type
end forward

global type n_rmt_ds from datastore
event type boolean ue_validate ( )
event type long ue_oleretrieve ( str_dwargs astr_args[] )
event type integer ue_retrievedddw ( )
event type integer ue_preretrieve ( )
event type string ue_getcriteria ( str_dwargs astr_args[] )
event type integer ue_retrieve ( )
end type
global n_rmt_ds n_rmt_ds

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

  n_rmt_tr			itr_object
  n_rmt_dssrv                           inv_base
  string                                      is_sql
  long                                       il_handle
    any                                      idw_arg[20]
 
protected:
    boolean           ib_isupdateable = TRUE
    string               is_null
end variables

forward prototypes
public function boolean of_validate ()
public function integer of_settransobject (n_rmt_tr atr_object)
public function integer of_setbase (boolean ab_switch)
public function boolean of_isupdateable ()
public function integer of_setupdateable (boolean ab_switch)
public function long of_retrieve (str_dwargs astr_args[])
public function integer of_retrieve ()
end prototypes

event ue_validate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:
//	ue_Validate
//
//	Arguments:  none
//
//	Returns:  boolean
//	 true = All validation passed
//	 false = validation failed
//
//	Description:
//	Perform validation logic.
//
// Note:
//	Specific Validation logic should be coded in descendant pfc_validation event.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
boolean   lb_valid


// code validation logic here!

return lb_valid
end event

event ue_oleretrieve;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_retrieve
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  This event packages the retrieval arguments (if any) using
//               istself, the sql filename, the sql query statement, and the 
//               selection criteria, and makes a call to n_olecontroller's
//               of_getdata() function to populate any DataStore.
// 
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

long   ll_result
str_retrieveparms lstr_parms


this.event ue_preretrieve()
lstr_parms.object = this
lstr_parms.criteria = this.event ue_getcriteria(astr_args)
lstr_parms.sql = this.is_sql 
if this.ib_isupdateable then
   lstr_parms.updateable = 1
else
   lstr_parms.updateable = 0
end if

ll_result = gnv_app.inv_client.of_getdata(lstr_parms)

return ll_result
end event

event ue_retrievedddw;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_retrievedddw
//
//	Arguments:  none
//
//	Returns:  long
//	Can be used with Powerscript Retrieve function, to indicate
//	   success/failure, or number of rows retrieved.
//
//	Description:  This event should be used in descendants to
//	   populate any DropDownDataWindows on the DataWindow.
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

event ue_preretrieve;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preretrieve
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  Code pre-retrieve logic here.
// 
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
this.settransobject(sqlca)
this.is_sql = this.describe("datawindow.table.select")

return 1
end event

event ue_getcriteria;//////////////////////////////////////////////////////////////////////////////
//
//	EVent:   		ue_GetCriteria
//
//	Access:  		public
//
//	Arguments:     astr_args[]
//
//	Returns:  		String
//						
//						
//	Description:  Concatenates the elements of astr_args for each index into one 
//               string of criteria.    The string is returned to the caller.
//               If there no elements exist then return a null string.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
string    ls_criteria
string    ls_dbname
int       li_total
int       li_ndx


//  Setup selection criteria into a string
ls_criteria = ""
li_total = upperbound(astr_args)
for li_ndx = 1 to li_total
	if isnull(astr_args[li_ndx].pbcol) or isnull(astr_args[li_ndx].value) &
	or len(astr_args[li_ndx].pbcol) <= 0 or len(astr_args[li_ndx].value) <= 0 then
		continue
	end if
	
	// Get corresponding database column name
	ls_dbname = this.describe(astr_args[li_ndx].pbcol + ".dbname")
	if isnull(ls_dbname) or len(ls_dbname) = 0 then
		continue   // column was not found in datastore
	end if
	
	// Concatenate the logical operatot, "AND"
	if not isnull(ls_criteria) and len(ls_criteria) > 0 then
		ls_criteria += "AND" + space(1)
	end if
	
	// Construct the criteria
	ls_criteria += ls_dbname + space(1)
	if isnull(astr_args[li_ndx].operator) or len(astr_args[li_ndx].operator) <= 0 then
		astr_args[li_ndx].operator = "="
	end if
	ls_criteria += astr_args[li_ndx].operator + space(1) + astr_args[li_ndx].value + space(1)
next

return ls_criteria


end event

event ue_retrieve;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_retrieve
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  This event retrieves the datastore.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_rows


li_rows = this.retrieve(idw_arg[1], idw_arg[2], idw_arg[3], idw_arg[4], idw_arg[5], &
                        idw_arg[6], idw_arg[7], idw_arg[8], idw_arg[9], idw_arg[10], & 
            				idw_arg[11], idw_arg[12], idw_arg[13], idw_arg[14], idw_arg[15], &
				            idw_arg[16], idw_arg[17], idw_arg[18], idw_arg[19], idw_arg[20])

return li_rows
end event

public function boolean of_validate ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:
//	of_Validate
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = All validation passed
//	-1 = validation failed
//
//	Description:
//	Perform validation logic.
//
// Note:
//	Specific Validation logic should be coded in descendant pfc_validation event.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


boolean	lb_rc

lb_rc = this.Event ue_Validate()

return lb_rc

end function

public function integer of_settransobject (n_rmt_tr atr_object);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetTransObject
//
//	Access:  public
//
//	Arguments:
//	atr_object:  transaction object to set for the datawindow
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Sets the transaction object that the datawindow will use
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
if IsNull (atr_object) or not IsValid (atr_object) then
	return FAILURE
end if

// Set the transaction object
li_rc = this.SetTransObject (atr_object)
if li_rc = 1 then
	itr_object = atr_object
end if

return li_rc
end function

public function integer of_setbase (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetBase
//
//	Access:  public
//
//	Arguments:
//	ab_switch:  whether an instance of the base datastore service is enabled/disabled
//
//	Returns:  integer
//	 1 = success
//	 0 = no action taken
//	-1 = error
//
//	Description:
//	Enables/disables an instance of the base datastore service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//	5.0.04   Return 0 if no action is taken
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
If IsNull(ab_switch) Then
	Return -1
End If

IF ab_Switch THEN
	IF IsNull(inv_base) Or Not IsValid (inv_base) THEN
		inv_base = Create n_rmt_dssrv
		inv_base.of_SetRequestor (this)
		Return 1
	END IF
ELSE 
	IF IsValid (inv_base) THEN
		Destroy inv_base
		Return 1
	END IF	
END IF

Return 0
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

public function long of_retrieve (str_dwargs astr_args[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function: of_Retrieve
//
// Access:	Public
//
//	Arguments: none
//
//	Returns:  long
//	The return code from the Retrieve Powerscript function
//	 0 = No rows returned from successful retrieve
//	-1 = Retrieve was unsuccessful
//	>1 = Success.  Total number of rows retrieved
//
//	Description:  Begins the datawindow retrieval process.
// Note:
//	Specific retrieve logic should be coded in descendant ue_Retrieve event.
//
//////////////////////////////////////////////////////////////////////////////


Long	ll_result

//ll_result = this.event ue_Retrieve(astr_args)

return ll_result

end function

public function integer of_retrieve ();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_retrieve()
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  This function retrieves the contents of the current datawindow
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

li_result = this.event ue_retrievedddw()
li_result = this.event ue_retrieve()

return li_result								  
end function

on n_rmt_ds.create
call datastore::create
TriggerEvent( this, "constructor" )
end on

on n_rmt_ds.destroy
call datastore::destroy
TriggerEvent( this, "destructor" )
end on

event destructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  destructor
//
//	(Arguments: none
//
//	Returns:  		Long
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  deactivate services, deallocate any variables, and any other 
//               cleanup activities.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

this.of_SetBase(false)
end event

event constructor;
//////////////////////////////////////////////////////////////////////////////
//
//	Event:  constructor
//
//	(Arguments: none
//
//	Returns:  		Long
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  activate services, allocate any variables, and any other 
//               initialization activities.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
this.of_SetBase(true)

end event

