/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_acct_usage_count
 *     Prototype: int (GL Account  Id)
 *        Returns: int 
 *  Description: Retrieves and returns to the caller a count where GL Account ID exist throughout the system.
 *******************************************************************************************************************************/
if exists (select * from   sysobjects 
	      where  name = 'ufn_get_acct_usage_count')
	drop function ufn_get_acct_usage_count
GO

create function ufn_get_acct_usage_count  ( @gl_acct_id int )
   returns int
as

begin
   declare @xact_count int
   declare @item_count int
   declare @subsidiary_count int

      --  Get Transaction Count
   exec @xact_count = dbo.ufn_get_acct_to_xact_count @gl_acct_id
    if @@error <> 0 
        return -1001
        
      --  Get Item Master Count
   exec @item_count = dbo.ufn_get_acct_item_mast_count @gl_acct_id
    if @@error <> 0 
        return -1002

      --  Get Subsidiary Count
   exec @subsidiary_count = dbo.ufn_get_acct_subsidiary_count @gl_acct_id
    if @@error <> 0 
        return -1003
        
   return @xact_count + @item_count + @subsidiary_count
end

GO
go


