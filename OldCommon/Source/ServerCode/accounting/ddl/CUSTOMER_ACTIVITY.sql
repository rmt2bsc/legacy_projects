/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: CUSTOMER_ACTIVITY                                     */
/*==============================================================*/
print  'creating table: CUSTOMER_ACTIVITY'
create table CUSTOMER_ACTIVITY (
   Id                   int                  identity,
   customer_id          int                  not null default autoincrement,
   xact_id              int                  null,
   amount               decimal(11,2)        not null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   User_Id              varchar(8)           not null default 'DBA',
   constraint PK_CUSTOMER_ACTIVITY primary key  (Id)
)
go


/*==============================================================*/
/* Index: cust_act_ndx_1                                        */
/*==============================================================*/
create   index cust_act_ndx_1 on CUSTOMER_ACTIVITY (
customer_id,
xact_id
)
go


/*==============================================================*/
/* Index: cust_act_ndx_2                                        */
/*==============================================================*/
create   index cust_act_ndx_2 on CUSTOMER_ACTIVITY (
customer_id
)
go


/*==============================================================*/
/* Index: cust_act_ndx_3                                        */
/*==============================================================*/
create   index cust_act_ndx_3 on CUSTOMER_ACTIVITY (
xact_id
)
go


