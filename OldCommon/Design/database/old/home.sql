/* ============================================================ */
/*   Database name:  Home_System                                */
/*   DBMS name:      Sybase AS Anywhere 6                       */
/*   Created on:     8/11/02  9:59 PM                           */
/* ============================================================ */

/* ============================================================ */
/*   Table: NULLTABLE                                           */
/* ============================================================ */
create table NULLTABLE
(
    NULLCOL                integer                       
);

/* ============================================================ */
/*   Table: SYSTEM_KEYS                                         */
/* ============================================================ */
create table SYSTEM_KEYS
(
    TABLE_ID               integer               not null
        default autoincrement,
    TABLENAME              varchar(40)                   ,
    MULTI_COLUMN_KEY       varchar(3)                    ,
    COLUMN_ID              varchar(100)                  ,
    NEXT_KEY_VALUE         varchar(10)                   ,
    DATE_CREATED           timestamp                     
        default current date,
    DATE_UPDATED           timestamp                     
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    ACTIVE_IND             char(1)                       ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    primary key (TABLE_ID)
);

/* ============================================================ */
/*   Index: NDX_MULTI_COLUMN_KEY                                */
/* ============================================================ */
create index NDX_MULTI_COLUMN_KEY on SYSTEM_KEYS (MULTI_COLUMN_KEY asc);

/* ============================================================ */
/*   Index: NDX_TABLENAME                                       */
/* ============================================================ */
create index NDX_TABLENAME on SYSTEM_KEYS (TABLENAME asc);

/* ============================================================ */
/*   Index: NDX_TABLENAMENEXTKEYVALUE                           */
/* ============================================================ */
create index NDX_TABLENAMENEXTKEYVALUE on SYSTEM_KEYS (TABLENAME asc, NEXT_KEY_VALUE asc);

/* ============================================================ */
/*   Table: COMMENTS                                            */
/* ============================================================ */
create table COMMENTS
(
    COMMENT_ID             integer               not null,
    COMMENT_TEXT           varchar(1000)                 ,
    DATE_CREATED           timestamp                     
        default current date,
    DATE_UPDATED           timestamp                     
        default current timestamp,
    USER_ID                char(8)                       
        default 'DBA',
    primary key (COMMENT_ID)
);

/* ============================================================ */
/*   Table: GENERAL_CODES_GROUP                                 */
/* ============================================================ */
create table GENERAL_CODES_GROUP
(
    GROUP_ID               integer               not null,
    DESCRIPTION            varchar(30)                   ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    permanent              varchar(3)                    ,
    primary key (GROUP_ID)
);

/* ============================================================ */
/*   Table: GL_ACCOUNT_TYPES                                    */
/* ============================================================ */
create table GL_ACCOUNT_TYPES
(
    ACCT_TYPE              integer               not null
        default autoincrement,
    DESCRIPTION            char(40)              not null,
    DATE_CREATED           timestamp             not null
        default current date,
    DATE_UPDATED           timestamp             not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    primary key (ACCT_TYPE)
);

/* ============================================================ */
/*   Index: DESC1                                               */
/* ============================================================ */
create index DESC1 on GL_ACCOUNT_TYPES (DESCRIPTION asc);

/* ============================================================ */
/*   Table: STATES_CODE                                         */
/* ============================================================ */
create table STATES_CODE
(
    STATE_ID               integer               not null,
    SHORTNAME              char(3)               not null,
    LONGNAME               char(25)              not null,
    STATE_CAPITAL          integer                       ,
    COUNTRY_ID             integer                       ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    permanent              varchar(3)                    ,
    primary key (STATE_ID)
);

/* ============================================================ */
/*   Index: NDX_STATECAPITAL                                    */
/* ============================================================ */
create index NDX_STATECAPITAL on STATES_CODE (STATE_CAPITAL asc);

/* ============================================================ */
/*   Index: NDX_STATESMULTI                                     */
/* ============================================================ */
create unique index NDX_STATESMULTI on STATES_CODE (COUNTRY_ID asc, STATE_ID asc, STATE_CAPITAL asc);

/* ============================================================ */
/*   Table: project_master                                      */
/* ============================================================ */
create table project_master
(
    id                     integer               not null
        default autoincrement
        check (id >= 1000),
    description            varchar(30)                   ,
    begin_date             date                          ,
    end_date               date                          ,
    estimated_hours        decimal(8,2)                  ,
    actual_hours           decimal(8,2)                  ,
    build_no               integer                       ,
    major_version          integer                       ,
    minor_version          integer                       ,
    date_created           date                          ,
    date_updated           date                          ,
    user_id                varchar(8)                    ,
    primary key (id)
);

/* ============================================================ */
/*   Table: TAXES_AND_DEDUCTIONS                                */
/* ============================================================ */
create table TAXES_AND_DEDUCTIONS
(
    TAXDED_ID              integer               not null
        default autoincrement,
    CITY_TAX               numeric(20,4)                 
        default 0,
    STATE_TAX              numeric(20,4)                 
        default 0,
    FEDERAL_TAX            numeric(20,4)                 
        default 0,
    SALES_TAX              numeric(20,4)                 
        default 0,
    PROPERTY_TAX           numeric(20,4)                 
        default 0,
    INSURANCE_TAX          numeric(20,4)                 
        default 0,
    LUXURY_TAX             numeric(20,4)                 
        default 0,
    MEDICAID_TAX           numeric(20,4)                 
        default 0,
    SOCIAL_SEC_DED         numeric(20,4)                 
        default 0,
    MEDICAL_INS_DED        numeric(20,4)                 
        default 0,
    DENTAL_DED             numeric(20,4)                 
        default 0,
    OTHER_DED              numeric(20,4)                 
        default 0,
    OTHER_TAX              numeric(20,4)                 
        default 0,
    OTHER_DED_COMMENT_ID   integer                       ,
    OTHER_TAX_COMMENT_ID   integer                       ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    OTHER_DED_COMMENTS_ID  integer                       ,
    OTHER_TAX_COMMENTS_ID  integer                       ,
    primary key (TAXDED_ID)
);

/* ============================================================ */
/*   Table: GENERAL_CODES                                       */
/* ============================================================ */
create table GENERAL_CODES
(
    CODE_ID                integer               not null,
    GROUP_ID               integer                       ,
    SHORTDESC              varchar(10)                   ,
    LONGDESC               varchar(80)                   ,
    COUNTRY_ID             integer                       ,
    GEN_IND_VALUE          varchar(3)                    ,
    BUTTON_API_VALUE       integer                       ,
    BUTTON_DEFAULT         integer                       ,
    ICON_API_VALUE         integer                       ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    permanent              varchar(3)                    ,
    primary key (CODE_ID)
);

/* ============================================================ */
/*   Index: NDX_BUTTONAPIVALUE                                  */
/* ============================================================ */
create index NDX_BUTTONAPIVALUE on GENERAL_CODES (BUTTON_API_VALUE asc);

/* ============================================================ */
/*   Index: NDX_BUTTONDEFAULT                                   */
/* ============================================================ */
create index NDX_BUTTONDEFAULT on GENERAL_CODES (BUTTON_DEFAULT asc);

/* ============================================================ */
/*   Index: NDX_GENINDVALUE                                     */
/* ============================================================ */
create index NDX_GENINDVALUE on GENERAL_CODES (GEN_IND_VALUE asc);

/* ============================================================ */
/*   Index: NDX_ICONAPIVALUE                                    */
/* ============================================================ */
create index NDX_ICONAPIVALUE on GENERAL_CODES (ICON_API_VALUE asc);

/* ============================================================ */
/*   Index: NDX_LONGDESC                                        */
/* ============================================================ */
create index NDX_LONGDESC on GENERAL_CODES (LONGDESC asc);

/* ============================================================ */
/*   Index: NDX_SHORTDESC                                       */
/* ============================================================ */
create index NDX_SHORTDESC on GENERAL_CODES (SHORTDESC asc);

