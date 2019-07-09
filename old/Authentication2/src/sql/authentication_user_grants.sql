revoke connect from authentication_user;

/*==============================================================*/
/* User: authentication_user                                    */
/*==============================================================*/
grant connect to authentication_user identified by hoover;

comment on user authentication_user is 
'Application user for the authentication system.';

/*==============================================================*/
/* Group: dba                                                   */
/*==============================================================*/

comment on user dba is 
'Convert the ASA DBA user to a group so that authentication_user will not have to qualify all object references with schema owner name.';

grant group to dba;

grant membership in group dba to authentication_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on APPLICATION to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on APPLICATION_ACCESS to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on APP_ROLE to authentication_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on GROUP_ROLES to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on ROLES to authentication_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on USER_APP_ROLE to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on USER_GROUP to authentication_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on USER_LOGIN to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on USER_RESOURCE to authentication_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on USER_RESOURCE_ACCESS to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on USER_RESOURCE_SUBTYPE to authentication_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on USER_RESOURCE_TYPE to authentication_user;

grant SELECT on VW_APP_ROLES to authentication_user;

grant SELECT on VW_RESOURCE to authentication_user;

grant SELECT on VW_RESOURCE_TYPE to authentication_user;

grant SELECT on VW_USER_APP_ROLES to authentication_user;

grant SELECT on VW_USER_GROUP to authentication_user;

grant SELECT on VW_USER_RESOURCE_ACCESS to authentication_user;

grant EXECUTE on usp_add_user_xref to authentication_user;

grant EXECUTE on usp_del_user_xref to authentication_user;
