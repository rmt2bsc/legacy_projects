/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_gen_code_grp
 *   Prototype: id int output
 *                   description varchar
 *      Returns: none
 *  Descrsiption: Inserts a row into the genreal codes group table.    Returns value of the primary key via @id output parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_gen_code_grp' AND type = 'P')
   DROP PROCEDURE usp_add_gen_code_grp
GO

create procedure usp_add_gen_code_grp @id int output,
																													 @description varchar(50) as
  begin
     declare @user_id  varchar(25)   
     declare @key int

     set @user_id = dbo.ufn_get_app_user_id()
      
     -- Verify that description has a value
     if @description is null or len(@description) <= 0
         raiserror(50055, 18, 1)

     select @key = max(id) from general_codes_group
     if @key is null 
        set @key = 1
     else
        set @key = @key + 1
         
     --  Update the database with the new general code group
     insert into general_codes_group
		( id,
		  description,
		  date_created,
		  date_updated,
		  user_id)
	 values
	  	 (@key,
		  @description,
		  getdate(),
		  getdate(),
		  @user_id)

     set @id = @key
  end
go


