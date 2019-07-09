$PBExportHeader$n_charter_order_bo.sru
$PBExportComments$Business object for Charter Order Window
forward
global type n_charter_order_bo from n_rmt_windatabject
end type
end forward

global type n_charter_order_bo from n_rmt_windatabject
end type
global n_charter_order_bo n_charter_order_bo

type variables
public:
  int          ii_client_id
  int          ii_quote_id
  int          ii_order_id
  int         ii_trans_id
  int         ii_comment_id
  int         ii_order_status
  dec {2} idec_bustotal
  int        ii_buscount
  boolean  ib_invoiced
  
/* Constants  */
   
   int         ii_ORDER_CANCEL = 206
   int         ii_ORDER_CLOSE = 205
   int         ii_ORDER_QUOTE = 222
   int         ii_ORDER_ORDER = 203
   int         ii_ORDER_INVOICED = 204
   
end variables
forward prototypes
public function integer of_getnewquoteid ()
public function decimal of_getbalance (integer ai_order_id)
public function integer of_updatebuscount (integer ai_count)
public function integer of_setupinvoice (integer ai_order_id)
public function integer of_reconcileorders ()
public function integer of_clientchanged (integer ai_client_id)
public function integer of_setneworder (integer ai_quote_id)
end prototypes

public function integer of_getnewquoteid ();
w_charter_order  lw_parent
int   li_quote

lw_parent = this.iw_parent
li_quote = lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.quote_id[1]

if isnull(li_quote) then
	li_quote = 0
end if

return li_quote
end function

public function decimal of_getbalance (integer ai_order_id);
dec {2} ldec_balancedue
w_charter_order  lw_parent
int  li_result
int  li_bus_count


lw_parent = this.iw_parent

select getOrderBalance(:ai_order_id)
  into :ldec_balancedue
  from orders;

if isnull(ldec_balancedue) then
	ldec_balancedue = 0
end if

return ldec_balancedue
end function

public function integer of_updatebuscount (integer ai_count);
w_charter_order  lw_parent
int  li_row
int  li_orig_count

lw_parent = this.iw_parent
li_row = lw_parent.tab_charter_order.tabpage_trip.dw_edit.rowcount()
li_orig_count = integer(lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.quote_bus_count[li_row])
if li_orig_count <> ai_count then
   lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.quote_bus_count[1] = ai_count
end if

return 1
end function

public function integer of_setupinvoice (integer ai_order_id);w_charter_order  lw_parent


lw_parent = iw_parent

lw_parent.tab_charter_order.tabpage_invoice.enabled = true
lw_parent.tab_charter_order.tabpage_invoice.dw_edit.of_setArgument(1, ai_order_id)    // orders ID
if lw_parent.tab_charter_order.tabpage_invoice.dw_edit.of_retrieve() >= 0 then
   return 1
else
	return -1
end if
end function

public function integer of_reconcileorders ();

datastore  ds
int li_rows
int  li_row
int  li_order_id
int  li_count
dec {2} ldec_bal

ds = create datastore

ds.dataobject = "d_charter_order_reconcile"
ds.settransobject(sqlca)
li_rows = ds.retrieve()

li_count = 0
for li_row = 1 to li_rows
	ldec_bal = ds.getitemnumber(li_row, "c_balance")
	if ldec_bal = 0 and ds.object.status[li_row] = this.ii_ORDER_INVOICED then
		li_order_id = ds.object.id[li_row]
		ds.object.status[li_row] = this.ii_ORDER_CLOSE  // Closed status   /* RMT 012202 */
		ds.object.date_updated[li_row] = today()
		ds.object.user_id[li_row] = gnv_app.of_getUserID()
		if ds.update() <> 1 then
			rollback;
			return -1
		else
			commit;
   		li_count++
		end if
	end if
next

return li_count

end function

public function integer of_clientchanged (integer ai_client_id);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_clientChanged
//
//	Arguments:	integer - clinet id
//
//	Returns:  integer
//	 1 = updates are successful
//	 0 = No updates occurred
//	-1 = error
//
//	Description:	Notifies the Trip tab of a client change with ai_client_id.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

w_charter_order  lw_parent
int   li_rows

lw_parent = iw_parent
li_rows = lw_parent.tab_charter_order.tabpage_trip.dw_edit.rowcount()
lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.quote_client_id[1] = ai_client_id
lw_parent.inv_bo.ii_client_id = ai_client_id
lw_parent.event ue_enablecontrols()

return 1
end function
public function integer of_setneworder (integer ai_quote_id);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_setNewOrder
//
//	Arguments:	integer - quote_id
//
//	Returns:  integer
//	 1 = updates are successful
//	 0 = No updates occurred
//	-1 = error
//
//	Description:	Notifies the Order tab to set up new order
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
// SCR    Date        Developer    Description
// =====  =========== =========  =============================================
//    11   04/04/2002 RMT        Initialized Minimum Hourly Rate Factor to 5
//                               hours when adding a order.
//////////////////////////////////////////////////////////////////////////////

w_charter_order  lw_parent
int  li_result
int  li_order_id
int  li_comment_id
string  ls_user
n_rmt_utility   lvn_util

lw_parent = this.iw_parent

select max(id)
  into :li_order_id
  from orders;
  
if isNull(li_order_id) or li_order_id <= 0 then
	li_order_id = 355
else
	li_order_id++
end if

lw_parent.tab_charter_order.tabpage_order.dw_edit.object.id[1] = li_order_id
lw_parent.tab_charter_order.tabpage_order.dw_edit.object.quote_id[1] = ai_quote_id
lw_parent.tab_charter_order.tabpage_order.dw_edit.object.min_hour_factor[1] = gnv_app.ii_DEFAULTMINHOURFACTOR   // SCR11 RMT040402
lw_parent.tab_charter_order.tabpage_trip.dw_edit.object.status[1] = this.ii_ORDER_ORDER   // RMT 012202 
li_result = lw_parent.tab_charter_order.tabpage_order.of_preupdate()
if li_result <> 1 then
	return -1
end if

if lw_parent.tab_charter_order.tabpage_order.dw_edit.update() <> 1 then
	return -2
end if
commit;

//li_order_id = lw_parent.tab_charter_order.tabpage_order.dw_edit.object.id[1]

  // Setup default comment for new bus detail record
setnull(li_comment_id)
li_comment_id = lvn_util.of_updatecomments(li_comment_id, 'N/A')  

  // Insert by default the first bus for the order
insert into bus_detail
  (orders_id, special_instructions, min_hour_factor)
values 
  (:li_order_id, :li_comment_id, :gnv_app.ii_DEFAULTMINHOURFACTOR);
  
return li_order_id

end function
on n_charter_order_bo.create
call super::create
end on

on n_charter_order_bo.destroy
call super::destroy
end on

