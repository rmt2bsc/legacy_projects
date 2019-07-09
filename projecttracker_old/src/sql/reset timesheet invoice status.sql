-- Be sure to cancel relates sales order invoices on the Sales systems after applying 
-- this script and before re-invoicing the following timesheets.

-- Remove invoice status history
delete from proj_timesheet_hist where timesheet_id = 312 and timesheet_status_id = 6;
-- Change status to Draft
update proj_timesheet_hist set end_date = null where timesheet_id = 312 and timesheet_status_id = 1;
-- Remove the invoice reference number from the time sheet.
update proj_timesheet set invoice_ref_no = null where timesheet_id = 312;

-- Verify Change.
select * from proj_timesheet where timesheet_id = 312