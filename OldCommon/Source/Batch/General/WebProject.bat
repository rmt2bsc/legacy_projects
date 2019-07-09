rem @echo off


md %2\projects\webapp\%1
cd %2\projects\webapp\%1
md build
md css
md javascript
md data
md data\xml
md doc
md images
md META-INF
md WEB-INF
md WEB-INF\classes
md xsl
md source
md source\lib
md source\com
md source\com\action
md source\com\util
md source\com\api
md source\com\apiimpl
md source\com\bean
md source\com\constants
md source\com\factory
md source\com\servlet
md source\com\taglib

echo ***********************************
echo * Project Directories are created!
echo ***********************************