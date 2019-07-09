$PBExportHeader$w_city.srw
$PBExportComments$City Maintenance window
forward
global type w_city from w_rmt_response
end type
type dw_country from u_rmt_dw within w_city
end type
type dw_states from u_rmt_dw within w_city
end type
type dw_city from u_rmt_dw within w_city
end type
type cb_3 from u_rmt_cb_cancel within w_city
end type
type cb_1 from u_rmt_cb_ok within w_city
end type
type cb_2 from u_rmt_cb_save within w_city
end type
type st_1 from statictext within w_city
end type
type st_2 from statictext within w_city
end type
type st_3 from statictext within w_city
end type
type st_6 from statictext within w_city
end type
type st_9 from statictext within w_city
end type
type cb_add from u_rmt_commandbutton within w_city
end type
type cb_delete from u_rmt_commandbutton within w_city
end type
end forward

global type w_city from w_rmt_response
integer x = 786
integer y = 416
integer width = 2843
integer height = 2172
string title = "City Maintenance"
dw_country dw_country
dw_states dw_states
dw_city dw_city
cb_3 cb_3
cb_1 cb_1
cb_2 cb_2
st_1 st_1
st_2 st_2
st_3 st_3
st_6 st_6
st_9 st_9
cb_add cb_add
cb_delete cb_delete
end type
global w_city w_city

event ue_postopen;call super::ue_postopen;int   li_result


dw_country.settransobject(sqlca)
dw_states.settransobject(sqlca)
dw_city.settransobject(sqlca)

dw_country.of_setrowselect(true)
dw_states.of_setrowselect(true)
dw_city.of_setrowselect(true)
dw_city.of_setrowmanager(true)
dw_country.of_setUpdateable(false)
dw_states.of_setUpdateable(false)
dw_city.of_setUpdateable(true)

li_result = dw_city.of_retrieve()
li_result = dw_states.of_retrieve()
li_result = dw_country.of_retrieve()


end event

on w_city.create
int iCurrent
call super::create
this.dw_country=create dw_country
this.dw_states=create dw_states
this.dw_city=create dw_city
this.cb_3=create cb_3
this.cb_1=create cb_1
this.cb_2=create cb_2
this.st_1=create st_1
this.st_2=create st_2
this.st_3=create st_3
this.st_6=create st_6
this.st_9=create st_9
this.cb_add=create cb_add
this.cb_delete=create cb_delete
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_country
this.Control[iCurrent+2]=this.dw_states
this.Control[iCurrent+3]=this.dw_city
this.Control[iCurrent+4]=this.cb_3
this.Control[iCurrent+5]=this.cb_1
this.Control[iCurrent+6]=this.cb_2
this.Control[iCurrent+7]=this.st_1
this.Control[iCurrent+8]=this.st_2
this.Control[iCurrent+9]=this.st_3
this.Control[iCurrent+10]=this.st_6
this.Control[iCurrent+11]=this.st_9
this.Control[iCurrent+12]=this.cb_add
this.Control[iCurrent+13]=this.cb_delete
end on

on w_city.destroy
call super::destroy
destroy(this.dw_country)
destroy(this.dw_states)
destroy(this.dw_city)
destroy(this.cb_3)
destroy(this.cb_1)
destroy(this.cb_2)
destroy(this.st_1)
destroy(this.st_2)
destroy(this.st_3)
destroy(this.st_6)
destroy(this.st_9)
destroy(this.cb_add)
destroy(this.cb_delete)
end on

event ue_add;call super::ue_add;int   li_row
int   li_masterrow
int   li_state_id
int   li_country_id


datastore  ds


if isvalid(dw_city.inv_rowmanager) then
	li_row = dw_city.inv_rowmanager.of_insertrow(1)
end if

li_masterrow = dw_states.getselectedrow(0)
if li_masterrow <= 0 then
	return 0
end if
li_state_id = dw_states.getitemnumber(li_masterrow, "state_id")
li_country_id = dw_states.getitemnumber(li_masterrow, "country_id")
dw_city.setitem(li_row, "state_id", li_state_id)
dw_city.setitem(li_row, "country_id", li_country_id)
dw_city.setitem(li_row, "permanent", 'N')

return li_row


end event

event type integer ue_predelete();call super::ue_predelete;
int   li_code
int   li_row
int   li_dependcount
boolean lb_permanent


li_row = dw_city.getselectedrow(0)
if isNull(li_row) or li_row <= 0 then
	return 0
end if

lb_permanent = (dw_city.getitemstring(li_row, "permanent") = 'Y')

if lb_permanent then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete from the system")
	return -1
end if

li_code = dw_city.getitemnumber(li_row, "city_id")
select count(*)
  into :li_dependcount
  from client
  where contact_city = :li_code or billing_city = :li_code;
  
if li_dependcount > 0 and not isnull(li_dependcount) then
	messagebox(gnv_app.iapp_object.displayname, "The selected group cannot be delete due to database dependencies")
	return -1
end if

return 1

end event
event ue_preupdate;call super::ue_preupdate;int  li_row
int  li_rows
int  li_actualupdatecount
dwitemstatus  l_stat

dw_city.accepttext()
if (dw_city.modifiedcount() + dw_city.deletedcount()) <= 0 then
	return 0
end if

