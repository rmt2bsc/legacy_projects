/*==============================================================*/
/* Database name:  Model_RMT2_Accounting                        */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:29:29 PM                          */
/*==============================================================*/


use Model_RMT2_Accounting
go


alter table ACCT_PREF
   drop constraint FK_ACCT_PRE_REFERENCE_ACCT_PER
go


alter table ASSET_DETAILS
   drop constraint FK_ASSET_DE_REF_16112_GL_ACCOU
go


alter table CREDITOR
   drop constraint FK_CREDITOR_REF_15706_CREDITOR
go


alter table CREDITOR
   drop constraint FK_CREDITOR_REF_16087_GL_ACCOU
go


alter table CREDITOR_ACTIVITY
   drop constraint FK_CREDITOR_REF_15657_CREDITOR
go


alter table CREDITOR_ACTIVITY
   drop constraint FK_CREDITOR_REF_42940_XACT
go


alter table CUSTOMER
   drop constraint FK_CUSTOMER_REF_15568_GL_ACCOU
go


alter table CUSTOMER_ACTIVITY
   drop constraint FK_CUSTOMER_REF_11478_CUSTOMER
go


alter table CUSTOMER_ACTIVITY
   drop constraint FK_CUSTOMER_REF_42939_XACT
go


alter table GL_ACCOUNTS
   drop constraint FK_GL_ACCOU_REF_23918_GL_ACCOU
go


alter table GL_ACCOUNTS
   drop constraint FK_GL_ACCT_TO_BALTYPE
go


alter table GL_ACCOUNT_CATEGORY
   drop constraint FK_GL_ACCOU_REF_16101_GL_ACCOU
go


alter table GL_ACCOUNT_TYPES
   drop constraint FK_GL_ACCTYPE_BALTYPE
go


alter table GL_SUBSIDIARY
   drop constraint FK_GL_SUBSID_REF_GL_ACCT
go


alter table ITEM_MASTER
   drop constraint FK_ITEM_MAS_REF_60128_CREDITOR
go


alter table ITEM_MASTER
   drop constraint FK_ITEMMAS_REF_ITEMTYPE
go


alter table ITEM_MASTER_STATUS_HIST
   drop constraint FK_ITEM_MAS_REFERENCE_ITEM_MAS
go


alter table ITEM_MASTER_STATUS_HIST
   drop constraint FK_ITEM_MAST_REF_ITEM_MAST_STAT
go


alter table PURCHASE_ORDER
   drop constraint FK_PURCHASE_REF_CREDITOR
go


alter table PURCHASE_ORDER
   drop constraint FK_PRCHORD_REF_PRCHSTAT
go


alter table PURCHASE_ORDER
   drop constraint FK_PURCHASE_REFERENCE_XACT
go


alter table PURCHASE_ORDER_ITEMS
   drop constraint FK_PURORDITEM_REF_ITEM_MAST
go


alter table PURCHASE_ORDER_ITEMS
   drop constraint FK_PRCHORDITM_REF_PRCHORD
go


alter table PURCHASE_ORDER_ITEM_RETURN
   drop constraint FK_PURCHASE_REFERENCE_PURCHASE
go


alter table PURCHASE_ORDER_ITEM_RETURN
   drop constraint FK_POIRTN_REF_POI
go


alter table PURCHASE_ORDER_RETURN
   drop constraint FK_PORTN_REF_PO
go


alter table PURCHASE_ORDER_RETURN
   drop constraint FK_PORTN_REF_XACT
go


alter table PURCHASE_ORDER_STATUS_HIST
   drop constraint FK_POSH__REF_PO
go


alter table PURCHASE_ORDER_STATUS_HIST
   drop constraint FK_POSH_REF_POS
go


alter table SALES_INVOICE
   drop constraint FK_SALES_IN_REF_58521_SALES_OR
go


alter table SALES_INVOICE
   drop constraint FK_SALES_IN_REFERENCE_XACT
go


alter table SALES_ORDER
   drop constraint FK_SALES_OR_REF_58518_CUSTOMER
go


alter table SALES_ORDER_ITEMS
   drop constraint FK_SALES_OR_REFERENCE_ITEM_MAS
go


alter table SALES_ORDER_ITEMS
   drop constraint FK_SALES_OR_1
go


alter table SALES_ORDER_STATUS_HIST
   drop constraint FK_SALES_OR_3
go


alter table SALES_ORDER_STATUS_HIST
   drop constraint FK_SALES_OR_2
go


alter table VENDOR_ITEMS
   drop constraint FK_VENDOR_I_REF_ITEM_MAS
go


