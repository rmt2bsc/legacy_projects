echo off
rem
rem In order to execute this file either include in OS Path or navigate to the directory 
rem where it resides and type at the command line prompt, xml_bind <return key>.
rem 

del /Q C:\projects\gen\com\xml\schema\bindings\*.*
del /Q C:\projects\webservices\databinding\src\java\main\com\xml\schema\bindings\*.*
xjc -d C:\projects\webservices\databinding\gen -p com.xml.schema.bindings C:\projects\webservices\databinding\src\xml\schemas\
copy C:\projects\gen\com\xml\schema\bindings\*.* C:\projects\webservices\databinding\src\java\main\com\xml\schema\bindings