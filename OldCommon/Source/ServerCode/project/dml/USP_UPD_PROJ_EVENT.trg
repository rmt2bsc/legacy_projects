/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_proj_event
 *      Returns: none
 *  Descrsiption: Modifies a row in the proj_event  table.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_proj_event' AND type = 'P')
   DROP PROCEDURE usp_upd_proj_event
GO

create procedure usp_upd_proj_event @id int ,
    								 @effective_date datetime,
    								 @end_date datetime,
    								 @proj_emp_id int,
    								 @total_hours numeric(5, 2) as
  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()

     update
           proj_event
	 set
 		   effective_date = @effective_date,
		   end_date = @end_date,
		   proj_employee_id = @proj_emp_id,
		   total_hours= @total_hours,
		   date_updated = getdate(),
		   user_id = @user_id
     where
           id = @id
     
  end
go


