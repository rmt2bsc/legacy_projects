/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     1/9/2007 7:41:54 AM                          */
/*==============================================================*/


/*==============================================================*/
/* View: VW_CLIENT_TIMESHEET_STATUS_SUMMARY                     */
/*==============================================================*/
create view VW_CLIENT_TIMESHEET_STATUS_SUMMARY as
select
     vce.client_id,
     vce.perbus_id,
     vce.name,
     vce.account_no,
     pts.id ts_status_id,
     pts.name ts_status_name,
     pts.description ts_status_descr,
     count(*) timesheet_count
from
     VW_CLIENT_EXT vce,
     PROJ_TIMESHEET pt,
     PROJ_TIMESHEET_HIST pth,
     PROJ_TIMESHEET_STATUS pts
where
      vce.client_id = pt.proj_client_id
  and pt.id = pth.proj_timesheet_id
  and pth.proj_timesheet_status_id = pts.id
  and pth.end_date is null
group by
     vce.client_id,
     vce.perbus_id,
     vce.name,
     vce.account_no,
     pts.id,
     pts.name,
     pts.description
go


