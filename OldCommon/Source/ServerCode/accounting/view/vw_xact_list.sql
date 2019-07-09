/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_xact_list                                           */
/*==============================================================*/
create view vw_xact_list as
Select  
      xact.id id, 
      sum(xact_type_item_activity.amount) xact_amount, 
      xact.xact_date xact_date, 
      xact.posted_date posted_date, 
      xact.confirm_no confirm_no,
      xact.neg_instr_no neg_instr_no,
      xact.tender_id tender_id,
      xact_codes.description tender_description,
      xact.xact_type_id xact_type_id,
      xact_type.description xact_type_name,
      xact.reason reason,
      xact.date_created create_date,
      xact.user_id user_id,
      xact_type.xact_category_id,
      xact_type.to_multiplier,
      xact_type.from_multiplier,
      xact_type.to_acct_type_id,
      xact_type.to_acct_catg_id,
      xact_type.from_acct_type_id,
      xact_type.from_acct_catg_id,
      xact_type.has_subsidiary,
      xact_type_item.xact_type_id xact_type_item_xact_type_id
From 
      xact, 
      xact_type,
      xact_codes,
      xact_type_item,
      xact_type_item_activity
Where 
      xact.xact_type_id = xact_type.id
  and xact.tender_id *= xact_codes.id
  and xact.id = xact_type_item_activity.xact_id
  and xact_type_item_activity.xact_type_item_id = xact_type_item.id

group by
      xact.id,
      xact.xact_date,
      xact.posted_date,
      xact.confirm_no,
      xact.neg_instr_no,
      xact.tender_id,
      xact_codes.description,
      xact.xact_type_id,
      xact_type.description,
      xact.reason,
      xact_type.xact_category_id,
      xact_type.to_multiplier,
      xact_type.from_multiplier,
      xact_type.to_acct_type_id,
      xact_type.to_acct_catg_id,
      xact_type.from_acct_type_id,
      xact_type.from_acct_catg_id,
      xact_type.has_subsidiary,
      xact_type_item.xact_type_id,
      xact.date_created,
      xact.user_id
go


