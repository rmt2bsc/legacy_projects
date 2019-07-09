/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_current_so_status
 *     Prototype: int @so_id
 *        Returns: int 
 *  Description: Retrieves the sales order status of a sales order.   Otherwise, a negative value is returned.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_current_so_status')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_so_status 
go

create function ufn_get_current_so_status (@so_id int)  returns int as

		begin
          declare @so_stat_id int
          declare @result int
          
          select
                 @so_stat_id = sales_order_status_id
           from
                  sales_order_status_hist
           where
                  sales_order_id = @so_id
           and end_date is null
           
           set @result = @@rowcount
           if @result > 1
              return -200
           
                --  Return null if history is not found
           if @result = 0
               return -100
               
           return @so_stat_id
		end
go


