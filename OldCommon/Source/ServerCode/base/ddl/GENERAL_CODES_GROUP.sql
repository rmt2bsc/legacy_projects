/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: GENERAL_CODES_GROUP                                   */
/*==============================================================*/
print  'creating table: GENERAL_CODES_GROUP'
create table GENERAL_CODES_GROUP (
   id                   int                  not null,
   description          varchar(30)          null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   permcol              varchar(3)           null,
   constraint PK_GENERAL_CODES_GROUP primary key  (id)
)
go


BULK INSERT GENERAL_CODES_GROUP
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\GENERAL_CODES_GROUP.dat'
GO
