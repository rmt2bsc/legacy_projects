/******************************************************************************************************************************
 *         Name: usp_log_message
 *  Descrsiption: Logs a customized message to the SQL Server error log and MS Windows Event Viewer.
 *                @severity goes as follows: 1=informational, 2= warning, and 3=error
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_log_message' AND type = 'P')
   DROP PROCEDURE usp_log_message
GO

create procedure usp_log_message @msg varchar(200), @severity int  as
  begin
    if @severity = 1
      begin
        EXEC master.dbo.xp_logevent 77777, @msg, informational
      end
    if @severity = 2
      begin
        EXEC master.dbo.xp_logevent 88888, @msg, warning
      end
    if @severity = 3
      begin
        EXEC master.dbo.xp_logevent 99999, @msg, error
      end
  end
go
            