select xmlelement(name timesheets, (

select
   1 as tag,
   null as parent,
   timesheet.timesheet_id as [timesheet!1!timesheetId!element],
   timesheet.begin_period as [timesheet!1!beginPeriod!element],
   timesheet.end_period as [timesheet!1!endPeriod!element],
   timesheet.invoice_ref_no as [timesheet!1!refNo!element],
   timesheet_status.name as [timesheet!1!status!element],

   client.name as [timesheet!1!clientName!element],
   client.bill_rate as [timesheet!1!clientBillRate!element],
   client.ot_bill_rate as [timesheet!1!clientOtBillRate!element],
   client.account_no as [timesheet!1!clientAccountNo!element],

   employee.firstname as [timesheet!1!employeeFirstName!element],
   employee.lastname as [timesheet!1!employeeLastName!element],
   employee.ssn as [timesheet!1!ssn!element],
   employee.email as [timesheet!1!employeeEmail!element],
   employee.login_name as [timesheet!1!employeeLoginName!element],
   employee.bill_rate as [timesheet!1!employeeBillRate!element],
   employee.ot_bill_rate as [timesheet!1!employeeOtBillRate!element],
   employee.start_date as [timesheet!1!employeeStartDate!element],
   employee.termination_date as [timesheet!1!employeeTerminationDate!element],
   employee_title.description as [timesheet!1!employeeTitle!element],
   employee_type.description as [timesheet!1!employeeType!element],

   null as [project!2!projId!element],
   null as [project!2!name!element],
   null as [project!2!effectiveDate!element],
   null as [project!2!endDate!element],

   null as [task!3!taskId!element],
   null as [task!3!name!element],
   null as [task!3!billable!element],

   null as [event!4!date!element],
   null as [event!4!hours!element]

from 
    proj_timesheet as timesheet inner join proj_client as client on timesheet.client_id = client.client_id
                                inner join proj_timesheet_hist as timesheet_hist on timesheet_hist.timesheet_id = timesheet.timesheet_id
                                inner join proj_timesheet_status as timesheet_status on timesheet_status.timesheet_status_id = timesheet_hist.timesheet_status_id
                                inner join proj_employee as employee on timesheet.emp_id = employee.emp_id
                                inner join proj_employee_title as employee_title on employee.emp_title_id = employee_title.emp_title_id
                                inner join proj_employee_type as employee_type on employee.emp_type_id = employee_type.emp_type_id
where timesheet_hist.end_date is null

union

select distinct
   2 as tag,
   1 as parent,
   timesheet.timesheet_id,
   null,
   null,
   null,
   null,

   null,
   null,
   null,
   null,

   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,

   project.proj_id,
   project.description,
   project.effective_date,
   project.end_date ,

   null,
   null,
   null,

   null,
   null
from 
    proj_timesheet as timesheet inner join proj_project_task as proj_task on timesheet.timesheet_id = proj_task.timesheet_id
                                inner join proj_project as project on proj_task.proj_id = project.proj_id


union all
select
   3 as tag,
   2 as parent,
   timesheet.timesheet_id,
   null,
   null,
   null,
   null,

   null,
   null,
   null,
   null,

   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,

   project.proj_id,
   null,
   null,
   null,

   task.task_id,
   task.description,
   task.billable,

   null,
   null

from 
    proj_timesheet as timesheet inner join proj_project_task as proj_task on timesheet.timesheet_id = proj_task.timesheet_id
                                inner join proj_project as project on proj_task.proj_id = project.proj_id
                                inner join proj_task as task on proj_task.task_id = task.task_id


union
select
   4 as tag,
   3 as parent,
   timesheet.timesheet_id,
   null,
   null,
   null,
   null,

   null,
   null,
   null,
   null,

   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,
   null,

   project.proj_id,
   null,
   null,
   null,

   task.task_id,
   null,
   null,

   event.event_date,
   isnull(event.hours, 0)

from 
    proj_timesheet as timesheet inner join proj_project_task as proj_task on timesheet.timesheet_id = proj_task.timesheet_id
                                inner join proj_project as project on proj_task.proj_id = project.proj_id
                                inner join proj_task as task on proj_task.task_id = task.task_id
                                inner join proj_event as event on event.project_task_id = proj_task.project_task_id

order by 
    3,
    23,
    27,
    30

for xml explicit)
)   
