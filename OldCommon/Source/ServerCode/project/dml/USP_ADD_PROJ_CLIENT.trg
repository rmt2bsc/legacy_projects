/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_proj_client
 *      Returns: none
 *  Descrsiption: Inserts a row into the proj_client  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_proj_client' AND type = 'P')
   DROP PROCEDURE usp_add_proj_client
GO

create procedure usp_add_proj_client @id int output,	 @business_id int as
  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()

     insert into proj_client
	   (business_id,
		date_created,
		date_updated,
		user_id)
      values
       (@business_id,
        getdate(),
        getdate(),
        @user_id)

    set @id = @@identity
  end
go


