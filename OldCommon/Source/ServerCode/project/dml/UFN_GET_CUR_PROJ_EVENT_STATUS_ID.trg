/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 3:01:23 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_cur_proj_event_status_id
  *        Returns: int 
 *  Description: Retrieves the primary key of a project event's current status.   Otherwise, a 
 *                         negative value is returned.
 *******************************************************************************************************************************/
create function ufn_get_cur_proj_event_status_id (@event_id int)  returns int as

		begin
          declare @id int
          declare @result int
          
          select
                 @id = proj_event_status_id
           from
                  proj_event_stat_hist
           where
                  proj_event_id = @event_id
           and end_date is null
           
           set @result = @@rowcount
           if @result > 1
              return -200
           
                --  Return null if event status is not found
           if @result = 0
               return -100
               
           return @id
		end
go


