/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_user_login
 *  Descrsiption: Adds an user login
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_add_user_login' AND type = 'P')
   DROP PROCEDURE usp_add_user_login
GO

create procedure usp_add_user_login @id int output,
                                     @employee_id int,
                                     @login_id varchar(8),
                                     @description varchar(30),
                                     @password varchar(500) as
                                     
  begin
      declare @exist_cnt int
      declare @user_id  varchar(25)
			       
      set @user_id = dbo.ufn_get_app_user_id()

      --  Verify that login is new
      select
	         @exist_cnt = count(*)
	  from
	         user_login
	  where
	         login = @login_id

      --  Raise error if Login Id exist
      if @exist_cnt is not null and @exist_cnt > 0
         raiserror(50051, 18, 1, @login_id)
            
      --  Verify that employee_id is valid provided that the value is not null
      if @employee_id is not null and @employee_id > 0
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


      insert into user_login
		(employee_id,
		 login,
		 description,
		 password,
                active,
		 date_created,
		 date_updated,
		 user_id)
	  values
		(@employee_id,
		 @login_id,
		 @description,
		 @password,
                1,
		 getdate(),
		 getdate(),
		 @user_id)

    set @id = @@identity
  end
go


