--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."XACT_TYPE"."xact_category_id" 
	64, -1, 22, 20, 
	0x0000000000000000000000000000f03f000000000000004000000000000008400000000000000840000000000000104000000000000018400000000000001c40000000000000204000000000000022400000000000002440000000000000264000000000000028400000000000002a400000000000002c400000000000002e400000000000003040000000000000314000000000000032400000000000003340,
	0x000000008c2e3a3d8c2eba3d8c2eba3d8c2eba3d8c2e3a3d8c2e3a3d8c2e3a3de9a20b3e8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d8c2e3a3d
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."XACT_TYPE" 
    FROM 'C:\\tmp\\\\xact_type.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
