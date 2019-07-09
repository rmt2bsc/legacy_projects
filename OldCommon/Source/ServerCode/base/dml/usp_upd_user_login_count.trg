/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_user_login_count
 *  Descrsiption: Increments the user's successful login count by 1.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_upd_user_login_count' AND type = 'P')
   DROP PROCEDURE usp_upd_user_login_count
GO

create procedure usp_upd_user_login_count @id int as
                                                     
  begin
      -- Update user login count
      update 
          user_login 
      set
		  total_logons = isNull(total_logons, 0) + 1
	  where
	      id = @id

  end
go


