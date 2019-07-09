/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_TYPE_ITEM                                        */
/*==============================================================*/
print  'creating table: XACT_TYPE_ITEM'
create table XACT_TYPE_ITEM (
   id                   int                  not null,
   xact_type_id         int                  not null,
   name                 varchar(35)          null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           not null default 'DBA',
   constraint PK_XACT_TYPE_ITEM primary key  (id)
)
go


BULK INSERT XACT_TYPE_ITEM
   FROM 'C:\temp\accounting\ddl\XACT_TYPE_ITEM.dat'
GO
