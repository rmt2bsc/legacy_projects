/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 7:46:15 AM                        */
/*==============================================================*/


/*==============================================================*/
/* View: VW_PROJECT_EMPLOYEE_EXT                                */
/*==============================================================*/
create view VW_PROJECT_EMPLOYEE_EXT as
select
   USER_LOGIN.employee_id,
   PROJ_EMPLOYEE.person_id,
   PROJ_EMPLOYEE.title_id,
   PROJ_EMPLOYEE.TYPE_ID,
   PROJ_EMPLOYEE.START_DATE,
   PROJ_EMPLOYEE.TERMINATION_DATE,
   PROJ_EMPLOYEE_TYPE.description employee_type,
   PROJ_EMPLOYEE_TITLE.description employee_title,
   USER_LOGIN.login,
   USER_LOGIN.description login_description,
   USER_LOGIN.total_logons,
   USER_LOGIN.active,
   vw_person_address.per_title per_title_id,
   dbo.ufn_general_code_description(vw_person_address.per_title) per_title,
   vw_person_address.per_ssn,
   vw_person_address.per_shortname,
   vw_person_address.per_firstname,
   vw_person_address.per_lastname,
   vw_person_address.per_midname,
   vw_person_address.per_generation,
   vw_person_address.per_birth_date,
   vw_person_address.per_gender_id,
   dbo.ufn_general_code_description(vw_person_address.per_gender_id) per_gender,
   vw_person_address.per_maidenname,
   vw_person_address.per_marital_status per_marital_status_id,
   dbo.ufn_general_code_description(vw_person_address.per_marital_status) per_marital_status,
   vw_person_address.per_race_id,
   dbo.ufn_general_code_description(vw_person_address.per_race_id) per_race,
   vw_person_address.addr_id,
   vw_person_address.addr1,
   vw_person_address.addr2,
   vw_person_address.addr3,
   vw_person_address.addr4,
   vw_person_address.zip_city,
   vw_person_address.zip_state,
   vw_person_address.addr_zip,
   vw_person_address.addr_zipext,
   vw_person_address.addr_phone_work,
   vw_person_address.addr_phone_ext,
   vw_person_address.addr_phone_cell,
   vw_person_address.addr_phone_fax,
   vw_person_address.addr_phone_home,
   vw_person_address.addr_phone_main,
   vw_person_address.addr_phone_pager,
   vw_person_address.per_email,
   PROJ_PROJECT.id project_id,
   PROJ_PROJECT.proj_client_id,
   PROJ_PROJECT.description project_name,
   PROJ_PROJECT.effective_date,
   PROJ_PROJECT.end_date
from
   PROJ_PROJECT,
   PROJ_EMPLOYEE,
   PROJ_EMPLOYEE_TITLE,
   PROJ_EMPLOYEE_TYPE,
   USER_LOGIN,
   vw_person_address
where
   (PROJ_EMPLOYEE_TITLE.ID = PROJ_EMPLOYEE.title_id)
   and ( PROJ_EMPLOYEE_TYPE.ID = PROJ_EMPLOYEE.TYPE_ID)
   and ( PROJ_EMPLOYEE.person_id = vw_person_address.person_id)
   and ( PROJ_EMPLOYEE.ID = USER_LOGIN.employee_id)
go


