--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."GL_ACCOUNT_CATEGORY"."acct_type_id" 
	64, -1, 22, 10, 
	0x0000000000000000000000000000f03f000000000000f03f0000000000000040000000000000004000000000000008400000000000001040000000000000144000000000000018400000000000001c40,
	0x000000000000403e0000403e0000403e0000403e0000803d0000a03e0000003e0000803d0000803d
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."GL_ACCOUNT_CATEGORY" 
    FROM 'C:\\tmp\\gl_account_category.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
