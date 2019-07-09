/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_user_login
 *  Descrsiption: Modifies a user's lign profile
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_upd_user_login' AND type = 'P')
   DROP PROCEDURE usp_upd_user_login
GO

create procedure usp_upd_user_login @id int output,
                                     @employee_id int,
                                     @login_id varchar(8),
                                     @description varchar(30),
                                     @password varchar(500),
                                     @total_logons int as
                                                     
  begin
      declare @exist_cnt int
      declare @orig_password varchar(500)
      declare @user_id  varchar(25)
			       
      set @user_id = dbo.ufn_get_app_user_id()

      --  Verify that login existis
      select
	        @orig_password = password
	  from
	        user_login
	  where
	        login = @login_id

      --  Raise error if Login Id exist
      if @@rowcount <= 0
         raiserror(50053, 18, 1, @login_id)
            
            
      --  Verify that employee_id is valid provided that the value is not null
      if @employee_id is not null  and @employee_id > 0
        begin
		  select
				@exist_cnt = count(*)
		  from
			    proj_employee
		  where
			    id = @employee_id

		  --  Raise error if employee Id does not exist
		  if @exist_cnt is null or @exist_cnt = 0
			 raiserror(50052, 18, 1, @employee_id)     
	    end

      -- Determine if password will be changeing
      if @password  = '!@#$%^&*'
         -- Password is not changing.
         set @password = @orig_password
         
      -- Update user login profile   
      update 
          user_login 
      set
		  employee_id = @employee_id,
		  login = @login_id,
		  description = @description,
		  password = @password,
		  total_logons = @total_logons,
		  date_updated = getdate(),
		  user_id = @user_id
      where
          id = @id
  end
go


