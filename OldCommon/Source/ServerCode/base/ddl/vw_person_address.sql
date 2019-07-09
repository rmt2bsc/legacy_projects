/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* View: vw_person_address                                      */
/*==============================================================*/
create view vw_person_address as
Select distinct address.id as addr_id, 
		address.phone_cell as addr_phone_cell, 
		address.phone_ext  as addr_phone_ext, 
		address.phone_fax as addr_phone_fax, 
		address.phone_home as addr_phone_home, 
		address.phone_main as addr_phone_main, 
		address.phone_pager as addr_phone_pager, 
		address.phone_work as addr_phone_work, 
		address.zip as addr_zip, 
		address.zipext as addr_zipext, 
		address.addr1 as addr1, 
		address.addr2 as addr2, 
		address.addr3 as addr3, 
		address.addr4 as addr4, 
		address.person_id as addr_person_id,
		address.business_id as addr_business_id,
		zipcode.city as zip_city,
		zipcode.state as zip_state,		
		person.birth_date as per_birth_date, 
		person.email as per_email, 
		person.firstname as per_firstname, 
		person.gender_id as per_gender_id, 
		person.generation as per_generation, 
		person.lastname as per_lastname, 
		person.maidenname as per_maidenname, 
		person.marital_status as per_marital_status, 
		person.midname as per_midname, 
		person.race_id as per_race_id, 
		person.shortname as per_shortname, 
		person.ssn as per_ssn, 
		person.title as per_title, 
		person.id as person_id
From person, 
     address, 
     zipcode
Where person.id = address.person_id 
  and address.zip = zipcode.zip
go


