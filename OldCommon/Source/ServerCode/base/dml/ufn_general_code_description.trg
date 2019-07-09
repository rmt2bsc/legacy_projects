/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/16/2006 2:29:33 PM                         */
/*==============================================================*/



create function ufn_general_code_description (@a_id int) RETURNS varchar(80) as
   
begin
declare @v_desc varchar(80)   
   select @v_desc = longdesc from general_codes where id = @a_id
   return @v_desc
end
go


