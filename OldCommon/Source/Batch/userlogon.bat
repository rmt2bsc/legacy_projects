@echo off
REM
echo This file is the login script for user, royroy
REM

REM
REM If this is running on dos, exit
REM

SET USERSHARE=\\rmt2system\rmt2users
SET DRIVE=U:
SET SCRIPTDIR=\\rmt2system\netlogon
echo ************************
echo Executing Userlogon.bat
echo ************************
echo SETUP HOME DIRECTORY 
IF "%OS%" == "Windows_NT" goto winnt

REM **************************
REM Handle other OS Platforms
REM **************************
echo *** Initializing Home Directory for Windows 95/98 Client

echo creating data file
net config | find /i "user name" > %SCRIPTDIR%\CURRENTUSER.BAT
echo finish creating data file

CALL %SCRIPTDIR%\CURRENTUSER.BAT
echo finish getting user name
pause

if %NON_NT_USER% == "" goto clientnotfound
echo Welcome %NON_NT_USER%
echo *** Executing net use ***
net use u: %USERSHARE%\%NON_NT_USER%
set CURRENTUSER=%NON_NT_USER%
pause

goto homedir_exit

:winnt
echo Initializing Home Directory for Windows NT Client
echo Welcome %USERNAME%
echo *** Executing net use ***
net use u: %USERSHARE%\%USERNAME%
set CURRENTUSER=%USERNAME%
goto homedir_exit

:clientnotfound
echo Could not find Home Directory for Windows 95/98 Clinet

:homedir_exit
if "%windir%" == "" goto exit

if "%COMPUTERNAME%" == "" goto proc_test
if "%COMPUTERNAME%" == "RMT2SYSTEM" goto exit

:proc_test
REM
REM Use PROCESSOR_ARCHITECTURE to pick the right directory
REM

if "%PROCESSOR_ARCHITECTURE%" == "" set DIRECTORY=win95
if "%PROCESSOR_ARCHITECTURE%" == "x86" set DIRECTORY=i386
if "%DIRECTORY%" == "" goto exit

\\RMT2SYSTEM\Clients\Setup\%DIRECTORY%\startcli.exe /s:RMT2SYSTEM /u:%CURRENTUSER% /l:c:\startcli.log

:exit
