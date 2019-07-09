/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_business
 *  Descrsiption: Inserts a row into the business  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_business' AND type = 'P')
   DROP PROCEDURE usp_add_business
GO

create procedure usp_add_business @id int output,
								 @bus_type int,
								 @serv_type int,
								 @longname varchar(40),
								 @shortname varchar(15),
								 @contact_firstname varchar(25),
								 @contact_lastname varchar(25),
								 @contact_phone varchar(15),
								 @contact_ext varchar(10),
								 @tax_id varchar(20),
								 @website varchar(100) 	 as

  begin
       declare @exist_cnt int
       declare @user_id  varchar(25)
       
       set @user_id = dbo.ufn_get_app_user_id()
       
      insert into BUSINESS
	   (bus_type,
			serv_type,
		longname,
		shortname,
		contact_firstname,
		contact_lastname,
		contact_phone,
		contact_ext,
		tax_id,
		website,
		    date_created,
	    date_updated,
	    user_id)
	  values
    	( nullIf(@bus_type, 0),
    	  nullIf(@serv_type, 0),
    	  @longname,
    	  @shortname,
    	  @contact_firstname,
    	  @contact_lastname,
    	  @contact_phone,
    	  @contact_ext,
    	  @tax_id,
    	  @website,
          getdate(),
    	  getdate(),
    	  @user_id)

    set @id = @@identity
  end
go


