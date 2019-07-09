/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_gen_code_grp
 *   Prototype: id int output
 *                   description varchar
 *      Returns: none
 *  Descrsiption: Deletes general code group entry
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_del_gen_code_grp' AND type = 'P')
   DROP PROCEDURE usp_del_gen_code_grp
GO

create procedure usp_del_gen_code_grp @id int as
  begin
      declare @user_id  varchar(25)   
      declare @exist_cnt int

      set @user_id = dbo.ufn_get_app_user_id()
      
       -- Verify that general code group is not associated with any general codes
      select 
           @exist_cnt = count(*) 
      from 
           general_codes
      where
           group_id = @id
           
     if @exist_cnt > 0
         raiserror(50058, 18, 1, @id)

      --  Update the genreal codes group 
      delete from
            general_codes_group
	  where
	        id = @id
  end
go


