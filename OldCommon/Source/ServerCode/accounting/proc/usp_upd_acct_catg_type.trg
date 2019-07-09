/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *               Name: usp_upd_acct_catg_type
 *         Prototype: id int 
 *                            desc varchar(50)
 *           Returns: none
 *  Descrsiption: Updates the description of a GL Account Categroy Type.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_upd_acct_catg_type' AND type = 'P')
   DROP PROCEDURE usp_upd_acct_catg_type
GO

create procedure usp_upd_acct_catg_type @id int, 
																																							@desc varchar(50) as
  begin
      update GL_ACCOUNT_CATEGORY set
           description =  @desc,
           date_updated = getdate()
           where id = @id;
  end
go


