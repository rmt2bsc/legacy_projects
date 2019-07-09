$PBExportHeader$n_rmt_utility.sru
$PBExportComments$General Purpose Objects
forward
global type n_rmt_utility from nonvisualobject
end type
end forward

global type n_rmt_utility from nonvisualobject autoinstantiate
end type

type prototypes
// Dialogs
function long PFC_PrintDlg (ulong hwnd, ref str_printdlgattrib printstruct) library "pfccom32.dll"

// Sound
function Boolean PlaySound( String szSound, uInt Hmodule, uLong Flags) library "winmm.dll"

end prototypes

forward prototypes
public function integer of_get_colsize_pixels (integer ai_colsize)
public function integer of_getstringarray (any any_data, ref string as_array[])
public function window of_getparentwindow (graphicobject ago_obj)
public function any of_getanydata (datastore ads_data, string as_colname, integer ai_row)
public function integer of_setfilesrv (ref n_rmt_filesrv anv_filesrv, boolean ab_switch)
public function string of_getstringdata (powerobject apo_data, string as_colname, integer ai_row, boolean ab_addquotes)
public function integer of_updatecomments (integer ai_id, string as_text)
public function boolean of_printdialog (window aw_parent)
public function boolean of_sound (string as_filename)
end prototypes

public function integer of_get_colsize_pixels (integer ai_colsize);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_get_colsize_pixels
//
//	Description: Convert the expected byte size of a column to PBU's.
//              This is mainly used to actuate the size of a listview
//              item's column when the listview control is viewd as
//              a report.  The minimum size that will be allowed is "10"
//	             
// Arguments: integer  ai_colsize
//
// Returns: integer ( column size in pixels )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

if isnull(ai_colsize) or ai_colsize < 10 then
	ai_colsize = 10
end if

return (ai_colsize * 96) / 3	

end function

public function integer of_getstringarray (any any_data, ref string as_array[]);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_getanydata
//
//	Description: Converts a variable(any_data) of type any, which contains an array of 
//              primitive data, to an array of string.   
//	             
// Arguments: any  any_data
//            string as_array[]
//
// Returns: integer ( array count of strings )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int  li_beg, li_end, li_ndx
string  ls_temp
string  ls_data


ls_temp = string(any_data)
if isnull(ls_temp) or ls_temp = "" then
	return 0      // May not be any selection criteria
end if

as_array = any_data

return upperbound(as_array)
end function

public function window of_getparentwindow (graphicobject ago_obj);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_getanydata
//
//	Description: Gets and returns to the caller the window object that 
//              ago_obj resides in.   
//	             
// Arguments: graphic object  ago_obj
//
// Returns: window ( parent window )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

powerobject	lpo_parent


// Loop getting the parent of the object until it is of type window!
lpo_parent = ago_obj.GetParent()
do while IsValid (lpo_parent) 
	if lpo_parent.TypeOf() <> window! then
		lpo_parent = lpo_parent.GetParent()
	else
		exit
	end if
loop

return lpo_parent

end function

public function any of_getanydata (datastore ads_data, string as_colname, integer ai_row);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_getanydata
//
//	Description: Gets data from a datawindow column, converts that data
//              to an any type, and returns the "any" results to the
//              caller. 
//	             
// Arguments: n_rmt_ds  ads_data
//            string    as_colname
//            integer   ai_row
//
// Returns: any ( data converted to any data type )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


string ls_coltype
any    la_temp


	
ls_coltype = lower(ads_data.describe(as_colname + ".coltype"))
           
if mid(ls_coltype, 1, 8) = "datetime" or  mid(ls_coltype, 1, 8) = "timestam" then
   la_temp = string(ads_data.getitemdatetime(ai_row, as_colname))
