/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:22:47 PM                          */
/*==============================================================*/


/*==============================================================*/
/* View: vw_item_master                                         */
/*==============================================================*/
create view vw_item_master as
select 
       im.id id,
       im.vendor_id vendor_id,
       im.item_type_id,
       im.description description,
       im.vendor_item_no,
       im.item_serial_no,
       im.qty_on_hand,
       im.unit_cost,
       im.markup,
       im.retail_price,
       im.override_retail,
       im.active,
       im.date_created item_create_date,
       im.date_updated item_update_date,
       im.user_id update_userid,
       imt.description item_type_description,
       (select longname from business  where id = (select business_id from creditor where id = im.vendor_id)) vendor_name,
       ims.description item_status,
       dbo.ufn_get_current_item_status(im.id) item_status_id
  from
       item_master im,
       item_master_type imt,
       item_master_status ims
 where
       im.item_type_id = imt.id
   and ims.id = dbo.ufn_get_current_item_status(im.id)
go


