/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: usp_get_cur_acct_prd
  *        Returns: varchar @period
 *  Description: Retrieves the current account period based on the account period type which the business is 
 *                         configured for.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects  where  id = object_id('usp_get_cur_acct_prd')  and type in ('P')) 
   drop procedure usp_get_cur_acct_prd
go



create procedure usp_get_cur_acct_prd @acct_period varchar(10) output as

		begin
          declare @cur_month int           -- Current month
          declare @fiscal_prd int          -- Calculated Fiscal Year Month
          declare @cur_yr int              --  Current year
          declare @fy_start_month int      --  Beginning month of the fiscal year
          declare @last_cal_month int      --  Last month of the calendar year
          declare @acct_prd_type_id int    --  Type of Accounting period we are dealing with
          declare @FISCAL_YEAR int         --  Constant for Fiscal year type id  
          declare @prev_yr_periods int     --  Total periods from the start month of fiscal year to the last calendar monthof that year
          declare @cur_date datetime
          
          set @last_cal_month = 12
          set @cur_date = getdate()
          select @cur_month = month(@cur_date)
          set @cur_yr = year(@cur_date)
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
                  set @acct_period = cast(@cur_month as varchar) + '-' + cast(@cur_yr as varchar)
                  return 
              end
          
          --  Determine account period based on fiscal year.
          if @cur_month between @fy_start_month and @last_cal_month
             begin
                set @fiscal_prd = (@cur_month - @fy_start_month) + 1
                set @acct_period = cast(@fiscal_prd as varchar) + '-' + cast(@cur_yr as varchar)
             end
          else   
             begin
                 -- We are in the next year
                 set @prev_yr_periods = (@last_cal_month - @fy_start_month) + 1
                 set @fiscal_prd = (@prev_yr_periods + @cur_month)
                 set @cur_yr = @cur_yr + 1
                 set @acct_period = cast(@fiscal_prd as varchar) + '-' + cast(@cur_yr as varchar)
             end    
                 
           return
		end
go


