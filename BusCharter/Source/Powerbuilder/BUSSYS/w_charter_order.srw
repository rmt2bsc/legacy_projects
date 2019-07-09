$PBExportHeader$w_charter_order.srw
forward
global type w_charter_order from w_rmt_response
end type
type tab_charter_order from u_charter_order_tab within w_charter_order
end type
type tab_charter_order from u_charter_order_tab within w_charter_order
end type
type cb_1 from u_rmt_cb_ok within w_charter_order
end type
type cb_2 from u_rmt_cb_save within w_charter_order
end type
type cb_3 from u_rmt_cb_cancel within w_charter_order
end type
type cb_add from u_rmt_commandbutton within w_charter_order
end type
type cb_delete from u_rmt_commandbutton within w_charter_order
end type
type st_1 from statictext within w_charter_order
end type
type st_2 from statictext within w_charter_order
end type
type dw_balance_due from u_rmt_dw within w_charter_order
end type
end forward

global type w_charter_order from w_rmt_response
integer x = 110
integer y = 512
integer width = 4462
integer height = 2200
string title = "Charter Order Maintenance"
long backcolor = 80269524
tab_charter_order tab_charter_order
cb_1 cb_1
cb_2 cb_2
cb_3 cb_3
cb_add cb_add
cb_delete cb_delete
st_1 st_1
st_2 st_2
dw_balance_due dw_balance_due
end type
global w_charter_order w_charter_order

type variables
public:
   n_charter_order_bo  inv_bo
end variables

on w_charter_order.create
int iCurrent
call super::create
this.tab_charter_order=create tab_charter_order
this.cb_1=create cb_1
this.cb_2=create cb_2
this.cb_3=create cb_3
this.cb_add=create cb_add
this.cb_delete=create cb_delete
this.st_1=create st_1
this.st_2=create st_2
this.dw_balance_due=create dw_balance_due
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.tab_charter_order
this.Control[iCurrent+2]=this.cb_1
this.Control[iCurrent+3]=this.cb_2
this.Control[iCurrent+4]=this.cb_3
this.Control[iCurrent+5]=this.cb_add
this.Control[iCurrent+6]=this.cb_delete
this.Control[iCurrent+7]=this.st_1
this.Control[iCurrent+8]=this.st_2
this.Control[iCurrent+9]=this.dw_balance_due
end on

on w_charter_order.destroy
call super::destroy
destroy(this.tab_charter_order)
destroy(this.cb_1)
destroy(this.cb_2)
destroy(this.cb_3)
destroy(this.cb_add)
destroy(this.cb_delete)
destroy(this.st_1)
destroy(this.st_2)
destroy(this.dw_balance_due)
end on

event ue_postopen;call super::ue_postopen;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postopen
//
//	Description: Opens the Green's Bus Charter Maintenance Screen
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR    Date        Developer    Description
// =====  =========== =========  =============================================
//////////////////////////////////////////////////////////////////////////////
n_rmt_utility   lnv_util
dec {2}   ldec_bal


tab_charter_order.tabpage_client.dw_edit.of_setUpdateable(false)
tab_charter_order.tabpage_trip.dw_edit.of_setUpdateable(true)
tab_charter_order.tabpage_order.dw_edit.of_setUpdateable(true)
tab_charter_order.tabpage_transaction.dw_edit.of_setUpdateable(true)
tab_charter_order.tabpage_expense.dw_edit.of_setUpdateable(true)
tab_charter_order.tabpage_invoice.dw_edit.of_setUpdateable(false)
this.dw_balance_due.of_setUpdateable(false)

this.dw_balance_due.insertrow(0)
if istr_winargs.isNew then
   tab_charter_order.tabpage_client.dw_edit.of_insert(0)
   tab_charter_order.tabpage_trip.dw_edit.of_insert(0)
	tab_charter_order.tabpage_trip.dw_edit.object.status[1] = this.inv_bo.ii_ORDER_QUOTE    /* RMT 012202 */
   tab_charter_order.tabpage_order.dw_edit.of_insert(0)
	tab_charter_order.tabpage_invoice.enabled = false
	this.dw_balance_due.object.balance_due[1] = 0
