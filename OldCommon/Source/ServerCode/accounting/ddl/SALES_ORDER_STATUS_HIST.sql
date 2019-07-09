/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: SALES_ORDER_STATUS_HIST                               */
/*==============================================================*/
create table SALES_ORDER_STATUS_HIST (
   id                   int                  identity,
   sales_order_id       int                  null,
   sales_order_status_id int                  null,
   effective_date       datetime             null,
   end_date             datetime             null,
   reason               varchar(100)         null,
   date_created         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_SALES_ORDER_STATUS_HIST primary key  (id)
)
go


/*==============================================================*/
/* Index: sls_ord_sts_hist_ndx_1                                */
/*==============================================================*/
create   index sls_ord_sts_hist_ndx_1 on SALES_ORDER_STATUS_HIST (
sales_order_id,
sales_order_status_id,
effective_date
)
go


/*==============================================================*/
/* Index: sls_ord_sts_hist_ndx_2                                */
/*==============================================================*/
create   index sls_ord_sts_hist_ndx_2 on SALES_ORDER_STATUS_HIST (
sales_order_id
)
go


