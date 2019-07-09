%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getPayments
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Adds up all transactions(+/-) except for deposits and gratuities
%               and returns the total transaction amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

create function getPayments(aorder_id integer) returns numeric(11,2)

begin
   declare TotalPayments numeric(11,2);

   if aorder_id is null or aorder_id <= 0 then
     return 0;
   end if;

   set TotalPayments = 0;

   select sum(amount)
     into TotalPayments
     from transaction
     where transaction.order_id = aorder_id
       and trans_type_id in (207);
		
   return ifNull(TotalPayments, 0, TotalPayments);
end;
