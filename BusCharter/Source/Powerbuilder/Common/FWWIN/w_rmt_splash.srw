$PBExportHeader$w_rmt_splash.srw
forward
global type w_rmt_splash from w_rmt_popup
end type
type gb_1 from groupbox within w_rmt_splash
end type
type p_splash from picture within w_rmt_splash
end type
type st_application from statictext within w_rmt_splash
end type
type st_version from statictext within w_rmt_splash
end type
type st_3 from statictext within w_rmt_splash
end type
end forward

global type w_rmt_splash from w_rmt_popup
int X=777
int Y=700
int Width=1915
int Height=844
boolean TitleBar=false
long BackColor=80269524
boolean ControlMenu=false
boolean MinBox=false
boolean MaxBox=false
gb_1 gb_1
p_splash p_splash
st_application st_application
st_version st_version
st_3 st_3
end type
global w_rmt_splash w_rmt_splash

type variables
n_rmt_splashattrib   inv_splashattrib
end variables

on w_rmt_splash.create
int iCurrent
call super::create
this.gb_1=create gb_1
this.p_splash=create p_splash
this.st_application=create st_application
this.st_version=create st_version
this.st_3=create st_3
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.gb_1
this.Control[iCurrent+2]=this.p_splash
this.Control[iCurrent+3]=this.st_application
this.Control[iCurrent+4]=this.st_version
this.Control[iCurrent+5]=this.st_3
end on

on w_rmt_splash.destroy
call super::destroy
destroy(this.gb_1)
destroy(this.p_splash)
destroy(this.st_application)
destroy(this.st_version)
destroy(this.st_3)
end on

event timer;//////////////////////////////////////////////////////////////////////////////
//
//	Event:	timer
//
//	Description:	Close window when specified length of time is reached
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


Close (this)
end event

event ue_preopen;// ignore
end event

event open;call super::open;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  ue_posttOpen
//
//	Description:  Get splash object and display splash window
//	with appropriate settings that were specified
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0    Initial version
//////////////////////////////////////////////////////////////////////////////

// Get the splash object
if isvalid(Message.PowerObjectParm) and not isnull(Message.PowerObjectParm) then
   inv_splashattrib = Message.PowerObjectParm
else
	return
end if

this.BringToTop = true

// Allow window to close without the CloseQuery checks being performed.
ib_disableclosequery = True


// Center the splash window.
of_SetBase(True)
inv_base.of_Center()


// Display the Application bitmap
If Len(inv_splashattrib.is_logo) > 0 Then
	p_splash.PictureName = inv_splashattrib.is_logo
End If

//// Copyright
//If Len(inv_splashattrib.is_copyright) > 0 Then
//	st_copyright.text = inv_splashattrib.is_copyright
//Else
//	st_copyright.text = ''
//End If

// Application display name
If Len(inv_splashattrib.is_application) > 0 Then
	st_application.text = inv_splashattrib.is_application
Else
	st_application.text = ''
End If

// Application version
If Len(inv_splashattrib.is_version) > 0 Then
	st_version.text = "Version: " + inv_splashattrib.is_version
Else
	st_version.text = ''
End If

// Set length of time for window to close
Timer (inv_splashattrib.ii_secondsvisible)
end event

type gb_1 from groupbox within w_rmt_splash
int X=37
int Width=1842
int Height=812
int TabOrder=10
BorderStyle BorderStyle=StyleRaised!
long TextColor=33554432
long BackColor=67108864
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type p_splash from picture within w_rmt_splash
int X=73
int Y=60
int Width=686
int Height=336
boolean BringToTop=true
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
end type

type st_application from statictext within w_rmt_splash
int X=69
int Y=448
int Width=1783
int Height=124
boolean Enabled=false
boolean BringToTop=true
string Text="Applicatin Name"
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-14
int Weight=700
string FaceName="Times New Roman"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Roman!
FontPitch FontPitch=Variable!
end type

type st_version from statictext within w_rmt_splash
int X=69
int Y=544
int Width=1056
int Height=80
boolean Enabled=false
boolean BringToTop=true
string Text="Version:  "
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type st_3 from statictext within w_rmt_splash
int X=69
int Y=628
int Width=1193
int Height=76
boolean Enabled=false
boolean BringToTop=true
string Text="Created by:   RMT2 Business Systems Corp"
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

