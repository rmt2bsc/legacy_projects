/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: PURCHASE_ORDER_ITEM_RETURN                            */
/*==============================================================*/
create table PURCHASE_ORDER_ITEM_RETURN (
   id                   int                  not null,
   purchase_order_return_id int                  not null,
   purchase_order_item_id int                  not null,
   qty_rtn              int                  not null,
   constraint PK_PURCHASE_ORDER_ITEM_RETURN primary key  (id)
)
go


