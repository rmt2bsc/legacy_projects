/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_datepart
 *       Prototye: @date_value
 *        Returns: datetime
 *    Description: Converts the format of datetime value of @date_value for mm-dd-yyyy hh:mm_ss to mm-dd-yyyy and returns the
 *                 results to caller.
 *******************************************************************************************************************************/
if exists (select * from   sysobjects where  name = 'ufn_get_datepart')
	drop function ufn_get_datepart
GO

create function ufn_get_datepart  ( @date_value datetime )
   returns datetime
as

begin
   declare @date_string varchar(20)
   declare @date datetime

   set @date_string = convert(varchar, @date_value, 110)
   set @date = cast(@date_string as datetime)        
   return @date
end

GO
go


