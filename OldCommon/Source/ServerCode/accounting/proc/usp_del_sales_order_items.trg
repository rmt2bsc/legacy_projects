/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_del_sales_order_items
 *  Descrsiption: Deletes all items pertaining to a selected sales order
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_del_sales_order_items' AND type = 'P')
   DROP PROCEDURE usp_del_sales_order_items
GO

create procedure usp_del_sales_order_items @sales_order_id int  as
  begin
      delete from SALES_ORDER_ITEMS
	   where sales_order_id = @sales_order_id
  end
go


