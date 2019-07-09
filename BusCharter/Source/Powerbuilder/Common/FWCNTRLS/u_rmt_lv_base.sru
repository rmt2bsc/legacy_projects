$PBExportHeader$u_rmt_lv_base.sru
forward
global type u_rmt_lv_base from listview
end type
end forward

global type u_rmt_lv_base from listview
int Width=741
int Height=504
int TabOrder=10
BorderStyle BorderStyle=StyleLowered!
int LargePictureWidth=32
int LargePictureHeight=32
long LargePictureMaskColor=553648127
int SmallPictureWidth=16
int SmallPictureHeight=16
long SmallPictureMaskColor=553648127
long StatePictureMaskColor=536870912
long TextColor=33554432
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
event ue_init ( )
end type
global u_rmt_lv_base u_rmt_lv_base

type variables
public:
  str_lv              istr_lv[]
  n_rmt_utility    inv_utility
  int                   ii_prev_col_sorted
  grsorttype       i_prev_col_sorttype
  m_lv_popup   im_lvmenu
  window           iw_parent
  string               is_windowtype

end variables

forward prototypes
public function any of_get_detail_key (n_rmt_ds ads, integer ai_row, integer ai_level)
public subroutine of_load_listview (n_rmt_ds ads, integer ai_level)
end prototypes

event ue_init;/*
  Example of how to setup the report columns

//  These are datawindow column names that are used to display the column headings 
//  and the column data in the list view.
istr_lv[1].level.reportcolumn[1] = "customer_fname"                
istr_lv[1].level.reportcolumn[2] = "customer_lname"
istr_lv[1].level.reportcolumn[3] = "customer_company_name"
istr_lv[1].level.reportcolumn[4] = "customer_address"
istr_lv[1].level.reportcolumn[5] = "customer_city"
istr_lv[1].level.reportcolumn[6] = "customer_state"
istr_lv[1].level.reportcolumn[7] = "customer_zip"
istr_lv[1].level.reportcolumn[8] = "customer_phone"

//  These are the sizes for each column.   The list view object will use these values
//  to determine the width of the column to display.
istr_lv[1].level.colsize[1] = 12
istr_lv[1].level.colsize[2] = 15
istr_lv[1].level.colsize[3] = 30
istr_lv[1].level.colsize[4] = 35
istr_lv[1].level.colsize[5] = 20
istr_lv[1].level.colsize[6] = 10
istr_lv[1].level.colsize[7] = 10
istr_lv[1].level.colsize[8] = 15

//  This is the datawindow column that will be used to retrieve the 
//  data value for the key to be stored in the data property of the
//  listview item.  This key value will be used to retrieve detail 
//  data when the listview item is double clicked.
istr_lv[1].level.detailkeycol[1] = "cust_id"


//  This is the datasource that will be used to retrieve the
//  detail data for the listview item that was doubleclicked.
istr_lv[1].level.detaildataobject = "d_customer_detail"

//  Extra property
istr_lv[1].level.duplicates = false




//   Second level if needed.
istr_lv[2].level.reportcolumn[1] = "order_id"
istr_lv[2].level.reportcolumn[2] = "order_date"
istr_lv[2].level.reportcolumn[3] = "sales_rep"
istr_lv[2].level.reportcolumn[4] = "fin_code_id"
istr_lv[2].level.reportcolumn[5] = "region"
istr_lv[2].level.colsize[1] = 10
istr_lv[2].level.colsize[2] = 15
istr_lv[2].level.colsize[3] = 20
istr_lv[2].level.colsize[4] = 15
istr_lv[2].level.colsize[5] = 10
istr_lv[2].level.detailkeycol[1] = "cust_id"
istr_lv[2].level.detailkeycol[2] = "order_id"
istr_lv[2].level.detaildataobject = ""
istr_lv[2].level.duplicates = false




//   Third level if needed.
istr_lv[3].level.reportcolumn[1] = "product_name"
istr_lv[3].level.reportcolumn[2] = "product_description"
istr_lv[3].level.reportcolumn[3] = "product_quantity"
istr_lv[3].level.reportcolumn[4] = "sales_order_items_quantity"
istr_lv[3].level.reportcolumn[5] = "product_unit_price"
istr_lv[3].level.reportcolumn[6] = "product_prod_size"
istr_lv[3].level.colsize[1] = 15
istr_lv[3].level.colsize[2] = 30
istr_lv[3].level.colsize[3] = 10
istr_lv[3].level.colsize[4] = 15
istr_lv[3].level.colsize[5] = 15
istr_lv[3].level.colsize[6] = 20
istr_lv[3].level.detailkeycol[1] = "cust_id"
istr_lv[3].level.detailkeycol[2] = "order_id"
istr_lv[3].level.detailkeycol[3] = "line_id"
istr_lv[3].level.detailkeycol[4] = "prod_id"
istr_lv[3].level.detaildataobject = ""
istr_lv[3].level.duplicates = false



// Initialize the picture property with an icon
// that will represent listview items
istr_lv[1].level.picture = "library!"
istr_lv[2].level.picture = "picture!"
istr_lv[3].level.picture = "table!"
istr_lv[4].level.picture = "userobject5!"


// Load pictures for each listview level
this.addsmallpicture(istr_lv[1].level.picture)
this.addsmallpicture(istr_lv[2].level.picture)
this.addsmallpicture(istr_lv[3].level.picture)
this.addsmallpicture(istr_lv[4].level.picture)

this.addlargepicture(istr_lv[1].level.picture)
this.addlargepicture(istr_lv[2].level.picture)
this.addlargepicture(istr_lv[3].level.picture)
this.addlargepicture(istr_lv[4].level.picture)

*/