else
	choose case mid(ls_coltype, 1, 3)
      case "cha"
				la_temp = ads_data.getitemstring(ai_row, as_colname)
  		case "dec", "int", "lon", "num", "rea", "ulo"
   		la_temp = ads_data.getitemnumber(ai_row, as_colname)
		case "dat"
			la_temp = ads_data.getitemdate(ai_row, as_colname)
		case "tim"
			la_temp = ads_data.getitemtime(ai_row, as_colname)
	end choose
end if	


return la_temp

end function

public function integer of_setfilesrv (ref n_rmt_filesrv anv_filesrv, boolean ab_switch);//////////////////////////////////////////////////////////////////////////////
// Function Name:  of_setfilesrv
//	Arguments:		
//	anv_Filesrv		User Object of type n_cst_filesrv that will be created - passed by reference
//	ab_Switch		True - start (create) the object
//						False - stop (destroy) the object
//
//	Returns:  		Integer
//						 1 - Successful operation.
//						 0 - No action taken.
//						-1 - An error was encountered.
//
//	Description:  	Creates/Destroys the file handler object (platform-specific)
//
//////////////////////////////////////////////////////////////////////////////
//
//	Revision History
//
//	Version
//	5.0   Initial version
// 5.0.02 Added support for Macintosh, Solaris, and Windows NT.
// 5.0.02 Added argument and error checking.
// 5.0.03 Fixed Creation of 16 bit service for 16 bit exe running under NT
//	6.0	Added support for Unix version on AIX (IBM) and HPUX (HP)
//
//////////////////////////////////////////////////////////////////////////////


// Argument and error checking.
If IsNull(ab_switch) Then Return -1

//if ab_switch then
//	if IsNull (anv_filesrv) or not IsValid (anv_filesrv) then
//		// create file service object based on platform
//		choose case gnv_app.ienv_object.ostype
//			case macintosh!
//				anv_filesrv = create n_cst_filesrvmac
//			case windows!
//				if gnv_app.ienv_object.win16 then
//					anv_filesrv = create n_cst_filesrvwin16
//				else
//					anv_filesrv = create n_cst_filesrvwin32
//				end if
//			case windowsnt!
//				if gnv_app.ienv_object.win16 then
//					anv_filesrv = create n_cst_filesrvwin16
//				else
//					anv_filesrv = create n_cst_filesrvwin32
//				end if
//			case sol2!
//				anv_filesrv = create n_cst_filesrvsol2
//			case hpux!
//				anv_filesrv = create n_cst_filesrvhpux
//			case aix!
//				anv_filesrv = create n_cst_filesrvaix
//			case else
//				anv_filesrv = create n_cst_filesrv
//		end choose
//		return 1
//	end if
//else
//	if IsValid (anv_filesrv) then
//		destroy anv_filesrv
//		Return 1
//	end if
//end if

Return 0

end function

public function string of_getstringdata (powerobject apo_data, string as_colname, integer ai_row, boolean ab_addquotes);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_getstringdata
//
//	Description:  Gets data from a datawindow or datastore column, converts that data
//               to a string type, and returns the string results to the
//               caller.   If the incoming data is already a string, then
//               "ab_addquotes" will determine if the string should be 
//               delimited by single quotation marks.
//	             
// Arguments: n_rmt_ds  apo_data
//            string as_colname
//            integer ai_row
//            boolean  ab_addqotes
//
// Returns: string ( data converted to a string )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


string ls_coltype
string ls_temp
datawindow  adw
datastore   ads