alter table VENDOR_ITEMS
   drop constraint FK_VENDOR_I_REF_CREDITOR
go


alter table XACT
   drop constraint FK_XACT_REF_32772_XACT_COD
go


alter table XACT
   drop constraint FK_XACT_REF_61745_XACT_TYP
go


alter table XACT
   drop constraint FK_XACT_REF_XACTTYPE2
go


alter table XACT_CODES
   drop constraint FK_XACT_COD_REF_32775_XACT_COD
go


alter table XACT_COMMENTS
   drop constraint FK_XACT_COM_REF_23270_XACT
go


alter table XACT_POSTING
   drop constraint FK_XACT_POS_REF_13040_GL_ACCOU
go


alter table XACT_POSTING
   drop constraint FK_XACT_POS_REF_42936_XACT
go


alter table XACT_POSTING
   drop constraint FK_XACT_POS_REFERENCE_ACCT_PER
go


alter table XACT_POSTING
   drop constraint FK_XACT_POS_REF_GL_SUBSID
go


alter table XACT_TYPE
   drop constraint FK_XACT_TYP_REF_1022_XACT_CAT
go


alter table XACT_TYPE
   drop constraint FK_XACT_TYP_REF_63386_GL_ACCOU
go


alter table XACT_TYPE
   drop constraint FK_XACT_TYP_REF_63389_GL_ACCOU
go


alter table XACT_TYPE
   drop constraint FK_XACT_TYP_REF_TO_GL_ACCTCATG
go


alter table XACT_TYPE
   drop constraint FK_XACT_TYP_REF_FROM_GL_ACCTCATG
go


alter table XACT_TYPE_ITEM
   drop constraint FK_XACT_TYP_ITM_REF_XACT_TYP
go


alter table XACT_TYPE_ITEM_ACTIVITY
   drop constraint FK_EXP_REF_XACT01
go


