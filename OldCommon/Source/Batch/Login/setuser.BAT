@echo off

echo Attemping to locate Non Nt User
find /i %1 %USERSHARE%\user.dat
if %errorlevel% == 0 SET NON_NT_USER=%1