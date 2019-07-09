$PBExportHeader$u_rmt_tv_base.sru
forward
global type u_rmt_tv_base from treeview
end type
end forward

global type u_rmt_tv_base from treeview
int Width=494
int Height=360
int TabOrder=10
BorderStyle BorderStyle=StyleLowered!
long PictureMaskColor=553648127
long StatePictureMaskColor=536870912
long TextColor=33554432
int TextSize=-10
int Weight=400
string FaceName="Arial"
FontCharSet FontCharSet=Ansi!
FontFamily FontFamily=Swiss!
FontPitch FontPitch=Variable!
event ue_custom_filter ( integer ai_level,  ref string as_filter )
event ue_init ( )
event ue_loadtree ( )
event ue_selectionchanged ( datastore ads,  integer ai_level,  long al_handle )
end type
global u_rmt_tv_base u_rmt_tv_base

type variables
n_rmt_utility  inv_utility
str_tv  istr_tv[]

protected:
   long   il_root
   string  is_rootlabel
end variables

forward prototypes
public function integer of_insertnode (treeviewitem atvi)
public subroutine of_setrootlabel (string as_label)
public function string of_getrootlabel ()
public function integer of_filter_treeview (n_rmt_ds ads, any aa_data, integer ai_level)
end prototypes

event ue_custom_filter;//string  ls_temp
//
//choose case ai_level
//	case 2
//		ls_temp = "line_id = 1"
//	case 4
//end choose
//
//if len(ls_temp) <= 0 then
//	return
//end if
//
//if len(as_filter) > 0 then
//	as_filter += " and " + ls_temp
//else
//	as_filter = ls_temp
//end if
//
return
end event

event ue_init;////////////////////////////////////////////////////////////////////
// Setup all of the properies that will be used to identify and 
// arrange the data for each of the treeview levels
////////////////////////////////////////////////////////////////////



this.sorttype = Ascending!


