/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_user_xref
 *  Descrsiption: Deletes a row from the user_connect_xref.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_del_user_xref' AND type = 'P')
   DROP PROCEDURE usp_del_user_xref
GO

create procedure usp_del_user_xref @user_id varchar(50) as
  begin
      delete from user_connect_xref
      where app_user_Id = @user_id
  end
go


