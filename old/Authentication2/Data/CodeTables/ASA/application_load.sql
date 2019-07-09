--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."APPLICATION"."name" 
	66, -1, 20, 6, 
	0xb8305612887ded5c655c43b7c6a4c85dc147ae81777f41fe808888888888,
	0x000000008c2eba3d8c2eba3d8c2eba3d8c2eba3d8c2eba3d
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."APPLICATION" 
    FROM 'C:.\\application.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