alter table XACT_TYPE_ITEM_ACTIVITY
   drop constraint FK_XACT_ACT_REF_XACT_TYP_ITM
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'ASSETMASTER_1_NDX'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.ASSETMASTER_1_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'ASSETMASTER_2_NDX'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.ASSETMASTER_2_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'ASSETMASTER_3_NDX'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.ASSETMASTER_3_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'ASSETMASTER_UPC_NDX'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.ASSETMASTER_UPC_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'ASSET_SN_NDX'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.ASSET_SN_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'b4444'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.b4444
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'hhhhhhh'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.hhhhhhh
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'ju55y5jes'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.ju55y5jes
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'jyht544'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.jyht544
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 't5hgfjjjj'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.t5hgfjjjj
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'y64wyw4w'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.y64wyw4w
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ASSET_DETAILS')
            and   name  = 'yjy54332'
            and   indid > 0
            and   indid < 255)
   drop index ASSET_DETAILS.yjy54332
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CREDITOR')
            and   name  = 'creditor_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index CREDITOR.creditor_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CREDITOR')
            and   name  = 'creditor_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index CREDITOR.creditor_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CREDITOR_ACTIVITY')
            and   name  = 'cred_act_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index CREDITOR_ACTIVITY.cred_act_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CREDITOR_ACTIVITY')
            and   name  = 'cred_act_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index CREDITOR_ACTIVITY.cred_act_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER')
            and   name  = 'cust_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER.cust_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER')
            and   name  = 'cust_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER.cust_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER')
            and   name  = 'cust_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER.cust_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER')
            and   name  = 'cust_ndx_4'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER.cust_ndx_4
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER_ACTIVITY')
            and   name  = 'cust_act_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER_ACTIVITY.cust_act_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER_ACTIVITY')
            and   name  = 'cust_act_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER_ACTIVITY.cust_act_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('CUSTOMER_ACTIVITY')
            and   name  = 'cust_act_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index CUSTOMER_ACTIVITY.cust_act_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('GL_ACCOUNTS')
            and   name  = 'GL_ACCT_NDX_1'
            and   indid > 0
            and   indid < 255)
   drop index GL_ACCOUNTS.GL_ACCT_NDX_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('GL_ACCOUNTS')
            and   name  = 'GL_ACCT_NDX_2'
            and   indid > 0
            and   indid < 255)
   drop index GL_ACCOUNTS.GL_ACCT_NDX_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('GL_ACCOUNTS')
            and   name  = 'GL_ACCT_NDX_3'
            and   indid > 0
            and   indid < 255)
   drop index GL_ACCOUNTS.GL_ACCT_NDX_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('GL_ACCOUNT_CATEGORY')
            and   name  = 'GL_ACCT_CAT_NDX_1'
            and   indid > 0
            and   indid < 255)
   drop index GL_ACCOUNT_CATEGORY.GL_ACCT_CAT_NDX_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('GL_ACCOUNT_CATEGORY')
            and   name  = 'GL_ACCT_CAT_NDX_2'
            and   indid > 0
            and   indid < 255)
   drop index GL_ACCOUNT_CATEGORY.GL_ACCT_CAT_NDX_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('GL_ACCOUNT_TYPES')
            and   name  = 'DESC1'
            and   indid > 0
            and   indid < 255)
   drop index GL_ACCOUNT_TYPES.DESC1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER')
            and   name  = 'item_mast_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER.item_mast_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER')
            and   name  = 'item_mast_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER.item_mast_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER')
            and   name  = 'item_mast_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER.item_mast_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER')
            and   name  = 'item_mast_ndx_4'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER.item_mast_ndx_4
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER_STATUS_HIST')
            and   name  = 'item_mast_stat_hst_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER_STATUS_HIST.item_mast_stat_hst_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER_STATUS_HIST')
            and   name  = 'item_mast_stat_hst_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER_STATUS_HIST.item_mast_stat_hst_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER_STATUS_HIST')
            and   name  = 'item_mast_stat_hst_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER_STATUS_HIST.item_mast_stat_hst_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('ITEM_MASTER_STATUS_HIST')
            and   name  = 'item_mast_stat_hst_ndx_4'
            and   indid > 0
            and   indid < 255)
   drop index ITEM_MASTER_STATUS_HIST.item_mast_stat_hst_ndx_4
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PURCHASE_ORDER_ITEMS')
            and   name  = 'prchorditem_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index PURCHASE_ORDER_ITEMS.prchorditem_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('PURCHASE_ORDER_STATUS_HIST')
            and   name  = 'Index_POSH1'
            and   indid > 0
            and   indid < 255)
   drop index PURCHASE_ORDER_STATUS_HIST.Index_POSH1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_INVOICE')
            and   name  = 'sls_inv_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index SALES_INVOICE.sls_inv_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_INVOICE')
            and   name  = 'sls_inv_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index SALES_INVOICE.sls_inv_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_INVOICE')
            and   name  = 'sls_inv_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index SALES_INVOICE.sls_inv_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_INVOICE')
            and   name  = 'sls_inv_ndx_4'
            and   indid > 0
            and   indid < 255)
   drop index SALES_INVOICE.sls_inv_ndx_4
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER')
            and   name  = 'sls_ord_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER.sls_ord_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER')
            and   name  = 'sls_ord_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER.sls_ord_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER')
            and   name  = 'sls_ord_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER.sls_ord_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER_ITEMS')
            and   name  = 'sls_ord_items_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER_ITEMS.sls_ord_items_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER_ITEMS')
            and   name  = 'sls_ord_items_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER_ITEMS.sls_ord_items_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER_ITEMS')
            and   name  = 'sls_ord_items_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER_ITEMS.sls_ord_items_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER_STATUS_HIST')
            and   name  = 'sls_ord_sts_hist_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER_STATUS_HIST.sls_ord_sts_hist_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('SALES_ORDER_STATUS_HIST')
            and   name  = 'sls_ord_sts_hist_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index SALES_ORDER_STATUS_HIST.sls_ord_sts_hist_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANS1_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANS1_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANS2_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANS2_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANSBANKIND_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANSBANKIND_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANSDATETYPE_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANSDATETYPE_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANSDATE_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANSDATE_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANSGROUP_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANSGROUP_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT')
            and   name  = 'GL_TRANSPE_NDX'
            and   indid > 0
            and   indid < 255)
   drop index XACT.GL_TRANSPE_NDX
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_CODES')
            and   name  = 'xact_codes_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index XACT_CODES.xact_codes_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_CODES')
            and   name  = 'xact_codes_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index XACT_CODES.xact_codes_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_POSTING')
            and   name  = 'xact_post_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index XACT_POSTING.xact_post_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_POSTING')
            and   name  = 'xact_post_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index XACT_POSTING.xact_post_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_POSTING')
            and   name  = 'xact_post_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index XACT_POSTING.xact_post_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_POSTING')
            and   name  = 'xact_post_ndx_4'
            and   indid > 0
            and   indid < 255)
   drop index XACT_POSTING.xact_post_ndx_4
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_POSTING')
            and   name  = 'xact_post_ndx_5'
            and   indid > 0
            and   indid < 255)
   drop index XACT_POSTING.xact_post_ndx_5
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_POSTING')
            and   name  = 'xact_post_ndx_6'
            and   indid > 0
            and   indid < 255)
   drop index XACT_POSTING.xact_post_ndx_6
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_TYPE')
            and   name  = 'xact_type_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index XACT_TYPE.xact_type_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_TYPE')
            and   name  = 'xact_type_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index XACT_TYPE.xact_type_ndx_2
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_TYPE')
            and   name  = 'xact_type_ndx_3'
            and   indid > 0
            and   indid < 255)
   drop index XACT_TYPE.xact_type_ndx_3
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_TYPE_ITEM_ACTIVITY')
            and   name  = 'exp_act_ndx_1'
            and   indid > 0
            and   indid < 255)
   drop index XACT_TYPE_ITEM_ACTIVITY.exp_act_ndx_1
