/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 7:46:15 AM                        */
/*==============================================================*/


/*==============================================================*/
/* View: VW_CLIENT_EXT                                          */
/*==============================================================*/
create view VW_CLIENT_EXT as
 SELECT vw_customer_business.customer_id client_id,   
         vw_customer_business.account_no,   
         vw_customer_business.credit_limit,   
         vw_customer_business.active,   
         vw_customer_business.date_created,   
         vw_customer_business.business_id perbus_id,   
         vw_customer_business.longname,   
         vw_customer_business.name,   
         vw_customer_business.balance,
         'bus' customer_type  
    FROM PROJ_CLIENT,   
         vw_customer_business  
   WHERE ( PROJ_CLIENT.id = vw_customer_business.customer_id )    

union

 SELECT vw_customer_person.customer_id client_id,   
         vw_customer_person.account_no,   
         vw_customer_person.credit_limit,   
         vw_customer_person.active,   
         vw_customer_person.date_created,   
         vw_customer_person.person_id perbus_id,   
         vw_customer_person.shortname,   
         vw_customer_person.name,   
         vw_customer_person.balance,
         'per' customer_type  
    FROM PROJ_CLIENT,   
         vw_customer_person  
   WHERE ( PROJ_CLIENT.id = vw_customer_person.customer_id )
go


