/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 7:09:32 AM                        */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_proj_employee
 *      Returns: none
 *  Descrsiption: Inserts a row into the proj_employee  table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_proj_employee' AND type = 'P')
   DROP PROCEDURE usp_add_proj_employee
GO

create procedure usp_add_proj_employee @id int output,
        							   @person_id int,
                                       @manager_id int,
									   @title_id int,
									   @type_id int,
                                       @bill_rate numeric,
                                       @ot_bill_rate numeric,
									   @start_date datetime,
									   @term_date datetime as
  begin
      declare @user_id  varchar(25)      
      
      set @user_id = dbo.ufn_get_app_user_id()

     insert into proj_employee
	   (person_id,
        manager_id,
		title_id,
		type_id,
        bill_rate,
        ot_bill_rate,
		start_date,
		termination_date,
		date_created,
		date_updated,
		user_id)
      values
       (@person_id,
        @manager_id,
        nullif(@title_id, 0),
        nullif(@type_id, 0),
        nullif(@bill_rate, 0),
        nullif(@ot_bill_rate, 0),
        @start_date,
        @term_date,
        getdate(),
        getdate(),
        @user_id)

    set @id = @@identity
  end
go


