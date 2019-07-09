--
-- This command file reloads a database that was unloaded using "dbunload".
--
-- (Version:  11.0.0.1264)
--



-------------------------------------------------
--   Reload column statistics
-------------------------------------------------


-------------------------------------------------
--   Reload data
-------------------------------------------------

call sa_unload_display_table_status( 17737, 1, 1, 'DBA', 'ITEM_MASTER_STATUS_HIST' )
go

INPUT INTO "DBA"."ITEM_MASTER_STATUS_HIST" 
    FROM 'C:\\tmp\\item_master_status_hist.dat'
    FORMAT TEXT
    ESCAPE CHARACTER '\\'
    BY ORDER("item_status_hist_id","item_id","item_status_id","unit_cost","markup","effective_date","end_date","reason","date_created","user_id")
    ENCODING 'windows-1252'
go

commit work
go
