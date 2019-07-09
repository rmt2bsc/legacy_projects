/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: state                                                 */
/*==============================================================*/
print  'creating table: state'
create table state (
   state_id             varchar(15)          not null,
   country_id           int                  null,
   state_name           varchar(80)          null,
   stt_void_ind         varchar(1)           null,
   constraint PK_STATE primary key  (state_id)
)
go


BULK INSERT state
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\state.dat'
GO
/*==============================================================*/
/* Index: state_ndx_1                                           */
/*==============================================================*/
create   index state_ndx_1 on state (
state_id,
country_id
)
go


/*==============================================================*/
/* Index: state_ndx_2                                           */
/*==============================================================*/
create   index state_ndx_2 on state (
state_id,
country_id,
state_name
)
go


/*==============================================================*/
/* Index: state_ndx_3                                           */
/*==============================================================*/
create   index state_ndx_3 on state (
state_name
)
go


