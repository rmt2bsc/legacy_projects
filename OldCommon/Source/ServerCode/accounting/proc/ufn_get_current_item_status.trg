/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



if exists (select 1 from sysobjects where  id = object_id('ufn_get_current_item_status')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_current_item_status
go

create function ufn_get_current_item_status (@item_master_id int)  returns int as

		begin
          declare @id int
          declare @result int
          
          select
                   @id = item_status_id
           from
                    item_master_status_hist
           where
                    item_master_id = @item_master_id
           and end_date is null
           
           set @result = @@rowcount
           if @result > 1
              return -200
           
                --  Return null if account type is not found
           if @result = 0
               return -100
               
           return @id
		end
go


