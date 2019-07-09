$PBExportHeader$n_rmt_propertyattrib.sru
$PBExportComments$Property - attributes
forward
global type n_rmt_propertyattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_propertyattrib from n_rmt_baseobject autoinstantiate
end type
global n_rmt_propertyattrib n_rmt_propertyattrib

type variables
Public:

// Note:  Not all attributes will be used by all objects.
string	is_name
string	is_description
string	is_propertypage[]
string	is_propertytabtext
boolean	ib_switchbuttons
end variables

on n_rmt_propertyattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_propertyattrib.destroy
TriggerEvent( this, "destructor" )
end on

