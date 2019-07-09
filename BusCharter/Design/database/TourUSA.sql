%% ============================================================
%%   Database name:  Green_s_Charter_Order                     
%%   DBMS name:      Sybase SQL Anywhere 5.5                   
%%   Created on:     4/3/2004  7:55 AM                         
%% ============================================================

%% ============================================================
%%   Table: NULLTABLE                                          
%% ============================================================
create table NULLTABLE
(
    NULLCOL                  integer                       
);

%% ============================================================
%%   Table: COMPANY_INFO                                       
%% ============================================================
create table COMPANY_INFO
(
    id                       char(10)              not null,
    name                     varchar(50)                   ,
    addr1                    varchar(50)                   ,
    addr2                    varchar(50)                   ,
    city                     varchar(25)                   ,
    state                    varchar(3)                    ,
    zip                      varchar(13)                   ,
    phone                    varchar(20)                   ,
    fax                      varchar(20)                   ,
    cell                     varchar(20)                   ,
    email                    varchar(80)                   ,
    website                  varchar(80)                   ,
    owner_name               varchar(50)                   ,
    active                   smallint                      ,
    primary key (id)
);

%% ============================================================
%%   Table: COMMENTS                                           
%% ============================================================
create table COMMENTS
(
    COMMENT_ID               integer               not null
        default autoincrement,
    COMMENT_TEXT             varchar(300)                  ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current timestamp,
    CHANGE_REASON            varchar(300)                  ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    primary key (COMMENT_ID)
);

%% ============================================================
%%   Table: GENERAL_CODES_GROUP                                
%% ============================================================
create table GENERAL_CODES_GROUP
(
    GROUP_ID                 integer               not null
        default autoincrement,
    DESCRIPTION              varchar(30)                   ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    CHANGE_REASON            varchar(300)                  ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    permanent                varchar(3)                    ,
    primary key (GROUP_ID)
);

%% ============================================================
%%   Table: STATES_CODE                                        
%% ============================================================
create table STATES_CODE
(
    STATE_ID                 integer               not null
        default autoincrement,
    SHORTNAME                char(3)               not null,
    LONGNAME                 char(25)              not null,
    STATE_CAPITAL            integer                       ,
    COUNTRY_ID               integer                       ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    CHANGE_REASON            varchar(300)                  ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    permanent                varchar(3)                    ,
    primary key (STATE_ID)
);

%% ============================================================
%%   Index: NDX_STATECAPITAL                                   
%% ============================================================
create index NDX_STATECAPITAL on STATES_CODE (STATE_CAPITAL asc);

%% ============================================================
%%   Index: NDX_STATESMULTI                                    
%% ============================================================
create unique index NDX_STATESMULTI on STATES_CODE (COUNTRY_ID asc, STATE_ID asc, STATE_CAPITAL asc);

%% ============================================================
%%   Table: GENERAL_CODES                                      
%% ============================================================
create table GENERAL_CODES
(
    CODE_ID                  integer               not null
        default autoincrement,
    GROUP_ID                 integer                       ,
    SHORTDESC                varchar(10)                   ,
    LONGDESC                 varchar(80)                   ,
    COUNTRY_ID               integer                       ,
    GEN_IND_VALUE            varchar(3)                    ,
    BUTTON_API_VALUE         integer                       ,
    BUTTON_DEFAULT           integer                       ,
    ICON_API_VALUE           integer                       ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    CHANGE_REASON            varchar(300)                  ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    permanent                varchar(3)                    ,
    primary key (CODE_ID)
);

%% ============================================================
%%   Index: NDX_BUTTONAPIVALUE                                 
%% ============================================================
create index NDX_BUTTONAPIVALUE on GENERAL_CODES (BUTTON_API_VALUE asc);

%% ============================================================
%%   Index: NDX_BUTTONDEFAULT                                  
%% ============================================================
create index NDX_BUTTONDEFAULT on GENERAL_CODES (BUTTON_DEFAULT asc);

%% ============================================================
%%   Index: NDX_GENINDVALUE                                    
%% ============================================================
create index NDX_GENINDVALUE on GENERAL_CODES (GEN_IND_VALUE asc);

