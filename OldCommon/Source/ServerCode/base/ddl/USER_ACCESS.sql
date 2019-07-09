/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: USER_ACCESS                                           */
/*==============================================================*/
print  'creating table: USER_ACCESS'
create table USER_ACCESS (
   id                   int                  identity,
   user_login_id        int                  null,
   group_id             int                  null,
   date_updated         datetime             null,
   date_created         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_USER_ACCESS primary key  (id)
)
go


/*==============================================================*/
/* Index: user_access_ndx_1                                     */
/*==============================================================*/
create   index user_access_ndx_1 on USER_ACCESS (
user_login_id,
group_id
)
go


