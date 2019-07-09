$PBExportHeader$w_frame.srw
forward
global type w_frame from w_rmt_frame
end type
end forward

global type w_frame from w_rmt_frame
string title = ""
string menuname = "m_greens_frame"
windowstate windowstate = maximized!
long backcolor = 79741120
event ue_business_maint ( )
event ue_client_maint ( )
event ue_neworder ( )
event type integer ue_reconcileorders ( )
event ue_expense_maint ( )
end type
global w_frame w_frame

type variables

end variables

event ue_business_maint;str_winargs  lstr  

lstr.dataclassname = "n_charter_order_bo"
openwithparm(w_business_maint, lstr)

end event

event ue_client_maint;str_winargs  lstr  

lstr.dataclassname = "n_charter_order_bo"
openwithparm(w_client_maint, lstr)

end event

event ue_neworder;str_winargs  lstr  
int  li_null

lstr.dataclassname = "n_charter_order_bo"
lstr.parentwin = this
lstr.isNew =true

setNull(li_null)
lstr.parms.parm[1] = li_null
lstr.parms.parm[2] = li_null
lstr.parms.parm[3] = li_null
openwithparm(w_charter_order, lstr)
end event

event ue_reconcileorders;n_charter_order_bo  lnv_bo
int                 li_result
string              ls_msg            



lnv_bo = create n_charter_order_bo

li_result = lnv_bo.of_reconcileOrders()
if li_result = 0 then
	ls_msg = "There are no Charter Orders to be reconciled at this time!"
elseif li_result > 0 then
	ls_msg = "Total number of orders reconciled: " + string(li_result)
elseif li_result = -1 then
	ls_msg = "Reconciliation of orders failed!" + string(li_result)
end if

if isValid(gnv_app.inv_msg) and not isNull(gnv_app.inv_msg) then
  	gnv_app.inv_msg.of_message(ls_msg, exclamation!)
else
	messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!)
end if

return li_result
end event

event ue_expense_maint;open(w_expense_maint)

end event
on w_frame.create
call super::create
if IsValid(this.MenuID) then destroy(this.MenuID)
if this.MenuName = "m_greens_frame" then this.MenuID = create m_greens_frame
end on

on w_frame.destroy
call super::destroy
if IsValid(MenuID) then destroy(MenuID)
end on

event ue_ordersearch;
setnull(message.powerobjectparm)
opensheet(w_charter_order_search, gnv_app.of_getframe(), 1, layered!)

end event

event ue_enablecontrols();call super::ue_enablecontrols;m_greens_frame  l_menu
string          ls_user

l_menu = this.menuID
ls_user = gnv_app.of_getUserID()

if isNull(ls_user) or space(len(ls_user)) = "" then
	l_menu.m_file.m_login.enabled = true
   l_menu.m_edit.enabled = false
   l_menu.m_file.m_company.enabled = false
   l_menu.m_file.m_client.enabled = false
   l_menu.m_window.enabled = false
   l_menu.m_file.m_logout.enabled = false
   l_menu.m_file.m_open.enabled = false
   l_menu.m_file.m_new.enabled = false
   l_menu.m_file.m_close.enabled = false
   l_menu.m_file.m_print.enabled = false
   l_menu.m_file.m_save.enabled = false
	l_menu.m_file.m_changepassword.enabled = false
	l_menu.m_file.m_reconcilecharterorders.enabled = false
	l_menu.m_database.enabled = true
	this.Title = gnv_app.iapp_object.displayname
else
	l_menu.m_file.m_login.enabled = false
	l_menu.m_edit.enabled = true
   l_menu.m_file.m_company.enabled = true
   l_menu.m_file.m_client.enabled = true
   l_menu.m_window.enabled = true
   l_menu.m_file.m_logout.enabled = true
   l_menu.m_file.m_open.enabled = true
   l_menu.m_file.m_new.enabled = true
   l_menu.m_file.m_close.enabled = true
   l_menu.m_file.m_print.enabled = false
   l_menu.m_file.m_save.enabled = true
	l_menu.m_file.m_changepassword.enabled = true
	l_menu.m_file.m_reconcilecharterorders.enabled = true
	l_menu.m_database.enabled = false
	this.Title = gnv_app.iapp_object.displayname + " - User [" + ls_user + "]"
end if


  // Grant access to code table screens to admin only
  /* Start SCR7 RMT040202 */
if gnv_app.of_getAccess() = gnv_app.ii_ADMIN then
	l_menu.m_edit.enabled = true
	l_menu.m_edit.m_users.enabled = true
	l_menu.m_file.m_company.enabled = true
   l_menu.m_file.m_client.enabled = true
	l_menu.m_file.m_save.enabled = true
	l_menu.m_file.m_new.enabled = true
	l_menu.m_file.m_reconcilecharterorders.enabled = true
end if
if gnv_app.of_getAccess() = gnv_app.ii_POWER then
	l_menu.m_edit.enabled = true
	l_menu.m_edit.m_users.enabled = false
	l_menu.m_file.m_company.enabled = true
   l_menu.m_file.m_client.enabled = true
	l_menu.m_file.m_save.enabled = true
	l_menu.m_file.m_new.enabled = true
	l_menu.m_file.m_reconcilecharterorders.enabled = true
end if
if gnv_app.of_getAccess() = gnv_app.ii_NORMAL then
	l_menu.m_edit.enabled = false
	l_menu.m_file.m_company.enabled = true 
   l_menu.m_file.m_client.enabled = true  
	l_menu.m_file.m_reconcilecharterorders.enabled = false
	l_menu.m_file.m_save.enabled = false
	l_menu.m_file.m_new.enabled = false
end if
  /* End SCR7 RMT040202 */  

end event

event ue_dbbackup;call super::ue_dbbackup;
gnv_app.of_dbBackup()
end event

event ue_dbrecover;call super::ue_dbrecover;gnv_app.of_dbRecover()
end event

