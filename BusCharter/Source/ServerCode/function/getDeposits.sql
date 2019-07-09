%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getDeposits
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Sums the total of all deposits for a given charter order and returns the 
%               summed amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
create function getDeposits(aorder_id integer) returns numeric(11,2)

begin
   declare TransTypeCode  integer;
   declare TotalDeposit numeric(11,2);

   if aorder_id is null or aorder_id <= 0 then
     return 0;
   end if;

   set TransTypeCode = 211;
   set TotalDeposit = 0;

   select sum(amount)
     into TotalDeposit
     from transaction
     where transaction.order_id = aorder_id
       and trans_type_id in (TransTypeCode);
	
   	
   return ifNull(TotalDeposit, 0, TotalDeposit);
end;
