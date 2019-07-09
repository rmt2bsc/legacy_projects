@echo off

rem ******************************************************************************
rem *  %1 path and filename of source backup
rem *  %2 destination path
rem ******************************************************************************

cls
dbstop all
echo **** Dababase Restore from seelcted Backup Procedure ****
echo .
echo .

if not exist %1 goto badsource
  echo Source backup was verified...

if not exist %2 goto baddestination
  echo Target where backup is to be sent verified...

:extract
  echo Extracting data files....
  attrib -R %2\*.*
  arj x -y %1 %2
  echo ************************************
  echo   backup completed successfully!!!
  echo ************************************
  goto exit
:badsource
  echo Named source backup is invalid...Backup Failed
  goto exit
:baddestination
  echo Named destination of backup is invalid...Backup Failed
:exit
  pause


