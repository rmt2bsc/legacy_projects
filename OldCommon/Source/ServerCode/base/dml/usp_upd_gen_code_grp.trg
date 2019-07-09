/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_gen_code_grp
 *   Prototype: id int output
 *                   description varchar
 *      Returns: none
 *  Descrsiption: Updates a row into the genreal codes group table. 
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_gen_code_grp' AND type = 'P')
   DROP PROCEDURE usp_upd_gen_code_grp
GO

create procedure usp_upd_gen_code_grp @id int,
																													 @description varchar(50) as
  begin
      declare @user_id  varchar(25)   

      set @user_id = dbo.ufn_get_app_user_id()
      
      -- Verify that description has a value
      if @description is null or len(@description) <= 0
         raiserror(50055, 18, 1)

      --  Update the genreal codes group 
      update
            general_codes_group
      set
			description = @description,
		    date_updated  = getdate(),
			user_id = @user_id
	  where
	        id = @id
					  
  end
go


