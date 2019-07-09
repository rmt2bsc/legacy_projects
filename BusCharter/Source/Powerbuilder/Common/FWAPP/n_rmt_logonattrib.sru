$PBExportHeader$n_rmt_logonattrib.sru
$PBExportComments$RMT Logon window attributes
forward
global type n_rmt_logonattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_logonattrib from n_rmt_baseobject autoinstantiate
end type

type variables
Public:
integer	ii_rc = -99
integer	ii_logonattempts = 3
string	is_userid
string	is_password
int            ii_access
string	is_logo
string	is_appname
powerobject	ipo_source

end variables

on n_rmt_logonattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_logonattrib.destroy
TriggerEvent( this, "destructor" )
end on

