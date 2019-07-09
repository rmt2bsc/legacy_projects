/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: SYSTEM_MESSAGE_DETAIL                                 */
/*==============================================================*/
print  'creating table: SYSTEM_MESSAGE_DETAIL'
create table SYSTEM_MESSAGE_DETAIL (
   id                   int                  not null,
   system_message_type_id int                  not null,
   description          varchar(255)         null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_SYS_MSG_DET primary key  (id)
)
go


BULK INSERT SYSTEM_MESSAGE_DETAIL
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\SYSTEM_MESSAGE_DETAIL.dat'
GO
/*==============================================================*/
/* Index: MSGTYPEID_NDX                                         */
/*==============================================================*/
create   index MSGTYPEID_NDX on SYSTEM_MESSAGE_DETAIL (
system_message_type_id
)
go


