/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_user_xref
 *  Descrsiption: Inserts a row into the user_connect_xref.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_user_xref' AND type = 'P')
   DROP PROCEDURE usp_add_user_xref
GO

create procedure usp_add_user_xref @user_id varchar(50) as
  begin
      insert into user_connect_xref
		(con_id,
		 con_user_id,
		 app_user_id)
 	  values
		(@@spid,
		 user,
		 @user_id)
  end
go


