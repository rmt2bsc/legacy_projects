$PBExportHeader$w_expense_maint.srw
$PBExportComments$General Code Group Maintenance window
forward
global type w_expense_maint from w_rmt_response_maint
end type
end forward

global type w_expense_maint from w_rmt_response_maint
integer width = 2304
end type
global w_expense_maint w_expense_maint

on w_expense_maint.create
call super::create
end on

on w_expense_maint.destroy
call super::destroy
end on

event ue_predelete;call super::ue_predelete;int    li_row
int    li_dependcount
int    li_id
boolean lb_permanent


li_row = dw_edit.getselectedrow(0)
li_id = dw_edit.getitemnumber(li_row, "id")

select count(*) 
   into :li_dependcount
	from order_expenses
	where expense_id = :li_id;
  
if li_dependcount > 0 then
	messagebox(gnv_app.iapp_object.displayname, "The selected expense cannot be deleted since it is assigned to one or more Charter Orders.")
	return -1
end if

return 1
end event
event ue_save;call super::ue_save;int  li_rc 

li_rc = ancestorReturnValue
if li_rc = SUCCESS then
	dw_edit.resetUpdate()
end if

return li_rc
end event

event ue_add;call super::ue_add;int  li_row

li_row = dw_edit.getRow()
dw_edit.setItem(li_row, "active", 1)

return 1
end event
type cb_3 from w_rmt_response_maint`cb_3 within w_expense_maint
integer x = 1810
integer taborder = 60
end type

type cb_1 from w_rmt_response_maint`cb_1 within w_expense_maint
integer x = 1810
end type

type cb_2 from w_rmt_response_maint`cb_2 within w_expense_maint
integer x = 1810
end type

type dw_edit from w_rmt_response_maint`dw_edit within w_expense_maint
integer width = 1632
integer height = 1112
integer taborder = 10
string dataobject = "d_expenses"
end type

type cb_delete from w_rmt_response_maint`cb_delete within w_expense_maint
integer x = 1810
integer taborder = 40
end type

type cb_add from w_rmt_response_maint`cb_add within w_expense_maint
integer x = 1810
integer taborder = 30
end type

