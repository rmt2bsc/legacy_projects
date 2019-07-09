/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_customer
 *      Returns: none
 *  Descrsiption: Inserts a row into the customer table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_customer' AND type = 'P')
   DROP PROCEDURE usp_add_customer
GO

create procedure usp_add_customer @id int output,
                                 @gl_account_id int,
                                 @person_id int,
                                 @business_id int,
                                 @credit_limit numeric(15, 2),
                                 @acct_no varchar(30) as
  begin
      declare @gl_acct_type int
      declare @exist_cnt int
      declare @ASSET_ACCT_TYPE int
      declare @ACCT_RECV_ID int
      declare @user_id  varchar(25)  
      declare @customer_type_uid int  
      declare @cur_date  datetime
      declare @cur_mm varchar(3)
      declare @cur_dy varchar(3)
      declare @cur_yr varchar(4)  
      
      set @user_id = dbo.ufn_get_app_user_id()

    if @person_id <= 0
       set @person_id = null
    if @business_id <= 0
       set @business_id = null

         --  Get Account Type for Assets
    select @ASSET_ACCT_TYPE = dbo.ufn_get_account_type('assets')
    select @ACCT_RECV_ID = dbo.ufn_get_account_id('accounts receivable')

          -- Ensure that GL Account Id is valid
--     if @gl_account_id is null or @gl_account_id <= 0
--        raiserror(50004, 18, 1)

          --  Verify that gl_account_id is of Accounts Receivable
--     if @ACCT_RECV_ID <> @gl_account_id
--         raiserror(50012, 18, 1, 'Accounts Receivables', @ACCT_RECV_ID, @gl_account_id)

      if @person_id is null and @business_id is null
         raiserror(50013, 18, 1)
         
      if @person_id is not null and @business_id is not null
         raiserror(50061, 18, 1)
      
           --  Validate Person Id
      if @person_id is not null
         begin
    		select
    			@exist_cnt = count(*)
    		from
    		    person
    		where
    			id = @person_id

    		--  Raise error if Person is not found.
    		if @exist_cnt <= 0
    		  raiserror(50014, 18, 1, @person_id)
    	 end

		           -- Validate Business Id
      if @business_id is not null
          begin
			select
				@exist_cnt  = count(*)
        	from
			    business
			where
			    id = @business_id

			--  Raise error if Business is not found.
			if @exist_cnt <= 0
			  raiserror(50015, 18, 1, @business_id)
		  end
          
          
      --  Build customer's account number.  Account number will contain either 
      --  the business_id or person_id whichever is greater that zero.
      set @cur_date = getdate()
      set @cur_mm = cast(month(@cur_date) as varchar)
      set @cur_dy = cast(day(@cur_date) as varchar)
      set @cur_yr = cast(year(@cur_date) as varchar)
      if len(@cur_mm) = 1
          set @cur_mm = '0' + @cur_mm
      if len(@cur_dy) = 1
          set @cur_dy = '0' + @cur_dy
          
      if @business_id > 0
         set @customer_type_uid = @business_id
      if @person_id > 0
         set @customer_type_uid = @person_id
         
      set @acct_no = cast(@customer_type_uid as varchar) + '-' + @cur_mm + @cur_dy + @cur_yr          

      --  Add customer to the database
      insert into CUSTOMER
		(gl_account_id,
         account_no,
         person_id,
         business_id,
         credit_limit,
         active,
	     date_created,
		 date_updated,
		 user_id)
	  values
		(@ACCT_RECV_ID,
         @acct_no,
         @person_id,
         @business_id,
         isNull(@credit_limit, 0),
         1,
		 getdate(),
		 getdate(),
		 @user_id)

    set @id = @@identity
  end
go


