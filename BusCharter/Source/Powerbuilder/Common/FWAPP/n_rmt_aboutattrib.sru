$PBExportHeader$n_rmt_aboutattrib.sru
$PBExportComments$About Dialog Attributes
forward
global type n_rmt_aboutattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_aboutattrib from n_rmt_baseobject autoinstantiate
end type

type variables
string	is_application		//The application name
string	is_logo			//The logo
string	is_version			//The application version
string	is_copyright		//A copyright message

end variables

on n_rmt_aboutattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_aboutattrib.destroy
TriggerEvent( this, "destructor" )
end on

