$PBExportHeader$u_transaction_tabpage.sru
forward
global type u_transaction_tabpage from u_rmt_tabpage
end type
type dw_edit from u_rmt_dw within u_transaction_tabpage
end type
type cb_add from u_rmt_commandbutton within u_transaction_tabpage
end type
type cb_delete from u_rmt_commandbutton within u_transaction_tabpage
end type
type st_1 from statictext within u_transaction_tabpage
end type
type st_2 from statictext within u_transaction_tabpage
end type
end forward

global type u_transaction_tabpage from u_rmt_tabpage
integer width = 3890
integer height = 1428
event type integer ue_calcbalance ( )
dw_edit dw_edit
cb_add cb_add
cb_delete cb_delete
st_1 st_1
st_2 st_2
end type
global u_transaction_tabpage u_transaction_tabpage

type variables
datawindowchild   idwc_type
datawindowchild   idwc_pay
end variables

on u_transaction_tabpage.create
int iCurrent
call super::create
this.dw_edit=create dw_edit
this.cb_add=create cb_add
this.cb_delete=create cb_delete
this.st_1=create st_1
this.st_2=create st_2
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_edit
this.Control[iCurrent+2]=this.cb_add
this.Control[iCurrent+3]=this.cb_delete
this.Control[iCurrent+4]=this.st_1
this.Control[iCurrent+5]=this.st_2
end on

on u_transaction_tabpage.destroy
call super::destroy
destroy(this.dw_edit)
destroy(this.cb_add)
destroy(this.cb_delete)
destroy(this.st_1)
destroy(this.st_2)
end on

event ue_init;call super::ue_init;this.dw_edit.settransobject(sqlca)
this.dw_edit.of_setRowManager(true)
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

dwitemstatus  l_stat
int           li_rows
int           li_row

this.dw_edit.accepttext()
li_rows = this.dw_edit.rowcount()
for li_row = 1 to li_rows
	l_stat = this.dw_edit.getitemstatus(li_row, 0, primary!)
   if l_stat = datamodified! or l_stat = newmodified! then
	 	/*  If a new quote, update the date_created  */	
		if l_stat = newmodified! then
      	dw_edit.object.date_created[li_row] = today()
		end if
		this.dw_edit.object.date_updated[li_row] = today()
		this.dw_edit.object.user_id[li_row] = gnv_app.of_getUserID()
	end if
next

return 1
end event

event ue_add;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  ue_add
//
//	Description:	Add a transaction
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


int  li_row
w_charter_order   lw_parent

lw_parent = iw_parent
li_row = this.dw_edit.of_insert(1)
if li_row > 0 then
	this.dw_edit.object.order_id[li_row] = lw_parent.tab_charter_order.tabpage_order.dw_edit.object.id[1]
   this.dw_edit.scrolltorow(li_row)	
	this.dw_edit.setfocus()
end if

return li_row

end event

event ue_delete;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  ue_delete
//
//	Description:	delete a transaction
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if isValid(this.dw_edit.inv_rowmanager) then
	this.dw_edit.inv_rowmanager.of_deleteselected()
end if

return 1

end event

event ue_predelete;call super::ue_predelete;if gnv_app.of_getAccess() <> gnv_app.ii_ADMIN then
	gnv_app.inv_msg.of_message("Only users with admin privileges are allowed to delete transactions from the system", exclamation!)
	return -2
end if

if isValid(dw_edit.inv_rowmanager) and not isNull(dw_edit.inv_rowmanager) then
	dw_edit.inv_rowmanager.of_setConfirmOnDelete(true)
end if

return 1
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
if li_access = gnv_app.ii_NORMAL or li_access = gnv_app.ii_POWER then
	this.cb_add.enabled = false
	this.cb_delete.enabled = false
end if
  //  End SCR7 RMT040202
  
return true
end event

type dw_edit from u_rmt_dw within u_transaction_tabpage
integer x = 27
integer y = 48
integer width = 3794
integer height = 1184
boolean bringtotop = true
string dataobject = "d_charter_order_transaction"
end type

event type integer ue_retrievedddw();call super::ue_retrievedddw;
int     li_result
//
//
//
//this.getchild("trans_type_id", idwc_type)
//this.getchild("payment_type", idwc_pay)
//idwc_type.settransobject(sqlca)
//idwc_pay.settransobject(sqlca)
//li_result = idwc_type.retrieve(21)
//li_result += idwc_pay.retrieve(16)
//
return li_result
//
end event

