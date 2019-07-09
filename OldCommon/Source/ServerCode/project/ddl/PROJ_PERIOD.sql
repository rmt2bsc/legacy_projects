/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_PERIOD                                           */
/*==============================================================*/
create table PROJ_PERIOD (
   id                   numeric(18)          not null,
   prd_type             varchar(20)          null,
   max_reg_hrs          int                  null,
   constraint PK_PROJ_PERIOD primary key clustered (id)
         on PRIMARY
)
go


