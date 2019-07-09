/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: SALES_ORDER                                           */
/*==============================================================*/
print  'creating table: SALES_ORDER'
create table SALES_ORDER (
   id                   int                  identity default 10000
        constraint CKC_ID_SALES_OR check (id >= 10000),
   customer_id          int                  null,
   invoiced             bit                  null default 0,
   order_total          numeric(11, 2)       null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_SALES_ORDER primary key  (id)
)
go


/*==============================================================*/
/* Index: sls_ord_ndx_1                                         */
/*==============================================================*/
create   index sls_ord_ndx_1 on SALES_ORDER (
customer_id,
invoiced
)
go


/*==============================================================*/
/* Index: sls_ord_ndx_2                                         */
/*==============================================================*/
create   index sls_ord_ndx_2 on SALES_ORDER (
customer_id
)
go


/*==============================================================*/
/* Index: sls_ord_ndx_3                                         */
/*==============================================================*/
create   index sls_ord_ndx_3 on SALES_ORDER (
invoiced
)
go


