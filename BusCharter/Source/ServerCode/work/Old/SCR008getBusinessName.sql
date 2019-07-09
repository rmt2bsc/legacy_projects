%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    getBusinessName
%  arguments:   integer  aCompanyID
%  returns:     varchar(100)
%  description: Gets the name of the selected business based on ID.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
create function getBusinessName(aCompanyID integer) returns varchar(100)

begin
   declare name varchar(100);

   if aCompanyID is null or aCompanyID <= 0 then
     return null;
   end if;

   select longname
     into name
     from business
     where id = aCompanyID;
		
   return ifNull(name, 'Unknown', name);
   
end;
