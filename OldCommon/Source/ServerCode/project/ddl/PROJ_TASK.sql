/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_TASK                                             */
/*==============================================================*/
print  'creating table: PROJ_TASK'
create table PROJ_TASK (
   id                   int                  identity,
   description          varchar(50)          null,
   billable             bit                  null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_TASK primary key  (id)
)
go


