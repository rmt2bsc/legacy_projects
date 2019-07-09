/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: PURCHASE_ORDER_STATUS_HIST                            */
/*==============================================================*/
create table PURCHASE_ORDER_STATUS_HIST (
   id                   int                  identity,
   purchase_order_id    int                  null,
   purchase_order_status_id int                  null,
   effective_date       datetime             null,
   end_date             datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PURCHASE_ORDER_STATUS_HIST primary key  (id)
)
go


/*==============================================================*/
/* Index: Index_POSH1                                           */
/*==============================================================*/
create   index Index_POSH1 on PURCHASE_ORDER_STATUS_HIST (
purchase_order_id,
effective_date
)
go


