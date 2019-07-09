/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: GL_SUBSIDIARY                                         */
/*==============================================================*/
print  'creating table: GL_SUBSIDIARY'
create table GL_SUBSIDIARY (
   id                   varchar(10)          not null,
   gl_account_id        int                  null,
   activity_table       varchar(50)          null,
   description          varchar(100)         null,
   constraint PK_GL_SUBSIDIARY primary key  (id)
)
go


BULK INSERT GL_SUBSIDIARY
   FROM 'C:\temp\accounting\ddl\GL_SUBSIDIARY.dat'
GO
