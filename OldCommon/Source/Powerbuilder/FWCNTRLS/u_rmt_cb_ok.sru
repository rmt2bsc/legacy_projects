$PBExportHeader$u_rmt_cb_ok.sru
forward
global type u_rmt_cb_ok from u_rmt_commandbutton
end type
end forward

global type u_rmt_cb_ok from u_rmt_commandbutton
string Text="&Ok"
end type
global u_rmt_cb_ok u_rmt_cb_ok

event clicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event Name:  clicked
//
//	Description: Saves changes and closes parent window.
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

if isValid(iw_parent.inv_data) then
   li_result = iw_parent.inv_data.idw_data.rowcount()
   CloseWithReturn(iw_parent, iw_parent.inv_data)
else
	close(iw_parent)
end if






//this.event ue_preclose()
//li_result = iw_parent.inv_data.ids_data.rowcount()
//CloseWithReturn(iw_parent, iw_parent.inv_data)
//iw_parent.event ue_close()

end event

