% This script should not have to be executed since I used it to 
% gather and convert data that wxist in the address2.txt and comments2.txt files

drop table address2;
CREATE TABLE address2 
  (id integer NOT NULL DEFAULT autoincrement, 
   addresstype_id integer NOT NULL DEFAULT NULL, 
   perbus_id integer DEFAULT NULL, 
   address1 char(25) DEFAULT NULL, 
   address2 char(25) DEFAULT NULL, 
   address3 char(25) DEFAULT NULL, 
   address4 char(25) DEFAULT NULL, 
   city_id integer DEFAULT NULL, 
   state_id integer DEFAULT NULL, 
   country_id integer DEFAULT NULL, 
   zip_code char(5) DEFAULT NULL, 
   zip_ext char(4) DEFAULT NULL, 
   phone_home char(13) DEFAULT NULL,
   phone_work char(13) DEFAULT NULL,
   phone_workext char(8) DEFAULT NULL,
   phone_business char(13) DEFAULT NULL,
   phone_celluar char(13) DEFAULT NULL,
   phone_fax char(13) DEFAULT NULL,
   phone_pager char(13) DEFAULT NULL,
   phone_other char(13) DEFAULT NULL,
   internet_address char(80) DEFAULT NULL, 
   email char(50) DEFAULT NULL, 
   date_created date DEFAULT current date, 
   date_updated date DEFAULT current date, 
   user_id char(8) DEFAULT 'DBA', 
   comment_id integer DEFAULT NULL , 
   PRIMARY KEY (id)) ;

drop table comments2;        
CREATE TABLE comments2 
 (comment_id integer NOT NULL DEFAULT autoincrement, 
   comment_text char(1000) DEFAULT NULL, 
   date_created timestamp DEFAULT current timestamp, 
   date_updated timestamp DEFAULT current timestamp, 
   user_id char(8) DEFAULT 'DBA' , 
   PRIMARY KEY (comment_id)) ;


begin
     
   declare  vAddresstype_id  int ;
   declare vPerbus_id int;
   declare vAddress1 char(25);
   declare vAddress2 char(25);
   declare vAddress3 char(25);
   declare vAddress4 char(25);
   declare vCity_id int;
   declare vState_id int;
   declare vCountry_id int;
   declare vZip_code char(5);
   declare vZip_ext char(4);
   declare vPhone_home char(13);
   declare vPhone_work char(13);
   declare vPhone_workext char(8);
   declare vPhone_business char(13);
   declare vPhone_cellular char(13);
   declare vPhone_fax char(13);
   declare vPhone_pager char(13);
   declare vPhone_other char(13);
   declare vInternet_address char(80);
   declare vEmail char(50);
   declare vDate_created date;
   declare vDate_updated date;
   declare vUser_id char(8);
   declare vComment_id int; 
   declare vComment_text char(1000); 
   declare vComment_temp char(300);

   declare vPersonalId int;
   declare vBusinessId int;
   declare vAddressId int;
   declare vPhoneId int;

   declare curComment cursor for
      select comment_text   from comments
        where comment_id = vComment_id;

   declare curMaxComment cursor for
        select max(comment_id) 
               from comments2;

   declare count int;


set count = 0;

