/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_POSTING                                          */
/*==============================================================*/
print  'creating table: XACT_POSTING'
create table XACT_POSTING (
   id                   int                  identity,
   gl_account_id        int                  not null,
   xact_id              int                  null,
   period               varchar(8)           not null,
   period_type_id       int                  null,
   post_amount          decimal(11,2)        null,
   post_date            datetime             null,
   post_ref_code        varchar(10)          null,
   date_created         datetime             not null default CURRENT_TIMESTAMP,
   date_updated         datetime             not null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           not null default 'DBA',
   constraint PK_XACT_POSTING primary key  (id)
)
go


/*==============================================================*/
/* Index: xact_post_ndx_1                                       */
/*==============================================================*/
create   index xact_post_ndx_1 on XACT_POSTING (
gl_account_id
)
go


/*==============================================================*/
/* Index: xact_post_ndx_2                                       */
/*==============================================================*/
create   index xact_post_ndx_2 on XACT_POSTING (
xact_id
)
go


/*==============================================================*/
/* Index: xact_post_ndx_3                                       */
/*==============================================================*/
create   index xact_post_ndx_3 on XACT_POSTING (
period
)
go


/*==============================================================*/
/* Index: xact_post_ndx_4                                       */
/*==============================================================*/
create   index xact_post_ndx_4 on XACT_POSTING (
gl_account_id,
xact_id
)
go


/*==============================================================*/
/* Index: xact_post_ndx_5                                       */
/*==============================================================*/
create   index xact_post_ndx_5 on XACT_POSTING (
gl_account_id,
xact_id,
period,
period_type_id
)
go


/*==============================================================*/
/* Index: xact_post_ndx_6                                       */
/*==============================================================*/
create   index xact_post_ndx_6 on XACT_POSTING (
period,
period_type_id
)
go


