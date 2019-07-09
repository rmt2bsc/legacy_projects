$PBExportHeader$w_password_change.srw
forward
global type w_password_change from w_rmt_response
end type
type dw_edit from u_rmt_dw within w_password_change
end type
type cb_1 from u_rmt_cb_ok within w_password_change
end type
type cb_2 from u_rmt_cb_cancel within w_password_change
end type
end forward

global type w_password_change from w_rmt_response
int Width=2267
int Height=544
dw_edit dw_edit
cb_1 cb_1
cb_2 cb_2
end type
global w_password_change w_password_change

on w_password_change.create
int iCurrent
call super::create
this.dw_edit=create dw_edit
this.cb_1=create cb_1
this.cb_2=create cb_2
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_edit
this.Control[iCurrent+2]=this.cb_1
this.Control[iCurrent+3]=this.cb_2
end on

on w_password_change.destroy
call super::destroy
destroy(this.dw_edit)
destroy(this.cb_1)
destroy(this.cb_2)
end on

event ue_postopen;call super::ue_postopen;
ib_disableclosequery = true
dw_edit.insertrow(0)
dw_edit.object.userid[1] = gnv_app.of_getUserID()
end event

type dw_edit from u_rmt_dw within w_password_change
int X=23
int Y=32
int Width=1714
int Height=396
boolean BringToTop=true
string DataObject="d_password_change"
boolean Border=false
BorderStyle BorderStyle=StyleBox!
boolean VScrollBar=false
end type

type cb_1 from u_rmt_cb_ok within w_password_change
int X=1778
int Y=48
int Width=393
int Height=88
int TabOrder=20
boolean BringToTop=true
end type

event clicked;datastore   ds
int         li_result
string      ls_user
string      ls_oldpw
string      ls_newpw
string      ls_confirmpw

dw_edit.accepttext()
ls_user = dw_edit.object.userid[1] 
ls_oldpw = dw_edit.object.oldpw[1]
ls_newpw = dw_edit.object.newpw[1]
ls_confirmpw = dw_edit.object.confirmpw[1]

if isNull(ls_oldpw) then
	gnv_app.inv_msg.of_message("You must supply a value for the old password!", exclamation!)
	return -1
end if
if isNull(ls_newpw) then
	gnv_app.inv_msg.of_message("You must supply a value for the new password!", exclamation!)
	return -2
end if
if isNull(ls_confirmpw) then
	gnv_app.inv_msg.of_message("You must supply a value for the confirmed password!", exclamation!)
	return -3
end if
if lower(ls_newpw) <> lower(ls_confirmpw) then
	gnv_app.inv_msg.of_message("The new passowrd and the confirmed password entered must be the same!", exclamation!)
	return -4
end if

ds = create datastore
ds.dataobject = "d_password_update"
li_result = ds.settransobject(sqlca)
li_result = ds.retrieve(ls_user, ls_oldpw)
if li_result <> 1 then
	gnv_app.inv_msg.of_message("Login using the old password is invalid...Please try again using your current user ID and password!", exclamation!)
	return -4
end if

ds.object.password[1] = ls_newpw
if ds.update() <> 1 then
	rollback;
	gnv_app.inv_msg.of_message("Password update operation failed.~r~nConsult technicalsupport", exclamation!)
	return -5
else
	commit;
	gnv_app.inv_msg.of_message("Password successfully changed!")
end if

Super::event clicked()
end event

type cb_2 from u_rmt_cb_cancel within w_password_change
int X=1778
int Y=164
int Width=393
int Height=88
int TabOrder=20
boolean BringToTop=true
end type

