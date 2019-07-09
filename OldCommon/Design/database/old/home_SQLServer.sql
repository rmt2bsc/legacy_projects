/* ============================================================ */
/*   Database name:  Model_6                                    */
/*   DBMS name:      Microsoft SQL Server 6.x                   */
/*   Created on:     8/11/02  10:17 PM                          */
/* ============================================================ */

/* ============================================================ */
/*   Database name:  Model_6                                    */
/* ============================================================ */
create database Model_6
go

/* ============================================================ */
/*   Table: NULLTABLE                                           */
/* ============================================================ */
create table NULLTABLE
(
    NULLCOL                integer               null    
)
go

/* ============================================================ */
/*   Table: SYSTEM_KEYS                                         */
/* ============================================================ */
create table SYSTEM_KEYS
(
    TABLE_ID               integer               identity,
    TABLENAME              varchar(40)           null    ,
    MULTI_COLUMN_KEY       varchar(3)            null    ,
    COLUMN_ID              varchar(100)          null    ,
    NEXT_KEY_VALUE         varchar(10)           null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    ACTIVE_IND             char(1)               null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    constraint PK_SYSTEM_KEYS primary key (TABLE_ID)
)
go

/* ============================================================ */
/*   Index: NDX_MULTI_COLUMN_KEY                                */
/* ============================================================ */
create index NDX_MULTI_COLUMN_KEY on SYSTEM_KEYS (MULTI_COLUMN_KEY)
go

/* ============================================================ */
/*   Index: NDX_TABLENAME                                       */
/* ============================================================ */
create index NDX_TABLENAME on SYSTEM_KEYS (TABLENAME)
go

/* ============================================================ */
/*   Index: NDX_TABLENAMENEXTKEYVALUE                           */
/* ============================================================ */
create index NDX_TABLENAMENEXTKEYVALUE on SYSTEM_KEYS (TABLENAME, NEXT_KEY_VALUE)
go

/* ============================================================ */
/*   Table: COMMENTS                                            */
/* ============================================================ */
create table COMMENTS
(
    COMMENT_ID             integer               not null,
    COMMENT_TEXT           varchar(1000)         null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    USER_ID                char(8)               null    
        default 'DBA',
    constraint PK_COMMENTS primary key (COMMENT_ID)
)
go

/* ============================================================ */
/*   Table: GENERAL_CODES_GROUP                                 */
/* ============================================================ */
create table GENERAL_CODES_GROUP
(
    GROUP_ID               integer               not null,
    DESCRIPTION            varchar(30)           null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    permanent              varchar(3)            null    ,
    constraint PK_GENERAL_CODES_GROUP primary key (GROUP_ID)
)
go

/* ============================================================ */
/*   Table: GL_ACCOUNT_TYPES                                    */
/* ============================================================ */
create table GL_ACCOUNT_TYPES
(
    ACCT_TYPE              integer               identity,
    DESCRIPTION            char(40)              not null,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    constraint PK_GL_ACCOUNT_TYPES primary key (ACCT_TYPE)
)
go

/* ============================================================ */
/*   Index: DESC1                                               */
/* ============================================================ */
create index DESC1 on GL_ACCOUNT_TYPES (DESCRIPTION)
go

/* ============================================================ */
/*   Table: STATES_CODE                                         */
/* ============================================================ */
create table STATES_CODE
(
    STATE_ID               integer               not null,
    SHORTNAME              char(3)               not null,
    LONGNAME               char(25)              not null,
    STATE_CAPITAL          integer               null    ,
    COUNTRY_ID             integer               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    permanent              varchar(3)            null    ,
    constraint PK_STATES_CODE primary key (STATE_ID)
)
go

/* ============================================================ */
/*   Index: NDX_STATECAPITAL                                    */
/* ============================================================ */
create index NDX_STATECAPITAL on STATES_CODE (STATE_CAPITAL)
go

/* ============================================================ */
/*   Index: NDX_STATESMULTI                                     */
/* ============================================================ */
create unique index NDX_STATESMULTI on STATES_CODE (COUNTRY_ID, STATE_ID, STATE_CAPITAL)
go

/* ============================================================ */
/*   Table: project_master                                      */
/* ============================================================ */
create table project_master
(
    id                     integer               identity
        constraint CKC_ID_PROJECT_ check (id >= 1000),
    description            varchar(30)           null    ,
    begin_date             datetime              null    ,
    end_date               datetime              null    ,
    estimated_hours        decimal(8,2)          null    ,
    actual_hours           decimal(8,2)          null    ,
    build_no               integer               null    ,
    major_version          integer               null    ,
    minor_version          integer               null    ,
    date_created           datetime              null    ,
    date_updated           datetime              null    ,
    user_id                varchar(8)            null    ,
    constraint PK_PROJECT_MASTER primary key (id)
)
go

/* ============================================================ */
/*   Table: TAXES_AND_DEDUCTIONS                                */
/* ============================================================ */
create table TAXES_AND_DEDUCTIONS
(
    TAXDED_ID              integer               identity,
    CITY_TAX               numeric(20,4)         null    
        default 0,
    STATE_TAX              numeric(20,4)         null    
        default 0,
    FEDERAL_TAX            numeric(20,4)         null    
        default 0,
    SALES_TAX              numeric(20,4)         null    
        default 0,
    PROPERTY_TAX           numeric(20,4)         null    
        default 0,
    INSURANCE_TAX          numeric(20,4)         null    
        default 0,
    LUXURY_TAX             numeric(20,4)         null    
        default 0,
    MEDICAID_TAX           numeric(20,4)         null    
        default 0,
    SOCIAL_SEC_DED         numeric(20,4)         null    
        default 0,
    MEDICAL_INS_DED        numeric(20,4)         null    
        default 0,
    DENTAL_DED             numeric(20,4)         null    
        default 0,
    OTHER_DED              numeric(20,4)         null    
        default 0,
    OTHER_TAX              numeric(20,4)         null    
        default 0,
    OTHER_DED_COMMENT_ID   integer               null    ,
    OTHER_TAX_COMMENT_ID   integer               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    OTHER_DED_COMMENTS_ID  integer               null    ,
    OTHER_TAX_COMMENTS_ID  integer               null    ,
    constraint PK_TAXES_AND_DEDUCTIONS primary key (TAXDED_ID)
)
go

/* ============================================================ */
/*   Table: GENERAL_CODES                                       */
/* ============================================================ */
create table GENERAL_CODES
(
    CODE_ID                integer               not null,
    GROUP_ID               integer               null    ,
    SHORTDESC              varchar(10)           null    ,
    LONGDESC               varchar(80)           null    ,
    COUNTRY_ID             integer               null    ,
    GEN_IND_VALUE          varchar(3)            null    ,
    BUTTON_API_VALUE       integer               null    ,
    BUTTON_DEFAULT         integer               null    ,
    ICON_API_VALUE         integer               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    permanent              varchar(3)            null    ,
    constraint PK_GENERAL_CODES primary key (CODE_ID)
)
go

