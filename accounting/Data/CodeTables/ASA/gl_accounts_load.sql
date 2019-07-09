--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."GL_ACCOUNTS"."acct_type_id" 
	65, -1, 6, 6, 
	0x000000000000f03f000000000000f03f0000000000000040000000000000144000000000000018400000000000001c40,
	0x000000008a9dd83ec54e6c3e4fec443ed9899d3dd9899d3d
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."GL_ACCOUNTS"."acct_cat_id" 
	64, -1, 22, 11, 
	0x00000000000059400000000000405940000000000040594000000000008059400000000000206940000000000020694000000000004069400000000000507f400000000000507f400000000000c882400000000000e88540,
	0x00000000c54e6c3ec54e6c3e4fec443e4fec443e4fec443ed9891d3d4fec443e4fec443ed9899d3dd9899d3d
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."GL_ACCOUNTS"."name" 
	66, -1, 20, 5, 
	0xe84067135e65956b418755b9e68d888793d14015c888888888,
	0x00000000d9891d3dd9891d3dd9891d3dd9891d3d
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."GL_ACCOUNTS"."code" 
	66, -1, 20, 3, 
	0x000000005e65956b7f3f05e4608888,
	0x8fc2753dd9891d3d00000000
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."GL_ACCOUNTS" 
    FROM 'C:\\tmp\\gl_accounts.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
