/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_xact_type_id
 *     Prototype: varchar @descr
 *        Returns: int 
 *  Description: Retrieves transaction type id based on the value of @descr.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_xact_type_id') and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_xact_type_id
go



create function ufn_get_xact_type_id (@descr varchar(40))  returns int as

begin
  declare @id int
   
     -- Notify the user that we cannot search for a null description
  if @descr is null
    return -1
           
  select
      @id = id
  from
      xact_type
  where
      description = @descr
          
     --  Return null if transation type is not found
  if @@rowcount <= 0
     return -100
    
  return @id
end
go


