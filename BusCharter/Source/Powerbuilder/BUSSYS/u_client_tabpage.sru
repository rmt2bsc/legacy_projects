$PBExportHeader$u_client_tabpage.sru
forward
global type u_client_tabpage from u_rmt_tabpage
end type
type dw_edit from u_rmt_dw within u_client_tabpage
end type
end forward

global type u_client_tabpage from u_rmt_tabpage
integer width = 3122
integer height = 1612
dw_edit dw_edit
end type
global u_client_tabpage u_client_tabpage

on u_client_tabpage.create
int iCurrent
call super::create
this.dw_edit=create dw_edit
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_edit
end on

on u_client_tabpage.destroy
call super::destroy
destroy(this.dw_edit)
end on

event ue_init;call super::ue_init;this.dw_edit.settransobject(sqlca)
end event

type dw_edit from u_rmt_dw within u_client_tabpage
integer x = 37
integer y = 36
integer width = 3063
integer height = 1532
boolean bringtotop = true
string dataobject = "d_charter_order_client_maint"
boolean vscrollbar = false
boolean border = false
borderstyle borderstyle = stylebox!
end type

event buttonclicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  buttonclicked
//
//	Returns:  long
//	number of the new row that was inserted
//	 0 = No row was added.
//	-1 = error
//
//	Description:	Handles the event when a datawindow button is selected.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//
//////////////////////////////////////////////////////////////////////////////

n_charter_order_bo   n_data
str_winargs  lstr  
int          li_row
int          li_result
int          li_client_id
w_charter_order  lw_parent


if isnull(this.iw_parent) or not isValid(this.iw_parent) then
	return -1
end if

lw_parent = this.iw_parent

if dwo.name = "cb_client" then
   lstr.dataclassname = "n_charter_order_bo"
   lstr.parentwin = lw_parent
   openwithparm(w_client_maint, lstr)	
   if isvalid(message.powerobjectparm) and not isnull(message.powerobjectparm) then
	   n_data = message.powerobjectparm
	else
		messagebox(gnv_app.iapp_object.displayName, "Data returned from client Maintenance is invalid.")
		return 0
   end if
   if not isNull(n_data.ii_client_id) and n_data.ii_client_id > 0 then
	   li_client_id = n_data.ii_client_id
	   if isNull(li_client_id) or li_client_id <= 0 then
		   return 0
 	   end if
	   this.idw_arg[1] = li_client_id
	   lw_parent.inv_bo.ii_client_id = li_client_id
	   if this.of_retrieve() > 0 then
		   lw_parent.inv_bo.of_clientChanged(li_client_id)
  	   end if
   else
	   messagebox("Test", "n_data.ii_client_id was invalid")
   end if
end if
end event

event itemchanged;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  itemchanged
//
//
//	Returns:  long
//	number of the new row that was inserted
//	 0 = No row was added.
//	-1 = error
//
//	Description:	Notifies the parent tab control that the client has changed.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//
//////////////////////////////////////////////////////////////////////////////

return 0
end event

event ue_enablecontrols;call super::ue_enablecontrols;w_charter_order   lw_parent


lw_parent = this.iw_parent
  
  // If order status is closed or cancelled, render all client information read-only
if lw_parent.inv_bo.ii_order_status = lw_parent.inv_bo.ii_ORDER_CLOSE or lw_parent.inv_bo.ii_order_status = lw_parent.inv_bo.ii_ORDER_CANCEL then
	this.object.cb_client.visible = 0
else
	this.object.cb_client.visible = 1
end if
end event