print 'Converting Personal Contacts';
 %*************************************************************************
 %*   Get all personal id's
 %*************************************************************************

  for curPersonal as personal_ids cursor for
    select id from personal
    
    do
       set  vPersonalId = id;


     %****************************
      % Get address 
      %****************************

       for curAdderss as address_data cursor for
         select address_id,   
	 	addresstype_id,   
	 	personal_id,   
	 	address1,   
	 	address2,   
	 	address3,   
	 	address4,   
	 	city_id,   
	 	state_id,   
	 	country_id,   
	 	zip_code,   
	 	zip_ext,   
	 	internet_address,   
	 	email,   
	 	date_created,   
	 	date_updated,
                                           user_id
           from address
           where personal_id = vPersonalId
         do  
         
         set vAddressId = address_id;
         set vComment_text = null;

         set vAddresstype_id = addresstype_id;
	 set vPerbus_id = personal_id;
	 set vAddress1 = address1;
	 set vAddress2 = address2;
	 set vAddress3 = address3;
	 set vAddress4 = address4;
	 set vCity_id = city_id;
	 set vState_id = state_id;
	 set vCountry_id = country_id;
	 set vZip_code = zip_code;
	 set vZip_ext = zip_ext;
	 set vPhone_home = null;
	 set vPhone_work = null;
	 set vPhone_workext = null;
	 set vPhone_business = null; 
	 set vPhone_cellular = null;
	 set vPhone_fax = null;
	 set vPhone_pager = null;
	 set vPhone_other = null;
	 set vInternet_address = internet_address;
	 set vEmail = email;
	 set vDate_created = date_created;
	 set vDate_updated = date_updated;
	 set vUser_id = user_id;
         set vComment_id = null;
         
        %*******************************************
        %  Get Phone numbers for current address
        %*******************************************

         for curPhonenumber as phonenumber_data cursor for
           select phonetype_id,   
	          area_code,
	   	  phone_prefix,
	   	  phone_suffix,
	   	  phone_ext,
	                      comment_id  
               from phonenumber
            where address_id = vAddressId
            
         do

          if phonetype_id = 1 then
             set vPhone_home = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 2 then
             set vPhone_work = area_code || phone_prefix || phone_suffix;
             set vPhone_workext = phone_ext;
           end if; 
           if phonetype_id = 3 or phonetype_id = 4 then
             set vPhone_Business = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 5 then
             set vPhone_fax = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 6 then
             set vPhone_pager = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 7 then
             set vPhone_cellular = area_code || phone_prefix || phone_suffix;
           end if;
           
           set vComment_id = comment_id;

             %****  Get comment for the current phonenumber  ****
           open curComment;
           fetch curComment into vComment_temp;
           close curComment;
           
          if vComment_temp is not null then
             if vComment_text is null then
               set vComment_text = vComment_temp;  
             else
               set vComment_text = vComment_text || '; ' || vComment_temp;
             end if;
           end if;

         end for;

         
         if vComment_text is not null then
          
           insert into comments2
             (comment_text,
              date_created,
              date_updated,
              user_id)
            values
              (vComment_text,
               vDate_Created,
               vDate_Updated,
               vUser_id);
       
         open curMaxComment;
         fetch curMaxComment into vComment_id;
        close curMaxComment;       
               
         end if;

         insert into address2
          (Addresstype_id,
      	   Perbus_id,
	   Address1,
	   Address2,
	   Address3,
	   Address4,
	   City_id,
	   State_id,
	   Country_id,
	   Zip_code,
	   Zip_ext,
	   Phone_home,
	   Phone_work,
	   Phone_workext,
	   Phone_business,
	   Phone_celluar,
	   Phone_fax,
	   Phone_pager,
	   Phone_other,
	   Internet_address,
	   Email,
	   Date_created,
	   Date_updated,
	   User_id,
           Comment_id)
         values (vAddresstype_id,
      	         vPerbus_id,
	         vAddress1,
	         vAddress2,
	         vAddress3,
	         vAddress4,
	         vCity_id,
	         vState_id,
	         vCountry_id,
	         vZip_code,
	         vZip_ext,
	         vPhone_home,
	         vPhone_work,
	         vPhone_workext,
	         vPhone_business,
	         vPhone_cellular,
	         vPhone_fax,
	         vPhone_pager,
	         vPhone_other,
	         vInternet_address,
	         vEmail,
	         vDate_created,
	         vDate_updated,
	         vUser_id,
                 vComment_id);
                
            
          end for;    
     end for;

     for curMissingAdderss as missing_address_data cursor for
         select personal.id, personal.shortname 
             from personal 
             where personal.id  not in (select perbus_id from address2 where perbus_id is not null)
         do  
                                  
            set  vPersonalId = id;
            insert into address2
               (perbus_id,
                addresstype_id)
           values (vPersonalId,
                          163 );
      end for;



 %*************************************************************************
 %*    Begin converting business contacts
 %*************************************************************************
