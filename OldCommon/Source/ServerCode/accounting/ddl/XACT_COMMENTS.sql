/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT_COMMENTS                                         */
/*==============================================================*/
print  'creating table: XACT_COMMENTS'
create table XACT_COMMENTS (
   id                   int                  identity,
   xact_id              int                  null,
   note                 varchar(1000)        null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_XACT_COMMENTS primary key  (id)
)
go


