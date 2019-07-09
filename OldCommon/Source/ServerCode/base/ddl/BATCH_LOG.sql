/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: BATCH_LOG                                             */
/*==============================================================*/
print  'creating table: BATCH_LOG'
create table BATCH_LOG (
   id                   int                  identity,
   batch_job_id         varchar(20)          not null,
   msg                  varchar(2000)        not null,
   batch_date           datetime             not null,
   user_id              varchar(8)           not null,
   constraint PK_BATCH_LOG primary key  (id)
)
go


sp_bindefault "CURRENT_TIMESTAMP", 'BATCH_LOG.batch_date'
go


