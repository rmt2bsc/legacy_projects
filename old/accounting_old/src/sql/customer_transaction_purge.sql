--delete from purchase_order_status_hist;
--delete from purchase_order_items;
--delete from purchase_order;


delete from sales_order_status_hist where so_id in (select so_id from sales_order where customer_id = 19);
delete from sales_invoice where so_id in (select so_id from sales_order where customer_id = 19);
delete from sales_order_items where so_id in (select so_id from sales_order where customer_id = 19);
delete from sales_order where customer_id = 19;

delete from customer_activity where customer_id = 19;

--delete from xact_type_item_activity;
--delete from xact_posting;
--delete from xact;