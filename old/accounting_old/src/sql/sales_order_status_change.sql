-- End most recent sales order status entry
UPDATE SALES_ORDER_STATUS_HIST 
  SET end_date ='2010-09-06' 
  WHERE so_status_hist_id = 951;

-- Create new sales order status history entry
INSERT INTO SALES_ORDER_STATUS_HIST 
  (so_id, 
   so_status_id, 
   effective_date, 
   end_date, 
   reason, 
   date_created, 
   user_id, 
   ip_created, 
   ip_updated) 
VALUES
  (409, 
   100,
   '2010-09-07',
   NULL, 
   'Applied to the incorrect account',
   '2010-09-07 11:36:56.000',
   'admin',
   '151.193.220.28',
   '151.193.220.28')


