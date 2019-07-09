/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: SALES_INVOICE                                         */
/*==============================================================*/
print  'creating table: SALES_INVOICE'
create table SALES_INVOICE (
   id                   int                  identity,
   sales_order_id       int                  null,
   invoice_no           varchar(50)          null,
   xact_id              int                  null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_SALES_INVOICE primary key  (id)
)
go


/*==============================================================*/
/* Index: sls_inv_ndx_1                                         */
/*==============================================================*/
create   index sls_inv_ndx_1 on SALES_INVOICE (
sales_order_id,
xact_id
)
go


/*==============================================================*/
/* Index: sls_inv_ndx_2                                         */
/*==============================================================*/
create   index sls_inv_ndx_2 on SALES_INVOICE (
sales_order_id
)
go


/*==============================================================*/
/* Index: sls_inv_ndx_3                                         */
/*==============================================================*/
create   index sls_inv_ndx_3 on SALES_INVOICE (
invoice_no
)
go


/*==============================================================*/
/* Index: sls_inv_ndx_4                                         */
/*==============================================================*/
create   index sls_inv_ndx_4 on SALES_INVOICE (
xact_id
)
go


