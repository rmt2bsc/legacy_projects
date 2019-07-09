/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: GENERAL_CODES                                         */
/*==============================================================*/
print  'creating table: GENERAL_CODES'
create table GENERAL_CODES (
   id                   int                  not null,
   group_id             int                  null,
   shortdesc            varchar(10)          null,
   longdesc             varchar(80)          null,
   gen_ind_value        varchar(3)           null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   permcol              varchar(3)           null,
   constraint PK_GENERAL_CODES primary key  (id)
)
go


BULK INSERT GENERAL_CODES
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\GENERAL_CODES.dat'
GO
/*==============================================================*/
/* Index: NDX_GENINDVALUE                                       */
/*==============================================================*/
create   index NDX_GENINDVALUE on GENERAL_CODES (
gen_ind_value
)
go


/*==============================================================*/
/* Index: NDX_LONGDESC                                          */
/*==============================================================*/
create   index NDX_LONGDESC on GENERAL_CODES (
longdesc
)
go


/*==============================================================*/
/* Index: NDX_SHORTDESC                                         */
/*==============================================================*/
create   index NDX_SHORTDESC on GENERAL_CODES (
shortdesc
)
go


