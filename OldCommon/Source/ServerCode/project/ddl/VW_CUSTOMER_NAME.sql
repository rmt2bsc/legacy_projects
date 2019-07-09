/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* View: VW_CUSTOMER_NAME                                       */
/*==============================================================*/
create view VW_CUSTOMER_NAME as
select id customer_id, 
       account_no, 
       gl_account_id, 
       credit_limit, 
       active, 
       date_created,
       date_updated,
       user_id user_id,
       person_id, 
       business_id,
       dbo.ufn_get_customer_name(person_id, business_id) as name,
       dbo.ufn_get_customer_balance(id) as balance
from customer
go


