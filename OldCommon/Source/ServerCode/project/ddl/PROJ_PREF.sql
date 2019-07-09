/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_PREF                                             */
/*==============================================================*/
create table PROJ_PREF (
   end_period_day       varchar(2)           not null,
   timesheet_base       varchar(20)          null,
   email_confirm        varchar(2)           null
)
go


