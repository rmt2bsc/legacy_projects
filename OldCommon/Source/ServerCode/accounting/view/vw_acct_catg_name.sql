/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_acct_catg_name                                      */
/*==============================================================*/
create view vw_acct_catg_name as
Select gla.acct_cat_id, 
       gla.acct_no, 
    	gla.acct_seq, 
    	gla.acct_type_id, 
    	gla.balance_type_id, 
    	gla.description, 
    	gla.id, 
    	gla.name, 
    	glac.description gl_catg_description
From gl_accounts gla, 
     gl_account_category glac
Where gla.acct_cat_id = glac.id
go