go


if exists (select 1
            from  sysindexes
           where  id    = object_id('XACT_TYPE_ITEM_ACTIVITY')
            and   name  = 'exp_act_ndx_2'
            and   indid > 0
            and   indid < 255)
   drop index XACT_TYPE_ITEM_ACTIVITY.exp_act_ndx_2
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ACCT_PERIOD')
            and   type = 'U')
   drop table ACCT_PERIOD
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ACCT_PREF')
            and   type = 'U')
   drop table ACCT_PREF
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ASSET_DETAILS')
            and   type = 'U')
   drop table ASSET_DETAILS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('CREDITOR')
            and   type = 'U')
   drop table CREDITOR
go


if exists (select 1
            from  sysobjects
           where  id = object_id('CREDITOR_ACTIVITY')
            and   type = 'U')
   drop table CREDITOR_ACTIVITY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('CREDITOR_TYPE')
            and   type = 'U')
   drop table CREDITOR_TYPE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('CUSTOMER')
            and   type = 'U')
   drop table CUSTOMER
go


if exists (select 1
            from  sysobjects
           where  id = object_id('CUSTOMER_ACTIVITY')
            and   type = 'U')
   drop table CUSTOMER_ACTIVITY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('GL_ACCOUNTS')
            and   type = 'U')
   drop table GL_ACCOUNTS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('GL_ACCOUNT_CATEGORY')
            and   type = 'U')
   drop table GL_ACCOUNT_CATEGORY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('GL_ACCOUNT_TYPES')
            and   type = 'U')
   drop table GL_ACCOUNT_TYPES
go


if exists (select 1
            from  sysobjects
           where  id = object_id('GL_BALANCE_TYPE')
            and   type = 'U')
   drop table GL_BALANCE_TYPE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('GL_SUBSIDIARY')
            and   type = 'U')
   drop table GL_SUBSIDIARY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ITEM_MASTER')
            and   type = 'U')
   drop table ITEM_MASTER
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ITEM_MASTER_STATUS')
            and   type = 'U')
   drop table ITEM_MASTER_STATUS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ITEM_MASTER_STATUS_HIST')
            and   type = 'U')
   drop table ITEM_MASTER_STATUS_HIST
go


if exists (select 1
            from  sysobjects
           where  id = object_id('ITEM_MASTER_TYPE')
            and   type = 'U')
   drop table ITEM_MASTER_TYPE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PURCHASE_ORDER')
            and   type = 'U')
   drop table PURCHASE_ORDER
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PURCHASE_ORDER_ITEMS')
            and   type = 'U')
   drop table PURCHASE_ORDER_ITEMS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PURCHASE_ORDER_ITEM_RETURN')
            and   type = 'U')
   drop table PURCHASE_ORDER_ITEM_RETURN
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PURCHASE_ORDER_RETURN')
            and   type = 'U')
   drop table PURCHASE_ORDER_RETURN
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PURCHASE_ORDER_STATUS')
            and   type = 'U')
   drop table PURCHASE_ORDER_STATUS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('PURCHASE_ORDER_STATUS_HIST')
            and   type = 'U')
   drop table PURCHASE_ORDER_STATUS_HIST
go


if exists (select 1
            from  sysobjects
           where  id = object_id('SALES_INVOICE')
            and   type = 'U')
   drop table SALES_INVOICE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('SALES_ORDER')
            and   type = 'U')
   drop table SALES_ORDER
go


if exists (select 1
            from  sysobjects
           where  id = object_id('SALES_ORDER_ITEMS')
            and   type = 'U')
   drop table SALES_ORDER_ITEMS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('SALES_ORDER_STATUS')
            and   type = 'U')
   drop table SALES_ORDER_STATUS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('SALES_ORDER_STATUS_HIST')
            and   type = 'U')
   drop table SALES_ORDER_STATUS_HIST