/* ============================================================ */
/*   Index: NDX_BUTTONAPIVALUE                                  */
/* ============================================================ */
create index NDX_BUTTONAPIVALUE on GENERAL_CODES (BUTTON_API_VALUE)
go

/* ============================================================ */
/*   Index: NDX_BUTTONDEFAULT                                   */
/* ============================================================ */
create index NDX_BUTTONDEFAULT on GENERAL_CODES (BUTTON_DEFAULT)
go

/* ============================================================ */
/*   Index: NDX_GENINDVALUE                                     */
/* ============================================================ */
create index NDX_GENINDVALUE on GENERAL_CODES (GEN_IND_VALUE)
go

/* ============================================================ */
/*   Index: NDX_ICONAPIVALUE                                    */
/* ============================================================ */
create index NDX_ICONAPIVALUE on GENERAL_CODES (ICON_API_VALUE)
go

/* ============================================================ */
/*   Index: NDX_LONGDESC                                        */
/* ============================================================ */
create index NDX_LONGDESC on GENERAL_CODES (LONGDESC)
go

/* ============================================================ */
/*   Index: NDX_SHORTDESC                                       */
/* ============================================================ */
create index NDX_SHORTDESC on GENERAL_CODES (SHORTDESC)
go

/* ============================================================ */
/*   Table: ADDRESS                                             */
/* ============================================================ */
create table ADDRESS
(
    ID                     integer               not null,
    ADDRESSTYPE_ID         integer               not null,
    PERSONAL_ID            integer               null    ,
    BUSINESS_ID            integer               null    ,
    ADDRESS1               char(25)              null    ,
    ADDRESS2               char(25)              null    ,
    ADDRESS3               char(25)              null    ,
    ADDRESS4               char(25)              null    ,
    CITY_ID                integer               null    ,
    STATE_ID               integer               null    ,
    COUNTRY_ID             integer               null    ,
    ZIP_CODE               char(5)               null    ,
    ZIP_EXT                char(4)               null    ,
    PHONE_HOME             varchar(13)           null    ,
    PHONE_WORK             varchar(13)           null    ,
    PHONE_WORKEXT          varchar(8)            null    ,
    PHONE_BUSINESS         varchar(13)           null    ,
    PHONE_CELLULAR         varchar(13)           null    ,
    PHONE_FAX              varchar(13)           null    ,
    PHONE_PAGER            varchar(13)           null    ,
    PHONE_OTHER            varchar(13)           null    ,
    INTERNET_ADDRESS       char(80)              null    ,
    EMAIL                  char(50)              null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    USER_ID                char(8)               null    
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_ADDRESS primary key (ID)
)
go

/* ============================================================ */
/*   Index: ADDERSS_EMAIL_NDX                                   */
/* ============================================================ */
create index ADDERSS_EMAIL_NDX on ADDRESS (EMAIL)
go

/* ============================================================ */
/*   Index: ADDRESS_1_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_1_NDX on ADDRESS (COUNTRY_ID, STATE_ID, CITY_ID, ID, PERSONAL_ID)
go

/* ============================================================ */
/*   Index: ADDRESS_2_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_2_NDX on ADDRESS (COUNTRY_ID, STATE_ID, CITY_ID, ID)
go

/* ============================================================ */
/*   Index: ADDRESS_3_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_3_NDX on ADDRESS (PERSONAL_ID, ID, ADDRESSTYPE_ID)
go

/* ============================================================ */
/*   Index: ADDRESS_4_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_4_NDX on ADDRESS (ID, ADDRESSTYPE_ID)
go

/* ============================================================ */
/*   Index: ADDRESS_5_NDX                                       */
/* ============================================================ */
create index ADDRESS_5_NDX on ADDRESS (CITY_ID, STATE_ID)
go

/* ============================================================ */
/*   Index: ADDRESS_6_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_6_NDX on ADDRESS (ID, ADDRESSTYPE_ID, CITY_ID, STATE_ID)
go

/* ============================================================ */
/*   Index: ESS1_NDX                                            */
/* ============================================================ */
create index ESS1_NDX on ADDRESS (ADDRESS1)
go

/* ============================================================ */
/*   Index: RESS2_NDX                                           */
/* ============================================================ */
create index RESS2_NDX on ADDRESS (ADDRESS2)
go

/* ============================================================ */
/*   Index: ESS3_NDX                                            */
/* ============================================================ */
create index ESS3_NDX on ADDRESS (ADDRESS3)
go

/* ============================================================ */
/*   Index: RESS4_NDX                                           */
/* ============================================================ */
create index RESS4_NDX on ADDRESS (ADDRESS4)
go

/* ============================================================ */
/*   Index: RYID_NDX                                            */
/* ============================================================ */
create index RYID_NDX on ADDRESS (COUNTRY_ID)
go

/* ============================================================ */
/*   Index: T_NDX                                               */
/* ============================================================ */
create index T_NDX on ADDRESS (INTERNET_ADDRESS)
go

/* ============================================================ */
/*   Index: IP_NDX                                              */
/* ============================================================ */
create index IP_NDX on ADDRESS (ZIP_CODE)
go

/* ============================================================ */
/*   Index: PEXT_NDX                                            */
/* ============================================================ */
create index PEXT_NDX on ADDRESS (ZIP_CODE, ZIP_EXT)
go

/* ============================================================ */
/*   Index: EXT_NDX                                             */
/* ============================================================ */
create index EXT_NDX on ADDRESS (ZIP_EXT)
go

/* ============================================================ */
/*   Table: ASSET_MASTER                                        */
/* ============================================================ */
create table ASSET_MASTER
(
    ID                     integer               identity,
    ITEM_ID                integer               not null,
    SERIAL_NUMBER          char(30)              null    ,
    UPC                    char(15)              null    ,
    ADDRESS_ID             integer               null    ,
    PHONE_ID               integer               null    ,
    ASSET_MAKE             char(30)              null    ,
    ASSET_TITLE            char(30)              null    ,
    ASSET_MFG_DATE         datetime              null    ,
    DATE_ACQUIRED          datetime              null    ,
    DATE_DISPOSED          datetime              null    ,
    PAY_OFF_DATE           datetime              null    ,
    QUANTITY               smallint              null    ,
    ASSET_VALUE            numeric(20,4)         null    
        default 0,
    SALE_VALUE             numeric(20,4)         null    
        default 0,
    TAXDED_ID              integer               null    ,
    DETAIL_IND             char(1)               null    ,
    PAYTEND_ID             integer               not null,
    INTEREST_IND           char(1)               null    ,
    PHOTOID                integer               null    ,
    RECIEPT_PHOTOID        integer               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_ASSET_MASTER primary key (ID)
)
go

/* ============================================================ */
/*   Index: TMASTER_1_NDX                                       */
/* ============================================================ */
create index TMASTER_1_NDX on ASSET_MASTER (ITEM_ID, SERIAL_NUMBER)
go

