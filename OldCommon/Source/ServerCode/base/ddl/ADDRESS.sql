/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: ADDRESS                                               */
/*==============================================================*/
print  'creating table: ADDRESS'
create table ADDRESS (
   id                   int                  identity,
   person_id            int                  null,
   business_id          int                  null,
   addr1                varchar(25)          null,
   addr2                varchar(25)          null,
   addr3                varchar(25)          null,
   addr4                varchar(25)          null,
   zip                  int                  null,
   zipext               varchar(4)           null,
   phone_home           varchar(13)          null,
   phone_work           varchar(13)          null,
   phone_ext            varchar(8)           null,
   phone_main           varchar(13)          null,
   phone_cell           varchar(13)          null,
   phone_fax            varchar(13)          null,
   phone_pager          varchar(13)          null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   constraint PK_ADDRESS primary key  (id)
)
go


/*==============================================================*/
/* Index: ADDRESS_1_NDX                                         */
/*==============================================================*/
create unique  index ADDRESS_1_NDX on ADDRESS (
id,
person_id
)
go


/*==============================================================*/
/* Index: ADDRESS_2_NDX                                         */
/*==============================================================*/
create unique  index ADDRESS_2_NDX on ADDRESS (
id
)
go


/*==============================================================*/
/* Index: ADDRESS_3_NDX                                         */
/*==============================================================*/
create unique  index ADDRESS_3_NDX on ADDRESS (
person_id,
id
)
go


/*==============================================================*/
/* Index: ADDRESS_4_NDX                                         */
/*==============================================================*/
create unique  index ADDRESS_4_NDX on ADDRESS (
id
)
go


/*==============================================================*/
/* Index: ADDRESS_6_NDX                                         */
/*==============================================================*/
create unique  index ADDRESS_6_NDX on ADDRESS (
id
)
go


/*==============================================================*/
/* Index: ADDRESS_ADDR1_NDX                                     */
/*==============================================================*/
create   index ADDRESS_ADDR1_NDX on ADDRESS (
addr1
)
go


/*==============================================================*/
/* Index: ADDRESS_ADDR2_NDX                                     */
/*==============================================================*/
create   index ADDRESS_ADDR2_NDX on ADDRESS (
addr2
)
go


/*==============================================================*/
/* Index: ADDRESS_ADDR3_NDX                                     */
/*==============================================================*/
create   index ADDRESS_ADDR3_NDX on ADDRESS (
addr3
)
go


/*==============================================================*/
/* Index: ADDRESS_ADDR4_NDX                                     */
/*==============================================================*/
create   index ADDRESS_ADDR4_NDX on ADDRESS (
addr4
)
go


/*==============================================================*/
/* Index: ADDRESS_ZIP_NDX                                       */
/*==============================================================*/
create   index ADDRESS_ZIP_NDX on ADDRESS (
zip
)
go


/*==============================================================*/
/* Index: ADDR_ZIPEXT_NDX                                       */
/*==============================================================*/
create   index ADDR_ZIPEXT_NDX on ADDRESS (
zip,
zipext
)
go


/*==============================================================*/
/* Index: ADDRESS_ZIPEXT_NDX                                    */
/*==============================================================*/
create   index ADDRESS_ZIPEXT_NDX on ADDRESS (
zipext
)
go


