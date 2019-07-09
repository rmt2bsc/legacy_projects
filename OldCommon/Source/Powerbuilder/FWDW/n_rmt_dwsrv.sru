$PBExportHeader$n_rmt_dwsrv.sru
forward
global type n_rmt_dwsrv from n_rmt_baseobject
end type
end forward

global type n_rmt_dwsrv from n_rmt_baseobject
end type
global n_rmt_dwsrv n_rmt_dwsrv

type variables
Public:
// Column Display Name Style settings
// Note: The constant DEFAULT=0 is used in descendants.
constant integer	DEFAULT = 0
constant integer	DBNAME =1
constant integer	HEADER = 2

Protected:
integer	ii_source = DEFAULT
string	is_defaultheadersuffix = "_t"
string	is_displayunits = "rows"
string	is_displayitem = "this row"
u_rmt_dw	  idw_requestor
str_required_columns     istr_reqcols[]

end variables

forward prototypes
public function integer of_getobjects (ref string as_objlist[], string as_objtype, string as_band, boolean ab_visibleonly)
public function string of_getheadername (string as_column)
public function integer of_getobjects (ref string as_objlist[])
public function string of_getheadername (string as_column, string as_suffix)
public function integer of_refreshdddws ()
public function long of_getheight ()
public function long of_getwidth ()
public function integer of_setdefaultheadersuffix (string as_suffix)
public function integer of_setrequestor (u_rmt_dw adw_requestor)
public function integer of_populatedddw (string as_dddwname)
public function integer of_populatedddw (integer ai_dddwnumber)
public function integer of_populatedddw ()
public function integer of_setrequiredcolumn (string as_col, string as_message)
protected function boolean of_isvalidcolumn (string as_colname)
public function boolean of_checkrequiredcolumns (ref string as_message)
public function integer of_protectdwcolumns (datawindow adw_obj, boolean ab_updatemode)
end prototypes

public function integer of_getobjects (ref string as_objlist[], string as_objtype, string as_band, boolean ab_visibleonly);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetObjects (FORMAT 2)
//
//	Access:    		Public
//
//	Arguments:
//   as_objlist[]:	A string array to hold objects (passed by reference)
//   as_objtype:  	The type of objects to get (* for all, others defined
//							by the object .TYPE attribute)
//   as_band:  		The dw band to get objects from (* for all) 
//							Valid bands: header, detail, footer, summary
//							header.#, trailer.#
//   ab_visibleonly: TRUE  - get only the visible objects,
//							 FALSE - get visible and non-visible objects
//
//	Returns:  		Integer
//   					The number of objects in the array
//
//	Description:	The following function will parse the list of objects 
//						contained in the datawindow control associated with this service,
//						returning their names into a string array passed by reference, 
//						and returning the number of names in the array as the return value 
//						of the function.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
string	ls_ObjString, ls_ObjHolder
integer	li_Start=1, li_Tab, li_Count=0

/* Get the Object String */
ls_ObjString = idw_Requestor.Describe("Datawindow.Objects")

/* Get the first tab position. */
li_Tab =  Pos(ls_ObjString, "~t", li_Start)
Do While li_Tab > 0
	ls_ObjHolder = Mid(ls_ObjString, li_Start, (li_Tab - li_Start))

	// Determine if object is the right type and in the right band
	If (idw_Requestor.Describe(ls_ObjHolder + ".type") = as_ObjType Or as_ObjType = "*") And &
		(idw_Requestor.Describe(ls_ObjHolder + ".band") = as_Band Or as_Band = "*") And &
		(idw_Requestor.Describe(ls_ObjHolder + ".visible") = "1" Or Not ab_VisibleOnly) Then
			li_Count ++
			as_ObjList[li_Count] = ls_ObjHolder
	End if

	/* Get the next tab position. */
	li_Start = li_Tab + 1
	li_Tab =  Pos(ls_ObjString, "~t", li_Start)
Loop 

// Check the last object
ls_ObjHolder = Mid(ls_ObjString, li_Start, Len(ls_ObjString))

