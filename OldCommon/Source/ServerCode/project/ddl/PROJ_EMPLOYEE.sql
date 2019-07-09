/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 6:44:43 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_EMPLOYEE                                         */
/*==============================================================*/
create table PROJ_EMPLOYEE (
   id                   int                  not null default 5000
        constraint CKC_ID_PROJ_EMP check (id >= 2000),
   person_id            int                  null,
   title_id             int                  null,
   type_id              int                  null,
   bill_rate            numeric(11,2)        null,
   ot_bill_rate         numeric(11, 2)       null,
   start_date           datetime             null,
   termination_date     datetime             null,
   manager_id           int                  null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_EMPLOYEE primary key  (id)
)
go


/*==============================================================*/
/* Index: PROJ_CONSULT_NDX_1                                    */
/*==============================================================*/
create   index PROJ_CONSULT_NDX_1 on PROJ_EMPLOYEE (
person_id,
title_id
)
go


/*==============================================================*/
/* Index: PROJ_CONSULT_NDX_2                                    */
/*==============================================================*/
create unique  index PROJ_CONSULT_NDX_2 on PROJ_EMPLOYEE (
person_id
)
go


