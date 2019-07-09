$PBExportHeader$w_charter_order_doc_print_menu.srw
forward
global type w_charter_order_doc_print_menu from w_rmt_response_maint
end type
end forward

global type w_charter_order_doc_print_menu from w_rmt_response_maint
integer x = 850
integer y = 948
integer width = 2587
integer height = 1216
end type
global w_charter_order_doc_print_menu w_charter_order_doc_print_menu

type variables
n_charter_order_doc_print_menu_bo  inv_bo
end variables

on w_charter_order_doc_print_menu.create
call super::create
end on

on w_charter_order_doc_print_menu.destroy
call super::destroy
end on

event ue_postopen;call super::ue_postopen;int   li_result

dw_edit.settransobject(sqlca)
dw_edit.of_setrowselect(false)
dw_edit.of_setrowmanager(false)
dw_edit.of_setUpdateable(false)
li_result = dw_edit.insertrow(0)

this.dw_edit.object.print_client[1] = 'N'
this.dw_edit.object.print_quote[1] = 'N'
this.dw_edit.object.print_order[1] = 'N'
this.dw_edit.object.print_transaction[1] = 'N'
this.dw_edit.object.print_invoice[1] = 'N'
this.dw_edit.object.print_drivers[1] = 'N'
end event

event ue_preopen;call super::ue_preopen;if not isnull(istr_winargs.busobj) and isValid(istr_winargs.busobj) then
	inv_bo = istr_winargs.busobj
	inv_bo.iw_parent = istr_winargs.parentwin
end if
end event

event ue_close;call super::ue_close;int  li_row

if this.dw_Edit.rowcount() <= 0 then
	setnull(inv_data) 
	return
end if

inv_bo.ib_print_client = (this.dw_edit.object.print_client[1] = 'Y')
inv_bo.ib_print_quote = (this.dw_edit.object.print_quote[1] = 'Y')
inv_bo.ib_print_order = (this.dw_edit.object.print_order[1] = 'Y')
inv_bo.ib_print_transaction = (this.dw_edit.object.print_transaction[1] = 'Y')
inv_bo.ib_print_invoice = (this.dw_edit.object.print_invoice[1] = 'Y')
inv_bo.ib_print_drivers = (this.dw_edit.object.print_drivers[1] = 'Y')
inv_bo.ib_print_agreement = (this.dw_edit.object.print_agreement[1] = 'Y')  // SCR8 RMT040202
inv_bo.ib_print_expenses = (this.dw_edit.object.print_expenses[1] = 'Y')  // SCR8 RMT012605
inv_data = inv_bo


  // Start SCR8 RMT040202
li_row = istr_winargs.dw.getselectedrow(0)
inv_bo.of_setClient(this.istr_winargs.dw.object.client_id[li_row])
inv_bo.of_setQuote(this.istr_winargs.dw.object.quote_id[li_row])
inv_bo.of_setOrder(this.istr_winargs.dw.object.orders_id[li_row])
inv_bo.of_printCharterOrder()
  // End SCR8 RMT040202
end event

event ue_cancel;call super::ue_cancel;
this.dw_edit.reset()
end event

type cb_3 from w_rmt_response_maint`cb_3 within w_charter_order_doc_print_menu
integer x = 2107
integer y = 244
end type

type cb_1 from w_rmt_response_maint`cb_1 within w_charter_order_doc_print_menu
integer x = 2107
integer y = 132
end type

type cb_2 from w_rmt_response_maint`cb_2 within w_charter_order_doc_print_menu
boolean visible = false
integer x = 2107
end type

type dw_edit from w_rmt_response_maint`dw_edit within w_charter_order_doc_print_menu
integer width = 1943
integer height = 1032
string dataobject = "d_charter_order_doc_print_menu"
boolean vscrollbar = false
end type

event dw_edit::itemchanged;call super::itemchanged;
if dwo.name = "select_all" then
	this.setItem(row, "print_client", data)
	this.setItem(row, "print_quote", data)
	this.setItem(row, "print_order", data)
	this.setItem(row, "print_transaction", data)
	this.setItem(row, "print_invoice", data)
	this.setItem(row, "print_drivers", data)
	this.setItem(row, "print_agreement", data)
	this.setItem(row, "print_expenses", data)
end if

return 0

end event
type cb_delete from w_rmt_response_maint`cb_delete within w_charter_order_doc_print_menu
boolean visible = false
integer x = 2107
end type

type cb_add from w_rmt_response_maint`cb_add within w_charter_order_doc_print_menu
boolean visible = false
integer x = 2107
end type

