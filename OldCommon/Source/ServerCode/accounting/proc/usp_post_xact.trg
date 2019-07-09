/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_post_xact
 *  Descrsiption: Updates xact row by setting posted_date column to the current date.    This is generally done after all
 *                            transaction entries have been successfully posted to the General Ledger.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_post_xact' AND type = 'P')
   DROP PROCEDURE usp_post_xact
GO

create procedure usp_post_xact @id int as
  begin
      -- Post Transaction      
      update 
          XACT 
      set
	  	  posted_date = getdate()
	  where 
          id = @id

      -- Post Transaction Items
      update 
          XACT_POSTING 
      set
          post_date = getdate()
      where 
          xact_id = @id
        
  end
go


