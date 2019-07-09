/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ITEM_MASTER_TYPE                                      */
/*==============================================================*/
print  'creating table: ITEM_MASTER_TYPE'
create table ITEM_MASTER_TYPE (
   id                   int                  identity,
   description          varchar(50)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_ITEM_MASTER_TYPE primary key  (id)
)
go


BULK INSERT ITEM_MASTER_TYPE
   FROM 'C:\temp\accounting\ddl\ITEM_MASTER_TYPE.dat'
GO
