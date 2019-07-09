
--revoke connect from contacts_user;

/*==============================================================*/
/* User: contacts_user                                          */
/*==============================================================*/
grant connect to contacts_user identified by hoover;

comment on user contacts_user is 
'This is the web user for the contacts system';

/*==============================================================*/
/* Group: dba                                                   */
/*==============================================================*/

comment on user dba is 
'Change the DBA user to DBA group.';

grant group to dba;

grant membership in group dba to contacts_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on ADDRESS to contacts_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on BUSINESS to contacts_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on CITY_TYPE to contacts_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on COUNTRY to contacts_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on GENERAL_CODES to contacts_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on GENERAL_CODES_GROUP to contacts_user;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on PERSON to contacts_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on STATE to contacts_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on TIME_ZONE to contacts_user;

grant UPDATE,SELECT,REFERENCES,INSERT,DELETE on ZIPCODE to contacts_user;

grant SELECT on vw_business_address to contacts_user;

grant SELECT on vw_person_address to contacts_user;

grant SELECT on vw_state_country to contacts_user;

grant SELECT on vw_zipcode to contacts_user;

grant EXECUTE on ufn_convert_date_to_num to contacts_user;

grant EXECUTE on ufn_get_app_user_id to contacts_user;

grant EXECUTE on ufn_get_datepart to contacts_user;

grant EXECUTE on usp_add_user_xref to contacts_user;

grant EXECUTE on usp_batch_msg to contacts_user;

grant EXECUTE on usp_del_user_xref to contacts_user;

grant EXECUTE on usp_get_system_pref to contacts_user;

grant EXECUTE on usp_log_message to contacts_user;
