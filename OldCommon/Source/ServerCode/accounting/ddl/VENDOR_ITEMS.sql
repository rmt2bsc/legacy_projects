/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: VENDOR_ITEMS                                          */
/*==============================================================*/
create table VENDOR_ITEMS (
   item_master_id       int                  not null,
   vendor_id            int                  not null,
   vendor_item_no       varchar(25)          null,
   item_serial_no       varchar(25)          null,
   unit_cost            decimal(11,2)        null,
   constraint PK_VENDOR_ITEMS primary key  (item_master_id, vendor_id)
)
go