go


if exists (select 1
            from  sysobjects
           where  id = object_id('VENDOR_ITEMS')
            and   type = 'U')
   drop table VENDOR_ITEMS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT')
            and   type = 'U')
   drop table XACT
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_CATEGORY')
            and   type = 'U')
   drop table XACT_CATEGORY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_CODES')
            and   type = 'U')
   drop table XACT_CODES
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_CODE_GROUP')
            and   type = 'U')
   drop table XACT_CODE_GROUP
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_COMMENTS')
            and   type = 'U')
   drop table XACT_COMMENTS
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_POSTING')
            and   type = 'U')
   drop table XACT_POSTING
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_TYPE')
            and   type = 'U')
   drop table XACT_TYPE
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_TYPE_ITEM')
            and   type = 'U')
   drop table XACT_TYPE_ITEM
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_TYPE_ITEM_ACTIVITY')
            and   type = 'U')
   drop table XACT_TYPE_ITEM_ACTIVITY
go


if exists (select 1
            from  sysobjects
           where  id = object_id('XACT_UPLOAD')
            and   type = 'U')
   drop table XACT_UPLOAD
go


if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'False'
   )
   drop default False
go


if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'No'
   )
   drop default No
go


if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'True'
   )
   drop default True
go


if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'Yes'
   )
   drop default Yes
go


use Model_RMT2_Accounting
go


/*==============================================================*/
/* Default: False                                               */
/*==============================================================*/
create default False
    as 0
go


/*==============================================================*/
/* Default: No                                                  */
/*==============================================================*/
create default No
    as 'N'
go


/*==============================================================*/
/* Default: True                                                */
/*==============================================================*/
create default True
    as 1
go


/*==============================================================*/
/* Default: Yes                                                 */
/*==============================================================*/
create default Yes
    as 'Y'
go


isql ACCT_PERIOD.sql
go


isql ACCT_PREF.sql
go


isql ASSET_DETAILS.sql
go


isql CREDITOR.sql
go


isql CREDITOR_ACTIVITY.sql
go


isql CREDITOR_TYPE.sql
go


isql CUSTOMER.sql
go


isql CUSTOMER_ACTIVITY.sql
go


isql GL_ACCOUNTS.sql
go


isql GL_ACCOUNT_CATEGORY.sql
go


isql GL_ACCOUNT_TYPES.sql
go


isql GL_BALANCE_TYPE.sql
go


isql GL_SUBSIDIARY.sql
go


isql ITEM_MASTER.sql
go


isql ITEM_MASTER_STATUS.sql
go


isql ITEM_MASTER_STATUS_HIST.sql
go


isql ITEM_MASTER_TYPE.sql
go


isql PURCHASE_ORDER.sql
go


isql PURCHASE_ORDER_ITEMS.sql
go


isql PURCHASE_ORDER_ITEM_RETURN.sql
go


isql PURCHASE_ORDER_RETURN.sql
go


isql PURCHASE_ORDER_STATUS.sql
go


isql PURCHASE_ORDER_STATUS_HIST.sql
go


isql SALES_INVOICE.sql
go


isql SALES_ORDER.sql
go


isql SALES_ORDER_ITEMS.sql
go


isql SALES_ORDER_STATUS.sql
go


isql SALES_ORDER_STATUS_HIST.sql
go


isql VENDOR_ITEMS.sql
go


isql XACT.sql
go


isql XACT_CATEGORY.sql
go


isql XACT_CODES.sql
go


isql XACT_CODE_GROUP.sql
go


isql XACT_COMMENTS.sql
go


isql XACT_POSTING.sql
go


isql XACT_TYPE.sql
go


isql XACT_TYPE_ITEM.sql
go


isql XACT_TYPE_ITEM_ACTIVITY.sql
go


isql XACT_UPLOAD.sql
go


alter table ACCT_PREF
   add constraint FK_ACCT_PRE_REFERENCE_ACCT_PER foreign key (acct_prd_type_id)
      references ACCT_PERIOD (id)
go


alter table ASSET_DETAILS
   add constraint FK_ASSET_DE_REF_16112_GL_ACCOU foreign key (gl_account_id)
      references GL_ACCOUNTS (id)
go


alter table CREDITOR
   add constraint FK_CREDITOR_REF_15706_CREDITOR foreign key (creditor_type_id)
      references CREDITOR_TYPE (id)
go


alter table CREDITOR
   add constraint FK_CREDITOR_REF_16087_GL_ACCOU foreign key (gl_account_id)
      references GL_ACCOUNTS (id)
