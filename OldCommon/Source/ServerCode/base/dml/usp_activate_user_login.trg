/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_activate_user_login
 *  Descrsiption: Activates a user's login
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_activate_user_login' AND type = 'P')
   DROP PROCEDURE usp_activate_user_login
GO

create procedure usp_activate_user_login @id int as
                                                     
  begin
      -- Update user login count
      update 
          user_login 
      set
		  active = 1
	  where
	      id = @id

  end
go


