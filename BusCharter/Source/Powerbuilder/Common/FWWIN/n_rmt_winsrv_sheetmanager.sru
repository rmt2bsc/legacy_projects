$PBExportHeader$n_rmt_winsrv_sheetmanager.sru
$PBExportComments$Window  Sheet Manager service
forward
global type n_rmt_winsrv_sheetmanager from n_rmt_winsrv
end type
end forward

global type n_rmt_winsrv_sheetmanager from n_rmt_winsrv
event type integer ue_cascade ( )
event type integer ue_tilehorizontal ( )
event type integer ue_tilevertical ( )
event type integer ue_layer ( )
event type integer ue_minimizeall ( )
event type integer ue_undoarrange ( )
end type
global n_rmt_winsrv_sheetmanager n_rmt_winsrv_sheetmanager

type variables
Protected:
arrangetypes	ie_arrange
str_sheetinfo            istr_sheet[]
end variables

forward prototypes
public function integer of_getsheetcount ()
public function integer of_getsheets (ref window aw_sheet[])
public function integer of_getsheetsbyclass (ref window aw_sheet[], string as_classname)
public function integer of_getsheetsbytitle (ref window aw_sheet[], string as_title, boolean ab_partialmatch)
public function integer of_getsheetsbytitle (ref window aw_sheet[], string as_title)
protected function integer of_setcurrentstate (arrangetypes ae_arrange)
public function integer of_setrequestor (w_rmt_ancestor aw_requestor)
public function arrangetypes of_getcurrentstate ()
public function integer of_closeactivesheet ()
end prototypes

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
//	Description:
//	Cascades sheets
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

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Set current state for undo
this.of_SetCurrentState(cascade!)
li_result = iw_requestor.ArrangeSheets(cascade!)

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
//	Description:
//	Tiles sheets horizontally
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

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Set current state for undo
this.of_SetCurrentState(tilehorizontal!)
li_result = iw_requestor.ArrangeSheets(tilehorizontal!)

return li_result
end event

event ue_tilevertical;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_tilevertical
//
//	Arguments:  none
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Tiles sheets vertically
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

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Set current state for undo
this.of_SetCurrentState(tile!)
li_result = iw_requestor.ArrangeSheets(tile!)

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
//	Description:
//	Layers sheets
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

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

this.of_SetCurrentState(layer!)
li_result = iw_requestor.ArrangeSheets(layer!)

return li_result
end event

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

integer	li_sheetcount
integer	li_cnt
window	lw_sheet[]

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Get all sheets
li_sheetcount = this.of_GetSheets(lw_sheet)
if li_sheetcount > 0 then
	// Save current state
	this.of_SetCurrentState(icons!)
	
	for li_cnt = 1 to li_sheetcount
		lw_sheet[li_cnt].windowstate = minimized!
	next
end if

return li_sheetcount

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

integer	li_ndx
integer	li_totalsheets

li_totalsheets = UpperBound (istr_sheet)
for li_ndx = li_totalsheets to 1 step -1
		if IsValid (istr_sheet[li_ndx].w_obj) then
			istr_sheet[li_ndx].w_obj.windowstate = istr_sheet[li_ndx].e_state
			istr_sheet[li_ndx].w_obj.width = istr_sheet[li_ndx].i_width
			istr_sheet[li_ndx].w_obj.height = istr_sheet[li_ndx].i_height
			istr_sheet[li_ndx].w_obj.x = istr_sheet[li_ndx].i_x
			istr_sheet[li_ndx].w_obj.y = istr_sheet[li_ndx].i_y
		end if
next

SetNull (ie_arrange)

return li_totalsheets
end event

public function integer of_getsheetcount ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_Getsheetcount
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  integer
//	number of open sheets
//	-1 = error
//
//	Description:  Returns sheet count for frame requestor
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
integer	li_counter
window	lw_sheet

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Get number of sheets
lw_sheet = iw_requestor.GetFirstSheet ()
if IsValid (lw_sheet) then
	do
		li_counter++
		lw_sheet = iw_requestor.GetNextSheet (lw_sheet)
	loop until IsNull(lw_sheet) Or not IsValid (lw_sheet)
end if

return li_counter


end function

