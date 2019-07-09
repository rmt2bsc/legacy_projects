--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."USER_RESOURCE"."resource_type_id" 
	64, -1, 22, 3, 
	0x000000000000004000000000000008400000000000000840,
	0x000000000000803f0000803f
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."USER_RESOURCE"."resource_subtype_id" 
	64, -1, 22, 2, 
	0x00000000000014400000000000001840,
	0x000000000000803f
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."USER_RESOURCE"."secured" 
	65, -1, 5, 2, 
	0x000000000000f03f000000000000f03f,
	0x000000002549923d
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."USER_RESOURCE" 
    FROM 'C:.\\user_resource.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
