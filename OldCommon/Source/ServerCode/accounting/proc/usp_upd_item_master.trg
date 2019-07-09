/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_item_master
 *      Returns: none
 *  Descrsiption: Inserts a row into the item master  table.    Returns the following values to the caller via output 
 *                           parameter:   item master primary key as @id,  gl account id as @gl_account_id,
 *                           retail price as @retail_price, and active as @active
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_upd_item_master' AND type = 'P')
   DROP PROCEDURE usp_upd_item_master
GO

create procedure usp_upd_item_master @id int, 
									 @vendor_id int output, 
                                     @item_type_id int,
									 @vendor_item_descr varchar(80), 
									 @vendor_item_no varchar(25),
									 @item_serial_no varchar(25),
									 @qty_on_hand int,
									 @unit_cost numeric(15,2),
									 @markup numeric(5,2),
									 @retail_price numeric (15, 2) output,
                                     @override_retail bit,
									 @active bit output    as
  begin
      declare @exist_cnt int
      declare @item_stat_id int
      declare @cur_item_stat_hist_id int

      -- Verify vendor exists
      if @vendor_id > 0
        begin
          select
               @exist_cnt  = count(*)
           from
               creditor
           where 
               id = @vendor_id
              
          --  Raise error if Vendor is not found.
          if @exist_cnt is null or @exist_cnt <= 0
            raiserror(50016, 18, 1, @vendor_id)               
        end
      else
         begin
           set @vendor_id = null
         end
         
      --  Update item with changes to the database.
      update 
            ITEM_MASTER 
      set
            vendor_id = @vendor_id,
			description = @vendor_item_descr,
			vendor_item_no =@vendor_item_no,
            item_type_id = @item_type_id,
			item_serial_no = @item_serial_no,
			qty_on_hand = @qty_on_hand, 
            unit_cost = @unit_cost,
            markup = @markup,
            active = @active,
			retail_price = @retail_price,
            override_retail = @override_retail,
			date_updated = getdate(), 
			user_id = user
	  where 
            id = @id

    select @cur_item_stat_hist_id = dbo.ufn_get_current_item_status_id( @id )
    if @cur_item_stat_hist_id <= 0
		    raiserror(50024, 18, 1, @cur_item_stat_hist_id )
 
	 --  Otherwise, make changes to item status history.
     select @item_stat_id = dbo.ufn_get_item_status_id('Replaced')
    
     --  Change the most recent item status, which should be 'Replaced'
     update 
           ITEM_MASTER_STATUS_HIST 
     set
           item_status_id = @item_stat_id,
           end_date = getdate(),
           user_id = user
     where 
           id = @cur_item_stat_hist_id
      
    --   Place Item in "Available" if quantity is greater than zero.   Otherwise, 'Out of Stock'.
    if @qty_on_hand <= 0
       select @item_stat_id = dbo.ufn_get_item_status_id('Out of Stock')
    else
       select @item_stat_id = dbo.ufn_get_item_status_id('Available')
       
    if @active = 0 
       select @item_stat_id = dbo.ufn_get_item_status_id('Out of Service')
       
    --  Update Item Status History with new unit cost and/or markup.
     insert into ITEM_MASTER_STATUS_HIST
	   (item_master_id,
		item_status_id,
		unit_cost,
		markup,
		effective_date,
		end_date,
		date_created, 
		user_id)
     values
       (@id,
        @item_stat_id,
        @unit_cost,
        @markup,
        getdate(),
        null,
        getdate(),
        user)     
  end
go


