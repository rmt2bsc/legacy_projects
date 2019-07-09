/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     4/25/2007 1:39:06 AM                         */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('VW_CREDITOR_XACT_HIST')
            and   type = 'V')
   drop view VW_CREDITOR_XACT_HIST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VW_CUSTOMER_NAME')
            and   type = 'V')
   drop view VW_CUSTOMER_NAME
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VW_MULTI_DISBURSE_TYPE_XACT')
            and   type = 'V')
   drop view VW_MULTI_DISBURSE_TYPE_XACT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_account_type_category')
            and   type = 'V')
   drop view vw_account_type_category
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_acct_catg_name')
            and   type = 'V')
   drop view vw_acct_catg_name
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_creditor_business')
            and   type = 'V')
   drop view vw_creditor_business
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_customer_business')
            and   type = 'V')
   drop view vw_customer_business
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_customer_person')
            and   type = 'V')
   drop view vw_customer_person
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_customer_xact_hist')
            and   type = 'V')
   drop view vw_customer_xact_hist
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_item_master')
            and   type = 'V')
   drop view vw_item_master
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_purchase_order_list')
            and   type = 'V')
   drop view vw_purchase_order_list
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_sales_order_invoice')
            and   type = 'V')
   drop view vw_sales_order_invoice
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_salesorder_items_by_salesorder')
            and   type = 'V')
   drop view vw_salesorder_items_by_salesorder
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_vendor_item_purchase_order_item')
            and   type = 'V')
   drop view vw_vendor_item_purchase_order_item
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_vendor_items')
            and   type = 'V')
   drop view vw_vendor_items
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_xact_credit_charge_list')
            and   type = 'V')
   drop view vw_xact_credit_charge_list
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_xact_gl_posting_list')
            and   type = 'V')
   drop view vw_xact_gl_posting_list
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_xact_list')
            and   type = 'V')
   drop view vw_xact_list
go

if exists (select 1
            from  sysobjects
           where  id = object_id('vw_xact_type_item_activity')
            and   type = 'V')
   drop view vw_xact_type_item_activity
go

isql VW_CREDITOR_XACT_HIST.sql
go

isql VW_CUSTOMER_NAME.sql
go

isql VW_MULTI_DISBURSE_TYPE_XACT.sql
go

isql vw_account_type_category.sql
go

isql vw_acct_catg_name.sql
go

isql vw_creditor_business.sql
go

isql vw_customer_business.sql
go

isql vw_customer_person.sql
go

isql vw_customer_xact_hist.sql
go

isql vw_item_master.sql
go

isql vw_purchase_order_list.sql
go

isql vw_sales_order_invoice.sql
go

isql vw_salesorder_items_by_salesorder.sql
go

isql vw_vendor_item_purchase_order_item.sql
go

isql vw_vendor_items.sql
go

isql vw_xact_credit_charge_list.sql
go

isql vw_xact_gl_posting_list.sql
go

isql vw_xact_list.sql
go

isql vw_xact_type_item_activity.sql
go

