/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_calc_proj_event_pay
 *      Returns: none
 *  Descrsiption: Inserts a row into the proj_event_details  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_calc_proj_event_pay' AND type = 'P')
   DROP PROCEDURE usp_calc_proj_event_pay
GO

create procedure usp_calc_proj_event_pay @event_id int,
										@proj_event_pay numeric(11, 2) as
  begin
      declare @user_id  varchar(25)      
      declare @tot_hrs numeric(5,2)
      declare @tot_ot_hrs numeric(5,2)
      declare @max_reg_hrs numeric
      declare @prd_type varchar(20)
      declare @prd_type_id int
      declare @pay_sum numeric(11,2)
      declare @bill_rate numeric(5,2)
      declare @event_detail_pay numeric(11,2)
      declare @event_stat int
      declare @emp_id int
      declare @client_id int
      declare @event_dtl_id int
      declare @event_dtl_event_id int
      declare @event_dtl_client_id int
      declare @event_dtl_proj_id int
      declare @event_dtl_role_id int
      declare @event_dtl_task_cd varchar(20)
      declare @event_dtl_date  datetime
      declare @event_dtl_hours numeric(9,2)
      declare @is_billable bit
      declare @is_ot_status bit
      declare @hr_rate numeric(5,2)
      declare @ot_rate numeric(5,2)
      declare @is_hr_bllng bit
      declare @is_ot_bllng bit
      declare @APPROVED int
      declare @COMPENSATED int
      
      declare cur_event_details cursor for
          select 
    			ped.id,
    			ped.proj_event_Id,
    			ped.proj_client_id,
    			ped.proj_project_id,
    			ped.proj_role_id,
    			ped.task_code,
    			ped.event_date,
    			ped.hours
          from
                proj_event_details ped,
                proj_event pe
          where
                pe.id = @event_id
            and pe.id = ped.proj_event_id
            and dbo.ufn_get_cur_proj_event_status_id(pe.id) = @APPROVED
          order by 
                event_date
                  
                  
      set @user_id = dbo.ufn_get_app_user_id()
      set @tot_hrs = 0
      set @tot_ot_hrs = 0
      set @pay_sum = 0
      set @APPROVED = 4
      set @COMPENSATED = 5

      -- Get Project Preferences
      select
			@max_reg_hrs = pp.max_reg_hrs,
			@prd_type = pp.prd_type,
			@prd_type_id = pp.id
	  from 
		    proj_period pp,
		    proj_pref pp2
	  where 
		    pp.id = pp2.event_submit_freq
                                             
	  if @@rowcount <> 1
	     raiserror(50049, 18, 1)
      
      
      -- Get Employee Id
      select
          @emp_id = proj_employee_id
      from
          proj_event
      where
          id = @event_id
      
      -- Throw exception if event is not found or 2 or more rows are returned
      if @@rowcount <> 1
         raiserror(50040, 18, 1, @event_id)

	  set @event_stat =  dbo.ufn_get_cur_proj_event_status_id(@event_id)
	  if @event_stat <= 0 
		 raiserror(50041, 18, 1, @event_id)

	  -- Current status must be "Approved"
	  if @event_stat <> @APPROVED
		 raiserror(50042, 18,1, @event_id) 

      --  process event details
	  open  cur_event_details
	  fetch next from cur_event_details into @event_dtl_id,
											 @event_dtl_event_id,
 						   		  	         @event_dtl_client_id,
											 @event_dtl_proj_id,
											 @event_dtl_role_id,
											 @event_dtl_task_cd,
											 @event_dtl_date,
											 @event_dtl_hours

	  while @@fetch_status = 0
	    begin
		  -- Determine if event detail is billiable
		  select
		        @is_billable = billable
		  from 
		        proj_role
		  where
		        id = @event_dtl_role_id
		
          -- Throw exception if role is not found.        
		  if @@rowcount <> 1
		     raiserror(50047, 18, 1, @event_dtl_role_id)
				    
		  -- Add event detail's hours to total hours.   If total hours exceed 
		  -- maximum regular hours, adjust total hours to equal maximum regular 
		  -- hours and apply the overage to total overtime hours.    
		  if @is_billable = 0
		     continue
				       
		  -- At this point we are billable and can resume processing detail item.
		  select 
				@hr_rate = rate,
				@ot_rate = hourly,
		    	@is_hr_bllng = ot_rate,
				@is_ot_bllng = ot_hourly
		  from 
				proj_client_bill_hist
		  where
				proj_employee_id = @emp_id
			and proj_client_id = @event_dtl_client_id
			and effective_date is not null
			and end_date is null

		  if @@rowcount <> 1
			 raiserror(50048, 18, 1, @emp_id)
            
          -- Calculate regular and/or overtime pay amount for detail item
		  if @tot_hrs < @max_reg_hrs
			begin
			  set @tot_hrs = @tot_hrs + @event_dtl_hours
			  if @tot_hrs > @max_reg_hrs
			     -- Regular hours have just exceeded maximum allowable regular hours.
			     -- Pay will have to be calculated using both regular and overtime
			     -- formulas.
				 begin
				   -- Calucate overtime hours
				   set @tot_ot_hrs = @tot_hrs - @max_reg_hrs
			
            	   -- Regular Pay portion
				   set @pay_sum = @pay_sum + ((@event_dtl_hours - @tot_ot_hrs) * @hr_rate)   
			
                   -- Overtime Pay portion
				   set @pay_sum = @pay_sum + (@tot_ot_hrs * @ot_rate)   
				   -- Set total regular hours to Maximum Regular Hours
				   set @tot_hrs = @max_reg_hrs
				 end
			  else
			    -- Only Calculate Regular pay
			    begin
			      set @pay_sum = @pay_sum + (@event_dtl_hours * @hr_rate)   
			    end
			 end
		 else
		   -- Only calculate overtime
		   begin
		  	 set @pay_sum = @pay_sum + (@event_dtl_hours * @ot_rate)
		   end

         -- Get next detail item.
		 fetch next from cur_event_details into @event_dtl_id,
										        @event_dtl_event_id,
												@event_dtl_client_id,
												@event_dtl_proj_id,
												@event_dtl_role_id,
												@event_dtl_task_cd,
												@event_dtl_date,
												@event_dtl_hours				    
	   end -- While
				 
	   set @proj_event_pay = @pay_sum
       close cur_event_details
       deallocate cur_event_details				 
				 
  end
go


