/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: SYSTEM_PREF                                           */
/*==============================================================*/
print  'creating table: SYSTEM_PREF'
create table SYSTEM_PREF (
   id                   int                  not null,
   main_key             varchar(30)          not null,
   sub_key              varchar(30)          null,
   descr                varchar(100)         null,
   constraint PK_SYSTEM_PREF primary key  (id)
)
go


BULK INSERT SYSTEM_PREF
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\SYSTEM_PREF.dat'
GO
