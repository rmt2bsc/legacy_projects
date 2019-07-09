/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_customer_person                                     */
/*==============================================================*/
create view vw_customer_person as
select customer.id customer_id, 
       customer.account_no account_no, 
       customer.gl_account_id gl_account_id, 
       customer.credit_limit credit_limit, 
       customer.active, 
       customer.date_created date_created,
       customer.date_updated date_updated,
       customer.user_id user_id,
       person.id person_id,
       person.title title,
       person.firstname firstname, 
       person.midname midname, 
       person.lastname lastname, 
       person.maidenname maidenname,
       person.shortname shortname, 
       person.generation generation,
       person.birth_date birth_date, 
       person.gender_id gender_id, 		
       person.email email, 
       person.ssn ssn, 	
       person.marital_status marital_status, 
       person.race_id race_id,
       dbo.ufn_get_customer_name(person_id, business_id) as name,
       dbo.ufn_get_customer_balance(customer.id) as balance
from customer,
     person
where customer.person_id = person.id
go


