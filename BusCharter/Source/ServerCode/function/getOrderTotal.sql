create function getOrderTotal(aorder_id integer) returns numeric(11,2)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getOrderTotal
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Calculates the total of a selected order as: 
%               (flat rate charge + live milage total + dead head mileage total) * bus count.
%               The calculated amount is returned to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


   begin
   
     declare totalcost numeric(11,2);  
     declare mileagecost numeric(11,2);  
     declare hourlycost numeric(11,2);  
     declare flatrate numeric(11,2);
     declare livemiles numeric(11,2);
     declare deadmiles numeric(11,2);
     declare live_rate numeric(11,2);
     declare deadhead_rate numeric(11,2);
     declare hourly_rate numeric(11,2);
     declare ext_hourly_rate numeric(11,2);
     declare totalhours integer;
     declare bus_cost numeric(11,2);
     declare quoteID integer;
     declare bus_count integer;



     if aorder_id is null then
        return 0;
     end if;

     select quote_id,
       ifnull(flat_rate, 0, flat_rate),
       ifnull(live_miles, 0, live_miles),
       ifnull(deadhead_miles, 0, deadhead_miles),
       ifnull(milage_rate, 0, milage_rate) ,
       ifnull(deadhead_milage_rate, 0, deadhead_milage_rate) ,
       ifnull(hourly_rate, 0, hourly_rate),
       ifnull(hourly_rate2, 0, hourly_rate2),
       ifnull(total_hours, 0, total_hours),
       ifnull(buscost, 0, buscost)
     into 
         quoteID,
         flatrate,
         livemiles,
         deadmiles,
         live_rate ,                                   
         deadhead_rate,
         hourly_rate,
         ext_hourly_rate,
         totalhours,
         bus_cost
      from orders
      where id = aorder_id;

      select bus_count
         into bus_count
         from quote
         where id = quoteID;
      
      set mileagecost = (livemiles * live_rate) + (deadmiles * deadhead_rate);

      %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
      %  Calculate minimum hourly cost
      %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
      if totalhours > 5 then
         set hourlycost = hourly_rate * 5;
      else
         set hourlycost = hourly_rate * totalhours;
      end if;
     
      %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
      %  Calculate extended hourly cost
      %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
      if totalhours > 5 then
         set hourlycost = hourlycost + (ext_hourly_rate * (totalhours - 5))
      end if;

      set totalcost = ((flatrate + mileagecost + hourlycost)  *  bus_count) + bus_cost;

      if totalcost is null then
         set totalcost = 0;
      end if;

      return totalcost;
      
   end;
