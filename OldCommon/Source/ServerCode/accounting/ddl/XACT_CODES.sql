/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_CODES                                            */
/*==============================================================*/
print  'creating table: XACT_CODES'
create table XACT_CODES (
   id                   int                  not null,
   group_id             int                  null,
   description          varchar(255)         null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_XACT_CODES primary key  (id)
)
go


BULK INSERT XACT_CODES
   FROM 'C:\temp\accounting\ddl\XACT_CODES.dat'
GO
/*==============================================================*/
/* Index: xact_codes_ndx_1                                      */
/*==============================================================*/
create   index xact_codes_ndx_1 on XACT_CODES (
group_id
)
go


/*==============================================================*/
/* Index: xact_codes_ndx_2                                      */
/*==============================================================*/
create   index xact_codes_ndx_2 on XACT_CODES (
description
)
go


