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
    LOAD STATISTICS "DBA"."STATE"."state_id" 
	66, -1, 20, 5, 
	0x5085cd13f4348fe03e318fe064338fe06a328fe00004048484,
	0x00000000210dd23cc54eec3d210d523c210dd23c
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."STATE"."country_id" 
	64, -1, 22, 5, 
	0x000000000000f0bf0000000000000000000000000000f03f000000000000f03f0000000000000040,
	0x00000000d9891d3d8334483f3333433f7dcb373e
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."STATE"."state_name" 
	66, -1, 20, 3, 
	0x8051561238328fe03e318fe0000484,
	0x8fc2753d210dd23cd9899d3d
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."STATE" 
    FROM 'C:\\tmp\\state.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
