/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_customer_activity
 *  Descrsiption: Creates creditor transaction activity which reflects accounts payable.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_creditor_activity' AND type = 'P')
   DROP PROCEDURE usp_add_creditor_activity
GO

create procedure usp_add_creditor_activity @confirm_no varchar(20) output, 
                                           @creditor_id int,
                                           @xact_id int, 
                                           @xact_amt numeric(15,2)  as
  begin
       declare @xact_date datetime
       declare @xact_cash_pay int
       declare @xact_acct_sales int
       declare @xact_type_id int
       declare @xact_reverse int
       declare @xact_purchase int
       declare @msg varchar(1000)
       
       set @xact_date = dbo.ufn_get_datepart(getdate())
       
       set @xact_reverse = 999;
       set @xact_purchase = 130;
       --set @xact_amt = abs(@xact_amt)
       
       -- Get cash payment transaction code
       select
             @xact_cash_pay = id
         from
             xact_type
        where
             xact_code = 'cashpaylib'
            
            
       -- Verify that transaction is valid by attempting 
       -- to obtain its transaction type id
       select
             @xact_type_id = xact_type_id
         from
             xact
        where
             id = @xact_id
            
        set @msg = 'Preparing to create creditor activity entry. Transaction Type for trasaction ' + cast(@xact_id as varchar) + ' is ' + cast(@xact_type_id as varchar) + '.  '
        
     
        --  Raise error if Transaction does not exist.
        if @xact_type_id is null or @xact_type_id <= 0 
            raiserror(50038, 18, 1, @xact_id)
  /*
        -- By default treat amount as if it is a reversal or a payment towards the account. 
        if @xact_type_id = @xact_reverse or @xact_type_id = @xact_cash_pay
          begin
            set @xact_amt = @xact_amt * -1
            set @msg = @msg + 'Creditor activity is found to be a reversal or payment.  ' 
          end

        -- If activity is purchase of inventory, ensure that the amount is always positive.
        if @xact_type_id = @xact_purchase
          begin
            set @msg = @msg + 'Creditor activity is found to be a purchase.  ' 
            set @xact_amt = abs(@xact_amt)
          end
    */        
            
       set @msg = @msg + 'Activity amount to be posted: '  + cast(@xact_amt as varchar)
       
       -- Record creditor activity
       insert into CREDITOR_ACTIVITY
			(creditor_id, 
             xact_id,
			 amount,
			 date_created, 
			 date_updated, 
			 user_id)
		values 
			(@creditor_id,
			 @xact_id,
			 @xact_amt,
			 getdate(),
			 getdate(),
			 user)

        if @@error <> 0
           set @confirm_no = '-1'
           
        if @xact_type_id <> @xact_cash_pay and @xact_type_id <> @xact_reverse
           set @confirm_no = ''
          
        -- Assign confirmation number to transaction
        update xact set
             confirm_no = @confirm_no
        where 
             id = @xact_id 
          
        exec usp_log_message @msg, 1                       
  end
go


