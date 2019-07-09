/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     9/6/2006 9:23:20 PM                          */
/*==============================================================*/



create procedure usp_build_account_no @acct_type int,
                                       @acct_cat int,
                                       @acct_seq int,
                                       @acct_no varchar(25) output  as

begin
   declare @seq_calc varchar(10)

          --  Validate Data Values
   if @acct_type <= 0
       raiserror(50001, 18, 1)
       
   if @acct_cat <= 0
       raiserror(50001, 18, 1)
       
   if @acct_seq <= 0 
       raiserror(50001, 18, 1)
       
        -- Compute GL Account Number using the Account Type Id, Account Catgegory Id, and Account Sequence Number
   set @acct_no =  cast(@acct_type as varchar) + '-'  +  cast(@acct_cat as varchar)  +  '-'
   
   if @acct_seq >= 1 and @acct_seq <= 9
      set @seq_calc = '00' + cast(@acct_seq as varchar)
      
   if @acct_seq > 9 and @acct_seq <= 99
      set @seq_calc = '0' + cast(@acct_seq as varchar)

   if @acct_seq > 99 and @acct_seq <= 999
      set @seq_calc = cast(@acct_seq as varchar)
      
   set @acct_no = @acct_no + @seq_calc
   
end
GO
go


