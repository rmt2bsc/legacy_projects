/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* View: VW_TIMESHEET_EVENT_LIST                                */
/*==============================================================*/
create view VW_TIMESHEET_EVENT_LIST as
  SELECT PROJ_EVENT.id event_id,   
         PROJ_EVENT.event_date,   
         PROJ_EVENT.hours,   
         PROJ_EVENT.date_created event_date_created,
         PROJ_PROJECT_TASK.id project_task_id,   
         PROJ_PROJECT_TASK.proj_timesheet_id timesheet_id,   
         PROJ_PROJECT_TASK.proj_project_id project_id,   
         PROJ_PROJECT_TASK.proj_task_id task_id,   
         PROJ_PROJECT.proj_client_id client_id,   
         PROJ_PROJECT.description project_name,   
         PROJ_PROJECT.bill_rate,   
         PROJ_PROJECT.ot_bill_rate,   
         PROJ_PROJECT.effective_date,   
         PROJ_PROJECT.end_date,   
         PROJ_TASK.description task_name,   
         PROJ_TASK.billable
    FROM PROJ_PROJECT,   
         PROJ_PROJECT_TASK,   
         PROJ_TASK,   
         PROJ_EVENT  
   WHERE ( PROJ_PROJECT_TASK.proj_project_id = PROJ_PROJECT.id ) and  
         ( PROJ_TASK.id = PROJ_PROJECT_TASK.proj_task_id ) and  
         ( PROJ_EVENT.proj_project_task_id = PROJ_PROJECT_TASK.id )
go


