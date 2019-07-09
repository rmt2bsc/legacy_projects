/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: user_connect_xref                                     */
/*==============================================================*/
create table user_connect_xref (
   id                   int                  identity,
   con_id               varchar(50)          null,
   con_user_id          varchar(20)          null,
   app_user_id          varchar(20)          null,
   constraint PK_USER_CONNECT_XREF primary key  (id)
)
go


