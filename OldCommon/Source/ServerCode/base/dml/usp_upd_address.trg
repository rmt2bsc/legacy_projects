/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_address
 *      Returns: none
 *  Descrsiption: Inserts a row into the business table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_upd_address' AND type = 'P')
   DROP PROCEDURE usp_upd_address
GO

create procedure usp_upd_address   @id int,
								 @person_id int,
								 @business_id int,
								 @addr1 varchar(25),
								 @addr2 varchar(25),
								 @addr3 varchar(25),
								 @addr4 varchar(25),
								 @zip int,
								 @zipext varchar(4),
								 @phone_home varchar(13),
								 @phone_work varchar(13),
								 @phone_ext varchar(8),
								 @phone_main varchar(13),
								 @phone_cell varchar(13),
								 @phone_fax varchar(13),
								 @phone_pager varchar(13)	  as

  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id() 
      
      update ADDRESS set
		person_id = nullIf(@person_id, 0),
		business_id = nullIf(@business_id, 0),
		addr1 = @addr1,
		addr2 = @addr2,
		addr3 = @addr3,
		addr4 = @addr4,
		zip = @zip,
		zipext = @zipext,
		phone_home = @phone_home,
		phone_work = @phone_work,
		phone_ext = @phone_ext,
		phone_main = @phone_main,
		phone_cell = @phone_cell,
		phone_fax = @phone_fax,
		phone_pager = @phone_pager,
		date_updated  = getdate(),
		user_id = @user_id
	  where id = @id
  end
go


