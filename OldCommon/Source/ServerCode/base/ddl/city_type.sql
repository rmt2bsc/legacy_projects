/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: city_type                                             */
/*==============================================================*/
print  'creating table: city_type'
create table city_type (
   id                   varchar(1)           not null,
   descr                varchar(35)          null,
   constraint PK_CITY_TYPE primary key  (id)
)
go


BULK INSERT city_type
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\city_type.dat'
GO
