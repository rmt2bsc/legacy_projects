$PBExportHeader$u_rmt_tabpage.sru
$PBExportComments$Tab Page Control Object
forward
global type u_rmt_tabpage from u_rmt_base
end type
end forward

global type u_rmt_tabpage from u_rmt_base
int Width=2048
int Height=1048
event type integer ue_preupdate ( )
event type integer ue_postupdate ( )
end type
global u_rmt_tabpage u_rmt_tabpage

forward prototypes
public function integer of_preupdate ()
end prototypes

event ue_preupdate;

return 1
end event

event ue_postupdate;

return 1
end event

public function integer of_preupdate ();int  li_result

if this.event ue_preupdate() <> 1 then
	return -1
end if

return 1
end function

on u_rmt_tabpage.create
call super::create
end on

on u_rmt_tabpage.destroy
call super::destroy
end on

