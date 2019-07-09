$PBExportHeader$greens.sra
$PBExportComments$This is an bus charter application  for Green's
forward
global transaction sqlca
global dynamicdescriptionarea sqlda
global dynamicstagingarea sqlsa
global error error
global message message
end forward

global variables
n_appmanager   gnv_app

end variables
global type greens from application
 end type
global greens greens

on greens.create
appname = "greens"
message = create message
sqlca = create transaction
sqlda = create dynamicdescriptionarea
sqlsa = create dynamicstagingarea
error = create error
end on

on greens.destroy
destroy( sqlca )
destroy( sqlda )
destroy( sqlsa )
destroy( error )
destroy( message )
end on

event open;
gnv_app = create n_appmanager

gnv_app.event ue_open("")


end event

