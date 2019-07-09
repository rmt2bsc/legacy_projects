$PBExportHeader$w_rmt_search_maintenance.srw
$PBExportComments$Ancestor search - maintenance window
forward
global type w_rmt_search_maintenance from w_rmt_search_base
end type
type dw_detail from u_rmt_dw within w_rmt_search_maintenance
end type
type cb_add from u_rmt_commandbutton within w_rmt_search_maintenance
end type
type cb_delete from u_rmt_commandbutton within w_rmt_search_maintenance
end type
type cb_1 from u_rmt_cb_save within w_rmt_search_maintenance
end type
end forward

global type w_rmt_search_maintenance from w_rmt_search_base
integer x = 535
integer y = 272
integer width = 3721
integer height = 2068
dw_detail dw_detail
cb_add cb_add
cb_delete cb_delete
cb_1 cb_1
end type
global w_rmt_search_maintenance w_rmt_search_maintenance

forward prototypes
public function integer of_getdetails ()
end prototypes

public function integer of_getdetails ();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_getdetails
//
//	Description: Attempts to retrieve the detail of the selected row in 
//              dw_result datawindow.  
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


return dw_detail.event ue_retrieve()
end function

on w_rmt_search_maintenance.create
int iCurrent
call super::create
this.dw_detail=create dw_detail
this.cb_add=create cb_add
this.cb_delete=create cb_delete
this.cb_1=create cb_1
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_detail
this.Control[iCurrent+2]=this.cb_add
this.Control[iCurrent+3]=this.cb_delete
this.Control[iCurrent+4]=this.cb_1
end on

on w_rmt_search_maintenance.destroy
call super::destroy
destroy(this.dw_detail)
destroy(this.cb_add)
destroy(this.cb_delete)
destroy(this.cb_1)
end on

event ue_postopen();call super::ue_postopen;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_postopen
//
//	Description: initializes the window's controls
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

dw_detail.setredraw(false)
dw_detail.settransobject(sqlca)

  // Insert a row via the framework mwthod of_insert() 
  // to prevent dropdown datawindow retrieval errors 
  // when a call to sharedata() is made.
dw_detail.of_insert(0)
dw_detail.reset()
dw_result.sharedata(dw_detail)

dw_detail.setredraw(true)
dw_detail.of_setupdateable(true)
this.of_setupdateable(true)
this.ib_disableclosequery = false
end event
event ue_search;call super::ue_search;

//dw_result.sharedata(dw_detail)


//this.dw_detail.object.datawindow.table.select = is_sql
//this.dw_detail.of_retrieve()

end event

event ue_delete;if isValid(dw_result.inv_rowmanager) then
	return dw_result.inv_rowmanager.of_deleteselected()
end if
end event

event ue_add;int li_row


li_row = dw_result.of_insert(0)
if isValid(dw_result.inv_rowselect) then
	dw_result.inv_rowselect.of_rowselect(li_row)
	dw_result.scrolltorow(li_row)
end if

return li_row
end event

event ue_preupdate;int  li_row
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
         dw_detail.object.date_created[li_row] = datetime(today(), now())
		end if
      dw_detail.object.date_updated[li_row] = datetime(today(), now())
      dw_detail.object.user_id[li_row] = gnv_app.of_getUserID()
	   li_actualupdatecount++
   end if
next

return (dw_detail.modifiedcount() + dw_detail.deletedcount())
  
end event

event ue_settargetdw;call super::ue_settargetdw;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_setTargetDW
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description: Determines which datawindow will serve as the source to be 
//              passed back to the caller via inv_searchdata.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////



//this.idw_target = dw_detail
end event

type dw_result from w_rmt_search_base`dw_result within w_rmt_search_maintenance
integer x = 32
integer y = 604
end type

event dw_result::ue_retrieve;call super::ue_retrieve;

int li_rows

li_rows = AncestorReturnValue

if li_rows > 0 then
	if isValid(inv_rowselect) then
		inv_rowselect.of_rowselect(1)
   else
		this.selectrow(0, false)
		this.selectrow(1, true)
	end if
end if

return li_rows
end event

event dw_result::rowfocuschanged;call super::rowfocuschanged;dw_detail.scrolltorow(currentrow)
end event

type cb_ok from w_rmt_search_base`cb_ok within w_rmt_search_maintenance
integer x = 3291
integer y = 780
integer height = 96
integer taborder = 80
end type

