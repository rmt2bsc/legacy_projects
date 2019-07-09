$PBExportHeader$n_client_bo.sru
forward
global type n_client_bo from n_rmt_windatabject
end type
end forward

global type n_client_bo from n_rmt_windatabject
end type
global n_client_bo n_client_bo

type variables
int    ii_client_id
end variables

on n_client_bo.create
TriggerEvent( this, "constructor" )
end on

on n_client_bo.destroy
TriggerEvent( this, "destructor" )
end on