/* ============================================================ */
/*   Index: ASSETMASTER_2_NDX                                   */
/* ============================================================ */
create index ASSETMASTER_2_NDX on ASSET_MASTER (ITEM_ID, PAY_OFF_DATE, QUANTITY, ASSET_VALUE)
go

/* ============================================================ */
/*   Index: ASSETMASTER_3_NDX                                   */
/* ============================================================ */
create index ASSETMASTER_3_NDX on ASSET_MASTER (SERIAL_NUMBER, UPC, ASSET_MAKE, ASSET_TITLE, PAY_OFF_DATE, QUANTITY, ASSET_VALUE)
go

/* ============================================================ */
/*   Index: O_NDX                                               */
/* ============================================================ */
create index O_NDX on ASSET_MASTER (ADDRESS_ID, PHOTOID)
go

/* ============================================================ */
/*   Index: MAKE_NDX                                            */
/* ============================================================ */
create index MAKE_NDX on ASSET_MASTER (ASSET_MAKE)
go

/* ============================================================ */
/*   Index: GDATE_NDX                                           */
/* ============================================================ */
create index GDATE_NDX on ASSET_MASTER (ASSET_MFG_DATE)
go

/* ============================================================ */
/*   Index: TLE_NDX                                             */
/* ============================================================ */
create index TLE_NDX on ASSET_MASTER (ASSET_TITLE)
go

/* ============================================================ */
/*   Index: UE_NDX                                              */
/* ============================================================ */
create index UE_NDX on ASSET_MASTER (ASSET_VALUE)
go

/* ============================================================ */
/*   Index: ED_NDX                                              */
/* ============================================================ */
create index ED_NDX on ASSET_MASTER (DATE_ACQUIRED)
go

/* ============================================================ */
/*   Index: hjkhED_NDX                                          */
/* ============================================================ */
create index hjkhED_NDX on ASSET_MASTER (DATE_DISPOSED)
go

/* ============================================================ */
/*   Index: DATE_NDX                                            */
/* ============================================================ */
create index DATE_NDX on ASSET_MASTER (PAY_OFF_DATE)
go

/* ============================================================ */
/*   Index: TY_NDX                                              */
/* ============================================================ */
create index TY_NDX on ASSET_MASTER (QUANTITY)
go

/* ============================================================ */
/*   Index: VALUE_NDX                                           */
/* ============================================================ */
create index VALUE_NDX on ASSET_MASTER (SALE_VALUE)
go

/* ============================================================ */
/*   Index: MBER_NDX                                            */
/* ============================================================ */
create index MBER_NDX on ASSET_MASTER (SERIAL_NUMBER)
go

/* ============================================================ */
/*   Index: UPC_NDX                                             */
/* ============================================================ */
create index UPC_NDX on ASSET_MASTER (UPC)
go

/* ============================================================ */
/*   Table: AUDIO_VIDEO_DETAIL                                  */
/* ============================================================ */
create table AUDIO_VIDEO_DETAIL
(
    ID                     integer               not null,
    ASSET_MASTER_ID        integer               not null,
    CATEGORY               integer               not null,
    MEDIA_TYPE             integer               not null,
    YEAR_RECORDED          char(4)               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_AUDIO_VIDEO_DETAIL primary key (ID)
)
go

/* ============================================================ */
/*   Index: AVD_1_NDX                                           */
/* ============================================================ */
create index AVD_1_NDX on AUDIO_VIDEO_DETAIL (ASSET_MASTER_ID, CATEGORY, MEDIA_TYPE)
go

/* ============================================================ */
/*   Index: TYPE_NDX                                            */
/* ============================================================ */
create index TYPE_NDX on AUDIO_VIDEO_DETAIL (CATEGORY, MEDIA_TYPE)
go

/* ============================================================ */
/*   Index: AVD_GENERAL_NDX                                     */
/* ============================================================ */
create index AVD_GENERAL_NDX on AUDIO_VIDEO_DETAIL (CATEGORY, MEDIA_TYPE, YEAR_RECORDED)
go

/* ============================================================ */
/*   Index: NDX                                                 */
/* ============================================================ */
create index NDX on AUDIO_VIDEO_DETAIL (YEAR_RECORDED)
go

/* ============================================================ */
/*   Table: BUSINESS                                            */
/* ============================================================ */
create table BUSINESS
(
    ID                     integer               not null,
    BUSINESS_TYPE          integer               null    
        default 3,
    SERVICE_TYPE           integer               null    ,
    LONGNAME               varchar(40)           null    ,
    SHORTNAME              varchar(15)           null    ,
    PHOTOID                integer               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    ACTIVE_IND             char(1)               null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_BUSINESS primary key (ID)
)
go

/* ============================================================ */
/*   Index: BUS_LONGNAME_NDX                                    */
/* ============================================================ */
create index BUS_LONGNAME_NDX on BUSINESS (LONGNAME)
go

/* ============================================================ */
/*   Index: BUS_SERVICETYPE_NDX                                 */
/* ============================================================ */
create index BUS_SERVICETYPE_NDX on BUSINESS (SERVICE_TYPE)
go

/* ============================================================ */
/*   Index: BUS_SHORTNAME_NDX                                   */
/* ============================================================ */
create index BUS_SHORTNAME_NDX on BUSINESS (SHORTNAME)
go

/* ============================================================ */
/*   Table: GL_TRANSACTION                                      */
/* ============================================================ */
create table GL_TRANSACTION
(
    TRANS_ID               integer               identity,
    TRANS_TYPE             integer               null    ,
    TRANS_DATE             datetime              not null,
    POSTED_DATE            datetime              null    ,
    BANK_TRANS_IND         char(1)               null    ,
    RECONCILED_DATE        datetime              null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_GL_TRANSACTION primary key (TRANS_ID)
)
go

/* ============================================================ */
/*   Index: GL_TRANS1_NDX                                       */
/* ============================================================ */
create unique index GL_TRANS1_NDX on GL_TRANSACTION (TRANS_ID, TRANS_TYPE, TRANS_DATE, RECONCILED_DATE)
go

/* ============================================================ */
/*   Index: GL_TRANS2_NDX                                       */
/* ============================================================ */
create unique index GL_TRANS2_NDX on GL_TRANSACTION (TRANS_ID, TRANS_TYPE, TRANS_DATE, BANK_TRANS_IND)
go

/* ============================================================ */
/*   Index: GL_TRANSBANKIND_NDX                                 */
/* ============================================================ */
create index GL_TRANSBANKIND_NDX on GL_TRANSACTION (BANK_TRANS_IND)
go

/* ============================================================ */
/*   Index: GL_TRANSDATE_NDX                                    */
/* ============================================================ */
create index GL_TRANSDATE_NDX on GL_TRANSACTION (TRANS_DATE)
go

