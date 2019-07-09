--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."USER_APP_ROLE"."app_role_id" 
	64, -1, 22, 10, 
	0x0000000000000000000000000000f03f0000000000000040000000000000084000000000000020400000000000002040000000000000244000000000000024400000000000002a400000000000002a40,
	0x00000000cdcc4c3ecdcc4c3ecdcc4c3ecdcc4c3ecdcc4c3ecdcc4c3ecdcc4c3e00000000abaa2a3e
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."USER_APP_ROLE"."user_login_id" 
	64, -1, 22, 5, 
	0x0000000000000000000000000000f03f000000000000f03f00000000000041400000000000004140,
	0x000000000000803fb76d5b3f000000002549123e
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."USER_APP_ROLE" 
    FROM 'C:\\tmp\\691.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
