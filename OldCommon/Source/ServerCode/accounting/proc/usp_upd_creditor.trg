/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_creditor
 *      Returns: none
 *  Descrsiption: Updates the credit limit and APR in the creditor table.    
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_creditor' AND type = 'P')
   DROP PROCEDURE usp_upd_creditor
GO

create procedure usp_upd_creditor @id int , 
                                  @crediti_limit numeric(15, 2),
                                  @apr numeric (6, 4) as
  begin
    --  Modifiy creditior to the database
    update 
         CREDITOR 
    set
	     credit_limit = isNull(@crediti_limit, 0),
         apr = isNull(@apr, 0),
	     date_updated = getdate(),
	     user_id = user
	where 
         id = @id
  end
go


