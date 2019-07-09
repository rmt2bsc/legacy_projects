$PBExportHeader$w_charter_order_search.srw
forward
global type w_charter_order_search from w_rmt_search_sheet_base
end type
type st_1 from statictext within w_charter_order_search
end type
type st_2 from statictext within w_charter_order_search
end type
end forward

global type w_charter_order_search from w_rmt_search_sheet_base
integer x = 5
integer y = 4
integer width = 4695
integer height = 3072
string title = "Charter Order Search"
string menuname = "m_greens_sheet"
windowstate windowstate = maximized!
long backcolor = 79741120
st_1 st_1
st_2 st_2
end type
global w_charter_order_search w_charter_order_search

on w_charter_order_search.create
int iCurrent
call super::create
if IsValid(this.MenuID) then destroy(this.MenuID)
if this.MenuName = "m_greens_sheet" then this.MenuID = create m_greens_sheet
this.st_1=create st_1
this.st_2=create st_2
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.st_1
this.Control[iCurrent+2]=this.st_2
end on

on w_charter_order_search.destroy
call super::destroy
if IsValid(MenuID) then destroy(MenuID)
destroy(this.st_1)
destroy(this.st_2)
end on

event ue_detail;
str_winargs  lstr  


if isNull(ai_row) then
	ai_row = dw_detail.getselectedRow(0)
end if
if ai_row <= 0 then
	gnv_app.inv_msg.of_message("A row must be selected in the ~"Charter Order Search Results~" section in order to display Charter Order Details", Exclamation!)
	return 0
end if

lstr.dataclassname = "n_charter_order_bo"  //"n_rmt_windatabject"
lstr.parms.parm[1] = this.dw_detail.object.client_id[ai_row]
lstr.parms.parm[2] = this.dw_detail.object.quote_id[ai_row]
lstr.parms.parm[3] = this.dw_detail.object.orders_id[ai_row]
lstr.isNew = false
openwithparm(w_charter_order, lstr)
this.event ue_search()

return 1

end event

event ue_enablecontrols;call super::ue_enablecontrols;//m_greens_sheet l_menu
boolean    lb_onoff
//l_menu = create m_greens_sheet

m_greens_sheet.m_edit.m_add.enabled = true


//m_greens_sheet.m_edit.m_details.enabled = false

lb_onoff = (dw_detail.rowcount() > 0)
m_greens_sheet.m_edit.m_details.enabled = lb_onoff
m_greens_sheet.m_edit.m_delete.enabled = lb_onoff
m_greens_sheet.m_file.m_print.enabled = lb_onoff
m_greens_sheet.m_database.enabled = false

  // This is to control the toolbar items belonging to the frame
m_greens_frame.m_edit.m_details.enabled = lb_onoff
m_greens_frame.m_edit.m_delete.enabled = lb_onoff
m_greens_frame.m_file.m_print.enabled = lb_onoff


end event

event ue_close;call super::ue_close;m_greens_frame.m_file.m_print.enabled = false
end event

event ue_print;call super::ue_print;int  li_client_id
int  li_quote_id
int  li_order_id
int  li_row
string  ls_msg
boolean  lb_print
str_winargs  lstr  
n_charter_order_doc_print_menu_bo lnv_bo

lb_print = AncestorReturnValue

li_row = this.dw_detail.getSelectedRow(0)

if li_row <= 0 then
   ls_msg = "A row must be selected before printing"
	if isValid(gnv_app.inv_msg) then
  	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
	else
	   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
	end if
	return false
end if


lstr.dataclassname = "n_charter_order_doc_print_menu_bo"
lstr.dw = dw_detail
openwithparm(w_charter_order_doc_print_menu, lstr)

return true
//
end event
event ue_search;call super::ue_search;n_rmt_utility  lnv_util

if dw_detail.rowcount() > 0 then
   lnv_util.of_sound("sound\Carbrake.wav")
end if
end event

event ue_delete;call super::ue_delete;int   li_quote
int   li_order
int   li_row
int   li_result
DECLARE CharterOrderDelete PROCEDURE FOR deleteCharterOrder  
         a_quote_id = :li_quote,   
         a_order_id = :li_order  ;
			

if gnv_app.of_getAccess() <> gnv_app.ii_ADMIN then
	gnv_app.inv_msg.of_message("Only users with admin privileges are allowed to delete orders from the system", exclamation!)
	return -2
end if

