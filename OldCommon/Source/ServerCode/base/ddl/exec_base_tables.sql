/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


use Model_RMT2_Base_CodeTables
go


drop database Model_RMT2_Base_CodeTables
go


/*==============================================================*/
/* Database: Model_RMT2_Base_CodeTables                         */
/*==============================================================*/
create database Model_RMT2_Base_CodeTables
go


use Model_RMT2_Base_CodeTables
go


/*==============================================================*/
/* Default: "CURRENT_TIMESTAMP"                                 */
/*==============================================================*/
create default "CURRENT_TIMESTAMP"
    as CURRENT_TIMESTAMP
go


isql ADDRESS.sql
go


isql ADDRESS_COMMENTS.sql
go


isql BATCH_JOB.sql
go


isql BATCH_LOG.sql
go


isql BUSINESS.sql
go


isql GENERAL_CODES.sql
go


isql GENERAL_CODES_GROUP.sql
go


isql PERSON.sql
go


isql SYSTEM_MESSAGE_DETAIL.sql
go


isql SYSTEM_MESSAGE_TYPE.sql
go


isql SYSTEM_PREF.sql
go


isql USER_ACCESS.sql
go


isql USER_DATABASE.sql
go


isql USER_DATABASE_ACCESS.sql
go


isql USER_GROUP.sql
go


isql USER_GROUP_FORMS.sql
go


isql USER_LOGIN.sql
go


isql city_type.sql
go


isql country.sql
go


isql state.sql
go


isql time_zone.sql
go


isql user_connect_xref.sql
go


isql zipcode.sql
go


isql vw_business_address.sql
go


isql vw_person_address.sql
go


alter table ADDRESS
   add constraint FK_ADDRESS_REF_26428_PERSON foreign key (person_id)
      references PERSON (id)
go


alter table ADDRESS
   add constraint FK_ADDRESS_REF_26463_BUSINESS foreign key (business_id)
      references BUSINESS (id)
go


alter table ADDRESS
   add constraint FK_ADDRESS_REFERENCE_ZIPCODE foreign key (zip)
      references zipcode (id)
go


alter table ADDRESS_COMMENTS
   add constraint FK_ADDRESS__REF_26458_ADDRESS foreign key (address_id)
      references ADDRESS (id)
go


alter table BATCH_LOG
   add constraint FK_BTCHLOG_REF_BATCH_JOB foreign key (batch_job_id)
      references BATCH_JOB (id)
go


alter table BUSINESS
   add constraint FK_BUSTYPE_REF_GENCODE foreign key (bus_type)
      references GENERAL_CODES (id)
go


alter table BUSINESS
   add constraint FK_BUSSERV_REF_GENCODE foreign key (serv_type)
      references GENERAL_CODES (id)
go


alter table GENERAL_CODES
   add constraint FK_GENERAL__REF_26468_GENERAL_ foreign key (group_id)
      references GENERAL_CODES_GROUP (id)
go


alter table PERSON
   add constraint FK_PERSON_REF_26413_GENERAL_ foreign key (gender_id)
      references GENERAL_CODES (id)
go


alter table PERSON
   add constraint FK_PERSON_REF_26416_GENERAL_ foreign key (marital_status)
      references GENERAL_CODES (id)
go


alter table PERSON
   add constraint FK_PERSON_REF_26419_GENERAL_ foreign key (race_id)
      references GENERAL_CODES (id)
go


alter table SYSTEM_MESSAGE_DETAIL
   add constraint FK_SYSTEM_M_REF_16130_SYSTEM_M foreign key (system_message_type_id)
      references SYSTEM_MESSAGE_TYPE (id)
go


alter table USER_ACCESS
   add constraint FK_USER_ACC_REF_31514_USER_LOG foreign key (user_login_id)
      references USER_LOGIN (id)
go


alter table USER_ACCESS
   add constraint FK_USER_ACC_REF_31517_USER_GRO foreign key (group_id)
      references USER_GROUP (id)
go


alter table USER_DATABASE_ACCESS
   add constraint FK_USER_DAT_REF_30837_USER_LOG foreign key (user_login_id)
      references USER_LOGIN (id)
go


alter table USER_DATABASE_ACCESS
   add constraint FK_USER_DAT_REF_30840_USER_DAT foreign key (user_database_id)
      references USER_DATABASE (id)
go


alter table USER_GROUP_FORMS
   add constraint FK_USER_GRO_REF_31520_USER_GRO foreign key (group_id)
      references USER_GROUP (id)
go


alter table state
   add constraint FK_STATE_REF_54806_COUNTRY foreign key (country_id)
      references country (id)
go


alter table zipcode
   add constraint FK_ZIPCODE_REF_52725_TIME_ZON foreign key (time_zone)
      references time_zone (id)
go


alter table zipcode
   add constraint FK_ZIPCODE_REF_52732_STATE foreign key (state)
      references state (state_id)
go


alter table zipcode
   add constraint FK_ZIPCODE_REF_52735_CITY_TYP foreign key (city_type)
      references city_type (id)
go


