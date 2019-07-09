$PBExportHeader$n_rmt_infoattrib.sru
$PBExportComments$Informational attributes
forward
global type n_rmt_infoattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_infoattrib from n_rmt_baseobject autoinstantiate
end type
global n_rmt_infoattrib n_rmt_baseobject

type variables
Public:

string	is_name
string	is_description

end variables

on n_rmt_infoattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_infoattrib.destroy
TriggerEvent( this, "destructor" )
end on

