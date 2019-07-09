--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."APP_ROLE"."application_id" 
	64, -1, 22, 12, 
	0x0000000000000000000000000000f03f000000000000f03f000000000000004000000000000000400000000000000840000000000000084000000000000010400000000000001040000000000000244000000000000028400000000000002840,
	0x00000000abaaaa3e2549923eabaaaa3e2549923eabaa2a3e2549123e5be95e3b2549123e052fa73c000000002549123e
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."APP_ROLE"."role_id" 
	64, -1, 22, 4, 
	0x0000000000000000000000000000f03f00000000000000400000000000001440,
	0x00000000abaa2a3fabaa2a3eabaa2a3e
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."APP_ROLE"."code" 
	66, -1, 20, 2, 
	0x000000003e60cb6ef084,
	0x8fc2753d2549123e
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."APP_ROLE"."name" 
	66, -1, 20, 2, 
	0x90355a134a79f5610084,
	0x8fc2753d2549123e
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."APP_ROLE" 
    FROM 'C:.\\app_role.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