/* ============================================================ */
/*   Index: GL_TRANSDATETYPE_NDX                                */
/* ============================================================ */
create unique index GL_TRANSDATETYPE_NDX on GL_TRANSACTION (TRANS_ID, TRANS_DATE, POSTED_DATE, TRANS_TYPE)
go

/* ============================================================ */
/*   Index: SGROUP_NDX                                          */
/* ============================================================ */
create index SGROUP_NDX on GL_TRANSACTION (TRANS_DATE, TRANS_TYPE, POSTED_DATE, BANK_TRANS_IND, RECONCILED_DATE)
go

/* ============================================================ */
/*   Index: DATE_NDX                                            */
/* ============================================================ */
create index DATE_NDX on GL_TRANSACTION (POSTED_DATE)
go

/* ============================================================ */
/*   Index: ECDDATE_NDX                                         */
/* ============================================================ */
create index ECDDATE_NDX on GL_TRANSACTION (RECONCILED_DATE)
go

/* ============================================================ */
/*   Table: PERSONAL                                            */
/* ============================================================ */
create table PERSONAL
(
    ID                     integer               not null,
    FIRSTNAME              varchar(20)           null    ,
    MIDNAME                varchar(20)           null    ,
    LASTNAME               varchar(20)           null    ,
    MAIDENNAME             varchar(20)           null    ,
    GENERATION             varchar(4)            null    ,
    SHORTNAME              varchar(40)           null    ,
    NICKNAME               varchar(20)           null    ,
    TITLE                  varchar(4)            null    ,
    GENDER_ID              integer               null    ,
    MARITAL_STATUS         integer               null    ,
    HOUSEHOLD_HEAD         varchar(1)            null    ,
    BIRTHTIMESTAMP         datetime              null    ,
    MEETING_TIMESTAMP      datetime              null    ,
    RELATIONSHIP           integer               null    ,
    RACE_ID                integer               null    ,
    SSN                    varchar(9)            null    ,
    PHOTOID                integer               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    ACTIVE_IND             char(1)               null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_PERSONAL primary key (ID)
)
go

/* ============================================================ */
/*   Index: PER_BIRTHDATE_NDX                                   */
/* ============================================================ */
create index PER_BIRTHDATE_NDX on PERSONAL (BIRTHTIMESTAMP)
go

/* ============================================================ */
/*   Index: PER_FIRSTNAME_NDX                                   */
/* ============================================================ */
create index PER_FIRSTNAME_NDX on PERSONAL (FIRSTNAME)
go

/* ============================================================ */
/*   Index: PER_FULLNAME_NDX                                    */
/* ============================================================ */
create index PER_FULLNAME_NDX on PERSONAL (LASTNAME, FIRSTNAME, MIDNAME)
go

/* ============================================================ */
/*   Index: PER_GENERATION_NDX                                  */
/* ============================================================ */
create index PER_GENERATION_NDX on PERSONAL (GENERATION)
go

/* ============================================================ */
/*   Index: PER_LASTNAME_NDX                                    */
/* ============================================================ */
create index PER_LASTNAME_NDX on PERSONAL (LASTNAME)
go

/* ============================================================ */
/*   Index: PER_MAIDENNAME_NDX                                  */
/* ============================================================ */
create index PER_MAIDENNAME_NDX on PERSONAL (MAIDENNAME)
go

/* ============================================================ */
/*   Index: PER_MEETING_DATE_NDX                                */
/* ============================================================ */
create index PER_MEETING_DATE_NDX on PERSONAL (MEETING_TIMESTAMP)
go

/* ============================================================ */
/*   Index: PER_NICKNAME_NDX                                    */
/* ============================================================ */
create index PER_NICKNAME_NDX on PERSONAL (NICKNAME)
go

/* ============================================================ */
/*   Index: PER_SHORTNAME_NDX                                   */
/* ============================================================ */
create index PER_SHORTNAME_NDX on PERSONAL (SHORTNAME)
go

/* ============================================================ */
/*   Index: PER_SSN_NDX                                         */
/* ============================================================ */
create index PER_SSN_NDX on PERSONAL (SSN)
go

/* ============================================================ */
/*   Index: PERSONAL_1_NDX                                      */
/* ============================================================ */
create index PERSONAL_1_NDX on PERSONAL (LASTNAME, FIRSTNAME, MIDNAME, GENDER_ID, MARITAL_STATUS, RACE_ID)
go

/* ============================================================ */
/*   Index: PERSONAL_2_NDX                                      */
/* ============================================================ */
create index PERSONAL_2_NDX on PERSONAL (SHORTNAME, GENDER_ID, MARITAL_STATUS, RACE_ID)
go

/* ============================================================ */
/*   Table: PHOTOGRAPH                                          */
/* ============================================================ */
create table PHOTOGRAPH
(
    PHOTOID                integer               identity,
    PHOTO                  long                  null    ,
    XREF_KEY               varchar(15)           null    ,
    PHOTOTYPE_ID           integer               null    ,
    DESCRIPTION            varchar(100)          null    ,
    FILENAME               varchar(15)           null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    ACTIVE_IND             char(1)               null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    constraint PK_PHOTOGRAPH primary key (PHOTOID)
)
go

/* ============================================================ */
/*   Index: FILENAME6_NDX                                       */
/* ============================================================ */
create index FILENAME6_NDX on PHOTOGRAPH (FILENAME)
go

/* ============================================================ */
/*   Table: GL_CATEGORY                                         */
/* ============================================================ */
create table GL_CATEGORY
(
    ACCT_TYPE              integer               not null,
    ACCT_CATEGORY          integer               not null,
    DESCRIPTION            char(40)              not null,
    SUB_ACCOUNTS_IND       char(1)               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    constraint PK_GL_CATEGORY primary key (ACCT_TYPE, ACCT_CATEGORY)
)
go

/* ============================================================ */
/*   Index: DESC2                                               */
/* ============================================================ */
create index DESC2 on GL_CATEGORY (DESCRIPTION)
go

/* ============================================================ */
/*   Table: GL_ACCT_ITEMS                                       */
/* ============================================================ */
create table GL_ACCT_ITEMS
(
    ACCT_TYPE              integer               not null,
    ACCT_CATEGORY          integer               not null,
    ACCT_ITEM              integer               not null,
    DESCRIPTION            char(40)              not null,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    constraint PK_GL_ACCT_ITEMS primary key (ACCT_TYPE, ACCT_CATEGORY, ACCT_ITEM)
)
go

/* ============================================================ */
/*   Index: CLASS1                                              */
/* ============================================================ */
create index CLASS1 on GL_ACCT_ITEMS (ACCT_ITEM)
go

/* ============================================================ */
/*   Index: DESC3                                               */
/* ============================================================ */
create index DESC3 on GL_ACCT_ITEMS (DESCRIPTION)
go

/* ============================================================ */
/*   Index: SUBTYPE1                                            */
/* ============================================================ */
create index SUBTYPE1 on GL_ACCT_ITEMS (ACCT_CATEGORY)
go