else
	tab_charter_order.tabpage_client.dw_edit.of_setArgument(1, inv_bo.ii_client_id)  // client ID
	tab_charter_order.tabpage_client.dw_edit.of_retrieve()
	tab_charter_order.tabpage_trip.dw_edit.of_setArgument(1, inv_bo.ii_client_id)  // client ID
	tab_charter_order.tabpage_trip.dw_edit.of_setArgument(2, inv_bo.ii_quote_id)  // quote ID
   tab_charter_order.tabpage_trip.dw_edit.of_retrieve()
	if not isNull(inv_bo.ii_order_id) then
   	tab_charter_order.tabpage_order.dw_edit.of_setArgument(1, inv_bo.ii_order_id)    // orders ID
      if tab_charter_order.tabpage_order.dw_edit.of_retrieve() > 0 then
		   this.st_1.text = this.st_1.text + " - Order: " + string(inv_bo.ii_order_id)
   	end if
	   tab_charter_order.tabpage_transaction.dw_edit.of_setArgument(1, inv_bo.ii_order_id)  // orders ID
       tab_charter_order.tabpage_transaction.dw_edit.of_retrieve()
	   tab_charter_order.tabpage_expense.dw_edit.of_setArgument(1, inv_bo.ii_order_id)  // orders ID
       tab_charter_order.tabpage_expense.dw_edit.of_retrieve()		
	   ldec_bal = this.inv_bo.of_getbalance(inv_bo.ii_order_id)
   	this.dw_balance_due.object.balance_due[1] = ldec_bal
	   if tab_charter_order.tabpage_order.dw_edit.rowcount() > 0 then
  	      inv_bo.ii_order_status = tab_charter_order.tabpage_trip.dw_edit.object.status[1]
   		tab_charter_order.tabpage_invoice.enabled = (inv_bo.ii_order_status = inv_bo.ii_ORDER_INVOICED or inv_bo.ii_order_status = inv_bo.ii_ORDER_CLOSE)
	   	tab_charter_order.tabpage_invoice.dw_edit.of_setArgument(1, inv_bo.ii_order_id)    // orders ID
		   tab_charter_order.tabpage_invoice.dw_edit.of_retrieve()
   	end if
      lnv_util.of_sound("sound\fileaccessed.wav")
	else
		tab_charter_order.tabpage_order.dw_edit.of_insert(0)
		
	end if
end if

tab_charter_order.tabpage_transaction.dw_edit.of_setRowManager(true)
tab_charter_order.tabpage_transaction.dw_edit.of_setRowSelect(true)
tab_charter_order.tabpage_expense.dw_edit.of_setRowManager(true)
tab_charter_order.tabpage_expense.dw_edit.of_setRowSelect(true)

this.event ue_enablecontrols()
this.of_CheckSecurity()
end event

event ue_enablecontrols;call super::ue_enablecontrols;
if not isnull(inv_bo.ii_order_id) then
	inv_bo.ib_invoiced = (this.tab_charter_order.tabpage_trip.dw_edit.object.status[1] = inv_bo.ii_ORDER_INVOICED)
	if inv_bo.ii_order_status = inv_bo.ii_ORDER_CLOSE or inv_bo.ii_order_status = inv_bo.ii_ORDER_CANCEL then  // if order is closed or cancelled
   	tab_charter_order.tabpage_client.dw_edit.of_setUpdateable(false)
      tab_charter_order.tabpage_trip.dw_edit.of_setUpdateable(false)
      tab_charter_order.tabpage_order.dw_edit.of_setUpdateable(false)
      tab_charter_order.tabpage_transaction.dw_edit.of_setUpdateable(false)
	end if
end if

if ( istr_winargs.isNew and (isnull(inv_bo.ii_order_id) and isnull(inv_bo.ii_quote_id))) or  &
   ( not istr_winargs.isNew and (isnull(inv_bo.ii_order_id) and not isnull(inv_bo.ii_quote_id)) ) then
