/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     1/9/2007 7:41:54 AM                          */
/*==============================================================*/


alter table PROJ_EMPLOYEE
   drop constraint FK_PROJ_EMP_REF_PROJ_EMP
go


alter table PROJ_EMPLOYEE
   drop constraint FK_PROJ_EMP_REF_PROJ_EMP2
go


alter table PROJ_EMPLOYEE
   drop constraint FK_PROJ_EMP_REF_PROJ_EMP1
go


alter table PROJ_EVENT
   drop constraint FK_PROJ_EVE_REF_PROJ_PRO_TASK
go


alter table PROJ_EVENT
   drop constraint FK_PROJ_EVE_REFERENCE_PROJ_PRO
go


alter table PROJ_PREF
   drop constraint FK_PROJPREF__PROJPER_02
go


alter table PROJ_PREF
   drop constraint FK_PROJPRE_PROJPER_01
go


alter table PROJ_PROJECT
   drop constraint FK_PROJ_PRO_REF_PROJ_CLI
go


alter table PROJ_PROJECT_TASK
   drop constraint FK_PROJ_PRO_REF_PROJ_PRO_TASK
go


alter table PROJ_PROJECT_TASK
   drop constraint FK_PROJ_PRO_TASK_REF_PROJ_TIM
go


alter table PROJ_PROJECT_TASK
   drop constraint FK_PROJ_PRO_TASK_REF_PROJ_TASK
go


alter table PROJ_TIMESHEET
   drop constraint FK_PROJ_TIM_REFERENCE_PROJ_EMP
go


alter table PROJ_TIMESHEET
   drop constraint FK_PROJ_TIM_REF_PROJ_CLI
go


alter table PROJ_TIMESHEET_HIST
   drop constraint FK_PROJ_EVE_REF_PROJ_EVE_hist
go


alter table PROJ_TIMESHEET_HIST
   drop constraint FK_PROJ_EVE_REF_PROJ_EMP_TSH
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_EMPLOYEE')
            and   name  = 'PROJ_CONSULT_NDX_1'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_EMPLOYEE.PROJ_CONSULT_NDX_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_EMPLOYEE')
            and   name  = 'PROJ_CONSULT_NDX_2'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_EMPLOYEE.PROJ_CONSULT_NDX_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_EVENT')
            and   name  = 'PROJ_EVNT_DTL_1'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_EVENT.PROJ_EVNT_DTL_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_EVENT')
            and   name  = 'PROJ_EVNT_DTL_3'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_EVENT.PROJ_EVNT_DTL_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_EVENT')
            and   name  = 'PROJ_EVNT_DTL_4'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_EVENT.PROJ_EVNT_DTL_4
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_PROJECT')
            and   name  = 'PROJ_PROJECT_NDX_1'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_PROJECT.PROJ_PROJECT_NDX_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PROJ_TIMESHEET_HIST')
            and   name  = 'PROJ_EVNT_STAT_HIST_NDX_1'
            and   indid > 0
            and   indid < 255)
   drop index PROJ_TIMESHEET_HIST.PROJ_EVNT_STAT_HIST_NDX_1
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_USER')
            and   type = 'V')
   drop view VW_USER
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_TIMESHEET_PROJECT_TASK')
            and   type = 'V')
   drop view VW_TIMESHEET_PROJECT_TASK
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_TIMESHEET_LIST')
            and   type = 'V')
   drop view VW_TIMESHEET_LIST
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_TIMESHEET_HOURS')
            and   type = 'V')
   drop view VW_TIMESHEET_HOURS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_TIMESHEET_EVENT_LIST')
            and   type = 'V')
   drop view VW_TIMESHEET_EVENT_LIST
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_PROJECT_EMPLOYEE_EXT')
            and   type = 'V')
   drop view VW_PROJECT_EMPLOYEE_EXT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_EMPLOYEE_EXT')
            and   type = 'V')
   drop view VW_EMPLOYEE_EXT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_CUSTOMER_NAME')
            and   type = 'V')
   drop view VW_CUSTOMER_NAME
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_CLIENT_TIMESHEET_STATUS_SUMMARY')
            and   type = 'V')
   drop view VW_CLIENT_TIMESHEET_STATUS_SUMMARY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_CLIENT_EXT')
            and   type = 'V')
   drop view VW_CLIENT_EXT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_CLIENT')
            and   type = 'U')
   drop table PROJ_CLIENT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_EMPLOYEE')
            and   type = 'U')
   drop table PROJ_EMPLOYEE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_EMPLOYEE_TITLE')
            and   type = 'U')
   drop table PROJ_EMPLOYEE_TITLE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_EMPLOYEE_TYPE')
            and   type = 'U')
   drop table PROJ_EMPLOYEE_TYPE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_EVENT')
            and   type = 'U')
   drop table PROJ_EVENT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_PERIOD')
            and   type = 'U')
   drop table PROJ_PERIOD
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_PREF')
            and   type = 'U')
   drop table PROJ_PREF
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_PROJECT')
            and   type = 'U')
   drop table PROJ_PROJECT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_PROJECT_TASK')
            and   type = 'U')
   drop table PROJ_PROJECT_TASK
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_TASK')
            and   type = 'U')
   drop table PROJ_TASK
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_TIMESHEET')
            and   type = 'U')
   drop table PROJ_TIMESHEET
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_TIMESHEET_HIST')
            and   type = 'U')
   drop table PROJ_TIMESHEET_HIST
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PROJ_TIMESHEET_STATUS')
            and   type = 'U')
   drop table PROJ_TIMESHEET_STATUS
