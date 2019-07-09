$PBExportHeader$n_rmt_sortattrib.sru
$PBExportComments$Attributes for DataWindow Sort service
forward
global type n_rmt_sortattrib from n_rmt_baseobject
end type
end forward

global type n_rmt_sortattrib from n_rmt_baseobject autoinstantiate
end type

type variables
Public:
string	is_sort
string 	is_sortcolumns[]
string	is_colnamedisplay[]
boolean 	ib_usedisplay[]
string 	is_origcolumns[]
string 	is_origorder[]
end variables

event constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Object Name:  n_rmt_sortattrib
//
//	Description:  A NVO class to hold attributes for pfc_n_cst_dwsrv_sort
//				     service object.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

end event

on n_rmt_sortattrib.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_sortattrib.destroy
TriggerEvent( this, "destructor" )
end on

