$PBExportHeader$w_logon.srw
$PBExportComments$Logon Screen
forward
global type w_logon from w_rmt_logon
end type
end forward

global type w_logon from w_rmt_logon
int X=466
int Y=764
int Width=2706
int Height=572
boolean TitleBar=true
string Title="System Logon"
end type
global w_logon w_logon

on w_logon.create
call super::create
end on

on w_logon.destroy
call super::destroy
end on

type p_logo from w_rmt_logon`p_logo within w_logon
int X=73
int Y=56
int Width=713
int Height=364
boolean BringToTop=true
end type

type st_help from w_rmt_logon`st_help within w_logon
int X=859
int Y=52
end type

type st_2 from w_rmt_logon`st_2 within w_logon
int X=859
int Y=216
end type

type st_3 from w_rmt_logon`st_3 within w_logon
int X=859
int Y=316
end type

type sle_userid from w_rmt_logon`sle_userid within w_logon
int X=1125
boolean BringToTop=true
int Limit=20
long BackColor=1090519039
end type

type sle_password from w_rmt_logon`sle_password within w_logon
int X=1125
int Y=312
boolean BringToTop=true
int Limit=8
end type

type cb_ok from w_rmt_logon`cb_ok within w_logon
int X=2286
int Y=36
boolean BringToTop=true
boolean Default=true
end type

type cb_cancel from w_rmt_logon`cb_cancel within w_logon
int X=2286
int Y=148
boolean BringToTop=true
end type

