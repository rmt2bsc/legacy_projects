/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_post_xact_entry
 *      Returns: none
 *  Descrsiption: Posts the amount of the target entry or one of the offset entries to the associated GL Account.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_post_xact_entry' AND type = 'P')
   DROP PROCEDURE usp_post_xact_entry
GO

create procedure usp_post_xact_entry @id int output, 
                                     @gl_account_id int, 
                                     @xact_id int, 
                                     @amount numeric(15, 2)  as
  begin
      declare @exist_cnt int
      declare @gl_acct_type_id int
      declare @xact_type_id int
      declare @xact_cat_id int
      declare @to_mltplr int
      declare @from_mltplr int
      declare @to_acct_type_id int
      declare @from_acct_type_id int
      declare @post_amount numeric(15,2)
      declare @acct_period varchar(15)
      declare @acct_period_type_id int
      declare @post_date datetime
      declare @str_post_amount varchar(30)
      
    
      set @post_date = dbo.ufn_get_datepart(getdate())
      set @str_post_amount = cast(@amount as varchar)
      
          -- Ensure that GL Account Id is valid  
      select
          @gl_acct_type_id = acct_type_id
      from
          gl_accounts
      where 
          id = @gl_account_id
            
      if @@rowcount <= 0
         raiserror(50001, 18, 2)
/*
      -- Get transaction type id
      select
          @xact_type_id = xact_type_id  
      from 
          xact
      where 
          id = @xact_id
             
      if @@rowcount <= 0
         raiserror(50001, 18, 1)
      --  Get Transaction Type details
      select
          @xact_cat_id = xact_category_id,
          @to_mltplr = to_multiplier,
          @from_mltplr = from_multiplier,
          @to_acct_type_id = to_acct_type_id,
          @from_acct_type_id = from_acct_type_id
      from
          xact_type
      where
          id = @xact_type_id
              
      if @@rowcount <= 0
         raiserror(50001, 18, 1)
             
      --  Determine the multiplier that is to be applied to the entry's amount.
 	  if @to_acct_type_id = @gl_acct_type_id
        -- We are dealing with the target Entry of the transaction.
        begin
          if @to_mltplr is not null
             set @post_amount = @amount * @to_mltplr
           else
             -- Transaction Posting Failed!: The target multiplier for Transactoin Type, %d,
             -- is not properly configured.  GL Account Id - %d, Post Amount - %d
             raiserror(50063, 18, 1, @xact_type_id, @gl_account_id, @str_post_amount)  
        end
     
	  
	  if @from_acct_type_id = @gl_acct_type_id
        -- We are dealing with an offset Entry of the transaction.
        begin
          if @from_mltplr is not null
             set @post_amount = @amount * @from_mltplr
           else
             -- Transaction Posting Failed!: The Offset multiplier for Transactoin Type, %d, is not properly configured.  GL Account Id - %d, Post Amount - %s
             raiserror(50064, 18, 1, @xact_type_id, @gl_account_id, @str_post_amount)  
        end                         
     
	  if @gl_acct_type_id <> @to_acct_type_id and @gl_acct_type_id <> @from_acct_type_id
        -- Transaction Posting Failed!: GL Account Type failed to match any of the account types configured for selected Transaction Type %d, GL Account ID %d, Post Amount %s
	    raiserror(50065, 18, 1, @xact_type_id, @gl_account_id, @str_post_amount)  
*/

      -- initialize post amount since the above code is commented out.
      set @post_amount = @amount
      
      --  Get current accounting period
      exec usp_get_cur_acct_prd @acct_period output
    
      --  Get current accounting period type id
      select
          @acct_period_type_id = acct_prd_type_id
      from 
          acct_pref
      where 
          id = 1
    
      if @@rowcount <> 1 
         raiserror(50001, 18, 1)
    
           -- Post entry to General Ledger
      insert into XACT_POSTING
            (gl_account_id,
              xact_id,
              period,
              period_type_id,
              post_amount,
              post_date,
              date_created,
              date_updated,
              user_id)
      values
            (@gl_account_id,
             @xact_id,
             @acct_period,
             @acct_period_type_id,
             @post_amount,
             null,
             getdate(),
             getdate(),
             user)
    
      if @@error <> 0 
          raiserror(50001, 18,1)
      
      if @@rowcount > 0
         -- Get the primary key of the posted entry.
         set @id = @@identity
      else
         set @id = null    
  end
  
go


