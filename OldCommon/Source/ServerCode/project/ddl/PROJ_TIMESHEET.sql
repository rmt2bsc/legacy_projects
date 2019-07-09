/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/16/2006 1:20:40 PM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_TIMESHEET                                        */
/*==============================================================*/
create table PROJ_TIMESHEET (
   id                   int                  not null,
   proj_client_id       int                  null,
   proj_employee_id     int                  null,
   display_value        varchar(20)          null,
   begin_period         datetime             null,
   end_period           datetime             null,
   invoice_ref_no       varchar(30)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_TIMESHEET primary key  (id)
)
go


