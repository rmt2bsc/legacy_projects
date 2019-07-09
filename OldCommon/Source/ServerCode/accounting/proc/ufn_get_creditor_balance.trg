/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create function ufn_get_creditor_balance (@creditor_id int) RETURNS numeric

begin
  declare @amount numeric
  select 
      @amount = isnull(sum(amount), 0) 
  from 
      creditor_activity  
  where 
      creditor_id = @creditor_id
      
  return @amount
end
go