// Determine if object is the right type and in the right band
If (idw_Requestor.Describe(ls_ObjHolder + ".type") = as_ObjType or as_ObjType = "*") And &
	(idw_Requestor.Describe(ls_ObjHolder + ".band") = as_Band or as_Band = "*") And &
	(idw_Requestor.Describe(ls_ObjHolder + ".visible") = "1" Or Not ab_VisibleOnly) Then
		li_Count ++
		as_ObjList[li_Count] = ls_ObjHolder
End if

Return li_Count
end function

public function string of_getheadername (string as_column);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetHeaderName (FORMAT 1) 
//
//	Access:    		Public
//
//	Arguments:
//   as_column   	A datawindow columnname
//
//	Returns:  		String
//   					The formatted column header for the column specified
//
//	Description:  	Extracts a formatted (underscores, carraige return/line
//					  	feeds and quotes removed) column header.
//					  	If no column header found, then the column name is
//					  	formatted (no underscores and Word Capped).
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0    Initial version
//	5.0.02   Fixed function to use the default header suffix property
//		when determining which text object to use for the the column header.
//
//////////////////////////////////////////////////////////////////////////////

Return of_GetHeaderName ( as_column, is_defaultheadersuffix) 
end function

public function integer of_getobjects (ref string as_objlist[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetObjects (FORMAT 1)
//
//	Access:    		Public
//
//	Arguments:
//   as_objlist[]:	A string array to hold objects (passed by reference)
//
//	Returns:  		Integer
//   					The number of objects in the array
//
//	Description:	The following function will parse the list of objects 
//						contained in the datawindow control associated with this service,
//						returning their names into a string array passed by reference, 
//						and returning the number of names in the array as the return value 
//						of the function.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

Return of_GetObjects ( as_objlist, "*", "*", FALSE ) 

end function

public function string of_getheadername (string as_column, string as_suffix);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetHeaderName (FORMAT 2) 
//
//	Access:    Public
//
//	Arguments:
//   as_column   A datawindow columnname
//	  as_suffix   The suffix used on column header text
//
//	Returns:  String
//	  The formatted column header for the column specified
//
//	Description:  Extracts a formatted (underscores, carriage return/line
//					  feeds and quotes removed) column header.
//					  If no column header found, then the column name is
//					  formatted (no underscores and Word Capped).
//
//  *NOTE: Use this format when column header text does NOT
//	  use the default header suffix
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0    Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
string ls_colhead
n_rmt_string	lnv_string

//Try using the column header.
ls_colhead = idw_Requestor.Describe ( as_column + as_suffix + ".Text" )
If ls_colhead = "!" Then
	//No valid column header, use column name.
	ls_colhead = as_column
End If	

//Remove undesired characters.
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~r~n", " " ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~t", " " ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~r", " " ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~n", " " ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "_", " " ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~"", "" ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~'", "" ) 
ls_colhead = lnv_string.of_GlobalReplace ( ls_colhead, "~~", "" )

//WordCap string.
ls_colhead = idw_Requestor.Describe ( "Evaluate('WordCap(~"" + ls_colhead + "~")',0)" )

Return ls_colhead
end function

public function integer of_refreshdddws ();////////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_RefreshDDDWs
//
//	Access:    Public
//
//	Arguments:  None
//
//	Returns:   Integer
//	  The number of dddw-style columns found and refreshed.
//		-1 if an error occurs.
//
//	Description:  To determine what columns have a DropDownDataWindow style 
//					  and to refresh the dddw. 
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0	Initial version
//	5.0.02 Handle cases where the column having a child datawindow does not 
//			equal the dropdowndatawindow column name. 
//	5.0.02 Check for required references and added error checking.
// 6.0	Marked obsolete Replaced by of_PopulateDDDWs(...).
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

Long		ll_rc
Long 		ll_cnt
Long		ll_columncount
Long		ll_dddwcount
String 	ls_colname
String	ls_dddwdatacolumn
String 	ls_args[]
String	ls_types[]
boolean	lb_dddwrefreshed=False
DataWindowChild ldwc_obj
//
//// Check required references.
//If IsNull(idw_Requestor) or Not IsValid(idw_Requestor) Then Return -1
//
//// Get the number of columns on the datawindow.
//ll_columncount = Long (idw_Requestor.Describe("DataWindow.Column.Count")) 
//
//// Loop around all columns.
//FOR ll_cnt=1 TO ll_columncount
//	// Reset boolean which states if dddw is refreshed.
//	lb_dddwrefreshed=False
//	
//	// Get the current column name.
//	ls_colname = idw_Requestor.Describe ( "#" + String ( ll_cnt ) + ".Name" )
//	// Determine if the current column is a DropDownDataWindow.
//	ls_dddwdatacolumn = idw_Requestor.Describe ( ls_colname + ".DDDW.DataColumn" )
//	IF ls_dddwdatacolumn = "" OR ls_dddwdatacolumn = "?" THEN
//		// Not a DropDownDataWindow.
//		CONTINUE
//	ELSE
//		// Get the Child reference.
//		ll_rc = idw_Requestor.GetChild (ls_colname, ldwc_obj) 
//		If ll_rc > 0 Then
//			// A DropDownDataWindow has been found.			
//			IF of_DWArguments ( ldwc_obj, ls_args, ls_types ) > 0 THEN 
//				// DropDownDataWindow has arguments, call event which will handle this case.
//				ll_rc = idw_Requestor.Event pfc_retrievedddw(ls_colname)
//				If ll_rc < 0 Then Return -1
//				lb_dddwrefreshed = True
//			ELSE 
//				// DropDownDataWindow does not have arguments, refresh the data.
//				If IsValid(idw_Requestor.itr_object) Then
//					ll_rc = ldwc_obj.SetTransObject(idw_Requestor.itr_object) 
//					If ll_rc < 0 Then Return -1					
//					ll_rc = ldwc_obj.Retrieve() 
//					If ll_rc < 0 Then Return -1
//					lb_dddwrefreshed = True				
//				End If
//			END IF
//			If lb_dddwrefreshed Then
//				// Increment the DropDownDataWindow count.
//				ll_dddwcount++			
//			End If
//		End If
//	END IF 
//NEXT 
 
Return ll_dddwcount
end function

public function long of_getheight ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_GetHeight
//
//	Access:    		Public
//
//	Arguments: 		None
//
//	Returns:  		long 
//   					The height of the datawindow
//
//	Description:  	Get the height of the datawindow associated with this service.
//					  	The	height is calculated by adding the height of all bands +
//					  	the height of the detail band * the number of rows.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
Integer	li_Bands, li_Cnt
long		ll_height
long		ll_detail
String	ls_DWBands, ls_Band[]
n_rmt_string lnv_string

ls_DWBands = idw_Requestor.Describe("DataWindow.Bands")

li_Bands = lnv_string.of_ParseToArray (ls_DWBands, "~t", ls_Band)

For li_Cnt = 1 To li_Bands
	If ls_Band[li_Cnt] <> "detail" Then
		ll_Height += Integer(idw_Requestor.Describe("Datawindow." + &
							ls_Band[li_Cnt] + ".Height"))
	End if
Next

ll_Detail = idw_Requestor.RowCount() * &
			Integer(idw_Requestor.Describe("Datawindow.Detail.Height"))

ll_Height += ll_Detail

Return ll_Height
end function

public function long of_getwidth ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_GetWidth
//
//	Access:    Public
//
//	Arguments: None
//
//	Returns:   long
//   The width of the datawindow
//
//	Description:  Get the width (x position + width of the rightmost object) of the 
//				     datawindow associated with this service
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////
long	ll_Width
long	ll_Return
integer	li_NumObjects
integer	li_Count
long	ll_X
long	ll_ObjWidth
string	ls_Objects[]

// Get the names of all visible objects in the datawindow
li_NumObjects = of_GetObjects(ls_Objects, "*", "*", True)

ll_Return = 0

For li_Count = 1 To li_NumObjects
	// Calculate the x position + the width of each object
	ll_X = Integer(idw_Requestor.Describe(ls_Objects[li_Count] + ".x"))
	ll_ObjWidth = Integer(idw_Requestor.Describe(ls_Objects[li_Count] + ".width"))
	ll_Width = ll_X + ll_ObjWidth

	// Return the rightmost value
	If ll_Width > ll_Return Then
		ll_Return = ll_Width
	End if
Next

Return ll_Return
end function

public function integer of_setdefaultheadersuffix (string as_suffix);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetDefaultHeaderSuffix
//
//	Access:  public
//
//	Arguments:  as_suffix
//
//	Returns:  integer
//	1 = success
//
//	Description:  Sets the suffix characters that are used for
//	column labels/headers
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
//
//	Copyright © 1996-1997 Sybase, Inc. and its subsidiaries.  All rights reserved.
//	Any distribution of the PowerBuilder Foundation Classes (PFC)
//	source code by other than Sybase, Inc. and its subsidiaries is prohibited.
//
//////////////////////////////////////////////////////////////////////////////

is_defaultheadersuffix = as_suffix
return 1
end function

public function integer of_setrequestor (u_rmt_dw adw_requestor);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  of_SetRequestor
//
//	Access:    Public
//
//	Arguments:
//   adw_Requestor   The datawindow requesting the service
//
//	Returns:  None
//
//	Description:  Associates a datawindow control with a datawindow service NVO
//			        by setting the idw_Requestor instance variable.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
// 6.0	Added function return code.
//
//////////////////////////////////////////////////////////////////////////////


If IsNull(adw_requestor) or Not IsValid(adw_requestor) Then
	Return -1
End If

idw_Requestor = adw_Requestor
Return 1
end function

public function integer of_populatedddw (string as_dddwname);//////////////////////////////////////////////////////////////////////////////
//
//	Function:
//	of_PopulateDDDW
//
//	Access:
//	Public
//
//	Arguments:
//	as_dddwname   The column name of the DDDW
//
//	Returns:
//	 1 = success
//	-1 = error
//
//	Description:
//	Populates specified DDDW
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

integer	li_rc = 1
Long		ll_rc
String	ls_dddwdatacolumn
DataWindowChild ldwc_obj

// Check required references.
If IsNull(idw_Requestor) or Not IsValid(idw_Requestor) Then Return -1

// Determine if the current column is a DropDownDataWindow.
ls_dddwdatacolumn = idw_Requestor.Describe ( as_dddwname + ".DDDW.DataColumn" )
IF ls_dddwdatacolumn = "" OR ls_dddwdatacolumn = "?" or ls_dddwdatacolumn = "!" THEN
	// Not a DropDownDataWindow.
	li_rc = -1
ELSE
	// Get the Child reference.
	ll_rc = idw_Requestor.GetChild (as_dddwname, ldwc_obj)
	If ll_rc > 0 Then
//		ll_rc = idw_requestor.event ue_RetrieveDDDW(as_dddwname, ldwc_obj)
		if ll_rc < 0 then return -1
	End If
END IF 

Return li_rc
end function

public function integer of_populatedddw (integer ai_dddwnumber);//////////////////////////////////////////////////////////////////////////////
//
//	Function:
//	of_PopulateDDDW
//
//	Access:
//	Public
//
//	Arguments:
//	ai_dddwnumber   Column number of the DDDW
//
//	Returns:
//	 1 = success
//	-1 = error
//
//	Description:
//	Populates specified DDDW
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

string	ls_dddwname

// Validate requestor DW
if isNull (idw_requestor) or not isValid (idw_requestor) then return -1

// Validate DW column name
ls_dddwname = idw_requestor.describe ("#" + string (ai_dddwnumber) + ".name")
if ls_dddwname = "?" or ls_dddwname = "!" then return -1

return of_populateDDDW (ls_dddwname)
end function

public function integer of_populatedddw ();//////////////////////////////////////////////////////////////////////////////
//
//	Function:
//	of_PopulateDDDW
//
//	Access:
//	Public
//
//	Arguments:
//	None
//
//	Returns:
//	integer
//	The number of dddw-style columns populated
//	-1 if an error occurs.
//
//	Description:
//	Populates all DDDWs on the DataWindow
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	6.0   Initial version - Replaces obsoleted function of_RefreshDDDWs(...)
//
//////////////////////////////////////////////////////////////////////////////

Long		ll_rc
Long 		ll_cnt
Long		ll_columncount
Long		ll_dddwcount
String 	ls_colname
String	ls_dddwdatacolumn
DataWindowChild ldwc_obj

// Check required references.
if IsNull(idw_Requestor) or not IsValid(idw_Requestor) then 
	return -1
end if

// Get the number of columns on the datawindow.
ll_columncount = Long (idw_Requestor.Describe("DataWindow.Column.Count")) 

// Loop around all columns.
for ll_cnt=1 to ll_columncount
	  	// Get the current column name.
	ls_colname = idw_Requestor.Describe ( "#" + String ( ll_cnt ) + ".Name" )
	   // Determine if the current column is a DropDownDataWindow.
	ls_dddwdatacolumn = idw_Requestor.Describe ( ls_colname + ".DDDW.DataColumn" )
	if ls_dddwdatacolumn = "" or ls_dddwdatacolumn = "?" or ls_dddwdatacolumn = "!" then
		   // Not a DropDownDataWindow.
		continue
	else
		   // Get the Child reference.
		ll_rc = idw_Requestor.GetChild (ls_colname, ldwc_obj) 
		if ll_rc > 0 then
			ll_rc = idw_requestor.event ue_RetrieveAllDDDW(ls_colname, ldwc_obj)
			if ll_rc < 0 then return -1
			
			// Increment the DropDownDataWindow count.
			ll_dddwcount++			
		end if
	end if
next 
 
return ll_dddwcount
end function

public function integer of_setrequiredcolumn (string as_col, string as_message);//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_SetRequiredColumn
//
//	Access:    		Public
//
//	Arguments:
//   as_col   	   A datawindow columnname
//   as_message   Message that will be displayed when a required column is found to be blank or null
//
//	Returns:  		integer
//    				 1  = success
//                -1 = general failure
//                -2 = Input column name (as_col) is invalid and/or does not exist
//                     in the current datawindow object.
//
//	Description:  	Set up a group of columns that are identified as required for 
//                
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	1.0    Initial version
//
//////////////////////////////////////////////////////////////////////////////

int  li_totalcolumns
int  li_result
int  li_ndx

// Check required references.
if IsNull(idw_Requestor) or not IsValid(idw_Requestor) then 
	return -1
end if

if not this.of_isValidColumn(as_col) then
	return -2
end if

li_totalcolumns = upperBound(istr_reqcols)
if isnull(li_totalcolumns) or li_totalcolumns = -1 then
	return -1
end if

li_ndx = li_totalcolumns + 1
istr_reqcols[li_ndx].col = as_col
istr_reqcols[li_ndx].message = as_message

return 1
end function

protected function boolean of_isvalidcolumn (string as_colname);//////////////////////////////////////////////////////////////////////////////
//
//	Function:	of_isValidColumn
//
//	Access:	Protected
//
//	Arguments:	String as_colname  -  Name of column to validate
//
//	Returns:	boolean - true = valid column
//                    false = invalid column
//
//	Description:  Determines if as_colname exist in current datawindow object.
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////
int   li_totalcolumns
int   li_ndx
string ls_name


// Check required references.
if IsNull(idw_Requestor) or not IsValid(idw_Requestor) then 
	return false
end if

if as_colname = "" or isNull(as_colname) then
	return false
end if

li_totalcolumns = integer(idw_Requestor.object.datawindow.column.count)
for li_ndx = 1 to li_totalcolumns
	ls_name = idw_requestor.describe("#" + string(li_ndx) + ".name")
	if ls_name = "" or ls_name = "?" or ls_name = "!" then
		   // Not a valid column name.
		continue
	end if
	if ls_name = as_colname then
		  // column name exist
		return true
	end if
next

return false
end function

public function boolean of_checkrequiredcolumns (ref string as_message);
//////////////////////////////////////////////////////////////////////////////
//
//	Function:  		of_checkRequiredColumns
//
//	Access:    		Public
//
//	Arguments:     String  as_message (ref)  Column's error message returned to caller.
//
//	Returns:  		boolean
//    				 true = required check was satisfied
//                 false = required check failed
//
//	Description:  	Check to see if all required columns have values
//                
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	1.0    Initial version
//
//////////////////////////////////////////////////////////////////////////////
int    li_rowcount
int    li_colcount
int    li_reqcolcount
int    li_row
int    li_col
int    li_reqcol
string ls_type
string ls_value
string ls_dwcolname


  // Check required references.
if IsNull(idw_Requestor) or not IsValid(idw_Requestor) then 
	return false
end if

li_rowcount = idw_requestor.rowcount()
li_colcount = integer(idw_requestor.object.datawindow.column.count)
li_reqcolcount = upperbound(istr_reqcols)

   // Process each and every column in the datawindow
for li_row = 1 to li_rowcount
	
	  // Evaluate each column for the current row of the datawindow
   for li_col = 1 to li_colcount
		ls_dwcolname = idw_requestor.describe("#" + string(li_col) + ".name")

		   // Determine if the current datawindow column is required
		for li_reqcol = 1 to li_reqcolcount
		   if lower(istr_reqcols[li_reqcol].col) <> lower(ls_dwcolname) then
			   continue
		   end if
		 
	      ls_type = idw_requestor.describe(istr_reqcols[li_reqcol].col + ".coltype")
   	   choose case mid(ls_type, 1, 3)
	   	  case "cha"
		   	  ls_value = idw_requestor.getitemstring(li_row, istr_reqcols[li_reqcol].col)
   		  case "dat"
 			   	if ls_type = "datetime" then
	  			      ls_value = string(idw_requestor.getitemdatetime(li_row, istr_reqcols[li_reqcol].col))
				   else
				      ls_value = string(idw_requestor.getitemdate(li_row, istr_reqcols[li_reqcol].col))
				   end if
			  case "tim"
				   ls_value = string(idw_requestor.getitemtime(li_row, istr_reqcols[li_reqcol].col))
	        case "dec", "int", "lon", "num"
				   ls_value = string(idw_requestor.getitemnumber(li_row, istr_reqcols[li_reqcol].col))
		   end choose
		
		   // Is the required column invalid?
		   if isNull(ls_value) or ls_value = space(len(ls_value)) then
			   as_message = istr_reqcols[li_reqcol].message
			   return false
		   end if
		next
	next
next

return true
end function

public function integer of_protectdwcolumns (datawindow adw_obj, boolean ab_updatemode);//////////////////////////////////////////////////////////////////////////////
//
//	Method:  of_protectDWColumns
//
//	Arguments:  datawindow adw_obj
//             boolean  ab_updatemode
//
//	Returns:  integer
//
//	Description: Changes the color and protect properties of each control of 
//              the selected datawindow object base on ab_updatemode.   If
//              ab_updatemode is true, then background color of columns are 
//              changed to white and will appear as unprotected.  Otherwise,
//              the background is changed to buttonface and will appear as 
//              protected.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////



int              li_colcount
int              li_col
string           ls_result
long             ll_color
int              li_protect
string           ls_tabseq
string           ls_name
string           ls_style


if adw_obj.rowcount() <= 0 then
	return -1
end if

li_colcount = integer(adw_obj.object.datawindow.column.count)

if ab_updatemode then
	ll_color = rgb(255, 255, 255)     // white
	li_protect = 0
else
	ll_color = long(adw_obj.describe("datawindow.color"))  //553648127 // buttonface
	li_protect = 1
end if

for li_col = 1 to li_colcount
	ls_name = adw_obj.describe("#" + string(li_col) + ".name")
	ls_tabseq = adw_obj.describe("#" + string(li_col) + ".tabsequence")
	ls_style = lower(adw_obj.describe("#" + string(li_col) + ".edit.style"))
	if ls_tabseq = "0" then
		continue
	end if
	if ls_style = "editmask" and not ab_updatemode then
		ls_result = adw_obj.modify("#" + string(li_col) + ".editmask.spin=no")
	end if
	ls_result = adw_obj.modify("#" + string(li_col) + ".protect=" + string(li_protect) )
	if ls_style <> "checkbox" then
   	ls_result = adw_obj.modify("#" + string(li_col) + ".background.color=" + string(ll_color) )
	end if
next

return 1
end function

on n_rmt_dwsrv.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_dwsrv.destroy
TriggerEvent( this, "destructor" )
end on

