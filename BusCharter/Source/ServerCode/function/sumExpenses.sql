%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    sumExpenses
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Adds up all expenses(+/-) for an order and returns the total transaction amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

create function sumExpenses(aorder_id integer) returns numeric(11,2)

begin
   declare tot  numeric(11,2);

   if aorder_id is null or aorder_id <= 0 then
     return 0;
   end if;

   set tot = 0;

   select sum(amount)
     into tot
     from order_expenses
     where order_id = aorder_id;
		
   return ifNull(tot, 0, tot);
end;
