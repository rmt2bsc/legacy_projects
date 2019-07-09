/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_person
 *      Returns: none
 *  Descrsiption: Inserts a row into the person table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_person' AND type = 'P')
   DROP PROCEDURE usp_add_person
GO

create procedure usp_add_person @id int output,
    							 @firstname varchar(30),
    							 @midname varchar(30),
    							 @lastname varchar(30),
    							 @maidenname varchar(30),
    							 @generation varchar(5),
    							 @title int,
    							 @gender_id int,
    							 @marital_status int,
    							 @birth_date varchar(12),
    							 @race_id int,
    							 @ssn varchar(15),
    							 @email varchar(80)   as

  begin
	   declare @user_id  varchar(25)      
	   
	   set @user_id = dbo.ufn_get_app_user_id()
  
      insert into PERSON
					(firstname,
					 midname,
					 lastname,
					 shortname,
					 maidenname,
					 generation,
					 title,
					 gender_id,
					 marital_status,
					 birth_date,
					 race_id,
					 ssn,
					 email,
					 date_created,
					 date_updated,
					 user_id)
				values
					(@firstname,
					 @midname,
					 @lastname,
					 @lastname + ', ' + @firstname,
					 @maidenname,
					 @generation,
					 nullif(@title, 0),
					 nullif(@gender_id, 0),
					 nullif(@marital_status, 0),
					 @birth_date,
					 nullif(@race_id, 0),
					 @ssn,
					 @email,
					 getdate(),
					 getdate(),
					 @user_id)

    set @id = @@identity
  end
go


