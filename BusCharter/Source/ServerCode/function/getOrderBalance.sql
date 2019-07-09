%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getOrderBalance
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Calculates the balance of an order as: 
%               (Total order cost + deposits + total payments (+/-).
%               The calculated balance is returned to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
create function getOrderBalance(aorder_id integer) returns numeric(11,2)

   begin
   
     declare totalcost numeric(11,2);  
     declare deposits numeric(11,2);
     declare payments numeric(11,2);
     declare adjustments numeric(11,2);
     declare balance numeric(11,2);
     declare gratuity numeric(11,2);

     if aorder_id is null then
        return 0;
     end if;

     select 
       getOrderTotal(aorder_id),
       getDeposits(aorder_id),
       getPayments(aorder_id),
       getAdjustments(aorder_id),
       getGratuity(aorder_id)
     into 
         totalcost,
         deposits,
         payments,
         adjustments,
         gratuity
      from orders
      where id = aorder_id;

      set balance = (totalcost + deposits + gratuity + payments + adjustments);

      if balance is null then
         set balance = 0;
      end if;

      return balance;
      
   end;
