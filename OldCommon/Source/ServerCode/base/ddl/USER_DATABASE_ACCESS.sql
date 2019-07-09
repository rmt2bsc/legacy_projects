/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: USER_DATABASE_ACCESS                                  */
/*==============================================================*/
print  'creating table: USER_DATABASE_ACCESS'
create table USER_DATABASE_ACCESS (
   id                   int                  not null,
   user_login_id        int                  null,
   user_database_id     int                  null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_USER_DATABASE_ACCESS primary key  (id)
)
go


/*==============================================================*/
/* Index: user_db_access_ndx_1                                  */
/*==============================================================*/
create   index user_db_access_ndx_1 on USER_DATABASE_ACCESS (
user_login_id,
user_database_id
)
go


