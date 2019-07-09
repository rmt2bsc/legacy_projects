rem @echo off

echo looping through directories
for %%u in (x:\*.*) do call %scriptdir%\setuser.bat %%u
echo finish looping through directories
