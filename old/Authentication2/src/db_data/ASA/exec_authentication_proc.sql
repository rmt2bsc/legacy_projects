/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     5/24/2008 3:30:29 PM                         */
/*==============================================================*/


if exists(select 1 from sys.sysprocedure where proc_name = 'USP_ADD_USER_XREF') then
   drop procedure USP_ADD_USER_XREF
end if;

if exists(select 1 from sys.sysprocedure where proc_name = 'USP_DEL_USER_XREF') then
   drop procedure USP_DEL_USER_XREF
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_APPACCESS_REF_APP') then
    alter table APPLICATION_ACCESS
       delete foreign key FK_APPACCESS_REF_APP
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_APPACCESS_REF_USERLOGIN') then
    alter table APPLICATION_ACCESS
       delete foreign key FK_APPACCESS_REF_USERLOGIN
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_APP_ROLE_REF_30840_APPLICAT') then
    alter table APP_ROLE
       delete foreign key FK_APP_ROLE_REF_30840_APPLICAT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_APP_REF_ROLES') then
    alter table APP_ROLE
       delete foreign key FK_APP_REF_ROLES
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_APP_ROLE_REFERENCE_APPLICAT') then
    alter table APP_ROLE
       delete foreign key FK_APP_ROLE_REFERENCE_APPLICAT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_GRP_ROLE_REF_ROLES2') then
    alter table GROUP_ROLES
       delete foreign key FK_GRP_ROLE_REF_ROLES2
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_GRP_ROLE_REF_USR_GRP') then
    alter table GROUP_ROLES
       delete foreign key FK_GRP_ROLE_REF_USR_GRP
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USRAPP_REF_USRLOG') then
    alter table USER_APP_ROLE
       delete foreign key FK_USRAPP_REF_USRLOG
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USRAPP_REF_APPROLE') then
    alter table USER_APP_ROLE
       delete foreign key FK_USRAPP_REF_APPROLE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USER_LOG_REFERENCE_USER_GRO') then
    alter table USER_LOGIN
       delete foreign key FK_USER_LOG_REFERENCE_USER_GRO
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USER_RES_REF_USR_RSRC_TYP') then
    alter table USER_RESOURCE
       delete foreign key FK_USER_RES_REF_USR_RSRC_TYP
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USER_RES_REF_RES_SUBTYP') then
    alter table USER_RESOURCE
       delete foreign key FK_USER_RES_REF_RES_SUBTYP
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USER_RES_REFERENCE_USER_LOG') then
    alter table USER_RESOURCE_ACCESS
       delete foreign key FK_USER_RES_REFERENCE_USER_LOG
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USER_RES_REFERENCE_USER_GRO') then
    alter table USER_RESOURCE_ACCESS
       delete foreign key FK_USER_RES_REFERENCE_USER_GRO
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USER_RES_ACC_REF_USER_RES') then
    alter table USER_RESOURCE_ACCESS
       delete foreign key FK_USER_RES_ACC_REF_USER_RES
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_USRRESSTYP_REF_USRRESTYP') then
    alter table USER_RESOURCE_SUBTYPE
       delete foreign key FK_USRRESSTYP_REF_USRRESTYP
end if;

if exists(select 1 from sys.systable where table_name='VW_APP_ROLES' and table_type='VIEW') then
   drop view VW_APP_ROLES
end if;

if exists(select 1 from sys.systable where table_name='VW_RESOURCE' and table_type='VIEW') then
   drop view VW_RESOURCE
end if;

if exists(select 1 from sys.systable where table_name='VW_RESOURCE_TYPE' and table_type='VIEW') then
   drop view VW_RESOURCE_TYPE
end if;

if exists(select 1 from sys.systable where table_name='VW_USER_APP_ROLES' and table_type='VIEW') then
   drop view VW_USER_APP_ROLES
end if;

if exists(select 1 from sys.systable where table_name='VW_USER_GROUP' and table_type='VIEW') then
   drop view VW_USER_GROUP
end if;

if exists(select 1 from sys.systable where table_name='VW_USER_RESOURCE_ACCESS' and table_type='VIEW') then
   drop view VW_USER_RESOURCE_ACCESS
end if;

