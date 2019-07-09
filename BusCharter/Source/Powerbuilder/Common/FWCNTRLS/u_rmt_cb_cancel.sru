$PBExportHeader$u_rmt_cb_cancel.sru
forward
global type u_rmt_cb_cancel from u_rmt_commandbutton
end type
end forward

global type u_rmt_cb_cancel from u_rmt_commandbutton
string Text="&Cancel"
end type
global u_rmt_cb_cancel u_rmt_cb_cancel

event clicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event Name:  clicked
//
//	Description:	Closes parent window without saving changes
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

iw_parent.event ue_cancel()
close(iw_parent)
end event

