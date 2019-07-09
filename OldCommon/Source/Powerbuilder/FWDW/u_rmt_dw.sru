$PBExportHeader$u_rmt_dw.sru
$PBExportComments$DataWindow class
forward
global type u_rmt_dw from datawindow
end type
end forward

global type u_rmt_dw from datawindow
integer width = 1445
integer height = 760
integer taborder = 10
boolean vscrollbar = true
boolean livescroll = true
borderstyle borderstyle = stylelowered!
event type long ue_oleretrieve ( str_dwargs astr_args[] )
event type boolean ue_validate ( )
event ue_lbuttondown pbm_lbuttondown
event ue_lbuttonup pbm_lbuttonup
event ue_cut pbm_cut
event ue_copy pbm_copy
event ue_clear pbm_clear
event ue_paste pbm_paste
event type integer ue_deleterow ( )
event type integer ue_predeleterow ( )
event type integer ue_insertrow ( )
event type integer ue_preinsertrow ( )
event type integer ue_populatedddw ( string as_colname,  datawindowchild adwc_obj )
event type integer ue_print ( )
event type integer ue_printdialog ( str_printdlgattrib astr_printdlg )
event ue_rbuttondown pbm_rbuttondown
event type integer ue_retrievedddw ( )
event type integer ue_preretrieve ( )
event type integer ue_getserverdata ( string as_data,  string as_args )
event rbuttonup pbm_dwnrbuttonup
event ue_init ( )
event ue_additionalcriteria ( ref str_dwargs astr_args[] )
event type integer ue_getcriteria ( str_dwparms astr_args )
event type integer ue_retrieve ( )
event type integer ue_insert ( integer ai_row )
event type integer ue_retrievealldddw ( string as_colname,  datawindowchild adwc_obj )
event type integer ue_setrequiredcolumns ( )
event ue_enablecontrols ( )
event type boolean ue_checksecurity ( )
event type integer ue_preupdate ( )
event type integer ue_update ( )
event type integer ue_postupdate ( )
end type
global u_rmt_dw u_rmt_dw

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
  
  w_rmt_ancestor                       iw_parent
  n_rmt_tr                                    itr_object
  n_rmt_dwsrv_rowmanager       inv_rowmanager
  n_rmt_dwsrv_rowselection       inv_rowselect
  n_rmt_dwsrv_sort                     inv_sort
  n_rmt_dwsrv_resize                  inv_resize
  n_rmt_utility                               inv_utility
  n_rmt_dwsrv                             inv_base
  n_rmt_sql                                  inv_sql

  protected:
    boolean	               ib_rmbfocuschange = true
    boolean                ib_isupdateable = TRUE
    any                       idw_arg[20]
  

end variables

forward prototypes
public function integer of_setrowmanager (boolean ab_switch)
public function integer of_setrowselect (boolean ab_switch)
public function integer of_setresize (boolean ab_switch)
public function integer of_setsort (boolean ab_switch)
public function boolean of_validate ()
public function integer of_settransobject (n_rmt_tr atr_object)
public function integer of_setbase (boolean ab_switch)
public function boolean of_isupdateable ()
public function integer of_setupdateable (boolean ab_switch)
public function boolean of_isroot ()
public function integer of_setlinkage (boolean ab_switch)
public function long of_retrieve (str_dwargs astr_args[])
public function integer of_retrieve ()
public subroutine of_setargument (integer ai_ndx, any any_value)
public function integer of_insert (integer ai_row)
end prototypes

