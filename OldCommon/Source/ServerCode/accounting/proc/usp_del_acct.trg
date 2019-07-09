/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_acct
 *      Returns: none
 *  Descrsiption: Deletes a GL Account from the system.    Raise error if one or more dependencies exist.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_del_acct' AND type = 'P')
   DROP PROCEDURE usp_del_acct
GO

create procedure usp_del_acct @id int as
  begin
      declare @depend_count int
      
          -- Check if GL Account have any dependencies
      exec @depend_count = dbo.ufn_get_acct_usage_count @id
      
          --  If dependencies exist, then raise error notifying client that GL Account cannot be deleted due to dependencies
      if @depend_count > 0 
          raiserror(50032, 18, 1, @id)
      
          --  At this point it is okay to delete GL Account since there are no dependencies.
      delete from  GL_ACCOUNTS 
       where id = @id
  end
go


