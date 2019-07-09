/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_customer_xact_hist                                  */
/*==============================================================*/
create view vw_customer_xact_hist as
Select xact.id xact_id,
       customer.id customer_id,
       --gl_accounts.id gl_account_id,
        customer.person_id,
	customer.business_id, 
	dbo.ufn_get_customer_name(customer.person_id, customer.business_id) as account_name,
        customer.account_no, 
	customer.credit_limit, 
	customer.active, 
	xact.xact_amount xact_amount, 
	xact.xact_date xact_date, 
	xact.xact_type_id xact_type_id,
	--xact_posting.post_date post_date,
	--gl_accounts.name gl_acct_name,
	xact.reason reason,
	xact_type.description xact_type_name,
        customer_activity.id customer_activity_id,
        customer_activity.amount customer_activity_amount
From xact, 
     xact_posting, 
     xact_type, 
     gl_accounts,
     customer,
     customer_activity
Where 
     xact.id *= xact_posting.xact_id 
--  and xact.xact_amount = xact_posting.post_amount
--  and xact_posting.gl_account_id = gl_accounts.id 
  and xact.xact_type_id = xact_type.id
  and customer.gl_account_id = gl_accounts.id
  and customer_activity.customer_id = customer.id
  and customer_activity.xact_id = xact.id
go


