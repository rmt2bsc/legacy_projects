$PBExportHeader$u_trip_quote_tabpage.sru
forward
global type u_trip_quote_tabpage from u_rmt_tabpage
end type
type dw_edit from u_rmt_dw within u_trip_quote_tabpage
end type
end forward

global type u_trip_quote_tabpage from u_rmt_tabpage
integer width = 3081
integer height = 1760
dw_edit dw_edit
end type
global u_trip_quote_tabpage u_trip_quote_tabpage

type variables
long     il_bgcolor
end variables

on u_trip_quote_tabpage.create
int iCurrent
call super::create
this.dw_edit=create dw_edit
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_edit
end on

on u_trip_quote_tabpage.destroy
call super::destroy
destroy(this.dw_edit)
end on

event ue_init();call super::ue_init;this.dw_edit.settransobject(sqlca)

il_bgcolor = long(this.backcolor)  //553648127 // buttonface
if gnv_app.of_getAccess() = gnv_app.ii_ADMIN then
   this.dw_edit.object.overridestatus.protect = 0
else 
	this.dw_edit.object.overridestatus.protect = 1
end if
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
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

dwitemstatus  l_quoterowstat
dwitemstatus  l_quotecommentstat
n_rmt_utility lnv_util
int           li_comment_id
int           li_null
int           li_quote
string        ls_text


setnull(li_null)

//  Process the comments of the Trip tabpage
l_quoterowstat = this.dw_edit.getitemstatus(1, 0, primary!)
l_quotecommentstat = this.dw_edit.getitemstatus(1, "comments_comment_text", primary!)

if l_quoterowstat = datamodified! or l_quoterowstat = newmodified! then
	 	/*  If a new quote, get next quote id  */	
	li_quote = dw_edit.object.quote_id[1]
   if isnull(li_quote) then 	
   	select max(id)
	     into :li_quote
   	  from quote;
	   
	   if isnull(li_quote) or li_quote < 0 then
		   li_quote = 777
   	end if
	
	   li_quote++
	   dw_edit.object.quote_id[1] = li_quote
	end if
	if l_quotecommentstat = datamodified! then
		li_comment_id = this.dw_edit.object.quote_special_instructions[1]
		ls_text = this.dw_edit.object.comments_comment_text[1]
		li_comment_id = lnv_util.of_updatecomments(li_comment_id, ls_text)
		this.dw_Edit.object.quote_special_instructions[1] = li_comment_id
   elseif l_quotecommentstat = newmodified! then
		setnull(li_comment_id)
		ls_text = this.dw_edit.object.comments_comment_text[1]
		li_comment_id = lnv_util.of_updatecomments(li_comment_id, ls_text)
		this.dw_Edit.object.quote_special_instructions[1] = li_comment_id
	elseif l_quotecommentstat = new! then
		setnull(li_comment_id)
		this.dw_Edit.object.quote_special_instructions[1] = li_comment_id
	end if
   
	
	if l_quoterowstat = newmodified! then
		this.dw_edit.object.quote_date_created[1] = today()
	end if
	this.dw_edit.setitem(1, "quote_date_updated", today())
	this.dw_edit.object.quote_user_id[1] = gnv_app.of_getUserID()
end if


return 1
end event

type dw_edit from u_rmt_dw within u_trip_quote_tabpage
integer x = 55
integer y = 28
integer width = 2944
integer height = 1680
boolean bringtotop = true
string dataobject = "d_charter_order_trip_quote_maint"
boolean vscrollbar = false
boolean border = false
borderstyle borderstyle = stylebox!
end type

event buttonclicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  buttonclicked
//
//	Returns:  long
//	number of the new row that was inserted
//	 0 = No row was added.
//	-1 = error
//
//	Description:	Handles the event when a datawindow button is selected.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//
//////////////////////////////////////////////////////////////////////////////

int          li_order
int          li_quote
w_charter_order  lw_parent


if isnull(this.iw_parent) or not isValid(this.iw_parent) then
	return -1
end if

lw_parent = this.iw_parent
if dwo.name = "cb_createorder" then
   this.accepttext()
	li_quote = this.object.quote_id[1]

     //  Update just this tab page only
   parent.of_preupdate()
	if this.update() <> 1 then
		messagebox(gnv_app.iapp_object.displayname, "Problem updating Quote Data")
		rollback;
		return 0
	end if
	
      //	Get new order number
	li_order = lw_parent.inv_bo.of_setNewOrder(li_quote)  // Quote Id
	if li_order <= 0 then
		messagebox(gnv_app.iapp_object.displayname, "Problem creating a new Order")
		rollback;
		return 0
	end if

   commit;
	
	lw_parent.inv_bo.ii_quote_id = li_quote
	lw_parent.inv_bo.ii_order_id = li_order
	lw_parent.event ue_enablecontrols()
   lw_parent.tab_charter_order.selecttab("tabpage_order")
end if

return 1
end event

event itemchanged;time   lt_spot
time   lt_drop
time   lt_garage
date   ld_return
date   ld_depart
date   ld_garage
int    li_hour
int    li_min


