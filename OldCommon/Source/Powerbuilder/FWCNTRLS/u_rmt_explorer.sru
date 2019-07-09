$PBExportHeader$u_rmt_explorer.sru
forward
global type u_rmt_explorer from u_rmt_tabpage
end type
type st_criteria from statictext within u_rmt_explorer
end type
type st_contents from statictext within u_rmt_explorer
end type
type lv_1 from u_rmt_lv_base within u_rmt_explorer
end type
type st_itemdesc from statictext within u_rmt_explorer
end type
type st_total from statictext within u_rmt_explorer
end type
type tv_1 from u_rmt_tv_base within u_rmt_explorer
end type
end forward

global type u_rmt_explorer from u_rmt_tabpage
int Width=3973
int Height=1492
event ue_loadtree ( )
event ue_init ( )
event ue_inittree ( )
st_criteria st_criteria
st_contents st_contents
lv_1 lv_1
st_itemdesc st_itemdesc
st_total st_total
tv_1 tv_1
end type
global u_rmt_explorer u_rmt_explorer

event ue_loadtree;
tv_1.event ue_loadtree()
end event

event ue_init;/***********************************************
 *  Set resize service.
 *  Initialize datastore and retrieve its data.
 *  Initialize treeview and listview.
 ***********************************************/
int li_result

this.of_SetResize(true)

//inv_resize.of_register(this.control, "Scale")


inv_resize.of_register(tv_1, "Scale")
inv_resize.of_register(lv_1, "Scale")
inv_resize.of_register(st_contents, "Scale")
inv_resize.of_register(st_criteria, "Scale")
inv_resize.of_register(st_total, "Scale")
inv_resize.of_register(st_itemdesc, "Scale")
inv_resize.of_setminsize(st_contents.width, st_contents.height)
inv_resize.of_setminsize(st_criteria.width, st_criteria.height)
inv_resize.of_setminsize(st_total.width, st_total.height)
inv_resize.of_setminsize(st_itemdesc.width, st_itemdesc.height)







end event

event ue_inittree;this.event ue_loadtree()
end event

on u_rmt_explorer.create
int iCurrent
call super::create
this.st_criteria=create st_criteria
this.st_contents=create st_contents
this.lv_1=create lv_1
this.st_itemdesc=create st_itemdesc
this.st_total=create st_total
this.tv_1=create tv_1
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.st_criteria
this.Control[iCurrent+2]=this.st_contents
this.Control[iCurrent+3]=this.lv_1
this.Control[iCurrent+4]=this.st_itemdesc
this.Control[iCurrent+5]=this.st_total
this.Control[iCurrent+6]=this.tv_1
end on

on u_rmt_explorer.destroy
call super::destroy
destroy(this.st_criteria)
destroy(this.st_contents)
destroy(this.lv_1)
destroy(this.st_itemdesc)
destroy(this.st_total)
destroy(this.tv_1)
end on

type st_criteria from statictext within u_rmt_explorer
int X=59
int Y=36
int Width=1088
int Height=76
boolean Enabled=false
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type st_contents from statictext within u_rmt_explorer
int X=1152
int Y=36
int Width=2757
int Height=76
boolean Enabled=false
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type lv_1 from u_rmt_lv_base within u_rmt_explorer
int X=1152
int Y=116
int Width=2757
int Height=1268
ListViewView View=ListViewReport!
long BackColor=16777215
end type

