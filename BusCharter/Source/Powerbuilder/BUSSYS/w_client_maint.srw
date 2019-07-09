$PBExportHeader$w_client_maint.srw
forward
global type w_client_maint from w_rmt_search_maintenance
end type
type st_1 from statictext within w_client_maint
end type
type st_2 from statictext within w_client_maint
end type
type st_3 from statictext within w_client_maint
end type
end forward

global type w_client_maint from w_rmt_search_maintenance
integer x = 91
integer y = 288
integer width = 4411
integer height = 2580
string title = "Client Maintenance"
long backcolor = 80269524
st_1 st_1
st_2 st_2
st_3 st_3
end type
global w_client_maint w_client_maint

type variables
datawindowchild dwc_city1
datawindowchild dwc_city2
datawindowchild dwc_city3

n_charter_order_bo   inv_bo

end variables

on w_client_maint.create
int iCurrent
call super::create
this.st_1=create st_1
this.st_2=create st_2
this.st_3=create st_3
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.st_1
this.Control[iCurrent+2]=this.st_2
this.Control[iCurrent+3]=this.st_3
end on

on w_client_maint.destroy
call super::destroy
destroy(this.st_1)
destroy(this.st_2)
destroy(this.st_3)
end on

event ue_preupdate;call super::ue_preupdate;int  li_row
int  li_rows
int  li_key
int  li_updatecount
string  ls_value
string ls_comments
dwItemStatus l_stat
n_rmt_utility    lnv_util
long             ll_id
boolean          lb_assignkey
 
li_updatecount = AncestorReturnValue

if (li_updatecount) <= 0 then
	return li_updatecount
end if

li_rows = dw_result.rowcount()
li_updatecount = 0
for li_row = 1 to li_rows
   l_stat = dw_detail.getitemstatus(li_row, 0, primary!)
   if l_stat = newmodified! or l_stat = datamodified! then
		ls_comments = dw_detail.object.comments_comment_text[li_row]
		li_key = dw_detail.object.comment_id[li_row]
		if isNull(ls_comments) or len(ls_comments) <= 0 then
			continue
		end if
		li_key = lnv_util.of_updatecomments(li_key, ls_comments)
		if isnull(li_key) or li_key <= 0 then
			continue
		end if
	   dw_detail.object.comment_id[li_row] = li_key

		ls_value = dw_detail.object.IsBillingExact[li_row]
		if isnull(ls_value) or ls_value = space(len(ls_value)) then
			dw_detail.object.IsBillingExact[li_row] = "N"
		end if
		li_updatecount++
  end if
next

return (dw_detail.modifiedcount() + dw_detail.deletedcount())
  
end event

event ue_close();call super::ue_close;int  li_row

li_row = inv_data.idw_data.getselectedrow(0)
if li_row <= 0 then
	return
end if
inv_bo.ii_client_id = inv_data.idw_data.object.id[li_row]
inv_data = inv_bo

end event

event ue_add;call super::ue_add;int  li_row
long  ll_key

li_row = AncestorReturnValue

dw_detail.object.contact_company[li_row] = 1000  // Default to No Company
dw_detail.object.isBillingExact[li_row] = 'N' // Default isBillingExact to "No"

// Determine the first key value
select max(id) into :ll_key from client;
if ll_key = 0 or isNull(ll_key) then
	if li_row = 1 then
		dw_detail.object.id[li_row] = 157
	end if
end if

dw_detail.setFocus()

return 1
end event

event ue_preopen;call super::ue_preopen;if not isnull(istr_winargs.busobj) and isValid(istr_winargs.busobj) then
	inv_bo = istr_winargs.busobj
	inv_bo.iw_parent = istr_winargs.parentwin
end if
end event

event type integer ue_predelete();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_predelete
//
//	Arguments:  none
//
//	Returns:  int
//
//	Description:	Checks if deleting selected client violates any database
//                constraints.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR     Date           Developer          Description
// =====  =============  ==============  ====================================
//    10   04/03/2002    RMT             Added a check to determine if 
//                                       the client selected for deletion is
//                                       associated with other entities in the
//                                       database.   RMT040302
//////////////////////////////////////////////////////////////////////////////
datastore  ds
int        li_count
int        li_row
int        li_client_id
String     ls_client
String     ls_msg


