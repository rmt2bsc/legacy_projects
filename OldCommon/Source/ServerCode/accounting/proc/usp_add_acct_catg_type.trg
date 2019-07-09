/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_acct_catg_type
 *   Prototype: id int output
 *                      acct_type_id  int
 *                      desc varchar(50)
 *      Returns: none
 *  Descrsiption: Inserts a row into the gl_account_category table.    Returns value of the primary key via @id output
 *                         parameter.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'usp_add_acct_catg_type' AND type = 'P')
   DROP PROCEDURE usp_add_acct_catg_type
GO

create procedure usp_add_acct_catg_type @id int output, 
																																							@acct_type_id int, 
																																							@desc varchar(50) as
  begin
      declare @exist_cnt int
      declare @max_id int
      
            -- Verify that the account type id exist
     select
             @exist_cnt = count(*)
     from
              gl_account_types
     where 
              id = @acct_type_id
              
         --  Raise error if account type does not exist.
     if @exist_cnt <= 0
         raiserror(50003, 18, 1, @acct_type_id)
         
     -- ****************************************************************************************************************************************
     -- *  Calculate the value of this primary key which will represent the category sequence of the associated account type id.
     -- *  The formula is basically,  (acct_type_id * 100).
     -- *   Example:  If the acct_type_id = 1, then its corresponding category id's (id) will be 100, 101, 102, 103, and so on.
     -- ****************************************************************************************************************************************
     select
             @max_id = max(id)
     from
             gl_account_category
     where
             acct_type_id = @acct_type_id
             
     if @max_id is null or @max_id <= 0
              --  Make first category id a product of (acct_type_id and 100) + 1 such as: 101, 201, 301, 401, and 501
         set @max_id = (@acct_type_id * 100) + 1
     else
              --  increment category by 1
          set @max_id = @max_id + 1
         
            --  Update the database with the new account category.    
      insert into GL_ACCOUNT_CATEGORY
					( id,
					  acct_type_id, 
					 description, 
					 date_created, 
					 date_updated, 
					 user_id)
				values 
					(@max_id,
					 @acct_type_id,
					 @desc,
					 getdate(),
					 getdate(),
					 user)
       
     set @id = @max_id
  end
go


