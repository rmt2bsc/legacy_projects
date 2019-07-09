--update PROJ_TIMESHEET_HIST set end_date = null where timesheet_hist_id in (26, 28)

--select * from proj_timesheet_hist

delete from PROJ_TIMESHEET_HIST where timesheet_status_id = 4;
update proj_timesheet_hist set timesheet_status_id = 4 where timesheet_status_id = 6;


commit;
