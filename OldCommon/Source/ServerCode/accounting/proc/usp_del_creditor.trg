/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_creditor
 *      Returns: none
 *  Descrsiption: Deletes a creditor from the system.    Raise error if one or more dependencies exist.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_del_creditor' AND type = 'P')
   DROP PROCEDURE usp_del_creditor
GO

create procedure usp_del_creditor @id int as
  begin
      declare @depend_count int
      
          -- Check if Creditor has dependencies
      exec @depend_count = dbo.ufn_get_creditor_usage_count @id
      
          --  If dependencies exist, then raise error notifying client that creditor cannot be deleted due to dependencies
      if @depend_count > 0 
          raiserror(50033, 18, 1, @id)
      
          --  At this point it is okay to delete creditor since there are no dependencies.
      delete from  CREDITOR 
       where id = @id
  end
go


