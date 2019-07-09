/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: CREDITOR                                              */
/*==============================================================*/
print  'creating table: CREDITOR'
create table CREDITOR (
   id                   int                  identity default 22000
        constraint CKC_ID_CREDITOR check (id >= 22000),
   gl_account_id        int                  null,
   business_id          int                  null,
   creditor_type_id     int                  null,
   account_number       varchar(25)          null,
   credit_limit         decimal(11,2)        null,
   apr                  decimal(11,2)        null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_CREDITORS primary key  (id)
)
go


set identity_insert fh_creditor on
go
insert into fh_creditor 
  (id, account_number)
 values (22000, 'test')
go
set identity_insert fh_creditor off
go
delete from fh_creditor 
  where id = 22000
go
/*==============================================================*/
/* Index: creditor_ndx_1                                        */
/*==============================================================*/
create   index creditor_ndx_1 on CREDITOR (
gl_account_id,
business_id,
creditor_type_id
)
go


/*==============================================================*/
/* Index: creditor_ndx_2                                        */
/*==============================================================*/
create   index creditor_ndx_2 on CREDITOR (
account_number
)
go


