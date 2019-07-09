/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 6:44:43 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_CLIENT                                           */
/*==============================================================*/
create table PROJ_CLIENT (
   id                   int                  not null default 1
        constraint CKC_ID_PROJ_CLI check (id >= 1),
   bill_rate            numeric(11,2)        null,
   ot_bill_rate         numeric(11, 2)       null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_PROJ_CLIENT primary key  (id)
)
go


