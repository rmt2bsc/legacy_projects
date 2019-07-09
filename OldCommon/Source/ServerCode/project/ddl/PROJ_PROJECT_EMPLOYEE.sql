/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/27/2006 11:41:33 PM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_PROJECT_EMPLOYEE                                 */
/*==============================================================*/
create table PROJ_PROJECT_EMPLOYEE (
   proj_project_id      int                  not null,
   proj_employee_id     int                  not null,
   constraint PK_PROJ_PROJECT_EMPLOYEE primary key  (proj_project_id, proj_employee_id)
)
go


