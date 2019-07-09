/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: PURCHASE_ORDER_ITEMS                                  */
/*==============================================================*/
print  'creating table: PURCHASE_ORDER_ITEMS'
create table PURCHASE_ORDER_ITEMS (
   id                   int                  not null,
   purchase_order_id    int                  not null,
   item_master_id       int                  null,
   unit_cost            decimal(11,2)        null,
   qty                  int                  null,
   qty_rcvd             int                  null,
   qty_rtn              int                  null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PURCHASE_ORDER_ITEM primary key  (id, purchase_order_id)
)
go


/*==============================================================*/
/* Index: prchorditem_ndx_1                                     */
/*==============================================================*/
create   index prchorditem_ndx_1 on PURCHASE_ORDER_ITEMS (
purchase_order_id,
item_master_id
)
go


