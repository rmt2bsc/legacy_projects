/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_proj_event
 *      Returns: none
 *  Descrsiption: Inserts a row into the proj_event  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_proj_event' AND type = 'P')
   DROP PROCEDURE usp_add_proj_event
GO

create procedure usp_add_proj_event @id int output,
									 @effective_date datetime,
									 @end_date datetime,
									 @proj_emp_id int,
									 @total_hours numeric(5, 2) as
begin
      declare @user_id  varchar(25)  
      declare @DRAFT_STAT int
      
      
      set @user_id = dbo.ufn_get_app_user_id()
      set @DRAFT_STAT = 1
      
		 insert into proj_event
		   (effective_date,
			end_date,
			proj_employee_id,
			total_hours,
			date_created,
			date_updated,
			user_id)
		values
		   (@effective_date,
			@end_date,
			@proj_emp_id,
			@total_hours,
			getdate(),
			getdate(),
			@user_id)

		set @id = @@identity
		exec usp_chg_proj_event_status @id, @DRAFT_STAT
end
go