if gnv_app.of_getAccess() <> gnv_app.ii_ADMIN then
	gnv_app.inv_msg.of_message("Only users with admin privileges are allowed to delete Clients from the system", exclamation!)
	return -2
end if

ds = create datastore
ds.dataobject = "d_exist_client"
ds.settransobject(sqlca)

li_row = dw_result.getselectedrow(0)
li_client_id = dw_result.object.id[li_row]
if ds.retrieve(li_client_id) > 0 then
	ls_client += string(dw_result.object.contact_fname[li_row])
   ls_client += space(1) + string(dw_result.object.contact_lname[li_row])
   ls_msg = "cannot be deleted.  ~r~nCheck if client is asscociated with an order in the system!"
	gnv_app.inv_msg.of_message("Client, " + string(li_client_id) + " - " + ls_client + ", " + ls_msg, stopsign!)
	return -1
end if

if isValid(dw_result.inv_rowmanager) and not isNull(dw_result.inv_rowmanager) then
	dw_result.inv_rowmanager.of_setConfirmOnDelete(true)
end if

return 1
end event
event type boolean ue_checksecurity(powerobject apo_control[]);call super::ue_checksecurity;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_CheckSecurity
//
//	Access:  public
//
//	Arguments:	apo_control array of controls to check for security
//
//	Returns:  boolean
//	         true - success
//	         false - failure
//
//	Description:  Disable add, delete, and save buttons if normal user
//
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

if gnv_app.of_getAccess() = gnv_app.ii_NORMAL then
	cb_add.enabled = false
	cb_delete.enabled = false
	cb_1.enabled = false
end if

return true
	
end event

event ue_search();call super::ue_search;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_Search
//
//	Access:  public
//
//	Arguments:	none
//
//	Returns:  none
//
//	Description:  post serach logic
//
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
//    7   04/02/2002   RMT        Protects the fields for normal user
//////////////////////////////////////////////////////////////////////////////

  //  Begin SCR7 RMT040202
if dw_result.rowcount() <= 0 then
	return
end if

if gnv_app.of_getAccess() = gnv_app.ii_NORMAL then
	dw_detail.setredraw(false)
	dw_detail.inv_base.of_protectDWColumns(dw_detail, false)
	dw_detail.setredraw(true)
end if
  //  End SCR7 RMT040202
end event

type dw_result from w_rmt_search_maintenance`dw_result within w_client_maint
integer x = 9
integer y = 448
integer width = 3927
integer height = 444
integer taborder = 20
string dataobject = "d_client_maint_result"
end type

event dw_result::rowfocuschanged;call super::rowfocuschanged;dwc_city2.setfilter("")
dwc_city3.setfilter("")
dwc_city2.filter()
dwc_city3.filter()
end event

type cb_ok from w_rmt_search_maintenance`cb_ok within w_client_maint
integer x = 4023
integer taborder = 100
end type

type cb_cancel from w_rmt_search_maintenance`cb_cancel within w_client_maint
integer x = 4023
end type

type dw_criteria from w_rmt_search_maintenance`dw_criteria within w_client_maint
integer y = 100
integer width = 3913
integer height = 236
integer taborder = 10
string dataobject = "d_client_maint_criteria"
end type

event dw_criteria::ue_retrievedddw;call super::ue_retrievedddw;
//
this.getchild("contact_city", dwc_city1)
//dwc_city1.settransobject(sqlca)
//dwc_city1.retrieve(0)
//
return dwc_city1.rowcount()
//
end event

event itemchanged;int  li_row
string  ls_value
string  ls_filter


choose case dwo.name
	case "contact_state_id"
		ls_value = data
		ls_filter = "state_id = " + ls_value
		dwc_city1.setfilter(ls_filter)
		dwc_city1.filter()
		this.object.contact_city[1] = dwc_city1.getitemnumber(1, "city_id")
end choose

end event

type cb_search from w_rmt_search_maintenance`cb_search within w_client_maint
integer x = 4023
integer taborder = 40
end type

type cb_reset from w_rmt_search_maintenance`cb_reset within w_client_maint
integer x = 4023
integer taborder = 50
end type

