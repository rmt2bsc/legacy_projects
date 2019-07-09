/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_person
 *      Returns: none
 *  Descrsiption: Inserts a row into the person table.    Returns value of the primary key via @id output  parameter.  
 ******************************************************************************************************************************/
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_upd_person' AND type = 'P')
   DROP PROCEDURE usp_upd_person
GO

create procedure usp_upd_person @id int, 
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
      if @title = 0
         set @title = null
      if @gender_id = 0
         set @gender_id = null
      if @marital_status = 0
         set @marital_status = null
      if @race_id = 0
         set @race_id = null
      update PERSON set
		 firstname =@firstname,
		 midname = @midname,
		 lastname = @lastname,
		 shortname = @lastname + ', ' + @firstname,
		 maidenname = @maidenname,
		 generation = @generation,
		 title = @title,
		 gender_id = @gender_id,
		 marital_status = @marital_status,
		 birth_date = @birth_date,
		 race_id = @race_id,
		 ssn = @ssn,
		 email = @email,
		 date_updated  = getdate(), 
		 user_id = user
 	  where id = @id

  end
go


