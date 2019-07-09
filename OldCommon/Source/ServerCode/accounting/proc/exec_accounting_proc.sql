/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_ITEM_MASTER')
          and type = 'P')
   drop procedure USP_ADD_ITEM_MASTER
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_CUSTOMER')
          and type = 'P')
   drop procedure USP_ADD_CUSTOMER
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_CREDITOR')
          and type = 'P')
   drop procedure USP_ADD_CREDITOR
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_ACCT')
          and type = 'P')
   drop procedure USP_ADD_ACCT
go


if exists (select 1
          from sysobjects
          where  id = object_id('UFN_GET_ACCT_USAGE_COUNT')
          and type in ('IF', 'FN', 'TF'))
   drop function UFN_GET_ACCT_USAGE_COUNT
go


if exists (select 1
          from sysobjects
          where  id = object_id('USP_ADD_SALES_ORDER')
          and type = 'P')
   drop procedure USP_ADD_SALES_ORDER
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_sales_order')
          and type = 'P')
   drop procedure usp_upd_sales_order
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_item_master')
          and type = 'P')
   drop procedure usp_upd_item_master
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_customer')
          and type = 'P')
   drop procedure usp_upd_customer
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_creditor')
          and type = 'P')
   drop procedure usp_upd_creditor
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_acct_catg_type')
          and type = 'P')
   drop procedure usp_upd_acct_catg_type
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_upd_acct')
          and type = 'P')
   drop procedure usp_upd_acct
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_reverse_xact')
          and type = 'P')
   drop procedure usp_reverse_xact
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_post_xact_entry')
          and type = 'P')
   drop procedure usp_post_xact_entry
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_post_xact')
          and type = 'P')
   drop procedure usp_post_xact
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_invoice_sales_order')
          and type = 'P')
   drop procedure usp_invoice_sales_order
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_get_next_acct_seq')
          and type = 'P')
   drop procedure usp_get_next_acct_seq
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_get_customer_balance')
          and type in ('IF', 'FN', 'TF'))
   drop function usp_get_customer_balance
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_get_cur_acct_prd')
          and type = 'P')
   drop procedure usp_get_cur_acct_prd
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_get_creditor_balance')
          and type = 'P')
   drop procedure usp_get_creditor_balance
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_sales_order_items')
          and type = 'P')
   drop procedure usp_del_sales_order_items
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_item_master')
          and type in ('IF', 'FN', 'TF'))
   drop function usp_del_item_master
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_customer')
          and type = 'P')
   drop procedure usp_del_customer
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_creditor')
          and type = 'P')
   drop procedure usp_del_creditor
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_del_acct')
          and type = 'P')
   drop procedure usp_del_acct
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_decode_acct_prd')
          and type = 'P')
   drop procedure usp_decode_acct_prd
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_create_sales_invoice_xact')
          and type = 'P')
   drop procedure usp_create_sales_invoice_xact
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_chg_so_status')
          and type = 'P')
   drop procedure usp_chg_so_status
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_chg_item_status')
          and type = 'P')
   drop procedure usp_chg_item_status
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_cancel_sales_order')
          and type = 'P')
   drop procedure usp_cancel_sales_order
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_build_account_no')
          and type = 'P')
   drop procedure usp_build_account_no
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_xact_type_item_activity')
          and type = 'P')
   drop procedure usp_add_xact_type_item_activity
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_xact_entry')
          and type = 'P')
   drop procedure usp_add_xact_entry
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_xact')
          and type = 'P')
   drop procedure usp_add_xact
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_sales_order_item')
          and type = 'P')
   drop procedure usp_add_sales_order_item
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_customer_activity')
          and type = 'P')
   drop procedure usp_add_customer_activity
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_creditor_activity')
          and type = 'P')
   drop procedure usp_add_creditor_activity
go


