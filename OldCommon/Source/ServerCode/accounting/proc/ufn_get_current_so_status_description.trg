/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_current_so_status_description
 *     Prototype: int @so_id
 *        Returns: varchar
 *  Description: Retrieves the sales order status desccription of a sales order.   Ottherwise, null is returned.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_current_so_status_description')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_so_status_description 
go

create function ufn_get_current_so_status_description (@so_id int)  returns varchar(30) as

		begin
          declare @so_stat_descr varchar(30)
          declare @result int
          
          select
                 @so_stat_descr = sos.description
           from
                  sales_order_status_hist sosh,
                  sales_order_status sos
           where
                  sosh.sales_order_id = @so_id
           and sosh.sales_order_status_id = sos.id
           and sosh.end_date is null
           
           set @result = @@rowcount
           
           --  Return null if history is not found
           if @result = 0
               return null
               
           return @so_stat_descr
		end
go


