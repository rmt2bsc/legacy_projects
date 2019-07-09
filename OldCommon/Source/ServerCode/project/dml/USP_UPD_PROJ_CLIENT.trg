/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_proj_client
 *      Returns: none
 *  Descrsiption: Modifies a row in the proj_client table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_proj_client' AND type = 'P')
   DROP PROCEDURE usp_upd_proj_client
GO

create procedure usp_upd_proj_client @id int,
                                     @business_id int as
  begin
     declare @user_id  varchar(25)      
      
     set @user_id = dbo.ufn_get_app_user_id()

     update 
           proj_client
     set
		   business_id = @business_id,
		   date_updated = getdate(),
		   user_id = @user_id
	 where
	       id = @id
  end
go