event ue_oleretrieve;////////////////////////////////////////////////////////////////////////////////
////
////	Event:  ue_retrieve
////
////	Arguments:  none
////
////	Returns:  none
////
////	Description:  This event packages the retrieval arguments (if any) using
////               istself, the sql filename, the sql query statement, and the 
////               selection criteria, and makes a call to n_olecontroller's
////               of_getdata() function to populate any DataWindow.
////
////////////////////////////////////////////////////////////////////////////////
////	
////	Revision History
////
////	Version
////	6.0   Initial version
////
////////////////////////////////////////////////////////////////////////////////
//
long   ll_result
//str_retrieveparms lstr_parms
//
//
//this.event ue_preretrieve()
//this.settransobject(sqlca)     //Set unconnected trans object so that the datawindow dbnames can be accessed.
//this.is_sql = this.describe("datawindow.table.select")
//
//lstr_parms.object = this
//this.event ue_AdditionalCriteria(astr_args)
//lstr_parms.criteria = this.event ue_getcriteria(astr_args)
//
//lstr_parms.sql = this.is_sql 
//if ib_isupdateable then
//   lstr_parms.updateable = 1
//else
//   lstr_parms.updateable = 0
//end if
//
//ll_result = gnv_app.inv_client.of_getdata(lstr_parms)
//
return ll_result
end event

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
//	Description:	Perform validation logic by checking for required columns
//
// Note:	Specific Validation logic should be coded in descendant ue_validation event.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
string   ls_message
boolean  lb_result

lb_result = this.inv_base.of_CheckRequiredColumns(ls_message)
if not lb_result then
	gnv_app.inv_msg.of_message(ls_message, exclamation!)
end if

return lb_result

end event

event ue_lbuttondown;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  lbuttondown
//
//	Description:  Send lbuttondown notification to services
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if IsValid (inv_rowselect) then
	inv_rowselect.event ue_lbuttondown (flags, xpos, ypos)
end if
end event

event ue_lbuttonup;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  lbuttonup
//
//	Description:  Send lbuttonup notification to services
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if IsValid (inv_rowselect) then
	inv_rowselect.event ue_lbuttonup (flags, xpos, ypos)
end if
end event

event ue_cut;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_cut
//
//	Arguments:  none
//
//	Returns:  integer
//	Return value from the PowerScript Cut function
//
//	Description:	 Cut the text to the clipboard.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return this.Cut()
end event

event ue_copy;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_copy
//
//	Arguments:  none
//
//	Returns:  integer
//	Return value from the PowerScript Copy function
//
//	Description:  Copy the text to the clipboard.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return this.Copy()
end event

event ue_clear;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_clear
//
//	Arguments:  None
//
//	Returns:  integer
//				Return value from PowerScript Clear() function
//
//	Description:  Clear Text (no clipboard) functionality
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

Return this.Clear()
end event

event ue_paste;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_paste
//
//	Arguments:  none
//
//	Returns:  integer
//	Return value from the Powerscript Paste function
//
//	Description:  Paste the text from the clipboard.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return this.Paste()
end event

event ue_deleterow;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_deleterow
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//  0 = Row not deleted
//	-1 = error
//
//	Description:
//	Deletes the current or selected row(s)
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0 	Enhanced with PreDelete process.
//
//////////////////////////////////////////////////////////////////////////////

integer	li_rc
long		ll_row

// Perform Pre Delete process.
If this.Event ue_predeleterow() <= PREVENT_ACTION Then
	Return NO_ACTION
End If

// Delete row.
if IsValid (inv_rowmanager) then
	li_rc = inv_rowmanager.event ue_deleterow () 
else	
	li_rc = this.DeleteRow (0) 
end if

if li_rc > 0 Then 
	ll_row = 0 
else 
	ll_row = -1
end if


return li_rc

end event

event ue_predeleterow;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_predeleterow
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = Continue with delete
//  0 = Prevent the actual delete.
//	-1 = error
//
//	Description:
//	Notification of a pending delete operation.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

Return CONTINUE_ACTION
end event

event ue_insertrow;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_insertrow
//
//	Arguments:  none
//
//	Returns:  long
//	number of the new row that was inserted
//	 0 = No row was added.
//	-1 = error
//
//	Description:
//	Inserts a new row into the DataWindow before the current row
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0	Enhanced with Pre Insert funcitonality.
//
//////////////////////////////////////////////////////////////////////////////

long	ll_currow
long	ll_rc

// Allow for pre functionality.
If this.Event ue_preinsertrow() <= PREVENT_ACTION Then
	Return NO_ACTION
End If

// Get current row
ll_currow = this.GetRow()
if ll_currow < 0 then ll_currow = 0


// Insert row.
if IsValid (inv_rowmanager) then
	ll_rc = inv_rowmanager.event ue_insertrow (ll_currow)
