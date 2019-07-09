/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_EMPLOYEE_TITLE                                   */
/*==============================================================*/
print  'creating table: PROJ_EMPLOYEE_TITLE'
create table PROJ_EMPLOYEE_TITLE (
   id                   int                  not null
        constraint CKC_ID_PROJ_CON3 check (id >= 100),
   description          varchar(50)          null,
   constraint PK_PROJ_EMPLOYEE_TITLE primary key  (id)
)
go


BULK INSERT PROJ_EMPLOYEE_TITLE
   FROM 'C:\temp\project\ddl\PROJ_EMPLOYEE_TITLE.dat'
GO