li_result = gnv_app.inv_msg.of_message("You are about to delete the selected order.   Are you sure?", question!, yesno!)
if li_result <> 1 then
	return 0
end if
	
li_row = dw_detail.getselectedrow(0)			
if not isnull(li_row) and li_row > 0 then
   li_quote = this.dw_detail.object.quote_id[li_row]
   li_order = this.dw_detail.object.orders_id[li_row]
end if

execute CharterOrderDelete;
if sqlca.sqldbcode <> 0 then
	messagebox(gnv_app.iapp_object.displayname, sqlca.sqlerrtext, stopsign!)
else
	close CharterOrderDelete;
	this.event ue_search()
	return 1
end if

close CharterOrderDelete;

return -1
			
			


end event

type dw_criteria from w_rmt_search_sheet_base`dw_criteria within w_charter_order_search
integer y = 120
integer width = 4585
integer height = 812
string dataobject = "d_charter_order_search_criteria"
end type

event dw_criteria::ue_retrievedddw;call super::ue_retrievedddw;/* RMT 012202 */

//datawindowchild  dwc
//
//this.getchild("orders_status", dwc)
//dwc.settransobject(sqlca)
//return dwc.retrieve(22)

return 1
end event

event dw_criteria::rbuttondown;call super::rbuttondown;//m_greens_sheet l_menu
//
//l_menu = create m_greens_sheet
m_greens_sheet.m_edit.m_x4.enabled = false
m_greens_sheet.m_edit.m_generalcodegroups.enabled = false
m_greens_sheet.m_edit.m_codetables.enabled = false
m_greens_sheet.m_edit.m_states.enabled = false
m_greens_sheet.m_edit.m_city.enabled = false
m_greens_sheet.m_edit.m_users.enabled = false
m_greens_sheet.m_edit.m_-1.enabled = false
m_greens_sheet.m_file.m_company.enabled = false
m_greens_sheet.m_file.m_client.enabled = false
m_greens_sheet.m_edit.m_add.enabled = false
m_greens_sheet.m_edit.m_delete.enabled = false
m_greens_sheet.m_edit.m_details.enabled = false

parent.event ue_enablecontrols()
m_greens_sheet.m_edit.PopMenu(PointerX(), PointerY())
end event

type dw_detail from w_rmt_search_sheet_base`dw_detail within w_charter_order_search
integer y = 1060
integer width = 4585
integer height = 1312
string dataobject = "d_charter_order_search_details"
end type

event dw_detail::ue_retrievedddw;call super::ue_retrievedddw;/* RMT 012202 */

//datawindowchild  dwc
//
//this.getchild("orders_status", dwc)
//dwc.settransobject(sqlca)
//return dwc.retrieve(22)

return 1
end event

event dw_detail::rbuttondown;call super::rbuttondown;//m_greens_sheet l_menu
boolean    lb_onoff
//
//l_menu = create m_greens_sheet
//
m_greens_sheet.m_edit.m_x4.enabled = false
m_greens_sheet.m_edit.m_generalcodegroups.enabled = false
m_greens_sheet.m_edit.m_codetables.enabled = false
m_greens_sheet.m_edit.m_states.enabled = false
m_greens_sheet.m_edit.m_city.enabled = false
m_greens_sheet.m_edit.m_users.enabled = false
m_greens_sheet.m_edit.m_-1.enabled = false
m_greens_sheet.m_file.m_company.enabled = false
m_greens_sheet.m_file.m_client.enabled = false
m_greens_sheet.m_edit.m_add.enabled = true

lb_onoff = (this.rowcount() > 0)
m_greens_sheet.m_edit.m_delete.enabled = lb_onoff
m_greens_sheet.m_edit.m_details.enabled = lb_onoff
//m_greens_sheet.m_edit.m_search.enabled = false
//m_greens_sheet.m_edit.m_reset.enabled = false
m_greens_sheet.m_edit.PopMenu(PointerX(), PointerY() + 450)




//m_greens_sheet.m_edit.PopMenu(PointerX(), PointerY() + 450)
end event

type st_1 from statictext within w_charter_order_search
integer x = 32
integer y = 28
integer width = 1248
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
string text = "Charter Order Search Criteria"
boolean focusrectangle = false
end type

type st_2 from statictext within w_charter_order_search
integer x = 32
integer y = 960
integer width = 1225
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
string text = "Charter Order Search Results"
boolean focusrectangle = false
end type