else
	ll_rc = this.InsertRow (ll_currow) 
end if



return ll_rc
end event

event ue_preinsertrow;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preinsertrow
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 if it OK to insert the row
//	 0 = prevent the row from being added.
//	-1 = error
//
//	Description:
//	Determines if it is OK to insert a new row.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


Return CONTINUE_ACTION
end event

event ue_populatedddw;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  pfc_populatedddw
//
//	Arguments:
//	as_colname		column name of the DDDW to populate
//	adwc_obj		DataWindowChild reference of the DDDW column
//
//	Returns:  long
//	Indicates success/failure, or number of rows populated.
//
//	Description:  
//	Populate the passed-in DropDownDataWindow.
//
//	This script for this event should be in descendant DataWindows.
//	The DropDownDataWindow can be populated in any manner, including
//	using the DataWindow caching service, external data, or retrieving.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version - Replaces obsoleted event pfc_retrievedddw.
//
//////////////////////////////////////////////////////////////////////////////

return NO_ACTION
end event

event ue_print;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_print
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Opens the print dialog to allow user to change print settings,
//	and then prints the DataWindow.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//	5.0.01   Modified script to avoid 64K segment problem with 16bit machine code executables
//
//////////////////////////////////////////////////////////////////////////////

boolean	lb_rowselection
integer	li_rc
long		ll_selected[]
long		ll_selectedcount
long		ll_cnt
string		ls_val
datastore				lds_selection
str_printdlgattrib		lstr_printdlg

li_rc = this.event ue_printdialog (lstr_printdlg)
if li_rc < 0 then
	return li_rc
end if

// Print selection
if this.object.datawindow.print.page.range = "selection" then
	// Get selected count
	lb_rowselection = IsValid (inv_rowselect)
	if not lb_rowselection then
		of_SetRowSelect (true)
	end if
	ll_selectedcount = inv_rowselect.of_SelectedCount (ll_selected)
	if not lb_rowselection then
		of_SetRowSelect (false)
	end if	

	if ll_selectedcount > 0 then
		// Create a datastore to print selected rows
		lds_selection = create datastore
		lds_selection.dataobject = this.dataobject

		// First discard any data in the dataobject
		lds_selection.Reset()

		// Copy selected rows
		for ll_cnt = 1 to ll_selectedcount
			if this.RowsCopy (ll_selected[ll_cnt], ll_selected[ll_cnt], primary!, lds_selection, 2147483647, primary!) < 0 then
				return -1
			end if
		next

		// Capture print properties of original DW
		// (Note:  this syntax uses Describe/Modify PS functions to avoid 64K segment limit)
		ls_val = this.Describe ("datawindow.print.collate")
		lds_selection.Modify ("datawindow.print.collate = " + ls_val)

		ls_val = this.Describe ("datawindow.print.color")
		lds_selection.Modify ("datawindow.print.color = " + ls_val)

		ls_val = this.Describe ("datawindow.print.columns")
		lds_selection.Modify ("datawindow.print.columns = " + ls_val)

		ls_val = this.Describe ("datawindow.print.columns.width")
		lds_selection.Modify ("datawindow.print.columns.width = " + ls_val)

		ls_val = this.Describe ("datawindow.print.copies")
		lds_selection.Modify ("datawindow.print.copies = " + ls_val)

		ls_val = this.Describe ("datawindow.print.documentname")
		lds_selection.Modify ("datawindow.print.documentname = " + ls_val)

		ls_val = this.Describe ("datawindow.print.duplex")
		lds_selection.Modify ("datawindow.print.duplex = " + ls_val)

		ls_val = this.Describe ("datawindow.print.filename")
		lds_selection.Modify ("datawindow.print.filename = " + ls_val)

		ls_val = this.Describe ("datawindow.print.margin.bottom")
		lds_selection.Modify ("datawindow.print.margin.bottom = " + ls_val)

		ls_val = this.Describe ("datawindow.print.margin.left")
		lds_selection.Modify ("datawindow.print.margin.left = " + ls_val)

		ls_val = this.Describe ("datawindow.print.margin.right")
		lds_selection.Modify ("datawindow.print.margin.right = " + ls_val)

		ls_val = this.Describe ("datawindow.print.margin.top")
		lds_selection.Modify ("datawindow.print.margin.top = " + ls_val)

		ls_val = this.Describe ("datawindow.print.orientation")
		lds_selection.Modify ("datawindow.print.orientation = " + ls_val)

		ls_val = this.Describe ("datawindow.print.page.range")
		lds_selection.Modify ("datawindow.print.page.range = " + ls_val)

		ls_val = this.Describe ("datawindow.print.page.rangeinclude")
		lds_selection.Modify ("datawindow.print.page.rangeinclude = " + ls_val)

		ls_val = this.Describe ("datawindow.print.paper.size")
		lds_selection.Modify ("datawindow.print.paper.size = " + ls_val)

		ls_val = this.Describe ("datawindow.print.paper.source")
		lds_selection.Modify ("datawindow.print.paper.source = " + ls_val)

		ls_val = this.Describe ("datawindow.print.prompt")
		lds_selection.Modify ("datawindow.print.prompt = " + ls_val)

		ls_val = this.Describe ("datawindow.print.quality")
		lds_selection.Modify ("datawindow.print.quality = " + ls_val)

		ls_val = this.Describe ("datawindow.print.scale")
		lds_selection.Modify ("datawindow.print.scale = " + ls_val)
	end if
