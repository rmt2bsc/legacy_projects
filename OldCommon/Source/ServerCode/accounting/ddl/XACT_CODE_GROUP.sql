/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_CODE_GROUP                                       */
/*==============================================================*/
print  'creating table: XACT_CODE_GROUP'
create table XACT_CODE_GROUP (
   id                   int                  not null,
   description          varchar(40)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_XACT_CODE_GROUP primary key  (id)
)
go


BULK INSERT XACT_CODE_GROUP
   FROM 'C:\temp\accounting\ddl\XACT_CODE_GROUP.dat'
GO