/* ============================================================ */
/*   Index: SUBTYPE2                                            */
/* ============================================================ */
create index SUBTYPE2 on GL_ACCT_ITEMS (ACCT_CATEGORY)
go

/* ============================================================ */
/*   Table: GL_ITEM_SEQUENCE                                    */
/* ============================================================ */
create table GL_ITEM_SEQUENCE
(
    ITEM_ID                integer               identity,
    ACCT_TYPE              integer               not null,
    ACCT_CATEGORY          integer               not null,
    ACCT_ITEM              integer               not null,
    DESCRIPTION            char(100)             null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    constraint PK_GL_ITEM_SEQUENCE primary key (ITEM_ID)
)
go

/* ============================================================ */
/*   Index: CLASS2                                              */
/* ============================================================ */
create index CLASS2 on GL_ITEM_SEQUENCE (ACCT_ITEM)
go

/* ============================================================ */
/*   Index: SUBTYPE3                                            */
/* ============================================================ */
create index SUBTYPE3 on GL_ITEM_SEQUENCE (ACCT_CATEGORY)
go

/* ============================================================ */
/*   Table: CITY_CODE                                           */
/* ============================================================ */
create table CITY_CODE
(
    CITY_ID                integer               not null,
    COUNTRY_ID             integer               null    ,
    STATE_ID               integer               null    ,
    LONGNAME               char(30)              null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    permanent              varchar(3)            null    ,
    constraint PK_CITY_CODE primary key (CITY_ID)
)
go

/* ============================================================ */
/*   Index: CITYLONGNAME_NDX                                    */
/* ============================================================ */
create index CITYLONGNAME_NDX on CITY_CODE (LONGNAME)
go

/* ============================================================ */
/*   Index: CITYMULTI2_NDX                                      */
/* ============================================================ */
create unique index CITYMULTI2_NDX on CITY_CODE (COUNTRY_ID, STATE_ID, CITY_ID, LONGNAME)
go

/* ============================================================ */
/*   Table: MASTER_AREA_CODE                                    */
/* ============================================================ */
create table MASTER_AREA_CODE
(
    AREA_CODE              varchar(3)            not null,
    AREA_CODE_SEQ          integer               not null,
    STATE_ID               integer               not null,
    ESTIMATED_CITY         integer               null    ,
    TIME_ZONE              smallint              null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    constraint PK_MASTER_AREA_CODE primary key (AREA_CODE, AREA_CODE_SEQ)
)
go

/* ============================================================ */
/*   Index: MAC_1_NDX                                           */
/* ============================================================ */
create index MAC_1_NDX on MASTER_AREA_CODE (ESTIMATED_CITY, STATE_ID, TIME_ZONE)
go

/* ============================================================ */
/*   Index: MAC_2_NDX                                           */
/* ============================================================ */
create index MAC_2_NDX on MASTER_AREA_CODE (AREA_CODE, STATE_ID, ESTIMATED_CITY, TIME_ZONE)
go

/* ============================================================ */
/*   Index: TEDCITY_NDX                                         */
/* ============================================================ */
create index TEDCITY_NDX on MASTER_AREA_CODE (ESTIMATED_CITY)
go

/* ============================================================ */
/*   Table: project_scr                                         */
/* ============================================================ */
create table project_scr
(
    id                     integer               identity
        constraint CKC_ID_PROJECT_ check (id >= 1),
    project_id             integer               null    ,
    description            varchar(15)           null    ,
    begin_date             datetime              null    ,
    end_date               datetime              null    ,
    est_compl_hours        decimal(8,2)          null    ,
    actual_compl_hours     decimal(8,2)          null    ,
    date_created           datetime              null    ,
    date_updated           datetime              null    ,
    user_id                varchar(8)            null    ,
    constraint PK_PROJECT_SCR primary key (id)
)
go

/* ============================================================ */
/*   Table: AUDIO_VIDEO_TRACKS                                  */
/* ============================================================ */
create table AUDIO_VIDEO_TRACKS
(
    ID                     integer               not null,
    AUDIO_VIDEO_DETAIL_ID  integer               not null,
    TRACK_NUMBER           smallint              not null,
    TRACK_TITLE            char(30)              null    ,
    TRACK_HOURS            smallint              null    ,
    TRACK_MINUTES          smallint              null    ,
    TRACK_SECONDS          smallint              null    ,
    PROJECT_SIDE           smallint              null    ,
    TRACK_PRODUCER         char(20)              null    ,
    TRACK_COMPOSER         char(20)              null    ,
    TRACK_LYRICIST         char(20)              null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_AUDIO_VIDEO_TRACKS primary key (ID)
)
go

/* ============================================================ */
/*   Index: AVD_ROW_NDX                                         */
/* ============================================================ */
create index AVD_ROW_NDX on AUDIO_VIDEO_TRACKS (TRACK_NUMBER, AUDIO_VIDEO_DETAIL_ID, TRACK_TITLE, TRACK_HOURS, TRACK_MINUTES, TRACK_SECONDS)
go

/* ============================================================ */
/*   Index: AVT_HOURS_NDX                                       */
/* ============================================================ */
create index AVT_HOURS_NDX on AUDIO_VIDEO_TRACKS (TRACK_HOURS)
go

/* ============================================================ */
/*   Index: AVT_MINUETS_NDX                                     */
/* ============================================================ */
create index AVT_MINUETS_NDX on AUDIO_VIDEO_TRACKS (TRACK_MINUTES)
go

/* ============================================================ */
/*   Index: AVT_PROJSIDE_NDX                                    */
/* ============================================================ */
create index AVT_PROJSIDE_NDX on AUDIO_VIDEO_TRACKS (PROJECT_SIDE)
go

/* ============================================================ */
/*   Index: AVT_SECONDS_NDX                                     */
/* ============================================================ */
create index AVT_SECONDS_NDX on AUDIO_VIDEO_TRACKS (TRACK_SECONDS)
go

/* ============================================================ */
/*   Index: AVT_TITLE_NDX                                       */
/* ============================================================ */
create index AVT_TITLE_NDX on AUDIO_VIDEO_TRACKS (TRACK_TITLE)
go

/* ============================================================ */
/*   Index: MPOSER_NDX                                          */
/* ============================================================ */
create index MPOSER_NDX on AUDIO_VIDEO_TRACKS (TRACK_COMPOSER)
go

/* ============================================================ */
/*   Index: TAILS_NDX                                           */
/* ============================================================ */
create index TAILS_NDX on AUDIO_VIDEO_TRACKS (TRACK_NUMBER, TRACK_TITLE, TRACK_HOURS, TRACK_MINUTES, TRACK_SECONDS)
go

/* ============================================================ */
/*   Index: CIST_NDX                                            */
/* ============================================================ */
create index CIST_NDX on AUDIO_VIDEO_TRACKS (TRACK_LYRICIST)
go

/* ============================================================ */
/*   Index: AVT_TRACKNUMBER_NDX                                 */
/* ============================================================ */
create index AVT_TRACKNUMBER_NDX on AUDIO_VIDEO_TRACKS (TRACK_NUMBER)
go

