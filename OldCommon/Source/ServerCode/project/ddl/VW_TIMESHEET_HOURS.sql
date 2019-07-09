/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/2/2007 9:25:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: VW_TIMESHEET_HOURS                                     */
/*==============================================================*/
create view VW_TIMESHEET_HOURS as
SELECT   PROJ_TIMESHEET.id timesheet_id,
         PROJ_TIMESHEET.proj_client_id client_id,
         PROJ_TIMESHEET.proj_employee_id employee_id,
         PROJ_TIMESHEET.display_value,
         PROJ_TIMESHEET.begin_period timesheet_begin_period,
         PROJ_TIMESHEET.end_period timesheet_end_period,
         PROJ_TIMESHEET.invoice_ref_no,                   
	     PROJ_PROJECT.id project_id,
	     PROJ_TASK.id task_id,   
         PROJ_EVENT.id event_id,   
	     PROJ_PROJECT_TASK.proj_task_id,
         PROJ_PROJECT.description project_name,   
         PROJ_TASK.description task_name,   
         PROJ_PROJECT.effective_date,   
         PROJ_PROJECT.end_date,   
         PROJ_TASK.billable,   
         PROJ_EVENT.event_date,   
         PROJ_EVENT.hours,   
         PROJ_EVENT.date_created  
    FROM PROJ_EVENT,   
         PROJ_PROJECT,   
         PROJ_TASK,
         PROJ_PROJECT_TASK,
         PROJ_TIMESHEET
   WHERE PROJ_TIMESHEET.id = PROJ_PROJECT_TASK.proj_timesheet_id and
         PROJ_PROJECT_TASK.proj_project_id = PROJ_PROJECT.id and
         PROJ_PROJECT_TASK.proj_task_id = PROJ_TASK.id and
         PROJ_PROJECT_TASK.id = PROJ_EVENT.proj_project_task_id
go


