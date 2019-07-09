/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_CATEGORY                                         */
/*==============================================================*/
print  'creating table: XACT_CATEGORY'
create table XACT_CATEGORY (
   id                   int                  not null,
   description          varchar(80)          null,
   code                 varchar(10)          null,
   constraint PK_XACT_CATEGORY primary key  (id)
)
on PRIMARY
go


BULK INSERT XACT_CATEGORY
   FROM 'C:\temp\accounting\ddl\XACT_CATEGORY.dat'
GO
