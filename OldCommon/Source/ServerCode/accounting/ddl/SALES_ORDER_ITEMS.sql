/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/30/2006 1:40:17 PM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: SALES_ORDER_ITEMS                                     */
/*==============================================================*/
create table SALES_ORDER_ITEMS (
   id                   int                  not null,
   sales_order_id       int                  not null,
   item_master_id       int                  null,
   item_name_override   varchar(80)          null,
   order_qty            numeric(8,2)         null,
   back_order_qty       numeric(8,2)         null,
   init_unit_cost       numeric(11,2)        null,
   init_markup          numeric(5,2)         null,
   constraint PK_SALES_ORDER_ITEMS primary key  (id, sales_order_id)
)
go


/*==============================================================*/
/* Index: sls_ord_items_ndx_1                                   */
/*==============================================================*/
create   index sls_ord_items_ndx_1 on SALES_ORDER_ITEMS (
sales_order_id,
item_master_id
)
go


/*==============================================================*/
/* Index: sls_ord_items_ndx_2                                   */
/*==============================================================*/
create   index sls_ord_items_ndx_2 on SALES_ORDER_ITEMS (
sales_order_id
)
go


/*==============================================================*/
/* Index: sls_ord_items_ndx_3                                   */
/*==============================================================*/
create   index sls_ord_items_ndx_3 on SALES_ORDER_ITEMS (
item_master_id
)
go


