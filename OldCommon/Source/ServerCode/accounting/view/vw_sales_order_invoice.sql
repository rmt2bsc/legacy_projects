/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_sales_order_invoice                                 */
/*==============================================================*/
create view vw_sales_order_invoice as
select sales_order.id sales_order_id, 
       sales_order.customer_id customer_id, 
       sales_order.invoiced invoiced,
       dbo.ufn_get_current_so_status(sales_order.id) order_status_id,
       dbo.ufn_get_current_so_status_description(sales_order.id) order_status_descr,
       sales_order.date_created sales_order_date,
       sales_invoice.xact_id xact_id,
       sales_invoice.invoice_no invoice_no,
       sales_invoice.date_created invoice_date,
       sales_order.order_total order_total
from sales_order, sales_invoice
where sales_order.id *= sales_invoice.sales_order_id
go


