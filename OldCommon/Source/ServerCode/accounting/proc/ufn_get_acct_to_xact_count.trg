/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create function ufn_get_acct_to_xact_count (@gl_acct_id int)
   returns int
as

begin
   declare @xact_count int

   select 
          @xact_count = count(*)
    from
           xact_posting
    where
           gl_account_id = @gl_acct_id
           
     if @@error <> 0 
        return -1
         
     if @xact_count is null
         set @xact_count = 0
         
     return @xact_count
     
end
go


