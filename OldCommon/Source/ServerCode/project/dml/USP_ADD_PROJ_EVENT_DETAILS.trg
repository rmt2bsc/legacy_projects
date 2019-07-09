/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_proj_event_details
 *      Returns: none
 *  Descrsiption: Inserts a row into the proj_event_details  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_proj_event_details' AND type = 'P')
   DROP PROCEDURE usp_add_proj_event_details
GO

create procedure usp_add_proj_event_details @id int output,
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
     
     insert into proj_event_details
	   (proj_event_id,
		proj_client_id,
		proj_project_id,
		proj_role_id,
		task_code,
		event_date,
		hours,
		date_created,
		date_updated,
		user_id)
      values
       (@proj_event_id,
        @proj_client_id,
        @proj_project_id,
        @proj_role_id,
        @task_code,
        @event_date,
        @hours,
        getdate(),
        getdate(),
        @user_id)

    set @id = @@identity
  end
go