//	tab_charter_order.tabpage_trip.dw_edit.object.cb_createorder.visible = true
	tab_charter_order.tabpage_trip.enabled = not isnull(inv_bo.ii_client_id)
	tab_charter_order.tabpage_order.enabled = false
   tab_charter_order.tabpage_transaction.enabled = false
	tab_charter_order.tabpage_invoice.enabled = false
	this.cb_add.enabled = false
	this.cb_delete.enabled = false
	tab_charter_order.tabpage_order.dw_edit.of_setUpdateable(false)
   tab_charter_order.tabpage_transaction.dw_edit.of_setUpdateable(false)
else
//	tab_charter_order.tabpage_trip.dw_edit.object.cb_createorder.visible = (isNull(inv_bo.ii_order_id))
	tab_charter_order.tabpage_order.enabled = (not isNull(inv_bo.ii_order_id))
   tab_charter_order.tabpage_transaction.enabled = (not isNull(inv_bo.ii_order_id))
	tab_charter_order.tabpage_invoice.enabled = (inv_bo.ib_invoiced or inv_bo.ii_order_status = inv_bo.ii_ORDER_CLOSE or inv_bo.ii_order_status = inv_bo.ii_ORDER_CANCEL)
	this.cb_add.enabled = (not isNull(inv_bo.ii_order_id))
	this.cb_delete.enabled = (not isNull(inv_bo.ii_order_id))
	tab_charter_order.tabpage_order.dw_edit.of_setUpdateable(true)
   tab_charter_order.tabpage_transaction.dw_edit.of_setUpdateable(true)
end if

if this.tab_charter_order.tabpage_order.dw_edit.rowcount() > 0 then
   inv_bo.ii_order_status = this.tab_charter_order.tabpage_trip.dw_edit.object.status[1]
end if
this.inv_bo.of_enableControls(this.control)

end event

event ue_preupdate;call super::ue_preupdate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preupdate
//
//	Description:  
//               
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

dwitemstatus  l_quoterowstat
dwitemstatus  l_quotecommentstat
n_rmt_utility lnv_util
int           li_comment_id


//  Process the Client tab page
this.tab_charter_order.tabpage_client.of_preupdate()

//  Process the Trip tab page
this.tab_charter_order.tabpage_trip.of_preupdate()

//  Process the Costs tab page
this.tab_charter_order.tabpage_order.of_preupdate()

//  Process the Transaction tab page
this.tab_charter_order.tabpage_transaction.of_preupdate()

//  Process the Expense tab page
this.tab_charter_order.tabpage_expense.of_preupdate()


return 1
end event
event ue_add;call super::ue_add;
//int  li_row
//
//li_row = tab_charter_order.tabpage_transaction.dw_edit.of_insert(1)
//if li_row > 0 then
//	tab_charter_order.tabpage_transaction.dw_edit.object.order_id[li_row] = tab_charter_order.tabpage_order.dw_edit.object.id[1]
//   tab_charter_order.tabpage_transaction.dw_edit.scrolltorow(li_row)	
//	tab_charter_order.tabpage_transaction.dw_edit.setfocus()
//end if
//
//return li_row

return 1
//
end event

event ue_preopen;call super::ue_preopen;

if not isnull(istr_winargs.busobj) and isValid(istr_winargs.busobj) then
	inv_bo = istr_winargs.busobj
	inv_bo.iw_parent = istr_winargs.parentwin
	inv_bo.ii_client_id = Integer(istr_winargs.parms.parm[1])
	inv_bo.ii_quote_id = Integer(istr_winargs.parms.parm[2])
	inv_bo.ii_order_id = Integer(istr_winargs.parms.parm[3])
end if
end event

event ue_save;call super::ue_save;dec {2}  ldec_bal


if upperBound(istr_winargs.parms.parm) = 3 then
  ldec_bal = this.inv_bo.of_getbalance(istr_winargs.parms.parm[3])
  this.dw_balance_due.object.balance_due[1] = ldec_bal
end if

return AncestorReturnValue

end event

event type boolean ue_checksecurity(powerobject apo_control[]);call super::ue_checksecurity;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_CheckSecurity
//
//	Access:  public
//
//	Arguments:	none
//
//	Returns:  none
//
//	Description:  Determines if the screen is editable base on the user logged in
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR    Date        Developer    Description
// =====  =========== =========  =============================================
//    7   04/02/2002   RMT        Added.   SCR7 RMT040202
//////////////////////////////////////////////////////////////////////////////

