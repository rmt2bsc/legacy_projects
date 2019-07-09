/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ACCT_PERIOD                                           */
/*==============================================================*/
print  'creating table: ACCT_PERIOD'
create table ACCT_PERIOD (
   id                   int                  not null,
   description          varchar(20)          null,
   constraint PK_ACCT_PERIOD primary key  (id)
)
go


BULK INSERT ACCT_PERIOD
   FROM 'C:\temp\accounting\ddl\ACCT_PERIOD.dat'
GO
