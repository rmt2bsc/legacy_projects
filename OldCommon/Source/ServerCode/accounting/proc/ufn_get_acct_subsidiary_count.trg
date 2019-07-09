/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create function ufn_get_acct_subsidiary_count  ( @gl_acct_id int )
   returns int
as

begin
   declare @cust_count int
   declare @vend_count int
   declare @asset_detail_count int

      --  Check customer table for entries
   select 
          @cust_count = count(*)
    from
           customer
    where
           gl_account_id = @gl_acct_id
           
     if @@error <> 0 
        return -1

      --  Check Creditor/Vendor table for entries         
   select 
          @vend_count = count(*)
    from
           creditor
    where
           gl_account_id = @gl_acct_id
           
     if @@error <> 0 
        return -2

      --  Check Asset Details table for entries         
   select 
          @asset_detail_count = count(*)
    from
           asset_details
    where
           gl_account_id = @gl_acct_id
           
     if @@error <> 0 
        return -3

         -- Retrun the sum of the results to the caller.   
     return isNull(@cust_count, 0) + isNull(@vend_count, 0) + isNull(@asset_detail_count, 0)
     
end
go


