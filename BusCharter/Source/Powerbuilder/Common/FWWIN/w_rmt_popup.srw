$PBExportHeader$w_rmt_popup.srw
$PBExportComments$Popup Window Ancestor
forward
global type w_rmt_popup from w_rmt_ancestor
end type
end forward

global type w_rmt_popup from w_rmt_ancestor
WindowType WindowType=popup!
boolean TitleBar=true
string Title=""
boolean Resizable=false
end type
global w_rmt_popup w_rmt_popup

on w_rmt_popup.create
call super::create
end on

on w_rmt_popup.destroy
call super::destroy
end on

