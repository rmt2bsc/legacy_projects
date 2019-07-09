delete from purchase_order_status_hist;
delete from purchase_order_items;
delete from purchase_order;

delete from sales_order_status_hist;
delete from sales_invoice;
delete from sales_order_items;
delete from sales_order;

delete from customer_activity;
delete from creditor_activity;
delete from vendor_items;
delete from customer;
--delete from creditor;

delete from xact_type_item_activity;
delete from xact_posting;
delete from xact;

commit;
