--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- ( Version :  10.0.0.2465)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------

if db_property('PageSize') >= 4096 then 
    LOAD STATISTICS "DBA"."GENERAL_CODES"."group_id" 
	64, -1, 22, 14, 
	0x000000000000104000000000000014400000000000001440000000000000184000000000000018400000000000001c400000000000001c40000000000000204000000000000020400000000000002e400000000000002e40000000000000394000000000000039400000000000003a40,
	0x00000000300c433c310cc33c2f0cc33b310c433c2f0cc33b310cc33c3dcf333f3dcf333ff43ccf3df43ccf3d3dcff33d3dcff33d2549923c
end if
go


-------------------------------------------------
--   Reload data
-------------------------------------------------

INPUT INTO "DBA"."GENERAL_CODES" 
    FROM 'C:\\tmp\\general_codes.dat'
    FORMAT ASCII
    ESCAPE CHARACTER '\\'
    BY ORDER
    ENCODING 'windows-1252'
go

commit work
go
