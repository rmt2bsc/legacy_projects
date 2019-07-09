/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_business
 *  Descrsiption: Removes business brom the database
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_del_business' AND type = 'P')
   DROP PROCEDURE usp_del_business
GO

create procedure usp_del_business @id int	 as

  begin
       declare @exist_cnt int
       declare @user_id  varchar(25)
       
       set @user_id = dbo.ufn_get_app_user_id()
       
       -- Verify that business does not exist as a creditor
       select
             @exist_cnt = count(*)
       from
             creditor
       where
             business_id = @id;
       
       if @exist_cnt > 0
          raiserror(50060, 18, 1, @id, 'Creditor')
          
       -- Verify that business does not exist as a customer
       select
             @exist_cnt = count(*)
       from
             customer
       where
             business_id = @id;
       
       if @exist_cnt > 0
          raiserror(50060, 18, 1, @id, 'Customer')
          
       -- Delete all occurrences of the business from the adddress table
       delete from 
             address
       where
             business_id = @id
             
       -- Delete business from the business table
       delete from 
             business
       where
             id = @id       
  end
go


