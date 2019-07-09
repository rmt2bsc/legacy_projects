$PBExportHeader$w_rmt_response_master_detail_maint.srw
forward
global type w_rmt_response_master_detail_maint from w_rmt_response_maint
end type
type dw_detail from u_rmt_dw within w_rmt_response_master_detail_maint
end type
end forward

global type w_rmt_response_master_detail_maint from w_rmt_response_maint
dw_detail dw_detail
end type
global w_rmt_response_master_detail_maint w_rmt_response_master_detail_maint

on w_rmt_response_master_detail_maint.create
int iCurrent
call super::create
this.dw_detail=create dw_detail
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_detail
end on

on w_rmt_response_master_detail_maint.destroy
call super::destroy
destroy(this.dw_detail)
end on

event ue_postopen;call super::ue_postopen;

dw_edit.sharedata(dw_detail)
end event

event ue_preopen;call super::ue_preopen;dw_detail.settransobject(sqlca)
end event

type cb_3 from w_rmt_response_maint`cb_3 within w_rmt_response_master_detail_maint
end type

type cb_1 from w_rmt_response_maint`cb_1 within w_rmt_response_master_detail_maint
end type

type cb_2 from w_rmt_response_maint`cb_2 within w_rmt_response_master_detail_maint
integer taborder = 60
end type

type dw_edit from w_rmt_response_maint`dw_edit within w_rmt_response_master_detail_maint
integer height = 512
integer taborder = 50
end type

event dw_edit::rowfocuschanged;call super::rowfocuschanged;//  scroll dw_detail to the current row of dw_edit

dw_detail.scrolltorow(currentrow)

end event
type cb_delete from w_rmt_response_maint`cb_delete within w_rmt_response_master_detail_maint
integer taborder = 40
end type

type cb_add from w_rmt_response_maint`cb_add within w_rmt_response_master_detail_maint
integer taborder = 30
end type

type dw_detail from u_rmt_dw within w_rmt_response_master_detail_maint
integer x = 69
integer y = 600
integer width = 1609
integer height = 552
integer taborder = 20
boolean bringtotop = true
boolean vscrollbar = false
end type

