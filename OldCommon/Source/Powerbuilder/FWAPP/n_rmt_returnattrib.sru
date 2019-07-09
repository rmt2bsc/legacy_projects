$PBExportHeader$n_rmt_returnattrib.sru
$PBExportComments$Simple return attributes
forward
global type n_rmt_returnattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_returnattrib from n_rmt_baseobject autoinstantiate
end type
global n_rmt_returnattrib n_rmt_returnattrib

type variables
Public:

integer	ii_rc=-99	// Return code
string	is_rs=''	// Return string
end variables

on n_rmt_returnattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_returnattrib.destroy
TriggerEvent( this, "destructor" )
end on

