/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create function ufn_get_sales_order_item_total (@sales_order_id int)
RETURNS numeric(11,2)

begin

    declare @item_amount numeric (11, 2)

    select 
          @item_amount = sum( isnull(soi.order_qty, 0) * (isnull(im.unit_cost, 0) * isnull(im.markup, 0)) ) 
    from 
          sales_order_items soi, 
          item_master im
    where 
          soi.item_master_id = im.id
      and soi.sales_order_id = @sales_order_id
  
    return @item_amount 
  
end
go


