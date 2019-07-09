$PBExportHeader$w_rmt_response_maint.srw
forward
global type w_rmt_response_maint from w_rmt_response
end type
type cb_3 from u_rmt_cb_cancel within w_rmt_response_maint
end type
type cb_1 from u_rmt_cb_ok within w_rmt_response_maint
end type
type cb_2 from u_rmt_cb_save within w_rmt_response_maint
end type
type dw_edit from u_rmt_dw within w_rmt_response_maint
end type
type cb_delete from u_rmt_commandbutton within w_rmt_response_maint
end type
type cb_add from u_rmt_commandbutton within w_rmt_response_maint
end type
end forward

global type w_rmt_response_maint from w_rmt_response
integer x = 914
integer y = 608
integer width = 2240
long backcolor = 80269524
cb_3 cb_3
cb_1 cb_1
cb_2 cb_2
dw_edit dw_edit
cb_delete cb_delete
cb_add cb_add
end type
global w_rmt_response_maint w_rmt_response_maint

event ue_postopen;call super::ue_postopen;int   li_result

dw_edit.settransobject(sqlca)
dw_edit.of_setrowselect(true)
dw_edit.of_setrowmanager(true)
li_result = dw_edit.of_retrieve()
end event

on w_rmt_response_maint.create
int iCurrent
call super::create
this.cb_3=create cb_3
this.cb_1=create cb_1
this.cb_2=create cb_2
this.dw_edit=create dw_edit
this.cb_delete=create cb_delete
this.cb_add=create cb_add
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.cb_3
this.Control[iCurrent+2]=this.cb_1
this.Control[iCurrent+3]=this.cb_2
this.Control[iCurrent+4]=this.dw_edit
this.Control[iCurrent+5]=this.cb_delete
this.Control[iCurrent+6]=this.cb_add
end on

on w_rmt_response_maint.destroy
call super::destroy
destroy(this.cb_3)
destroy(this.cb_1)
destroy(this.cb_2)
destroy(this.dw_edit)
destroy(this.cb_delete)
destroy(this.cb_add)
end on

event ue_delete;call super::ue_delete;

if isValid(dw_edit.inv_rowmanager) then
	return dw_edit.inv_rowmanager.of_deleterow(ai_row)
end if
end event

event type integer ue_add();int  li_row

if isvalid(dw_edit.inv_rowmanager) then
	li_row = dw_edit.inv_rowmanager.of_insertrow(0)
end if

return li_row
	
end event
event ue_preupdate;call super::ue_preupdate;int  li_row
int  li_rows
int  li_actualupdatecount
dwitemstatus  l_stat

dw_edit.accepttext()
if (dw_edit.modifiedcount() + dw_edit.deletedcount()) <= 0 then
	return 0
end if

li_rows = dw_edit.rowcount()
li_actualupdatecount = 0
for li_row = 1 to li_rows
	l_stat = dw_edit.getitemstatus(li_row, 0, primary!)
   if l_stat = newmodified! or l_stat = datamodified! then
		if l_stat = newmodified! then
         dw_edit.object.date_created[li_row] = today()
		end if
      dw_edit.object.date_updated[li_row] = today()
      dw_edit.object.user_id[li_row] = gnv_app.of_getUserID()
	   li_actualupdatecount++
   end if
next

return 1
  
end event

type cb_3 from u_rmt_cb_cancel within w_rmt_response_maint
integer x = 1769
integer y = 448
integer width = 411
integer height = 84
boolean bringtotop = true
end type

type cb_1 from u_rmt_cb_ok within w_rmt_response_maint
integer x = 1769
integer y = 36
integer width = 411
integer height = 84
integer taborder = 20
boolean bringtotop = true
boolean default = true
end type

type cb_2 from u_rmt_cb_save within w_rmt_response_maint
integer x = 1769
integer y = 340
integer width = 411
integer height = 84
integer taborder = 50
boolean bringtotop = true
end type

type dw_edit from u_rmt_dw within w_rmt_response_maint
integer x = 55
integer y = 44
integer width = 1623
integer height = 1164
integer taborder = 40
boolean bringtotop = true
end type

type cb_delete from u_rmt_commandbutton within w_rmt_response_maint
integer x = 1769
integer y = 236
integer width = 411
integer height = 84
integer taborder = 30
boolean bringtotop = true
string text = "Delete"
end type

event clicked;long  ll_row

ll_row = dw_edit.GetSelectedRow(0)

if isValid(dw_edit.inv_rowmanager) then
   if ll_row > 0 then 
      iw_parent.of_delete(ll_row)
	else
		messagebox(gnv_app.iapp_object.displayname, "A row must be selected in order to perform a delete action")
	end if
end if
end event

type cb_add from u_rmt_commandbutton within w_rmt_response_maint
integer x = 1769
integer y = 136
integer width = 411
integer height = 84
integer taborder = 20
boolean bringtotop = true
string text = "Add"
end type

event clicked;iw_parent.of_add()
end event

