$PBExportHeader$n_charter_order_doc_print_menu_bo.sru
forward
global type n_charter_order_doc_print_menu_bo from n_rmt_windatabject
end type
end forward

global type n_charter_order_doc_print_menu_bo from n_rmt_windatabject
end type
global n_charter_order_doc_print_menu_bo n_charter_order_doc_print_menu_bo

type variables
boolean   ib_print_client
boolean   ib_print_quote
boolean   ib_print_order
boolean   ib_print_transaction
boolean   ib_print_invoice
boolean   ib_print_drivers
boolean   ib_print_agreement   /* SCR8 RMT040202 */
boolean   ib_print_expenses   /* SCR 8 RMT012605 */

protected:

int  ii_client_id
int  ii_quote_id
int  ii_order_id

end variables
forward prototypes
public subroutine of_setquote (integer ai_value)
public subroutine of_setclient (integer ai_value)
public function integer of_getquote ()
public function integer of_getorder ()
public function integer of_set_company_data (datastore ads)
public function boolean of_printcharterorder ()
public function integer of_getclient ()
public subroutine of_setexpense (boolean ab_flag)
public subroutine of_setorder (integer ai_value)
public function boolean of_getexpense ()
end prototypes

public subroutine of_setquote (integer ai_value);this.ii_quote_id = ai_value
end subroutine

public subroutine of_setclient (integer ai_value);this.ii_client_id = ai_value
end subroutine

public function integer of_getquote ();return this.ii_quote_id
end function

public function integer of_getorder ();return this.ii_order_id
end function

public function integer of_set_company_data (datastore ads);
string  temp
str_company_info  lstr_data

lstr_data = gnv_app.of_get_company_info()

ads.object.t_company_name.text = lstr_data.name

temp = lstr_data.addr1 + "~r~n" + lstr_data.city + ", " + lstr_data.state + " " + lstr_data.zip 
ads.object.t_address.text = temp

ads.object.t_phone.text = "PHONE: " + lstr_data.phone
ads.object.t_fax.text = "FAX: " + lstr_data.fax

temp = ads.describe("t_website.name")
if not temp = "?" and not temp = "!" then
   if isNull(lstr_data.website) or len(lstr_data.website) <= 0 then
        ads.object.t_website.text = ""                                
   else
	    ads.object.t_website.text = lstr_data.website   
   end if
end if

return 1
end function

public function boolean of_printcharterorder ();int  li_rows
string  ls_msg
datastore  ds
str_company_info  lstr_data


lstr_data = gnv_app.of_get_company_info()


ds = create datastore
if not isValid(ds) or isNull(ds) then
	return false
end if

if this.ib_print_client then
   ds.dataobject = "d_charter_order_client_print"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_client_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Client Information.~r~nClient record does not exists for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
      ds.object.order_id[1] = this.ii_order_id
	 this.of_set_company_data(ds)
      ds.print()		
   end if		
end if

if this.ib_print_quote then
   ds.dataobject = "d_charter_order_trip_quote_print"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_client_id, this.ii_quote_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Trip Information.~r~nTrip record does not exists for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		ds.object.order_id[1] = this.ii_order_id
		this.of_set_company_data(ds)
         ds.print()
   end if		
end if

if this.ib_print_order then
	datawindowchild dwc_dropoff
   datawindowchild dwc_status
	
   ds.dataobject = "d_charter_order_order_print"
   ds.settransobject(sqlca)
	
	ds.getchild("dropoff_collect_tender", dwc_dropoff)
   dwc_dropoff.settransobject(sqlca)
   dwc_dropoff.retrieve(16)
   ds.getchild("status", dwc_status)
   dwc_status.settransobject(sqlca)
   dwc_status.retrieve(22)
	
   li_rows = ds.retrieve(this.ii_order_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Order Information.~r~nOrder record does not exists for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		this.of_set_company_data(ds)
		ds.print()
   end if		
end if

if this.ib_print_transaction then
   ds.dataobject = "d_charter_order_transactions"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_order_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Transaction Information.~r~nTransaction record does not exists for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		this.of_set_company_data(ds)
		ds.print()
   end if		
end if

if this.ib_print_expenses then
   ds.dataobject = "d_charter_order_expenses"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_order_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Expense Information.~r~nExpense record(s) does not exists for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		this.of_set_company_data(ds)
		ds.print()
   end if		
end if


if this.ib_print_invoice then
   ds.dataobject = "d_charter_order_invoice_main_print"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_order_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Invoice Information.~r~nInvoice record does not exists for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		this.of_set_company_data(ds)
		ds.print()
   end if		
end if

if this.ib_print_drivers then
   ds.dataobject = "d_driver_manifest"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_order_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Driver's Manifest Information.~r~nDriver(s) do not exist for Charter Order " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		this.of_set_company_data(ds)
		ds.print()
   end if		
end if

  // Begin SCR8 RMT040202
if this.ib_print_agreement then
   ds.dataobject = "d_order_agreement"
   ds.settransobject(sqlca)
   li_rows = ds.retrieve(this.ii_order_id)
	if li_rows <= 0 then
		ls_msg = "Cannot Print Order Agreement.~r~nOrder does not exist: " + string(this.ii_order_id)
		if isValid(gnv_app.inv_msg) then
   	   gnv_app.inv_msg.of_message(ls_msg, exclamation!) 
		else
		   messagebox(gnv_app.iapp_object.displayname, ls_msg, exclamation!) 
		end if
	else
		this.of_set_company_data(ds)
         lstr_data = gnv_app.of_get_company_info()
		ds.object.t_company_name2.text = lstr_data.name + "."
		ds.object.t_company_name3.text = lstr_data.name + "."
		ds.object.t_contact.text = ds.object.t_phone.text + "   " +  ds.object.t_fax.text
		if not isNull(lstr_data.website) and len(lstr_data.website) > 0 then
			ds.object.t_contact.text = ds.object.t_contact.text + "   " + lstr_data.website
		end if
		ds.print()
   end if		
end if
   // End SCR8 RMT040202

return true

end function
public function integer of_getclient ();return this.ii_client_id
end function
public subroutine of_setexpense (boolean ab_flag);return
end subroutine
public subroutine of_setorder (integer ai_value);this.ii_order_id = ai_value
end subroutine
public function boolean of_getexpense ();return this.ib_print_expenses
end function
on n_charter_order_doc_print_menu_bo.create
call super::create
end on

on n_charter_order_doc_print_menu_bo.destroy
call super::destroy
end on