%% ============================================================
%%   Index: NDX_ICONAPIVALUE                                   
%% ============================================================
create index NDX_ICONAPIVALUE on GENERAL_CODES (ICON_API_VALUE asc);

%% ============================================================
%%   Index: NDX_LONGDESC                                       
%% ============================================================
create index NDX_LONGDESC on GENERAL_CODES (LONGDESC asc);

%% ============================================================
%%   Index: NDX_SHORTDESC                                      
%% ============================================================
create index NDX_SHORTDESC on GENERAL_CODES (SHORTDESC asc);

%% ============================================================
%%   Table: BUSINESS                                           
%% ============================================================
create table BUSINESS
(
    ID                       integer               not null
        default autoincrement,
    BUSINESS_TYPE            integer                       
        default 3,
    SERVICE_TYPE             integer                       ,
    LONGNAME                 varchar(40)                   ,
    SHORTNAME                varchar(15)                   ,
    PHOTOID                  integer                       ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    CHANGE_REASON            varchar(300)                  ,
    ACTIVE_IND               char(1)                       ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    COMMENT_ID               integer                       ,
    status                   integer                       ,
    termination_date         date                          ,
    suspension_date          date                          ,
    suspension_removed_date  date                          ,
    active_date              date                          ,
    primary key (ID)
);

%% ============================================================
%%   Index: BUS_LONGNAME_NDX                                   
%% ============================================================
create index BUS_LONGNAME_NDX on BUSINESS (LONGNAME asc);

%% ============================================================
%%   Index: BUS_SERVICETYPE_NDX                                
%% ============================================================
create index BUS_SERVICETYPE_NDX on BUSINESS (SERVICE_TYPE asc);

%% ============================================================
%%   Index: BUS_SHORTNAME_NDX                                  
%% ============================================================
create index BUS_SHORTNAME_NDX on BUSINESS (SHORTNAME asc);

%% ============================================================
%%   Table: orders                                             
%% ============================================================
create table orders
(
    id                       integer               not null
        default autoincrement,
    client_order_id          varchar(20)                   ,
    quote_id                 integer               not null,
    status                   integer               not null,
    flat_rate                numeric(11,2)                 
        default 0,
    milage_rate              numeric(11,2)                 
        default 0,
    deadhead_milage_rate     numeric(11,2)                 
        default 0,
    live_miles               integer                       
        default 0,
    deadhead_miles           integer                       
        default 0,
    hourly_rate              numeric(11,2)                 
        default 0,
    hourly_rate2             numeric(11,2)                 
        default 0,
    total_hours              integer                       
        default 0,
    buscost                  numeric(11,2)                 
        default 0,
    dropoff_collect          varchar(3)                    ,
    dropoff_collect_tender   integer                       ,
    date_created             date                          ,
    date_updated             date                          ,
    user_id                  varchar(10)                   ,
    primary key (id)
);

%% ============================================================
%%   Table: quote                                              
%% ============================================================
create table quote
(
    id                       integer               not null
        default autoincrement,
    company_id               integer               not null,
    client_id                integer               not null,
    charter_party            varchar(30)                   ,
    signage                  varchar(25)                   ,
    bus_count                integer                       ,
    head_count               integer                       ,
    quote_price              numeric(11,2)                 ,
    depart_date              date                          ,
    depart_spottime          time                          ,
    depart_droptime          time                          ,
    return_date              date                          ,
    return_spottime          time                          ,
    return_droptime          time                          ,
    garage_date              date                          ,
    garage_time              time                          ,
    pickup_location          varchar(150)                  ,
    destination              varchar(150)                  ,
    special_instructions     integer                       ,
    date_created             date                          ,
    date_updated             date                          ,
    user_id                  varchar(10)                   ,
    primary key (id)
);

%% ============================================================
%%   Index: ix_departdate                                      
%% ============================================================
create index ix_departdate on quote (depart_date asc);

%% ============================================================
%%   Index: ix_dpartspottime                                   
%% ============================================================
create index ix_dpartspottime on quote (depart_spottime asc);

