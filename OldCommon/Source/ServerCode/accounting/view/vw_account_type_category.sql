/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_account_type_category                               */
/*==============================================================*/
create view vw_account_type_category as
Select gl_accounts.id as id, 
       gl_accounts.acct_type_id as acct_type_id, 
       gl_accounts.acct_cat_id as acct_cat_id, 
       gl_accounts.acct_seq as acct_seq, 
       gl_accounts.acct_no as acct_no, 
       gl_accounts.name as name, 
       gl_accounts.description,
       gl_accounts.balance_type_id,
       gl_account_types.Description as acctTypeDescr, 
       gl_account_category.Description as acctCatgDescr
From  gl_accounts, 
      gl_account_types, 
      gl_account_category
Where gl_accounts.acct_type_id = gl_account_types.id 
  and gl_accounts.acct_cat_id = gl_account_category.id
go


