/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_create_sales_invoice_xact
 *  Descrsiption: Create transaction and post individual items of the transaction to the General Ledger.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_create_sales_invoice_xact' AND type = 'P')
   DROP PROCEDURE usp_create_sales_invoice_xact
GO

create procedure usp_create_sales_invoice_xact @xact_id int output,
                                               @sales_order_id int  as
  begin
       declare @exist_cnt int
       declare @xact_amt  numeric(15,2)
       declare @xact_type_id int
       declare @xact_date datetime
       declare @xact_post_id int
       declare @acct_id int
       declare @item_id int
       declare @order_qty numeric(5,2)
	   declare @unit_cost numeric(11,2)
	   declare @markup numeric(5,2)


       declare cur_sales_order_items_to_invoice cursor for
           select
                 item_master_id,
                 order_qty,
                 init_unit_cost,
                 init_markup
           from
                 sales_order_items
           where
                 sales_order_id = @sales_order_id

           --  Verify that Sales Order  exists
       select
	        @exist_cnt = count(*)
	   from
	        sales_order
	   where
	        id = @sales_order_id

       if @exist_cnt <= 0
          raiserror(50021, 18, 1, @sales_order_id)

       --  Determine transaction amount
       select
            @xact_amt = isNull( sum( order_qty * (init_unit_cost * init_markup) ), 0)
       from
            sales_order_items
       where
            sales_order_id = @sales_order_id

       --  Get Transaction Type Id
       set @xact_type_id = dbo.ufn_get_xact_type_id('sales on account')
       if @xact_type_id = -100
          raiserror(50029, 18, 1, 'sales on account')

       -- Create transactaion
       exec usp_add_xact @xact_id output, @xact_date, @xact_amt, @xact_type_id

       -- Post target entry of transaction.
       set @acct_id =  dbo.ufn_get_account_id('accounts receivable')
       exec usp_add_xact_entry @xact_post_id,  @acct_id, @xact_id, @xact_amt

       --  Post offset entries of the transaction
       open  cur_sales_order_items_to_invoice
       fetch next from cur_sales_order_items_to_invoice into @item_id,	 @order_qty, @unit_cost, @markup

       while @@fetch_status = 0
         begin
            --  Get GL Account Id
            select
                 @acct_id = gla.id
            from
                 sales_order_items soi,
                 item_master im,
                 gl_accounts gla
            where
                  soi.item_master_id = @item_id
              and soi.item_master_id = im.id
              and im.gl_account_id = gla.id
              and soi.sales_order_id = @sales_order_id

            if @@rowcount <> 1
               raiserror(50030, 18, 1, @item_id, @sales_order_id)

            -- Calculate current offset amount
            set @xact_amt = @order_qty * (@unit_cost * @markup)
            exec usp_add_xact_entry @xact_post_id, @acct_id, @xact_id, @xact_amt

            -- Get next offset entry
		    fetch next from cur_sales_order_items_to_invoice into @item_id, @order_qty, @unit_cost,  @markup
		 end  --while
		 close cur_sales_order_items_to_invoice
		 deallocate cur_sales_order_items_to_invoice
   end
go


