$PBExportHeader$w_general_groups.srw
$PBExportComments$General Code Group Maintenance window
forward
global type w_general_groups from w_rmt_response_maint
end type
type st_1 from statictext within w_general_groups
end type
type st_2 from statictext within w_general_groups
end type
end forward

global type w_general_groups from w_rmt_response_maint
int Width=2304
st_1 st_1
st_2 st_2
end type
global w_general_groups w_general_groups

on w_general_groups.create
int iCurrent
call super::create
this.st_1=create st_1
this.st_2=create st_2
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.st_1
this.Control[iCurrent+2]=this.st_2
end on

on w_general_groups.destroy
call super::destroy
destroy(this.st_1)
destroy(this.st_2)
end on

event ue_predelete;call super::ue_predelete;int    li_row
int    li_dependcount
int    li_group
boolean lb_permanent


li_row = dw_edit.getselectedrow(0)
li_group = dw_edit.getitemnumber(li_row, "group_id")
lb_permanent = (dw_edit.getitemstring(li_row, "permanent") = 'Y')


if lb_permanent then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete from the system")
	return -1
end if

select count(*) 
   into :li_dependcount
	from general_codes
  where group_id = :li_group;
  
if li_dependcount > 0 then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete due to database dependencies")
	return -1
end if

return 1
end event

type cb_3 from w_rmt_response_maint`cb_3 within w_general_groups
int X=1810
boolean BringToTop=true
end type

type cb_1 from w_rmt_response_maint`cb_1 within w_general_groups
int X=1810
boolean BringToTop=true
end type

type cb_2 from w_rmt_response_maint`cb_2 within w_general_groups
int X=1810
boolean BringToTop=true
end type

type dw_edit from w_rmt_response_maint`dw_edit within w_general_groups
int Width=1691
int Height=1112
boolean BringToTop=true
string DataObject="d_general_groups_maint"
end type

type cb_delete from w_rmt_response_maint`cb_delete within w_general_groups
int X=1810
boolean BringToTop=true
end type

type cb_add from w_rmt_response_maint`cb_add within w_general_groups
int X=1810
boolean BringToTop=true
end type

type st_1 from statictext within w_general_groups
int X=64
int Y=1184
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

type st_2 from statictext within w_general_groups
int X=137
int Y=1196
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

