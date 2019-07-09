/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     1/20/2007 2:29:35 PM                         */
/*==============================================================*/


/*==============================================================*/
/* View: VW_TIMESHEET_LIST                                      */
/*==============================================================*/
create view VW_TIMESHEET_LIST as
select
   PROJ_TIMESHEET.id timesheet_id,
   PROJ_TIMESHEET.proj_client_id,
   PROJ_TIMESHEET.proj_employee_id,
   PROJ_TIMESHEET.display_value,
   PROJ_TIMESHEET.begin_period,
   PROJ_TIMESHEET.end_period,
   PROJ_TIMESHEET.invoice_ref_no,
   PROJ_TIMESHEET_HIST.proj_timesheet_status_id,
   PROJ_TIMESHEET_HIST.effective_date status_effective_date,
   PROJ_TIMESHEET_HIST.end_date status_end_date,
   PROJ_TIMESHEET_HIST.id proj_timesheet_hist_id,
   PROJ_TIMESHEET_STATUS.name status_name,
   PROJ_TIMESHEET_STATUS.description status_description,
   VW_EMPLOYEE_EXT.type_id,
   VW_EMPLOYEE_EXT.shortname,
   VW_EMPLOYEE_EXT.manager_id,
   VW_EMPLOYEE_EXT.ot_bill_rate,
   VW_EMPLOYEE_EXT.bill_rate,
   VW_EMPLOYEE_EXT.generation,
   VW_EMPLOYEE_EXT.birth_date,
   VW_EMPLOYEE_EXT.lastname + ', ' + VW_EMPLOYEE_EXT.firstname last_first_name,
   vw_client_ext.longname client_name,
   vw_client_ext.account_no,
   vw_client_ext.balance,
   (select sum(pe.hours)             from proj_event pe,                     proj_project_task ppt,                     proj_task pt            where pe.proj_project_task_id = ppt.id                 and ppt.proj_timesheet_id  = PROJ_TIMESHEET.id                 and ppt.proj_task_id = pt.id                and pt.billable = 1                and pe.hours is not null) bill_hrs,
   (select sum(pe.hours)             from proj_event pe,                     proj_project_task ppt,                     proj_task pt            where pe.proj_project_task_id = ppt.id                 and ppt.proj_timesheet_id  = PROJ_TIMESHEET.id                 and ppt.proj_task_id = pt.id                and pt.billable = 0                and pe.hours is not null) non_bill_hrs
from
   PROJ_TIMESHEET,
   PROJ_TIMESHEET_HIST,
   PROJ_TIMESHEET_STATUS,
   VW_EMPLOYEE_EXT,
   vw_client_ext
where
   (PROJ_TIMESHEET_HIST.proj_timesheet_id = PROJ_TIMESHEET.id)
   and ( PROJ_TIMESHEET_HIST.proj_timesheet_status_id = PROJ_TIMESHEET_STATUS.id)
   and ( PROJ_TIMESHEET_HIST.end_date is null)
   and PROJ_TIMESHEET.proj_employee_id = VW_EMPLOYEE_EXT.employee_id
   and vw_client_ext.client_id = PROJ_TIMESHEET.proj_client_id
go


