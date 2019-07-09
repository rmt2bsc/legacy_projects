$PBExportHeader$w_charter_order_bus_detail.srw
forward
global type w_charter_order_bus_detail from w_rmt_response_master_detail_maint
end type
end forward

global type w_charter_order_bus_detail from w_rmt_response_master_detail_maint
integer x = 251
integer y = 368
integer width = 4242
integer height = 2428
long backcolor = 79741120
end type
global w_charter_order_bus_detail w_charter_order_bus_detail

type variables
n_charter_order_bo  inv_bo
n_charter_order_bo  inv_parent_bo
end variables

on w_charter_order_bus_detail.create
call super::create
end on

on w_charter_order_bus_detail.destroy
call super::destroy
end on

event ue_close;call super::ue_close;int  li_rows
int  li_ndx

li_rows = this.dw_detail.rowcount()
inv_bo.idec_bustotal = 0
if li_rows > 0 and not isNull(li_rows) then
   for li_ndx = 1 to li_rows
      inv_bo.idec_bustotal += this.dw_detail.getitemnumber(li_ndx, "c_trip_total")
	next
   inv_bo.ii_buscount = this.dw_edit.rowcount()
   inv_data = inv_bo
end if
end event

event ue_postopen();call super::ue_postopen;
//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postopen
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  performs post datawindow retrieval operations.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//////////////////////////////////////////////////////////////////////////////

dw_edit.of_setUpdateable(false)
dw_detail.of_setUpdateable(true)
this.event ue_enablecontrols()
dw_detail.setredraw(false)
this.of_checkSecurity()     // SCR7 RMT040202
dw_detail.setredraw(true)
dw_edit.setfocus()
end event

event ue_preopen;call super::ue_preopen;if not isnull(istr_winargs.busobj) and isValid(istr_winargs.busobj) then
	inv_bo = istr_winargs.busobj
	inv_bo.iw_parent = istr_winargs.parentwin
end if

if isValid(istr_winargs.data) and not isNull(istr_winargs.data) then
	inv_parent_bo = istr_winargs.data
end if
this.dw_edit.of_setArgument(1, istr_winargs.parms.parm[1])  // Order_id
end event

event ue_add;call super::ue_add;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_add
//
//	Arguments:	none
//
//	Returns:  integer (inserted row)
//
//	Description: Initializes the new row
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
//    11   04/04/2002 RMT        Initialized Minimum Hourly Rate Factor to 5
//                               hours when adding a order.
//////////////////////////////////////////////////////////////////////////////
int   li_row

li_row = AncestorReturnValue
if li_row <= 0 then
	return li_row
end if

this.dw_detail.object.orders_id[li_row] = integer(istr_winargs.parms.parm[1])
this.dw_detail.object.min_hour_factor[li_row] = gnv_app.ii_DEFAULTMINHOURFACTOR         // SCR11 RMT040402
this.dw_detail.object.c_buscount[li_row] =  li_row 

return li_row
end event

event ue_preupdate;call super::ue_preupdate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preupdate
//
//	Arguments:  powerobject
//
//	Returns:  int
//
//	Description:  Performs pre update routines
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
// 
//  SCR      DATE     Developer          Comments
// ------- ---------- ---------------    -------------------------------------
//  12      3/26/2005   RMT               Removed original code and focused all
//                                        update activity in the ue_update event.
//////////////////////////////////////////////////////////////////////////////
return 1
end event
event ue_enablecontrols;call super::ue_enablecontrols;this.inv_bo.of_enableControls(this.control)
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
	this.cb_add.enabled = false
	this.cb_delete.enabled = false
	this.cb_2.enabled = false
	this.setredraw(true)
end if
  //  End SCR7 RMT040202
  
return true  
end event

event ue_update;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_update
//
//	Arguments:  powerobject
//
//	Returns:  int
//
//	Description:  Performs custom datawindow updates by applying the changes to both 
//               bus_details and comments table.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
// 
//  SCR      DATE     Developer          Comments
// ------- ---------- ---------------    -------------------------------------
//  12      3/26/2005   RMT               Overrode script by adding custom logic
//                                        to update the bus_details and comment
//                                        tables.    This would prevent entries
//                                        from duplicating at save time.
//////////////////////////////////////////////////////////////////////////////


n_rmt_utility   lnv_util
string   ls_text
int      li_comment_id
int      li_rows
int      li_row
int      li_rc
dwItemStatus   l_stat

li_rows = dw_detail.rowcount()
setnull(li_comment_id)
for li_row = 1 to li_rows
	li_comment_id = this.dw_detail.object.special_instructions[li_row]
	ls_text = this.dw_detail.object.comment_text[li_row]
	l_stat = dw_detail.getItemStatus(li_row, 0, primary!)
	li_comment_id = lnv_util.of_updateComments(li_comment_id, ls_text)
	choose case l_stat
		case newModified!
			if li_comment_id > 0 and not isnull(li_comment_id) then
				this.dw_detail.object.special_instructions[li_row] = li_comment_id
			end if			   
		case dataModified!
		   // Do nothing
	end choose
next

li_rc = dw_detail.update()
return li_rc
end event
type cb_3 from w_rmt_response_master_detail_maint`cb_3 within w_charter_order_bus_detail
integer x = 3749
end type

type cb_1 from w_rmt_response_master_detail_maint`cb_1 within w_charter_order_bus_detail
integer x = 3749
end type

type cb_2 from w_rmt_response_master_detail_maint`cb_2 within w_charter_order_bus_detail
integer x = 3749
end type

type dw_edit from w_rmt_response_master_detail_maint`dw_edit within w_charter_order_bus_detail
integer x = 599
integer width = 2546
string dataobject = "d_charter_order_bus_list"
end type

type cb_delete from w_rmt_response_master_detail_maint`cb_delete within w_charter_order_bus_detail
integer x = 3749
end type

type cb_add from w_rmt_response_master_detail_maint`cb_add within w_charter_order_bus_detail
integer x = 3749
end type

type dw_detail from w_rmt_response_master_detail_maint`dw_detail within w_charter_order_bus_detail
integer x = 55
integer width = 3675
integer height = 1688
string dataobject = "d_charter_order_bus_detail"
end type

event dw_detail::ue_enablecontrols;call super::ue_enablecontrols;w_charter_order  lw_parent
boolean          lb_updatemode


lw_parent = inv_parent_bo.iw_parent
if isValid(inv_parent_bo) then
	  // If order status is closed or cancelled, render all trip information read-only
   lb_updatemode = (inv_parent_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CLOSE and inv_parent_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CANCEL)
   this.inv_base.of_protectDWColumns(this, lb_updatemode)
end if

end event

event dw_detail::ue_setrequiredcolumns;call super::ue_setrequiredcolumns;if isValid(inv_base) and not isNull(inv_base) then
	inv_base.of_setRequiredColumn("comment_text", "Special Instructions cannot be blank")	
end if

return 1

end event

event type boolean dw_detail::ue_checksecurity();//////////////////////////////////////////////////////////////////////////////
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
if li_access = gnv_app.ii_NORMAL then
//	this.setredraw(false)
	this.inv_base.of_protectDWColumns(this, false)
//	this.setredraw(true)
end if
  //  End SCR7 RMT040202
  
return true  
end event

