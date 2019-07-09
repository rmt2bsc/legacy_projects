/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_customer_business                                   */
/*==============================================================*/
create view vw_customer_business as
select customer.id customer_id, 
       customer.account_no account_no, 
       customer.gl_account_id gl_account_id, 
       customer.credit_limit credit_limit, 
       customer.active,
       customer.date_created date_created,
       customer.date_updated date_updated,
       customer.user_id user_id, 
       business.id business_id, 
       business.longname longname, 
       business.shortname shortname, 
       business.serv_type serv_type, 
       business.bus_type bus_type,
       business.contact_firstname contact_firstname, 
       business.contact_lastname contact_lastname, 
       business.contact_phone contact_phone, 
       business.contact_ext contact_ext, 
       business.tax_id tax_id, 
       business.website website,
       dbo.ufn_get_customer_name(person_id, business_id) as name,
       dbo.ufn_get_customer_balance(customer_id) as balance
from customer,
     business
where customer.business_id = business.id
go


