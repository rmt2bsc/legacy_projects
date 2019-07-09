/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create procedure usp_get_creditor_balance (@creditor_id int, @amount numeric output)
as
begin
  select 
      @amount = isnull(sum(amount), 0) 
  from 
      creditor_activity  
  where 
      creditor_id = @creditor_id
end
go


