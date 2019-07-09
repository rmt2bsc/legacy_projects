/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_xact_type_item_activity
 *  Descrsiption: Creates customer transaction activity which reflects accounts receivable.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_xact_type_item_activity' AND type = 'P')
   DROP PROCEDURE usp_add_xact_type_item_activity
GO

create procedure usp_add_xact_type_item_activity @id int output, 
                                                 @xact_id int,
                                                 @xact_type_item_id int, 
                                                 @xact_amt numeric(15,2),
                                                 @description varchar(50)  as
  begin
       declare @xact_date datetime
       declare @xact_type_id int
       
       set @xact_date = dbo.ufn_get_datepart(getdate())
       
       -- Verify that transaction is valid by attempting 
       -- to obtain its transaction type id
       select
             @xact_type_id = xact_type_id
         from
             xact
        where
             id = @xact_id
            
        --  Raise error if Transaction does not exist.
        if @xact_type_id is null or @xact_type_id <= 0 
            raiserror(50038, 18, 1, @xact_id)
            
       -- Record transaction type item activity
       insert into XACT_TYPE_ITEM_ACTIVITY
			(xact_id,
             xact_type_item_id,
			 amount,
             description,
			 date_created, 
			 date_updated, 
			 user_id)
		values 
			(@xact_id,
             @xact_type_item_id,
			 @xact_amt,
             @description,
			 getdate(),
			 getdate(),
			 user)

        set @id = @@identity                   
  end
go


