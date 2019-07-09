/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_chg_item_status
 *      Returns: none
 *  Descrsiption: Changes the status of an item.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_chg_item_status' AND type = 'P')
   DROP PROCEDURE usp_chg_item_status
GO

create procedure usp_chg_item_status @item_id int, 
																												                 @item_status_desc varchar(20)  as
  begin
      declare @item_stat_id int
      declare @cur_item_stat_hist_id int
      declare @cur_item_stat_id int
      declare @old_unit_cost numeric(15,2)
      declare @old_markup numeric(5,2)


           --  Get current item master status history key         
      select @cur_item_stat_hist_id = dbo.ufn_get_current_item_status_id( @item_id )
      if @cur_item_stat_hist_id <= 0
  		    raiserror(50024, 18, 1, @item_id)
		    
		    --  Get current status data for item
	  select 
	        @cur_item_stat_id = item_status_id,
            @old_unit_cost = unit_cost,
            @old_markup = markup
      from
            ITEM_MASTER_STATUS_HIST
      where
            id = @cur_item_stat_hist_id
             
     if @@rowcount <> 1
        --  Current status history could not be obtained for item.
	    raiserror(50025, 18, 1, @item_id, @cur_item_stat_hist_id)             
		      
	--  Get destination item status id
    select @item_stat_id = dbo.ufn_get_item_status_id(@item_status_desc)
    if @item_stat_id = null
       --  Invalid item status description or name provided.
       raiserror(50026, 18, 1, @item_status_desc, @item_id)
        
    --  Put an ending date to the most recent item status, which should be 'Available' or 'In Service'.
    update 
         ITEM_MASTER_STATUS_HIST 
    set
         end_date = getdate(),
         user_id = user
    where 
         id = @cur_item_stat_hist_id
      
    --  Update Item Status History with the desination
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
       (@item_id,
        @item_stat_id,
        @old_unit_cost,
        @old_markup,
        getdate(),
        null,
        getdate(),
        user)     
  end
go


