/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 8:02:20 AM                        */
/*==============================================================*/


if exists (select 1
          from sysobjects
          where  id = object_id('USP_APPROVE_PROJECT')
          and type = 'P')
   drop procedure USP_APPROVE_PROJECT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_PROJ_EVENT')
          and type = 'P')
   drop procedure USP_ADD_PROJ_EVENT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_UPD_PROJ_PROJECT')
          and type = 'P')
   drop procedure USP_UPD_PROJ_PROJECT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_UPD_PROJ_EVENT_DETAILS')
          and type = 'P')
   drop procedure USP_UPD_PROJ_EVENT_DETAILS
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_UPD_PROJ_EVENT')
          and type = 'P')
   drop procedure USP_UPD_PROJ_EVENT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_UPD_PROJ_EMPLOYEE')
          and type = 'P')
   drop procedure USP_UPD_PROJ_EMPLOYEE
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_UPD_PROJ_CLIENT')
          and type = 'P')
   drop procedure USP_UPD_PROJ_CLIENT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_SUBMIT_PROJECT')
          and type = 'P')
   drop procedure USP_SUBMIT_PROJECT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_REJECT_PROJECT')
          and type = 'P')
   drop procedure USP_REJECT_PROJECT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_CHG_PROJ_EVENT_STATUS')
          and type = 'P')
   drop procedure USP_CHG_PROJ_EVENT_STATUS
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_CALC_PROJ_HOURS')
          and type = 'P')
   drop procedure USP_CALC_PROJ_HOURS
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_CALC_PROJ_EVENT_PAY')
          and type = 'P')
   drop procedure USP_CALC_PROJ_EVENT_PAY
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_PROJ_PROJECT')
          and type = 'P')
   drop procedure USP_ADD_PROJ_PROJECT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_PROJ_EVENT_DETAILS')
          and type = 'P')
   drop procedure USP_ADD_PROJ_EVENT_DETAILS
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_PROJ_EMPLOYEE')
          and type = 'P')
   drop procedure USP_ADD_PROJ_EMPLOYEE
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_PROJ_CLIENT')
          and type = 'P')
   drop procedure USP_ADD_PROJ_CLIENT
go


if exists (select 1
          from sysobjects
          where  id = object_id('UFN_GET_CUR_PROJ_EVENT_STATUS_ID')
          and type in ('IF', 'FN', 'TF'))
   drop function UFN_GET_CUR_PROJ_EVENT_STATUS_ID
go


isql UFN_GET_CUR_PROJ_EVENT_STATUS_ID.trg
go


isql USP_ADD_PROJ_CLIENT.trg
go


isql USP_ADD_PROJ_EMPLOYEE.trg
go


isql USP_ADD_PROJ_EVENT_DETAILS.trg
go


isql USP_ADD_PROJ_PROJECT.trg
go


isql USP_CALC_PROJ_EVENT_PAY.trg
go


isql USP_CALC_PROJ_HOURS.trg
go


isql USP_CHG_PROJ_EVENT_STATUS.trg
go


isql USP_REJECT_PROJECT.trg
go


isql USP_SUBMIT_PROJECT.trg
go


isql USP_UPD_PROJ_CLIENT.trg
go


isql USP_UPD_PROJ_EMPLOYEE.trg
go


isql USP_UPD_PROJ_EVENT.trg
go


isql USP_UPD_PROJ_EVENT_DETAILS.trg
go


isql USP_UPD_PROJ_PROJECT.trg
go


isql USP_ADD_PROJ_EVENT.trg
go


isql USP_APPROVE_PROJECT.trg
go


