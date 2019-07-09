/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_sales_order
 *  Descrsiption: Creates a new sales order.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_add_sales_order' AND type = 'P')
   DROP PROCEDURE usp_add_sales_order
GO

create procedure usp_add_sales_order @id int output, 
                                     @customer_id int  as
  begin
    declare @exist_cnt int
    declare @QUOTE varchar(8)
   
    set @QUOTE = 'Quote'
   
    --  Verify that Customer exists
    select
        @exist_cnt = count(*)
    from
        customer
    where
        id = @customer_id

    if @exist_cnt <= 0 
       raiserror(50018, 18, 1, @customer_id)

    insert into 
        SALES_ORDER
			(customer_id,
			 date_created, 
			 date_updated, 
			 user_id)
		values 
			(@customer_id,
			 getdate(),
			 getdate(),
			 user)

    set @id = @@identity

    --  Update sales order status to 'Qoute'    
    exec usp_chg_so_status @id, @QUOTE

  end
go