end if

// Print
if IsValid (lds_selection) then
	li_rc = lds_selection.Print (true)
	destroy lds_selection
else
	li_rc = this.Print (true)
end if

this.object.datawindow.print.filename = ""
this.object.datawindow.print.page.range = ""

return li_rc
end event

event ue_printdialog;////////////////////////////////////////////////////////////////////////////////
////
////	Event:  ue_printdialog
////
////	Arguments:
////	astr_printdlg:  print dialog structure by ref
////
////	Returns:  integer
////	 1 = success
////	-1 = error
////
////	Description:  
////	Opens the print dialog for this DataWindow, 
////	and sets the print values the user selected for the DW.
////
////////////////////////////////////////////////////////////////////////////////
//
//boolean			lb_collate
//integer			li_copies
long				ll_rc
//long				ll_pagecount
//string				ls_pagecount
//string				ls_pathname = "Output"
//string				ls_filename
//string				ls_copies
//string				ls_collate
////n_cst_platform		lnv_platform
//n_rmt_conversion	lnv_conversion
//window				lw_parent
//
//// Initialize printdlg structure with current print values of DW
//astr_printdlg.b_allpages = true
//
//ls_copies = this.object.datawindow.print.copies
//if not IsNumber (ls_copies) then
//	ls_copies = "1"
//end if
//li_copies = Integer (ls_copies)
//astr_printdlg.l_copies = li_copies
//
//ls_collate = this.object.datawindow.print.collate
//lb_collate = lnv_conversion.of_Boolean (ls_collate)
//astr_printdlg.b_collate = lb_collate
//
//astr_printdlg.l_frompage = 1
//astr_printdlg.l_minpage = 1
//
//ls_pagecount = this.Describe ("evaluate ('PageCount()', 1)")
//if IsNumber (ls_pagecount) then
//	ll_pagecount = Long (ls_pagecount)
//	astr_printdlg.l_maxpage = ll_pagecount
//	astr_printdlg.l_topage = ll_pagecount
//end if
//
//if this.GetSelectedRow (0) = 0 then
//	astr_printdlg.b_disableselection = true
//end if
//
//// Allow printdlg structure to have additional values
//// set before opening print dialog
//this.event pfc_preprintdlg (astr_printdlg)
//
//// Open print dialog
////f_setplatform (lnv_platform, true)
//this.of_GetParentWindow (lw_parent)
////ll_rc = lnv_platform.of_PrintDlg (astr_printdlg, lw_parent)
////f_setplatform (lnv_platform, false)
//
//// Set print values of DW based on users selection
//if ll_rc > 0 then
//	// Page Range
//	if astr_printdlg.b_allpages then
//		this.object.datawindow.print.page.range = ""
//	elseif astr_printdlg.b_pagenums then
//		this.object.datawindow.print.page.range = &
//			String (astr_printdlg.l_frompage) + "-" + String (astr_printdlg.l_topage)
//	elseif astr_printdlg.b_selection then
//		this.object.datawindow.print.page.range = "selection"
//	end if
//
//	// Collate copies
//	this.object.datawindow.print.collate = astr_printdlg.b_collate
//
//	// Number of copies
//	this.object.datawindow.print.copies = astr_printdlg.l_copies
//
//	// Print to file (must prompt user for filename first)
//	if astr_printdlg.b_printtofile then
//		if GetFileSaveName ("Print to File", ls_pathname, ls_filename, "prn", &
//			"Printer Files,*.prn,All Files,*.*") <= 0 then
//			return -1
//		else
//			this.object.datawindow.print.filename = ls_pathname
//		end if
//	end if
//end if
//
return ll_rc
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
//	Description:  This event should be used in descendants to
//	              handle pre retrieve logic.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


