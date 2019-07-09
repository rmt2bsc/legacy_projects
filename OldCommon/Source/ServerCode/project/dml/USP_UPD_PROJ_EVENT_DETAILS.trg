/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_proj_event_details
 *      Returns: none
 *  Descrsiption: Modifies a row in the proj_event_details  table.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_upd_proj_event_details' AND type = 'P')
   DROP PROCEDURE usp_upd_proj_event_details
GO

create procedure usp_upd_proj_event_details @id int,
											 @proj_event_id int,
											 @proj_client_id int,
											 @proj_project_id int,
											 @proj_role_id int,
											 @task_code varchar(20),
											 @event_date datetime,
											 @hours numeric(5, 2) as
  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()

     update 
           proj_event_details
     set
    	   proj_event_id = @proj_event_id,
   		   proj_client_id = @proj_client_id,
    	   proj_project_id = @proj_project_id,
    	   proj_role_id = @proj_role_id,
    	   task_code = @task_code,
    	   event_date = @event_date,
    	   hours = @hours,
    	   date_updated = getdate(),
    	   user_id = @user_id
	 where
	       id = @id
  end
go


