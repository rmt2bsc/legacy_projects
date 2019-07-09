$PBExportHeader$w_splash.srw
$PBExportComments$Splash Screen
forward
global type w_splash from w_rmt_splash
end type
end forward

global type w_splash from w_rmt_splash
int Width=1934
int Height=856
end type
global w_splash w_splash

on w_splash.create
call super::create
end on

on w_splash.destroy
call super::destroy
end on

type gb_1 from w_rmt_splash`gb_1 within w_splash
int X=46
int Height=816
long BackColor=80269524
end type

type p_splash from w_rmt_splash`p_splash within w_splash
int X=78
int Y=64
boolean BringToTop=true
end type

type st_application from w_rmt_splash`st_application within w_splash
int X=73
int Y=452
boolean BringToTop=true
end type

type st_version from w_rmt_splash`st_version within w_splash
int X=73
int Y=548
boolean BringToTop=true
end type

type st_3 from w_rmt_splash`st_3 within w_splash
int X=73
int Y=632
boolean BringToTop=true
end type

