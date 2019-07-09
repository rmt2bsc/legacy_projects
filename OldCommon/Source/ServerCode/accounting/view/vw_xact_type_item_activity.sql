/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_xact_type_item_activity                             */
/*==============================================================*/
create view vw_xact_type_item_activity as
select
      xtia.id id,
      xtia.xact_type_item_id xact_type_item_id,
      xtia.xact_id xact_id,
      xtia.amount amount,
      xtia.description description,
      xti.xact_type_id xact_type_id,
      xti.name xact_type_item_name,
      xt.xact_code xact_type_xact_code,
      xt.description xact_type_description,
      xt.xact_category_id xact_category_id,
      xc.description xact_category_description,
      xc.code xact_category_code
from
      xact_type_item_activity xtia,
      xact_type_item xti,
      xact_type xt,
      xact_category xc
where 
      xtia.xact_type_item_id = xti.id
  and xti.xact_type_id = xt.id
  and xt.xact_category_id = xc.id
go


