/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: PURCHASE_ORDER_RETURN                                 */
/*==============================================================*/
create table PURCHASE_ORDER_RETURN (
   id                   int                  identity,
   purchase_order_id    int                  null,
   xact_id              int                  null,
   reason               varchar(200)         null,
   constraint PK_PURCHASE_ORDER_RETURN primary key  (id)
)
go