string   ls_presentation

ls_presentation = string(this.Object.DataWindow.Processing)
if ls_presentation = "0" or ls_presentation = "1" then
	this.of_setSort(true)
	this.inv_sort.of_setcolumnheader(true)
end if

return 1
end event

event ue_getserverdata;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_getserverdata
//
//	Arguments:  string - tab delimited, string args
//
//	Returns:  long
//	Can be used with Powerscript Retrieve function, to indicate
//	   success/failure, or number of rows retrieved.
//
//	Description:  This event should be used retrieve the data from a remote server
//
//////////////////////////////////////////////////////////////////////////////


return 0
end event

event ue_init;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_init
//
//	Description:  Initializes iw_parent to the the window which it is contained.
//	              Perform additional initialization at the descendent level
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version - 
//
//////////////////////////////////////////////////////////////////////////////

// Get parent window of this listview object
iw_parent = inv_utility.of_getParentWindow(this)

//this.of_SetResize(true)
//inv_resize.of_register(this.object, "Scale")

end event

event ue_additionalcriteria;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_AdditionalCriteria
//
//	Arguments:  str_args[]
//
//	Returns:  none
//
//	Description:  Specific logic should be coded at the descendent level to 
//               setup the istr_args[], which belongs to the parent window, 
//               with SQL selection criteria.    
//               After this event is executed, ue_retrieve will use istr_args[] 
//               to build the selection criteria into a string to be 
//               passed to the application server.
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
boolean   lb


//  Setup selection criteria into a string
ls_criteria = ""
//li_total = upperbound(astr_args)
lb = astr_args.usewhereclause
//for li_ndx = 1 to li_total
//	if isnull(astr_args[li_ndx].pbcol) or isnull(astr_args[li_ndx].value) &
//	or len(astr_args[li_ndx].pbcol) <= 0 or len(astr_args[li_ndx].value) <= 0 then
//		continue
//	end if
//	
//	// Get corresponding database column name
//	ls_dbname = this.describe(astr_args[li_ndx].pbcol + ".dbname")
//	if isnull(ls_dbname) or len(ls_dbname) = 0 then
//		continue   // column was not found in datawindow
//	end if
//	
//	// Concatenate the logical operatot, "AND"
//	if not isnull(ls_criteria) and len(ls_criteria) > 0 then
//		ls_criteria += "AND" + space(1)
//	end if
//	
//	// Construct the criteria
//	ls_criteria += ls_dbname + space(1)
//	if isnull(astr_args[li_ndx].operator) or len(astr_args[li_ndx].operator) <= 0 then
//		astr_args[li_ndx].operator = "="
//	end if
//	ls_criteria += astr_args[li_ndx].operator + space(1) + astr_args[li_ndx].value + space(1)
//next

return 1


end event

event ue_retrieve;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_retrieve
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  This event calls of_retreive() to get the contents of this
//               datawindow from its source.
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



//this.event ue_retrievedddw()
li_rows = this.retrieve(idw_arg[1], idw_arg[2], idw_arg[3], idw_arg[4], idw_arg[5], &
                        idw_arg[6], idw_arg[7], idw_arg[8], idw_arg[9], idw_arg[10], & 
            	   	   idw_arg[11], idw_arg[12], idw_arg[13], idw_arg[14], idw_arg[15], &
				            idw_arg[16], idw_arg[17], idw_arg[18], idw_arg[19], idw_arg[20])

