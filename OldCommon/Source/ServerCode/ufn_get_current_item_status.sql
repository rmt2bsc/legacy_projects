/******************************************************************************************************************************
 *         Method: ufn_get_current_item_status
 *     Prototype: int @item_master_id
 *        Returns: int 
 *  Description: Retrieves the item status id of an item (item_master_id) which is iavailbale or in use.   Otherwise, a 
 *                         negative value is returned.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_current_item_status')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_item_status
go

create function ufn_get_current_item_status (@item_master_id int)  returns int as

		begin
          declare @id int
          declare @result int
          
          select
                   @id = item_status_id
           from
                    item_master_status_hist
           where
                    id = @item_master_id
           and end_date is null
           
           set @result = @@rowcount
           if @result > 1
              return -200
           
                --  Return null if account type is not found
           if @result = 0
               return -100
               
           return @id
		end
