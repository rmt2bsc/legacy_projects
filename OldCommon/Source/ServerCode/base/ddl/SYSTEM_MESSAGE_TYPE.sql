/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: SYSTEM_MESSAGE_TYPE                                   */
/*==============================================================*/
print  'creating table: SYSTEM_MESSAGE_TYPE'
create table SYSTEM_MESSAGE_TYPE (
   id                   int                  not null,
   description          varchar(10)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_SYSTEM_MESSAGE_TYPE primary key  (id)
)
go


BULK INSERT SYSTEM_MESSAGE_TYPE
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\SYSTEM_MESSAGE_TYPE.dat'
GO
