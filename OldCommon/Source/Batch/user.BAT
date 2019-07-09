@echo off

if not %2 == "" goto UserFound

:UserNotFound
echo Home Directory for Windows 95/98 User not found!
rem SET NON_NT_USER=
goto End

:UserFound
echo Home Directory for Windows 95/98 User was found!
SET NON_NT_USER=%2

:End
