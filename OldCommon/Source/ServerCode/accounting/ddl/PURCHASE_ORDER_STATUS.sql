/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: PURCHASE_ORDER_STATUS                                 */
/*==============================================================*/
print  'creating table: PURCHASE_ORDER_STATUS'
create table PURCHASE_ORDER_STATUS (
   id                   int                  not null,
   description          varchar(25)          null,
   constraint PK_PURCHASE_ORDER_STATUS primary key  (id)
)
go


BULK INSERT PURCHASE_ORDER_STATUS
   FROM 'C:\temp\accounting\ddl\PURCHASE_ORDER_STATUS.dat'
GO
