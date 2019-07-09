/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_proj_project
 *      Returns: none
 *  Descrsiption: Inserts a row into the proj_project  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_proj_project' AND type = 'P')
   DROP PROCEDURE usp_add_proj_project
GO

create procedure usp_add_proj_project @id int output,
									 @proj_client_id int,
									 @description varchar(50),
									 @eff_date datetime,
									 @end_date datetime as
  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()

     insert into proj_project
	   (proj_client_id,
	    description,
		effective_date,
		end_date,
		date_created,
		date_updated,
		user_id)
      values
       (@proj_client_id,
        @description,
        @eff_date,
        @end_date,
        getdate(),
        getdate(),
        @user_id)

    set @id = @@identity
  end
go