if exists(
   select 1 from sys.systable 
   where table_name='APPLICATION'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table APPLICATION
end if;

if exists(
   select 1 from sys.systable 
   where table_name='APPLICATION_ACCESS'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table APPLICATION_ACCESS
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='NDX1_APPROLE_CODE'
     and t.table_name='APP_ROLE'
) then
   drop index APP_ROLE.NDX1_APPROLE_CODE
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USER_DB_ACCESS_NDX_1'
     and t.table_name='APP_ROLE'
) then
   drop index APP_ROLE.USER_DB_ACCESS_NDX_1
end if;

if exists(
   select 1 from sys.systable 
   where table_name='APP_ROLE'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table APP_ROLE
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='NDX_GRP_ROLES1'
     and t.table_name='GROUP_ROLES'
) then
   drop index GROUP_ROLES.NDX_GRP_ROLES1
end if;

if exists(
   select 1 from sys.systable 
   where table_name='GROUP_ROLES'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table GROUP_ROLES
end if;

if exists(
   select 1 from sys.systable 
   where table_name='ROLES'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table ROLES
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='NDX_USER_ROLE1'
     and t.table_name='USER_APP_ROLE'
) then
   drop index USER_APP_ROLE.NDX_USER_ROLE1
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_APP_ROLE'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_APP_ROLE
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_CONNECT_XREF'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_CONNECT_XREF
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_GROUP'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_GROUP
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USER_LOGIN_NDX_2'
     and t.table_name='USER_LOGIN'
) then
   drop index USER_LOGIN.USER_LOGIN_NDX_2
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='user_login_ndx_1'
     and t.table_name='USER_LOGIN'
) then
   drop index USER_LOGIN.user_login_ndx_1
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USERNAME_NDX2'
     and t.table_name='USER_LOGIN'
) then
   drop index USER_LOGIN.USERNAME_NDX2
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_LOGIN'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_LOGIN
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USERRESOURCE_CODE_NDX'
     and t.table_name='USER_RESOURCE'
) then
   drop index USER_RESOURCE.USERRESOURCE_CODE_NDX
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USERRESOURCES_CODEURL_NDX'
     and t.table_name='USER_RESOURCE'
) then
   drop index USER_RESOURCE.USERRESOURCES_CODEURL_NDX
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USERRESOURCES_TYP_SUBTYP_NDX'
     and t.table_name='USER_RESOURCE'
) then
   drop index USER_RESOURCE.USERRESOURCES_TYP_SUBTYP_NDX
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_RESOURCE'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_RESOURCE
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='USER_ACCESS_NDX_1'
     and t.table_name='USER_RESOURCE_ACCESS'
) then
   drop index USER_RESOURCE_ACCESS.USER_ACCESS_NDX_1
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_RESOURCE_ACCESS'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_RESOURCE_ACCESS
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_RESOURCE_SUBTYPE'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_RESOURCE_SUBTYPE
end if;

