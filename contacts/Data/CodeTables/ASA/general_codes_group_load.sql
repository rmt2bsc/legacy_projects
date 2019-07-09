--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."GENERAL_CODES_GROUP" 
    FROM 'C:\\tmp\\general_codes_group.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