go


alter table CREDITOR_ACTIVITY
   add constraint FK_CREDITOR_REF_15657_CREDITOR foreign key (creditor_id)
      references CREDITOR (id)
go


alter table CREDITOR_ACTIVITY
   add constraint FK_CREDITOR_REF_42940_XACT foreign key (xact_id)
      references XACT (id)
go


alter table CUSTOMER
   add constraint FK_CUSTOMER_REF_15568_GL_ACCOU foreign key (gl_account_id)
      references GL_ACCOUNTS (id)
go


alter table CUSTOMER_ACTIVITY
   add constraint FK_CUSTOMER_REF_11478_CUSTOMER foreign key (customer_id)
      references CUSTOMER (id)
go


alter table CUSTOMER_ACTIVITY
   add constraint FK_CUSTOMER_REF_42939_XACT foreign key (xact_id)
      references XACT (id)
go


alter table GL_ACCOUNTS
   add constraint FK_GL_ACCOU_REF_23918_GL_ACCOU foreign key (acct_cat_id)
      references GL_ACCOUNT_CATEGORY (id)
go


alter table GL_ACCOUNTS
   add constraint FK_GL_ACCT_TO_BALTYPE foreign key (balance_type_id)
      references GL_BALANCE_TYPE (id)
go


alter table GL_ACCOUNT_CATEGORY
   add constraint FK_GL_ACCOU_REF_16101_GL_ACCOU foreign key (acct_type_id)
      references GL_ACCOUNT_TYPES (id)
go


alter table GL_ACCOUNT_TYPES
   add constraint FK_GL_ACCTYPE_BALTYPE foreign key (balance_type_id)
      references GL_BALANCE_TYPE (id)
go


alter table GL_SUBSIDIARY
   add constraint FK_GL_SUBSID_REF_GL_ACCT foreign key (gl_account_id)
      references GL_ACCOUNTS (id)
go


alter table ITEM_MASTER
   add constraint FK_ITEM_MAS_REF_60128_CREDITOR foreign key (vendor_id)
      references CREDITOR (id)
go


alter table ITEM_MASTER
   add constraint FK_ITEMMAS_REF_ITEMTYPE foreign key (item_type_id)
      references ITEM_MASTER_TYPE (id)
go


alter table ITEM_MASTER_STATUS_HIST
   add constraint FK_ITEM_MAS_REFERENCE_ITEM_MAS foreign key (item_master_id)
      references ITEM_MASTER (id)
go


alter table ITEM_MASTER_STATUS_HIST
   add constraint FK_ITEM_MAST_REF_ITEM_MAST_STAT foreign key (item_status_id)
      references ITEM_MASTER_STATUS (id)
go


alter table PURCHASE_ORDER
   add constraint FK_PURCHASE_REF_CREDITOR foreign key (vendor_id)
      references CREDITOR (id)
go


alter table PURCHASE_ORDER
   add constraint FK_PRCHORD_REF_PRCHSTAT foreign key (status)
      references PURCHASE_ORDER_STATUS (id)
go


alter table PURCHASE_ORDER
   add constraint FK_PURCHASE_REFERENCE_XACT foreign key (xact_id)
      references XACT (id)
go


alter table PURCHASE_ORDER_ITEMS
   add constraint FK_PURORDITEM_REF_ITEM_MAST foreign key (item_master_id)
      references ITEM_MASTER (id)
go


alter table PURCHASE_ORDER_ITEMS
   add constraint FK_PRCHORDITM_REF_PRCHORD foreign key (purchase_order_id)
      references PURCHASE_ORDER (id)
go


alter table PURCHASE_ORDER_ITEM_RETURN
   add constraint FK_PURCHASE_REFERENCE_PURCHASE foreign key (purchase_order_return_id)
      references PURCHASE_ORDER_RETURN (id)
go


alter table PURCHASE_ORDER_ITEM_RETURN
   add constraint FK_POIRTN_REF_POI foreign key (purchase_order_item_id)
      references PURCHASE_ORDER_ITEMS (id)
go


alter table PURCHASE_ORDER_RETURN
   add constraint FK_PORTN_REF_PO foreign key (purchase_order_id)
      references PURCHASE_ORDER (id)
go


alter table PURCHASE_ORDER_RETURN
   add constraint FK_PORTN_REF_XACT foreign key (xact_id)
      references XACT (id)
go


alter table PURCHASE_ORDER_STATUS_HIST
   add constraint FK_POSH__REF_PO foreign key (purchase_order_id)
      references PURCHASE_ORDER (id)