%% ============================================================
%%   Index: ix_quotebuscount                                   
%% ============================================================
create index ix_quotebuscount on quote (bus_count asc);

%% ============================================================
%%   Index: ix_quotecharterparty                               
%% ============================================================
create index ix_quotecharterparty on quote (charter_party asc);

%% ============================================================
%%   Index: ix_quotereturndate                                 
%% ============================================================
create index ix_quotereturndate on quote (return_date asc);

%% ============================================================
%%   Index: ix_quotereturndroptime                             
%% ============================================================
create index ix_quotereturndroptime on quote (return_droptime asc);

%% ============================================================
%%   Index: ix_quotesinage                                     
%% ============================================================
create index ix_quotesinage on quote (signage asc);

%% ============================================================
%%   Table: CITY_CODE                                          
%% ============================================================
create table CITY_CODE
(
    CITY_ID                  integer               not null
        default autoincrement,
    COUNTRY_ID               integer                       ,
    STATE_ID                 integer                       ,
    LONGNAME                 char(30)                      ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    CHANGE_REASON            varchar(300)                  ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    permanent                varchar(3)                    ,
    primary key (CITY_ID)
);

%% ============================================================
%%   Index: CITYLONGNAME_NDX                                   
%% ============================================================
create index CITYLONGNAME_NDX on CITY_CODE (LONGNAME asc);

%% ============================================================
%%   Index: CITYMULTI2_NDX                                     
%% ============================================================
create unique index CITYMULTI2_NDX on CITY_CODE (COUNTRY_ID asc, STATE_ID asc, CITY_ID asc, LONGNAME asc);

%% ============================================================
%%   Table: client                                             
%% ============================================================
create table client
(
    id                       integer               not null
        default autoincrement,
    contact_fname            varchar(25)                   ,
    contact_lname            varchar(25)                   ,
    contact_company          integer                       ,
    contact_address1         varchar(25)                   ,
    contact_address2         varchar(25)                   ,
    contact_address3         varchar(25)                   ,
    contact_city             integer                       ,
    contact_state_id         integer                       ,
    contact_zip              varchar(10)                   ,
    contact_phone            varchar(10)                   ,
    contact_fax              varchar(10)                   ,
    billing_fname            varchar(25)                   ,
    billing_lname            varchar(25)                   ,
    billing_address1         varchar(25)                   ,
    billing_address2         varchar(25)                   ,
    billing_address3         varchar(25)                   ,
    billing_city             integer                       ,
    billing_state_id         integer                       ,
    billing_zip              varchar(10)                   ,
    billing_phone            varchar(10)                   ,
    billing_fax              varchar(10)                   ,
    isbillingexact           varchar(3)                    ,
    email                    varchar(50)                   ,
    website                  varchar(100)                  ,
    comment_id               integer                       ,
    date_created             timestamp                     ,
    date_updated             timestamp                     ,
    user_id                  varchar(10)                   ,
    primary key (id)
);

%% ============================================================
%%   Index: ix_billingexact                                    
%% ============================================================
create index ix_billingexact on client (isbillingexact asc);

%% ============================================================
%%   Index: ix_clbillfname                                     
%% ============================================================
create index ix_clbillfname on client (billing_fname asc);

%% ============================================================
%%   Index: ix_clbillname                                      
%% ============================================================
create index ix_clbillname on client (billing_lname asc);

%% ============================================================
%%   Index: ix_clfname                                         
%% ============================================================
create index ix_clfname on client (contact_fname asc);

%% ============================================================
%%   Index: ix_cllname                                         
%% ============================================================
create index ix_cllname on client (contact_lname asc);

%% ============================================================
%%   Index: ix_email                                           
%% ============================================================
create index ix_email on client (email asc);

%% ============================================================
%%   Index: ix_website                                         
%% ============================================================
create index ix_website on client (website asc);

