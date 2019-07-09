$PBExportHeader$n_rmt_zoomattrib.sru
$PBExportComments$Attributes for DataWindow Zoom service
forward
global type n_rmt_zoomattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_zoomattrib from n_rmt_baseobject autoinstantiate
end type

type variables
Public:
integer	ii_zoom
datawindow	idw_obj
datastore		ids_obj
end variables

on n_rmt_zoomattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_zoomattrib.destroy
TriggerEvent( this, "destructor" )
end on

