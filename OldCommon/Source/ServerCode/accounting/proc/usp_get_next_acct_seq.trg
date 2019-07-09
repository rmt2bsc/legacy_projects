/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *       Method: usp_get_next_acct_seq
 *   Prototype: int (Account Type Id)
 *                      int (Account Category Id)
 *                      int (New Account Id) output
 *      Returns: none
 *  Description: Locates a Gl Account Category and packages the account data into a GlAccountCategory object
 *                         where the the category's description matches exactly "value".
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_get_next_acct_seq' AND type = 'P')
   DROP PROCEDURE usp_get_next_acct_seq
GO

create procedure usp_get_next_acct_seq  @acct_type_id int, 
                                        @acct_catg_id int,
                                        @seq_no int output as
  declare @acct_seq int
      
  begin
      if @acct_type_id <= 0
         raiserror(50001, 18, 1, 'GL Account Type cannot be blank')
           
      if @acct_catg_id <= 0
         raiserror(50001, 18, 1, 'GL Account Category cannot be blank')
    
      select 
            @acct_seq = max(acct_seq) 
      from 
            gl_accounts 
      where 
            acct_type_id = @acct_type_id
        and acct_cat_id = @acct_catg_id
      
      if @acct_seq is null 
         set  @acct_seq = 1
      else
         set @acct_seq = @acct_seq  + 1

      set @seq_no = @acct_seq          
                
  end
  
go


