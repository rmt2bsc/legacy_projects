/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_salesorder_items_by_salesorder                      */
/*==============================================================*/
create view vw_salesorder_items_by_salesorder as
Select sales_order_items.id sales_order_item_id, 
       sales_order_items.sales_order_id,
       sales_order_items.item_master_id,
       
       item_master.vendor_id,
       item_master.item_type_id,
       item_master.description item_name,
       item_master.vendor_item_no,
       item_master.item_serial_no,
       item_master.qty_on_hand,
       item_master.unit_cost,
       item_master.markup,
       item_master.retail_price,
       item_master_type.description item_type_descr,
       
       sales_order_items.order_qty,
       sales_order_items.back_order_qty,
       sales_order_items.init_unit_cost,
       sales_order_items.init_markup, 
       sales_order.customer_id,
       sales_order.invoiced,

       customer.person_id,
       customer.business_id,

	case
	      when customer.person_id is null then business.longname
	      when customer.business_id is null then person.lastname + ', ' + person.firstname
	      when customer.person_id is not null and customer.business_id is not null then business.longname + ' - ' + person.lastname + ', ' + person.firstname
        else 'Customer Name is unknown'
        end customer_name 

From sales_order_items, 
     sales_order,
     customer,
     person, 
     business,
     item_master,
     item_master_type
where sales_order.id = sales_order_items.sales_order_id 
  and sales_order.customer_id = customer.id 
  and customer.person_id *= person.id 
  and customer.business_id *= business.id
  and item_master.id = sales_order_items.item_master_id
  and item_master.item_type_id = item_master_type.id
go


