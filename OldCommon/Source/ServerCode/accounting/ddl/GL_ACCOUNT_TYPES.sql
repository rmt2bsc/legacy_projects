/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: GL_ACCOUNT_TYPES                                      */
/*==============================================================*/
print  'creating table: GL_ACCOUNT_TYPES'
create table GL_ACCOUNT_TYPES (
   id                   int                  identity,
   description          varchar(40)          not null,
   balance_type_id      int                  null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           not null default 'DBA',
   constraint PK_GL_ACCOUNT_TYPES primary key  (id)
)
go


BULK INSERT GL_ACCOUNT_TYPES
   FROM 'C:\temp\accounting\ddl\GL_ACCOUNT_TYPES.dat'
GO
/*==============================================================*/
/* Index: DESC1                                                 */
/*==============================================================*/
create   index DESC1 on GL_ACCOUNT_TYPES (
description
)
go


