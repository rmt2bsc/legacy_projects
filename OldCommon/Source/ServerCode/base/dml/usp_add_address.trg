/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_address
 *  Descrsiption: Inserts a row into the address  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_address' AND type = 'P')
   DROP PROCEDURE usp_add_address
GO

create procedure usp_add_address @id int output,
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
								 @phone_pager varchar(13)	 as

  begin
       declare @exist_cnt int
       declare @user_id  varchar(25)
       
       set @user_id = dbo.ufn_get_app_user_id()
       
      insert into ADDRESS
					( person_id,
						business_id,
						addr1,
						addr2,
						addr3,
						addr4,
						zip,
						zipext,
						phone_home,
						phone_work,
						phone_ext,
						phone_main,
						phone_cell,
						phone_fax,
						phone_pager,
 					    date_created,
					    date_updated,
					    user_id)
				values
					(   nullIf(@person_id, 0),
    					nullIf(@business_id, 0),
    					@addr1,
    					@addr2,
    					@addr3,
    					@addr4,
    					@zip,
    					@zipext,
    					@phone_home,
    					@phone_work,
    					@phone_ext,
    					@phone_main,
    					@phone_cell,
    					@phone_fax,
    					@phone_pager,
					    getdate(),
					    getdate(),
					    @user_id)

    set @id = @@identity
  end
go


