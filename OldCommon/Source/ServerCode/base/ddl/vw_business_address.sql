/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* View: vw_business_address                                    */
/*==============================================================*/
create view vw_business_address as
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
		business.contact_ext as bus_contact_ext, 
		business.contact_firstname as bus_contact_firstname, 
		business.contact_lastname as bus_contact_lastname, 
		business.contact_phone as bus_contact_phone, 
		business.longname as bus_longname, 
		business.serv_type as bus_serv_type, 
		business.shortname as bus_shortname, 
		business.tax_id as bus_tax_id, 
		business.website as bus_website, 
		business.bus_type as bus_type, 
		business.id as business_id
From business, address, zipcode
Where business.id = address.business_id 
  and address.zip = zipcode.zip
go


