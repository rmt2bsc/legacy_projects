/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_chg_so_status
 *      Returns: none
 *  Descrsiption: Adds sales order status history.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_chg_so_status' AND type = 'P')
   DROP PROCEDURE usp_chg_so_status
GO

create procedure usp_chg_so_status @so_id int, 
																										                  @so_status_desc varchar(20)  as
  begin
    declare @new_stat_id int
    declare @cur_stat_hist_id int
    declare @cur_stat_id int

    --  Get current sales order status history key         
    select @cur_stat_hist_id = dbo.ufn_get_current_so_status_id(@so_id)
    if @cur_stat_hist_id is null
       raiserror(50027, 18, 1, @so_id)
		    
    --  Get destination sales order status id
    select @new_stat_id = dbo.ufn_get_so_status_id(@so_status_desc)
    if @new_stat_id = null
        --  Invalid sales order status description or name provided.
        raiserror(50028, 18, 1, @so_status_desc)
        
    --  Put an ending date to the most recent item status, which should be 'Available' or 'In Service'.
    update 
         sales_order_status_hist
    set
         end_date = getdate(),
         user_id = user
    where 
         id = @cur_stat_hist_id
      
    --  Update sales order status history with the desination
    insert into sales_order_status_hist
       (sales_order_id,
		sales_order_status_id,
		effective_date,
		end_date,
		date_created, 
		user_id)
    values
       (@so_id,
        @new_stat_id,
        getdate(),
        null,
        getdate(),
        user)     
  end
go


