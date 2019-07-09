$PBExportHeader$n_rmt_messaging.sru
$PBExportComments$Messaging Object
forward
global type n_rmt_messaging from n_rmt_baseobject
end type
end forward

global type n_rmt_messaging from n_rmt_baseobject
end type
global n_rmt_messaging n_rmt_messaging

type variables
Public:
// Return info  from RMT Message Window.
integer		ii_rc=-99
integer		ii_buttonclicked

// Standard PB MessageBox information.
string		is_title
string		is_text
icon		ie_icon
button		ie_buttonstyle
integer		ii_default

// Other attributes for Message Window.
integer		ii_severity
boolean		ib_print
boolean		ib_userinput
string		is_usertext
integer		ii_timeout

// Other attributes for service.
datetime		idt_date
string		is_user


//  Constant message ID's
public:
   constant  int  CLOSEQUERY = 1
   constant  int  SYSTEMERROR = 2

end variables

forward prototypes
public function integer of_message (integer ai_msgid)
public function integer of_message (int ai_msgid, string as_msgparms[])
public function integer of_message (integer ai_msgid, string as_msgparms[], icon a_icon)
public function integer of_message (integer ai_msgid, string ai_msgparms[], icon a_icon, button a_buttonstyle)
public function integer of_setdatacache (boolean ab_switch, string as_sql)
public function integer of_message (string as_message)
public function integer of_message (string as_message, icon a_icon)
public function integer of_message (string as_message, icon a_icon, button a_buttonstyle)
end prototypes

public function integer of_message (integer ai_msgid);


return 1
end function

public function integer of_message (int ai_msgid, string as_msgparms[]);

return 1
end function

public function integer of_message (integer ai_msgid, string as_msgparms[], icon a_icon);


return 1
end function

public function integer of_message (integer ai_msgid, string ai_msgparms[], icon a_icon, button a_buttonstyle);

return 1
end function

public function integer of_setdatacache (boolean ab_switch, string as_sql);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetDatacache
//
//	Access:  		public
//
//	Arguments:
//	ab_switch		True - add messaging data to the data cache of the application
//                       manager object
//						False - set messaging data cache indicator to false
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//						
//	Description:  	Starts or stops the Error Handling Service.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
// 
//
//////////////////////////////////////////////////////////////////////////////


//Check arguments
If IsNull(ab_switch) Then
	Return -1
End If

IF ab_Switch THEN
	IF IsNull(gnv_app.inv_cache) Or Not IsValid (gnv_app.inv_cache) THEN
		Return -1
	END IF
ELSE
	IF IsValid (gnv_app.inv_cache) THEN
		return gnv_app.inv_cache.of_register("messaging", as_sql, "DataObject")
	END IF	
END IF

Return 0
end function

public function integer of_message (string as_message);/////////////////////////////////////////////
//  Display, as_message, in the form of 
//  a message box with just the OK button.
/////////////////////////////////////////////

return messagebox(gnv_app.iapp_object.displayname, as_message)
end function

public function integer of_message (string as_message, icon a_icon);/////////////////////////////////////////////
//  Display, as_message, in the form of 
//  a message box with just the OK button 
//  and a specified icon.
/////////////////////////////////////////////

return messagebox(gnv_app.iapp_object.displayname, as_message, a_icon)
end function

public function integer of_message (string as_message, icon a_icon, button a_buttonstyle);/////////////////////////////////////////////
//  Display, as_message, in the form of 
//  a message box with just the OK button 
//  and a specified icon.
/////////////////////////////////////////////

return messagebox(gnv_app.iapp_object.displayname, as_message, a_icon, a_buttonstyle)


return 1
end function

on n_rmt_messaging.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_messaging.destroy
TriggerEvent( this, "destructor" )
end on

