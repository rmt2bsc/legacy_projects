--revoke connect from project_user;

/*==============================================================*/
/* User: project_user                                           */
/*==============================================================*/
grant connect to project_user identified by hoover;

comment on user project_user is 
'The application user for the project tracker system';

/*==============================================================*/
/* Group: dba                                                   */
/*==============================================================*/
comment on user dba is 
'Converts the database user, dba, to a group.';

grant group to dba;

grant membership in group dba to project_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on PROJ_CLIENT to project_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PROJ_EMPLOYEE to project_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on PROJ_EMPLOYEE_TITLE to project_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on PROJ_EMPLOYEE_TYPE to project_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PROJ_EVENT to project_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on PROJ_PERIOD to project_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PROJ_PREF to project_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on PROJ_PROJECT to project_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PROJ_PROJECT_TASK to project_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PROJ_TASK to project_user;

grant UPDATE,SELECT,DELETE,INSERT,REFERENCES on PROJ_TIMESHEET to project_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on PROJ_TIMESHEET_HIST to project_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PROJ_TIMESHEET_STATUS to project_user;

grant SELECT on VW_CLIENT_TIMESHEET_SUMMARY to project_user;

grant SELECT on VW_EMPLOYEE_EXT to project_user;

grant SELECT on VW_PROJECT_CLIENT to project_user;

grant SELECT on VW_TIMESHEET_EVENT_LIST to project_user;

grant SELECT on VW_TIMESHEET_HOURS to project_user;

grant SELECT on VW_TIMESHEET_LIST to project_user;

grant SELECT on VW_TIMESHEET_PROJECT_TASK to project_user;

grant SELECT on VW_TIMESHEET_SUMMARY to project_user;

grant EXECUTE on usp_add_user_xref to project_user;

grant EXECUTE on usp_del_user_xref to project_user;