type dw_detail from w_rmt_search_maintenance`dw_detail within w_client_maint
integer x = 9
integer y = 1008
integer width = 3931
integer height = 1444
integer taborder = 30
string dataobject = "d_client_maint_detail"
boolean vscrollbar = false
end type

event dw_detail::ue_retrievedddw;call super::ue_retrievedddw;
this.getchild("contact_city", dwc_city2)
dwc_city2.settransobject(sqlca)
dwc_city2.retrieve(0)

this.getchild("billing_city", dwc_city3)
dwc_city3.settransobject(sqlca)
dwc_city3.retrieve(0)


return dwc_city2.rowcount() + dwc_city3.rowcount()
end event

event dw_detail::itemchanged;int  li_row
string  ls_value
string  ls_filter
string  ls_null
int     li_null


setnull(ls_null)
setnull(li_null)

choose case dwo.name
	case "contact_state_id"
		ls_value = data
		ls_filter = "state_id = " + ls_value
		dwc_city2.setfilter(ls_filter)
		dwc_city2.filter()
		this.object.contact_city[row] = dwc_city2.getitemnumber(1, "city_id")
	case "billing_state_id"
		ls_value = data
		ls_filter = "state_id = " + ls_value
		dwc_city3.setfilter(ls_filter)
		dwc_city3.filter()
		this.object.billing_city[row] = dwc_city3.getitemnumber(1, "city_id")
	case "isbillingexact"
		if data = 'Y' then
			this.setitem(row, "billing_fname", this.object.contact_fname[row])
			this.setitem(row, "billing_lname", this.object.contact_lname[row])
			this.setitem(row, "billing_company", this.object.contact_company[row])
			this.setitem(row, "billing_address1", this.object.contact_address1[row])
			this.setitem(row, "billing_address2", this.object.contact_address2[row])
			this.setitem(row, "billing_address3", this.object.contact_address3[row])
			this.setitem(row, "billing_city", this.object.contact_city[row])
			this.setitem(row, "billing_state_id", this.object.contact_state_id[row])
			this.setitem(row, "billing_zip", this.object.contact_zip[row])
			this.setitem(row, "billing_phone", this.object.contact_phone[row])
			this.setitem(row, "billing_fax", this.object.contact_fax[row])
		elseif data = 'N' then
			this.setitem(row, "billing_fname", ls_null)
			this.setitem(row, "billing_lname", ls_null)
			this.setitem(row, "billing_address1", ls_null)
			this.setitem(row, "billing_address2", ls_null)
			this.setitem(row, "billing_address3", ls_null)
			this.setitem(row, "billing_city", li_null)
			this.setitem(row, "billing_state_id", li_null)
			this.setitem(row, "billing_zip", ls_null)
			this.setitem(row, "billing_phone", ls_null)
			this.setitem(row, "billing_fax", ls_null)
		end if
end choose

end event

event dw_detail::ue_setrequiredcolumns;call super::ue_setrequiredcolumns;

if isValid(inv_base) and not isNull(inv_base) then
	inv_base.of_setRequiredColumn("contact_fname", "Contact's first name cannot be blank")
	inv_base.of_setRequiredColumn("contact_lname", "Contact's last name cannot be blank")
	inv_base.of_setRequiredColumn("contact_phone", "Contact's phone number cannot be blank")
	inv_base.of_setRequiredColumn("billing_fname", "Billing's first name cannot be blank")
	inv_base.of_setRequiredColumn("billing_lname", "Billing's last name cannot be blank")
	inv_base.of_setRequiredColumn("billing_phone", "Billing's phone number cannot be blank")
end if

return 1
end event

type cb_add from w_rmt_search_maintenance`cb_add within w_client_maint
integer x = 4023
integer taborder = 60
end type

type cb_delete from w_rmt_search_maintenance`cb_delete within w_client_maint
integer x = 4023
integer taborder = 70
end type

type cb_1 from w_rmt_search_maintenance`cb_1 within w_client_maint
integer x = 4023
integer taborder = 80
end type

type st_1 from statictext within w_client_maint
integer x = 18
integer width = 1051
integer height = 96
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
string text = "Client Search Criteria"
boolean focusrectangle = false
end type

type st_2 from statictext within w_client_maint
integer x = 14
integer y = 348
integer width = 882
integer height = 84
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
string text = "Client Search Results"
boolean focusrectangle = false
end type

type st_3 from statictext within w_client_maint
integer x = 18
integer y = 908
integer width = 987
integer height = 88
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
string text = "Client Details (Editable)"
boolean focusrectangle = false
end type

