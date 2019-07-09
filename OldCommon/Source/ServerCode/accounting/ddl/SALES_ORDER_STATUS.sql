/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: SALES_ORDER_STATUS                                    */
/*==============================================================*/
print  'creating table: SALES_ORDER_STATUS'
create table SALES_ORDER_STATUS (
   id                   int                  not null,
   description          varchar(50)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_SALES_ORDER_STATUS primary key  (id)
)
go


BULK INSERT SALES_ORDER_STATUS
   FROM 'C:\temp\accounting\ddl\SALES_ORDER_STATUS.dat'
GO
