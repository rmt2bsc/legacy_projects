/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     4/24/2007 9:48:26 AM                         */
/*==============================================================*/


/*==============================================================*/
/* View: VW_CREDITOR_XACT_HIST                                  */
/*==============================================================*/
create view VW_CREDITOR_XACT_HIST as
Select  xact.id xact_id,
        creditor.id as creditor_id, 
    	creditor.account_number as account_number, 
    	creditor.creditor_type_id as creditor_type_id, 
    	creditor.active as active, 
    	creditor.apr as apr, 
    	creditor.credit_limit as credit_limit, 
    	creditor_type.description as creditor_type_description,
    	business.id as business_id,
    	business.longname as longname, 
    	business.shortname as shortname, 
    	business.serv_type as serv_type, 
    	business.bus_type as bus_type, 
    	business.contact_ext as contact_ext, 
    	business.contact_firstname as contact_firstname, 
    	business.contact_lastname as contact_lastname, 
    	business.contact_phone as contact_phone, 
    	business.tax_id as tax_id, 
    	business.website as website, 		
    	xact.xact_amount xact_amount, 
    	xact.xact_date xact_date, 
    	xact.xact_type_id xact_type_id,
    	xact.reason reason,
    	xact_type.description xact_type_name,
        xact.date_created,
        xact.user_id,
        xact.posted_date,
        xact.confirm_no,
        xact.neg_instr_no,
        xact.tender_id,
        creditor_activity.id creditor_activity_id,
        creditor_activity.amount creditor_activity_amount
From xact, 
     xact_type, 
     business,
     creditor,
     creditor_type,
     creditor_activity
Where 
      xact.xact_type_id = xact_type.id
  and creditor.business_id = business.id 
  and creditor_activity.creditor_id = creditor.id
  and creditor_activity.xact_id = xact.id
  and creditor_type.id = creditor.creditor_type_id
go

