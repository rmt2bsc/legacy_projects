/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_TIMESHEET_HIST                                   */
/*==============================================================*/
create table PROJ_TIMESHEET_HIST (
   id                   int                  identity,
   proj_timesheet_id    int                  not null,
   proj_timesheet_status_id int                  null,
   effective_date       datetime             null,
   end_date             datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_TIMESHEET_HIST primary key  (id)
)
go


/*==============================================================*/
/* Index: PROJ_EVNT_STAT_HIST_NDX_1                             */
/*==============================================================*/
create   index PROJ_EVNT_STAT_HIST_NDX_1 on PROJ_TIMESHEET_HIST (
proj_timesheet_id,
proj_timesheet_status_id,
effective_date,
end_date
)
go


