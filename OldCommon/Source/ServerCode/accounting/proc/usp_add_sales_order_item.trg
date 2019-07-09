/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_sales_order_item
 *  Descrsiption: Creates a new sales order item..
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_add_sales_order_item' AND type = 'P')
   DROP PROCEDURE usp_add_sales_order_item
GO

create procedure usp_add_sales_order_item @id int output,
										 @sales_order_id int,
										 @item_master_id int,
										 @order_qty numeric(8, 2),
										 @init_unit_cost numeric(15,2),
										 @init_markup numeric(5,2) as
  begin
       declare @exist_cnt int
       declare @qty_on_hand numeric(8,2)

           --  Verify that Master Item  exists
       select
	             @qty_on_hand = qty_on_hand,
	             @init_unit_cost = unit_cost,
	             @init_markup = markup
	     from
	             item_master
	     where
	             id = @item_master_id

        if @@rowCount <= 0
            raiserror(50019, 18, 1, @item_master_id)


          -- Add sales order
      insert into SALES_ORDER_ITEMS
		(sales_order_id,
		 item_master_id,
		 order_qty,
		 back_order_qty,
		 init_unit_cost,
		 init_markup)
	  values
		(@sales_order_id,
		 @item_master_id,
		 @order_qty,
		 null,
		 @init_unit_cost,
		 @init_markup)

    set @id = @@identity
  end
go


