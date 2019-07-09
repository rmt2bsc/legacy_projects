@echo off

rem Run your application with "javaw.exe" rather than "java.exe".   Since we are running 
rem from a bat file, use the "javaw.exe" interpreter in combination with the "start" 
rem command in order to prevent the existence of the windows console.

start .\dist\jre\bin\javaw -jar LaunchPad.jar