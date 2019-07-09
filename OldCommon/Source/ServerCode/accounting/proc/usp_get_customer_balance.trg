/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create procedure usp_get_customer_balance (@customer_id int, @amount numeric output)

begin
  select 
      @amount = isnull(sum(amount), 0) 
  from 
      customer_activity  
  where 
      customer_id = @customer_id
end
go


