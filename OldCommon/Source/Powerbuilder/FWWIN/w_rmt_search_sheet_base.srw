$PBExportHeader$w_rmt_search_sheet_base.srw
forward
global type w_rmt_search_sheet_base from w_rmt_sheet
end type
type dw_criteria from u_rmt_dw within w_rmt_search_sheet_base
end type
type dw_detail from u_rmt_dw within w_rmt_search_sheet_base
end type
end forward

global type w_rmt_search_sheet_base from w_rmt_sheet
int Width=3305
int Height=1932
string MenuName="m_rmt_ancestor"
long BackColor=80269524
event type integer ue_detail ( integer ai_row )
dw_criteria dw_criteria
dw_detail dw_detail
end type
global w_rmt_search_sheet_base w_rmt_search_sheet_base

type variables
protected:
//   n_rmt_searchobject  inv_searchdata
   u_rmt_dw       idw_target

public:
   n_rmt_sql     inv_sql
end variables

on w_rmt_search_sheet_base.create
int iCurrent
call super::create
if this.MenuName = "m_rmt_ancestor" then this.MenuID = create m_rmt_ancestor
this.dw_criteria=create dw_criteria
this.dw_detail=create dw_detail
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_criteria
this.Control[iCurrent+2]=this.dw_detail
end on

on w_rmt_search_sheet_base.destroy
call super::destroy
if IsValid(MenuID) then destroy(MenuID)
destroy(this.dw_criteria)
destroy(this.dw_detail)
end on

event ue_postopen;call super::ue_postopen;//////////////////////////////////////////////////////////////////////////////
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

int   li_result

this.of_setResize(true)
dw_criteria.settransobject(sqlca)
dw_detail.settransobject(sqlca)
dw_detail.of_setrowselect(true)
dw_criteria.of_setUpdateable(false)
dw_detail.of_setUpdateable(false)

this.inv_resize.of_register(dw_criteria, "Scale")
this.inv_resize.of_register(dw_detail, "Scale")
li_result = dw_criteria.of_insert(0)
//li_result = dw_detail.of_insert(0)
inv_sql = create n_rmt_sql
this.event ue_enablecontrols()







//if isvalid(message.powerobjectparm) and not isnull(message.powerobjectparm) then
//	istr_winargs = message.powerobjectparm
//	if not isnull(istr_winargs.DataClassName) then
//   	inv_searchdata = create using istr_winargs.DataClassName
//	else
//		inv_searchdata = create n_rmt_searchobject
//	end if
//	inv_searchdata.il_id = handle(this)
//	inv_searchdata.is_name = this.ClassName()
//	this.of_setTargetDW()
//else
//	messagebox(gnv_app.iapp_object.displayname, "A serach object was not associated with the current window object.~r~nMake note of this error and report to the technical department.");
//end if
//
//this.ib_disableclosequery = true
//this.of_setupdateable(false)
//dw_criteria.settransobject(sqlca)
//dw_result.settransobject(sqlca)
//dw_result.of_setRowSelect(true)
//dw_result.of_setRowManager(true)
//dw_criteria.of_insert(0)
//
//dw_criteria.of_setupdateable(false)
//dw_result.of_setupdateable(false)
//dw_result.insertrow(0)




end event

event ue_search;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_search
//
//	Arguments:  none
//
//	Returns:  string
//
//	Description: Retrieves data in dw_result base on the criteria set in dw_criteria
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

string   ls_sql


ls_sql = inv_sql.of_performSearch(dw_detail, dw_criteria)
//messagebox("", ls_sql)
this.event ue_enablecontrols()
dw_detail.setfocus()




end event

event ue_reset;call super::ue_reset;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_resets
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description: Clears and sets focus on the critera datawindow.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

dw_criteria.setredraw(false)
dw_criteria.reset()
dw_criteria.insertrow(0)
dw_criteria.setredraw(true)

dw_detail.setredraw(false)
dw_detail.reset()
dw_detail.setredraw(true)
this.event ue_enablecontrols()

dw_criteria.setfocus()
end event

type dw_criteria from u_rmt_dw within w_rmt_search_sheet_base
int X=18
int Y=16
int Width=3145
boolean BringToTop=true
boolean VScrollBar=false
end type

type dw_detail from u_rmt_dw within w_rmt_search_sheet_base
int X=23
int Y=852
int Width=3145
int Height=556
int TabOrder=20
boolean BringToTop=true
boolean HScrollBar=true
end type

event constructor;call super::constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  constructor
//
//	Description:
//	
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version - 
//
//////////////////////////////////////////////////////////////////////////////

this.of_setrowmanager(true)
end event

event doubleclicked;if row > 0 and not isnull(row) then
  parent.event ue_detail(row)
end if
end event

