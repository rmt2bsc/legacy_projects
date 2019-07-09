ALTER VIEW "DBA"."vw_charter_order_list"( /* view_column_name, ... */ )
AS
SELECT client.id client_id,   
         client.contact_fname,   
         client.contact_lname,   
         client.contact_company, 
         business.longname contact_company_name, 
         quote.id quote_id,   
         quote.company_id transport_company_id,
         (select longname from business where id = quote.company_id) transport_company,
         quote.signage,   
         quote.charter_party,   
         quote.depart_date,   
         quote.return_date,   
         orders.id order_id,   
         quote.status status_id,   
         general_codes.longdesc status,
         quote.destination,
         orders.client_order_id,
         getOrderBalance(orders.id) balance_due,
         getOrderTotal(orders.id) order_total,
         sumExpenses(orders.id) expenses
    FROM quote
        LEFT OUTER JOIN orders ON quote.id = orders.quote_id
        LEFT OUTER JOIN general_codes ON quote.status = general_codes.code_id
        INNER JOIN client ON  quote.client_id = client.id
        INNER JOIN business on client.contact_company = business.id
   GROUP BY  client.id,   
             client.contact_fname,   
             client.contact_lname,   
             client.contact_company,
             business.longname,   
             quote.id,   
             quote.company_id,
             quote.signage,   
             quote.charter_party,   
             quote.depart_date,   
             quote.return_date,   
             orders.id,   
             quote.status,   
             general_codes.longdesc,
             quote.destination,
             orders.client_order_id