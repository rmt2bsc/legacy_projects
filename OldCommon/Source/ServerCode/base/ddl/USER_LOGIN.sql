/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: USER_LOGIN                                            */
/*==============================================================*/
print  'creating table: USER_LOGIN'
create table USER_LOGIN (
   id                   int                  identity,
   employee_id          int                  null,
   login                varchar(8)           null,
   description          varchar(30)          null,
   password             varchar(500)         null,
   total_logons         int                  null,
   active               bit                  null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null,
   constraint PK_USERS primary key  (id)
)
go


BULK INSERT USER_LOGIN
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\USER_LOGIN.dat'
GO
/*==============================================================*/
/* Index: USERNAME_NDX2                                         */
/*==============================================================*/
create   index USERNAME_NDX2 on USER_LOGIN (
description
)
go


/*==============================================================*/
/* Index: user_login_ndx_1                                      */
/*==============================================================*/
create   index user_login_ndx_1 on USER_LOGIN (
employee_id
)
go


/*==============================================================*/
/* Index: user_login_ndx_2                                      */
/*==============================================================*/
create   index user_login_ndx_2 on USER_LOGIN (
login
)
go


