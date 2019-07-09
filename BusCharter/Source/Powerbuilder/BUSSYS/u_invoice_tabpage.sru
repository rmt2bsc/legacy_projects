$PBExportHeader$u_invoice_tabpage.sru
forward
global type u_invoice_tabpage from u_rmt_tabpage
end type
type dw_edit from u_rmt_dw within u_invoice_tabpage
end type
end forward

global type u_invoice_tabpage from u_rmt_tabpage
integer width = 3735
integer height = 1704
dw_edit dw_edit
end type
global u_invoice_tabpage u_invoice_tabpage

on u_invoice_tabpage.create
int iCurrent
call super::create
this.dw_edit=create dw_edit
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_edit
end on

on u_invoice_tabpage.destroy
call super::destroy
destroy(this.dw_edit)
end on

event ue_init;call super::ue_init;this.dw_edit.settransobject(sqlca)
end event

type dw_edit from u_rmt_dw within u_invoice_tabpage
integer x = 32
integer y = 36
integer width = 3662
integer height = 1592
boolean bringtotop = true
string dataobject = "d_invoice_summary"
end type

