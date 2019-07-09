/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_UPLOAD                                           */
/*==============================================================*/
create table XACT_UPLOAD (
   code                 varchar(5)           null,
   date                 datetime             null,
   descr                varchar(100)         null,
   amt                  numeric(18,2)        null
)
go