event itemchanged;int    li_row
string ls_ind
dec    ldec_amount
int    li_trancode


if dwo.name = "trans_type_id" or dwo.name = "amount" then
	li_trancode = integer(data)
   select gen_ind_value 
	   into :ls_ind 
		from general_codes 
		where code_id = :li_trancode;
		
	if sqlca.sqlcode <> 0 then
	   return 0
	end if
	
	
	if dwo.name = "trans_type_id" then
  		ldec_amount = this.object.amount[row]
	else
  		ldec_amount = dec(this.gettext())
	end if
	if ls_ind = "CR" then
		if isnull(ldec_amount) then
			return 0
		end if
		ldec_amount = abs(ldec_amount) * -1
		this.object.amount[row] = ldec_amount
	else
		this.object.amount[row] = abs(ldec_amount)
	end if
end if

end event

event ue_enablecontrols;call super::ue_enablecontrols;w_charter_order  lw_parent
boolean          lb_updatemode


lw_parent = this.iw_parent
  
  // If order status is closed or cancelled, render all trip information read-only
lb_updatemode = ( (lw_parent.inv_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CLOSE and &
                   lw_parent.inv_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CANCEL) &
                   or isNull(lw_parent.inv_bo.ii_order_status) )
this.inv_base.of_protectDWColumns(this, lb_updatemode)

end event

event ue_setrequiredcolumns;call super::ue_setrequiredcolumns;if isValid(inv_base) and not isNull(inv_base) then
	inv_base.of_setRequiredColumn("amount", "Error occurred on the Transaction Tab:~r~nAmount cannot be blank")
	inv_base.of_setRequiredColumn("trans_date", "Error occurred on the Transaction Tab:~r~nTransaction Date cannot be blank")
	inv_base.of_setRequiredColumn("trans_type_id", "Error occurred on the Transaction Tab:~r~nTransaction Type must be selected")
	inv_base.of_setRequiredColumn("payment_type", "Error occurred on the Transaction Tab:~r~nPayment Tender must be selected")
end if

return 1
end event

event type boolean ue_checksecurity();call super::ue_checksecurity;//////////////////////////////////////////////////////////////////////////////
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
if this.rowcount() <= 0 then
	return true
end if

li_access = gnv_app.of_getAccess()
if li_access = gnv_app.ii_NORMAL or li_access = gnv_app.ii_POWER then
	this.setredraw(false)
	this.inv_base.of_protectDWColumns(this, false)
	this.setredraw(true)
end if
  //  End SCR7 RMT040202
  
return true  
end event

event ue_update;call super::ue_update;int  li_rc

li_rc = AncestorReturnValue
if li_rc = 1 then
	this.resetUpdate()
end if

return li_rc
end event
type cb_add from u_rmt_commandbutton within u_transaction_tabpage
integer x = 73
integer y = 1292
integer width = 393
integer height = 84
integer taborder = 20
boolean bringtotop = true
string text = "Add"
boolean default = true
end type

event clicked;parent.event ue_add()
end event

event constructor;iw_parent = parent.iw_parent
end event

type cb_delete from u_rmt_commandbutton within u_transaction_tabpage
integer x = 512
integer y = 1292
integer width = 393
integer height = 84
integer taborder = 20
boolean bringtotop = true
string text = "Delete"
end type

event clicked;long  ll_row

ll_row = parent.dw_edit.GetSelectedRow(0)

if isValid(parent.dw_edit.inv_rowmanager) then
   if ll_row > 0 then 
      parent.of_delete(ll_row)
	else
		messagebox(gnv_app.iapp_object.displayname, "Transaction Tab: A row must be selected in order to a delete a transaction", Exclamation!)
	end if
end if
end event

event constructor;iw_parent = parent.iw_parent
end event

type st_1 from statictext within u_transaction_tabpage
integer x = 1138
integer y = 1296
integer width = 78
integer height = 76
boolean bringtotop = true
integer textsize = -18
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = swiss!
string facename = "Arial"
long textcolor = 33554432
long backcolor = 67108864
boolean enabled = false
string text = "*"
alignment alignment = center!
boolean focusrectangle = false
end type

type st_2 from statictext within u_transaction_tabpage
integer x = 1234
integer y = 1304
integer width = 512
integer height = 76
boolean bringtotop = true
integer textsize = -9
integer weight = 400
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = swiss!
string facename = "Arial"
long textcolor = 33554432
long backcolor = 67108864
boolean enabled = false
string text = "Data Entry Required"
alignment alignment = center!
boolean focusrectangle = false
end type

