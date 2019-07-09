/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: CREDITOR_TYPE                                         */
/*==============================================================*/
print  'creating table: CREDITOR_TYPE'
create table CREDITOR_TYPE (
   id                   int                  identity,
   description          varchar(20)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_CREDITOR_TYPE primary key  (id)
)
go


BULK INSERT CREDITOR_TYPE
   FROM 'C:\temp\accounting\ddl\CREDITOR_TYPE.dat'
GO
