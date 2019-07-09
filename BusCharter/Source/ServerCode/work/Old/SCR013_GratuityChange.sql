update general_codes set
   gen_ind_value = 'DB'
  where code_id = 210;

commit;

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


alter function dba.getOrderBalance(in aorder_id integer) returns numeric(11,2)

--%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
--  function:    getOrderBalance
--  arguments:   integer  aorder_id
--  returns:     numeric
--  description: Calculates the balance of an order as: 
--               (Total order cost + deposits + total payments (+/-).
--               The calculated balance is returned to the caller.
--%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

begin
  declare totalcost numeric(11,2);
  declare deposits numeric(11,2);
  declare payments numeric(11,2);
  declare adjustments numeric(11,2);
  declare balance numeric(11,2);
  declare gratuity numeric(11,2);
  if aorder_id is null then
    return 0
  end if;
  select getOrderTotal(aorder_id),
    getDeposits(aorder_id),
    getPayments(aorder_id),
    getAdjustments(aorder_id),
    getGratuity(aorder_id) into totalcost,
    deposits,
    payments,
    adjustments,
    gratuity from orders where
    id=aorder_id;
  set balance=(totalcost+deposits+gratuity+payments+adjustments);
  if balance is null then
    set balance=0
  end if;
  return balance
end;

commit;