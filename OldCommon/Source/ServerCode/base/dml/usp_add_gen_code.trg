/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_gen_code
 *   Prototype: id int output
 *                   description varchar
 *      Returns: none
 *  Descrsiption: Inserts a row into the genreal codes group table.    Returns value of the primary key via @id output parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_gen_code' AND type = 'P')
   DROP PROCEDURE usp_add_gen_code
GO

create procedure usp_add_gen_code @id int output,
                                  @group_id int,
					  			  @shortdesc varchar(50),
					  			  @longdesc varchar(50),
					  			  @gen_ind_value varchar(5) as
  begin
      declare @user_id  varchar(25)   
      declare @key int
      declare @exist_cnt int

      set @user_id = dbo.ufn_get_app_user_id()
      
     --  Verify that group id exist
     select
           @exist_cnt = count(*)
     from 
           general_codes_group
     where
           id = @group_id
           
     if @@rowcount <= 0
        raiserror(50057, 18, 1, @group_id)
        
       -- Verify that either short description and long description has a value
     if (@shortdesc is null or len(@shortdesc) <= 0) and (@longdesc is null or len(@longdesc) <= 0)
         raiserror(50056, 18, 1)

      select @key = max(id) from general_codes
      if @key is null 
         set @key = 1
      else
         set @key = @key + 1
         
      --  Update the database with the new general code group
      insert into general_codes
	  ( id,
 		group_id,
		shortdesc,
		longdesc,
		gen_ind_value,
	    date_created,
	    date_updated,
	    user_id)
	values
		(@key,
		 @group_id,
		 @shortdesc,
		 @longdesc,
		 @gen_ind_value,
		 getdate(),
		 getdate(),
		 @user_id)

     set @id = @key
  end
go


