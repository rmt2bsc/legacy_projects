/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_item_master
 *      Returns: none
 *  Descrsiption: Deletes a Item Master from the system.    Raise error if one or more dependencies exist.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_del_item_master' AND type = 'P')
   DROP PROCEDURE usp_del_item_master
GO

create procedure usp_del_item_master @id int as
  begin
      declare @depend_count int
      
      -- Check if Item Master has dependencies
      exec @depend_count = dbo.ufn_get_item_usage_count @id
      
      --  If dependencies exist, then raise error notifying client that Item Master cannot be deleted due to dependencies
      if @depend_count > 0 
          raiserror(50035, 18, 1, @id)
      
      -- remove all history pertaining to target item.
      delete from item_master_status_hist
        where item_master_id = @id
        
      --  At this point it is okay to delete GL Account since there are no dependencies.
      delete from  ITEM_MASTER 
       where id = @id
  end
go


