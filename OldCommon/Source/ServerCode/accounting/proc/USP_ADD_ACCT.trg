/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_acct
 *  Descrsiption: Inserts a row into the gl_accounts table.    Returns value of the primary key via @id output parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_acct' AND type = 'P')
   DROP PROCEDURE usp_add_acct
GO

create procedure usp_add_acct @id int output,
                               @acct_type_id int,
                               @acct_catg_id int,
                               @acct_seq int output,
                               @acct_no varchar(20) output,
                               @name varchar(100),
                               @descr varchar(255) as
  begin
       declare @exist_cnt int

           --  Verify that the GL Account Category exist.
       select
	            @exist_cnt = count(*)
	     from
	             gl_account_category
	     where
	             id = @acct_catg_id
	     and acct_type_id = @acct_type_id

            --  Raise error if GL Account Category is not found.
        if @exist_cnt is null or @exist_cnt <= 0
            raiserror(50002, 18, 1, @acct_catg_id, @acct_type_id)

             --  Begin to build GL Account.
      exec usp_get_next_acct_seq @acct_type_id, @acct_catg_id, @acct_seq output
      exec usp_build_account_no @acct_type_id, @acct_catg_id, @acct_seq, @acct_no output

      insert into GL_ACCOUNTS
					(acct_type_id,
					 acct_cat_id,
					 acct_seq,
					 acct_no,
					 name,
                     description,
					 date_created,
					 date_updated,
					 user_id)
				values
					(@acct_type_id,
					 @acct_catg_id,
					 @acct_seq,
					 @acct_no,
					 @name,
                     @descr,
					 getdate(),
					 getdate(),
					 user)

    if @@error <> 0
          raiserror(50001, 18, 1)

    set @id = @@identity
  end
go