go


isql PROJ_CLIENT.sql
go


isql PROJ_EMPLOYEE.sql
go


isql PROJ_EMPLOYEE_TITLE.sql
go


isql PROJ_EMPLOYEE_TYPE.sql
go


isql PROJ_EVENT.sql
go


isql PROJ_PERIOD.sql
go


isql PROJ_PREF.sql
go


isql PROJ_PROJECT.sql
go


isql PROJ_PROJECT_TASK.sql
go


isql PROJ_TASK.sql
go


isql PROJ_TIMESHEET.sql
go


isql PROJ_TIMESHEET_HIST.sql
go


isql PROJ_TIMESHEET_STATUS.sql
go


isql VW_CLIENT_EXT.sql
go


isql VW_CLIENT_TIMESHEET_STATUS_SUMMARY.sql
go


isql VW_CUSTOMER_NAME.sql
go


isql VW_EMPLOYEE_EXT.sql
go


isql VW_PROJECT_EMPLOYEE_EXT.sql
go


isql VW_TIMESHEET_EVENT_LIST.sql
go


isql VW_TIMESHEET_HOURS.sql
go


isql VW_TIMESHEET_LIST.sql
go


isql VW_TIMESHEET_PROJECT_TASK.sql
go


isql VW_USER.sql
go


alter table PROJ_EMPLOYEE
   add constraint FK_PROJ_EMP_REF_PROJ_EMP foreign key (manager_id)
      references PROJ_EMPLOYEE (id)
go


alter table PROJ_EMPLOYEE
   add constraint FK_PROJ_EMP_REF_PROJ_EMP2 foreign key (title_id)
      references PROJ_EMPLOYEE_TITLE (id)
go


alter table PROJ_EMPLOYEE
   add constraint FK_PROJ_EMP_REF_PROJ_EMP1 foreign key (type_id)
      references PROJ_EMPLOYEE_TYPE (id)
go


alter table PROJ_EVENT
   add constraint FK_PROJ_EVE_REF_PROJ_PRO_TASK foreign key (proj_project_task_id)
      references PROJ_PROJECT_TASK (id)
go


alter table PROJ_EVENT
   add constraint FK_PROJ_EVE_REFERENCE_PROJ_PRO foreign key (proj_project_task_id)
      references PROJ_PROJECT (id)
go


alter table PROJ_PREF
   add constraint FK_PROJPREF__PROJPER_02 foreign key (email_confirm)
      references PROJ_PERIOD (id)
go


alter table PROJ_PREF
   add constraint FK_PROJPRE_PROJPER_01 foreign key (timesheet_base)
      references PROJ_PERIOD (id)
go


alter table PROJ_PROJECT
   add constraint FK_PROJ_PRO_REF_PROJ_CLI foreign key (proj_client_id)
      references PROJ_CLIENT (id)
go


alter table PROJ_PROJECT_TASK
   add constraint FK_PROJ_PRO_REF_PROJ_PRO_TASK foreign key (proj_project_id)
      references PROJ_PROJECT (id)
go


alter table PROJ_PROJECT_TASK
   add constraint FK_PROJ_PRO_TASK_REF_PROJ_TIM foreign key (proj_timesheet_id)
      references PROJ_TIMESHEET (id)
go


alter table PROJ_PROJECT_TASK
   add constraint FK_PROJ_PRO_TASK_REF_PROJ_TASK foreign key (proj_task_id)
      references PROJ_TASK (id)
go


alter table PROJ_TIMESHEET
   add constraint FK_PROJ_TIM_REFERENCE_PROJ_EMP foreign key (proj_employee_id)
      references PROJ_EMPLOYEE (id)
go


alter table PROJ_TIMESHEET
   add constraint FK_PROJ_TIM_REF_PROJ_CLI foreign key (proj_client_id)
      references PROJ_CLIENT (id)
go


alter table PROJ_TIMESHEET_HIST
   add constraint FK_PROJ_EVE_REF_PROJ_EVE_hist foreign key (proj_timesheet_status_id)
      references PROJ_TIMESHEET_STATUS (id)
go


alter table PROJ_TIMESHEET_HIST
   add constraint FK_PROJ_EVE_REF_PROJ_EMP_TSH foreign key (proj_timesheet_id)
      references PROJ_TIMESHEET (id)
go


