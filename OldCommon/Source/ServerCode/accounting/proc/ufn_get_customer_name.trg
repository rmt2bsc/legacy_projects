/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_customer_name
 *     Prototype: int person_id, int business_id
 *        Returns: varchar 
 *  Description: Retrieves the name of the customer based on either person id or business id.
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_customer_name') and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_customer_name
go

create function ufn_get_customer_name (@per_id int, @bus_id int) returns varchar(40) as

begin
  declare @cust_name varchar(40)
   
   
  if @per_id is null and @bus_id is null
     return null
     
  -- Notify the user that personal id and business id are mutually exclusive
  if @per_id > 0 and @bus_id > 0
    return '[Business/Person Conflict]'
           
  -- Try to get person name
  if @per_id > 0
    begin
     select
         @cust_name = shortname
      from
          person
      where
          id = @per_id
          
     --  Return null if personal contact information is not found
     if @@rowcount <= 0
        return '[Person N/A]'
   end
  
  -- Try to get business name
  if @bus_id > 0
    begin
     select
         @cust_name = longname
      from
          business
      where
          id = @bus_id
          
     --  Return null if business contact information is not found
     if @@rowcount <= 0
        return '[Business N/A]'
   end     
      
  return @cust_name
end
go


