/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 8:25:59 AM                        */
/*==============================================================*/


/*==============================================================*/
/* View: VW_TIMESHEET_PROJECT_TASK                              */
/*==============================================================*/
create view VW_TIMESHEET_PROJECT_TASK as
select
   PROJ_PROJECT_TASK.id project_task_id,
   PROJ_PROJECT_TASK.proj_timesheet_id timesheet_id,
   PROJ_PROJECT_TASK.proj_project_id project_id,
   PROJ_PROJECT_TASK.proj_task_id task_id,
   PROJ_PROJECT.proj_client_id client_id,
   PROJ_PROJECT.description project_name,
   PROJ_PROJECT.effective_date,
   PROJ_PROJECT.end_date,
   PROJ_TASK.description task_name,
   PROJ_TASK.billable
from
   PROJ_PROJECT,
   PROJ_PROJECT_TASK,
   PROJ_TASK
where
   (PROJ_PROJECT_TASK.proj_project_id = PROJ_PROJECT.id)
   and ( PROJ_TASK.id = PROJ_PROJECT_TASK.proj_task_id)
go


