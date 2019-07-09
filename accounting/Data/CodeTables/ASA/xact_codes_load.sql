--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."XACT_CODES"."group_id" 
	64, -1, 22, 3, 
	0x0000000000000000000000000000f03f0000000000000040,
	0x00000000e4380e3f398ee33e
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."XACT_CODES" 
    FROM 'C:\\tmp\\\\xact_codes.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
