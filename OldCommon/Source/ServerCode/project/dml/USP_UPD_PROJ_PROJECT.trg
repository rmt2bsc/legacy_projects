/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_proj_project
 *      Returns: none
 *  Descrsiption: Modifies a row in the proj_project  table.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_proj_project' AND type = 'P')
   DROP PROCEDURE usp_upd_proj_project
GO

create procedure usp_upd_proj_project @id int ,
									 @proj_client_id int,
									 @description varchar(50),
									 @eff_date datetime,
									 @end_date datetime as
  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()

     update
            proj_project
     set
            proj_client_id = @proj_client_id,
		    description = @description,
		    effective_date = @eff_date,
		    end_date = @end_date,
    		date_updated = getdate(),
	    	user_id = @user_id
     where
            id = @id
  end
go


