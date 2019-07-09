@echo off
REM
REM This file is the template used to create the logon script for each user.
REM

REM
REM If this is running on dos, exit
REM

SET USERSHARE=F:\
SET HOMEDIR=U:
SET NON_NT_USER=

echo SETUP HOME DIRECTORY 
IF "%OS%" == "Windows_NT" goto winnt

REM Handle other OS Platforms
echo Initializing Home Directory for Windows 95/98 Client
net config workstation | find /i "user name" > %USERSHARE%\user.dat
for /d %user in (%USERSHARE%) do call h:\source\batch\login\setuser %user
if %NON_NO_USER% == "" goto homedir_exit
subst %HOMEDIR% %USERSHARE%%NON_NT_USER%
goto homedir_exit

:winnt
echo Initializing Home Directory for Windows NT Client
subst %HOMEDIR% %USERSHARE%%USERNAME%


:homedir_exit
del f:\user.dat

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


\\RMT2SYSTEM\Clients\Setup\%DIRECTORY%\startcli.exe /s:RMT2SYSTEM /u:analyst1 /l:c:\startcli.log


:exit