/*  Example setup code for the descendent level:

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

event ue_loadtree;int   li_totlevels
int   li_levelndx
int   li_totrows
treeviewitem  ltvi

//  create and retrieve each level's datastore
li_totlevels = upperbound(istr_tv)
for li_levelndx = 1 to  li_totlevels
	istr_tv[li_levelndx].level.ds = create n_rmt_ds    //datastore
	istr_tv[li_levelndx].level.ds.dataobject = istr_tv[li_levelndx].level.dataobject
	istr_tv[li_levelndx].level.ds.settransobject(sqlca)
	li_totrows = istr_tv[li_levelndx].level.ds.retrieve()
//	istr_tnav[li_levelndx].level.prevkey = '!@#$%^&*()'
next

il_root = this.insertitemfirst(0, this.of_getrootlabel(), 1)  // Setup root level
this.getitem(il_root, ltvi)

// Setup all other child nodes by traversing the datastore, ids_data.
// ids_data should be in key order so that this process can populate
// the treeview using a line-break method.
this.of_insertnode(ltvi)

// Expand only the root level
this.expanditem(ltvi.itemhandle)
this.selectitem(il_root)

return

end event

event ue_selectionchanged;//  Add code here to process selected treenode


return


end event

public function integer of_insertnode (treeviewitem atvi);//  Load the child nodes


int    li_colndx
int    li_coltotal
int    li_row 
int    li_rowtotal
any    la_colvalue
string ls_temp
string ls_colname
string ls_coltype
string ls_displaycolname
long   ll_childlevel
long   ll_handle
treeviewitem  ltvi
n_rmt_ds      lds_data

ll_childlevel = atvi.level + 1    // Position to the target child level
if ll_childlevel > upperbound(istr_tv) then
	return 0
end if

// check if target child level is passed the total number of
// levels allocated for the tree
if ll_childlevel > upperbound(istr_tv) then
	return -1      //  level out of bounds error code...usually means there are no more levels to process
end if

if isvalid(istr_tv[atvi.level].level.ds) then
   lds_data = istr_tv[atvi.level].level.ds
else
	return -2     //  current level's datastore has not be initialized
end if


// filter next level's datastore with parent's data
li_rowtotal = this.of_filter_treeview(lds_data, atvi.data, atvi.level) 

//  Obtain the total number of child key columns and 
//  the Display value from the parent.
li_coltotal = upperbound(istr_tv[atvi.level].level.colname)
ls_temp = ''
ls_displaycolname = istr_tv[atvi.level].level.displaycolname

//  Add Treeview items to current node
for li_row = 1 to li_rowtotal
   //  Get Key column values that corresponds with 
   //  the column names for the parent level.
   for li_colndx = 1 to li_coltotal
      ls_colname = istr_tv[atvi.level].level.colname[li_colndx]
      la_colvalue = this.inv_utility.of_getanydata(lds_data, ls_colname, li_row)
     	istr_tv[atvi.level].level.colvalue[li_colndx] = la_colvalue
   next
			
   ltvi.data = istr_tv[atvi.level].level.colvalue
  	ltvi.pictureindex = ll_childlevel
   ltvi.selectedpictureindex = ll_childlevel
  	ltvi.label = lds_data.getitemstring(li_row, ls_displaycolname)
  	ll_handle = this.insertitemlast(atvi.itemhandle, ltvi)
  	this.getitem(ll_handle, ltvi)
   this.of_insertnode(ltvi)     //  Recursion for the next level
next

return ltvi.itemhandle
end function

public subroutine of_setrootlabel (string as_label);
if not isnull(as_label) then
	is_rootlabel = as_label
end if
end subroutine

public function string of_getrootlabel ();
return is_rootlabel
end function

public function integer of_filter_treeview (n_rmt_ds ads, any aa_data, integer ai_level);int     li_coltotal
int     li_ndx
int     li_result
string  ls_filter
string  ls_temp
any     la_data[]


/////////////////////////////////////////////////////////////////////////////
// Since the current level of the treeview is equivocal to istr_tv element
// current level - 1, the parent level is ai_level - 1.  For
// example.  A treeview item's data property at level 2 would be
// associated with istr_tv at index 1.   This happens because the
// root node is setup at level one, and its immediate children begin 
// at level 2.   When setting up levels in terms of istr_tv,
// each level implemented is for the immediate child level below.
/////////////////////////////////////////////////////////////////////////////
ls_filter = ""
if ai_level > 1 then  //  if not root level	
   ai_level -= 1 // 
   li_coltotal = upperbound(istr_tv[ai_level].level.colname)
	if li_coltotal > 0 then
		la_data = aa_data
      if li_coltotal = upperbound(aa_data)  then
         for li_ndx = 1 to li_coltotal
				choose case classname(la_data[li_ndx])
					case 'string'
      	         ls_filter += istr_tv[ai_level].level.colname[li_ndx] + " = " + "'" + string(la_data[li_ndx]) + "'"
					case else
      	         ls_filter += istr_tv[ai_level].level.colname[li_ndx] + " = " + string(la_data[li_ndx])
				end choose
   	      if li_ndx < li_coltotal then
	   	      ls_filter += " and "
         	end if
         next
	   end if
	end if
end if

this.event ue_custom_filter(ai_level, ls_filter) 
ads.setfilter(ls_filter)
ads.filter()

return ads.rowcount()
end function

event constructor;// Set up report columns at the descendent level  in ue_init()

this.event ue_init()



end event

event selectionchanged;/////////////////////////////////////////////////////////////////
//  if the current level is not greater than the total number
//  of elements in istr_tv,  filter its datasource for this
//  event's descendent level to process
/////////////////////////////////////////////////////////////////

treeviewitem  ltvi
int    li_result	

this.getitem(newhandle, ltvi)
if (ltvi.level) > upperbound(istr_tv) then
   return -1   
else
	li_result = this.of_filter_treeview(istr_tv[ltvi.level].level.ds, ltvi.data, ltvi.level)
	this.event ue_selectionchanged(istr_tv[ltvi.level].level.ds, ltvi.level, ltvi.itemhandle)
   return li_result
end if



/*     
Properties that could be used at the descendent level


treeviewitem  ltvi
int    li_result	

//  Important properties to access
this.getitem(handle, ltvi)
level - ltvi.level
datastore  -   istr_tv[ltvi.level].level.ds

*/
end event

event doubleclicked;treeviewitem    ltvi


this.getitem(handle, ltvi)
if ltvi.children then
	if ltvi.expanded then
		this.collapseitem(handle)
	else
		this.expanditem(handle)
	end if
end if

end event

