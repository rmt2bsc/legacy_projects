$PBExportHeader$n_rmt_baseobject.sru
$PBExportComments$Base Object for Non-Visuals
forward
global type n_rmt_baseobject from nonvisualobject
end type
end forward

global type n_rmt_baseobject from nonvisualobject
end type
global n_rmt_baseobject n_rmt_baseobject

type variables
public:
  // - Common return value constants:
  constant integer 		SUCCESS = 1
  constant integer 		FAILURE = -1
  constant integer 		NO_ACTION = 0
  // - Continue/Prevent return value constants:
  constant integer 		CONTINUE_ACTION = 1
  constant integer 		PREVENT_ACTION = 0
  //constant integer 		FAILURE = -1

w_rmt_ancestor     iw_parent

protected:
  string                    is_sqlfile
  string                    is_sql



end variables

forward prototypes
public subroutine of_setsqlfile (string as_filename)
public function integer of_loadquery ()
public function string of_getsqlfile ()
public function string of_getsql ()
public function integer of_messagebox (string as_id, string as_title, string as_text, icon ae_icon, button ae_button, integer ai_default)
end prototypes

public subroutine of_setsqlfile (string as_filename);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetSQLFile
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  Sets the value of is_sqlfile
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

is_sqlfile = as_filename



end subroutine

public function integer of_loadquery ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_loadquery
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		integer  
//                -1 = an error occurred when attempting to read the sql file
//                -2 = the filename is invalid
//                -3 = the path and filename of the sql file is invalid
//                -100 = an end-of-file mark (EOF) is encountered before any
//                       characters are read from the file
//                 null = an argument of "fileread" function was null
//                 0 = if the file is opened in LineMode and a CR or LF is 
//                     encountered before any characters are read.
//                 > 0 = the sql file was read successfully.  The total amount
//                       of bytes that was read.
//
//						
//						
//	Description:  	Loads the SQL Query that is to be used for u_rmt_dw or n_rmt_ds
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int        li_file
int        li_result
string     ls_sqlfile
string     ls_sql


// Get SQL Path and FileName
if isnull(is_sqlfile) or len(is_sqlfile) <= 0 then
	return -2
end if

ls_sqlfile = gnv_app.of_getsqldir() + is_sqlfile
if isnull(ls_sqlfile) or len(ls_sqlfile) <= 0 then
	return -3
end if

//  Load SQL Statement
li_file = fileopen(ls_sqlfile, streammode!)
li_result = fileread(li_file, ls_sql)
if li_result <= 0 then
	return li_result
end if
is_sql = trim(ls_sql)
fileclose(li_file)

return li_result
end function

public function string of_getsqlfile ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetSQLFile
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  Gets the value of is_sqlfile
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_sqlfile
end function

public function string of_getsql ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetSQL
//
//	Access:  		public
//
//	Arguments:     none
//
//	Returns:  		String
//						
//						
//	Description:  Returns the value of is_sql
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return is_sql
end function

public function integer of_messagebox (string as_id, string as_title, string as_text, icon ae_icon, button ae_button, integer ai_default);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  			of_MessageBox
//
//	Access:  			protected
//
//	Arguments:
//	as_id			An ID for the Message.
//	as_title  	Text for title bar
//	as_text		Text for the actual message.
//	ae_icon 		The icon you want to display on the MessageBox.
//	ae_button	Set of CommandButtons you want to display on the MessageBox.
//	ai_default  The default button.
//
//	Returns:  integer
//	Return value of the MessageBox.
//
//	Description:
//	Display a PowerScript MessageBox.  
//	Allow RMT MessageBoxes to be manipulated prior to their actual display.
//
//////////////////////////////////////////////////////////////////////////////

Return MessageBox(as_title, as_text, ae_icon, ae_button, ai_default)
end function

on n_rmt_baseobject.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_baseobject.destroy
TriggerEvent( this, "destructor" )
end on

