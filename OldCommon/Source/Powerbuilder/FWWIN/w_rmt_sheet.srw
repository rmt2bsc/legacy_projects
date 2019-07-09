$PBExportHeader$w_rmt_sheet.srw
$PBExportComments$Sheet window ancestor
forward
global type w_rmt_sheet from w_rmt_ancestor
end type
end forward

global type w_rmt_sheet from w_rmt_ancestor
boolean TitleBar=true
string Title=""
end type
global w_rmt_sheet w_rmt_sheet

on w_rmt_sheet.create
call super::create
end on

on w_rmt_sheet.destroy
call super::destroy
end on

event ue_setactivecontrol;call super::ue_setactivecontrol;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_setactivecontrol
//
//	Arguments:		
//	//   Control which just got focus
//
//	Returns:  none
//
//	Description:
//	Display the microhelp stored in the tag value of the current control
//
//	Note:  The format is MICROHELP=<microhelp to be displayed>
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


string			ls_columntag
string			ls_microhelp
string			ls_colname
datawindow		ldw_control
n_rmt_string 	lnv_string 

// Request microhelp
if gnv_app.of_GetMicrohelp() then
	// If control with focus is a datawindow, use current column's microhelp
	if ado_control.TypeOf() = DataWindow! then
		ldw_control = ado_control
		ls_colname = ldw_control.GetColumnName()
		if Len (ls_colname) > 0 then
			// Check the column tag for any microhelp information.
			ls_columntag = ldw_control.Describe (ls_colname + ".tag")
			ls_microhelp = lnv_string.of_GetKeyValue (ls_columntag, "microhelp", ";")
		end if
	else
		// Check the control tag for any microhelp information.
		ls_microhelp = lnv_string.of_GetKeyValue (ado_control.tag, "microhelp", ";")
	end if

	// If the microhelp variable is empty make sure it displays "Ready".
	if lnv_string.of_IsEmpty (ls_microhelp) then
		ls_microhelp = ''	
	end If

	// display microhelp
//	this.event ue_microHelp (ls_microhelp)
end if

end event

event ue_microhelp;call super::ue_microhelp;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_microhelp
//
//	Arguments:
//	as_microhelp   microhelp to be displayed
//
//	Returns:  none
//
//	Description:
//	Notify frame to display microhelp
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

window	lw_frame

lw_frame = this.ParentWindow()
if IsValid (lw_frame) then
	// Send notification to the MDI frame.
	lw_frame.dynamic event ue_microhelp (as_microhelp)
end if

end event

event activate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  activate
//
//	Description:
//	notify frame that microhelp needs to be refreshed
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

object				le_object
graphicobject	lgo_focus

if gnv_app.of_GetMicrohelp() then
	lgo_focus = GetFocus()
	if IsValid (lgo_focus) and not isnull(lgo_focus) then
		le_object = lgo_focus.TypeOf()
		if le_object <> mdiclient! and le_object <> menu! and le_object <> window! then
			this.event ue_SetActiveControl(lgo_focus)
		end if
	end if
end if
end event

event ue_postopen;call super::ue_postopen;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postopen
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:	Performs a postopen logic at the descendent level
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//////////////////////////////////////////////////////////////////////////////



this.of_setResize(true)


end event

