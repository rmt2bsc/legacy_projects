--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."USER_RESOURCE_SUBTYPE"."resource_type_id" 
	64, -1, 22, 6, 
	0x0000000000000000000000000000f03f0000000000000040000000000000004000000000000008400000000000000840,
	0x00000000cdcccc3dcdcccc3d398ee33dcdcc4c3f0000803f
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."USER_RESOURCE_SUBTYPE" 
    FROM 'C:.\\user_resource_subtype.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
