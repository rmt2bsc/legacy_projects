$PBExportHeader$u_rmt_calendar.sru
$PBExportComments$PFC Calendar class
forward
global type u_rmt_calendar from u_rmt_base
end type
type cb_close from commandbutton within u_rmt_calendar
end type
type dw_cal from u_rmt_dw within u_rmt_calendar
end type
end forward

global type u_rmt_calendar from u_rmt_base
integer width = 704
integer height = 716
long backcolor = 0
long tabtextcolor = 0
long picturemaskcolor = 25166016
event type integer ue_activate ( )
event ue_hide ( )
event type integer ue_drawmonth ( date ad_date )
cb_close cb_close
dw_cal dw_cal
end type
global u_rmt_calendar u_rmt_calendar

type variables
Public:
// Datawindow Register columnStyle.
constant integer NONE = 1
constant integer DDLB = 2
constant integer DDLB_WITHARROW = 3
//n_cst_dropdown	inv_dropdown

integer ii_registry

Protected:
// Internal attibutes.
dragobject idrg_requestor
datawindow idw_requestor
editmask iem_requestor
Date	id_date
Date	id_prevdate
Date	id_resetdate
String	is_prevcell=''
Integer	ii_boldfontweight=700
Integer	ii_normalfontweight=400
//n_cst_datetime inv_datetime
//n_cst_calendarattrib inv_calendarattrib

// API accessible attributes
Long	il_fontcolor=0
String	is_dateformat=''
Date	id_holiday[]
Date	id_markedday[]
Boolean	ib_holidaybold=False
Boolean	ib_sundaybold=False
Boolean	ib_saturdaybold=False
Boolean	ib_markeddaybold=False
Long	il_sundaycolor=255	
Long	il_saturdaycolor=255
Long	il_holidaycolor=16711680
Long	il_markeddaycolor=65535
Boolean	ib_closeonclick = True
Boolean	ib_closeondclick = True
Boolean	ib_initialvalue= False
Boolean	ib_alwaysredraw = False

//API accessible attributes - DataWindow only attributes.
string	is_dwcolumns[]
string	is_dwcolumnsexp[]
integer	ii_dwcolumnstyle[]

// To support other languages, this array could be changed
// in the constructor event.
String 	is_month[12] = { &
	"January", "February", "March", "April", &
	 "May",  "June", "July", "August",  "September", &
	"October",  "November", "December" }

Constant Integer DWSTYLE_BOX = 1
Constant Integer DWSTYLE_SHADOWBOX = 2
Constant Integer DWSTYLE_LOWERED = 3
Constant Integer DWSTYLE_RAISED = 4
Constant Integer STYLE_BOX = 5
Constant Integer STYLE_SHADOWBOX = 6
Constant Integer STYLE_LOWERED = 7
Constant Integer STYLE_RAISED = 8
Constant Integer DW_HSPLITBAR_WIDTH = 9
Constant Integer TAB_BORDER = 10
Constant Integer MISC_XPOSITION = 11
Constant Integer MISC_YPOSITION = 12
Constant Integer DWMISC_XPOSITION = 13
Constant Integer DWMISC_YPOSITION = 14
Constant Integer DWDETAIL_HEIGHT = 15
Constant Integer BORDER_CHECK = 16
end variables

forward prototypes
public function integer of_setrequestor (dragobject adrg_requestor)
protected function integer of_drawmonth (date ad_date)
public function integer of_setholiday (date ad_dates[])
public function integer of_setmarkedday (date ad_dates[])
public function integer of_setholidaycolor (long al_color)
public function integer of_SetMarkeddayBold (boolean ab_bold)
public function integer of_SetMarkedDayColor (long al_color)
public function integer of_SetSaturdayBold (boolean ab_bold)
public function integer of_SetSaturdayColor (long al_color)
public function integer of_SetSundayBold (boolean ab_bold)
public function integer of_SetSundayColor (long al_color)
public function integer of_setholidaybold (boolean ab_bold)
public function boolean of_IsHolidayBold ()
public function boolean of_IsMarkeddayBold ()
public function boolean of_IsSundayBold ()
public function boolean of_IsSaturdayBold ()
public function long of_GetHolidayColor ()
public function long of_GetMarkeddayColor ()
public function long of_GetSaturdayColor ()
public function long of_GetSundayColor ()
public function integer of_getholiday (ref date ad_dates[])
public function integer of_getmarkedday (ref date ad_dates[])
public function integer of_setcloseonclick (boolean ab_switch)
public function integer of_setcloseondclick (boolean ab_switch)
public function integer of_setdateformat (string as_format)
public function integer of_getregistered (ref string as_dwcolumns[])
public function boolean of_isregistered (string as_dwcolumn)
protected function integer of_setfocusonrequestor ()
public function integer of_register ()
public function integer of_register (string as_dwcolumn)
protected function integer of_setdate (date ad_date, boolean ab_setrequestor)
protected function boolean of_isdatetype (string as_type)
public function integer of_setdropdown (boolean ab_switch)
public function integer of_register (string as_dwcolumn, integer ai_style)
public function integer of_register (integer ai_style)
public function integer of_unregister (string as_dwcolumn)
public function boolean of_IsCloseOnClick ()
public function boolean of_IsCloseOnDClick ()
public function integer of_getregistered (ref string as_dwcolumns[], ref integer ai_dwcolumnstyle[])
public function integer of_getregisteredstyle (string as_dwcolumn)
public function integer of_unregister ()
protected function integer of_redirectfocus ()
public function integer of_setalwaysredraw (boolean ab_switch)
public function boolean of_isalwaysredraw ()
public function integer of_GetRegisterable (ref string as_allcolumns[])
public function integer of_SetInitialValue (boolean ab_switch)
public function boolean of_IsInitialValue ()
public function integer of_position (dragobject adrg_object, boolean ab_makevisible)
public function integer of_getsystemsetting (integer ai_option)
public function integer of_getparentposition (dragobject adrg_object, ref integer ai_x, ref integer ai_y)
public function integer of_activate ()
protected function integer of_reset ()
end prototypes

event ue_activate;
//////////////////////////////////////////////////////////////////////////////
//
//	Function:  ue_activate
//
//	Access:    Protected
//
//	Arguments:	None
//
//	Returns:  Integer
//		1 if it succeeds
//		-1 if an error occurs.
//		0 if the current datawindow column has not been registered.
//
//	Description:  
//		Display the Calendar after getting the appropriate location.
//		For datawindow columns check that the current column has 
//		been registered.
//
//////////////////////////////////////////////////////////////////////////////

Integer 	li_rc
String	ls_colname
Integer	li_x
Integer	li_y

// Check the required references.
if isNull(idrg_requestor) or not isValid(idrg_requestor) then
	return -1
end if

// Datawindow control specific checks.
if IsValid(idw_requestor) then
	// Get the current column name.
	ls_colname = idw_requestor.getColumnName()

	// Check if column is in the search column array.
	if not of_isRegistered(ls_colname) then
		return 0
	end if

	// Validate that it is still a date column.
	if not of_IsDateType(idw_requestor.describe(ls_colname+".coltype")) then
		return -1
	end if
end if

// Set this object on its new Position.
li_rc = this.of_Position(idrg_requestor, false)
if li_rc < 0 then 
	return -1
end if

// Set the calendar to the appropriate date.
of_reset()

// Show the object.
this.visible = true	

return 1

end event

event ue_hide;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			ue_hide
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Hides the Calendar user object
//
//////////////////////////////////////////////////////////////////////////////

this.visible = false	
end event

event ue_drawmonth;//////////////////////////////////////////////////////////////////////////////
//
//	Function:  ue_DrawMonth
//
//	Access:    Protected
//
//	Arguments:
// 	ad_date A Date containing the month to draw.
//
//	Returns:  Integer
//		1 if it succeeds and -1 if an error occurs.
//
//	Description:  Draws the requested month.
//
//////////////////////////////////////////////////////////////////////////////
integer 	li_month
integer 	li_year
integer 	li_day
integer	li_loop, li_daycount
integer	li_upperbound
integer  li_FirstDayNum, li_cell, li_daysinmonth
integer	li_days[12]={31,28,31,30,31,30,31,31,30,31,30,31}
date		ldt_special
string	ls_monthname
string	ls_cell
integer	li_weight
string	ls_modifyexp
date		ldt_holiday[], ldt_markedday[]
boolean  lb_sundaybold, lb_saturdaybold, lb_holidaybold, lb_markeddaybold
long		ll_sundaycolor, ll_saturdaycolor, ll_holidaycolor, ll_markeddaycolor

//Get appropriate information.
of_GetHoliday(ldt_holiday)
of_GetMarkedday(ldt_markedday)
lb_sundaybold = of_IsSundayBold()
lb_saturdaybold = of_IsSaturdayBold()
lb_holidaybold = of_IsHolidayBold()
lb_markeddaybold = of_IsMarkeddayBold()
ll_sundaycolor = of_GetSundayColor()
ll_saturdaycolor = of_GetSaturdayColor()
ll_holidaycolor = of_GetHolidayColor()
ll_markeddaycolor = of_GetMarkeddayColor()		

// Check the argument(s).
if isNull(ad_date) or ad_date = date("50/50/1900") then
	return -1
end if			 
							 
//Set Pointer to an Hourglass and turn off redrawing of Calendar
setPointer(Hourglass!)
setRedraw(dw_cal,false)

//Initialize local values.
li_year = Year(ad_date)
li_month = Month(ad_date)
li_day = Day(ad_date)

//if appropriate, insert a row into the script datawindow
if dw_cal.RowCount()=0 then
	dw_cal.InsertRow(0)
