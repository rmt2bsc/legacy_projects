/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ASSET_DETAILS                                         */
/*==============================================================*/
print  'creating table: ASSET_DETAILS'
create table ASSET_DETAILS (
   gl_account_id        int                  not null default 0,
   account_number       varchar(35)          null,
   serial_number        varchar(30)          null,
   upc                  varchar(15)          null,
   make                 varchar(30)          null,
   title                varchar(30)          null,
   model                varchar(30)          null,
   mfg_date             datetime             null,
   date_acquired        datetime             null,
   date_disposed        datetime             null,
   pay_off_date         datetime             null,
   reciept_photo_id     int                  null,
   date_created         datetime             null,
   date_updated         datetime             null,
   user_id              varchar(8)           null,
   constraint PK_ASSET_DETAILS primary key  (gl_account_id)
)
go


/*==============================================================*/
/* Index: ASSETMASTER_1_NDX                                     */
/*==============================================================*/
create   index ASSETMASTER_1_NDX on ASSET_DETAILS (
serial_number
)
go


/*==============================================================*/
/* Index: ASSETMASTER_2_NDX                                     */
/*==============================================================*/
create   index ASSETMASTER_2_NDX on ASSET_DETAILS (
pay_off_date,
gl_account_id
)
go


/*==============================================================*/
/* Index: ASSETMASTER_3_NDX                                     */
/*==============================================================*/
create   index ASSETMASTER_3_NDX on ASSET_DETAILS (
serial_number,
upc,
make,
title,
pay_off_date,
gl_account_id
)
go


/*==============================================================*/
/* Index: hhhhhhh                                               */
/*==============================================================*/
create   index hhhhhhh on ASSET_DETAILS (
make
)
go


/*==============================================================*/
/* Index: b4444                                                 */
/*==============================================================*/
create   index b4444 on ASSET_DETAILS (
mfg_date
)
go


/*==============================================================*/
/* Index: jyht544                                               */
/*==============================================================*/
create   index jyht544 on ASSET_DETAILS (
title
)
go


/*==============================================================*/
/* Index: t5hgfjjjj                                             */
/*==============================================================*/
create   index t5hgfjjjj on ASSET_DETAILS (
gl_account_id
)
go


/*==============================================================*/
/* Index: yjy54332                                              */
/*==============================================================*/
create   index yjy54332 on ASSET_DETAILS (
date_acquired
)
go


/*==============================================================*/
/* Index: ju55y5jes                                             */
/*==============================================================*/
create   index ju55y5jes on ASSET_DETAILS (
date_disposed
)
go


/*==============================================================*/
/* Index: y64wyw4w                                              */
/*==============================================================*/
create   index y64wyw4w on ASSET_DETAILS (
pay_off_date
)
go


/*==============================================================*/
/* Index: ASSET_SN_NDX                                          */
/*==============================================================*/
create   index ASSET_SN_NDX on ASSET_DETAILS (
serial_number
)
go


/*==============================================================*/
/* Index: ASSETMASTER_UPC_NDX                                   */
/*==============================================================*/
create   index ASSETMASTER_UPC_NDX on ASSET_DETAILS (
upc
)
go


