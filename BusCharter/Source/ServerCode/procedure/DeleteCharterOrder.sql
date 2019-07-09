%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    deleteCharterOrder
%  arguments:   integer a_quote_id,
%               integer  a_order_id
%  returns:     none
%  description: Deletes a charter order by first removing all dependcies to the ordres table.
%               summed amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
create procedure deleteCharterOrder(in a_quote_id int,  in a_order_id int) 
    
  begin
    declare l_comment_id int;
    declare l_quote_id int;
    declare l_order_id  int;
    declare l_count        int;

/***********************************************************
 *              Validate Input Parameters
 ***********************************************************/            
    if a_quote_id is null and a_order_id is null then
       raiserror 20000 'The Trip ID and the Order ID cannot be null when attempting to delete a Carter Order';
   end if;


     if a_quote_id is null and a_order_id is not null then
       raiserror 20001 'An invalid  Trip ID was detected for order ' || a_order_id;
    end if;

             /*  Check to see if an order exist for this quote */
    if a_quote_id is not null and a_order_id is null then
       select count(*) 
           into l_count
          from orders
          where quote_id =  a_quote_id;
  
        if l_count > 0 then
            raiserror 20002 'Cannot delete a Trip without first deleting its associated orders! ';
        end if;

             /*  Is the order valid?  */
        if a_order_id is not null and a_order_id > 0 then
           select  id
               into l_order_id
               from orders
               where id = a_order_id;

            if sqlcode <> 0 then       
               raiserror 20003 'The Order ID supplied is invalid!     ';             
           end if;
        end if;

             /*  Is the quote valid?  */
        select  id
            into l_quote_id
            from quote
            where id = a_quote_id;

         if sqlcode <> 0 then       
              raiserror 20004 'The Trip ID supplied is invalid!     ';             
        end if;
    end if;


/******************************************
 *   Begin deleting the order/quote
 ******************************************/
     if a_order_id is not null then
             /*  check to see if bus detail exist  */
         select  count(*)
              into l_count
              from bus_detail
             where orders_id = a_order_id;

         if l_count > 0 then
             delete from bus_detail
                  where orders_id = a_order_id;
             if sqlcode <> 0 then
                  raiserror 20005 'The was a problem deleting the bus detail for Order ID:  ' || a_order_id;             
             end if;
          end if;          

             /*  check to see if any transactions exist for the order  */
         select  count(*)
              into l_count
              from transaction
             where order_id = a_order_id;

         if l_count > 0 then
             delete from transaction
                  where order_id = a_order_id;
             if sqlcode <> 0 then
                  raiserror 20006 'The was a problem deleting the transactions belonging to Order ID:  ' || a_order_id;             
             end if;
         end if;          

             /*  delete order  */
         delete from orders where id = a_order_id;
         if sqlcode <> 0 then
              raiserror 20007 'The was a problem deleting the Order ID:  ' || a_order_id;                         
         end if;
     end if;  /*  End deleting Orders  */


      if a_quote_id is not null then
           delete from quote where id = a_quote_id;
           if sqlcode <> 0 then
                raiserror 20008 'There was a problem deleting the Trip ID:  ' || a_quote_id;                         
          end if;
    end if;  /*  End deleting Quote  */

end;