return li_rows
end event

event ue_insert;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_insert
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  This event is called from of_insert(). 
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_row



//this.event ue_retrievedddw()
li_row = this.insertrow(ai_row)

return li_row
end event

event ue_retrievealldddw;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_RetrieveALLDDDW
//
//	Arguments:  String as_colname - datawindow column name that is to be associated
//                                 with a dropdown datawindow
//             DatawindowChild  adwc_obj - The actual datawindow that will serve as
//                                         the dropdown datawindow.
//
//	Returns:  integer
//	Can be used with Powerscript Retrieve function, to indicate
//	   success/failure, or number of rows retrieved.
//
//	Description:  This event should be used in descendants to
//	              populate any DropDownDataWindows on the DataWindow.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return 1
end event

event ue_setrequiredcolumns;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_setRequiredColumns
//
//	Description:  This event is triggered so that required columns can be 
//               identified.   Typically this is done using the method,
//               of_setRequiredColumns of the object, inv_base.   This logic
//               should be coded at the descendent.
//	
// Returns:  integer - 1 = successful,  -1 = failure
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version - 
//
//////////////////////////////////////////////////////////////////////////////

return 1
end event

event ue_enablecontrols;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_enablecontrols
//
//	Description:  Add code here to enable/disable DataWindow controls
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//////////////////////////////////////////////////////////////////////////////


return
end event

event type boolean ue_checksecurity();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  ue_CheckSecurity
//
//	Access:  public
//
//	Arguments:	none
//
//	Returns:  boolean
//	         true - success
//	         false - failure
//
//	Description:  Code security at descendent
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


return true
end event

event ue_preupdate;////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preupdate
//
//	Arguments:  none
//
//	Returns:  integer
//
//	Description:	Process any pre update requirements
//
// Note:	Specific PRe Update logic should be coded in descendant ue_preupdate event.
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

return SUCCESS
end event
event ue_update;////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_update
//
//	Arguments:  none
//
//	Returns:  integer
//
//	Description:	Apply updates to the database
//
// Note:	Specific Validation logic should be coded in descendant ue_update event.
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

if this.event ue_preupdate() = FAILURE then
	return FAILURE
end if

if this.update (true, false) = FAILURE then
	return FAILURE
end if

if this.event ue_postupdate() = FAILURE then
	return FAILURE
end if

return SUCCESS
end event
event ue_postupdate;////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postupdate
//
//	Arguments:  none
//
//	Returns:  integer
//
//	Description:	Process any post update requirements
//
// Note:	Specific Post Update logic should be coded in descendant ue_preupdate event.
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



