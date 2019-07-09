/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: BUSINESS                                              */
/*==============================================================*/
print  'creating table: BUSINESS'
create table BUSINESS (
   id                   int                  identity default 2000000
        constraint CKC_ID_BUSINESS check (id >= 2000000),
   bus_type             int                  null default 3,
   serv_type            int                  null,
   longname             varchar(40)          null,
   shortname            varchar(15)          null,
   contact_firstname    varchar(25)          null,
   contact_lastname     varchar(25)          null,
   contact_phone        varchar(15)          null,
   contact_ext          varchar(10)          null,
   tax_id               varchar(20)          null,
   website              varchar(100)         null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   constraint PK_BUSINESS primary key  (id)
)
go


set identity_insert business on
go
insert into business
  (id, longname)
 values (2000000, 'test')
go
set identity_insert business off
go
delete from business
  where id = 2000000
go
/*==============================================================*/
/* Index: BUS_BUSINESSTYPE_NDX                                  */
/*==============================================================*/
create   index BUS_BUSINESSTYPE_NDX on BUSINESS (
bus_type
)
go


/*==============================================================*/
/* Index: BUS_LONGNAME_NDX                                      */
/*==============================================================*/
create   index BUS_LONGNAME_NDX on BUSINESS (
longname
)
go


/*==============================================================*/
/* Index: BUS_SERVICETYPE_NDX                                   */
/*==============================================================*/
create   index BUS_SERVICETYPE_NDX on BUSINESS (
serv_type
)
go


/*==============================================================*/
/* Index: BUS_SHORTNAME_NDX                                     */
/*==============================================================*/
create   index BUS_SHORTNAME_NDX on BUSINESS (
shortname
)
go


