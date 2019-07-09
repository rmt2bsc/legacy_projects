/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_item_status_id
 *     Prototype: varchar @descr
 *        Returns: int 
 *  Description: Retrieves the item status id from the item_master_status_hist table that corresponds to @descr.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_item_status_id')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_item_status_id
go

create function ufn_get_item_status_id (@descr varchar(40))  returns int as

		begin
          declare @id int
          
              -- Notify the user that we cannot search for a null description
          if @descr is null
              return -1
              
          select
                   @id = id
           from
                   item_master_status
           where
                   description = @descr
           
                --  Return null if account type is not found
           if @@error <> 0
               return -100
               
           return @id
		end
go