elseif dw_cal.RowCount()> 0 then
	dw_cal.Reset()
	dw_cal.InsertRow(0)
end if

//Set the Title.
ls_monthname = is_month[li_month]
dw_cal.Object.st_month.text = ls_monthname + " " + string(li_year)

//--Determine the number of days in the month.--
// Get the number of days per month for a non leap year.
li_daysinmonth = li_days[li_month]
// Check for a leap year.
if li_month=2 then
	if ( (Mod(li_year,4) = 0 And Mod(li_year,100) <> 0) Or (Mod(li_year,400) = 0) ) then
		li_daysinmonth = 29
	end if
end if

//-- Update the DataWindow object to display the desired month --.
//Find the weekday for the first day in the month.
li_FirstDayNum = DayNumber(Date(li_year, li_month, 1))

//Blank cells prior to the first day of the month.
for li_loop = 1 to li_FirstDayNum
	dw_cal.SetItem(1,li_loop,"")
next

//Set the day number on the the appropriate cells.
for li_loop = 1 to li_daysinmonth
	li_daycount = li_FirstDayNum + li_loop - 1
	dw_cal.SetItem(1,li_daycount,String(li_loop))
next

//Blank cells after the last day of the month.
for li_loop = li_daycount +1 to 42 
	dw_cal.SetItem(1,li_loop,"") 
next

// Restore all cells back to default color and fontweight.
ls_modifyexp = ''
for li_loop = 1 to 42
	ls_modifyexp += "cell"+string(li_loop)+".Color='"+string(il_fontcolor)+"' " + &
						 "cell"+string(li_loop)+".Font.Weight='"+string(ii_normalfontweight)+"' "
next
dw_cal.Modify(ls_modifyexp)

// Mark Sundays.
ls_modifyexp = ''
if lb_sundaybold then li_weight = ii_boldfontweight &
						else li_weight = ii_normalfontweight
for li_loop = 1 to 36 step 7
	ls_modifyexp += "cell"+string(li_loop)+".Color='"+string(ll_sundaycolor)+"' " + &
						 "cell"+string(li_loop)+".Font.Weight='"+string(li_weight)+"' "
next
dw_cal.Modify(ls_modifyexp)

// Mark Saturdays.
ls_modifyexp = ''
if lb_saturdaybold then li_weight = ii_boldfontweight &
						else li_weight = ii_normalfontweight
for li_loop = 7 to 42 step 7
	ls_modifyexp += "cell"+string(li_loop)+".Color='"+string(ll_saturdaycolor)+"' " + &
						 "cell"+string(li_loop)+".Font.Weight='"+string(li_weight)+"' "
next
dw_cal.Modify(ls_modifyexp)

// Mark holidays for this month.
ls_modifyexp = ''
li_upperbound = UpperBound(ldt_holiday)
if li_upperbound > 0 then
	if lb_holidaybold then li_weight = ii_boldfontweight &
							else li_weight = ii_normalfontweight
	for li_loop = 1 to li_upperbound
		ldt_special = ldt_holiday[li_loop]
		if Year(ldt_special)=Year(ad_date) And Month(ldt_special)=Month(ad_date) then
			li_FirstDayNum = DayNumber(Date(Year(ldt_special), Month(ldt_special), 1))
			ls_cell = 'cell'+string(li_FirstDayNum + Day(ldt_special) - 1)
			ls_modifyexp += ls_cell+".Color='"+string(ll_holidaycolor)+"' " + &
								 ls_cell+".Font.Weight='"+string(li_weight)+"' "
		end if
	next
	if Len(Trim(ls_modifyexp)) > 0 then
		dw_cal.Modify(ls_modifyexp)
	end if
end if

// Mark special days for this month.
ls_modifyexp = ''
li_upperbound = UpperBound(ldt_markedday)
if li_upperbound > 0 then
	if lb_markeddaybold then li_weight = ii_boldfontweight &
								else li_weight = ii_normalfontweight
	for li_loop = 1 to li_upperbound
		ldt_special = ldt_markedday[li_loop]
		if Year(ldt_special)=Year(ad_date) And Month(ldt_special)=Month(ad_date) then
			li_FirstDayNum = DayNumber(Date(Year(ldt_special), Month(ldt_special), 1))
			ls_cell = 'cell'+string(li_FirstDayNum + Day(ldt_special) - 1)
			ls_modifyexp += ls_cell+".Color='"+string(ll_markeddaycolor)+"' " + &
								 ls_cell+".Font.Weight='"+string(li_weight)+"' "
		end if
	next
	if Len(Trim(ls_modifyexp)) > 0 then
		dw_cal.Modify(ls_modifyexp)
	end if
end if

//Turn back redraw.
dw_cal.SetRedraw(true)

return 1

end event

public function integer of_setrequestor (dragobject adrg_requestor);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetRequestor
//
//	Access:    Public
//
//	Arguments:
//   adrg_requestor   The object requesting the service.
//			Valid objects are DataWindow! and dropdownlistbox!.
//
//	Returns:  Integer
//		1 if it succeeds 
//		-1 if an error occurs.
//		-2 if attempting to associate object where only dropdowns are supported.
//		-3 MaskDataType is incorrect.
//
//	Description:  Associates an object control with this object 
//		by setting the requestor instance variable(s).
//		The valid objects which can be associated are of type datawindow and 
//		type editmask.  Editmask type should further have a mask of 
//		type DateMask!.
//
//////////////////////////////////////////////////////////////////////////////

dragobject	ldrg_notvalid
editmask		lem_testonly
boolean		lb_dropdownbehavior

// Validate Reference.
if IsNull(adrg_requestor) or not IsValid(adrg_requestor) then
	return -1
end if

// Invalidate references.
idrg_requestor = ldrg_notvalid
idw_requestor = ldrg_notvalid
iem_requestor = ldrg_notvalid


// Make sure it is one of the expected type and get the 
// parent window reference.
choose case TypeOf(adrg_requestor)
	case datawindow!
		idw_requestor = adrg_requestor
	case editmask!
		lem_testonly = adrg_requestor
		if lem_testonly.MaskDataType <> datemask! then
			return -3
		end if
		iem_requestor = adrg_requestor
		if not lb_dropdownbehavior then
			// Field cannot be typed on.
			iem_requestor.DisplayOnly = true
			// Set initial value.
			of_reset()
		End If						
	case else
		return -1
end choose

// Set the generic reference.
idrg_requestor = adrg_requestor

return 1
end function

protected function integer of_drawmonth (date ad_date);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_DrawMonth
//
//	Access:    Protected
//
//	Arguments:
// 	ad_date A Date containing the month to draw.
//
//	Returns:  Integer
//		1 if it succeeds and -1 if an error occurs.
//
//	Description:  Draws the requested month.
//
//////////////////////////////////////////////////////////////////////////////
return  this.event ue_drawMonth(ad_date)
end function

