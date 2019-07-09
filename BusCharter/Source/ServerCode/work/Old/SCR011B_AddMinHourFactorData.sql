/* SCR 011 */

/******************************
 *   Clean Section.
 *   Uncomment when needed
 ******************************/

--delete from general_codes
--   where group_id = 26;
   
--delete from general_codes_group
--   where group_id = 26;
   
--commit;

/***************************************************/

insert into general_codes_group
  (group_id, description)
values (26, 'Minimum Hourly Factor');

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '1', '1 Hour Minimum', 'N', 1);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '2', '2 Hours Minimum', 'N', 2);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '3', '3 Hours Minimum', 'N', 3);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '4', '4 Hour Minimum', 'N', 4);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '5', '5 Hours Minimum', 'N', 5);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '6', '6 Hours Minimum', 'N', 6);
 
insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '7', '7 Hours Minimum', 'N', 7);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '8', '8 Hours Minimum', 'N', 8);
 
insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '9', '9 Hours Minimum', 'N', 9);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '10', '10 Hours Minimum', 'N', 10);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '11', '11 Hour Minimum', 'N', 11);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '12', '12 Hours Minimum', 'N', 12);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '13', '13 Hours Minimum', 'N', 13); 
 
insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '14', '14 Hours Minimum', 'N', 14);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '15', '15 Hours Minimum', 'N', 15);
 
insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '16', '16 Hours Minimum', 'N', 16);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '17', '17 Hours Minimum', 'N', 17);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '18', '18 Hour Minimum', 'N', 18);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '19', '19 Hours Minimum', 'N', 19);

insert into general_codes
  (group_id, shortdesc, longdesc, permanent, int_value)
 values (26, '20', '20 Hours Minimum', 'N', 20);  
 
commit; 