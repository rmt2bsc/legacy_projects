/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ITEM_MASTER                                           */
/*==============================================================*/
print  'creating table: ITEM_MASTER'
create table ITEM_MASTER (
   id                   int                  identity,
   vendor_id            int                  null,
   item_type_id         int                  null,
   description          varchar(80)          null,
   vendor_item_no       varchar(25)          null,
   item_serial_no       varchar(25)          null,
   qty_on_hand          int                  null,
   unit_cost            decimal(11,2)        null,
   markup               numeric(5,2)         null,
   retail_price         numeric(15,2)        null,
   override_retail      bit                  not null default 0,
   active               bit                  not null default 1,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_ITEM_MASTER primary key  (id)
)
go


/*==============================================================*/
/* Index: item_mast_ndx_1                                       */
/*==============================================================*/
create   index item_mast_ndx_1 on ITEM_MASTER (
vendor_id
)
go


/*==============================================================*/
/* Index: item_mast_ndx_2                                       */
/*==============================================================*/
create   index item_mast_ndx_2 on ITEM_MASTER (
vendor_id,
vendor_item_no,
item_serial_no
)
go


/*==============================================================*/
/* Index: item_mast_ndx_3                                       */
/*==============================================================*/
create   index item_mast_ndx_3 on ITEM_MASTER (
vendor_id
)
go


/*==============================================================*/
/* Index: item_mast_ndx_4                                       */
/*==============================================================*/
create   index item_mast_ndx_4 on ITEM_MASTER (
description
)
go