li_rows = dw_city.rowcount()
li_actualupdatecount = 0
for li_row = 1 to li_rows
	l_stat = dw_city.getitemstatus(li_row, 0, primary!)
   if l_stat = newmodified! or l_stat = datamodified! then
		if l_stat = newmodified! then
         dw_city.object.date_created[li_row] = today()
		end if
      dw_city.object.date_updated[li_row] = today()
      dw_city.object.user_id[li_row] = gnv_app.of_getUserID()
	   li_actualupdatecount++
   end if
next

return 1
  
end event

event ue_delete;call super::ue_delete;

if isValid(dw_city.inv_rowmanager) then
	return dw_city.inv_rowmanager.of_deleterow(ai_row)
end if


end event

type dw_country from u_rmt_dw within w_city
integer x = 32
integer y = 120
integer width = 901
integer height = 280
boolean bringtotop = true
string dataobject = "dddw_country"
end type

event rowfocuschanged;call super::rowfocuschanged;string  ls_filter
string  ls_country_id
string  ls_state_id

ls_country_id = string(this.object.code_id[currentrow])
dw_states.setfilter("country_id = " + ls_country_id)
dw_states.filter()
dw_states.setsort("longname A")
dw_states.sort()
if dw_states.rowcount() >= 1 then
   dw_states.selectrow(0, false)
	dw_states.selectrow(1, true)
	if dw_city.rowcount() >= 1 then
      ls_state_id = string(dw_states.object.state_id[1])
      dw_city.setfilter("state_id = " + ls_state_id)
      dw_city.filter()
      dw_city.setsort("longname A")
      dw_city.sort()
	end if
end if
end event

type dw_states from u_rmt_dw within w_city
integer x = 41
integer y = 564
integer width = 901
integer height = 340
integer taborder = 20
boolean bringtotop = true
string dataobject = "dddw_states"
end type

event clicked;call super::clicked;string  ls_filter
string  ls_state_id


if isNull(row) or row <= 0 then
	return 0
end if

ls_state_id = string(this.object.state_id[row])
dw_city.setfilter("state_id = " + ls_state_id)
dw_city.filter()
dw_city.setsort("longname A")
dw_city.sort()

end event

event rowfocuschanged;call super::rowfocuschanged;string  ls_filter
string  ls_state_id

if currentrow <= 0 or isNull(currentrow) or this.rowcount() <= 0 then
	return 1
end if

ls_state_id = string(this.object.state_id[currentrow])
dw_city.setfilter("state_id = " + ls_state_id)
dw_city.filter()
dw_city.setsort("longname A")
dw_city.sort()

end event
type dw_city from u_rmt_dw within w_city
integer x = 55
integer y = 1060
integer width = 2757
integer height = 928
integer taborder = 70
boolean bringtotop = true
string dataobject = "d_city"
end type

type cb_3 from u_rmt_cb_cancel within w_city
integer x = 2112
integer y = 448
integer width = 439
integer height = 84
integer taborder = 60
boolean bringtotop = true
end type

type cb_1 from u_rmt_cb_ok within w_city
integer x = 2112
integer y = 60
integer width = 439
integer height = 84
integer taborder = 50
boolean bringtotop = true
end type

type cb_2 from u_rmt_cb_save within w_city
integer x = 2112
integer y = 156
integer width = 439
integer height = 84
integer taborder = 40
boolean bringtotop = true
end type

type st_1 from statictext within w_city
integer x = 27
integer y = 20
integer width = 1051
integer height = 96
boolean bringtotop = true
integer textsize = -16
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = roman!
string facename = "Times New Roman"
long textcolor = 16711680
long backcolor = 80269524
boolean enabled = false
string text = "List of Countries"
boolean focusrectangle = false
end type

type st_2 from statictext within w_city
integer x = 41
integer y = 460
integer width = 1902
integer height = 96
boolean bringtotop = true
integer textsize = -16
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = roman!
string facename = "Times New Roman"
long textcolor = 16711680
long backcolor = 80269524
boolean enabled = false
string text = "List of States by selected Country"
boolean focusrectangle = false
end type

type st_3 from statictext within w_city
integer x = 55
integer y = 948
integer width = 1618
integer height = 100
boolean bringtotop = true
integer textsize = -16
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = roman!
string facename = "Times New Roman"
long textcolor = 16711680
long backcolor = 80269524
boolean enabled = false
string text = "City Details by selected State (Editable)"
boolean focusrectangle = false
end type

type st_6 from statictext within w_city
integer x = 64
integer y = 2000
integer width = 55
integer height = 76
boolean bringtotop = true
integer textsize = -18
integer weight = 700
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = swiss!
string facename = "Arial"
long textcolor = 255
long backcolor = 67108864
boolean enabled = false
string text = "*"
boolean focusrectangle = false
end type

type st_9 from statictext within w_city
integer x = 137
integer y = 2012
integer width = 887
integer height = 76
boolean bringtotop = true
integer textsize = -9
integer weight = 400
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = swiss!
string facename = "Arial"
long textcolor = 33554432
long backcolor = 67108864
boolean enabled = false
string text = "Cannot be deleted from the system"
boolean focusrectangle = false
end type

type cb_add from u_rmt_commandbutton within w_city
integer x = 2112
integer y = 252
integer width = 439
integer height = 84
integer taborder = 30
boolean bringtotop = true
string text = "Add"
end type

event clicked;iw_parent.of_add()
end event

type cb_delete from u_rmt_commandbutton within w_city
integer x = 2112
integer y = 352
integer width = 439
integer height = 84
integer taborder = 20
boolean bringtotop = true
string text = "Delete"
end type

event clicked;
iw_parent.of_delete(dw_city.getselectedrow(0))
end event

