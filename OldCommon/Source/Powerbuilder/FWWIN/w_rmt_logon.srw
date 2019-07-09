$PBExportHeader$w_rmt_logon.srw
$PBExportComments$RMT Logon window
forward
global type w_rmt_logon from w_rmt_response
end type
type p_logo from picture within w_rmt_logon
end type
type st_help from statictext within w_rmt_logon
end type
type st_2 from statictext within w_rmt_logon
end type
type st_3 from statictext within w_rmt_logon
end type
type sle_userid from singlelineedit within w_rmt_logon
end type
type sle_password from singlelineedit within w_rmt_logon
end type
type cb_ok from commandbutton within w_rmt_logon
end type
type cb_cancel from commandbutton within w_rmt_logon
end type
end forward

global type w_rmt_logon from w_rmt_response
int Width=2386
int Height=532
event ue_default ( )
p_logo p_logo
st_help st_help
st_2 st_2
st_3 st_3
sle_userid sle_userid
sle_password sle_password
cb_ok cb_ok
cb_cancel cb_cancel
end type
global w_rmt_logon w_rmt_logon

type variables
Protected:
n_rmt_logonattrib	inv_logonattrib
integer		ii_logonattempts = 1
end variables

event ue_default;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_default
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:  Peform logon
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0 	Enhanced to support multiple logon attempts.
//
//////////////////////////////////////////////////////////////////////////////

integer	li_rc

//////////////////////////////////////////////////////////////////////////////
// Check required fields
//////////////////////////////////////////////////////////////////////////////
if Len (sle_userid.text) = 0 then
	gnv_app.of_MessageBox ("ue_logon_enterid", inv_logonattrib.is_appname,	"Please enter a User ID to logon.", exclamation!, OK!, 1)
	sle_userid.SetFocus()
	return
end if
if Len (sle_password.text) = 0 then
	gnv_app.of_MessageBox ("ue_logon_enterpassword", inv_logonattrib.is_appname, "Please enter a password to logon.", exclamation!, OK!, 1)
	sle_password.SetFocus()
	return
end if
if Isnull(inv_logonattrib.ipo_source) or Not IsValid (inv_logonattrib.ipo_source) then
	this.event ue_cancel()
	return
End If

inv_logonattrib.is_userid = sle_userid.text
inv_logonattrib.is_password = sle_password.text
//////////////////////////////////////////////////////////////////////////////
// Attempt to logon
//////////////////////////////////////////////////////////////////////////////
li_rc = inv_logonattrib.ipo_source.dynamic event ue_logon(inv_logonattrib)
ii_logonattempts --
if IsNull (li_rc) then 
	this.event ue_cancel()
	return
ElseIf li_rc <= 0 Then
	// There still exist more attempts for a succesful login.
	gnv_app.of_MessageBox ("ue_logon_incorrectpassword", "Login", "The password is incorrect.", StopSign!, Ok!, 1)
	sle_password.SetFocus()
	If ii_logonattempts = 0 Then
		gnv_app.of_MessageBox ("ue_logon_maxattemps", "Login", "The maximum number of login attemps have been exceeded...Program will be aborted!.", StopSign!, Ok!, 1)
      halt		
	elseif ii_logonattempts > 0 Then
		return
	else
		// Failure return code
		inv_logonattrib.ii_rc = -1	
		gnv_app.of_MessageBox ("ue_logon_genrealfailure", "Login", "A general failure occured while attemping to logon to system.  Advise Administrator", StopSign!, Ok!, 1)
		CloseWithReturn (this, inv_logonattrib)
	End If
elseif li_rc > 1 then
	// Tow or more users have the same identity
	gnv_app.of_MessageBox ("ue_logon_multipleidentities", "Login", "Two or more user have the same user id and password.  Advise Administrator.", StopSign!, Ok!, 1)
	halt close
else
	// Successful return code
	inv_logonattrib.ii_rc = 1
	inv_logonattrib.is_userid = sle_userid.text
	inv_logonattrib.is_password = sle_password.text	
	CloseWithReturn (this, inv_logonattrib)	
End if

Return
end event

event close;call super::close;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  close
//
//	Description:
//	Treat window close from control menu as cancel operation
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// If the return code matches the default value,
// then window is currently being closed as a Cancel operation.
if inv_logonattrib.ii_rc= -99 then
	this.event ue_cancel ()
end if
end event

event open;call super::open;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  open
//
//	Description:  Get information from the logon object
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//////////////////////////////////////////////////////////////////////////////


ib_disableclosequery = true