go


alter table PURCHASE_ORDER_STATUS_HIST
   add constraint FK_POSH_REF_POS foreign key (purchase_order_status_id)
      references PURCHASE_ORDER_STATUS (id)
go


alter table SALES_INVOICE
   add constraint FK_SALES_IN_REF_58521_SALES_OR foreign key (sales_order_id)
      references SALES_ORDER (id)
go


alter table SALES_INVOICE
   add constraint FK_SALES_IN_REFERENCE_XACT foreign key (xact_id)
      references XACT (id)
go


alter table SALES_ORDER
   add constraint FK_SALES_OR_REF_58518_CUSTOMER foreign key (customer_id)
      references CUSTOMER (id)
go


alter table SALES_ORDER_ITEMS
   add constraint FK_SALES_OR_REFERENCE_ITEM_MAS foreign key (item_master_id)
      references ITEM_MASTER (id)
go


alter table SALES_ORDER_ITEMS
   add constraint FK_SALES_OR_1 foreign key (sales_order_id)
      references SALES_ORDER (id)
go


alter table SALES_ORDER_STATUS_HIST
   add constraint FK_SALES_OR_3 foreign key (sales_order_id)
      references SALES_ORDER (id)
go


alter table SALES_ORDER_STATUS_HIST
   add constraint FK_SALES_OR_2 foreign key (sales_order_status_id)
      references SALES_ORDER_STATUS (id)
go


alter table VENDOR_ITEMS
   add constraint FK_VENDOR_I_REF_ITEM_MAS foreign key (item_master_id)
      references ITEM_MASTER (id)
go


alter table VENDOR_ITEMS
   add constraint FK_VENDOR_I_REF_CREDITOR foreign key (vendor_id)
      references CREDITOR (id)
go


alter table XACT
   add constraint FK_XACT_REF_32772_XACT_COD foreign key (tender_id)
      references XACT_CODES (id)
go


alter table XACT
   add constraint FK_XACT_REF_61745_XACT_TYP foreign key (xact_type_id)
      references XACT_TYPE (id)
go


alter table XACT
   add constraint FK_XACT_REF_XACTTYPE2 foreign key (xact_subtype_id)
      references XACT_TYPE (id)
go


alter table XACT_CODES
   add constraint FK_XACT_COD_REF_32775_XACT_COD foreign key (group_id)
      references XACT_CODE_GROUP (id)
go


alter table XACT_COMMENTS
   add constraint FK_XACT_COM_REF_23270_XACT foreign key (xact_id)
      references XACT (id)
go


alter table XACT_POSTING
   add constraint FK_XACT_POS_REF_13040_GL_ACCOU foreign key (gl_account_id)
      references GL_ACCOUNTS (id)
go


alter table XACT_POSTING
   add constraint FK_XACT_POS_REF_42936_XACT foreign key (xact_id)
      references XACT (id)
go


alter table XACT_POSTING
   add constraint FK_XACT_POS_REFERENCE_ACCT_PER foreign key (period_type_id)
      references ACCT_PERIOD (id)
go


alter table XACT_POSTING
   add constraint FK_XACT_POS_REF_GL_SUBSID foreign key (post_ref_code)
      references GL_SUBSIDIARY (id)
go


alter table XACT_TYPE
   add constraint FK_XACT_TYP_REF_1022_XACT_CAT foreign key (xact_category_id)
      references XACT_CATEGORY (id)
go


alter table XACT_TYPE
   add constraint FK_XACT_TYP_REF_63386_GL_ACCOU foreign key (to_acct_type_id)
      references GL_ACCOUNT_TYPES (id)
go


alter table XACT_TYPE
   add constraint FK_XACT_TYP_REF_63389_GL_ACCOU foreign key (from_acct_type_id)
      references GL_ACCOUNT_TYPES (id)
go


alter table XACT_TYPE
   add constraint FK_XACT_TYP_REF_TO_GL_ACCTCATG foreign key (to_acct_catg_id)
      references GL_ACCOUNT_CATEGORY (id)
go


alter table XACT_TYPE
   add constraint FK_XACT_TYP_REF_FROM_GL_ACCTCATG foreign key (from_acct_catg_id)
      references GL_ACCOUNT_CATEGORY (id)
go


alter table XACT_TYPE_ITEM
   add constraint FK_XACT_TYP_ITM_REF_XACT_TYP foreign key (xact_type_id)
      references XACT_TYPE (id)
go


alter table XACT_TYPE_ITEM_ACTIVITY
   add constraint FK_EXP_REF_XACT01 foreign key (xact_id)
      references XACT (id)
