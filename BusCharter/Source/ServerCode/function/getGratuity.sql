create function getGratuity(aorder_id integer) returns numeric(11,2)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getGratuity
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Sums the total of all gratuities for a given charter order and returns the
%               summed amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

begin
   declare TransTypeCode  integer;
   declare TotalGratuity numeric(11,2);

   if aorder_id is null or aorder_id <= 0 then
     return 0;
   end if;

   set TransTypeCode = 210;
   set TotalGratuity = 0;

   select sum(amount)
     into TotalGratuity
     from transaction
     where transaction.order_id = aorder_id
       and trans_type_id in (TransTypeCode);


   return ifNull(TotalGratuity, 0, TotalGratuity);
end;
