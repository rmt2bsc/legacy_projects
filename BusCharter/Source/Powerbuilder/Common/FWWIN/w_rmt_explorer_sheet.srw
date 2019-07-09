$PBExportHeader$w_rmt_explorer_sheet.srw
forward
global type w_rmt_explorer_sheet from w_rmt_sheet
end type
type uo_explorer from u_rmt_explorer within w_rmt_explorer_sheet
end type
end forward

global type w_rmt_explorer_sheet from w_rmt_sheet
int X=315
int Y=320
int Width=4142
int Height=1756
long BackColor=80269524
uo_explorer uo_explorer
end type
global w_rmt_explorer_sheet w_rmt_explorer_sheet

type variables
n_rmt_sql       inv_sql
end variables

on w_rmt_explorer_sheet.create
int iCurrent
call super::create
this.uo_explorer=create uo_explorer
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.uo_explorer
end on

on w_rmt_explorer_sheet.destroy
call super::destroy
destroy(this.uo_explorer)
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
this.inv_resize.of_register(uo_explorer, "Scale")
inv_sql = create n_rmt_sql
this.event ue_enablecontrols()






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


//ls_sql = inv_sql.of_performSearch(dw_detail, dw_criteria)
//messagebox("", ls_sql)
this.event ue_enablecontrols()
uo_explorer.setfocus()


end event

type uo_explorer from u_rmt_explorer within w_rmt_explorer_sheet
int X=69
int Y=48
int TabOrder=20
boolean BringToTop=true
long BackColor=80269524
end type

on uo_explorer.destroy
call u_rmt_explorer::destroy
end on

