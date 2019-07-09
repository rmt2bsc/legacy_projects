/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_sales_order
 *  Descrsiption: Modifies an existing sales order.   Only the customer id can be changed.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_upd_sales_order' AND type = 'P')
   DROP PROCEDURE usp_upd_sales_order
GO

create procedure usp_upd_sales_order @id int output, 
                                     @customer_id int  as
  begin
       declare @exist_cnt int
       
       --  Verify that Customer exists
       select
	         @exist_cnt = count(*)
	   from
	         customer
	   where
	         id = @customer_id

       if @exist_cnt <= 0 
          raiserror(50018, 18, 1, @customer_id)

       update 
             SALES_ORDER 
       set
			 customer_id = @customer_id,
			 date_updated = getdate(), 
			 user_id = user
       where id = @id
             
  end
go


