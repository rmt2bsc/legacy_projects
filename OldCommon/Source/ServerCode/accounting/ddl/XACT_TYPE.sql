/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_TYPE                                             */
/*==============================================================*/
print  'creating table: XACT_TYPE'
create table XACT_TYPE (
   id                   int                  not null,
   xact_category_id     int                  not null,
   description          varchar(80)          null,
   xact_code            varchar(10)          null,
   to_multiplier        int                  null,
   from_multiplier      int                  null,
   to_acct_type_id      int                  null,
   to_acct_catg_id      int                  null,
   from_acct_type_id    int                  null,
   from_acct_catg_id    int                  null,
   has_subsidiary       bit                  not null,
   constraint PK_XACT_TYPE primary key  (id)
)
on PRIMARY
go


sp_bindefault False, 'XACT_TYPE.has_subsidiary'
go


BULK INSERT XACT_TYPE
   FROM 'C:\temp\accounting\ddl\XACT_TYPE.dat'
GO
/*==============================================================*/
/* Index: xact_type_ndx_1                                       */
/*==============================================================*/
create   index xact_type_ndx_1 on XACT_TYPE (
xact_category_id,
description
)
go


/*==============================================================*/
/* Index: xact_type_ndx_2                                       */
/*==============================================================*/
create   index xact_type_ndx_2 on XACT_TYPE (
xact_category_id,
to_acct_type_id,
from_acct_type_id
)
go


/*==============================================================*/
/* Index: xact_type_ndx_3                                       */
/*==============================================================*/
create   index xact_type_ndx_3 on XACT_TYPE (
description
)
go


