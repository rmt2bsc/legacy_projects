/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_invoice_sales_order
 *  Descrsiption: Invoices a sales order.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_invoice_sales_order' AND type = 'P')
   DROP PROCEDURE usp_invoice_sales_order
GO

create procedure usp_invoice_sales_order @invoice_id int output, 
                                         @xact_id int,
                                         @sales_order_id int  as
  begin
    declare @exist_cnt int
    declare @invoice_number varchar(20)
    declare @date_number varchar(20)
    declare @MERCH_ITEM_TYPE int
    declare @item_id int
	declare @order_qty numeric(5,2)
	declare @unit_cost numeric(11,2)
	declare @markup numeric(5,2)
	declare @qty_on_hand numeric(12,2)
	declare @back_order_qty numeric(12,2)
	declare @cur_item_stat_hist_id int
	declare @out_of_stock_stat_id int
	declare @OUT_OF_STOCK  varchar(20)
	declare @INVOICED  varchar(10)
        declare @msg  varchar(100)
   
    set @MERCH_ITEM_TYPE = 2   
  	declare cur_sales_order_items cursor for
		select
			  item_master_id,
			  order_qty,
			  init_unit_cost,
			  init_markup
		from
			  sales_order_items,
              item_master
		where 
			  sales_order_id = @sales_order_id
          and sales_order_items.item_master_id = item_master.id
          and item_master.item_type_id = @MERCH_ITEM_TYPE
      
    set @OUT_OF_STOCK = 'Out of Stock'
    set @INVOICED = 'Invoiced'
    
      
    --  Verify that Transaction exists
    select
          @exist_cnt = count(*)
    from
          xact
    where
          id = @xact_id      
      
    if @exist_cnt <= 0 
       raiserror(50038, 18, 1, @xact_id)
      
      
    --  Verify that Sales Order exists
    select
          @exist_cnt = count(*)
    from
          sales_order
    where
          id = @sales_order_id

    if @exist_cnt <= 0 
       raiserror(50021, 18, 1, @sales_order_id)

    --  Build Sales Invoice number in the format of <slaes order id> - <MMDDYYYY>
    set @date_number = dbo.ufn_convert_date_to_num(getdate())
    set @invoice_number = cast(@sales_order_id as varchar) + '-' + @date_number
      

/*
    -- Create Transaction
    
    exec usp_create_sales_invoice_xact @xact_id output, @sales_order_id
   if @xact_id is null or @xact_id <= 0
      raiserror(50001, 18, 1, @sales_order_id)
*/    
   -- Create Sales Invoice.
   insert into SALES_INVOICE
     (sales_order_id,
      invoice_no,
      xact_id,
      date_created,
      date_updated,
      user_id)
   values 
     (@sales_order_id,
      @invoice_number,
      @xact_id,
      getdate(),
      getdate(),
      user)
       
   set @invoice_id = @@identity
    
   --  Ensure that there is adequate quantity on hand for each sales order item.    It not,
   --  create a back order amount for item.
   open  cur_sales_order_items 
   fetch next from cur_sales_order_items into @item_id, @order_qty, @unit_cost, @markup
   while @@fetch_status = 0
	 begin
	   --  Get Quantity on Hand for current item from item master table
	   select
			 @qty_on_hand = qty_on_hand
	   from 
			 item_master
	   where
	    	 id = @item_id

       if @@rowcount <> 1 
		  raiserror(50037, 18, 1, @item_id)

	   --  Compare order qty of item to actual qty on hand of item master to determine if item 
	   --  back order is required.   If item quantity is insufficient and back order is required, calculate 
	   --  back order quantity amount and adjust order qauantity to the quantity available for sale.
	   if @order_qty > @qty_on_hand
	      begin
			set @back_order_qty = @order_qty - @qty_on_hand
			set @order_qty = @qty_on_hand

			-- Adjust sales order item
			update 
    			  sales_order_items 
			set
				  order_qty = @order_qty,
				  back_order_qty = @back_order_qty
			where
			      sales_order_id = @sales_order_id
	  		  and item_master_id = @item_id
	      end
	    else
		  begin
		    set @back_order_qty = 0
          end
  
		--  Adjust Item Master Inventory Quantity for the current sales order item.
        set @msg = 'Preparing to update quantiy on hand for item ' + cast(@item_id as varchar)
        exec usp_log_message @msg, 1
		update 
		  	   item_master
	    set
			   qty_on_hand = qty_on_hand - @order_qty
   	    where
			   id = @item_id          

        --  Change status of current item to 'Out of Stock' if value is something otherwise.
        if @qty_on_hand <= 0
          begin
            select @cur_item_stat_hist_id = dbo.ufn_get_current_item_status(@item_id)
            select @out_of_stock_stat_id = dbo.ufn_get_item_status_id(@OUT_OF_STOCK)
            if @cur_item_stat_hist_id <> @out_of_stock_stat_id
               exec usp_chg_item_status @item_id, @OUT_OF_STOCK
          end
                  
	 	  -- Get next offset entry
		  fetch next from cur_sales_order_items into @item_id, @order_qty, @unit_cost, @markup

	end  --while
	close cur_sales_order_items
	deallocate cur_sales_order_items
    
/*
        --  Post transaction.
    exec usp_post_xact @xact_id
*/

    set @msg = 'Invoicing Sales Order from stored proc ' + cast(@sales_order_id as varchar)
    exec usp_log_message @msg, 1
        --  Flag sales order as invoiced.    
    update 
           sales_order
    set
           invoiced = 1,
           user_id = user,
           date_updated = getdate()
    where
           id = @sales_order_id           

    set @msg = 'Total number of sales orders Invoiced: ' + cast(@@rowcount as varchar)
    exec usp_log_message @msg, 1
       --  Update sales order status to 'Inoviced'    
    exec usp_chg_so_status @sales_order_id, @INVOICED
  end
go


