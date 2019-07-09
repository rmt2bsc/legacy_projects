/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: BATCH_JOB                                             */
/*==============================================================*/
print  'creating table: BATCH_JOB'
create table BATCH_JOB (
   id                   varchar(20)          not null,
   description          varchar(50)          null,
   constraint PK_BATCH_JOB primary key  (id)
)
go


BULK INSERT BATCH_JOB
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\BATCH_JOB.dat'
GO
