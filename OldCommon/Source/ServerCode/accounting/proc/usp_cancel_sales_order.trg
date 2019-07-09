/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_cancel_sales_order
 *  Descrsiption: Cancels a sales order.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_cancel_sales_order' AND type = 'P')
   DROP PROCEDURE usp_cancel_sales_order
GO

create procedure usp_cancel_sales_order @so_id int,  @rc int output as
  begin
       declare @upd_cnt int
       declare @invoiced_ind int
       declare @item_id int
       declare @ord_qty numeric
       declare @back_order_qty numeric
       declare @so_stat_id int
       declare @xact_id int
       declare @new_xact_type_id int
       
       declare cur_so_items cursor for
           select
                  item_master_id,
                  order_qty,
                  back_order_qty
            from
                  sales_order_items
            where 
                  sales_order_id = @so_id
       
           --  Verify that Sales Order exists
       select
	         @invoiced_ind = invoiced
	     from
	         sales_order
	     where
	         id = @so_id

        if @@rowcount <= 0 
            raiserror(50021, 18, 1, @so_id)

           --  Sales Order must be invoiced before cancelling.
        if @invoiced_ind = 0
            raiserror(50022, 18, 1)

            --  Process all items of the sales order.       
        set @upd_cnt = 0
        open cur_so_items
        fetch next from cur_so_items into @item_id, @ord_qty, @back_order_qty

       while @@fetch_status = 0
           begin
			 update 
				 item_master
			 set
				 qty_on_hand = isNull(qty_on_hand, 0) + isNull(@ord_qty, 0) + isNull(@back_order_qty, 0)
			 where
				 id = @item_id

			 set @upd_cnt = @upd_cnt + @@rowcount
			 fetch next from cur_so_items into @item_id, @ord_qty, @back_order_qty
           end
        close cur_so_items
        deallocate cur_so_items

        exec usp_chg_so_status @so_id, 'Cancelled'
        
           -------------------------------------------------------------
           --   Let's begin reversing the transaction that is
           --   associated with the sales invoice.
           -------------------------------------------------------------
           
           --  Get transaction Id from sales invoice table.
        select
              @xact_id = xact_id 
        from
              sales_invoice
        where
              sales_order_id = @so_id

        if @@rowcount <= 0 
            raiserror(50023, 18, 1)              
        
            --   Call stored proc to reverse the transaction
        set @new_xact_type_id = dbo.ufn_get_xact_type_id('Sales on Account Reversal')
        exec usp_reverse_xact @xact_id, @new_xact_type_id
        
        return
  end
go


