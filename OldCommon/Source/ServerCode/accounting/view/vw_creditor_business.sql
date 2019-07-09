/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_creditor_business                                   */
/*==============================================================*/
create view vw_creditor_business as
select creditor.id creditor_id, 
       creditor.creditor_type_id,
       creditor.account_number account_no, 
       creditor.gl_account_id gl_account_id, 
       creditor.credit_limit credit_limit, 
       creditor.active,
       creditor.apr apr,
       creditor.date_created date_created,
       creditor.date_updated date_updated,
       creditor.user_id user_id, 
       creditor_type.description creditor_type_description,
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
       dbo.ufn_get_customer_name(null, business_id) as name,
       dbo.ufn_get_creditor_balance(creditorr.id) as balance
from creditor,
     creditor_type,
     business
where creditor.business_id = business.id
  and creditor_type.id = creditor.creditor_type_id
go


