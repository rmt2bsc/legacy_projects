/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: PURCHASE_ORDER                                        */
/*==============================================================*/
print  'creating table: PURCHASE_ORDER'
create table PURCHASE_ORDER (
   id                   int                  identity,
   vendor_id            int                  null,
   status               int                  null,
   xact_id              int                  null,
   ref_no               varchar(25)          null,
   total                numeric(11,2)        null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PURCHASE_ORDER primary key  (id)
)
go


