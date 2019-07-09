/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_xact_credit_charge_list                             */
/*==============================================================*/
create view vw_xact_credit_charge_list as
select  
	xact.id xact_id, 
	xact.xact_amount, 
	xact.xact_date xact_date, 
	xact.posted_date posted_date, 
	xact.confirm_no confirm_no,
	xact.neg_instr_no neg_instr_no,
	xact.tender_id tender_id,
	xact_codes.description tender_description,
	xact.xact_type_id xact_type_id,
	xact_type.description xact_type_name,
	xact.reason reason,
	xact.date_created xact_entry_date,
	xact.user_id user_id,
	xact_type.xact_category_id,
	xact_type.to_multiplier,
	xact_type.from_multiplier,
	xact_type.to_acct_type_id,
	xact_type.to_acct_catg_id,
	xact_type.from_acct_type_id,
	xact_type.from_acct_catg_id,
	xact_type.has_subsidiary,
	creditor.id creditor_id, 
	creditor.creditor_type_id,
	creditor.account_number account_no, 
	creditor.credit_limit credit_limit, 
	creditor.active,
	creditor.apr apr,
	creditor.date_created creditor_date_created,
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
        dbo.ufn_get_creditor_balance(creditor.id) as balance
from 
	xact, 
	xact_type,
	xact_codes,
	creditor_activity,
	creditor,
	creditor_type,
	business
where 
      xact.xact_type_id = xact_type.id
  and xact.tender_id *= xact_codes.id
  and creditor_activity.xact_id = xact.id
  and creditor_activity.creditor_id = creditor.id
  and creditor.business_id = business.id
  and creditor_type.id = creditor.creditor_type_id
go


