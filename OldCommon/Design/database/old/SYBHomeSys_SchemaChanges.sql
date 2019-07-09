begin

   declare vcPhone varchar(10);
   declare vcPhoneExt varchar(15);
   declare vcComment varchar(300);
   declare vcCommentAccum varchar(300);
   declare vnCommentID integer;
   declare vnPhoneType integer;
   declare vnAddressID integer;
   declare vnPersonalID integer;
 
   ALTER TABLE address 
       ADD home_phone1 varchar(10) ,
       ADD home_phone1_ext varchar(15),
       ADD home_phone2 varchar(10) ,
       ADD home_phone2_ext varchar(15),
       ADD work_phone1 varchar(10) ,
       ADD work_phone_ext1 varchar(15),
       ADD work_phone2 varchar(10) ,
       ADD work_phone_ext2 varchar(15),
       ADD business_phone varchar(10) ,
       ADD fax varchar(10) ,
       ADD pager varchar(10) ,
       ADD cell_phone varchar(10) ,
       ADD alternate_phone varchar(10);


      /**************************************
       ****  Get all personal addresses  ****
       **************************************/
   for curloop1 as curAddress cursor for
      select id
        from personal
    do
       set vnPersonaID = id;
       for curloop2 as curPhoneNumber cursor for
          select area_code || phone_prefix || phone_suffix as PhoneNo,
                 phone_ext,
                 comment_id,
                 address_id,
                 phonetype_id
            from phonenumber
            where personal_id = vnPersonalID
              and phonetype_id in (1,5,6,7)
       do 
          set vcPhone = PhoneNo;
          set vnCommentID = comment_id;
          set vcPhoneExt = phone_ext;     
          set vnAddressID = address_id;
          set vnPhoneType = phonetype_id

          select comment_text
             into vcComment
             from comments
             where comment_id = vnCommentID;

          set vcCommentAccum = vcCommentAccum || vcComment || '; ';
          case vnPhoneType
             when 1
                update address set
                     home_phone = vcPhone,
                     home_phone_ext = vcPhoneExt
                  where address_id = vnAddressID;
             when 5
                 update address set
                     fax = vcPhone
                  where address_id = vnAddressID;
             when 6
                 update address set
                     pager = vcPhone
                  where address_id = vnAddressID;
             when 7
                 update address set
                     cell_phone = vcPhone
                  where address_id = vnAddressID;
           end case;
          


end
