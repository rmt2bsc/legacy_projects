@echo off

rem ******************************************************************************
rem *  %1 path of the temporary location of new archive
rem *  %2 filename of archive
rem *  %3 path and filename of the data files (source)
rem *  %4 should be the path where the compressed file will be moved.
rem ******************************************************************************

cls
dbstop all
echo **** Dababase Backup Procedure ****
echo .
echo .
if not exist %1 goto badtemplocation
  echo Path where temporary archive is to resided was verified...

if not exist %3 goto badsource
  echo Source of backup was verified...

if not exist %4 goto badtarget
  echo Target where backup is to be sent verified...

:compress
  echo compressing data files....
  rem arj a -r %1%2 %3*.*
  cd %3
  attrib -R *.*
  arj a -r %1%2 *.*

  echo ---  moving %1%2.arj to %4 ---
  move %1%2.arj %4
  cd ..
  echo ************************************
  echo   backup completed successfully!!!
  echo ************************************
  goto exit
:badtemplocation
  echo .
  echo .
  echo **************************************************************************
  echo *                     Error !!!!!!!!!
  echo *                  ---------------------
  echo *  Path where temporary archive is to resided is invalid...Backup Failed
  echo **************************************************************************
  goto exit
:badsource
  echo .
  echo .
  echo **************************************************************************
  echo *                        Error !!!!!!!!!
  echo *                    ---------------------
  echo *              Source of backup is invalid...Backup Failed
  echo **************************************************************************
  goto exit
:badtarget
  echo .
  echo .
  echo **************************************************************************
  echo *                        Error !!!!!!!!!
  echo *                    ---------------------
  echo *        Target backup location is invalid...Backup Failed
  echo ************************************************************************** 
:exit
  pause


