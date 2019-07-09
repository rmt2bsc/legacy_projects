$PBExportHeader$n_rmt_datacache.sru
$PBExportComments$Data Cache Object
forward
global type n_rmt_datacache from nonvisualobject
end type
end forward

global type n_rmt_datacache from nonvisualobject
end type
global n_rmt_datacache n_rmt_datacache

type variables
public:
   str_datacache     istr_cache[]
   
end variables

forward prototypes
public function integer of_getcount ()
public function integer of_register (string as_name)
public function integer of_refresh (string as_name)
public function integer of_isregistered (string as_name)
public function n_rmt_ds of_getcache (string as_name)
public function integer of_register (string as_name, string as_sql, string as_dataobject)
end prototypes

public function integer of_getcount ();//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_getcount
//
//	Description:  Returns the total number of registerd cache objects.
//	             
// Arguments: none
//
// Returns: integer ( data cache  count )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

return  upperbound(istr_cache)
end function

public function integer of_register (string as_name);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_register
//
//	Description:  create and add a datastore to the array of data caches.
//	             
// Arguments: string  as_name
//
// Returns: integer (-1 - if cache name is invalid
//                   > 0 - the total number of caches that exist in the array
//                         after the newly created datastore was added. )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int    li_total


if isnull(as_name) or len(as_name) <= 0 then
	return -1   // Cache name is invalid
end if

li_total = upperbound(istr_cache)
if li_total > 0 then
	istr_cache[li_total + 1].ds = create n_rmt_ds
	istr_cache[li_total + 1].ds.object.name = as_name
else
	istr_cache[1].ds = create n_rmt_ds
	istr_cache[1].ds.object.name = as_name
end if
	
return upperbound(istr_cache)
end function

public function integer of_refresh (string as_name);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_refresh
//
//	Description:  Refresh a single cache's data which originated from the 
//               database.
//	             
// Arguments: string  as_name
//
// Returns: integer (0 - not found 
//                   > 0 - the index of the data cache that was refreshed  )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_ndx


li_ndx = this.of_isRegistered(as_name)
if li_ndx > 0 then
	//  update the database with this cache
	
end if


return li_ndx
end function

public function integer of_isregistered (string as_name);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_isregisterd
//
//	Description:  Determine if the datastore with an id 
//               equal to the value of as_name exist is
//               istr_cache[].   If found return the array
//               position.   
//	             
// Arguments: string  as_name
//
// Returns: integer (0 - not found 
//                   -1 - as_name is equal to spaces or is null. )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int    li_ndx
int    li_total


if isnull(as_name) or len(as_name) <= 0 then
	return -1    // cache name is invalid
end if

li_total = upperbound(istr_cache)
for li_ndx = 1 to li_total
	if lower(as_name) = istr_cache[li_ndx].ds.object.name then
	   return li_ndx     // cache was found...return position of array where cach was found
	end if
next

return 0    // cache was not found
	   
    	
end function

public function n_rmt_ds of_getcache (string as_name);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_getcaches
//
//	Description:   Retrieves a single data cached.
//	             
// Arguments: string as_name
//
// Returns: n_rmt_ds ( data cache )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_ndx
n_rmt_ds    lds_null

li_ndx = this.of_isRegistered(as_name)
if li_ndx > 0 then
	return istr_cache[li_ndx].ds
else
	return lds_null
end if
 

end function

public function integer of_register (string as_name, string as_sql, string as_dataobject);//////////////////////////////////////////////////////////////////////////////
//
//	Function Name:  of_register
//
//	Description:  Associates a sql statement and dataobject with the newly registerd data 
//               cache.
//	             
// Arguments: string  as_name
//            string  as_sql
//            string  as_dataobject
//
// Returns: integer (-1 - if cache name is invalid
//                   > 0 - the total number of caches that exist in the array
//                         after the newly created datastore was added. )
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////


int   li_ndx


if isnull(as_sql) or len(as_sql) <= 0 then
	return -2         //  SQL Statemnet is invalid
end if

li_ndx = this.of_register(as_name)
if li_ndx > 0 then
	this.istr_cache[li_ndx].sql = as_sql
	this.istr_cache[li_ndx].dataobject = as_dataobject
end if
	
return li_ndx
end function

on n_rmt_datacache.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_datacache.destroy
TriggerEvent( this, "destructor" )
end on