if isvalid(apo_data) then
   choose case apo_data.typeof() 
      case datawindow!
   		adw = apo_data
   		ls_coltype = lower(adw.describe(as_colname + ".coltype"))
           
         if mid(ls_coltype, 1, 8) = "datetime" or  mid(ls_coltype, 1, 8) = "timestam" then
            ls_temp = string(adw.getitemdatetime(ai_row, as_colname))
         else
         	choose case mid(ls_coltype, 1, 3)
               case "cha"
			          if ab_addquotes then
         				 ls_temp = "'" + adw.getitemstring(ai_row, as_colname) + "'"
       				 else
        					 ls_temp = adw.getitemstring(ai_row, as_colname)
       				 end if
         		case "dec", "int", "lon", "num", "rea", "ulo"
   		          ls_temp = string(adw.getitemnumber(ai_row, as_colname))
         		case "dat"
			          ls_temp = string(adw.getitemdate(ai_row, as_colname))
         		case "tim"
			          ls_temp = string(adw.getitemtime(ai_row, as_colname))
         	end choose
         end if	

       case datastore!
			 ads = apo_data
          ls_coltype = lower(ads.describe(as_colname + ".coltype"))
           
          if mid(ls_coltype, 1, 8) = "datetime" or  mid(ls_coltype, 1, 8) = "timestam" then
             ls_temp = string(ads.getitemdatetime(ai_row, as_colname))
          else
             choose case mid(ls_coltype, 1, 3)
                case "cha"
			          if ab_addquotes then
  				          ls_temp = "'" + ads.getitemstring(ai_row, as_colname) + "'"
         			 else
			      		 ls_temp = ads.getitemstring(ai_row, as_colname)
       				 end if
  		          case "dec", "int", "lon", "num", "rea", "ulo"
               	 ls_temp = string(ads.getitemnumber(ai_row, as_colname))
         		 case "dat"
			          ls_temp = string(ads.getitemdate(ai_row, as_colname))
         		 case "tim"
			          ls_temp = string(ads.getitemtime(ai_row, as_colname))
         	 end choose
          end if	

       case else
			 setnull(ls_temp)
	end choose
end if

return ls_temp



end function

public function integer of_updatecomments (integer ai_id, string as_text);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_updateComments
//
//	Description: Updates the comment table with the input parameters ai_id and as_text.
//              if ai_id equals null add the comment.  Otherwise, update the comment.
//	             
// Arguments: integer   ai_id
//            string    as_text
//
// Returns: integer - the comment id that was updated
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////



int  li_id
string  ls_user
date   ld_current


if isNull(as_text) or len(as_text) <= 0 then
	return 0
end if

ld_current = today()
ls_user = gnv_app.of_getUserID()
if isNull(ai_id) then
   select max(comment_id)
      into :li_id
   	from comments;
	if isNull(li_id) or li_id = 0 then
   	li_id = 1
   else
	   li_id++
	end if
	
	insert into comments
     (comment_id,
      comment_text,
    	date_created,
   	date_updated,
	   user_id)
   values
     (:li_id,
      :as_text,
   	:ld_current,
	   :ld_current,
      :ls_user);	
else
	li_id = ai_id
	update comments set
	   comment_text = :as_text,
		date_updated = :ld_current,
		user_id = :ls_user
	where comment_id = :li_id;
end if
	



return li_id;
end function

public function boolean of_printdialog (window aw_parent);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_printDialog
//
//	Description: Display the System Print Dialog to set up printing options.
//              
//	             
// Arguments: window aw_window (window responsible for calling this method)
//
// Returns: boolean (true=print, flase=do not print)
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

long  ll_result
boolean lb_successful
long ll_hwnd
str_printdlgattrib  lstr_data

ll_hwnd = handle (aw_parent)
if ll_hwnd = 0 then
	return false
end if

lb_successful = false
ll_result = pfc_printdlg (ll_hwnd, lstr_data) // returns 1=print -1=cancel
if ll_result = 1 then
 	lb_successful = true
else
	lb_successful = false
end if

return lb_successful



end function

public function boolean of_sound (string as_filename);
Uint  ui_null
Ulong ul_filename_flag
Ulong ul_async_flag


setNull(ui_null)
ul_filename_flag = 131072
ul_async_flag = 1

return PlaySound(as_filename, ui_null, ul_async_flag)


end function

on n_rmt_utility.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_utility.destroy
TriggerEvent( this, "destructor" )
end on

