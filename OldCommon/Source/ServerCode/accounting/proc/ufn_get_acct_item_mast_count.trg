/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create function ufn_get_acct_item_mast_count  ( @gl_acct_id int )
   returns int
as

begin
   declare @item_count int

   select 
          @item_count = count(*)
    from
           item_master
    where
           gl_account_id = @gl_acct_id
           
     if @@error <> 0 
        return -1
         
     if @item_count is null
         set @item_count = 0
         
     return @item_count
     
end
GO
go


