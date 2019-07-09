/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_xact_gl_posting_list                                */
/*==============================================================*/
create view vw_xact_gl_posting_list as
Select  
      xact.id id, 
      xact_posting.gl_account_id gl_account_id,
      (select name from gl_accounts where id = xact_posting.gl_account_id) gl_acct_name,
      xact_posting.post_amount xact_amount, 
      xact.xact_date xact_date, 
      xact_posting.post_date post_date,
      xact.xact_type_id xact_type_id,
      xact_type.description xact_type_name,
      xact_type.xact_category_id xact_category_id,
      xact_category.description xact_category_description,
      xact_type.to_multiplier to_multiplier,
      xact_type.from_multiplier from_multiplier,
      xact_type.to_acct_type_id to_acct_type_id,
      xact_type.to_acct_catg_id to_acct_catg_id,
      xact_type.from_acct_type_id from_acct_type_id,
      xact_type.from_acct_catg_id from_acct_catg_id,
      xact_type.has_subsidiary has_subsidiary,
      xact.reason reason
From 
      xact, 
      xact_posting, 
      xact_type,
      xact_category
Where 
      xact.id = xact_posting.xact_id 
  and xact.xact_type_id = xact_type.id
  and xact_category.id = xact_type.xact_category_id
go