int  li_access

  //  Begin SCR7 RMT040202
li_access = gnv_app.of_getAccess()
if li_access = gnv_app.ii_NORMAL then
	this.setredraw(false)
	this.cb_2.enabled = false
	this.setredraw(true)
end if
  //  End SCR7 RMT040202
  
return true  
end event

type tab_charter_order from u_charter_order_tab within w_charter_order
integer x = 32
integer y = 212
integer width = 3945
boolean bringtotop = true
end type

event selectionchanged;call super::selectionchanged;

if this.i_currentpage.classname() = "tabpage_transaction" then
   parent.cb_add.enabled = true
	parent.cb_delete.enabled = true
else
	parent.cb_add.enabled = false
	parent.cb_delete.enabled = false
end if
end event

type cb_1 from u_rmt_cb_ok within w_charter_order
integer x = 4018
integer y = 324
integer width = 393
integer height = 84
integer taborder = 20
boolean bringtotop = true
end type

type cb_2 from u_rmt_cb_save within w_charter_order
integer x = 4018
integer y = 428
integer width = 393
integer height = 84
integer taborder = 60
boolean bringtotop = true
end type

event clicked;call super::clicked;int   li_result
dec{2} ldec_bal


li_result = AncestorReturnValue

if li_result = 1 then
	if tab_charter_order.tabpage_invoice.dw_edit.rowcount() > 0 then
		tab_charter_order.tabpage_invoice.dw_edit.retrieve(inv_bo.ii_order_id)
	end if
	ldec_bal = parent.inv_bo.of_getbalance(inv_bo.ii_order_id)
  	parent.dw_balance_due.object.balance_due[1] = ldec_bal
end if

return 1
end event

type cb_3 from u_rmt_cb_cancel within w_charter_order
integer x = 4018
integer y = 536
integer width = 393
integer height = 84
integer taborder = 50
boolean bringtotop = true
end type

type cb_add from u_rmt_commandbutton within w_charter_order
boolean visible = false
integer x = 4037
integer y = 1072
integer width = 393
integer height = 84
integer taborder = 40
boolean bringtotop = true
string text = "Add Trans."
end type

event clicked;iw_parent.event ue_add()
end event

type cb_delete from u_rmt_commandbutton within w_charter_order
boolean visible = false
integer x = 4046
integer y = 1164
integer width = 393
integer height = 84
integer taborder = 30
boolean bringtotop = true
string text = "Delete Trans"
end type

event clicked;long  ll_row

ll_row = parent.tab_charter_order.tabpage_transaction.dw_edit.GetSelectedRow(0)

if isValid(parent.tab_charter_order.tabpage_transaction.dw_edit.inv_rowmanager) then
   if ll_row > 0 then 
      iw_parent.of_delete(ll_row)
	else
		messagebox(gnv_app.iapp_object.displayname, "A row must be selected in order to perform a delete action")
	end if
end if
end event

type st_1 from statictext within w_charter_order
integer x = 50
integer y = 44
integer width = 2171
integer height = 108
boolean bringtotop = true
integer textsize = -16
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = roman!
string facename = "Times New Roman"
long textcolor = 16711680
long backcolor = 80269524
boolean enabled = false
string text = "Charter Order Maintenance"
boolean focusrectangle = false
end type

type st_2 from statictext within w_charter_order
integer x = 2551
integer y = 44
integer width = 581
integer height = 108
boolean bringtotop = true
integer textsize = -16
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = roman!
string facename = "Times New Roman"
long textcolor = 16711680
long backcolor = 80269524
boolean enabled = false
string text = "Balance Due:"
boolean focusrectangle = false
end type

type dw_balance_due from u_rmt_dw within w_charter_order
integer x = 3127
integer y = 12
integer width = 1106
integer height = 164
boolean bringtotop = true
string dataobject = "d_balance_due_display"
boolean vscrollbar = false
boolean border = false
boolean livescroll = false
borderstyle borderstyle = stylebox!
end type

