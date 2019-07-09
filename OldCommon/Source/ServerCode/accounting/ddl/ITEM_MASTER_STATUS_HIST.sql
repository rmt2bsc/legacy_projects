/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ITEM_MASTER_STATUS_HIST                               */
/*==============================================================*/
create table ITEM_MASTER_STATUS_HIST (
   id                   int                  identity,
   item_master_id       int                  null,
   item_status_id       int                  null,
   unit_cost            decimal(11,2)        null,
   markup               numeric(5,2)         null,
   effective_date       datetime             null,
   end_date             datetime             null,
   reason               varchar(100)         null,
   date_created         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_ITEM_MASTER_STATUS_HIST primary key  (id)
)
go


/*==============================================================*/
/* Index: item_mast_stat_hst_ndx_1                              */
/*==============================================================*/
create   index item_mast_stat_hst_ndx_1 on ITEM_MASTER_STATUS_HIST (
item_master_id,
item_status_id
)
go


/*==============================================================*/
/* Index: item_mast_stat_hst_ndx_2                              */
/*==============================================================*/
create   index item_mast_stat_hst_ndx_2 on ITEM_MASTER_STATUS_HIST (
item_master_id
)
go


/*==============================================================*/
/* Index: item_mast_stat_hst_ndx_3                              */
/*==============================================================*/
create   index item_mast_stat_hst_ndx_3 on ITEM_MASTER_STATUS_HIST (
item_status_id
)
go


/*==============================================================*/
/* Index: item_mast_stat_hst_ndx_4                              */
/*==============================================================*/
create   index item_mast_stat_hst_ndx_4 on ITEM_MASTER_STATUS_HIST (
effective_date
)
go


