/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Method: ufn_get_app_user_id
 *     Prototype: none
 *        Returns: varchar
 *  Description: Retrieves corresponding applicatio user id for the current system process id (Connection)
 *******************************************************************************************************************************/
if exists (select 1 from sysobjects where  id = object_id('ufn_get_app_user_id')  and type in ('IF', 'FN', 'TF'))
   drop function ufn_get_app_user_id
go

create function ufn_get_app_user_id()  returns varchar(25) as

		begin
          declare @user_id varchar(50)

          select
                   @user_id = app_user_id
           from
                   user_connect_xref
           where
                   con_id = @@spid

           if @user_id is null
              set @user_id = 'Unknown'
              
           return @user_id
		end
go