if exists (select 1
          from sysobjects
          where  id = object_id('usp_add_acct_catg_type')
          and type = 'P')
   drop procedure usp_add_acct_catg_type
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_xact_type_id')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_xact_type_id
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_so_status_id')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_so_status_id
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_sales_order_item_total')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_sales_order_item_total
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_item_usage_count')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_item_usage_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_item_status_id')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_item_status_id
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_customer_name')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_customer_name
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_customer_balance')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_customer_balance
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_cust_usage_count')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_cust_usage_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_current_so_status_id')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_so_status_id
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_current_so_status_description')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_so_status_description
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_current_so_status')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_so_status
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_current_item_status_id')
          and type = 'P')
   drop procedure ufn_get_current_item_status_id
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_current_item_status')
          and type = 'P')
   drop procedure ufn_get_current_item_status
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_creditor_usage_count')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_creditor_usage_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_creditor_balance')
          and type = 'P')
   drop procedure ufn_get_creditor_balance
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_acct_to_xact_count')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_acct_to_xact_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_acct_subsidiary_count')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_acct_subsidiary_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_acct_item_mast_count')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_acct_item_mast_count
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_account_type')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_account_type
go


if exists (select 1
          from sysobjects
          where  id = object_id('ufn_get_account_id')
          and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_account_id
go


isql ufn_get_account_id.trg
go


isql ufn_get_account_type.trg
go


isql ufn_get_acct_item_mast_count.trg
go


isql ufn_get_acct_subsidiary_count.trg
go


isql ufn_get_acct_to_xact_count.trg
go


isql ufn_get_creditor_balance.trg
go


isql ufn_get_creditor_usage_count.trg
go


isql ufn_get_current_item_status.trg
go


isql ufn_get_current_item_status_id.trg
go


isql ufn_get_current_so_status.trg
go


isql ufn_get_current_so_status_description.trg
go


isql ufn_get_current_so_status_id.trg
go


isql ufn_get_cust_usage_count.trg
go


isql ufn_get_customer_balance.trg
go


isql ufn_get_customer_name.trg
go


isql ufn_get_item_status_id.trg
go


isql ufn_get_item_usage_count.trg
go


isql ufn_get_sales_order_item_total.trg
go


isql ufn_get_so_status_id.trg
go


isql ufn_get_xact_type_id.trg
go


isql usp_add_acct_catg_type.trg
go


isql usp_add_creditor_activity.trg
go


isql usp_add_customer_activity.trg
go


isql usp_add_sales_order_item.trg
go


isql usp_add_xact.trg
go


isql usp_add_xact_entry.trg
go


isql usp_add_xact_type_item_activity.trg
go


isql usp_build_account_no.trg
go


isql usp_cancel_sales_order.trg
go


isql usp_chg_item_status.trg
go


isql usp_chg_so_status.trg
go


isql usp_create_sales_invoice_xact.trg
go


isql usp_decode_acct_prd.trg
go


isql usp_del_acct.trg
go


isql usp_del_creditor.trg
go


isql usp_del_customer.trg
go


isql usp_del_item_master.trg
go


isql usp_del_sales_order_items.trg
go


isql usp_get_creditor_balance.trg
go


isql usp_get_cur_acct_prd.trg
go


isql usp_get_customer_balance.trg
go


isql usp_get_next_acct_seq.trg
go


isql usp_invoice_sales_order.trg
go


isql usp_post_xact.trg
go


isql usp_post_xact_entry.trg
go


isql usp_reverse_xact.trg
go


isql usp_upd_acct.trg
go


isql usp_upd_acct_catg_type.trg
go


isql usp_upd_creditor.trg
go


isql usp_upd_customer.trg
go


isql usp_upd_item_master.trg
go


isql usp_upd_sales_order.trg
go


isql USP_ADD_SALES_ORDER.trg
go


isql UFN_GET_ACCT_USAGE_COUNT.trg
go


isql USP_ADD_ACCT.trg
go


isql USP_ADD_CREDITOR.trg
go


isql USP_ADD_CUSTOMER.trg
go


isql USP_ADD_ITEM_MASTER.trg
go


