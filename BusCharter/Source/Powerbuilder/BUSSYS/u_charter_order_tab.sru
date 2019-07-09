$PBExportHeader$u_charter_order_tab.sru
forward
global type u_charter_order_tab from u_rmt_tab
end type
type tabpage_client from u_client_tabpage within u_charter_order_tab
end type
type tabpage_client from u_client_tabpage within u_charter_order_tab
end type
type tabpage_trip from u_trip_quote_tabpage within u_charter_order_tab
end type
type tabpage_trip from u_trip_quote_tabpage within u_charter_order_tab
end type
type tabpage_order from u_order_tabpage within u_charter_order_tab
end type
type tabpage_order from u_order_tabpage within u_charter_order_tab
end type
type tabpage_transaction from u_transaction_tabpage within u_charter_order_tab
end type
type tabpage_transaction from u_transaction_tabpage within u_charter_order_tab
end type
type tabpage_expense from u_expenses_tabpage within u_charter_order_tab
end type
type tabpage_expense from u_expenses_tabpage within u_charter_order_tab
end type
type tabpage_invoice from u_invoice_tabpage within u_charter_order_tab
end type
type tabpage_invoice from u_invoice_tabpage within u_charter_order_tab
end type
end forward

global type u_charter_order_tab from u_rmt_tab
integer width = 4265
integer height = 1844
long backcolor = 80269524
boolean powertips = true
tabpage_client tabpage_client
tabpage_trip tabpage_trip
tabpage_order tabpage_order
tabpage_transaction tabpage_transaction
tabpage_expense tabpage_expense
tabpage_invoice tabpage_invoice
end type
global u_charter_order_tab u_charter_order_tab

forward prototypes
public function integer of_getnewquoteid ()
public function integer of_setneworder (integer ai_quote_id)
end prototypes

public function integer of_getnewquoteid ();
int   li_quote
li_quote = this.tabpage_trip.dw_edit.object.quote_id[1]

if isnull(li_quote) then
	li_quote = 0
end if

return li_quote
end function

public function integer of_setneworder (integer ai_quote_id);
int   li_result 

li_result = this.tabpage_order.event ue_setNewOrder(ai_quote_id)

return 1
end function

on u_charter_order_tab.create
this.tabpage_client=create tabpage_client
this.tabpage_trip=create tabpage_trip
this.tabpage_order=create tabpage_order
this.tabpage_transaction=create tabpage_transaction
this.tabpage_expense=create tabpage_expense
this.tabpage_invoice=create tabpage_invoice
int iCurrent
call super::create
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.tabpage_client
this.Control[iCurrent+2]=this.tabpage_trip
this.Control[iCurrent+3]=this.tabpage_order
this.Control[iCurrent+4]=this.tabpage_transaction
this.Control[iCurrent+5]=this.tabpage_expense
this.Control[iCurrent+6]=this.tabpage_invoice
end on

on u_charter_order_tab.destroy
call super::destroy
destroy(this.tabpage_client)
destroy(this.tabpage_trip)
destroy(this.tabpage_order)
destroy(this.tabpage_transaction)
destroy(this.tabpage_expense)
destroy(this.tabpage_invoice)
end on

type tabpage_client from u_client_tabpage within u_charter_order_tab
integer x = 18
integer y = 112
integer width = 4229
integer height = 1716
string text = "Client Details"
string picturename = "images\Icon0130.bmp"
end type

type tabpage_trip from u_trip_quote_tabpage within u_charter_order_tab
integer x = 18
integer y = 112
integer width = 4229
integer height = 1716
long backcolor = 80269524
string text = "Trip Details"
long tabbackcolor = 80269524
string picturename = "Picture5!"
end type

type tabpage_order from u_order_tabpage within u_charter_order_tab
integer x = 18
integer y = 112
integer width = 4229
integer height = 1716
long backcolor = 80269524
string text = "Financials"
long tabbackcolor = 80269524
string picturename = "Custom048!"
end type

type tabpage_transaction from u_transaction_tabpage within u_charter_order_tab
integer x = 18
integer y = 112
integer width = 4229
integer height = 1716
string text = "Transactions"
string picturename = "images\Icon0141.bmp"
end type

type tabpage_expense from u_expenses_tabpage within u_charter_order_tab
integer x = 18
integer y = 112
integer width = 4229
integer height = 1716
string text = "Expenses"
string picturename = "SingletonReturn!"
end type

type tabpage_invoice from u_invoice_tabpage within u_charter_order_tab
integer x = 18
integer y = 112
integer width = 4229
integer height = 1716
long backcolor = 80269524
string text = "Invoice Summary"
long tabbackcolor = 80269524
string picturename = "DosEdit5!"
end type

