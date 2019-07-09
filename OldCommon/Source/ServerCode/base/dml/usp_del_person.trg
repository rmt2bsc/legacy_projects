/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_person
 *  Descrsiption: Removes person from the database
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_del_person' AND type = 'P')
   DROP PROCEDURE usp_del_person
GO

create procedure usp_del_person @id int	 as

  begin
       declare @exist_cnt int
       declare @user_id  varchar(25)
       
       set @user_id = dbo.ufn_get_app_user_id()
       
       -- Verify that person does not exist as a customer
       select
             @exist_cnt = count(*)
       from
             customer
       where
             person_id = @id;
       
       if @exist_cnt > 0
          raiserror(50062, 18, 1, @id, 'Customer')
          
       -- Delete all occurrences of the person from the adddress table
       delete from 
             address
       where
             person_id = @id
             
       -- Delete business from the business table
       delete from 
             person
       where
             id = @id       
  end
go


