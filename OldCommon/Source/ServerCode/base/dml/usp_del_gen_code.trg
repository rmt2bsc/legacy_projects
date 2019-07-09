/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_gen_code
 *   Prototype: id int 
 *      Returns: none
 *  Descrsiption: Deletes a genreal code row.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_del_gen_code' AND type = 'P')
   DROP PROCEDURE usp_del_gen_code
GO

create procedure usp_del_gen_code @id int as
  begin
      declare @user_id  varchar(25)   
      declare @exist_cnt int

      set @user_id = dbo.ufn_get_app_user_id()
      
     --  Verify that genreal code does not exist
     select
           @exist_cnt = count(*)
     from 
           person
     where
           @id in (gender_id, race_id, marital_status)
           
     if @exist_cnt > 0
        raiserror(50059, 18, 1, @id)
        
    select 
        @exist_cnt = count(*) 
    from 
        business 
    where 
        @id in (bus_type, serv_type)        

    if @exist_cnt > 0
        raiserror(50059, 18, 1, @id)        

      --  Delete general code from the database
      delete from 
          general_codes
	  where
	      id = @id
  end
go


