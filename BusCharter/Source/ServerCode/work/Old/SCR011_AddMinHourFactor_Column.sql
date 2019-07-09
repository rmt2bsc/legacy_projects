/* SCR 011 */

ALTER TABLE orders 
  ADD min_hour_factor numeric(5,2) DEFAULT NULL;
  
ALTER TABLE bus_detail
  ADD min_hour_factor numeric(5,2) DEFAULT NULL;  
  
ALTER TABLE general_codes
  ADD int_value int DEFAULT NULL;    