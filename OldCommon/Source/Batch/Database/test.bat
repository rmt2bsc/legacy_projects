echo %1
echo %2
echo dbf=%1%2
echo %1backup\reload.sql
dbisql -c "uid=dba;pwd=sql;dbf=%1%2;dbs=-q" read %1backup\reload.sql