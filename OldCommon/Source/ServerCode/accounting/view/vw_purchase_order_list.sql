/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_purchase_order_list                                 */
/*==============================================================*/
create view vw_purchase_order_list as
select 
     po.id id,
     po.vendor_id vendor_id,
     posh.purchase_order_status_id status_id,
     po.ref_no ref_no,
     (select sum(qty * unit_cost) from purchase_order_items where purchase_order_id = po.id) total,
     pos.description status_description,
     posh.id status_hist_id,
     posh.effective_date effective_date,
     posh.end_date end_date,
     posh.user_id user_id,
     c.account_number account_number,
     c.credit_limit credit_limit,
     c.creditor_type_id credit_type_id,
     ct.description creditor_type_id,
     b.longname longname,
     b.shortname shortname,
     b.tax_id tax_id
from
     purchase_order po,
     purchase_order_status pos,
     purchase_order_status_hist posh,
     creditor c,
     creditor_type ct,
     business b

where
      po.id = posh.purchase_order_id
  and pos.id = posh.purchase_order_status_id
  and posh.end_date is null
  and po.vendor_id = c.id
  and c.creditor_type_id = ct.id
  and c.business_id = b.id
go


