EXEC sp_addmessage 60001, 18, 'Audio Video Artist type, %d, is not valid'
go
EXEC sp_addmessage 60002, 18, 'Audio Video Artist, %s, cannot be added since it already exist in the system'
go
EXEC sp_addmessage 60003, 18, 'Audio Video Genre, %s, cannot be determined'
go
EXEC sp_addmessage 60004, 18, 'Audio Video Id does not exist: %d'
go
EXEC sp_addmessage 60005, 18, 'Audio Video Track Title is required'
go
EXEC sp_addmessage 60006, 18, 'Audio Video Track Number is required'
go
EXEC sp_addmessage 60007, 18, 'Audio/Video Library update failed!   A Security Manager exists and has denied read access to the file or directory.  File or path in error: %s'
go
