/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: USER_GROUP                                            */
/*==============================================================*/
print  'creating table: USER_GROUP'
create table USER_GROUP (
   id                   int                  identity,
   description          varchar(25)          null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_USER_GROUP primary key  (id)
)
go


BULK INSERT USER_GROUP
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\USER_GROUP.dat'
GO
