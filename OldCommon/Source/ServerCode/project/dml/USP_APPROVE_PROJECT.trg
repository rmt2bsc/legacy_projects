/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: usp_approve_project
 *  Description: Attempts to approve the time of a project event.  Changes the status of the project to Submitted.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects  where  id = object_id('usp_approve_project')  and type in ('P'))
   drop procedure usp_approve_project
go

create procedure usp_approve_project @event_id int as

    begin
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
      if @event_stat <> @SUBMIT
          raiserror(50045, 18, 1)
  
      -- Change status to approved
      exec usp_chg_proj_event_status @event_id, @APPROVED              

       return
    end
go


