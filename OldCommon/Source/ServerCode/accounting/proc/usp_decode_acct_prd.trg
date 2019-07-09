/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: usp_decode_acct_prd
  *        Returns: int @period
 *  Description: Retrieves the calendar month that corresponds to the accounting period which the busines is 
 *                         configured for.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects  where  id = object_id('usp_decode_acct_prd')  and type in ('P')) 
   drop procedure usp_decode_acct_prd
go



create procedure usp_decode_acct_prd @acct_period int, 
                                     @cal_month int output as

		begin
          declare @fy_start_month int      --  Beginning month of the fiscal year
          declare @last_cal_month int      --  Last month of the calendar year
          declare @acct_prd_type_id int    --  Type of Accounting period we are dealing with
          declare @FISCAL_YEAR int         --  Constant for Fiscal year type id  
          declare @prev_yr_periods int     --  Total periods from the start month of fiscal year to the last calendar monthof that year
          
          set @last_cal_month = 12
          set @FISCAL_YEAR = 2
          
          select 
                @fy_start_month = acct_prd_beg_month,
                @acct_prd_type_id = acct_prd_type_id
          from
                acct_pref
          where
                id = 1
          
          if @@rowcount <> 1
              raiserror(50031, 18, 1)
              
          if @acct_prd_type_id <> @FISCAL_YEAR
              begin
                  set @cal_month = @acct_period
                  return 
              end
          
             --  Determine Calendar month based on fiscal year.
          if @acct_period between @fy_start_month and @last_cal_month
             begin
                set @cal_month = (@fy_start_month + @acct_period) - 1
             end
          else   
             begin
                       -- We are in the next year
                 set @prev_yr_periods = (@last_cal_month - @fy_start_month) + 1
                 set @cal_month = (@acct_period - @prev_yr_periods)
             end    
                 
           return
		end
go


