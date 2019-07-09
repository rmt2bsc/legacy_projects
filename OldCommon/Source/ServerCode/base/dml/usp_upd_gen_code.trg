/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_gen_code
 *   Prototype: id int output
 *                   d@group_id int,
 *						  			 @shortdesc varchar(50),
 *						  			 @longdesc varchar(50),
 * 					  			 @gen_ind_value varchar(5)
 *      Returns: none
 *  Descrsiption: Updates a genreal code row.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_gen_code' AND type = 'P')
   DROP PROCEDURE usp_upd_gen_code
GO

create procedure usp_upd_gen_code @id int,
                                  @group_id int,
	  				  			  @shortdesc varchar(50),
					  			  @longdesc varchar(50),
					  			  @gen_ind_value varchar(5) as
  begin
      declare @user_id  varchar(25)   
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

      --  Update the database with modfied general code
      update 
            general_codes
      set
			group_id =@group_id,
			shortdesc = @shortdesc,
			longdesc = @longdesc,
			gen_ind_value = @gen_ind_value,
			date_updated = getdate(),
			user_id = @user_id
	  where
	        id = @id
  end
go


