/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: ADDRESS_COMMENTS                                      */
/*==============================================================*/
print  'creating table: ADDRESS_COMMENTS'
create table ADDRESS_COMMENTS (
   id                   int                  identity,
   address_id           int                  not null default autoincrement,
   data                 varchar(255)         null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   constraint PK_ADDRESS_COM primary key  (id)
)
go


