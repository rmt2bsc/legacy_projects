/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_acct
 *      Returns: none
 *  Descrsiption: Inserts a row into the gl_accounts table.    Returns value of the primary key via @id output
 *                         parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_upd_acct' AND type = 'P')
   DROP PROCEDURE usp_upd_acct
GO

create procedure usp_upd_acct @id int, 
                              @name varchar(100), 
                              @descr varchar(255) as
  begin
      update GL_ACCOUNTS set
           name = @name,
           description = @descr,
           date_updated = getdate(),
           user_id = user
       where id = @id
  end
go