%% ============================================================
%%   Table: bus_detail                                         
%% ============================================================
create table bus_detail
(
    id                       integer               not null
        default autoincrement,
    orders_id                integer               not null,
    flat_rate                numeric(11,2)                 
        default 0,
    milage_rate              numeric(11,2)                 
        default 0,
    deadhead_milage_rate     numeric(11,2)                 
        default 0,
    live_miles               integer                       
        default 0,
    deadhead_miles           integer                       
        default 0,
    hourly_rate              numeric(11,2)                 
        default 0,
    hourly_rate2             numeric(11,2)                 
        default 0,
    total_hours              integer                       
        default 0,
    bus_number               integer                       ,
    driver_name              varchar(40)                   ,
    special_instructions     integer                       ,
    date_created             date                          ,
    date_updated             date                          ,
    user_id                  varchar(10)                   ,
    primary key (id)
);

%% ============================================================
%%   Index: ix_busnumber                                       
%% ============================================================
create index ix_busnumber on bus_detail (bus_number asc);

%% ============================================================
%%   Index: ix_drivername                                      
%% ============================================================
create index ix_drivername on bus_detail (driver_name asc);

%% ============================================================
%%   Table: SYSTEM_MESSAGE_DETAIL                              
%% ============================================================
create table SYSTEM_MESSAGE_DETAIL
(
    MESG_ID                  integer               not null
        default autoincrement,
    MESG_TYPE_ID             integer               not null,
    DESCRIPTION              varchar(100)                  ,
    BUTTON_ID                integer                       ,
    ICON_ID                  integer                       ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    CHANGE_REASON            varchar(300)                  ,
    ACTIVE_IND               char(1)                       ,
    AREACODE_CREATED         char(3)                       
        default '972',
    CITY_CREATED             integer                       
        default 1,
    USER_ID                  char(8)                       
        default 'DBA',
    primary key (MESG_ID)
);

%% ============================================================
%%   Index: DATECHANGED_NDX                                    
%% ============================================================
create index DATECHANGED_NDX on SYSTEM_MESSAGE_DETAIL (DATE_UPDATED asc);

%% ============================================================
%%   Index: DATECREATED1_NDX                                   
%% ============================================================
create index DATECREATED1_NDX on SYSTEM_MESSAGE_DETAIL (DATE_CREATED asc);

%% ============================================================
%%   Index: DESCRIPTION20_NDX                                  
%% ============================================================
create index DESCRIPTION20_NDX on SYSTEM_MESSAGE_DETAIL (DESCRIPTION asc);

%% ============================================================
%%   Table: transaction                                        
%% ============================================================
create table transaction
(
    id                       integer               not null
        default autoincrement,
    order_id                 integer               not null,
    amount                   numeric(11,2)                 ,
    trans_date               date                          ,
    trans_type_id            integer                       ,
    payment_type             integer                       ,
    check_no                 varchar(25)                   ,
    notes                    varchar(100)                  ,
    date_created             date                          ,
    date_updated             date                          ,
    user_id                  varchar(10)                   ,
    primary key (id)
);

%% ============================================================
%%   Index: ix_amount                                          
%% ============================================================
create index ix_amount on transaction (amount asc);

%% ============================================================
%%   Index: ix_transxadate                                     
%% ============================================================
create index ix_transxadate on transaction (trans_date asc);

%% ============================================================
%%   Index: ix_transxchechnumber                               
%% ============================================================
create index ix_transxchechnumber on transaction (check_no asc);

%% ============================================================
%%   Table: USER_LOCATION                                      
%% ============================================================
create table USER_LOCATION
(
    ID                       integer               not null
        default autoincrement,
    LOGIN_ID                 char(8)                       ,
    USER_NAME                char(30)                      ,
    FIRSTNAME                char(25)                      ,
    LASTNAME                 char(25)                      ,
    TITLE                    char(35)                      ,
    START_DATE               date                          
        default current date,
    TERMINATION_DATE         date                          ,
    ACCESS_LEVEL             integer                       ,
    PASSWORD                 char(30)                      ,
    TOTAL_LOGONS             integer                       ,
    LOGON_CITY               char(30)                      ,
    LOGON_AREACODE           char(3)                       ,
    LOGON_PHONE_PREFIX       char(3)                       ,
    DATE_CREATED             date                          
        default current date,
    DATE_UPDATED             date                          
        default current date,
    USER_ID                  varchar(8)                    ,
    primary key (ID)
);

