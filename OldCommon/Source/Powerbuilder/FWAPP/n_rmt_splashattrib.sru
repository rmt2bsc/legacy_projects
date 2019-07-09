$PBExportHeader$n_rmt_splashattrib.sru
$PBExportComments$Splash Screen Attributes
forward
global type n_rmt_splashattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_splashattrib from n_rmt_baseobject autoinstantiate
end type

type variables
integer	ii_secondsvisible	//Seconds the splah window should be visible
string	is_application	//The application name
string	is_logo		//The logo
string	is_version		//The application version
string	is_copyright	//A copyright message
end variables

on n_rmt_splashattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_splashattrib.destroy
TriggerEvent( this, "destructor" )
end on

