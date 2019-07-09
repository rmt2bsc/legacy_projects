/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: CREDITOR_ACTIVITY                                     */
/*==============================================================*/
print  'creating table: CREDITOR_ACTIVITY'
create table CREDITOR_ACTIVITY (
   id                   int                  identity,
   creditor_id          int                  null,
   xact_id              int                  null,
   amount               decimal(11,2)        null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_CREDITORS_ACTIVITY primary key  (id)
)
go


/*==============================================================*/
/* Index: cred_act_ndx_1                                        */
/*==============================================================*/
create   index cred_act_ndx_1 on CREDITOR_ACTIVITY (
creditor_id,
xact_id
)
go


/*==============================================================*/
/* Index: cred_act_ndx_2                                        */
/*==============================================================*/
create   index cred_act_ndx_2 on CREDITOR_ACTIVITY (
xact_id
)
go


