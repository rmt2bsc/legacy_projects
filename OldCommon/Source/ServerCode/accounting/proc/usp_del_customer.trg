/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_customer
 *      Returns: none
 *  Descrsiption: Deletes a customer from the system.    Raise error if one or more dependencies exist.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_del_customer' AND type = 'P')
   DROP PROCEDURE usp_del_customer
GO

create procedure usp_del_customer @id int as
  begin
      declare @depend_count int
      
          -- Check if Customer has dependencies
      exec @depend_count = dbo.ufn_get_cust_usage_count @id
      
          --  If dependencies exist, then raise error notifying client that Customer cannot be deleted due to dependencies
      if @depend_count > 0 
          raiserror(50034, 18, 1, @id)
      
          --  At this point it is okay to delete Customer since there are no dependencies.
      delete from  CUSTOMER 
       where id = @id
  end
go