go


alter table XACT_TYPE_ITEM_ACTIVITY
   add constraint FK_XACT_ACT_REF_XACT_TYP_ITM foreign key (xact_type_item_id)
      references XACT_TYPE_ITEM (id)
go




Print 'Creating Server System Messages in the Master Database'
go
use master
go
EXEC sp_addmessage 50001, 18, '%s'
go
EXEC sp_addmessage 50002, 18, 'General Ledger Account  category was not found.    Category: %d, Account Type: %d'
go
EXEC sp_addmessage 50003, 18, 'General Ledger Account  type does not exist.     Account Type: %d'
go
EXEC sp_addmessage 50004, 18, 'General Ledger Account  Id cannot be blank and must greater than zero'
go
EXEC sp_addmessage 50005, 18, 'General Ledger Account  does not exist.     GL Account Id: %d'
go
EXEC sp_addmessage 50006, 18, 'When adding Creditor/Vendor, GL Account Type must be a Liability'
go
EXEC sp_addmessage 50007, 18, 'Business Id cannot be blank and must be greater that zero when adding a Creditor/Vendor'
go
EXEC sp_addmessage 50008, 18, 'Business is not found using the following business id: %d'
go
EXEC sp_addmessage 50009, 18, 'Creditor/Vendor Id cannot be blank and must be greater than zero'
go
EXEC sp_addmessage 50010, 18, 'Creditor Type Id does not exist for the Creditor/Vendor that is being added'
go
EXEC sp_addmessage 50011, 18, 'Creditor Type Id does not exist for the Creditor/Vendor that is being added'
go
EXEC sp_addmessage 50012, 18, 'There is a conflict in GL Account Id''s when processing %s.   Expected Account Id: %d, Actual Account Id: %d'
go
EXEC sp_addmessage 50013, 18, 'Either personal data or business data must be supplied when processing this type of transaction'
go
EXEC sp_addmessage 50014, 18, 'The Person Id supplied for this transaction does not exist in the system, %d'
go
EXEC sp_addmessage 50015, 18, 'The Business Id supplied for this transaction does not exist in the system, %d'
go
EXEC sp_addmessage 50016, 18, 'Creditor/Vendor was not found in the system, %d'
go
EXEC sp_addmessage 50017, 18, 'Problem obtaining the primary key for the item added to the item_master table.    Item Master update failed.    GL Account Id: %d, Vendor Id: %d, Item Description: %s, Vendor Item Number %s, Vendor Item Serial Number: %s'
go
EXEC sp_addmessage 50018, 18, 'Customer was not found in the system, %d'
go
EXEC sp_addmessage 50019, 18, 'Item Master Id was not found in the system, %d'
go
EXEC sp_addmessage 50020, 18, 'Transaction Type Id was not found in the system, %d'
go
EXEC sp_addmessage 50021, 18, 'Sales Order  was not found in the system, %d'
go
EXEC sp_addmessage 50022, 18, 'Sales Order  must be invoiced before cancelling'
go
EXEC sp_addmessage 50023, 18, 'Problem cancelling sales order.   Sales order is not linked to a Transaction'
go
EXEC sp_addmessage 50024, 18, 'Problem obtaining current status history id for item %d'
go
EXEC sp_addmessage 50025, 18, 'Problem retrieving current status of item %d using status id %d'
go
EXEC sp_addmessage 50026, 18, 'Problem obtaining destination item status using description, %s, for item %d'
go
EXEC sp_addmessage 50027, 18, 'Problem retrieving current status id of Sales Order %d'
go
EXEC sp_addmessage 50028, 18, 'Problem obtaining destination sales order status using description, %s'
go
EXEC sp_addmessage 50029, 18, 'Problem obtaining transaction type id using description, %s'
go
EXEC sp_addmessage 50030, 18, 'General Ledger account id for item %d could not be obtained for sales order %d'
go
EXEC sp_addmessage 50031, 18, 'Account preference does not exist'
go
EXEC sp_addmessage 50032, 18, 'General Ledger Account %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50033, 18, 'Creditor %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50034, 18, 'Customer %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50035, 18, 'Item Master %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50036, 18, 'Problem invoicing sales order.   Transaction could not be created for sales order %d'
go
EXEC sp_addmessage 50037, 18, 'Quantity on Hand could not be obtained for Item %d'
go
EXEC sp_addmessage 50038, 18, 'Transaction %d does ot exist'
go
EXEC sp_addmessage 50039, 18, 'Problem creating reversal Transaction.    Old transaction that is to be reversed %d'
go
use Model_RMT2_Accounting
go
