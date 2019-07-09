/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 7:46:15 AM                        */
/*==============================================================*/


/*==============================================================*/
/* View: VW_USER                                                */
/*==============================================================*/
create view VW_USER as
Select user_login.description as login_description, 
	user_login.employee_id, 
	user_login.id, 
	user_login.login, 
	user_login.password, 
	user_login.total_logons,
	user_login.active,
	user_login.date_created,
	user_login.date_updated,
	user_login.user_id,
	proj_employee.person_id,
	proj_employee.start_date, 
	proj_employee.termination_date, 
	proj_employee.title_id, 
	proj_employee.type_id,
    proj_employee.bill_rate,
    proj_employee.ot_bill_rate,
	person.firstname, 
	person.midname, 
	person.lastname,
	person.shortname,
	person.generation,
	person.maidenname,
	person.title,
    dbo.ufn_general_code_description(person.title) per_title_name,
	person.email,
	person.gender_id, 
    dbo.ufn_general_code_description(person.gender_id) gender_name,
	person.marital_status, 
    dbo.ufn_general_code_description(person.marital_status) marital_status_name,
	person.race_id, 
    dbo.ufn_general_code_description(person.race_id) race_name,
	person.ssn,
	person.birth_date,
	proj_employee_title.description as employee_title,
	proj_employee_type.description as employee_type
From user_login, 
     proj_employee,
     person, 
     proj_employee_title, 
     proj_employee_type
Where user_login.employee_id = proj_employee.id 
  and proj_employee.person_id = person.id
  and proj_employee.title_id = proj_employee_title.id
  and proj_employee.type_id = proj_employee_type.id
go


