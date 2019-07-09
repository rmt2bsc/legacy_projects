/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_get_system_pref
 *  Descrsiption: Retrieves a system preference by supplying the key value.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_get_system_pref' AND type = 'P')
   DROP PROCEDURE usp_get_system_pref
GO

create procedure usp_get_system_pref @id varchar(25),
                                     @descr varchar(50) output as
  begin
      declare @user_id  varchar(25)
			       
      set @user_id = dbo.ufn_get_app_user_id()
      
       select
	            @descr = descr
	     from
	            system_pref
	     where
	            main_key = @id

       -- Send null if row not found
       if @@rowcount = 0
          set @descr = null
  end
go