event ue_init;call super::ue_init;/*  Exmaple

istr_lv[1].level.reportcolumn[1] = "customer_fname"
istr_lv[1].level.reportcolumn[2] = "customer_lname"
istr_lv[1].level.reportcolumn[3] = "customer_company_name"
istr_lv[1].level.reportcolumn[4] = "customer_address"
istr_lv[1].level.reportcolumn[5] = "customer_city"
istr_lv[1].level.reportcolumn[6] = "customer_state"
istr_lv[1].level.reportcolumn[7] = "customer_zip"
istr_lv[1].level.reportcolumn[8] = "customer_phone"
istr_lv[1].level.colsize[1] = 12
istr_lv[1].level.colsize[2] = 15
istr_lv[1].level.colsize[3] = 30
istr_lv[1].level.colsize[4] = 35
istr_lv[1].level.colsize[5] = 20
istr_lv[1].level.colsize[6] = 10
istr_lv[1].level.colsize[7] = 10
istr_lv[1].level.colsize[8] = 15
istr_lv[1].level.detailkeycol[1] = "cust_id"
istr_lv[1].level.detaildataobject = "d_customer_detail"
istr_lv[1].level.duplicates = false


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
istr_lv[2].level.detaildataobject = "d_order_detail"
istr_lv[2].level.duplicates = false


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
istr_lv[3].level.detaildataobject = "d_product_detail"
istr_lv[3].level.duplicates = false



// Initialize the picture property with an icon
// that will represent listview items
//istr_lv[1].level.picture = "library!"
istr_lv[1].level.picture = "picture!"
istr_lv[2].level.picture = "table!"
istr_lv[3].level.picture = "userobject5!"


// Load pictures for each listview level
this.addsmallpicture(istr_lv[1].level.picture)
this.addsmallpicture(istr_lv[2].level.picture)
this.addsmallpicture(istr_lv[3].level.picture)

this.addlargepicture(istr_lv[1].level.picture)
this.addlargepicture(istr_lv[2].level.picture)
this.addlargepicture(istr_lv[3].level.picture)


*/
end event

type st_itemdesc from statictext within u_rmt_explorer
int X=718
int Y=1380
int Width=3195
int Height=76
boolean Enabled=false
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=67108864
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type st_total from statictext within u_rmt_explorer
int X=64
int Y=1380
int Width=654
int Height=76
boolean Enabled=false
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
long TextColor=33554432
long BackColor=79741120
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
end type

type tv_1 from u_rmt_tv_base within u_rmt_explorer
int X=64
int Y=112
int Width=1079
int Height=1268
long BackColor=16777215
end type

event ue_init;call super::ue_init;
// Setup all of the properies that will be used to identify and 
// arrange the data for each of the treeview levels

/*   Example

this.of_setrootlabel("Customers")                //  Set the label for the root level

istr_tv[1].level.dataobject = "d_customers"      //  Datastore that contains child data to be displayed by level 1.
istr_tv[1].level.colname[1] = "cust_id"          //  Key to be stored in the data property for each level 2 node.
istr_tv[1].level.displaycolname = "c_custname"   //  The column name that will contain the data for the label 
                                                 //  for each level 2 node.

istr_tv[2].level.dataobject = "d_orders"         //  Datastore that contains child data to be displayed by level 2.
istr_tv[2].level.colname[1] = "order_id"         //  Key to be stored in the data property for each level 3 node.
istr_tv[2].level.displaycolname = "c_orderdate"  //  The column name that will contain the data for the label 
                                                 //  for each level 3 node.

//  This last node will not be displayed.  It is for data purposes only!
istr_tv[3].level.dataobject = "d_products"       //  Datastore that represents the detail data for level 3.
istr_tv[3].level.colname[1] = "order_id"         //  Key to filter data.



// Initialize the picture property with an icon
// that will represent both treeview items
istr_tv[1].level.picture = "library!"
istr_tv[2].level.picture = "picture!"
istr_tv[3].level.picture = "table!"

// Load pictures for each treeview level
this.addpicture(istr_tv[1].level.picture)
this.addpicture(istr_tv[2].level.picture)
this.addpicture(istr_tv[3].level.picture)


*/
end event

event ue_selectionchanged;call super::ue_selectionchanged;
string    ls_msg
treeviewitem   ltvi
w_rmt_ancestor  lw

this.getitem(al_handle, ltvi)
if isvalid(ads) and not isnull(ai_level) then
   lv_1.of_load_listview(ads, ai_level)
end if

st_contents.text = space(1) + "Contents of" + space(3) + "'" + ltvi.label + "'"
ls_msg = space(1) + string(ads.rowcount()) + space(2)
choose case ltvi.level
	case 1
		ls_msg += "Customers"
	case 2
		ls_msg += "Orders"
	case 3
		ls_msg += "Products"
	case else
end choose
			
lw = this.inv_utility.of_getparentwindow(this)
lw.event ue_microhelp(ls_msg)

end event

