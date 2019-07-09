/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_PROJECT_TASK                                     */
/*==============================================================*/
create table PROJ_PROJECT_TASK (
   id                   int                  identity,
   proj_timesheet_id    int                  null,
   proj_project_id      int                  null,
   proj_task_id         int                  null,
   constraint PK_PROJ_PROJECT_TASK primary key  (id)
)
go


