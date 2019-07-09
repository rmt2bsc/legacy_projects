/******************************************************************************************************************************
 *         Name: ufn_convert_date_to_num
 *      Returns: varchar
 *  Descrsiption: Converts the date part of the current timestamp an 8-digit number in the form of a varchar and returns
 *                            returns the results to the caller.
 *******************************************************************************************************************************/

if exists (select 1 from sysobjects  where  id = object_id('ufn_convert_date_to_num') and type in ('IF', 'FN', 'TF'))
   drop function ufn_convert_date_to_num
go

create function ufn_convert_date_to_num(@cur_date datetime )  returns varchar(10) as
  begin
      declare @cur_mm varchar(3)
      declare @cur_dy varchar(3)
      declare @cur_yr varchar(4)
      
              --  Build creditor's account number
      set @cur_mm = cast(month(@cur_date) as varchar)
      set @cur_dy = cast(day(@cur_date) as varchar)
      set @cur_yr = cast(year(@cur_date) as varchar)
      if len(@cur_mm) = 1
          set @cur_mm = '0' + @cur_mm
      if len(@cur_dy) = 1
          set @cur_dy = '0' + @cur_dy
          
      return @cur_mm + @cur_dy + @cur_yr
      
  end
go
            