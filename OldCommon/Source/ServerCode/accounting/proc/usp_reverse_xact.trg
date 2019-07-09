/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_reverse_xact
 *  Descrsiption: reverses transaction along with reversing associated GL entries.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects  WHERE name = 'usp_reverse_xact' AND type = 'P')
   DROP PROCEDURE usp_reverse_xact
GO

create procedure usp_reverse_xact @old_xact_id int, @new_xact_type_id int as
  begin
       declare @rc int
       declare @xact_type_id int
       declare @xact_amount  numeric(11,2)
       declare @xact_date  datetime
       declare @new_xact_id int
       declare @xact_cat_id int
       declare @to_mltplr int
       declare @from_mltplr int
       declare @to_acct_type_id int
       declare @from_acct_type_id int
       declare @gl_acct_id int
       declare @gl_amt   numeric(11,2)
       declare @today datetime
              
       declare cur_old_xact_postings cursor for
           select
                  gl_account_id,
                  abs(post_amount)
            from
                  xact_posting
            where 
                  xact_id = @old_xact_id
       
       
       --  Verify that old transaction exist.
       select
	         @xact_type_id = xact_type_id,
	         @xact_amount = xact_amount * -1,
	         @xact_date = xact_date
	   from
	         xact
	   where
	         id = @old_xact_id

       if @@rowcount <= 0 
          raiserror(50038, 18, 1, @old_xact_id)

       --  Add new transaction
       set @today = getdate()
       exec usp_add_xact @new_xact_id output, @today, @xact_amount, @new_xact_type_id
       if @new_xact_id <= 0
          raiserror(50039, 18, 1, @old_xact_id)
     
       --  Reverse all GL entries tied to the old transaction id.
       open cur_old_xact_postings
       fetch next from cur_old_xact_postings into @gl_acct_id, @gl_amt
           
       while @@fetch_status = 0
	   	 begin
		   --  Add reversed GL Account entry
		   exec usp_add_xact_entry @rc output, @gl_acct_id, @new_xact_id, @gl_amt
		   fetch next from cur_old_xact_postings into @gl_acct_id, @gl_amt
		 end
       close cur_old_xact_postings
       deallocate cur_old_xact_postings

       --  Post the new transaction
       exec usp_post_xact @new_xact_id
        
       return
  end
go


