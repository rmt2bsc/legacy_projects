/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: usp_calc_proj_hours
  *        Returns: numeric @tot_hrs
 *  Description: Calculate the total billable hours for a period (Year and Month) of a project.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects  where  id = object_id('usp_calc_proj_hours')  and type in ('P'))
   drop procedure usp_calc_proj_hours
go

create procedure usp_calc_proj_hours @event_id int, 
                                     @tot_hrs numeric(5, 2) output,
                                     @only_billable bit = 1 as

		begin
          declare @hours numeric(5, 2)               
          declare @event_date datetime
          declare @role_id int
          declare @billable bit
         
		  declare cur_event_dtl cursor for
		  	select
		    	  event_date,
		    	  hours,
		    	  proj_role_id
			from
		    	  proj_event_details
			where
		    	  proj_event_id = @event_id
          

          -- Total hours for the period.          
          set @tot_hrs = 0
          open cur_event_dtl
          fetch next from cur_event_dtl into @event_date, @hours, @role_id
          
          while @@fetch_status = 0 
              begin
                  if @only_billable = 1
                    -- We can only add billable items
    				begin
    				  -- Get billable flag for current role
    				  select
    				    	 @billable = billable
   					  from 
							 proj_role
   					  where
  							 id = @role_id
							  
					  -- Throw exception if role is not found.        
    				  if @@rowcount <> 1
    					 raiserror(50047, 18, 1, @role_id)

    				  if @billable = 1                   
    				     -- Role is billable
    				     begin
    					   set @tot_hrs = @tot_hrs + @hours
    					 end
    				end
                  else
                    -- We can add both billable and non-billable items.
                    begin
                      set @tot_hrs = @tot_hrs + @hours
                    end
                  fetch next from cur_event_dtl into @event_date, @hours, @role_id
              end -- While
              
           close cur_event_dtl
           deallocate cur_event_dtl
                     
           return
		end
go