print 'Converting Business Contacts';  

 %*************************************************************************
 %*   Get all business id's
 %*************************************************************************
  for curBusiness as business_ids cursor for
    select id from business
    
    do
       set  vBusinessId = id;

     %****************************
      % Get address 
      %****************************

     for curAdderss2 as address_data2 cursor for
         select address_id,   
	 	addresstype_id,   
	 	business_id,   
	 	address1,   
	 	address2,   
	 	address3,   
	 	address4,   
	 	city_id,   
	 	state_id,   
	 	country_id,   
	 	zip_code,   
	 	zip_ext,   
	 	internet_address,   
	 	email,   
	 	date_created,   
	 	date_updated,
                                           user_id
           from address
           where business_id = vBusinessId 
         do  
         
         set vAddressId = address_id;
         set vComment_text = null;

         set vAddresstype_id = addresstype_id;
	 set vPerbus_id = business_id;
	 set vAddress1 = address1;
	 set vAddress2 = address2;
	 set vAddress3 = address3;
	 set vAddress4 = address4;
	 set vCity_id = city_id;
	 set vState_id = state_id;
	 set vCountry_id = country_id;
	 set vZip_code = zip_code;
	 set vZip_ext = zip_ext;
	 set vPhone_home = null;
	 set vPhone_work = null;
	 set vPhone_workext = null;
	 set vPhone_business = null; 
	 set vPhone_cellular = null;
	 set vPhone_fax = null;
	 set vPhone_pager = null;
	 set vPhone_other = null;
	 set vInternet_address = internet_address;
	 set vEmail = email;
	 set vDate_created = date_created;
	 set vDate_updated = date_updated;
	 set vUser_id = user_id;
         set vComment_id = null;
         
        %*******************************************
        %  Get Phone numbers for current address
        %*******************************************

         for curPhonenumber2 as phonenumber_data2 cursor for
           select phonetype_id,   
	          area_code,
	   	  phone_prefix,
	   	  phone_suffix,
	   	  phone_ext,
	                      comment_id  
               from phonenumber
            where address_id = vAddressId
            
         do

          if phonetype_id = 1 then
             set vPhone_home = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 2 then
             set vPhone_work = area_code || phone_prefix || phone_suffix;
             set vPhone_workext = phone_ext;
           end if; 
           if phonetype_id = 3 or phonetype_id = 4 then
             set vPhone_Business = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 5 then
             set vPhone_fax = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 6 then
             set vPhone_pager = area_code || phone_prefix || phone_suffix;
           end if;
           if phonetype_id = 7 then
             set vPhone_cellular = area_code || phone_prefix || phone_suffix;
           end if;
           
           set vComment_id = comment_id;

             %****  Get comment for the current phonenumber  ****
           open curComment;
           fetch curComment into vComment_temp;
           close curComment;
           
          if vComment_temp is not null then
             if vComment_text is null then
               set vComment_text = vComment_temp;  
             else
               set vComment_text = vComment_text || '; ' || vComment_temp;
             end if;
           end if;

         end for;

         
         if vComment_text is not null then
          
           insert into comments2
             (comment_text,
              date_created,
              date_updated,
              user_id)
            values
              (vComment_text,
               vDate_Created,
               vDate_Updated,
               vUser_id);
       
         open curMaxComment;
         fetch curMaxComment into vComment_id;
        close curMaxComment;       
               
         end if;

         insert into address2
          (Addresstype_id,
      	   Perbus_id,
	   Address1,
	   Address2,
	   Address3,
	   Address4,
	   City_id,
	   State_id,
	   Country_id,
	   Zip_code,
	   Zip_ext,
	   Phone_home,
	   Phone_work,
	   Phone_workext,
	   Phone_business,
	   Phone_celluar,
	   Phone_fax,
	   Phone_pager,
	   Phone_other,
	   Internet_address,
	   Email,
	   Date_created,
	   Date_updated,
	   User_id,
           Comment_id)
         values (vAddresstype_id,
      	         vPerbus_id,
	         vAddress1,
	         vAddress2,
	         vAddress3,
	         vAddress4,
	         vCity_id,
	         vState_id,
	         vCountry_id,
	         vZip_code,
	         vZip_ext,
	         vPhone_home,
	         vPhone_work,
	         vPhone_workext,
	         vPhone_business,
	         vPhone_cellular,
	         vPhone_fax,
	         vPhone_pager,
	         vPhone_other,
	         vInternet_address,
	         vEmail,
	         vDate_created,
	         vDate_updated,
	         vUser_id,
                 vComment_id);
                
            
       end for;    
           
     end for;

  commit;

end

