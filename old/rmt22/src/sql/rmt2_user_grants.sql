/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     6/14/2009 5:40:33 PM                         */
/*==============================================================*/
revoke connect from RMT2_USER;

/*==============================================================*/
/* Group: DBA                                                   */
/*==============================================================*/
grant connect to DBA identified by sql;
grant group to DBA;


/*==============================================================*/
/* User: RMT2_USER                                              */
/*==============================================================*/
grant connect to RMT2_USER identified by hoover;

comment on user RMT2_USER is 
'The application user for RMT2 Web application.';

grant membership in group DBA to RMT2_USER;


grant EXECUTE on usp_add_user_xref to RMT2_USER;

grant EXECUTE on ufn_get_datepart to RMT2_USER;

grant EXECUTE on ufn_convert_date_to_num to RMT2_USER;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on USER_CONNECT_XREF to RMT2_USER;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on QUOTE_PLAN to RMT2_USER;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on MEMBERS to RMT2_USER;

grant EXECUTE on usp_del_user_xref to RMT2_USER;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on QUOTE_STATUS to RMT2_USER;

grant DELETE,INSERT,REFERENCES,SELECT,UPDATE on QUOTE to RMT2_USER;

