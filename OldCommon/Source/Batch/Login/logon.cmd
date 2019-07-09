@echo off

set USER_SHARE_PATH=\\rmtdalsys01\users\

rem synchronize workstation to rmtdalsqlsrv01
net time /set /y

c:
cd \
if "%OS%" == "Windows_NT" goto WINNT

:NOWINNT
net config | find /I "user name" > c:\setuser.bat
copy \\rmtdalsys01\archives\source\batch\user.bat c:\
call c:\setuser.bat
goto HOMEDIR

:WINNT
net config workstation | find /I "user name" > c:\user.dat
for /F "tokens=3 delims= " %%I in ('type "user.dat"') do set username2=%%I

:HOMEDIR
net use z: %USER_SHARE_PATH%%username2% 

del c:\setuser.bat
set username2= 
