/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_verify_user_login
 *  Descrsiption: Authenticates a user's login
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_verify_user_login' AND type = 'P')
   DROP PROCEDURE usp_verify_user_login
GO

create procedure usp_verify_user_login @id int output,
                                       @employee_id int output,
                                       @login_id varchar(8),
                                       @description varchar(50) output,
                                       @password varchar(500),
                                       @total_logons int output as
                                                     
  begin
       declare @exist_cnt int
       declare @pw  varchar(500)
			       
       --  Verify that login existis
       select
              @id = id,
              @employee_id = employee_id,
              @login_id = login,
              @description = description,
	          @pw = password,
	          @total_logons = total_logons
	     from
	          user_login
	     where
	          login = @login_id

       --  Raise error if Login Id exist
       if @pw is null
          raiserror(50053, 18, 1, @login_id)
            
       --  Verify password is correct
       if @pw <> @password
		  raiserror(50054, 18, 1)     

       -- Login authenticated successfully!
       return
  end
go


