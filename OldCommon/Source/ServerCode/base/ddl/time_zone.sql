/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: time_zone                                             */
/*==============================================================*/
print  'creating table: time_zone'
create table time_zone (
   id                   int                  not null,
   descr                varchar(35)          null,
   constraint PK_TIME_ZONE primary key  (id)
)
go


BULK INSERT time_zone
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\time_zone.dat'
GO
