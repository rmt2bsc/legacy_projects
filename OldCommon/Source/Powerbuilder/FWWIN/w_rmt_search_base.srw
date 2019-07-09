$PBExportHeader$w_rmt_search_base.srw
$PBExportComments$Ancestor search windows
forward
global type w_rmt_search_base from w_rmt_response
end type
type dw_result from u_rmt_dw within w_rmt_search_base
end type
type cb_ok from u_rmt_cb_ok within w_rmt_search_base
end type
type cb_cancel from u_rmt_cb_cancel within w_rmt_search_base
end type
type dw_criteria from u_rmt_dw within w_rmt_search_base
end type
type cb_search from u_rmt_commandbutton within w_rmt_search_base
end type
type cb_reset from u_rmt_commandbutton within w_rmt_search_base
end type
end forward

global type w_rmt_search_base from w_rmt_response
int X=361
int Y=416
int Width=3703
int Height=1548
event ue_settargetdw ( )
dw_result dw_result
cb_ok cb_ok
cb_cancel cb_cancel
dw_criteria dw_criteria
cb_search cb_search
cb_reset cb_reset
end type
global w_rmt_search_base w_rmt_search_base

type variables
protected:
   u_rmt_dw       idw_target

public:
   n_rmt_sql      inv_sql
end variables

forward prototypes
public subroutine of_settargetdw ()
end prototypes

event ue_settargetdw;
//////////////////////////////////////////////////////////////////////////////
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



this.idw_target = dw_result
end event

public subroutine of_settargetdw ();
this.event ue_settargetdw()
end subroutine

on w_rmt_search_base.create
int iCurrent
call super::create
this.dw_result=create dw_result
this.cb_ok=create cb_ok
this.cb_cancel=create cb_cancel
this.dw_criteria=create dw_criteria
this.cb_search=create cb_search
this.cb_reset=create cb_reset
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_result
this.Control[iCurrent+2]=this.cb_ok
this.Control[iCurrent+3]=this.cb_cancel
this.Control[iCurrent+4]=this.dw_criteria
this.Control[iCurrent+5]=this.cb_search
this.Control[iCurrent+6]=this.cb_reset
end on

on w_rmt_search_base.destroy
call super::destroy
destroy(this.dw_result)
destroy(this.cb_ok)
destroy(this.cb_cancel)
destroy(this.dw_criteria)
destroy(this.cb_search)
destroy(this.cb_reset)
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

this.of_setTargetDW()

this.ib_disableclosequery = true
this.of_setupdateable(false)
dw_criteria.settransobject(sqlca)
dw_result.settransobject(sqlca)
dw_result.of_setRowSelect(true)
dw_result.of_setRowManager(true)
dw_criteria.of_insert(0)

dw_criteria.of_setupdateable(false)
dw_result.of_setupdateable(false)
inv_sql = create n_rmt_sql

//dw_result.insertrow(0)

end event

event ue_reset;call super::ue_reset;
//////////////////////////////////////////////////////////////////////////////
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

//dw_result.setredraw(false)
//dw_result.reset()
//dw_result.insertrow(0)
//dw_result.setredraw(true)

this.event ue_enablecontrols()
dw_criteria.setfocus()
end event

event ue_preclose;call super::ue_preclose;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_preclose
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description: Insert logic at the descendent level to packagae the object,
//              inv_searchdata, to be returned to the called via the call
//              CloseWithReturn.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////



inv_data.of_setdataobject(idw_target)

return
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


inv_sql.of_performSearch(dw_result, dw_criteria)
dw_result.setfocus()




end event

type dw_result from u_rmt_dw within w_rmt_search_base
int X=27
int Y=816
int Width=3145
int Height=556
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

type cb_ok from u_rmt_cb_ok within w_rmt_search_base
int X=3278
int Y=276
int Width=334
int Height=92
int TabOrder=50
boolean BringToTop=true
string Text="&Return"
end type

type cb_cancel from u_rmt_cb_cancel within w_rmt_search_base
int X=3278
int Y=380
int Width=334
int Height=92
int TabOrder=60
boolean BringToTop=true
end type

type dw_criteria from u_rmt_dw within w_rmt_search_base
int X=27
int Y=16
int Width=3145
int TabOrder=20
boolean BringToTop=true
boolean VScrollBar=false
end type

type cb_search from u_rmt_commandbutton within w_rmt_search_base
int X=3278
int Y=68
int Width=334
int Height=92
int TabOrder=40
boolean BringToTop=true
string Text="Search"
boolean Default=true
end type

event clicked;parent.event ue_search()
end event

type cb_reset from u_rmt_commandbutton within w_rmt_search_base
int X=3278
int Y=172
int Width=334
int Height=92
int TabOrder=30
boolean BringToTop=true
string Text="Reset"
end type

event clicked;parent.event ue_reset()
end event

