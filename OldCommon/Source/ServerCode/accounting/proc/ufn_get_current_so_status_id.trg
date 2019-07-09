/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_current_so_status_id
 *     Prototype: int @so_id
 *        Returns: int 
 *  Description: Retrieves the primary key of the sales order status history entry in the sales_order_status_hist table regarding a sales order.   Otherwise, 
 *                    a negative value is returned.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_current_so_status_id')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_so_status_id 
go

create function ufn_get_current_so_status_id (@so_id int)  returns int as

		begin
          declare @id int
          declare @result int
          
          select
                 @id = id
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
               
           return @id
		end
go


