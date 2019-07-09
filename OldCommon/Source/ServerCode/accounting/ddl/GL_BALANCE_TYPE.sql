/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: GL_BALANCE_TYPE                                       */
/*==============================================================*/
print  'creating table: GL_BALANCE_TYPE'
create table GL_BALANCE_TYPE (
   id                   int                  not null,
   long_desc            varchar(15)          null,
   short_desc           varchar(3)           null,
   constraint PK_GL_BALANCE_TYPE primary key  (id)
)
go


BULK INSERT GL_BALANCE_TYPE
   FROM 'C:\temp\accounting\ddl\GL_BALANCE_TYPE.dat'
GO
