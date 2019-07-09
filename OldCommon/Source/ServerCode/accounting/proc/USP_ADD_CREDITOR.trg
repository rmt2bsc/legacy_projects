/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_creditor
 *      Returns: none
 *  Descrsiption: Inserts a row into the creditor table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_add_creditor' AND type = 'P')
   DROP PROCEDURE usp_add_creditor
GO

create procedure usp_add_creditor @id int output, 
                                 @gl_account_id int, 
                                 @business_id int, 
                                 @creditor_type_id int,
                                 @account_number varchar(25) output,
                                 @credit_limit numeric(15, 2),
                                 @apr numeric (6, 4) as
  begin
      declare @gl_acct_type int
      declare @exist_cnt int
      declare @LIABILITY_ACCT_TYPE int
      declare @cur_date  datetime
      declare @cur_mm varchar(3)
      declare @cur_dy varchar(3)
      declare @cur_yr varchar(4)
      
      
         --  Get Account Type for liabilities
    select @LIABILITY_ACCT_TYPE = dbo.ufn_get_account_type('liabilities')
    
          -- Ensure that GL Account Id is valid  
     if @gl_account_id is null or @gl_account_id <= 0 
        -- Try to retrieve gl account id
        select @gl_account_id = dbo.ufn_get_account_id('Accounts Payable')
        if @gl_account_id is null or @gl_account_id <= 0 
           -- error if gl account id is not obtainable
           raiserror(50004, 18, 1)
        
     select 
             @gl_acct_type = acct_type_id
     from
              gl_accounts
     where 
              id = @gl_account_id
          
          --  Raise error if GL Account is not found.
      if @@rowcount <= 0
          raiserror(50005, 18, 1, @gl_account_id)

          --  Verify that GL Account type is a Liability
      if @gl_acct_type <> @LIABILITY_ACCT_TYPE
          raiserror(50006, 18, 1)

           -- Validate Business ID
      if @business_id is null or @business_id <= 0 
          raiserror(50007, 18, 1)
          
             -- Verify that the business exist
      select
              @exist_cnt  = count(*)
       from
               business
       where 
               id = @business_id
          
          --  Raise error if Business is not found.
      if @exist_cnt <= 0
          raiserror(50008, 18, 1, @business_id)

           --  Validate Creditor Type Id
      if @creditor_type_id is null or @creditor_type_id <= 0
         raiserror(50009, 18, 1)
         
      -- Verify that Creditor Type exists
      select
               @exist_cnt = count(*)
       from
                creditor_type
       where 
                id = @creditor_type_id
                
       if @exist_cnt <= 0
           raiserror(50010, 18, 1)
      
      --  Build creditor's account number
      set @cur_date = getdate()
      set @cur_mm = cast(month(@cur_date) as varchar)
      set @cur_dy = cast(day(@cur_date) as varchar)
      set @cur_yr = cast(year(@cur_date) as varchar)
      if len(@cur_mm) = 1
          set @cur_mm = '0' + @cur_mm
      if len(@cur_dy) = 1
          set @cur_dy = '0' + @cur_dy
          
      set @account_number = cast(@business_id as varchar) + '-' + @cur_mm + @cur_dy + @cur_yr
      
           --  Add creditior to the database
      insert into CREDITOR
	   (gl_account_id,
        business_id, 
        creditor_type_id,
        account_number,
        credit_limit,
        apr, 
        active,
	    date_created, 
	    date_updated, 
	    user_id)
	  values 
	   (@gl_account_id,
        @business_id, 
        @creditor_type_id,
        @account_number,
        isNull(@credit_limit, 0),
        isNull(@apr, 0),
        1,
	    getdate(),
		getdate(),
		user)

    set @id = @@identity
  end
go