/* ============================================================ */
/*   Index: CER_NDX                                             */
/* ============================================================ */
create index CER_NDX on AUDIO_VIDEO_TRACKS (TRACK_PRODUCER)
go

/* ============================================================ */
/*   Table: EXPENSE_MASTER                                      */
/* ============================================================ */
create table EXPENSE_MASTER
(
    ID                     integer               identity,
    ITEM_ID                integer               not null,
    AMOUNT_PAID            numeric(20,4)         null    ,
    TAXDED_ID              integer               null    ,
    QUANTITY               integer               null    ,
    PREV_TRAN_ID           char(15)              null    ,
    RECIEPT_PHOTOID        integer               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_EXPENSE_MASTER primary key (ID)
)
go

/* ============================================================ */
/*   Index: EXPENSE_1_NDX                                       */
/* ============================================================ */
create index EXPENSE_1_NDX on EXPENSE_MASTER (ITEM_ID, AMOUNT_PAID, QUANTITY)
go

/* ============================================================ */
/*   Index: EXPENSE_2_NDX                                       */
/* ============================================================ */
create index EXPENSE_2_NDX on EXPENSE_MASTER (ITEM_ID, PREV_TRAN_ID)
go

/* ============================================================ */
/*   Index: EXPENSE_AMTPAID_NDX                                 */
/* ============================================================ */
create index EXPENSE_AMTPAID_NDX on EXPENSE_MASTER (AMOUNT_PAID)
go

/* ============================================================ */
/*   Index: EXPENSE_PREVTRANID_NDX                              */
/* ============================================================ */
create index EXPENSE_PREVTRANID_NDX on EXPENSE_MASTER (PREV_TRAN_ID)
go

/* ============================================================ */
/*   Index: EXPENSE_QUANTITY_NDX                                */
/* ============================================================ */
create index EXPENSE_QUANTITY_NDX on EXPENSE_MASTER (QUANTITY)
go

/* ============================================================ */
/*   Table: GL_TRANSACTION_DETAIL                               */
/* ============================================================ */
create table GL_TRANSACTION_DETAIL
(
    TRANS_ID               integer               not null,
    TRANS_ID_LINE          integer               not null,
    ITEM_ID                integer               not null,
    TRANS_AMOUNT           numeric(20,4)         null    ,
    DB_CR_IND              varchar(2)            null    ,
    PAYTEND_ID             integer               null    ,
    CHECK_NUMBER           varchar(10)           null    ,
    TERMINAL_ID            varchar(10)           null    ,
    TRANS_ADDRID           integer               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       varchar(3)            null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                varchar(8)            null    
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_GL_TRANSACTION_DETAIL primary key (TRANS_ID, TRANS_ID_LINE)
)
go

/* ============================================================ */
/*   Index: GL_TRANSDET1_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET1_NDX on GL_TRANSACTION_DETAIL (ITEM_ID, CHECK_NUMBER)
go

/* ============================================================ */
/*   Index: GL_TRANSDET2_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET2_NDX on GL_TRANSACTION_DETAIL (ITEM_ID, TRANS_AMOUNT)
go

/* ============================================================ */
/*   Index: GL_TRANSDET4_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET4_NDX on GL_TRANSACTION_DETAIL (ITEM_ID, TRANS_ADDRID)
go

/* ============================================================ */
/*   Index: GL_TRANSDET5_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET5_NDX on GL_TRANSACTION_DETAIL (ITEM_ID, TERMINAL_ID)
go

/* ============================================================ */
/*   Index: GL_TRANSDET6_NDX                                    */
/* ============================================================ */
create unique index GL_TRANSDET6_NDX on GL_TRANSACTION_DETAIL (TRANS_ID, TRANS_ID_LINE, ITEM_ID, DB_CR_IND, CHECK_NUMBER, TERMINAL_ID)
go

/* ============================================================ */
/*   Index: NUMBER_NDX                                          */
/* ============================================================ */
create index NUMBER_NDX on GL_TRANSACTION_DETAIL (CHECK_NUMBER)
go

/* ============================================================ */
/*   Index: DBCRIND_NDX                                         */
/* ============================================================ */
create index DBCRIND_NDX on GL_TRANSACTION_DETAIL (DB_CR_IND)
go

/* ============================================================ */
/*   Index: ERMID_NDX                                           */
/* ============================================================ */
create index ERMID_NDX on GL_TRANSACTION_DETAIL (TERMINAL_ID)
go

/* ============================================================ */
/*   Index: RANAMT_NDX                                          */
/* ============================================================ */
create index RANAMT_NDX on GL_TRANSACTION_DETAIL (TRANS_AMOUNT)
go

/* ============================================================ */
/*   Table: LIABILITY_MASTER                                    */
/* ============================================================ */
create table LIABILITY_MASTER
(
    ID                     integer               identity,
    ITEM_ID                integer               not null,
    PAYMENT_ADDRID         integer               null    ,
    PAYMENT_PHONEID        integer               null    ,
    CUSTSERV_ADDRID        integer               null    ,
    CUSTSERV_PHONEID       integer               null    ,
    EXTRA_ADDRID           integer               null    ,
    EXTRA_PHONEID          integer               null    ,
    ACCOUNT_NUMBER         varchar(20)           null    ,
    ACCOUNT_TYPE           char(1)               null    ,
    ACCOUNT_STATUS         char(1)               null    ,
    APR                    numeric(20,4)         null    ,
    CREDIT_LIMIT           numeric(20,4)         null    ,
    PREVIOUS_BALANCE       numeric(20,4)         null    ,
    CURRENT_BALANCE        numeric(20,4)         null    ,
    BILL_FROM_DATE         datetime              null    ,
    BILL_TO_DATE           datetime              null    ,
    PAYMENT_DUE            numeric(20,4)         null    ,
    PAYMENT_DUE_DATE       datetime              null    ,
    RECIEPT_PHOTOID        integer               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       varchar(3)            not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                varchar(8)            not null
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_LIABILITY_MASTER primary key (ID)
)
go

/* ============================================================ */
/*   Index: BILLFROMDATE_NDX                                    */
/* ============================================================ */
create index BILLFROMDATE_NDX on LIABILITY_MASTER (BILL_FROM_DATE)
go

/* ============================================================ */
/*   Index: BILLTODATE_NDX                                      */
/* ============================================================ */
create index BILLTODATE_NDX on LIABILITY_MASTER (BILL_TO_DATE)
go

/* ============================================================ */
/*   Index: LIABILITY_1_NDX                                     */
/* ============================================================ */
create index LIABILITY_1_NDX on LIABILITY_MASTER (PAYMENT_ADDRID, PAYMENT_PHONEID)
go

/* ============================================================ */
/*   Index: LIABILITY_2_NDX                                     */
/* ============================================================ */
create index LIABILITY_2_NDX on LIABILITY_MASTER (CUSTSERV_ADDRID, CUSTSERV_PHONEID)
go

