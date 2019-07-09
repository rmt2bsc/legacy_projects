$PBExportHeader$u_rmt_cb_save.sru
forward
global type u_rmt_cb_save from u_rmt_commandbutton
end type
end forward

global type u_rmt_cb_save from u_rmt_commandbutton
string Text="&Save"
end type
global u_rmt_cb_save u_rmt_cb_save

event clicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event Name:  clicked
//
//	Description: Saves changes.
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

// Check for any pending updates
li_result = iw_parent.of_UpdatesPending()
if li_result <= 0 then
	return 0
else
	return iw_parent.event ue_save()
end if
end event

