$PBExportHeader$bussys.sra
$PBExportComments$This is an bus charter application  for Green's
forward
global type bussys from application
end type
global transaction sqlca
global dynamicdescriptionarea sqlda
global dynamicstagingarea sqlsa
global error error
global message message
end forward

global variables
n_appmanager   gnv_app

end variables

global type bussys from application
string appname = "bussys"
end type
global bussys bussys

on bussys.create
appname="bussys"
message=create message
sqlca=create transaction
sqlda=create dynamicdescriptionarea
sqlsa=create dynamicstagingarea
error=create error
end on

on bussys.destroy
destroy(sqlca)
destroy(sqlda)
destroy(sqlsa)
destroy(error)
destroy(message)
end on

event open;
gnv_app = create n_appmanager

gnv_app.event ue_open("")


end event