%% ============================================================
%%   Index: LOGON_AREACODE_NDX                                 
%% ============================================================
create index LOGON_AREACODE_NDX on USER_LOCATION (LOGON_AREACODE asc);

%% ============================================================
%%   Index: LOGON_AREACODE_PREFIX_NDX                          
%% ============================================================
create index LOGON_AREACODE_PREFIX_NDX on USER_LOCATION (LOGON_AREACODE asc, LOGON_PHONE_PREFIX asc);

%% ============================================================
%%   Index: LOGON_CITYAREACODEPREFIX_NDX                       
%% ============================================================
create index LOGON_CITYAREACODEPREFIX_NDX on USER_LOCATION (LOGON_CITY asc, LOGON_AREACODE asc, LOGON_PHONE_PREFIX asc);

%% ============================================================
%%   Index: LOGON_PHONEPREFIX_NDX                              
%% ============================================================
create index LOGON_PHONEPREFIX_NDX on USER_LOCATION (LOGON_PHONE_PREFIX asc);

%% ============================================================
%%   Index: LOGONCITY_NDX                                      
%% ============================================================
create index LOGONCITY_NDX on USER_LOCATION (LOGON_CITY asc);

%% ============================================================
%%   Index: MULTIKEY2_NDX                                      
%% ============================================================
create index MULTIKEY2_NDX on USER_LOCATION (USER_NAME asc, LOGON_CITY asc, LOGON_AREACODE asc, LOGON_PHONE_PREFIX asc);

%% ============================================================
%%   Index: USERNAME_NDX                                       
%% ============================================================
create index USERNAME_NDX on USER_LOCATION (USER_NAME asc);

alter table GENERAL_CODES
    add foreign key GENERAL_CODES_GROUP (GROUP_ID)
       references GENERAL_CODES_GROUP (GROUP_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key COMMENTS (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key COMMENTS001 (COMMENT_ID)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key GENERAL_CODES (BUSINESS_TYPE)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table BUSINESS
    add foreign key general_codes001 (status)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table orders
    add foreign key general_codes (status)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table orders
    add foreign key quote (quote_id)
       references quote (id) on update restrict on delete restrict;

alter table quote
    add foreign key business (company_id)
       references BUSINESS (ID) on update restrict on delete restrict;

alter table quote
    add foreign key client (client_id)
       references client (id) on update restrict on delete restrict;

alter table quote
    add foreign key comments (special_instructions)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table CITY_CODE
    add foreign key STATES_CODE (STATE_ID)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table client
    add foreign key business (contact_company)
       references BUSINESS (ID) on update restrict on delete restrict;

alter table client
    add foreign key city_code (contact_city)
       references CITY_CODE (CITY_ID) on update restrict on delete restrict;

alter table client
    add foreign key city_code001 (billing_city)
       references CITY_CODE (CITY_ID) on update restrict on delete restrict;

alter table client
    add foreign key comments (comment_id)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table client
    add foreign key states_code (contact_state_id)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table client
    add foreign key states_code001 (billing_state_id)
       references STATES_CODE (STATE_ID) on update restrict on delete restrict;

alter table bus_detail
    add foreign key comments (special_instructions)
       references COMMENTS (COMMENT_ID) on update restrict on delete restrict;

alter table bus_detail
    add foreign key orders (orders_id)
       references orders (id) on update restrict on delete restrict;

alter table SYSTEM_MESSAGE_DETAIL
    add foreign key GENERAL_CODES (MESG_TYPE_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table SYSTEM_MESSAGE_DETAIL
    add foreign key GENERAL_CODES001 (BUTTON_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table SYSTEM_MESSAGE_DETAIL
    add foreign key GENERAL_CODES002 (ICON_ID)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table transaction
    add foreign key general_codes (trans_type_id)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table transaction
    add foreign key general_codes001 (payment_type)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table transaction
    add foreign key orders (order_id)
       references orders (id) on update restrict on delete restrict;

alter table USER_LOCATION
    add foreign key general_codes (ACCESS_LEVEL)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

