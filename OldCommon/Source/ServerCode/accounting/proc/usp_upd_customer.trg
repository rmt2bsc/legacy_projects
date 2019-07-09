/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_customer
 *      Returns: none
 *  Descrsiption: Updates a row into the customer table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_customer' AND type = 'P')
   DROP PROCEDURE usp_upd_customer
GO

create procedure usp_upd_customer @id int,
                                  @person_id int,
                                  @business_id int,
                                  @credit_limit numeric(15, 2) as
  begin
      declare @exist_cnt int
      declare @ASSET_ACCT_TYPE int
      declare @ACCT_RECV_ID int
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()       

	  if @person_id <= 0
		 set @person_id = null
	  if @business_id <= 0
		 set @business_id = null

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

      -- Update customer to the database
      update 
          CUSTOMER 
      set
		  person_id = @person_id,
		  business_id = @business_id,
		  credit_limit = @credit_limit,
		  date_updated = getdate(),
		  user_id = @user_id
      where 
          id = @id

  end
go


