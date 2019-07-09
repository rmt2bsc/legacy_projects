/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_item_usage_count
 *        Returns: int 
 *  Description: Retrieves and returns to the caller a count where Item Master exist throughout the system.
 *******************************************************************************************************************************/
if exists (select * from   sysobjects 
	      where  name = 'ufn_get_item_usage_count')
	drop function ufn_get_item_usage_count
GO

create function ufn_get_item_usage_count  ( @id int )
   returns int
as

begin
   declare @item_count int

      --  Get Sales order count
   select
           @item_count = isNull(count(*),0)
    from
            sales_order_items
    where item_master_id = @id
    
    if @@error <> 0 
        return -1001
    
        
   return @item_count 
end

GO
go