public function integer of_getsheets (ref window aw_sheet[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_Getsheets
//
//	Access:  public
//
//	Arguments:  aw_sheet[] by ref
//
//	Returns:  integer
//	number of open sheets
//	-1 = error
//
//	Description:  Get reference to all open sheets
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_counter
window	lw_sheet

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Get all sheets
lw_sheet = iw_requestor.GetFirstSheet()
if IsValid (lw_sheet) then
	do
		li_counter++
		aw_sheet[li_counter] = lw_sheet
		lw_sheet = iw_requestor.GetNextSheet(lw_sheet)
	loop until IsNull(lw_sheet) Or not IsValid (lw_sheet)
end if

return li_counter



end function

public function integer of_getsheetsbyclass (ref window aw_sheet[], string as_classname);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetSheetsbyClass
//
//	Access:  public
//
//	Arguments:
//	aw_sheet[] by ref
//	as_classname:  classname of the sheets to get
//
//	Returns:  integer
//	number of sheets of classname specified
//	-1 = error
//
//	Description:  Get reference to all open sheets with classname specified
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
integer	li_counter
window	lw_sheet

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Get all sheets of classname
lw_sheet = iw_requestor.GetFirstSheet ()
if IsValid (lw_sheet) then
	do
		if ClassName (lw_sheet) = as_classname then
			li_counter++
			aw_sheet[li_counter] = lw_sheet
		end if
		lw_sheet = iw_requestor.GetNextSheet (lw_sheet)
	loop until IsNull(lw_sheet) Or not IsValid (lw_sheet)
end if

return li_counter

end function

public function integer of_getsheetsbytitle (ref window aw_sheet[], string as_title, boolean ab_partialmatch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetSheetsbyTitle
//
//	Access:  public
//
//	Arguments:
//	aw_sheet[] by ref
//	as_title:  title of the sheets to get
//	ab_partialmatch:  partially match window titles (default is false)
//
//	Returns:  integer
//	number of sheets open with title specified
//	-1 = error
//
//	Description:  Get reference to all open sheets with title specified
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
integer	li_counter
window	lw_sheet

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Get all sheets of title specified
lw_sheet = iw_requestor.GetFirstSheet ()
if IsValid (lw_sheet) then
	do
		if ab_partialmatch = false then
			if lw_sheet.title = as_title then
				li_counter++
				aw_sheet[li_counter] = lw_sheet
			end if
		else
			if Pos (lw_sheet.title, as_title) > 0 then
				li_counter++
				aw_sheet[li_counter] = lw_sheet
			end if
		end if
	
		lw_sheet = iw_requestor.GetNextSheet (lw_sheet)
	loop until IsNull(lw_sheet) Or not IsValid (lw_sheet)
end if

return li_counter


end function

public function integer of_getsheetsbytitle (ref window aw_sheet[], string as_title);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetSheetsbyTitle
//
//	Access:  public
//
//	Arguments:
//	aw_sheet[] by ref
//	as_title:  title of the sheets to get
//
//	Returns:  integer
//	number of sheets open with title specified
//	-1 = error
//
//	Description:  Get reference to all open sheets with title specified
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

return of_GetSheetsByTitle (aw_sheet, as_title, false)


end function

protected function integer of_setcurrentstate (arrangetypes ae_arrange);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetCurrentState
//
//	Access:  protected
//
//	Arguments:
//	ae_arrange:  window arrange type that was performed
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:
//	Sets the current arrange type of the sheets to allow for undo
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_result = 1
integer	li_ndx
integer	li_totalsheets
window	lw_sheets[]
str_sheetinfo	lstr_sheet[]

ie_arrange = ae_arrange

// Get sheets
li_totalsheets = this.of_GetSheets(lw_sheets)

// Store window dimensions for each sheet
istr_sheet = lstr_sheet
for li_ndx=1 to li_totalsheets
	istr_sheet[li_ndx].w_obj = lw_sheets[li_ndx]
	istr_sheet[li_ndx].e_state = lw_sheets[li_ndx].windowstate
	istr_sheet[li_ndx].i_width = lw_sheets[li_ndx].width
	istr_sheet[li_ndx].i_height = lw_sheets[li_ndx].height
	istr_sheet[li_ndx].i_x = lw_sheets[li_ndx].x
	istr_sheet[li_ndx].i_y = lw_sheets[li_ndx].y
next

return li_result
end function

public function integer of_setrequestor (w_rmt_ancestor aw_requestor);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetRequestor
//
//	Access:  public
//
//	Arguments:
//	aw_requestor	The frame window requesting this service
//
//	Returns:  integer
//	 1 = success
//	-1 = error
//
//	Description:  associates a frame window with this service.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

// Validate arguments
if IsNull(aw_requestor) Or not IsValid (aw_requestor) then
	return -1
end if

// Only frame windows can use this service
if aw_requestor.windowtype <> mdi! and aw_requestor.windowtype <> mdihelp! then
	return -1
end if

iw_requestor = aw_requestor
return 1
end function

public function arrangetypes of_getcurrentstate ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetCurrentState
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  arrangetypes
//	Current arrange state of open windows
//	NULL if undo was the last arrange performed	
//
//	Description:
//	Gets the current arrange state of open windows
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

return ie_arrange
end function

public function integer of_closeactivesheet ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_closeActiveSheet
//
//	Access:  public
//
//	Arguments:  none
//
//	Returns:  integer
//	      1 = success
//	      -1 = frame does not exist
//       0 = no more active sheets
//
//	Description:  Closes the active sheet
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

window	lw_sheet

// Validate window requestor
if IsNull(iw_requestor) Or not IsValid (iw_requestor) then
	return -1
end if

// Get active sheet
lw_sheet = iw_requestor.GetActiveSheet()
if IsValid (lw_sheet) then
  close(lw_sheet)
else
	return 0
end if

return 1



end function

on n_rmt_winsrv_sheetmanager.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_winsrv_sheetmanager.destroy
TriggerEvent( this, "destructor" )
end on

event constructor;call n_rmt_winsrv::constructor;SetNull (ie_arrange)
end event

