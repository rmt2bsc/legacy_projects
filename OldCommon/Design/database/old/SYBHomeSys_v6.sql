
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %  Create Database
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  

%START ENGINE AS Homesyst;
CREATE DATABASE 'c:\\data\\sql\\HomeSysT.db'
   TRANSACTION LOG ON 'c:\\data\\sql\\HomeSysT.log'
   CASE IGNORE
   PAGE SIZE 1024
   JAVA ON;


 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 %  Connect to Database
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
   CONNECT  %TO 'Homesyst' 
     DATABASE 'c:\\data\\sql\\HomeSysT.db'
     USER "DBA"
     IDENTIFIED BY "sql";


  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Create Database Tables
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\source\sql\sybase\dbcore\DBCoreTables.sql";
  READ "C:\Source\SQL\Sybase\Projects\Home\TB206MusicVideo.sql";


  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Populate tables with base data
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\source\sql\sybase\dbcore\DBCoreData.sql";
  READ "C:\Source\SQL\Sybase\Projects\Home\SYBHomesysBaseData.sql";

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Create Primary Keys and Foreign Keys
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\source\sql\sybase\dbcore\DBCoreKeys.sql";
  READ "C:\Source\SQL\Sybase\Projects\Home\PK206MusicVideo.sql";
  READ "C:\Source\SQL\Sybase\Projects\Home\FK206MusicVideo.sql";

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Create Indexes
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\source\sql\sybase\dbcore\DBCoreIndex.sql";  
  READ "C:\Source\SQL\Sybase\Projects\Home\NDXHomesysCore.sql";

  COMMIT;



