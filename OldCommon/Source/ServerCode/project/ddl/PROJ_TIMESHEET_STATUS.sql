/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     10/30/2006 1:36:14 AM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: PROJ_TIMESHEET_STATUS                                 */
/*==============================================================*/
print  'creating table: PROJ_TIMESHEET_STATUS'
create table PROJ_TIMESHEET_STATUS (
   id                   int                  not null,
   name                 varchar(25)          null,
   description          varchar(500)         null,
   constraint PK_PROJ_TIMESHEET_STATUS primary key  (id)
)
go


BULK INSERT PROJ_TIMESHEET_STATUS
   FROM 'C:\temp\project\ddl\PROJ_TIMESHEET_STATUS.dat'
GO
