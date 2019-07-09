--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."XACT_TYPE_ITEM"."xact_type_id" 
	64, -1, 22, 5, 
	0x0000000000804d400000000000004e400000000000004e4000000000008066400000000000806640,
	0x0000000031b9023f31b9023f9e8dfa3e9e8dfa3e
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."XACT_TYPE_ITEM" 
    FROM 'C:\\tmp\\\\xact_type_item.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
