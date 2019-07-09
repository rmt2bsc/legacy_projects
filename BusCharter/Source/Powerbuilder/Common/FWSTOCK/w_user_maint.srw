$PBExportHeader$w_user_maint.srw
$PBExportComments$User Maintenance
forward
global type w_user_maint from w_rmt_search_maintenance
end type
type st_1 from statictext within w_user_maint
end type
type st_2 from statictext within w_user_maint
end type
type st_3 from statictext within w_user_maint
end type
end forward

global type w_user_maint from w_rmt_search_maintenance
integer height = 2020
long backcolor = 80269524
st_1 st_1
st_2 st_2
st_3 st_3
end type
global w_user_maint w_user_maint

on w_user_maint.create
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

on w_user_maint.destroy
call super::destroy
destroy(this.st_1)
destroy(this.st_2)
destroy(this.st_3)
end on

event ue_add;call super::ue_add;dw_detail.setfocus()

return 1
end event

event type integer ue_preupdate(powerobject apo_control[]);call super::ue_preupdate;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preupdate
//
//	Access:  public
//
//	Arguments:	apo_control array of controls to check for security
//
//	Returns:  boolean
//	         true - success
//	         false - failure
//
//	Description:  Perform pre update logic
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
//   12   04/06/2002   RMT       Added logic to recognize deleted rows as 
//                               modifications.
//////////////////////////////////////////////////////////////////////////////
int  li_row 
int  li_rows
int  li_actualupdatecount
dwitemstatus  l_stat

dw_result.accepttext()
if (dw_detail.modifiedcount() + dw_detail.deletedcount()) <= 0 then
	return 0
end if

li_rows = dw_result.rowcount()
li_actualupdatecount = 0
for li_row = 1 to li_rows
	l_stat = dw_detail.getitemstatus(li_row, 0, primary!)
   if l_stat = newmodified! or l_stat = datamodified! then
		if l_stat = newmodified! then
         dw_detail.object.date_created[li_row] = today()
         dw_detail.object.password[li_row] = dw_detail.object.login_id[li_row]
		end if
      dw_detail.object.date_updated[li_row] = today()
      dw_detail.object.user_id[li_row] = gnv_app.of_getUserID()
	   li_actualupdatecount++
   end if
next

return li_actualupdatecount + dw_result.deletedcount()   // SCR012 RMT 040602
  

end event
type dw_result from w_rmt_search_maintenance`dw_result within w_user_maint
integer x = 14
integer y = 664
integer width = 2569
string dataobject = "d_user_result"
boolean hscrollbar = false
boolean livescroll = false
end type

type cb_ok from w_rmt_search_maintenance`cb_ok within w_user_maint
end type

type cb_cancel from w_rmt_search_maintenance`cb_cancel within w_user_maint
end type

type dw_criteria from w_rmt_search_maintenance`dw_criteria within w_user_maint
integer y = 120
integer height = 416
string dataobject = "d_user_criteria"
end type

type cb_search from w_rmt_search_maintenance`cb_search within w_user_maint
end type

type cb_reset from w_rmt_search_maintenance`cb_reset within w_user_maint
end type

type dw_detail from w_rmt_search_maintenance`dw_detail within w_user_maint
integer x = 27
integer y = 1372
integer width = 2843
integer height = 500
string dataobject = "d_user_detail"
boolean vscrollbar = false
end type

event dw_detail::ue_retrievedddw;call super::ue_retrievedddw;datawindowchild  dwc

this.getchild("access_level", dwc)
dwc.settransobject(sqlca)
dwc.retrieve(23)

return dwc.rowcount()
end event

event type boolean dw_detail::ue_validate();call super::ue_validate;string  ls_login
long    ll_count
boolean  lb_result
dwitemstatus   l_stat

lb_result = AncestorReturnValue
if not lb_result then
	return false
end if

ls_login = dw_detail.object.login_id[this.getrow()]
l_stat = dw_result.getitemstatus(this.getrow(), 0, primary!)
select count(*)
  into :ll_count
  from user_location
  where lcase(login_id) = lcase(:ls_login);
  
if ll_count >= 1 and l_stat = NewModified! then
	gnv_app.inv_msg.of_message("Cannot add User ID, " + ls_login + ".  Already exist in the system!~r~nTry Again")
	return false
end if

return true
 



end event

event dw_detail::ue_setrequiredcolumns;call super::ue_setrequiredcolumns;if isValid(inv_base) and not isNull(inv_base) then
	inv_base.of_setRequiredColumn("login_id", "User ID cannot be blank")
	inv_base.of_setRequiredColumn("firstname", "First Name cannot be blank")
	inv_base.of_setRequiredColumn("lastname", "Last Name cannot be blank")
	inv_base.of_setRequiredColumn("title", "Title cannot be blank")
	inv_base.of_setRequiredColumn("access_level", "Access Level cannot be blank")
end if

return 1
end event

type cb_add from w_rmt_search_maintenance`cb_add within w_user_maint
end type

type cb_delete from w_rmt_search_maintenance`cb_delete within w_user_maint
end type

type cb_1 from w_rmt_search_maintenance`cb_1 within w_user_maint
end type

type st_1 from statictext within w_user_maint
integer x = 27
integer y = 28
integer width = 1051
integer height = 76
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
string text = "User Search Criteria"
boolean focusrectangle = false
end type

type st_2 from statictext within w_user_maint
integer x = 32
integer y = 568
integer width = 827
integer height = 76
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
string text = "User Search Results"
boolean focusrectangle = false
end type

type st_3 from statictext within w_user_maint
integer x = 32
integer y = 1264
integer width = 1275
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
string text = "User Details (Editable)"
boolean focusrectangle = false
end type

