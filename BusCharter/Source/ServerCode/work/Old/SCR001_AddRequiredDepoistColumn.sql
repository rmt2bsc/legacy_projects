/*  SCR 1  */

ALTER TABLE orders 
  ADD required_deposit numeric(11,2) DEFAULT NULL;