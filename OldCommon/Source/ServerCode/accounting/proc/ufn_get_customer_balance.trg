/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create function ufn_get_customer_balance (@customer_id int) returns numeric

begin
 declare @amount numeric
  select 
      @amount = isnull(sum(amount), 0) 
  from 
      customer_activity  
  where 
      customer_id = @customer_id
      
  return @amount      
end
go


