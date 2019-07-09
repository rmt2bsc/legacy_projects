/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_EVENT                                            */
/*==============================================================*/
create table PROJ_EVENT (
   id                   int                  identity
        constraint CKC_ID_PROJECT_2 check (id >= 1),
   proj_project_task_id int                  null,
   event_date           datetime             null,
   hours                numeric(5, 2)        null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_EVENT primary key  (id)
)
go


/*==============================================================*/
/* Index: PROJ_EVNT_DTL_1                                       */
/*==============================================================*/
create   index PROJ_EVNT_DTL_1 on PROJ_EVENT (
proj_project_task_id
)
go


/*==============================================================*/
/* Index: PROJ_EVNT_DTL_3                                       */
/*==============================================================*/
create   index PROJ_EVNT_DTL_3 on PROJ_EVENT (
event_date
)
go


/*==============================================================*/
/* Index: PROJ_EVNT_DTL_4                                       */
/*==============================================================*/
create unique  index PROJ_EVNT_DTL_4 on PROJ_EVENT (
proj_project_task_id,
event_date
)
go