if exists(
   select 1 from sys.systable 
   where table_name='USER_RESOURCE_TYPE'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table USER_RESOURCE_TYPE
end if;

/*==============================================================*/
/* Table: APPLICATION                                           */
/*==============================================================*/
print  'creating table: APPLICATION'
create table APPLICATION 
(
   id                   integer                        not null default autoincrement,
   name                 varchar(20),
   description          varchar(100),
   active               smallint,
   date_created         timestamp,
   date_updated         timestamp,
   user_id              varchar(8),
   constraint PK_APPLICATION primary key (id)
);

/*==============================================================*/
/* Table: APPLICATION_ACCESS                                    */
/*==============================================================*/
create table APPLICATION_ACCESS 
(
   id                   integer                        not null default autoincrement,
   application_id       integer,
   user_login_id        integer,
   loggedin             smallint,
   login_date           timestamp,
   last_login_date      timestamp,
   constraint PK_APPLICATION_ACCESS primary key clustered (id)
);

/*==============================================================*/
/* Table: APP_ROLE                                              */
/*==============================================================*/
print  'creating table: APP_ROLE'
create table APP_ROLE 
(
   id                   integer                        not null default autoincrement,
   application_id       integer,
   role_id              integer,
   code                 varchar(12),
   name                 varchar(30),
   description          varchar(150),
   date_created         timestamp,
   date_updated         timestamp,
   user_id              varchar(8),
   constraint PK_APP_ROLE primary key (id)
);

/*==============================================================*/
/* Index: USER_DB_ACCESS_NDX_1                                  */
/*==============================================================*/
create index USER_DB_ACCESS_NDX_1 on APP_ROLE (
role_id ASC,
application_id ASC
);

/*==============================================================*/
/* Index: NDX1_APPROLE_CODE                                     */
/*==============================================================*/
create unique index NDX1_APPROLE_CODE on APP_ROLE (
code ASC
);

/*==============================================================*/
/* Table: GROUP_ROLES                                           */
/*==============================================================*/
create table GROUP_ROLES 
(
   id                   integer                        not null default autoincrement,
   group_id             integer,
   role_id              integer,
   date_created         timestamp                      default 'current_timestamp',
   date_updated         timestamp                      default 'current_timestamp',
   user_id              varchar(8),
   constraint PK_GROUP_ROLES primary key clustered (id),
   constraint AK_KEY_1_GROUP_RO unique (id)
);

/*==============================================================*/
/* Index: NDX_GRP_ROLES1                                        */
/*==============================================================*/
create unique index NDX_GRP_ROLES1 on GROUP_ROLES (
group_id ASC,
role_id ASC
);

/*==============================================================*/
/* Table: ROLES                                                 */
/*==============================================================*/
print  'creating table: ROLES'
create table ROLES 
(
   id                   integer                        not null default autoincrement,
   name                 varchar(10),
   description          varchar(30),
   date_created         timestamp,
   date_updated         timestamp,
   user_id              varchar(8),
   constraint PK_ROLES primary key clustered (id)
);

/*==============================================================*/
/* Table: USER_APP_ROLE                                         */
/*==============================================================*/
print  'creating table: USER_APP_ROLE'
create table USER_APP_ROLE 
(
   id                   integer                        not null,
   app_role_id          integer,
   user_login_id        integer,
   date_created         timestamp                      default 'current_timestamp',
   date_updated         timestamp                      default 'current_timestamp',
   user_id              varchar(8),
   constraint PK_USER_APP_ROLE primary key clustered (id)
);

/*==============================================================*/
/* Index: NDX_USER_ROLE1                                        */
/*==============================================================*/
create unique index NDX_USER_ROLE1 on USER_APP_ROLE (
app_role_id ASC,
user_login_id ASC
);

/*==============================================================*/
/* Table: USER_CONNECT_XREF                                     */
/*==============================================================*/
create table USER_CONNECT_XREF 
(
   id                   integer                        not null default autoincrement,
   con_id               varchar(50),
   con_user_id          varchar(50),
   app_user_id          varchar(50),
   constraint PK_USER_CONNECT_XREF primary key (id)
);

/*==============================================================*/
/* Table: USER_GROUP                                            */
/*==============================================================*/
print  'creating table: USER_GROUP'
create table USER_GROUP 
(
   id                   integer                        not null default autoincrement,
   description          varchar(25),
   date_created         timestamp,
   date_updated         timestamp,
   user_id              varchar(8),
   constraint PK_USER_GROUP primary key (id)
);

BULK INSERT USER_GROUP
   FROM 'C:\projects\Authentication\src\sql\USER_GROUP.dat'
GO

/*==============================================================*/
/* Table: USER_LOGIN                                            */
/*==============================================================*/
print  'creating table: USER_LOGIN'
create table USER_LOGIN 
(
   id                   integer                        not null,
   group_id             integer,
   login_id             varchar(8),
   firstname            varchar(35),
   lastname             varchar(35),
   birth_date           timestamp,
   ssn                  varchar(20),
   start_date           timestamp,
   termination_date     timestamp,
   description          varchar(30),
   password             varchar(500),
   total_logons         integer,
   email                varchar(80),
   active               smallint,
   date_created         timestamp                      default 'CURRENT_TIMESTAMP',
   date_updated         timestamp                      default 'CURRENT_TIMESTAMP',
   user_id              varchar(8),
   constraint PK_USERS primary key (id)
);

BULK INSERT USER_LOGIN
   FROM 'C:\projects\Authentication\src\sql\USER_LOGIN.dat'
GO

/*==============================================================*/
/* Index: USERNAME_NDX2                                         */
/*==============================================================*/
create index USERNAME_NDX2 on USER_LOGIN (
description ASC
);

/*==============================================================*/
/* Index: user_login_ndx_1                                      */
/*==============================================================*/
create index user_login_ndx_1 on USER_LOGIN (
group_id ASC
);

/*==============================================================*/
/* Index: USER_LOGIN_NDX_2                                      */
/*==============================================================*/
create unique index USER_LOGIN_NDX_2 on USER_LOGIN (
login_id ASC
);

/*==============================================================*/
/* Table: USER_RESOURCE                                         */
/*==============================================================*/
print  'creating table: USER_RESOURCE'
create table USER_RESOURCE 
(
   id                   integer                        not null default autoincrement,
   name                 varchar(20),
   resource_type_id     integer                        not null,
   resource_subtype_id  integer,
   url                  varchar(100),
   description          varchar(250),
   secured              smallint                       not null default 0,
   date_created         timestamp                      default 'current_timestamp',
   date_updated         timestamp                      default 'current_timestamp',
   user_id              varchar(8),
   constraint PK_USER_RESOURCE primary key (id)
);

/*==============================================================*/
/* Index: USERRESOURCES_TYP_SUBTYP_NDX                          */
/*==============================================================*/
create index USERRESOURCES_TYP_SUBTYP_NDX on USER_RESOURCE (
resource_type_id ASC,
resource_subtype_id ASC,
name ASC
);

/*==============================================================*/
/* Index: USERRESOURCES_CODEURL_NDX                             */
/*==============================================================*/
create index USERRESOURCES_CODEURL_NDX on USER_RESOURCE (
description ASC,
name ASC,
url ASC
);

/*==============================================================*/
/* Index: USERRESOURCE_CODE_NDX                                 */
/*==============================================================*/
create unique index USERRESOURCE_CODE_NDX on USER_RESOURCE (
name ASC
);

/*==============================================================*/
/* Table: USER_RESOURCE_ACCESS                                  */
/*==============================================================*/
print  'creating table: USER_RESOURCE_ACCESS'
create table USER_RESOURCE_ACCESS 
(
   id                   integer                        not null default autoincrement,
   group_id             integer                        not null,
   resource_id          integer                        not null,
   user_login_id        integer                        not null,
   date_updated         timestamp,
   date_created         timestamp,
   user_id              varchar(8),
   constraint PK_USER_RESOURCE_ACCESS primary key (id)
);

/*==============================================================*/
/* Index: USER_ACCESS_NDX_1                                     */
/*==============================================================*/
create index USER_ACCESS_NDX_1 on USER_RESOURCE_ACCESS (
user_login_id ASC,
group_id ASC
);

/*==============================================================*/
/* Table: USER_RESOURCE_SUBTYPE                                 */
/*==============================================================*/
print  'creating table: USER_RESOURCE_SUBTYPE'
create table USER_RESOURCE_SUBTYPE 
(
   id                   integer                        not null default autoincrement,
   resource_type_id     integer                        not null,
   name                 varchar(10),
   description          varchar(100),
   date_created         timestamp,
   date_updated         timestamp,
   user_id              varchar(8),
   constraint PK_USER_RESOURCE_SUBTYPE primary key clustered (id)
);

/*==============================================================*/
/* Table: USER_RESOURCE_TYPE                                    */
/*==============================================================*/
create table USER_RESOURCE_TYPE 
(
   id                   integer                        not null default autoincrement,
   description          varchar(50),
   date_created         timestamp,
   date_updated         timestamp,
   user_id              varchar(8),
   constraint PK_USER_RESOURCE_TYPE primary key clustered (id)
);

/*==============================================================*/
/* View: VW_APP_ROLES                                           */
/*==============================================================*/
create view VW_APP_ROLES  as
select app_role.id app_role_id,
       app_role.code app_role_code,
       app_role.name app_role_name,
       app_role.description app_role_description,
       roles.id role_id,
       roles.name role_name,
       application.name app_name,
       application.id application_id
from APP_ROLE, 
     APPLICATION,
     ROLES
where app_role.role_id = roles.id and
      app_role.application_id = application.id;

comment on view VW_APP_ROLES is 
'Joins data from the application, app_role, and roles tables.';

/*==============================================================*/
/* View: VW_RESOURCE                                            */
/*==============================================================*/
create view VW_RESOURCE  as
select ur.id,
       ur.name,
       ur.url,
       ur.description,
       ur.secured,
       urt.id type_id,
       urt.description type_descr,
       urst.id subtype_id,
       urst.name subtype_name,
       urst.description subtype_desc
from   USER_RESOURCE ur,
       USER_RESOURCE_TYPE urt,
       USER_RESOURCE_SUBTYPE urst
where  ur.resource_type_id = urt.id
   and ur.resource_subtype_id = urst.id
   and urt.id = urst.resource_type_id;

comment on view VW_RESOURCE is 
'combines data from the user_Resource, user_resource_type, and user_resource_subtype tables.';

/*==============================================================*/
/* View: VW_RESOURCE_TYPE                                       */
/*==============================================================*/
create view VW_RESOURCE_TYPE  as
select urt.id resrc_type_id,
       urt.description resrc_type_name,
       urst.id resrc_subtype_id,
       urst.name resrc_subtype_name,
       urst.description resrc_subtype_desc
from   USER_RESOURCE_TYPE urt,
       USER_RESOURCE_SUBTYPE urst
where  urt.id = urst.resource_type_id;

comment on view VW_RESOURCE_TYPE is 
'Combine data from the user_Resource_type and user_resource_subtype tables.';

/*==============================================================*/
/* View: VW_USER_APP_ROLES                                      */
/*==============================================================*/
create view VW_USER_APP_ROLES  as
select ul.id user_uid,
       ul.login_id login_id,
       ul.firstname firstname,
       ul.lastname lastname,
       ul.birth_date birth_date,
       ul.ssn ssn,
       ul.description user_description,
       ul.start_date start_date,
       ul.termination_date termination_date,
       ul.email email,
       ul.active active,
       ug.id group_id,
       ug.description group_description,
       ar.id app_role_id,
       ar.code app_role_code,
       ar.name app_role_name,
       ar.description app_role_description,
       r.id role_id,
       r.name role_name,
       a.name app_name,
       a.id application_id
from USER_GROUP ug, 
     USER_LOGIN ul, 
     APP_ROLE ar, 
     APPLICATION a,
     ROLES r, 
     USER_APP_ROLE uar
where ul.group_id = ug.id and
      ul.id = uar.user_login_id and
      uar.app_role_id = ar.id and
      ar.role_id = r.id and
      ar.application_id = a.id;

comment on view VW_USER_APP_ROLES is 
'Combines date from the user_login, user_group, user_app_role, app_role, roles, and application tables.';

/*==============================================================*/
/* View: VW_USER_GROUP                                          */
/*==============================================================*/
create view VW_USER_GROUP  as
select user_login.id user_uid,
       user_login.login_id login_id,
       user_login.firstname firstname,
       user_login.lastname lastname,
       user_login.birth_date birth_date,
       user_login.ssn ssn,
       user_login.description user_description,
       user_login.start_date start_date,
       user_login.termination_date termination_date,
       user_login.email email,
       user_login.active active,
       user_group.id group_id,
       user_group.description group_description
from USER_GROUP, USER_LOGIN
where user_group.id = user_login.group_id;

comment on view VW_USER_GROUP is 
'Combines data from the user_login and user_group tables.';

/*==============================================================*/
/* View: VW_USER_RESOURCE_ACCESS                                */
/*==============================================================*/
create view VW_USER_RESOURCE_ACCESS  as
select ur.id resrc_id,
       ur.name resrc_name,
       ur.url resrc_url,
       ur.description resrc_desc,
       ur.secured resrc_secured,
       ur.resource_type_id resrc_type_id,
       urt.description resrc_type_name,
       ur.resource_subtype_id resrc_subtype_id,
       urst.name resrc_subtype_name,
       urst.description resrc_subtype_desc,
       ura.user_login_id user_uid,
       ul.login_id user_login_id,
       ul.firstname user_firstname,
       ul.lastname user_lastname,
       ul.active user_active_status,
       ul.email user_email,
       ura.group_id user_group_id,
       ug.description user_group_name
from   USER_RESOURCE ur,
       USER_RESOURCE_ACCESS ura,
       USER_LOGIN ul,
       USER_GROUP ug,
       USER_RESOURCE_TYPE urt,
       USER_RESOURCE_SUBTYPE urst
where  ura.group_id = ug.id
  and  ura.resource_id = ur.id
  and  ura.user_login_id = ul.id
  and  ur.resource_type_id = urt.id
  and  ur.resource_subtype_id = urst.id;

comment on view VW_USER_RESOURCE_ACCESS is 
'Query that combines data from user_group, user_resource, user_resource_access, user_login, user_resource_type, and user_resource_subtype tables.';

alter table APPLICATION_ACCESS
   add constraint FK_APPACCESS_REF_APP foreign key (application_id)
      references APPLICATION (id)
      on update restrict
      on delete restrict;

alter table APPLICATION_ACCESS
   add constraint FK_APPACCESS_REF_USERLOGIN foreign key (user_login_id)
      references USER_LOGIN (id)
      on update restrict
      on delete restrict;

alter table APP_ROLE
   add constraint FK_APP_ROLE_REF_30840_APPLICAT foreign key (application_id)
      references APPLICATION (id)
      on update restrict
      on delete restrict;

alter table APP_ROLE
   add constraint FK_APP_REF_ROLES foreign key (role_id)
      references ROLES (id)
      on update restrict
      on delete restrict;

alter table APP_ROLE
   add constraint FK_APP_ROLE_REFERENCE_APPLICAT foreign key (application_id)
      references APPLICATION (id)
      on update restrict
      on delete restrict;

alter table GROUP_ROLES
   add constraint FK_GRP_ROLE_REF_ROLES2 foreign key (role_id)
      references ROLES (id)
      on update restrict
      on delete restrict;

alter table GROUP_ROLES
   add constraint FK_GRP_ROLE_REF_USR_GRP foreign key (group_id)
      references USER_GROUP (id)
      on update restrict
      on delete restrict;

alter table USER_APP_ROLE
   add constraint FK_USRAPP_REF_USRLOG foreign key (user_login_id)
      references USER_LOGIN (id)
      on update restrict
      on delete restrict;

alter table USER_APP_ROLE
   add constraint FK_USRAPP_REF_APPROLE foreign key (app_role_id)
      references APP_ROLE (id)
      on update restrict
      on delete restrict;

alter table USER_LOGIN
   add constraint FK_USER_LOG_REFERENCE_USER_GRO foreign key (group_id)
      references USER_GROUP (id)
      on update restrict
      on delete restrict;

alter table USER_RESOURCE
   add constraint FK_USER_RES_REF_USR_RSRC_TYP foreign key (resource_type_id)
      references USER_RESOURCE_TYPE (id)
      on update restrict
      on delete restrict;

alter table USER_RESOURCE
   add constraint FK_USER_RES_REF_RES_SUBTYP foreign key (resource_subtype_id)
      references USER_RESOURCE_SUBTYPE (id)
      on update restrict
      on delete restrict;

alter table USER_RESOURCE_ACCESS
   add constraint FK_USER_RES_REFERENCE_USER_LOG foreign key (user_login_id)
      references USER_LOGIN (id)
      on update restrict
      on delete restrict;

alter table USER_RESOURCE_ACCESS
   add constraint FK_USER_RES_REFERENCE_USER_GRO foreign key (group_id)
      references USER_GROUP (id)
      on update restrict
      on delete restrict;

alter table USER_RESOURCE_ACCESS
   add constraint FK_USER_RES_ACC_REF_USER_RES foreign key (resource_id)
      references USER_RESOURCE (id)
      on update restrict
      on delete restrict;

alter table USER_RESOURCE_SUBTYPE
   add constraint FK_USRRESSTYP_REF_USRRESTYP foreign key (resource_type_id)
      references USER_RESOURCE_TYPE (id)
      on update restrict
      on delete restrict;


/******************************************************************************************************************************
 *         Name: usp_add_user_xref
 *  Descrsiption: Inserts a row into the user_connect_xref.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_user_xref' AND type = 'P')
   DROP PROCEDURE usp_add_user_xref
GO

create procedure usp_add_user_xref @user_id varchar(50) as
  begin
      insert into user_connect_xref
		(con_id,
		 con_user_id,
		 app_user_id)
 	  values
		(@@spid,
		 user,
		 @user_id)
  end
go;


/******************************************************************************************************************************
 *         Name: usp_del_user_xref
 *  Descrsiption: Deletes a row from the user_connect_xref.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_del_user_xref' AND type = 'P')
   DROP PROCEDURE usp_del_user_xref
GO

create procedure usp_del_user_xref @user_id varchar(50) as
  begin
      delete from user_connect_xref
      where app_user_Id = @user_id
  end
go;

