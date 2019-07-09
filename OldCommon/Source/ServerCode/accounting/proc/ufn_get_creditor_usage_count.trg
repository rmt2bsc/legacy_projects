/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_creditor_usage_count
 *        Returns: int 
 *  Description: Retrieves and returns to the caller a count where Creditor exist throughout the system.
 *******************************************************************************************************************************/
if exists (select * from   sysobjects 
	      where  name = 'ufn_get_creditor_usage_count')
	drop function ufn_get_creditor_usage_count
GO

create function ufn_get_creditor_usage_count  ( @id int )
   returns int
as

begin
   declare @im_count int
   declare @cred_actv_count int

      --  Get Item Master count
   select
           @im_count = isNull(count(*),0)
    from
            item_master
    where vendor_id = @id
    
    if @@error <> 0 
        return -1001
    
    
       -- Get Creditor Activity Count
   select
	            @cred_actv_count = isNull(count(*),0)
	     from
	             creditor_activity
	     where creditor_id = @id
	     
	     if @@error <> 0 
        return -1002
        
   return @im_count  + @cred_actv_count
   
end

GO
go


