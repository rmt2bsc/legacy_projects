%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getAdjustments
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Adds up all transactions(+/-) except for deposits, gratuities, and payments
%               and returns the total transaction amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

create function getAdjustments(aorder_id integer) returns numeric(11,2)

begin
   declare TotalAdjustments numeric(11,2);

   if aorder_id is null or aorder_id <= 0 then
     return 0;
   end if;

   set TotalAdjustments = 0;

   select sum(amount)
     into TotalAdjustments
     from transaction
     where transaction.order_id = aorder_id
       and trans_type_id not in (207, 210, 211);
		
   return ifNull(TotalAdjustments, 0, TotalAdjustments);
end;
