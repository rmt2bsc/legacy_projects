/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/16/2006 1:19:12 PM                        */
/*==============================================================*/


/*==============================================================*/
/* Table: XACT                                                  */
/*==============================================================*/
print  'creating table: XACT'
create table XACT (
   id                   int                  identity,
   xact_date            datetime             not null,
   xact_amount          decimal(11,2)        null,
   xact_type_id         int                  not null,
   xact_subtype_id      int                  null,
   tender_id            int                  null,
   neg_instr_no         varchar(30)          null,
   bank_trans_ind       varchar(1)           null,
   confirm_no           varchar(20)          null,
   entity_ref_no        varchar(30)          null,
   posted_date          datetime             null,
   reason               varchar(500)         null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   constraint PK_XACT primary key  (id)
)
go


/*==============================================================*/
/* Index: GL_TRANS1_NDX                                         */
/*==============================================================*/
create unique  index GL_TRANS1_NDX on XACT (
id,
xact_type_id,
xact_date
)
go


/*==============================================================*/
/* Index: GL_TRANS2_NDX                                         */
/*==============================================================*/
create unique  index GL_TRANS2_NDX on XACT (
id,
xact_type_id,
xact_date,
bank_trans_ind
)
go


/*==============================================================*/
/* Index: GL_TRANSBANKIND_NDX                                   */
/*==============================================================*/
create   index GL_TRANSBANKIND_NDX on XACT (
bank_trans_ind
)
go


/*==============================================================*/
/* Index: GL_TRANSDATE_NDX                                      */
/*==============================================================*/
create   index GL_TRANSDATE_NDX on XACT (
xact_date
)
go


/*==============================================================*/
/* Index: GL_TRANSDATETYPE_NDX                                  */
/*==============================================================*/
create unique  index GL_TRANSDATETYPE_NDX on XACT (
id,
xact_date,
posted_date,
xact_type_id
)
go


/*==============================================================*/
/* Index: GL_TRANSGROUP_NDX                                     */
/*==============================================================*/
create   index GL_TRANSGROUP_NDX on XACT (
xact_date,
xact_type_id,
posted_date,
bank_trans_ind
)
go


/*==============================================================*/
/* Index: GL_TRANSPE_NDX                                        */
/*==============================================================*/
create   index GL_TRANSPE_NDX on XACT (
posted_date
)
go