type cb_cancel from w_rmt_search_base`cb_cancel within w_rmt_search_maintenance
integer x = 3291
integer y = 668
integer height = 96
integer taborder = 90
end type

type dw_criteria from w_rmt_search_base`dw_criteria within w_rmt_search_maintenance
integer height = 548
end type

type cb_search from w_rmt_search_base`cb_search within w_rmt_search_maintenance
integer x = 3291
integer y = 36
integer height = 96
integer taborder = 70
end type

type cb_reset from w_rmt_search_base`cb_reset within w_rmt_search_maintenance
integer x = 3291
integer y = 144
integer height = 96
integer taborder = 60
end type

type dw_detail from u_rmt_dw within w_rmt_search_maintenance
integer x = 37
integer y = 1196
integer width = 3136
integer taborder = 50
boolean bringtotop = true
end type

event ue_retrieve;call super::ue_retrieve;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_retrieve
//
//	Description: Gets the location of the selected row in dw_result datawindow
//              and return the result to the descendent script via 
//              AncestorReturnValue.   Should not be overriden.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

//Constant Integer	ALLOW_CLOSE = 0
//Constant Integer	PREVENT_CLOSE = 1
//powerobject       lpo_updatearray[], lpo_pendingupdates[]
//int               li_result
//
//
//
//// Prevent validation error messages from appearing while the window is closing
//// and allow others to check if the  CloseQuery process is in progress
//ib_closestatus = True
//
//// Check for any pending updates
//li_result = of_UpdatesPending()
//if li_result = 0 then
//	// Updates are NOT pending, allow the window to be closed.
//	ib_closestatus = false
//else
//	 // Changes are pending, prompt the user to determine if they should be saved
//	 if li_result > 0 then
//   	 if IsValid(gnv_app.inv_msg) then
//	   	 li_result = gnv_app.inv_msg.of_Message("inv_msg.CLOSEQUERY")		
//	    else
//		    li_result = MessageBox(gnv_app.iapp_object.DisplayName, "Do you want to save changes before moving to the next record?", exclamation!, YesNoCancel!, 1)
//	    end if
//   	 choose case li_result
//		    case 1
//			    // YES - Update
//			    // If the update fails, prevent the window from closing
//			    if this.event ue_save() >= 1 then
//				   // Successful update, allow the window to be closed
//				   ib_closestatus = false
//  				 else
//					 return -1
//			    end if
//		   case 2
//			   // NO - Allow the window to be closed without saving changes
//			   ib_closestatus = false
//		   case 3

//			   // CANCEL -  Prevent the window from closing
//				return 0
//	   end choose
//	end if
//end if

return dw_result.getselectedrow(0)
end event

type cb_add from u_rmt_commandbutton within w_rmt_search_maintenance
integer x = 3291
integer y = 252
integer width = 334
integer height = 96
integer taborder = 40
boolean bringtotop = true
string text = "Add"
end type

event clicked;parent.of_add()
end event

type cb_delete from u_rmt_commandbutton within w_rmt_search_maintenance
integer x = 3291
integer y = 360
integer width = 334
integer height = 96
integer taborder = 30
boolean bringtotop = true
string text = "Delete"
end type

event clicked;if isValid(dw_result.inv_rowmanager) then
	dw_result.inv_rowmanager.of_setconfirmondelete(true)
	parent.of_delete(dw_result.getselectedrow(0))
end if

end event

type cb_1 from u_rmt_cb_save within w_rmt_search_maintenance
integer x = 3291
integer y = 560
integer width = 334
integer height = 96
integer taborder = 20
boolean bringtotop = true
end type

event clicked;call super::clicked;parent.ib_closestatus = false
end event

