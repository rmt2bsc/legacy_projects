@echo off

rem  %1 = path
rem  %2 = data file (without extension)
rem  %3 = Version of ISQL

rem dberase -y %1%2.db
rem dbinit %1%2

if %3 == 5 goto version5
if %3 == 6 goto version6

:version5
  dberase -y %1%2.db
  "E:\Applications\sybase7\SQL Anywhere 5.0\win32\dbinit" %1%2
  rtsql -v -k -c "uid=dba;pwd=sql;dbf=%1%2;dbs=-q" read E:\Projects\Greens\source\sql\Sybase\greensdb.sql
  goto exit

:version6
  dberase -y %1%2.db
  "E:\Applications\sybase7\Adaptive Server Anywhere 6.0\win32\dbinit" %1%2
  dbisql -c "uid=dba;pwd=sql;dbf=%1%2;dbs=-q" read E:\Projects\Greens\source\sql\Sybase\greensdb.sql

:exit