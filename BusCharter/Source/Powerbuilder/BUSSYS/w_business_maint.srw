$PBExportHeader$w_business_maint.srw
forward
global type w_business_maint from w_rmt_search_maintenance
end type
type st_1 from statictext within w_business_maint
end type
type st_2 from statictext within w_business_maint
end type
type st_3 from statictext within w_business_maint
end type
end forward

global type w_business_maint from w_rmt_search_maintenance
integer x = 174
integer y = 432
integer width = 4192
integer height = 2164
long backcolor = 80269524
st_1 st_1
st_2 st_2
st_3 st_3
end type
global w_business_maint w_business_maint

on w_business_maint.create
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

on w_business_maint.destroy
call super::destroy
destroy(this.st_1)
destroy(this.st_2)
destroy(this.st_3)
end on

event ue_add;call super::ue_add;dw_detail.setFocus()

return 1
end event

event ue_postopen;call super::ue_postopen;inv_sql.is_othersql = "  ORDER BY  business.longname asc, business.id asc"

end event

event type integer ue_predelete();call super::ue_predelete;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_predelete
//
//	Arguments:  none
//
//	Returns:  int
//
//	Description:	Checks if deleting selected company violates any database
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
//                                       the company selected for deletion is
//                                       associated with other entities in the
//                                       database.   RMT040302
//////////////////////////////////////////////////////////////////////////////

datastore  ds
int        li_count
int        li_company_id
int        li_row
int        li_result
String     ls_company
String     ls_msg


if gnv_app.of_getAccess() <> gnv_app.ii_ADMIN then
	gnv_app.inv_msg.of_message("Only users with admin privileges are allowed to delete Companies from the system", exclamation!)
	return -2
end if

ds = create datastore
ds.dataobject = "d_exist_company"
ds.settransobject(sqlca)

li_row = dw_result.getselectedrow(0)
li_company_id = dw_result.object.id[li_row]

if ds.retrieve(li_company_id) > 0 then
	ls_company = string(dw_result.object.longname[li_row])
   ls_msg = "cannot be deleted.  ~r~nCheck if selected company is asscociated with an order or a client in the system!"
	gnv_app.inv_msg.of_message("Comapny, " + string(li_company_id) + " - " + ls_company + ", " + ls_msg, stopsign!)
	return -1
end if

if isValid(dw_result.inv_rowmanager) and not isNull(dw_result.inv_rowmanager) then
	dw_result.inv_rowmanager.of_setConfirmOnDelete(true)
end if

return 1
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
type dw_result from w_rmt_search_maintenance`dw_result within w_business_maint
integer y = 720
integer width = 2597
string dataobject = "d_business_maint_result"
end type

event dw_result::ue_retrievedddw;call super::ue_retrievedddw;datawindowchild dwc_bustype

this.getchild("business_type", dwc_bustype)
dwc_bustype.settransobject(sqlca)
dwc_bustype.retrieve(7)

return dwc_bustype.rowcount()

end event

type cb_ok from w_rmt_search_maintenance`cb_ok within w_business_maint
integer x = 3753
integer y = 736
integer taborder = 90
end type

type cb_cancel from w_rmt_search_maintenance`cb_cancel within w_business_maint
integer x = 3753
integer y = 624
integer taborder = 100
end type

type dw_criteria from w_rmt_search_maintenance`dw_criteria within w_business_maint
integer y = 140
integer width = 3602
integer height = 420
string dataobject = "d_business_maint_criteria"
end type

event dw_criteria::ue_retrievedddw;call super::ue_retrievedddw;datawindowchild dwc_bustype
datawindowchild dwc_status


this.getchild("business_type", dwc_bustype)
dwc_bustype.settransobject(sqlca)
dwc_bustype.retrieve(7)

this.getchild("status", dwc_status)
dwc_status.settransobject(sqlca)
dwc_status.retrieve(24)

return dwc_status.rowcount() + dwc_bustype.rowcount()

end event

type cb_search from w_rmt_search_maintenance`cb_search within w_business_maint
integer x = 3753
integer taborder = 80
end type

type cb_reset from w_rmt_search_maintenance`cb_reset within w_business_maint
integer x = 3753
integer taborder = 70
end type

type dw_detail from w_rmt_search_maintenance`dw_detail within w_business_maint
integer x = 14
integer y = 1420
integer width = 3278
integer height = 632
integer taborder = 60
string dataobject = "d_business_maint_detail"
boolean vscrollbar = false
end type

event dw_detail::ue_retrievedddw;call super::ue_retrievedddw;datawindowchild dwc_bustype
datawindowchild dwc_status


this.getchild("business_type", dwc_bustype)
dwc_bustype.settransobject(sqlca)
dwc_bustype.retrieve(7)

this.getchild("status", dwc_status)
dwc_status.settransobject(sqlca)
dwc_status.retrieve(24)

return dwc_status.rowcount() + dwc_bustype.rowcount()

end event

event dw_detail::ue_setrequiredcolumns;call super::ue_setrequiredcolumns;if isValid(inv_base) and not isNull(inv_base) then
	inv_base.of_setRequiredColumn("longname", "Name field cannot be blank")
	inv_base.of_setRequiredColumn("business_type", "Business Type must be selected...cannot be blank")
end if

return 1
end event

type cb_add from w_rmt_search_maintenance`cb_add within w_business_maint
integer x = 3753
integer taborder = 50
end type

type cb_delete from w_rmt_search_maintenance`cb_delete within w_business_maint
integer x = 3753
integer taborder = 40
end type

type cb_1 from w_rmt_search_maintenance`cb_1 within w_business_maint
integer x = 3753
integer y = 516
integer taborder = 30
end type

type st_1 from statictext within w_business_maint
integer x = 27
integer y = 24
integer width = 1102
integer height = 104
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
string text = "Company Search Criteria"
boolean focusrectangle = false
end type

type st_2 from statictext within w_business_maint
integer x = 32
integer y = 616
integer width = 1033
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
string text = "Company Search Results"
boolean focusrectangle = false
end type

type st_3 from statictext within w_business_maint
integer x = 32
integer y = 1316
integer width = 1138
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
string text = "Company Details (Editable)"
boolean focusrectangle = false
end type