/* ============================================================ */
/*   Table: ADDRESS                                             */
/* ============================================================ */
create table ADDRESS
(
    ID                     integer               not null,
    ADDRESSTYPE_ID         integer               not null,
    PERSONAL_ID            integer                       ,
    BUSINESS_ID            integer                       ,
    ADDRESS1               char(25)                      ,
    ADDRESS2               char(25)                      ,
    ADDRESS3               char(25)                      ,
    ADDRESS4               char(25)                      ,
    CITY_ID                integer                       ,
    STATE_ID               integer                       ,
    COUNTRY_ID             integer                       ,
    ZIP_CODE               char(5)                       ,
    ZIP_EXT                char(4)                       ,
    PHONE_HOME             varchar(13)                   ,
    PHONE_WORK             varchar(13)                   ,
    PHONE_WORKEXT          varchar(8)                    ,
    PHONE_BUSINESS         varchar(13)                   ,
    PHONE_CELLULAR         varchar(13)                   ,
    PHONE_FAX              varchar(13)                   ,
    PHONE_PAGER            varchar(13)                   ,
    PHONE_OTHER            varchar(13)                   ,
    INTERNET_ADDRESS       char(80)                      ,
    EMAIL                  char(50)                      ,
    DATE_CREATED           timestamp                     
        default current date,
    DATE_UPDATED           timestamp                     
        default current date,
    USER_ID                char(8)                       
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: ADDERSS_EMAIL_NDX                                   */
/* ============================================================ */
create index ADDERSS_EMAIL_NDX on ADDRESS (EMAIL asc);

/* ============================================================ */
/*   Index: ADDRESS_1_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_1_NDX on ADDRESS (COUNTRY_ID asc, STATE_ID asc, CITY_ID asc, ID asc, PERSONAL_ID asc);

/* ============================================================ */
/*   Index: ADDRESS_2_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_2_NDX on ADDRESS (COUNTRY_ID asc, STATE_ID asc, CITY_ID asc, ID asc);

/* ============================================================ */
/*   Index: ADDRESS_3_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_3_NDX on ADDRESS (PERSONAL_ID asc, ID asc, ADDRESSTYPE_ID asc);

/* ============================================================ */
/*   Index: ADDRESS_4_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_4_NDX on ADDRESS (ID asc, ADDRESSTYPE_ID asc);

/* ============================================================ */
/*   Index: ADDRESS_5_NDX                                       */
/* ============================================================ */
create index ADDRESS_5_NDX on ADDRESS (CITY_ID asc, STATE_ID asc);

/* ============================================================ */
/*   Index: ADDRESS_6_NDX                                       */
/* ============================================================ */
create unique index ADDRESS_6_NDX on ADDRESS (ID asc, ADDRESSTYPE_ID asc, CITY_ID asc, STATE_ID asc);

/* ============================================================ */
/*   Index: ADDRESS_ADDRESS1_NDX                                */
/* ============================================================ */
create index ADDRESS_ADDRESS1_NDX on ADDRESS (ADDRESS1 asc);

/* ============================================================ */
/*   Index: ADDRESS_ADDRESS2_NDX                                */
/* ============================================================ */
create index ADDRESS_ADDRESS2_NDX on ADDRESS (ADDRESS2 asc);

/* ============================================================ */
/*   Index: ADDRESS_ADDRESS3_NDX                                */
/* ============================================================ */
create index ADDRESS_ADDRESS3_NDX on ADDRESS (ADDRESS3 asc);

/* ============================================================ */
/*   Index: ADDRESS_ADDRESS4_NDX                                */
/* ============================================================ */
create index ADDRESS_ADDRESS4_NDX on ADDRESS (ADDRESS4 asc);

/* ============================================================ */
/*   Index: ADDRESS_COUNTRYID_NDX                               */
/* ============================================================ */
create index ADDRESS_COUNTRYID_NDX on ADDRESS (COUNTRY_ID asc);

/* ============================================================ */
/*   Index: ADDRESS_INTERNET_NDX                                */
/* ============================================================ */
create index ADDRESS_INTERNET_NDX on ADDRESS (INTERNET_ADDRESS asc);

/* ============================================================ */
/*   Index: ADDRESS_ZIP_NDX                                     */
/* ============================================================ */
create index ADDRESS_ZIP_NDX on ADDRESS (ZIP_CODE asc);

/* ============================================================ */
/*   Index: ADDRESS_ZIP_ZIPEXT_NDX                              */
/* ============================================================ */
create index ADDRESS_ZIP_ZIPEXT_NDX on ADDRESS (ZIP_CODE asc, ZIP_EXT asc);

/* ============================================================ */
/*   Index: ADDRESS_ZIPEXT_NDX                                  */
/* ============================================================ */
create index ADDRESS_ZIPEXT_NDX on ADDRESS (ZIP_EXT asc);

/* ============================================================ */
/*   Table: ASSET_MASTER                                        */
/* ============================================================ */
create table ASSET_MASTER
(
    ID                     integer               not null
        default autoincrement,
    ITEM_ID                integer               not null,
    SERIAL_NUMBER          char(30)                      ,
    UPC                    char(15)                      ,
    ADDRESS_ID             integer                       ,
    PHONE_ID               integer                       ,
    ASSET_MAKE             char(30)                      ,
    ASSET_TITLE            char(30)                      ,
    ASSET_MFG_DATE         date                          ,
    DATE_ACQUIRED          date                          ,
    DATE_DISPOSED          date                          ,
    PAY_OFF_DATE           date                          ,
    QUANTITY               smallint                      ,
    ASSET_VALUE            numeric(20,4)                 
        default 0,
    SALE_VALUE             numeric(20,4)                 
        default 0,
    TAXDED_ID              integer                       ,
    DETAIL_IND             char(1)                       ,
    PAYTEND_ID             integer               not null,
    INTEREST_IND           char(1)                       ,
    PHOTOID                integer                       ,
    RECIEPT_PHOTOID        integer                       ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: ASSETMASTER_1_NDX                                   */
/* ============================================================ */
create index ASSETMASTER_1_NDX on ASSET_MASTER (ITEM_ID asc, SERIAL_NUMBER asc);

/* ============================================================ */
/*   Index: ASSETMASTER_2_NDX                                   */
/* ============================================================ */
create index ASSETMASTER_2_NDX on ASSET_MASTER (ITEM_ID asc, PAY_OFF_DATE asc, QUANTITY asc, ASSET_VALUE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_3_NDX                                   */
/* ============================================================ */
create index ASSETMASTER_3_NDX on ASSET_MASTER (SERIAL_NUMBER asc, UPC asc, ASSET_MAKE asc, ASSET_TITLE asc, PAY_OFF_DATE asc, QUANTITY asc, ASSET_VALUE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_ADDRESSPHOTO_NDX                        */
/* ============================================================ */
create index ASSETMASTER_ADDRESSPHOTO_NDX on ASSET_MASTER (ADDRESS_ID asc, PHOTOID asc);

/* ============================================================ */
/*   Index: ASSETMASTER_ASSETMAKE_NDX                           */
/* ============================================================ */
create index ASSETMASTER_ASSETMAKE_NDX on ASSET_MASTER (ASSET_MAKE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_ASSETMFGDATE_NDX                        */
/* ============================================================ */
create index ASSETMASTER_ASSETMFGDATE_NDX on ASSET_MASTER (ASSET_MFG_DATE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_ASSETTITLE_NDX                          */
/* ============================================================ */
create index ASSETMASTER_ASSETTITLE_NDX on ASSET_MASTER (ASSET_TITLE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_ASSETVALUE_NDX                          */
/* ============================================================ */
create index ASSETMASTER_ASSETVALUE_NDX on ASSET_MASTER (ASSET_VALUE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_DATEACQUIRED_NDX                        */
/* ============================================================ */
create index ASSETMASTER_DATEACQUIRED_NDX on ASSET_MASTER (DATE_ACQUIRED asc);

/* ============================================================ */
/*   Index: ASSETMASTER_DATEDISPOSED_NDX                        */
/* ============================================================ */
create index ASSETMASTER_DATEDISPOSED_NDX on ASSET_MASTER (DATE_DISPOSED asc);

/* ============================================================ */
/*   Index: ASSETMASTER_PAYOFDATE_NDX                           */
/* ============================================================ */
create index ASSETMASTER_PAYOFDATE_NDX on ASSET_MASTER (PAY_OFF_DATE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_QTY_NDX                                 */
/* ============================================================ */
create index ASSETMASTER_QTY_NDX on ASSET_MASTER (QUANTITY asc);

/* ============================================================ */
/*   Index: ASSETMASTER_SALEVALUE_NDX                           */
/* ============================================================ */
create index ASSETMASTER_SALEVALUE_NDX on ASSET_MASTER (SALE_VALUE asc);

/* ============================================================ */
/*   Index: ASSETMASTER_SERIALNUMBER_NDX                        */
/* ============================================================ */
create index ASSETMASTER_SERIALNUMBER_NDX on ASSET_MASTER (SERIAL_NUMBER asc);

/* ============================================================ */
/*   Index: ASSETMASTER_UPC_NDX                                 */
/* ============================================================ */
create index ASSETMASTER_UPC_NDX on ASSET_MASTER (UPC asc);

/* ============================================================ */
/*   Table: AUDIO_VIDEO_DETAIL                                  */
/* ============================================================ */
create table AUDIO_VIDEO_DETAIL
(
    ID                     integer               not null,
    ASSET_MASTER_ID        integer               not null,
    CATEGORY               integer               not null,
    MEDIA_TYPE             integer               not null,
    YEAR_RECORDED          char(4)                       ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: AVD_1_NDX                                           */
/* ============================================================ */
create index AVD_1_NDX on AUDIO_VIDEO_DETAIL (ASSET_MASTER_ID asc, CATEGORY asc, MEDIA_TYPE asc);

/* ============================================================ */
/*   Index: AVD_CATEGORY_MEDIATYPE_NDX                          */
/* ============================================================ */
create index AVD_CATEGORY_MEDIATYPE_NDX on AUDIO_VIDEO_DETAIL (CATEGORY asc, MEDIA_TYPE asc);

/* ============================================================ */
/*   Index: AVD_GENERAL_NDX                                     */
/* ============================================================ */
create index AVD_GENERAL_NDX on AUDIO_VIDEO_DETAIL (CATEGORY asc, MEDIA_TYPE asc, YEAR_RECORDED asc);

/* ============================================================ */
/*   Index: AVD_YEARRECORD_NDX                                  */
/* ============================================================ */
create index AVD_YEARRECORD_NDX on AUDIO_VIDEO_DETAIL (YEAR_RECORDED asc);

/* ============================================================ */
/*   Table: BUSINESS                                            */
/* ============================================================ */
create table BUSINESS
(
    ID                     integer               not null,
    BUSINESS_TYPE          integer                       
        default 3,
    SERVICE_TYPE           integer                       ,
    LONGNAME               varchar(40)                   ,
    SHORTNAME              varchar(15)                   ,
    PHOTOID                integer                       ,
    DATE_CREATED           timestamp                     
        default current date,
    DATE_UPDATED           timestamp                     
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    ACTIVE_IND             char(1)                       ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: BUS_LONGNAME_NDX                                    */
/* ============================================================ */
create index BUS_LONGNAME_NDX on BUSINESS (LONGNAME asc);

/* ============================================================ */
/*   Index: BUS_SERVICETYPE_NDX                                 */
/* ============================================================ */
create index BUS_SERVICETYPE_NDX on BUSINESS (SERVICE_TYPE asc);

/* ============================================================ */
/*   Index: BUS_SHORTNAME_NDX                                   */
/* ============================================================ */
create index BUS_SHORTNAME_NDX on BUSINESS (SHORTNAME asc);

/* ============================================================ */
/*   Table: GL_TRANSACTION                                      */
/* ============================================================ */
create table GL_TRANSACTION
(
    TRANS_ID               integer               not null
        default autoincrement,
    TRANS_TYPE             integer                       ,
    TRANS_DATE             date                  not null,
    POSTED_DATE            date                          ,
    BANK_TRANS_IND         char(1)                       ,
    RECONCILED_DATE        date                          ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (TRANS_ID)
);

/* ============================================================ */
/*   Index: GL_TRANS1_NDX                                       */
/* ============================================================ */
create unique index GL_TRANS1_NDX on GL_TRANSACTION (TRANS_ID asc, TRANS_TYPE asc, TRANS_DATE asc, RECONCILED_DATE asc);

/* ============================================================ */
/*   Index: GL_TRANS2_NDX                                       */
/* ============================================================ */
create unique index GL_TRANS2_NDX on GL_TRANSACTION (TRANS_ID asc, TRANS_TYPE asc, TRANS_DATE asc, BANK_TRANS_IND asc);

/* ============================================================ */
/*   Index: GL_TRANSBANKIND_NDX                                 */
/* ============================================================ */
create index GL_TRANSBANKIND_NDX on GL_TRANSACTION (BANK_TRANS_IND asc);

/* ============================================================ */
/*   Index: GL_TRANSDATE_NDX                                    */
/* ============================================================ */
create index GL_TRANSDATE_NDX on GL_TRANSACTION (TRANS_DATE asc);

/* ============================================================ */
/*   Index: GL_TRANSDATETYPE_NDX                                */
/* ============================================================ */
create unique index GL_TRANSDATETYPE_NDX on GL_TRANSACTION (TRANS_ID asc, TRANS_DATE asc, POSTED_DATE asc, TRANS_TYPE asc);

/* ============================================================ */
/*   Index: GL_TRANSGROUP_NDX                                   */
/* ============================================================ */
create index GL_TRANSGROUP_NDX on GL_TRANSACTION (TRANS_DATE asc, TRANS_TYPE asc, POSTED_DATE asc, BANK_TRANS_IND asc, RECONCILED_DATE asc);

/* ============================================================ */
/*   Index: GL_TRANSPOSTDATE_NDX                                */
/* ============================================================ */
create index GL_TRANSPOSTDATE_NDX on GL_TRANSACTION (POSTED_DATE asc);

/* ============================================================ */
/*   Index: GL_TRANSRECDDATE_NDX                                */
/* ============================================================ */
create index GL_TRANSRECDDATE_NDX on GL_TRANSACTION (RECONCILED_DATE asc);

/* ============================================================ */
/*   Table: PERSONAL                                            */
/* ============================================================ */
create table PERSONAL
(
    ID                     integer               not null,
    FIRSTNAME              varchar(20)                   ,
    MIDNAME                varchar(20)                   ,
    LASTNAME               varchar(20)                   ,
    MAIDENNAME             varchar(20)                   ,
    GENERATION             varchar(4)                    ,
    SHORTNAME              varchar(40)                   ,
    NICKNAME               varchar(20)                   ,
    TITLE                  varchar(4)                    ,
    GENDER_ID              integer                       ,
    MARITAL_STATUS         integer                       ,
    HOUSEHOLD_HEAD         varchar(1)                    ,
    BIRTHTIMESTAMP         date                          ,
    MEETING_TIMESTAMP      date                          ,
    RELATIONSHIP           integer                       ,
    RACE_ID                integer                       ,
    SSN                    varchar(9)                    ,
    PHOTOID                integer                       ,
    DATE_CREATED           timestamp                     
        default current date,
    DATE_UPDATED           timestamp                     
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    ACTIVE_IND             char(1)                       ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: PER_BIRTHDATE_NDX                                   */
/* ============================================================ */
create index PER_BIRTHDATE_NDX on PERSONAL (BIRTHTIMESTAMP asc);

/* ============================================================ */
/*   Index: PER_FIRSTNAME_NDX                                   */
/* ============================================================ */
create index PER_FIRSTNAME_NDX on PERSONAL (FIRSTNAME asc);

/* ============================================================ */
/*   Index: PER_FULLNAME_NDX                                    */
/* ============================================================ */
create index PER_FULLNAME_NDX on PERSONAL (LASTNAME asc, FIRSTNAME asc, MIDNAME asc);

/* ============================================================ */
/*   Index: PER_GENERATION_NDX                                  */
/* ============================================================ */
create index PER_GENERATION_NDX on PERSONAL (GENERATION asc);

/* ============================================================ */
/*   Index: PER_LASTNAME_NDX                                    */
/* ============================================================ */
create index PER_LASTNAME_NDX on PERSONAL (LASTNAME asc);

/* ============================================================ */
/*   Index: PER_MAIDENNAME_NDX                                  */
/* ============================================================ */
create index PER_MAIDENNAME_NDX on PERSONAL (MAIDENNAME asc);

/* ============================================================ */
/*   Index: PER_MEETING_DATE_NDX                                */
/* ============================================================ */
create index PER_MEETING_DATE_NDX on PERSONAL (MEETING_TIMESTAMP asc);

/* ============================================================ */
/*   Index: PER_NICKNAME_NDX                                    */
/* ============================================================ */
create index PER_NICKNAME_NDX on PERSONAL (NICKNAME asc);

/* ============================================================ */
/*   Index: PER_SHORTNAME_NDX                                   */
/* ============================================================ */
create index PER_SHORTNAME_NDX on PERSONAL (SHORTNAME asc);

/* ============================================================ */
/*   Index: PER_SSN_NDX                                         */
/* ============================================================ */
create index PER_SSN_NDX on PERSONAL (SSN asc);

/* ============================================================ */
/*   Index: PERSONAL_1_NDX                                      */
/* ============================================================ */
create index PERSONAL_1_NDX on PERSONAL (LASTNAME asc, FIRSTNAME asc, MIDNAME asc, GENDER_ID asc, MARITAL_STATUS asc, RACE_ID asc);

/* ============================================================ */
/*   Index: PERSONAL_2_NDX                                      */
/* ============================================================ */
create index PERSONAL_2_NDX on PERSONAL (SHORTNAME asc, GENDER_ID asc, MARITAL_STATUS asc, RACE_ID asc);

/* ============================================================ */
/*   Table: PHOTOGRAPH                                          */
/* ============================================================ */
create table PHOTOGRAPH
(
    PHOTOID                integer               not null
        default autoincrement,
    PHOTO                  long binary                   ,
    XREF_KEY               varchar(15)                   ,
    PHOTOTYPE_ID           integer                       ,
    DESCRIPTION            varchar(100)                  ,
    FILENAME               varchar(15)                   ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    ACTIVE_IND             char(1)                       ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    primary key (PHOTOID)
);

/* ============================================================ */
/*   Index: FILENAME6_NDX                                       */
/* ============================================================ */
create index FILENAME6_NDX on PHOTOGRAPH (FILENAME asc);

/* ============================================================ */
/*   Table: GL_CATEGORY                                         */
/* ============================================================ */
create table GL_CATEGORY
(
    ACCT_TYPE              integer               not null,
    ACCT_CATEGORY          integer               not null,
    DESCRIPTION            char(40)              not null,
    SUB_ACCOUNTS_IND       char(1)                       ,
    DATE_CREATED           timestamp             not null
        default current date,
    DATE_UPDATED           timestamp             not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    primary key (ACCT_TYPE, ACCT_CATEGORY)
);

/* ============================================================ */
/*   Index: DESC2                                               */
/* ============================================================ */
create index DESC2 on GL_CATEGORY (DESCRIPTION asc);

/* ============================================================ */
/*   Table: GL_ACCT_ITEMS                                       */
/* ============================================================ */
create table GL_ACCT_ITEMS
(
    ACCT_TYPE              integer               not null,
    ACCT_CATEGORY          integer               not null,
    ACCT_ITEM              integer               not null,
    DESCRIPTION            char(40)              not null,
    DATE_CREATED           timestamp             not null
        default current date,
    DATE_UPDATED           timestamp             not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    primary key (ACCT_TYPE, ACCT_CATEGORY, ACCT_ITEM)
);

/* ============================================================ */
/*   Index: CLASS1                                              */
/* ============================================================ */
create index CLASS1 on GL_ACCT_ITEMS (ACCT_ITEM asc);

/* ============================================================ */
/*   Index: DESC3                                               */
/* ============================================================ */
create index DESC3 on GL_ACCT_ITEMS (DESCRIPTION asc);

/* ============================================================ */
/*   Index: SUBTYPE1                                            */
/* ============================================================ */
create index SUBTYPE1 on GL_ACCT_ITEMS (ACCT_CATEGORY asc);

/* ============================================================ */
/*   Index: SUBTYPE2                                            */
/* ============================================================ */
create index SUBTYPE2 on GL_ACCT_ITEMS (ACCT_CATEGORY asc);

/* ============================================================ */
/*   Table: GL_ITEM_SEQUENCE                                    */
/* ============================================================ */
create table GL_ITEM_SEQUENCE
(
    ITEM_ID                integer               not null
        default autoincrement,
    ACCT_TYPE              integer               not null,
    ACCT_CATEGORY          integer               not null,
    ACCT_ITEM              integer               not null,
    DESCRIPTION            char(100)                     ,
    DATE_CREATED           timestamp             not null
        default current date,
    DATE_UPDATED           timestamp             not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    primary key (ITEM_ID)
);

/* ============================================================ */
/*   Index: CLASS2                                              */
/* ============================================================ */
create index CLASS2 on GL_ITEM_SEQUENCE (ACCT_ITEM asc);

/* ============================================================ */
/*   Index: SUBTYPE3                                            */
/* ============================================================ */
create index SUBTYPE3 on GL_ITEM_SEQUENCE (ACCT_CATEGORY asc);

/* ============================================================ */
/*   Table: CITY_CODE                                           */
/* ============================================================ */
create table CITY_CODE
(
    CITY_ID                integer               not null,
    COUNTRY_ID             integer                       ,
    STATE_ID               integer                       ,
    LONGNAME               char(30)                      ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    permanent              varchar(3)                    ,
    primary key (CITY_ID)
);

/* ============================================================ */
/*   Index: CITYLONGNAME_NDX                                    */
/* ============================================================ */
create index CITYLONGNAME_NDX on CITY_CODE (LONGNAME asc);

/* ============================================================ */
/*   Index: CITYMULTI2_NDX                                      */
/* ============================================================ */
create unique index CITYMULTI2_NDX on CITY_CODE (COUNTRY_ID asc, STATE_ID asc, CITY_ID asc, LONGNAME asc);

/* ============================================================ */
/*   Table: MASTER_AREA_CODE                                    */
/* ============================================================ */
create table MASTER_AREA_CODE
(
    AREA_CODE              varchar(3)            not null,
    AREA_CODE_SEQ          integer               not null,
    STATE_ID               integer               not null,
    ESTIMATED_CITY         integer                       ,
    TIME_ZONE              smallint                      ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    primary key (AREA_CODE, AREA_CODE_SEQ)
);

/* ============================================================ */
/*   Index: MAC_1_NDX                                           */
/* ============================================================ */
create index MAC_1_NDX on MASTER_AREA_CODE (ESTIMATED_CITY asc, STATE_ID asc, TIME_ZONE asc);

/* ============================================================ */
/*   Index: MAC_2_NDX                                           */
/* ============================================================ */
create index MAC_2_NDX on MASTER_AREA_CODE (AREA_CODE asc, STATE_ID asc, ESTIMATED_CITY asc, TIME_ZONE asc);

/* ============================================================ */
/*   Index: MAC_ESTIMATEDCITY_NDX                               */
/* ============================================================ */
create index MAC_ESTIMATEDCITY_NDX on MASTER_AREA_CODE (ESTIMATED_CITY asc);

/* ============================================================ */
/*   Table: project_scr                                         */
/* ============================================================ */
create table project_scr
(
    id                     integer               not null
        default autoincrement
        check (id >= 1),
    project_id             integer                       ,
    description            varchar(15)                   ,
    begin_date             date                          ,
    end_date               date                          ,
    est_compl_hours        decimal(8,2)                  ,
    actual_compl_hours     decimal(8,2)                  ,
    date_created           date                          ,
    date_updated           date                          ,
    user_id                varchar(8)                    ,
    primary key (id)
);

/* ============================================================ */
/*   Table: AUDIO_VIDEO_TRACKS                                  */
/* ============================================================ */
create table AUDIO_VIDEO_TRACKS
(
    ID                     integer               not null,
    AUDIO_VIDEO_DETAIL_ID  integer               not null,
    TRACK_NUMBER           smallint              not null,
    TRACK_TITLE            char(30)                      ,
    TRACK_HOURS            smallint                      ,
    TRACK_MINUTES          smallint                      ,
    TRACK_SECONDS          smallint                      ,
    PROJECT_SIDE           smallint                      ,
    TRACK_PRODUCER         char(20)                      ,
    TRACK_COMPOSER         char(20)                      ,
    TRACK_LYRICIST         char(20)                      ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: AVD_ROW_NDX                                         */
/* ============================================================ */
create index AVD_ROW_NDX on AUDIO_VIDEO_TRACKS (TRACK_NUMBER asc, AUDIO_VIDEO_DETAIL_ID asc, TRACK_TITLE asc, TRACK_HOURS asc, TRACK_MINUTES asc, TRACK_SECONDS asc);

/* ============================================================ */
/*   Index: AVT_HOURS_NDX                                       */
/* ============================================================ */
create index AVT_HOURS_NDX on AUDIO_VIDEO_TRACKS (TRACK_HOURS asc);

/* ============================================================ */
/*   Index: AVT_MINUETS_NDX                                     */
/* ============================================================ */
create index AVT_MINUETS_NDX on AUDIO_VIDEO_TRACKS (TRACK_MINUTES asc);

/* ============================================================ */
/*   Index: AVT_PROJSIDE_NDX                                    */
/* ============================================================ */
create index AVT_PROJSIDE_NDX on AUDIO_VIDEO_TRACKS (PROJECT_SIDE asc);

/* ============================================================ */
/*   Index: AVT_SECONDS_NDX                                     */
/* ============================================================ */
create index AVT_SECONDS_NDX on AUDIO_VIDEO_TRACKS (TRACK_SECONDS asc);

/* ============================================================ */
/*   Index: AVT_TITLE_NDX                                       */
/* ============================================================ */
create index AVT_TITLE_NDX on AUDIO_VIDEO_TRACKS (TRACK_TITLE asc);

/* ============================================================ */
/*   Index: AVT_TRACKCOMPOSER_NDX                               */
/* ============================================================ */
create index AVT_TRACKCOMPOSER_NDX on AUDIO_VIDEO_TRACKS (TRACK_COMPOSER asc);

/* ============================================================ */
/*   Index: AVT_TRACKDETAILS_NDX                                */
/* ============================================================ */
create index AVT_TRACKDETAILS_NDX on AUDIO_VIDEO_TRACKS (TRACK_NUMBER asc, TRACK_TITLE asc, TRACK_HOURS asc, TRACK_MINUTES asc, TRACK_SECONDS asc);

/* ============================================================ */
/*   Index: AVT_TRACKLYRICIST_NDX                               */
/* ============================================================ */
create index AVT_TRACKLYRICIST_NDX on AUDIO_VIDEO_TRACKS (TRACK_LYRICIST asc);

/* ============================================================ */
/*   Index: AVT_TRACKNUMBER_NDX                                 */
/* ============================================================ */
create index AVT_TRACKNUMBER_NDX on AUDIO_VIDEO_TRACKS (TRACK_NUMBER asc);

/* ============================================================ */
/*   Index: AVT_TRACKPRODUCER_NDX                               */
/* ============================================================ */
create index AVT_TRACKPRODUCER_NDX on AUDIO_VIDEO_TRACKS (TRACK_PRODUCER asc);

/* ============================================================ */
/*   Table: EXPENSE_MASTER                                      */
/* ============================================================ */
create table EXPENSE_MASTER
(
    ID                     integer               not null
        default autoincrement,
    ITEM_ID                integer               not null,
    AMOUNT_PAID            numeric(20,4)                 ,
    TAXDED_ID              integer                       ,
    QUANTITY               integer                       ,
    PREV_TRAN_ID           char(15)                      ,
    RECIEPT_PHOTOID        integer                       ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: EXPENSE_1_NDX                                       */
/* ============================================================ */
create index EXPENSE_1_NDX on EXPENSE_MASTER (ITEM_ID asc, AMOUNT_PAID asc, QUANTITY asc);

/* ============================================================ */
/*   Index: EXPENSE_2_NDX                                       */
/* ============================================================ */
create index EXPENSE_2_NDX on EXPENSE_MASTER (ITEM_ID asc, PREV_TRAN_ID asc);

/* ============================================================ */
/*   Index: EXPENSE_AMTPAID_NDX                                 */
/* ============================================================ */
create index EXPENSE_AMTPAID_NDX on EXPENSE_MASTER (AMOUNT_PAID asc);

/* ============================================================ */
/*   Index: EXPENSE_PREVTRANID_NDX                              */
/* ============================================================ */
create index EXPENSE_PREVTRANID_NDX on EXPENSE_MASTER (PREV_TRAN_ID asc);

/* ============================================================ */
/*   Index: EXPENSE_QUANTITY_NDX                                */
/* ============================================================ */
create index EXPENSE_QUANTITY_NDX on EXPENSE_MASTER (QUANTITY asc);

/* ============================================================ */
/*   Table: GL_TRANSACTION_DETAIL                               */
/* ============================================================ */
create table GL_TRANSACTION_DETAIL
(
    TRANS_ID               integer               not null,
    TRANS_ID_LINE          integer               not null,
    ITEM_ID                integer               not null,
    TRANS_AMOUNT           numeric(20,4)                 ,
    DB_CR_IND              varchar(2)                    ,
    PAYTEND_ID             integer                       ,
    CHECK_NUMBER           varchar(10)                   ,
    TERMINAL_ID            varchar(10)                   ,
    TRANS_ADDRID           integer                       ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       varchar(3)                    
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                varchar(8)                    
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (TRANS_ID, TRANS_ID_LINE)
);

/* ============================================================ */
/*   Index: GL_TRANSDET1_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET1_NDX on GL_TRANSACTION_DETAIL (ITEM_ID asc, CHECK_NUMBER asc);

/* ============================================================ */
/*   Index: GL_TRANSDET2_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET2_NDX on GL_TRANSACTION_DETAIL (ITEM_ID asc, TRANS_AMOUNT asc);

/* ============================================================ */
/*   Index: GL_TRANSDET4_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET4_NDX on GL_TRANSACTION_DETAIL (ITEM_ID asc, TRANS_ADDRID asc);

/* ============================================================ */
/*   Index: GL_TRANSDET5_NDX                                    */
/* ============================================================ */
create index GL_TRANSDET5_NDX on GL_TRANSACTION_DETAIL (ITEM_ID asc, TERMINAL_ID asc);

/* ============================================================ */
/*   Index: GL_TRANSDET6_NDX                                    */
/* ============================================================ */
create unique index GL_TRANSDET6_NDX on GL_TRANSACTION_DETAIL (TRANS_ID asc, TRANS_ID_LINE asc, ITEM_ID asc, DB_CR_IND asc, CHECK_NUMBER asc, TERMINAL_ID asc);

/* ============================================================ */
/*   Index: GL_TRANSDETCHECKNUMBER_NDX                          */
/* ============================================================ */
create index GL_TRANSDETCHECKNUMBER_NDX on GL_TRANSACTION_DETAIL (CHECK_NUMBER asc);

/* ============================================================ */
/*   Index: GL_TRANSDETDBCRIND_NDX                              */
/* ============================================================ */
create index GL_TRANSDETDBCRIND_NDX on GL_TRANSACTION_DETAIL (DB_CR_IND asc);

/* ============================================================ */
/*   Index: GL_TRANSDETTERMID_NDX                               */
/* ============================================================ */
create index GL_TRANSDETTERMID_NDX on GL_TRANSACTION_DETAIL (TERMINAL_ID asc);

/* ============================================================ */
/*   Index: GL_TRANSDETTRANAMT_NDX                              */
/* ============================================================ */
create index GL_TRANSDETTRANAMT_NDX on GL_TRANSACTION_DETAIL (TRANS_AMOUNT asc);

/* ============================================================ */
/*   Table: LIABILITY_MASTER                                    */
/* ============================================================ */
create table LIABILITY_MASTER
(
    ID                     integer               not null
        default autoincrement,
    ITEM_ID                integer               not null,
    PAYMENT_ADDRID         integer                       ,
    PAYMENT_PHONEID        integer                       ,
    CUSTSERV_ADDRID        integer                       ,
    CUSTSERV_PHONEID       integer                       ,
    EXTRA_ADDRID           integer                       ,
    EXTRA_PHONEID          integer                       ,
    ACCOUNT_NUMBER         varchar(20)                   ,
    ACCOUNT_TYPE           char(1)                       ,
    ACCOUNT_STATUS         char(1)                       ,
    APR                    numeric(20,4)                 ,
    CREDIT_LIMIT           numeric(20,4)                 ,
    PREVIOUS_BALANCE       numeric(20,4)                 ,
    CURRENT_BALANCE        numeric(20,4)                 ,
    BILL_FROM_DATE         date                          ,
    BILL_TO_DATE           date                          ,
    PAYMENT_DUE            numeric(20,4)                 ,
    PAYMENT_DUE_DATE       date                          ,
    RECIEPT_PHOTOID        integer                       ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       varchar(3)            not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                varchar(8)            not null
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: BILLFROMDATE_NDX                                    */
/* ============================================================ */
create index BILLFROMDATE_NDX on LIABILITY_MASTER (BILL_FROM_DATE asc);

/* ============================================================ */
/*   Index: BILLTODATE_NDX                                      */
/* ============================================================ */
create index BILLTODATE_NDX on LIABILITY_MASTER (BILL_TO_DATE asc);

/* ============================================================ */
/*   Index: LIABILITY_1_NDX                                     */
/* ============================================================ */
create index LIABILITY_1_NDX on LIABILITY_MASTER (PAYMENT_ADDRID asc, PAYMENT_PHONEID asc);

/* ============================================================ */
/*   Index: LIABILITY_2_NDX                                     */
/* ============================================================ */
create index LIABILITY_2_NDX on LIABILITY_MASTER (CUSTSERV_ADDRID asc, CUSTSERV_PHONEID asc);

/* ============================================================ */
/*   Index: LIABILITY_3_NDX                                     */
/* ============================================================ */
create index LIABILITY_3_NDX on LIABILITY_MASTER (EXTRA_ADDRID asc, EXTRA_PHONEID asc);

/* ============================================================ */
/*   Index: LIABILITY_4_NDX                                     */
/* ============================================================ */
create index LIABILITY_4_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER asc, ACCOUNT_TYPE asc, APR asc, CREDIT_LIMIT asc, PREVIOUS_BALANCE asc, CURRENT_BALANCE asc);

/* ============================================================ */
/*   Index: LIABILITY_5_NDX                                     */
/* ============================================================ */
create index LIABILITY_5_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER asc, ACCOUNT_TYPE asc, PAYMENT_DUE asc, PAYMENT_DUE_DATE asc);

/* ============================================================ */
/*   Index: LIABILITY_ACCOUNTNUMBER_NDX                         */
/* ============================================================ */
create index LIABILITY_ACCOUNTNUMBER_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER asc);

/* ============================================================ */
/*   Index: LIABILITY_ACCOUNTNUMBERTYPE_NDX                     */
/* ============================================================ */
create index LIABILITY_ACCOUNTNUMBERTYPE_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER asc, ACCOUNT_TYPE asc);

/* ============================================================ */
/*   Index: LIABILITY_ACCTAPR_NDX                               */
/* ============================================================ */
create index LIABILITY_ACCTAPR_NDX on LIABILITY_MASTER (APR asc);

/* ============================================================ */
/*   Index: LIABILITY_ACCTSTAT_NDX                              */
/* ============================================================ */
create index LIABILITY_ACCTSTAT_NDX on LIABILITY_MASTER (ACCOUNT_STATUS asc);

/* ============================================================ */
/*   Index: LIABILITY_ACCTTYPE_NDX                              */
/* ============================================================ */
create index LIABILITY_ACCTTYPE_NDX on LIABILITY_MASTER (ACCOUNT_TYPE asc);

/* ============================================================ */
/*   Index: LIABILITY_CURRBAL_NDX                               */
/* ============================================================ */
create index LIABILITY_CURRBAL_NDX on LIABILITY_MASTER (CURRENT_BALANCE asc);

/* ============================================================ */
/*   Index: LIABILITY_LIMIT_NDX                                 */
/* ============================================================ */
create index LIABILITY_LIMIT_NDX on LIABILITY_MASTER (CREDIT_LIMIT asc);

/* ============================================================ */
/*   Index: LIABILITY_MASTER_NDX                                */
/* ============================================================ */
create index LIABILITY_MASTER_NDX on LIABILITY_MASTER (ITEM_ID asc, ACCOUNT_NUMBER asc, ACCOUNT_TYPE asc, ACCOUNT_STATUS asc, CREDIT_LIMIT asc, CURRENT_BALANCE asc, BILL_FROM_DATE asc, BILL_TO_DATE asc, PAYMENT_DUE asc, PAYMENT_DUE_DATE asc);

/* ============================================================ */
/*   Index: LIABILITY_NUMBERTYPESTATUS_NDX                      */
/* ============================================================ */
create index LIABILITY_NUMBERTYPESTATUS_NDX on LIABILITY_MASTER (ACCOUNT_NUMBER asc, ACCOUNT_TYPE asc, ACCOUNT_STATUS asc);

/* ============================================================ */
/*   Index: LIABILITY_PREVBAL_NDX                               */
/* ============================================================ */
create index LIABILITY_PREVBAL_NDX on LIABILITY_MASTER (PREVIOUS_BALANCE asc);

/* ============================================================ */
/*   Index: PAYDUE_NDX                                          */
/* ============================================================ */
create index PAYDUE_NDX on LIABILITY_MASTER (PAYMENT_DUE asc);

/* ============================================================ */
/*   Index: PAYDUEDATE_NDX                                      */
/* ============================================================ */
create index PAYDUEDATE_NDX on LIABILITY_MASTER (PAYMENT_DUE_DATE asc);

/* ============================================================ */
/*   Table: METRO_EXCHANGE_CODES                                */
/* ============================================================ */
create table METRO_EXCHANGE_CODES
(
    STATE_ID               integer               not null
        default autoincrement,
    CITY_ID                integer               not null,
    AREA_CODE              varchar(3)            not null,
    AREA_CODE_SEQ          integer               not null,
    PHONE_PREFIX           varchar(3)            not null,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       varchar(3)                    
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    primary key (STATE_ID, CITY_ID, AREA_CODE, AREA_CODE_SEQ, PHONE_PREFIX)
);

/* ============================================================ */
/*   Index: MEXCH_1_NDX                                         */
/* ============================================================ */
create index MEXCH_1_NDX on METRO_EXCHANGE_CODES (AREA_CODE asc, AREA_CODE_SEQ asc, CITY_ID asc, PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: MEXCH_2_NDX                                         */
/* ============================================================ */
create index MEXCH_2_NDX on METRO_EXCHANGE_CODES (STATE_ID asc, CITY_ID asc, AREA_CODE asc, PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: MEXCH_CITYID_NDX                                    */
/* ============================================================ */
create index MEXCH_CITYID_NDX on METRO_EXCHANGE_CODES (CITY_ID asc);

/* ============================================================ */
/*   Index: MEXCH_CITYPHONEPREFIX_NDX                           */
/* ============================================================ */
create index MEXCH_CITYPHONEPREFIX_NDX on METRO_EXCHANGE_CODES (CITY_ID asc, PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: MEXCH_CITYSTATE_NDX                                 */
/* ============================================================ */
create index MEXCH_CITYSTATE_NDX on METRO_EXCHANGE_CODES (CITY_ID asc, STATE_ID asc);

/* ============================================================ */
/*   Table: PHONE_USAGE_HISTORY                                 */
/* ============================================================ */
create table PHONE_USAGE_HISTORY
(
    AREA_CODE              varchar(3)            not null,
    PHONE_PREFIX           varchar(3)            not null,
    PHONE_SUFFIX           varchar(4)            not null,
    USAGE_DATE             timestamp             not null,
    LONG_DISTANCE_IND      char(1)                       ,
    CONNECT_STATUS         char(1)                       ,
    CONNECT_TYPE           integer                       ,
    CONNECT_TIME           date                          ,
    DISCONNECT_TIME        date                          ,
    ORIGIN_COUNTRY         integer                       ,
    ORIGIN_STATE           integer                       ,
    ORIGIN_CITY            integer                       ,
    ORIGIN_AREA_CODE       varchar(3)                    ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       varchar(3)                    
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                varchar(8)                    
        default 'DBA',
    primary key (AREA_CODE, PHONE_PREFIX, PHONE_SUFFIX, USAGE_DATE)
);

/* ============================================================ */
/*   Index: PHONEUSAGE_1_NDX                                    */
/* ============================================================ */
create index PHONEUSAGE_1_NDX on PHONE_USAGE_HISTORY (AREA_CODE asc, PHONE_PREFIX asc, PHONE_SUFFIX asc, ORIGIN_AREA_CODE asc, ORIGIN_CITY asc);

/* ============================================================ */
/*   Index: PHONEUSAGE_DATE_NDX                                 */
/* ============================================================ */
create index PHONEUSAGE_DATE_NDX on PHONE_USAGE_HISTORY (USAGE_DATE asc);

/* ============================================================ */
/*   Index: PHONEUSAGE_ORIGINAREACODE_NDX                       */
/* ============================================================ */
create index PHONEUSAGE_ORIGINAREACODE_NDX on PHONE_USAGE_HISTORY (ORIGIN_AREA_CODE asc);

/* ============================================================ */
/*   Index: PHONEUSAGE_PHONENUMBER_NDX                          */
/* ============================================================ */
create index PHONEUSAGE_PHONENUMBER_NDX on PHONE_USAGE_HISTORY (AREA_CODE asc, PHONE_PREFIX asc, PHONE_SUFFIX asc);

/* ============================================================ */
/*   Table: REVENUE_MASTER                                      */
/* ============================================================ */
create table REVENUE_MASTER
(
    ID                     integer               not null
        default autoincrement,
    ITEM_ID                integer               not null,
    GROSS_AMOUNT           numeric(20,4)                 ,
    TAXDED_ID              integer                       ,
    NET_AMOUNT             numeric(20,4)                 ,
    PREV_TRAN_ID           char(15)                      ,
    RECIEPT_PHOTOID        integer                       ,
    DATE_CREATED           date                  not null
        default current date,
    DATE_UPDATED           date                  not null
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    AREACODE_CREATED       char(3)               not null
        default '972',
    CITY_CREATED           integer               not null
        default 1,
    USER_ID                char(8)               not null
        default 'DBA',
    COMMENT_ID             integer                       ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: REVENUE_1_NDX                                       */
/* ============================================================ */
create index REVENUE_1_NDX on REVENUE_MASTER (ITEM_ID asc, GROSS_AMOUNT asc, NET_AMOUNT asc);

/* ============================================================ */
/*   Index: REVENUE_2_NDX                                       */
/* ============================================================ */
create index REVENUE_2_NDX on REVENUE_MASTER (ITEM_ID asc, PREV_TRAN_ID asc);

/* ============================================================ */
/*   Index: REVENUE_GROSSAMT_NDX                                */
/* ============================================================ */
create index REVENUE_GROSSAMT_NDX on REVENUE_MASTER (GROSS_AMOUNT asc);

/* ============================================================ */
/*   Index: REVENUE_NETAMT_NDX                                  */
/* ============================================================ */
create index REVENUE_NETAMT_NDX on REVENUE_MASTER (NET_AMOUNT asc);

/* ============================================================ */
/*   Index: REVENUE_PREVTRANID_NDX                              */
/* ============================================================ */
create index REVENUE_PREVTRANID_NDX on REVENUE_MASTER (PREV_TRAN_ID asc);

/* ============================================================ */
/*   Table: SYSTEM_MESSAGE_DETAIL                               */
/* ============================================================ */
create table SYSTEM_MESSAGE_DETAIL
(
    MESG_ID                integer               not null
        default autoincrement,
    MESG_TYPE_ID           integer               not null,
    DESCRIPTION            varchar(100)                  ,
    BUTTON_ID              integer                       ,
    ICON_ID                integer                       ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    CHANGE_REASON          varchar(300)                  ,
    ACTIVE_IND             char(1)                       ,
    AREACODE_CREATED       char(3)                       
        default '972',
    CITY_CREATED           integer                       
        default 1,
    USER_ID                char(8)                       
        default 'DBA',
    primary key (MESG_ID)
);

/* ============================================================ */
/*   Index: DATECHANGED_NDX                                     */
/* ============================================================ */
create index DATECHANGED_NDX on SYSTEM_MESSAGE_DETAIL (DATE_UPDATED asc);

/* ============================================================ */
/*   Index: DATECREATED1_NDX                                    */
/* ============================================================ */
create index DATECREATED1_NDX on SYSTEM_MESSAGE_DETAIL (DATE_CREATED asc);

/* ============================================================ */
/*   Index: DESCRIPTION20_NDX                                   */
/* ============================================================ */
create index DESCRIPTION20_NDX on SYSTEM_MESSAGE_DETAIL (DESCRIPTION asc);

/* ============================================================ */
/*   Table: USER_LOCATION                                       */
/* ============================================================ */
create table USER_LOCATION
(
    ID                     integer               not null
        default autoincrement,
    LOGIN_ID               char(8)                       ,
    USER_NAME              char(30)                      ,
    FIRSTNAME              char(25)                      ,
    LASTNAME               char(25)                      ,
    TITLE                  char(35)                      ,
    START_DATE             date                          
        default current date,
    TERMINATION_DATE       date                          ,
    ACCESS_LEVEL           integer                       ,
    PASSWORD               char(30)                      ,
    TOTAL_LOGONS           integer                       ,
    LOGON_CITY             char(30)                      ,
    LOGON_AREACODE         char(3)                       ,
    LOGON_PHONE_PREFIX     char(3)                       ,
    DATE_CREATED           date                          
        default current date,
    DATE_UPDATED           date                          
        default current date,
    USER_ID                varchar(8)                    ,
    primary key (ID)
);

/* ============================================================ */
/*   Index: LOGON_AREACODE_NDX                                  */
/* ============================================================ */
create index LOGON_AREACODE_NDX on USER_LOCATION (LOGON_AREACODE asc);

/* ============================================================ */
/*   Index: LOGON_AREACODE_PREFIX_NDX                           */
/* ============================================================ */
create index LOGON_AREACODE_PREFIX_NDX on USER_LOCATION (LOGON_AREACODE asc, LOGON_PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: LOGON_CITYAREACODEPREFIX_NDX                        */
/* ============================================================ */
create index LOGON_CITYAREACODEPREFIX_NDX on USER_LOCATION (LOGON_CITY asc, LOGON_AREACODE asc, LOGON_PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: LOGON_PHONEPREFIX_NDX                               */
/* ============================================================ */
create index LOGON_PHONEPREFIX_NDX on USER_LOCATION (LOGON_PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: LOGONCITY_NDX                                       */
/* ============================================================ */
create index LOGONCITY_NDX on USER_LOCATION (LOGON_CITY asc);

/* ============================================================ */
/*   Index: MULTIKEY2_NDX                                       */
/* ============================================================ */
create index MULTIKEY2_NDX on USER_LOCATION (USER_NAME asc, LOGON_CITY asc, LOGON_AREACODE asc, LOGON_PHONE_PREFIX asc);

/* ============================================================ */
/*   Index: USERNAME_NDX                                        */
/* ============================================================ */
create index USERNAME_NDX on USER_LOCATION (USER_NAME asc);

/* ============================================================ */
/*   Table: project_tasks                                       */
/* ============================================================ */
create table project_tasks
(
    id                     integer               not null
        default autoincrement
        check (id >= 1),
    role_id                integer                       ,
    scr_id                 integer                       ,
    date_worked            date                          ,
    hours_worked           decimal(5,2)                  ,
    billable               integer                       ,
    date_created           date                          ,
    date_updated           date                          ,
    user_id                varchar(8)                    ,
    primary key (id)
);

alter table TAXES_AND_DEDUCTIONS
    add foreign key COMMENTS (OTHER_DED_COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table TAXES_AND_DEDUCTIONS
    add foreign key COMMENTS001 (OTHER_TAX_COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table TAXES_AND_DEDUCTIONS
    add foreign key COMMENTS002 (OTHER_DED_COMMENTS_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table TAXES_AND_DEDUCTIONS
    add foreign key COMMENTS003 (OTHER_TAX_COMMENTS_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table GENERAL_CODES
    add foreign key GENERAL_CODES_GROUP (GROUP_ID)
       references GENERAL_CODES_GROUP (GROUP_ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key CITY_CODE (CITY_ID)
       references CITY_CODE (CITY_ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key GENERAL_CODES (ADDRESSTYPE_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key PERSONAL (PERSONAL_ID)
       references PERSONAL (ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key STATES_CODE (STATE_ID)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table ADDRESS
    add foreign key FK_ADDRESS_REF_2486_BUSINESS (BUSINESS_ID)
       references BUSINESS (ID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key ADDRESS (ADDRESS_ID)
       references ADDRESS (ID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key GENERAL_CODES (PAYTEND_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key GL_ITEM_SEQUENCE (ITEM_ID)
       references GL_ITEM_SEQUENCE (ITEM_ID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key PHOTOGRAPH (PHOTOID)
       references PHOTOGRAPH (PHOTOID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key PHOTOGRAPH001 (RECIEPT_PHOTOID)
       references PHOTOGRAPH (PHOTOID) on update restrict on delete restrict;

alter table ASSET_MASTER
    add foreign key TAXES_AND_DEDUCTIONS (TAXDED_ID)
       references TAXES_AND_DEDUCTIONS (TAXDED_ID) on update restrict on delete restrict;

alter table AUDIO_VIDEO_DETAIL
    add foreign key ASSET_MASTER (ASSET_MASTER_ID)
       references ASSET_MASTER (ID) on update restrict on delete restrict;

alter table AUDIO_VIDEO_DETAIL
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table AUDIO_VIDEO_DETAIL
    add foreign key GENERAL_CODES (MEDIA_TYPE)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table AUDIO_VIDEO_DETAIL
    add foreign key GENERAL_CODES001 (CATEGORY)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key GENERAL_CODES (BUSINESS_TYPE)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION
    add foreign key GENERAL_CODES (TRANS_TYPE)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key GENERAL_CODES (PHOTOID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key GENERAL_CODES001 (GENDER_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key GENERAL_CODES002 (MARITAL_STATUS)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key GENERAL_CODES003 (RACE_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key GENERAL_CODES004 (RELATIONSHIP)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PERSONAL
    add foreign key PHOTOGRAPH (PHOTOID)
       references PHOTOGRAPH (PHOTOID) on update restrict on delete restrict;

alter table PHOTOGRAPH
    add foreign key GENERAL_CODES (PHOTOTYPE_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table GL_CATEGORY
    add foreign key GL_ACCOUNT_TYPES (ACCT_TYPE)
       references GL_ACCOUNT_TYPES (ACCT_TYPE) on update restrict on delete restrict;

alter table GL_ACCT_ITEMS
    add foreign key GL_CATEGORY (ACCT_TYPE, ACCT_CATEGORY)
       references GL_CATEGORY (ACCT_TYPE, ACCT_CATEGORY) on update restrict on delete restrict;

alter table GL_ITEM_SEQUENCE
    add foreign key GL_ACCT_ITEMS (ACCT_TYPE, ACCT_CATEGORY, ACCT_ITEM)
       references GL_ACCT_ITEMS (ACCT_TYPE, ACCT_CATEGORY, ACCT_ITEM) on update restrict on delete restrict;

alter table CITY_CODE
    add foreign key STATES_CODE (STATE_ID)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table MASTER_AREA_CODE
    add foreign key STATES_CODE (STATE_ID)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table project_scr
    add foreign key FK_PROJECT__REF_1264_PROJECT_ (project_id)
       references project_master (id) on update restrict on delete restrict;

alter table AUDIO_VIDEO_TRACKS
    add foreign key AUDIO_VIDEO_DETAIL (AUDIO_VIDEO_DETAIL_ID)
       references AUDIO_VIDEO_DETAIL (ID) on update restrict on delete restrict;

alter table AUDIO_VIDEO_TRACKS
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table EXPENSE_MASTER
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table EXPENSE_MASTER
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table EXPENSE_MASTER
    add foreign key GL_ITEM_SEQUENCE (ITEM_ID)
       references GL_ITEM_SEQUENCE (ITEM_ID) on update restrict on delete restrict;

alter table EXPENSE_MASTER
    add foreign key PHOTOGRAPH (RECIEPT_PHOTOID)
       references PHOTOGRAPH (PHOTOID) on update restrict on delete restrict;

alter table EXPENSE_MASTER
    add foreign key TAXES_AND_DEDUCTIONS (TAXDED_ID)
       references TAXES_AND_DEDUCTIONS (TAXDED_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION_DETAIL
    add foreign key ADDRESS (TRANS_ADDRID)
       references ADDRESS (ID) on update restrict on delete restrict;

alter table GL_TRANSACTION_DETAIL
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION_DETAIL
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION_DETAIL
    add foreign key GENERAL_CODES (PAYTEND_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table GL_TRANSACTION_DETAIL
    add foreign key GL_TRANSACTION (TRANS_ID)
       references GL_TRANSACTION (TRANS_ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key ADDRESS (PAYMENT_ADDRID)
       references ADDRESS (ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key ADDRESS001 (CUSTSERV_ADDRID)
       references ADDRESS (ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key ADDRESS002 (EXTRA_ADDRID)
       references ADDRESS (ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key GL_ITEM_SEQUENCE (ITEM_ID)
       references GL_ITEM_SEQUENCE (ITEM_ID) on update restrict on delete restrict;

alter table LIABILITY_MASTER
    add foreign key PHOTOGRAPH (RECIEPT_PHOTOID)
       references PHOTOGRAPH (PHOTOID) on update restrict on delete restrict;

alter table METRO_EXCHANGE_CODES
    add foreign key MASTER_AREA_CODE (AREA_CODE, AREA_CODE_SEQ)
       references MASTER_AREA_CODE (AREA_CODE, AREA_CODE_SEQ) on update restrict on delete restrict;

alter table METRO_EXCHANGE_CODES
    add foreign key STATES_CODE (STATE_ID)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table PHONE_USAGE_HISTORY
    add foreign key CITY_CODE (ORIGIN_CITY)
       references CITY_CODE (CITY_ID) on update restrict on delete restrict;

alter table PHONE_USAGE_HISTORY
    add foreign key GENERAL_CODES (ORIGIN_COUNTRY)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table PHONE_USAGE_HISTORY
    add foreign key STATES_CODE (ORIGIN_STATE)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table REVENUE_MASTER
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table REVENUE_MASTER
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table REVENUE_MASTER
    add foreign key GL_ITEM_SEQUENCE (ITEM_ID)
       references GL_ITEM_SEQUENCE (ITEM_ID) on update restrict on delete restrict;

alter table REVENUE_MASTER
    add foreign key PHOTOGRAPH (RECIEPT_PHOTOID)
       references PHOTOGRAPH (PHOTOID) on update restrict on delete restrict;

alter table REVENUE_MASTER
    add foreign key TAXES_AND_DEDUCTIONS (TAXDED_ID)
       references TAXES_AND_DEDUCTIONS (TAXDED_ID) on update restrict on delete restrict;

alter table SYSTEM_MESSAGE_DETAIL
    add foreign key GENERAL_CODES (MESG_TYPE_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table SYSTEM_MESSAGE_DETAIL
    add foreign key GENERAL_CODES001 (BUTTON_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table SYSTEM_MESSAGE_DETAIL
    add foreign key GENERAL_CODES002 (ICON_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table USER_LOCATION
    add foreign key general_codes (ACCESS_LEVEL)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table project_tasks
    add foreign key FK_PROJECT__REF_1260_GENERAL_ (role_id)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table project_tasks
    add foreign key FK_PROJECT__REF_1261_PROJECT_ (scr_id)
       references project_scr (id) on update restrict on delete restrict;

