@echo off

echo Attemping to locate Non Nt User
echo finding %1 from setuser.bat
find /i "%1" x:\user.dat
if %errorlevel% == 0 SET NON_NT_USER=%1
