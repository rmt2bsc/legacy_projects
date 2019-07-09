/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: USER_DATABASE                                         */
/*==============================================================*/
print  'creating table: USER_DATABASE'
create table USER_DATABASE (
   id                   int                  identity,
   db_code              varchar(8)           null,
   description          varchar(25)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_USER_DATABASE primary key  (id)
)
go