/* ============================================================ */
/*   Index: LIABILITY_3_NDX                                     */
/* ============================================================ */
create index LIABILITY_3_NDX on LIABILITY_MASTER (EXTRA_ADDRID, EXTRA_PHONEID)
go

/* ============================================================ */
/*   Index: LIABILITY_4_NDX                                     */
/* ============================================================ */
create index LIABILITY_4_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER, ACCOUNT_TYPE, APR, CREDIT_LIMIT, PREVIOUS_BALANCE, CURRENT_BALANCE)
go

/* ============================================================ */
/*   Index: LIABILITY_5_NDX                                     */
/* ============================================================ */
create index LIABILITY_5_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER, ACCOUNT_TYPE, PAYMENT_DUE, PAYMENT_DUE_DATE)
go

/* ============================================================ */
/*   Index: BER_NDX                                             */
/* ============================================================ */
create index BER_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER)
go

/* ============================================================ */
/*   Index: TYPE_NDX                                            */
/* ============================================================ */
create index TYPE_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER, ACCOUNT_TYPE)
go

/* ============================================================ */
/*   Index: LIABILITY_ACCTAPR_NDX                               */
/* ============================================================ */
create index LIABILITY_ACCTAPR_NDX on LIABILITY_MASTER (APR)
go

/* ============================================================ */
/*   Index: LIABILITY_ACCTSTAT_NDX                              */
/* ============================================================ */
create index LIABILITY_ACCTSTAT_NDX on LIABILITY_MASTER (ACCOUNT_STATUS)
go

/* ============================================================ */
/*   Index: LIABILITY_ACCTTYPE_NDX                              */
/* ============================================================ */
create index LIABILITY_ACCTTYPE_NDX on LIABILITY_MASTER (ACCOUNT_TYPE)
go

/* ============================================================ */
/*   Index: LIABILITY_CURRBAL_NDX                               */
/* ============================================================ */
create index LIABILITY_CURRBAL_NDX on LIABILITY_MASTER (CURRENT_BALANCE)
go

/* ============================================================ */
/*   Index: LIABILITY_LIMIT_NDX                                 */
/* ============================================================ */
create index LIABILITY_LIMIT_NDX on LIABILITY_MASTER (CREDIT_LIMIT)
go

/* ============================================================ */
/*   Index: LIABILITY_MASTER_NDX                                */
/* ============================================================ */
create index LIABILITY_MASTER_NDX on LIABILITY_MASTER (ITEM_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, ACCOUNT_STATUS, CREDIT_LIMIT, CURRENT_BALANCE, BILL_FROM_DATE, BILL_TO_DATE, PAYMENT_DUE, PAYMENT_DUE_DATE)
go

/* ============================================================ */
/*   Index: S_NDX                                               */
/* ============================================================ */
create index S_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER, ACCOUNT_TYPE, ACCOUNT_STATUS)
go

/* ============================================================ */
/*   Index: LIABILITY_PREVBAL_NDX                               */
/* ============================================================ */
create index LIABILITY_PREVBAL_NDX on LIABILITY_MASTER (PREVIOUS_BALANCE)
go

/* ============================================================ */
/*   Index: PAYDUE_NDX                                          */
/* ============================================================ */
create index PAYDUE_NDX on LIABILITY_MASTER (PAYMENT_DUE)
go

/* ============================================================ */
/*   Index: PAYDUEDATE_NDX                                      */
/* ============================================================ */
create index PAYDUEDATE_NDX on LIABILITY_MASTER (PAYMENT_DUE_DATE)
go

/* ============================================================ */
/*   Table: METRO_EXCHANGE_CODES                                */
/* ============================================================ */
create table METRO_EXCHANGE_CODES
(
    STATE_ID               integer               identity,
    CITY_ID                integer               not null,
    AREA_CODE              varchar(3)            not null,
    AREA_CODE_SEQ          integer               not null,
    PHONE_PREFIX           varchar(3)            not null,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       varchar(3)            null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    constraint PK_METRO_EXCHANGE_CODES primary key (STATE_ID, CITY_ID, AREA_CODE, AREA_CODE_SEQ, PHONE_PREFIX)
)
go

