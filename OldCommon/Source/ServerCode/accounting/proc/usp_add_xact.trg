/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_xact
 *  Descrsiption: Inserts a row into the xact  table.    Returns value of the primary key via @id output parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_add_xact' AND type = 'P')
   DROP PROCEDURE usp_add_xact
GO

create procedure usp_add_xact @id int output, 
                              @xact_date datetime output, 
                              @xact_amt numeric(15,2), 
                              @xact_type_id int,
                              @tender_id int,
                              @neg_instr_no varchar(30),
                              @xact_reason varchar(100)  as
  begin
       declare @exist_cnt int
       declare @post_date datetime
       
       
       --  Verify that the Transaction Type exist.
       select
	            @exist_cnt = count(*)
	     from
	             xact_type
	     where
	             id = @xact_type_id
     
            --  Raise error if Transaction Type is not found.
        if @exist_cnt is null or @exist_cnt <= 0 
            raiserror(50020, 18, 1, @xact_type_id)
  
       if @tender_id = 0
         set @tender_id = null
         
           -- Create Transaction      
       insert into XACT
					(xact_date, 
					 xact_amount,
					 xact_type_id,
                     tender_id,
					 posted_date,
                     neg_instr_no,
                     reason,
					 date_created, 
					 date_updated, 
					 user_id)
				values 
					(@xact_date,
					 @xact_amt,
					 @xact_type_id,
                     @tender_id,
					 null,
                     @neg_instr_no,
                     @xact_reason,
					 getdate(),
					 getdate(),
					 user)

    set @id = @@identity
  end
go


