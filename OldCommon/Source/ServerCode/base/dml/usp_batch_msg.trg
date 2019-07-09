/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:49:22 AM                         */
/*==============================================================*/



/******************************************************************************************************************************
 *         Name: usp_batch_msg
 *      Returns: int
 * Descrsiption: Creates a batch job message.
 *******************************************************************************************************************************/

IF EXISTS (SELECT name FROM sysobjects WHERE name = 'usp_batch_msg' AND type = 'P')
   DROP PROCEDURE usp_batch_msg
GO

create procedure usp_batch_msg @batch_job_id varchar(20),
                               @msg varchar(255) as
  begin
    declare @exist_cnt int
    declare @user_id varchar(8)
    
    set @user_id = dbo.ufn_get_app_user_id()
    
    --  Verify batch_job_id exist
    select
        1
    from 
        batch_job
    where
        id = @batch_job_id
        
    if @@rowcount <= 0
       raiserror (50050, 18, 1, @batch_job_id)
       
    -- Create Batch Message       
    insert into batch_log 
       (batch_job_id, 
        msg, 
        user_id) 
    values 
	   (@batch_job_id, 
		@msg, 
		@user_id)
    
  end
go


