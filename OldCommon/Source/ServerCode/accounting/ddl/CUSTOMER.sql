/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
print  'creating table: CUSTOMER'
create table CUSTOMER (
   id                   int                  identity default 1000
        constraint CKC_ID_CUSTOMER check (id >= 1000),
   gl_account_id        int                  null,
   account_no           varchar(30)          null,
   person_id            int                  null,
   business_id          int                  null,
   credit_limit         decimal(11,2)        null,
   description          varchar(300)         null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           not null default 'DBA',
   constraint PK_CUSTOMER primary key  (id)
)
go


set identity_insert fh_customer on
go
insert into fh_customer
  (id, credit_limit)
 values (1000, 0)
go
set identity_insert fh_customer off
go
delete from fh_customer
  where id = 1000
go
/*==============================================================*/
/* Index: cust_ndx_1                                            */
/*==============================================================*/
create   index cust_ndx_1 on CUSTOMER (
gl_account_id,
person_id,
business_id
)
go


/*==============================================================*/
/* Index: cust_ndx_2                                            */
/*==============================================================*/
create   index cust_ndx_2 on CUSTOMER (
person_id
)
go


/*==============================================================*/
/* Index: cust_ndx_3                                            */
/*==============================================================*/
create   index cust_ndx_3 on CUSTOMER (
business_id
)
go


/*==============================================================*/
/* Index: cust_ndx_4                                            */
/*==============================================================*/
create   index cust_ndx_4 on CUSTOMER (
person_id,
business_id
)
go


