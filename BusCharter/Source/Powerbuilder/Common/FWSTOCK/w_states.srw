$PBExportHeader$w_states.srw
$PBExportComments$United States Maintenance
forward
global type w_states from w_rmt_response
end type
type dw_states from u_rmt_dw within w_states
end type
type cb_3 from u_rmt_cb_cancel within w_states
end type
type cb_1 from u_rmt_cb_ok within w_states
end type
type cb_2 from u_rmt_cb_save within w_states
end type
type st_1 from statictext within w_states
end type
type st_2 from statictext within w_states
end type
end forward

global type w_states from w_rmt_response
int X=187
int Y=560
int Width=4334
int Height=2152
boolean TitleBar=true
string Title="State Maintenance"
dw_states dw_states
cb_3 cb_3
cb_1 cb_1
cb_2 cb_2
st_1 st_1
st_2 st_2
end type
global w_states w_states

event ue_postopen;call super::ue_postopen;
int   li_result

dw_states.settransobject(sqlca)
dw_states.of_setrowselect(true)
li_result = dw_states.of_retrieve()
end event

on w_states.create
int iCurrent
call super::create
this.dw_states=create dw_states
this.cb_3=create cb_3
this.cb_1=create cb_1
this.cb_2=create cb_2
this.st_1=create st_1
this.st_2=create st_2
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_states
this.Control[iCurrent+2]=this.cb_3
this.Control[iCurrent+3]=this.cb_1
this.Control[iCurrent+4]=this.cb_2
this.Control[iCurrent+5]=this.st_1
this.Control[iCurrent+6]=this.st_2
end on

on w_states.destroy
call super::destroy
destroy(this.dw_states)
destroy(this.cb_3)
destroy(this.cb_1)
destroy(this.cb_2)
destroy(this.st_1)
destroy(this.st_2)
end on

event ue_predelete;call super::ue_predelete;int    li_row
boolean lb_permanent


li_row = dw_states.getselectedrow(0)
lb_permanent = (dw_states.getitemstring(li_row, "states_code_permanent") = 'Y')

if lb_permanent then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete from the system")
	return -1
end if

return 1



end event

type dw_states from u_rmt_dw within w_states
int X=23
int Y=36
int Width=3771
int Height=1932
boolean BringToTop=true
string DataObject="d_states"
end type

type cb_3 from u_rmt_cb_cancel within w_states
int X=3867
int Y=320
int Width=411
int Height=84
int TabOrder=20
boolean BringToTop=true
end type

type cb_1 from u_rmt_cb_ok within w_states
int X=3867
int Y=48
int Width=411
int Height=84
int TabOrder=30
boolean BringToTop=true
end type

type cb_2 from u_rmt_cb_save within w_states
int X=3867
int Y=180
int Width=411
int Height=84
int TabOrder=20
boolean BringToTop=true
end type

type st_1 from statictext within w_states
int X=64
int Y=1972
int Width=55
int Height=76
boolean Enabled=false
boolean BringToTop=true
string Text="*"
boolean FocusRectangle=false
long TextColor=255
long BackColor=67108864
int TextSize=-18
int Weight=700
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type st_2 from statictext within w_states
int X=137
int Y=1984
int Width=887
int Height=76
boolean Enabled=false
boolean BringToTop=true
string Text="Cannot be deleted from the system"
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-9
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

