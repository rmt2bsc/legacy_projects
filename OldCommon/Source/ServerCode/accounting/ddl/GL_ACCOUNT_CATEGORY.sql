/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: GL_ACCOUNT_CATEGORY                                   */
/*==============================================================*/
print  'creating table: GL_ACCOUNT_CATEGORY'
create table GL_ACCOUNT_CATEGORY (
   id                   int                  not null,
   acct_type_id         int                  not null,
   description          varchar(40)          not null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           not null default 'DBA',
   constraint PK_GL_ACCOUNT_CATEGORY primary key  (id)
)
go


BULK INSERT GL_ACCOUNT_CATEGORY
   FROM 'C:\temp\accounting\ddl\GL_ACCOUNT_CATEGORY.dat'
GO
/*==============================================================*/
/* Index: GL_ACCT_CAT_NDX_2                                     */
/*==============================================================*/
create   index GL_ACCT_CAT_NDX_2 on GL_ACCOUNT_CATEGORY (
description
)
go


/*==============================================================*/
/* Index: GL_ACCT_CAT_NDX_1                                     */
/*==============================================================*/
create   index GL_ACCT_CAT_NDX_1 on GL_ACCOUNT_CATEGORY (
acct_type_id
)
go


