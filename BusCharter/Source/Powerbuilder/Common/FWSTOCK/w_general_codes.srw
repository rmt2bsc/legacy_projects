$PBExportHeader$w_general_codes.srw
$PBExportComments$General Codes Maintenance
forward
global type w_general_codes from w_rmt_response_maint
end type
type dw_master from u_rmt_dw within w_general_codes
end type
type st_2 from statictext within w_general_codes
end type
type st_1 from statictext within w_general_codes
end type
end forward

global type w_general_codes from w_rmt_response_maint
int X=315
int Y=1060
int Width=3447
int Height=1360
boolean TitleBar=true
string Title="General Code Maintenance"
dw_master dw_master
st_2 st_2
st_1 st_1
end type
global w_general_codes w_general_codes

on w_general_codes.create
int iCurrent
call super::create
this.dw_master=create dw_master
this.st_2=create st_2
this.st_1=create st_1
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_master
this.Control[iCurrent+2]=this.st_2
this.Control[iCurrent+3]=this.st_1
end on

on w_general_codes.destroy
call super::destroy
destroy(this.dw_master)
destroy(this.st_2)
destroy(this.st_1)
end on

event ue_postopen;call super::ue_postopen;int   li_result

this.of_setResize(true)
dw_master.settransobject(sqlca)
dw_edit.settransobject(sqlca)
dw_master.of_setrowselect(true)


li_result = dw_master.of_retrieve()
li_result = dw_edit.of_retrieve()
dw_master.setfocus()

end event

event ue_predelete;call super::ue_predelete;int    li_row
int    li_dependcount
int    li_code
boolean lb_permanent
datastore  ds

li_row = dw_edit.getselectedrow(0)
li_code = dw_edit.getitemnumber(li_row, "code_id")
lb_permanent = (dw_edit.getitemstring(li_row, "permanent") = 'Y')

if lb_permanent then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete from the system")
	return -1
end if

ds = create datastore
ds.dataobject = "d_general_code_dependency"
ds.settransobject(sqlca)
ds.retrieve(li_code)
li_dependcount = ds.object.c_count[1]

if li_dependcount > 0 then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete due to database dependencies")
	return -1
end if

return 1

end event

event ue_add;call super::ue_add;
int   li_row
int   li_masterrow
int   li_group


li_row = AncestorReturnValue
li_masterrow = dw_master.getselectedrow(0)
if li_masterrow <= 0 then
	return 0
end if

li_group = dw_master.getitemnumber(li_masterrow, "group_id")
dw_edit.setitem(li_row, "group_id", li_group)

return li_row
end event

type cb_3 from w_rmt_response_maint`cb_3 within w_general_codes
int X=2976
int Y=444
boolean BringToTop=true
end type

type cb_1 from w_rmt_response_maint`cb_1 within w_general_codes
int X=2976
int Y=32
boolean BringToTop=true
end type

type cb_2 from w_rmt_response_maint`cb_2 within w_general_codes
int X=2976
int Y=336
int TabOrder=60
boolean BringToTop=true
end type

type dw_edit from w_rmt_response_maint`dw_edit within w_general_codes
int X=1056
int Y=28
int Width=1856
int Height=1144
int TabOrder=50
boolean BringToTop=true
string DataObject="d_general_group_codes"
end type

type cb_delete from w_rmt_response_maint`cb_delete within w_general_codes
int X=2976
int Y=232
int TabOrder=40
boolean BringToTop=true
end type

type cb_add from w_rmt_response_maint`cb_add within w_general_codes
int X=2976
int Y=132
int TabOrder=30
boolean BringToTop=true
end type

type dw_master from u_rmt_dw within w_general_codes
int X=37
int Y=28
int Width=987
int Height=1140
int TabOrder=20
boolean BringToTop=true
string DataObject="d_general_groups"
end type

event rowfocuschanged;call super::rowfocuschanged;int li_gencode


li_gencode = dw_master.object.group_id[currentrow]
dw_edit.setredraw(false)
dw_edit.setfilter("group_id = " + string(li_gencode))
dw_edit.filter()
dw_edit.setsort("longdesc A")
dw_edit.sort()
dw_edit.setredraw(true)
end event

type st_2 from statictext within w_general_codes
int X=1138
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

type st_1 from statictext within w_general_codes
int X=1065
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

