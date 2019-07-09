/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_chg_proj_event_status
 *      Returns: none
 *  Descrsiption: Adds project event status history.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_chg_proj_event_status' AND type = 'P')
   DROP PROCEDURE usp_chg_proj_event_status
GO

create procedure usp_chg_proj_event_status @event_id int,
																										                       @new_status_id int  as
  begin
      declare @new_stat_id int
      declare @cur_stat_hist_id int
      declare @cur_stat_id int
  	  declare @user_id  varchar(25)      
	  declare @hist_count int
	   
 	  set @user_id = dbo.ufn_get_app_user_id()        

      select 
            @hist_count = count(*) 
      from 
            proj_event_stat_hist 
      where 
            proj_event_id = @event_id
               
      --  End current project event status
      update
           proj_event_stat_hist
      set
           end_date = getdate()
      where 
           proj_event_id = @event_id
       and end_date is null
       
       if @@rowcount <> 1 
          begin
			if @hist_count > 0
		       -- History exist and there should have been an entry available where the end date is null.
			   raiserror(50044, 18, 1)
			end

      -- Add status history          
       insert into proj_event_stat_hist
            (proj_event_id,
             proj_event_status_id,
             effective_date,
             end_date,
             user_id)
       values
            (@event_id,
             @new_status_id,
             getdate(),
             null,
             @user_id)
  end
go


