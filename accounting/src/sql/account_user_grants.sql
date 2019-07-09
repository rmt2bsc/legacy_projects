
revoke connect from accounting_user;

/*==============================================================*/
/* User: accounting_user                                        */
/*==============================================================*/


grant group to dba;
create user accounting_user identified by hoover;
comment on user accounting_user is 'This is the accounting application user';
grant membership in group dba to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on ACCT_PERIOD to accounting_user;

grant DELETE,INSERT,REFERENCES,UPDATE,SELECT on ACCT_PREF to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on CREDITOR to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on CREDITOR_ACTIVITY to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on CREDITOR_TYPE to accounting_user;

grant DELETE,INSERT,REFERENCES,UPDATE,SELECT on CUSTOMER to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on CUSTOMER_ACTIVITY to accounting_user;

grant DELETE,INSERT,UPDATE,REFERENCES,SELECT on GL_ACCOUNTS to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on GL_ACCOUNT_CATEGORY to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on GL_ACCOUNT_TYPES to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on GL_BALANCE_TYPE to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on GL_SUBSIDIARY to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on ITEM_MASTER to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on ITEM_MASTER_STATUS to accounting_user;

grant DELETE,INSERT,SELECT,REFERENCES,UPDATE on ITEM_MASTER_STATUS_HIST to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on ITEM_MASTER_TYPE to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on PURCHASE_ORDER to accounting_user;

grant DELETE,INSERT,REFERENCES,UPDATE,SELECT on PURCHASE_ORDER_ITEMS to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PURCHASE_ORDER_ITEM_RETURN to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PURCHASE_ORDER_RETURN to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PURCHASE_ORDER_STATUS to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on PURCHASE_ORDER_STATUS_HIST to accounting_user;

grant DELETE,INSERT,SELECT,UPDATE,REFERENCES on SALES_INVOICE to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on SALES_ORDER to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on SALES_ORDER_ITEMS to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on SALES_ORDER_STATUS to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on SALES_ORDER_STATUS_HIST to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on VENDOR_ITEMS to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on XACT to accounting_user;

grant DELETE,INSERT,REFERENCES,UPDATE,SELECT on XACT_CATEGORY to accounting_user;

grant DELETE,INSERT,REFERENCES,UPDATE,SELECT on XACT_CODES to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on XACT_CODE_GROUP to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on XACT_COMMENTS to accounting_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on XACT_POSTING to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on XACT_TYPE to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on XACT_TYPE_ITEM to accounting_user;

grant DELETE,INSERT,UPDATE,SELECT,REFERENCES on XACT_TYPE_ITEM_ACTIVITY to accounting_user;

grant SELECT on VW_CREDITOR_XACT_HIST to accounting_user;

grant SELECT on VW_MULTI_DISBURSE_TYPE_XACT to accounting_user;

grant SELECT on VW_XACT_ITEM_LIST to accounting_user;

grant SELECT on VW_XACT_LIST to accounting_user;

grant SELECT on vw_account to accounting_user;

grant SELECT on vw_acct_catg_name to accounting_user;

grant SELECT on vw_category to accounting_user;

grant SELECT on vw_customer_xact_hist to accounting_user;

grant SELECT on vw_item_associations to accounting_user;

grant SELECT on vw_item_master to accounting_user;

grant SELECT on vw_purchase_order_list to accounting_user;

grant SELECT on vw_sales_order_invoice to accounting_user;

grant SELECT on vw_salesorder_items_by_salesorder to accounting_user;

grant SELECT on vw_vendor_item_purchase_order_item to accounting_user;

grant SELECT on vw_vendor_items to accounting_user;

grant SELECT on vw_xact_credit_charge_list to accounting_user;

grant SELECT on vw_xact_gl_posting_list to accounting_user;

grant SELECT on vw_xact_type_item_activity to accounting_user;

grant EXECUTE on ufn_get_acct_item_mast_count to accounting_user;

grant EXECUTE on ufn_get_acct_subsidiary_count to accounting_user;

grant EXECUTE on ufn_get_acct_to_xact_count to accounting_user;

grant EXECUTE on ufn_get_acct_usage_count to accounting_user;

grant EXECUTE on ufn_get_creditor_usage_count to accounting_user;

grant EXECUTE on ufn_get_current_item_status to accounting_user;

grant EXECUTE on ufn_get_current_so_status to accounting_user;

grant EXECUTE on ufn_get_current_so_status_description to accounting_user;

grant EXECUTE on ufn_get_cust_usage_count to accounting_user;

grant EXECUTE on ufn_get_item_usage_count to accounting_user;

grant EXECUTE on usp_add_user_xref to accounting_user;

grant EXECUTE on usp_del_user_xref to accounting_user;


