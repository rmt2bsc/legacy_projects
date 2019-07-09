/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_customer_activity
 *  Descrsiption: Creates customer transaction activity which reflects accounts receivable.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_customer_activity' AND type = 'P')
   DROP PROCEDURE usp_add_customer_activity
GO

create procedure usp_add_customer_activity @confirm_no varchar(20) output, 
                                           @customer_id int,
                                           @xact_id int, 
                                           @xact_amt numeric(15,2)  as
  begin
       declare @xact_date datetime
       declare @xact_cash_pay int
       declare @xact_acct_sales int
       declare @xact_type_id int
       
       set @xact_date = dbo.ufn_get_datepart(getdate())
       
       -- Get cash payment transaction code
       select
             @xact_cash_pay = id
         from
             xact_type
        where
             xact_code = 'cashsale'
            
            
       -- Verify that transaction is valid by attempting 
       -- to obtain its transaction type id
       select
             @xact_type_id = xact_type_id
         from
             xact
        where
             id = @xact_id
            
     
        --  Raise error if Transaction does not exist.
        if @xact_type_id is null or @xact_type_id <= 0 
            raiserror(50038, 18, 1, @xact_id)
  
        -- Determine if activity is an account sale or a cash payment.
        if @xact_type_id = @xact_cash_pay
          begin
            set @xact_amt = @xact_amt * -1
          end

        if @xact_type_id = @xact_acct_sales
          begin
            set @xact_amt = abs(@xact_amt)
          end
            
            
            
           -- Record cusomer activity
       insert into CUSTOMER_ACTIVITY
			(customer_id, 
             xact_id,
			 amount,
			 date_created, 
			 date_updated, 
			 user_id)
		values 
			(@customer_id,
			 @xact_id,
			 @xact_amt,
			 getdate(),
			 getdate(),
			 user)

        if @@error <> 0
           set @confirm_no = '-1'
           
        if @xact_type_id <> @xact_cash_pay and @xact_type_id <> @xact_acct_sales
           set @confirm_no = ''
          
        -- Assign confirmation number to transaction
        update xact set
             confirm_no = @confirm_no
        where 
             id = @xact_id 
          
                   
  end
go


