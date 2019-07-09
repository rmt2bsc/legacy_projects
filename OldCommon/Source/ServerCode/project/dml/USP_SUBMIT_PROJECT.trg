/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: usp_submit_project
 *  Description: Calculate the total billable hours for a period (Year and Month) of a project, and changes
 *                      the status of the project to Submitted.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects  where  id = object_id('usp_submit_project')  and type in ('P'))
   drop procedure usp_submit_project
go

create procedure usp_submit_project @event_id int as

		begin
          declare @total_hours numeric(5, 2)               
          declare @event_stat int
          declare @SUBMIT int
          declare @APPROVED int
          declare @user_id  varchar(25)      
					      
		  set @user_id = dbo.ufn_get_app_user_id()
          set @SUBMIT = 2
          set @APPROVED = 4
   
          --   Get current status of project event.
          set @event_stat =  dbo.ufn_get_cur_proj_event_status_id(@event_id)
          if @event_stat <= 0
              raiserror(50041, 18, 1, @event_id)

          --  Project cannot be submitted if curretn status is "Approved"
          if @event_stat = @APPROVED
              raiserror(50043, 18, 1)
          
          exec usp_calc_proj_hours @event_id, @total_hours output

           --  Update the total hours for the event in the proj_event table
		   update
				 proj_event
		   set
 			     total_hours= @total_hours,
				 date_updated = getdate(),
				 user_id = @user_id
		   where
			     id = @event_id
          
          -- Change status to submitted
          exec usp_chg_proj_event_status @event_id, @SUBMIT              

           return
		end
go


