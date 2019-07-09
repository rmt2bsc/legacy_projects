
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %  Create Database
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 % DBTOOL CREATE DATABASE "c:\projects\home\data\sql\HomeSysT.db"
 %     TRANSACTION LOG TO "c:\projects\home\data\sql\HomeSysT.log"
 %     IGNORE CASE 
 %     PAGE SIZE 4096;

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %  Connect to Database
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 % CONNECT TO "c:\projects\home\data\sql\HomeSysT"
 %    USER "DBA"
 %    IDENTIFIED BY "sql";

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Create Database Tables
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\Projects\RMT2\source\sql\Sybase\DBCore\DBCoreTables.sql";
  READ "c:\Projects\RMT2\source\sql\Sybase\Accounting\TBAccounting.sql";
  READ "c:\Projects\Home\source\sql\Sybase\TB206MusicVideo.sql";


  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Populate tables with base data
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\Projects\RMT2\source\sql\Sybase\DBCore\DBCoreData.sql";
  READ "c:\Projects\Home\source\sql\Sybase\SYBHomesysBaseData.sql";
  READ "c:\Projects\RMT2\source\sql\Sybase\Accounting\AccountingData.sql";

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Create Primary Keys and Foreign Keys
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\Projects\RMT2\source\sql\Sybase\DBCore\DBCoreKeys.sql";
  READ "c:\Projects\RMT2\source\sql\Sybase\Accounting\PKAccounting.sql";
  READ "c:\Projects\RMT2\source\sql\Sybase\Accounting\FKAccounting.sql";
  READ "c:\Projects\Home\source\sql\Sybase\PK206MusicVideo.sql";
  READ "c:\Projects\Home\source\sql\Sybase\FK206MusicVideo.sql";

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  % Create Indexes
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\Projects\RMT2\source\sql\Sybase\DBCore\DBCoreIndex.sql";  
  READ "c:\Projects\Home\source\sql\Sybase\NDXHomesysCore.sql";
  READ "c:\Projects\RMT2\source\sql\Sybase\Accounting\NDXAccounting.sql";

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %      Clean Up Data
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  READ "c:\Projects\RMT2\source\sql\Sybase\DBCore\DBCoreRemoveCityCodeDupsFromAddress.sql";
  READ "c:\Projects\RMT2\source\sql\Sybase\DBCore\RemoveBusinessContactTable.sql";
  READ "c:\Projects\home\source\sql\Sybase\TBProject.sql";
  
  COMMIT;



