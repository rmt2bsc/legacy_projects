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


LOAD TABLE IP_LOCATION ("ip_from","ip_to","country_code","country_name","region","city","latitude","longitude","zipcode","timezone","ip_id")
    FROM 'C:/tmp/IP_LOCATION.dat'
    FORMAT 'TEXT' QUOTES ON
    ORDER OFF ESCAPES ON
    CHECK CONSTRAINTS OFF COMPUTES OFF
    STRIP OFF DELIMITED BY ','
    ENCODING 'windows-1252'
go

