/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/16/2006 2:29:33 PM                         */
/*==============================================================*/


if exists (select 1
          from sysobjects
          where  id = object_id('usp_verify_user_login')
          and type = 'P')
   drop procedure usp_verify_user_login
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_user_login_count')
          and type = 'P')
   drop procedure usp_upd_user_login_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_user_login')
          and type = 'P')
   drop procedure usp_upd_user_login
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_person')
          and type = 'P')
   drop procedure usp_upd_person
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_gen_code_grp')
          and type = 'P')
   drop procedure usp_upd_gen_code_grp
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_gen_code')
          and type = 'P')
   drop procedure usp_upd_gen_code
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_business')
          and type = 'P')
   drop procedure usp_upd_business
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_address')
          and type = 'P')
   drop procedure usp_upd_address
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_log_message')
          and type = 'P')
   drop procedure usp_log_message
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_get_system_pref')
          and type = 'P')
   drop procedure usp_get_system_pref
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_user_xref')
          and type = 'P')
   drop procedure usp_del_user_xref
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_user_login')
          and type = 'P')
   drop procedure usp_del_user_login
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_person')
          and type = 'P')
   drop procedure usp_del_person
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_gen_code_grp')
          and type = 'P')
   drop procedure usp_del_gen_code_grp
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_gen_code')
          and type = 'P')
   drop procedure usp_del_gen_code
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_business')
          and type = 'P')
   drop procedure usp_del_business
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_batch_msg')
          and type = 'P')
   drop procedure usp_batch_msg
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_user_xref')
          and type = 'P')
   drop procedure usp_add_user_xref
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_user_login')
          and type = 'P')
   drop procedure usp_add_user_login
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_person')
          and type = 'P')
   drop procedure usp_add_person
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_gen_code_grp')
          and type = 'P')
   drop procedure usp_add_gen_code_grp
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_gen_code')
          and type = 'P')
   drop procedure usp_add_gen_code
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_business')
          and type = 'P')
   drop procedure usp_add_business
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_address')
          and type = 'P')
   drop procedure usp_add_address
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_activate_user_login')
          and type = 'P')
   drop procedure usp_activate_user_login
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_datepart')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_datepart
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_app_user_id')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_app_user_id
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_general_code_description')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_general_code_description
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_convert_date_to_num')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_convert_date_to_num
go


isql ufn_convert_date_to_num.trg
go


isql ufn_general_code_description.trg
go


isql ufn_get_app_user_id.trg
go


isql ufn_get_datepart.trg
go


isql usp_activate_user_login.trg
go


isql usp_add_address.trg
go


isql usp_add_business.trg
go


isql usp_add_gen_code.trg
go


isql usp_add_gen_code_grp.trg
go


isql usp_add_person.trg
go


isql usp_add_user_login.trg
go


isql usp_add_user_xref.trg
go


isql usp_batch_msg.trg
go


isql usp_del_business.trg
go


isql usp_del_gen_code.trg
go


isql usp_del_gen_code_grp.trg
go


isql usp_del_person.trg
go


isql usp_del_user_login.trg
go


isql usp_del_user_xref.trg
go


isql usp_get_system_pref.trg
go


isql usp_log_message.trg
go


isql usp_upd_address.trg
go


isql usp_upd_business.trg
go


isql usp_upd_gen_code.trg
go


isql usp_upd_gen_code_grp.trg
go


isql usp_upd_person.trg
go


isql usp_upd_user_login.trg
go


isql usp_upd_user_login_count.trg
go


isql usp_verify_user_login.trg
go


