create function getOrderHourlyRate(aOrderId integer, aRateType int) returns numeric(11,2)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getOrderHourlyRate
%  arguments:   integer  aOrderId
%               int      aRateType (1=Minimum Rate, 2=Extended Rate, 3=Total Hourly Rate)
%  returns:     numeric
%  description: Calculates the Minimum Hourly Rate, the Extended Hourly Rate, or the sum
%               of the Minimum Rate and the Extended Rate for an order. 
%               The calculated amount is returned to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


   begin
   
     declare result numeric(11,2);  
     declare minRateFactor int;
     declare rateType int;
     declare hourly_rate numeric(11,2);
     declare ext_hourly_rate numeric(11,2);
     declare totalhours integer;
     declare minCost numeric;
     declare extCost numeric;
     declare minHours int;

     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     % Validate input parameters
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     if aOrderId is null then
        return -1;
     end if;
     if aRateType is null then
        set rateType = 3;
     else
        if aRateType in (1, 2, 3) then
          set rateType = aRateType;
        else
          return -101;
        end if
     end if;
     
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     %  Obtain Data from the selected datasource
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     
     select 
         ifnull(hourly_rate, 0, hourly_rate),
         ifnull(hourly_rate2, 0, hourly_rate2),
         ifnull(total_hours, 0, total_hours),
         ifnull(min_hour_factor, 0, min_hour_factor)
     into 
         hourly_rate,
         ext_hourly_rate,
         totalhours,
         minHours
     from orders
     where id = aOrderId;
     
     if sqlcode <> 0 then
       return -100;
     end if;

     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     %  Calculate minimum hourly cost
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     if rateType in (1, 3) then
       if totalhours > minHours then
          set minCost = hourly_rate * minHours;
       else
          set minCost = hourly_rate * totalhours;
       end if;
     else
       set minCost = 0;
     end if;
     
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     %  Calculate extended hourly cost
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     if rateType in (2, 3) then
       if totalhours > minHours then
          set extCost = ext_hourly_rate * (totalhours - minHours)
       else
          set extCost = 0;
       end if;
     else
       set extCost = 0;
     end if;
      
     set result = minCost + extCost;
     
     return result;
      
   end;
