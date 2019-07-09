$PBExportHeader$w_rmt_response.srw
$PBExportComments$Ancestor of all window objects
forward
global type w_rmt_response from w_rmt_ancestor
end type
end forward

global type w_rmt_response from w_rmt_ancestor
int Width=2405
int Height=1372
WindowType WindowType=response!
boolean TitleBar=true
string Title=""
boolean MinBox=false
boolean MaxBox=false
boolean Resizable=false
event ue_cancel ( )
end type
global w_rmt_response w_rmt_response

event ue_cancel;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			ue_Cancel
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	This process disables the CloseQuery processing so that no
//						checks are done during the close process.
//
//		*Note:	The developer (on the descendant script) should code 
//					any specific actions prior to closing the window and then 
//					Close the window.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

/* Allow window to close without the CloseQuery checks being performed */
ib_disableclosequery = True
end event

on w_rmt_response.create
call super::create
end on

on w_rmt_response.destroy
call super::destroy
end on

