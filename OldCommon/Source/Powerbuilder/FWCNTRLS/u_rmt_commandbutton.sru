$PBExportHeader$u_rmt_commandbutton.sru
forward
global type u_rmt_commandbutton from commandbutton
end type
end forward

global type u_rmt_commandbutton from commandbutton
int Width=247
int Height=108
int TabOrder=10
string Text="Generic"
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type
global u_rmt_commandbutton u_rmt_commandbutton

type variables
w_rmt_ancestor     iw_parent
end variables

event constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event Name:  constructor
//
//	Description:
//	Perform any initializations
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

iw_parent = parent
end event

