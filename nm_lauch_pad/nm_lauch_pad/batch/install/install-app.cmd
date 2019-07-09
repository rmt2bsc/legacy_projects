@echo off

if not "%1" == "jre" (
   if not "%1" == "nojre" (
     @echo Script failed!  Batch requires one of the following input parameters "jre" or "nojre".
     goto :END_BATCH
   )
)

set INSTALL_TYPE=%1
set ZIPPED_APP_PATH = W:\dist
set ZIP_TOOL_PATH = W:\ZipTool\7-Zip-9_20

for /F %%c in (Store-Computer-Names.txt) do call :MANAGE_COMPUTER %%c
goto :END_BATCH

:MANAGE_COMPUTER
if %1 == "" goto :END_BATCH
@echo Installing Launch Pad Application for computer: %1

REM ping %1

net use t: \\%1\rflaunchpad Drum9999 /USER:infosrv\vsrt5 
if "%INSTALL_TYPE%"=="jre" (
  call :JRE_INSTALL 
) else (
   call :NO_JRE_INSTALL
)

dir t:
net use t: /DELETE
goto :EOF


:JRE_INSTALL
@echo Intalling app with JRE...
%ZIP_TOOL_PATH%\7za x -ot: %ZIPPED_APP_PATH%\LaunchPad_JRE.zip 
goto :EOF

:NO_JRE_INSTALL
@echo Intalling app without JRE...
%ZIP_TOOL_PATH%\7za x -y -ot: %ZIPPED_APP_PATH%\LaunchPad_NO_JRE.zip 
goto :EOF



:END_BATCH
@echo installation complete!

:EOF