/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ACCT_PREF                                             */
/*==============================================================*/
print  'creating table: ACCT_PREF'
create table ACCT_PREF (
   id                   int                  identity,
   acct_prd_type_id     int                  null default 1,
   acct_prd_beg_month   smallint             null default 1
        constraint CKC_ACCT_PRD_BEG_MONT_ACCT_PRE check (acct_prd_beg_month is null or (acct_prd_beg_month between 1 and 12 )),
   constraint PK_ACCT_PREF primary key  (id)
)
go


BULK INSERT ACCT_PREF
   FROM 'C:\temp\accounting\ddl\ACCT_PREF.dat'
GO
