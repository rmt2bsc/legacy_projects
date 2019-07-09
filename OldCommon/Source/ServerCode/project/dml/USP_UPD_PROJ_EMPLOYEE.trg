/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     12/19/2006 7:09:32 AM                        */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_upd_proj_employee
 *      Returns: none
 *  Descrsiption: Modifies a row in the proj_employee table.    Returns value of the primary key via @id output  parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_upd_proj_employee' AND type = 'P')
   DROP PROCEDURE usp_upd_proj_employee
GO

create procedure usp_upd_proj_employee @id int,
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

     update 
           proj_employee
     set
		   person_id = @person_id,
		   title_id = @title_id,
           manager_id = @manager_id,
		   type_id = @type_id,
           bill_rate = @bill_rate,
           ot_bill_rate = @ot_bill_rate,
		   start_date = @start_date,
		   termination_date = @term_date,
		   date_updated = getdate(),
		   user_id = @user_id
	 where
	       id = @id
  end
go


