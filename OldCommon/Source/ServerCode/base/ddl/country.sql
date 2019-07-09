/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: country                                               */
/*==============================================================*/
print  'creating table: country'
create table country (
   id                   int                  not null,
   name                 varchar(80)          null,
   cntry_void_ind       varchar(1)           null,
   constraint PK_COUNTRY primary key  (id)
)
go


BULK INSERT country
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\country.dat'
GO
/*==============================================================*/
/* Index: country_ndx_1                                         */
/*==============================================================*/
create   index country_ndx_1 on country (
name
)
go


