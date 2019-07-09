/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_vendor_items                                        */
/*==============================================================*/
create view vw_vendor_items as
select 
     vi.item_master_id, 
     vi.vendor_id, 
     vi.vendor_item_no, 
     vi.item_serial_no, 
     vi.unit_cost, 
     im.description, 
     im.qty_on_hand, 
     im.markup, 
     im.override_retail
from 
     vendor_items vi, 
     item_master im 
where 
     vi.item_master_id = im.id
  and im.override_retail = 1
  and im.vendor_id is not null
union all
select 
     vi.item_master_id, 
     vi.vendor_id, 
     im.vendor_item_no, 
     im.item_serial_no, 
     im.unit_cost,
     im.description, 
     im.qty_on_hand, 
     im.markup, 
     im.override_retail
from 
     vendor_items vi, 
     item_master im 
where 
     vi.item_master_id = im.id
  and im.override_retail = 0
go