// Validate for a valid PowerObjectParm
If IsValid(Message.PowerObjectParm) Then
	If inv_logonattrib.ClassName() = Message.PowerObjectParm.ClassName() Then
		inv_logonattrib = Message.PowerObjectParm
	End IF
Else
	// Set the return code to mean the window was closed by error.
	inv_logonattrib.ii_rc = -1
	inv_logonattrib.is_userid = ""
	CloseWithReturn (this, inv_logonattrib)
	Return
End If

// User ID
sle_userid.text = inv_logonattrib.is_userid

// Password
sle_password.text = inv_logonattrib.is_password

// Logo
If Len(inv_logonattrib.is_logo) > 0 Then
	p_logo.picturename = inv_logonattrib.is_logo
Else
	p_logo.Visible = False
End If

// Application Name
if Len (inv_logonattrib.is_appname) = 0 then
	inv_logonattrib.is_appname = "the application"	
end if
st_help.text = st_help.text + inv_logonattrib.is_appname + "."

// Set the logon attempts variable
If IsValid(inv_logonattrib) Then
	If Not IsNull(inv_logonattrib.ii_logonattempts) Then
		ii_logonattempts = inv_logonattrib.ii_logonattempts
	End If
End If

// Set focus
if Len (sle_userid.text) > 0 then
	if Len (sle_password.text) > 0 then
		cb_ok.SetFocus()
	else
		sle_password.SetFocus()
	end if
else
	sle_userid.SetFocus()
end if

end event

event ue_cancel;call super::ue_cancel;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_Cancel
//
//	Arguments:  none
//
//	Returns:  none
//
//	Description:
//	Set the return code to 0 (cancel)
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


// Set the return code to mean the window was closed by a cancel operation.
inv_logonattrib.ii_rc = 0

inv_logonattrib.is_userid = ""
CloseWithReturn (this, inv_logonattrib)
end event

on w_rmt_logon.create
int iCurrent
call super::create
this.p_logo=create p_logo
this.st_help=create st_help
this.st_2=create st_2
this.st_3=create st_3
this.sle_userid=create sle_userid
this.sle_password=create sle_password
this.cb_ok=create cb_ok
this.cb_cancel=create cb_cancel
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.p_logo
this.Control[iCurrent+2]=this.st_help
this.Control[iCurrent+3]=this.st_2
this.Control[iCurrent+4]=this.st_3
this.Control[iCurrent+5]=this.sle_userid
this.Control[iCurrent+6]=this.sle_password
this.Control[iCurrent+7]=this.cb_ok
this.Control[iCurrent+8]=this.cb_cancel
end on

on w_rmt_logon.destroy
call super::destroy
destroy(this.p_logo)
destroy(this.st_help)
destroy(this.st_2)
destroy(this.st_3)
destroy(this.sle_userid)
destroy(this.sle_password)
destroy(this.cb_ok)
destroy(this.cb_cancel)
end on

event ue_preopen;ib_bypass_dataobject = true
end event

type p_logo from picture within w_rmt_logon
int X=178
int Y=108
int Width=165
int Height=144
boolean BringToTop=true
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
end type

type st_help from statictext within w_rmt_logon
int X=434
int Y=44
int Width=1376
int Height=120
string Text="Enter a User ID and password to log onto database"
long BackColor=79741120
int TextSize=-9
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type st_2 from statictext within w_rmt_logon
int X=434
int Y=208
int Width=265
int Height=72
string Text="User ID:"
long BackColor=80269524
int TextSize=-9
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type st_3 from statictext within w_rmt_logon
int X=434
int Y=308
int Width=256
int Height=72
string Text="Password:"
long BackColor=80269524
int TextSize=-9
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type sle_userid from singlelineedit within w_rmt_logon
int X=773
int Y=208
int Width=1038
int Height=72
int TabOrder=10
boolean BringToTop=true
BorderStyle BorderStyle=StyleLowered!
boolean AutoHScroll=false
long TextColor=33554432
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type sle_password from singlelineedit within w_rmt_logon
int X=773
int Y=296
int Width=1038
int Height=72
int TabOrder=20
boolean BringToTop=true
BorderStyle BorderStyle=StyleLowered!
boolean PassWord=true
long TextColor=33554432
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type cb_ok from commandbutton within w_rmt_logon
int X=1938
int Y=52
int Width=329
int Height=92
int TabOrder=30
boolean BringToTop=true
string Text="Ok"
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

event clicked;parent.event ue_default()
end event

type cb_cancel from commandbutton within w_rmt_logon
int X=1938
int Y=192
int Width=329
int Height=92
int TabOrder=40
boolean BringToTop=true
string Text="Cancel"
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

event clicked;parent.event ue_cancel()
end event