choose case dwo.name
	case "quote_depart_date"
		if isDate(data) then
   		this.object.quote_garage_date[1] = date(data)
		end if
		
	case "quote_depart_spottime"
		if isTime(data) then
			   // Set garage time 1 hour before departure spot time
			li_hour = hour(time(data))
			li_min = minute(time(data))
			if li_hour = 0 then
				lt_garage = time("23:" + string(li_min) + ":00")
				ld_garage = this.object.quote_garage_date[1]
				if isDate(string(ld_garage)) then
   				ld_garage = relativeDate(ld_garage, -1)
				end if
				this.object.quote_garage_time[1] = lt_garage
				this.object.quote_garage_date[1] = ld_garage
			else
   			lt_garage = relativeTime(time(data), -3600)  //1hr = 60mins = 3600secs
	   		this.object.quote_garage_time[1] = lt_garage
				this.object.quote_garage_date[1] = this.object.quote_depart_date[1]
			end if
		end if
		
	case "overridestatus" 
   	if data = 'Y' then
	   	this.object.status.protect = 0
		   this.object.status.dddw.UseAsBorder = "Yes"
		   this.object.status.background.color = rgb(255,255,255)
		   this.object.status.border = 5
	   elseif data = 'N' then
	      this.object.status.protect = 1
		   this.object.status.dddw.UseAsBorder = "No"
   		this.object.status.background.color = il_bgcolor
	   	this.object.status.border = 0
   	end if
	   this.setitemstatus(1, "overridestatus", primary!, notmodified!)
		
//	case "status"
//		iw_parent.event ue_enablecontrols()
		
end choose

data = data
end event

event ue_setrequiredcolumns;call super::ue_setrequiredcolumns;if isValid(inv_base) and not isNull(inv_base) then
	inv_base.of_setRequiredColumn("quote_company_id", "Error occurred on the Trip Tab:~r~nTransport Company cannot be blank")
	inv_base.of_setRequiredColumn("quote_depart_date", "Error occurred on the Trip Tab:~r~nDeparture Date cannot be blank")
	inv_base.of_setRequiredColumn("quote_depart_spottime", "Error occurred on the Trip Tab:~r~nDeparture Spot Time cannot be blank")
	inv_base.of_setRequiredColumn("quote_return_date", "Error occurred on the Trip Tab:~r~nReturn Date cannot be blank")
	inv_base.of_setRequiredColumn("quote_return_droptime", "Error occurred on the Trip Tab:~r~nReturn Drop Time cannot be blank")
	inv_base.of_setRequiredColumn("quote_pickup_location", "Error occurred on the Trip Tab:~r~nPick Up Location cannot be blank")
	inv_base.of_setRequiredColumn("quote_destination", "Error occurred on the Trip Tab:~r~nDestination cannot be blank")
	inv_base.of_setRequiredColumn("comments_comment_text", "Error occurred on the Trip Tab:~r~nSpecial Instructions cannot be blank")	
end if

return 1
end event

event ue_enablecontrols();call super::ue_enablecontrols;w_charter_order  lw_parent
boolean          lb_updatemode
long             ll_bgcolor


lw_parent = this.iw_parent
this.object.cb_createorder.visible = (isNull(lw_parent.inv_bo.ii_order_id))
  
  // If order status is closed or cancelled, render all trip information read-only
lb_updatemode = ( (lw_parent.inv_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CLOSE and &
                   lw_parent.inv_bo.ii_order_status <> lw_parent.inv_bo.ii_ORDER_CANCEL) &
                   or isNull(lw_parent.inv_bo.ii_order_status) )
this.inv_base.of_protectDWColumns(this, lb_updatemode)

if this.object.overridestatus[1] = 'Y' then
  	this.object.status.protect = 0
   this.object.status.dddw.UseAsBorder = "Yes"
   this.object.status.background.color = rgb(255,255,255)
   this.object.status.border = 5
elseif object.overridestatus[1] = 'N' then
   this.object.status.protect = 1
   this.object.status.dddw.UseAsBorder = "No"
	this.object.status.background.color = il_bgcolor
  	this.object.status.border = 0
end if


  // Begin SCR007 RMT040202
ll_bgcolor = long(parent.backcolor)  //553648127 // buttonface
if gnv_app.of_getAccess() = gnv_app.ii_ADMIN then
   parent.dw_edit.object.overridestatus.protect = 0
else 
	parent.dw_edit.object.overridestatus.protect = 1
end if		
   // End SCR007 RMT040202
end event

event ue_validate;call super::ue_validate;time   lt_departspot
time   lt_returndrop
date   ld_return
date   ld_depart
boolean lb_acestorsuccessful

		
lb_acestorsuccessful = AncestorReturnValue
if not lb_acestorsuccessful then
	return lb_acestorsuccessful
end if

ld_return = this.object.quote_return_date[1]
ld_depart = this.object.quote_depart_date[1]
lt_returndrop = this.object.quote_return_droptime[1]
lt_departspot = this.object.quote_depart_spottime[1]
if ld_depart > ld_return then
	gnv_app.inv_msg.of_message("The return date cannot be before departure date", stopsign!)
	return false
end if			
if isNull(ld_return) then
	return true
end if
if ld_return = ld_depart then
	if lt_returndrop <= lt_departspot then
		gnv_app.inv_msg.of_message("Since the departure date is equal to the return date, the return drop time must be greater than then departure spot time", stopsign!)
		return false
   end if
end if

return true
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

event ue_postupdate;call super::ue_postupdate;return this.resetUpdate()
end event