return
end event

public function any of_get_detail_key (n_rmt_ds ads, integer ai_row, integer ai_level);int     li_coltotal
int     li_ndx
int     li_result
any     la_key[]
any     lany
str_lvdetaildata  lstr_parms



if not isvalid(ads) then 
	return -1
end if

if isnull(ai_row) or ai_row <= 0 then 
	return -2
end if

li_coltotal = upperbound(istr_lv[ai_level].level.detailkeycol)
if li_coltotal <= 0 then 
	return -3
end if


for li_ndx = 1 to li_coltotal
	la_key[li_ndx] = this.inv_utility.of_getanydata(ads, istr_lv[ai_level].level.detailkeycol[li_ndx], ai_row)
next

lstr_parms.args = la_key
lstr_parms.dataobject = istr_lv[ai_level].level.detaildataobject
lstr_parms.level = ai_level
lany = lstr_parms

return lany
end function

public subroutine of_load_listview (n_rmt_ds ads, integer ai_level);//////////////////////////////////////////////////////////
//  Populates listview with data coming from a datastore
//////////////////////////////////////////////////////////

string  ls_col
int     li_totrows
int     li_totcols
int     li_row
int     li_col
int     li_colsize
listviewitem   llvi


this.setredraw(false)
this.deletecolumns()
this.deleteitems()


li_totrows = ads.rowcount()
li_totcols = upperbound(this.istr_lv[ai_level].level.reportcolumn)

//  Load column headings
for li_col = 1 to li_totcols	
	ls_col = istr_lv[ai_level].level.reportcolumn[li_col]
	li_colsize = istr_lv[ai_level].level.colsize[li_col]
	this.addcolumn(ads.describe(ls_col + "_t.text"), left!, this.inv_utility.of_get_colsize_pixels(li_colsize))
next

// Load column data for each row
for li_row = 1 to li_totrows
	llvi.pictureindex = ai_level
   for li_col = 1 to li_totcols	
		ls_col = istr_lv[ai_level].level.reportcolumn[li_col]
		li_colsize = istr_lv[ai_level].level.colsize[li_col]
      if li_col = 1 then
     	   llvi.label = this.inv_utility.of_getstringdata(ads, ls_col, li_row, false)		
			llvi.data = this.of_get_detail_key(ads, li_row, ai_level)
         this.additem(llvi)
		else
			this.setitem(li_row, li_col, this.inv_utility.of_getstringdata(ads, ls_col, li_row, false))   
		end if
	next
next

this.setredraw(true)
return


end subroutine

event constructor;
// Set up report columns at the descendent level  in ue_init()

// Setup pop up menu
im_lvmenu = create m_lv_popup
im_lvmenu.ilv = this
im_lvmenu.m_lvmenu.m_view.m_report.checked = true

//  Initialize listview object
this.event ue_init()

// Get parent window of this listview object
iw_parent = inv_utility.of_getParentWindow(this)

return 1



end event

event columnclick;

if ii_prev_col_sorted = column then
   if i_prev_col_sorttype = ascending! then
      this.sort(descending!, column)
      i_prev_col_sorttype = descending!
	else
   	this.sort(ascending!, column)
      i_prev_col_sorttype = ascending!
	end if
else
	this.sort(ascending!, column)
   i_prev_col_sorttype = ascending!
end if

ii_prev_col_sorted = column
end event

event rightclicked;window   lw_parent



if isvalid(im_lvmenu) then
	im_lvmenu.m_LVMenu.popmenu(iw_parent.pointerx() + 10, iw_parent.pointery() + 10)
end if
end event

event doubleclicked;
listviewitem    llvi
string          ls_key[]
str_lvdetaildata  lstr_parms


if index = -1 or isnull(index) then
	return index
end if

if this.getitem(index, llvi) = -1 then
	return -2
end if

lstr_parms = llvi.data
//openwithparm(w_lv_detail_edit_window, lstr_parms)

//this.inv_utility.of_getstringarray(llvi.data, ls_key)


end event

event key;int  index


index = this.selectedindex()
choose case keyflags
	case 0     // no modifier keys pressed
		if key = KeyEnter! then
		   this.event doubleclicked(index)
		end if
	case 1     // shift key pressed
	case 2     // control key pressed
	case 3     // shift and control keys pressed
end choose	
	

end event