/* ============================================================ */
/*   Index: MEXCH_1_NDX                                         */
/* ============================================================ */
create index MEXCH_1_NDX on METRO_EXCHANGE_CODES (AREA_CODE, AREA_CODE_SEQ, CITY_ID, PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: MEXCH_2_NDX                                         */
/* ============================================================ */
create index MEXCH_2_NDX on METRO_EXCHANGE_CODES (STATE_ID, CITY_ID, AREA_CODE, PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: MEXCH_CITYID_NDX                                    */
/* ============================================================ */
create index MEXCH_CITYID_NDX on METRO_EXCHANGE_CODES (CITY_ID)
go

/* ============================================================ */
/*   Index: EFIX_NDX                                            */
/* ============================================================ */
create index EFIX_NDX on METRO_EXCHANGE_CODES (CITY_ID, PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: MEXCH_CITYSTATE_NDX                                 */
/* ============================================================ */
create index MEXCH_CITYSTATE_NDX on METRO_EXCHANGE_CODES (CITY_ID, STATE_ID)
go

/* ============================================================ */
/*   Table: PHONE_USAGE_HISTORY                                 */
/* ============================================================ */
create table PHONE_USAGE_HISTORY
(
    AREA_CODE              varchar(3)            not null,
    PHONE_PREFIX           varchar(3)            not null,
    PHONE_SUFFIX           varchar(4)            not null,
    USAGE_DATE             datetime              not null,
    LONG_DISTANCE_IND      char(1)               null    ,
    CONNECT_STATUS         char(1)               null    ,
    CONNECT_TYPE           integer               null    ,
    CONNECT_TIME           datetime              null    ,
    DISCONNECT_TIME        datetime              null    ,
    ORIGIN_COUNTRY         integer               null    ,
    ORIGIN_STATE           integer               null    ,
    ORIGIN_CITY            integer               null    ,
    ORIGIN_AREA_CODE       varchar(3)            null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       varchar(3)            null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                varchar(8)            null    
        default 'DBA',
    constraint PK_PHONE_USAGE_HISTORY primary key (AREA_CODE, PHONE_PREFIX, PHONE_SUFFIX, USAGE_DATE)
)
go

/* ============================================================ */
/*   Index: PHONEUSAGE_1_NDX                                    */
/* ============================================================ */
create index PHONEUSAGE_1_NDX on PHONE_USAGE_HISTORY (AREA_CODE, PHONE_PREFIX, PHONE_SUFFIX, ORIGIN_AREA_CODE, ORIGIN_CITY)
go

/* ============================================================ */
/*   Index: PHONEUSAGE_DATE_NDX                                 */
/* ============================================================ */
create index PHONEUSAGE_DATE_NDX on PHONE_USAGE_HISTORY (USAGE_DATE)
go

/* ============================================================ */
/*   Index: PHONEUSAGE_ORIGINAREACODE_NDX                       */
/* ============================================================ */
create index PHONEUSAGE_ORIGINAREACODE_NDX on PHONE_USAGE_HISTORY (ORIGIN_AREA_CODE)
go

/* ============================================================ */
/*   Index: PHONEUSAGE_PHONENUMBER_NDX                          */
/* ============================================================ */
create index PHONEUSAGE_PHONENUMBER_NDX on PHONE_USAGE_HISTORY (AREA_CODE, PHONE_PREFIX, PHONE_SUFFIX)
go

/* ============================================================ */
/*   Table: REVENUE_MASTER                                      */
/* ============================================================ */
create table REVENUE_MASTER
(
    ID                     integer               identity,
    ITEM_ID                integer               not null,
    GROSS_AMOUNT           numeric(20,4)         null    ,
    TAXDED_ID              integer               null    ,
    NET_AMOUNT             numeric(20,4)         null    ,
    PREV_TRAN_ID           char(15)              null    ,
    RECIEPT_PHOTOID        integer               null    ,
    DATE_CREATED           datetime              not null
        default current_timestamp,
    DATE_UPDATED           datetime              not null
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer               null    ,
    constraint PK_REVENUE_MASTER primary key (ID)
)
go

/* ============================================================ */
/*   Index: REVENUE_1_NDX                                       */
/* ============================================================ */
create index REVENUE_1_NDX on REVENUE_MASTER (ITEM_ID, GROSS_AMOUNT, NET_AMOUNT)
go

/* ============================================================ */
/*   Index: REVENUE_2_NDX                                       */
/* ============================================================ */
create index REVENUE_2_NDX on REVENUE_MASTER (ITEM_ID, PREV_TRAN_ID)
go

/* ============================================================ */
/*   Index: REVENUE_GROSSAMT_NDX                                */
/* ============================================================ */
create index REVENUE_GROSSAMT_NDX on REVENUE_MASTER (GROSS_AMOUNT)
go

/* ============================================================ */
/*   Index: REVENUE_NETAMT_NDX                                  */
/* ============================================================ */
create index REVENUE_NETAMT_NDX on REVENUE_MASTER (NET_AMOUNT)
go

/* ============================================================ */
/*   Index: REVENUE_PREVTRANID_NDX                              */
/* ============================================================ */
create index REVENUE_PREVTRANID_NDX on REVENUE_MASTER (PREV_TRAN_ID)
go

/* ============================================================ */
/*   Table: SYSTEM_MESSAGE_DETAIL                               */
/* ============================================================ */
create table SYSTEM_MESSAGE_DETAIL
(
    MESG_ID                integer               identity,
    MESG_TYPE_ID           integer               not null,
    DESCRIPTION            varchar(100)          null    ,
    BUTTON_ID              integer               null    ,
    ICON_ID                integer               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    CHANGE_REASON          varchar(300)          null    ,
    ACTIVE_IND             char(1)               null    ,
    AREACODE_CREATED       char(3)               null    
        default '972',
    CITY_CREATED           integer               null    
        default 1,
    USER_ID                char(8)               null    
        default 'DBA',
    constraint PK_SYSTEM_MESSAGE_DETAIL primary key (MESG_ID)
)
go

/* ============================================================ */
/*   Index: DATECHANGED_NDX                                     */
/* ============================================================ */
create index DATECHANGED_NDX on SYSTEM_MESSAGE_DETAIL (DATE_UPDATED)
go

/* ============================================================ */
/*   Index: DATECREATED1_NDX                                    */
/* ============================================================ */
create index DATECREATED1_NDX on SYSTEM_MESSAGE_DETAIL (DATE_CREATED)
go

/* ============================================================ */
/*   Index: DESCRIPTION20_NDX                                   */
/* ============================================================ */
create index DESCRIPTION20_NDX on SYSTEM_MESSAGE_DETAIL (DESCRIPTION)
go

/* ============================================================ */
/*   Table: USER_LOCATION                                       */
/* ============================================================ */
create table USER_LOCATION
(
    ID                     integer               identity,
    LOGIN_ID               char(8)               null    ,
    USER_NAME              char(30)              null    ,
    FIRSTNAME              char(25)              null    ,
    LASTNAME               char(25)              null    ,
    TITLE                  char(35)              null    ,
    START_DATE             datetime              null    
        default current_timestamp,
    TERMINATION_DATE       datetime              null    ,
    ACCESS_LEVEL           integer               null    ,
    PASSWORD               char(30)              null    ,
    TOTAL_LOGONS           integer               null    ,
    LOGON_CITY             char(30)              null    ,
    LOGON_AREACODE         char(3)               null    ,
    LOGON_PHONE_PREFIX     char(3)               null    ,
    DATE_CREATED           datetime              null    
        default current_timestamp,
    DATE_UPDATED           datetime              null    
        default current_timestamp,
    USER_ID                varchar(8)            null    ,
    constraint PK_USER_LOCATION primary key (ID)
)
go

/* ============================================================ */
/*   Index: LOGON_AREACODE_NDX                                  */
/* ============================================================ */
create index LOGON_AREACODE_NDX on USER_LOCATION (LOGON_AREACODE)
go

/* ============================================================ */
/*   Index: EFIX_NDX                                            */
/* ============================================================ */
create index EFIX_NDX on USER_LOCATION (LOGON_AREACODE, LOGON_PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: EPREFIX_NDX                                         */
/* ============================================================ */
create index EPREFIX_NDX on USER_LOCATION (LOGON_CITY, LOGON_AREACODE, LOGON_PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: PREFIX_NDX                                          */
/* ============================================================ */
create index PREFIX_NDX on USER_LOCATION (LOGON_PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: LOGONCITY_NDX                                       */
/* ============================================================ */
create index LOGONCITY_NDX on USER_LOCATION (LOGON_CITY)
go

/* ============================================================ */
/*   Index: MULTIKEY2_NDX                                       */
/* ============================================================ */
create index MULTIKEY2_NDX on USER_LOCATION (USER_NAME, LOGON_CITY, LOGON_AREACODE, LOGON_PHONE_PREFIX)
go

/* ============================================================ */
/*   Index: USERNAME_NDX                                        */
/* ============================================================ */
create index USERNAME_NDX on USER_LOCATION (USER_NAME)
go

/* ============================================================ */
/*   Table: project_tasks                                       */
/* ============================================================ */
create table project_tasks
(
    id                     integer               identity
        constraint CKC_ID_PROJECT_ check (id >= 1),
    role_id                integer               null    ,
    scr_id                 integer               null    ,
    date_worked            datetime              null    ,
    hours_worked           decimal(5,2)          null    ,
    billable               integer               null    ,
    date_created           datetime              null    ,
    date_updated           datetime              null    ,
    user_id                varchar(8)            null    ,
    constraint PK_PROJECT_TASKS primary key (id)
)
go

