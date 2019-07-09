/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: GL_ACCOUNTS                                           */
/*==============================================================*/
print  'creating table: GL_ACCOUNTS'
create table GL_ACCOUNTS (
   id                   int                  identity
        constraint CKC_ID_GL_ACCOU check (id >= 1),
   acct_type_id         int                  not null,
   acct_cat_id          int                  not null,
   acct_seq             int                  null,
   acct_no              varchar(25)          null,
   name                 varchar(100)         not null,
   code                 varchar(15)          null,
   description          varchar(255)         null,
   balance_type_id      int                  null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           not null default 'DBA',
   constraint PK_GL_ACCOUNTS primary key  (id)
)
go


BULK INSERT GL_ACCOUNTS
   FROM 'C:\temp\accounting\ddl\GL_ACCOUNTS.dat'
GO
/*==============================================================*/
/* Index: GL_ACCT_NDX_3                                         */
/*==============================================================*/
create   index GL_ACCT_NDX_3 on GL_ACCOUNTS (
name
)
go


/*==============================================================*/
/* Index: GL_ACCT_NDX_2                                         */
/*==============================================================*/
create   index GL_ACCT_NDX_2 on GL_ACCOUNTS (
acct_no
)
go


/*==============================================================*/
/* Index: GL_ACCT_NDX_1                                         */
/*==============================================================*/
create   index GL_ACCT_NDX_1 on GL_ACCOUNTS (
acct_type_id,
acct_cat_id,
acct_seq
)
go


