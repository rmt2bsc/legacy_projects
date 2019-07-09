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
    LOAD STATISTICS "DBA"."ZIPCODE"."zip" 
	65, 4.33739369328E-5, 5, 1, 
	0x584dcf1200000000,
	0x00000000
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."ZIPCODE"."city" 
	66, -1, 20, 2, 
	0x08b16213cc8dbd620084,
	0x8fc2753dd4eb7f3c
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."ZIPCODE"."state" 
	66, -1, 20, 5, 
	0x1044f61246140651684206516e3e0651463a06511084040404,
	0x8fc2753dd9ef5c3d192d693dc21c833cece43c3c
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."ZIPCODE"."area_code" 
	66, -1, 20, 2, 
	0x403081123b0b2b51b088,
	0xd42bbd38b026253c
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."ZIPCODE"."city_alias_name" 
	66, -1, 20, 3, 
	0x0000000066140651c2348fe0a88484,
	0x8fc2753d5178d93c005f053d
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."ZIPCODE"."city_type" 
	65, -1, 5, 1, 
	0xb044f51300000000,
	0x47ea7337
end if
go

if db_property('PageSize') >= 4096 and
   db_property('Collation') = '1252LATIN1' then 
    LOAD STATISTICS "DBA"."ZIPCODE"."county_name" 
	66, -1, 20, 1, 
	0x0000000060,
	0x8fc2753d
end if
go

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."ZIPCODE"."time_zone" 
	64, -1, 22, 11, 
	0x0000000000000840000000000000104000000000000014400000000000001440000000000000184000000000000018400000000000001c400000000000001c400000000000002040000000000000204073760bbf1a132240,
	0x08b3213c26b4173db497d03eebf3033bfe36c53e67e15e3d26b4973d1cbb243b26b4973dd9ef5c3dd6b9723c
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."ZIPCODE" 
    FROM 'C:\\tmp\\zipcode.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
