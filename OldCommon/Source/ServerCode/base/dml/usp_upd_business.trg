/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_business
 *      Returns: none
 *  Descrsiption: Inserts a row into the business table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_upd_business' AND type = 'P')
   DROP PROCEDURE usp_upd_business
GO

create procedure usp_upd_business @id int,
								 @bus_type int,
								 @serv_type int,
								 @longname varchar(40),
								 @shortname varchar(15),
								 @contact_firstname varchar(25),
								 @contact_lastname varchar(25),
								 @contact_phone varchar(15),
								 @contact_ext varchar(10),
								 @tax_id varchar(20),
								 @website varchar(100)   as

  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id() 
      
      update BUSINESS set
		 bus_type = nullIf(@bus_type, 0),
		 serv_type = nullIf(@serv_type, 0),
		 longname = @longname,
		 shortname = @shortname,
		 contact_firstname = @contact_firstname,
		 contact_lastname = @contact_lastname,
		 contact_phone = @contact_phone,
		 contact_ext = @contact_ext,
		 tax_id = @tax_id,
		 website = @website,
		 date_updated  = getdate(),
		 user_id = @user_id
	  where id = @id
  end
go


