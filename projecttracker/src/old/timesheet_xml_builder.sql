select xmlelement(name timesheets, (

select
   timesheet.timesheet_id,
   timesheet.begin_period,
   timesheet.end_period,
   timesheet.invoice_ref_no,

   client.name,
   client.bill_rate,
   client.ot_bill_rate,
   client.account_no,

   employee.firstname,
   employee.lastname,
   employee.ssn,
   employee.email,
   employee.login_name,
   employee.bill_rate,
   employee.ot_bill_rate,
   employee.start_date,
   employee.termination_date,
   employee_title.description,
   employee_type.description,

   project.description,
   project.effective_date,
   project.end_date ,

   task.description,
   task.billable,

   event.event_date,
   event.hours

from 
    proj_timesheet as timesheet inner join proj_client as client on timesheet.client_id = client.client_id
                                inner join proj_employee as employee on timesheet.emp_id = employee.emp_id
                                inner join proj_employee_title as employee_title on employee.emp_title_id = employee_title.emp_title_id
                                inner join proj_employee_type as employee_type on employee.emp_type_id = employee_type.emp_type_id
                                inner join proj_project_task as proj_task on timesheet.timesheet_id = proj_task.timesheet_id
                                inner join proj_project as project on proj_task.proj_id = project.proj_id
                                inner join proj_task as task on proj_task.task_id = task.task_id
                                inner join proj_event as event on event.project_task_id = proj_task.project_task_id

order by 
    timesheet.timesheet_id,
    client.name,
     employee.lastname,
    employee.firstname,
     project.description,
    task.description,
    event.event_date

for xml auto)
)   
