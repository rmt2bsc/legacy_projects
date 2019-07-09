/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: PERSON                                                */
/*==============================================================*/
print  'creating table: PERSON'
create table PERSON (
   id                   int                  identity default 1000000
        constraint CKC_ID_PERSON check (id >= 1000000),
   firstname            varchar(20)          null,
   midname              varchar(20)          null,
   lastname             varchar(20)          null,
   maidenname           varchar(20)          null,
   generation           varchar(4)           null,
   shortname            varchar(40)          null,
   title                int                  null,
   gender_id            int                  null,
   marital_status       int                  null,
   birth_date           datetime             null,
   race_id              int                  null,
   ssn                  varchar(20)          null,
   email                varchar(50)          null,
   date_created         datetime             null default CURRENT_TIMESTAMP,
   date_updated         datetime             null default CURRENT_TIMESTAMP,
   user_id              varchar(8)           null default 'DBA',
   constraint PK_PERSON primary key  (id)
)
go


set identity_insert person on
go
insert into person 
  (id, firstname)
 values (1000000, 'test')
go
set identity_insert person off
go
delete from person 
  where id = 1000000
go
/*==============================================================*/
/* Index: PER_BIRTHDATE_NDX                                     */
/*==============================================================*/
create   index PER_BIRTHDATE_NDX on PERSON (
birth_date
)
go


/*==============================================================*/
/* Index: PER_FIRSTNAME_NDX                                     */
/*==============================================================*/
create   index PER_FIRSTNAME_NDX on PERSON (
firstname
)
go


/*==============================================================*/
/* Index: PER_FULLNAME_NDX                                      */
/*==============================================================*/
create   index PER_FULLNAME_NDX on PERSON (
lastname,
firstname,
midname
)
go


/*==============================================================*/
/* Index: PER_GENERATION_NDX                                    */
/*==============================================================*/
create   index PER_GENERATION_NDX on PERSON (
generation
)
go


/*==============================================================*/
/* Index: PER_LASTNAME_NDX                                      */
/*==============================================================*/
create   index PER_LASTNAME_NDX on PERSON (
lastname
)
go


/*==============================================================*/
/* Index: PER_MAIDENNAME_NDX                                    */
/*==============================================================*/
create   index PER_MAIDENNAME_NDX on PERSON (
maidenname
)
go


/*==============================================================*/
/* Index: PER_MS_NDX                                            */
/*==============================================================*/
create   index PER_MS_NDX on PERSON (
marital_status
)
go


/*==============================================================*/
/* Index: PER_RACE_ID_NDX                                       */
/*==============================================================*/
create   index PER_RACE_ID_NDX on PERSON (
race_id
)
go


/*==============================================================*/
/* Index: PER_SHORTNAME_NDX                                     */
/*==============================================================*/
create   index PER_SHORTNAME_NDX on PERSON (
shortname
)
go


/*==============================================================*/
/* Index: PER_SSN_NDX                                           */
/*==============================================================*/
create   index PER_SSN_NDX on PERSON (
ssn
)
go


/*==============================================================*/
/* Index: PERSONAL_1_NDX                                        */
/*==============================================================*/
create   index PERSONAL_1_NDX on PERSON (
lastname,
firstname,
midname,
gender_id,
marital_status,
race_id
)
go


/*==============================================================*/
/* Index: PERSONAL_2_NDX                                        */
/*==============================================================*/
create   index PERSONAL_2_NDX on PERSON (
shortname,
gender_id,
marital_status,
race_id
)
go


