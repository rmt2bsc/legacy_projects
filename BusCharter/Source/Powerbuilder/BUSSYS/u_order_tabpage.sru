$PBExportHeader$u_order_tabpage.sru
forward
global type u_order_tabpage from u_rmt_tabpage
end type
type dw_edit from u_rmt_dw within u_order_tabpage
end type
end forward

global type u_order_tabpage from u_rmt_tabpage
integer width = 3918
integer height = 1676
event type integer ue_setneworder ( integer ai_quote_id )
event ue_test ( )
dw_edit dw_edit
end type
global u_order_tabpage u_order_tabpage

event ue_setneworder;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_setNewOrder
//
//	Arguments:	integer - quote_id
//
//	Returns:  integer
//	 1 = updates are successful
//	 0 = No updates occurred
//	-1 = error
//
//	Description:	Notifies the Order tab to set up new order
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int  li_result
w_charter_order    lw_parent

lw_parent = this.iw_parent


this.dw_edit.object.quote_id[1] = ai_quote_id
//this.dw_edit.object.status[1] = 203    /* RMT 012202 */

lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.status[1] = lw_parent.inv_bo.ii_ORDER_ORDER
li_result = this.of_preupdate()
if li_result <> 1 then
	return -1
end if

if this.dw_edit.update() <> 1 then
	return -2
end if

return 1
end event

event ue_test;decimal ldec_total

ldec_total = this.dw_edit.getitemnumber(1, "c_trip_total")
ldec_total = ldec_total
end event

on u_order_tabpage.create
int iCurrent
call super::create
this.dw_edit=create dw_edit
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_edit
end on

on u_order_tabpage.destroy
call super::destroy
destroy(this.dw_edit)
end on

event ue_init;call super::ue_init;this.dw_edit.settransobject(sqlca)

//if gnv_app.of_getAccess() = 218 then
//   this.dw_edit.object.overridestatus.protect = 0
//else 
//	this.dw_edit.object.overridestatus.protect = 1
//end if

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

dwitemstatus  l_rowstat
int           li_quote
long          ll_key

//  Process the comments of the Trip tabpage
l_rowstat = this.dw_edit.getitemstatus(1, 0, primary!)
if l_rowstat = datamodified! or l_rowstat = newmodified! then
	if l_rowstat = newmodified! then
		this.dw_edit.object.date_created[1] = today()
		li_quote = parent.dynamic of_getNewQuoteID()
		this.dw_edit.object.quote_id[1] = li_quote
	end if
	this.dw_edit.object.date_updated[1] = today()
	this.dw_edit.object.user_id[1] = gnv_app.of_getUserID()
end if


return 1
end event

type dw_edit from u_rmt_dw within u_order_tabpage
integer x = 46
integer y = 36
integer width = 3867
integer height = 1576
boolean bringtotop = true
string dataobject = "d_charter_order_order_maint"
boolean vscrollbar = false
boolean border = false
borderstyle borderstyle = stylebox!
end type

event ue_retrievedddw;call super::ue_retrievedddw;datawindowchild dwc_dropoff
datawindowchild dwc_status


this.getchild("dropoff_collect_tender", dwc_dropoff)
dwc_dropoff.settransobject(sqlca)
dwc_dropoff.retrieve(16)

return dwc_dropoff.rowcount()


end event

event itemchanged;w_charter_order    lw_parent

lw_parent = this.iw_parent



return 0


end event

event buttonclicked;str_winargs          lstr  
n_charter_order_bo   lnv_data
w_charter_order      lw_parent
int                  li_order_id
int                  li_result
dec {2}              ldec_total


if isnull(this.iw_parent) or not isValid(this.iw_parent) then
	return -1
end if

lw_parent = this.iw_parent

if dwo.name = "cb_bus_detail" then
   lstr.dataclassname = "n_charter_order_bo"
   lstr.parms.parm[1] = this.object.id[1]  // Order ID
   lstr.isNew = false
	lstr.data = lw_parent.inv_bo
   openwithparm(w_charter_order_bus_detail, lstr)
	if not isValid(message.powerobjectparm) or isnull(message.powerobjectparm) then
		return 0
	end if
	lnv_data = message.powerobjectparm
	this.object.buscost[1] = lnv_data.idec_bustotal
	lw_parent.inv_bo.of_updateBusCount(lnv_data.ii_buscount)
	this.object.buscount[1] = lnv_data.ii_buscount
	
elseif dwo.name = "cb_invoice" then
   this.accepttext()
	ldec_total = this.getitemnumber(1, "buscount")
	ldec_total = this.getitemnumber(1, "c_trip_total")
	if ldec_total = 0 then
		gnv_app.inv_msg.of_message("Cannot advance to invoice status at this time since~r~nno charges have been applied to the current Charter Order.", stopsign!)
      return 0
	end if
	li_order_id = this.object.id[1]
	li_result = lw_parent.inv_bo.of_setupInvoice(li_order_id)  
	if li_result <> 1 then
		messagebox(gnv_app.iapp_object.displayname, "Problem creating Invoice")
		return 0
	end if
   lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.status[1] = lw_parent.inv_bo.ii_ORDER_INVOICED      /* RMT 012202 */
	lw_parent.event ue_enablecontrols()
	lw_parent.tab_charter_order.selecttab("tabpage_invoice")
end if

return 1
end event

event ue_enablecontrols;call super::ue_enablecontrols;w_charter_order  lw_parent
boolean          lb_updatemode


lw_parent = this.iw_parent
  
  // If order status is closed or cancelled, render all trip information read-only
lb_updatemode = (lw_parent.inv_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CLOSE and &
                 lw_parent.inv_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CANCEL)
this.inv_base.of_protectDWColumns(this, lb_updatemode)



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

  //  Begin SCR7 RMT040202
if this.rowcount() <= 0 then
	return true
end if

if gnv_app.of_getAccess() = gnv_app.ii_NORMAL then
	this.setredraw(false)
	this.inv_base.of_protectDWColumns(this, false)
	this.setredraw(true)
end if
  //  End SCR7 RMT040202
  
return true
end event

