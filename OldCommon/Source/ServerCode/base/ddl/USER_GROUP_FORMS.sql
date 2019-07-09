/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: USER_GROUP_FORMS                                      */
/*==============================================================*/
print  'creating table: USER_GROUP_FORMS'
create table USER_GROUP_FORMS (
   id                   int                  identity,
   group_id             int                  null,
   access_code          varchar(8)           null,
   description          varchar(30)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_USER_GROUP_FORMS primary key  (id)
)
go


/*==============================================================*/
/* Index: user_grp_frm_ndx_1                                    */
/*==============================================================*/
create   index user_grp_frm_ndx_1 on USER_GROUP_FORMS (
group_id,
access_code
)
go


/*==============================================================*/
/* Index: user_grp_frm_ndx_2                                    */
/*==============================================================*/
create   index user_grp_frm_ndx_2 on USER_GROUP_FORMS (
description
)
go


