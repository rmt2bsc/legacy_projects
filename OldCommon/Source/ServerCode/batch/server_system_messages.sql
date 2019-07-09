EXEC sp_addmessage 50001, 18, '%s'
go
EXEC sp_addmessage 50002, 18, 'General Ledger Account  category was not found.    Category: %d, Account Type: %d'
go
EXEC sp_addmessage 50003, 18, 'General Ledger Account  type does not exist.     Account Type: %d'
go
EXEC sp_addmessage 50004, 18, 'General Ledger Account  Id cannot be blank and must greater than zero'
go
EXEC sp_addmessage 50005, 18, 'General Ledger Account  does not exist.     GL Account Id: %d'
go
EXEC sp_addmessage 50006, 18, 'When adding Creditor/Vendor, GL Account Type must be a Liability'
go
EXEC sp_addmessage 50007, 18, 'Business Id cannot be blank and must be greater that zero when adding a Creditor/Vendor'
go
EXEC sp_addmessage 50008, 18, 'Business is not found using the following business id: %d'
go
EXEC sp_addmessage 50009, 18, 'Creditor/Vendor Type cannot be blank and must be greater than zero'
go
EXEC sp_addmessage 50010, 18, 'Creditor Type Id does not exist for the Creditor/Vendor that is being added'
go
EXEC sp_addmessage 50011, 18, 'Creditor Type Id does not exist for the Creditor/Vendor that is being added'
go
EXEC sp_addmessage 50012, 18, 'There is a conflict in GL Account Id''s when processing %s.   Expected Account Id: %d, Actual Account Id: %d'
go
EXEC sp_addmessage 50013, 18, 'Either personal data or business data must be supplied when processing this type of transaction'
go
EXEC sp_addmessage 50014, 18, 'The Person Id supplied for this transaction does not exist in the system, %d'
go
EXEC sp_addmessage 50015, 18, 'The Business Id supplied for this transaction does not exist in the system, %d'
go
EXEC sp_addmessage 50016, 18, 'Creditor/Vendor was not found in the system, %d'
go
EXEC sp_addmessage 50017, 18, 'Problem obtaining the primary key for the item added to the item_master table.    Item Master update failed.   Vendor Id: %d, Item Description: %s, Vendor Item Number %s, Vendor Item Serial Number: %s'
go
EXEC sp_addmessage 50018, 18, 'Customer was not found in the system, %d'
go
EXEC sp_addmessage 50019, 18, 'Item Master Id was not found in the system, %d'
go
EXEC sp_addmessage 50020, 18, 'Transaction Type Id was not found in the system, %d'
go
EXEC sp_addmessage 50021, 18, 'Sales Order  was not found in the system, %d'
go
EXEC sp_addmessage 50022, 18, 'Sales Order  must be invoiced before cancelling'
go
EXEC sp_addmessage 50023, 18, 'Problem cancelling sales order.   Sales order is not linked to a Transaction'
go
EXEC sp_addmessage 50024, 18, 'Problem obtaining current status history id for item %d'
go
EXEC sp_addmessage 50025, 18, 'Problem retrieving current status of item %d using status id %d'
go
EXEC sp_addmessage 50026, 18, 'Problem obtaining destination item status using description, %s, for item %d'
go
EXEC sp_addmessage 50027, 18, 'Problem retrieving current status id of Sales Order %d'
go
EXEC sp_addmessage 50028, 18, 'Problem obtaining destination sales order status using description, %s'
go
EXEC sp_addmessage 50029, 18, 'Problem obtaining transaction type id using description, %s'
go
EXEC sp_addmessage 50030, 18, 'General Ledger account id for item %d could not be obtained for sales order %d'
go
EXEC sp_addmessage 50031, 18, 'Account preference does not exist'
go
EXEC sp_addmessage 50032, 18, 'General Ledger Account %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50033, 18, 'Creditor %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50034, 18, 'Customer %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50035, 18, 'Item Master %d cannot be deleted due to existing dependencies'
go
EXEC sp_addmessage 50036, 18, 'Problem invoicing sales order.   Transaction could not be created for sales order %d'
go
EXEC sp_addmessage 50037, 18, 'Quantity on Hand could not be obtained for Item %d'
go
EXEC sp_addmessage 50038, 18, 'Transaction %d does ot exist'
go
EXEC sp_addmessage 50039, 18, 'Problem creating reversal Transaction.    Old transaction that is to be reversed %d'
go
EXEC sp_addmessage 50040, 18, 'Project Event, %d, could not be found'
go
EXEC sp_addmessage 50041, 18, 'Current status for Project Event, %d, could not be obtained'
go
EXEC sp_addmessage 50042, 18, 'Project event compensation can only be calculated for Approved events.  Event in error: %d'
go
EXEC sp_addmessage 50043, 18, 'Project event cannot be submitted again once current status reaches "Approved" '
go
EXEC sp_addmessage 50044, 18, 'Project event current status could not be updated since it was unable to be found'
go
EXEC sp_addmessage 50045, 18, 'Project event current status must be "Submitted" when attempting to approve a project'
go
EXEC sp_addmessage 50046, 18, 'Project event current status must be "Submitted" when attempting to reject a project'
go
EXEC sp_addmessage 50047, 18, 'Project Rolee could not be found using key value: %d'
go
EXEC sp_addmessage 50048, 18, 'Billing rate could not be obtain for employee %d'
go
EXEC sp_addmessage 50049, 18, 'Project Preferences has not been setup.  Project cannot be processed'
go
EXEC sp_addmessage 50050, 18, 'Batch Job, %s, does not exist'
go
EXEC sp_addmessage 50051, 18, 'User Id already exists in the system: %s'
go
EXEC sp_addmessage 50052, 18, 'Employee Id, %d,  must exist in the system if supplied with a User Lgoin profile'
go
EXEC sp_addmessage 50053, 18, 'User Id does not exist: %s'
go
EXEC sp_addmessage 50054, 18, 'Password is incorrect'
go
EXEC sp_addmessage 50055, 18, 'Description is required for General Code Group'
go
EXEC sp_addmessage 50056, 18, 'Either Short Description or Long Description must have a value'
go
EXEC sp_addmessage 50057, 18, 'Group Id does not exist: %d'
go
EXEC sp_addmessage 50058, 18, 'Group Id, %d, is associated with one or more genreal codes and cannot be deleted'
go
EXEC sp_addmessage 50059, 18, 'General Code Id, %d, is associated with one or more child tables and cannot be deleted'
go
EXEC sp_addmessage 50060, 18, 'Business [%d] cannot be deleted, because one or more occurrences were found in the %s table'
go
EXEC sp_addmessage 50061, 18, 'Person Id and Business Id must be mutally exclusive for customer'
go
EXEC sp_addmessage 50062, 18, 'Person [%d] cannot be deleted, because one or more occurrences were found in the %s table'
go
EXEC sp_addmessage 50063, 18, 'Transaction Posting Failed: The target multiplier for Transactoin Type, %d, is not properly configured.  GL Account Id - %d, Post Amount - %s'
go
EXEC sp_addmessage 50064, 18, 'Transaction Posting Failed: The Offset multiplier for Transactoin Type, %d, is not properly configured.  GL Account Id - %d, Post Amount - %s'
go
EXEC sp_addmessage 50065, 18, 'Transaction Posting Failed!: GL Account Type failed to match any of the account types configured for selected Transaction Type %d, GL Account ID %d, Post Amount %s'
go