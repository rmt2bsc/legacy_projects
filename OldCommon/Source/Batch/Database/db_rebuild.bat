@echo off
if %1. == . goto usage
if %2. == . goto usage
    if exist %1%2.db goto ok1
	echo Database %1%2.db does not exists.
	echo be sure that %2.db exist and that it resides in the directory, %1.
	goto exit
    :ok1
    if exist %1backup goto ok2
        echo Creating directory %1\backup
	mkdir %1\backup

    :ok2
    dbunload -c "uid=dba;pwd=sql;dbf=%1%2" -r %1backup\reload.sql -y %1\backup
    dberase -y %1%2.db
    dbinit %1%2
    dbisql -c "uid=dba;pwd=sql;dbf=%1%2;dbs=-q" read %1backup\reload.sql
    goto done
:usage
    echo Usage: db_rebuild {database path} {database name}
    echo        Unloads "{<database path> <database name>}", then creates new "{<database path> <database name>}.db".
    echo        Be sure to not specify ".db" in the database names.
    goto exit
:done
  echo !
  echo *****************************************
  echo * Rebuild completed successfully !!!!   *
  echo *****************************************
  goto exit
:faliure
  echo !
  echo *****************************************
  echo * Rebuild Failed !!!!   *
  echo *****************************************
  goto exit
:exit
