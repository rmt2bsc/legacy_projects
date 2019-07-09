/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_add_item_master
 *      Returns: none
 *  Descrsiption: Inserts a row into the item master  table.    Returns the following values to the caller via output
 *                           parameter:   item master primary key as @id,  gl account id as @gl_account_id,
 *                           retail price as @retail_price, and active as @active
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects
         WHERE name = 'usp_add_item_master' AND type = 'P')
   DROP PROCEDURE usp_add_item_master
GO

create procedure usp_add_item_master @id int output,
                                     @vendor_id int,
                                     @item_type_id int,
									 @vendor_item_descr varchar(80),
									 @vendor_item_no varchar(25),
									 @item_serial_no varchar(25),
									 @qty_on_hand int,
									 @unit_cost numeric(15,2),
									 @markup numeric(5,2),
									 @retail_price numeric (15, 2) output,
                                     @override_retail bit,
									 @active bit output
as

  begin
      declare @exist_cnt int
      declare @ITEM_STATUS_ID int
      declare @acct_seq int
      declare @acct_no varchar(25)


      -- ********************************************************************************
      -- * Validate Vendor. If @vendor_id is not null Validate Item Master Details 
      -- * against database. This procedure assumes the Vendor Id has been verified to be 
      -- * a valid numeric value at this point.
      -- ********************************************************************************
      if @vendor_id > 0
        begin
          -- Verify vendor exists
          select
               @exist_cnt  = count(*)
           from
               creditor
           where
               id = @vendor_id

              --  Raise error if Vendor is not found.
          if @exist_cnt is null or @exist_cnt <= 0
              raiserror(50016, 18, 1, @vendor_id)
        end
      else
         begin
           set @vendor_id = null
         end

      set @active = 1

      --  Update database with new Item.
      insert into ITEM_MASTER
	   (vendor_id,
        item_type_id,
        description,
        vendor_item_no,
        item_serial_no,
        qty_on_hand,
        unit_cost,
        markup,
        retail_price,
        override_retail,
        active,
	    date_created,
		date_updated,
		user_id)
	  values
	    (@vendor_id,
         @item_type_id,
		 @vendor_item_descr,
		 @vendor_item_no,
		 @item_serial_no,
		 @qty_on_hand,
         @unit_cost,
         @markup,
		 @retail_price,
         @override_retail,
		 @active,
 	     getdate(),
	     getdate(),
	     user)

    set @id = @@identity

    if @id is null or @id <= 0
       raiserror(50017, 18, 1, @vendor_id, @vendor_item_descr, @vendor_item_no, @item_serial_no)

    --   Place Item in "Available" if quantity is greater than zero.   Otherwise, 'Out of Stock'.
    if @qty_on_hand <= 0
       select @ITEM_STATUS_ID = dbo.ufn_get_item_status_id('Out of Stock')
    else
       select @ITEM_STATUS_ID = dbo.ufn_get_item_status_id('Available')
       
    insert into ITEM_MASTER_STATUS_HIST
	   (item_master_id,
	    item_status_id,
		unit_cost,
		markup,
		effective_date,
		end_date,
		date_created,
		user_id)
    values
       (@id,
        @ITEM_STATUS_ID,
        @unit_cost,
        @markup,
        getdate(),
        null,
        getdate(),
        user)
  end
go


