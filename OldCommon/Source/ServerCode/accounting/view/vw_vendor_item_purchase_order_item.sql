/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_vendor_item_purchase_order_item                     */
/*==============================================================*/
create view vw_vendor_item_purchase_order_item as
select vvi.item_master_id, 
     vvi.vendor_id, 
     vvi.vendor_item_no, 
     vvi.item_serial_no, 
     vvi.unit_cost, 
     vvi.description, 
     vvi.qty_on_hand, 
     vvi.markup, 
     vvi.override_retail,
     poi.purchase_order_id,
     poi.qty qty_orderd,
     poi.unit_cost actual_unit_cost,
     poi.qty_rcvd qty_received,
     poi.qty_rtn qty_returned
from vw_vendor_items vvi,
     purchase_order_items poi
where vvi.item_master_id = poi.item_master_id
go


