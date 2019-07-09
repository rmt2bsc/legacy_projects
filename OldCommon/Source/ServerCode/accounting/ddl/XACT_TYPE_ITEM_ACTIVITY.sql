/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_TYPE_ITEM_ACTIVITY                               */
/*==============================================================*/
print  'creating table: XACT_TYPE_ITEM_ACTIVITY'
create table XACT_TYPE_ITEM_ACTIVITY (
   Id                   int                  identity,
   xact_id              int                  not null,
   xact_type_item_id    int                  not null,
   amount               decimal(11,2)        not null,
   description          varchar(50)          null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_Id              varchar(8)           not null default 'DBA',
   constraint PK_XACT_TYPE_ITEM_ACTIVITY primary key  (Id)
)
go


/*==============================================================*/
/* Index: exp_act_ndx_1                                         */
/*==============================================================*/
create   index exp_act_ndx_1 on XACT_TYPE_ITEM_ACTIVITY (
xact_id
)
go


/*==============================================================*/
/* Index: exp_act_ndx_2                                         */
/*==============================================================*/
create   index exp_act_ndx_2 on XACT_TYPE_ITEM_ACTIVITY (
xact_type_item_id
)
go


