/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 7:46:15 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_PROJECT                                          */
/*==============================================================*/
create table PROJ_PROJECT (
   id                   int                  identity,
   proj_client_id       int                  null,
   description          varchar(50)          null,
   effective_date       datetime             null,
   end_date             datetime             null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_PROJECT primary key  (id)
)
go


/*==============================================================*/
/* Index: PROJ_PROJECT_NDX_1                                    */
/*==============================================================*/
create   index PROJ_PROJECT_NDX_1 on PROJ_PROJECT (
proj_client_id
)
go


