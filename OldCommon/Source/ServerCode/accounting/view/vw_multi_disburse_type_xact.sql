/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     4/25/2007 1:39:06 AM                         */
/*==============================================================*/


/*==============================================================*/
/* View: VW_MULTI_DISBURSE_TYPE_XACT                            */
/*==============================================================*/
create view VW_MULTI_DISBURSE_TYPE_XACT as
select xact.id xact_id,
       xact.xact_amount,
       xact.posted_date,
       xact.confirm_no,
       xact.neg_instr_no,
       xact.tender_id,
       xact_codes.description tender_description,
       xact.xact_type_id,
       xact_type.description xact_type_name,
       xact.reason,
       xact.xact_date,
       xact.date_created create_date,
       xact.user_id,
       xact_type.xact_category_id,
       0 xact_type_item_xact_type_id,
       0 creditor_id,
       '' account_number,
       0 creditor_type_id,
       0 credit_limit,
       0 business_id,
       '' longname,
       0 serv_type,
       0 bus_type,
       '' tax_id,
       '' website,
       '' contact_firstname,
       '' contact_lastname,
       '' contact_phone,
       '' contact_ext
 from xact, 
      xact_type,
      xact_codes
Where 
      xact.xact_type_id = xact_type.id
  and xact.tender_id *= xact_codes.id
  and not exists (select 1 from creditor_activity where xact_id = xact.id)

union

select xact.id xact_id,
       xact.xact_amount,
       xact.posted_date,
       xact.confirm_no,
       xact.neg_instr_no,
       xact.tender_id,
       xact_codes.description tender_description,
       xact.xact_type_id,
       xact_type.description xact_type_name,
       xact.reason,
       xact.xact_date,
       xact.date_created create_date,
       xact.user_id,
       xact_type.xact_category_id,
       0 xact_type_item_xact_type_id,
       creditor.id creditor_id,
       creditor.account_number,
       creditor.creditor_type_id,
       creditor.credit_limit,
       business.id business_id,
       business.longname,
       business.serv_type,
       business.bus_type,
       business.tax_id,
       business.website,
       business.contact_firstname,
       business.contact_lastname,
       business.contact_phone,
       business.contact_ext
From xact, 
     xact_type, 
     xact_codes,
     business,
     creditor,
     creditor_type,
     creditor_activity
Where 
      xact.xact_type_id = xact_type.id
  and creditor.business_id = business.id 
  and creditor_activity.creditor_id = creditor.id
  and creditor_activity.xact_id = xact.id
  and creditor_type.id = creditor.creditor_type_id
  and xact.tender_id *= xact_codes.id
go