public function integer of_setholiday (date ad_dates[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetHoliday
//
//	Access:    	Public
//
//	Arguments:
//		ad_dates[]  The holidays.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the Holidays of the calendar.
//
//////////////////////////////////////////////////////////////////////////////

// Check the argument.
If IsNull(ad_dates) Then
	Return -1
End If

id_holiday = ad_dates
Return 1
end function

public function integer of_setmarkedday (date ad_dates[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetMarkedDay
//
//	Access:    	Public
//
//	Arguments:
//		ad_dates[]	The Marked dates.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the Marked days for the calendar.
//
//////////////////////////////////////////////////////////////////////////////


// Check the argument.
If IsNull(ad_dates) Then
	Return -1
End If

id_markedday = ad_dates
Return 1
end function

public function integer of_setholidaycolor (long al_color);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetHolidayColor
//
//	Access:    	Public
//
//	Arguments:
//		al_color Text Color for the holidays.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the Holiday text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////

// Check the argument.
IF IsNull(al_color) THEN 
	Return -1
End If

il_holidaycolor = al_color

Return 1
end function

public function integer of_SetMarkeddayBold (boolean ab_bold);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetMarkeddayBold
//
//	Access:    	Public
//
//	Arguments:
//		ab_bold	True or False
//
//	Returns:   	Integer
//   				1 if successful, otherwise -1
//
//	Description:  	Sets the Bold attribute for the Marked dates.
//
//////////////////////////////////////////////////////////////////////////////

// Check the argument.
IF IsNull(ab_bold) THEN 
	Return -1
End If

ib_markeddaybold = ab_bold

Return 1
end function

public function integer of_SetMarkedDayColor (long al_color);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetMarkedDayColor
//
//	Access:    	Public
//
//	Arguments:
//		al_color Text Color for the Marked dates.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the Marked date text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////
// Check the argument.
IF IsNull(al_color) THEN 
	Return -1
End If

il_markeddaycolor = al_color

Return 1
end function

public function integer of_SetSaturdayBold (boolean ab_bold);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetSaturdayBold
//
//	Access:    	Public
//
//	Arguments:
//		ab_bold	True or False
//
//	Returns:   	Integer
//   				1 if successful, otherwise -1
//
//	Description:  	Sets the Bold attribute for the Saturday dates.
//
//////////////////////////////////////////////////////////////////////////////
// Check the argument.
IF IsNull(ab_bold) THEN 
	Return -1
End If

ib_saturdaybold = ab_bold

Return 1
end function

public function integer of_SetSaturdayColor (long al_color);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetSaturdayColor
//
//	Access:    	Public
//
//	Arguments:
//		al_color Text Color for the Saturday dates.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the Saturday date text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////

// Check the argument.
IF IsNull(al_color) THEN 
	Return -1
End If

il_saturdaycolor = al_color

Return 1
end function

public function integer of_SetSundayBold (boolean ab_bold);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetSundayBold
//
//	Access:    	Public
//
//	Arguments:
//		ab_bold	True or False
//
//	Returns:   	Integer
//   				1 if successful, otherwise -1
//
//	Description:  	Sets the Bold attribute for the Sunday dates.
//
//////////////////////////////////////////////////////////////////////////////

// Check the argument.
IF IsNull(ab_bold) THEN 
	Return -1
End If

ib_sundaybold = ab_bold

Return 1
end function

public function integer of_SetSundayColor (long al_color);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetSundayColor
//
//	Access:    	Public
//
//	Arguments:
//		al_color Text Color for the Sunday dates.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the Sunday date text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////
// Check the argument.
IF IsNull(al_color) THEN 
	Return -1
End If

il_sundaycolor = al_color

Return 1
end function

public function integer of_setholidaybold (boolean ab_bold);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetHolidayBold
//
//	Access:    	Public
//
//	Arguments:
//		ab_bold	True or False
//
//	Returns:   	Integer
//   				1 if successful, otherwise -1
//
//	Description:  	Sets the Bold attribute for the Holiday dates.
//
//////////////////////////////////////////////////////////////////////////////

// Check the argument.
IF IsNull(ab_bold) THEN 
	Return -1
End If

ib_holidaybold = ab_bold

Return 1
end function

public function boolean of_IsHolidayBold ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsHolidayBold
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Bold
//					True if dates are bolded.
//
//	Description:  	Gets the Bold attribute for the Holiday dates.
//
//////////////////////////////////////////////////////////////////////////////
Return ib_holidaybold
end function

public function boolean of_IsMarkeddayBold ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsMarkeddayBold
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Bold
//					True if dates are bolded.
//
//	Description:  	Gets the Bold attribute for the Marked dates.
//
//////////////////////////////////////////////////////////////////////////////

Return ib_markeddaybold
end function

public function boolean of_IsSundayBold ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsSundayBold
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Bold
//					True if dates are bolded.
//
//	Description:  	Gets the Bold attribute for the Sunday dates.
//
//////////////////////////////////////////////////////////////////////////////

Return ib_sundaybold
end function

public function boolean of_IsSaturdayBold ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsSaturdayBold
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Bold
//					True if dates are bolded.
//
//	Description:  	Gets the Bold attribute for the Saturday dates.
//
//////////////////////////////////////////////////////////////////////////////

Return ib_saturdaybold
end function

public function long of_GetHolidayColor ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_GetHolidayColor
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   Long
//   	The text color.
//
//	Description:  Gets the Holiday text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////

Return il_holidaycolor
end function

public function long of_GetMarkeddayColor ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_GetMarkeddayColor
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   Long
//   	The text color.
//
//	Description:  Gets the Marked dates text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////

Return il_markeddaycolor
end function

public function long of_GetSaturdayColor ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_GetSaturdayColor
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   Long
//   	The text color.
//
//	Description:  Gets the Saturday dates text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////

Return il_saturdaycolor
end function

public function long of_GetSundayColor ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_GetSundayColor
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   Long
//   	The text color.
//
//	Description:  Gets the Sunday dates text color for the calendar.
//
//////////////////////////////////////////////////////////////////////////////

Return il_sundaycolor
end function

public function integer of_getholiday (ref date ad_dates[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_GetHoliday
//
//	Access:    	Public
//
//	Arguments:
//		ad_dates[]  The holidays by reference.
//
//	Returns:   		Integer
//   					The number of dates on the array.
//
//	Description:  	Gets the Holidays.
//
//////////////////////////////////////////////////////////////////////////////

ad_dates = id_holiday

Return UpperBound(ad_dates)
end function

public function integer of_getmarkedday (ref date ad_dates[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_GetMarkedday
//
//	Access:    	Public
//
//	Arguments:
//		ad_dates[]  The Marked days by reference.
//
//	Returns:   		Integer
//   					The number of dates on the array.
//
//	Description:  	Gets the Marked days.
//
//////////////////////////////////////////////////////////////////////////////

ad_dates = id_markedday

Return UpperBound(ad_dates)
end function

public function integer of_setcloseonclick (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetCloseOnClick
//
//	Access:    	Public
//
//	Arguments:
//   ab_switch  A switch that determines when to close the calendar object.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the switch that determines when to close the 
//						calendar object.
//
//////////////////////////////////////////////////////////////////////////////

// Check to see if the passed style number is valid.
IF IsNull(ab_switch) THEN 
	Return -1
End If

ib_closeonclick = ab_switch
Return 1
end function

public function integer of_setcloseondclick (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SeCloseOnDClick
//
//	Access:    	Public
//
//	Arguments:
//   ab_switch  A switch that determines when to close the calendar object.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	Sets the switch that determines when to close the 
//						calendar object.   "DoubleClick"
//
//////////////////////////////////////////////////////////////////////////////

// Check to see if the passed style number is valid.
IF IsNull(ab_switch) THEN 
	Return -1
End If

ib_closeondclick = ab_switch
Return 1
end function

public function integer of_setdateformat (string as_format);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetDateFormat
//
//	Access:    Public
//
//	Arguments:
//   as_format	The format to be used on dates being converted to strings..
//
//	Returns:  Integer
//		1 if it succeeds and -1 if an error occurs.
//
//	Description:  Sets the date format.
//
//////////////////////////////////////////////////////////////////////////////

// Validate argument.
If IsNull(as_format) or Len(Trim(as_format))=0 Then
	Return -1
End If

// Validate Reference.
If IsNull(idrg_requestor) or Not IsValid(idrg_requestor) Then
	Return -1
End If

is_dateformat = as_format
Return 1
end function

public function integer of_getregistered (ref string as_dwcolumns[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function: 		of_GetRegistered
//
//	Access:  		public
//
//	Arguments:
//	as_dwcolumns[]	Columns names for which the service is providing a calendar 
//						(by reference)
//
//	Returns:  		integer
//						The number of entries in the returned array.
//
//	Description:  	This function returns the column names for which the service 
//						is providing calendar capabilities.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////
integer	li_style[]

Return of_GetRegistered(as_dwcolumns, li_style)
end function

public function boolean of_isregistered (string as_dwcolumn);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_IsRegistered
//
//	Access:  		Public.
//
//	Arguments:
//	as_dwcolumn		The registered column to search for.
//
//	Returns:  		boolean
//						True or False depending if the column is already registered.
//
//	Description: 	This function is called to determine if the passed in 
//						column name has already been registered with the service.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////
integer	li_count
integer	li_i

// Check arguments
If IsNull(as_dwcolumn) Or Len(Trim(as_dwcolumn))=0 Then 
	Return False
End If

// Validate the references.
If IsNull(idw_requestor) or Not IsValid(idw_requestor) Then
	Return False
End If

// Trim and Convert to lower case.
as_dwcolumn = Trim(Lower(as_dwcolumn))

// Find column name.
li_count = upperbound(is_dwcolumns)
For li_i=1 To li_count
	If is_dwcolumns[li_i] = as_dwcolumn THEN
		// Column name was found.
		Return True
	end if
Next

// Column name not found in array.
Return False
end function

protected function integer of_setfocusonrequestor ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetFocusOnRequestor
//
//	Access:    Protected
//
//	Arguments:	None
//
//	Returns:  Integer
//		1 if it succeeds and -1 if an error occurs.
//
//	Description:  Sets the focus on the requestor.
//
//////////////////////////////////////////////////////////////////////////////

// Validate Reference.
If IsNull(idrg_requestor) or Not IsValid(idrg_requestor) Then 
	Return -1
End If

Return idrg_requestor.SetFocus()
end function

public function integer of_register ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_Register
//
//	Access: 			public
//
//	Arguments:		None.
//
//	Returns: 		integer
//						The number of columns registered.
//						-1 if an error is encountered.
//
//	Description:	
//	Register all the appropriate columns that are holding date fields.
// This version should only be called when "ALL" date columns are desired, 
// otherwise call the version which accepts a column name as an argument.
//	Columns need to be of editstyle 'ddlb', 'edit' or 'editmask'.
//
//		*Note:	For a column to be added it most have a field of type Date.
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////

// Use the NONE as a default.
Return of_Register(NONE)
end function

public function integer of_register (string as_dwcolumn);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_Register
//
//	Access:  		public
//
//	Arguments:
//	 as_dwcolumn	Column to register.
//
//	Returns:  		Integer
//						1 if the column was added.
//						0 if the column was not added.
//						-1 if an error is encountered.
//
//	Description: 	
//	 Register the column which should be holding a date field.
//	 Columns need to be of editstyle 'ddlb', 'edit' or 'editmask'.
//
//		*Note:	For a column to be added it most have a field of type Date.
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////

// Use the NONE default.
Return of_Register(as_dwcolumn, NONE)
end function

protected function integer of_setdate (date ad_date, boolean ab_setrequestor);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetDate
//
//	Access:    Protected
//
//	Arguments:
//  ad_date		The date to set.
//	 ab_setrequestor	Switch stating if the requestor object should get this date.
//
//	returns:  Integer
//		1 if it succeeds and -1 if an error occurs.
//
//	Description: Sets a new date on the Visual calendar date.  If appropriate, it
//		will also set the requestor to get this new date.
//
//////////////////////////////////////////////////////////////////////////////

integer			li_rc = 1
integer 			li_month
integer 			li_year
integer 			li_day
integer  		li_FirstDayNum
string			ls_newcell
string			ls_date


// Set the new date.
id_date = ad_date

// if appropriate, set the requestor with the new date.
if ab_setrequestor then
	// Convert the date into a string.
	ls_date = string(ad_date, is_dateformat)

	// Set the requestor with the new date.
	if isValid(idw_requestor) then
		idw_requestor.setText(ls_date)
	elseif isValid(iem_requestor) then
		iem_requestor.Text = ls_date	
	else 
		return -1
	end if
end if
						 
//if appropriate, draw a new month.
if (Year(ad_date) <> Year(id_prevdate) or Month(ad_date) <> Month(id_prevdate)) or ib_alwaysredraw then
	this.of_drawMonth(ad_date)
end if
						 
//Initialize local values.
li_year = Year(ad_date)
li_month = Month(ad_date)
li_day = Day(ad_date)

// Unhighlight any previous cell.
if Len(Trim(is_prevcell)) > 0 then
	if dw_cal.Modify(is_prevcell + ".border=0") <> "" then
		li_rc = -1
	end if
end if

//Highlight the current date.
li_FirstDayNum = DayNumber(Date(li_year, li_month, 1))
ls_newcell = 'cell'+string(li_FirstDayNum + li_day - 1)
if dw_cal.Modify(ls_newcell + ".border=5") <> "" then
	li_rc = -1
end if

// Store the new previous infomration.
is_prevcell = ls_newcell
id_prevdate = ad_date

return li_rc




end function

protected function boolean of_isdatetype (string as_type);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_IsDateType
//
//	Access: 			Protected
//
//	Arguments:
//	 as_type			The type to test for.
//
//	Returns: 		Boolean
//						True if the parameter is of type 'date'.
//
//	Description:	Determines if the passed in type is of type date.
//
//////////////////////////////////////////////////////////////////////////////
boolean lb_date

// Check the required argument.
If IsNull(as_type) Then
	Return False
End If

lb_date = ((as_type = 'date') or (as_type = 'datetime'))
Return lb_date

end function

public function integer of_setdropdown (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_SetDropDown
//
//	Arguments:
//	ab_switch   starts/stops the UserObject DropDown service
//
//	Returns:  integer
//	 1 = Successful operation.
//	 0 = No action necessary
//	-1 = An error was encountered
//
//	Description:
//	Starts or stops the UserObject DropDown service
//
//////////////////////////////////////////////////////////////////////////////

integer	li_rc

// Check arguments.
if IsNull (ab_switch) then return -1

//if ab_Switch then
//	if IsNull(inv_dropdown) Or not IsValid (inv_dropdown) then
//		inv_dropdown = create n_cst_dropdown
//		inv_dropdown.of_SetRequestor (this)
//		li_rc = 1
//	end if
//else
//	if IsValid (inv_dropdown) then
//
//		destroy inv_dropdown
//		li_rc = 1
//	end if
//end if

return li_rc
end function

public function integer of_register (string as_dwcolumn, integer ai_style);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_Register
//
//	Access:  		public
//
//	Arguments:
//	 as_dwcolumn	Column to register.
//	 ai_style		The columnstyle.
//
//	Returns:  		Integer
//						1 if the column was added.
//						0 if the column was not added.
//						-1 if an error is encountered.
//
//	Description: 	
//	 Register the column which should be holding a date field.
//	 Columns need to be of editstyle 'ddlb', 'edit' or 'editmask'.
//
//		*Note:	For a column to be added it most have a field of type Date.
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////

integer 		li_cnt, li_rc
integer		li_availableentry
integer		li_upperbound
string		ls_coltype
string		ls_modexp
string		ls_descexp
string		ls_descret
string		ls_editstyle
string		ls_storemodify=''
string		ls_rc

// Check the required reference.
If IsNull(idw_requestor) or Not IsValid(idw_requestor) Then
	Return -1
End If

// Check arguments
If (IsNull(as_dwcolumn) Or Len(Trim(as_dwcolumn))=0) Or &
	(ai_style < NONE or ai_style >  DDLB_WITHARROW) Or &
	IsNull(idw_requestor) Or Not IsValid(idw_requestor) Then 
	Return -1
End If

// Trim and Convert to lower case.
as_dwcolumn = Trim(Lower(as_dwcolumn))

// Check if the column is already registered.
If of_IsRegistered(as_dwcolumn) Then
	Return 0
End If

// Get the column type.
ls_coltype = idw_requestor.Describe(as_dwcolumn+".coltype")
If of_IsDateType(ls_coltype) Then

	// Get the upperbound of all registered columns.
	li_upperbound = upperbound(is_dwcolumns)
	
	// Determine if there is an open slot available other than a
	// new entry on the array
	For li_cnt = 1 to li_upperbound
		If IsNull(is_dwcolumns[li_cnt]) or Len(Trim(is_dwcolumns[li_cnt])) = 0 Then
			If li_availableentry = 0 Then
				//Get the first slot found
				li_availableentry = li_cnt
				Exit
			End If
		End If
	Next
	//If an available slot was not found then create a new entry
	If li_availableentry = 0 Then
		li_availableentry = li_upperbound + 1
	End If
		
	// Add/Initilize the new entry.				
	is_dwcolumns[li_availableentry] = as_dwcolumn
	ii_dwcolumnstyle[li_availableentry] = ai_style
	is_dwcolumnsexp[li_availableentry] = ''
	
	If ai_style = DDLB Or ai_style = DDLB_WITHARROW Then	

		// Store the Modify expression needed to unregister the column.
		ls_editstyle = idw_requestor.Describe (as_dwcolumn+".Edit.Style")
		CHOOSE CASE Lower(ls_editstyle)
			CASE 'edit'
				ls_descret = idw_requestor.Describe (as_dwcolumn+".Edit.Required")
				If ls_descret = 'yes' or ls_descret = 'no' Then
					ls_storemodify += as_dwcolumn+".Edit.Required=" + ls_descret + " "
					ls_modexp = as_dwcolumn+".DDLB.Required=" + ls_descret + " "
				End If			
				ls_descret = idw_requestor.Describe (as_dwcolumn+".Edit.NilIsNull")				
				If ls_descret = 'yes' or ls_descret = 'no' Then
					ls_storemodify += as_dwcolumn+".Edit.NilIsNull=" + ls_descret + " "				
					ls_modexp += as_dwcolumn+".DDLB.NilIsNull=" + ls_descret + " "
				End If					
			CASE 'editmask'
				ls_descret = idw_requestor.Describe (as_dwcolumn+".EditMask.Mask")
				If ls_descret = '!' or ls_descret = '?' Then
					ls_storemodify += as_dwcolumn+".EditMask.Mask='' "		
				Else
					ls_storemodify += as_dwcolumn+".EditMask.Mask='" + ls_descret + "' "				
				End If						
				ls_descret = idw_requestor.Describe (as_dwcolumn+".EditMask.Required")
				If ls_descret = 'yes' or ls_descret = 'no' Then
					ls_storemodify += as_dwcolumn+".EditMask.Required=" + ls_descret + " "				
					ls_modexp = as_dwcolumn+".DDLB.Required=" + ls_descret + " "
				End If			
				ls_descret = idw_requestor.Describe (as_dwcolumn+".EditMask.NilIsNull")				
				If ls_descret = 'yes' or ls_descret = 'no' Then
					ls_storemodify += as_dwcolumn+".EditMask.NilIsNull=" + ls_descret + " "				
					ls_modexp += as_dwcolumn+".DDLB.NilIsNull=" + ls_descret + " "
				End If					
			CASE 'ddlb'
				ls_descret = idw_requestor.Describe (as_dwcolumn+".DDLB.useasborder")	
				If ls_descret = 'yes' or ls_descret = 'no' Then
					ls_storemodify = as_dwcolumn+".DDLB.useasborder=" + ls_descret + " "	
				End If			
			CASE Else
				// Not a valid original edit style.
				Return -1
		END CHOOSE
			
		// Store the Modify statement that allows unregister.
		is_dwcolumnsexp[li_availableentry] = ls_storemodify		
		
		// Convert to DDLB.
		ls_modexp += as_dwcolumn+".DDLB.limit=0 " + &
						 as_dwcolumn+".DDLB.AllowEdit=Yes " 
		ls_rc = idw_requestor.Modify (ls_modexp)
		If Len(ls_rc) > 0 Then Return -1

		If ai_style = DDLB_WITHARROW Then		
			ls_modexp =	as_dwcolumn+".DDLB.useasborder=Yes " 
			ls_rc = idw_requestor.Modify (ls_modexp)	
			If Len(ls_rc) > 0 Then Return -1			
		End If
	End If	
	
	// The column was registered.
	Return 1
End If	

// The column was not registered.
Return 0
end function

public function integer of_register (integer ai_style);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_Register
//
//	Access: 			public
//
//	Arguments:		
//	 ai_style		The style for all the columns.
//
//	Returns: 		integer
//						The number of columns registered.
//						-1 if an error is encountered.
//
//	Description:	
//	Register all the appropriate columns that are holding date fields.
// This version should only be called when "ALL" date columns are desired, 
// otherwise call the version which accepts a column name as an argument.
//	Columns need to be of editstyle 'ddlb', 'edit' or 'editmask'.
//
//		*Note:	For a column to be added it most have a field of type Date.
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////

integer		li_colcount, li_i, li_count, li_rc
string		ls_colname
string		ls_coltype
string		ls_editstyle

// Check the arguments.
If	(ai_style < NONE or ai_style >  DDLB_WITHARROW)  Then
	Return -1
End If

// Check the required reference.
If IsNull(idw_requestor) or Not IsValid(idw_requestor) Then
	Return -1
End If

// Get the number of columns in the datawindow object
li_colcount = integer(idw_requestor.object.datawindow.Column.Count)

// Loop around all columns looking for date columns.
For li_i=1 to li_colcount
	//Get-Validate the name and column type of the column.
	ls_colname = idw_requestor.Describe("#"+string(li_i)+".Name")
	ls_coltype = idw_requestor.Describe("#"+string(li_i)+".ColType")	
	ls_editstyle = idw_requestor.Describe ("#"+string(li_i)+".Edit.Style")
	If ls_coltype = '!' or ls_colname = '!' or ls_editstyle = '!' Then 
		Return -1	
	End If
	
	If ls_editstyle = 'ddlb' or ls_editstyle='edit' or ls_editstyle='editmask' Then
		If of_IsDateType(ls_coltype) Then
			// Add entry into array.
			li_rc = of_Register(ls_colname, ai_style)
		End If
	End If
Next

Return upperbound(is_dwcolumns)
end function

public function integer of_unregister (string as_dwcolumn);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_UnRegister
//
//	Access:  		Public.
//
//	Arguments:
//	as_dwcolumn		The registered column to search for.
//
//	Returns:  		Integer
//	1 successful.
// 0 not previously registered.
//	-1 error.
//
//	Description: 	
//	 UnRegisters the object from the service by the column name.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////


Integer	li_upper
Integer	li_cnt
String	ls_rc
Constant String EMPTY=''

// Check for a valid ID.
If IsNull(as_dwcolumn) or Len(Trim(as_dwcolumn))= 0  Then
	Return -1
End If

// Trim and Convert to lower case.
as_dwcolumn = Trim(Lower(as_dwcolumn))

// Find the Column.
li_upper = UpperBound(is_dwcolumns)
For li_cnt = 1 to li_upper
	If as_dwcolumn = is_dwcolumns[li_cnt] Then 
		// The entry has been found.  
		// Clear out any attribute changes.
		If Len(is_dwcolumnsexp[li_cnt]) > 0 Then
			ls_rc = idw_requestor.Modify (is_dwcolumnsexp[li_cnt])	
			If Len(ls_rc) > 0 Then 
				Return -1			
			End If
		End If

		// Perform the actual Unregister.
		is_dwcolumns[li_cnt] = EMPTY
		is_dwcolumnsexp[li_cnt]= EMPTY
		ii_dwcolumnstyle[li_cnt]= 0
		
		Return 1
	End If
Next

// The column was not found.
Return 0
end function

public function boolean of_IsCloseOnClick ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsCloseOnClick
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Boolean
//  True / False
//
//	Description:  	
//		Helps in determining when it is ok to close the object. "Single Click"
//
//////////////////////////////////////////////////////////////////////////////


Return ib_closeonclick
end function

public function boolean of_IsCloseOnDClick ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsCloseOnDClick
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Boolean
//  True / False
//
//	Description:  	
//		Helps in determining when it is ok to close the object. "Double Click"
//
//////////////////////////////////////////////////////////////////////////////


Return ib_closeondclick 
end function

public function integer of_getregistered (ref string as_dwcolumns[], ref integer ai_dwcolumnstyle[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function: 		of_GetRegistered
//
//	Access:  		public
//
//	Arguments:
//	as_dwcolumns[]	Columns names for which the service is providing a calendar 
//						(by reference)
//	ai_dwcolumnstyle[] The style for the columns (by reference)
//
//	Returns:  		integer
//						The number of entries in the returned array(s).
//
//	Description:  	This function returns the column names for which the service 
//						is providing calendar capabilities.  It also returns the style.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////

integer 	li_i
integer	li_loop
integer	li_upper
integer	li_cnt
string	ls_empty[]
integer	li_empty[]

// Initialize strings.
as_dwcolumns = ls_empty
ai_dwcolumnstyle = li_empty

// Validate the references.
If IsNull(idw_requestor) or Not IsValid(idw_requestor) Then
	Return -1
End If

// Loop around all entries and populate arrays with columnnames and style.
li_upper = upperbound(is_dwcolumns)
For li_i=1 To li_upper
	If Len(is_dwcolumns[li_i]) > 0 Then
		li_cnt ++
		as_dwcolumns[li_cnt] = is_dwcolumns[li_i]
		ai_dwcolumnstyle[li_cnt] = ii_dwcolumnstyle[li_i]
	End If
Next

Return UpperBound(as_dwcolumns)
end function

public function integer of_getregisteredstyle (string as_dwcolumn);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetRegisteredStyle
//
//	Access:  		Public.
//
//	Arguments:
//	as_dwcolumn		The registered column to search for.
//
//	Returns:  		integer
//		The style for the column passed in.
//		0 if the column is not registered.
//		-1 if an error is encountered.
//
//	Description: 	
//	 This function is called to determine the style of the column name passed in.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////

integer	li_count
integer	li_i

// Check arguments
If IsNull(as_dwcolumn) Or Len(Trim(as_dwcolumn))=0 Then 
	Return 0
End If

// Validate the references.
If IsNull(idw_requestor) or Not IsValid(idw_requestor) Then
	Return -1
End If

// Trim and Convert to lower case.
as_dwcolumn = Trim(Lower(as_dwcolumn))

// Find column name.
li_count = upperbound(is_dwcolumns)
For li_i=1 To li_count
	If is_dwcolumns[li_i] = as_dwcolumn THEN
		// Column name was found.
		Return ii_dwcolumnstyle[li_i]
	end if
Next

// Column name not found in array.
Return 0
end function

public function integer of_unregister ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_UnRegister
//
//	Access:  		Public.
//
//	Arguments:		None
//
//	Returns:  		Integer
//	1 successful.
// 0 nothing previously registered.
//
//	Description: 	
//	 UnRegisters all registerd columns from the service.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////


Integer	li_upper
Integer	li_cnt
Integer	li_unregistered = 0

// Loop around all registered columns.
li_upper = UpperBound(is_dwcolumns)
For li_cnt = 1 to li_upper
	If Len(is_dwcolumns[li_cnt]) > 0 Then
		If of_Unregister(is_dwcolumns[li_cnt]) = 1 Then
			li_unregistered ++
		End If
	End If
Next

If li_unregistered > 0 Then
	Return 1
End If
	
Return 0
end function

protected function integer of_redirectfocus ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_RedirectFocus
//
//	Access:    Protected
//
//	Arguments:	None
//
//	Returns:  Integer
//	1 if it succeeds.
//	-1 if an error occurs.
//
//	Description:  
//	Prevent this object from having focus while not visible.
//
//////////////////////////////////////////////////////////////////////////////


// Prevent this object from having focus while not visible.
If this.Visible = False Then
	Return of_SetFocusOnRequestor()
End If

Return 1
end function

public function integer of_setalwaysredraw (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetAlwaysRedraw
//
//	Access:    	Public
//
//	Arguments:
//		ab_switch	True or False
//
//	Returns:   	Integer
//   				1 if successful, otherwise -1
//
//	Description:  	
//	Sets the attribute which can force a redraw of the calendar month at various
// times.
//
//////////////////////////////////////////////////////////////////////////////


// Check the argument.
IF IsNull(ab_switch) THEN 
	Return -1
End If

ib_alwaysredraw = ab_switch
Return 1
end function

public function boolean of_isalwaysredraw ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsAlwaysRedraw
//
//	Access:    	Public
//
//	Arguments:
//		ab_switch	True or False
//
//	Returns:   	Boolean
//   True /False
//
//	Description:  	
//	Reports on the attribute which forces a redraw of the calendar month at various
// times.
//
//////////////////////////////////////////////////////////////////////////////


Return ib_alwaysredraw
end function

public function integer of_GetRegisterable (ref string as_allcolumns[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetRegisterable
//
//	Access:  		public
//
//	Arguments:
//	as_allcolumns[] By Reference.  All columns belonging to the requestor which
//						could be registered.
//
//	Returns:  		Integer
//	 The column count.
//	-1 if an error is encountered.
//
//	Description:
//	 Determines all columns belonging to the requestor which could be registered.
//
//		*Note:	Function is only valid when serving a DataWindow control.
//
//////////////////////////////////////////////////////////////////////////////


integer		li_colcount, li_i
integer		li_count
string		ls_coltype
string		ls_colname
string		ls_editstyle
string		ls_allcolumns[]

// Initialize.
as_allcolumns = ls_allcolumns

// Validate required reference.
If IsNull(idw_requestor) or Not IsValid(idw_requestor) Then
	Return -1
End If

// Get the number of columns in the datawindow object
li_colcount = integer(idw_requestor.object.datawindow.Column.Count)

// Loop around all columns looking for Date columns.
For li_i=1 to li_colcount
	ls_coltype = idw_requestor.Describe("#"+string(li_i)+".coltype")
	ls_editstyle = idw_requestor.Describe ("#"+string(li_i)+".Edit.Style")

	If ls_editstyle = 'ddlb' or ls_editstyle='edit' or ls_editstyle='editmask' Then
		If of_IsDateType(ls_coltype) Then	
			ls_colname = idw_requestor.Describe("#"+string(li_i)+".Name")

			// Add entry into array.
			li_count = upperbound(ls_allcolumns) +1
			ls_allcolumns[li_count] = ls_colname		
		End If
	End If
	
Next

as_allcolumns = ls_allcolumns
Return UpperBound(as_allcolumns)

end function

public function integer of_SetInitialValue (boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_SetInitialValue
//
//	Access:    	Public
//
//	Arguments:
//   ab_switch  A switch that determines if todays date should be set on the
//					requestor when an invalid date or no date is found on the requestor.
//
//	Returns:   		Integer
//   					1 if successful, otherwise -1
//
//	Description:  	
// Sets the switch that determines if an initial (todays date) value should be used 
// when an initial invalid value or no value is found on the requestor.
//
// *Note: The main behavior change through this attribute is in the row/column status.
//		For example,
//		A) The attribute is set to True.
//			1) The calendar is requested on a field that has no date.
//			2) The calendar dropsdown with todays date showing.
//			3) The field also displays todays date.  This means the column status may
//				be changed.  One possibility is from NotModified! to Modified!.
//		B) The attribute is set to False.
//			1) The calendar is requested on a field that has no date.
//			2) The calendar dropsdown with todays date showing.
//			3) The field continues to display empty.  This means the column status 
//				has not changed.
//
//////////////////////////////////////////////////////////////////////////////

// Check to see if the passed style number is valid.
IF IsNull(ab_switch) THEN 
	Return -1
End If

ib_initialvalue = ab_switch
Return 1
end function

public function boolean of_IsInitialValue ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  	of_IsInitialValue
//
//	Access:    	Public
//
//	Arguments:	None
//
//	Returns:   	Boolean
//
//	Description: 
// Gets the switch that determines if an initial (todays date) value should be used 
// when an initial invalid value or no value is found on the requestor.
//
//////////////////////////////////////////////////////////////////////////////


Return ib_initialvalue

end function

public function integer of_position (dragobject adrg_object, boolean ab_makevisible);/////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_Position
//
//	Access:    Public
//
//	Arguments:	
//		adrg_object 	The object that is used to calculate the new position.
//		ab_makevisible if true then the Dropdown object will be made visible
//				after a valid position is calculated.
//
//	returns:  integer
//		1 if it succeeds.
//		-1 if an error occurs.
//		-2 if the requested functionality is not supported.
//		-3 if a valid position could not be calculated.
//
//	Description:
//		Updates the position of the requestor object based on the 
//		current location of the adrg_object.  The adrg_object is the object
// 	to which the "actual dropdown object" is associated. 
//		if adrg_object is a datawindow, it is based on the current column/row
//		within the datawindow.
//
// Note: Title Bar on datawindows are not supported.
//
//////////////////////////////////////////////////////////////////////////////

integer	li_x, li_y
integer	li_idx
integer	li_objheight
integer	li_parentx, li_parenty
integer	li_rc
integer	li_colx, li_coly, li_colheight
integer	li_detailheight
integer	li_dwx, li_dwy, li_dwborder, li_dwtitlebar, li_dwtitleborder
integer	li_headerheight
integer	li_groupheaderheight, li_grouptrailerheight
integer	li_groupheaderheightarray[], li_grouptrailerheightarray[]
integer	li_groupbreakarray[]
long		ll_groupchange
long		ll_testrow
integer	li_group, li_groupcnt, li_breakcnt
string	ls_colname
string	ls_headerheight, ls_trailerheight
integer	li_rowsafterfirst	
integer	li_counter
integer	li_hsplit, li_hpos, li_hpos1, li_hpos2
integer	li_pointerx, li_pointery
integer	li_bordercheck
long		ll_firstrowonpage
long		ll_lastrowonpage
long		ll_currrow
datawindow ldw_object

// Validate the references.
if IsNull(adrg_object) or Not IsValid(adrg_object)  then
	return -1
end if

// Get the parent window.
//of_GetParentWindow(adrg_object, lw_parent)
if IsNull(iw_parent) or Not IsValid(iw_parent) then
	return -1
end if

// Get the X/Y coordinates for the parent object holding this datawindow.
this.of_GetParentPosition(adrg_object, li_parentx, li_parenty)

// Determine which type of processing is needed.
if adrg_object.TypeOf() = DataWindow! then
	// Cast to the appropriate variable type.
	ldw_object = adrg_object

	// Determine if this positioning is not supported.
	if ldw_object.Titlebar then
		// Not supported.
		idrg_requestor.Visible = False
		return -2
		// Calculate Title Bar attributes.
		//li_dwtitlebar = ?
		//li_dwtitleborder = ?
	end if

	// Get the column name.
	ls_colname = ldw_object.GetColumnName()

	// Get exact pointers.
	li_pointerx = ldw_object.PointerX()
	li_pointery = ldw_object.PointerY()

	// Get the row values.
	ll_firstrowonpage = Long(ldw_object.Describe("DataWindow.FirstRowOnPage"))
	ll_lastrowonpage = Long(ldw_object.Describe("DataWindow.LastRowOnPage"))
	ll_currrow	= ldw_object.GetRow()

	// Get the DataWindow X/Y coordinates, Border width, and Title width. 
	li_dwx = ldw_object.X
	li_dwy = ldw_object.Y
	if ldw_object.Border then
		CHOOSE CASE ldw_object.BorderStyle
			CASE StyleBox!
				if Not ldw_object.Titlebar then
					li_dwborder = this.of_GetSystemSetting(DWSTYLE_BOX)
				end if
			CASE StyleShadowBox!
				if Not ldw_object.Titlebar then
					li_dwborder = this.of_GetSystemSetting(DWSTYLE_SHAdoWBOX)
				end if
			CASE StyleLowered!
				li_dwborder = this.of_GetSystemSetting(DWSTYLE_LOWERED)
			CASE StyleRaised!		
				li_dwborder = this.of_GetSystemSetting(DWSTYLE_RAISED)
		END CHOOSE
	end if

	// Get the X/Y point of the Left/Upper location for this column.
	li_colx = integer(ldw_object.Describe(ls_colname+".X"))
	li_coly = integer(ldw_object.Describe(ls_colname+".Y"))

	// Get the Height for this column and for the the Detail portion.
	li_colheight = integer(ldw_object.Describe(ls_colname+".Height"))
	li_detailheight = integer(ldw_object.Describe("DataWindow.Detail.Height")) + this.of_GetSystemSetting(DWDETAIL_HEIGHT)

	// Get the height of the header band.
	ls_headerheight = ldw_object.Describe("DataWindow.Header.Height")
	if IsNumber(ls_headerheight) then 
		li_headerheight += integer(ls_headerheight)		
	end if
	
	// Get the band height(s) for group headers and trailers.
	li_idx = 0
	do
		li_idx ++
		ls_headerheight =ldw_object.Describe("DataWindow.Header."+string(li_idx)+".Height")
		ls_trailerheight =ldw_object.Describe("DataWindow.Trailer."+string(li_idx)+".Height")
		if Pos(ls_headerheight, "!") = 0 then
			li_groupheaderheightarray[li_idx] = integer(ls_headerheight)
			li_grouptrailerheightarray[li_idx] = integer(ls_trailerheight)
		end if	
	loop until Pos(ls_headerheight, "!") > 0

	// Attempt to determine the number of group breaks visible prior to 
	// the clicked row.
	li_groupcnt = UpperBound(li_groupheaderheightarray)
	for li_group = 1 to li_groupcnt
		ll_testrow = ll_firstrowonpage
		li_breakcnt = 0
		
		// Catch the "gap".
		ll_groupchange = ldw_object.FindGroupChange(ll_firstrowonpage, li_group)
		if ll_firstrowonpage <> ll_groupchange then li_breakcnt ++
		
		do while ll_testrow >= 0 and ll_testrow <= ll_currrow
			ll_testrow = ldw_object.FindGroupChange(ll_testrow, li_group)
			if ll_testrow > 0 then
				if ll_testrow <= ll_currrow then li_breakcnt ++
			else
				Exit
			end if
			ll_testrow ++
		loop
		li_groupbreakarray[li_group] = li_breakcnt
	next

//	// Debugging - display the group header/trailer/breaks arrays.
//	string ls_temp
//	ls_temp = 'HeightArray = ' 
//	for li_idx = 1 to UpperBound(li_groupheaderheightarray)
//		ls_temp += '  ' +String(li_groupheaderheightarray[li_idx])
//	next
//	ls_temp += '   TrailerArray = '
//	for li_idx = 1 to UpperBound(li_grouptrailerheightarray)
//		ls_temp += '  ' +String(li_grouptrailerheightarray[li_idx])
//	next	
//	ls_temp += '   BreakArray = '
//	for li_idx = 1 to UpperBound(li_groupbreakarray)
//		ls_temp += '  ' +String(li_groupbreakarray[li_idx])
//	next	
//	gnv_app.of_debug (ls_temp)	

	// Calculate the total Height for each Header/Trailer.
	for li_idx = 1 to UpperBound(li_groupheaderheightarray)
		li_groupheaderheight += li_groupheaderheightarray[li_idx] * li_groupbreakarray[li_idx]
		if li_groupbreakarray[li_idx] > 1 then
			li_grouptrailerheight += li_grouptrailerheightarray[li_idx] * (li_groupbreakarray[li_idx] - 1)
		end if
	next

	// Determine the on-screen row.
	li_rowsafterfirst = ll_currrow - ll_firstrowonpage
	if li_rowsafterfirst < 0 then
		// Hide the object since the row is not visible on the screen.
		idrg_requestor.Visible = False
		return -3
	end if

	// Get Horizontal Scrollbar and Horizontal Split Scrolling variables.
	li_hsplit = integer (ldw_object.Describe("DataWindow.HorizontalScrollSplit"))
	li_hpos1 = integer (ldw_object.Describe("DataWindow.HorizontalScrollPosition"))
	li_hpos2 = integer (ldw_object.Describe("DataWindow.HorizontalScrollPosition2"))
	if ldw_object.hsplitscroll then
		if li_hsplit > 4 and li_pointerx > li_hsplit then
			li_hpos = li_hpos2 - li_hsplit - this.of_GetSystemSetting(DW_HSPLITBAR_WIDTH)
		else
			li_hpos = li_hpos1
		end if
	else
		li_hpos = li_hpos1
	end if

	// Determine the Height of the column holding the dropdown.
	li_objheight = li_colheight
	
	// Calculate the X and Y Coordinates (check that it does not go past borders).
	li_x = li_parentx + li_dwx + li_dwborder + li_dwtitleborder + &
		li_colx - li_hpos + this.of_GetSystemSetting(DWMISC_XPOSITION)
	li_y = li_parenty + li_dwy + li_dwborder + li_dwtitleborder + li_dwtitlebar + &
		li_coly + li_headerheight +  &
		li_groupheaderheight + li_grouptrailerheight + &
		(li_detailheight * li_rowsafterfirst ) + &
		li_colheight + this.of_GetSystemSetting(DWMISC_YPOSITION)
		
//	gnv_app.of_debug ('1Final values li_y='+string(li_y)+ &
//		' li_parenty='+string(li_parenty)+' li_dwy='+string(li_dwy)+ &
//		' li_dwborder='+string(li_dwborder)+' li_dwtitleborder='+string(li_dwtitleborder))
//	gnv_app.of_debug ('2Final values li_coly='+string(li_coly)+  &
//		' li_headerheight='+string(li_headerheight)+' li_groupheaderheight='+string(li_groupheaderheight)+&
//		' li_grouptrailerheight='+string(li_grouptrailerheight)+ &
//		' li_detailheight='+string((li_detailheight * li_rowsafterfirst )))
//	gnv_app.of_debug ('3Final values li_colheight='+string(li_colheight))
//	gnv_app.of_debug ('4Other values li_pointery='+string(li_pointery))
		
else
	// Determine the Object Height of the control holding the dropdown.
	li_objheight = adrg_object.Height
	
	// Calculate the preffered X and Y Coordinates.
	li_x = li_parentx + adrg_object.X + this.of_GetSystemSetting(MISC_XPOSITION)
	li_y = li_parenty + adrg_object.Y + li_objheight + this.of_GetSystemSetting(MISC_YPOSITION)
end if

// Get the border check value.
li_bordercheck = this.of_GetSystemSetting(BORDER_CHECK)

// Make sure the coordinates will not force the calendar past the Right border.
if (iw_parent.WorkSpaceWidth() - idrg_requestor.Width - li_bordercheck) > 0 then
	if (li_x + idrg_requestor.Width +li_bordercheck  > iw_parent.WorkSpaceWidth()) then
		// Position it at the extreme right without going past border.
		li_x = iw_parent.WorkSpaceWidth() - idrg_requestor.Width - li_bordercheck
	end if
end if

// Make sure this coordinates will not force the calendar past the Bottom border.
if (li_y - li_objheight - idrg_requestor.Height) > 0 then
	if (li_y + idrg_requestor.Height +li_bordercheck > iw_parent.WorkSpaceHeight()) then
		// Position on top of the field.
		li_y = li_y - li_objheight - idrg_requestor.Height
	end if
end if

// Position the requestor object to the calculated coordinates.
this.Move (li_x, li_y)

// if requested, make the dropdown object visible.
if ab_makevisible then
	idrg_requestor.Visible = True
end if

return 1

end function

public function integer of_getsystemsetting (integer ai_option);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetSystemSetting
//
//	Scope:	Protected
//
//	Arguments:
//		ai_option	The name of the desired setting.
//
//	Returns:  Integer
//		The setting.
//
//	Description:  Get the platform specific setting.
//
//////////////////////////////////////////////////////////////////////////////


Environment lenv_object

GetEnvironment(lenv_object)

If lenv_object.ostype = Windows! Then

	CHOOSE CASE ai_option
		CASE DWSTYLE_BOX
			Return 3
		CASE DWSTYLE_SHADOWBOX
			Return 3			
		CASE DWSTYLE_LOWERED
			Return 6			
		CASE DWSTYLE_RAISED
			Return 6		
		CASE DWDETAIL_HEIGHT
			Return 0
			Return -1
		CASE STYLE_BOX
			Return 3			
		CASE STYLE_SHADOWBOX
			Return 3			
		CASE STYLE_LOWERED
			Return 6					
		CASE STYLE_RAISED
			Return 6			
		CASE DW_HSPLITBAR_WIDTH
			Return 12					
		CASE TAB_BORDER
			Return 0		
		CASE MISC_XPOSITION
			Return 0						
		CASE MISC_YPOSITION
			Return 0		
		CASE DWMISC_XPOSITION
			Return 0						
		CASE DWMISC_YPOSITION
			Return 3			
		CASE BORDER_CHECK 			
			Return 3
	END CHOOSE

Else
	CHOOSE CASE ai_option
		CASE DWSTYLE_BOX
			Return 3
		CASE DWSTYLE_SHADOWBOX
			Return 3			
		CASE DWSTYLE_LOWERED
			Return 6			
		CASE DWSTYLE_RAISED
			Return 6		
		CASE DWDETAIL_HEIGHT
			Return 0
			Return -1
		CASE STYLE_BOX
			Return 3			
		CASE STYLE_SHADOWBOX
			Return 3			
		CASE STYLE_LOWERED
			Return 6					
		CASE STYLE_RAISED
			Return 6			
		CASE DW_HSPLITBAR_WIDTH
			Return 12					
		CASE TAB_BORDER
			Return 0		
		CASE MISC_XPOSITION
			Return 0						
		CASE MISC_YPOSITION
			Return 0		
		CASE DWMISC_XPOSITION
			Return 0						
		CASE DWMISC_YPOSITION
			Return 3			
		CASE BORDER_CHECK 			
			Return 3
	END CHOOSE


End If

Return 0
end function

public function integer of_getparentposition (dragobject adrg_object, ref integer ai_x, ref integer ai_y);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetParentPosition
//
//	Access:  		public
//
//	Arguments:
//	adrg_object			The dragobject whose parent position is needed.
//	al_x(by reference) The x coordinate for the parent.
//	al_y(by reference) The y coordinate for the parent.
//
//	returns:  		Integer
//						1 if it succeeds and -1 if an error occurs.
//
//	Description:	Calculates the parent position in relationship to the window.
//
//////////////////////////////////////////////////////////////////////////////

PowerObject	lpo_parent
UserObject	luo_parent
Tab			ltab_parent
integer		li_parentx, li_parenty
integer		li_width
integer		li_x, li_y
integer		li_border
string      ls_colname

// Validate required reference.
if IsNull(adrg_object) or Not IsValid(adrg_object) then
	return -1
end if

// Loop getting the parent of the object until it is of type window!
lpo_parent = adrg_object.GetParent()
do while IsValid (lpo_parent)
	if lpo_parent.TypeOf() = window! then
		exit
	end if
	li_border = 0
	choose case TypeOf(lpo_parent)
		case UserObject!
			luo_parent = lpo_parent
			li_parentx = luo_parent.X 
			li_parenty = luo_parent.Y 
			// Determine the Left/Upper Border.
			if luo_parent.Border then
				choose case luo_parent.BorderStyle
					case StyleBox!
						li_border = of_GetSystemSetting(STYLE_BOX)
					case StyleShadowBox!
						li_border = of_GetSystemSetting(STYLE_SHADOWBOX)
					case StyleLowered!
						li_border = of_GetSystemSetting(STYLE_LOWERED)
					case StyleRaised!		
						li_border = of_GetSystemSetting(STYLE_RAISED)
				end choose				
			end if			
		case Tab!
			ltab_parent = lpo_parent
			li_parentx = ltab_parent.X 
			li_parenty = ltab_parent.Y 
			li_border = of_GetSystemSetting(TAB_BORDER)
	end choose
	li_x += li_parentx + li_border
	li_y += li_parenty + li_border
	lpo_parent = lpo_parent.GetParent()
loop

if not IsValid (lpo_parent) then
	return -1
end if

ai_x = li_x
ai_y = li_y

return 1


end function

public function integer of_activate ();//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			of_activate
//
//	(Arguments:		None)
//
//	Returns:  Integer
//		1 if it succeeds
//		-1 if an error occurs.
//		0 if the current datawindow column has not been registered.
//
//	Description:  Request the activate calendar.
//
//////////////////////////////////////////////////////////////////////////////

Return this.event ue_activate()
end function

protected function integer of_reset ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_Reset
//
//	Access:    Protected
//
//	Arguments:	None.
//
//	returns:  Integer
//		1 if it succeeds and -1 if an error occurs.
//
//	Description: Resets the calendar visual date according to the requestor date.
//
//////////////////////////////////////////////////////////////////////////////

string			ls_date
integer			li_FirstDayNum, li_Cell, li_DaysInMonth
string			ls_Year, ls_return
date				ld_FirstDay
integer 			li_month
integer 			li_year
integer 			li_day
n_rmt_utility      lnv_util

// Validate Reference.
if isValid(idw_requestor) Or isValid(iem_requestor) then
	// Good reference.
else
	return -1
end if

// Get the current date from the requesting object.
if isValid(idw_requestor) then
	ls_date = idw_requestor.getText()
elseif isValid(iem_requestor) then
	ls_date = iem_requestor.Text
else
	return -1
end if

// Bring focus on the calendar.
setFocus(this)
setFocus(dw_cal)

// Validate the date (compare to an invalid date).
// Keep track if the date was valid or not.
//ls_date = lnv_util.of_parseDateTime(ls_date, 1)  // Extract just the date part
id_resetdate = date(ls_date)
if id_resetdate <> date('1900-01-01') and not isNull(id_resetdate) then
	// Set the new date.  Do not Update the requestor.
	this.of_setDate(id_resetdate, false)
else
	// Set the new date.  The requestor is/is_not updated according to the 
	// attribute.
	this.of_setDate(today(), ib_initialvalue)
end if

return 1
end function

event constructor;call super::constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			Constructor
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Intialize values for the Calendar object.
//
// Usage:  -- Open Calendar User OBject
//                  u_calendar  iuo_calendar
//                  openUserObject(iuo_calendar)
//
//         -- Associate the datawindow or Edit mask with the calendar object
//                  iuo_calendar.of_setRequestor(dw_app)
//
//         -- Register datawindow columns that will use the calendar object if
//            applicable
//                  iuo_calendar.of_register("rcvd_date")
//                  iuo_calendar.of_register("status_date")
//
//////////////////////////////////////////////////////////////////////////////

this.event ue_hide()

end event

on u_rmt_calendar.create
int iCurrent
call super::create
this.cb_close=create cb_close
this.dw_cal=create dw_cal
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.cb_close
this.Control[iCurrent+2]=this.dw_cal
end on

on u_rmt_calendar.destroy
call super::destroy
destroy(this.cb_close)
destroy(this.dw_cal)
end on

event destructor;call super::destructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			Destructor
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Destroy the instantiated services attached.
//
//////////////////////////////////////////////////////////////////////////////


// Unregister all columns.
of_Unregister()

of_SetDropDown(False)
end event

type cb_close from commandbutton within u_rmt_calendar
integer x = 219
integer y = 616
integer width = 247
integer height = 72
integer taborder = 20
boolean bringtotop = true
integer textsize = -10
integer weight = 400
fontcharset fontcharset = ansi!
fontpitch fontpitch = variable!
fontfamily fontfamily = swiss!
string facename = "Arial"
string text = "Close"
end type

event clicked;parent.event ue_hide()
end event

type dw_cal from u_rmt_dw within u_rmt_calendar
event type integer ue_nextmonth ( )
event type integer ue_prevmonth ( )
event type integer ue_nextday ( )
event type integer ue_prevday ( )
event type integer ue_prevweek ( )
event type integer ue_nextweek ( )
event key pbm_dwnkey
integer width = 695
integer height = 704
string dataobject = "d_calendar"
boolean vscrollbar = false
boolean livescroll = false
borderstyle borderstyle = styleraised!
end type

event ue_nextmonth;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			ue_nextmonth
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Goto to the next month.
//
//////////////////////////////////////////////////////////////////////////////

integer li_month
integer li_year
integer li_day

//Initialize local values.
li_year = Year(id_date)
li_month = Month(id_date)
li_day = Day(id_date)

//Increment the month number.
li_month ++
if li_month = 13 then
	li_month = 1
	li_year = li_year + 1
end if

//Validate day number for the new month.
if not(isdate(string(li_month) + "/" + string(li_day) + "/"+ string(li_year))) then 
	li_day = 1
end if

//Set the new date.
of_SetDate (date(li_year, li_month, li_Day), true)

return 1
end event

event ue_prevmonth;Integer li_month
Integer li_year
Integer li_day

//Initialize local values.
li_year = Year(id_date)
li_month = Month(id_date)
li_day = Day(id_date)

//Decrement the month.
li_month --
if li_month = 0 then
	li_month = 12
	li_year = li_year - 1
end if

//Validate day number for the new month.
if not(IsDate(string(li_month) + "/" + string(li_day) + "/"+ string(li_year))) then
	li_day = 1
end if

//Set the new date.
parent.of_setDate(date(li_year, li_month, li_Day), true)

return 1
end event

event ue_nextday;call super::ue_nextday;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			ue_nexday
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Goto to the following day.
//
//////////////////////////////////////////////////////////////////////////////

// Set the new date.
of_SetDate (RelativeDate (id_date, 1 ), True)

Return 1
end event

event ue_prevday;call super::ue_prevday;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			ue_prevday
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Goto the previous day.
//
//////////////////////////////////////////////////////////////////////////////

// Set the new date.
of_SetDate (RelativeDate (id_date, -1), True)

Return 1
end event

event ue_prevweek;call super::ue_prevweek;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			ue_prevweek
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	Goto to the previous week.
//
//////////////////////////////////////////////////////////////////////////////

// Set the new date.
of_SetDate (RelativeDate (id_date, -7), True)

Return 1
end event

event ue_nextweek;call super::ue_nextweek;// Set the new date.
of_SetDate (RelativeDate (id_date, 7), True)

Return 1
end event

event key;call super::key;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			key
//
//	(Arguments:		
//	key
//	keyflags
//
//	(Returns:  		None)
//
//	Description:
//	Support keyboard to be used to change the date on the calendar.
//
//////////////////////////////////////////////////////////////////////////////

Choose Case key
	Case KeyEscape! 
		//If appropriate, hide the Calendar.
//		If IsValid(inv_dropdown) Then
//			If ib_closeonclick or ib_closeondclick Then
//				// Set focus on the Requestor object which in turns hides the calendar.
//				of_SetFocusOnRequestor()
//			End If
//		End If
	Case KeyTab!
//		If IsValid(inv_dropdown) Then
//			Post of_SetFocusOnRequestor()
//		End If
	Case KeyEnter!
//		If Not inv_datetime.of_IsValid(id_resetdate) Then
//			of_SetDate(id_date, True)
//		End If
		//If appropriate, hide the Calendar.
//		If IsValid(inv_dropdown) Then
//			If ib_closeonclick or ib_closeondclick Then
//				// Set focus on the Requestor object which in turns hides the calendar.
//				of_SetFocusOnRequestor()
//			End If
//		End If
	Case KeyLeftArrow!
		this.Event ue_PrevDay()	
	Case KeyUpArrow!
		this.Event ue_PrevWeek()		
	Case KeyPageUp!
		this.Event ue_PrevMonth()	
	Case KeyRightArrow!
		this.Event ue_NextDay()	
	Case KeyDownArrow!
		this.Event ue_NextWeek()		
	Case KeyPageDown!
		this.Event ue_NextMonth()
End Choose
end event

event clicked;call super::clicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			clicked
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	
//
//////////////////////////////////////////////////////////////////////////////

Integer 	li_month
Integer 	li_year
Integer 	li_day
String 	ls_clickedcolumn, ls_clickedcolumnID
String 	ls_day, ls_return

//Return if click was not on a valid dwobject, depending on what was
//clicked, dwo will be null or dwo.name will be "datawindow"
If IsNull(dwo) Then Return
If Pos(dwo.name, "cell") = 0 Then Return

//Initialize local values.
li_year = Year(id_date)
li_month = Month(id_date)
li_day = Day(id_date)

//Find which column was clicked on and return if it is not valid
ls_clickedcolumn = dwo.name
ls_clickedcolumnID = dwo.id
If ls_clickedcolumn = '' Then Return

//Set Day to the text of the clicked column. Return if it is an empty column
ls_day = dwo.primary[1]
If ls_day = "" then Return

//Convert to a number.
li_day = Integer(ls_day)

//Set the new date. 
of_SetDate (date(li_year, li_month, li_Day), True)

//If appropriate, hide the Calendar.
//If IsValid(inv_dropdown) Then
//	If ib_closeonclick Then
//		// Set focus on the Requestor object which in turns hides the calendar.
//		of_SetFocusOnRequestor()
//	End If
//End If

end event

event doubleclicked;call super::doubleclicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			doubleclicked
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	
//
//////////////////////////////////////////////////////////////////////////////

//Return if click was not on a valid dwobject, depending on what was
//clicked, dwo will be null or dwo.name will be "datawindow"
If IsNull(dwo) Then Return
If Pos(dwo.name, "cell") = 0 Then Return

//If appropriate, hide the Calendar.
//If IsValid(inv_dropdown) Then
//	If ib_closeondclick Then
//		// Set focus on the Requestor object which in turns hides the calendar.
//		of_SetFocusOnRequestor()
//	End If
//End If
end event

event losefocus;call super::losefocus;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			losefocus
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	
//	If this object is being used as a DropDown object, hide it when focus
// is lost.
//
//////////////////////////////////////////////////////////////////////////////


//Determine if the object is being used as a dropdown object.
//If IsValid(inv_dropdown) Then
//	// Hide object.
//	Parent.Visible = False
//End If

Return
end event

event getfocus;call super::getfocus;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			getfocus
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	The object may need to redirect focus when not visible.
//
//////////////////////////////////////////////////////////////////////////////

// Prevent this object from having focus while not visible.
Post of_RedirectFocus() 

end event

event constructor;call super::constructor;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			Constructor
//
//	(Arguments:		None)
//
//	(Returns:  		None)
//
//	Description:	
//
//////////////////////////////////////////////////////////////////////////////

// Prevent Updates from this datawindow.
of_SetUpdateable (False)

// Prevent Right Mouse Menu.
//ib_rmbmenu = False
end event

event buttonclicked;//////////////////////////////////////////////////////////////////////////////
//
//	Event:  			buttonclicked
//
//	Arguments:		
//	row
//	actionreturncode
//	dwo
//
//	Returns:  		None
//
//	Description:	Perform the Action desired.
//
//////////////////////////////////////////////////////////////////////////////

string ls_buttonname

if IsNull(dwo) then 
	return
end if

ls_buttonname = dwo.Name

choose case ls_buttonname
	case 'prevmonth'
		// Request the previous month.
		this.event ue_PrevMonth()
		
	case 'nextmonth'
		// Request the next month.
		this.event ue_NextMonth()
end choose

this.SetFocus()
end event

