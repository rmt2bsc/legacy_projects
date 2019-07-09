/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_cust_usage_count
 *        Returns: int 
 *  Description: Retrieves and returns to the caller a count where Cusomter exist throughout the system.
 *******************************************************************************************************************************/
if exists (select * from   sysobjects 
	      where  name = 'ufn_get_cust_usage_count')
	drop function ufn_get_cust_usage_count
GO

create function ufn_get_cust_usage_count  ( @id int )
   returns int
as

begin
   declare @so_count int
   declare @cust_actv_count int

      --  Get Sales order count
   select
           @so_count = isNull(count(*),0)
    from
            sales_order
    where customer_id = @id
    
    if @@error <> 0 
        return -1001
    
    
       -- Get Customer Activity Count
   select
         @cust_actv_count = isNull(count(*),0)
   from
         customer_activity
   where 
         customer_id = @id
	     
   if @@error <> 0 
      return -1002
        
   return @so_count  + @cust_actv_count
   
end

GO
go