return SUCCESS
end event
public function integer of_setrowmanager (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_SetRowManager
//
//	(Arguments: boolean
//   TRUE  - Start (create) the service
//   FALSE - Stop (destroy ) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  Starts or stops the Row Management Services.  This service
//				     facilitates the addition, deletion of rows in a datawindow
//					  and provides an 'last-change' undo.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//Check arguments
If IsNull(ab_switch) Then
	Return FAILURE
End If

IF ab_Switch THEN
	IF IsNull(inv_rowmanager) Or Not IsValid (inv_rowmanager) THEN
		inv_rowmanager = Create n_rmt_dwsrv_rowmanager
		inv_rowmanager.of_SetRequestor ( this )
		Return SUCCESS
	END IF
ELSE 
	IF IsValid (inv_rowmanager) THEN
		Destroy inv_rowmanager
		Return SUCCESS
	END IF	
END IF

Return NO_ACTION
end function

public function integer of_setrowselect (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_SetRowSelect
//
//	(Arguments: boolean
//   TRUE  - Start (create) the service
//   FALSE - Stop (destroy ) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  Starts or stops the Row Selection Services.  This service
//					  provides for single, multi and extended row selection.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//Check arguments
If IsNull(ab_switch) Then
	Return FAILURE
End If

IF ab_Switch THEN
	IF IsNull(inv_RowSelect) Or Not IsValid (inv_RowSelect) THEN
		inv_RowSelect = Create n_rmt_dwsrv_rowselection
		inv_RowSelect.of_SetRequestor ( this )
		Return SUCCESS
	END IF
ELSE 
	IF IsValid (inv_RowSelect) THEN
		Destroy inv_RowSelect
		Return SUCCESS
	END IF	
END IF

Return NO_ACTION
end function

public function integer of_setresize (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_SetResize
//
//	(Arguments: boolean
//   TRUE  - Start (create) the service
//   FALSE - Stop (destroy ) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  Starts or stops the DW Resize Services. 
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
If IsNull(ab_switch) Then
	Return FAILURE
End If

IF ab_Switch THEN
	IF IsNull(inv_resize) Or Not IsValid (inv_resize) THEN
		inv_resize = Create n_rmt_dwsrv_resize
		inv_resize.of_SetRequestor ( this )
		inv_resize.of_SetOrigSize (this.Width, this.Height)
		Return SUCCESS
	END IF
ELSE 
	IF IsValid (inv_resize) THEN
		Destroy inv_resize
		Return SUCCESS
	END IF	
END IF

Return NO_ACTION
end function

public function integer of_setsort (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_SetSort
//
//	(Arguments: boolean
//   TRUE  - Start (create) the service
//   FALSE - Stop (destroy ) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  Starts or stops the Sort Service.  This service
//				     provides several sort dialogs and refinements.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//Check arguments
If IsNull(ab_switch) Then
	Return FAILURE
End If

IF ab_Switch THEN
	IF IsNull(inv_Sort) Or Not IsValid (inv_Sort) THEN
		inv_Sort = Create n_rmt_dwsrv_sort
		inv_Sort.of_SetRequestor ( this )
		Return SUCCESS
	END IF
ELSE 
	IF IsValid (inv_Sort) THEN
		Destroy inv_Sort
		Return SUCCESS
	END IF	
END IF

Return NO_ACTION
end function

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
//	5.0   Initial version
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
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
If IsNull(ab_switch) Then
	Return -1
End If

IF ab_Switch THEN
	IF IsNull(inv_base) Or Not IsValid (inv_base) THEN
		inv_base = Create n_rmt_dwsrv
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

public function boolean of_isroot ();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_IsRoot
//
//	(Arguments: None
//
//	Returns:  Boolean
//		True if a root datawindow (any dw not having a master dw).
//		False if not a root datawindow.
//
//	Description:  
//	Determine if the datawindow is a root datawindow.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//If IsValid(inv_linkage) Then
//	Return inv_linkage.of_isRoot()
//End If

Return True
end function

public function integer of_setlinkage (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_SetLinkage
//
//	(Arguments: boolean
//   TRUE  - Start (create) the service
//   FALSE - Stop (destroy ) the service
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  Starts or stops the DataWindow Linkage Services.  This service
//				     allows for multiple levels of master/detail style datawindows.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//Check arguments
If IsNull(ab_switch) Then
	Return FAILURE
End If

//IF ab_Switch THEN
//	IF IsNull(inv_linkage) Or Not IsValid (inv_linkage) THEN
//		inv_linkage = Create n_cst_dwsrv_linkage
//		inv_linkage.of_SetRequestor ( this )
//		Return SUCCESS
//	END IF
//ELSE 
//	IF IsValid (inv_linkage) THEN
//		Destroy inv_linkage
//		Return SUCCESS
//	END IF	
//END IF

Return NO_ACTION
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


Long	ll_rc


//ll_rc = this.Retrieve(astr_args)

return ll_rc

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

this.event ue_preretrieve()
this.event ue_retrievedddw()
li_result = this.event ue_retrieve()

                          
return li_result								  
end function

public subroutine of_setargument (integer ai_ndx, any any_value);

this.idw_arg[ai_ndx] = any_value
end subroutine

public function integer of_insert (integer ai_row);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_insert()
//
//	Arguments:  none
//
//	Returns:  row number inserted
//
//	Description:  This function inserts a row into the current datawindow
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


int   li_row

this.event ue_retrievedddw()
li_row = this.event ue_insert(ai_row)

                          
return li_row								  
end function

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

this.of_SetRowSelect(false)
this.of_SetRowManager(false)
this.of_SetSort(false)
this.of_SetResize(false)
this.of_SetBase(false)
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

// Notify the resize service that the object size has changed.
If IsValid (inv_resize) Then
	inv_resize.Event ue_Resize (sizetype, This.Width, This.Height)
End If
end event

event clicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  Clicked
//
//	Description:  DataWindow clicked
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//////////////////////////////////////////////////////////////////////////////

integer li_rc

// Check arguments
IF IsNull(xpos) or IsNull(ypos) or IsNull(row) or IsNull(dwo) THEN
	Return
END IF

IF IsValid (inv_RowSelect) THEN
	inv_RowSelect.Event ue_clicked ( xpos, ypos, row, dwo )
END IF

IF IsValid (inv_Sort) THEN 
	inv_Sort.Event ue_clicked ( xpos, ypos, row, dwo ) 
END IF 

end event

event itemfocuschanged;//////////////////////////////////////////////////////////////////////////////
//
//	Event: itemfocuschanged
//
//	Description:  Send itemfocuschanged notification to DW services
//	If appropriate, display the microhelp stored in the tag value of the current column.
//
//	Note:  The tag value of a column can contain just the microhelp, or may 
//	contain other information as well. 
//	The format follows: MICROHELP=<microhelp to be displayed>. 
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//	6.0	Added notification to the Linkage Service.
//
//////////////////////////////////////////////////////////////////////////////


string			ls_microhelp
window			lw_parent
n_rmt_string 	lnv_string

If IsNull(dwo) Or Not IsValid (dwo) Then
	Return
End If

//Check for microhelp requirements.
If gnv_app.of_GetMicrohelp() Then
	// Check the tag for any "microhelp" information.
	ls_microhelp = lnv_string.of_GetKeyValue (dwo.tag, "microhelp", ";")
	if IsNull (ls_microhelp) or Len(Trim(ls_microhelp)) = 0 then
		ls_microhelp = ''	
	end if

	//Notify the window.
	lw_parent = inv_utility.of_GetParentWindow(this)
	If IsValid(lw_parent) Then
		// Dynamic call to the parent window.
		lw_parent.Dynamic Event ue_microHelp (ls_microhelp)
	End If	
End If


end event

event rowfocuschanged;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  rowfocuschanged
//
//	Description:  Send rowfocuschanged notification to services
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0	Added RowSelect service notification.
//
//////////////////////////////////////////////////////////////////////////////

If IsValid( inv_rowselect ) Then
	inv_rowselect.Event ue_RowFocusChanged (currentrow) 
End If

end event

event rbuttondown;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  rbuttondown
//
//	Description:  Allow for focus change on rbuttondown
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0 	Added Linkage service notification.
//
//////////////////////////////////////////////////////////////////////////////

integer	li_rc
long		ll_currow
string	ls_colname
string	ls_curcolname

// Validate arguments.
if not ib_rmbfocuschange or IsNull (dwo) or row <= 0 then
	return
end if

if IsValid (inv_rowselect) then
	inv_rowselect.event ue_rbuttondown (xpos, ypos, row, dwo)
end if

if dwo.type <> "column" then
	return
end if

// Perform no action if already over current row/column.
ls_colname = dwo.name
ls_curcolname = this.GetColumnName()
ll_currow = this.GetRow()
if (ls_colname = ls_curcolname) and (row = ll_currow) then
	return
end if

// Set row & column.
if this.SetRow (row) = 1 then
	this.SetColumn (ls_colname)
end if

parent.triggerevent("ue_enablecontrols")
end event

event getfocus;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			getfocus
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Notify the parent window that this control got focus.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

window 	lw_parent

lw_parent = inv_utility.of_GetParentWindow(this)
If IsValid(lw_parent) Then
	// Dynamic call to the parent window.
	lw_parent.dynamic event ue_SetActiveControl(this)
End If

end event

event constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  constructor
//
//	Description:
//	
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version - 
//
//////////////////////////////////////////////////////////////////////////////

this.of_setBase(true)
this.post event ue_init()

if isValid(inv_base) and not isNull(inv_base) then
	this.event ue_setRequiredColumns()
end if
	

end event

on u_rmt_dw.create
end on

on u_rmt_dw.destroy
end on

